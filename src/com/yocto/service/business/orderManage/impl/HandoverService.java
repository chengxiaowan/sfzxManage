package com.yocto.service.business.orderManage.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yocto.dao.DaoSupport;
import com.yocto.entity.Page;
import com.yocto.service.business.orderManage.IHandoverService;
import com.yocto.util.PageData;

@Service("handoverService")
public class HandoverService implements IHandoverService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) dao.findForList("HandoverMapper.listPage", page);
	}

	@Override
	public void save(PageData pd) throws Exception {
		dao.save("HandoverMapper.save", pd);
	}

	@Override
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("HandoverMapper.findById", pd);
	}

	@Override
	public void update(PageData pd) throws Exception {
		dao.update("HandoverMapper.update", pd);
	}

	@Override
	public void delete(String[] arrayIds) throws Exception {
		dao.delete("HandoverMapper.delete", arrayIds);
	}

	@Override
	public void updateTask(PageData pd) throws Exception {
		dao.update("HandoverMapper.updateTask", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listAll() throws Exception {
		return (List<PageData>) dao.findForList("HandoverMapper.listAll", null);
	}

	@Override
	public void save1(PageData pd) throws Exception {
		dao.save("HandoverMapper.save1", pd);
	}
}
