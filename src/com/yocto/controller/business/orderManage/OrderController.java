package com.yocto.controller.business.orderManage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.socket.TextMessage;

import com.github.pagehelper.PageInfo;
import com.yocto.controller.base.BaseController;
import com.yocto.entity.Page;
import com.yocto.entity.system.User;
import com.yocto.service.business.attachInfoManage.IAttachInfoService;
import com.yocto.service.business.contractManage.IContractService;
import com.yocto.service.business.customerManage.ICustomerService;
import com.yocto.service.business.orderManage.IHandoverService;
import com.yocto.service.business.orderManage.IOrderService;
import com.yocto.util.AppUtil;
import com.yocto.util.Const;
import com.yocto.util.DateUtil;
import com.yocto.util.Jurisdiction;
import com.yocto.util.PageData;
import com.yocto.util.RsmwUtil;
import com.yocto.util.TextUtil;
import com.yocto.websockect.SystemWebSocketHandler;

@Controller
@RequestMapping(value = "/order")
public class OrderController extends BaseController {
	String menuUrl = "order/list.do"; // 菜单地址(权限用)

	@Resource(name = "orderService")
	private IOrderService orderService;

	@Resource(name = "contractService")
	private IContractService contractService;

	@Resource(name = "attachService")
	private IAttachInfoService attachService;

	@Resource(name = "customerService")
	private ICustomerService customerService;

	@Resource(name = "handoverService")
	private IHandoverService handoverService;

