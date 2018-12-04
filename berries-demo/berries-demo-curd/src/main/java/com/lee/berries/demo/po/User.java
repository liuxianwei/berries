/**
 * @author apple 2018-12-04
 */
package com.lee.berries.demo.po;

import com.lee.berries.dao.annotation.Column;
import com.lee.berries.dao.annotation.Entity;
import com.lee.berries.dao.annotation.Id;
import java.util.Date;

/**
 * 
 */
@Entity(tableName = "user")
public class User {
    /**
     * 
     */
    private Long id;

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private Date createTime;

    /**
     * 
     * @return id 
     */
    @Id(name = "id")
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
     * 
     * @return name 
     */
    @Column(name = "name")
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name 
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 
     * @return create_time 
     */
    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * @param createTime 
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}