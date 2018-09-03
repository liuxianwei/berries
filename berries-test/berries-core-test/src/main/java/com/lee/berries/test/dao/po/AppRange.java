/**
 * @author lxw 2018-03-26
*/
package com.lee.berries.test.dao.po;

import com.lee.berries.dao.annotation.Entity;

@Entity(tableName = "ad_app_range")
public class AppRange {
    /**
     * 
     */
    private Long id;

    /**
     * appId
     */
    private Long appId;

    /**
     * App名称
     */
    private String appName;

    /**
     * 私钥
     */
    private String privateKey;

    /**
     * 公钥
     */
    private String publlicKey;

    /**
     * 启用标记
     */
    private Boolean enableFlag;

    /**
     * 删除标记
     */
    private Boolean deleteFlag;

    /**
     * 
     * @return id 
     */
    public Long getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * appId
     * @return app_id appId
     */
    public Long getAppId() {
        return appId;
    }

    /**
     * appId
     * @param appId appId
     */
    public void setAppId(Long appId) {
        this.appId = appId;
    }

    /**
     * App名称
     * @return app_name App名称
     */
    public String getAppName() {
        return appName;
    }

    /**
     * App名称
     * @param appName App名称
     */
    public void setAppName(String appName) {
        this.appName = appName == null ? null : appName.trim();
    }

    /**
     * 私钥
     * @return private_key 私钥
     */
    public String getPrivateKey() {
        return privateKey;
    }

    /**
     * 私钥
     * @param privateKey 私钥
     */
    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey == null ? null : privateKey.trim();
    }

    /**
     * 公钥
     * @return publlic_key 公钥
     */
    public String getPubllicKey() {
        return publlicKey;
    }

    /**
     * 公钥
     * @param publlicKey 公钥
     */
    public void setPubllicKey(String publlicKey) {
        this.publlicKey = publlicKey == null ? null : publlicKey.trim();
    }

    /**
     * 启用标记
     * @return enable_flag 启用标记
     */
    public Boolean getEnableFlag() {
        return enableFlag;
    }

    /**
     * 启用标记
     * @param enableFlag 启用标记
     */
    public void setEnableFlag(Boolean enableFlag) {
        this.enableFlag = enableFlag;
    }

    /**
     * 删除标记
     * @return delete_flag 删除标记
     */
    public Boolean getDeleteFlag() {
        return deleteFlag;
    }

    /**
     * 删除标记
     * @param deleteFlag 删除标记
     */
    public void setDeleteFlag(Boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
}