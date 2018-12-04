/**
 * @author apple 2018-12-04
 */
package com.lee.berries.demo.po;

import com.lee.berries.dao.annotation.Column;
import com.lee.berries.dao.annotation.Entity;
import com.lee.berries.dao.annotation.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 */
@Entity(tableName = "user_order")
public class UserOrder {
    /**
     * 
     */
    private Long id;

    /**
     * 
     */
    private Long userId;

    /**
     * 
     */
    private String orderName;

    /**
     * 
     */
    private BigDecimal price;

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
     * @return user_id 
     */
    @Column(name = "user_id")
    public Long getUserId() {
        return userId;
    }

    /**
     * 
     * @param userId 
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 
     * @return order_name 
     */
    @Column(name = "order_name")
    public String getOrderName() {
        return orderName;
    }

    /**
     * 
     * @param orderName 
     */
    public void setOrderName(String orderName) {
        this.orderName = orderName == null ? null : orderName.trim();
    }

    /**
     * 
     * @return price 
     */
    @Column(name = "price")
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 
     * @param price 
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
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