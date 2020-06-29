package com.yocto.controller.business.invoiceManage;

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
import com.yocto.service.business.invoiceManage.IInvoiceService;
import com.yocto.service.business.workBenchManage.IWorkBenchService;
import com.yocto.util.AppUtil;
import com.yocto.util.Jurisdiction;
import com.yocto.util.PageData;
import com.yocto.util.TextUtil;

/**
 * 类名称：InvoiceController 创建人： @author 更新时间：2015年11月3日
 * 
 * @version
 */
@Controller
@RequestMapping(value = "/invoice")
public class InvoiceController extends BaseController {

	String menuUrl = "invoice/list.do"; // 菜单地址(权限用)

	@Resource(name = "invoiceService")
	private IInvoiceService invoiceService;

	@Resource(name = "attachService")
	private IAttachInfoService attachService;

	@Resource(name = "workBenchService")
	private IWorkBenchService workBenchService;

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
			List<PageData> list = invoiceService.list(page); // 列表
			mv.addObject("dataList", list);
			mv.addObject("pd", pd);
			mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
			mv.setViewName("business/invoiceManage/invoice_list");
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
		PageData pd1 = this.getPageData();
		pd1 = invoiceService.findById(pd1); // 根据ID读取
		PageData pd = new PageData();
		pd.put("paymentId", pd1.get("id"));
		pd = workBenchService.findWarnById(pd);
		mv.addObject("pd", pd);
		mv.addObject("pd1", pd1);
		mv.addObject("msg", "save");
		mv.setViewName("business/workBenchManage/task_view_type1");
		return mv;
	}

	/**
	 * 新增或修改
	 * 
	 * @return
	 */
	@RequestMapping(value = "/save")
	public Object save() throws Exception {
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		logBefore(logger, Jurisdiction.getUsername() + "新增");
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		pd.put("remark", TextUtil.transNull(pd.getString("remark")).trim());

		invoiceService.saveOrUpdate(pd);
		PageData contract = invoiceService.findById(pd);
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
				map.put("relateId", pd.get("id"));
				map.put("orderId", null);
				map.put("type", 12);
				map.put("id", attachId[i]);
				if (!"0".equals(attachId[i])) {
					list1.add(attachId[i]);
				} else {
					attachService.save(map);
				}
			}
			list2.removeAll(list1);
			System.out.println(list2);
			if (list2.size() > 0) {
				attachService.delete(list2);
			}
		} else {
			if (list2.size() > 0) {
				attachService.delete(list2);
			}
		}

		// 将任务改为已完成
		invoiceService.updateTask(pd);
        if("1".equals(pd.getString("sy_workwench"))){
        	mv.addObject("sy_workwench", "1");
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
		logBefore(logger, Jurisdiction.getUsername() + "批量删除invoice");
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = this.getPageData();
		String ids = pd.getString("ids");
		if (TextUtil.isNotNull(ids)) {
			System.out.println("批量删除ids:" + ids);
			String arrayIds[] = ids.split(",");
			if (null != arrayIds && arrayIds.length > 0) {
				invoiceService.delete(arrayIds);
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
	 * 判断发票号码是否存在
	 * 
	 * @return
	 */
	@RequestMapping(value = "/hasInvoiceCode")
	@ResponseBody
	public Object hasInvoiceCode() {
		Map<String, String> map = new HashMap<String, String>();
		String errInfo = "success";
		PageData pd = this.getPageData();
		try {
			if (invoiceService.findByInvoiceCode(pd) != null) {
				errInfo = "error";
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo); // 返回结果
		return AppUtil.returnObject(new PageData(), map);
	}

	/**
	 * 查看详情
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
			pd = invoiceService.findById(pd); // 列出客户列表
			mv.setViewName("business/invoiceManage/invoice_view");
			mv.addObject("pd", pd);
			mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
}
