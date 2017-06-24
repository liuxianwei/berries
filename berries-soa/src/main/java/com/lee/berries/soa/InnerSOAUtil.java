package com.lee.berries.soa;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import com.lee.berries.soa.util.ParamUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

/**
 * @author 黄奕鹏(大鹏)
 *
 */
public class InnerSOAUtil {

  private static Logger logger = LoggerFactory.getLogger(InnerSOAUtil.class);

  static String defaultCfgName = "soa.properties";

  public static boolean isClient = false;

  public static boolean isService = false;

  static boolean isInit = false;

  static ApplicationConfig application;

  static RegistryConfig registry;

  static ProtocolConfig protocol;

  static String appName;

  static String registryUrl;

  static void readServiceConfig(Properties prop) throws URISyntaxException {
    String localPort = prop.getProperty("soa.localport");
    localPort = localPort.trim();
    ParamUtil.check(appName.length() > 3, "soa.appname must > 3 length");
    ParamUtil.check(registryUrl.length() > 10, "soa.registry must > 10 length");
    ParamUtil.check(localPort.length() > 3, "soa.localport must > 3 length");
    int intLocalPort = Integer.parseInt(localPort);
    application = new ApplicationConfig();
    application.setName(appName);
    // 连接注册中心配置
    registry = new RegistryConfig();

    if (StringUtils.isEmpty(registryUrl) || registryUrl.startsWith("dubbo://")) { // registryUrl为空或者以dubbo://开头，将registry地址设为N/A（不使用注册中心）
      registry.setAddress(RegistryConfig.NO_AVAILABLE);
    } else {
      registry.setAddress(registryUrl);
    }

    //URI uri = new URI(registryUrl);
    //registry.setProtocol(uri.getScheme());
    // registry.setAddress("127.0.0.1:2181");
    //registry.setAddress("ju50:2181");
    //registry.setAddress(uri.getHost() + ":" + uri.getPort());
    // 服务提供者协议配置
    protocol = new ProtocolConfig();
    protocol.setName("dubbo");
    protocol.setPort(intLocalPort);
    protocol.setThreads(200);
    protocol.setSerialization("hessian2");
  }

  static void readBasicConfig(Properties prop) {
    appName = prop.getProperty("soa.appname");
    appName = appName.trim();
    registryUrl = prop.getProperty("soa.registry");
    registryUrl = registryUrl.trim();
  }

  public static void init(Properties prop) throws IOException, URISyntaxException {

    String strClient = prop.getProperty("soa.client");
    if (strClient == null) {
      isClient = false;
      return;
    }
    if (strClient != null) {
      strClient = strClient.trim();
    }
    isClient = ParamUtil.parseBoolean(strClient, "soa.client must be boolean");
    if (!isClient) {
      return;
    } else {
      readBasicConfig(prop);
    }

    String strService = prop.getProperty("soa.service");
    if (strService == null) {
      isService = false;
      return;
    }
    if (strService != null) {
      strService = strService.trim();
    }
    isService = ParamUtil.parseBoolean(strService, "soa.service must be boolean");
    if (!isService) {
      return;
    } else {
      readServiceConfig(prop);
    }
  }

  /**
   * 通过实体类获得远程接口
   * @param cls
   * @return
     */
  static Class getServiceInteface(Class cls) {
    // com.jumore.dove.demo.service.SOADemoUserService$$EnhancerByCGLIB$$46bf2215
    String clsName = cls.getName();
    if (clsName.contains("EnhancerByCGLIB")) {
      Class superCls = cls.getSuperclass();
      if (superCls != null) {
        cls = superCls;
      }
    }

    Class[] ifaces = cls.getInterfaces();
    Class iface = ifaces[0];
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

  public static void exportRemoteService(SOARemote remoteService) {
    exportRemoteService(remoteService, SOAConfiguration.DEFAULTVERSION);
  }

  public static void exportRemoteService(SOARemote remoteService, String version) {
    //		Class[] ifaces = remoteService.getClass().getInterfaces();
    //		logger.info("dove:soa:class:"+remoteService.getClass().getName());
    //		Class superCls = remoteService.getClass().getSuperclass();
    //		logger.info("dove:soa:superClass:" + superCls.getName());
    //		superCls = superCls.getSuperclass();
    //		logger.info("dove:soa:superClass:" + superCls);
    //		for(Class iface:ifaces){
    //			logger.info("dove:soa:interface" + iface.getName());
    //		}
    Class iface = getServiceInteface(remoteService.getClass());

    ServiceConfig<SOARemote> service = new ServiceConfig<SOARemote>(); // 此实例很重，封装了与注册中心的连接，请自行缓存，否则可能造成内存和连接泄漏
    service.setApplication(application);
    service.setRegistry(registry); // 多个注册中心可以用setRegistries()
    service.setProtocol(protocol); // 多个协议可以用setProtocols()
    service.setInterface(iface);
    service.setRef(remoteService);
    service.setVersion(version);

    // 暴露及注册服务
    service.export();

  }
}
