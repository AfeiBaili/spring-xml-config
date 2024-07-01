# 基于XML的Spring配置

## IoC容器

springIoC容器,负责实例化,配置和组装bean(组件)的核心容器,容器通过读取配置文件来实例化,配置和组装组件的指令

## IoC (Inversion of Control)控制反转

IoC是针对对象的创建和调用的控制而言的,当需要一个对象时,不是你来创建而是用IoC容器帮你管理和创建,即控制权由应用程序转移到IoC容器中,也就是反转了控制权

## DI(Dependency Injection)依赖注入

DI是在组件之间传递依赖关系过程中,将依赖关系在容器内部进行处理,
在spring中DI是由XML或注解的方式实现的,它提供了三种形式的依赖注入:
构造函数注入,setter方法注入,接口注入

## IoC配置

这是一个spring的XML配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

</beans>
```

* 声明一个简单的组件(bean)  
  id:声明一个唯一标识方便使用  
  class:类的位置

```xml

<bean id="happyComponent" class="com.afeibaili.ioc_1.HappyComponent"/>

```

* 静态工厂类  
  factory-method:创建实例的工厂方法

```java
package com.afeibaili.ioc_1;

public class ClientService {
    public static ClientService clientService = new ClientService();

    private ClientService() {
    }

    public static ClientService createClientService() {
        return clientService;
    }
}
```

```xml

<bean id="clientService" class="com.afeibaili.ioc_1.ClientService" factory-method="createClientService"/>

```

* 非静态工厂类  
  静态工厂只需要一个配置而非静态需要两个配置  
  factory-bean:非静态工厂类标识

```java
package com.afeibaili.ioc_1;

public class DefaultServiceLocator {
    private static ClientServiceImpl clientService = new ClientServiceImpl();

    public ClientServiceImpl createClientService() {
        return clientService;
    }
}
```

```xml

<beans>
    <bean id="defaultServiceLocator" class="com.afeibaili.ioc_1.DefaultServiceLocator"/>
    <bean id="clientService2" factory-bean="defaultServiceLocator" factory-method="createClientService"/>
</beans>

```

## DI配置

* 构造参数注入  
  通过`<constructor-arg>`标签来指定参数名字和内容,里面包含两个属性一个ref一个value  
  ref:引用其他的组件类  
  value:基本数据类型

```java
package com.afeibaili.ioc_2;

public class UserService {

    private final UserDao userDao;
    private String name;
    private int age;

    //单个构造参数
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    //多个构造参数
    public UserService(UserDao userDao, String name, int age) {
        this.userDao = userDao;
        this.name = name;
        this.age = age;
    }
}
```

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!--单个构造参数注入-->
    <bean id="userDao" class="com.afeibaili.ioc_2.UserDao"/>
    <!--将userDao传入构造参数中-->
    <bean id="userService" class="com.afeibaili.ioc_2.UserService">
        <constructor-arg ref="userDao"/>
    </bean>
    <!--多个构造参数注入-->
    <!--1.有序多个构造参数-->
    <bean id="userService2" class="com.afeibaili.ioc_2.UserService">
        <constructor-arg ref="userDao"/>
        <constructor-arg value="张三"/>
        <constructor-arg value="18"/>
    </bean>
    <!--2.通过参数名匹配参数，循序可以不一样-->
    <bean id="userService3" class="com.afeibaili.ioc_2.UserService">
        <constructor-arg name="userDao" ref="userDao"/>
        <constructor-arg name="age" value="18"/>
        <constructor-arg name="name" value="张三"/>
    </bean>
    <!--2.通过参数下角标匹配参数，循序可以不一样-->
    <bean id="userService4" class="com.afeibaili.ioc_2.UserService">
        <constructor-arg index="0" ref="userDao"/>
        <constructor-arg index="2" value="18"/>
        <constructor-arg index="1" value="张三"/>
    </bean>
</beans>
```

* setter方法注入  
  通过`<property>`标签来指定参数名字和内容  
  name:去掉set首字母小写的方法名

```java
package com.afeibaili.ioc_2;

public class SimpleMovieLister {
    private MovieFinder movieFinder;
    private String name;

    public void setMovieFinder(MovieFinder movieFinder) {
        this.movieFinder = movieFinder;
    }

    public void setName(String name) {
        this.name = name;
    }
}
```

```xml

<beans>
    <bean id="finder" class="com.afeibaili.ioc_2.MovieFinder"/>
    <bean id="simpleMovieLister" class="com.afeibaili.ioc_2.SimpleMovieLister">
        <property name="movieFinder" ref="finder"/>
        <property name="name" value="我的世界"/>
    </bean>
</beans>

```

## 容器的创建

通过ClassPathXmlApplicationContext来创建一个XML配置容器

```java
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
```  

## 获取配置中的组件

获取组件通过"容器.getBean('组件ID')"每个组件默认都是单例模式可以通过scope更改为多例模式

```java

@Test
public void test01() {
    ClassPathXmlApplicationContext app = new ClassPathXmlApplicationContext();
    app.setConfigLocation("spring-03.xml");
    app.refresh();
    app.getBean("springIoC");
}
```

## 周期方法

到了对应的时期会主动调用,类似Vue的钩子有init方法和destroy  
init:(单例模式)初始化时调用  
destroy:销毁时调用

```java

@Test
public void initAndDestroy() {
    ClassPathXmlApplicationContext app = new ClassPathXmlApplicationContext("spring-03.xml");
    //初始化
    app.getBean("springIoC");
    //销毁
    app.close();
}
```

## 作用域(scope)

在容器中组件默认是单例模式(singleton)可以通过scope改成多例模式(prototype)

```xml

<bean id="happyComponent02" class="com.afeibaili.ioc_3.HappyComponent" scope="prototype"/>

```

## FactoryBean

spring通过检查你是否实现FactoryBean接口,来判断你是否是标准工厂,通过接口中的方法来返回指定实例  
它是用来干什么的?,它是用来创建复杂对象的实例,例如一个对象要许多步骤,就可以把逻辑写在工厂里面,使用的时候直接获取

```xml

<bean id="javaBeanFactory" class="com.afeibaili.ioc_4.JavaBeanFactory"/>

```

```java
//类
public class JavaBeanFactory implements FactoryBean<JavaBean> {
    @Override
    public JavaBean getObject() throws Exception {
        return new JavaBean();
    }

    @Override
    public Class<?> getObjectType() {
        return JavaBean.class;
    }
}
```
```java
//运行
@Test
public void javaBeanFactoryTest() {
  ClassPathXmlApplicationContext app = new ClassPathXmlApplicationContext("spring-04.xml");
  //如果需要指定标准工厂类就需要在getBean时前面加上&
  JavaBean javaBeanFactory = app.getBean("javaBeanFactory", JavaBean.class);
  System.out.println(javaBeanFactory);
}
```

