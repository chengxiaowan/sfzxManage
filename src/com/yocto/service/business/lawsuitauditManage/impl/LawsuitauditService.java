package com.yocto.service.business.lawsuitauditManage.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yocto.dao.DaoSupport;
import com.yocto.entity.Page;
import com.yocto.service.business.lawsuitauditManage.ILawsuitauditService;
import com.yocto.util.PageData;

@Service("lawsuitauditService")
public class LawsuitauditService implements ILawsuitauditService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) dao.findForList("LawsuitauditMapper.listPage", page);
	}

	@Override
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("LawsuitauditMapper.findById", pd);
	}

	@Override
	public void update(PageData pd) throws Exception {
		dao.update("LawsuitauditMapper.update", pd);
	}

	@Override
	public void saveTask(PageData pd) throws Exception {
		dao.save("LawsuitauditMapper.saveTask", pd);
	}

	@Override
	public void updateOrder(PageData pd) throws Exception {
		dao.update("LawsuitauditMapper.updateOrder", pd);
	}

}
