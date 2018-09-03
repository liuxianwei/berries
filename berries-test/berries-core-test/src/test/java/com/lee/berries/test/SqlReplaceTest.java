package com.lee.berries.test;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import com.lee.berries.test.dao.po.Template;

public class SqlReplaceTest extends BaseTest{
	
	@Test
	public void testHashcode() {
		StringBuilder sBuilder = new StringBuilder(15000000);
		StringBuffer stringBuffer = new StringBuffer(15000000);
		Long start = System.currentTimeMillis();
		for(int i = 0; i < 1000000; i++) {
			sBuilder.append("assss1111111111");
		}
		System.out.println("cost:" + (System.currentTimeMillis()-start));
		
		
		start = System.currentTimeMillis();
		for(int i = 0; i < 1000000; i++) {
			stringBuffer.append("assss1111111111");
		}
		System.out.println("cost:" + (System.currentTimeMillis()-start));
		/*
		String aString = "";
		start = System.currentTimeMillis();
		for(int i = 0; i < 100000; i++) {
			aString += "assss";
		}
		System.out.println("cost:" + (System.currentTimeMillis()-start));*/
	}
	
	@Test
	public void test() throws Exception{
		Template template = new Template();
		template.setAppId(1L);
		long start = System.currentTimeMillis();
		for(int i = 0; i < 10000000; i++) {
			Field field = template.getClass().getDeclaredField("appId");
			field.setAccessible(true);
			field.get(template);
		}
		System.out.println("cost:" + (System.currentTimeMillis() - start));
		
		start = System.currentTimeMillis();
		Field field = template.getClass().getDeclaredField("appId");
		field.setAccessible(true);
		Map<String, Field> map = new HashMap<>();
		map.put(field.getName(), field);
		for(int i = 0; i < 10000000; i++) {
			map.get(field.getName()).get(template);
		}
		System.out.println("cost:" + (System.currentTimeMillis() - start));
	}

	@Test
	public void testSelect() {
		String SQL = "select ranking.* from user_ranking ranking where   ranking.date_value <= ?, a.sds= ?, a.sddd like ? order by  ranking.ranking_value asc limit ?,?";
		long start = System.currentTimeMillis();
		for(int i = 0; i < 1000000; i++) {
			Pattern p = Pattern.compile("[\\s|,]([0-1a-zA-Z_-|\\.]+?)\\s{0,}(=|<=|>=|<>|like|>)\\s{0,}([\\?])\\s?");
			Matcher  matcher = p.matcher(SQL);
			while(matcher.find()) {  
				//System.out.println(matcher.group() + "==");
			}
		}
		System.out.println("cost:" + (System.currentTimeMillis() - start));
	}
	
	@Test
	public void testTableName() {
		String SQL = "select " + 
				"		DISTINCT temp.* " + 
				"	from " + 
				"		(select " + 
				"			app.*" + 
				"		from " + 
				"			sso_app app" + 
				"		left join " + 
				"			sso_app_admin app_admin" + 
				"		on " + 
				"			app.id=app_admin.app_id" + 
				"		where " + 
				"			app_admin.user_id=#{userId, jdbcType=BIGINT}" + 
				"			and app.enable_flag=1 " + 
				"			and app.delete_flag=0" + 
				"		union" + 
				"		select " + 
				"			app.*" + 
				"		from " + 
				"			sso_user_role user_role" + 
				"		left join " + 
				"			sso_role role" + 
				"		on " + 
				"			role.id=user_role.role_id" + 
				"		left join " + 
				"			sso_app app" + 
				"		on " + 
				"			app.id=user_role.app_id" + 
				"		where " + 
				"			user_role.user_id=?" + 
				"			and role.enable_flag<>1 " + 
				"			and role.delete_flag=0" + 
				"			and app.enable_flag=1 " + 
				"			and app.delete_flag=0" + 
				"			and user_role.role_id!=? ) temp";
		
		Pattern p = Pattern.compile("[from|join]\\s+([a-zA-Z0-9_-]+)\\s+([a-zA-Z0-9_-]+)\\s+");
		Matcher  matcher = p.matcher(SQL);
		 while(matcher.find()) {  
			 System.out.println(matcher.group(1));
			 System.out.println(matcher.group(2));
		 }
	}
	
	
	
	@Test
	public void testInsert() {
		String rexp = "insert\\s+into\\s+(.+)\\s+?\\((.+)\\)\\s+select(.+)from";
		String sql = "INSERT INTO user_ranking " + 
				"		(user_id,nick_name,sex,head_img,score,ranking_Value,date_value) " + 
				"		SELECT " + 
				"		t1.user_id,t2.nick_name,t2.sex,t2.head_img,t1.score,(@rowNO := " + 
				"		@rowNo+1),? from (SELECT @rowNO :=0) money,(select\r\n" + 
				"		user_id,sum(score) as score from user_score_details where changed_Time\r\n" + 
				"		  >  \r\n" + 
				"		? and changed_Time\r\n" + 
				"		  <  \r\n" + 
				"		? GROUP BY user_id ORDER BY score DESC) t1 LEFT\r\n" + 
				"		JOIN\r\n" + 
				"		user_score t2 on t1.user_id = t2.user_id\r\n" + 
				"  " + 
				"";
		Pattern p = Pattern.compile(rexp, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
		Matcher  matcher = p.matcher(sql);
		 while(matcher.find()) {  
			 System.out.println(matcher.group(0) + "==");
			 System.out.println(matcher.group(1) + "==");
			 System.out.println(matcher.group(2) + "==");
			 System.out.println(matcher.group(3) + "==");
		 }
	}
	
	@Test
	public void testUpdate() {
		String rexp = "[\\s|,]([0-1a-zA-Z_-|\\.]+?)[=|<=|>=|<>|like|>]([\\?])\\s?";
		String sql = "update `cx_score` set a.app_id=?, app_name=? where id=? and app_id=?";
		Pattern p = Pattern.compile(rexp);
		Matcher  matcher = p.matcher(sql);
		 while(matcher.find()) {  
			 System.out.println(matcher.group());
		 }
	}
	
	@Test
	public void testRange() {
		short a1 = 1;
		byte b2 = 2;
		int b = b2;
		long a = Math.floorMod(12433434L, 10);
		System.out.println(a);
	}
	
	@Test
	public void testFactory() {
		
		
		ExecutorService executor = Executors.newFixedThreadPool(100);
		final long start = System.currentTimeMillis();
		Map<Long, Long> map = new ConcurrentHashMap<>(100000);
		for(int i = 0; i < 100000; i++) {
			Runnable runnable = new Runnable() {
				
				@Override
				public void run() {
					Long id = IdFactory.getInstance().getIdWorker().nextId();
					map.put(id, id);
					if(map.containsKey(id)) {
						System.out.println(id);
					}
				}
			};
			executor.submit(runnable);
		}
		while(executor.isTerminated()) {}
		System.out.println(System.currentTimeMillis() - start);
		System.out.println(map.size());
	}
	
}
