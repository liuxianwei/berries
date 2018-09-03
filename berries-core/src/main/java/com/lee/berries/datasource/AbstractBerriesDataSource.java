package com.lee.berries.datasource;

import java.io.PrintWriter;
import java.sql.Driver;
import java.util.Date;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import javax.management.ObjectName;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;

import org.springframework.jdbc.datasource.AbstractDataSource;

import com.alibaba.druid.pool.DruidDataSourceStatLogger;
import com.alibaba.druid.pool.DruidDataSourceStatLoggerImpl;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.alibaba.druid.pool.ExceptionSorter;
import com.alibaba.druid.pool.ValidConnectionChecker;
import com.alibaba.druid.util.Histogram;

public abstract class AbstractBerriesDataSource extends AbstractDataSource implements java.io.Serializable{

	private static final long                          serialVersionUID                          = 1L;

    public final static int                            DEFAULT_INITIAL_SIZE                      = 0;
    public final static int                            DEFAULT_MAX_ACTIVE_SIZE                   = 8;
    public final static int                            DEFAULT_MAX_IDLE                          = 8;
    public final static int                            DEFAULT_MIN_IDLE                          = 0;
    public final static int                            DEFAULT_MAX_WAIT                          = -1;
    public final static String                         DEFAULT_VALIDATION_QUERY                  = null;                                                //
    public final static boolean                        DEFAULT_TEST_ON_BORROW                    = false;
    public final static boolean                        DEFAULT_TEST_ON_RETURN                    = false;
    public final static boolean                        DEFAULT_WHILE_IDLE                        = true;
    public static final long                           DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS = 60 * 1000L;
    public static final long                           DEFAULT_TIME_BETWEEN_CONNECT_ERROR_MILLIS = 30 * 1000;
    public static final int                            DEFAULT_NUM_TESTS_PER_EVICTION_RUN        = 3;

    /**
     * The default value for {@link #getMinEvictableIdleTimeMillis}.
     * 
     * @see #getMinEvictableIdleTimeMillis
     * @see #setMinEvictableIdleTimeMillis
     */
    public static final long                           DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS    = 1000L * 60L * 30L;
    public static final long                           DEFAULT_MAX_EVICTABLE_IDLE_TIME_MILLIS    = 1000L * 60L * 60L * 7;
    public static final long                           DEFAULT_PHY_TIMEOUT_MILLIS                = -1;

    protected volatile boolean                         defaultAutoCommit                         = true;
    protected volatile Boolean                         defaultReadOnly;
    protected volatile Integer                         defaultTransactionIsolation;
    protected volatile String                          defaultCatalog                            = null;

    protected String                                   name;

    protected volatile String                          username;
    protected volatile String                          password;
    protected volatile String                          jdbcUrl;
    protected volatile String                          driverClass;
    protected volatile ClassLoader                     driverClassLoader;
    protected volatile Properties                      connectProperties                         = new Properties();

    protected volatile PasswordCallback                passwordCallback;
    protected volatile NameCallback                    userCallback;

    protected volatile int                             initialSize                               = DEFAULT_INITIAL_SIZE;
    protected volatile int                             maxActive                                 = DEFAULT_MAX_ACTIVE_SIZE;
    protected volatile int                             minIdle                                   = DEFAULT_MIN_IDLE;
    protected volatile int                             maxIdle                                   = DEFAULT_MAX_IDLE;
    protected volatile long                            maxWait                                   = DEFAULT_MAX_WAIT;
    protected int                                      notFullTimeoutRetryCount                  = 0;

    protected volatile String                          validationQuery                           = DEFAULT_VALIDATION_QUERY;
    protected volatile int                             validationQueryTimeout                    = -1;
    private volatile boolean                           testOnBorrow                              = DEFAULT_TEST_ON_BORROW;
    private volatile boolean                           testOnReturn                              = DEFAULT_TEST_ON_RETURN;
    private volatile boolean                           testWhileIdle                             = DEFAULT_WHILE_IDLE;
    protected volatile boolean                         poolPreparedStatements                    = false;
    protected volatile boolean                         sharePreparedStatements                   = false;
    protected volatile int                             maxPoolPreparedStatementPerConnectionSize = 10;

