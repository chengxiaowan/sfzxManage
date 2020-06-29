package com.yocto.controller.business.reportManage;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.TextMessage;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yocto.controller.base.BaseController;
import com.yocto.entity.system.User;
import com.yocto.service.business.customerManage.ICustomerService;
import com.yocto.service.business.expressManage.IExpressService;
import com.yocto.service.business.orderManage.IOrderService;
import com.yocto.service.business.reportManage.IReportService;
import com.yocto.util.AppUtil;
import com.yocto.util.Const;
import com.yocto.util.DateUtil;
import com.yocto.util.Jurisdiction;
import com.yocto.util.PageData;
import com.yocto.util.TextUtil;
import com.yocto.util.TimeUtil;
import com.yocto.websockect.SystemWebSocketHandler;

@Controller
@RequestMapping(value = "/report")
public class ReportController extends BaseController {

	@Resource(name = "reportService")
	private IReportService reportService;

	@Resource(name = "expressService")
	private IExpressService expressService;

	@Resource(name = "customerService")
	private ICustomerService customerService;

	@Resource(name = "orderService")
	private IOrderService orderService;

	@Resource(name = "systemWebSocketHandler")
	private SystemWebSocketHandler systemWebSocketHandler;

	// =================销售报表电话统计====================
	@RequestMapping(value = "/showSaleProcessDetail")
	@ResponseBody
	public Object showSaleProcessDetail() {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = "";
		String error = "";
		List<PageData> result = null;
		try {
			PageData pd = this.getPageData();
			String startTime = pd.getString("startTime"); // 开始时间
			String endTime = pd.getString("endTime"); // 结束时间
			if (TextUtil.isNotNull(startTime)) {
				pd.put("startTime", startTime + " 00:00:00");
			}
			if (TextUtil.isNotNull(endTime)) {
				pd.put("endTime", endTime + " 23:59:59");
			}
			result = reportService.findSalePhoneReport(pd);
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
			page = new PageInfo(reportService.findReportDetail(pd));
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

	// =================客户预测展示============
	@RequestMapping(value = "/showSaleFroecast")
	@ResponseBody
	public Object showSaleFroecast() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = null;
		String error = null;
		BigDecimal totalMoney = new BigDecimal(0.0);
		PageInfo<List<PageData>> page = null;
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
			if (isRole(Const.ROLE_SALES_ELITE) || isRole(Const.ROLE_PHONE_SALES)) {
				if (!"1".equals(userId)) {
					pd.put("saleId", userId);
				}
			}
			String pageNo = pd.getString("pageNo");
			String pageSize = pd.getString("pageSize");
			int pNo = TextUtil.isNull(pageNo) ? 1 : Integer.parseInt(pageNo);
			int pSize = TextUtil.isNull(pageSize) ? 10 : Integer.parseInt(pageSize);
			PageHelper.startPage(pNo, pSize); // 核心分页代码
			page = new PageInfo(reportService.listFroecast(pd));// 列出任务列表
			List<PageData> list = reportService.listFroecast(pd);
			for (PageData cus : list) {
				totalMoney = totalMoney.add(new BigDecimal(Double.valueOf(cus.get("ycMoney").toString())));
			}
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
			map.put("totalMoney", totalMoney.setScale(2, BigDecimal.ROUND_HALF_UP));
		}
		return AppUtil.returnObject(new PageData(), map);
	}

	// 潜在客户模糊查询接口
	@RequestMapping(value = "/getQzkh")
	@ResponseBody
	public Object getQzkh() throws Exception {
		PageData pd = this.getPageData();
		pd.put("key", pd.getString("data[q]"));
		List<PageData> result = new ArrayList<PageData>();
		try {
			String pageNo = pd.getString("page");
			String pageSize = pd.getString("pageSize");
			int pNo = TextUtil.isNull(pageNo) ? 1 : Integer.parseInt(pageNo);
			int pSize = TextUtil.isNull(pageSize) ? 10 : Integer.parseInt(pageSize);
			PageHelper.startPage(pNo, pSize); // 核心分页代码
			result = reportService.listAll(pd);
		} catch (Exception e) {
			logger.error(e);
		}
		return JSONArray.fromObject(result);
	}

