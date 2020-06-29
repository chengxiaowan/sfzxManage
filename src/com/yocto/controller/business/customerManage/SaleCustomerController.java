package com.yocto.controller.business.customerManage;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.socket.TextMessage;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yocto.controller.base.BaseController;
import com.yocto.entity.system.User;
import com.yocto.service.business.customerManage.ICustomerService;
import com.yocto.service.business.expressManage.IExpressService;
import com.yocto.service.business.linkmanManage.ILinkmanService;
import com.yocto.service.business.orderManage.IOrderService;
import com.yocto.util.AppUtil;
import com.yocto.util.Const;
import com.yocto.util.DateUtil;
import com.yocto.util.FileUpload;
import com.yocto.util.Jurisdiction;
import com.yocto.util.ObjectExcelRead;
import com.yocto.util.PageData;
import com.yocto.util.RsmwUtil;
import com.yocto.util.TextUtil;
import com.yocto.websockect.SystemWebSocketHandler;

/**
 * 
 * 
 * @author xl
 *
 */
@Controller
@RequestMapping(value = "saleCustomer")
public class SaleCustomerController extends BaseController {

	@Resource(name = "customerService")
	private ICustomerService customerService;

	@Resource(name = "systemWebSocketHandler")
	private SystemWebSocketHandler systemWebSocketHandler;

	@Resource(name = "expressService")
	private IExpressService expressService;

	@Resource(name = "orderService")
	private IOrderService orderService;

	@Resource(name = "linkmanService")
	private ILinkmanService linkmanService;

	/**
	 * 获取客户公海列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/showSaleCustomer")
	@ResponseBody
	public Object showSaleCustomer() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = null;
		String error = null;
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

			if ("2".equals(pd.getString("isKhGh"))) {
				User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
				String userId = user.getUSER_ID();
				// 销售能看到自己的客户
				/*	if (isRole(Const.ROLE_SALES_ELITE) || isRole(Const.ROLE_PHONE_SALES)) {
						if (!"1".equals(userId)) {
							pd.put("saleId", userId);
						}
					}*/