    protected volatile boolean                         inited                                    = false;

    protected PrintWriter                              logWriter                                 = new PrintWriter(
                                                                                                                   System.out);

    protected String                                   filters                                   = "";
    private boolean                                    clearFiltersEnable                        = true;
    protected volatile ExceptionSorter                 exceptionSorter                           = null;

    protected Driver                                   driver;

    protected volatile int                             queryTimeout;
    protected volatile int                             transactionQueryTimeout;

    protected AtomicLong                               createErrorCount                          = new AtomicLong();

    protected long                                     createTimespan;

    protected volatile int                             maxWaitThreadCount                        = -1;

    protected volatile boolean                         accessToUnderlyingConnectionAllowed       = true;

    protected volatile long                            timeBetweenEvictionRunsMillis             = DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS;

    protected volatile int                             numTestsPerEvictionRun                    = DEFAULT_NUM_TESTS_PER_EVICTION_RUN;

    protected volatile long                            minEvictableIdleTimeMillis                = DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS;
    protected volatile long                            maxEvictableIdleTimeMillis                = DEFAULT_MAX_EVICTABLE_IDLE_TIME_MILLIS;

    protected volatile long                            phyTimeoutMillis                          = DEFAULT_PHY_TIMEOUT_MILLIS;

    protected volatile boolean                         removeAbandoned;

    protected volatile long                            removeAbandonedTimeoutMillis              = 300 * 1000;

    protected volatile boolean                         logAbandoned;

    protected volatile int                             maxOpenPreparedStatements                 = -1;

    protected volatile List<String>                    connectionInitSqls;

    protected volatile String                          dbType;

    protected volatile long                            timeBetweenConnectErrorMillis             = DEFAULT_TIME_BETWEEN_CONNECT_ERROR_MILLIS;

    protected volatile ValidConnectionChecker          validConnectionChecker                    = null;

    protected final AtomicLong                         errorCount                                = new AtomicLong();
    protected final AtomicLong                         dupCloseCount                             = new AtomicLong();

    protected final Map<DruidPooledConnection, Object> activeConnections                         = new IdentityHashMap<DruidPooledConnection, Object>();
    protected final static Object                      PRESENT                                   = new Object();

    protected long                                     id;

    protected final Date                               createdTime                               = new Date();
    protected Date                                     initedTime;

    protected int                                      connectionErrorRetryAttempts              = 30;

    protected boolean                                  breakAfterAcquireFailure                  = false;

    protected long                                     transactionThresholdMillis                = 0L;

    protected final AtomicLong                         commitCount                               = new AtomicLong();
    protected final AtomicLong                         startTransactionCount                     = new AtomicLong();
    protected final AtomicLong                         rollbackCount                             = new AtomicLong();
    protected final AtomicLong                         cachedPreparedStatementHitCount           = new AtomicLong();
    protected final AtomicLong                         preparedStatementCount                    = new AtomicLong();
    protected final AtomicLong                         closedPreparedStatementCount              = new AtomicLong();
    protected final AtomicLong                         cachedPreparedStatementCount              = new AtomicLong();
    protected final AtomicLong                         cachedPreparedStatementDeleteCount        = new AtomicLong();
    protected final AtomicLong                         cachedPreparedStatementMissCount          = new AtomicLong();

    protected final Histogram                          transactionHistogram                      = new Histogram(1,
                                                                                                                 10,
                                                                                                                 100,
                                                                                                                 1000,
                                                                                                                 10 * 1000,
                                                                                                                 100 * 1000);

    private boolean                                    dupCloseLogEnable                         = false;

    private ObjectName                                 objectName;

    protected final AtomicLong                         executeCount                              = new AtomicLong();

