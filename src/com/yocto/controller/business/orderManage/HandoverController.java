package com.yocto.controller.business.orderManage;

import java.util.ArrayList;
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
import com.yocto.service.business.attachInfoManage.IAttachInfoService;
import com.yocto.service.business.orderManage.IHandoverService;
import com.yocto.util.AppUtil;
import com.yocto.util.DateUtil;
import com.yocto.util.Jurisdiction;
import com.yocto.util.PageData;
import com.yocto.util.TextUtil;

@Controller
@RequestMapping(value = "/handover")
public class HandoverController extends BaseController {

	String menuUrl = "handover/list.do"; // 菜单地址(权限用)

	@Resource(name = "handoverService")
	private IHandoverService handoverService;

	@Resource(name = "attachService")
	private IAttachInfoService attachService;

	/**
	 * 获取案件交接列表(对诉讼的案件进行案件交接)
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
			List<PageData> list = handoverService.list(page); // 列出客户列表
			mv.addObject("orderList", list);
			mv.addObject("pd", pd);
			mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
			mv.setViewName("business/orderManage/handover_list");
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
		mv.addObject("pd", pd);
		mv.addObject("msg", "doAdd");
		mv.setViewName("business/orderManage/handover_edit");
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
		handoverService.save1(pd);
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
				map.put("relateId", pd.get("id"));
				map.put("type", 8);
				map.put("orderId", pd.get("orderId"));
				list.add(map);
			}
			if (list.size() > 0) {
				attachService.save(list);
			}
		}
		mv.addObject("msg", "success");
		mv.addObject("action", "parent");
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
		String status=pd.getString("status");
		pd = handoverService.findById(pd); // 根据ID读取
		pd.put("status",status);
		mv.addObject("pd", pd);
		mv.addObject("msg", "doEdit");
		mv.setViewName("business/orderManage/handover_edit");
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
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha") || !Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return null;
		} // 校验权限 判断当前操作者有无客户管理查看权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		pd.put("editTime", currenTime);
		handoverService.update(pd);
		PageData contract = handoverService.findById(pd);
		@SuppressWarnings("unchecked")
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
				map.put("type", 8);
				map.put("orderId", pd.getString("orderId"));
				if (!"0".equals(attachId[i])) {
					list1.add(attachId[i]);
				} else {
					attachService.save(map);
				}
			}
			list2.removeAll(list1);
			if (list2.size() > 0) {
				attachService.delete(list2);
			}
		} else {
			if (list2.size() > 0) {
				attachService.delete(list2);
			}
		}
		mv.addObject("msg", "success");
		mv.addObject("action", "parent");
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
		logBefore(logger, Jurisdiction.getUsername() + "批量删除linkman");
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = this.getPageData();
		String ids = pd.getString("ids");
		if (TextUtil.isNotNull(ids)) {
			System.out.println("批量删除ids:" + ids);
			String arrayIds[] = ids.split(",");
			if (null != arrayIds && arrayIds.length > 0) {
				handoverService.delete(arrayIds);
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
			pd = handoverService.findById(pd); // 列出客户列表
			mv.setViewName("business/orderManage/handover_view");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

}
