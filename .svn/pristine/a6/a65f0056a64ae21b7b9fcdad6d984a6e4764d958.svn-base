package com.yocto.controller.business.lawsuitauditManage;

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
import com.yocto.service.business.lawsuitauditManage.ILawsuitauditService;
import com.yocto.service.business.orderManage.IHandoverService;
import com.yocto.util.AppUtil;
import com.yocto.util.Const;
import com.yocto.util.DateUtil;
import com.yocto.util.Jurisdiction;
import com.yocto.util.PageData;
import com.yocto.util.TextUtil;
import com.yocto.websockect.SystemWebSocketHandler;

@Controller
@RequestMapping(value = "lawsuitaudit")
public class LawsuitauditController extends BaseController {

	String menuUrl = "lawsuitaudit/list.do"; // 菜单地址(权限用)

	@Resource(name = "lawsuitauditService")
	private ILawsuitauditService lawsuitauditService;

	@Resource(name = "handoverService")
	private IHandoverService handoverService;

	@Resource(name = "customerService")
	private ICustomerService customerService;

	@Resource(name = "systemWebSocketHandler")
	private SystemWebSocketHandler systemWebSocketHandler;

	/**
	 * 审核列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
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
			if (Const.ROLE_OPERATION_DIRECTOR.equals(user.getROLE_ID())) {
				pd.put("flags", "0");
			}
			if (isRole(Const.ROLE_MANAGER) || isRole(Const.ROLE_CUSTOMER_SERVICE)) {
				pd.put("flags", "3");
			}
			List<PageData> list = lawsuitauditService.list(page); // 列出客户列表
			mv.addObject("lawList", list);
			mv.addObject("pd", pd);
			mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
			mv.setViewName("business/lawsuitauditManage/lawsuitaudit_list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	// 去审核页面
	@RequestMapping(value = "/goEdit")
	public ModelAndView goAdd() throws Exception {
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String sy_workwench = pd.getString("sy_workwench");
		pd = lawsuitauditService.findById(pd);
		pd.put("sy_workwench", sy_workwench);
		mv.addObject("pd", pd);
		mv.addObject("msg", "doEdit");
		mv.setViewName("business/lawsuitauditManage/lawsuitaudit_edit");
		return mv;
	}

	@RequestMapping(value = "/getLaw")
	@ResponseBody
	public Object getLaw() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData result = null;
		String msg = null;
		String error = null;
		try {
			PageData pd = this.getPageData();
			result = lawsuitauditService.findById(pd);
			error = "00";
			msg = "获取成功";
		} catch (Exception e) {
			error = "500";
			msg = "程序异常";
			e.printStackTrace();
		} finally {
			map.put("error", error);
			map.put("msg", msg);
			map.put("result", result);
		}
		return AppUtil.returnObject(new PageData(), map);
	}

	// 审核
	@RequestMapping(value = "/doEdit")
	public ModelAndView doEdit() throws Exception {
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
		pd.put("auditerId", user.getUSER_ID());
		pd.put("editTime", DateUtil.getCurrentTime());
		lawsuitauditService.update(pd);
		if ("1".equals(pd.getString("auditStatus"))) {
			// 将审核状态改为//诉讼或者总裁
			if ("0".equals(pd.getString("applyType"))) {
				pd.put("status", 3);
				lawsuitauditService.updateOrder(pd);
				handoverService.save(pd);
				/*pd.put("type", 5);
				pd.put("title", "对该案件申请的诉讼已通过审核，点击此任务进入案件交接流程");
				lawsuitauditService.saveTask(pd);*/
			}
			if ("1".equals(pd.getString("applyType"))) {
				pd.put("status", 4);
				lawsuitauditService.updateOrder(pd);
				handoverService.save(pd);
				/*pd.put("type", 6);
				pd.put("title", "对该案件申请的仲裁已通过审核，点击此任务进入案件交接流程");
				lawsuitauditService.saveTask(pd);*/
			}

			if (TextUtil.isNotNull(pd.getString("recipientId"))) {
				PageData pd4 = new PageData();
				pd4.put("userId", pd.getString("recipientId"));
				pd4.put("type", 16);
				pd4.put("relateId", pd.getString("id"));
				pd4.put("content", "有新的诉讼/仲裁案件转移给了你");
				pd4.put("flag", 0);
				customerService.saveNoticInfo(pd4);
				systemWebSocketHandler.sendMessageToUser(pd.get("recipientId").toString(), new TextMessage("notic:" + pd4.get("id")));
			}
		}
		if ("1".equals(pd.getString("sy_workwench"))) {
			mv.addObject("sy_workwench", "1");
		}
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}
}
