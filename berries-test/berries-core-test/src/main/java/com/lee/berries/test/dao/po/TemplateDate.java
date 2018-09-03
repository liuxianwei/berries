/**
 * @author lxw 2018-03-26
*/
package com.lee.berries.test.dao.po;

import com.lee.berries.dao.annotation.Entity;
import java.util.Date;

@Entity(tableName = "ad_template_date")
public class TemplateDate {
    /**
     * 
     */
    private Long id;

    /**
     * 应用id
     */
    private Long appId;

    /**
     * 消息类型id
     */
    private Long typeId;

    /**
     * 模板名称
     */
    private String name;

    /**
     * 模板编号
     */
    private String code;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 启用标记
     */
    private Boolean enableFlag;

    /**
     * 删除标记
     */
    private Boolean deleteFlag;

    /**
     * 模板内容
     */
    private String content;

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
     * 应用id
     * @return app_id 应用id
     */
    public Long getAppId() {
        return appId;
    }

    /**
     * 应用id
     * @param appId 应用id
     */
    public void setAppId(Long appId) {
        this.appId = appId;
    }

    /**
     * 消息类型id
     * @return type_id 消息类型id
     */
    public Long getTypeId() {
        return typeId;
    }

    /**
     * 消息类型id
     * @param typeId 消息类型id
     */
    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    /**
     * 模板名称
     * @return name 模板名称
     */
    public String getName() {
        return name;
    }

    /**
     * 模板名称
     * @param name 模板名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 模板编号
     * @return code 模板编号
     */
    public String getCode() {
        return code;
    }

    /**
     * 模板编号
     * @param code 模板编号
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * 创建时间
     * @return create_time 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    /**
     * 模板内容
     * @return content 模板内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 模板内容
     * @param content 模板内容
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}