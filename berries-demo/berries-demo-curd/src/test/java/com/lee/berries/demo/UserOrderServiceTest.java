package com.lee.berries.demo;

import java.math.BigDecimal;

import org.junit.Test;

import com.lee.berries.demo.po.UserOrder;
import com.lee.berries.demo.service.UserOrderService;

public class UserOrderServiceTest extends BaseTest {

	@Test
	public void testSave() {
		UserOrderService userOrderService = getBean(UserOrderService.class);
		UserOrder userOrder = new UserOrder();
		userOrder.setUserId(1L);
		userOrder.setPrice(BigDecimal.valueOf(100L));
		userOrder.setOrderName("Apple!");
		userOrderService.save(userOrder);
	}
	
	@Test
	public void testUpdate() {
		UserOrderService userOrderService = getBean(UserOrderService.class);
		UserOrder userOrder = new UserOrder();
		userOrder.setId(519533689367236608L);
		userOrder.setOrderName("Orange");
		userOrder.setPrice(BigDecimal.valueOf(101L));
		userOrderService.update(userOrder);
	}
	
	
	@Test
	public void testGet() {
		UserOrderService userOrderService = getBean(UserOrderService.class);
		UserOrder userOrder = userOrderService.get(519533689367236608L);
		System.out.println(userOrder.getOrderName());
	}
	
	@Test
	public void testDelete() {
		UserOrderService userOrderService = getBean(UserOrderService.class);
		Long []id = {1L,1L};
		userOrderService.delete(id);
	}
	
	
}
