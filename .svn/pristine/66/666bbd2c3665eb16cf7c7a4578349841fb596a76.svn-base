package com.yocto.controller.business.customerManage;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.socket.TextMessage;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yocto.controller.base.BaseController;
import com.yocto.entity.Page;
import com.yocto.entity.system.User;
import com.yocto.service.business.customerManage.ICustomerService;
import com.yocto.service.business.expressManage.IExpressService;
import com.yocto.service.business.orderManage.IOrderService;
import com.yocto.util.AppUtil;
import com.yocto.util.Const;
import com.yocto.util.DateUtil;
import com.yocto.util.Jurisdiction;
import com.yocto.util.PageData;
import com.yocto.util.TextUtil;
import com.yocto.websockect.SystemWebSocketHandler;

/**
 * 类名称：CustomerController 创建人： @author 更新时间：2015年11月3日
 * 
 * @version
 */
@Controller
@RequestMapping(value = "/customer")
public class CustomerController extends BaseController {

	String menuUrl = "customer/list.do"; // 菜单地址(权限用)

	@Resource(name = "systemWebSocketHandler")
	private SystemWebSocketHandler systemWebSocketHandler;

	@Resource(name = "customerService")
	private ICustomerService customerService;

	@Resource(name = "orderService")
	private IOrderService orderService;

	@Resource(name = "expressService")
	private IExpressService expressService;

	/**
	 * 获取用户列表
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
			String userId = user.getUSER_ID();
			// 销售能看到自己的客户
			/*if (isRole(Const.ROLE_SALES_ELITE) || isRole(Const.ROLE_PHONE_SALES)) {
				if (!"1".equals(userId)) {
					pd.put("saleId", userId);
				}
			}*/
			if ("2".equals(this.getGroup())) {
				pd.put("saleId", userId);
			}
			if ("4".equals(this.getGroup()) || isRole(Const.ROLE_CUSTOMER_SERVICE)) {
				pd.put("flag", 1);
			}