				if ("1".equals(this.getGroup()) || "2".equals(this.getGroup())) {
					if (isRole(Const.ROLE_PHONE_SALES_DIRECTOR)) {
						pd.put("dxzgId", userId);
					} else {
						pd.put("saleId", userId);
					}
				}
			}

			if (TextUtil.isNotNull(pd.getString("type"))) {
				User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
				String userId = user.getUSER_ID();
				// 销售能看到自己的客户
				if ("2".equals(this.getGroup())) {
					pd.put("tSaleId", userId);
				}

				if ("1".equals(this.getGroup())) {
					if (isRole(Const.ROLE_PHONE_SALES_DIRECTOR)) {
						pd.put("tDxzgId", userId);
					} else {
						pd.put("tDfsaleId", userId);
					}
				}

				if (isRole(Const.ROLE_MANAGER) && TextUtil.isNotNull(pd.getString("saleId"))) {
					pd.put("USER_ID", pd.getString("saleId"));
					if ("2".equals(this.getGroup(pd))) {
						pd.put("tSaleId", pd.getString("saleId"));
					}

					if ("1".equals(this.getGroup(pd))) {
						pd.put("tDfsaleId", pd.getString("saleId"));
					}
					pd.remove("saleId");
					pd.remove("USER_ID");
				}
			}

			if ("5".equals(pd.getString("isKhGh"))) {

				User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
				String userId = user.getUSER_ID();
				// 销售能看到自己的客户
				if ("1".equals(this.getGroup())) {
					if (!isRole(Const.ROLE_PHONE_SALES_DIRECTOR)) {
						pd.put("dfSaleName", userId);
					}
				}

				if ((isRole(Const.ROLE_MANAGER) && TextUtil.isNotNull(pd.getString("saleName"))) || (isRole(Const.ROLE_SALES_ELITEDX) && TextUtil.isNotNull(pd.getString("saleName")))
						|| (isRole(Const.ROLE_PHONE_SALES_DIRECTOR) && TextUtil.isNotNull(pd.getString("saleName")))) {
					pd.put("USER_ID", pd.getString("saleName"));
					if ("1".equals(this.getGroup(pd))) {
						pd.put("dfSaleName", pd.getString("saleName"));
					}
					pd.remove("saleName");
					pd.remove("USER_ID");
				}
			}
			String pageNo = pd.getString("pageNo");
			String pageSize = pd.getString("pageSize");
			int pNo = TextUtil.isNull(pageNo) ? 1 : Integer.parseInt(pageNo);
			int pSize = TextUtil.isNull(pageSize) ? 10 : Integer.parseInt(pageSize);
			PageHelper.startPage(pNo, pSize); // 核心分页代码
			page = new PageInfo(customerService.listSaleCustomer(pd));// 列出任务列表
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
		}
		return AppUtil.returnObject(new PageData(), map);
	}

	/**
	 * 获取客户公海列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/showCustomerKhgh")
	@ResponseBody
	public Object showCustomerKhgh() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = null;
		String error = null;
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
			String pageNo = pd.getString("pageNo");
			String pageSize = pd.getString("pageSize");
			int pNo = TextUtil.isNull(pageNo) ? 1 : Integer.parseInt(pageNo);
			int pSize = TextUtil.isNull(pageSize) ? 10 : Integer.parseInt(pageSize);
			PageHelper.startPage(pNo, pSize); // 核心分页代码
			page = new PageInfo(customerService.listCustomerKhgh(pd));// 列出任务列表
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
		}
		return AppUtil.returnObject(new PageData(), map);
	}

	/**
	 * 判断用户名是否重复
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
			pd.put("name",pd.getString("name").trim());
			if (customerService.findName(pd) != null|| customerService.findByName(pd) != null) {
				errInfo = "error";
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo); // 返回结果
		return AppUtil.returnObject(new PageData(), map);
	}

	/**
	 * 新增潜在客户
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveSaleCustomer")
	@ResponseBody
	public Object saveSaleCustomer() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String error = "";
		String msg = "";
		String result = "";
		try {
			PageData pd = this.getPageData();
			pd.put("isKhgh", 2);
			pd.put("type", 2);
			pd.put("qdTime", DateUtil.getCurrentTime());
			List<PageData> list1 = customerService.findAllTask();
			for (PageData task : list1) {
				if ((task.get("saleIds").toString()).indexOf(pd.get("saleId").toString()) >= 0) {
					pd.put("hrghTime", getNextTime(pd.getString("qdTime"), Integer.valueOf((task.get("days").toString()))));
					break;
				}
			}
			customerService.saveSaleCustomer(pd);
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
	 * 将文件上传到服务器并将它导入数据库
	 * 
	 */
	@RequestMapping(value = "/importExcel")
	@ResponseBody
	public synchronized Object importExcel(@RequestParam(required = false) MultipartFile file, @RequestParam(required = true) String isKhgh, @RequestParam(required = false) String saleId)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = null;
		String error = null;
		String result = "";
		try {
			if (null != file && !file.isEmpty()) {
				// 保存文件
				PageData map1 = FileUpload.upload(file);
				// 获取上传人
				String realPath = map1.get("realPath").toString();
				String originalFilename = map1.get("originalFilename").toString();
				// 解析excel
				List<Object> readExcel = ObjectExcelRead.readExcel(realPath, originalFilename, 1, 0, 0);
				List<PageData> list1 = customerService.listSaleCustomer1(this.getPageData());
				List<PageData> list2 = customerService.listAll(this.getPageData());
				List<Map<String, Object>> list = toList(readExcel);
				List<String> l2 = new ArrayList<String>();
				for (PageData pd : list1) {
					l2.add(pd.getString("name"));
				}
				for (PageData pd : list2) {
					l2.add(pd.getString("name"));
				}
				String names = "";
				List<PageData> list5 = customerService.findAllTask();
				PageData task1 = null;
				if ("2".equals(isKhgh)) {
					for (PageData task : list5) {
						if ((task.get("saleIds").toString()).indexOf(saleId) >= 0) {
							task1 = task;
							break;
						}
					}
				}
				Map<String, Map<String, Object>> excelMap = new HashMap<String, Map<String, Object>>();
				for (int i = 0, len = list.size(); i < len; ++i) { //
					// 插入客户公海
					list.get(i).put("hrghTime", null);
					if ("2".equals(isKhgh)) {
						list.get(i).put("qdTime", DateUtil.getCurrentTime());
						list.get(i).put("type", 2);
						if (task1 != null)
							list.get(i).put("hrghTime", getNextTime(list.get(i).get("qdTime").toString(), Integer.valueOf((task1.get("days").toString()))));

					}
					list.get(i).put("isKhgh", isKhgh);
					list.get(i).put("saleId", saleId);
					excelMap.put(list.get(i).get("name").toString(), list.get(i));
				}

				for (String name : l2) {
					if (excelMap.containsKey(name)) {
						if ("6".equals(isKhgh)) {
							PageData pd1 = new PageData();
							pd1.put("flag", "0");
							pd1.put("isKhgh", isKhgh);
							pd1.put("name", name);
							customerService.updateName(pd1);
							customerService.updateName1(pd1);
						}
						if ("7".equals(isKhgh)) {
							PageData pd1 = new PageData();
							pd1.put("flag", "1");
							pd1.put("isKhgh", isKhgh);
							pd1.put("name", name);
							customerService.updateName(pd1);
							customerService.updateName1(pd1);
						} else {
							names += name + ",";
							excelMap.remove(name);
						}
					}
				}
				// 将list重新装箱
				list = new ArrayList<Map<String, Object>>();
				Set<Entry<String, Map<String, Object>>> entrySet = excelMap.entrySet();
				for (Entry<String, Map<String, Object>> value : entrySet) {
					list.add(value.getValue());
				}

				// 批量插入
				if (list.size() > 0) {
					customerService.saveSaleCustomers(list);
				}
				List<Map<String, Object>> list3 = new ArrayList<Map<String, Object>>();
				for (Map<String, Object> pd : list) {
					if (TextUtil.isNotNull(pd.get("gjTime").toString()) || TextUtil.isNotNull(pd.get("status").toString()) || TextUtil.isNotNull(pd.get("remark").toString())
							|| TextUtil.isNotNull(pd.get("createTime").toString())) {
						Map<String, Object> pd2 = new HashMap<String, Object>();
						User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);

						String status = pd.get("status").toString();
						int i = 0;
						if ("未处理".equals(status)) {
							i = 0;
						}
						if ("初步沟通".equals(status)) {
							i = 1;
						}
						if ("无意向".equals(status)) {
							i = 2;
						}
						if ("有意向".equals(status)) {
							i = 3;
						}
						if ("销售外访".equals(status)) {
							i = 4;
						}
						if ("客户来访".equals(status)) {
							i = 5;
						}
						if ("需求确定".equals(status)) {
							i = 6;
						}
						if ("方案/报价".equals(status)) {
							i = 7;
						}
						if ("谈判/合同".equals(status)) {
							i = 8;
						}
						if ("成交".equals(status)) {
							i = 9;
						}
						if ("暂时搁置".equals(status)) {
							i = 10;
						}
						if ("未成交".equals(status)) {
							i = 11;
						}
						pd2.put("saleId", user.getUSER_ID());
						pd2.put("saleCustomerId", pd.get("id"));
						pd2.put("gjTime", pd.get("gjTime").toString());
						pd2.put("content", pd.get("remark").toString());
						pd2.put("status", i);
						pd2.put("type", 0);
						pd2.put("createTime", pd.get("createTime").toString());
						list3.add(pd2);
					}
				}
				if (list3.size() > 0) {
					customerService.saveSaleProcess1s(list3);
				}
				if (TextUtil.isNotNull(names)) {
					result = names.substring(0, names.length() - 1);
				}
				error = "00";
				msg = "导入成功";
			} else {
				error = "01";
				msg = "请选中excel文件";
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

	// 去重list
	private static List<Map<String, Object>> toQcList(List<Map<String, Object>> list) {
		Map<String, Map<String, Object>> temp = new HashMap<String, Map<String, Object>>();
		for (Map<String, Object> pd1 : list) {
			String key = pd1.get("name").toString();
			temp.put(key, pd1);
		}
		List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();
		for (String key : temp.keySet()) {
			list1.add(temp.get(key));
		}
		return list1;
	}

	private List<Map<String, Object>> toList(List<Object> list) throws Exception {
		// 查找关键字
		/*String keywords = "";
		List<PageData> listWords = customerService.listWords(new PageData());
		for (int i = 0; null != listWords && i < listWords.size(); i++) {
			keywords = keywords + listWords.get(i).getString("keywords") + ",";
		}*/
		List<PageData> listWords = customerService.listWords(new PageData());
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
		List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < list.size(); i++) {
			@SuppressWarnings("unchecked")
			Map<String, Object> pd = (Map<String, Object>) list.get(i);
			Map<String, Object> pd1 = new HashMap<String, Object>();
			pd1.put("name", null != pd.get("var0") ? pd.get("var0").toString().trim() : "");
			pd1.put("cjrName", user.getNAME());
			pd1.put("linkmanName", null != pd.get("var1") ? pd.get("var1").toString().trim() : "");
			pd1.put("linkmanLandline", null != pd.get("var2") ? pd.get("var2").toString().trim().replaceAll(" ", "") : "");
			pd1.put("linkmanMobilePhone", null != pd.get("var3") ? pd.get("var3").toString().trim().replaceAll(" ", "") : "");
			pd1.put("email", null != pd.get("var4") ? pd.get("var4").toString().trim() : "");
			pd1.put("weChat", null != pd.get("var5") ? pd.get("var5").toString().trim() : "");
			pd1.put("qq", null != pd.get("var6") ? pd.get("var6").toString().trim() : "");
			pd1.put("province", null != pd.get("var7") ? pd.get("var7").toString().trim() : "");
			pd1.put("cityName", null != pd.get("var8") ? pd.get("var8").toString().trim() : "");
			pd1.put("area", null != pd.get("var9") ? pd.get("var9").toString().trim() : "");
			pd1.put("address", null != pd.get("var10") ? pd.get("var10").toString().trim() : "");
			pd1.put("status", null != pd.get("var11") ? pd.get("var11").toString().trim() : "");
			pd1.put("remark", null != pd.get("var12") ? pd.get("var12").toString().trim() : ""); // 跟进内容
			pd1.put("createTime", null != pd.get("var13") ? pd.get("var13").toString().trim() : ""); // 实际跟进时间
			pd1.put("gjTime", null != pd.get("var14") ? pd.get("var14").toString().trim() : ""); // 下次跟进时间
			pd1.put("content", null != pd.get("var15") ? pd.get("var15").toString().trim() : "");
			pd1.put("zczb", null != pd.get("var16") ? pd.get("var16").toString().trim() : "");
			pd1.put("clrq", null != pd.get("var17") ? pd.get("var17").toString().trim() : "");
			if (TextUtil.isNotNull(pd1.get("name").toString())/* && keywords.indexOf(pd1.get("name").toString()) < 0*/) {
				boolean flag = true;
				for (int j = 0; null != listWords && j < listWords.size(); j++) {
					if (pd1.get("name").toString().indexOf(listWords.get(j).getString("keywords")) > -1) {
						flag = false;
						break;
					}
				}
				if (flag) {
					list1.add(pd1);
				}
			}
		}
		return list1;
	}

	public static void main(String[] args) {
		System.out.println("民主,富强,正义".indexOf("大义自然民主"));
	}

	/**
	 * 
	 * 销售抢夺客户公海
	 * 
	 */
	@RequestMapping(value = "/qdKhgh")
	@ResponseBody
	public synchronized Object qdKhgh() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = null;
		String error = null;
		String result = "";
		try {
			User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
			PageData pd = this.getPageData();
			String id = pd.getString("ids");
			String[] ids = id.split(",");

			PageData pd2 = this.getPageData();
			pd2.put("isKhGh", 2);
			List<PageData> list = customerService.listSaleCustomer(pd2);
			List<String> idlist1 = new ArrayList<String>(); //
			List<String> idlist2 = new ArrayList<String>(); // 数据库用户
			for (String id1 : ids) {
				idlist1.add(id1);
			}

			List<PageData> list1 = customerService.findAllTask();
			int j = 0;

			for (PageData task : list1) {
				if ((task.get("saleIds").toString()).indexOf(user.getUSER_ID()) >= 0 && "1".equals(task.get("isOpen").toString())) {
					for (int i = 0; i < idlist1.size(); i++) {
						PageData customer = new PageData();
						customer.put("lastSaleId", user.getUSER_ID());
						customer.put("cfdays", task.get("cfdays"));
						customer.put("id", idlist1.get(i));
						customer = customerService.findLastCustomer(customer);
						if (null != customer) {
							idlist1.remove(i);
							i--;
							j++;
						}
					}
					break;
				}
			}

			/*	if (idlist1.size() > 0) {
					ids = new String[idlist1.size()];
					for (int i = 0; i < idlist1.size(); i++) {
						ids[i] = idlist1.get(i);
					}
				} else {
					ids = new String[0];
				}
			*/
			// 判断idlist1里是否是10天后的日期

			for (PageData pd3 : list) {
				idlist2.add(pd3.get("id").toString());
			}
			idlist2.retainAll(idlist1);

			if (j > 0) {
				result = "该客户因您长期未跟进自动划入公海，暂时不能抢。";
			}

			if (idlist2.size() > 0) {
				if (idlist2.size() >= idlist1.size()) {
					if (j > 0) {
						result = "该客户因您长期未跟进自动划入公海，暂时不能抢。";
					} else {
						result = "您抢到0个客户";
					}
				} else {
					if (j > 0) {
						result = "您抢到" + (idlist1.size() - idlist2.size()) + "个客户（有" + j + "个最近才操作的客户您并没有抢到）";
					} else {
						result = "您抢到" + (idlist1.size() - idlist2.size()) + "个客户";
					}
				}
				idlist1.removeAll(idlist2);
				if (idlist1.size() >= 0) {
					ids = (String[]) idlist1.toArray(new String[idlist1.size()]);
				}
			} else {
				if (idlist1.size() > 0) {
					if (j > 0) {
						result = "您抢到" + idlist1.size() + "个客户（有" + j + "个客户因您长期未跟进自动划入公海，暂时不能抢）";
					} else {
						result = "您抢到" + idlist1.size() + "个客户";
					}
					ids = new String[idlist1.size()];
					for (int i = 0; i < idlist1.size(); i++) {
						ids[i] = idlist1.get(i);
					}
				} else {
					ids = new String[0];
				}
			}

			if (ids.length > 0) {
				// 判断当前用户是否为电访销售
				String saleId = user.getUSER_ID();
				pd.put("saleId", saleId);
				pd.put("qdTime", DateUtil.getCurrentTime());
				if ("1".equals(this.getGroup())) {
					if (!"1".equals(saleId)) {
						pd.put("dfSaleId", saleId);
					}
				}
				for (PageData task : list1) {
					if ((task.get("saleIds").toString()).indexOf(pd.get("saleId").toString()) >= 0) {
						pd.put("hrghTime", getNextTime(pd.getString("qdTime"), Integer.valueOf((task.get("days").toString()))));
						break;
					}
				}
				pd.put("id", ids);
				customerService.updateSaleId(pd);
				error = "00";
				msg = "抢夺成功";
			}
		} catch (Exception e) {
			e.printStackTrace();
			error = "500";
			msg = "接口错误：" + e.getMessage();
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
				// 判断当前用户是否为客服
				String saleId = user.getUSER_ID();
				pd.put("saleId", saleId);
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
					pd.put("type", 0);
					pd.put("createTime", DateUtil.getCurrentTime());
					pd.put("status", 0);
					customerService.saveSaleProcess(pd);
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
		return name.trim();
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
			pd.put("saleCustomerId", Double.valueOf(map1.get("customerId").toString()).intValue());
			pd.put("customerName", map1.get("customerName"));
			pd.put("callUuid", map1.get("callId"));
			pd.put("time", map1.get("nextTime"));
			pd.put("content", map1.get("content"));
			pd.put("status", map1.get("status"));
			pd.put("score", map1.get("score"));
			pd.put("type", map1.get("type"));

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

			if (null != pd.get("callUuid")) {
				customerService.updateProcess(pd);
				pd = customerService.findProcessByIdOrCallUuid(pd);
			} else {
				User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
				pd.put("saleId", user.getUSER_ID());
				customerService.saveSaleProcess(pd);
			}

			for (int i = 0; i < userIds.size(); i++) {
				/*userId += userIds.get(i) + ",";*/
				// 保存通知
				PageData pd3 = new PageData();
				pd3.put("userId", userIds.get(i));
				pd3.put("type", 0);
				pd3.put("relateId", pd.get("id"));
				pd3.put("content", /*"销售" + */((User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER)).getNAME() + "对潜在客户" + "跟进了一条记录需要您阅读");
				pd3.put("flag", 0);
				customerService.saveNoticInfo(pd3);
				systemWebSocketHandler.sendMessageToUser(userIds.get(i), new TextMessage("notic:" + pd3.get("id")));
			}

			if ("1".equals(this.getGroup()) && "3".equals(map1.get("status").toString())) {
				if (!"1".equals(((User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER)).getUSER_ID())) {
					pd.put("zyTime", DateUtil.getCurrentTime());
					pd.put("isKhgh", 3);
					customerService.updateKhgh5(pd);
				}
			}

			if ("2".equals(map1.get("status").toString())) {
				pd.put("zyTime", DateUtil.getCurrentTime());
				PageData pd2 = new PageData();
				pd2.put("id", pd.get("saleCustomerId"));
				pd2 = customerService.findSaleCustomerById(pd2);
				if ("4".equals(pd2.get("type").toString())) {
					pd.put("isKhgh", 4);
					customerService.updateKhgh5(pd);
				}
				if ("6".equals(pd2.get("type").toString())) {
					pd.put("isKhgh", 6);
					customerService.updateKhgh5(pd);
				}
				if ("7".equals(pd2.get("type").toString())) {
					pd.put("isKhgh", 7);
					customerService.updateKhgh5(pd);
				}
				if ("1".equals(pd2.get("type").toString())) {
					pd.put("isKhgh", 5);
					customerService.updateKhgh5(pd);
				}
				if ("0".equals(pd2.get("type").toString()) || "3".equals(pd2.get("type").toString())) {
					pd.put("isKhgh", 1);
					customerService.updateKhgh5(pd);
				}
			}

			if ("12".equals(map1.get("status").toString())) {
				pd.put("zyTime", DateUtil.getCurrentTime());
				PageData pd2 = new PageData();
				pd2.put("id", pd.get("saleCustomerId"));
				pd2 = customerService.findSaleCustomerById(pd2);

				if ("1".equals(pd2.get("type").toString())) {
					pd.put("isKhgh", 5);
					customerService.updateKhgh5(pd);
				}
			}

			List<PageData> list2 = customerService.findAllTask();
			for (PageData task : list2) {
				if ((task.get("saleIds").toString()).indexOf(pd.get("saleId").toString()) >= 0) {
					pd.put("hrghTime", getNextTime(pd.getString("createTime"), Integer.valueOf((task.get("days").toString()))));
					customerService.updateHrghTime1(pd);
					break;
				}
			}

			// 保存附件
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> list = (List<Map<String, Object>>) map1.get("files");
			for (Map<String, Object> map2 : list) {
				map2.put("expressId", pd.get("id"));
				map2.put("type", 13);
			}
			if (list.size() > 0) {
				expressService.saveAttach(list);
			}
			// 保存提醒人列表
			pd.put("type", 0);
			@SuppressWarnings("unchecked")
			List<String> list1 = (List<String>) map1.get("remind");
			for (String id : list1) {
				pd.put("saleProcessId", id);
				customerService.saveSaleProcessAbout(pd);
			}
			/*if ("2".equals(map1.get("status").toString())) {
				// 将客户名称导入已成交客户
				pd.put("name", map1.get("customerName"));
				customerService.saveCustomer(pd);
			}*/
			if (TextUtil.isNotNull(map1.get("nextTime").toString())) {
				// 写提醒
				User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
				PageData pd1 = new PageData();
				pd1.put("userId", user.getUSER_ID());
				pd1.put("time", map1.get("nextTime"));
				pd1.put("customerId", Double.valueOf(map1.get("customerId").toString()).intValue());
				pd1.put("remark", "你的客户" + map1.get("customerName").toString() + "需要你进行跟进0");
				orderService.saveCallInfo(pd1);
			}
			// 保存日志
			PageData pd1 = new PageData();
			User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
			pd1.put("userId", user.getUSER_ID());
			pd1.put("type", "跟进保存");
			pd1.put("saleCustomerId", Double.valueOf(map1.get("customerId").toString()).intValue());
			pd1.put("flag", "0");
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

	/**
	 * 打完电话系统返回的回调函数、将录音保存到跟进表中
	 * 
	 * @param request
	 * @param response
	 * @param mydomin
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/callBack")
	@ResponseBody
	public Object callBack(HttpServletRequest request, HttpServletResponse response, @RequestBody String mydomin) throws Exception {
		System.out.println("==============已经打完电话了，并收到了回调函数================");
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = null;
		String error = null;
		PageInfo<List<PageData>> page = null;
		try {
			Gson gson = new Gson();
			Type type = new TypeToken<Map<Object, Object>>() {
			}.getType();
			Map<Object, Object> map1 = gson.fromJson(mydomin, type);
			String recordUrl = Const.RSMW_DOMAIN + map1.get("recordUrl").toString();
			String callUuid = map1.get("callUuid").toString();
			// 根据callUuid更改数据recordUrl
			PageData pd = this.getPageData();
			pd.put("recordUrl", recordUrl);
			pd.put("startTime", map1.get("startTime"));
			pd.put("billSeconds", map1.get("billSeconds"));
			pd.put("callUuid", callUuid);

			PageData pd1 = customerService.findProcessByIdOrCallUuid(pd);
			if (null != pd1) {
				if (customerService.updateProcess(pd) > 0) {
					systemWebSocketHandler.sendMessageToUser(pd1.get("saleId").toString(), new TextMessage("saler:" + callUuid));
					System.out.println("==============已经发送了websocket内容给客户端================");
					msg = "拨打电话成功";
					error = "00";
				}
			} else {
				pd1 = customerService.findYzProcessById(pd);
				if (null != pd1) {
					if (customerService.updateProcess1(pd) > 0) {
						systemWebSocketHandler.sendMessageToUser(pd1.get("runnerId").toString(), new TextMessage("runner:" + callUuid));
						System.out.println("==============已经发送了websocket内容给客户端================");
						msg = "拨打电话成功";
						error = "00";
					}
				}
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
	 * 
	 * 客户转移至有意向公海、无意向公海
	 */
	@RequestMapping(value = "/moveGh")
	@ResponseBody
	public Object moveGh(HttpServletRequest request, HttpServletResponse response, @RequestBody String mydomin) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = null;
		String error = null;
		PageInfo<List<PageData>> page = null;
		try {
			Gson gson = new Gson();
			Type type = new TypeToken<Map<Object, Object>>() {
			}.getType();
			Map<Object, Object> map1 = gson.fromJson(mydomin, type);
			@SuppressWarnings("unchecked")
			List<Double> id = (List<Double>) map1.get("id");
			List<String> ids = new ArrayList<String>();
			for (Double d : id) {
				ids.add(String.valueOf(d.intValue()));
			}
			int isKhgh = Double.valueOf(map1.get("isKhgh").toString()).intValue();
			if (ids.size() > 0) {
				PageData pd = this.getPageData();
				pd.put("id", ids);
				pd.put("isKhgh", isKhgh);
				pd.put("zyTime", DateUtil.getCurrentTime());
				customerService.updateKhgh(pd);
				error = "00";
				msg = "修改成功";
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

	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delete(HttpServletRequest request, HttpServletResponse response, @RequestBody String mydomin) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = null;
		String error = null;
		PageInfo<List<PageData>> page = null;
		try {
			Gson gson = new Gson();
			Type type = new TypeToken<Map<Object, String[]>>() {
			}.getType();
			Map<Object, String[]> map1 = gson.fromJson(mydomin, type);
			String[] ids = map1.get("id");
			if (ids.length > 0) {
				customerService.deletes(ids);
				error = "00";
				msg = "删除成功";
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

	// 获取几天后的天数
	private static String getNextTime(String time, int day) throws Exception {
		Calendar calendarH = Calendar.getInstance();
		calendarH.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time));// 把当前时间赋给日历
		calendarH.add(Calendar.DATE, day);
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendarH.getTime());
	}

	/**
	 * 经理转移
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/moveKh")
	@ResponseBody
	public Object moveKh(@RequestBody String mydomin) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = null;
		String error = null;
		PageInfo<List<PageData>> page = null;
		try {
			Gson gson = new Gson();
			Type type = new TypeToken<Map<Object, Object>>() {
			}.getType();
			Map<Object, Object> map1 = gson.fromJson(mydomin, type);
			@SuppressWarnings("unchecked")
			List<Double> id = (ArrayList<Double>) map1.get("id");
			PageData pd = this.getPageData();
			pd.put("qdTime", DateUtil.getCurrentTime());
			List<PageData> list1 = customerService.findAllTask();
			for (PageData task : list1) {
				if ((task.get("saleIds").toString()).indexOf(map1.get("saleId").toString()) >= 0) {
					pd.put("hrghTime", getNextTime(pd.getString("qdTime"), Integer.valueOf(task.get("days").toString())));
					break;
				}
			}
			if (id.size() > 0 && null != map1.get("saleId")) {
				pd.put("id", id);
				pd.put("saleId", map1.get("saleId"));
				PageData sales = customerService.findPhoneSale(pd);
				if (null != sales) {
					pd.put("dfSaleId", map1.get("saleId"));
				} else {
					// 根据id找出是否有电访销售
					List<PageData> pds = customerService.findPhoneSaleByIds(pd);
					// 如果有电访销售
					List<String> list = new ArrayList<String>();
					for (PageData ids : pds) {
						list.add(ids.get("id").toString());
					}
					PageData pd1 = new PageData();
					if (list.size() > 0) {
						pd1.put("type", "0");
						pd1.put("id", list);
						customerService.updateSaleId(pd1);
					}
				}
				if (null != map1.get("type")) {
					pd.put("type", ((Double) map1.get("type")).intValue() + "");
				}
				customerService.updateSaleId(pd);
				error = "00";
				msg = "转移成功";
			} else {
				error = "01";
				msg = "转移失败";
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
	 * 获取客户概览
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getsaleCustomerDetail")
	@ResponseBody
	public Object getOrderDetail() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData result = null;
		String msg = null;
		String error = null;
		try {
			PageData pd = this.getPageData();
			if (TextUtil.isNotNull(pd.getString("id"))) {
				/*User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
				String userId = user.getUSER_ID();*/
				result = customerService.findSaleCustomerById(pd);
				/*if (isRole(Const.ROLE_SALES_ELITE) || isRole(Const.ROLE_PHONE_SALES)) {
					if (!"1".equals(userId)) {
						@SuppressWarnings("unchecked")
						List<PageData> list = (List<PageData>) result.get("logs");
						List<PageData> list1 = new ArrayList<PageData>();
						for (PageData pd1 : list) {
							if (userId.equals(pd1.get("userId").toString())) {
								list1.add(pd1);
							}
						}
						result.remove("logs");
						result.put("logs", list1);
					}
				}*/
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
			if (TextUtil.isNotNull(pd.getString("id"))) {
				result = customerService.findProcessInfo(pd);
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
		}
		return AppUtil.returnObject(new PageData(), map);
	}

	// 修改潜在客户信息
	@RequestMapping(value = "updateSaleCustomer")
	@ResponseBody
	public Object updateSaleCustomer() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData result = null;
		String msg = null;
		String error = null;
		try {
			PageData pd = this.getPageData();
			pd.put("editTime", DateUtil.getCurrentTime());
			PageData pd1 = customerService.findSaleCustomerById(pd);
			if (customerService.updateSaleCustomer(pd) > 0) {
				String qzdz = "";
				String hzdz = "";
				// 前字段值、后字段值
				if (TextUtil.isNotNull(pd.getString("name"))) {
					qzdz = pd1.getString("name");
					hzdz = pd.getString("name");
				}
				if (TextUtil.isNotNull(pd.getString("postion"))) {
					qzdz = pd1.getString("postion");
					hzdz = pd.getString("postion");
				}
				if (TextUtil.isNotNull(pd.getString("landline"))) {
					qzdz = pd1.getString("landline");
					hzdz = pd.getString("landline");
				}
				if (TextUtil.isNotNull(pd.getString("mobilePhone"))) {
					qzdz = pd1.getString("mobilePhone");
					hzdz = pd.getString("mobilePhone");
				}
				if (TextUtil.isNotNull(pd.getString("email"))) {
					qzdz = pd1.getString("email");
					hzdz = pd.getString("email");
				}
				if (TextUtil.isNotNull(pd.getString("province"))) {
					qzdz = pd1.getString("province");
					hzdz = pd.getString("province");
				}
				if (TextUtil.isNotNull(pd.getString("cityName"))) {
					qzdz = pd1.getString("cityName");
					hzdz = pd.getString("cityName");
				}
				if (TextUtil.isNotNull(pd.getString("area"))) {
					qzdz = pd1.getString("area");
					hzdz = pd.getString("area");
				}
				if (TextUtil.isNotNull(pd.getString("zczb"))) {
					qzdz = pd1.getString("zczb");
					hzdz = pd.getString("zczb");
				}
				if (TextUtil.isNotNull(pd.getString("clrq"))) {
					qzdz = pd1.getString("clrq");
					hzdz = pd.getString("clrq");
				}
				if (TextUtil.isNotNull(pd.getString("address"))) {
					qzdz = pd1.getString("address");
					hzdz = pd.getString("address");
				}
				if (TextUtil.isNotNull(pd.getString("status"))) {
					qzdz = pd1.getString("status");
					hzdz = pd.getString("status");
				}
				if (TextUtil.isNotNull(pd.getString("customerresource"))) {
					qzdz = pd1.getString("customerresource");
					hzdz = pd.getString("customerresource");
				}
				if (TextUtil.isNotNull(pd.getString("ownerindustry"))) {
					qzdz = pd1.getString("ownerindustry");
					hzdz = pd.getString("ownerindustry");
				}
				if (TextUtil.isNotNull(pd.getString("money"))) {
					qzdz = pd1.getString("money");
					hzdz = pd.getString("money");
				}
				if (TextUtil.isNotNull(pd.getString("xcgetTime"))) {
					qzdz = pd1.getString("xcgetTime");
					hzdz = pd.getString("xcgetTime");
				}
				if (TextUtil.isNotNull(pd.getString("content"))) {
					qzdz = pd1.getString("content");
					hzdz = pd.getString("content");
				}
				if (TextUtil.isNotNull(pd.getString("linkmanLandline"))) {
					qzdz = pd1.getString("linkmanLandline");
					hzdz = pd.getString("linkmanLandline");
				}
				if (TextUtil.isNotNull(pd.getString("linkmanName"))) {
					qzdz = pd1.getString("linkmanName");
					hzdz = pd.getString("linkmanName");
				}
				if (TextUtil.isNotNull(pd.getString("linkmanMobilePhone"))) {
					qzdz = pd1.getString("linkmanMobilePhone");
					hzdz = pd.getString("linkmanMobilePhone");
				}
				if (TextUtil.isNotNull(pd.getString("qq"))) {
					qzdz = pd1.getString("qq");
					hzdz = pd.getString("qq");
				}
				if (TextUtil.isNotNull(pd.getString("weChat"))) {
					qzdz = pd1.getString("weChat");
					hzdz = pd.getString("weChat");
				}
				User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
				pd.put("userId", user.getUSER_ID());
				pd.put("qzdz", qzdz);
				pd.put("hzdz", hzdz);
				pd.put("saleCustomerId", pd.getString("id"));
				pd.put("flag", "0");
				pd.put("type", "编辑");
				customerService.saveLogs(pd);
				error = "00";
				msg = "修改成功";
			} else {
				error = "01";
				msg = "修改失败";
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
	 * 去新增页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goZy")
	public ModelAndView goZy() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		pd.put("parentId", 0);
		List<PageData> sfList = orderService.showCountryData(pd);
		mv.addObject("sfList", sfList);
		pd.put("parentId", 1);
		List<PageData> sqList = orderService.showCountryData(pd);
		mv.addObject("sqList", sqList);
		pd = customerService.findSaleCustomerById(pd);
		mv.addObject("pd", pd);
		mv.addObject("msg", "doAdd");
		mv.setViewName("business/customerManage/saleCustomer_add");
		return mv;
	}

	@RequestMapping(value = "/doAdd")
	public Object doAdd() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "新增");
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		customerService.updateCustomer(pd);
		pd.remove("id");
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

		// 保存完了保存联系人
		if (null != pd.getString("linkmanName") || null != pd.getString("linkmanPostion") || null != pd.getString("linkmanLandline") || null != pd.getString("linkmanMobilePhone")) {
			String[] linkmanName = pd.getString("linkmanName").split(",");
			String[] linkmanPostion = pd.getString("linkmanPostion").split(",");
			String[] linkmanLandline = pd.getString("linkmanLandline").split(",");
			String[] linkmanMobilePhone = pd.getString("linkmanMobilePhone").split(",");

			if (linkmanName.length == linkmanPostion.length && linkmanLandline.length == linkmanMobilePhone.length && linkmanName.length == linkmanLandline.length) {
				List<Map<String, String>> list = new ArrayList<Map<String, String>>();
				for (int i = 0; i < linkmanName.length; i++) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("name", "@xy@".equals(linkmanName[i]) ? "" : linkmanName[i]);
					map.put("relateId", pd.get("id").toString());
					map.put("postion", "@xy@".equals(linkmanPostion[i]) ? "" : linkmanPostion[i]);
					map.put("landline", "@xy@".equals(linkmanLandline[i]) ? "" : linkmanLandline[i]);
					map.put("mobilePhone", "@xy@".equals(linkmanMobilePhone[i]) ? "" : linkmanMobilePhone[i]);
					list.add(map);
				}
				if (list.size() > 0) {
					customerService.saveLinkmans(list);
				}
			}
		}

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

	@RequestMapping(value = "/updateLinkMans")
	@ResponseBody
	public Object updateLinkMans() throws Exception {
		String error = "";
		String msg = "";
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			PageData pd = this.getPageData();
			if (TextUtil.isNotNull(pd.getString("id"))) {
				linkmanService.update(pd);
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
			map.put("result", "");
		}
		return AppUtil.returnObject(new PageData(), map);
	}

	@RequestMapping(value = "/getSaleCustomerInfo")
	@ResponseBody
	public Object getSaleCustomerInfo() throws Exception {
		PageData pd = this.getPageData();
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
		if (isRole(Const.ROLE_PHONE_SALES) || isRole(Const.ROLE_SALES_ELITE)) {
			if (!"1".equals(user.getUSER_ID())) {
				pd.put("saleId", user.getUSER_ID());
			}
		}
		pd.put("key", pd.getString("data[q]"));
		List<PageData> result = new ArrayList<PageData>();
		try {
			String pageNo = pd.getString("page");
			String pageSize = pd.getString("pageSize");
			/*String id = pd.getString("id");*/
			int pNo = TextUtil.isNull(pageNo) ? 1 : Integer.parseInt(pageNo);
			int pSize = TextUtil.isNull(pageSize) ? 10 : Integer.parseInt(pageSize);
			PageHelper.startPage(pNo, pSize); // 核心分页代码
			result = customerService.listAll1(pd);
		} catch (Exception e) {
			logger.error(e);
		}
		return JSONArray.fromObject(result);
	}

	@RequestMapping(value = "/getNoticInfo")
	@ResponseBody
	public Object getNoticInfo() throws Exception {
		String error = "";
		String msg = "";
		PageInfo<PageData> page = null;
		int size = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			PageData pd = this.getPageData();
			User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
			pd.put("userId", user.getUSER_ID());
			if ("1".equals(user.getUSER_ID()) || "4".equals(this.getGroup())) {
				pd.put("roleId", user.getUSER_ID());
			}
			String pageNo = pd.getString("page");
			String pageSize = pd.getString("pageSize");
			/*String id = pd.getString("id");*/
			int pNo = TextUtil.isNull(pageNo) ? 1 : Integer.parseInt(pageNo);
			int pSize = TextUtil.isNull(pageSize) ? 10 : Integer.parseInt(pageSize);
			PageHelper.startPage(pNo, pSize); // 核心分页代码
			page = new PageInfo<PageData>(orderService.findNoticInfo(pd));
			size = orderService.findNoticInfo(pd).size();
			error = "00";
			msg = "修改成功";
		} catch (Exception e) {
			error = "500";
			msg = "程序异常";
			e.printStackTrace();
		} finally {
			map.put("error", error);
			map.put("msg", msg);
			map.put("result", page);
			map.put("size", size);
		}
		return AppUtil.returnObject(new PageData(), map);
	}

	// 设置已读按钮
	@RequestMapping(value = "/updateFlag")
	@ResponseBody
	public Object updateFlag() throws Exception {
		String error = "";
		String msg = "";
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			PageData pd = this.getPageData();
			orderService.updateNoticInfo(pd);
			error = "00";
			msg = "修改成功";
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
			if (TextUtil.isNotNull(pd.getString("flag"))) {
				result = customerService.showProcessDetail1(pd);
			} else {
				result = customerService.showProcessDetail(pd);
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

	// 保存点评
	@RequestMapping(value = "/saveEvaluate")
	@ResponseBody
	public Object saveEvaluate() throws Exception {
		String error = "";
		String msg = "";
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			PageData pd = this.getPageData();
			User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
			pd.put("userId", user.getUSER_ID());
			pd.put("type", 1);
			customerService.saveEvaluate(pd);

			PageData pd4 = new PageData();
			pd4.put("userId", pd.getString("saleId"));
			pd4.put("type", 2);
			pd4.put("relateId", pd.get("processId"));
			pd4.put("content", "你的潜在客户跟进已被" + user.getNAME() + "点评");
			pd4.put("flag", 0);
			customerService.saveNoticInfo(pd4);
			systemWebSocketHandler.sendMessageToUser(pd.get("saleId").toString(), new TextMessage("notic:" + pd4.get("id")));

			error = "00";
			msg = "修改成功";
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

	//
	@RequestMapping(value = "/isYxx")
	@ResponseBody
	public Object isYxx() throws Exception {
		String error = "";
		String msg = "";
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			PageData pd = this.getPageData();
			// 判断当前用户是否是电访销售组
			if ("1".equals(getGroup())) {
				// 查看当前记录的最新动态
				pd = customerService.findSaleCustomerById(pd);
				if ("3".equals(pd.get("gjStatus") + "")) {
					error = "01";
					msg = "该客户已转至客户公海，您没有权限继续跟进。";
				} else {
					error = "00";
					msg = "通过";
				}
			} else {
				error = "00";
				msg = "通过";
			}
		} catch (Exception e) {
			error = "01";
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
	 * 新增keyWords
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/searchKeywords")
	@ResponseBody
	public Object searchKeywords() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String error = "";
		String msg = "";
		PageInfo<List<PageData>> page = null;
		try {
			PageData pd = this.getPageData();
			String pageNo = pd.getString("pageNo");
			String pageSize = pd.getString("pageSize");
			int pNo = TextUtil.isNull(pageNo) ? 1 : Integer.parseInt(pageNo);
			int pSize = TextUtil.isNull(pageSize) ? 10 : Integer.parseInt(pageSize);
			PageHelper.startPage(pNo, pSize); // 核心分页代码
			page = new PageInfo(customerService.listWords(pd));
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

	/**
	 * 查询keyWords
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveOrupdateKeywords")
	@ResponseBody
	public Object saveOrupdateKeywords() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String error = "";
		String msg = "";
		String result = "";
		try {
			PageData pd = this.getPageData();
			if ("1".equals(pd.getString("delflag"))) {
				customerService.delete(pd);
			} else {
				if (TextUtil.isNotNull(pd.getString("id"))) {
					customerService.update(pd);
				} else {
					customerService.save(pd);
				}
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

	// 判断有无有意向公海
	@RequestMapping(value = "/isGh")
	@ResponseBody
	public Object isGh() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String error = "";
		String msg = "";
		String result = "";
		try {
			PageData pd = customerService.findDxswgw();
			if (null != pd) {
				msg = "有";
			} else {
				msg = "无";
			}
			error = "00";
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
