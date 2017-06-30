package com.lee.berries.soa;

import java.lang.annotation.Annotation;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.ProviderConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;

/**
 * SOA服务配置，建议还是使用配置文件来发布吧，程序发布不够灵活
 * 
 * @author huangwq 下午11:20:24
 *
 */
public class SOAConfiguration implements InitializingBean {

	private RegistryConfig registry;

	private ProviderConfig provider;

	private ApplicationConfig application;

	private ProtocolConfig dubboProtocol;

	private ProtocolConfig restProtocol;

	public final static String DEFAULTVERSION = "1.0.0";

	@Value(value = "${enableSOA:false}")
	private boolean enableSOA;

	public boolean isEnableSOA() {
		return enableSOA;
	}

	@Value(value = "${soa.appname:sampleService}")
	private String appName;

	@Value(value = "${soa.registry:zookeeper://192.168.1.51:2181?backup=192.168.1.50:2182,192.168.1.52:2181}")
	private String registryUrl;

	@Value(value = "${soa.localport:20880}")
	private Integer localPort = 0;

	@Value(value = "${soa.restport:20881}")
	private Integer restPort = 0;

	@Value(value = "${soa.provider.filter:}")
	private String providerFilter;

	@Value(value = "${soa.timeout:0}")
	// 默认为0，只有当timeout大于0时，才设置service的timeout
	private Integer timeout;

	public RegistryConfig getRegistry() {
		return registry;
	}

	public void setRegistry(RegistryConfig registry) {
		this.registry = registry;
	}

	public ApplicationConfig getApplication() {
		return application;
	}

	public void setApplication(ApplicationConfig application) {
		this.application = application;
	}

	public String getRegistryUrl() {
		return registryUrl;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (enableSOA == false) {
			return;
		}
		initServiceConfig();
	}

	private void initServiceConfig() throws URISyntaxException {

		application = new ApplicationConfig();
		application.setName(appName);
		provider = new ProviderConfig();
		provider.setFilter(providerFilter);
		// 连接注册中心配置
		registry = new RegistryConfig();
		if (StringUtils.isEmpty(registryUrl) || registryUrl.startsWith("dubbo://")) { // registryUrl为空或者以dubbo://开头，将registry地址设为N/A（不使用注册中心）
			registry.setAddress(RegistryConfig.NO_AVAILABLE);
		} else {
			registry.setAddress(registryUrl);
		}
		// URI uri = new URI(registryUrl);
		// registry.setProtocol(uri.getScheme());
		// registry.setAddress(uri.getHost() + ":" + uri.getPort());
		// 服务提供者协议配置
		dubboProtocol = new ProtocolConfig();
		dubboProtocol.setName("dubbo");
		dubboProtocol.setPort(localPort);
		dubboProtocol.setThreads(200);
		dubboProtocol.setSerialization("hessian2");

		restProtocol = new ProtocolConfig();
		restProtocol.setName("rest");
		restProtocol.setPort(restPort);
		restProtocol.setThreads(100);
	}

	/**
	 * 通过实体类获得远程接口
	 * 
	 * @param cls
	 * @return
	 */
	private Class<?> getServiceInteface(Class<?> cls) {
		// com.jumore.dove.demo.service.SOADemoUserService$$EnhancerByCGLIB$$46bf2215
		String clsName = cls.getName();
		if (clsName.contains("EnhancerByCGLIB")) {
			Class<?> superCls = cls.getSuperclass();
			if (superCls != null) {
				cls = superCls;
			}
		}

		Class<?>[] ifaces = cls.getInterfaces();
		Class<?> iface = ifaces[0];
		for (int i = 0; i < ifaces.length; i++) {
			if (ifaces[i].equals(SOARemote.class)) {
				continue;
			} else {
				iface = ifaces[i];
				break;
			}
		}
		return iface;
	}

	public void exportRemoteService(SOARemote remoteService) {
		
		Class<?> iface = getServiceInteface(remoteService.getClass());

		ServiceConfig<SOARemote> service = new ServiceConfig<SOARemote>(); // 此实例很重，封装了与注册中心的连接，请自行缓存，否则可能造成内存和连接泄漏
		service.setApplication(application);
		service.setRegistry(registry); // 多个注册中心可以用setRegistries()
		List<ProtocolConfig> protocols = new ArrayList<>();
		protocols.add(dubboProtocol);

		Annotation[] annotations = iface.getAnnotations();
		for (Annotation annotation : annotations) {
			if (annotation.annotationType().getName().equals("javax.ws.rs.Path")) {
				protocols.add(restProtocol);
			}
		}
		service.setProtocols(protocols);
		service.setInterface(iface);
		service.setRef(remoteService);
		service.setVersion(remoteService.getVersion());
		if (!StringUtils.isEmpty(provider)) {
			service.setProvider(provider);
		}
		if (timeout > 0) {
			service.setTimeout(timeout);
		}

		// 暴露及注册服务
		service.export();

	}
}