	@Resource(name = "systemWebSocketHandler")
	private SystemWebSocketHandler systemWebSocketHandler;

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
			page.setPd(pd);
			List<PageData> list = orderService.list(page); // 列出客户列表
			boolean isKefu = isRole(Const.ROLE_CUSTOMER_SERVICE) || isRole(Const.ROLE_MANAGER); // 客服及以上 分配 结案 关闭 归档
			mv.addObject("isKefu", isKefu); // 客服及客服以上的角色
			boolean flag = isRole(Const.ROLE_OPERATION_DIRECTOR);
			User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
			if (flag) {
				if (!"1".equals(user.getUSER_ID())) {
					mv.addObject("isYunzuoAdmin", true);
				}
			} // 运作总监
			boolean flag1 = isRole(Const.ROLE_CUSTOMER_SERVICE);
			if (flag1) {
				if (!"1".equals(user.getUSER_ID())) {
					mv.addObject("isKefu1", true);
				}
			}
			List<PageData> listAllPage = orderService.listAll(pd);
			BigDecimal qkzje = new BigDecimal(0.0);
			BigDecimal hkzje = new BigDecimal(0.0);
			BigDecimal syzje = new BigDecimal(0.0);
			for (PageData cus : listAllPage) {
				qkzje = qkzje.add(new BigDecimal(Double.valueOf(cus.get("debtAmount").toString())));
				syzje = syzje.add(new BigDecimal(Double.valueOf(cus.get("syMoney").toString())));
				hkzje = hkzje.add(new BigDecimal(Double.valueOf(cus.get("totalMoney").toString())));
			}
			pd.put("qkzje", qkzje.setScale(2, BigDecimal.ROUND_HALF_UP));
			pd.put("hkzje", hkzje.setScale(2, BigDecimal.ROUND_HALF_UP));
			pd.put("syzje", syzje.setScale(2, BigDecimal.ROUND_HALF_UP));
			mv.addObject("orderList", list);
			mv.addObject("pd", pd);
			mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
			mv.setViewName("business/orderManage/order_list");
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
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
		if (!"1".equals(user.getUSER_ID())) {
			boolean isYunzuo = isRole(Const.ROLE_OPERATION);
			mv.addObject("isYunzuo", isYunzuo);
		}
		mv.addObject("pd", pd);
		mv.addObject("msg", "doAdd");
		mv.setViewName("business/orderManage/order_edit");
		return mv;
	}

	/**
	 * 新增案件信息
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
		// 先保存债务人信息
		orderService.saveDebtor(pd);
		// 非诉讼
		pd.put("status", 0);
		// 保存案件信息
		orderService.save(pd);
		/*if ("1".equals(type) || "2".equals(type)) {
			pd.put("handoverTime", DateUtil.getCurrentTime());
			handoverService.save(pd);
		}*/
		pd.put("editTime", DateUtil.getCurrentTime());
		// 将债务人或客户信息和联系人绑定并且和案件绑定
		if (TextUtil.isNotNull(pd.getString("linkId1"))) {
			pd.put("flag", 0);
			pd.put("linkId1", Arrays.asList(pd.getString("linkId1").split(",")));
			orderService.updateLinks(pd); // 联系人和债务人绑定
			orderService.saveLinksAndOrder(pd);
		}
		if (TextUtil.isNotNull(pd.getString("linkId2"))) {
			pd.put("flag", 1);
			pd.put("linkId2", Arrays.asList(pd.getString("linkId2").split(",")));
			orderService.updateLinks(pd);
			orderService.saveLinksAndOrder(pd);
		}
		if (TextUtil.isNotNull(pd.getString("linkId3"))) {
			pd.put("flag", 2);
			pd.put("linkId3", Arrays.asList(pd.getString("linkId3").split(",")));
			orderService.updateLinks(pd);
			orderService.saveLinksAndOrder(pd);
		}
		if (TextUtil.isNotNull(pd.getString("linkId4"))) {
			pd.put("flag", 3);
			pd.put("linkId4", Arrays.asList(pd.getString("linkId4").split(",")));
			orderService.updateLinks(pd);
			orderService.saveLinksAndOrder(pd);
		}
		// 保存相关附件
		if (TextUtil.isNotNull(pd.getString("fileSize")) && TextUtil.isNotNull(pd.getString("realPath")) && TextUtil.isNotNull(pd.getString("originalFilename"))
				&& TextUtil.isNotNull(pd.getString("url")) && TextUtil.isNotNull(pd.getString("uploadTime")) && TextUtil.isNotNull(pd.getString("uploader"))) {
			// 定义一个map装对象
			String[] fileSize = pd.getString("fileSize").split(",");
			String[] realPath = pd.getString("realPath").split(",");
			String[] originalFilename = pd.getString("originalFilename").split(",");
			String[] url = pd.getString("url").split(",");
			String[] uploadTime = pd.getString("uploadTime").split(",");
			String[] uploader = pd.getString("uploader").split(",");
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < fileSize.length; i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("fileSize", fileSize[i]);
				map.put("realPath", realPath[i]);
				map.put("originalFilename", originalFilename[i]);
				map.put("url", url[i]);
				map.put("uploadTime", uploadTime[i]);
				map.put("uploader", uploader[i]);
				map.put("type", 1);
				map.put("relateId", pd.get("orderId"));
				map.put("orderId", pd.get("orderId"));
				list.add(map);
			}
			if (list.size() > 0) {
				orderService.saveAttach(list);
			}
		}

		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
		PageData pd2 = new PageData();
		pd2.put("userId", user.getUSER_ID());
		pd2.put("saleCustomerId", pd.getString("id"));
		pd2.put("type", "保存");
		pd2.put("flag", "4");
		customerService.saveLogs(pd2);
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
		// if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
		// return null;
		// } // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		pd = orderService.findById(pd); // 根据ID读取
		String linkId1 = "";
		String linkId2 = "";
		String linkId3 = "";
		String linkId4 = "";
		@SuppressWarnings("unchecked")
		List<PageData> linkMan1List = (List<PageData>) pd.get("linkMan1");
		for (PageData linkMan1 : linkMan1List) {
			linkId1 = linkId1 + linkMan1.get("linkId1").toString() + ",";
		}
		if (TextUtil.isNotNull(linkId1)) {
			linkId1 = linkId1.substring(0, linkId1.length() - 1);
		}
		@SuppressWarnings("unchecked")
		List<PageData> linkMan2List = (List<PageData>) pd.get("linkMan2");
		for (PageData linkMan2 : linkMan2List) {
			linkId2 = linkId2 + linkMan2.get("linkId2").toString() + ",";
		}
		if (TextUtil.isNotNull(linkId2)) {
			linkId2 = linkId2.substring(0, linkId2.length() - 1);
		}
		@SuppressWarnings("unchecked")
		List<PageData> linkMan3List = (List<PageData>) pd.get("linkMan3");
		for (PageData linkMan3 : linkMan3List) {
			linkId3 = linkId3 + linkMan3.get("linkId3").toString() + ",";
		}
		if (TextUtil.isNotNull(linkId3)) {
			linkId3 = linkId3.substring(0, linkId3.length() - 1);
		}
		@SuppressWarnings("unchecked")
		List<PageData> linkMan4List = (List<PageData>) pd.get("linkMan4");
		for (PageData linkMan4 : linkMan4List) {
			linkId4 = linkId4 + linkMan4.get("linkId4").toString() + ",";
		}
		if (TextUtil.isNotNull(linkId4)) {
			linkId4 = linkId4.substring(0, linkId4.length() - 1);
		}
		pd.put("parentId", 0);
		List<PageData> sfList = orderService.showCountryData(pd);
		mv.addObject("sfList", sfList);
		pd.put("parentId", 1);
		List<PageData> sqList = orderService.showCountryData(pd);
		mv.addObject("sqList", sqList);
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
		if (!"1".equals(user.getUSER_ID())) {
			boolean isYunzuo = isRole(Const.ROLE_OPERATION);
			mv.addObject("isYunzuo", isYunzuo);
		}
		pd.put("linkId1", linkId1);
		pd.put("linkId2", linkId2);
		pd.put("linkId3", linkId3);
		pd.put("linkId4", linkId4);
		mv.addObject("pd", pd);
		mv.addObject("msg", "doEdit");
		mv.setViewName("business/orderManage/order_edit");
		return mv;
	}

	/**
	 * 修改案件
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/doEdit")
	public ModelAndView doEdit() throws Exception {
		String currenTime = DateUtil.getCurrentTime();
		logBefore(logger, Jurisdiction.getUsername() + "修改order");
		// if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha") || !Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
		// return null;
		// } // 校验权限 判断当前操作者有无客户管理查看权限
		ModelAndView mv = this.getModelAndView();

		PageData pd = this.getPageData();
		pd.put("editTime", currenTime);
		pd.put("orderId", pd.getString("id"));
		PageData pd2 = orderService.findById(pd);
		if (TextUtil.isNotNull(pd.getString("linkId1"))) {
			pd.put("linkId1", Arrays.asList(pd.getString("linkId1").split(",")));
		} else {
			pd.put("linkId1", new ArrayList<String>());
		}
		if (TextUtil.isNotNull(pd.getString("linkId2"))) {
			pd.put("linkId2", Arrays.asList(pd.getString("linkId2").split(",")));
		} else {
			pd.put("linkId2", new ArrayList<String>());
		}
		if (TextUtil.isNotNull(pd.getString("linkId3"))) {
			pd.put("linkId3", Arrays.asList(pd.getString("linkId3").split(",")));
		} else {
			pd.put("linkId3", new ArrayList<String>());
		}
		if (TextUtil.isNotNull(pd.getString("linkId4"))) {
			pd.put("linkId4", Arrays.asList(pd.getString("linkId4").split(",")));
		} else {
			pd.put("linkId4", new ArrayList<String>());
		}
		pd.put("flag", 0);
		if (TextUtil.isNotNull(pd.getString("origLinkId1"))) {
			// list1 list2 list3.addAll(list1) list3.addAll(list1) list1.removeAll(list3) list2.removeAll(list3)
			// 如果list1.size>0 删除案件与联系人的关系(根据案件id和联系人id) 如果list2.size()>0 新增orderService.updateLinks(pd);
			// orderService.saveLinksAndOrder(pd);
			List<String> list1 = Arrays.asList((pd.getString("origLinkId1").split(",")));
			list1 = toArrayList(list1);
			List<String> list2 = (List<String>) pd.get("linkId1");
			list2 = toArrayList(list2);
			List<String> list3 = new ArrayList<String>();
			list3.addAll(list1);
			list3.retainAll(list2);
			list1.removeAll(list3);
			list2.removeAll(list3);
			PageData pd1 = new PageData();
			pd1.put("flag", 0);
			if (list1.size() > 0) {
				pd1.put("orderId", pd.getString("id"));
				pd1.put("rId1", list1);
				orderService.delteLinksAndOrder(pd1);
				orderService.delteLinks(pd1);
			}
			if (list2.size() > 0) {
				pd1.put("debtorId", pd.getString("debtorId"));
				pd1.put("linkId1", list2);
				orderService.updateLinks(pd1);
				pd1.put("orderId", pd.getString("id"));
				orderService.saveLinksAndOrder(pd1);
			}
		} else {
			if (((List<String>) pd.get("linkId1")).size() > 0) {
				orderService.updateLinks(pd);
				orderService.saveLinksAndOrder(pd);
			}
		}
		pd.put("flag", 1);
		if (TextUtil.isNotNull(pd.getString("origLinkId2"))) {
			List<String> list1 = Arrays.asList((pd.getString("origLinkId2").split(",")));
			list1 = toArrayList(list1);
			List<String> list2 = (List<String>) pd.get("linkId2");
			list2 = toArrayList(list2);
			List<String> list3 = new ArrayList<String>();
			list3.addAll(list1);
			list3.retainAll(list2);
			list1.removeAll(list3);
			list2.removeAll(list3);
			PageData pd1 = new PageData();
			pd1.put("flag", 1);
			if (list1.size() > 0) {
				pd1.put("orderId", pd.getString("id"));
				pd1.put("rId2", list1);
				orderService.delteLinksAndOrder(pd1);
				orderService.delteLinks(pd1);
			}
			if (list2.size() > 0) {
				pd1.put("debtorId", pd.getString("debtorId"));
				pd1.put("linkId2", list2);
				orderService.updateLinks(pd1);
				pd1.put("orderId", pd.getString("id"));
				orderService.saveLinksAndOrder(pd1);
			}
		} else {
			if (((List<String>) pd.get("linkId2")).size() > 0) {
				orderService.updateLinks(pd);
				orderService.saveLinksAndOrder(pd);
			}
		}

		pd.put("flag", 2);
		if (TextUtil.isNotNull(pd.getString("origLinkId3"))) {
			List<String> list1 = Arrays.asList((pd.getString("origLinkId3").split(",")));
			list1 = toArrayList(list1);
			List<String> list2 = (List<String>) pd.get("linkId3");
			list2 = toArrayList(list2);
			List<String> list3 = new ArrayList<String>();
			list3.addAll(list1);
			list3.retainAll(list2);
			list1.removeAll(list3);
			list2.removeAll(list3);
			PageData pd1 = new PageData();
			pd1.put("flag", 2);
			if (list1.size() > 0) {
				pd1.put("orderId", pd.getString("id"));
				pd1.put("rId3", list1);
				orderService.delteLinksAndOrder(pd1);
			}
			if (list2.size() > 0) {
				pd1.put("customerId", pd.getString("customerId"));
				pd1.put("linkId3", list2);
				orderService.updateLinks(pd1);
				pd1.put("orderId", pd.getString("id"));
				orderService.saveLinksAndOrder(pd1);
			}
		} else {
			if (((List<String>) pd.get("linkId3")).size() > 0) {
				orderService.updateLinks(pd);
				orderService.saveLinksAndOrder(pd);
			}
		}
		pd.put("flag", 3);
		if (TextUtil.isNotNull(pd.getString("origLinkId4"))) {
			List<String> list1 = Arrays.asList((pd.getString("origLinkId4").split(",")));
			list1 = toArrayList(list1);
			List<String> list2 = (List<String>) pd.get("linkId4");
			list2 = toArrayList(list2);
			List<String> list3 = new ArrayList<String>();
			list3.addAll(list1);
			list3.retainAll(list2);
			list1.removeAll(list3);
			list2.removeAll(list3);
			PageData pd1 = new PageData();
			pd1.put("flag", 3);
			if (list1.size() > 0) {
				pd1.put("orderId", pd.getString("id"));
				pd1.put("rId4", list1);
				orderService.delteLinksAndOrder(pd1);
			}
			if (list2.size() > 0) {
				pd1.put("customerId", pd.getString("customerId"));
				pd1.put("linkId4", list2);
				orderService.updateLinks(pd1);
				pd1.put("orderId", pd.getString("id"));
				orderService.saveLinksAndOrder(pd1);
			}
		} else {
			if (((List<String>) pd.get("linkId4")).size() > 0) {
				orderService.updateLinks(pd);
				orderService.saveLinksAndOrder(pd);
			}
		}
		if (!pd.getString("origCustomerId").equals(pd.getString("customerId"))) {
			pd.put("flag", 2);
			if (((List<String>) pd.get("linkId3")).size() > 0) {
				orderService.updateLinks(pd);
			}
			pd.put("flag", 3);
			if (((List<String>) pd.get("linkId4")).size() > 0) {
				orderService.updateLinks(pd);
			}
		}
		// 修改案件
		orderService.updateDebtor(pd);
		orderService.update(pd);

		PageData order = orderService.findById(pd);
		List<PageData> attachs = (List<PageData>) order.get("attachs");
		List<String> list2 = new ArrayList<String>();
		for (PageData attach : attachs) {
			list2.add(attach.get("id").toString());
		}
		if (TextUtil.isNotNull(pd.getString("fileSize")) && TextUtil.isNotNull(pd.getString("realPath")) && TextUtil.isNotNull(pd.getString("originalFilename"))
				&& TextUtil.isNotNull(pd.getString("url")) && TextUtil.isNotNull(pd.getString("uploadTime")) && TextUtil.isNotNull(pd.getString("uploader"))) {
			// 定义一个map装对象
			String[] fileSize = pd.getString("fileSize").split(",");
			String[] realPath = pd.getString("realPath").split(",");
			String[] originalFilename = pd.getString("originalFilename").split(",");
			String[] url = pd.getString("url").split(",");
			String[] uploadTime = pd.getString("uploadTime").split(",");
			String[] uploader = pd.getString("uploader").split(",");
			String[] attachId = pd.getString("attachId").split(",");
			List<String> list1 = new ArrayList<String>();
			for (int i = 0; i < fileSize.length; i++) {
				PageData map = new PageData();
				map.put("fileSize", fileSize[i]);
				map.put("realPath", realPath[i]);
				map.put("originalFilename", originalFilename[i]);
				map.put("url", url[i]);
				map.put("uploadTime", uploadTime[i]);
				map.put("uploader", uploader[i]);
				map.put("relateId", pd.getString("id"));
				map.put("id", attachId[i]);
				map.put("type", 1);
				if (!"0".equals(attachId[i])) {
					list1.add(attachId[i]);
				} else {
					orderService.saveAttach1(map);
				}
			}
			list2.removeAll(list1);
			System.out.println(list2);
			if (list2.size() > 0) {
				contractService.deleteAttach(list2);
			}
		} else {
			if (list2.size() > 0) {
				contractService.deleteAttach(list2);
			}
		}

		PageData pd1 = orderService.findById(pd);
		if (null != pd1) {
			this.ggMethod(pd2, pd1, "orderNo", "");
			this.ggMethod(pd2, pd1, "cType", "佣金比例,固定金额");
			this.ggMethod(pd2, pd1, "type", "非诉讼,诉讼,仲裁");
			this.ggMethod(pd2, pd1, "orderPlacement", "");
			this.ggMethod(pd2, pd1, "debtAmount", "");
			this.ggMethod(pd2, pd1, "lastPaymentDate", "");
			this.ggMethod(pd2, pd1, "ageOfDebt", "");
			this.ggMethod(pd2, pd1, "debtType", "产品交易欠款,信用保险索赔欠款,资金借贷欠款");
			this.ggMethod(pd2, pd1, "commissionRate", "");
			this.ggMethod(pd2, pd1, "discount", "");
			this.ggMethod(pd2, pd1, "susongdiscount", "");
			this.ggMethod(pd2, pd1, "lawCommissionRate", "");
			this.ggMethod(pd2, pd1, "nonPayReasons", "");
			this.ggMethod(pd2, pd1, "collectHistory", "");
			this.ggMethod(pd2, pd1, "othersRequest", "");
			this.ggMethod(pd2, pd1, "isDispute", "债务无争议,债务有争议");
			this.ggMethod(pd2, pd1, "lastContactTime", "");
			this.ggMethod(pd2, pd1, "orederAttachType", "");
		}

		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	public void ggMethod(PageData pd, PageData pd1, String param, String flag) throws Exception {
		String qzdz = null != pd.get(param) ? pd.get(param).toString() : "";
		String hzdz = null != pd1.get(param) ? pd1.get(param).toString() : "";
		if (!(qzdz).equals(hzdz)) {
			if (TextUtil.isNotNull(flag)) {
				String[] value = flag.split(",");
				qzdz = value[Integer.valueOf(qzdz)];
				hzdz = value[Integer.valueOf(hzdz)];
			}
			User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
			PageData pd2 = new PageData();
			pd2.put("userId", user.getUSER_ID());
			pd2.put("qzdz", qzdz);
			pd2.put("hzdz", hzdz);
			pd2.put("saleCustomerId", pd.get("id"));
			pd2.put("type", "编辑");
			pd2.put("flag", "4");
			customerService.saveLogs(pd2);
		}
	}

	private List<String> toArrayList(List<String> list) {
		List<String> list1 = new ArrayList<String>();
		for (String s : list) {
			list1.add(s);
		}
		return list1;
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
				orderService.delete(arrayIds);
				error = "00";
				msg = "删除成功";

				for (String id : arrayIds) {
					User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
					PageData pd2 = new PageData();
					pd2.put("userId", user.getUSER_ID());
					pd2.put("saleCustomerId", id);
					pd2.put("type", "删除");
					pd2.put("flag", "4");
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
	 * 归档
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/doGuidang")
	@ResponseBody
	public Object doGuidang() throws Exception {
		String error = "";
		String msg = "";
		logBefore(logger, Jurisdiction.getUsername() + "批量删除customer");
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = this.getPageData();
		String ids = pd.getString("id");
		if (TextUtil.isNotNull(ids)) {
			if (orderService.updateGuiDang(pd) > 0) {
				error = "00";
				msg = "归档成功";
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
	 * 分配运作人员
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/doRun")
	@ResponseBody
	public Object doRun() throws Exception {
		String error = "";
		String msg = "";
		logBefore(logger, Jurisdiction.getUsername() + "批量删除customer");
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = this.getPageData();
		String ids = pd.getString("id");
		if (TextUtil.isNotNull(ids)) {
			pd.put("runnerId", TextUtil.isNotNull(pd.getString("runnerId")) ? pd.getString("runnerId") : "0");
			pd.put("runTime", DateUtil.getCurrentTime());
			pd.put("id", Arrays.asList(ids.split(",")));
			if (orderService.updateRun(pd) > 0) {
				error = "00";
				msg = "分配运作成功";
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
	 * 去结案、关闭\\归档页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goTotal")
	public ModelAndView goTotal() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.addObject("pd", pd);
		mv.addObject("msg", "doSave");
		mv.setViewName("business/orderManage/order_total"); // 去结案或关闭 归档的共有页面
		return mv;
	}

	/**
	 * 新增案件信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/doSave")
	public ModelAndView doSave() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "新增");
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String[] orderIds = pd.getString("orderId").split(",");
		for (String orderId : orderIds) {
			pd.put("orderId", orderId);
			orderService.saveTotal(pd);
			if (TextUtil.isNotNull(pd.getString("fileSize")) && TextUtil.isNotNull(pd.getString("realPath")) && TextUtil.isNotNull(pd.getString("originalFilename"))
					&& TextUtil.isNotNull(pd.getString("url")) && TextUtil.isNotNull(pd.getString("uploadTime")) && TextUtil.isNotNull(pd.getString("uploader"))) {
				// 定义一个map装对象
				String[] fileSize = pd.getString("fileSize").split(",");
				String[] realPath = pd.getString("realPath").split(",");
				String[] originalFilename = pd.getString("originalFilename").split(",");
				String[] url = pd.getString("url").split(",");
				String[] uploadTime = pd.getString("uploadTime").split(",");
				String[] uploader = pd.getString("uploader").split(",");
				// List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				for (int i = 0; i < fileSize.length; i++) {
					PageData map = new PageData();
					map.put("fileSize", fileSize[i]);
					map.put("realPath", realPath[i]);
					map.put("originalFilename", originalFilename[i]);
					map.put("url", url[i]);
					map.put("uploadTime", uploadTime[i]);
					map.put("uploader", uploader[i]);
					map.put("orderId", pd.get("orderId"));
					map.put("relateId", pd.get("totalId"));
					int type = 0; // 附件类型
					if ("5".equals(pd.getString("type"))) {
						type = 6;
					} else if ("6".equals(pd.getString("type"))) {
						type = 7;
					} else if ("8".equals(pd.getString("type"))) {
						type = 9;
					}
					map.put("type", type);
					attachService.save(map);
				}
			}
			orderService.updateTotal(pd);
		}
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 查看关闭或者结案详情
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
			pd = orderService.findTotalById(pd); // 列出客户列表
			mv.setViewName("business/orderManage/order_totalView");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	/**
	 * 判断订单号是否存在
	 * 
	 * @return
	 */
	@RequestMapping(value = "/hasOrderNo")
	@ResponseBody
	public Object hasName() {
		Map<String, String> map = new HashMap<String, String>();
		String errInfo = "success";
		PageData pd = this.getPageData();
		try {
			if (orderService.findByOrderNo(pd) != null) {
				errInfo = "error";
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
	@RequestMapping(value = "/viewOrder")
	public ModelAndView viewOrder() throws Exception {
		ModelAndView mv = this.getModelAndView();
		try {
			PageData pd = new PageData();
			pd = this.getPageData();
			pd = orderService.findById(pd); // 列出客户列表
			User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
			String userId = user.getUSER_ID();
			boolean flag = isRole(Const.ROLE_SALES_DIRECTOR) || isRole(Const.ROLE_SALES_ELITE) || isRole(Const.ROLE_PHONE_SALES_DIRECTOR) || isRole(Const.ROLE_PHONE_SALES);
			if (flag) {
				if (!"1".equals(userId)) {
					pd.put("types", 1);
				}
			}

			boolean flag1 = isRole(Const.ROLE_OPERATION_DIRECTOR) || isRole(Const.ROLE_OPERATION);
			if (flag1) {
				if (!"1".equals(userId)) {
					pd.put("types", 2);
				}
			}

			if (isRole(Const.ROLE_MANAGER)) {
				pd.put("flags", 1);
			}

			mv.setViewName("business/orderManage/order_view");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	/**
	 * 展示案件列表
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/chooseOrder")
	public ModelAndView chooseOrder(Page page) throws Exception {
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
			if (TextUtil.isNotNull(pd.getString("status")))
				pd.put("status", Arrays.asList(pd.getString("status").split(",")));
			if (TextUtil.isNotNull(pd.getString("orderIds")))
				pd.put("orderIds", Arrays.asList(pd.getString("orderIds").split(",")));
			List<PageData> cusList = orderService.list(page); // 列出客户列表
			mv.setViewName("business/orderManage/chooseOrder_list");
			mv.addObject("cusList", cusList);
			mv.addObject("pd", pd);
			mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	/**
	 * 获取当前案件的列表 type=1\2\3(案件跟进列表)\4(欠款催收)
	 * 
	 * @throws Exception
	 */

	@RequestMapping(value = "showAll")
	@ResponseBody
	public Object showAll() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<PageData> result = null;
		String msg = null;
		String error = null;
		try {
			PageData pd = this.getPageData();
			if (TextUtil.isNotNull(pd.getString("orderId")) && TextUtil.isNotNull(pd.getString("type"))) {
				if ("1".equals(pd.getString("type"))) {
					User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
					String userId = user.getUSER_ID();
					if ("1".equals(userId)) {
						pd.put("isOperationDirector", "1");
					} else {
						if (isRole(Const.ROLE_OPERATION)) {
							pd.put("isOperation", "1");
							pd.put("isYunzuo", userId);
							System.out.println("是运作");
						} else if (isRole(Const.ROLE_OPERATION_DIRECTOR) /*|| isRole(Const.ROLE_CUSTOMER_SERVICE)*/|| isRole(Const.ROLE_MANAGER)) {
							pd.put("isOperationDirector", "1");
							System.out.println("是运作总监");
						} else if (isRole(Const.ROLE_CUSTOMER_SERVICE)) {
							pd.put("isOperation", "1");
							pd.put("isKefu", userId);
							System.out.println("是运作");
						}
					}
					result = orderService.findReportInfo(pd);
					error = "00";
					msg = "获取列表成功";
				} else if ("2".equals(pd.getString("type"))) {
					result = orderService.findPayment(pd);
					error = "00";
					msg = "获取列表成功";
				} else if ("3".equals(pd.getString("type"))) {
					result = orderService.findProcessInfo(pd);
					error = "00";
					msg = "获取列表成功";
				} else if ("4".equals(pd.getString("type"))) {
					result = orderService.finddebtor(pd);
					error = "00";
					msg = "获取列表成功";
				} else if ("5".equals(pd.getString("type"))) {
					result = orderService.findHkjh(pd);
					error = "00";
					msg = "获取列表成功";
				} else if ("6".equals(pd.getString("type"))) {
					result = orderService.findAjjj(pd);
					error = "00";
					msg = "获取列表成功";
				} else {
					error = "01";
					msg = "参数异常";
				}
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
	 * 
	 * 保存客户
	 * 
	 * 
	 * 
	 */
	@RequestMapping(value = "/saveCustomer")
	@ResponseBody
	public Object saveCustomer() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String error = "";
		String msg = "";
		String result = "";
		try {
			PageData pd = this.getPageData();
			if (orderService.saveDebtorAndCustomer(pd) > 0) {
				result = pd.get("customerId").toString();
				error = "00";
				msg = "保存成功";
			}
		} catch (Exception e) {
			error = "500";
			msg = "程序异常";
		} finally {
			map.put("error", error);
			map.put("msg", msg);
			map.put("result", result);
		}
		return AppUtil.returnObject(new PageData(), map);
	}

	/**
	 * 
	 * 保存客户
	 * 
	 * 
	 * 
	 */
	@RequestMapping(value = "/saveProcess")
	@ResponseBody
	public Object saveProcess() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String error = "";
		String msg = "";
		String result = "";
		try {
			PageData pd = this.getPageData();
			if (TextUtil.isNotNull(pd.getString("callId"))) {
				orderService.updateProcess(pd);
				pd.put("callUuid", pd.getString("callId"));
				PageData pd1 = customerService.findYzProcessById(pd);
				pd.put("relateId", pd1.get("id"));
				error = "00";
				msg = "保存成功";
			} else {
				if (orderService.saveProcess(pd) > 0) {
					error = "00";
					msg = "保存成功";
				}
			}
			if (TextUtil.isNotNull(pd.getString("xcgjTime"))) {
				User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
				PageData pd1 = new PageData();
				pd1.put("userId", user.getUSER_ID());
				pd1.put("time", pd.getString("xcgjTime"));
				pd1.put("orderId", pd.getString("orderId"));
				pd1.put("processId", pd.get("relateId"));
				pd1.put("remark", "债务人(公司)" + pd.getString("debtorName") + "需要您进行跟进");
				orderService.saveCallInfo(pd1);
			}
			if (null != pd.get("relateId")) {
				// 保存附件
				if (TextUtil.isNotNull(pd.getString("fileSize")) && TextUtil.isNotNull(pd.getString("realPath")) && TextUtil.isNotNull(pd.getString("originalFilename"))
						&& TextUtil.isNotNull(pd.getString("url")) && TextUtil.isNotNull(pd.getString("uploadTime")) && TextUtil.isNotNull(pd.getString("uploader"))) {
					// 定义一个map装对象
					String[] fileSize = pd.getString("fileSize").split(",");
					String[] realPath = pd.getString("realPath").split(",");
					String[] originalFilename = pd.getString("originalFilename").split(",");
					String[] url = pd.getString("url").split(",");
					String[] uploadTime = pd.getString("uploadTime").split(",");
					String[] uploader = pd.getString("uploader").split(",");
					List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
					for (int i = 0; i < fileSize.length; i++) {
						Map<String, Object> map1 = new HashMap<String, Object>();
						map1.put("fileSize", fileSize[i]);
						map1.put("realPath", realPath[i]);
						map1.put("originalFilename", originalFilename[i]);
						map1.put("url", url[i]);
						map1.put("uploadTime", uploadTime[i]);
						map1.put("uploader", uploader[i]);
						map1.put("type", 10);
						map1.put("orderId", pd.get("orderId"));
						map1.put("relateId", pd.get("relateId"));
						list.add(map1);
					}
					if (list.size() > 0) {
						orderService.saveAttach(list);
					}
				}
			}
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
	 * 
	 * 打电话
	 * 
	 */
	@RequestMapping(value = "/call")
	@ResponseBody
	public Object call() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = null;
		String error = null;
		PageInfo<List<PageData>> page = null;
		try {
			PageData pd = this.getPageData();
			String mobilePhone = pd.getString("mobilePhone");

			// 业务逻辑
			// 1.拨打电话
			if (TextUtil.isNotNull(mobilePhone)) {
				User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
				String saleId = user.getUSER_ID();
				pd.put("runnerId", saleId);
				String srcGateway = "";
				String srcAccessNumber = "";
				if ("808".equals(replaceName(user.getPHONE()).trim())) {
					srcGateway = "xswg";
					srcAccessNumber = "65366628";
				}
				if ("812".equals(replaceName(user.getPHONE()).trim())) {
					srcGateway = "xswg";
					srcAccessNumber = "65366608";
				}
				if ("815".equals(replaceName(user.getPHONE()).trim())) {
					srcGateway = "xswg";
					srcAccessNumber = "65366608";
				}
				String callUuid = RsmwUtil.call(7200, replaceName(user.getPHONE()), "", "", "", replaceName(mobilePhone), srcGateway, srcAccessNumber, "");
				if (TextUtil.isNotNull(callUuid)) {
					pd.put("callUuid", callUuid);
					pd.put("link", "电话");
					/*pd.put("createTime", DateUtil.getCurrentTime());*/
					orderService.saveProcess(pd);
					error = "00";
					msg = "拨打电话成功";
				} else {
					error = "02";
					msg = "拨打电话失败";
				}
			} else {
				error = "01";
				msg = "缺少参数";
			}
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

	/**
	 * 去"-"
	 */

	private String replaceName(String name) {
		if (name.indexOf("-") > 0) {
			name = name.split("-")[0].trim() + name.split("-")[1].trim();
		}
		return name;
	}

	/**
	 * 
	 * 保存附件
	 * 
	 * 
	 * 
	 */
	@RequestMapping(value = "/saveProductAttach")
	@ResponseBody
	public Object saveProductAttach() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String error = "";
		String msg = "";
		String result = "";
		try {
			PageData pd = this.getPageData();
			if (TextUtil.isNotNull(pd.getString("orderId")) || TextUtil.isNotNull(pd.getString("customerId")) || TextUtil.isNotNull(pd.getString("contractId"))) {
				String orderOrCustomerId;
				if (TextUtil.isNotNull(pd.getString("orderId"))) {
					pd.put("type", 1);
					orderOrCustomerId = pd.getString("orderId");
				} else if (TextUtil.isNotNull(pd.getString("customerId"))) {
					pd.put("type", 14);
					orderOrCustomerId = pd.getString("customerId");
				} else {
					pd.put("type", 0);
					orderOrCustomerId = pd.getString("contractId");
				}
				pd.put("orderOrCustomerId", orderOrCustomerId);
				orderService.deleteAttachs(pd);
				// 保存附件
				if (TextUtil.isNotNull(pd.getString("fileSize")) && TextUtil.isNotNull(pd.getString("realPath")) && TextUtil.isNotNull(pd.getString("originalFilename"))
						&& TextUtil.isNotNull(pd.getString("url")) && TextUtil.isNotNull(pd.getString("uploadTime")) && TextUtil.isNotNull(pd.getString("uploader"))) {
					// 定义一个map装对象
					String[] fileSize = pd.getString("fileSize").split(",");
					String[] realPath = pd.getString("realPath").split(",");
					String[] originalFilename = pd.getString("originalFilename").split(",");
					String[] url = pd.getString("url").split(",");
					String[] uploadTime = pd.getString("uploadTime").split(",");
					String[] uploader = pd.getString("uploader").split(",");
					List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
					for (int i = 0; i < fileSize.length; i++) {
						Map<String, Object> map1 = new HashMap<String, Object>();
						map1.put("fileSize", fileSize[i]);
						map1.put("realPath", realPath[i]);
						map1.put("originalFilename", originalFilename[i]);
						map1.put("url", url[i]);
						map1.put("uploadTime", uploadTime[i]);
						map1.put("uploader", uploader[i]);
						map1.put("type", pd.get("type"));
						map1.put("orderId", pd.get("orderId"));
						map1.put("relateId", orderOrCustomerId);
						list.add(map1);
					}
					if (list.size() > 0) {
						orderService.saveAttach(list);
					}
				}
				error = "00";
				msg = "保存成功";
			}
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
	 * 
	 * 保存客户
	 * 
	 * 
	 * 
	 */
	@RequestMapping(value = "/updateProcess")
	@ResponseBody
	public Object updateProcess() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String error = "";
		String msg = "";
		String result = "";
		try {
			PageData pd = this.getPageData();
			if (orderService.updateProcess(pd) > 0) {
				if (TextUtil.isNotNull(pd.getString("xcgjTime"))) {
					PageData pd1 = new PageData();
					pd1.put("time", pd.getString("xcgjTime"));
					pd1.put("id", pd.getString("id"));
					orderService.updateCallInfo1(pd1);
				}
				// orderService.updateGjStatus(pd);
				List<PageData> order = orderService.findProcessInfo(pd);
				if (order.size() > 0) {
					@SuppressWarnings("unchecked")
					List<PageData> attachs = (List<PageData>) order.get(0).get("attachs");
					List<String> list2 = new ArrayList<String>();
					for (PageData attach : attachs) {
						list2.add(attach.get("id").toString());
					}
					if (TextUtil.isNotNull(pd.getString("fileSize")) && TextUtil.isNotNull(pd.getString("realPath")) && TextUtil.isNotNull(pd.getString("originalFilename"))
							&& TextUtil.isNotNull(pd.getString("url")) && TextUtil.isNotNull(pd.getString("uploadTime")) && TextUtil.isNotNull(pd.getString("uploader"))) {
						// 定义一个map装对象
						String[] fileSize = pd.getString("fileSize").split(",");
						String[] realPath = pd.getString("realPath").split(",");
						String[] originalFilename = pd.getString("originalFilename").split(",");
						String[] url = pd.getString("url").split(",");
						String[] uploadTime = pd.getString("uploadTime").split(",");
						String[] uploader = pd.getString("uploader").split(",");
						String[] attachId = pd.getString("attachId").split(",");
						List<String> list1 = new ArrayList<String>();
						for (int i = 0; i < fileSize.length; i++) {
							PageData map1 = new PageData();
							map1.put("fileSize", fileSize[i]);
							map1.put("realPath", realPath[i]);
							map1.put("originalFilename", originalFilename[i]);
							map1.put("url", url[i]);
							map1.put("uploadTime", uploadTime[i]);
							map1.put("uploader", uploader[i]);
							map1.put("orderId", pd.get("orderId"));
							map1.put("relateId", pd.getString("id"));
							map1.put("id", attachId[i]);
							map1.put("type", 10);
							if (!"0".equals(attachId[i])) {
								list1.add(attachId[i]);
							} else {
								attachService.save(map1);
							}
						}
						list2.removeAll(list1);
						System.out.println(list2);
						if (list2.size() > 0) {
							contractService.deleteAttach(list2);
						}
					} else {
						if (list2.size() > 0) {
							contractService.deleteAttach(list2);
						}
					}
				}
				error = "00";
				msg = "保存成功";
			}
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
	 * 获取案件概览
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getOrderDetail")
	@ResponseBody
	public Object getOrderDetail() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData result = null;
		String msg = null;
		String error = null;
		try {
			PageData pd = this.getPageData();
			if (TextUtil.isNotNull(pd.getString("id"))) {
				User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
				String userId = user.getUSER_ID();
				result = orderService.findById(pd);
				if (null != result) {
					@SuppressWarnings("unchecked")
					List<PageData> list = (List<PageData>) result.get("callInfo");
					List<PageData> list1 = new ArrayList<PageData>();
					for (PageData pd1 : list) {
						if (userId.equals(pd1.get("userId").toString())) {
							list1.add(pd1);
						}
					}
					result.remove("callInfo");
					result.put("callInfo", list1);
				}
			} else {
				error = "01";
				msg = "缺少参数";
			}
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
	 * 获取国家、省、市数据
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCountryData")
	@ResponseBody
	public Object getCountryData() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		// 找出国家parentId=0
		PageData pd = this.getPageData();
		pd.put("parentId", 0);
		List<PageData> sfList = orderService.showCountryData(pd);
		pd.put("parentId", 1);
		List<PageData> sqList = orderService.showCountryData(pd);
		// 根据市区找区县
		List<String> qxId = new ArrayList<String>();
		for (int i = 0; i < sqList.size(); i++) {
			qxId.add(sqList.get(i).get("id").toString());
		}
		List<PageData> list = orderService.showAllArea(qxId);
		// map.put("国家", gjList);
		map.put("省份", sfList);
		map.put("市区", sqList);
		map.put("区", list);
		return AppUtil.returnObject(new PageData(), map);
	}

	/**
	 * 新增债务人联系人
	 */
	@RequestMapping(value = "saveLinkManInfo")
	@ResponseBody
	public Object saveLinkManInfo() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData result = null;
		String msg = null;
		String error = null;
		try {
			PageData pd = this.getPageData();
			if (TextUtil.isNotNull(pd.getString("debtorId"))) {
				orderService.saveLinkManInfo(pd);
				error = "00";
				msg = "保存成功";
			} else {
				error = "01";
				msg = "缺少参数";
			}
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
	 * 债务人修改
	 */
	@RequestMapping(value = "updateDebtor")
	@ResponseBody
	public Object updateDebtor() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData result = null;
		String msg = null;
		String error = null;
		try {
			PageData pd = this.getPageData();
			if (TextUtil.isNotNull(pd.getString("debtorId"))) {
				pd.put("editTime", DateUtil.getCurrentTime());
				orderService.updateDebtor1(pd);
				error = "00";
				msg = "修改成功";
			} else {
				error = "01";
				msg = "缺少参数";
			}
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
	 * 获取本月部分数据(运作、运作总监能新增)
	 */
	@RequestMapping(value = "getMonthData")
	@ResponseBody
	public Object updateGjStatus() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData result = new PageData();
		String msg = null;
		String error = null;
		try {
			PageData pd = this.getPageData();
			User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
			String userId = user.getUSER_ID();
			if (isRole(Const.ROLE_OPERATION)) {
				pd.put("runnerId", userId);
			} else {
				pd.put("isRunDirector", 1);
			}
			if (TextUtil.isNotNull(pd.getString("id"))) {
				result = orderService.findMonthDataById(pd);
			} else {
				pd.put("xydd", "1");
				List<PageData> list1 = orderService.listAll(pd);
				int xyDd = list1.size();
				result.put("xydd", xyDd); // 现有订单
				BigDecimal qkzje1 = new BigDecimal(0.0);
				for (PageData cus : list1) {
					qkzje1 = qkzje1.add(new BigDecimal(Double.valueOf(cus.get("debtAmount").toString())));
				}
				result.put("xyje", qkzje1.setScale(2, BigDecimal.ROUND_HALF_UP)); // 现有金额
				pd.put("currentMonth", DateUtil.getCurrentMonth());
				List<PageData> list2 = orderService.listAll(pd);
				int bywtDd = list2.size();
				result.put("wtdd", bywtDd); // 本月委托订单(本月新增)
				BigDecimal qkzje = new BigDecimal(0.0);
				for (PageData cus : list2) {
					qkzje = qkzje.add(new BigDecimal(Double.valueOf(cus.get("debtAmount").toString())));
				}
				result.put("bywtje", qkzje.setScale(2, BigDecimal.ROUND_HALF_UP)); // 本月委托金额
				pd.put("currentMonth", DateUtil.getCurrentYM());
				// 结案
				pd.put("status1", 1);
				List<PageData> list3 = orderService.listAll(pd);
				result.put("byja", list3.size());
				// 关闭
				pd.put("status1", 2);
				List<PageData> list4 = orderService.listAll(pd);
				result.put("bygb", list4.size());
				// 获取本月数据
				List<PageData> list5 = orderService.getMonthData(pd);
				String rq = "";
				String zqr = "";
				String zwr = "";
				String hkje = "";
				String yjsr = "";
				String jhnwzd = "";
				for (PageData pd1 : list5) {
					rq += pd1.get("currentTime").toString() + "@bzf@";
					zqr += pd1.get("custoemrName").toString() + "@bzf@";
					zwr += pd1.get("debtorName").toString() + "@bzf@";
					hkje += pd1.get("currentMoney").toString() + "@bzf@";
					yjsr += pd1.get("yjje").toString() + "@bzf@";
					jhnwzd += pd1.get("jhnw").toString() + "@bzf@";
				}
				result.put("rq", rq);
				result.put("zqr", zqr);
				result.put("zwr", zwr);
				result.put("hkje", hkje);
				result.put("yjsr", yjsr);
				result.put("jhnwzd", jhnwzd);
				// 获取下月回款预估数据
				pd.put("issxy", "xy");
				List<PageData> list6 = orderService.getXyhklist(pd);
				String yjrq = "";
				String yjzqr = "";
				String yjzwr = "";
				String yjhkje = "";
				String yjyjsr = "";
				for (PageData pd1 : list6) {
					yjrq += pd1.get("planTime").toString() + "@bzf@";
					yjzqr += pd1.get("custoemrName").toString() + "@bzf@";
					yjzwr += pd1.get("debtorName").toString() + "@bzf@";
					yjhkje += pd1.get("money").toString() + "@bzf@";
					yjyjsr += pd1.get("commissionaMount").toString() + "@bzf@";
				}
				result.put("yjrq", yjrq);
				result.put("yjzqr", yjzqr);
				result.put("yjzwr", yjzwr);
				result.put("yjhkje", yjhkje);
				result.put("yjyjsr", yjyjsr);
			}
			// 获取上月获取预估金额
			pd.put("issxy", "sy");
			BigDecimal syhkygje = new BigDecimal(0.0);
			List<PageData> list7 = orderService.getXyhklist(pd);
			for (PageData cus : list7) {
				syhkygje = syhkygje.add(new BigDecimal(Double.valueOf(cus.get("money").toString())));
			}
			result.put("syhkygje", syhkygje.setScale(2, BigDecimal.ROUND_HALF_UP));
			error = "00";
			msg = "获取数据成功";
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
	 * 新增或修改月报
	 */
	@RequestMapping(value = "/saveMonthData")
	@ResponseBody
	public Object saveMonthData() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData result = null;
		String msg = null;
		String error = null;
		try {
			PageData pd = this.getPageData();
			if (TextUtil.isNotNull(pd.getString("status"))) {
				User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
				String userId = user.getUSER_ID();
				if (isRole(Const.ROLE_OPERATION) || isRole(Const.ROLE_OPERATION_DIRECTOR)) {
					if (!"1".equals(userId)) {
						pd.put("userId", userId);
					}
				}

				orderService.saveorUpdateMonthData(pd);

				if ("1".equals(pd.getString("status"))) {

					PageData pd4 = new PageData();
					// pd4.put("userId", pd.getString("userId"));
					pd4.put("type", 10);
					pd4.put("relateId", pd.get("id"));
					pd4.put("content", "运作" + pd.getString("name") + "写了一份月报请查看");
					pd4.put("flag", 0);
					customerService.saveNoticInfo(pd4);
					systemWebSocketHandler.sendMessageToUser(pd.getString("userId"), new TextMessage("notic:" + pd4.get("id")));
				}

				error = "00";
				msg = "保存成功";
			} else {
				error = "01";
				msg = "缺少参数";
			}
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
	 * 获取运作月报列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/listMonthReport")
	public ModelAndView listMonthReport(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		try {
			PageData pd = this.getPageData();
			String keywords = pd.getString("keywords"); // 关键词检索条件
			if (TextUtil.isNotNull(keywords)) {
				pd.put("keywords", keywords.trim());
			}
			/*String createTimeStart = pd.getString("createTimeStart"); // 开始时间
			String createTimeEnd = pd.getString("createTimeEnd"); // 结束时间
			if (TextUtil.isNotNull(createTimeStart)) {
				pd.put("createTimeStart", createTimeStart + " 00:00:00");
			}
			if (TextUtil.isNotNull(createTimeEnd)) {
				pd.put("createTimeEnd", createTimeEnd + " 23:59:59");
			}*/
			User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
			String userId = user.getUSER_ID();
			if (isRole(Const.ROLE_OPERATION) || isRole(Const.ROLE_OPERATION_DIRECTOR)) {
				if (!"1".equals(userId)) {
					pd.put("userId", userId);
					pd.put("time", DateUtil.getCurrentYM());
					PageData obj = orderService.findMonthDataById(pd);
					if (null == obj) {
						pd.put("flag", 1);
					}
				}
			}

			page.setPd(pd);
			List<PageData> list = orderService.listMonthReport(page); // 列出客户列表
			mv.addObject("orderList", list);
			mv.addObject("pd", pd);
			mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
			mv.setViewName("business/orderManage/monthReport_list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	/**
	 * 新增债务人联系人
	 */
	@RequestMapping(value = "saveCallInfo")
	@ResponseBody
	public Object saveCallInfo() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData result = null;
		String msg = null;
		String error = null;
		try {
			PageData pd = this.getPageData();
			User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
			String userId = user.getUSER_ID();
			pd.put("userId", userId);
			if (TextUtil.isNotNull(pd.getString("customerName"))) {
				pd.put("remark", "你的客户" + pd.getString("customerName") + "需要你进行跟进," + pd.getString("remark") + "0");
			}
			orderService.saveCallInfo(pd);
			error = "00";
			msg = "保存成功";
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

	@RequestMapping(value = "updateCallInfo")
	@ResponseBody
	public Object updateCallInfo() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData result = null;
		String msg = null;
		String error = null;
		try {
			PageData pd = this.getPageData();
			if (TextUtil.isNotNull(pd.getString("id"))) {
				orderService.updateCallInfo(pd);
				error = "00";
				msg = "修改成功";
			} else {
				error = "01";
				msg = "缺少参数";
			}
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

	// =================================销售=====================
	@RequestMapping(value = "/listSaleOrder")
	public ModelAndView listSaleOrder(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		try {
			PageData pd = this.getPageData();
			String keywords = pd.getString("keywords"); // 关键词检索条件
			if (TextUtil.isNotNull(keywords)) {
				pd.put("keywords", keywords.trim());
			}
			String createTimeStart = pd.getString("startTime"); // 开始时间
			String createTimeEnd = pd.getString("endTime"); // 结束时间
			if (TextUtil.isNotNull(createTimeStart)) {
				pd.put("startTime", createTimeStart + " 00:00:00");
			}
			if (TextUtil.isNotNull(createTimeEnd)) {
				pd.put("endTime", createTimeEnd + " 23:59:59");
			}
			page.setPd(pd);
			/*boolean flag = isRole(Const.ROLE_PHONE_SALES) || isRole(Const.ROLE_SALES_ELITE);
			User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
			if (flag) {
				if (!"1".equals(user.getUSER_ID())) {
					mv.addObject("saleId", "yes");
					pd.put("saleId", user.getUSER_ID());
				}
			}*/
			User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
			if ("2".equals(this.getGroup())) {
				pd.put("saleId", user.getUSER_ID());
			}
			List<PageData> list = orderService.list(page); // 列出客户列表
			List<PageData> listAllPage = orderService.listAll(pd);
			BigDecimal qkzje = new BigDecimal(0.0);
			BigDecimal hkzje = new BigDecimal(0.0);
			BigDecimal syzje = new BigDecimal(0.0);
			for (PageData cus : listAllPage) {
				qkzje = qkzje.add(new BigDecimal(Double.valueOf(cus.get("debtAmount").toString())));
				syzje = syzje.add(new BigDecimal(Double.valueOf(cus.get("syMoney").toString())));
				hkzje = hkzje.add(new BigDecimal(Double.valueOf(cus.get("totalMoney").toString())));
			}
			pd.put("qkzje", qkzje.setScale(2, BigDecimal.ROUND_HALF_UP));
			pd.put("hkzje", hkzje.setScale(2, BigDecimal.ROUND_HALF_UP));
			pd.put("syzje", syzje.setScale(2, BigDecimal.ROUND_HALF_UP));
			mv.addObject("orderList", list);
			mv.addObject("pd", pd);
			mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
			mv.setViewName("business/orderManage/orderSale_list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
}
