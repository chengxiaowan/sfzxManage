package com.yocto.controller.business.visitManage;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yocto.controller.base.BaseController;
import com.yocto.entity.Page;
import com.yocto.entity.system.User;
import com.yocto.service.business.taskassignManage.ITaskassignService;
import com.yocto.service.business.visitManage.IVisitService;
import com.yocto.util.Const;
import com.yocto.util.DateUtil;
import com.yocto.util.Jurisdiction;
import com.yocto.util.PageData;
import com.yocto.util.TextUtil;

/**
 * 外访审核
 * 
 * @author Admin
 *
 */
@Controller
@RequestMapping(value = "/vistsh")
public class VisitShenHeController extends BaseController {

	String menuUrl = "vistsh/list.do"; // 菜单地址(权限用)

	@Resource(name = "visitService")
	private IVisitService visitService;

	@Resource(name = "taskassignService")
	private ITaskassignService taskassignService;

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
			// 运作总监
			if (Const.ROLE_OPERATION_DIRECTOR.equals(user.getROLE_ID())) {
				pd.put("flag", "3");
				pd.put("zjid", user.getUSER_ID());
			}
			// 销售总监
			if (Const.ROLE_SALES_DIRECTOR.equals(user.getROLE_ID())) {
				pd.put("flag", "4");
				pd.put("zjid", user.getUSER_ID());
			}
			// 客服、经理、管理员 客服取消审核
			if (isRole(Const.ROLE_MANAGER) /*|| isRole(Const.ROLE_CUSTOMER_SERVICE)*/) {
				pd.put("flag", "5");
			}

			List<PageData> list = visitService.list(page); // 列出客户列表
			mv.addObject("orderList", list);
			mv.addObject("pd", pd);
			mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
			mv.setViewName("business/visitManage/visit_list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	/**
	 * 去修改页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goEdit")
	public ModelAndView goEdit() throws Exception {
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		// 运作总监
		pd = visitService.findById(pd); // 根据ID读取
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
		if (Const.ROLE_OPERATION_DIRECTOR.equals(user.getROLE_ID())) {
			pd.put("flag", "3");
		}
		// 销售总监
		if (Const.ROLE_SALES_DIRECTOR.equals(user.getROLE_ID())) {
			pd.put("flag", "4");
		}
		// 客服、经理、管理员
		if (isRole(Const.ROLE_MANAGER) || isRole(Const.ROLE_CUSTOMER_SERVICE)) {
			pd.put("flag", "5");
		}
		mv.addObject("pd", pd);
		mv.addObject("msg", "doEdit");
		mv.setViewName("business/visitManage/visit_edit");
		return mv;
	}

	/**
	 * 审核(生成任务1)
	 * 
	 * @return
	 */
	@RequestMapping(value = "/doEdit")
	public ModelAndView doEdit() throws Exception {
		String currenTime = DateUtil.getCurrentTime();
		logBefore(logger, Jurisdiction.getUsername() + "修改customer");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha") || !Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return null;
		} // 校验权限 判断当前操作者有无客户管理查看权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		pd.put("editTime", currenTime);
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
		pd.put("auditerId", user.getUSER_ID());
		visitService.update(pd);
		String auditStatus = pd.getString("auditStatus");
		if ("2".equals(auditStatus)) {
			pd.put("doId", pd.getString("userId")); // 执行任务人自己
			visitService.saveWarnInfo(pd);
		}
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
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
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
		String flag = "";
		// 获取当前登陆人信息
		if (Const.ROLE_OPERATION_DIRECTOR.equals(user.getROLE_ID())) {
			flag = "1"; // 代表运作总监
		}
		if (Const.ROLE_SALES_DIRECTOR.equals(user.getROLE_ID())) {
			flag = "2"; // 代表销售总监
		}
		mv.addObject("flag", flag);
		mv.addObject("pd", pd);
		mv.addObject("msg", "doAdd");
		mv.setViewName("business/taskassignManage/visit_edit");
		return mv;
	}

	/**
	 * 新增外访信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "visit/doAdd")
	public Object visitdoAdd() throws Exception {
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		logBefore(logger, Jurisdiction.getUsername() + "新增");
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
		pd.put("auditerId", user.getUSER_ID());
		taskassignService.saveVisit(pd);
		// 生成一条外访任务
		pd.put("type", 0);
		taskassignService.saveTasks(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}
}
