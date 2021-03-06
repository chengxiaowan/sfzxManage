package com.yocto.controller.business.mainManage;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yocto.controller.base.BaseController;
import com.yocto.entity.system.User;
import com.yocto.service.business.customerManage.ICustomerService;
import com.yocto.service.business.mainManage.IMainService;
import com.yocto.service.business.orderManage.IOrderService;
import com.yocto.util.AppUtil;
import com.yocto.util.Const;
import com.yocto.util.Jurisdiction;
import com.yocto.util.PageData;
import com.yocto.util.TextUtil;

@Controller
@RequestMapping(value = "/main")
public class MainController extends BaseController {

	@Resource(name = "mainService")
	private IMainService mainService;

	@Resource(name = "customerService")
	private ICustomerService customerService;

	@Resource(name = "orderService")
	private IOrderService orderService;

	// 获取成交客户数量、合同总金额、已回款金额
	// 新增有意向客户数、（大于等于有意向、小于成交)
	// 跟进
	// 预测
	@RequestMapping(value = "/showSaleProcessDetail")
	@ResponseBody
	public Object showSaleProcessDetail() {
		Map<String, Object> map = new HashMap<String, Object>();
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
		String msg = "";
		String error = "";
		PageData result1 = null;
		PageData result2 = null;
		PageData result3 = null;
		PageData result4 = null;
		try {
			PageData pd = this.getPageData();
			// 电访销售
			if (isRole(Const.ROLE_PHONE_SALES) && !"1".equals(user.getUSER_ID())) {
				/*if (!"1".equals(user.getUSER_ID())) {
					pd.put("dfSaleId", user.getUSER_ID());
				}*/
				pd.put("dfSaleId", user.getUSER_ID());
				result2 = mainService.findyyx(pd);
				result3 = mainService.findgj(pd);
				result4 = mainService.findyc(pd);
			} else {
				// 经理
				if (isRole(Const.ROLE_SALES_ELITE)) {
					if (!"1".equals(user.getUSER_ID())) {
						pd.put("saleId", user.getUSER_ID());
					}
				}
				// 成交
				result1 = mainService.findCj(pd);
				// 新增有意向客户数
				result2 = mainService.findyyx(pd);
				// 跟进
				result3 = mainService.findgj(pd);
				// 预测
				result4 = mainService.findyc(pd);
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
			map.put("cj", result1);
			map.put("xz", result2);
			map.put("gj", result3);
			map.put("yc", result4);
		}
		return AppUtil.returnObject(new PageData(), map);
	}

	/**
	 * 新增销售预测
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveSaleyc")
	@ResponseBody
	public Object saveSaleyc() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String error = "";
		String msg = "";
		String result = "";
		try {
			PageData pd = this.getPageData();
			User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
			if (isRole(Const.ROLE_SALES_ELITE)) {
				if (!"1".equals(user.getUSER_ID())) {
					pd.put("saleId", user.getUSER_ID());
				}
				mainService.saveSaleyc(pd);
			}
			if (isRole(Const.ROLE_PHONE_SALES)) {
				if (!"1".equals(user.getUSER_ID())) {
					pd.put("saleId", user.getUSER_ID());
				}
				mainService.saveSaleyc1(pd);
			}
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

	/**
	 * 去重
	 * 
	 * @return
	 */
	@RequestMapping(value = "/hasName")
	@ResponseBody
	public Object hasName() {
		Map<String, String> map = new HashMap<String, String>();
		String errInfo = "success";
		PageData pd = this.getPageData();
		try {
			if (mainService.findName(pd) != null) {
				errInfo = "error";
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo); // 返回结果
		return AppUtil.returnObject(new PageData(), map);
	}

	@RequestMapping(value = "/ycDetail")
	@ResponseBody
	public Object ycDetail() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String error = "";
		String msg = "";
		PageInfo<List<PageData>> result = null;
		try {
			PageData pd = this.getPageData();
			User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
			if (isRole(Const.ROLE_SALES_ELITE)) {
				if (!"1".equals(user.getUSER_ID())) {
					pd.put("saleId", user.getUSER_ID());
				}
			}
			if (isRole(Const.ROLE_PHONE_SALES)) {
				if (!"1".equals(user.getUSER_ID())) {
					pd.put("dfSaleId", user.getUSER_ID());
				}
			}
			String pageNo = pd.getString("page");
			String pageSize = pd.getString("pageSize");
			int pNo = TextUtil.isNull(pageNo) ? 1 : Integer.parseInt(pageNo);
			int pSize = TextUtil.isNull(pageSize) ? 10 : Integer.parseInt(pageSize);
			PageHelper.startPage(pNo, pSize);
			result = new PageInfo(mainService.showSaleyc(pd));
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

	// 详情页展示
	@RequestMapping(value = "/showDetail")
	@ResponseBody
	public Object showDetail() {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = "";
		String error = "";
		PageInfo<List<PageData>> page = null;
		try {
			PageData pd = this.getPageData();
			User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
			if (isRole(Const.ROLE_PHONE_SALES) || isRole(Const.ROLE_SALES_ELITE)) {
				if (!"1".equals(user.getUSER_ID())) {
					pd.put("saleId", user.getUSER_ID());
				}
			}
			String startTime = pd.getString("startTime"); // 开始时间
			String endTime = pd.getString("endTime"); // 结束时间
			if (TextUtil.isNotNull(startTime)) {
				pd.put("startTime", startTime + " 00:00:00");
			}
			if (TextUtil.isNotNull(endTime)) {
				pd.put("endTime", endTime + " 23:59:59");
			}
			String pageNo = pd.getString("page");
			String pageSize = pd.getString("pageSize");
			int pNo = TextUtil.isNull(pageNo) ? 1 : Integer.parseInt(pageNo);
			int pSize = TextUtil.isNull(pageSize) ? 10 : Integer.parseInt(pageSize);
			PageHelper.startPage(pNo, pSize);
			page = new PageInfo(mainService.findReportDetail(pd));
			error = "00";
			msg = "查询成功";
		} catch (Exception e) {
			error = "500";
			msg = "程序异常";
			e.printStackTrace();
			e.printStackTrace();
		} finally {
			map.put("error", error);
			map.put("msg", msg);
			map.put("result", page);
		}
		return AppUtil.returnObject(new PageData(), map);
	}

	@RequestMapping(value = "/showVisitDetail")
	@ResponseBody
	public Object showVisitDetail() {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = "";
		String error = "";
		PageInfo<List<PageData>> page = null;
		try {
			PageData pd = this.getPageData();
			User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
			if (isRole(Const.ROLE_PHONE_SALES) || isRole(Const.ROLE_SALES_ELITE)) {
				if (!"1".equals(user.getUSER_ID())) {
					pd.put("saleId", user.getUSER_ID());
				}
			}
			String startTime = pd.getString("startTime"); // 开始时间
			String endTime = pd.getString("endTime"); // 结束时间
			if (TextUtil.isNotNull(startTime)) {
				pd.put("startTime", startTime + " 00:00:00");
			}
			if (TextUtil.isNotNull(endTime)) {
				pd.put("endTime", endTime + " 23:59:59");
			}
			String pageNo = pd.getString("page");
			String pageSize = pd.getString("pageSize");
			int pNo = TextUtil.isNull(pageNo) ? 1 : Integer.parseInt(pageNo);
			int pSize = TextUtil.isNull(pageSize) ? 10 : Integer.parseInt(pageSize);
			PageHelper.startPage(pNo, pSize);
			page = new PageInfo(mainService.findVisitDetail(pd));
			error = "00";
			msg = "查询成功";
		} catch (Exception e) {
			error = "500";
			msg = "程序异常";
			e.printStackTrace();
			e.printStackTrace();
		} finally {
			map.put("error", error);
			map.put("msg", msg);
			map.put("result", page);
		}
		return AppUtil.returnObject(new PageData(), map);
	}

	/**
	 * 转化率比较
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/xszhRate")
	@ResponseBody
	public Object xszhRate() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String error = "";
		String msg = "";
		List<PageData> result = new ArrayList<PageData>();
		try {
			PageData pd = this.getPageData();
			User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
			if (isRole(Const.ROLE_PHONE_SALES)) {
				if (!"1".equals(user.getUSER_ID())) {
					pd.put("dfSaleId", user.getUSER_ID());
				}
			}
			if (isRole(Const.ROLE_SALES_ELITE)) {
				if (!"1".equals(user.getUSER_ID())) {
					pd.put("saleId", user.getUSER_ID());
				}
			}
			if (isRole(Const.ROLE_MANAGER)) {
				pd.put("managerId", user.getUSER_ID());
			}
			result = mainService.showRate(pd);
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

	/**
	 * 销售漏斗
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/xsld")
	@ResponseBody
	public Object xsld() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String error = "";
		String msg = "";
		List<PageData> result = new ArrayList<PageData>();
		try {
			PageData pd = this.getPageData();
			User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
			if (isRole(Const.ROLE_PHONE_SALES)) {
				if (!"1".equals(user.getUSER_ID())) {
					pd.put("dfSaleId", user.getUSER_ID());
				}
			}
			if (isRole(Const.ROLE_SALES_ELITE)) {
				if (!"1".equals(user.getUSER_ID())) {
					pd.put("saleId", user.getUSER_ID());
				}
			}
			result = mainService.showXsld(pd);
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

	/**
	 * 销售助手
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/xszs")
	@ResponseBody
	public Object xszs() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String error = "";
		String msg = "";
		PageInfo<List<PageData>> page = null;
		try {
			PageData pd = this.getPageData();
			User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
			if ("1".equals(this.getGroup()) || "2".equals(this.getGroup())) {
				if (!"1".equals(user.getUSER_ID())) {
					pd.put("saleId", user.getUSER_ID());
				}
			}
			if ("3".equals(this.getGroup()) || "2".equals(this.getGroup())) {
				pd.put("runnerId", user.getUSER_ID());
			}
			String pageNo = pd.getString("page");
			String pageSize = pd.getString("pageSize");
			int pNo = TextUtil.isNull(pageNo) ? 1 : Integer.parseInt(pageNo);
			int pSize = TextUtil.isNull(pageSize) ? 10 : Integer.parseInt(pageSize);
			PageHelper.startPage(pNo, pSize);
			page = new PageInfo(mainService.showXszs(pd));
			error = "00";
			msg = "保存成功";
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

	/**
	 * 模糊搜索
	 * 
	 * @return
	 */
	@RequestMapping(value = "/searchKeywords")
	@ResponseBody
	public Object searchKeywords() {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = "";
		String error = "";
		PageInfo<List<PageData>> result = null;
		try {
			PageData pd = this.getPageData();
			String keywords = pd.getString("keywords"); // 关键词检索条件
			if (TextUtil.isNotNull(keywords)) {
				pd.put("keywords", keywords.trim());
			}
			User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
			if (isRole(Const.ROLE_SALES_ELITE)) {
				if (!"1".equals(user.getUSER_ID())) {
					pd.put("saleId", user.getUSER_ID());
				}
			}
			//
			String pageNo = pd.getString("page");
			String pageSize = pd.getString("pageSize");
			int pNo = TextUtil.isNull(pageNo) ? 1 : Integer.parseInt(pageNo);
			int pSize = TextUtil.isNull(pageSize) ? 10 : Integer.parseInt(pageSize);
			PageHelper.startPage(pNo, pSize);
			result = new PageInfo(mainService.searchKeywords(pd));
			error = "00";
			msg = "查询成功";
		} catch (Exception e) {
			error = "500";
			msg = "程序异常";
			e.printStackTrace();
		} finally {
			map.put("error", error);
			map.put("msg", msg);
			map.put("result1", result);
		}
		return AppUtil.returnObject(new PageData(), map);
	}

	/**
	 * 展示已成交的客户数目
	 * 
	 * @return
	 */
	@RequestMapping(value = "/showCj")
	@ResponseBody
	public Object showCustomer() {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = "";
		String error = "";
		BigDecimal hkje = new BigDecimal(0.0).setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal yjje = new BigDecimal(0.0).setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal qkzje = new BigDecimal(0.0);
		BigDecimal hkzje = new BigDecimal(0.0);
		BigDecimal syzje = new BigDecimal(0.0);
		PageInfo<List<PageData>> page = null;
		PageData pd = this.getPageData();
		try {
			User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
			/*if (isRole(Const.ROLE_PHONE_SALES) || isRole(Const.ROLE_SALES_ELITE)) {
				if (!"1".equals(user.getUSER_ID())) {
					pd.put("saleId", user.getUSER_ID());
				}
			}*/
			if ("1".equals(this.getGroup()) || ("2".equals(this.getGroup()) && !Const.ROLE_SALES_ELITEDX.equals(user.getROLE_ID()))) {
				pd.put("saleId", user.getUSER_ID());
			}
			if (Const.ROLE_SALES_ELITEDX.equals(user.getROLE_ID())) {
				pd.put("dxswgwId", user.getUSER_ID());
			}
			String startTime = pd.getString("startTime"); // 开始时间
			String endTime = pd.getString("endTime"); // 结束时间
			if (TextUtil.isNotNull(startTime)) {
				pd.put("startTime", startTime + " 00:00:00");
			}
			if (TextUtil.isNotNull(endTime)) {
				pd.put("endTime", endTime + " 23:59:59");
			}
			String pageNo = pd.getString("page");
			String pageSize = pd.getString("pageSize");
			int pNo = TextUtil.isNull(pageNo) ? 1 : Integer.parseInt(pageNo);
			int pSize = TextUtil.isNull(pageSize) ? 10 : Integer.parseInt(pageSize);
			PageHelper.startPage(pNo, pSize);
			page = new PageInfo(mainService.showCustomer(pd));
			if ("2".equals(pd.getString("flag"))) {
				List<PageData> listAllPage = orderService.listAll(pd);
				qkzje = new BigDecimal(0.0);
				hkzje = new BigDecimal(0.0);
				syzje = new BigDecimal(0.0);
				for (PageData cus : listAllPage) {
					qkzje = qkzje.add(new BigDecimal(Double.valueOf(cus.get("debtAmount").toString())));
					syzje = syzje.add(new BigDecimal(Double.valueOf(cus.get("syMoney").toString())));
					hkzje = hkzje.add(new BigDecimal(Double.valueOf(cus.get("totalMoney").toString())));
				}
			}

			if ("3".equals(pd.getString("flag"))) {
				List<PageData> listAllPage = mainService.showCustomer(pd);
				List<PageData> listAllPage1 = orderService.listAll(pd);
				for (PageData cus : listAllPage) {
					hkje = hkje.add(new BigDecimal(Double.valueOf(cus.get("money").toString())));
					if (null != cus.get("commissionaMount")) {
						yjje = yjje.add(new BigDecimal(Double.valueOf(cus.get("commissionaMount").toString())));
					}
				}
				for (PageData cus : listAllPage1) {
					qkzje = qkzje.add(new BigDecimal(Double.valueOf(cus.get("debtAmount").toString())));
				}
			}
			error = "00";
			msg = "查询成功";
		} catch (Exception e) {
			error = "500";
			msg = "程序异常";
			e.printStackTrace();
			e.printStackTrace();
		} finally {
			map.put("error", error);
			map.put("msg", msg);
			map.put("result", page);
			if ("2".equals(pd.getString("flag"))) {
				map.put("qkzje", qkzje.setScale(2, BigDecimal.ROUND_HALF_UP));
				map.put("syzje", syzje.setScale(2, BigDecimal.ROUND_HALF_UP));
				map.put("hkzje", hkzje.setScale(2, BigDecimal.ROUND_HALF_UP));
			}
			if ("3".equals(pd.getString("flag"))) {
				map.put("qkzje", qkzje.setScale(2, BigDecimal.ROUND_HALF_UP));
				map.put("hkje", hkje.setScale(2, BigDecimal.ROUND_HALF_UP));
				map.put("yjje", yjje.setScale(2, BigDecimal.ROUND_HALF_UP));
			}
		}
		return AppUtil.returnObject(new PageData(), map);
	}

	/**
	 * 查看企业名称详细信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/showCompayDetail")
	@ResponseBody
	public Object showCompayDetail() {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = "";
		String error = "";
		PageData result = null;
		try {
			PageData pd = this.getPageData();
			String keywords = pd.getString("keywords"); // 关键词检索条件
			if (TextUtil.isNotNull(keywords)) {
				pd.put("keywords", keywords.trim());
			}

			if ("0".equals(pd.getString("flag"))) {
				result = customerService.findById(pd);
			}
			if ("1".equals(pd.getString("flag"))) {
				result = mainService.findDebtorDetail(pd);
			}
			if ("2".equals(pd.getString("flag"))) {
				result = customerService.findSaleCustomerById(pd);
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
			map.put("result1", result);
		}
		return AppUtil.returnObject(new PageData(), map);
	}

	// =================电销售报表电话统计====================
	@RequestMapping(value = "/showdfSaleProcessDetail")
	@ResponseBody
	public Object showdfSaleProcessDetail() {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = "";
		String error = "";
		List<PageData> result = null;
		try {
			PageData pd = this.getPageData();
			if (/*"1".equals(this.getGroup()) &&*/TextUtil.isNotNull(pd.getString("saleId"))) {
				pd.put("flag", 1);
				result = mainService.findSalePhoneReport1(pd);
				List<PageData> zouResult = toZouResult(result, pd.getString("startTime"), pd.getString("endTime"), 1);
				pd.put("flag", 2);
				result = mainService.findSalePhoneReport1(pd);
				List<PageData> zouResult2 = toZouResult(result, pd.getString("startTime"), pd.getString("endTime"), 2);
				// 找出销售本月的业绩目标
				pd.put("type", "0");
				result = mainService.findDhlAndYxkhsBySaleId(pd);
				List<PageData> zouResult3 = toZouResult(result, pd.getString("startTime"), pd.getString("endTime"), 3);
				pd.put("type", "1");
				result = mainService.findDhlAndYxkhsBySaleId(pd);
				List<PageData> zouResult4 = toZouResult(result, pd.getString("startTime"), pd.getString("endTime"), 4);

				result = hbList(zouResult, zouResult2, zouResult3, zouResult4);
			} else {
				result = mainService.findSalePhoneReport(pd);
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

	private List<PageData> hbList(List<PageData> zouResult, List<PageData> zouResult2, List<PageData> zouResult3, List<PageData> zouResult4) {
		List<PageData> list = new ArrayList<PageData>();
		for (int i = 0; i < zouResult.size(); i++) {
			PageData pd = new PageData();
			pd.put("time", zouResult.get(i).get("time"));
			pd.put("dhl", zouResult.get(i).get("zcs"));
			pd.put("yxkhs", zouResult2.get(i).get("yxkhs"));
			pd.put("mbdhl", zouResult3.get(i).get("targetDetail"));
			pd.put("mbyxkhs", zouResult4.get(i).get("targetDetail"));
			list.add(pd);
		}
		return list;
	}

	private List<PageData> toZouResult(List<PageData> result, String startTime, String endTime, int type) throws Exception {
		List<String> list = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Calendar lastDate = Calendar.getInstance();
		startTime = sdf.format(sdf.parse(startTime));
		endTime = sdf.format(sdf.parse(endTime));
		while (!startTime.equals(endTime)) {
			list.add(startTime);
			lastDate.setTime(sdf.parse(startTime));
			lastDate.add(Calendar.MONTH, 1);
			startTime = sdf.format(lastDate.getTime());
		}
		list.add(endTime);

		List<PageData> list1 = new ArrayList<PageData>();
		for (int i = 0; i < list.size(); i++) {
			PageData pd = new PageData();
			boolean mark = true;
			for (int j = 0; null != result && j < result.size(); j++) {
				if (list.get(i).equals(result.get(j).get("time").toString())) {
					list1.add(result.get(j));
					mark = false;
					break;
				}
			}
			if (mark) {
				if (1 == type) {
					pd.put("zcs", 0);
				}
				if (2 == type) {
					pd.put("yxkhs", 0);
				}
				if (3 == type) {
					pd.put("targetDetail", 0);
				}
				if (4 == type) {
					pd.put("targetDetail", 0);
				}
				pd.put("time", list.get(i));
				list1.add(pd);
			}
		}
		return list1;
	}

	// =======
	@RequestMapping(value = "/showxsldDetail")
	@ResponseBody
	public Object showxsldDetail() {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = "";
		String error = "";
		PageInfo<List<PageData>> result = null;
		try {
			PageData pd = this.getPageData();
			User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
			if (isRole(Const.ROLE_PHONE_SALES)) {
				if (!"1".equals(user.getUSER_ID())) {
					pd.put("dfSaleId", user.getUSER_ID());
				}
			}
			if (isRole(Const.ROLE_SALES_ELITE)) {
				if (!"1".equals(user.getUSER_ID())) {
					pd.put("saleId", user.getUSER_ID());
				}
			}
			String pageNo = pd.getString("page");
			String pageSize = pd.getString("pageSize");
			int pNo = TextUtil.isNull(pageNo) ? 1 : Integer.parseInt(pageNo);
			int pSize = TextUtil.isNull(pageSize) ? 10 : Integer.parseInt(pageSize);
			PageHelper.startPage(pNo, pSize);
			result = new PageInfo(mainService.showxsldDetail(pd));
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
}
