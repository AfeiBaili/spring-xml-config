package com.afeibaili.ioc_4;

import org.springframework.beans.factory.FactoryBean;

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
