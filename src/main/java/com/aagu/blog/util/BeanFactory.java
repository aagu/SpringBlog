package com.aagu.blog.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

public class BeanFactory implements ApplicationContextAware {
    private static ApplicationContext applicationContext;
    private static Logger logger = LoggerFactory.getLogger(BeanFactory.class);
    private static DefaultListableBeanFactory defaultListableBeanFactory;

    public BeanFactory() {}

    public static <T> T getBean(Class<T> cls) {
        if (applicationContext != null) {
            return applicationContext.getBean(cls);
        } else {
            logger.warn("Spring容器为空，无法获取beanClass：{}", cls);
            return null;
        }
    }

    public static Object getBean(String name) {
        if (applicationContext != null) {
            return applicationContext.getBean(name);
        } else {
            logger.warn("Spring容器为空，无法获取beanClass：{}", name);
            return null;
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext mApplicationContext) throws BeansException {
        applicationContext = mApplicationContext;
        // 提供了对bean定义的分析和修改的便利方法，同时也提供了对单例的预实例化
        ConfigurableListableBeanFactory beanFactory = null;
        if (applicationContext instanceof GenericApplicationContext) {
            beanFactory = ((GenericApplicationContext) applicationContext).getBeanFactory();
        } else if (applicationContext instanceof FileSystemXmlApplicationContext) { //从系统文件路径加载
            beanFactory = ((FileSystemXmlApplicationContext) applicationContext).getBeanFactory();
        } else if (applicationContext instanceof XmlWebApplicationContext) { //从 web工厂文件路径加载
            beanFactory = ((XmlWebApplicationContext) applicationContext).getBeanFactory();
        } else {
            logger.warn("未识别的applicationContext");
        }

        if (beanFactory instanceof DefaultListableBeanFactory) {
            defaultListableBeanFactory = (DefaultListableBeanFactory) beanFactory;
        }
    }

    public static void registerBeanDefinition(String beanId, GenericBeanDefinition beanDefinition) {
        defaultListableBeanFactory.registerBeanDefinition(beanId, beanDefinition);
    }
}
