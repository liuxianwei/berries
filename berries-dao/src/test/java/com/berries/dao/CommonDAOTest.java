package com.berries.dao;

import org.junit.Test;

import com.berries.test.BaseTest;
import com.lee.berries.dao.CommonDAO;
import com.lee.berries.dao.Page;
import com.lee.berries.dao.po.UserDetails;
import com.lee.berries.dao.query.UserQuery;

public class CommonDAOTest extends BaseTest {

	@Test
	public void testlistByExample(){
		CommonDAO testDAO = getBean(CommonDAO.class);
		testDAO.listByExampleRow(new UserDetails());
		testDAO.listByExample(new UserDetails());
		
		Page<UserDetails> page = testDAO.findPage(new UserDetails() , null);
		System.out.println(page);
	}
	
	@Test
	public void testSave(){
		CommonDAO testDAO = getBean(CommonDAO.class);
		UserDetails user = new UserDetails();
		user.setUserName("213123");
		testDAO.save(user);
	}
	
	@Test
	public void testUpdateWithOptimisticLock(){
		CommonDAO testDAO = getBean(CommonDAO.class);
		UserDetails user = testDAO.get(UserDetails.class, 5L);
		UserDetails lock = new UserDetails();
		lock.setVersion(user.getVersion());
		user.setUserName("23424");
		user.setVersion(user.getVersion()+1);
		int count = testDAO.updateWithOptimisticLock(user, lock);
		user.setUserName("第二次名称");
		int count2 = testDAO.updateWithOptimisticLock(user, lock);
		System.out.println(count + ":" + count2);
	}
	
	@Test
	public void testDelete(){
		CommonDAO testDAO = getBean(CommonDAO.class);
		UserDetails user = new UserDetails();
		user.setId(3L);
		testDAO.remove(user);
	}
	
	@Test
	public void testQuery(){
		CommonDAO testDAO = getBean(CommonDAO.class);
		UserQuery query = new UserQuery();
		query.setOrder_u_id("desc");
		testDAO.query(query, new Page<UserDetails>());
	}
}
