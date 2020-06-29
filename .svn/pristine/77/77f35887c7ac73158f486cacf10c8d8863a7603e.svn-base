package com.yocto.util.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 通过spring上下文 手动获取applicationContext.xml配置文件中注入的bean
 * 
 * @author wxh
 * @version [版本号, 2014-2-10]
 */
public class SpringContextUtils implements ApplicationContextAware {

	public static ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		SpringContextUtils.context = context;
	}

	public static <T> T getBean(String beanId, Class<T> clazz) {
		return context.getBean(beanId, clazz);
	}

	public static ApplicationContext getContext() {
		return context;
	}

}