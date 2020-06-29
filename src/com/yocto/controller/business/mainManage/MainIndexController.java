package com.yocto.controller.business.mainManage;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
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
import com.yocto.service.business.yjService.IYjService;
import com.yocto.service.system.user.UserManager;
import com.yocto.util.AppUtil;
import com.yocto.util.Const;
import com.yocto.util.Jurisdiction;
import com.yocto.util.PageData;
import com.yocto.util.TextUtil;
import com.yocto.util.TimeUtil;

/**
 * 
 * @author xl 第三期首页
 */
@Controller
@RequestMapping(value = "/mainIndex")
public class MainIndexController extends BaseController {

	@Resource(name = "mainService")
	private IMainService mainService;

	@Resource(name = "customerService")
	private ICustomerService customerService;

	@Resource(name = "yjService")
	private IYjService yjService;

	@Resource(name = "userService")
	private UserManager userService;

	/**
	 * 已成交客户数、订单数量、订单总金额、已回款金额、到款金额
	 * 
	 * @return
	 */
	@RequestMapping(value = "/showSaleYjtj")
	@ResponseBody
	public Object showSaleYjtj() {
		Map<String, Object> map = new HashMap<String, Object>();
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
		String msg = "";
		String error = "";
		PageData result = new PageData();
		try {
			PageData pd = this.getPageData();
			// 经理销售业绩统计

			if ("1".equals(this.getGroup())) {
				if (isRole(Const.ROLE_PHONE_SALES_DIRECTOR)) {
					pd.put("dxzgId", user.getUSER_ID());
				} else {
					pd.put("dfSaleId", user.getUSER_ID());
				}
			}
			if ("2".equals(this.getGroup())) {
				pd.put("saleId", user.getUSER_ID());
				if (isRole(Const.ROLE_SALES_ELITEDX)) {
					pd.put("marks", "1");
				}
			}
			String time = pd.getString("time"); // 开始时间
			if (TextUtil.isNotNull(time)) {
				pd.put("startTime", time + "-04-01 00:00:00");
				pd.put("endTime", (Integer.valueOf(time) + 1) + "-03-31 23:59:59");
			} else {
				String startTime = pd.getString("startTime"); // 开始时间
				String endTime = pd.getString("endTime"); // 结束时间
				if (TextUtil.isNotNull(startTime)) {
					pd.put("startTime", startTime + " 00:00:00");
				}
				if (TextUtil.isNotNull(endTime)) {
					pd.put("endTime", endTime + " 23:59:59");
				}
				// 查找意向客户数
				PageData yxkhs = mainService.findyyx(pd);
				result.put("yxkhs", yxkhs.get("khs"));
			}
			// 已成交客户数
			pd.put("flag", 1);
			List<PageData> list = mainService.showCustomer(pd);
			result.put("ycjkh", list.size());
			// 订单数量、订单总金额、
			BigDecimal qkzje = new BigDecimal(0.0);
			pd.put("flag", 2);
			List<PageData> list1 = mainService.showCustomer(pd);
			for (PageData cus : list1) {
				qkzje = qkzje.add(new BigDecimal(Double.valueOf(cus.get("debtAmount").toString())));
			}
			if (TextUtil.isNotNull(time)) {
				result.put("ddsl", list1.size());
			}
			result.put("ddzje", qkzje.setScale(2, BigDecimal.ROUND_HALF_UP));
			// 已回款金额、到款金额
			pd.put("flag", 3);
			BigDecimal hkje = new BigDecimal(0.0).setScale(2, BigDecimal.ROUND_HALF_UP);
			BigDecimal yjje = new BigDecimal(0.0).setScale(2, BigDecimal.ROUND_HALF_UP);
			List<PageData> listAllPage = mainService.showCustomer(pd);
			for (PageData cus : listAllPage) {
				hkje = hkje.add(new BigDecimal(Double.valueOf(cus.get("money").toString())));
				if (null != cus.get("commissionaMount")) {
					yjje = yjje.add(new BigDecimal(Double.valueOf(cus.get("commissionaMount").toString())));
				}
			}
			result.put("hkje", hkje.setScale(2, BigDecimal.ROUND_HALF_UP));
			result.put("yjje", yjje.setScale(2, BigDecimal.ROUND_HALF_UP));

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

	// 商务顾问销售统计 已成交客户数、订单数量、订单总金额、到款金额
	@RequestMapping(value = "/showSwgwXstj")
	@ResponseBody
	public Object showSwgwXstj() {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = "";
		String error = "";
		List<PageData> result = new ArrayList<PageData>();
		try {
			PageData pd = this.getPageData();
			// 经理销售业绩统计
			if (isRole(Const.ROLE_MANAGER)) {
				String time = pd.getString("time"); // 开始时间
				if (TextUtil.isNotNull(time)) {
					pd.put("startTime", time + "-04-01 00:00:00");
					pd.put("endTime", (Integer.valueOf(time) + 1) + "-03-31 23:59:59");
				}
				// 已成交客户数
				result = mainService.showSwgwXstj(pd);
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

	// 商务顾问业绩目标完成情况
	@RequestMapping(value = "/showSwgwYjmb")
	@ResponseBody
	public Object showSwgwYjmb() {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = "";
		String error = "";
		List<PageData> result = new ArrayList<PageData>();
		PageData result2 = new PageData();
		try {
			PageData pd = this.getPageData();
			pd.put("mark", "1");
			// 经理销售业绩统计
			if (isRole(Const.ROLE_MANAGER)) {
				String startTime = pd.getString("startTime"); // 开始时间
				String endTime = pd.getString("endTime"); // 结束时间
				if (TextUtil.isNotNull(startTime)) {
					pd.put("startTime", startTime + " 00:00:00");
				}
				if (TextUtil.isNotNull(endTime)) {
					pd.put("endTime", endTime + " 23:59:59");
				}
				result = mainService.showSwgwYjmb(pd);
			}
			if (/*"2".equals(this.getGroup()) && */TextUtil.isNotNull(pd.getString("saleId"))) {
				List<PageData> result1 = null;
				PageData pd1 = new PageData();
				com.yocto.util.TimeUtil tu = new com.yocto.util.TimeUtil();
				// 到款任务(季度)
				pd1.put("type", "2");
				pd1.put("saleId", pd.getString("saleId"));
				pd1.put("USER_ID", pd.getString("saleId"));
				PageData findById = userService.findById(pd1);
				pd1.put("roleId", findById.getString("ROLE_ID"));
				String nowTime = tu.getNowTime("MM");
				String time = "";
				String year = tu.getNowTime("yyyy");
				if ("04,05,06".indexOf(nowTime) > -1) {
					time = "1";
					pd.put("startTime", year + "-04-01 00:00:00");
					pd.put("endTime", year + "-06-30 23:59:59");
				}

				if ("07,08,09".indexOf(nowTime) > -1) {
					time = "2";
					pd.put("startTime", year + "-07-01 00:00:00");
					pd.put("endTime", year + "-09-30 23:59:59");
				}

				if ("10,11,12".indexOf(nowTime) > -1) {
					time = "3";
					pd.put("startTime", year + "-10-01 00:00:00");
					pd.put("endTime", year + "-12-31 23:59:59");
				}

				if ("01,02,03".indexOf(nowTime) > -1) {
					pd.put("startTime", year + "-01-01 00:00:00");
					pd.put("endTime", year + "-03-31 23:59:59");
					time = "4";
					year = Integer.valueOf((Integer.parseInt(year) - 1)).toString();
				}
				pd1.put("time", time);
				pd1.put("year", year);
				result1 = mainService.findDhlAndYxkhsBySaleId(pd1);
				result2.put("mbyjdk", result1 != null && result1.size() > 0 ? result1.get(0).get("targetDetail") : 0);
				pd.put("type", "0");
				result1 = mainService.showSwgwYjmb(pd);
				result2.put("yjdk", result1 != null && result1.size() > 0 ? result1.get(0).get("dkje") : 0);
				// 合同数量
				pd1.put("type", "3");
				System.out.println("获取本月第一天日期:" + tu.getFirstDayOfMonth());
				System.out.println("获取本月最后一天日期:" + tu.getDefaultDay());
				pd1.put("startTime", tu.getFirstDayOfMonth());
				pd1.put("endTime", tu.getDefaultDay());
				pd.put("startTime", tu.getFirstDayOfMonth());
				pd.put("endTime", tu.getDefaultDay());
				result1 = mainService.findDhlAndYxkhsBySaleId(pd1);
				result2.put("mbhtsl", result1 != null && result1.size() > 0 ? result1.get(0).get("targetDetail") : 0);
				pd.put("type", "1");
				result1 = mainService.showSwgwYjmb(pd);
				result2.put("htsl", result1 != null && result1.size() > 0 ? result1.get(0).get("dkje") : 0);
				// 订单总金额
				pd1.put("type", "4");
				result1 = mainService.findDhlAndYxkhsBySaleId(pd1);
				result2.put("mbddzje", result1 != null && result1.size() > 0 ? result1.get(0).get("targetDetail") : 0);
				pd.put("type", "2");
				result1 = mainService.showSwgwYjmb(pd);
				result2.put("ddzje", result1 != null && result1.size() > 0 ? result1.get(0).get("dkje") : 0);
				// 拜访量(暂时没有)
				pd1.put("type", "5");
				result1 = mainService.findDhlAndYxkhsBySaleId(pd1);
				result2.put("mbbfl", result1 != null && result1.size() > 0 ? result1.get(0).get("targetDetail") : 0);
				pd.put("type", "3");
				result1 = mainService.showSwgwYjmb(pd);
				result2.put("bfl", result1 != null && result1.size() > 0 ? result1.get(0).get("dkje") : 0);
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
			map.put("result2", result2);
		}
		return AppUtil.returnObject(new PageData(), map);
	}

	// 商务顾问业绩目标点击圆盘详情展示
	@RequestMapping(value = "/showSwgwYjmbDetatil")
	@ResponseBody
	public Object showSwgwYjmbDetatil() {
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

			if ("0".equals(pd.getString("type"))) {
				// 到款任务
				List<PageData> result1 = mainService.showSwgwYjmb1(pd);
				result1 = toZb(result1, 1);
				pd.put("type", 2);
				String year = new SimpleDateFormat("yyyy").format(new SimpleDateFormat("yyyy").parse(startTime));
				pd.put("year", year);
				List<PageData> roleIds = toRoleIds(pd);
				if (roleIds.size() > 0) {
					List<PageData> list = new ArrayList<PageData>();
					String startTime1 = startTime;
					String remark1 = "";
					String remark2 = "";
					String remark3 = "";

					for (int i = 0; i < roleIds.size(); i++) {
						String times = "";
						if ("04,05,06".indexOf(roleIds.get(i).getString("time").substring(5, 7)) > -1 && year.equals(roleIds.get(i).getString("time").substring(0, 4))) {
							times = "1";
							remark1 = "1";
						}
						if ("07,08,09".indexOf(roleIds.get(i).getString("time").substring(5, 7)) > -1 && year.equals(roleIds.get(i).getString("time").substring(0, 4))) {
							times = "2";
							remark2 = "1";
						}
						if ("10,11,12".indexOf(roleIds.get(i).getString("time").substring(5, 7)) > -1 && year.equals(roleIds.get(i).getString("time").substring(0, 4))) {
							times = "3";
							remark3 = "1";
						}
						if ("01,02,03".indexOf(roleIds.get(i).getString("time").substring(5, 7)) > -1 && year.equals((Integer.valueOf((roleIds.get(i).getString("time").substring(0, 4))) - 1))) {
							times = "4";
						}
						pd.remove("time");
						pd.put("times", times);
						pd.put("remark1", remark1);
						pd.put("remark2", remark2);
						pd.put("remark3", remark3);
						pd.put("roleId", roleIds.get(i).get("roleId"));
						List<PageData> result2 = mainService.findDhlAndYxkhsBySaleId(pd);
						list.addAll(result2);
						pd.put("endTime", new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(roleIds.get(i).getString("time") + "-01")));
						startTime1 = pd.getString("endTime");
					}

					if (endTime.indexOf(startTime1) < 0) {
						pd.remove("times");
						String time = "";
						if ("04,05,06".indexOf(startTime1.substring(5, 7)) > -1) {
							time = "2";
						}
						if ("07,08,09".indexOf(startTime1.substring(5, 7)) > -1) {
							time = "3";
						}
						if ("10,11,12".indexOf(startTime1.substring(5, 7)) > -1) {
							time = "4";
						}
						/*if ("01,02,03".indexOf(startTime1.substring(5, 7)) > -1) {
							time = "1";
						}*/
						PageData pd1 = new PageData();
						pd1.put("USER_ID", pd.getString("saleId"));
						pd1 = userService.findById(pd1);
						pd.put("roleId", pd1.getString("ROLE_ID"));
						pd.put("time", time);
						/*if ("1".equals(time)) {
							List<PageData> result2 = mainService.findDhlAndYxkhsBySaleId(pd);
							list.addAll(result2);
							time = "2";
							pd.put("time", time);
						}*/
						if ("2".equals(time)) {
							List<PageData> result2 = mainService.findDhlAndYxkhsBySaleId(pd);
							list.addAll(result2);
							time = "3";
							pd.put("time", time);
						}
						if ("3".equals(time)) {
							List<PageData> result2 = mainService.findDhlAndYxkhsBySaleId(pd);
							list.addAll(result2);
							time = "4";
							pd.put("time", time);
						}
						if ("4".equals(time)) {
							List<PageData> result2 = mainService.findDhlAndYxkhsBySaleId(pd);
							list.addAll(result2);
						}
					}

					list = toZb(list, 2);
					result = hbList(result1, list, 0);
				} else {
					PageData pd1 = new PageData();
					pd1.put("USER_ID", pd.getString("saleId"));
					pd1 = userService.findById(pd1);
					pd.put("roleId", pd1.getString("ROLE_ID"));
					List<PageData> result2 = mainService.findDhlAndYxkhsBySaleId(pd);
					result2 = toZb(result2, 2);
					result = hbList(result1, result2, 0);
				}
				pd.put("type", "0");
			}
			if ("1".equals(pd.getString("type"))) {
				// 合同数量
				List<PageData> result1 = mainService.showSwgwYjmb1(pd);
				result1 = toZouResult(result1, pd.getString("startTime"), pd.getString("endTime"), "1", 5);
				pd.put("type", 3);
				pd.put("startTime", new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(startTime)));
				pd.put("endTime", new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(endTime)));

				List<PageData> roleIds = toRoleIds(pd);
				if (roleIds.size() > 0) {
					List<PageData> list = new ArrayList<PageData>();
					String startTime1 = startTime;
					for (int i = 0; i < roleIds.size(); i++) {
						pd.put("startTime", new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(startTime1)));
						pd.put("endTime", new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(roleIds.get(i).getString("time") + "-01")));
						pd.put("roleId", roleIds.get(i).get("roleId"));
						List<PageData> result2 = mainService.findDhlAndYxkhsBySaleId(pd);
						list.addAll(result2);
						startTime1 = pd.getString("endTime");
					}
					if (endTime.indexOf(startTime1) < 0) {
						PageData pd1 = new PageData();
						pd1.put("USER_ID", pd.getString("saleId"));
						pd1 = userService.findById(pd1);
						pd.put("roleId", pd1.getString("ROLE_ID"));
						pd.remove("startTime");
						pd.put("startTime1", startTime1);
						pd.put("endTime", new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(endTime)));
						List<PageData> result2 = mainService.findDhlAndYxkhsBySaleId(pd);
						list.addAll(result2);
					}
					list = toZouResult(list, startTime, endTime, "1", 6);
					result = hbList(result1, list, 1);
				} else {
					PageData pd1 = new PageData();
					pd1.put("USER_ID", pd.getString("saleId"));
					pd1 = userService.findById(pd1);
					pd.put("roleId", pd1.getString("ROLE_ID"));
					List<PageData> result2 = mainService.findDhlAndYxkhsBySaleId(pd);
					result2 = toZouResult(result2, pd.getString("startTime"), pd.getString("endTime"), "1", 6);
					result = hbList(result1, result2, 1);
				}
				pd.put("type", "1");
			}
			if ("2".equals(pd.getString("type"))) {
				// 订单金额
				List<PageData> result1 = mainService.showSwgwYjmb1(pd);
				result1 = toZouResult(result1, pd.getString("startTime"), pd.getString("endTime"), "1", 7);
				pd.put("type", 4);
				pd.put("startTime", new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(startTime)));
				pd.put("endTime", new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(endTime)));

