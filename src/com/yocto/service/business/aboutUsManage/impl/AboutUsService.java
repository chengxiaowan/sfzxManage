package com.yocto.service.business.aboutUsManage.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yocto.dao.DaoSupport;
import com.yocto.service.business.aboutUsManage.IAboutUsService;
import com.yocto.util.PageData;

/**
 * 关于我们信息管理
 * 
 * @author xl
 *
 */
@Service(value = "aboutUsService")
public class AboutUsService implements IAboutUsService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 * 插入或者更新
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void updateOrSave(PageData pd) throws Exception {
		dao.save("AboutUsMapper.updateOrSave", pd);
	}

	/**
	 * 根据id查询
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@Override
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("AboutUsMapper.findById", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getLogs(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("AboutUsMapper.getLogs", pd);
	}
}
