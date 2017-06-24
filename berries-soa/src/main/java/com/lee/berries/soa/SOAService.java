package com.lee.berries.soa;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.alibaba.dubbo.config.ReferenceConfig;

public class SOAService {

  @Autowired
  private SOAConfiguration soaConfig;

  public SOAService() {

  }

  private Object lock = new Object();

  static class CachedRemoteValue {
    ReferenceConfig refConfig;

    Object remoteService;
  }

  private Map<String, CachedRemoteValue> cacheMap = Collections
      .synchronizedMap(new HashMap<String, CachedRemoteValue>());

  private <T> T checkCacheAndCreateRemoteService(Class<T> cls, String iKey,String version) {
    if (cacheMap.containsKey(iKey)) {
      Object remoteService = cacheMap.get(iKey).remoteService;
      return (T) remoteService;
    } else {
      // 此实例很重，封装了与注册中心的连接以及与提供者的连接，请自行缓存，否则可能造成内存和连接泄漏
      ReferenceConfig<T> reference = new ReferenceConfig<T>();
      reference.setApplication(soaConfig.getApplication());

      if (!StringUtils.isEmpty(soaConfig.getRegistryUrl()) && soaConfig.getRegistryUrl().startsWith("dubbo://")) { // 不经过注册中心，而是直连
        String url = soaConfig.getRegistryUrl().endsWith("/") ? soaConfig.getRegistryUrl() + cls.getName()
            : soaConfig.getRegistryUrl() + "/" + cls.getName();
        reference.setUrl(url);
      }

      reference.setRegistry(soaConfig.getRegistry());
      reference.setInterface(cls);
      reference.setVersion(version);
      T remoteService = reference.get();

      // 将生成的结果放入缓存
      CachedRemoteValue value = new CachedRemoteValue();
      value.refConfig = reference;
      value.remoteService = remoteService;
      cacheMap.put(iKey, value);
      return remoteService;
    }
  }

  public <T> T getRemoteService(Class<T> cls) {
    return getRemoteService(cls, SOAConfiguration.DEFAULTVERSION);
  }

  public <T> T getRemoteService(Class<T> cls, String version) {
    String iName = cls.getName();
    String iKey = iName + "_" + version;
    if (cacheMap.containsKey(iKey)) {
      Object remoteService = cacheMap.get(iKey).remoteService;
      return (T) remoteService;
    } else {
      synchronized (lock) {
        return checkCacheAndCreateRemoteService(cls, iKey, version);
      }
    }
  }
}
