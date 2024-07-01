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
