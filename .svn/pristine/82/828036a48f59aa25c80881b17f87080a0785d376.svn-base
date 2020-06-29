package com.yocto.controller.business.taskassignManage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.socket.TextMessage;

import com.yocto.controller.base.BaseController;
import com.yocto.entity.Page;
import com.yocto.entity.system.User;
import com.yocto.service.business.customerManage.ICustomerService;
import com.yocto.service.business.taskassignManage.ITaskassignService;
import com.yocto.service.business.visitManage.IVisitService;
import com.yocto.util.AppUtil;
import com.yocto.util.Const;
import com.yocto.util.DateUtil;
import com.yocto.util.Jurisdiction;
import com.yocto.util.PageData;
import com.yocto.util.TextUtil;
import com.yocto.websockect.SystemWebSocketHandler;

/**
 * 任务指派 只针对外访
 * 
 * @author xl
 *
 */
@Controller
@RequestMapping(value = "taskAssign")
public class TaskAssignController extends BaseController {

	String menuUrl1 = "taskAssign/visit/list.do"; // 外访客户

	@Resource(name = "taskassignService")
	private ITaskassignService taskassignService;

	@Resource(name = "customerService")
	private ICustomerService customerService;

	@Resource(name = "visitService")
	private IVisitService visitService;

	@Resource(name = "systemWebSocketHandler")
	private SystemWebSocketHandler systemWebSocketHandler;

	/**
	 * 外访客户
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */

	@RequestMapping(value = "visit/list")
	public ModelAndView visitList(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		try {
			PageData pd = this.getPageData();
			String keywords = pd.getString("keywords"); // 关键词检索条件
			if (TextUtil.isNotNull(keywords)) {
				pd.put("keywords", keywords.trim());
			}
			String createTimeStart = pd.getString("createTimeStart"); // 开始时间
			String createTimeEnd = pd.getString("createTimeEnd"); // 结束时间
			if (TextUtil.isNotNull(createTimeStart)) {
				pd.put("createTimeStart", createTimeStart + " 00:00:00");
			}
			if (TextUtil.isNotNull(createTimeEnd)) {
				pd.put("createTimeEnd", createTimeEnd + " 23:59:59");
			}
			page.setPd(pd);
			User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
			// 运作总监
			/*	if (Const.ROLE_OPERATION_DIRECTOR.equals(user.getROLE_ID())) {
					pd.put("auditerId", user.getUSER_ID());
				}
				// 销售总监
				if (Const.ROLE_SALES_DIRECTOR.equals(user.getROLE_ID())) {
					pd.put("auditerId", user.getUSER_ID());
				}*/

			if (!Const.ROLE_MANAGER.equals(user.getROLE_ID())) {
				pd.put("auditerId", user.getUSER_ID());
			}
			List<PageData> list = taskassignService.listVisit(page); // 列出客户列表
			mv.addObject("orderList", list);
			mv.addObject("pd", pd);
			mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
			mv.setViewName("business/taskassignManage/visit_list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	/**
	 * 去外访新增页面
	 * 
	 * @return
	 * @throws Exception
	 */

	@RequestMapping(value = "visit/goAdd")
	public ModelAndView goAdd() throws Exception {
		/*if (!Jurisdiction.buttonJurisdiction(menuUrl1, "add")) {
			return null;
		} */// 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
		// 运作总监
		if (Const.ROLE_OPERATION_DIRECTOR.equals(user.getROLE_ID())) {
			pd.put("flag", 1);
		}
		// 销售总监
		if (Const.ROLE_SALES_DIRECTOR.equals(user.getROLE_ID())) {
			pd.put("flag", 2);
		}
		pd.put("auditerId", user.getUSER_ID());
		mv.addObject("pd", pd);
		mv.addObject("msg", "doAdd");
		mv.setViewName("business/taskassignManage/visit_edit");
		return mv;
	}

	/**
	 * 新增外访信息(生成任务2)该任务是由领导发起的
	 * 
	 * @return
	 */

	@RequestMapping(value = "visit/doAdd")
	public Object visitdoAdd() throws Exception {
		/*if (!Jurisdiction.buttonJurisdiction(menuUrl1, "add")) {
			return null;
		}*/// 校验权限
		logBefore(logger, Jurisdiction.getUsername() + "新增");
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
		pd.put("auditerId", user.getUSER_ID());
		taskassignService.saveVisit(pd);
		// 生成一条外访任务
		pd.put("type", 1);
		taskassignService.saveTasks(pd);

		PageData pd4 = new PageData();
		pd4.put("userId", pd.getString("userId"));
		pd4.put("type", 9);
		pd4.put("relateId", pd.get("id"));
		pd4.put("content", user.getNAME() + "给你安排了一个任务请查看");
		pd4.put("flag", 0);
		customerService.saveNoticInfo(pd4);
		systemWebSocketHandler.sendMessageToUser(pd.getString("userId").toString(), new TextMessage("notic:" + pd4.get("id")));
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 去修改页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "visit/goEdit")
	public ModelAndView goEdit() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		pd = visitService.findById(pd); // 根据ID读取
		mv.addObject("pd", pd);
		mv.addObject("msg", "doEdit");
		mv.setViewName("business/taskassignManage/visit_edit");
		return mv;
	}

	/**
	 * 修改合同
	 * 
	 * @return
	 */
	@RequestMapping(value = "visit/doEdit")
	public ModelAndView doEdit() throws Exception {
		String currenTime = DateUtil.getCurrentTime();
		logBefore(logger, Jurisdiction.getUsername() + "修改customer");
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		pd.put("editTime", currenTime);
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
		pd.put("auditerId", user.getUSER_ID());
		taskassignService.update(pd);

		pd.put("type", 1);
		taskassignService.updateTasks(pd);
		PageData pd6 = taskassignService.findTasks(pd);

		PageData pd4 = new PageData();
		pd4.put("userId", pd.getString("userId"));
		pd4.put("type", 9);
		pd4.put("relateId", pd6.get("id"));
		pd4.put("flag", 0);
		PageData pd5 = customerService.findNoticInfo(pd4);
		customerService.updateNoticInfo(pd4);
		if (!pd5.getString("userId").equals(pd.getString("userId"))) {
			systemWebSocketHandler.sendMessageToUser(pd.getString("userId").toString(), new TextMessage("notic:" + pd6.get("id")));
		}
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 删除
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "visit/delete")
	@ResponseBody
	public Object delete() throws Exception {
		String error = "";
		String msg = "";
		logBefore(logger, Jurisdiction.getUsername() + "批量删除customer");
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = this.getPageData();
		String ids = pd.getString("ids");
		if (TextUtil.isNotNull(ids)) {
			System.out.println("批量删除ids:" + ids);
			String arrayIds[] = ids.split(",");
			if (null != arrayIds && arrayIds.length > 0) {
				visitService.delete(arrayIds);
				pd.put("type", 1);
				pd.put("id", arrayIds);
				taskassignService.deleteTasks(pd);
				PageData pd6 = taskassignService.findTasks(pd);
				PageData pd4 = new PageData();
				pd4.put("type", 9);
				pd4.put("relateId", pd6.get("id"));
				customerService.deleteNoticInfo(pd4);
				error = "00";
				msg = "删除成功";
			}
		} else {
			error = "02";
			msg = "缺少参数";
		}
		map.put("error", error);
		map.put("msg", msg);
		return AppUtil.returnObject(pd, map);
	}

}
