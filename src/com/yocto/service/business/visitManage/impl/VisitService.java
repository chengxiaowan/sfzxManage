package com.yocto.service.business.visitManage.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yocto.dao.DaoSupport;
import com.yocto.entity.Page;
import com.yocto.service.business.visitManage.IVisitService;
import com.yocto.util.PageData;

@Service("visitService")
public class VisitService implements IVisitService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) dao.findForList("VisitMapper.listPage", page);
	}

	@Override
	public void save(PageData pd) throws Exception {
		dao.save("VisitMapper.save", pd);
	}

	@Override
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("VisitMapper.findById", pd);
	}

	@Override
	public void update(PageData pd) throws Exception {
		dao.update("VisitMapper.update", pd);
	}

	@Override
	public void saveWarnInfo(PageData pd) throws Exception {
		dao.save("VisitMapper.saveWarnInfo", pd);
	}

	@Override
	public void delete(String[] arrayIds) throws Exception {
		dao.delete("VisitMapper.delete", arrayIds);
	}

	@Override
	public void updateWranInfo(PageData pd) throws Exception {
		dao.update("VisitMapper.updateWranInfo", pd);
	}

	@Override
	public void updateWranInfo1(PageData pd) throws Exception {
		dao.update("VisitMapper.updateWranInfo1", pd);
	}
}
