package com.lee.berries.datasource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.util.StringUtils;

import com.alibaba.druid.pool.DruidDataSource;
import com.lee.berries.datasource.WeightedRoundRobinScheduling.Server;

public class BerriesDataSource extends AbstractBerriesDataSource{

	private static final long serialVersionUID = 1L;

	private final static String HOST_SPLIT = ","; //多主机分隔符
	
	private final static String HOST_REXP = "\\[.*\\]";
	
	private String weight; //读的权重 0为不参加读
	private String defaultHost; //默认数据源主机地址，ip:port的形式
	
	private Map<String, DataSource> resolvedDataSources;
	
	private int hostCount = 0;
	
	private String defaultTargetDataSource;
	
	private DataSource currentDataSource;
	
	private List<String> keys = new ArrayList<>();
	
	private WeightedRoundRobinScheduling weightedRoundRobinScheduling;
	
	/**
	 * 采用[]形式，以,分割每个主机
	 * [192.168.5.11:3306,192.168.5.12:3306]
	 * 采用读写分离的时候默认第一个主机为写
	 * @throws SQLException 
	 */
	public void init() throws SQLException {
		String []hostList = getHostList();
		if(hostList != null) {
			hostCount = hostList.length;
			resolvedDataSources = new HashMap<>();
			for(int i = 0; i < hostList.length; i++) {
				String key = "";
				String host = hostList[i];
				if(i == 0) {
					key = BerriesDataSourceHolder.WRITE_KEY;
				}
				else {
					key = BerriesDataSourceHolder.READ_KEY + i;
				}
				DruidDataSource dataSource = create(key, host);
				resolvedDataSources.put(key, dataSource);
				if(host.equals(defaultHost)) {
					defaultTargetDataSource = key;
				}
				keys.add(key);
				dataSource.init();
			}
			if(defaultTargetDataSource == null) {
				defaultTargetDataSource = BerriesDataSourceHolder.WRITE_KEY;
			}
			currentDataSource = resolvedDataSources.get(defaultTargetDataSource);
			
			initWeight();
			
		}
		else {
			hostCount = 1;
			DruidDataSource dataSource = create(BerriesDataSourceHolder.WRITE_KEY, "");
			currentDataSource = dataSource;
			defaultTargetDataSource = BerriesDataSourceHolder.WRITE_KEY;
		}
		
	}
	
	public void close() {
		for(DataSource dataSource:resolvedDataSources.values()) {
			DruidDataSource druidDataSource = (DruidDataSource) dataSource;
			druidDataSource.close();
		}
	}
	
	private void initWeight() {
		if(hostCount > 1) {
			if(StringUtils.isEmpty(weight)) { //如果没有设置权重，那么设置默认权重， 写库不参与读，读库权重一致。
				for(int i = 0; i < hostCount; i++) {
					if(i == 0) { //默认写库不参与负载均衡
						weight = "-1";
					}
					else {
						weight += ",1";
					}
				}
			}
			String []weights = weight.split(HOST_SPLIT);
			if(weights.length != hostCount) {
				throw new RuntimeException("weight count must be equas server count!");
			}
			weightedRoundRobinScheduling = new WeightedRoundRobinScheduling();
			for(int i = 0; i < hostCount; i++) {
				weightedRoundRobinScheduling.add(keys.get(i), Integer.valueOf(weights[i]));
			}
		}
	}
	
	private String[] getHostList(){
		String jdbcUrl = super.getJdbcUrl();
		int start = jdbcUrl.indexOf("[");
		int end = jdbcUrl.indexOf("]");
		if(start == -1 || end == -1) {
			return null;
		}
		String hosts = jdbcUrl.substring(start + 1, end);
		return hosts.split(HOST_SPLIT);
	}
	
	/**
	 * 创建一个数据源
	 * @param host
	 * @return
	 * @throws SQLException 
	 */
	private DruidDataSource create(String key, String host) throws SQLException {
		String jdbcUrl = super.getJdbcUrl().replaceFirst(HOST_REXP, host);
		DruidDataSource dataSource = new DruidDataSource();
		
		//dataSource.setName(host + "_" + key);
		dataSource.setUrl(jdbcUrl);
		dataSource.setUsername(getUsername());
		dataSource.setPassword(getPassword());
		
		dataSource.setFilters(filters);
		
		dataSource.setInitialSize(initialSize);
		dataSource.setMaxActive(maxActive);
		dataSource.setMaxWait(maxWait);
		dataSource.setMinIdle(minIdle);
		
		dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
		dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		
		dataSource.setTestOnBorrow(isTestOnBorrow());
		dataSource.setTestWhileIdle(isTestWhileIdle());
		dataSource.setTestOnReturn(isTestOnReturn());
		
		dataSource.setPoolPreparedStatements(poolPreparedStatements);
		dataSource.setMaxOpenPreparedStatements(maxOpenPreparedStatements);
		
		dataSource.setAsyncInit(asyncInit);
		return dataSource;
	}

	@Override
	public Connection getConnection() throws SQLException {
		return getDataSource().getConnection();
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		return getDataSource().getConnection(username, password);
	}
	
	private DataSource getDataSource() {
		if(hostCount == 1) { //如果只有一个连接机器，主从不分直接返回默认的
			return currentDataSource;
		}
		else { //如果是写库的话，直接返回写库的数据源
			String key = BerriesDataSourceHolder.get();
			if(BerriesDataSourceHolder.WRITE_KEY.equals(key)) {
				return resolvedDataSources.get(key);
			}
			else { //如果是读库，那么根据权重进行负载均衡
				Server server = weightedRoundRobinScheduling.GetBestServer();
				if(server != null) {
					key = server.getName();
				}
				else {
					key = defaultTargetDataSource;
				}
				DataSource readDataSource = resolvedDataSources.get(key);
				return readDataSource;
			}
		}
	}

	public String getDefaultHost() {
		return defaultHost;
	}

	public void setDefaultHost(String defaultHost) {
		this.defaultHost = defaultHost;
	}
	
	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}
	
	
}
