package com.afeibaili.test;

import com.afeibaili.ioc_4.JavaBean;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringIoCTest {
    @Test
    public void createIoC() {
        //第一种方式，容器创建配置集成一起
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-03.xml");

        //第二种方式
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext();
        //选择配置文件
        classPathXmlApplicationContext.setConfigLocation("spring-03.xml");
        //刷新
        classPathXmlApplicationContext.refresh();
    }

    @Test
    public void testGetBean() {
        ClassPathXmlApplicationContext app = new ClassPathXmlApplicationContext();
        app.setConfigLocation("spring-03.xml");
        app.refresh();
        app.getBean("springIoC");
    }

    @Test
    public void initAndDestroy() {
        ClassPathXmlApplicationContext app = new ClassPathXmlApplicationContext("spring-03.xml");
        //初始化
        app.getBean("springIoC");
        //销毁
        app.close();
    }

    @Test
    public void javaBeanFactoryTest() {
        ClassPathXmlApplicationContext app = new ClassPathXmlApplicationContext("spring-04.xml");
        JavaBean javaBeanFactory = app.getBean("javaBeanFactory", JavaBean.class);
        System.out.println(javaBeanFactory);
    }
}