	// 新增潜在客户
	@RequestMapping(value = "saveForecast")
	@ResponseBody
	public Object saveForecast() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData result = null;
		String msg = null;
		String error = null;
		try {
			PageData pd = this.getPageData();
			User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
			String userId = user.getUSER_ID();
			pd.put("saleId", userId);
			reportService.saveForecast(pd);
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

	@RequestMapping(value = "updateForecast")
	@ResponseBody
	public Object updateForecast() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData result = null;
		String msg = null;
		String error = null;
		try {
			PageData pd = this.getPageData();
			/*User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
			String userId = user.getUSER_ID();
			pd.put("saleId", userId);*/
			reportService.updateForecast(pd);
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

	// =================销售预测统计================
	@RequestMapping(value = "xsycReport")
	@ResponseBody
	public Object xsycReport() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = "";
		String error = "";
		List<PageData> result = new ArrayList<PageData>();
		try {
			PageData pd = this.getPageData();
			String startTime = pd.getString("startTime"); // 开始时间
			String endTime = pd.getString("endTime"); // 结束时间
			/*if (TextUtil.isNotNull(startTime)) {
				pd.put("startTime", startTime + " 00:00:00");
			}
			if (TextUtil.isNotNull(endTime)) {
				pd.put("endTime", endTime + " 23:59:59");
			}*/
			List<String> list = toTimeList(startTime, endTime);
			for (int i = 0; i < list.size(); i++) {
				pd.put("time", list.get(i));
				PageData result1 = reportService.showXsycReport(pd);
				if (null == result1) {
					result1 = new PageData();
					result1.put("date", list.get(i));
					result1.put("totalMoney", 0);
				}
				result.add(result1);
			}
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

	@RequestMapping(value = "xsycReportDetail")
	@ResponseBody
	public Object xsycReportDetail() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = "";
		String error = "";
		PageInfo<List<PageData>> result = null;
		try {
			PageData pd = this.getPageData();
			/*String startTime = pd.getString("startTime"); // 开始时间
			String endTime = pd.getString("endTime"); // 结束时间
			if (TextUtil.isNotNull(startTime)) {
				pd.put("startTime", startTime + " 00:00:00");
			}
			if (TextUtil.isNotNull(endTime)) {
				pd.put("endTime", endTime + " 23:59:59");
			}*/
			result = new PageInfo(reportService.showXsycReportDetail(pd));
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

	private static List<String> toTimeList(String startTime, String endTime) throws Exception {
		List<String> list = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Calendar min = Calendar.getInstance();
		Calendar max = Calendar.getInstance();

		min.setTime(sdf.parse(startTime));
		min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

		max.setTime(sdf.parse(endTime));
		max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

		Calendar curr = min;
		while (curr.before(max)) {
			list.add(sdf.format(curr.getTime()));
			curr.add(Calendar.MONTH, 1);
		}

		return list;
	}

	// ==========销售回款明细报表=========================
	@RequestMapping(value = "xshkReport")
	@ResponseBody
	public Object xshkReport() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = "";
		String error = "";
		List<PageData> result = null;
		BigDecimal hktotalMoney = new BigDecimal(0.0);
		BigDecimal yjtotalMoney = new BigDecimal(0.0);
		try {
			PageData pd = this.getPageData();
			result = reportService.showXshkReport1(pd);
			for (PageData cus : result) {
				Object money = cus.get("hkMoney");
				if (null == money) {
					money = "0";
				}
				hktotalMoney = hktotalMoney.add(new BigDecimal(Double.valueOf(money.toString())));
				Object money1 = cus.get("commissionaMount");
				if (null == money1) {
					money1 = "0";
				}
				yjtotalMoney = yjtotalMoney.add(new BigDecimal(Double.valueOf(money1.toString())));
			}
			error = "00";
			msg = "修改成功";
		} catch (Exception e) {
			error = "500";
			msg = "程序异常";
			e.printStackTrace();
		} finally {
			map.put("error", error);
			map.put("msg", msg);
			map.put("hktotalMoney", hktotalMoney.setScale(2, BigDecimal.ROUND_HALF_UP));
			map.put("yjtotalMoney", yjtotalMoney.setScale(2, BigDecimal.ROUND_HALF_UP));
			map.put("result", result);
		}
		return AppUtil.returnObject(new PageData(), map);
	}

	@RequestMapping(value = "xshkReportDetail")
	@ResponseBody
	public Object xshkReportDetail() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = "";
		String error = "";
		PageInfo<List<PageData>> result = null;
		BigDecimal hktotalMoney = new BigDecimal(0.0);
		BigDecimal yjtotalMoney = new BigDecimal(0.0);
		try {
			PageData pd = this.getPageData();
			String pageNo = pd.getString("page");
			String pageSize = pd.getString("pageSize");
			int pNo = TextUtil.isNull(pageNo) ? 1 : Integer.parseInt(pageNo);
			int pSize = TextUtil.isNull(pageSize) ? 10 : Integer.parseInt(pageSize);
			PageHelper.startPage(pNo, pSize);
			result = new PageInfo(reportService.showXshkReport(pd));
			List<PageData> list = reportService.showXshkReport(pd);
			for (PageData cus : list) {
				Object money = cus.get("hkMoney");
				if (null == money) {
					money = "0";
				}
				hktotalMoney = hktotalMoney.add(new BigDecimal(Double.valueOf(money.toString())));
				Object money1 = cus.get("commissionaMount");
				if (null == money1) {
					money1 = "0";
				}
				yjtotalMoney = yjtotalMoney.add(new BigDecimal(Double.valueOf(money1.toString())));
			}
			error = "00";
			msg = "修改成功";
		} catch (Exception e) {
			error = "500";
			msg = "程序异常";
			e.printStackTrace();
		} finally {
			map.put("error", error);
			map.put("msg", msg);
			map.put("hktotalMoney", hktotalMoney.setScale(2, BigDecimal.ROUND_HALF_UP));
			map.put("yjtotalMoney", yjtotalMoney.setScale(2, BigDecimal.ROUND_HALF_UP));
			map.put("result", result);
		}
		return AppUtil.returnObject(new PageData(), map);
	}

	// =========数据来源已成交客户的列表==================
	@RequestMapping(value = "showCustomerResource")
	@ResponseBody
	public Object customerResource() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = "";
		String error = "";
		List<PageData> result = null;
		try {
			PageData pd = this.getPageData();
			result = reportService.customerResource(pd);
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

	@RequestMapping(value = "showCustomerResourceDetail")
	@ResponseBody
	public Object customerResourceDetail() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = "";
		String error = "";
		PageInfo<List<PageData>> result = null;
		try {
			PageData pd = this.getPageData();
			String pageNo = pd.getString("page");
			String pageSize = pd.getString("pageSize");
			int pNo = TextUtil.isNull(pageNo) ? 1 : Integer.parseInt(pageNo);
			int pSize = TextUtil.isNull(pageSize) ? 10 : Integer.parseInt(pageSize);
			PageHelper.startPage(pNo, pSize);
			result = new PageInfo(reportService.customerResourceDetail(pd));
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

	// =======销售额排名报表=================
	@RequestMapping(value = "xspmReport")
	@ResponseBody
	public Object xspmReport() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = "";
		String error = "";
		List<PageData> result = null;
		BigDecimal htzje = new BigDecimal(0.0);
		BigDecimal htzsl = new BigDecimal(0.0);
		try {
			PageData pd = this.getPageData();
			User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
			if (isRole(Const.ROLE_MANAGER)) {
				pd.put("managerId", user.getUSER_ID());
			}
			result = reportService.showXspmReport(pd);
			for (PageData cus : result) {
				Object money = cus.get("price");
				if (null == money) {
					money = "0";
				}
				htzje = htzje.add(new BigDecimal(Double.valueOf(money.toString())));
				Object money1 = cus.get("sl");
				if (null == money1) {
					money1 = "0";
				}
				htzsl = htzsl.add(new BigDecimal(Double.valueOf(money1.toString())));
			}
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
			map.put("htzje", htzje.setScale(2, BigDecimal.ROUND_HALF_UP));
			map.put("htzsl", htzsl.setScale(2, BigDecimal.ROUND_HALF_UP));
		}
		return AppUtil.returnObject(new PageData(), map);
	}

	@RequestMapping(value = "xspmReportDetail")
	@ResponseBody
	public Object xspmReportDetail() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = "";
		String error = "";
		PageInfo<List<PageData>> result = null;
		BigDecimal htzje = new BigDecimal(0.0);
		BigDecimal qkzje = new BigDecimal(0.0);
		BigDecimal hkzje = new BigDecimal(0.0);
		BigDecimal syzje = new BigDecimal(0.0);
		try {
			PageData pd = this.getPageData();
			String pageNo = pd.getString("page");
			String pageSize = pd.getString("pageSize");
			int pNo = TextUtil.isNull(pageNo) ? 1 : Integer.parseInt(pageNo);
			int pSize = TextUtil.isNull(pageSize) ? 10 : Integer.parseInt(pageSize);
			PageHelper.startPage(pNo, pSize);
			result = new PageInfo(reportService.showXspmReportDetail(pd));
			List<PageData> list = reportService.showXspmReportDetail(pd);
			if ("1".equals(pd.getString("flag"))) {
				for (PageData cus : list) {
					Object money = cus.get("price");
					if (TextUtil.isNull(money.toString())) {
						money = "0";
					}
					htzje = htzje.add(new BigDecimal(Double.valueOf(money.toString())));
				}
			} else {
				for (PageData cus : list) {
					qkzje = qkzje.add(new BigDecimal(Double.valueOf(cus.get("debtAmount").toString())));
					syzje = syzje.add(new BigDecimal(Double.valueOf(cus.get("syMoney").toString())));
					hkzje = hkzje.add(new BigDecimal(Double.valueOf(cus.get("totalMoney").toString())));
				}
			}
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
			map.put("htzje", htzje.setScale(2, BigDecimal.ROUND_HALF_UP));
			map.put("qkzje", qkzje.setScale(2, BigDecimal.ROUND_HALF_UP));
			map.put("hkzje", hkzje.setScale(2, BigDecimal.ROUND_HALF_UP));
			map.put("syzje", syzje.setScale(2, BigDecimal.ROUND_HALF_UP));
		}
		return AppUtil.returnObject(new PageData(), map);
	}

	// =============回款记录报表============
	@RequestMapping(value = "hkjlReport")
	@ResponseBody
	public Object hkjlReport() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = "";
		String error = "";
		List<PageData> result = null;
		BigDecimal yqtotalMoney = new BigDecimal(0.0);
		BigDecimal sjtotalMoney = new BigDecimal(0.0);
		try {
			PageData pd = this.getPageData();
			result = reportService.showHkjlReport(pd);
			for (PageData cus : result) {
				Object money = cus.get("yqMoney");
				if (null == money) {
					money = "0";
				}
				yqtotalMoney = yqtotalMoney.add(new BigDecimal(Double.valueOf(money.toString())));
				Object money1 = cus.get("sjMoney");
				if (null == money1) {
					money1 = "0";
				}
				sjtotalMoney = sjtotalMoney.add(new BigDecimal(Double.valueOf(money1.toString())));
			}
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
			map.put("yqtotalMoney", yqtotalMoney.setScale(2, BigDecimal.ROUND_HALF_UP));
			map.put("sjtotalMoney", sjtotalMoney.setScale(2, BigDecimal.ROUND_HALF_UP));
		}
		return AppUtil.returnObject(new PageData(), map);
	}

	@RequestMapping(value = "hkjlReportDeatil")
	@ResponseBody
	public Object hkjlReportDeatil() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = "";
		String error = "";
		PageInfo<List<PageData>> result = null;
		BigDecimal yjhkje = new BigDecimal(0.0);
		BigDecimal yjdkje = new BigDecimal(0.0);
		BigDecimal hkje = new BigDecimal(0.0);
		BigDecimal dkje = new BigDecimal(0.0);
		PageData pd = this.getPageData();
		try {
			String pageNo = pd.getString("page");
			String pageSize = pd.getString("pageSize");
			int pNo = TextUtil.isNull(pageNo) ? 1 : Integer.parseInt(pageNo);
			int pSize = TextUtil.isNull(pageSize) ? 10 : Integer.parseInt(pageSize);
			PageHelper.startPage(pNo, pSize);
			result = new PageInfo(reportService.showHkjlReportDetail(pd));
			List<PageData> list = reportService.showHkjlReportDetail(pd);
			for (PageData cus : list) {
				if ("1".equals(pd.getString("flag"))) {
					Object money = cus.get("hkMoney");
					if (null == money) {
						money = "0";
					}
					yjhkje = yjhkje.add(new BigDecimal(Double.valueOf(money.toString())));

					Object money1 = cus.get("ygdkje");
					if (null == money1) {
						money1 = "0";
					}
					yjdkje = yjdkje.add(new BigDecimal(Double.valueOf(money1.toString())));
				} else {
					Object money = cus.get("hkMoney");
					if (null == money) {
						money = "0";
					}
					hkje = hkje.add(new BigDecimal(Double.valueOf(money.toString())));

					Object money1 = cus.get("dkje");
					if (null == money1) {
						money1 = "0";
					}
					dkje = dkje.add(new BigDecimal(Double.valueOf(money1.toString())));
				}
			}
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

			if ("1".equals(pd.getString("flag"))) {
				map.put("yjhkje", yjhkje.setScale(2, BigDecimal.ROUND_HALF_UP));
				map.put("yjdkje", yjdkje.setScale(2, BigDecimal.ROUND_HALF_UP));
			} else {
				map.put("hkje", hkje.setScale(2, BigDecimal.ROUND_HALF_UP));
				map.put("dkje", dkje.setScale(2, BigDecimal.ROUND_HALF_UP));
			}

		}
		return AppUtil.returnObject(new PageData(), map);
	}

