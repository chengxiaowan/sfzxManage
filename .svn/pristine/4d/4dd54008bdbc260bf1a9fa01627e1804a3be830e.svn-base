package com.yocto.controller.business.contractManage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yocto.controller.base.BaseController;
import com.yocto.entity.Page;
import com.yocto.entity.system.User;
import com.yocto.service.business.contractManage.IContractService;
import com.yocto.service.business.customerManage.ICustomerService;
import com.yocto.util.AppUtil;
import com.yocto.util.Const;
import com.yocto.util.DateUtil;
import com.yocto.util.FileUpload;
import com.yocto.util.Jurisdiction;
import com.yocto.util.PageData;
import com.yocto.util.TextUtil;

/**
 * 合同列表(只有客服和管理员可以操作)
 * 
 * @author xl
 *
 */
@Controller
@RequestMapping(value = "/contract")
public class ContractController extends BaseController {
	String menuUrl = "contract/list.do";

	@Resource(name = "contractService")
	private IContractService contractService;

	@Resource(name = "customerService")
	private ICustomerService customerService;

	/**
	 * 获取非诉讼合同列表
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
			pd.put("type", 0);
			page.setPd(pd);
			List<PageData> list = contractService.list(page); // 列出客户列表
			mv.addObject("contractList", list);
			mv.addObject("pd", pd);
			mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
			mv.setViewName("business/contractManage/contract_list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	@RequestMapping(value = "/showAllContract")
	@ResponseBody
	public Object showAllContract() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = null;
		String error = null;
		PageInfo<PageData> page = null;
		int htsl = 0;
		BigDecimal qkzje = new BigDecimal(0.0);
		try {
			PageData pd = this.getPageData();
			String createTimeStart = pd.getString("startTime"); // 开始时间
			String createTimeEnd = pd.getString("endTime"); // 结束时间
			if (TextUtil.isNotNull(createTimeStart)) {
				pd.put("startTime", createTimeStart + " 00:00:00");
			}
			if (TextUtil.isNotNull(createTimeEnd)) {
				pd.put("endTime", createTimeEnd + " 23:59:59");
			}
			String pageNo = pd.getString("pageNo");
			String pageSize = pd.getString("pageSize");
			int pNo = TextUtil.isNull(pageNo) ? 1 : Integer.parseInt(pageNo);
			int pSize = TextUtil.isNull(pageSize) ? 10 : Integer.parseInt(pageSize);
			PageHelper.startPage(pNo, pSize); // 核心分页代码
			page = new PageInfo<PageData>(contractService.showAllContract(pd));// 列出任务列表
			List<PageData> listAllPage1 = contractService.showAllContract(pd);
			for (PageData cus : listAllPage1) {
				qkzje = qkzje.add(new BigDecimal(Double.valueOf((null != cus.get("price") ? cus.get("price").toString() : "0.0"))));
			}
			htsl = listAllPage1.size();
			error = "00";
			msg = "查询成功";
		} catch (Exception e) {
			e.printStackTrace();
			error = "500";
			msg = "接口错误：" + e.getMessage();
		} finally {
			map.put("error", error);
			map.put("msg", msg);
			map.put("result", page);
			map.put("qkzje", qkzje.setScale(2, BigDecimal.ROUND_HALF_UP));
			map.put("htsl", htsl);
		}
		return AppUtil.returnObject(new PageData(), map);
	}

	/**
	 * 获取诉讼合同列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sslist")
	public ModelAndView sslist(Page page) throws Exception {
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
			pd.put("type", 1);
			page.setPd(pd);
			List<PageData> list = contractService.sslist(page); // 列出客户列表
			mv.addObject("contractList", list);
			mv.addObject("pd", pd);
			mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
			mv.setViewName("business/contractManage/contract_list");
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
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		mv.addObject("pd", pd);
		mv.addObject("msg", "doAdd");
		mv.setViewName("business/contractManage/contract_edit");
		return mv;
	}

	/**
	 * 附件上传
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/uploadAttachment")
	@ResponseBody
	public Object uploadAttachment(@RequestParam(required = false) MultipartFile file) throws Exception {
		PageData map = null;
		if (null != file && !file.isEmpty()) {
			// 保存文件
			map = FileUpload.upload(file);
			// 获取上传人
			User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
			if (null != user) {
				map.put("uploader", user.getNAME());
			}
		}
		return AppUtil.returnObject(new PageData(), map);
	}

	/**
	 * 新增合同编号信息
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
		pd.put("price", TextUtil.isNotNull(pd.getString("price")) ? pd.getString("price") : null);
		pd.put("debtorId", TextUtil.isNotNull(pd.getString("debtorId")) ? pd.getString("debtorId") : null);
		contractService.save(pd); // 新增合同信息
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
				map.put("contractId", pd.get("id"));
				list.add(map);
			}
			if (list.size() > 0) {
				contractService.saveAttach(list);
			}
		}
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
		if ("0".equals(pd.getString("type"))) {
			pd = contractService.findById(pd);
		} else {
			pd = contractService.findById1(pd);
		}
		mv.addObject("pd", pd);
		mv.addObject("msg", "doEdit");
		mv.setViewName("business/contractManage/contract_edit");
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
		PageInfo<PageData> page = null;
		try {
			PageData pd = this.getPageData();
			if ("0".equals(pd.getString("type"))) {
				result = contractService.findById(pd);
				// 根据合同id查看订单列表
				String pageNo = pd.getString("pageNo");
				String pageSize = pd.getString("pageSize");
				int pNo = TextUtil.isNull(pageNo) ? 1 : Integer.parseInt(pageNo);
				int pSize = TextUtil.isNull(pageSize) ? 10 : Integer.parseInt(pageSize);
				PageHelper.startPage(pNo, pSize); // 核心分页代码
				page = new PageInfo<PageData>(contractService.findOrderInfoByContractId(pd));
				result.put("orderInfo", page);
			} else {
				result = contractService.findById1(pd);
				String pageNo = pd.getString("pageNo");
				String pageSize = pd.getString("pageSize");
				int pNo = TextUtil.isNull(pageNo) ? 1 : Integer.parseInt(pageNo);
				int pSize = TextUtil.isNull(pageSize) ? 10 : Integer.parseInt(pageSize);
				PageHelper.startPage(pNo, pSize); // 核心分页代码
				page = new PageInfo<PageData>(contractService.findOrderInfoByContractId(pd));
				result.put("orderInfo", page);
			}
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
		pd.put("editTime", currenTime);
		pd.put("price", TextUtil.isNotNull(pd.getString("price")) ? pd.getString("price") : null);
		pd.put("debtorId", TextUtil.isNotNull(pd.getString("debtorId")) ? pd.getString("debtorId") : null);
		contractService.update(pd);
		contractService.updateCustomerSaleId(pd);
		PageData contract = contractService.findById(pd);
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
				if (!"0".equals(attachId[i])) {
					list1.add(attachId[i]);
				} else {
					contractService.saveAttach1(map);
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
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
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
			pd.put("editTime", currenTime);
			pd.put("price", TextUtil.isNotNull(pd.getString("price")) ? pd.getString("price") : null);
			pd.put("debtorId", TextUtil.isNotNull(pd.getString("debtorId")) ? pd.getString("debtorId") : null);

			if (TextUtil.isNotNull(pd.getString("userId"))) {
				contractService.updateCustomerSaleId(pd);
			}

			PageData pd1 = contractService.findById(pd);
			String qzdz = "";
			String hzdz = "";
			// 前字段值、后字段值
			if (TextUtil.isNotNull(pd.getString("userName"))) {
				qzdz = qzdz + pd1.getString("userName");
				hzdz = hzdz + pd.getString("userName");
			}
			if (TextUtil.isNotNull(pd.getString("customerName"))) {
				qzdz = qzdz + pd1.getString("customerName");
				hzdz = hzdz + pd.getString("customerName");
			}
			if (TextUtil.isNotNull(pd.getString("debtorName"))) {
				qzdz = qzdz + pd1.getString("debtorName");
				hzdz = hzdz + pd.getString("debtorName");
			}
			if (TextUtil.isNotNull(pd.getString("contractNo"))) {
				qzdz = qzdz + pd1.getString("contractNo");
				hzdz = hzdz + pd.getString("contractNo");
			}
			if (TextUtil.isNotNull(pd.getString("endTime"))) {
				qzdz = qzdz + pd1.getString("endTime");
				hzdz = hzdz + pd.getString("endTime");
			}
			if (TextUtil.isNotNull(pd.getString("price"))) {
				qzdz = qzdz + pd1.getString("price");
				hzdz = hzdz + pd.getString("price");
			}
			if (TextUtil.isNotNull(pd.getString("signingTime"))) {
				qzdz = qzdz + pd1.getString("signingTime");
				hzdz = hzdz + pd.getString("signingTime");
			}
			if (TextUtil.isNotNull(pd.getString("address"))) {
				qzdz = qzdz + pd1.getString("address");
				hzdz = hzdz + pd.getString("address");
			}
			if (TextUtil.isNotNull(pd.getString("commissionRate"))) {
				qzdz = qzdz + pd1.getString("commissionRate");
				hzdz = hzdz + pd.getString("commissionRate");
			}
			if (TextUtil.isNotNull(pd.getString("remark"))) {
				qzdz = qzdz + pd1.getString("remark");
				hzdz = hzdz + pd.getString("remark");
			}
			User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
			PageData pd2 = new PageData();
			pd2.put("userId", user.getUSER_ID());
			pd2.put("qzdz", qzdz);
			pd2.put("hzdz", hzdz);
			pd2.put("saleCustomerId", pd.getString("id"));
			pd2.put("type", "编辑");
			pd2.put("flag", "1");
			customerService.saveLogs(pd2);

			contractService.update(pd);
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
				contractService.delete(arrayIds);
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
			if ("0".equals(pd.getString("type"))) {
				pd = contractService.findById(pd);
			} else {
				pd = contractService.findById1(pd);
			}
			mv.setViewName("business/contractManage/contract_view");
			mv.addObject("pd", pd);
			mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	@RequestMapping(value = "/getContractInfo")
	@ResponseBody
	public Object getLinkmanInfo() throws Exception {
		PageData pd = this.getPageData();
		List<PageData> result = new ArrayList<PageData>();
		try {
			pd.put("key", pd.getString("data[q]"));
			result = contractService.listAll(pd);
		} catch (Exception e) {
			logger.error(e);
		}
		return JSONArray.fromObject(result);
	}

	@RequestMapping(value = "/getDebtorInfo")
	@ResponseBody
	public Object getDebtorInfo() throws Exception {
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
			result = contractService.getDebtorInfo(pd);
		} catch (Exception e) {
			logger.error(e);
		}
		return JSONArray.fromObject(result);
	}

	@RequestMapping(value = "/getDebtorInfo1")
	@ResponseBody
	public Object getDebtorInfo1() throws Exception {
		PageData pd = this.getPageData();
		List<PageData> result = new ArrayList<PageData>();
		try {
			result = contractService.getDebtorInfo(pd);
		} catch (Exception e) {
			logger.error(e);
		}
		return JSONArray.fromObject(result);
	}

	/**
	 * 展示合同列表
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/chooseContract")
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
			List<PageData> cusList = contractService.list1(page); // 列出客户列表
			mv.setViewName("business/contractManage/chooseContract_list");
			mv.addObject("cusList", cusList);
			mv.addObject("pd", pd);
			mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
}