				List<PageData> roleIds = toRoleIds(pd);
				if (roleIds.size() > 0) {
					List<PageData> list = new ArrayList<PageData>();
					String startTime1 = startTime;
					for (int i = 0; i < roleIds.size(); i++) {
						pd.put("startTime", new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(startTime1)));
						pd.put("endTime", new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(roleIds.get(i).getString("time") + "-01")));
						pd.put("roleId", roleIds.get(i).get("roleId"));
						List<PageData> result2 = mainService.findDhlAndYxkhsBySaleId(pd);
						list.addAll(result2);
						startTime1 = pd.getString("endTime");
					}
					if (endTime.indexOf(startTime1) < 0) {
						PageData pd1 = new PageData();
						pd1.put("USER_ID", pd.getString("saleId"));
						pd1 = userService.findById(pd1);
						pd.put("roleId", pd1.getString("ROLE_ID"));
						pd.remove("startTime");
						pd.put("startTime1", startTime1);
						pd.put("endTime", new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(endTime)));
						List<PageData> result2 = mainService.findDhlAndYxkhsBySaleId(pd);
						list.addAll(result2);
					}
					list = toZouResult(list, startTime, endTime, "1", 6);
					result = hbList(result1, list, 2);
				} else {
					PageData pd1 = new PageData();
					pd1.put("USER_ID", pd.getString("saleId"));
					pd1 = userService.findById(pd1);
					pd.put("roleId", pd1.getString("ROLE_ID"));
					List<PageData> result2 = mainService.findDhlAndYxkhsBySaleId(pd);
					result2 = toZouResult(result2, pd.getString("startTime"), pd.getString("endTime"), "1", 6);
					result = hbList(result1, result2, 2);
				}
				pd.put("type", "2");
			}
			if ("3".equals(pd.getString("type"))) {
				// 拜访量
				List<PageData> result1 = mainService.showSwgwYjmb1(pd);
				result1 = toZouResult(result1, pd.getString("startTime"), pd.getString("endTime"), "1", 8);
				pd.put("type", 5);
				pd.put("startTime", new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(startTime)));
				pd.put("endTime", new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(endTime)));

				List<PageData> roleIds = toRoleIds(pd);
				if (roleIds.size() > 0) {
					List<PageData> list = new ArrayList<PageData>();
					String startTime1 = startTime;
					for (int i = 0; i < roleIds.size(); i++) {
						pd.put("startTime", new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(startTime1)));
						pd.put("endTime", new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(roleIds.get(i).getString("time") + "-01")));
						pd.put("roleId", roleIds.get(i).get("roleId"));
						List<PageData> result2 = mainService.findDhlAndYxkhsBySaleId(pd);
						list.addAll(result2);
						startTime1 = pd.getString("endTime");
					}
					if (endTime.indexOf(startTime1) < 0) {
						PageData pd1 = new PageData();
						pd1.put("USER_ID", pd.getString("saleId"));
						pd1 = userService.findById(pd1);
						pd.put("roleId", pd1.getString("ROLE_ID"));
						pd.remove("startTime");
						pd.put("startTime1", startTime1);
						pd.put("endTime", new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(endTime)));
						List<PageData> result2 = mainService.findDhlAndYxkhsBySaleId(pd);
						list.addAll(result2);
					}
					list = toZouResult(list, startTime, endTime, "1", 6);
					result = hbList(result1, list, 3);
				} else {
					PageData pd1 = new PageData();
					pd1.put("USER_ID", pd.getString("saleId"));
					pd1 = userService.findById(pd1);
					pd.put("roleId", pd1.getString("ROLE_ID"));
					List<PageData> result2 = mainService.findDhlAndYxkhsBySaleId(pd);
					result2 = toZouResult(result2, pd.getString("startTime"), pd.getString("endTime"), "1", 6);
					result = hbList(result1, result2, 3);
				}
				pd.put("type", "3");
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

	private List<PageData> toRoleIds(PageData pd) throws Exception {
		return mainService.findRoles(pd);
	}

	private List<PageData> hbList(List<PageData> zouResult, List<PageData> zouResult2, int type) {
		List<PageData> list = new ArrayList<PageData>();
		for (int i = 0; i < zouResult.size(); i++) {
			PageData pd = new PageData();
			pd.put("time", zouResult.get(i).get("time"));
			if (type == 0) {
				pd.put("dkje", zouResult.get(i).get("dkje"));
				pd.put("mbdkje", zouResult2.get(i).get("targetDetail"));
			}
			if (type == 1) {
				pd.put("htsl", zouResult.get(i).get("htsl"));
				pd.put("mbhtsl", zouResult2.get(i).get("targetDetail"));
			}
			if (type == 2) {
				pd.put("ddje", zouResult.get(i).get("ddje"));
				pd.put("mbddje", zouResult2.get(i).get("targetDetail"));
			}
			if (type == 3) {
				pd.put("bfl", zouResult.get(i).get("bfl"));
				pd.put("mbbfl", zouResult2.get(i).get("targetDetail"));
			}
			if (type == 4) {
				pd.put("khs", zouResult.get(i).get("sl"));
				pd.put("mbkhs", zouResult2.get(i).get("targetDetail"));
			}
			list.add(pd);
		}
		return list;
	}

	private List<PageData> toZb(List<PageData> result1, int type) {

		List<String> list = new ArrayList<String>();
		list.add("1");
		list.add("2");
		list.add("3");
		list.add("4");

		List<PageData> list1 = new ArrayList<PageData>();
		for (int i = 0; i < list.size(); i++) {
			PageData pd1 = new PageData();
			boolean mark = true;
			for (int j = 0; null != result1 && j < result1.size(); j++) {
				if (list.get(i).equals(result1.get(j).get("time").toString())) {
					list1.add(result1.get(j));
					mark = false;
					break;
				}
			}
			if (mark) {
				if (1 == type) {
					pd1.put("dkje", 0);
				}
				if (2 == type) {
					pd1.put("targetDetail", 0);
				}
				pd1.put("time", list.get(i));
				list1.add(pd1);
			}
		}
		return list1;
	}

	// 运作简报(实际到款金额)
	@RequestMapping(value = "/showYzsjdk")
	@ResponseBody
	public Object showYzsjdk() {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = "";
		String error = "";
		List<PageData> result = new ArrayList<PageData>();
		try {
			PageData pd = this.getPageData();
			// 经理销售业绩统计
			if (isRole(Const.ROLE_MANAGER)) {
				String startTime = pd.getString("startTime"); // 开始时间
				String endTime = pd.getString("endTime"); // 结束时间
				if (TextUtil.isNotNull(startTime)) {
					pd.put("startTime", startTime + " 00:00:00");
				}
				if (TextUtil.isNotNull(endTime)) {
					pd.put("endTime", endTime + " 23:59:59");
				}
				// 已成交客户数
				result = mainService.showYzsjdk(pd);
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

	// 电话量、意向客户数、拜访量、签单量
	@RequestMapping(value = "/showAll")
	@ResponseBody
	public Object showAll() {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = "";
		String error = "";
		List<PageData> result1 = new ArrayList<PageData>();
		List<PageData> result2 = new ArrayList<PageData>();
		List<PageData> result3 = new ArrayList<PageData>();
		List<PageData> result4 = new ArrayList<PageData>();
		try {
			PageData pd = this.getPageData();

			if ("1".equals(this.getGroup())) {
				if (isRole(Const.ROLE_PHONE_SALES_DIRECTOR)) {
					pd.put("dxzgId", pd.getString("saleId"));
				} else {
					pd.put("dfSaleId", pd.getString("saleId"));
				}
				pd.remove("saleId");
			}
			if ("2".equals(this.getGroup())) {
				pd.put("saleId", pd.getString("saleId"));
			}
			// 经理销售业绩统计
			String startTime = pd.getString("startTime"); // 开始时间
			String endTime = pd.getString("endTime"); // 结束时间
			String flag = pd.getString("flag");
			/*if (TextUtil.isNotNull(startTime)) {
				pd.put("startTime", startTime + " 00:00:00");
			}
			if (TextUtil.isNotNull(endTime)) {
				pd.put("endTime", endTime + " 23:59:59");
			}*/
			// 已成交客户数
			result1 = mainService.showDhlAndYxDhl(pd);
			// 电话量和有效电话量
			result1 = toZouResult(result1, startTime, endTime, flag, 1);
			// 意向客户数
			result2 = mainService.showYxkhs(pd);
			result2 = toZouResult(result2, startTime, endTime, flag, 2);
			// 拜访量
			result3 = mainService.showBfl(pd);
			result3 = toZouResult(result3, startTime, endTime, flag, 3);
			// 签单量
			result4 = mainService.showHt(pd);
			result4 = toZouResult(result4, startTime, endTime, flag, 4);
			error = "00";
			msg = "查询成功";
		} catch (Exception e) {
			error = "500";
			msg = "程序异常";
			e.printStackTrace();
		} finally {
			map.put("error", error);
			map.put("msg", msg);
			map.put("result1", result1);
			map.put("result2", result2);
			map.put("result3", result3);
			map.put("result4", result4);
		}
		return AppUtil.returnObject(new PageData(), map);
	}

	// 根据name查询拜访量详情
	@RequestMapping(value = "/showBfl")
	@ResponseBody
	public Object showBfl() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String error = "";
		String msg = "";
		PageInfo<PageData> page = null;
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
			String pageNo = pd.getString("pageNo");
			String pageSize = pd.getString("pageSize");
			int pNo = TextUtil.isNull(pageNo) ? 1 : Integer.parseInt(pageNo);
			int pSize = TextUtil.isNull(pageSize) ? 10 : Integer.parseInt(pageSize);
			PageHelper.startPage(pNo, pSize); // 核心分页代码
			page = new PageInfo<PageData>(mainService.listbfls(pd));
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

	public static List<PageData> toZouResult(List<PageData> result, String startTime, String endTime, String flag, int type) throws Exception {
		List<String> list = new ArrayList<String>();
		if ("0".equals(flag)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar lastDate = Calendar.getInstance();
			while (!startTime.equals(endTime)) {
				list.add(startTime);
				lastDate.setTime(sdf.parse(startTime));
				lastDate.add(Calendar.DATE, 1);
				startTime = sdf.format(lastDate.getTime());
			}
		}
		if ("1".equals(flag)) {
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
					pd.put("dhl", 0);
					pd.put("yxdhl", 0);
				}
				if (2 == type) {
					pd.put("yxkhs", 0);
				}
				if (3 == type) {
					pd.put("bfl", 0);
				}
				if (4 == type) {
					pd.put("qdl", 0);
				}
				if (5 == type) {
					pd.put("htsl", 0);
				}
				if (6 == type) {
					pd.put("targetDetail", 0);
				}
				if (7 == type) {
					pd.put("ddje", 0);
				}
				if (8 == type) {
					pd.put("bfl", 0);
				}
				if (9 == type) {
					pd.put("sl", 0);
				}
				pd.put("time", list.get(i));
				list1.add(pd);
			}
		}
		return list1;
	}

	private BigDecimal toGetM(PageData zxz, String string) {
		BigDecimal i = new BigDecimal(0.0);
		return null != zxz.get(string) ? new BigDecimal(Double.valueOf(zxz.get(string).toString())) : i;
	}

	// 运作简报详情
	@RequestMapping(value = "/showYzjbDetatil")
	@ResponseBody
	public Object showYzjbDetatil() {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = "";
		String error = "";
		Object zb = null;
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
			List<PageData> result1 = mainService.showYzsjdk1(pd);
			BigDecimal hkMoney = new BigDecimal(0.0);
			for (int i = 0; i < result1.size(); i++) {
				hkMoney = hkMoney.add(toGetM(result1.get(i), "dkje"));
			}

			result = toZb1(result1, startTime, endTime);
			/*pd.put("type", 6);
			pd.put("startTime", new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(startTime)));
			pd.put("endTime", new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(endTime)));
			pd.put("saleId", pd.getString("runnerId"));
			List<PageData> result2 = mainService.findDhlAndYxkhsBySaleId(pd);
			result2 = toZouResult(result2, pd.getString("startTime"), pd.getString("endTime"), "1", 6);
			result = hbList(result1, result2, 0);*/
			pd.put("userId", pd.getString("runnerId"));
			pd.put("fdStandard", 6);
			List<Double> list = new ArrayList<Double>();
			List<PageData> findJe3 = yjService.findJe3(pd);
			for (int j = 0; findJe3 != null && j < findJe3.size(); j++) {
				if (TextUtil.isNotNull(findJe3.get(j).getString("fdzb"))) {
					String[] split = findJe3.get(j).getString("fdzb").split(",");
					if (split.length == 1) {
						if (Double.valueOf(findJe3.get(j).get("tc").toString()) > 0) {
							list.add(Double.valueOf(findJe3.get(j).get("fdzb").toString().split(",")[0]));
						}
					} else {
						if (Double.valueOf(findJe3.get(j).get("tc").toString()) > 0) {
							list.add(Double.valueOf(findJe3.get(j).get("fdzb").toString().split(",")[0]));
							list.add(Double.valueOf(findJe3.get(j).get("fdzb").toString().split(",")[1]));
						}
					}

				}
			}

			if (list.size() > 0) {
				Object[] array = list.toArray();
				Arrays.sort(array);
				zb = array[0];
			} else {
				zb = 0;
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
			map.put("zb", zb);
		}
		return AppUtil.returnObject(new PageData(), map);
	}

	private List<PageData> toZb1(List<PageData> result, String startTime, String endTime) throws ParseException {
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
				pd.put("dkje", 0);
				pd.put("time", list.get(i));
				list1.add(pd);
			}
		}
		return list1;
	}

	@RequestMapping(value = "/showDxSwgwXstj")
	@ResponseBody
	public Object showDxSwgwXstj() {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = "";
		String error = "";
		List<PageData> result = new ArrayList<PageData>();
		PageData result2 = new PageData();
		try {
			PageData pd = this.getPageData();
			// 经理销售业绩统计
			if (isRole(Const.ROLE_MANAGER)) {
				result = mainService.showDxSwgwXstj(pd);
			}
			if (/*"2".equals(this.getGroup()) && */TextUtil.isNotNull(pd.getString("saleId"))) {

				TimeUtil tu = new TimeUtil();
				String startTime = tu.getFirstDayOfMonth(); // 开始时间
				String endTime = tu.getDefaultDay(); // 结束时间
				if (TextUtil.isNotNull(startTime)) {
					pd.put("startTime", startTime + " 00:00:00");
				}
				if (TextUtil.isNotNull(endTime)) {
					pd.put("endTime", endTime + " 23:59:59");
				}
				// 客户数
				pd.put("type", "0");
				List<PageData> result1 = mainService.showDxSwgwYjmb1(pd);
				result2.put("khs", result1 != null && result1.size() > 0 ? result1.get(0).get("sl") : 0);
				pd.put("type", 9);
				String year = new SimpleDateFormat("yyyy").format(new Date());
				pd.put("year", year);
				result1 = mainService.findDhlAndYxkhsBySaleId(pd);
				result2.put("mbkhs", result1 != null && result1.size() > 0 ? result1.get(0).get("targetDetail") : 0);
				// 订单总金额
				pd.put("type", "1");
				result1 = mainService.showDxSwgwYjmb1(pd);
				result2.put("ddje", result1 != null && result1.size() > 0 ? result1.get(0).get("ddje") : 0);
				pd.put("type", 10);
				pd.put("year", year);
				result1 = mainService.findDhlAndYxkhsBySaleId(pd);
				result2.put("mbddje", result1 != null && result1.size() > 0 ? result1.get(0).get("targetDetail") : 0);
				// 拜访量
				pd.put("type", "2");
				result1 = mainService.showDxSwgwYjmb1(pd);
				result2.put("bfl", result1 != null && result1.size() > 0 ? result1.get(0).get("bfl") : 0);
				pd.put("type", 8);
				pd.put("year", year);
				result1 = mainService.findDhlAndYxkhsBySaleId(pd);
				result2.put("mbbfl", result1 != null && result1.size() > 0 ? result1.get(0).get("targetDetail") : 0);
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
			map.put("result2", result2);
		}
		return AppUtil.returnObject(new PageData(), map);
	}

	@RequestMapping(value = "/showDxSwgwXstjDetail")
	@ResponseBody
	public Object showDxSwgwXstjDetail() {
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

			if ("0".equals(pd.getString("type"))) {
				// 客户数
				List<PageData> result1 = mainService.showDxSwgwYjmb1(pd);
				result1 = toZouResult(result1, pd.getString("startTime"), pd.getString("endTime"), "1", 9);
				pd.put("type", 9);
				String year = new SimpleDateFormat("yyyy").format(new SimpleDateFormat("yyyy").parse(startTime));
				pd.put("year", year);
				List<PageData> result2 = mainService.findDhlAndYxkhsBySaleId(pd);
				result2 = toZouResult(result2, pd.getString("startTime"), pd.getString("endTime"), "1", 6);
				result = hbList(result1, result2, 4);
				pd.put("type", "0");
			}
			if ("1".equals(pd.getString("type"))) {
				// 订单总金额
				List<PageData> result1 = mainService.showDxSwgwYjmb1(pd);
				result1 = toZouResult(result1, pd.getString("startTime"), pd.getString("endTime"), "1", 7);
				pd.put("type", 10);
				pd.put("startTime", new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(startTime)));
				pd.put("endTime", new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(endTime)));
				List<PageData> result2 = mainService.findDhlAndYxkhsBySaleId(pd);
				result2 = toZouResult(result2, pd.getString("startTime"), pd.getString("endTime"), "1", 6);
				result = hbList(result1, result2, 2);
				pd.put("type", "1");
			}
			if ("2".equals(pd.getString("type"))) {
				// 拜访量
				List<PageData> result1 = mainService.showDxSwgwYjmb1(pd);
				result1 = toZouResult(result1, pd.getString("startTime"), pd.getString("endTime"), "1", 8);
				pd.put("type", 8);
				pd.put("startTime", new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(startTime)));
				pd.put("endTime", new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(endTime)));
				List<PageData> result2 = mainService.findDhlAndYxkhsBySaleId(pd);
				result2 = toZouResult(result2, pd.getString("startTime"), pd.getString("endTime"), "1", 6);
				result = hbList(result1, result2, 3);
				pd.put("type", "2");
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

	@RequestMapping(value = "/showWindow")
	@ResponseBody
	public Object showWindow() {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = "";
		String error = "";
		List<PageData> result = new ArrayList<PageData>();
		PageData result2 = new PageData();
		try {
			PageData pd = this.getPageData();
			TimeUtil tu = new TimeUtil();

			if (TextUtil.isNotNull(pd.getString("saleId"))) {
				PageData markInfo = mainService.findObject(pd);
				if ((null != markInfo && "未达标".equals(markInfo.getString("msg"))) || null == markInfo) {
					pd.put("USER_ID", pd.getString("saleId"));
					if ("1".equals(this.getGroup(pd))) {
						pd.put("startTime", pd.getString("startTime"));
						pd.put("endTime", pd.getString("endTime"));
						pd.put("dfSaleId", pd.getString("saleId"));
						PageData dhl1 = mainService.findgj(pd);
						PageData yxkhs1 = mainService.findyyx(pd);

						pd.put("userId", pd.getString("saleId"));
						pd.put("monthOrjd", Integer.valueOf(tu.getNowTime("MM")).toString());
						pd.put("year", tu.getNowTime("yyyy"));
						pd.put("type", 0);
						// 获取电话量指标
						PageData dhl2 = yjService.findDhlByUserId(pd);
						pd.put("type", 1);
						// 获取意向客户数指标
						PageData yxkhs2 = yjService.findDhlByUserId(pd);

						int flag = 0;
						if (null != dhl2 && null != dhl1 && null != dhl1.get("dhzcs") && null != dhl2.get("targetDetail") && TextUtil.isNotNull(dhl2.get("targetDetail").toString())
								&& TextUtil.isNotNull(dhl1.get("dhzcs").toString())) {
							String string = new BigDecimal(Double.valueOf(dhl2.get("targetDetail").toString())).divide(new BigDecimal(4)).setScale(0, BigDecimal.ROUND_HALF_UP).toString();
							Integer value = Integer.valueOf(string);
							if (Integer.valueOf(dhl1.get("dhzcs").toString()) >= value) {
								flag++;
							}
						}

						// 获取各用户的意向客户数
						if (null != yxkhs2 && null != yxkhs1 && null != yxkhs1.get("khs") && null != yxkhs2.get("targetDetail") && TextUtil.isNotNull(yxkhs2.get("targetDetail").toString())
								&& TextUtil.isNotNull(yxkhs1.get("khs").toString())) {
							String value = yxkhs2.get("targetDetail").toString();
							String[] value1 = value.split(",");
							if (value1.length == 2) {
								Integer qz = Integer.valueOf(value1[0]);
								Integer hz = Integer.valueOf(value1[1]);
								qz = Integer.valueOf(new BigDecimal(qz).divide(new BigDecimal(4)).setScale(0, BigDecimal.ROUND_HALF_UP).toString());
								hz = Integer.valueOf(new BigDecimal(hz).divide(new BigDecimal(4)).setScale(0, BigDecimal.ROUND_HALF_UP).toString());
								if (Integer.valueOf(yxkhs1.get("khs").toString()) <= hz && Integer.valueOf(yxkhs1.get("khs").toString()) >= qz) {
									// 达标
									flag++;
								} else if (Integer.valueOf(yxkhs1.get("khs").toString()) > hz) {
									// 超标
									flag++;
								}
							}
						}

						error = "00";
						if (flag == 2) {
							msg = "达标";
						} else {
							msg = "未达标";
						}
					}

					if ("2".equals(this.getGroup(pd)) && !Const.ROLE_SALES_ELITEWX.equals(pd.getString("roleId")) && !Const.ROLE_SALES_ELITEDX.equals(pd.getString("roleId"))) {
						int flag = 0;
						List<PageData> result1 = null;
						PageData pd1 = new PageData();
						// 到款任务(季度)
						pd1.put("type", "2");
						pd1.put("saleId", pd.getString("saleId"));
						String nowTime = tu.getNowTime("MM");
						String year = tu.getNowTime("yyyy");

						if ("01,02,03".indexOf(nowTime) > -1) {
							year = Integer.valueOf((Integer.parseInt(year) - 1)).toString();
						}
						// 合同数量
						pd1.put("type", "3");
						pd1.put("startTime", pd.getString("startTime"));
						pd1.put("endTime", pd.getString("endTime"));
						result1 = mainService.findDhlAndYxkhsBySaleId(pd1);
						result2.put("mbhtsl", result1 != null && result1.size() > 0 ? TextUtil.isNotNull(result1.get(0).get("targetDetail").toString()) ? result1.get(0).get("targetDetail") : 0 : 0);
						pd.put("type", "1");
						result1 = mainService.showSwgwYjmb(pd);
						result2.put("htsl", result1 != null && result1.size() > 0 ? result1.get(0).get("dkje") : 0);

						if (Integer.valueOf(result2.get("mbhtsl").toString()) <= Integer.valueOf(result2.get("htsl").toString())) {
							flag++;
						}

						// 订单总金额
						pd1.put("type", "4");
						result1 = mainService.findDhlAndYxkhsBySaleId(pd1);
						result2.put("mbddzje", result1 != null && result1.size() > 0 ? TextUtil.isNotNull(result1.get(0).get("targetDetail").toString()) ? result1.get(0).get("targetDetail") : 0 : 0);
						pd.put("type", "2");
						result1 = mainService.showSwgwYjmb(pd);
						result2.put("ddzje", result1 != null && result1.size() > 0 ? result1.get(0).get("dkje") : 0);

						if (Double.valueOf(result2.get("mbddzje").toString()) <= Double.valueOf(result2.get("ddzje").toString())) {
							flag++;
						}
						// 拜访量(暂时没有)
						pd1.put("type", "5");
						result1 = mainService.findDhlAndYxkhsBySaleId(pd1);
						result2.put("mbbfl", result1 != null && result1.size() > 0 ? TextUtil.isNotNull(result1.get(0).get("targetDetail").toString()) ? result1.get(0).get("targetDetail") : 0 : 0);
						pd.put("type", "3");
						pd.put("mark", "1");
						result1 = mainService.showSwgwYjmb(pd);
						result2.put("bfl", result1 != null && result1.size() > 0 ? result1.get(0).get("dkje") : 0);

						if (Integer.valueOf(result2.get("mbbfl").toString()) <= Integer.valueOf(result2.get("bfl").toString())) {
							flag++;
						}

						error = "00";
						if (flag == 3) {
							msg = "达标";
						} else {
							msg = "未达标";
						}
					}

					if ("2".equals(this.getGroup(pd)) && Const.ROLE_SALES_ELITEDX.equals(pd.getString("roleId"))) {
						int flag = 0;
						PageData pageData = new PageData();
						// 到款任务(季度)
						String currentMonth = tu.getNowTime("yyyy-MM");

						// 拜访量
						pageData.put("saleId", pd.getString("saleId"));
						pageData.put("time", currentMonth);
						PageData bfl1 = yjService.findCountVisit(pageData);
						if (null != bfl1 && null != bfl1.get("bfl") && TextUtil.isNotNull(bfl1.get("bfl").toString())) {
							PageData pd1 = new PageData();
							pd1.put("fdStandard", 7);
							pd1.put("userId", pd.getString("saleId"));
							pd1.put("saleId", pd.getString("saleId"));
							pd1.put("type", "8");
							pd1.put("startTime", new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(pd.getString("startTime"))));
							pd1.put("endTime", new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(pd.getString("endTime"))));
							List<PageData> dx = mainService.findDhlAndYxkhsBySaleId(pd1);

							if (null != dx && dx.size() == 1) {
								if (Integer.valueOf(bfl1.get("bfl").toString()) >= Integer.valueOf(dx.get(0).get("targetDetail").toString())) {
									// 0达标
									flag++;
								}
							}
						}

						// 客户签约奖金
						pageData.put("startTime", currentMonth + "-01 00:00:00");
						pageData.put("endTime", pd.getString("endTime") + " 23:59:59");
						pageData.put("type", "0");
						// 实际签约客户数
						List<PageData> resultsjqy = mainService.showDxSwgwYjmb1(pageData);

						if (null != resultsjqy && resultsjqy.size() > 0 && null != resultsjqy.get(0).get("sl")) {
							PageData pd1 = new PageData();
							pd1.put("fdStandard", 8);
							pd1.put("userId", pd.getString("saleId"));
							pd1.put("saleId", pd.getString("saleId"));
							pd1.put("type", "9");
							pd1.put("startTime", new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(pd.getString("startTime"))));
							pd1.put("endTime", new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(pd.getString("endTime"))));
							// 指标客户数
							List<PageData> resultzbqy = mainService.findDhlAndYxkhsBySaleId(pd1);

							if (null != resultzbqy && resultzbqy.size() > 0 && resultzbqy.size() == 1) {
								if (Integer.valueOf(resultsjqy.get(0).get("sl").toString()) >= Integer.valueOf(resultzbqy.get(0).get("targetDetail").toString())) {
									// 0达标
									flag++;
								}
							}
						}

						// 订单标的奖金
						pageData.put("type", "1");
						// 实际
						List<PageData> resultddje = mainService.showDxSwgwYjmb1(pageData);

						if (null != resultddje && resultddje.size() > 0 && null != resultddje.get(0).get("ddje")) {
							PageData pd1 = new PageData();
							pd1.put("fdStandard", 9);
							pd1.put("userId", pd.getString("saleId"));
							// 提成
							List<PageData> pd2 = yjService.findJe3(pd1);
							/*pd1.put("saleId", user.get("userId"));
							pd1.put("type", "10");
							pd1.put("startTime", new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(defaultDay)));
							pd1.put("endTime", new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(defaultDay)));
							// 指标客户数
							List<PageData> resultzbje = mainService.findDhlAndYxkhsBySaleId(pd1);*/

							for (int j = 0; pd2 != null && j < pd2.size(); j++) {
								if (TextUtil.isNotNull(pd2.get(j).getString("fdzb"))) {
									String[] split = pd2.get(j).getString("fdzb").split(",");
									if (split.length == 1) {
										if (Double.valueOf(resultddje.get(0).get("ddje").toString()) >= Double.valueOf(split[0])) {
											if (new BigDecimal(Double.valueOf(pd2.get(j).get("tc").toString())).compareTo(new BigDecimal(0.0)) > 0) {
												flag++;
												break;
											}
										}
									} else {
										if (Double.valueOf(resultddje.get(0).get("ddje").toString()) >= Double.valueOf(split[0])
												&& Double.valueOf(resultddje.get(0).get("ddje").toString()) < Double.valueOf(split[1])) {
											if (new BigDecimal(Double.valueOf(pd2.get(j).get("tc").toString())).compareTo(new BigDecimal(0.0)) > 0) {
												flag++;
												break;
											}
										}
									}
								}
							}
						}
						error = "00";
						if (flag == 3) {
							msg = "达标";
						} else {
							msg = "未达标";
						}
					}

					/*if (Const.ROLE_SALES_ELITEDX.equals(pd.getString("roleId"))) {
						int flag = 0;

						String startTime = pd.getString("startTime"); // 开始时间
						String endTime = pd.getString("endTime"); // 结束时间
						if (TextUtil.isNotNull(startTime)) {
							pd.put("startTime", startTime + " 00:00:00");
						}
						if (TextUtil.isNotNull(endTime)) {
							pd.put("endTime", endTime + " 23:59:59");
						}
						// 客户数
						pd.put("type", "0");
						List<PageData> result1 = mainService.showDxSwgwYjmb1(pd);
						result2.put("khs", result1 != null && result1.size() > 0 ? result1.get(0).get("sl") : 0);
						pd.put("type", 9);
						String year = new SimpleDateFormat("yyyy").format(new Date());
						String nowTime = tu.getNowTime("MM");
						if ("01,02,03".indexOf(nowTime) > -1) {
							year = Integer.valueOf((Integer.parseInt(year) - 1)).toString();
						}
						pd.put("year", year);
						result1 = mainService.findDhlAndYxkhsBySaleId(pd);
						result2.put("mbkhs", result1 != null && result1.size() > 0 ? TextUtil.isNotNull(result1.get(0).get("targetDetail").toString()) ? result1.get(0).get("targetDetail") : 0 : 0);
						if (Integer.valueOf(result2.get("mbkhs").toString()) <= Integer.valueOf(result2.get("khs").toString())) {
							flag++;
						}
						// 订单总金额
						pd.put("type", "1");
						result1 = mainService.showDxSwgwYjmb1(pd);
						result2.put("ddje", result1 != null && result1.size() > 0 ? result1.get(0).get("ddje") : 0);
						pd.put("type", 10);
						pd.put("year", year);
						result1 = mainService.findDhlAndYxkhsBySaleId(pd);
						result2.put("mbddje", result1 != null && result1.size() > 0 ? TextUtil.isNotNull(result1.get(0).get("targetDetail").toString()) ? result1.get(0).get("targetDetail") : 0 : 0);

						if (Double.valueOf(result2.get("mbddje").toString()) <= Double.valueOf(result2.get("ddje").toString())) {
							flag++;
						}
						// 拜访量
						pd.put("type", "2");
						result1 = mainService.showDxSwgwYjmb1(pd);
						result2.put("bfl", result1 != null && result1.size() > 0 ? result1.get(0).get("bfl") : 0);
						pd.put("type", 8);
						pd.put("year", year);
						result1 = mainService.findDhlAndYxkhsBySaleId(pd);
						result2.put("mbbfl", result1 != null && result1.size() > 0 ? TextUtil.isNotNull(result1.get(0).get("targetDetail").toString()) ? result1.get(0).get("targetDetail") : 0 : 0);

						if (Integer.valueOf(result2.get("mbbfl").toString()) <= Integer.valueOf(result2.get("bfl").toString())) {
							flag++;
						}

						error = "00";
						if (flag == 3) {
							msg = "达标";
						} else {
							msg = "未达标";
						}
					}*/
					pd.put("msg", msg);
					if (mainService.update(pd) <= 0) {
						mainService.insert(pd);
					}
				} else {
					error = "01";
					msg = "无需弹出";
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

}
