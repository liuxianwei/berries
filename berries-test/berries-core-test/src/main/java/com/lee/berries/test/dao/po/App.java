/**
 * @author lxw 2018-03-26
*/
package com.lee.berries.test.dao.po;

import com.lee.berries.dao.annotation.Entity;

@Entity(tableName = "ad_app")
public class App {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((appId == null) ? 0 : appId.hashCode());
		result = prime * result + ((appName == null) ? 0 : appName.hashCode());
		result = prime * result + ((deleteFlag == null) ? 0 : deleteFlag.hashCode());
		result = prime * result + ((enableFlag == null) ? 0 : enableFlag.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((privateKey == null) ? 0 : privateKey.hashCode());
		result = prime * result + ((publlicKey == null) ? 0 : publlicKey.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		App other = (App) obj;
		if (appId == null) {
			if (other.appId != null)
				return false;
		} else if (!appId.equals(other.appId))
			return false;
		if (appName == null) {
			if (other.appName != null)
				return false;
		} else if (!appName.equals(other.appName))
			return false;
		if (deleteFlag == null) {
			if (other.deleteFlag != null)
				return false;
		} else if (!deleteFlag.equals(other.deleteFlag))
			return false;
		if (enableFlag == null) {
			if (other.enableFlag != null)
				return false;
		} else if (!enableFlag.equals(other.enableFlag))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (privateKey == null) {
			if (other.privateKey != null)
				return false;
		} else if (!privateKey.equals(other.privateKey))
			return false;
		if (publlicKey == null) {
			if (other.publlicKey != null)
				return false;
		} else if (!publlicKey.equals(other.publlicKey))
			return false;
		return true;
	}
}