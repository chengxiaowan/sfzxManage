package com.yocto.controller.business.exprssManage;

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

import com.yocto.controller.base.BaseController;
import com.yocto.entity.Page;
import com.yocto.entity.system.User;
import com.yocto.service.business.expressManage.IExpressService;
import com.yocto.util.AppUtil;
import com.yocto.util.Const;
import com.yocto.util.DateUtil;
import com.yocto.util.Jurisdiction;
import com.yocto.util.PageData;
import com.yocto.util.TextUtil;

@Controller
@RequestMapping(value = "express")
public class ExpressController extends BaseController {

	String menuUrl = "express/list.do"; // 菜单地址(权限用)

	@Resource(name = "expressService")
	private IExpressService expressService;

	/**
	 * 获取快递列表
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
			// 判断当前用户是否为客服
			String userId = user.getUSER_ID();
			// 管理员客服经理
			if (isRole(Const.ROLE_MANAGER) || isRole(Const.ROLE_CUSTOMER_SERVICE)) {
				pd.put("flag", "5");
			}
			// 运作、销售、运作总监、销售总监
			/*if (isRole(Const.ROLE_OPERATION_DIRECTOR) || isRole(Const.ROLE_OPERATION) || isRole(Const.ROLE_SALES_DIRECTOR) || isRole(Const.ROLE_SALES_ELITE) || isRole(Const.ROLE_PHONE_SALES)) {
				if (!"1".equals(userId)) {
					pd.put("userId", userId);
				}
			}*/
			if ("1".equals(this.getGroup()) || "2".equals(this.getGroup()) || "3".equals(this.getGroup())) {
				pd.put("userId", userId);
			}
			List<PageData> list = expressService.list(page); // 列出客户列表
			List<PageData> list1 = expressService.listAll(pd);
			BigDecimal qkzje = new BigDecimal(0.0);
			for (PageData cus : list1) {
				qkzje = qkzje.add(new BigDecimal(Double.valueOf(cus.get("expressMoney").toString())));
			}
			pd.put("qkzje", qkzje.setScale(2, BigDecimal.ROUND_HALF_UP));
			mv.addObject("contractList", list);
			mv.addObject("pd", pd);
			mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
			mv.setViewName("business/expressManage/express_list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	/**
	 * 去合同新增页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goAdd")
	public ModelAndView goAdd() throws Exception {
		ModelAndView mv = this.getModelAndView();
		// 客服、经理、管理员
		PageData pd = this.getPageData();
		if (isRole(Const.ROLE_MANAGER) || isRole(Const.ROLE_CUSTOMER_SERVICE)) {
			pd.put("flag", "5");
		}
		mv.addObject("pd", pd);
		mv.addObject("msg", "doAdd");
		mv.setViewName("business/expressManage/express_edit");
		return mv;
	}

	/**
	 * 新增合同编号信息(生成任务4)
	 * 
	 * @return
	 */
	@RequestMapping(value = "/doAdd")
	public Object doAdd() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "新增");
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
		// 判断当前用户是否为客服
		String userId = user.getUSER_ID();
		pd.put("userId", userId);
		expressService.save(pd); // 新增合同信息
		if ("0".equals(pd.getString("sendway"))) {

			// 找出客服id
			PageData serivice = expressService.findServiceId();
			pd.put("doId", serivice.get("userId")); // 客服执行人
			expressService.saveWarnInfo(pd);
		}
		pd.put("editTime", DateUtil.getCurrentTime());
		if (TextUtil.isNotNull(pd.getString("recieveValue"))) {
			pd.put("linkId", Arrays.asList(pd.getString("recieveValue").split(",")));
			expressService.updateLinks(pd); // 联系人和快递绑定
		}
		System.out.println("保存后的ID：" + pd.get("id") + "==========================" + pd);
		// 保存附件信息
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
				map.put("type", 11);
				map.put("expressId", pd.get("id"));
				list.add(map);
			}
			if (list.size() > 0) {
				expressService.saveAttach(list);
			}
		}

		if (TextUtil.isNotNull(pd.getString("orderId")) && !"0".equals(pd.getString("orderId"))) {
			mv.addObject("action", "parent");
		} else {
			mv.addObject("msg", "success");
		}
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
		String mark1 = pd.getString("mark1");
		pd = expressService.findById(pd); // 根据ID读取
		String linkId1 = "";
		@SuppressWarnings("unchecked")
		List<PageData> linkMan1List = (List<PageData>) pd.get("links");
		for (PageData linkMan1 : linkMan1List) {
			linkId1 = linkId1 + linkMan1.get("id").toString() + ",";
		}
		if (TextUtil.isNotNull(linkId1)) {
			linkId1 = linkId1.substring(0, linkId1.length() - 1);
		}
		pd.put("recieveValue", linkId1);
		if (isRole(Const.ROLE_MANAGER) || isRole(Const.ROLE_CUSTOMER_SERVICE)) {
			pd.put("flag", "5");
		}
		if (TextUtil.isNotNull(mark1)) {
			pd.put("mark1", mark1);
		}
		mv.addObject("pd", pd);
		mv.addObject("msg", "doEdit");
		mv.setViewName("business/expressManage/express_edit");
		return mv;
	}

	/**
	 * 修改合同
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
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
		if (TextUtil.isNotNull(pd.getString("recieveValue"))) {
			pd.put("recieveValue", Arrays.asList(pd.getString("recieveValue").split(",")));
		} else {
			pd.put("recieveValue", new ArrayList<String>());
		}

		if (TextUtil.isNotNull(pd.getString("orecieveValue"))) {
			// list1 list2 list3.addAll(list1) list3.addAll(list1) list1.removeAll(list3) list2.removeAll(list3)
			// 如果list1.size>0 删除案件与联系人的关系(根据案件id和联系人id) 如果list2.size()>0 新增orderService.updateLinks(pd);
			// orderService.saveLinksAndOrder(pd);
			List<String> list1 = Arrays.asList((pd.getString("orecieveValue").split(",")));
			list1 = toArrayList(list1);
			List<String> list2 = (List<String>) pd.get("recieveValue");
			list2 = toArrayList(list2);
			List<String> list3 = new ArrayList<String>();
			list3.addAll(list1);
			list3.retainAll(list2);
			list1.removeAll(list3);
			list2.removeAll(list3);
			PageData pd1 = new PageData();
			pd1.put("flag", 0);
			pd1.put("id", pd.getString("id"));
			if (list1.size() > 0) {
				pd1.put("rId1", list1);
				expressService.delteLinks(pd1);
			}
			if (list2.size() > 0) {
				pd1.put("linkId", list2);
				expressService.updateLinks(pd1);

			}
		} else {
			if (((List<String>) pd.get("recieveValue")).size() > 0) {
				pd.put("linkId", ((List<String>) pd.get("recieveValue")));
				expressService.updateLinks(pd);
			}
		}
		expressService.update(pd);
		/*	User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
			// 判断当前用户是否为客服总经理、管理员
			String userId = user.getUSER_ID();*/
		if (!"0".equals(pd.getString("sendWay"))) {
			expressService.deleteWarnInfo(pd);
		}
		PageData contract = expressService.findById(pd);
		List<PageData> attachs = (List<PageData>) contract.get("attachs");
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
				map.put("type", 11);
				if (!"0".equals(attachId[i])) {
					list1.add(attachId[i]);
				} else {
					expressService.saveAttach1(map);
				}
			}
			list2.removeAll(list1);
			System.out.println(list2);
			if (list2.size() > 0) {
				expressService.deleteAttach(list2);
			}
		} else {
			if (list2.size() > 0) {
				expressService.deleteAttach(list2);
			}
		}
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
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
				expressService.delete(arrayIds);
				// 删除相关任务
				PageData pd1 = new PageData();
				for (int i = 0; i < arrayIds.length; i++) {
					pd1.put("id", arrayIds[i]);
					expressService.deleteWarnInfo(pd1);
				}
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
			pd = expressService.findById(pd); // 列出客户列表
			if (isRole(Const.ROLE_MANAGER) || isRole(Const.ROLE_CUSTOMER_SERVICE)) {
				pd.put("flag", "5");
			}
			mv.setViewName("business/expressManage/express_view");
			mv.addObject("pd", pd);
			mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	@RequestMapping(value = "/update")
	@ResponseBody
	public Object update() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String error = "";
		String msg = "";
		String result = "";
		try {
			PageData pd = this.getPageData();
			pd.put("editTime", DateUtil.getCurrentTime());
			if (TextUtil.isNotNull(pd.getString("id")) && TextUtil.isNotNull(pd.getString("expressCom")) && TextUtil.isNotNull(pd.getString("expressNo"))
					&& TextUtil.isNotNull(pd.getString("expressMoney"))) {
				if (expressService.update1(pd) > 0) {
					// 待发任务完成
					expressService.doWarnInfo(pd);
					error = "00";
					msg = "发送成功";
				}
			} else if (TextUtil.isNotNull(pd.getString("id")) && TextUtil.isNotNull(pd.getString("reciveTime")) && TextUtil.isNotNull(pd.getString("status"))) {
				if (expressService.update1(pd) > 0) {
					error = "00";
					msg = "保存成功";
				}
			} else {
				error = "01";
				msg = "参数异常";
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

}