	/**
	 * 保存销售报告
	 */
	@RequestMapping(value = "/saveReportData")
	@ResponseBody
	public Object saveMonthData(HttpServletRequest request, HttpServletResponse response, @RequestBody String mydomin) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = null;
		String error = null;
		try {
			User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
			Gson gson = new Gson();
			Type type = new TypeToken<Map<Object, Object>>() {
			}.getType();
			Map<Object, Object> map1 = gson.fromJson(mydomin, type);
			PageData pd = this.getPageData();
			pd.put("content", map1.get("content"));
			pd.put("plan", map1.get("plan"));
			pd.put("readMan", map1.get("readMan"));
			pd.put("readManName", map1.get("readManName"));
			pd.put("type", Double.valueOf(map1.get("type").toString()).intValue());
			pd.put("userId", user.getUSER_ID());

			// 周报
			if ("0".equals(pd.get("type").toString())) {
				pd.put("zbtimeStart", map1.get("time").toString().split("~")[0].trim());
				pd.put("zbtimeEnd", map1.get("time").toString().split("~")[1].trim());
			}

			// 月报
			if ("1".equals(pd.get("type").toString())) {
				pd.put("time", map1.get("time"));
			}

			@SuppressWarnings("unchecked")
			List<String> userIds = (List<String>) map1.get("copyMan");
			@SuppressWarnings("unchecked")
			List<String> copyManNames = (List<String>) map1.get("copyManName");
			String userId = "";
			String copyManName = "";
			for (int i = 0; i < userIds.size() && userIds.size() == copyManNames.size(); i++) {
				userId += userIds.get(i) + ",";
				copyManName += copyManNames.get(i) + ",";
			}
			if (TextUtil.isNotNull(userId)) {
				pd.put("copyMan", userId.substring(0, userId.length() - 1));
				pd.put("copyManName", copyManName.substring(0, copyManName.length() - 1));
			}
			reportService.saveReportData(pd);

			PageData pd4 = new PageData();
			pd4.put("userId", pd.get("readMan"));
			pd4.put("type", 1);
			pd4.put("relateId", pd.get("id"));
			pd4.put("content", user.getNAME() + "提交了一份" + ("0".equals(pd.get("type").toString()) ? "周" : "月") + "报需要批阅");
			pd4.put("flag", 0);
			customerService.saveNoticInfo(pd4);
			systemWebSocketHandler.sendMessageToUser(pd.get("readMan").toString(), new TextMessage("notic:" + pd4.get("id")));

			for (int i = 0; i < userIds.size(); i++) {
				/*userId += userIds.get(i) + ",";*/
				// 保存通知
				PageData pd3 = new PageData();
				pd3.put("userId", userIds.get(i));
				pd3.put("type", 1);
				pd3.put("relateId", pd.get("id"));
				pd3.put("content", user.getNAME() + "提交了一份" + ("0".equals(pd.get("type").toString()) ? "周" : "月") + "报需要点评");
				pd3.put("flag", 0);
				customerService.saveNoticInfo(pd3);
				systemWebSocketHandler.sendMessageToUser(userIds.get(i), new TextMessage("notic:" + pd3.get("id")));
			}
			// 保存附件
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> list = (List<Map<String, Object>>) map1.get("files");
			for (Map<String, Object> map2 : list) {
				map2.put("expressId", pd.get("id"));
				map2.put("type", 15);
			}
			if (list.size() > 0) {
				expressService.saveAttach(list);
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
			map.put("result", "");
		}
		return AppUtil.returnObject(new PageData(), map);
	}