			List<PageData> list = customerService.list(page); // 列出客户列表
			pd.put("parentId", 0);
			List<PageData> sfList = orderService.showCountryData(pd);
			mv.addObject("sfList", sfList);
			pd.put("parentId", 1);
			List<PageData> sqList = orderService.showCountryData(pd);
			mv.addObject("sqList", sqList);
			mv.addObject("customerList", list);
			mv.addObject("pd", pd);
			mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
			mv.setViewName("business/customerManage/customer_list");
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
		pd.put("parentId", 0);
		List<PageData> sfList = orderService.showCountryData(pd);
		mv.addObject("sfList", sfList);
		pd.put("parentId", 1);
		List<PageData> sqList = orderService.showCountryData(pd);
		mv.addObject("sqList", sqList);
		mv.addObject("msg", "doAdd");
		mv.setViewName("business/customerManage/customer_edit");
		return mv;
	}

	/**
	 * 新增用户信息
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
		/*pd.put("mobilePhone", TextUtil.transNull(pd.getString("mobilePhone")).trim());*/
		pd.put("companyAddress", TextUtil.transNull(pd.getString("companyAddress")).trim());
		pd.put("name", TextUtil.transNull(pd.getString("name")).trim());
		pd.put("bankAccountName", TextUtil.transNull(pd.getString("bankAccountName")).trim());
		/*pd.put("bankName", TextUtil.transNull(pd.getString("bankName")).trim());*/
		pd.put("bankNumber", TextUtil.transNull(pd.getString("bankNumber")).trim());
		pd.put("taxIdentificationNumber", TextUtil.transNull(pd.getString("taxIdentificationNumber")).trim());
		pd.put("postCode", TextUtil.transNull(pd.getString("postCode")).trim());
		pd.put("remark", TextUtil.transNull(pd.getString("remark")).trim());
		/*pd.put("status", TextUtil.transNull(pd.getString("status")).trim());*/
		customerService.saveOrUpdate(pd);
		System.out.println("保存后的ID：" + pd.get("id") + "==========================" + pd);

		if (TextUtil.isNotNull(pd.getString("orderSaleId"))) {
			PageData pd4 = new PageData();
			pd4.put("userId", pd.getString("orderSaleId"));
			pd4.put("type", 8);
			pd4.put("relateId", pd.get("id"));
			pd4.put("content", "恭喜您通过您的努力，" + pd.getString("name") + "已成为我司已成交客户");
			pd4.put("flag", 0);
			customerService.saveNoticInfo(pd4);
			systemWebSocketHandler.sendMessageToUser(pd.get("orderSaleId").toString(), new TextMessage("notic:" + pd4.get("id")));
		}

		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 去新增页面
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
		pd.put("parentId", 0);
		List<PageData> sfList = orderService.showCountryData(pd);
		mv.addObject("sfList", sfList);
		pd.put("parentId", 1);
		List<PageData> sqList = orderService.showCountryData(pd);
		mv.addObject("sqList", sqList);
		pd = customerService.findById(pd); // 根据ID读取
		mv.addObject("pd", pd);
		mv.addObject("msg", "doEdit");
		mv.setViewName("business/customerManage/customer_edit");
		return mv;
	}

	/**
	 * 查看详情接口
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goEdit1")
	@ResponseBody
	public Object goEdit1() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = "";
		String error = "";
		PageData result = new PageData();
		try {
			PageData pd = this.getPageData();
			result = customerService.findById(pd);
			error = "00";
			msg = "查询成功";
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

	@RequestMapping(value = "showAll")
	@ResponseBody
	public Object showAll() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<PageData> result = null;
		List<PageData> result1 = null;
		String msg = null;
		String error = null;
		try {
			PageData pd = this.getPageData();
			if (TextUtil.isNotNull(pd.getString("id"))) {
				result = customerService.findProcess(pd);
				// 根据id获取salecustomerId
				pd = customerService.findSalecustomerIdById(pd);
				result1 = customerService.findProcessInfo(pd);
				error = "00";
				msg = "获取列表成功";
			}
		} catch (Exception e) {
			error = "01";
			msg = "程序异常";
			e.printStackTrace();
		} finally {
			map.put("error", error);
			map.put("msg", msg);
			map.put("result", result);
			map.put("result1", result1);
		}
		return AppUtil.returnObject(new PageData(), map);
	}

	/**
	 * 保存电话等跟进列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveProcessInfo")
	@ResponseBody
	public Object saveProcessInfo(HttpServletRequest request, HttpServletResponse response, @RequestBody String mydomin) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = null;
		String error = null;
		PageInfo<List<PageData>> page = null;
		try {
			Gson gson = new Gson();
			Type type = new TypeToken<Map<Object, Object>>() {
			}.getType();
			Map<Object, Object> map1 = gson.fromJson(mydomin, type);
			PageData pd = this.getPageData();
			pd.put("createTime", DateUtil.getCurrentTime());
			pd.put("customerId", Double.valueOf(map1.get("customerId").toString()).intValue());// 关联客户id
			pd.put("nextTime", map1.get("nextTime")); // 下次跟进时间
			pd.put("content", map1.get("content"));// 跟进详情
			pd.put("type", map1.get("type")); // 跟进方式

			@SuppressWarnings("unchecked")
			List<String> userIds = (List<String>) map1.get("remind");
			String userId = "";
			for (int i = 0; i < userIds.size(); i++) {
				userId += userIds.get(i) + ",";
			}

			if (TextUtil.isNotNull(userId)) {
				pd.put("userId", userId.substring(0, userId.length() - 1));
			}
			// 保存联系人
			String linkmanName = "";
			@SuppressWarnings("unchecked")
			List<String> linkmanNames = (List<String>) map1.get("linkmanId");
			for (String linkmanName1 : linkmanNames) {
				linkmanName += linkmanName1 + ",";
			}
			if (TextUtil.isNotNull(linkmanName)) {
				linkmanName = linkmanName.substring(0, linkmanName.length() - 1);
				pd.put("linkmanName", linkmanName);
			}

			User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
			pd.put("swgwId", user.getUSER_ID());
			customerService.saveSwgwProcess(pd);

			for (int i = 0; i < userIds.size(); i++) {
				/*userId += userIds.get(i) + ",";*/
				// 保存通知
				PageData pd3 = new PageData();
				pd3.put("userId", userIds.get(i));
				pd3.put("type", 11);
				pd3.put("relateId", pd.get("id"));
				pd3.put("content", "商务顾问" + ((User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER)).getNAME() + "对客户" + "跟进了一条记录需要您阅读");
				pd3.put("flag", 0);
				customerService.saveNoticInfo(pd3);
				systemWebSocketHandler.sendMessageToUser(userIds.get(i), new TextMessage("notic:" + pd3.get("id")));
			}

			// 保存附件
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> list = (List<Map<String, Object>>) map1.get("files");
			for (Map<String, Object> map2 : list) {
				map2.put("expressId", pd.get("id"));
				map2.put("type", 16);
			}
			if (list.size() > 0) {
				expressService.saveAttach(list);
			}
			// 保存提醒人列表
			pd.put("type", 2);
			@SuppressWarnings("unchecked")
			List<String> list1 = (List<String>) map1.get("remind");
			for (String id : list1) {
				pd.put("saleProcessId", id);
				customerService.saveSaleProcessAbout(pd);
			}
			if (TextUtil.isNotNull(map1.get("nextTime").toString())) {
				// 写提醒
				PageData pd1 = new PageData();
				pd1.put("userId", user.getUSER_ID());
				pd1.put("time", map1.get("nextTime"));
				pd1.put("customerId", Double.valueOf(map1.get("customerId").toString()).intValue());
				pd1.put("remark", "你的客户" + map1.get("customerName").toString() + "需要你进行跟进1");
				orderService.saveCallInfo(pd1);
			}
			// 保存日志
			PageData pd1 = new PageData();
			pd1.put("userId", user.getUSER_ID());
			pd1.put("type", "跟进保存");
			pd1.put("saleCustomerId", Double.valueOf(map1.get("customerId").toString()).intValue());
			customerService.saveLogs(pd1);
			error = "00";
			msg = "保存成功";
		} catch (Exception e) {
			e.printStackTrace();
			error = "500";
			msg = "接口错误：" + e.getMessage();
		} finally {
			map.put("error", error);
			map.put("msg", msg);
			map.put("result", page);
		}
		return AppUtil.returnObject(new PageData(), map);
	}

	// 查看跟进详情
	@RequestMapping(value = "/showProcessDetail")
	@ResponseBody
	public Object showProcessDetail() throws Exception {
		String error = "";
		String msg = "";
		Map<String, Object> map = new HashMap<String, Object>();
		PageData result = null;
		try {
			PageData pd = this.getPageData();
			/*if (TextUtil.isNotNull(pd.getString("flag"))) {
				result = customerService.showProcessDetail1(pd);
			} else {*/
			result = customerService.showSwgwProcessDetail(pd);
			/*}*/
			error = "00";
			msg = "修改成功";
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

	/**
	 * 修改合同
	 * 
	 * @return
	 */
	@RequestMapping(value = "/doEdit1")
	@ResponseBody
	public Object doEdit1() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String error = "";
		String msg = "";
		String result = "";
		try {
			PageData pd = this.getPageData();
			String currenTime = DateUtil.getCurrentTime();
			pd.put("editTime", currenTime);// 修改时间

			if (TextUtil.isNotNull(pd.getString("orderSaleId"))) {
				customerService.updateOrderInfo(pd);
			}

			PageData pd1 = customerService.findById(pd);
			String qzdz = "";
			String hzdz = "";
			// 前字段值、后字段值
			if (TextUtil.isNotNull(pd.getString("companyAddress"))) {
				qzdz = pd1.getString("companyAddress");
				hzdz = pd.getString("companyAddress");
			}
			if (TextUtil.isNotNull(pd.getString("name"))) {
				qzdz = pd1.getString("name");
				hzdz = pd.getString("name");
			}
			if (TextUtil.isNotNull(pd.getString("bankAccountName"))) {
				qzdz = pd1.getString("bankAccountName");
				hzdz = pd.getString("bankAccountName");
			}
			if (TextUtil.isNotNull(pd.getString("bankNumber"))) {
				qzdz = pd1.getString("bankNumber");
				hzdz = pd.getString("bankNumber");
			}
			if (TextUtil.isNotNull(pd.getString("taxIdentificationNumber"))) {
				qzdz = pd1.getString("taxIdentificationNumber");
				hzdz = pd.getString("taxIdentificationNumber");
			}
			if (TextUtil.isNotNull(pd.getString("remark"))) {
				qzdz = pd1.getString("remark");
				hzdz = pd.getString("remark");
			}
			if (TextUtil.isNotNull(pd.getString("provinceName"))) {
				qzdz = pd1.getString("provinceName");
				hzdz = pd.getString("provinceName");
			}
			if (TextUtil.isNotNull(pd.getString("cityName"))) {
				qzdz = pd1.getString("cityName");
				hzdz = pd.getString("cityName");
			}
			if (TextUtil.isNotNull(pd.getString("mobilePhone"))) {
				qzdz = pd1.getString("mobilePhone");
				hzdz = pd.getString("mobilePhone");
			}
			if (TextUtil.isNotNull(pd.getString("bankName"))) {
				qzdz = pd1.getString("bankName");
				hzdz = pd.getString("bankName");
			}
			if (TextUtil.isNotNull(pd.getString("email"))) {
				qzdz = pd1.getString("email");
				hzdz = pd.getString("email");
			}
			if (TextUtil.isNotNull(pd.getString("fax"))) {
				qzdz = pd1.getString("fax");
				hzdz = pd.getString("fax");
			}
			if (TextUtil.isNotNull(pd.getString("status"))) {
				qzdz = pd1.getString("status");
				hzdz = pd.getString("status");
			}
			if (TextUtil.isNotNull(pd.getString("otherContract"))) {
				qzdz = pd1.getString("otherContract");
				hzdz = pd.getString("otherContract");
			}
			if (TextUtil.isNotNull(pd.getString("postCode"))) {
				qzdz = pd1.getString("postCode");
				hzdz = pd.getString("postCode");
			}
			if (TextUtil.isNotNull(pd.getString("contractSaleName"))) {
				qzdz = pd1.getString("contractSaleName");
				hzdz = pd.getString("contractSaleName");
			}
			if (TextUtil.isNotNull(pd.getString("orderSaleName"))) {
				qzdz = pd1.getString("orderSaleName");
				hzdz = pd.getString("orderSaleName");
			}

			User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
			PageData pd2 = new PageData();
			pd2.put("userId", user.getUSER_ID());
			pd2.put("qzdz", qzdz);
			pd2.put("hzdz", hzdz);
			pd2.put("saleCustomerId", pd.getString("id"));
			pd2.put("type", "编辑");
			pd2.put("flag", "2");
			customerService.saveLogs(pd2);
			customerService.saveOrUpdate(pd);

			error = "00";
			msg = "修改成功";
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

	/**
	 * 修改用户
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
		int id = Integer.parseInt(pd.getString("id"));
		if (id > 0) {
			pd.put("id", id);
			/*pd.put("mobilePhone", TextUtil.transNull(pd.getString("mobilePhone")).trim());*/
			pd.put("companyAddress", TextUtil.transNull(pd.getString("companyAddress")).trim());
			pd.put("name", TextUtil.transNull(pd.getString("name")).trim());
			pd.put("bankAccountName", TextUtil.transNull(pd.getString("bankAccountName")).trim());
			/*pd.put("bankName", TextUtil.transNull(pd.getString("bankName")).trim());*/
			pd.put("bankNumber", TextUtil.transNull(pd.getString("bankNumber")).trim());
			pd.put("taxIdentificationNumber", TextUtil.transNull(pd.getString("taxIdentificationNumber")).trim());
			pd.put("postCode", TextUtil.transNull(pd.getString("postCode")).trim());
			pd.put("remark", TextUtil.transNull(pd.getString("remark")).trim());
			pd.put("editTime", currenTime);// 修改时间
			/*pd.put("status", TextUtil.transNull(pd.getString("status")).trim());// 修改时间
			*/customerService.saveOrUpdate(pd); // 执行修改
			customerService.updateOrderInfo(pd);
			System.out.println("修改后的ID：" + pd.get("id") + "==========================" + pd);
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
				customerService.delete(arrayIds);
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
			if (customerService.findByMobilePhone(pd) != null) {
				errInfo = "error";
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo); // 返回结果
		return AppUtil.returnObject(new PageData(), map);
	}

	/**
	 * 判断客户是否已存在
	 * 
	 * @return
	 */
	@RequestMapping(value = "/hasName")
	@ResponseBody
	public Object hasName() {
		Map<String, Object> map = new HashMap<String, Object>();
		String error = "";
		String msg = "";
		PageData result = null;
		PageData pd = this.getPageData();
		try {
			if (customerService.findByName(pd) != null) {
				error = "01";
				msg = "有重复的客户名字";
			} else {
				if (TextUtil.isNotNull(pd.getString("flag"))) {
					error = "00";
					msg = "success";
				} else {
					if (customerService.findName(pd) != null) {
						error = "02";
						msg = "潜在客户已存在该客户";
					} else {
						error = "00";
						msg = "success";
					}
				}
			}
		} catch (Exception e) {
			error = "500";
			msg = "程序异常";
			logger.error(e.toString(), e);
		} finally {
			map.put("error", error);
			map.put("msg", msg);
			map.put("result", result);
		}// 返回结果
		return AppUtil.returnObject(new PageData(), map);
	}

	/**
	 * 展示产品列表
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/chooseCustomer")
	public ModelAndView chooseListProducts(Page page) throws Exception {
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
			List<PageData> cusList = customerService.list(page); // 列出客户列表
			mv.setViewName("business/customerManage/chooseCustomer_list");
			mv.addObject("cusList", cusList);
			mv.addObject("pd", pd);
			mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	/**
	 * 查看用户详情
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/view")
	public ModelAndView view() throws Exception {
		ModelAndView mv = this.getModelAndView();
		try {
			PageData pd = new PageData();
			pd = this.getPageData();
			pd = customerService.findById(pd); // 列出客户列表
			mv.setViewName("business/customerManage/customer_view");
			User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
			String userId = user.getUSER_ID();
			// 销售能看到自己的客户
			if (isRole(Const.ROLE_SALES_ELITE) || isRole(Const.ROLE_PHONE_SALES)) {
				if (!"1".equals(userId)) {
					pd.put("saleId", userId);
				}
			}

			if (isRole(Const.ROLE_MANAGER) || isRole(Const.ROLE_CUSTOMER_SERVICE)) {
				pd.put("serviceAndManager", 1);
			}
			mv.addObject("pd", pd);
			mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	/**
	 * 
	 * 获取客户列表详情的接口
	 */

	@RequestMapping(value = "/getCustomerInfo")
	@ResponseBody
	public Object getCustomerInfo() throws Exception {
		PageData pd = this.getPageData();
		pd.put("key", pd.getString("data[q]"));
		List<PageData> result = new ArrayList<PageData>();
		try {
			User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
			if ("2".equals(this.getGroup())) {
				pd.put("orderSaleId", user.getUSER_ID());
			}
			String pageNo = pd.getString("page");
			String pageSize = pd.getString("pageSize");
			int pNo = TextUtil.isNull(pageNo) ? 1 : Integer.parseInt(pageNo);
			int pSize = TextUtil.isNull(pageSize) ? 10 : Integer.parseInt(pageSize);
			PageHelper.startPage(pNo, pSize); // 核心分页代码
			result = customerService.listAll(pd);
		} catch (Exception e) {
			logger.error(e);
		}
		return JSONArray.fromObject(result);
	}

	/**
	 * 删除
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAttach")
	@ResponseBody
	public Object deleteAttach() throws Exception {
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return null;
		} // 校验权限
		String error = "";
		String msg = "";
		logBefore(logger, Jurisdiction.getUsername() + "批量删除customer");
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = this.getPageData();
		customerService.deleteAttach(pd);
		error = "00";
		msg = "删除成功";
		map.put("error", error);
		map.put("msg", msg);
		return AppUtil.returnObject(pd, map);
	}
}
