package com.yocto.service.business.workBenchManage;

import java.util.List;

import com.yocto.entity.Page;
import com.yocto.util.PageData;

/**
 * 任务信息接口类
 * 
 * @author wxh
 *
 */
public interface IWorkBenchService {

	/**
	 * 我的任务列表(接口用)
	 * 
	 * @param PageData
	 * @return
	 * @throws Exception
	 */
	public List<PageData> taskList(PageData pd) throws Exception;

	/**
	 * 我的客户列表(接口用)
	 * 
	 * @param PageData
	 * @return
	 * @throws Exception
	 */
	public List<PageData> customerList(PageData pd) throws Exception;

	/**
	 * 我的联系人列表(接口用)
	 * 
	 * @param PageData
	 * @return
	 * @throws Exception
	 */
	public List<PageData> linkManList(PageData pd) throws Exception;

	/**
	 * 我的案件列表(接口用)
	 * 
	 * @param PageData
	 * @return
	 * @throws Exception
	 */
	public List<PageData> orderList(PageData pd) throws Exception;

	/**
	 * 我的任务列表
	 * 
	 * @param PageData
	 * @return
	 * @throws Exception
	 */
	public List<PageData> taskListPage(Page page) throws Exception;

	/**
	 * 我的客户列表
	 * 
	 * @param PageData
	 * @return
	 * @throws Exception
	 */
	public List<PageData> customerListPage(Page page) throws Exception;

	/**
	 * 我的联系人列表
	 * 
	 * @param PageData
	 * @return
	 * @throws Exception
	 */
	public List<PageData> linkManListPage(Page page) throws Exception;

	/**
	 * 我的案件列表
	 * 
	 * @param PageData
	 * @return
	 * @throws Exception
	 */
	public List<PageData> orderListPage(Page page) throws Exception;

	/**
	 * 根据ID获取任务内容
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findTaskById(PageData pd) throws Exception;

	/**
	 * 执行任务
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public int doTask(PageData pd) throws Exception;

	/**
	 * 我的案件任务列表
	 * 
	 * @param PageData
	 * @return
	 * @throws Exception
	 */
	public List<PageData> findTaskByOrderId(PageData pd) throws Exception;

	/**
	 * 执行我的案件任务
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public int doOrderTask(PageData pd) throws Exception;

	/**
	 * 删除回款计划
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public int deletePaymentplan(PageData pd) throws Exception;

	/**
	 * 删除回款明细
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public int deletePaymentdetail(PageData pd) throws Exception;

	/**
	 * 回款计划列表
	 * 
	 * @param PageData
	 * @return
	 * @throws Exception
	 */
	public List<PageData> paymentplanList(Page page) throws Exception;

	public int updateVisit(PageData pd) throws Exception;

	/**
	 * 关联任务列表
	 * 
	 * @param PageData
	 * @return
	 * @throws Exception
	 */
	public List<PageData> relateTasklistPage(Page page) throws Exception;

	public List<PageData> orderReportList(Page page) throws Exception;

	public PageData finOrderReportById(PageData pd) throws Exception;

	public void updateReportStatus(PageData pd) throws Exception;

	public List<PageData> findPaymentPlan(PageData pd) throws Exception;

	public void saveWarnTask(PageData pd) throws Exception;

	public PageData findWarnById(PageData pd) throws Exception;

	public List<PageData> xyhkList(Page page) throws Exception;

	public void saveWarnTask1(PageData pd) throws Exception;

	public void updateWarnInfo(PageData pd) throws Exception;

	public int doUpdate(PageData pd) throws Exception;

	public List<PageData> shList(PageData pd) throws Exception;

	public int doUpdate1(PageData pd) throws Exception;

	public PageData findWarnById1(PageData pd) throws Exception;

	public List<PageData> xyhkList1(Page page) throws Exception;

	public PageData findNextMoney(PageData pd) throws Exception;

	public List<PageData> listAll(PageData pd) throws Exception;

	public List<PageData> ajhkList(Page page) throws Exception;

	public void updateReportLinkman(PageData pd) throws Exception;

	public void saveCallinfo(PageData pd) throws Exception;

	public List<PageData> callList(PageData pd) throws Exception;

	public void saveLinks(List<PageData> pd) throws Exception;

	public void updatePaymentPlan(PageData pd) throws Exception;

	public List<PageData> ajhkList1(PageData pd) throws Exception;

	public PageData findRepportById(PageData pd) throws Exception;

	public PageData findById(PageData pd) throws Exception;

	public PageData findTaskById1(PageData pd) throws Exception;

	public void updateVisitInfo(PageData pd1) throws Exception;

}
