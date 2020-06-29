package com.yocto.controller.business.LinkmanManage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;
import com.yocto.controller.base.BaseController;
import com.yocto.entity.Page;
import com.yocto.entity.system.User;
import com.yocto.service.business.customerManage.ICustomerService;
import com.yocto.service.business.linkmanManage.ILinkmanService;
import com.yocto.util.AppUtil;
import com.yocto.util.Const;
import com.yocto.util.DateUtil;
import com.yocto.util.Jurisdiction;
import com.yocto.util.PageData;
import com.yocto.util.TextUtil;

@Controller
@RequestMapping(value = "/linkman")
public class LinkManController extends BaseController {
	String menuUrl = "linkman/list.do"; // 菜单地址(权限用)

	@Resource(name = "linkmanService")
	private ILinkmanService linkmanService;

	@Resource(name = "customerService")
	private ICustomerService customerService;

	/**
	 * 获取案件列表
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
			User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
			String userId = user.getUSER_ID();
			// 销售能看到自己的客户
			if ("2".equals(this.getGroup())) {
				pd.put("saleId", userId);
			}
			page.setPd(pd);
			List<PageData> list = linkmanService.list(page); // 列出客户列表
			mv.addObject("linkmanList", list);
			mv.addObject("pd", pd);
			mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
			mv.setViewName("business/linkmanManage/linkman_list");
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
		// if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
		// return null;
		// } // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.addObject("pd", pd);
		mv.addObject("msg", "doAdd");
		mv.setViewName("business/linkmanManage/linkman_edit");
		return mv;
	}

	/**
	 * 新增案件信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/doAdd")
	public Object doAdd() throws Exception {
		// if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
		// return null;
		// } // 校验权限
		logBefore(logger, Jurisdiction.getUsername() + "新增");
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		pd.put("type", 0);
		linkmanService.save(pd);
		// 根据案件找出客户id和债务人id

		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
		PageData pd2 = new PageData();
		pd2.put("userId", user.getUSER_ID());
		pd2.put("saleCustomerId", pd.getString("id"));
		pd2.put("type", "保存");
		pd2.put("flag", "3");
		customerService.saveLogs(pd2);

		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	@RequestMapping(value = "/saveOrupdate")
	@ResponseBody
	public Object saveOrupdate() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<PageData> result = null;
		String msg = null;
		String error = null;
		try {
			PageData pd = this.getPageData();
			if (TextUtil.isNull(pd.getString("id"))) {
				linkmanService.save(pd);
				error = "00";
				msg = "保存成功";

				User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
				PageData pd2 = new PageData();
				pd2.put("userId", user.getUSER_ID());
				pd2.put("saleCustomerId", pd.getString("id"));
				pd2.put("type", "保存");
				pd2.put("flag", "3");
				customerService.saveLogs(pd2);
			} else {
				linkmanService.update1(pd);
				error = "00";
				msg = "修改成功";

				PageData pd1 = linkmanService.findById(pd);
				String qzdz = "";
				String hzdz = "";
				// 前字段值、后字段值
				if (TextUtil.isNotNull(pd.getString("name"))) {
					qzdz = pd1.getString("name");
					hzdz = pd.getString("name");
				}
				if (TextUtil.isNotNull(pd.getString("mobilePhone"))) {
					qzdz = pd1.getString("mobilePhone");
					hzdz = pd.getString("mobilePhone");
				}
				if (TextUtil.isNotNull(pd.getString("landline"))) {
					qzdz = pd1.getString("landline");
					hzdz = pd.getString("landline");
				}
				if (TextUtil.isNotNull(pd.getString("email"))) {
					qzdz = pd1.getString("email");
					hzdz = pd.getString("email");
				}
				if (TextUtil.isNotNull(pd.getString("postion"))) {
					qzdz = pd1.getString("postion");
					hzdz = pd.getString("postion");
				}
				if (TextUtil.isNotNull(pd.getString("fax"))) {
					qzdz = pd1.getString("fax");
					hzdz = pd.getString("fax");
				}
				if (TextUtil.isNotNull(pd.getString("remark"))) {
					qzdz = pd1.getString("remark");
					hzdz = pd.getString("remark");
				}
				if (TextUtil.isNotNull(pd.getString("wechat"))) {
					qzdz = pd1.getString("wechat");
					hzdz = pd.getString("wechat");
				}
				if (TextUtil.isNotNull(pd.getString("qq"))) {
					qzdz = pd1.getString("qq");
					hzdz = pd.getString("qq");
				}

				User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
				PageData pd2 = new PageData();
				pd2.put("userId", user.getUSER_ID());
				pd2.put("qzdz", qzdz);
				pd2.put("hzdz", hzdz);
				pd2.put("saleCustomerId", pd.getString("id"));
				pd2.put("type", "编辑");
				pd2.put("flag", "3");
				customerService.saveLogs(pd2);
			}
		} catch (Exception e) {
			error = "01";
			msg = "程序异常";
			e.printStackTrace();
		} finally {
			map.put("error", error);
			map.put("msg", msg);
			map.put("result", result);
		}
		return AppUtil.returnObject(new PageData(), map);
	}

	/**
	 * 删除
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete1")
	@ResponseBody
	public Object delete1() throws Exception {
		String error = "";
		String msg = "";
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = this.getPageData();
		String ids = pd.getString("ids");
		if (TextUtil.isNotNull(ids)) {
			if ("1".equals(this.getGroup())) {
				error = "01";
				msg = "您没有权限删除";
			} else {
				String arrayIds[] = ids.split(",");
				if (null != arrayIds && arrayIds.length > 0) {
					linkmanService.delete(arrayIds);
					error = "00";
					msg = "删除成功";

					for (String id : arrayIds) {
						User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
						PageData pd2 = new PageData();
						pd2.put("userId", user.getUSER_ID());
						pd2.put("saleCustomerId", id);
						pd2.put("type", "删除");
						pd2.put("flag", "3");
						customerService.saveLogs(pd2);
					}
				}
			}
		} else {
			error = "02";
			msg = "缺少参数";
		}
		map.put("error", error);
		map.put("msg", msg);
		return AppUtil.returnObject(pd, map);
	}

	//

	/**
	 * 去修改页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goEdit")
	public ModelAndView goEdit() throws Exception {
		// if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
		// return null;
		// } // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		pd = linkmanService.findById(pd); // 根据ID读取
		mv.addObject("pd", pd);
		mv.addObject("msg", "doEdit");
		mv.setViewName("business/linkmanManage/linkman_edit");
		return mv;
	}

	/**
	 * 修改联系人
	 * 
	 * @return
	 */
	@RequestMapping(value = "/doEdit")
	public ModelAndView doEdit() throws Exception {
		String currenTime = DateUtil.getCurrentTime();

		logBefore(logger, Jurisdiction.getUsername() + "修改customer");
		// if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha") || !Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
		// return null;
		// } // 校验权限 判断当前操作者有无客户管理查看权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		pd.put("editTime", currenTime);
		PageData pd2 = linkmanService.findById(pd);
		linkmanService.update(pd);
		PageData pd1 = linkmanService.findById(pd);

		if (null != pd1) {
			this.ggMethod(pd2, pd1, "discount");
			this.ggMethod(pd2, pd1, "name");
			this.ggMethod(pd2, pd1, "mobilePhone");
			this.ggMethod(pd2, pd1, "landline");
			this.ggMethod(pd2, pd1, "email");
			this.ggMethod(pd2, pd1, "postion");
			this.ggMethod(pd2, pd1, "remark");
			this.ggMethod(pd2, pd1, "wechat");
			this.ggMethod(pd2, pd1, "qq");
		}

		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	public void ggMethod(PageData pd, PageData pd1, String param) throws Exception {
		String qzdz = "";
		String hzdz = "";
		if (!(null != pd.get(param) ? pd.get(param).toString() : "").equals(null != pd1.get(param) ? pd1.get(param).toString() : "")) {
			qzdz = pd.get(param).toString();
			hzdz = pd1.get(param).toString();
			User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
			PageData pd2 = new PageData();
			pd2.put("userId", user.getUSER_ID());
			pd2.put("qzdz", qzdz);
			pd2.put("hzdz", hzdz);
			pd2.put("saleCustomerId", pd.get("id"));
			pd2.put("type", "编辑");
			pd2.put("flag", "3");
			customerService.saveLogs(pd2);
		}
	}

	/**
	 * 删除
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delete() throws Exception {
		// if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
		// return null;
		// } // 校验权限
		String error = "";
		String msg = "";
		logBefore(logger, Jurisdiction.getUsername() + "批量删除linkman");
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = this.getPageData();
		String ids = pd.getString("ids");
		if (TextUtil.isNotNull(ids)) {
			System.out.println("批量删除ids:" + ids);
			String arrayIds[] = ids.split(",");
			if (null != arrayIds && arrayIds.length > 0) {
				linkmanService.delete(arrayIds);
				error = "00";
				msg = "删除成功";

				for (String id : arrayIds) {
					User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
					PageData pd2 = new PageData();
					pd2.put("userId", user.getUSER_ID());
					pd2.put("saleCustomerId", id);
					pd2.put("type", "删除");
					pd2.put("flag", "3");
					customerService.saveLogs(pd2);
				}
			}
		} else {
			error = "02";
			msg = "缺少参数";
		}
		map.put("error", error);
		map.put("msg", msg);
		return AppUtil.returnObject(pd, map);
	}

	/**
	 * 判断客户手机号是否存在
	 * 
	 * @return
	 */
	@RequestMapping(value = "/hasMobilePhone")
	@ResponseBody
	public Object hasMobilePhone() {
		Map<String, String> map = new HashMap<String, String>();
		String errInfo = "success";
		PageData pd = this.getPageData();
		try {
			if (linkmanService.findByMobilePhone(pd) != null) {
				errInfo = "error";
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo); // 返回结果
		return AppUtil.returnObject(new PageData(), map);
	}

	/**
	 * 展示联系人列表
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/chooseLinkman")
	public ModelAndView chooseLinkman(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		try {
			PageData pd = new PageData();
			pd = this.getPageData();
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
			List<PageData> cusList = linkmanService.list(page); // 列出客户列表
			mv.setViewName("business/linkmanManage/chooseLinkman_list");
			mv.addObject("cusList", cusList);
			mv.addObject("pd", pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	@RequestMapping(value = "/hasLinkCustomer")
	@ResponseBody
	public Object hasLinkCustomer() {
		Map<String, String> map = new HashMap<String, String>();
		String errInfo = "success";
		PageData pd = this.getPageData();
		try {
			PageData pd1 = linkmanService.findById(pd);
			if (null != pd1) {
				if (null != pd1.get("relateId") && !"0".equals(pd1.get("relateId").toString())) {
					errInfo = "error";
				}
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo); // 返回结果
		return AppUtil.returnObject(new PageData(), map);
	}

	/**
	 * 查看案件详情
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/view")
	public ModelAndView viewOrder() throws Exception {
		ModelAndView mv = this.getModelAndView();
		try {
			PageData pd = new PageData();
			pd = this.getPageData();
			pd = linkmanService.findById(pd); // 列出客户列表
			mv.setViewName("business/linkmanManage/linkman_view");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	@RequestMapping(value = "/getLinkmanInfo")
	@ResponseBody
	public Object getLinkmanInfo() throws Exception {
		PageData pd = this.getPageData();
		pd.put("key", pd.getString("data[q]"));
		List<PageData> result = new ArrayList<PageData>();
		try {
			String pageNo = pd.getString("page");
			String pageSize = pd.getString("pageSize");
			/*String id = pd.getString("id");*/
			int pNo = TextUtil.isNull(pageNo) ? 1 : Integer.parseInt(pageNo);
			int pSize = TextUtil.isNull(pageSize) ? 10 : Integer.parseInt(pageSize);
			PageHelper.startPage(pNo, pSize); // 核心分页代码
			result = linkmanService.listAll(pd);
			List<PageData> linkmans = linkmanService.listAllLinkmanName(pd);
			for (PageData name : linkmans) {
				result.add(name);
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return JSONArray.fromObject(result);
	}

	/**
	 * 
	 * 保存债务人联系人
	 * 
	 * 
	 * 
	 */
	@RequestMapping(value = "/saveLinkMan")
	@ResponseBody
	public Object saveLinkMan() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String error = "";
		String msg = "";
		String result = "";
		try {
			PageData pd = this.getPageData();
			pd.put("type", pd.getString("type"));
			linkmanService.save(pd);
			if (TextUtil.isNotNull(pd.getString("flag"))) {
				linkmanService.saveOrderLinkman(pd);
			}
			error = "00";
			msg = "保存成功";
			result = pd.get("id").toString();
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

	@RequestMapping(value = "/updateLinkMan")
	@ResponseBody
	public Object updateLinkMan() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String error = "";
		String msg = "";
		PageData result = null;
		try {
			PageData pd = this.getPageData();
			pd.put("type", pd.getString("type"));
			linkmanService.update1(pd);
			error = "00";
			msg = "保存成功";
			result = linkmanService.findById1(pd);
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

}
