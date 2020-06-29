package com.yocto.controller.business.yjManage;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yocto.controller.base.BaseController;
import com.yocto.controller.business.mainManage.MainIndexController;
import com.yocto.entity.business.yjManage.YjVo;
import com.yocto.entity.business.yjtcManage.YjtcRelate;
import com.yocto.entity.business.yjtcManage.YjtcVo;
import com.yocto.entity.system.User;
import com.yocto.service.business.customerManage.ICustomerService;
import com.yocto.service.business.mainManage.IMainService;
import com.yocto.service.business.orderManage.IOrderService;
import com.yocto.service.business.yjService.IYjService;
import com.yocto.service.system.user.UserManager;
import com.yocto.util.AppUtil;
import com.yocto.util.Const;
import com.yocto.util.HttpsUtil;
import com.yocto.util.PageData;
import com.yocto.util.TextUtil;
import com.yocto.util.TimeUtil;

/**
 * 业绩指标设置
 * 
 * @author xl
 *
 */
@Controller
@RequestMapping(value = "/yjzb")
public class YjController extends BaseController {

	@Resource(name = "yjService")
	private IYjService yjService;

	@Resource(name = "mainService")
	private IMainService mainService;

	@Resource(name = "orderService")
	private IOrderService orderService;

	@Resource(name = "customerService")
	private ICustomerService customerService;

	@Resource(name = "userService")
	private UserManager userService;

