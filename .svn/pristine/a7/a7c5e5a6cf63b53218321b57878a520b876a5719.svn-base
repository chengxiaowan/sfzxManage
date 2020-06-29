package com.yocto.service.business.workBenchManage.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yocto.dao.DaoSupport;
import com.yocto.entity.Page;
import com.yocto.service.business.workBenchManage.IWorkBenchService;
import com.yocto.util.PageData;

/**
 * 工作台信息服务类
 * 
 * @author 修改时间：2015.11.2
 */
@Service("workBenchService")
public class WorkBenchService implements IWorkBenchService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 * 我的任务列表(接口用)
	 * 
	 * @param PageData
	 * @return
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> taskList(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("WorkBenchMapper.taskList", pd);
	}

	/**
	 * 我的客户列表(接口用)
	 * 
	 * @param PageData
	 * @return
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> customerList(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("WorkBenchMapper.customerList", pd);
	}

	/**
	 * 我的联系人列表(接口用)
	 * 
	 * @param PageData
	 * @return
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> linkManList(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("WorkBenchMapper.linkManList", pd);
	}

	/**
	 * 我的案件列表(接口用)
	 * 
	 * @param PageData
	 * @return
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> orderList(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("WorkBenchMapper.orderList", pd);
	}

	/**
	 * 我的任务列表
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> taskListPage(Page page) throws Exception {
		return (List<PageData>) dao.findForList("WorkBenchMapper.tasklistPage", page);
	}

	/**
	 * 我的客户列表
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> customerListPage(Page page) throws Exception {
		return (List<PageData>) dao.findForList("WorkBenchMapper.customerlistPage", page);
	}

	/**
	 * 我的联系人列表
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> linkManListPage(Page page) throws Exception {
		return (List<PageData>) dao.findForList("WorkBenchMapper.linkManlistPage", page);
	}

	/**
	 * 我的案件列表
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> orderListPage(Page page) throws Exception {
		return (List<PageData>) dao.findForList("WorkBenchMapper.orderlistPage", page);
	}

	/**
	 * 根据ID获取任务内容
	 */
	@Override
	public PageData findTaskById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("WorkBenchMapper.findTaskById", pd);
	}

	@Override
	public int doTask(PageData pd) throws Exception {
		return dao.save("WorkBenchMapper.doTask", pd);
	}

	/**
	 * 我的案件任务列表
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> findTaskByOrderId(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("WorkBenchMapper.findTaskByOrderId", pd);
	}

	/**
	 * 执行我的案件任务
	 */
	@Override
	public int doOrderTask(PageData pd) throws Exception {
		return dao.save("WorkBenchMapper.doOrderTask", pd);
	}

	/**
	 * 删除回款计划
	 */
	@Override
	public int deletePaymentplan(PageData pd) throws Exception {
		return dao.delete("WorkBenchMapper.deletePaymentplan", pd);
	}

	/**
	 * 删除回款明细
	 */
	@Override
	public int deletePaymentdetail(PageData pd) throws Exception {
		return dao.delete("WorkBenchMapper.deletePaymentdetail", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> paymentplanList(Page page) throws Exception {
		return (List<PageData>) dao.findForList("WorkBenchMapper.paymentplanlistPage", page);
	}

	@Override
	public int updateVisit(PageData pd) throws Exception {
		return dao.update("WorkBenchMapper.updateVisit", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> relateTasklistPage(Page page) throws Exception {
		return (List<PageData>) dao.findForList("WorkBenchMapper.relateTasklistPage", page);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> orderReportList(Page page) throws Exception {
		return (List<PageData>) dao.findForList("WorkBenchMapper.orderReportlistPage", page);
	}

	@Override
	public PageData finOrderReportById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("WorkBenchMapper.finOrderReportById", pd);
	}

	@Override
	public void updateReportStatus(PageData pd) throws Exception {
		dao.update("WorkBenchMapper.updateReportStatus", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> findPaymentPlan(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("WorkBenchMapper.findPaymentPlan", pd);
	}

	@Override
	public void saveWarnTask(PageData pd) throws Exception {
		dao.save("WorkBenchMapper.saveWarnTask", pd);
	}

	@Override
	public PageData findWarnById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("WorkBenchMapper.findWarnById", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> xyhkList(Page page) throws Exception {
		return (List<PageData>) dao.findForList("WorkBenchMapper.xyhklistPage", page);
	}

	@Override
	public void saveWarnTask1(PageData pd) throws Exception {
		dao.save("WorkBenchMapper.saveWarnTask1", pd);
	}

	@Override
	public void updateWarnInfo(PageData pd) throws Exception {
		dao.update("WorkBenchMapper.updateWarnInfo", pd);
	}

	@Override
	public int doUpdate(PageData pd) throws Exception {
		return dao.update("WorkBenchMapper.doUpdate", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> shList(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("WorkBenchMapper.shList", pd);
	}

	@Override
	public int doUpdate1(PageData pd) throws Exception {
		return dao.update("WorkBenchMapper.doUpdate1", pd);
	}

	@Override
	public PageData findWarnById1(PageData pd) throws Exception {
		return (PageData) dao.findForObject("WorkBenchMapper.findWarnById1", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> xyhkList1(Page page) throws Exception {
		return (List<PageData>) dao.findForList("WorkBenchMapper.xyhklistPage1", page);
	}

	@Override
	public PageData findNextMoney(PageData pd) throws Exception {
		return (PageData) dao.findForObject("WorkBenchMapper.findNextMoney", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listAll(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("WorkBenchMapper.listAll", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> ajhkList(Page page) throws Exception {
		return (List<PageData>) dao.findForList("WorkBenchMapper.ajhklistPage", page);
	}

	@Override
	public void updateReportLinkman(PageData pd) throws Exception {
		dao.update("WorkBenchMapper.updateReportLinkman", pd);
	}

	@Override
	public void saveCallinfo(PageData pd) throws Exception {
		dao.save("WorkBenchMapper.saveCallinfo", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> callList(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("WorkBenchMapper.callList", pd);
	}

	@Override
	public void saveLinks(List<PageData> pd) throws Exception {
		dao.update("WorkBenchMapper.saveLinks", pd);
	}

	@Override
	public void updatePaymentPlan(PageData pd) throws Exception {
		dao.update("WorkBenchMapper.updatePaymentPlan", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> ajhkList1(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("WorkBenchMapper.ajhkList", pd);
	}

	@Override
	public PageData findRepportById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("WorkBenchMapper.findRepportById", pd);
	}

	@Override
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("WorkBenchMapper.findById", pd);
	}

	@Override
	public PageData findTaskById1(PageData pd) throws Exception {
		return (PageData) dao.findForObject("WorkBenchMapper.findTaskById1", pd);
	}

	@Override
	public void updateVisitInfo(PageData pd1) throws Exception {
		dao.update("WorkBenchMapper.updateVisitInfo", pd1);
	}
}
