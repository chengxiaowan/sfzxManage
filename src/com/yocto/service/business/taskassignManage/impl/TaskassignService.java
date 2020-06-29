package com.yocto.service.business.taskassignManage.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yocto.dao.DaoSupport;
import com.yocto.entity.Page;
import com.yocto.service.business.taskassignManage.ITaskassignService;
import com.yocto.util.PageData;

@Service("taskassignService")
public class TaskassignService implements ITaskassignService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listVisit(Page page) throws Exception {
		return (List<PageData>) dao.findForList("TaskassignMapper.visitlistPage", page);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listOrderReport(Page page) throws Exception {
		return (List<PageData>) dao.findForList("TaskassignMapper.orderReportlistPage", page);
	}

	// 新增外访客户
	@Override
	public void saveVisit(PageData pd) throws Exception {
		dao.save("TaskassignMapper.saveVisit", pd);
	}

	@Override
	public void saveTasks(PageData pd) throws Exception {
		dao.save("TaskassignMapper.saveTasks", pd);
	}

	@Override
	public void saveOrderReport(PageData pd) throws Exception {
		dao.save("TaskassignMapper.saveOrderReport", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listdebtRoport(Page page) throws Exception {
		return (List<PageData>) dao.findForList("TaskassignMapper.debtRoportlistPage", page);
	}

	@Override
	public void saveDebt(PageData pd) throws Exception {
		dao.save("TaskassignMapper.saveDebt", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listLawers(Page page) throws Exception {
		return (List<PageData>) dao.findForList("TaskassignMapper.lawerslistPage", page);
	}

	@Override
	public void saveLawyer(PageData pd) throws Exception {
		dao.save("TaskassignMapper.saveLawyer", pd);
	}

	@Override
	public void update(PageData pd) throws Exception {
		dao.save("TaskassignMapper.update", pd);
	}

	@Override
	public void updateTasks(PageData pd) throws Exception {
		dao.update("TaskassignMapper.updateTasks", pd);
	}

	@Override
	public PageData findTasks(PageData pd) throws Exception {
		return (PageData) dao.findForObject("TaskassignMapper.findTasks", pd);
	}

	@Override
	public void deleteTasks(PageData pd) throws Exception {
		dao.delete("TaskassignMapper.deleteTasks", pd);
	}

}