    protected volatile Throwable                       createError;
    protected volatile Throwable                       lastError;
    protected volatile long                            lastErrorTimeMillis;
    protected volatile Throwable                       lastCreateError;
    protected volatile long                            lastCreateErrorTimeMillis;
    protected volatile long                            lastCreateStartTimeMillis;

    protected boolean                                  isOracle                                  = false;

    protected boolean                                  useOracleImplicitCache                    = true;

    protected ReentrantLock                            lock;
    protected Condition                                notEmpty;
    protected Condition                                empty;

    protected ReentrantLock                            activeConnectionLock                      = new ReentrantLock();

    protected AtomicInteger                            creatingCount                             = new AtomicInteger();
    protected AtomicLong                               createCount                               = new AtomicLong();
    protected AtomicLong                               destroyCount                              = new AtomicLong();

    private Boolean                                    useUnfairLock                             = null;

    private boolean                                    useLocalSessionState                      = true;

    protected long                                     timeBetweenLogStatsMillis;
    protected DruidDataSourceStatLogger                statLogger                                = new DruidDataSourceStatLoggerImpl();
    
    private boolean                                    asyncCloseConnectionEnable                = false;
    protected int                                      maxCreateTaskCount                        = 3;
    protected boolean                                  failFast                                  = false;
    protected AtomicBoolean                            failContinuous                            = new AtomicBoolean(false);
    protected ScheduledExecutorService                 destroyScheduler;
    protected ScheduledExecutorService                 createScheduler;

    protected boolean                                  initVariants                              = false;
    protected boolean                                  initGlobalVariants                        = false;
    protected boolean                         		   asyncInit                 				 = false;
   
