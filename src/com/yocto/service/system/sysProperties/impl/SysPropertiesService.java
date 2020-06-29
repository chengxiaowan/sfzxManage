package com.yocto.service.system.sysProperties.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yocto.dao.DaoSupport;
import com.yocto.service.system.sysProperties.ISysPropertiesService;

/**
 * 系统属性信息接口类
 * 
 * @author wxh
 *
 */
@Service("sysPropertiesService")
public class SysPropertiesService implements ISysPropertiesService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 * 通过key获取属性值
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public String getSysPropertiesByKey(String key) throws Exception {
		return (String) dao.findForObject("SysPropertiesMapper.getValueByKey", key);
	}
}
