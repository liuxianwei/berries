   
/**
 * @discription 在此输入一句话描述此文件的作用
 * @author liuxianwei
 * @created 2016年11月16日 上午11:20:12
 * tags
 * see_to_target
*/

package com.lee.berries.test;

import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

  
/**
 * Title: BaseTest.java
 * Description: 描述
 * @author liuxianwei
 * @created 2016年11月16日 上午11:20:12
 */

public abstract class BaseTest {

	//以下为容器实例声明及初始化、销毁
    private ClassPathXmlApplicationContext context;
    
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 测试初始化
     */
    @Before
    public void before(){
        //加载spring容器
    	logger.info("start unit test");
        context = new ClassPathXmlApplicationContext("/spring/berries-test.xml");
        logger.info("finish spring context!");
        
    }

    @After
    public void after(){
        context.destroy();
    }

    //从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
    @SuppressWarnings("unchecked")
	public <E> E getBean(String name) {
        return (E) context.getBean(name);
    }

    //从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
    public <T> T getBean(Class<T> requiredType) {
        return context.getBean(requiredType);
    }
}