    public boolean isAsyncInit() {
		return asyncInit;
	}
	public void setAsyncInit(boolean asyncInit) {
		this.asyncInit = asyncInit;
	}
	public boolean isDefaultAutoCommit() {
		return defaultAutoCommit;
	}
	public void setDefaultAutoCommit(boolean defaultAutoCommit) {
		this.defaultAutoCommit = defaultAutoCommit;
	}
	public Boolean getDefaultReadOnly() {
		return defaultReadOnly;
	}
	public void setDefaultReadOnly(Boolean defaultReadOnly) {
		this.defaultReadOnly = defaultReadOnly;
	}
	public Integer getDefaultTransactionIsolation() {
		return defaultTransactionIsolation;
	}
	public void setDefaultTransactionIsolation(Integer defaultTransactionIsolation) {
		this.defaultTransactionIsolation = defaultTransactionIsolation;
	}
	public String getDefaultCatalog() {
		return defaultCatalog;
	}
	public void setDefaultCatalog(String defaultCatalog) {
		this.defaultCatalog = defaultCatalog;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getJdbcUrl() {
		return jdbcUrl;
	}
	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}
	public String getDriverClass() {
		return driverClass;
	}
	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}
	public ClassLoader getDriverClassLoader() {
		return driverClassLoader;
	}
	public void setDriverClassLoader(ClassLoader driverClassLoader) {
		this.driverClassLoader = driverClassLoader;
	}
	public Properties getConnectProperties() {
		return connectProperties;
	}
	public void setConnectProperties(Properties connectProperties) {
		this.connectProperties = connectProperties;
	}
	public PasswordCallback getPasswordCallback() {
		return passwordCallback;
	}
	public void setPasswordCallback(PasswordCallback passwordCallback) {
		this.passwordCallback = passwordCallback;
	}
	public NameCallback getUserCallback() {
		return userCallback;
	}
	public void setUserCallback(NameCallback userCallback) {
		this.userCallback = userCallback;
	}
	public int getInitialSize() {
		return initialSize;
	}
	public void setInitialSize(int initialSize) {
		this.initialSize = initialSize;
	}
	public int getMaxActive() {
		return maxActive;
	}
	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}
	public int getMinIdle() {
		return minIdle;
	}
	public void setMinIdle(int minIdle) {
		this.minIdle = minIdle;
	}
	public int getMaxIdle() {
		return maxIdle;
	}
	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}
	public long getMaxWait() {
		return maxWait;
	}
	public void setMaxWait(long maxWait) {
		this.maxWait = maxWait;
	}
	public int getNotFullTimeoutRetryCount() {
		return notFullTimeoutRetryCount;
	}
	public void setNotFullTimeoutRetryCount(int notFullTimeoutRetryCount) {
		this.notFullTimeoutRetryCount = notFullTimeoutRetryCount;
	}
	public String getValidationQuery() {
		return validationQuery;
	}
	public void setValidationQuery(String validationQuery) {
		this.validationQuery = validationQuery;
	}
	public int getValidationQueryTimeout() {
		return validationQueryTimeout;
	}
	public void setValidationQueryTimeout(int validationQueryTimeout) {
		this.validationQueryTimeout = validationQueryTimeout;
	}
	public boolean isTestOnBorrow() {
		return testOnBorrow;
	}
	public void setTestOnBorrow(boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}
	public boolean isTestOnReturn() {
		return testOnReturn;
	}
	public void setTestOnReturn(boolean testOnReturn) {
		this.testOnReturn = testOnReturn;
	}
	public boolean isTestWhileIdle() {
		return testWhileIdle;
	}
	public void setTestWhileIdle(boolean testWhileIdle) {
		this.testWhileIdle = testWhileIdle;
	}
	public boolean isPoolPreparedStatements() {
		return poolPreparedStatements;
	}
	public void setPoolPreparedStatements(boolean poolPreparedStatements) {
		this.poolPreparedStatements = poolPreparedStatements;
	}
	public boolean isSharePreparedStatements() {
		return sharePreparedStatements;
	}
	public void setSharePreparedStatements(boolean sharePreparedStatements) {
		this.sharePreparedStatements = sharePreparedStatements;
	}
	public int getMaxPoolPreparedStatementPerConnectionSize() {
		return maxPoolPreparedStatementPerConnectionSize;
	}
	public void setMaxPoolPreparedStatementPerConnectionSize(int maxPoolPreparedStatementPerConnectionSize) {
		this.maxPoolPreparedStatementPerConnectionSize = maxPoolPreparedStatementPerConnectionSize;
	}
	public boolean isInited() {
		return inited;
	}
	public void setInited(boolean inited) {
		this.inited = inited;
	}
	public PrintWriter getLogWriter() {
		return logWriter;
	}
	public void setLogWriter(PrintWriter logWriter) {
		this.logWriter = logWriter;
	}
	public String getFilters() {
		return filters;
	}
	public void setFilters(String filters) {
		this.filters = filters;
	}
	public boolean isClearFiltersEnable() {
		return clearFiltersEnable;
	}
	public void setClearFiltersEnable(boolean clearFiltersEnable) {
		this.clearFiltersEnable = clearFiltersEnable;
	}
	public ExceptionSorter getExceptionSorter() {
		return exceptionSorter;
	}
	public void setExceptionSorter(ExceptionSorter exceptionSorter) {
		this.exceptionSorter = exceptionSorter;
	}
	public Driver getDriver() {
		return driver;
	}
	public void setDriver(Driver driver) {
		this.driver = driver;
	}
	public int getQueryTimeout() {
		return queryTimeout;
	}
	public void setQueryTimeout(int queryTimeout) {
		this.queryTimeout = queryTimeout;
	}
	public int getTransactionQueryTimeout() {
		return transactionQueryTimeout;
	}
	public void setTransactionQueryTimeout(int transactionQueryTimeout) {
		this.transactionQueryTimeout = transactionQueryTimeout;
	}
	public AtomicLong getCreateErrorCount() {
		return createErrorCount;
	}
	public void setCreateErrorCount(AtomicLong createErrorCount) {
		this.createErrorCount = createErrorCount;
	}
	public long getCreateTimespan() {
		return createTimespan;
	}
	public void setCreateTimespan(long createTimespan) {
		this.createTimespan = createTimespan;
	}
	public int getMaxWaitThreadCount() {
		return maxWaitThreadCount;
	}
	public void setMaxWaitThreadCount(int maxWaitThreadCount) {
		this.maxWaitThreadCount = maxWaitThreadCount;
	}
	public boolean isAccessToUnderlyingConnectionAllowed() {
		return accessToUnderlyingConnectionAllowed;
	}
	public void setAccessToUnderlyingConnectionAllowed(boolean accessToUnderlyingConnectionAllowed) {
		this.accessToUnderlyingConnectionAllowed = accessToUnderlyingConnectionAllowed;
	}
	public long getTimeBetweenEvictionRunsMillis() {
		return timeBetweenEvictionRunsMillis;
	}
	public void setTimeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis) {
		this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
	}
	public int getNumTestsPerEvictionRun() {
		return numTestsPerEvictionRun;
	}
	public void setNumTestsPerEvictionRun(int numTestsPerEvictionRun) {
		this.numTestsPerEvictionRun = numTestsPerEvictionRun;
	}
	public long getMinEvictableIdleTimeMillis() {
		return minEvictableIdleTimeMillis;
	}
	public void setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis) {
		this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
	}
	public long getMaxEvictableIdleTimeMillis() {
		return maxEvictableIdleTimeMillis;
	}
	public void setMaxEvictableIdleTimeMillis(long maxEvictableIdleTimeMillis) {
		this.maxEvictableIdleTimeMillis = maxEvictableIdleTimeMillis;
	}
	public long getPhyTimeoutMillis() {
		return phyTimeoutMillis;
	}
	public void setPhyTimeoutMillis(long phyTimeoutMillis) {
		this.phyTimeoutMillis = phyTimeoutMillis;
	}
	public boolean isRemoveAbandoned() {
		return removeAbandoned;
	}
	public void setRemoveAbandoned(boolean removeAbandoned) {
		this.removeAbandoned = removeAbandoned;
	}
	public long getRemoveAbandonedTimeoutMillis() {
		return removeAbandonedTimeoutMillis;
	}
	public void setRemoveAbandonedTimeoutMillis(long removeAbandonedTimeoutMillis) {
		this.removeAbandonedTimeoutMillis = removeAbandonedTimeoutMillis;
	}
	public boolean isLogAbandoned() {
		return logAbandoned;
	}
	public void setLogAbandoned(boolean logAbandoned) {
		this.logAbandoned = logAbandoned;
	}
	public int getMaxOpenPreparedStatements() {
		return maxOpenPreparedStatements;
	}
	public void setMaxOpenPreparedStatements(int maxOpenPreparedStatements) {
		this.maxOpenPreparedStatements = maxOpenPreparedStatements;
	}
	public List<String> getConnectionInitSqls() {
		return connectionInitSqls;
	}
	public void setConnectionInitSqls(List<String> connectionInitSqls) {
		this.connectionInitSqls = connectionInitSqls;
	}
	public String getDbType() {
		return dbType;
	}
	public void setDbType(String dbType) {
		this.dbType = dbType;
	}
	public long getTimeBetweenConnectErrorMillis() {
		return timeBetweenConnectErrorMillis;
	}
	public void setTimeBetweenConnectErrorMillis(long timeBetweenConnectErrorMillis) {
		this.timeBetweenConnectErrorMillis = timeBetweenConnectErrorMillis;
	}
	public ValidConnectionChecker getValidConnectionChecker() {
		return validConnectionChecker;
	}
	public void setValidConnectionChecker(ValidConnectionChecker validConnectionChecker) {
		this.validConnectionChecker = validConnectionChecker;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getInitedTime() {
		return initedTime;
	}
	public void setInitedTime(Date initedTime) {
		this.initedTime = initedTime;
	}
	public int getConnectionErrorRetryAttempts() {
		return connectionErrorRetryAttempts;
	}
	public void setConnectionErrorRetryAttempts(int connectionErrorRetryAttempts) {
		this.connectionErrorRetryAttempts = connectionErrorRetryAttempts;
	}
	public boolean isBreakAfterAcquireFailure() {
		return breakAfterAcquireFailure;
	}
	public void setBreakAfterAcquireFailure(boolean breakAfterAcquireFailure) {
		this.breakAfterAcquireFailure = breakAfterAcquireFailure;
	}
	public long getTransactionThresholdMillis() {
		return transactionThresholdMillis;
	}
	public void setTransactionThresholdMillis(long transactionThresholdMillis) {
		this.transactionThresholdMillis = transactionThresholdMillis;
	}
	public boolean isDupCloseLogEnable() {
		return dupCloseLogEnable;
	}
	public void setDupCloseLogEnable(boolean dupCloseLogEnable) {
		this.dupCloseLogEnable = dupCloseLogEnable;
	}
	public ObjectName getObjectName() {
		return objectName;
	}
	public void setObjectName(ObjectName objectName) {
		this.objectName = objectName;
	}
	public Throwable getCreateError() {
		return createError;
	}
	public void setCreateError(Throwable createError) {
		this.createError = createError;
	}
	public Throwable getLastError() {
		return lastError;
	}
	public void setLastError(Throwable lastError) {
		this.lastError = lastError;
	}
	public long getLastErrorTimeMillis() {
		return lastErrorTimeMillis;
	}
	public void setLastErrorTimeMillis(long lastErrorTimeMillis) {
		this.lastErrorTimeMillis = lastErrorTimeMillis;
	}
	public Throwable getLastCreateError() {
		return lastCreateError;
	}
	public void setLastCreateError(Throwable lastCreateError) {
		this.lastCreateError = lastCreateError;
	}
	public long getLastCreateErrorTimeMillis() {
		return lastCreateErrorTimeMillis;
	}
	public void setLastCreateErrorTimeMillis(long lastCreateErrorTimeMillis) {
		this.lastCreateErrorTimeMillis = lastCreateErrorTimeMillis;
	}
	public long getLastCreateStartTimeMillis() {
		return lastCreateStartTimeMillis;
	}
	public void setLastCreateStartTimeMillis(long lastCreateStartTimeMillis) {
		this.lastCreateStartTimeMillis = lastCreateStartTimeMillis;
	}
	public boolean isOracle() {
		return isOracle;
	}
	public void setOracle(boolean isOracle) {
		this.isOracle = isOracle;
	}
	public boolean isUseOracleImplicitCache() {
		return useOracleImplicitCache;
	}
	public void setUseOracleImplicitCache(boolean useOracleImplicitCache) {
		this.useOracleImplicitCache = useOracleImplicitCache;
	}
	public ReentrantLock getLock() {
		return lock;
	}
	public void setLock(ReentrantLock lock) {
		this.lock = lock;
	}
	public Condition getNotEmpty() {
		return notEmpty;
	}
	public void setNotEmpty(Condition notEmpty) {
		this.notEmpty = notEmpty;
	}
	public Condition getEmpty() {
		return empty;
	}
	public void setEmpty(Condition empty) {
		this.empty = empty;
	}
	public ReentrantLock getActiveConnectionLock() {
		return activeConnectionLock;
	}
	public void setActiveConnectionLock(ReentrantLock activeConnectionLock) {
		this.activeConnectionLock = activeConnectionLock;
	}
	public AtomicInteger getCreatingCount() {
		return creatingCount;
	}
	public void setCreatingCount(AtomicInteger creatingCount) {
		this.creatingCount = creatingCount;
	}
	public AtomicLong getCreateCount() {
		return createCount;
	}
	public void setCreateCount(AtomicLong createCount) {
		this.createCount = createCount;
	}
	public AtomicLong getDestroyCount() {
		return destroyCount;
	}
	public void setDestroyCount(AtomicLong destroyCount) {
		this.destroyCount = destroyCount;
	}
	public Boolean getUseUnfairLock() {
		return useUnfairLock;
	}
	public void setUseUnfairLock(Boolean useUnfairLock) {
		this.useUnfairLock = useUnfairLock;
	}
	public boolean isUseLocalSessionState() {
		return useLocalSessionState;
	}
	public void setUseLocalSessionState(boolean useLocalSessionState) {
		this.useLocalSessionState = useLocalSessionState;
	}
	public long getTimeBetweenLogStatsMillis() {
		return timeBetweenLogStatsMillis;
	}
	public void setTimeBetweenLogStatsMillis(long timeBetweenLogStatsMillis) {
		this.timeBetweenLogStatsMillis = timeBetweenLogStatsMillis;
	}
	public DruidDataSourceStatLogger getStatLogger() {
		return statLogger;
	}
	public void setStatLogger(DruidDataSourceStatLogger statLogger) {
		this.statLogger = statLogger;
	}
	public boolean isAsyncCloseConnectionEnable() {
		return asyncCloseConnectionEnable;
	}
	public void setAsyncCloseConnectionEnable(boolean asyncCloseConnectionEnable) {
		this.asyncCloseConnectionEnable = asyncCloseConnectionEnable;
	}
	public int getMaxCreateTaskCount() {
		return maxCreateTaskCount;
	}
	public void setMaxCreateTaskCount(int maxCreateTaskCount) {
		this.maxCreateTaskCount = maxCreateTaskCount;
	}
	public boolean isFailFast() {
		return failFast;
	}
	public void setFailFast(boolean failFast) {
		this.failFast = failFast;
	}
	public AtomicBoolean getFailContinuous() {
		return failContinuous;
	}
	public void setFailContinuous(AtomicBoolean failContinuous) {
		this.failContinuous = failContinuous;
	}
	public ScheduledExecutorService getDestroyScheduler() {
		return destroyScheduler;
	}
	public void setDestroyScheduler(ScheduledExecutorService destroyScheduler) {
		this.destroyScheduler = destroyScheduler;
	}
	public ScheduledExecutorService getCreateScheduler() {
		return createScheduler;
	}
	public void setCreateScheduler(ScheduledExecutorService createScheduler) {
		this.createScheduler = createScheduler;
	}
	public boolean isInitVariants() {
		return initVariants;
	}
	public void setInitVariants(boolean initVariants) {
		this.initVariants = initVariants;
	}
	public boolean isInitGlobalVariants() {
		return initGlobalVariants;
	}
	public void setInitGlobalVariants(boolean initGlobalVariants) {
		this.initGlobalVariants = initGlobalVariants;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public AtomicLong getErrorCount() {
		return errorCount;
	}
	public AtomicLong getDupCloseCount() {
		return dupCloseCount;
	}
	public Map<DruidPooledConnection, Object> getActiveConnections() {
		return activeConnections;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public AtomicLong getCommitCount() {
		return commitCount;
	}
	public AtomicLong getStartTransactionCount() {
		return startTransactionCount;
	}
	public AtomicLong getRollbackCount() {
		return rollbackCount;
	}
	public AtomicLong getCachedPreparedStatementHitCount() {
		return cachedPreparedStatementHitCount;
	}
	public AtomicLong getPreparedStatementCount() {
		return preparedStatementCount;
	}
	public AtomicLong getClosedPreparedStatementCount() {
		return closedPreparedStatementCount;
	}
	public AtomicLong getCachedPreparedStatementCount() {
		return cachedPreparedStatementCount;
	}
	public AtomicLong getCachedPreparedStatementDeleteCount() {
		return cachedPreparedStatementDeleteCount;
	}
	public AtomicLong getCachedPreparedStatementMissCount() {
		return cachedPreparedStatementMissCount;
	}
	public Histogram getTransactionHistogram() {
		return transactionHistogram;
	}
	public AtomicLong getExecuteCount() {
		return executeCount;
	}
    
    
}
