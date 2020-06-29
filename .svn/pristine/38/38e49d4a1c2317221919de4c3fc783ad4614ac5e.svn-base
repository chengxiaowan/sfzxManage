package com.yocto.service.business.expressManage.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yocto.dao.DaoSupport;
import com.yocto.entity.Page;
import com.yocto.service.business.expressManage.IExpressService;
import com.yocto.util.PageData;

@Service("expressService")
public class ExpressService implements IExpressService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) dao.findForList("ExpressMapper.listPage", page);
	}

	@Override
	public void save(PageData pd) throws Exception {
		dao.save("ExpressMapper.save", pd);
	}

	@Override
	public void saveAttach(List<Map<String, Object>> list) throws Exception {
		dao.save("ExpressMapper.saveAttach", list);
	}

	@Override
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("ExpressMapper.findById", pd);
	}

	@Override
	public void update(PageData pd) throws Exception {
		dao.update("ExpressMapper.update", pd);
	}

	@Override
	public void saveAttach1(PageData map) throws Exception {
		dao.save("ExpressMapper.saveAttach1", map);
	}

	@Override
	public void deleteAttach(List<String> list2) throws Exception {
		dao.delete("ExpressMapper.deleteAttach", list2);
	}

	@Override
	public void delete(String[] arrayIds) throws Exception {
		dao.delete("ExpressMapper.delete", arrayIds);
	}

	@Override
	public void updateLinks(PageData pd) throws Exception {
		dao.update("ExpressMapper.updateLinks", pd);
	}

	@Override
	public void delteLinks(PageData pd1) throws Exception {
		dao.delete("ExpressMapper.delteLinks", pd1);
	}

	@Override
	public void saveWarnInfo(PageData pd) throws Exception {
		dao.save("ExpressMapper.saveWarnInfo", pd);
	}

	@Override
	public void deleteWarnInfo(PageData pd) throws Exception {
		dao.delete("ExpressMapper.deleteWarnInfo", pd);
	}

	@Override
	public int update1(PageData pd) throws Exception {
		return dao.update("ExpressMapper.update1", pd);
	}

	@Override
	public void doWarnInfo(PageData pd) throws Exception {
		dao.update("ExpressMapper.doWarnInfo", pd);
	}

	@Override
	public PageData findServiceId() throws Exception {
		return (PageData) dao.findForObject("ExpressMapper.findServiceId", null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listAll(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("ExpressMapper.listAll", pd);
	}

}
