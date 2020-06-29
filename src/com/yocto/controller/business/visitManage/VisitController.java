package com.yocto.controller.business.visitManage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yocto.controller.base.BaseController;
import com.yocto.entity.Page;
import com.yocto.entity.system.User;
import com.yocto.service.business.visitManage.IVisitService;
import com.yocto.util.AppUtil;
import com.yocto.util.Const;
import com.yocto.util.DateUtil;
import com.yocto.util.Jurisdiction;
import com.yocto.util.PageData;
import com.yocto.util.TextUtil;

/**
 * 申请外访运作和销售
 * 
 * @author Admin
 *
 */
@Controller
@RequestMapping(value = "/vist")
public class VisitController extends BaseController {

	String menuUrl = "vist/list.do"; // 菜单地址(权限用)

	@Resource(name = "visitService")
	private IVisitService visitService;

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
			// 运作精英
			if (Const.ROLE_ATTORNEY.equals(user.getROLE_ID()) || Const.ROLE_OPERATION.equals(user.getROLE_ID()) || Const.ROLE_OPERATION_DIRECTOR.equals(user.getROLE_ID())
					|| Const.ROLE_CUSTOMER_SERVICE.equals(user.getROLE_ID())) {
				pd.put("flag", "1");
			}
			// 销售精英
			if (Const.ROLE_SALES_ELITE.equals(user.getROLE_ID()) || Const.ROLE_SALES_DIRECTOR.equals(user.getROLE_ID()) || Const.ROLE_CUSTOMER_SERVICE.equals(user.getROLE_ID())) {
				pd.put("flag", "2");
			}

			pd.put("userId", user.getUSER_ID());
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
	 * 去新增页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goAdd")
	public ModelAndView goAdd() throws Exception {
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
		// 获取当前登陆人信息
		if (Const.ROLE_OPERATION.equals(user.getROLE_ID()) || Const.ROLE_OPERATION_DIRECTOR.equals(user.getROLE_ID()) || Const.ROLE_CUSTOMER_SERVICE.equals(user.getROLE_ID())) {
			pd.put("flag", "1");
		}
		// 销售精英
		if (Const.ROLE_SALES_ELITE.equals(user.getROLE_ID()) || Const.ROLE_SALES_DIRECTOR.equals(user.getROLE_ID()) || Const.ROLE_CUSTOMER_SERVICE.equals(user.getROLE_ID())) {
			pd.put("flag", "2");
		}
		mv.addObject("pd", pd);
		mv.addObject("msg", "doAdd");
		mv.setViewName("business/visitManage/visit_edit");
		return mv;
	}

	/**
	 * 新增完毕后就是待审核状态
	 * 
	 * @return
	 */
	@RequestMapping(value = "/doAdd")
	public Object doAdd() throws Exception {
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		logBefore(logger, Jurisdiction.getUsername() + "新增");
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
		pd.put("userId", user.getUSER_ID());
		// update 指派任务为完成
		/*	visitService.updateWranInfo(pd);*/
		visitService.save(pd);
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
	@RequestMapping(value = "/goEdit")
	public ModelAndView goEdit() throws Exception {
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		pd = visitService.findById(pd); // 根据ID读取
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
		if (Const.ROLE_OPERATION.equals(user.getROLE_ID()) || Const.ROLE_OPERATION_DIRECTOR.equals(user.getROLE_ID()) || Const.ROLE_CUSTOMER_SERVICE.equals(user.getROLE_ID())) {
			pd.put("flag", "1");
		}
		// 销售精英
		if (Const.ROLE_SALES_ELITE.equals(user.getROLE_ID()) || Const.ROLE_SALES_DIRECTOR.equals(user.getROLE_ID()) || Const.ROLE_CUSTOMER_SERVICE.equals(user.getROLE_ID())) {
			pd.put("flag", "2");
		}
		mv.addObject("pd", pd);
		mv.addObject("msg", "doEdit");
		mv.setViewName("business/visitManage/visit_edit");
		return mv;
	}

	/**
	 * 修改合同
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
		if("1".equals(pd.getString("auditStatus"))){
			pd.put("auditStatus", "0");
		}
		pd.put("editTime", currenTime);
		visitService.update(pd);
		String auditStatus = pd.getString("auditStatus");
		if ("2".equals(auditStatus)) {
			visitService.updateWranInfo1(pd);  //审核已通过
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
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delete() throws Exception {
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return null;
		} // 校验权限
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

	@RequestMapping(value = "/view")
	public ModelAndView view() throws Exception {
		ModelAndView mv = this.getModelAndView();
		try {
			PageData pd = new PageData();
			pd = this.getPageData();
			pd = visitService.findById(pd); // 列出客户列表
			mv.setViewName("business/visitManage/visit_view");
			mv.addObject("pd", pd);
			mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

}