	/**
	 * 新增业绩指标标题
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addYjzb")
	@ResponseBody
	public Object addYjzb() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData result = null;
		String msg = null;
		String error = null;
		try {
			PageData pd = this.getPageData();
			if (TextUtil.isNotNull(pd.getString("type")) && TextUtil.isNotNull(pd.getString("year"))) {
				// 判断业绩指标是否存在
				PageData pd1 = yjService.findIsExsits(pd);
				if (null != pd1) {
					error = "01";
					msg = "该年的业绩指标已存在";
				} else {
					yjService.addYjzb(pd);
					error = "00";
					msg = "新增成功";
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
	 * 
	 * 
	 * 删除业绩指标
	 * 
	 */
	@RequestMapping(value = "/deleteYjzb")
	@ResponseBody
	public Object deleteYjzb() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData result = null;
		String msg = null;
		String error = null;
		try {
			PageData pd = this.getPageData();
			if (TextUtil.isNotNull(pd.getString("parentId"))) {
				yjService.deleteYjzb(pd);
				error = "00";
				msg = "删除成功";
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

	@RequestMapping(value = "/showYjzbList")
	@ResponseBody
	public Object showYjzbList() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<PageData> result = null;
		String msg = null;
		String error = null;
		try {

			result = yjService.showYjzbList(this.getPageData());
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

	// 获取第二个跳转页的接口
	@RequestMapping(value = "/goSecondYm")
	@ResponseBody
	public Object goSecondYm() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<PageData> result = null;
		String msg = null;
		String error = null;
		try {
			PageData pd = this.getPageData();
			if (TextUtil.isNotNull(pd.getString("type"))) {
				result = yjService.showSwgw(pd);
				error = "00";
				msg = "查看成功";
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
	 * 点进去查看业绩指标详情 参数id(作为子类的parentId)
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/showYjzbDetail")
	@ResponseBody
	public Object showYjzbDetail() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<PageData> result = null;
		String msg = null;
		String error = null;
		try {
			PageData pd = this.getPageData();
			if (TextUtil.isNotNull(pd.getString("parentId")) && TextUtil.isNotNull(pd.getString("type"))) {
				result = yjService.showYjzbDetail(pd);
				if (!"7".equals(pd.getString("type"))) {
					result = (getMap(result));
				} else {
					if (result == null || (result != null && result.size() == 0)) {
						result = new ArrayList<PageData>();
						PageData pd1 = new PageData();
						pd1.put("zbA", null);
						pd1.put("zbB", null);
						pd1.put("bfzb", null);
						result.add(pd1);
					}
				}
				error = "00";
				msg = "查看成功";
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

	public static List<PageData> getMap(List<PageData> list) {
		List<PageData> list1 = new ArrayList<PageData>();
		List<Integer> lz = new ArrayList<Integer>();
		for (int i = 0; i < list.size(); i++) {
			PageData map = new PageData();
			List<Map<String, Object>> list2 = new ArrayList<Map<String, Object>>();
			if (!lz.contains(i)) {
				if (null != list.get(i).get("name")) {
					map.put("name", list.get(i).get("name"));
				}
				if (null != list.get(i).get("roleName")) {
					map.put("roleName", list.get(i).get("roleName"));
				}
				if (null != list.get(i).get("roleId")) {
					map.put("roleId", list.get(i).get("roleId"));
				}
				if (null != list.get(i).get("userId")) {
					map.put("userId", list.get(i).get("userId"));
				}
				if (null != list.get(i).get("type")) {
					map.put("type", list.get(i).get("type"));
				}
				Map<String, Object> map1 = new HashMap<String, Object>();
				if (null != list.get(i).get("monthOrjd")) {
					map1.put("monthOrjd", list.get(i).get("monthOrjd"));
				}
				if (null != list.get(i).get("targetDetail")) {
					map1.put("targetDetail", list.get(i).get("targetDetail"));
				}
				if (null != list.get(i).get("conditions")) {
					map1.put("conditions", list.get(i).get("conditions"));
				}
				list2.add(map1);
			} else {
				continue;
			}

			for (int j = i + 1; j < list.size(); j++) {
				if (null != list.get(i).get("userId") && list.get(i).get("userId").toString().equals(list.get(j).get("userId").toString())) {
					Map<String, Object> map1 = new HashMap<String, Object>();
					if (null != list.get(j).get("monthOrjd")) {
						map1.put("monthOrjd", list.get(j).get("monthOrjd"));
					}
					if (null != list.get(j).get("targetDetail")) {
						map1.put("targetDetail", list.get(j).get("targetDetail"));
					}
					list2.add(map1);
					lz.add(j);
				}
				if (null != list.get(i).get("roleId") && list.get(i).get("roleId").toString().equals(list.get(j).get("roleId").toString())) {
					Map<String, Object> map1 = new HashMap<String, Object>();
					if (null != list.get(j).get("monthOrjd")) {
						map1.put("monthOrjd", list.get(j).get("monthOrjd"));
					}
					if (null != list.get(j).get("targetDetail")) {
						map1.put("targetDetail", list.get(j).get("targetDetail"));
					}
					list2.add(map1);
					lz.add(j);
				}
			}
			if (list2.size() > 0) {
				map.put("xinxi", list2);
				list1.add(map);
			}
		}
		return list1;
	}

	/**
	 * type=7时的保存
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
	public Object add() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<PageData> result = null;
		String msg = null;
		String error = null;
		try {
			PageData pd = this.getPageData();
			yjService.deleteYjzb1(pd);
			yjService.insertYjzb(pd);
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
	 * 
	 * 修改或保存业绩指标
	 * 
	 * 
	 */
	@RequestMapping(value = "/addOrUpdateYjzb", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public Object addOrUpdateYjzb(HttpServletRequest request, @RequestBody YjVo yj) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData result = null;
		String msg = null;
		String error = null;
		try {
			PageData pd = this.getPageData();
			if (null != yj.getParentId() && null != yj.getType()) {
				// 先删除
				pd.put("parentId", yj.getParentId());
				if (null != yj.getYjList()) {
					pd.put("userId", yj.getYjList().get(0).getUserId());
					yjService.deleteYjzb1(pd);
					// 再新增
					List<PageData> list1 = new ArrayList<PageData>();
					for (int i = 0; i < yj.getYjList().size(); i++) {
						PageData pd1 = new PageData();
						pd1.put("parentId", yj.getParentId());
						pd1.put("type", yj.getType());
						pd1.put("monthOrjd", yj.getYjList().get(i).getMonthOrjd());
						pd1.put("targetDetail", yj.getYjList().get(i).getTargetDetail());
						pd1.put("userId", yj.getYjList().get(i).getUserId());
						pd1.put("roleId", yj.getYjList().get(i).getRoleId());
						if (yj.getType() == 11 || yj.getType() == 12) {
							pd1.put("conditions", yj.getYjList().get(i).getConditions());
						}
						list1.add(pd1);
					}
					yjService.insertBatch(list1);
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

	// ===============================================开始==================================
	/**
	 * 新增绩效薪资
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addJxxz")
	@ResponseBody
	public Object addJxxz() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData result = null;
		String msg = null;
		String error = null;
		try {
			PageData pd = this.getPageData();
			if (TextUtil.isNotNull(pd.getString("type"))) {
				if (TextUtil.isNotNull(pd.getString("id"))) {
					yjService.updateById(pd);
				} else {
					PageData pd1 = yjService.findIsExsits1(pd);
					if (null != pd1) {
						error = "01";
						msg = "绩效薪资类别已存在";
					} else {
						yjService.addJxxz(pd);
						error = "00";
						msg = "新增成功";
					}
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
	 * 
	 * 
	 * 删除绩效薪资
	 * 
	 */
	@RequestMapping(value = "/deleteYjxz")
	@ResponseBody
	public Object deleteYjxz() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData result = null;
		String msg = null;
		String error = null;
		try {
			PageData pd = this.getPageData();
			if (TextUtil.isNotNull(pd.getString("parentId"))) {
				yjService.deleteYjxz(pd);
				error = "00";
				msg = "删除成功";
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
	 * 展示业绩薪资列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/showYjxzList")
	@ResponseBody
	public Object showYjxzList() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<PageData> result = null;
		String msg = null;
		String error = null;
		try {
			result = yjService.showYjtcList();
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

	/**
	 * 点进去查看业绩提成详情 参数id(作为子类的parentId)
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/showXzDetail")
	@ResponseBody
	public Object showXzDetail() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<PageData> result = null;
		String msg = null;
		String error = null;
		try {
			PageData pd = this.getPageData();
			if (TextUtil.isNotNull(pd.getString("flag"))) {
				String flag = pd.getString("flag");
				if ("0".equals(flag)) {
					if (TextUtil.isNotNull(pd.getString("parentId"))) {
						result = yjService.showYjxzDetail(pd);
						error = "00";
						msg = "查看成功";
					} else {
						error = "01";
						msg = "缺少参数";
					}
				}
				if ("1".equals(flag)) {
					// 查看底薪
					result = yjService.showYDxzDetail(pd);
					error = "00";
					msg = "查看成功";
				}
				if ("2".equals(flag)) {
					// 查看特殊员工
					result = yjService.showTsyg(pd);
					error = "00";
					msg = "查看成功";
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
	 * 根据type展示岗位列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/showRoleList")
	@ResponseBody
	public Object showRoleList() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<PageData> result = null;
		String msg = null;
		String error = null;
		try {
			PageData pd = this.getPageData();
			if (TextUtil.isNotNull(pd.getString("type"))) {
				result = yjService.showRoleList(pd);
				error = "00";
				msg = "查看成功";
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
	 * 底薪(特殊员工薪资)的新增和修改
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addOrUpdateDxandTs")
	@ResponseBody
	public Object addOrUpdateDxandTs() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData result = null;
		String msg = null;
		String error = null;
		try {
			PageData pd = this.getPageData();
			if (TextUtil.isNotNull(pd.getString("id"))) {
				// 根据id修改
				yjService.updateById(pd);
				error = "00";
				msg = "修改成功";
			} else {
				if (TextUtil.isNotNull(pd.getString("flag")) && TextUtil.isNotNull(pd.getString("userId")) && TextUtil.isNotNull(pd.getString("targetDetail"))) {
					PageData pd1 = yjService.findIsExsits2(pd);
					if (null != pd1) {
						error = "01";
						msg = "该年的绩效类别已存在";
					} else {
						if ("0".equals(pd.getString("flag"))) {
							if (TextUtil.isNull(pd.getString("flag"))) {
								error = "01";
								msg = "缺少参数";
							} else {
								yjService.addDxOrTs(pd);
								error = "00";
								msg = "新增成功";
							}
						} else {
							yjService.addDxOrTs(pd);
							error = "00";
							msg = "新增成功";
						}
					}
				} else {
					error = "01";
					msg = "缺少参数";
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

	// 根据id删除子信息
	@RequestMapping(value = "/deleteById")
	@ResponseBody
	public Object deleteById() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData result = null;
		String msg = null;
		String error = null;
		try {
			PageData pd = this.getPageData();
			if (TextUtil.isNotNull(pd.getString("id"))) {
				yjService.deleteById(pd);
				error = "00";
				msg = "删除成功";
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

	// ==================================结束=============================================
	/**
	 * 展示提成规则列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/showTcgzList")
	@ResponseBody
	public Object showTcgzList() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<PageData> result = null;
		String msg = null;
		String error = null;
		try {
			PageData pd = this.getPageData();
			if ("0".equals(pd.getString("flag")))
				result = yjService.showTcgzList();
			if ("1".equals(pd.getString("flag")) || "2".equals(pd.getString("flag")))
				result = yjService.showTcgzList1(pd);
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

	/**
	 * 根据分档指标获取提成岗位
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/showRoleListByFdzb")
	@ResponseBody
	public Object showRoleListByFdzb() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<PageData> result = null;
		String msg = null;
		String error = null;
		try {
			PageData pd = this.getPageData();
			if (TextUtil.isNotNull(pd.getString("fdStandard"))) {
				result = yjService.showRoleListByFdzb(pd);
				error = "00";
				msg = "查看成功";
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
	 * 新增提成规则
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addOrUpdateTcgz", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public Object addOrUpdateTcgz(HttpServletRequest request, @RequestBody YjtcVo yj) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData result = null;
		String msg = null;
		String error = null;
		try {
			if (null != yj.getFdStandard()) {
				PageData pd = new PageData();
				pd.put("fdStandard", yj.getFdStandard());
				pd.put("roleId", yj.getRoleId());
				Integer id = yj.getId();
				if (null != id && id > 0) {
					pd.put("id", id);
				}
				pd = yjService.findIsExsits3(pd);
				if (null != pd) {
					error = "01";
					msg = "该指标已存在";
				} else {
					PageData pd1 = this.getPageData();
					pd1.put("name", yj.getName());
					pd1.put("tcStandard", yj.getTcStandard());
					pd1.put("fdStandard", yj.getFdStandard());
					pd1.put("yjStandard", yj.getYjStandard());
					pd1.put("roleId", yj.getRoleId());
					if (null != id && id > 0) {
						// 修改
						pd1.put("yjtcId", id);
						yjService.updateTcgz(pd1);
						// 删除原来的关联关系
						yjService.deleteAbout(pd1);
						if (yj.getYjtcList().size() > 0) {
							List<YjtcRelate> list = yj.getYjtcList();
							for (YjtcRelate obj : list) {
								obj.setYjtcId((Integer) pd1.get("yjtcId"));
							}
							yjService.insertBatchTc(list);
						}
						error = "00";
						msg = "修改成功";
					} else {
						// 新增
						yjService.saveTcgz(pd1);
						if (null != pd1.get("id") && null != yj.getYjtcList()) {
							if (yj.getYjtcList().size() > 0) {
								List<YjtcRelate> list = yj.getYjtcList();
								for (YjtcRelate obj : list) {
									obj.setYjtcId(Integer.valueOf(pd1.get("id").toString()));
								}
								yjService.insertBatchTc(list);
							}
						}
						error = "00";
						msg = "保存成功";
					}
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

	// 根据id查看详情
	@RequestMapping(value = "/findById")
	@ResponseBody
	public Object findById() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData result = null;
		String msg = null;
		String error = null;
		try {
			result = yjService.findById(this.getPageData());
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

	// 级联删除
	@RequestMapping(value = "/deleteTcgzById")
	@ResponseBody
	public Object deleteTcgzById() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData result = null;
		String msg = null;
		String error = null;
		try {
			PageData pd = this.getPageData();
			if (TextUtil.isNotNull(pd.getString("id"))) {
				yjService.deleteTcgzById(pd);
				pd.put("yjtcId", pd.getString("id"));
				yjService.deleteAbout(pd);
				error = "00";
				msg = "删除成功";
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
	 * 定时任务(每天晚上11点定时查询)
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Scheduled(cron = "59 59 22 * * ?")
	public void findVisitInfo() throws Exception {

		try {
			TimeUtil tu = new TimeUtil();

			String time1 = tu.getNowTime("yyyy-MM-dd") + " 00:00:00";
			String time2 = tu.getNowTime("yyyy-MM-dd") + " 23:59:59";

			time1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time1).getTime() + "";
			time2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time2).getTime() + "";

			String requestUrl = "https://oapi.dingtalk.com/gettoken?corpid=CORPID&corpsecret=CORPSECRET";

			requestUrl = requestUrl.replace("CORPID", Const.CORPID);
			requestUrl = requestUrl.replace("CORPSECRET", Const.CORPSECRET);

			String httpsRequest = HttpsUtil.httpsRequest(requestUrl, "GET", null);

			Map<String, Object> map = JSONObject.parseObject(httpsRequest, Map.class);

			requestUrl = "https://oapi.dingtalk.com/checkin/record?access_token=ACCESS_TOKEN&department_id=40598521&start_time=START_TIME&end_time=END_TIME&offset=0&size=100&order=asc";
			requestUrl = requestUrl.replace("ACCESS_TOKEN", map.get("access_token").toString());
			requestUrl = requestUrl.replace("START_TIME", time1);
			requestUrl = requestUrl.replace("END_TIME", time2);

			httpsRequest = HttpsUtil.httpsRequest(requestUrl, "GET", null);

			map = JSONObject.parseObject(httpsRequest, Map.class);
			List<Map<String, Object>> m1 = (List<Map<String, Object>>) map.get("data");

			for (int i = 0; null != m1 && i < m1.size(); i++) {
				PageData pd = new PageData();
				pd.put("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Long(m1.get(i).get("timestamp").toString())));
				pd.put("name", m1.get(i).get("name").toString());
				pd.put("place", m1.get(i).get("place").toString());
				pd.put("detailPlace", m1.get(i).get("detailPlace").toString());
				yjService.insertVisitInfo(pd);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 清除tomcat日志
			System.out.println("<<<<<<<<<<<<<=============日志清除==================>>>>>>>>");

			String path = "/usr/local/apache-tomcat-7.0.78/logs";// 文件夹路径
			String[] cmd = new String[] { "/bin/sh", "-c", "rm -rf " + path };
			Runtime.getRuntime().exec(cmd);
		}
	}

	/*private static boolean deleteDir(File dir, boolean flag) {
		boolean directory = dir.isDirectory();
		if (directory) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]), false);
				if (!success) {
					return false;
				}
			}
		}
		// 目录此时为空，可以删除
		return flag ? true : dir.delete();
	}*/

	// ==================================================开始=================================

	/**
	 * 定时任务(每天晚上11点半定时查询)
	 * 
	 * @throws Exception
	 */
	@Scheduled(cron = "59 29 23 * * ?")
	public void findSaleCustomer() throws Exception {

		// 1.查询所有的潜在客户
		TimeUtil tu = new TimeUtil();
		String defaultDay = tu.getNowTime("yyyy-MM-dd"); // 当天的日期

		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(defaultDay));
		currentDate.add(GregorianCalendar.DATE, 1);
		String defaultDay1 = new SimpleDateFormat("yyyy-MM-dd").format(currentDate.getTime());
		String nowTime = tu.getNowTime("MM-dd");
		String currentMonth = tu.getNowTime("yyyy-MM");
		String currentMonth1 = tu.getNowTime("MM");
		String currentYear = tu.getNowTime("yyyy");
		String currentWeekday = tu.getCurrentWeekday(); // 获得本周星期日的日期
		String mondayOFWeek = tu.getMondayOFWeek(); // 获得本周一的日期
		String previousMonthFirst = tu.getPreviousMonthFirst(); // 上个月的1号
		String previousWeekSunday = tu.getPreviousWeekSunday(); // 上周的周日
		String previousWeekSunday2 = tu.getPreviousWeekSunday(7); // 前8周周日
		String previousMonthEnd = tu.getDefaultDay();

		if ("01,02,03".indexOf(currentMonth1) > -1) {
			currentYear = Integer.valueOf((Integer.valueOf(currentYear) - 1)).toString();
		}

		System.out.println("=====当前月日===" + nowTime + "===当前年月===" + currentMonth + "=====当前月====" + currentMonth1 + "=====当前年====" + currentYear);

		// 周报
		if (currentWeekday.equals(defaultDay1)) {
			PageData pd = new PageData();
			pd.put("roleGroup", Const.ROLE_SALES_GROUP);
			List<PageData> list = yjService.findUserByGroup(pd);

			String ids = "";
			// 各个用户
			for (PageData user : list) {
				PageData report = new PageData();
				// 新增报告的姓名
				report.put("userId", user.get("userId"));
				// 新增报告的月份
				report.put("time", currentWeekday);
				// 获取各用户的电话量
				pd.put("startTime", mondayOFWeek);
				pd.put("endTime", currentWeekday);
				pd.put("dfSaleId", user.get("userId"));
				// pd.put("saleId", user.get("userId"));
				PageData dhl1 = mainService.findgj(pd);
				PageData yxkhs1 = mainService.findyyx(pd);

				// 判断电话量是否达标
				user.put("monthOrjd", Integer.valueOf(currentMonth1));
				user.put("year", currentYear);
				user.put("type", 0);
				// 获取电话量指标
				PageData dhl2 = yjService.findDhlByUserId(user);
				user.put("type", 1);
				// 获取意向客户数指标
				PageData yxkhs2 = yjService.findDhlByUserId(user);

				String flag = "";
				if (null != dhl2 && null != dhl1 && null != dhl1.get("dhzcs") && null != dhl2.get("targetDetail") && TextUtil.isNotNull(dhl2.get("targetDetail").toString())
						&& TextUtil.isNotNull(dhl1.get("dhzcs").toString())) {
					String string = new BigDecimal(Double.valueOf(dhl2.get("targetDetail").toString())).divide(new BigDecimal(4), 0, BigDecimal.ROUND_HALF_UP).setScale(0, BigDecimal.ROUND_HALF_UP)
							.toString();
					Integer value = Integer.valueOf(string);
					if (Integer.valueOf(dhl1.get("dhzcs").toString()) >= value) {
						// 达标
						flag = "0";
					} else {
						// 未达标
						flag = "1";
					}
					report.put("bz1", dhl1.get("dhzcs").toString() + "/" + value);
				}

				// 获取各用户的意向客户数
				if (null != yxkhs2 && null != yxkhs1 && null != yxkhs1.get("khs") && null != yxkhs2.get("targetDetail") && TextUtil.isNotNull(yxkhs2.get("targetDetail").toString())
						&& TextUtil.isNotNull(yxkhs1.get("khs").toString())) {
					String value = yxkhs2.get("targetDetail").toString();
					String[] value1 = value.split(",");
					if (value1.length == 2) {
						Integer qz = Integer.valueOf(value1[0]);
						Integer hz = Integer.valueOf(value1[1]);
						qz = Integer.valueOf(new BigDecimal(qz).divide(new BigDecimal(4), 0, BigDecimal.ROUND_HALF_UP).setScale(0, BigDecimal.ROUND_HALF_UP).toString());
						hz = Integer.valueOf(new BigDecimal(hz).divide(new BigDecimal(4), 0, BigDecimal.ROUND_HALF_UP).setScale(0, BigDecimal.ROUND_HALF_UP).toString());
						if (Integer.valueOf(yxkhs1.get("khs").toString()) <= hz && Integer.valueOf(yxkhs1.get("khs").toString()) >= qz) {
							// 达标
							flag = flag + "0";
						} else if (Integer.valueOf(yxkhs1.get("khs").toString()) > hz) {
							// 超标
							flag = flag + "1";
						} else {
							// 未达标
							flag = flag + "1";
						}

						report.put("bz2", yxkhs1.get("khs").toString() + "/" + qz + "," + hz);
					}
				}

				// 获取上一个月的数据
				pd.put("time", previousMonthFirst.substring(0, 7));
				PageData reportInfo2 = yjService.findLastReportInfo(pd);

				if (reportInfo2.getString("tips").equals("转试用期员工考核方案") && "1".equals(reportInfo2.get("isSure").toString())) {
					// 获取上个周电销员工的报告表
					pd.put("time", previousWeekSunday);
					PageData reportInfo = yjService.findLastReportInfo(pd);
					if (flag.length() == 2) {
						if (null != reportInfo) {
							if (!reportInfo.getString("tips").equals("转月考核")) {
								if ("11".equals(reportInfo.getString("flag")) || "01".equals(reportInfo.getString("flag"))) {
									// 如果上个月电话量、意向客户数都没达标
									if ("00".equals(flag) || "10".equals(flag)) {
										report.put("tips", "保持不变");
										report.put("flag", flag);
										report.put("isSure", 1);
									} else {
										report.put("tips", "辞退");
										report.put("flag", flag);
										report.put("isSure", 0);
									}
								}
								if ("00".equals(reportInfo.getString("flag")) || "10".equals(reportInfo.getString("flag"))) {

									if ("00".equals(flag) || "10".equals(flag)) {
										if (Const.ROLE_PHONESy_SALES.equals(user.getString("roleId"))) {
											report.put("tips", "转月考核");
											report.put("flag", flag);
											report.put("isSure", 1);
										} else {
											pd.put("timeStart", previousWeekSunday2);
											pd.put("timeEnd", previousWeekSunday);
											PageData reportInfo1 = yjService.findLastReportInfo2(pd);
											if ("0".equals(reportInfo1.get("flag").toString()) && "8".equals(reportInfo1.get("cs").toString())) {
												report.put("tips", "转月考核");
												report.put("flag", flag);
												report.put("isSure", 1);
											} else {
												report.put("tips", "保持不变");
												report.put("flag", flag);
												report.put("isSure", 1);
											}
										}
									} else {
										report.put("tips", "保持不变");
										report.put("flag", flag);
										report.put("isSure", 1);
									}
								}
							}
						} else {
							// 第一次写周报
							report.put("tips", "保持不变");
							report.put("flag", flag);
							report.put("isSure", 1);
						}

						// 保存userReport_info 表
						report.put("type", 1);
						yjService.insertUserReportInfo(report);

						if (null != report.get("id")) {
							ids = ids + report.get("id") + ",";
						}
					}
				}
			}
			if (TextUtil.isNotNull(ids)) {
				// 保存到通知表
				PageData pd3 = new PageData();
				pd3.put("type", 6);
				pd3.put("relateId", ids.substring(0, ids.length() - 1));
				pd3.put("content", defaultDay1 + "的电销周报");
				pd3.put("flag", 0);
				customerService.saveNoticInfo(pd3);
			}

		}

		// 月报
		if (previousMonthEnd.equals(defaultDay)) {
			// ================(电销团队的月工资)=================
			PageData pd = new PageData();
			pd.put("roleGroup", Const.ROLE_SALES_GROUP);
			List<PageData> list = yjService.findUserByGroup(pd);
			// 各个用户
			String ids = "";
			int i = list.size();
			int i1 = 0;
			for (PageData user : list) {
				PageData report = new PageData();
				// 新增报告的姓名
				report.put("userId", user.get("userId"));
				// 新增报告的月份
				report.put("time", currentMonth);
				user.put("flag", 1);
				PageData zxz = new PageData();
				PageData zxz1 = yjService.findDxByUserId(user); // 获取用户的当前的底薪
				if (null != zxz1) {
					zxz.put("je1", zxz1.get("je1"));
				}
				// 获取各用户的电话量
				pd.put("startTime", currentMonth + "-01");
				pd.put("endTime", defaultDay);
				pd.put("dfSaleId", user.get("userId"));
				// pd.put("saleId", user.get("userId"));
				PageData dhl1 = mainService.findgj(pd);
				PageData yxkhs1 = mainService.findyyx(pd);

				// 判断电话量是否达标
				user.put("monthOrjd", Integer.valueOf(currentMonth1));
				user.put("year", currentYear);
				user.put("type", 0);
				// 获取电话量指标
				PageData dhl2 = yjService.findDhlByUserId(user);
				user.put("type", 1);
				// 获取意向客户数指标
				PageData yxkhs2 = yjService.findDhlByUserId(user);

				PageData pd1 = new PageData();
				String flag = "";
				if (null != dhl2 && null != dhl1 && null != dhl1.get("dhzcs") && null != dhl2.get("targetDetail") && TextUtil.isNotNull(dhl2.get("targetDetail").toString())
						&& TextUtil.isNotNull(dhl1.get("dhzcs").toString())) {
					if (Integer.valueOf(dhl1.get("dhzcs").toString()) >= Integer.valueOf(dhl2.get("targetDetail").toString())) {
						// 达标
						pd1.put("fdzb", 0);
						flag = "0";
					} else {
						// 未达标
						pd1.put("fdzb", 1);
						flag = "1";
					}
					pd1.put("userId", user.get("userId"));
					pd1.put("fdStandard", 0);
					pd1 = yjService.findJe2(pd1);
					report.put("bz1", dhl1.get("dhzcs").toString() + "/" + dhl2.get("targetDetail").toString());
				}
				// 获取电销绩效薪资
				user.put("flag", 0);
				user.put("type", "0");
				PageData jxxz = yjService.findDxByUserId(user);
				if (null != pd1 && null != jxxz && null != pd1.get("tc") && null != jxxz.get("je1")) {
					zxz.put("je2", new BigDecimal(Double.valueOf(pd1.get("tc").toString())).multiply(new BigDecimal(Double.valueOf(jxxz.get("je1").toString())).divide(new BigDecimal(100))));
				}
				PageData pd2 = new PageData();
				pd2.put("userId", user.get("userId"));
				pd2.put("fdStandard", 1);

				// 获取各用户的意向客户数
				if (null != yxkhs2 && null != yxkhs1 && null != yxkhs1.get("khs") && null != yxkhs2.get("targetDetail") && TextUtil.isNotNull(yxkhs2.get("targetDetail").toString())
						&& TextUtil.isNotNull(yxkhs1.get("khs").toString())) {
					String value = yxkhs2.get("targetDetail").toString();
					String[] value1 = value.split(",");
					if (value1.length == 2) {

						Integer qz = Integer.valueOf(value1[0]);
						Integer hz = Integer.valueOf(value1[1]);
						if (Integer.valueOf(yxkhs1.get("khs").toString()) <= hz && Integer.valueOf(yxkhs1.get("khs").toString()) >= qz) {
							// 达标
							pd2.put("fdzb", 0);
							pd2 = yjService.findJe2(pd2);
							if (null != pd2 && null != pd2.get("tc")) {
								if (null != jxxz && null != jxxz.get("je1")) {
									zxz.put("je3",
											new BigDecimal(Double.valueOf(pd2.get("tc").toString())).multiply(new BigDecimal(Double.valueOf(jxxz.get("je1").toString()))).divide(new BigDecimal(100)));
								} else {
									zxz.put("je3", 0);
								}
							}
							flag = flag + "0";
							i1++;
						} else if (Integer.valueOf(yxkhs1.get("khs").toString()) > hz) {
							// 超标
							pd2.put("fdzb", 0);
							pd2 = yjService.findJe2(pd2);
							if (null != pd2 && null != pd2.get("tc")) {
								BigDecimal tc1 = new BigDecimal(Double.valueOf(pd2.get("tc").toString())).multiply(new BigDecimal(Double.valueOf(jxxz.get("je1").toString()))).divide(
										new BigDecimal(100));

								pd2.put("fdzb", 2);
								pd2 = yjService.findJe2(pd2);
								if (null != pd2) {
									String[] tcs = pd2.get("tc").toString().split(",");

									tc1 = tc1.add(new BigDecimal(Double.valueOf(tcs[1])).multiply(new BigDecimal(Integer.valueOf(yxkhs1.get("khs").toString()) - hz)));
									zxz.put("je3", tc1);
								}
							}
							flag = flag + "1";
							i1++;
						} else {
							// 未达标
							pd2.put("fdzb", 1);
							pd2 = yjService.findJe2(pd2);
							if (null != pd2 && null != pd2.get("tc")) {
								zxz.put("je3", new BigDecimal(Double.valueOf(pd2.get("tc").toString())).multiply(new BigDecimal(Double.valueOf(jxxz.get("je1").toString())))
										.divide(new BigDecimal(100)));
							}
							flag = flag + "1";
						}
						report.put("bz2", yxkhs1.get("khs").toString() + "/" + yxkhs2.get("targetDetail").toString());
					}
				}
				// 判断当前用户是否是电销主管
				if (user.get("roleId").toString().equals(Const.ROLE_PHONE_SALES_DIRECTOR)) {
					user.put("flag", 0);
					user.put("type", 1);
					PageData dxzgxz = yjService.findDxByUserId(user);
					if (null != dxzgxz) {
						zxz.put("je4", dxzgxz.get("je1"));
					}
				} else {
					zxz.put("je4", 0);
				}

				zxz.put("userId", user.get("userId"));
				zxz.put("flag", 0);
				zxz.put("createTime", currentMonth);
				// 插入ygxz_info
				yjService.insertYgxzInfo(zxz);

				// 获取上个月电销员工的报告表
				pd.put("time", previousMonthFirst.substring(0, 7));
				PageData reportInfo = yjService.findLastReportInfo(pd);
				if (flag.length() == 2) {
					if (null != reportInfo) {
						if (!(reportInfo.getString("tips").equals("转试用期员工考核方案") && "1".equals(reportInfo.get("isSure").toString()))) {
							if (Const.ROLE_PHONESy_SALES.equals(user.getString("roleId"))) {
								// 如果是试用期员工
								if ("11".equals(reportInfo.getString("flag"))) {
									// 如果上个月电话量、意向客户数都没达标
									if ("11".equals(flag)) {
										report.put("tips", "辞退");
										report.put("flag", flag);
										report.put("isSure", 0);
									} else if ("01".equals(flag)) {
										report.put("tips", "转试用期员工考核方案");
										report.put("flag", flag);
										report.put("isSure", 0);
									} else {
										report.put("tips", "保持不变");
										report.put("flag", flag);
										report.put("isSure", 1);
									}
								} else if ("01".equals(reportInfo.getString("flag"))) {
									if ("00".equals(flag) || "10".equals(flag)) {
										report.put("tips", "保持不变");
										report.put("flag", flag);
										report.put("isSure", 1);
									} else {
										report.put("tips", "转试用期员工考核方案");
										report.put("flag", flag);
										report.put("isSure", 0);
									}
								} else {
									report.put("tips", "保持不变");
									report.put("flag", flag);
									report.put("isSure", 1);
								}
							} else {
								// 如果是正式员工
								if ("11".equals(reportInfo.getString("flag"))) {
									// 如果上个月电话量、意向客户数都没达标
									if ("11".equals(flag)) {
										report.put("tips", "转试用期员工考核方案");
										report.put("flag", flag);
										report.put("isSure", 0);
									} else {
										report.put("tips", "保持不变");
										report.put("flag", flag);
										report.put("isSure", 1);
									}
								} else {
									report.put("tips", "保持不变");
									report.put("flag", flag);
									report.put("isSure", 1);
								}
							}
						} else {
							// 转试用期员工考核方案获取本月最新的周报，如果周报里有按月考核
							pd.put("time", currentMonth);
							PageData reportInfo1 = yjService.findLastReportInfo1(pd);

							if (null != reportInfo1 && reportInfo1.getString("tips").equals("转月考核")) {
								if (Const.ROLE_PHONESy_SALES.equals(user.getString("roleId"))) {
									// 如果是试用期员工
									if ("11".equals(reportInfo.getString("flag"))) {
										// 如果上个月电话量、意向客户数都没达标
										if ("11".equals(flag)) {
											report.put("tips", "辞退");
											report.put("flag", flag);
											report.put("isSure", 0);
										} else if ("01".equals(flag)) {
											report.put("tips", "转试用期员工考核方案");
											report.put("flag", flag);
											report.put("isSure", 0);
										} else {
											report.put("tips", "保持不变");
											report.put("flag", flag);
											report.put("isSure", 1);
										}
									} else if ("01".equals(reportInfo.getString("flag"))) {
										if ("00".equals(flag) || "10".equals(flag)) {
											report.put("tips", "保持不变");
											report.put("flag", flag);
											report.put("isSure", 1);
										} else {
											report.put("tips", "转试用期员工考核方案");
											report.put("flag", flag);
											report.put("isSure", 0);
										}
									} else {
										report.put("tips", "保持不变");
										report.put("flag", flag);
										report.put("isSure", 1);
									}
								} else {
									// 如果是正式员工
									if ("11".equals(reportInfo.getString("flag"))) {
										// 如果上个月电话量、意向客户数都没达标
										if ("11".equals(flag)) {
											report.put("tips", "转试用期员工考核方案");
											report.put("flag", flag);
											report.put("isSure", 0);
										} else {
											report.put("tips", "保持不变");
											report.put("flag", flag);
											report.put("isSure", 1);
										}
									} else {
										report.put("tips", "保持不变");
										report.put("flag", flag);
										report.put("isSure", 1);
									}
								}
							}/*else{
								report.put("tips", "转试用期员工考核方案");
								report.put("flag", flag);
								report.put("isSure", 0);
								}*/
						}

					} else {
						// 第一次来
						report.put("tips", "保持不变");
						report.put("flag", flag);
						report.put("isSure", 1);
					}

					// 保存userReport_info 表
					report.put("type", 0);
					yjService.insertUserReportInfo(report);

					if (null != report.get("id")) {
						ids = ids + report.get("id") + ",";
					}
				}

			}

			// 获取电销主管的工资并修改yjService.showYgxzOrTc(pd);
			PageData pd33 = new PageData();
			pd33.put("flag", 0);
			pd33.put("roleId", Const.ROLE_PHONE_SALES_DIRECTOR);
			List<PageData> showYgxzOrTc = yjService.showYgxzOrTc(pd33);
			for (int j = 0; j < showYgxzOrTc.size(); j++) {
				PageData pd1 = new PageData();
				PageData user = new PageData();
				user.put("flag", 0);
				user.put("type", 1);
				user.put("userId", showYgxzOrTc.get(j).get("userId"));
				PageData fdxz = yjService.findDxByUserId(user);
				if (null != fdxz) {
					pd1.put("je4",
							new BigDecimal(Double.valueOf(fdxz.get("je1").toString())).multiply(new BigDecimal(i1)).divide(new BigDecimal(i), 2, BigDecimal.ROUND_HALF_UP)
									.setScale(2, BigDecimal.ROUND_HALF_UP));
				}

				pd1.put("userId", showYgxzOrTc.get(j).get("userId"));
				yjService.updateYgxz(pd1);
			}

			if (TextUtil.isNotNull(ids)) {
				// 保存到通知表
				PageData pd3 = new PageData();
				pd3.put("type", 5);
				pd3.put("relateId", ids.substring(0, ids.length() - 1));
				pd3.put("content", defaultDay + "的电销月报");
				pd3.put("flag", 0);
				customerService.saveNoticInfo(pd3);
			}
			// =================(商务顾问的月工资)==============================
			PageData pdswgw = new PageData();
			pdswgw.put("roleGroup", Const.ROLE_SWGWS_GROUP);
			List<PageData> list1 = yjService.findUserByGroup(pdswgw);
			// 各个用户
			for (PageData user : list1) {

				if (!Const.ROLE_SALES_ELITEWX.equals(user.getString("roleId")) && !Const.ROLE_SALES_ELITEDX.equals(user.getString("roleId"))) {
					// 获取底薪+浮动工资+拜访奖金+签单奖金
					user.put("flag", 1);
					PageData zxz = yjService.findDxByUserId(user); // 获取用户的当前的底薪

					if (null == zxz) {
						zxz = new PageData();
					}
					// 浮动工资
					user.put("flag", 0);
					user.put("type", 2);
					PageData fdxz = yjService.findDxByUserId(user);
					if (null != fdxz) {
						zxz.put("je2", fdxz.get("je1"));
					}
					zxz.put("userId", user.get("userId"));
					zxz.put("flag", 1);
					zxz.put("createTime", currentMonth);
					// 拜访奖金
					pdswgw.put("saleId", user.get("userId"));
					pdswgw.put("time", currentMonth);
					PageData bfl1 = yjService.findCountVisit(pdswgw);
					if (null != bfl1 && null != bfl1.get("bfl") && TextUtil.isNotNull(bfl1.get("bfl").toString())) {
						PageData pd1 = new PageData();
						pd1.put("time", currentMonth);
						pd1.put("saleId", user.get("userId"));
						pd1.put("type", "11");
						pd1 = yjService.findbfOrQd(pd1);
						if (null != pd1 && null != pd1.get("conditions")) {
							if (Integer.valueOf(bfl1.get("bfl").toString()) >= Integer.valueOf(pd1.get("conditions").toString())) {
								// 达标
								zxz.put("je3",
										new BigDecimal(Integer.valueOf(bfl1.get("bfl").toString())).subtract(new BigDecimal(Integer.valueOf(pd1.get("targetDetail").toString()))).multiply(
												new BigDecimal(Integer.valueOf(pd1.get("targetDetail").toString()))));
							} else {
								// 未达标
								zxz.put("je3", new BigDecimal(0));
							}
						}
					}
					// 签单奖金
					user.put("startTime", currentMonth + "-01 00:00:00");
					user.put("endTime", defaultDay);
					user.put("saleId", user.get("userId"));
					user.put("type", "1");
					List<PageData> qdl1 = mainService.showSwgwYjmb(user);

					if (null != qdl1 && null != qdl1.get(0).get("dkje") && TextUtil.isNotNull(qdl1.get(0).get("dkje").toString())) {
						PageData pd1 = new PageData();
						pd1.put("time", currentMonth);
						pd1.put("saleId", user.get("userId"));
						pd1.put("type", "12");
						pd1 = yjService.findbfOrQd(pd1);
						if (null != pd1 && null != pd1.get("conditions")) {
							if (Integer.valueOf(qdl1.get(0).get("dkje").toString()) >= Integer.valueOf(pd1.get("conditions").toString())) {
								// 达标
								zxz.put("je4", new BigDecimal(Integer.valueOf(qdl1.get(0).get("dkje").toString())).subtract(new BigDecimal(Integer.valueOf(pd1.get("targetDetail").toString())))
										.multiply(new BigDecimal(Integer.valueOf(pd1.get("targetDetail").toString()))));
							} else {
								// 未达标
								zxz.put("je4", new BigDecimal(0));
							}
						}
					}
					// 插入ygxz_info
					yjService.insertYgxzInfo(zxz);
				} else if (Const.ROLE_SALES_ELITEWX.equals(user.getString("roleId"))) {
					// 特殊员工的工资（到款金额、提成比例、总薪资)
					user.put("flag", 2);
					PageData zxz1 = yjService.findDxByUserId(user);
					PageData zxz = new PageData();
					if (null != zxz1) {
						zxz.put("tcbl", zxz1.get("je1"));
					}
					PageData wx = yjService.findwxDkje(user);
					// 获取商务外协这个月的到款金额。。
					if (null != wx && null != wx.get("hkMoney")) {
						BigDecimal object1 = new BigDecimal(Double.valueOf(wx.get("hkMoney").toString())).setScale(2, BigDecimal.ROUND_HALF_UP);
						zxz.put("je1", object1);
					}
					zxz.put("flag", 2);
					zxz.put("userId", user.get("userId"));
					zxz.put("createTime", currentMonth);
					yjService.insertYgxzInfo(zxz);
				} else {
					// 电销商务顾问的工资 获取底薪+拜访奖金+签单奖金
					user.put("flag", 1);
					PageData zxz = yjService.findDxByUserId(user);
					if (null == zxz) {
						zxz = new PageData();
					}
					// 拜访量
					pdswgw.put("saleId", user.get("userId"));
					pdswgw.put("time", currentMonth);
					PageData bfl1 = yjService.findCountVisit(pdswgw);
					if (null != bfl1 && null != bfl1.get("bfl") && TextUtil.isNotNull(bfl1.get("bfl").toString())) {
						PageData pd1 = new PageData();
						pd1.put("fdStandard", 7);
						pd1.put("userId", user.get("userId"));
						List<PageData> pd2 = yjService.findJe3(pd1);
						pd1.put("saleId", user.get("userId"));
						pd1.put("type", "8");
						pd1.put("startTime", new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(defaultDay)));
						pd1.put("endTime", new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(defaultDay)));
						List<PageData> result1 = mainService.findDhlAndYxkhsBySaleId(pd1);

						if (null != result1 && result1.size() == 1) {
							if (Integer.valueOf(bfl1.get("bfl").toString()) >= Integer.valueOf(result1.get(0).get("targetDetail").toString())) {
								// 0达标
								if (null != pd2.get(0) && null != pd2.get(0).get("fdzb") && "0".equals(pd2.get(0).get("fdzb").toString())) {
									zxz.put("je2", new BigDecimal(Integer.valueOf(bfl1.get("bfl").toString())).multiply(new BigDecimal(Integer.valueOf(pd2.get(0).get("tc").toString()))));
								}
								if (null != pd2.get(1) && null != pd2.get(1).get("fdzb") && "0".equals(pd2.get(1).get("fdzb").toString())) {
									zxz.put("je2", new BigDecimal(Integer.valueOf(bfl1.get("bfl").toString())).multiply(new BigDecimal(Integer.valueOf(pd2.get(1).get("tc").toString()))));
								}
							} else {
								// 未达标
								if (null != pd2.get(0) && null != pd2.get(0).get("fdzb") && "1".equals(pd2.get(0).get("fdzb").toString())) {
									zxz.put("je2", new BigDecimal(Integer.valueOf(pd2.get(0).get("tc").toString())));
								}
								if (null != pd2.get(1) && null != pd2.get(1).get("fdzb") && "1".equals(pd2.get(1).get("fdzb").toString())) {
									zxz.put("je2", new BigDecimal(Integer.valueOf(pd2.get(1).get("tc").toString())));
								}
							}
						}
					}

					// 客户签约奖金
					pdswgw.put("startTime", currentMonth + "-01 00:00:00");
					pdswgw.put("endTime", defaultDay + " 23:59:59");
					pdswgw.put("type", "0");
					// 实际签约客户数
					List<PageData> resultsjqy = mainService.showDxSwgwYjmb1(pdswgw);

					if (null != resultsjqy && resultsjqy.size() > 0 && null != resultsjqy.get(0).get("sl")) {
						PageData pd1 = new PageData();
						pd1.put("fdStandard", 8);
						pd1.put("userId", user.get("userId"));
						List<PageData> pd2 = yjService.findJe3(pd1);
						pd1.put("saleId", user.get("userId"));
						pd1.put("type", "9");
						pd1.put("startTime", new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(defaultDay)));
						pd1.put("endTime", new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(defaultDay)));
						// 指标客户数
						List<PageData> resultzbqy = mainService.findDhlAndYxkhsBySaleId(pd1);

						if (null != resultzbqy && resultzbqy.size() > 0 && resultzbqy.size() == 1) {
							if (Integer.valueOf(resultsjqy.get(0).get("sl").toString()) >= Integer.valueOf(resultzbqy.get(0).get("targetDetail").toString())) {
								// 0达标
								if (null != pd2.get(0) && null != pd2.get(0).get("fdzb") && "0".equals(pd2.get(0).get("fdzb").toString())) {
									zxz.put("je3", new BigDecimal(Integer.valueOf(resultsjqy.get(0).get("sl").toString())).multiply(new BigDecimal(Integer.valueOf(pd2.get(0).get("tc").toString()))));
								}
								if (null != pd2.get(1) && null != pd2.get(1).get("fdzb") && "0".equals(pd2.get(1).get("fdzb").toString())) {
									zxz.put("je3", new BigDecimal(Integer.valueOf(resultsjqy.get(0).get("sl").toString())).multiply(new BigDecimal(Integer.valueOf(pd2.get(1).get("tc").toString()))));
								}
							} else {
								// 未达标
								if (null != pd2.get(0) && null != pd2.get(0).get("fdzb") && "1".equals(pd2.get(0).get("fdzb").toString())) {
									zxz.put("je3", new BigDecimal(Integer.valueOf(pd2.get(0).get("tc").toString())));
								}
								if (null != pd2.get(1) && null != pd2.get(1).get("fdzb") && "1".equals(pd2.get(1).get("fdzb").toString())) {
									zxz.put("je3", new BigDecimal(Integer.valueOf(pd2.get(1).get("tc").toString())));
								}
							}
						}
					}

					// 订单标的奖金
					pdswgw.put("startTime", currentMonth + "-01 00:00:00");
					pdswgw.put("endTime", defaultDay + " 23:59:59");
					pdswgw.put("type", "1");
					// 实际
					List<PageData> resultddje = mainService.showDxSwgwYjmb1(pdswgw);

					if (null != resultddje && resultddje.size() > 0 && null != resultddje.get(0).get("ddje")) {
						PageData pd1 = new PageData();
						pd1.put("fdStandard", 9);
						pd1.put("userId", user.get("userId"));
						// 提成
						List<PageData> pd2 = yjService.findJe3(pd1);
						pd1.put("saleId", user.get("userId"));
						pd1.put("type", "10");
						pd1.put("startTime", new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(defaultDay)));
						pd1.put("endTime", new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(defaultDay)));
						// 指标客户数
						List<PageData> resultzbje = mainService.findDhlAndYxkhsBySaleId(pd1);

						for (int j = 0; pd2 != null && j < pd2.size(); j++) {
							if (TextUtil.isNotNull(pd2.get(j).getString("fdzb"))) {
								String[] split = pd2.get(j).getString("fdzb").split(",");
								if (split.length == 1) {
									if (Double.valueOf(resultddje.get(0).get("ddje").toString()) >= Double.valueOf(split[0])) {
										zxz.put("je4", new BigDecimal(Double.valueOf(pd2.get(j).get("tc").toString())));
										break;
									}
								} else {
									if (Double.valueOf(resultddje.get(0).get("ddje").toString()) >= Double.valueOf(split[0])
											&& Double.valueOf(resultddje.get(0).get("ddje").toString()) < Double.valueOf(split[1])) {
										zxz.put("je4", new BigDecimal(Double.valueOf(pd2.get(j).get("tc").toString())));
										break;
									}
								}
							}
						}

						if (null != resultzbje && resultzbje.size() == 1 && null != resultzbje.get(0).get("targetDetail")) {
							if (Double.valueOf(resultddje.get(0).get("ddje").toString()) >= Double.valueOf(resultzbje.get(0).get("targetDetail").toString())) {
								// 0达标

								if (null != pd2.get(0) && null != pd2.get(0).get("fdzb")) {

									String[] split = pd2.get(0).get("fdzb").toString().split(",");
									Double qz = Double.valueOf(split[0]);
									Double hz = Double.valueOf(split[1]);

									if (Double.valueOf(resultddje.get(0).get("ddje").toString()) <= hz && Double.valueOf(resultddje.get(0).get("ddje").toString()) >= qz)
										zxz.put("je4", new BigDecimal(Double.valueOf(pd2.get(0).get("tc").toString())));

								}

							} else {
								// 未达标
								if (null != pd2.get(0) && null != pd2.get(0).get("fdzb") && "1".equals(pd2.get(0).get("fdzb").toString())) {
									zxz.put("je4", new BigDecimal(Double.valueOf(pd2.get(0).get("tc").toString())));
								}
								if (null != pd2.get(1) && null != pd2.get(1).get("fdzb") && "1".equals(pd2.get(1).get("fdzb").toString())) {
									zxz.put("je4", new BigDecimal(Double.valueOf(pd2.get(1).get("tc").toString())));
								}
							}
						}
					}
					zxz.put("flag", 5);
					zxz.put("userId", user.get("userId"));
					zxz.put("createTime", currentMonth);
					yjService.insertYgxzInfo(zxz);
				}
			}

			// 行政人员的月工资
			List<PageData> xzryList = yjService.findUserByGroup1();
			// 各个用户
			for (PageData user : xzryList) {
				user.put("flag", 1);
				PageData zxz = yjService.findDxByUserId(user); // 获取用户的当前的底薪
				if (null == zxz) {
					zxz = new PageData();
				}
				zxz.put("flag", 3);
				zxz.put("userId", user.get("userId"));
				zxz.put("createTime", currentMonth);
				yjService.insertYgxzInfo(zxz);
			}

		}

		// 季报(商务顾问季报的展示关系到岗位的升级降级)
		if ("06-30,09-30,12-31,03-31".indexOf(nowTime) > -1) {

			List<PageData> result1 = null;
			PageData pd = new PageData();
			PageData pd1 = new PageData();
			String startTime = "";
			String endTime = "";
			String year = currentYear;
			String time = "";
			String lastJd = "";

			if ("06-30".equals(nowTime)) {
				time = "1";
				startTime = year + "-04-01 00:00:00";
				endTime = year + "-06-30 23:59:59";
				lastJd = year + "-03-31";
			}

			if ("09-30".equals(nowTime)) {
				// 7~9月的季报(第二季度)
				time = "2";
				startTime = year + "-07-01 00:00:00";
				endTime = year + "-09-30 23:59:59";
				lastJd = year + "-06-30";
			}

			if ("12-31".equals(nowTime)) {
				// 10~12月的季报(第三季度)
				time = "3";
				startTime = year + "-10-01 00:00:00";
				endTime = year + "-12-31 23:59:59";
				lastJd = year + "-09-30";
			}

			if ("03-31".equals(nowTime)) {
				// 年报(统计前年例如:2017-04-01 - 2018-03-31)
				// 1~3月的季报(第四季度)
				time = "4";
				String year1 = Integer.valueOf((Integer.parseInt(year) + 1)).toString();
				startTime = year1 + "-01-01 00:00:00";
				endTime = year1 + "-03-31 23:59:59";
				lastJd = year + "-12-31";
			}

			pd.put("startTime", startTime);
			pd.put("endTime", endTime);
			// 商务顾问组
			pd.put("roleGroup", Const.ROLE_SWGWS_GROUP);
			// 求销售助理的

			List<PageData> list = yjService.findUserByGroup(pd);
			String ids = "";
			for (PageData user : list) {
				PageData reportInfo = new PageData();
				if (!Const.ROLE_SALES_ELITEWX.equals(user.getString("roleId")) && !Const.ROLE_SALES_ELITEDX.equals(user.getString("roleId"))) {
					pd.put("saleId", user.getString("userId"));
					pd1.put("type", "2");
					pd1.put("saleId", user.getString("userId"));
					pd1.put("roleId", user.getString("roleId"));
					pd1.put("time", time);
					pd1.put("year", year);
					result1 = mainService.findDhlAndYxkhsBySaleId(pd1);

					// 佣金到款
					reportInfo.put("mbyjdk", result1 != null && result1.size() > 0 ? result1.get(0).get("targetDetail") : 0);
					pd.put("type", "0");
					result1 = mainService.showSwgwYjmb(pd);
					reportInfo.put("yjdk", result1 != null && result1.size() > 0 ? result1.get(0).get("dkje") : 0);

					pd1.put("type", "7");
					result1 = mainService.findDhlAndYxkhsBySaleId(pd1);
					// 商务顾问辞退的指标
					BigDecimal zbA = result1.size() > 0 && null != result1.get(0).get("zbA") ? new BigDecimal(Integer.valueOf(result1.get(0).get("zbA").toString())) : new BigDecimal(0);
					BigDecimal zbB = result1.size() > 0 && null != result1.get(0).get("zbB") ? new BigDecimal(Integer.valueOf(result1.get(0).get("zbB").toString())) : new BigDecimal(0);
					BigDecimal bfzb = result1.size() > 0 && null != result1.get(0).get("bfzb") ? new BigDecimal(Integer.valueOf(result1.get(0).get("bfzb").toString())) : new BigDecimal(0);

					BigDecimal bigDecimal1 = new BigDecimal(Double.valueOf(reportInfo.get("yjdk").toString())).setScale(2, BigDecimal.ROUND_HALF_UP);
					BigDecimal bigDecimal2 = new BigDecimal(Double.valueOf(reportInfo.get("mbyjdk").toString())).setScale(2, BigDecimal.ROUND_HALF_UP);
					// 合同数量
					pd1.put("type", "3");
					pd1.put("startTime", new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(startTime)));
					pd1.put("endTime", new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(endTime)));
					result1 = mainService.findDhlAndYxkhsBySaleId(pd1);

					BigDecimal bigDecimal3 = result1.size() > 0 && null != result1.get(0).get("targetDetail") ? new BigDecimal(Integer.valueOf(result1.get(0).get("targetDetail").toString()))
							: new BigDecimal(0);
					BigDecimal bigDecimal4 = result1.size() > 0 && null != result1.get(1).get("targetDetail") ? new BigDecimal(Integer.valueOf(result1.get(1).get("targetDetail").toString()))
							: new BigDecimal(0);
					BigDecimal bigDecimal5 = result1.size() > 0 && null != result1.get(2).get("targetDetail") ? new BigDecimal(Integer.valueOf(result1.get(2).get("targetDetail").toString()))
							: new BigDecimal(0);

					pd.put("type", "1");
					result1 = mainService.showSwgwYjmb1(pd);
					result1 = MainIndexController.toZouResult(result1, pd.getString("startTime"), pd.getString("endTime"), "1", 5);

					BigDecimal bigDecimal6 = result1.size() > 0 && null != result1.get(0).get("htsl") ? new BigDecimal(Integer.valueOf(result1.get(0).get("htsl").toString())) : new BigDecimal(0);
					BigDecimal bigDecimal7 = result1.size() > 0 && null != result1.get(1).get("htsl") ? new BigDecimal(Integer.valueOf(result1.get(1).get("htsl").toString())) : new BigDecimal(0);
					BigDecimal bigDecimal8 = result1.size() > 0 && null != result1.get(2).get("htsl") ? new BigDecimal(Integer.valueOf(result1.get(2).get("htsl").toString())) : new BigDecimal(0);

					// 订单总金额
					pd1.put("type", "4");
					result1 = mainService.findDhlAndYxkhsBySaleId(pd1);

					BigDecimal bigDecimal9 = result1.size() > 0 && null != result1.get(0).get("targetDetail") ? new BigDecimal(Integer.valueOf(result1.get(0).get("targetDetail").toString()))
							: new BigDecimal(0);
					BigDecimal bigDecimal10 = result1.size() > 0 && null != result1.get(1).get("targetDetail") ? new BigDecimal(Integer.valueOf(result1.get(1).get("targetDetail").toString()))
							: new BigDecimal(0);
					BigDecimal bigDecimal11 = result1.size() > 0 && null != result1.get(2).get("targetDetail") ? new BigDecimal(Integer.valueOf(result1.get(2).get("targetDetail").toString()))
							: new BigDecimal(0);

					pd.put("type", "2");
					result1 = mainService.showSwgwYjmb1(pd);
					result1 = MainIndexController.toZouResult(result1, pd.getString("startTime"), pd.getString("endTime"), "1", 7);

					BigDecimal bigDecimal12 = result1.size() > 0 && null != result1.get(0).get("ddje") ? new BigDecimal(Double.valueOf(result1.get(0).get("ddje").toString())).setScale(2,
							BigDecimal.ROUND_HALF_UP) : new BigDecimal(0);
					BigDecimal bigDecimal13 = result1.size() > 0 && null != result1.get(1).get("ddje") ? new BigDecimal(Double.valueOf(result1.get(1).get("ddje").toString())).setScale(2,
							BigDecimal.ROUND_HALF_UP) : new BigDecimal(0);
					BigDecimal bigDecimal14 = result1.size() > 0 && null != result1.get(2).get("ddje") ? new BigDecimal(Double.valueOf(result1.get(2).get("ddje").toString())).setScale(2,
							BigDecimal.ROUND_HALF_UP) : new BigDecimal(0);

					// 拜访量
					pd1.put("type", "5");
					result1 = mainService.findDhlAndYxkhsBySaleId(pd1);

					BigDecimal bigDecima27 = result1.size() > 0 && null != result1.get(0).get("targetDetail") ? new BigDecimal(Integer.valueOf(result1.get(0).get("targetDetail").toString()))
							: new BigDecimal(0);
					BigDecimal bigDecima28 = result1.size() > 0 && null != result1.get(1).get("targetDetail") ? new BigDecimal(Integer.valueOf(result1.get(1).get("targetDetail").toString()))
							: new BigDecimal(0);
					BigDecimal bigDecima29 = result1.size() > 0 && null != result1.get(2).get("targetDetail") ? new BigDecimal(Integer.valueOf(result1.get(2).get("targetDetail").toString()))
							: new BigDecimal(0);

					pd.put("type", "3");
					pd.put("mark", "1");
					result1 = mainService.showSwgwYjmb1(pd);
					result1 = MainIndexController.toZouResult(result1, pd.getString("startTime"), pd.getString("endTime"), "1", 8);

					BigDecimal bigDecimal30 = result1.size() > 0 && null != result1.get(0).get("bfl") ? new BigDecimal(Integer.valueOf(result1.get(0).get("bfl").toString())) : new BigDecimal(0);
					BigDecimal bigDecimal31 = result1.size() > 0 && null != result1.get(1).get("bfl") ? new BigDecimal(Integer.valueOf(result1.get(1).get("bfl").toString())) : new BigDecimal(0);
					BigDecimal bigDecimal32 = result1.size() > 0 && null != result1.get(2).get("bfl") ? new BigDecimal(Integer.valueOf(result1.get(2).get("bfl").toString())) : new BigDecimal(0);

					reportInfo.put("userId", user.get("userId"));

					reportInfo.put("bz1", bigDecimal1.toString() + "/" + bigDecimal2.toString());
					reportInfo.put("bz2", bigDecimal6.toString() + "," + bigDecimal7.toString() + "," + bigDecimal8.toString() + "/" + bigDecimal3.toString() + "," + bigDecimal4.toString() + ","
							+ bigDecimal5.toString());
					reportInfo.put("bz3", bigDecimal12.toString() + "," + bigDecimal13.toString() + "," + bigDecimal14.toString() + "/" + bigDecimal9.toString() + "," + bigDecimal10.toString() + ","
							+ bigDecimal11.toString());
					reportInfo.put("bz4", bigDecimal30.toString() + "," + bigDecimal31.toString() + "," + bigDecimal32.toString() + "/" + bigDecima27.toString() + "," + bigDecima28.toString() + ","
							+ bigDecima29.toString());

					reportInfo.put("time", currentMonth);
					reportInfo.put("type", 2);
					reportInfo.put("orgRoleId", user.get("roleId"));
					reportInfo.put("flag1", 0);
					reportInfo.put("qkje", 0);

					pd.put("time", lastJd);
					PageData reportInfo3 = yjService.findLastReportInfo3(pd);

					int htsjs1 = 0;

					if (bigDecimal3.compareTo(new BigDecimal(0)) != 0) {
						htsjs1++;
					} else {
						bigDecimal6 = new BigDecimal(0);
					}

					if (bigDecimal4.compareTo(new BigDecimal(0)) != 0) {
						htsjs1++;
					} else {
						bigDecimal7 = new BigDecimal(0);
					}

					if (bigDecimal5.compareTo(new BigDecimal(0)) != 0) {
						htsjs1++;
					} else {
						bigDecimal8 = new BigDecimal(0);
					}

					BigDecimal yjhtsl = null;
					BigDecimal yjmbhtsl = null;
					if (htsjs1 > 0) {
						yjmbhtsl = bigDecimal3.add(bigDecimal4).add(bigDecimal5).divide(new BigDecimal(htsjs1), 0, BigDecimal.ROUND_HALF_UP).setScale(0, BigDecimal.ROUND_HALF_UP);
						yjhtsl = bigDecimal6.add(bigDecimal7).add(bigDecimal8).divide(new BigDecimal(htsjs1), 0, BigDecimal.ROUND_HALF_UP).setScale(0, BigDecimal.ROUND_HALF_UP);
					}

					int wtjejs1 = 0;

					if (bigDecimal9.compareTo(new BigDecimal(0)) != 0) {
						wtjejs1++;
					} else {
						bigDecimal12 = new BigDecimal(0);
					}

					if (bigDecimal10.compareTo(new BigDecimal(0)) != 0) {
						wtjejs1++;
					} else {
						bigDecimal13 = new BigDecimal(0);
					}

					if (bigDecimal11.compareTo(new BigDecimal(0)) != 0) {
						wtjejs1++;
					} else {
						bigDecimal14 = new BigDecimal(0);
					}

					BigDecimal yjwtje = null;
					BigDecimal yjmbwtje = null;
					if (wtjejs1 > 0) {
						yjmbwtje = bigDecimal9.add(bigDecimal10).add(bigDecimal11).divide(new BigDecimal(wtjejs1), 2, BigDecimal.ROUND_HALF_UP).setScale(2, BigDecimal.ROUND_HALF_UP);
						yjwtje = bigDecimal12.add(bigDecimal13).add(bigDecimal14).divide(new BigDecimal(wtjejs1), 2, BigDecimal.ROUND_HALF_UP).setScale(2, BigDecimal.ROUND_HALF_UP);
					}

					int bfljs1 = 0;

					if (bigDecima27.compareTo(new BigDecimal(0)) != 0) {
						bfljs1++;
					} else {
						bigDecimal30 = new BigDecimal(0);
					}

					if (bigDecima28.compareTo(new BigDecimal(0)) != 0) {
						bfljs1++;
					} else {
						bigDecimal31 = new BigDecimal(0);
					}

					if (bigDecima29.compareTo(new BigDecimal(0)) != 0) {
						bfljs1++;
					} else {
						bigDecimal32 = new BigDecimal(0);
					}

					BigDecimal bfl = null;
					BigDecimal mbbfl = null;
					if (bfljs1 > 0) {
						bfl = bigDecima27.add(bigDecima28).add(bigDecima29).divide(new BigDecimal(bfljs1), 0, BigDecimal.ROUND_HALF_UP).setScale(0, BigDecimal.ROUND_HALF_UP);
						mbbfl = bigDecimal30.add(bigDecimal31).add(bigDecimal32).divide(new BigDecimal(bfljs1), 0, BigDecimal.ROUND_HALF_UP).setScale(0, BigDecimal.ROUND_HALF_UP);
					}

					int bfljs = 0;

					int htsjs = 0;

					int wtjejs = 0;

					if (null != reportInfo3) {
						String bz2 = reportInfo3.getString("bz2");
						String bz3 = reportInfo3.getString("bz3");
						String bz4 = reportInfo3.getString("bz4");
						String[] split1 = bz2.split("/")[0].split(",");
						String[] split2 = bz2.split("/")[1].split(",");
						String[] split3 = bz3.split("/")[0].split(",");
						String[] split4 = bz3.split("/")[1].split(",");
						String[] split5 = bz4.split("/")[0].split(",");
						String[] split6 = bz4.split("/")[1].split(",");
						if (split1.length == split2.length && split2.length == 3) {
							BigDecimal bigDecimal15 = new BigDecimal(Double.valueOf(split1[0]));
							BigDecimal bigDecimal16 = new BigDecimal(Double.valueOf(split1[1]));
							BigDecimal bigDecimal17 = new BigDecimal(Double.valueOf(split1[2]));

							BigDecimal bigDecimal18 = new BigDecimal(Double.valueOf(split2[0]));
							BigDecimal bigDecimal19 = new BigDecimal(Double.valueOf(split2[1]));
							BigDecimal bigDecimal20 = new BigDecimal(Double.valueOf(split2[2]));

							if (bigDecimal15.compareTo(bigDecimal18) >= 0) {
								if (bigDecimal18.compareTo(new BigDecimal(0)) != 0) {
									htsjs++;
								} else {
									bigDecimal15 = new BigDecimal(0);
								}
							}

							if (bigDecimal16.compareTo(bigDecimal19) >= 0) {
								if (bigDecimal19.compareTo(new BigDecimal(0)) != 0) {
									htsjs++;
								} else {
									bigDecimal16 = new BigDecimal(0);
								}
							}

							if (bigDecimal17.compareTo(bigDecimal20) >= 0) {
								if (bigDecimal20.compareTo(new BigDecimal(0)) != 0) {
									htsjs++;
								} else {
									bigDecimal17 = new BigDecimal(0);
								}
							}

							/*lastyjhtsl = bigDecimal15.add(bigDecimal16).add(bigDecimal17).divide(new BigDecimal(htsjs)).setScale(0, BigDecimal.ROUND_HALF_UP);
							lastyjmbhtsl = bigDecimal18.add(bigDecimal19).add(bigDecimal20).divide(new BigDecimal(htsjs)).setScale(0, BigDecimal.ROUND_HALF_UP);*/

							// 后3个月
							if (bigDecimal3.compareTo(bigDecimal6) >= 0) {
								if (bigDecimal6.compareTo(new BigDecimal(0)) != 0) {
									htsjs++;
								}
							}

							if (bigDecimal4.compareTo(bigDecimal7) >= 0) {
								if (bigDecimal7.compareTo(new BigDecimal(0)) != 0) {
									htsjs++;
								}
							}

							if (bigDecimal5.compareTo(bigDecimal8) >= 0) {
								if (bigDecimal8.compareTo(new BigDecimal(0)) != 0) {
									htsjs++;
								}
							}

						}
						if (split3.length == split4.length && split3.length == 3) {
							BigDecimal bigDecimal21 = new BigDecimal(Double.valueOf(split3[0]));
							BigDecimal bigDecimal22 = new BigDecimal(Double.valueOf(split3[1]));
							BigDecimal bigDecimal23 = new BigDecimal(Double.valueOf(split3[2]));

							BigDecimal bigDecimal24 = new BigDecimal(Double.valueOf(split4[0]));
							BigDecimal bigDecimal25 = new BigDecimal(Double.valueOf(split4[1]));
							BigDecimal bigDecimal26 = new BigDecimal(Double.valueOf(split4[2]));

							if (bigDecimal21.compareTo(bigDecimal24) >= 0) {
								if (bigDecimal24.compareTo(new BigDecimal(0)) != 0) {
									wtjejs++;
								} else {
									bigDecimal21 = new BigDecimal(0);
								}
							}

							if (bigDecimal22.compareTo(bigDecimal25) >= 0) {
								if (bigDecimal25.compareTo(new BigDecimal(0)) != 0) {
									wtjejs++;
								} else {
									bigDecimal22 = new BigDecimal(0);
								}
							}

							if (bigDecimal23.compareTo(bigDecimal26) >= 0) {
								if (bigDecimal26.compareTo(new BigDecimal(0)) != 0) {
									wtjejs++;
								} else {
									bigDecimal23 = new BigDecimal(0);
								}
							}

							/*lastwtje = bigDecimal21.add(bigDecimal22).add(bigDecimal23).divide(new BigDecimal(wtjejs)).setScale(2, BigDecimal.ROUND_HALF_UP);
							lastyjwtje = bigDecimal24.add(bigDecimal25).add(bigDecimal26).divide(new BigDecimal(wtjejs)).setScale(2, BigDecimal.ROUND_HALF_UP);*/

							// 后3个月
							if (bigDecimal9.compareTo(bigDecimal12) >= 0) {
								wtjejs++;
							}

							if (bigDecimal10.compareTo(bigDecimal13) >= 0) {
								wtjejs++;
							}

							if (bigDecimal11.compareTo(bigDecimal14) >= 0) {
								wtjejs++;
							}

						}
						if (split5.length == split6.length && split5.length == 3) {
							BigDecimal bigDecimal33 = new BigDecimal(Double.valueOf(split3[0]));
							BigDecimal bigDecimal34 = new BigDecimal(Double.valueOf(split3[1]));
							BigDecimal bigDecimal35 = new BigDecimal(Double.valueOf(split3[2]));

							BigDecimal bigDecimal36 = new BigDecimal(Double.valueOf(split4[0]));
							BigDecimal bigDecimal37 = new BigDecimal(Double.valueOf(split4[1]));
							BigDecimal bigDecimal38 = new BigDecimal(Double.valueOf(split4[2]));

							if (bigDecimal33.compareTo(bigDecimal36) >= 0) {
								if (bigDecimal36.compareTo(new BigDecimal(0)) != 0) {
									bfljs++;
								} else {
									bigDecimal33 = new BigDecimal(0);
								}

							}

							if (bigDecimal34.compareTo(bigDecimal37) >= 0) {
								if (bigDecimal37.compareTo(new BigDecimal(0)) != 0) {
									bfljs++;
								} else {
									bigDecimal34 = new BigDecimal(0);
								}
							}

							if (bigDecimal35.compareTo(bigDecimal38) >= 0) {
								if (bigDecimal38.compareTo(new BigDecimal(0)) != 0) {
									bfljs++;
								} else {
									bigDecimal35 = new BigDecimal(0);
								}
							}

							// 后3个月
							if (bigDecima27.compareTo(bigDecimal30) >= 0) {
								bfljs++;
							}

							if (bigDecima28.compareTo(bigDecimal31) >= 0) {
								bfljs++;
							}

							if (bigDecima29.compareTo(bigDecimal32) >= 0) {
								bfljs++;
							}

						}
					}

					// 一级指标是空值
					if (bigDecimal2.equals(new BigDecimal(0))) {
						reportInfo.put(
								"qkje",
								bigDecimal2.add(new BigDecimal(Double.valueOf(null != reportInfo3 ? reportInfo3.get("qkje").toString() : "0"))).subtract(bigDecimal1)
										.setScale(2, BigDecimal.ROUND_HALF_UP));
						if (null != yjhtsl && null != yjwtje) {
							if (yjhtsl.compareTo(yjmbhtsl) >= 0 || yjwtje.compareTo(yjmbwtje) >= 0 || wtjejs >= 3 || htsjs >= 3) {

								// 1级空值、2级达标
								// 商务助理
								if (Const.ROLE_SALES_ELITEZL.equals(user.getString("roleId"))) {
									reportInfo.put("tips", "岗位晋级");
									reportInfo.put("isSure", "0");
									reportInfo.put("targetRoleId", Const.ROLE_SALES_ELITE);
								}
								// 角色信息- 商务顾问J1
								if (Const.ROLE_SALES_ELITE.equals(user.getString("roleId"))) {
									reportInfo.put("tips", "级别自动升级");
									reportInfo.put("isSure", "1");
									reportInfo.put("targetRoleId", Const.ROLE_SALES_ELITEJ2);
								}
								// 角色信息- 商务顾问J2
								if (Const.ROLE_SALES_ELITEJ2.equals(user.getString("roleId"))) {
									reportInfo.put("tips", "级别自动升级");
									reportInfo.put("isSure", "1");
									reportInfo.put("targetRoleId", Const.ROLE_SALES_ELITEJ3);
								}
								// 角色信息- 商务顾问J3
								if (Const.ROLE_SALES_ELITEJ3.equals(user.getString("roleId"))) {
									reportInfo.put("tips", "岗位晋级");
									reportInfo.put("isSure", "0");
									reportInfo.put("targetRoleId", Const.ROLE_SALES_ELITES1);
								}
								// 角色信息- 商务顾问S1
								if (Const.ROLE_SALES_ELITES1.equals(user.getString("roleId"))) {
									reportInfo.put("tips", "级别自动升级");
									reportInfo.put("isSure", "1");
									reportInfo.put("targetRoleId", Const.ROLE_SALES_ELITES2);
								}
								// 角色信息- 商务顾问S2
								if (Const.ROLE_SALES_ELITES2.equals(user.getString("roleId"))) {
									reportInfo.put("tips", "级别自动升级");
									reportInfo.put("isSure", "1");
									reportInfo.put("targetRoleId", Const.ROLE_SALES_ELITES3);
								}
								// 角色信息- 商务顾问S3
								if (Const.ROLE_SALES_ELITES3.equals(user.getString("roleId"))) {
									reportInfo.put("tips", "保持不变");
									reportInfo.put("isSure", "1");
									reportInfo.put("targetRoleId", Const.ROLE_SALES_ELITEZL);
								}

							} else {
								if (yjhtsl.compareTo(zbA) >= 0 || yjwtje.compareTo(zbB) >= 0) {
									// 1级空值、2级未达标但过了辞退标准
									if (Const.ROLE_SALES_ELITEZL.equals(user.getString("roleId"))) {
										reportInfo.put("tips", "保持不变");
										reportInfo.put("isSure", "1");
										reportInfo.put("targetRoleId", Const.ROLE_SALES_ELITEZL);
									}
									// 角色信息- 商务顾问J1
									if (Const.ROLE_SALES_ELITE.equals(user.getString("roleId"))) {
										reportInfo.put("tips", "岗位降级");
										reportInfo.put("isSure", "0");
										reportInfo.put("targetRoleId", Const.ROLE_SALES_ELITEZL);
									}
									// 角色信息- 商务顾问J2
									if (Const.ROLE_SALES_ELITEJ2.equals(user.getString("roleId"))) {
										reportInfo.put("flag1", new BigDecimal(1).add(new BigDecimal(Double.valueOf(null != reportInfo3 ? reportInfo3.get("flag1").toString() : "0"))));
										reportInfo.put("tips", "级别自动降级");
										reportInfo.put("isSure", "1");
										if ("2".equals(reportInfo.get("flag1").toString()) || "3".equals(reportInfo.get("flag1").toString())) {
											reportInfo.put("tips", "岗位降级");
											reportInfo.put("isSure", "0");
											reportInfo.put("targetRoleId", Const.ROLE_SALES_ELITEZL);
										} else {
											reportInfo.put("targetRoleId", Const.ROLE_SALES_ELITE);
										}
									}
									// 角色信息- 商务顾问J3
									if (Const.ROLE_SALES_ELITEJ3.equals(user.getString("roleId"))) {
										reportInfo.put("flag1", new BigDecimal(1).add(new BigDecimal(Double.valueOf(null != reportInfo3 ? reportInfo3.get("flag1").toString() : "0"))));
										reportInfo.put("tips", "级别自动降级");
										reportInfo.put("isSure", "1");
										if ("2".equals(reportInfo.get("flag1").toString()) || "3".equals(reportInfo.get("flag1").toString())) {
											reportInfo.put("tips", "岗位降级");
											reportInfo.put("isSure", "0");
											reportInfo.put("targetRoleId", Const.ROLE_SALES_ELITEZL);
										} else {
											reportInfo.put("targetRoleId", Const.ROLE_SALES_ELITEJ2);
										}
									}
									// 角色信息- 商务顾问S1
									if (Const.ROLE_SALES_ELITES1.equals(user.getString("roleId"))) {
										reportInfo.put("flag1", new BigDecimal(1).add(new BigDecimal(Double.valueOf(null != reportInfo3 ? reportInfo3.get("flag1").toString() : "0"))));
										reportInfo.put("tips", "级别自动降级");
										reportInfo.put("isSure", "1");
										if ("3".equals(reportInfo.get("flag1").toString())) {
											reportInfo.put("tips", "岗位降级");
											reportInfo.put("isSure", "0");
											reportInfo.put("targetRoleId", Const.ROLE_SALES_ELITEZL);
										} else {
											reportInfo.put("targetRoleId", Const.ROLE_SALES_ELITEJ3);
										}
									}
									// 角色信息- 商务顾问S2
									if (Const.ROLE_SALES_ELITES2.equals(user.getString("roleId"))) {
										reportInfo.put("flag1", new BigDecimal(1).add(new BigDecimal(Double.valueOf(null != reportInfo3 ? reportInfo3.get("flag1").toString() : "0"))));
										reportInfo.put("tips", "级别自动降级");
										reportInfo.put("isSure", "1");
										if ("3".equals(reportInfo.get("flag1").toString())) {
											reportInfo.put("tips", "岗位降级");
											reportInfo.put("isSure", "0");
											reportInfo.put("targetRoleId", Const.ROLE_SALES_ELITEZL);
										} else {
											reportInfo.put("targetRoleId", Const.ROLE_SALES_ELITES1);
										}
									}
									// 角色信息- 商务顾问S3
									if (Const.ROLE_SALES_ELITES3.equals(user.getString("roleId"))) {
										reportInfo.put("flag1", new BigDecimal(1).add(new BigDecimal(Double.valueOf(null != reportInfo3 ? reportInfo3.get("flag1").toString() : "0"))));
										reportInfo.put("tips", "级别自动降级");
										reportInfo.put("isSure", "1");
										if ("3".equals(reportInfo.get("flag1").toString())) {
											reportInfo.put("tips", "岗位降级");
											reportInfo.put("isSure", "0");
											reportInfo.put("targetRoleId", Const.ROLE_SALES_ELITEZL);
										} else {
											reportInfo.put("targetRoleId", Const.ROLE_SALES_ELITES2);
										}
									}
								} else {
									// 3级达标、1级空值、2级未达标没有过辞退标准
									reportInfo.put("tips", "辞退");
									reportInfo.put("isSure", "0");
								}

							}
						} else {
							// 1级空值、2级空值
							reportInfo.put("tips", "保持不变");
							reportInfo.put("isSure", "1");
							reportInfo.put("targetRoleId", user.getString("roleId"));
						}
					} else {
						// 1及指标达标
						if (bigDecimal1.compareTo(bigDecimal2.add(new BigDecimal(Double.valueOf(null != reportInfo3 ? reportInfo3.get("qkje").toString() : "0")))) >= 0) {
							// 3级达标、1级达标 直接晋级或者提高级别
							if (Const.ROLE_SALES_ELITEZL.equals(user.getString("roleId"))) {
								reportInfo.put("tips", "岗位晋级");
								reportInfo.put("isSure", "0"); // 不是自动处理的
								reportInfo.put("targetRoleId", Const.ROLE_SALES_ELITE);
							}
							// 角色信息- 商务顾问J1
							if (Const.ROLE_SALES_ELITE.equals(user.getString("roleId"))) {
								reportInfo.put("tips", "级别自动升级");
								reportInfo.put("isSure", "1");
								reportInfo.put("targetRoleId", Const.ROLE_SALES_ELITEJ2);
							}
							// 角色信息- 商务顾问J2
							if (Const.ROLE_SALES_ELITEJ2.equals(user.getString("roleId"))) {
								reportInfo.put("tips", "级别自动升级");
								reportInfo.put("isSure", "1");
								reportInfo.put("targetRoleId", Const.ROLE_SALES_ELITEJ3);
							}
							// 角色信息- 商务顾问J3
							if (Const.ROLE_SALES_ELITEJ3.equals(user.getString("roleId"))) {
								reportInfo.put("tips", "岗位晋级");
								reportInfo.put("isSure", "0");
								reportInfo.put("targetRoleId", Const.ROLE_SALES_ELITES1);
							}
							// 角色信息- 商务顾问S1
							if (Const.ROLE_SALES_ELITES1.equals(user.getString("roleId"))) {
								reportInfo.put("tips", "级别自动升级");
								reportInfo.put("isSure", "1");
								reportInfo.put("targetRoleId", Const.ROLE_SALES_ELITES2);
							}
							// 角色信息- 商务顾问S2
							if (Const.ROLE_SALES_ELITES2.equals(user.getString("roleId"))) {
								reportInfo.put("tips", "级别自动升级");
								reportInfo.put("isSure", "1");
								reportInfo.put("targetRoleId", Const.ROLE_SALES_ELITES3);
							}
							// 角色信息- 商务顾问S3
							if (Const.ROLE_SALES_ELITES3.equals(user.getString("roleId"))) {
								reportInfo.put("tips", "保持不变");
								reportInfo.put("isSure", "1");
								reportInfo.put("targetRoleId", Const.ROLE_SALES_ELITEZL);
							}

						} else {
							// 1级不达标
							reportInfo.put(
									"qkje",
									bigDecimal2.add(new BigDecimal(Double.valueOf(null != reportInfo3 ? reportInfo3.get("qkje").toString() : "0"))).subtract(bigDecimal1)
											.setScale(2, BigDecimal.ROUND_HALF_UP));
							if (null != yjhtsl && null != yjwtje) {
								if (yjhtsl.compareTo(yjmbhtsl) >= 0 || yjwtje.compareTo(yjmbwtje) >= 0 || wtjejs >= 3 || htsjs >= 3) {
									// 1级不达标 2级达标 只能做级别挑战、或者保持不变
									if (Const.ROLE_SALES_ELITEZL.equals(user.getString("roleId"))) {
										reportInfo.put("tips", "保持不变");
										reportInfo.put("isSure", "1");
										reportInfo.put("targetRoleId", Const.ROLE_SALES_ELITEZL);
									}
									// 角色信息- 商务顾问J1
									if (Const.ROLE_SALES_ELITE.equals(user.getString("roleId"))) {
										reportInfo.put("tips", "级别自动升级");
										reportInfo.put("isSure", "1");
										reportInfo.put("targetRoleId", Const.ROLE_SALES_ELITEJ2);
									}
									// 角色信息- 商务顾问J2
									if (Const.ROLE_SALES_ELITEJ2.equals(user.getString("roleId"))) {
										reportInfo.put("tips", "级别自动升级");
										reportInfo.put("isSure", "1");
										reportInfo.put("targetRoleId", Const.ROLE_SALES_ELITEJ3);
									}
									// 角色信息- 商务顾问J3
									if (Const.ROLE_SALES_ELITEJ3.equals(user.getString("roleId"))) {
										reportInfo.put("tips", "保持不变");
										reportInfo.put("isSure", "1");
										reportInfo.put("targetRoleId", Const.ROLE_SALES_ELITEJ3);
									}
									// 角色信息- 商务顾问S1
									if (Const.ROLE_SALES_ELITES1.equals(user.getString("roleId"))) {
										reportInfo.put("tips", "级别自动升级");
										reportInfo.put("isSure", "1");
										reportInfo.put("targetRoleId", Const.ROLE_SALES_ELITES2);
									}
									// 角色信息- 商务顾问S2
									if (Const.ROLE_SALES_ELITES2.equals(user.getString("roleId"))) {
										reportInfo.put("tips", "级别自动升级");
										reportInfo.put("isSure", "1");
										reportInfo.put("targetRoleId", Const.ROLE_SALES_ELITES3);
									}
									// 角色信息- 商务顾问S3
									if (Const.ROLE_SALES_ELITES3.equals(user.getString("roleId"))) {
										reportInfo.put("tips", "保持不变");
										reportInfo.put("isSure", "1");
										reportInfo.put("targetRoleId", Const.ROLE_SALES_ELITEZL);
									}
								} else {
									if (yjhtsl.compareTo(zbA) >= 0 || yjwtje.compareTo(zbB) >= 0 || (null != bfl && bfl.compareTo(bfzb) >= 0)) {
										// 1级不达标 2级不达标但过了辞退标准
										if (Const.ROLE_SALES_ELITEZL.equals(user.getString("roleId"))) {
											reportInfo.put("tips", "保持不变");
											reportInfo.put("isSure", "1");
											reportInfo.put("targetRoleId", Const.ROLE_SALES_ELITEZL);
										}
										// 角色信息- 商务顾问J1
										if (Const.ROLE_SALES_ELITE.equals(user.getString("roleId"))) {

											reportInfo.put("tips", "岗位降级");
											reportInfo.put("isSure", "0");
											reportInfo.put("targetRoleId", Const.ROLE_SALES_ELITEZL);
										}
										// 角色信息- 商务顾问J2
										if (Const.ROLE_SALES_ELITEJ2.equals(user.getString("roleId"))) {

											reportInfo.put("flag1", new BigDecimal(1).add(new BigDecimal(Double.valueOf(null != reportInfo3 ? reportInfo3.get("flag1").toString() : "0"))));
											reportInfo.put("tips", "级别自动降级");
											reportInfo.put("isSure", "1");
											if ("2".equals(reportInfo.get("flag1").toString()) || "3".equals(reportInfo.get("flag1").toString())) {
												reportInfo.put("tips", "岗位降级");
												reportInfo.put("isSure", "0");
												reportInfo.put("targetRoleId", Const.ROLE_SALES_ELITEZL);
											} else {
												reportInfo.put("targetRoleId", Const.ROLE_SALES_ELITE);
											}
										}
										// 角色信息- 商务顾问J3
										if (Const.ROLE_SALES_ELITEJ3.equals(user.getString("roleId"))) {
											reportInfo.put("flag1", new BigDecimal(1).add(new BigDecimal(Double.valueOf(null != reportInfo3 ? reportInfo3.get("flag1").toString() : "0"))));
											reportInfo.put("tips", "级别自动降级");
											reportInfo.put("isSure", "1");
											if ("2".equals(reportInfo.get("flag1").toString()) || "3".equals(reportInfo.get("flag1").toString())) {
												reportInfo.put("tips", "岗位降级");
												reportInfo.put("isSure", "0");
												reportInfo.put("targetRoleId", Const.ROLE_SALES_ELITEZL);
											} else {
												reportInfo.put("targetRoleId", Const.ROLE_SALES_ELITEJ2);
											}
										}
										// 角色信息- 商务顾问S1
										if (Const.ROLE_SALES_ELITES1.equals(user.getString("roleId"))) {
											reportInfo.put("flag1", new BigDecimal(1).add(new BigDecimal(Double.valueOf(null != reportInfo3 ? reportInfo3.get("flag1").toString() : "0"))));
											reportInfo.put("tips", "级别自动降级");
											reportInfo.put("isSure", "1");
											if ("3".equals(reportInfo.get("flag1").toString())) {
												reportInfo.put("tips", "岗位降级");
												reportInfo.put("isSure", "0");
												reportInfo.put("targetRoleId", Const.ROLE_SALES_ELITEZL);
											} else {
												reportInfo.put("targetRoleId", Const.ROLE_SALES_ELITEJ3);
											}
										}
										// 角色信息- 商务顾问S2
										if (Const.ROLE_SALES_ELITES2.equals(user.getString("roleId"))) {
											reportInfo.put("flag1", new BigDecimal(1).add(new BigDecimal(Double.valueOf(null != reportInfo3 ? reportInfo3.get("flag1").toString() : "0"))));
											reportInfo.put("tips", "级别自动降级");
											reportInfo.put("isSure", "1");
											if ("3".equals(reportInfo.get("flag1").toString())) {
												reportInfo.put("tips", "岗位降级");
												reportInfo.put("isSure", "0");
												reportInfo.put("targetRoleId", Const.ROLE_SALES_ELITEZL);
											} else {
												reportInfo.put("targetRoleId", Const.ROLE_SALES_ELITES1);
											}
										}
										// 角色信息- 商务顾问S3
										if (Const.ROLE_SALES_ELITES3.equals(user.getString("roleId"))) {
											reportInfo.put("flag1", new BigDecimal(1).add(new BigDecimal(Double.valueOf(null != reportInfo3 ? reportInfo3.get("flag1").toString() : "0"))));
											reportInfo.put("tips", "级别自动降级");
											reportInfo.put("isSure", "1");
											if ("3".equals(reportInfo.get("flag1").toString())) {
												reportInfo.put("tips", "岗位降级");
												reportInfo.put("isSure", "0");
												reportInfo.put("targetRoleId", Const.ROLE_SALES_ELITEZL);
											} else {
												reportInfo.put("targetRoleId", Const.ROLE_SALES_ELITES2);
											}
										}
									} else {
										// 1级不达标 2级不达标但没过辞退标准
										reportInfo.put("tips", "辞退");
										reportInfo.put("isSure", "0");
									}

								}
							} else {
								// 1级不达标 2级空值 按未达标处理
								if (Const.ROLE_SALES_ELITEZL.equals(user.getString("roleId"))) {
									reportInfo.put("tips", "保持不变");
									reportInfo.put("isSure", "1");
									reportInfo.put("targetRoleId", Const.ROLE_SALES_ELITEZL);
								}
								// 角色信息- 商务顾问J1
								if (Const.ROLE_SALES_ELITE.equals(user.getString("roleId"))) {

									reportInfo.put("tips", "岗位降级");
									reportInfo.put("isSure", "0");
									reportInfo.put("targetRoleId", Const.ROLE_SALES_ELITEZL);
								}
								// 角色信息- 商务顾问J2
								if (Const.ROLE_SALES_ELITEJ2.equals(user.getString("roleId"))) {

									reportInfo.put("flag1", new BigDecimal(1).add(new BigDecimal(Double.valueOf(null != reportInfo3 ? reportInfo3.get("flag1").toString() : "0"))));
									reportInfo.put("tips", "级别自动降级");
									reportInfo.put("isSure", "1");
									if ("2".equals(reportInfo.get("flag1").toString()) || "3".equals(reportInfo.get("flag1").toString())) {
										reportInfo.put("tips", "岗位降级");
										reportInfo.put("isSure", "0");
										reportInfo.put("targetRoleId", Const.ROLE_SALES_ELITEZL);
									} else {
										reportInfo.put("targetRoleId", Const.ROLE_SALES_ELITE);
									}
								}
								// 角色信息- 商务顾问J3
								if (Const.ROLE_SALES_ELITEJ3.equals(user.getString("roleId"))) {
									reportInfo.put("flag1", new BigDecimal(1).add(new BigDecimal(Double.valueOf(null != reportInfo3 ? reportInfo3.get("flag1").toString() : "0"))));
									reportInfo.put("tips", "级别自动降级");
									reportInfo.put("isSure", "1");
									if ("2".equals(reportInfo.get("flag1").toString()) || "3".equals(reportInfo.get("flag1").toString())) {
										reportInfo.put("tips", "岗位降级");
										reportInfo.put("isSure", "0");
										reportInfo.put("targetRoleId", Const.ROLE_SALES_ELITEZL);
									} else {
										reportInfo.put("targetRoleId", Const.ROLE_SALES_ELITEJ2);
									}
								}
								// 角色信息- 商务顾问S1
								if (Const.ROLE_SALES_ELITES1.equals(user.getString("roleId"))) {
									reportInfo.put("flag1", new BigDecimal(1).add(new BigDecimal(Double.valueOf(null != reportInfo3 ? reportInfo3.get("flag1").toString() : "0"))));
									reportInfo.put("tips", "级别自动降级");
									reportInfo.put("isSure", "1");
									if ("3".equals(reportInfo.get("flag1").toString())) {
										reportInfo.put("tips", "岗位降级");
										reportInfo.put("isSure", "0");
										reportInfo.put("targetRoleId", Const.ROLE_SALES_ELITEZL);
									} else {
										reportInfo.put("targetRoleId", Const.ROLE_SALES_ELITEJ3);
									}
								}
								// 角色信息- 商务顾问S2
								if (Const.ROLE_SALES_ELITES2.equals(user.getString("roleId"))) {
									reportInfo.put("flag1", new BigDecimal(1).add(new BigDecimal(Double.valueOf(null != reportInfo3 ? reportInfo3.get("flag1").toString() : "0"))));
									reportInfo.put("tips", "级别自动降级");
									reportInfo.put("isSure", "1");
									if ("3".equals(reportInfo.get("flag1").toString())) {
										reportInfo.put("tips", "岗位降级");
										reportInfo.put("isSure", "0");
										reportInfo.put("targetRoleId", Const.ROLE_SALES_ELITEZL);
									} else {
										reportInfo.put("targetRoleId", Const.ROLE_SALES_ELITES1);
									}
								}
								// 角色信息- 商务顾问S3
								if (Const.ROLE_SALES_ELITES3.equals(user.getString("roleId"))) {
									reportInfo.put("flag1", new BigDecimal(1).add(new BigDecimal(Double.valueOf(null != reportInfo3 ? reportInfo3.get("flag1").toString() : "0"))));
									reportInfo.put("tips", "级别自动降级");
									reportInfo.put("isSure", "1");
									if ("3".equals(reportInfo.get("flag1").toString())) {
										reportInfo.put("tips", "岗位降级");
										reportInfo.put("isSure", "0");
										reportInfo.put("targetRoleId", Const.ROLE_SALES_ELITEZL);
									} else {
										reportInfo.put("targetRoleId", Const.ROLE_SALES_ELITES2);
									}
								}
							}
						}

					}

					// 保存员工季报
					yjService.insertUserReportInfo(reportInfo);
					// 挑战自动员工的岗位

					if (null != reportInfo.get("id")) {
						ids = ids + reportInfo.get("id") + ",";
					}
				}
			}

			List<PageData> userRole = yjService.findTargetRole();

			for (PageData userRoles : userRole) {
				yjService.update(userRoles);
				PageData pd3 = new PageData();
				pd3.put("userId", userRoles.getString("userId"));
				pd3.put("type", 12);
				pd3.put("content", "您的级别已调整到:" + userRoles.getString("targetName"));
				pd3.put("flag", 0);
				customerService.saveNoticInfo(pd3);
				// 保存到swgwBg_info
				PageData pd4 = new PageData();
				pd4.put("userId", userRoles.getString("userId"));
				pd4.put("roleId", userRoles.getString("orgRoleId"));
				pd4.put("time", currentMonth);
				customerService.saveSwgwBgInfo(pd4);
			}

			if (TextUtil.isNotNull(ids)) {
				// 保存到通知表
				PageData pd3 = new PageData();
				pd3.put("type", 7);
				pd3.put("relateId", ids.substring(0, ids.length() - 1));
				pd3.put("content", defaultDay + "的商务顾问季报");
				pd3.put("flag", 0);
				customerService.saveNoticInfo(pd3);
			}
		}

	}

	// 根据角色获取到款金额
	@RequestMapping(value = "/getMoneyByUserId")
	@ResponseBody
	public Object getMoneyByUserId() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData result = null;
		String msg = null;
		String error = null;
		try {
			PageData pd = this.getPageData();
			if (TextUtil.isNotNull(pd.getString("userId"))) {
				pd.put("USER_ID", pd.getString("userId"));
				String flag = this.getGroup(pd);
				if (!"2".equals(flag) && !"3".equals(flag)) {
					error = "01";
					msg = "该用户是错误用户";
				} else {
					// 获取当前的年份
					SimpleDateFormat format = new SimpleDateFormat("yyyy");
					Date date = new Date();
					pd.put("date", format.format(date));
					pd.put("flag", flag);
					result = yjService.getMoneyByUserId(pd);
					error = "00";
					msg = "获取成功";
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
	 * 展示员工薪资或提成
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/showYgxzOrTc")
	@ResponseBody
	public Object showYgxzOrTc() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<PageData> result = null;
		String msg = null;
		String error = null;
		try {
			PageData pd = this.getPageData();
			if (TextUtil.isNotNull(pd.getString("flag"))) {
				if ("4".equals(pd.getString("flag"))) {
					result = yjService.showYgxzOrTc(pd);
					for (int i = 0; result != null && i < result.size(); i++) {
						PageData pd1 = new PageData();
						pd1.put("USER_ID", result.get(i).get("userId"));
						pd1.put("userId", result.get(i).get("userId"));
						String hkMoney = result.get(i).get("je2").toString();
						if ("2".equals(this.getGroup(pd1))) {
							pd1.put("fdStandard", 5);
						}
						if ("3".equals(this.getGroup(pd1))) {
							pd1.put("fdStandard", 6);
						}

						List<PageData> findJe3 = yjService.findJe3(pd1);
						for (int j = 0; findJe3 != null && j < findJe3.size(); j++) {
							if (TextUtil.isNotNull(findJe3.get(j).getString("fdzb"))) {
								String[] split = findJe3.get(j).getString("fdzb").split(",");
								if (split.length == 1) {
									if (Double.valueOf(hkMoney) >= Double.valueOf(split[0])) {
										result.get(i).put("je1", "(" + findJe3.get(j).get("fdzb").toString().replace(",", "~") + ")");
										result.get(i).put("tcbl", findJe3.get(j).get("tc"));
										result.get(i).put(
												"zje",
												new BigDecimal(Double.valueOf(findJe3.get(j).get("tc").toString())).multiply(new BigDecimal(Double.valueOf(hkMoney))).divide(new BigDecimal(100))
														.setScale(2, BigDecimal.ROUND_HALF_UP));
										break;
									}
								} else {
									if (Double.valueOf(hkMoney) >= Double.valueOf(split[0]) && Double.valueOf(hkMoney) < Double.valueOf(split[1])) {
										result.get(i).put("je1", "(" + findJe3.get(j).get("fdzb").toString().replace(",", "~") + ")");
										result.get(i).put("tcbl", findJe3.get(j).get("tc"));
										result.get(i).put(
												"zje",
												new BigDecimal(Double.valueOf(findJe3.get(j).get("tc").toString())).multiply(new BigDecimal(Double.valueOf(hkMoney))).divide(new BigDecimal(100))
														.setScale(2, BigDecimal.ROUND_HALF_UP));
										break;
									}
								}

							}
						}

						/*if ("6".equals(pd1.get("fdStandard").toString())) {
							List<PageData> findJe3 = yjService.findJe3(pd1);
							for (int j = 0; findJe3 != null && j < findJe3.size(); j++) {
								if (TextUtil.isNotNull(findJe3.get(j).getString("fdzb"))) {
									String[] split = findJe3.get(j).getString("fdzb").split(",");
									if (split.length == 1) {
										if (Double.valueOf(hkMoney) >= Double.valueOf(split[0])) {
											result.get(i).put("je1", findJe3.get(j).get("fdzb"));
											result.get(i).put("tcbl", findJe3.get(j).get("tc"));
											result.get(i).put(
													"zje",
													new BigDecimal(Double.valueOf(findJe3.get(j).get("tc").toString())).multiply(new BigDecimal(Double.valueOf(hkMoney))).divide(new BigDecimal(100))
															.setScale(2, BigDecimal.ROUND_HALF_UP));
											break;
										}
									} else {
										if (Double.valueOf(hkMoney) >= Double.valueOf(split[0]) && Double.valueOf(hkMoney) < Double.valueOf(split[1])) {
											result.get(i).put("je1", findJe3.get(j).get("fdzb"));
											result.get(i).put("tcbl", findJe3.get(j).get("tc"));
											result.get(i).put(
													"zje",
													new BigDecimal(Double.valueOf(findJe3.get(j).get("tc").toString())).multiply(new BigDecimal(Double.valueOf(hkMoney))).divide(new BigDecimal(100))
															.setScale(2, BigDecimal.ROUND_HALF_UP));
											break;
										}
									}

								}
							}
							List<String> list = new ArrayList<String>();
							for (int j = 0; findJe3 != null && j < findJe3.size(); j++) {
								if (TextUtil.isNotNull(findJe3.get(j).getString("fdzb"))) {
									String[] split = findJe3.get(j).getString("fdzb").split(",");
									if (split.length > 0) {
										if (Double.valueOf(findJe3.get(j).get("tc").toString()) > 0) {
											list.add(findJe3.get(j).get("fdzb").toString().split(",")[0] + "," + findJe3.get(j).get("tc").toString());
										}
									}
								}
							}

							list = sortList(list);
							if (list.size() > 0) {
								result.get(i).put("je1", list.get(0).split(",")[0]);
								result.get(i).put("tcbl", list.get(0).split(",")[1]);
								result.get(i).put(
										"zje",
										new BigDecimal(Double.valueOf(list.get(0).split(",")[1])).multiply(new BigDecimal(Double.valueOf(hkMoney))).divide(new BigDecimal(100))
												.setScale(2, BigDecimal.ROUND_HALF_UP));
							}

						}*/
					}
				} else {
					result = yjService.showYgxzOrTc(pd);
					TimeUtil tu = new TimeUtil();
					String defaultDay = tu.getNowTime("yyyy-MM-dd"); // 当天的日期
					String currentMonth = tu.getNowTime("yyyy-MM");
					String currentMonth1 = tu.getNowTime("MM");
					String currentYear = tu.getNowTime("yyyy");
					String previousMonthEnd = tu.getDefaultDay();

					if (result.size() <= 0 && !defaultDay.equals(previousMonthEnd) && currentMonth.equals(pd.getString("time"))) {
						if ("0".equals(pd.getString("flag"))) {

							// ================(电销团队的月工资)=================
							pd.put("roleGroup", Const.ROLE_SALES_GROUP);
							List<PageData> list = yjService.findUserByGroup(pd);
							// 各个用户
							int i = list.size();
							int i1 = 0;
							for (PageData user : list) {
								PageData report = new PageData();
								// 新增报告的姓名
								report.put("userId", user.get("userId"));
								// 新增报告的月份
								report.put("time", currentMonth);
								user.put("flag", 1);
								PageData zxz = new PageData();
								PageData zxz1 = yjService.findDxByUserId(user); // 获取用户的当前的底薪
								if (null != zxz1) {
									zxz.put("je1", zxz1.get("je1"));
								}
								// 获取各用户的电话量
								pd.put("startTime", currentMonth + "-01");
								pd.put("endTime", defaultDay);
								pd.put("dfSaleId", user.get("userId"));
								// pd.put("saleId", user.get("userId"));
								PageData yxkhs1 = mainService.findyyx(pd);

								// 判断电话量是否达标
								user.put("monthOrjd", Integer.valueOf(currentMonth1));
								user.put("year", currentYear);
								user.put("type", 0);
								// 获取电话量指标
								user.put("type", 1);
								// 获取意向客户数指标
								PageData yxkhs2 = yjService.findDhlByUserId(user);

								PageData pd1 = new PageData();
								// 获取电销绩效薪资
								user.put("flag", 0);
								user.put("type", "0");
								PageData jxxz = yjService.findDxByUserId(user);
								if (null != pd1 && null != jxxz && null != pd1.get("tc") && null != jxxz.get("je1")) {
									zxz.put("je2",
											new BigDecimal(Double.valueOf(pd1.get("tc").toString())).multiply(new BigDecimal(Double.valueOf(jxxz.get("je1").toString())).divide(new BigDecimal(100))));
								}
								PageData pd2 = new PageData();
								pd2.put("userId", user.get("userId"));
								pd2.put("fdStandard", 1);

								// 获取各用户的意向客户数
								if (null != yxkhs2 && null != yxkhs1 && null != yxkhs1.get("khs") && null != yxkhs2.get("targetDetail") && TextUtil.isNotNull(yxkhs2.get("targetDetail").toString())
										&& TextUtil.isNotNull(yxkhs1.get("khs").toString())) {
									String value = yxkhs2.get("targetDetail").toString();
									String[] value1 = value.split(",");
									if (value1.length == 2) {

										Integer qz = Integer.valueOf(value1[0]);
										Integer hz = Integer.valueOf(value1[1]);
										if (Integer.valueOf(yxkhs1.get("khs").toString()) <= hz && Integer.valueOf(yxkhs1.get("khs").toString()) >= qz) {
											// 达标
											pd2.put("fdzb", 0);
											pd2 = yjService.findJe2(pd2);
											if (null != pd2 && null != pd2.get("tc")) {
												if (null != jxxz && null != jxxz.get("je1")) {
													zxz.put("je3", new BigDecimal(Double.valueOf(pd2.get("tc").toString())).multiply(new BigDecimal(Double.valueOf(jxxz.get("je1").toString())))
															.divide(new BigDecimal(100)));
												} else {
													zxz.put("je3", 0);
												}
											}
											i1++;
										} else if (Integer.valueOf(yxkhs1.get("khs").toString()) > hz) {
											// 超标
											pd2.put("fdzb", 0);
											pd2 = yjService.findJe2(pd2);
											if (null != pd2 && null != pd2.get("tc")) {
												BigDecimal tc1 = new BigDecimal(Double.valueOf(pd2.get("tc").toString())).multiply(new BigDecimal(Double.valueOf(jxxz.get("je1").toString()))).divide(
														new BigDecimal(100));

												pd2.put("fdzb", 2);
												pd2 = yjService.findJe2(pd2);
												if (null != pd2) {
													String[] tcs = pd2.get("tc").toString().split(",");

													tc1 = tc1.add(new BigDecimal(Double.valueOf(tcs[1])).multiply(new BigDecimal(Integer.valueOf(yxkhs1.get("khs").toString()) - hz)));
													zxz.put("je3", tc1);
												}
											}
											i1++;
										} else {
											// 未达标
											pd2.put("fdzb", 1);
											pd2 = yjService.findJe2(pd2);
											if (null != pd2 && null != pd2.get("tc")) {
												zxz.put("je3",
														new BigDecimal(Double.valueOf(pd2.get("tc").toString())).multiply(new BigDecimal(Double.valueOf(jxxz.get("je1").toString()))).divide(
																new BigDecimal(100)));
											}
										}
									}
								}
								// 判断当前用户是否是电销主管
								if (user.get("roleId").toString().equals(Const.ROLE_PHONE_SALES_DIRECTOR)) {
									user.put("flag", 0);
									user.put("type", 1);
									PageData dxzgxz = yjService.findDxByUserId(user);
									if (null != dxzgxz) {
										zxz.put("je4", dxzgxz.get("je1"));
										zxz.put("mark", "dxzg");
									}
								} else {
									zxz.put("je4", 0);
								}

								zxz.put("userId", user.get("userId"));
								zxz.put("userName", user.get("name"));
								zxz.put("roleName", user.get("roleName"));
								zxz.put("flag", 0);
								zxz.put("createTime", currentMonth);
								zxz.put("zje", toGetM(zxz, "je1").add(toGetM(zxz, "je2")).add(toGetM(zxz, "je3")).add(toGetM(zxz, "je4")).setScale(2, BigDecimal.ROUND_HALF_UP));
								// 插入ygxz_info
								result.add(zxz);

							}

							// 获取电销主管的工资并修改yjService.showYgxzOrTc(pd);
							for (int j = 0; j < result.size(); j++) {
								if (null != result.get(j).get("mark")) {

									PageData user = new PageData();
									user.put("flag", 0);
									user.put("type", 1);
									user.put("userId", result.get(j).get("userId"));
									PageData fdxz = yjService.findDxByUserId(user);
									if (null != fdxz) {
										result.get(j).put("zje", toGetM(result.get(j), "zje").subtract(toGetM(fdxz, "je1")));
										result.get(j).put("je4",
												toGetM(fdxz, "je1").multiply(new BigDecimal(i1)).divide(new BigDecimal(i), 2, BigDecimal.ROUND_HALF_UP).setScale(2, BigDecimal.ROUND_HALF_UP));
										result.get(j).put("zje", toGetM(result.get(j), "zje").add(toGetM(result.get(j), "je4")));
									}
								}
							}

						}
						if ("1".equals(pd.getString("flag"))) {

							// =================(商务顾问的月工资)==============================
							PageData pdswgw = new PageData();
							pdswgw.put("roleGroup", Const.ROLE_SWGWS_GROUP);
							pdswgw.put("userId", pd.getString("userId"));
							List<PageData> list1 = yjService.findUserByGroup(pdswgw);
							// 各个用户
							for (PageData user : list1) {
								if (!Const.ROLE_SALES_ELITEWX.equals(user.getString("roleId")) && !Const.ROLE_SALES_ELITEDX.equals(user.getString("roleId"))) {
									// 获取底薪+浮动工资+拜访奖金+签单奖金
									user.put("flag", 1);
									PageData zxz = yjService.findDxByUserId(user); // 获取用户的当前的底薪

									if (null == zxz) {
										zxz = new PageData();
									}
									// 浮动工资
									user.put("flag", 0);
									user.put("type", 2);
									PageData fdxz = yjService.findDxByUserId(user);
									if (null != fdxz) {
										zxz.put("je2", fdxz.get("je1"));
									}
									zxz.put("userId", user.get("userId"));
									zxz.put("userName", user.get("name"));
									zxz.put("roleName", user.get("roleName"));
									zxz.put("flag", 1);
									zxz.put("createTime", currentMonth);
									// 拜访奖金
									pdswgw.put("saleId", user.get("userId"));
									pdswgw.put("time", currentMonth);
									PageData bfl1 = yjService.findCountVisit(pdswgw);
									if (null != bfl1 && null != bfl1.get("bfl") && TextUtil.isNotNull(bfl1.get("bfl").toString())) {
										PageData pd1 = new PageData();
										pd1.put("time", currentMonth);
										pd1.put("saleId", user.get("userId"));
										pd1.put("type", "11");
										pd1 = yjService.findbfOrQd(pd1);
										if (null != pd1 && null != pd1.get("conditions")) {
											if (Integer.valueOf(bfl1.get("bfl").toString()) >= Integer.valueOf(pd1.get("conditions").toString())) {
												// 达标
												zxz.put("je3",
														new BigDecimal(Integer.valueOf(bfl1.get("bfl").toString()))
														/*.subtract(new BigDecimal(Integer.valueOf(pd1.get("targetDetail").toString())))*/.multiply(new BigDecimal(Integer.valueOf(pd1.get(
																"targetDetail").toString()))));
											} else {
												// 未达标
												zxz.put("je3", new BigDecimal(0));
											}
										}
									}
									// 签单奖金
									user.put("startTime", currentMonth + "-01 00:00:00");
									user.put("endTime", defaultDay);
									user.put("saleId", user.get("userId"));
									user.put("type", "1");
									List<PageData> qdl1 = mainService.showSwgwYjmb(user);

									if (null != qdl1 && null != qdl1.get(0).get("dkje") && TextUtil.isNotNull(qdl1.get(0).get("dkje").toString())) {
										PageData pd1 = new PageData();
										pd1.put("time", currentMonth);
										pd1.put("saleId", user.get("userId"));
										pd1.put("type", "12");
										pd1 = yjService.findbfOrQd(pd1);
										if (null != pd1 && null != pd1.get("conditions")) {
											if (Integer.valueOf(qdl1.get(0).get("dkje").toString()) >= Integer.valueOf(pd1.get("conditions").toString())) {
												// 达标
												zxz.put("je4",
														new BigDecimal(Integer.valueOf(qdl1.get(0).get("dkje").toString()))
														/*.subtract(new BigDecimal(Integer.valueOf(pd1.get("targetDetail").toString())))*/.multiply(new BigDecimal(Integer.valueOf(pd1.get(
																"targetDetail").toString()))));
											} else {
												// 未达标
												zxz.put("je4", new BigDecimal(0));
											}
										}
									}
									zxz.put("zje", toGetM(zxz, "je1").add(toGetM(zxz, "je2")).add(toGetM(zxz, "je3")).add(toGetM(zxz, "je4")).setScale(2, BigDecimal.ROUND_HALF_UP));
									// 插入ygxz_info
									result.add(zxz);
								}
							}

						}
						if ("2".equals(pd.getString("flag"))) {

							// =================(特殊员工的月工资)==============================
							PageData pdswgw = new PageData();
							pdswgw.put("roleGroup", Const.ROLE_SWGWS_GROUP);
							List<PageData> list1 = yjService.findUserByGroup(pdswgw);
							// 各个用户
							for (PageData user : list1) {

								if (Const.ROLE_SALES_ELITEWX.equals(user.getString("roleId"))) {
									// 特殊员工的工资（到款金额、提成比例、总薪资)
									user.put("flag", 2);
									PageData zxz1 = yjService.findDxByUserId(user);
									PageData zxz = new PageData();
									if (null != zxz1) {
										zxz.put("tcbl", zxz1.get("je1"));
									}
									PageData wx = yjService.findwxDkje(user);
									// 获取商务外协这个月的到款金额。。
									if (null != wx && null != wx.get("hkMoney")) {
										BigDecimal object1 = new BigDecimal(Double.valueOf(wx.get("hkMoney").toString())).setScale(2, BigDecimal.ROUND_HALF_UP);
										zxz.put("je1", object1);
									}
									zxz.put("flag", 2);
									zxz.put("userName", user.get("name"));
									zxz.put("roleName", user.get("roleName"));
									zxz.put("userId", user.get("userId"));
									zxz.put("createTime", currentMonth);
									zxz.put("zje", toGetM(zxz, "je1").multiply(toGetM(zxz, "tcbl")).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP));
									result.add(zxz);
								}
							}
						}
						if ("3".equals(pd.getString("flag"))) {
							// 行政人员的月工资
							List<PageData> xzryList = yjService.findUserByGroup1();
							// 各个用户
							for (PageData user : xzryList) {
								user.put("flag", 1);
								PageData zxz = yjService.findDxByUserId(user); // 获取用户的当前的底薪
								if (null == zxz) {
									zxz = new PageData();
								}
								zxz.put("flag", 3);
								zxz.put("userName", user.get("name"));
								zxz.put("roleName", user.get("roleName"));
								zxz.put("userId", user.get("userId"));
								zxz.put("createTime", currentMonth);
								result.add(zxz);
							}

						}
						if ("5".equals(pd.getString("flag"))) {

							// =================(商务顾问的月工资)==============================
							PageData pdswgw = new PageData();
							pdswgw.put("roleGroup", Const.ROLE_SWGWS_GROUP);
							pdswgw.put("userId", pd.getString("userId"));
							List<PageData> list1 = yjService.findUserByGroup(pdswgw);
							// 各个用户
							for (PageData user : list1) {

								if (Const.ROLE_SALES_ELITEDX.equals(user.getString("roleId"))) {
									// 电销商务顾问的工资 获取底薪+拜访奖金+签单奖金
									user.put("flag", 1);
									PageData zxz = yjService.findDxByUserId(user);
									if (null == zxz) {
										zxz = new PageData();
									}
									// 拜访量
									pdswgw.put("saleId", user.get("userId"));
									pdswgw.put("time", currentMonth);
									PageData bfl1 = yjService.findCountVisit(pdswgw);
									if (null != bfl1 && null != bfl1.get("bfl") && TextUtil.isNotNull(bfl1.get("bfl").toString())) {
										PageData pd1 = new PageData();
										pd1.put("fdStandard", 7);
										pd1.put("userId", user.get("userId"));
										List<PageData> pd2 = yjService.findJe3(pd1);
										pd1.put("saleId", user.get("userId"));
										pd1.put("type", "8");
										pd1.put("startTime", new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(defaultDay)));
										pd1.put("endTime", new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(defaultDay)));
										List<PageData> result1 = mainService.findDhlAndYxkhsBySaleId(pd1);

										if (null != result1 && result1.size() == 1) {
											if (Integer.valueOf(bfl1.get("bfl").toString()) >= Integer.valueOf(result1.get(0).get("targetDetail").toString())) {
												// 0达标
												if (null != pd2.get(0) && null != pd2.get(0).get("fdzb") && "0".equals(pd2.get(0).get("fdzb").toString())) {
													zxz.put("je2",
															new BigDecimal(Integer.valueOf(bfl1.get("bfl").toString())).multiply(new BigDecimal(Integer.valueOf(pd2.get(0).get("tc").toString()))));
												}
												if (null != pd2.get(1) && null != pd2.get(1).get("fdzb") && "0".equals(pd2.get(1).get("fdzb").toString())) {
													zxz.put("je2",
															new BigDecimal(Integer.valueOf(bfl1.get("bfl").toString())).multiply(new BigDecimal(Integer.valueOf(pd2.get(1).get("tc").toString()))));
												}
											} else {
												// 未达标
												if (null != pd2.get(0) && null != pd2.get(0).get("fdzb") && "1".equals(pd2.get(0).get("fdzb").toString())) {
													zxz.put("je2", new BigDecimal(Integer.valueOf(pd2.get(0).get("tc").toString())));
												}
												if (null != pd2.get(1) && null != pd2.get(1).get("fdzb") && "1".equals(pd2.get(1).get("fdzb").toString())) {
													zxz.put("je2", new BigDecimal(Integer.valueOf(pd2.get(1).get("tc").toString())));
												}
											}
										}
									}

									// 客户签约奖金
									pdswgw.put("startTime", currentMonth + "-01 00:00:00");
									pdswgw.put("endTime", defaultDay + " 23:59:59");
									pdswgw.put("type", "0");
									// 实际签约客户数
									List<PageData> resultsjqy = mainService.showDxSwgwYjmb1(pdswgw);

									if (null != resultsjqy && resultsjqy.size() > 0 && null != resultsjqy.get(0).get("sl")) {
										PageData pd1 = new PageData();
										pd1.put("fdStandard", 8);
										pd1.put("userId", user.get("userId"));
										List<PageData> pd2 = yjService.findJe3(pd1);
										pd1.put("saleId", user.get("userId"));
										pd1.put("type", "9");
										pd1.put("startTime", new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(defaultDay)));
										pd1.put("endTime", new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(defaultDay)));
										// 指标客户数
										List<PageData> resultzbqy = mainService.findDhlAndYxkhsBySaleId(pd1);

										if (null != resultzbqy && resultzbqy.size() > 0 && resultzbqy.size() == 1) {
											if (Integer.valueOf(resultsjqy.get(0).get("sl").toString()) >= Integer.valueOf(resultzbqy.get(0).get("targetDetail").toString())) {
												// 0达标
												if (null != pd2.get(0) && null != pd2.get(0).get("fdzb") && "0".equals(pd2.get(0).get("fdzb").toString())) {
													zxz.put("je3",
															new BigDecimal(Integer.valueOf(resultsjqy.get(0).get("sl").toString())).multiply(new BigDecimal(Integer.valueOf(pd2.get(0).get("tc")
																	.toString()))));
												}
												if (null != pd2.get(1) && null != pd2.get(1).get("fdzb") && "0".equals(pd2.get(1).get("fdzb").toString())) {
													zxz.put("je3",
															new BigDecimal(Integer.valueOf(resultsjqy.get(0).get("sl").toString())).multiply(new BigDecimal(Integer.valueOf(pd2.get(1).get("tc")
																	.toString()))));
												}
											} else {
												// 未达标
												if (null != pd2.get(0) && null != pd2.get(0).get("fdzb") && "1".equals(pd2.get(0).get("fdzb").toString())) {
													zxz.put("je3", new BigDecimal(Integer.valueOf(pd2.get(0).get("tc").toString())));
												}
												if (null != pd2.get(1) && null != pd2.get(1).get("fdzb") && "1".equals(pd2.get(1).get("fdzb").toString())) {
													zxz.put("je3", new BigDecimal(Integer.valueOf(pd2.get(1).get("tc").toString())));
												}
											}
										}
									}

									// 订单标的奖金
									pdswgw.put("startTime", currentMonth + "-01 00:00:00");
									pdswgw.put("endTime", defaultDay + " 23:59:59");
									pdswgw.put("type", "1");
									// 实际
									List<PageData> resultddje = mainService.showDxSwgwYjmb1(pdswgw);

									if (null != resultddje && resultddje.size() > 0 && null != resultddje.get(0).get("ddje")) {
										PageData pd1 = new PageData();
										pd1.put("fdStandard", 9);
										pd1.put("userId", user.get("userId"));
										List<PageData> pd2 = yjService.findJe3(pd1);

										for (int j = 0; pd2 != null && j < pd2.size(); j++) {
											if (TextUtil.isNotNull(pd2.get(j).getString("fdzb"))) {
												String[] split = pd2.get(j).getString("fdzb").split(",");

												if (split.length == 1) {
													if (Double.valueOf(resultddje.get(0).get("ddje").toString()) >= Double.valueOf(split[0])) {
														zxz.put("je4", new BigDecimal(Double.valueOf(pd2.get(j).get("tc").toString())));
														break;
													}
												} else {
													if (Double.valueOf(resultddje.get(0).get("ddje").toString()) >= Double.valueOf(split[0])
															&& Double.valueOf(resultddje.get(0).get("ddje").toString()) < Double.valueOf(split[1])) {
														zxz.put("je4", new BigDecimal(Double.valueOf(pd2.get(j).get("tc").toString())));
														break;
													}
												}
												/*if (Double.valueOf(resultddje.get(0).get("ddje").toString()) >= Double.valueOf(split[0])
														&& Double.valueOf(resultddje.get(0).get("ddje").toString()) < Double.valueOf(split[1])) {
													zxz.put("je4", new BigDecimal(Double.valueOf(pd2.get(0).get("tc").toString())));
													break;
												}*/
											}
										}
										/*pd1.put("saleId", user.get("userId"));
										pd1.put("type", "10");
										pd1.put("startTime", new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(defaultDay)));
										pd1.put("endTime", new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(defaultDay)));
										// 指标客户数
										List<PageData> resultzbje = mainService.findDhlAndYxkhsBySaleId(pd1);

										if (null != resultzbje && resultzbje.size() == 1 && null != resultzbje.get(0).get("targetDetail")) {
											if (Double.valueOf(resultddje.get(0).get("ddje").toString()) >= Double.valueOf(resultzbje.get(0).get("targetDetail").toString())) {
												// 0达标
												if (null != pd2.get(0) && null != pd2.get(0).get("fdzb") && "0".equals(pd2.get(0).get("fdzb").toString())) {
													zxz.put("je4", new BigDecimal(Double.valueOf(pd2.get(0).get("tc").toString())));
												}
												if (null != pd2.get(1) && null != pd2.get(1).get("fdzb") && "0".equals(pd2.get(1).get("fdzb").toString())) {
													zxz.put("je4", new BigDecimal(Double.valueOf(pd2.get(1).get("tc").toString())));
												}

											} else {
												// 未达标
												if (null != pd2.get(0) && null != pd2.get(0).get("fdzb") && "1".equals(pd2.get(0).get("fdzb").toString())) {
													zxz.put("je4", new BigDecimal(Double.valueOf(pd2.get(0).get("tc").toString())));
												}
												if (null != pd2.get(1) && null != pd2.get(1).get("fdzb") && "1".equals(pd2.get(1).get("fdzb").toString())) {
													zxz.put("je4", new BigDecimal(Double.valueOf(pd2.get(1).get("tc").toString())));
												}
											}
										}*/
									}
									zxz.put("flag", 5);
									zxz.put("userId", user.get("userId"));
									zxz.put("userName", user.get("name"));
									zxz.put("roleName", user.get("roleName"));
									zxz.put("createTime", currentMonth);
									zxz.put("zje", toGetM(zxz, "je1").add(toGetM(zxz, "je2")).add(toGetM(zxz, "je3")).add(toGetM(zxz, "je4")).setScale(2, BigDecimal.ROUND_HALF_UP));
									result.add(zxz);
								}
							}
						}

					}
				}
				error = "00";
				msg = "查看成功";
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

	private List<String> sortList(List<String> list) {
		Comparator<String> comp = new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return Double.valueOf(o1.split(",")[0]).compareTo(Double.valueOf(o2.split(",")[0]));
			}

		};
		Collections.sort(list, comp);

		return list;
	}

	private BigDecimal toGetM(PageData zxz, String string) {
		BigDecimal i = new BigDecimal(0.0);
		return null != zxz.get(string) ? new BigDecimal(Double.valueOf(zxz.get(string).toString())) : i;
	}

	// 获取到款提成的岗位
	@RequestMapping(value = "/getDktcGw")
	@ResponseBody
	public Object getDktcGw() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<PageData> result = null;
		String msg = null;
		String error = null;
		try {
			PageData pd = this.getPageData();
			if (TextUtil.isNotNull(pd.getString("flag"))) {
				pd.put("parentId", Const.ROLE_SWGWS_GROUP);
				result = yjService.getDktcGw(pd);
				error = "00";
				msg = "查看成功";
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

	// 5，6，7的通知详情
	@RequestMapping(value = "/showNoticDetail")
	@ResponseBody
	public Object showNoticDetail() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = null;
		String error = null;
		PageInfo<PageData> page = null;
		try {
			PageData pd = this.getPageData();
			if (TextUtil.isNotNull(pd.getString("id"))) {
				// 根据id获取notic_info
				String userId = "";
				if (TextUtil.isNotNull(pd.getString("userId"))) {
					userId = pd.getString("userId");
					pd.remove("userId");
				}
				List<PageData> findNoticInfo = orderService.findNoticInfo(pd);
				if (findNoticInfo.size() == 1 && null != findNoticInfo.get(0).get("relateId")) {
					pd.put("list", findNoticInfo.get(0).get("relateId").toString().split(","));
					String pageNo = pd.getString("page");
					String pageSize = pd.getString("pageSize");
					/*String id = pd.getString("id");*/
					int pNo = TextUtil.isNull(pageNo) ? 1 : Integer.parseInt(pageNo);
					int pSize = TextUtil.isNull(pageSize) ? 10 : Integer.parseInt(pageSize);
					PageHelper.startPage(pNo, pSize); // 核心分页代码
					pd.put("userId", userId);
					page = new PageInfo<PageData>(yjService.showNoticDetail(pd));
					error = "00";
					msg = "查看成功";
				} else {
					error = "01";
					msg = "查看失败";
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
			map.put("result", page);
		}
		return AppUtil.returnObject(new PageData(), map);
	}

	// 针对辞退
	@RequestMapping(value = "/doUserReportInfo")
	@ResponseBody
	public Object doUserReportInfo() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<PageData> result = null;
		String msg = null;
		String error = null;
		try {
			PageData pd = this.getPageData();
			pd.put("USER_ID", pd.getString("userId"));
			PageData user = userService.findById(pd);
			// 修改成功
			yjService.updateIsSure(pd);
			//
			if ("辞退".equals(pd.getString("tips"))) {
				if ("1".equals(user.get("isQuit").toString())) {
					error = "00";
					msg = "已离职";
				} else {
					if ("2".equals(this.getGroup(pd))) {
						pd.put("flag", "1");
						userService.updateCustomerInfo(pd);
						userService.updateCustomerInfo1(pd);
						userService.updateOrderInfo(pd);
						userService.updateContractInfo(pd);

						pd.put("type", 0);
						userService.updateKhgh1(pd);
						pd.put("type", 1);
						userService.updateKhgh1(pd);
						pd.put("type", 3);
						userService.updateKhgh1(pd);
						pd.put("type", 4);
						userService.updateKhgh1(pd);
						pd.put("type", 5);
						userService.updateKhgh1(pd);
						pd.put("type", 6);
						userService.updateKhgh1(pd);
						pd.put("type", 7);
						userService.updateKhgh1(pd);
					}

					// 电访销售
					if ("1".equals(this.getGroup(pd))) {
						userService.updateKhgh(pd);
					}
					User user1 = new User();
					user1.setUSER_ID(pd.getString("USER_ID"));
					user1.setIsQuit("1");
					userService.editU1(user1);

					error = "00";
					msg = "辞退成功";
				}
			} else if ("岗位降级".equals(pd.getString("tips")) || "岗位晋级".equals(pd.getString("tips"))) {
				yjService.update(pd);
				// 薪资调节
				yjService.updateXz(pd);
				error = "00";
				msg = pd.getString("tips");

				PageData pd3 = new PageData();
				pd3.put("userId", pd.getString("userId"));
				pd3.put("type", 12);
				pd3.put("content", "您的级别已调整到:" + pd.getString("targetName"));
				pd3.put("flag", 0);
				customerService.saveNoticInfo(pd3);

				PageData pd4 = new PageData();
				pd4.put("userId", pd.getString("userId"));
				pd4.put("roleId", user.getString("ROLE_ID"));
				pd4.put("time", pd.getString("month").substring(0, 7));
				customerService.saveSwgwBgInfo(pd4);
			} else {
				error = "00";
				msg = pd.getString("tips");
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

	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Date date = new Date();
		Calendar lastDate = Calendar.getInstance();
		lastDate.setTime(date);
		lastDate.add(Calendar.MONTH, -1);
		String time = sdf.format(lastDate.getTime());
		// 获取可提现金额(所有)
		Integer time1 = Integer.valueOf(time.substring(5));
		if (time1 > 20) {
			lastDate.add(Calendar.MONTH, -1);//
			time = sdf.format(lastDate.getTime());
			System.out.println(time);
		} else {
			System.out.println(time);
		}
	}
}
