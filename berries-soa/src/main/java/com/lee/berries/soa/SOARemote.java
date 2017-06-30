package com.lee.berries.soa;

/**
 * Dubbo服务接口，需要进行Dubbo注册的服务需要实现这个接口
 * @author Liuxianwei
 *
 */
public interface SOARemote {
  
  public String getVersion();
}