	/**
	 * 修改销售报告
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateReportData")
	@ResponseBody
	public Object saveReportData(HttpServletRequest request, HttpServletResponse response, @RequestBody String mydomin) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
		PageData result = null;
		String msg = null;
		String error = null;
		try {
			Gson gson = new Gson();
			Type type = new TypeToken<Map<Object, Object>>() {
			}.getType();
			Map<Object, Object> map1 = gson.fromJson(mydomin, type);
			PageData pd = this.getPageData();
			pd.put("type", Double.valueOf(map1.get("type").toString()).intValue());
			pd.put("content", map1.get("content"));
			pd.put("readMan", map1.get("readMan"));
			pd.put("readManName", map1.get("readManName"));
			pd.put("content", map1.get("content"));
			pd.put("plan", map1.get("plan"));
			pd.put("id", Double.valueOf(map1.get("id").toString()).intValue());
			List<String> userIds = (List<String>) map1.get("copyMan");
			List<String> copyManNames = (List<String>) map1.get("copyManName");
			String userId = "";
			String copyManName = "";
			for (int i = 0; i < userIds.size() && userIds.size() == copyManNames.size(); i++) {
				userId += userIds.get(i) + ",";
				copyManName += copyManNames.get(i) + ",";
			}
			if (TextUtil.isNotNull(userId)) {
				pd.put("copyMan", userId.substring(0, userId.length() - 1));
				pd.put("copyManName", copyManName.substring(0, copyManName.length() - 1));
			} else {
				pd.put("copyMan", "");
				pd.put("copyManName", "");
			}
			reportService.updateReportData(pd);

			PageData pd4 = new PageData();
			pd4.put("userId", pd.get("readMan"));
			pd4.put("type", 1);
			pd4.put("relateId", pd.get("id"));
			pd4.put("content", user.getNAME() + "提交了一份" + ("0".equals(pd.get("type").toString()) ? "周" : "月") + "报需要批阅");
			pd4.put("flag", 0);
			customerService.saveNoticInfo(pd4);
			systemWebSocketHandler.sendMessageToUser(pd.get("readMan").toString(), new TextMessage("notic:" + pd4.get("id")));

			for (int i = 0; i < userIds.size(); i++) {
				/*userId += userIds.get(i) + ",";*/
				// 保存通知
				PageData pd3 = new PageData();
				pd3.put("userId", userIds.get(i));
				pd3.put("type", 1);
				pd3.put("relateId", pd.get("id"));
				pd3.put("content", user.getNAME() + "提交了一份" + ("0".equals(pd.get("type").toString()) ? "周" : "月") + "报需要点评");
				pd3.put("flag", 0);
				customerService.saveNoticInfo(pd3);
				systemWebSocketHandler.sendMessageToUser(userIds.get(i), new TextMessage("notic:" + pd3.get("id")));
			}

			reportService.deleteAboutAttach(pd);
			List<Map<String, Object>> list = (List<Map<String, Object>>) map1.get("files");
			for (Map<String, Object> map2 : list) {
				map2.put("expressId", pd.get("id"));
				map2.put("type", 15);
			}
			if (list.size() > 0) {
				expressService.saveAttach(list);
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

	// 报告展示
	@RequestMapping(value = "/showReportInfo")
	@ResponseBody
	public Object showReportInfo() {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = "";
		String error = "";
		List<Map<Object, Object>> result = new ArrayList<Map<Object, Object>>();
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
		try {
			PageData pd = this.getPageData();
			Set<String> set = new HashSet<String>();
			pd.put("userId1", user.getUSER_ID());
			List<PageData> result1 = reportService.showReportInfo(pd);
			for (PageData reportInfo : result1) {
				if ("0".equals(pd.getString("type"))) {
					set.add(reportInfo.get("zbtimeStart").toString() + "~" + reportInfo.get("zbtimeEnd").toString());
				} else {
					set.add(reportInfo.get("time").toString());
				}
			}
			if ("0".equals(pd.getString("type"))) {
				Iterator<String> iterator = set.iterator();
				while (iterator.hasNext()) {
					Map<Object, Object> map1 = new HashMap<Object, Object>();
					String time = iterator.next();
					map1.put("time", time);
					List<PageData> result2 = new ArrayList<PageData>();
					for (PageData reportInfo : result1) {
						if (time.indexOf(reportInfo.get("zbtimeStart").toString()) > -1) {
							result2.add(reportInfo);
						}
					}
					map1.put("reportInfo", result2);
					result.add(map1);
				}

				Collections.sort(result, new Comparator<Map<Object, Object>>() {

					@Override
					public int compare(Map<Object, Object> o1, Map<Object, Object> o2) {
						String time1 = o1.get("time").toString().split("~")[0];
						String time2 = o2.get("time").toString().split("~")[0];
						return time2.compareTo(time1);
					}
				});

			}

			if ("1".equals(pd.getString("type"))) {
				Iterator<String> iterator = set.iterator();
				while (iterator.hasNext()) {
					Map<Object, Object> map1 = new HashMap<Object, Object>();
					String time = iterator.next();
					map1.put("time", time);
					List<PageData> result2 = new ArrayList<PageData>();
					for (PageData reportInfo : result1) {
						if (time.indexOf(reportInfo.get("time").toString()) > -1) {
							result2.add(reportInfo);
						}
					}
					map1.put("reportInfo", result2);
					result.add(map1);
				}

				Collections.sort(result, new Comparator<Map<Object, Object>>() {

					@Override
					public int compare(Map<Object, Object> o1, Map<Object, Object> o2) {
						String time1 = o1.get("time").toString();
						String time2 = o2.get("time").toString();
						return time2.compareTo(time1);
					}
				});
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
			map.put("result", result.subList(0, result.size() > 10 ? 10 : result.size()));
		}
		return AppUtil.returnObject(new PageData(), map);
	}

	// 查看报告详情
	@RequestMapping(value = "/showReportInfoDetail")
	@ResponseBody
	public Object showReportInfoDetail() {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = "";
		String error = "";
		PageData result = null;
		try {
			PageData pd = this.getPageData();
			String startTime = pd.getString("startTime"); // 开始时间
			String endTime = pd.getString("endTime"); // 结束时间
			if (TextUtil.isNotNull(startTime)) {
				pd.put("startTime", startTime + " 00:00:00");
			}
			if (TextUtil.isNotNull(endTime)) {
				pd.put("endTime", endTime + " 23:59:59");
			}
			result = reportService.findById(pd);
			/*
						@SuppressWarnings("unchecked")
						List<PageData> list = (List<PageData>) result.get("evaluate");
						List<PageData> parentList = new ArrayList<PageData>();
						for (PageData p : list) {
							if (null == p.get("parentId")) {
								parentList.add(p);
							} else if ("0".equals(p.get("parentId").toString())) {
								parentList.add(p);
							}
						}
						result.put("evaluate", diGUi(list, parentList));*/
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

	/*public List<PageData> diGUi(List<PageData> list, List<PageData> parentList) {
		for (int i = 0; i < parentList.size(); i++) {
			List<PageData> list1 = new ArrayList<PageData>();
			for (int j = 0; j < list.size(); j++) {
				if (list.get(j).get("parentId").toString().equals(parentList.get(i).get("id").toString())) {
					list1.add(list.get(j));
				}
			}
			list.removeAll(list1);
			parentList.get(i).put("children", list1.size() > 0 ? diGUi(list, list1) : list1);
		}
		return parentList;
	}*/

	// 查看日期
	@RequestMapping(value = "/showReportDate")
	@ResponseBody
	public Object showReportDate() {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = "";
		String error = "";
		List<String> result = new ArrayList<String>();
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
		try {
			PageData pd = this.getPageData();
			pd.put("userId", user.getUSER_ID());
			List<PageData> showReportInfo = reportService.showReportInfo(pd);
			TimeUtil tu = new TimeUtil();
			String times = "";
			if ("0".equals(pd.getString("type")) && TextUtil.isNull(pd.getString("flag"))) {
				for (PageData time : showReportInfo) {
					times = times + time.getString("zbtimeStart").toString() + ",";
				}

				// 列出这个月的周日期
				for (int i = 0; i < 6; i++) {
					int mondayPlus = tu.getMondayPlus();
					GregorianCalendar currentDate = new GregorianCalendar();
					currentDate.add(GregorianCalendar.DATE, mondayPlus - 7 * i);
					Date monday = currentDate.getTime();
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					String preMonday = df.format(monday);

					GregorianCalendar currentDate1 = new GregorianCalendar();
					currentDate1.add(GregorianCalendar.DATE, mondayPlus - 1 - ((i - 1) * 7));
					Date monday1 = currentDate1.getTime();
					DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
					String preMonday1 = df1.format(monday1);

					if (times.indexOf(preMonday) < 0) {
						result.add(preMonday.toString().trim() + "~" + preMonday1.toString().trim());
					}
				}
			}

			if ("0".equals(pd.getString("type")) && TextUtil.isNotNull(pd.getString("flag"))) {

				// 列出这个月的周日期
				for (int i = 0; i < 6; i++) {
					int mondayPlus = tu.getMondayPlus();
					GregorianCalendar currentDate = new GregorianCalendar();
					currentDate.add(GregorianCalendar.DATE, mondayPlus - 7 * i);
					Date monday = currentDate.getTime();
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					String preMonday = df.format(monday);

					GregorianCalendar currentDate1 = new GregorianCalendar();
					currentDate1.add(GregorianCalendar.DATE, mondayPlus - 1 - ((i - 1) * 7));
					Date monday1 = currentDate1.getTime();
					DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
					String preMonday1 = df1.format(monday1);

					result.add(preMonday.toString().trim() + "~" + preMonday1.toString().trim());
				}
			}

			if ("1".equals(pd.getString("type"))) {
				for (PageData time : showReportInfo) {
					times = times + time.getString("time").toString() + ",";
				}
				// 列出这个月的月报
				for (int i = 0; i < 6; i++) {
					GregorianCalendar currentDate = new GregorianCalendar();
					currentDate.add(GregorianCalendar.MONTH, 0 - i);
					Date monday = currentDate.getTime();
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
					String preMonday = df.format(monday);
					if (times.indexOf(preMonday) < 0) {
						result.add(preMonday.toString().trim());
					}
				}
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

	// 保存批阅、点评(saleReportId)
	@RequestMapping(value = "/saveEvaluate")
	@ResponseBody
	public Object saveEvaluate() throws Exception {
		String error = "";
		String msg = "";
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			PageData pd = this.getPageData();
			pd.put("id", pd.getString("saleReportId"));
			User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
			pd.put("userId", user.getUSER_ID());
			PageData pd1 = reportService.findById(pd);
			if (user.getUSER_ID().trim().equals(pd1.getString("readMan").trim()) && "0".equals(pd1.get("isDo").toString())) {
				pd.put("type", 0); // 批阅
				pd.put("isDo", 1);
				pd.put("editTime", DateUtil.getCurrentTime());
				reportService.updateReportData(pd);
			} else {
				pd.put("type", 1); // 点评
			}
			customerService.saveEvaluate(pd);

			PageData pd4 = new PageData();
			pd4.put("userId", pd1.get("userId"));
			pd4.put("type", 3);
			pd4.put("relateId", pd.get("id"));
			pd4.put("content", "您提交的一份" + ("0".equals(pd1.get("type").toString()) ? "周报" : "月报") + "已被" + user.getNAME() + ("0".equals(pd.get("type").toString()) ? "批阅" : "点评"));
			pd4.put("flag", 0);
			customerService.saveNoticInfo(pd4);
			systemWebSocketHandler.sendMessageToUser(pd1.get("userId").toString(), new TextMessage("notic:" + pd4.get("id")));

			error = "00";
			msg = "保存成功";
		} catch (Exception e) {
			error = "500";
			msg = "程序异常";
			e.printStackTrace();
		} finally {
			map.put("error", error);
			map.put("msg", msg);
			map.put("result", "");
		}
		return AppUtil.returnObject(new PageData(), map);
	}

	// 删除报告
	@RequestMapping(value = "/deleteSaleReport")
	@ResponseBody
	public Object deleteSaleReport() throws Exception {
		String error = "";
		String msg = "";
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			PageData pd = this.getPageData();
			customerService.deleteSaleReport(pd);
			error = "00";
			msg = "删除成功";
		} catch (Exception e) {
			error = "500";
			msg = "程序异常";
			e.printStackTrace();
		} finally {
			map.put("error", error);
			map.put("msg", msg);
			map.put("result", "");
		}
		return AppUtil.returnObject(new PageData(), map);
	}

	// result:[{"time:2012-01-01~2012-01-07,data:[{},{},{},{}]"},{}...]
	@RequestMapping(value = "/reportTj")
	@ResponseBody
	public Object reportTj() throws Exception {
		String error = "";
		String msg = "";
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			PageData pd = this.getPageData();
			String time = pd.getString("time");
			if ("0".equals(pd.getString("flag"))) {
				List<PageData> yData = new ArrayList<PageData>();
				for (int i = 0; i < 5; i++) {
					Map<String, Object> hasDay = hasDay(time);
					GregorianCalendar currentDate = (GregorianCalendar) hasDay.get("df");
					currentDate.add(GregorianCalendar.DATE, (Integer) hasDay.get("time") - 7 * i);
					Date monday = currentDate.getTime();
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					String preMonday = df.format(monday);
					Map<String, Object> hasDay1 = hasDay(time);
					GregorianCalendar currentDate1 = (GregorianCalendar) hasDay1.get("df");
					currentDate1.add(GregorianCalendar.DATE, (Integer) hasDay1.get("time") - 1 - ((i - 1) * 7));
					Date monday1 = currentDate1.getTime();
					DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
					String preMonday1 = df1.format(monday1);

					System.out.println(preMonday + "====" + preMonday1);
					pd.put("type", 0);
					pd.put("time", preMonday);
					yData = reportService.ReportTj(pd);
					Map<String, Object> map1 = new HashMap<String, Object>();
					map1.put("time", preMonday + "~" + preMonday1);
					map1.put("data", yData);
					result.add(map1);
				}

			}
			if ("1".equals(pd.getString("flag"))) {
				List<PageData> yData = new ArrayList<PageData>();
				for (int i = 0; i < 12; i++) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
					Calendar lastDate = Calendar.getInstance();
					lastDate.setTime(sdf.parse(time + "-12"));
					lastDate.add(GregorianCalendar.MONTH, 0 - i);
					String preMonday = sdf.format(lastDate.getTime());
					System.out.println(preMonday);
					pd.put("time", preMonday);
					pd.put("type", 1);
					yData = reportService.ReportTj(pd);
					Map<String, Object> map1 = new HashMap<String, Object>();
					map1.put("time", preMonday);
					map1.put("data", yData);
					result.add(map1);
				}

			}

			error = "00";
			msg = "查看成功";
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

	public static Map<String, Object> hasDay(String time) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		int mondayPlus1 = 0;
		Calendar lastDate = Calendar.getInstance();
		lastDate.setTime(sdf.parse(time));
		lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		lastDate.roll(Calendar.DATE, -1);// 日期回滚一天，也就是本月最后一天
		int dayOfWeek = lastDate.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
		if (dayOfWeek == 1) {
			mondayPlus1 = 0;
		} else {
			mondayPlus1 = 1 - dayOfWeek;
		}
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("df", lastDate);
		map1.put("time", mondayPlus1);
		return map1;
	}

	@RequestMapping(value = "/saveDpEvaluate")
	@ResponseBody
	public Object saveDpEvaluate() throws Exception {
		String error = "";
		String msg = "";
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			PageData pd = this.getPageData();
			pd.put("id", pd.getString("saleReportId"));
			User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
			pd.put("userId", user.getUSER_ID());
			reportService.saveDpEvaluate(pd);
			PageData pd4 = new PageData();
			pd4.put("userId", pd.get("dpUserId"));
			pd4.put("type", 4);
			pd4.put("relateId", pd.get("id"));
			pd4.put("content", "您收到一条回复");
			pd4.put("flag", 0);
			customerService.saveNoticInfo(pd4);
			systemWebSocketHandler.sendMessageToUser(pd.get("dpUserId").toString(), new TextMessage("notic:" + pd4.get("id")));
			systemWebSocketHandler.sendMessageToUser(pd.get("reportUserId").toString(), new TextMessage("notic:" + pd4.get("id")));
			error = "00";
			msg = "保存成功";
		} catch (Exception e) {
			error = "500";
			msg = "程序异常";
			e.printStackTrace();
		} finally {
			map.put("error", error);
			map.put("msg", msg);
			map.put("result", "");
		}
		return AppUtil.returnObject(new PageData(), map);
	}

	// 商务顾问业绩目标完成度报表
	@RequestMapping(value = "/showSwgwReport")
	@ResponseBody
	public Object showSwgwReport() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = "";
		String error = "";
		List<PageData> result = new ArrayList<PageData>();
		try {
			PageData pd = this.getPageData();

			String startTime = pd.getString("startTime"); // 开始时间
			String endTime = pd.getString("endTime"); // 结束时间
			if (TextUtil.isNotNull(startTime)) {
				pd.put("startTime", startTime + " 00:00:00");
			}
			if (TextUtil.isNotNull(endTime)) {
				pd.put("endTime", endTime + " 23:59:59");
			}
			result = reportService.showSwgwYjmb(pd);
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

	// 商务顾问业绩目标完成度报表
	@RequestMapping(value = "/showSwgwVisitReport")
	@ResponseBody
	public Object showSwgwVisitReport() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = "";
		String error = "";
		List<PageData> result = new ArrayList<PageData>();
		try {
			PageData pd = this.getPageData();

			String startTime = pd.getString("startTime"); // 开始时间
			String endTime = pd.getString("endTime"); // 结束时间
			if (TextUtil.isNotNull(startTime)) {
				pd.put("startTime", startTime + " 00:00:00");
			}
			if (TextUtil.isNotNull(endTime)) {
				pd.put("endTime", endTime + " 23:59:59");
			}
			result = reportService.showSwgwVisitReport(pd);
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

	@RequestMapping(value = "/showImportantCustomerResourceDetail")
	@ResponseBody
	public Object showSwgwVisitReportDetail() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = "";
		String error = "";
		PageInfo<PageData> page = null;
		BigDecimal qkzje = new BigDecimal(0.0);
		BigDecimal hkzje = new BigDecimal(0.0);
		BigDecimal syzje = new BigDecimal(0.0);
		try {
			PageData pd = this.getPageData();

			/*String startTime = pd.getString("startTime"); // 开始时间
			String endTime = pd.getString("endTime"); // 结束时间
			if (TextUtil.isNotNull(startTime)) {
				pd.put("startTime", startTime + " 00:00:00");
			}
			if (TextUtil.isNotNull(endTime)) {
				pd.put("endTime", endTime + " 23:59:59");
			}*/

			String pageNo = pd.getString("page");
			String pageSize = pd.getString("pageSize");
			int pNo = TextUtil.isNull(pageNo) ? 1 : Integer.parseInt(pageNo);
			int pSize = TextUtil.isNull(pageSize) ? 10 : Integer.parseInt(pageSize);
			PageHelper.startPage(pNo, pSize);
			page = new PageInfo<PageData>(reportService.showSwgwVisitReportDetail(pd));

			// 订单金额
			if ("1".equals(pd.getString("type"))) {
				List<PageData> listAllPage = reportService.showSwgwVisitReportDetail(pd);
				qkzje = new BigDecimal(0.0);
				hkzje = new BigDecimal(0.0);
				syzje = new BigDecimal(0.0);
				for (PageData cus : listAllPage) {
					qkzje = qkzje.add(new BigDecimal(Double.valueOf(cus.get("debtAmount").toString())));
					syzje = syzje.add(new BigDecimal(Double.valueOf(cus.get("syMoney").toString())));
					hkzje = hkzje.add(new BigDecimal(Double.valueOf(cus.get("totalMoney").toString())));
				}
			}

			// 到款金额
			if ("0".equals(pd.getString("type"))) {
				List<PageData> listAllPage = reportService.showSwgwVisitReportDetail(pd);
				// List<PageData> listAllPage1 = orderService.listAll1(pd);
				for (PageData cus : listAllPage) {
					syzje = syzje.add(new BigDecimal(Double.valueOf(cus.get("syMoney").toString())));
					hkzje = hkzje.add(new BigDecimal(Double.valueOf(cus.get("totalMoney").toString())));
				}
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
			map.put("result", page);
			map.put("qkzje", qkzje.setScale(2, BigDecimal.ROUND_HALF_UP));
			map.put("syzje", syzje.setScale(2, BigDecimal.ROUND_HALF_UP));
			map.put("hkzje", hkzje.setScale(2, BigDecimal.ROUND_HALF_UP));
		}
		return AppUtil.returnObject(new PageData(), map);
	}

	// 重要客户排名报表
	@RequestMapping(value = "/showImportantCustomer")
	@ResponseBody
	public Object showImportantCustomer() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = "";
		String error = "";
		List<PageData> result = new ArrayList<PageData>();
		try {
			PageData pd = this.getPageData();

			/*String startTime = pd.getString("startTime"); // 开始时间
			String endTime = pd.getString("endTime"); // 结束时间
			if (TextUtil.isNotNull(startTime)) {
				pd.put("startTime", startTime + " 00:00:00");
			}
			if (TextUtil.isNotNull(endTime)) {
				pd.put("endTime", endTime + " 23:59:59");
			}*/
			result = reportService.showImportantCustomer(pd);
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

	// 重要客户排名报表
	@RequestMapping(value = "/showImportantCustomerResource")
	@ResponseBody
	public Object showImportantCustomerResource() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = "";
		String error = "";
		List<PageData> result = new ArrayList<PageData>();
		try {
			PageData pd = this.getPageData();

			String startTime = pd.getString("startTime"); // 开始时间
			String endTime = pd.getString("endTime"); // 结束时间
			if (TextUtil.isNotNull(startTime)) {
				pd.put("startTime", startTime + " 00:00:00");
			}
			if (TextUtil.isNotNull(endTime)) {
				pd.put("endTime", endTime + " 23:59:59");
			}
			result = reportService.showImportantCustomerResource(pd);
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

	// 重要客户排名报表
	@RequestMapping(value = "/showDxyjReport")
	@ResponseBody
	public Object showDxyjReport() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = "";
		String error = "";
		List<PageData> result = new ArrayList<PageData>();
		try {
			PageData pd = this.getPageData();
			/*String startTime = pd.getString("startTime"); // 开始时间
			String endTime = pd.getString("endTime"); // 结束时间
			if (TextUtil.isNotNull(startTime)) {
				pd.put("startTime", startTime + " 00:00:00");
			}
			if (TextUtil.isNotNull(endTime)) {
				pd.put("endTime", endTime + " 23:59:59");
			}*/

			result = reportService.showDxyjReport(pd);
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

	@RequestMapping(value = "/showDxyjReportDetail")
	@ResponseBody
	public Object showDxyjReportDetail() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = "";
		String error = "";
		PageInfo<PageData> page = null;
		try {
			PageData pd = this.getPageData();

			/*String startTime = pd.getString("startTime"); // 开始时间
			String endTime = pd.getString("endTime"); // 结束时间
			if (TextUtil.isNotNull(startTime)) {
				pd.put("startTime", startTime + " 00:00:00");
			}
			if (TextUtil.isNotNull(endTime)) {
				pd.put("endTime", endTime + " 23:59:59");
			}*/
			String pageNo = pd.getString("pageNo");
			String pageSize = pd.getString("pageSize");
			int pNo = TextUtil.isNull(pageNo) ? 1 : Integer.parseInt(pageNo);
			int pSize = TextUtil.isNull(pageSize) ? 10 : Integer.parseInt(pageSize);
			PageHelper.startPage(pNo, pSize); // 核心分页代码
			page = new PageInfo<PageData>(reportService.showDxyjReportDetail(pd));// 列出任务列表
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
