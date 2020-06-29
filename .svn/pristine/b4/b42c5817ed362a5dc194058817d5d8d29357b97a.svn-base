package com.yocto.service.business.linkmanManage.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yocto.dao.DaoSupport;
import com.yocto.entity.Page;
import com.yocto.service.business.linkmanManage.ILinkmanService;
import com.yocto.util.PageData;

@Service("linkmanService")
public class LinkmanService implements ILinkmanService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) dao.findForList("LinkmanMapper.listPage", page);
	}

	@Override
	public void save(PageData pd) throws Exception {
		dao.save("LinkmanMapper.save", pd);
	}

	@Override
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("LinkmanMapper.findById", pd);
	}

	@Override
	public void update(PageData pd) throws Exception {
		dao.update("LinkmanMapper.update", pd);
	}

	@Override
	public void delete(String[] arrayIds) throws Exception {
		dao.delete("LinkmanMapper.delete", arrayIds);
	}

	@Override
	public Object findByMobilePhone(PageData pd) throws Exception {
		return dao.findForObject("LinkmanMapper.findByMobilePhone", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listAll(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("LinkmanMapper.listAll", pd);
	}

	@Override
	public void saveOrderLinkman(PageData pd) throws Exception {
		dao.save("LinkmanMapper.saveOrderLinkman", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listAllLinkmanName(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("LinkmanMapper.listAllLinkmanName", pd);
	}

	@Override
	public void update1(PageData pd) throws Exception {
		dao.update("LinkmanMapper.update1", pd);
	}

	@Override
	public PageData findById1(PageData pd) throws Exception {
		return (PageData) dao.findForObject("LinkmanMapper.findById1", pd);
	}
}
