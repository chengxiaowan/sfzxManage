package com.yocto.controller.business.aboutUsManage;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yocto.controller.base.BaseController;
import com.yocto.entity.system.User;
import com.yocto.service.business.aboutUsManage.IAboutUsService;
import com.yocto.service.system.user.UserManager;
import com.yocto.util.AppUtil;
import com.yocto.util.Const;
import com.yocto.util.Jurisdiction;
import com.yocto.util.PageData;
import com.yocto.util.TextUtil;

/**
 * 日志查看
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "log")
public class AboutUsController extends BaseController {
	String menuUrl = "aboutUs/goEdit.do"; // 菜单地址(权限用)

	@Resource(name = "aboutUsService")
	private IAboutUsService aboutUsService;

	@Resource(name = "userService")
	private UserManager userService;

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
		PageData pd = new PageData();
		pd = this.getPageData();
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
		pd.put("id", user.getUSER_ID());
		mv.addObject("pd", pd);
		mv.setViewName("business/aboutUsManage/aboutUs_edit");
		mv.addObject("msg", "editAboutUs");
		return mv;
	}

	/**
	 * 修改
	 */
	@RequestMapping(value = "/edit")
	@ResponseBody
	public Object editAboutUs() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "修改AboutUs");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha") || !Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return null;
		} // 校验权限 判断当前操作者有无客户管理查看权限
		PageData pd = new PageData();
		pd = this.getPageData();
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
		String passwd = new SimpleHash("SHA-1", user.getUSERNAME(), pd.getString("originalPassword")).toString();
		if (passwd.equals(user.getPASSWORD())) {
			pd.put("password", new SimpleHash("SHA-1", user.getUSERNAME(), pd.getString("password")).toString());
			if (userService.updatePassword(pd) > 0) {
				user.setPASSWORD(pd.getString("password"));
				Jurisdiction.getSession().setAttribute(Const.SESSION_USER, user);
				pd.put("msg", "success");
			} else {
				pd.put("msg", "error");
			}
		} else {
			pd.put("msg", "error1");
		}
		return AppUtil.returnObject(new PageData(), pd);
	}

	@RequestMapping(value = "/getLogs")
	@ResponseBody
	public Object updateCallInfo() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = null;
		String error = null;
		PageInfo<PageData> page = null;
		try {
			PageData pd = this.getPageData();
			String pageNo = pd.getString("pageNo");
			String pageSize = pd.getString("pageSize");
			int pNo = TextUtil.isNull(pageNo) ? 1 : Integer.parseInt(pageNo);
			int pSize = TextUtil.isNull(pageSize) ? 10 : Integer.parseInt(pageSize);
			PageHelper.startPage(pNo, pSize); // 核心分页代码
			page = new PageInfo<PageData>(aboutUsService.getLogs(pd));// 列出任务列表
			error = "00";
			msg = "查询成功";
		} catch (Exception e) {
			error = "500";
			msg = "程序异常";
			e.printStackTrace();
		} finally {
			map.put("error", error);
			map.put("msg", msg);
			map.put("result", page);
		}
		return AppUtil.returnObject(new PageData(), map);
	}
}
