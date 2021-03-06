package com.yocto.controller.business.workBenchManage;

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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.socket.TextMessage;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yocto.controller.base.BaseController;
import com.yocto.entity.Page;
import com.yocto.entity.system.User;
import com.yocto.service.business.attachInfoManage.IAttachInfoService;
import com.yocto.service.business.customerManage.ICustomerService;
import com.yocto.service.business.expressManage.IExpressService;
import com.yocto.service.business.invoiceManage.IInvoiceService;
import com.yocto.service.business.lawsuitauditManage.ILawsuitauditService;
import com.yocto.service.business.orderManage.IOrderService;
import com.yocto.service.business.workBenchManage.IWorkBenchService;
import com.yocto.util.AppUtil;
import com.yocto.util.Const;
import com.yocto.util.DateUtil;
import com.yocto.util.Jurisdiction;
import com.yocto.util.PageData;
import com.yocto.util.TextUtil;
import com.yocto.util.mail.SimpleMailSender;
import com.yocto.websockect.SystemWebSocketHandler;

/**
 * 类名称：WorkBenchController 创建人： @author 更新时间：2015年11月3日
 * 
 * @version
 */
@Controller
@RequestMapping(value = "/workBench")
public class WorkBenchController extends BaseController {

	String menuUrl = "workBench/list.do"; // 菜单地址(权限用)

	@Resource(name = "workBenchService")
	private IWorkBenchService workBenchService;

	@Resource(name = "attachService")
	private IAttachInfoService attachService;

	@Resource(name = "lawsuitauditService")
	private ILawsuitauditService lawsuitauditService;

	@Resource(name = "invoiceService")
	private IInvoiceService invoiceService;

	@Resource(name = "orderService")
	private IOrderService orderService;

	@Resource(name = "customerService")
	private ICustomerService customerService;

	@Resource(name = "expressService")
	private IExpressService expressService;

	@Resource(name = "systemWebSocketHandler")
	private SystemWebSocketHandler systemWebSocketHandler;

	/**
	 * 去修改页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/index")
	public ModelAndView index() throws Exception {
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		mv.addObject("pd", pd);
		mv.setViewName("business/workBenchManage/index");
		return mv;
	}

	/**
	 * 获取我的任务列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/taskList")
	@ResponseBody
	public Object taskList() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = null;
		String error = null;
		PageInfo<List<PageData>> page = null;
		try {
			PageData pd = this.getPageData();
			String keywords = pd.getString("keywords"); // 关键词检索条件
			pd.put("isCompleted", "0");
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
			// 客服 能看到待开发票
			if (isRole(Const.ROLE_CUSTOMER_SERVICE)) {
				if (!"1".equals(userId)) {
					pd.put("flag", "0");
					pd.put("doId", userId);
				}
			}

			// 运作精英、销售精英、电访销售
			/*if (isRole(Const.ROLE_OPERATION) || isRole(Const.ROLE_SALES_ELITE) || isRole(Const.ROLE_PHONE_SALES)) {
				if (!"1".equals(userId)) {
					pd.put("flag", "1");
					pd.put("doId", userId);
				}
			}*/

			// 电访组、商务顾问组、运作组(除去运作总监、销售总监、商务外协)
			if ("1".equals(this.getGroup()) || "2".equals(this.getGroup()) || "3".equals(this.getGroup())) {
				if (!(isRole(Const.ROLE_OPERATION_DIRECTOR) && isRole(Const.ROLE_SALES_DIRECTOR) && isRole(Const.ROLE_ATTORNEY))) {
					if (!"1".equals(userId)) {
						pd.put("flag", "1");
						pd.put("doId", userId);
					}
				}
			}

			// 运作总监
			if (isRole(Const.ROLE_OPERATION_DIRECTOR)) {
				if (!"1".equals(userId)) {
					pd.put("flag", "2");
					pd.put("doId", userId);
				}
			}

			// 销售主管
			if (isRole(Const.ROLE_SALES_DIRECTOR)) {
				if (!"1".equals(userId)) {
					pd.put("flag", "3");
					pd.put("doId", userId);
				}
			}

			// 公司内部律师(诉讼/仲裁)
			if (isRole(Const.ROLE_IN_ATTORNEY)) {
				if (!"1".equals(userId)) {
					pd.put("flag", "4");
					pd.put("doId", userId);
				}
			}

			String pageNo = pd.getString("pageNo");
			String pageSize = pd.getString("pageSize");
			int pNo = TextUtil.isNull(pageNo) ? 1 : Integer.parseInt(pageNo);
			int pSize = TextUtil.isNull(pageSize) ? 5 : Integer.parseInt(pageSize);
			PageHelper.startPage(pNo, pSize); // 核心分页代码
			page = new PageInfo(workBenchService.taskList(pd));// 列出任务列表
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

	@RequestMapping(value = "/callList")
	@ResponseBody
	public Object callList() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = null;
		String error = null;
		PageInfo<List<PageData>> page = null;
		try {
			PageData pd = this.getPageData();
			String pageNo = pd.getString("pageNo");
			String pageSize = pd.getString("pageSize");
			User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
			String userId = user.getUSER_ID();
			pd.put("userId", userId);
			int pNo = TextUtil.isNull(pageNo) ? 1 : Integer.parseInt(pageNo);
			int pSize = TextUtil.isNull(pageSize) ? 5 : Integer.parseInt(pageSize);
			PageHelper.startPage(pNo, pSize); // 核心分页代码
			page = new PageInfo(workBenchService.callList(pd));// 列出任务列表
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
	 * 获取我的客户列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/customerList")
	@ResponseBody
	public Object customerList() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = null;
		String error = null;
		PageInfo<List<PageData>> page = null;
		boolean isSale = false;// 是否销售
		boolean isOperation = false;// 是否运作
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
			if (isRole(Const.ROLE_SALES_ELITE)) {
				if (!"1".equals(userId)) {
					pd.put("saleId", userId);
				}
			}

			// 销售总监能看到销售精英的客户
			if (isRole(Const.ROLE_SALES_DIRECTOR)) {
				if (!"1".equals(userId)) {
					pd.put("isSaleDirector", 1);
				}
			}

			/*// 运作和运作总监不能看到客户
			if (isRole(Const.ROLE_OPERATION_DIRECTOR) || isRole(Const.ROLE_OPERATION)) {
				if (!"1".equals(userId)) {
					pd.put("noCustomer", 1);
				}
			}*/

			// 其余的目前都能看到我的客户
			String pageNo = pd.getString("pageNo");
			String pageSize = pd.getString("pageSize");
			int pNo = TextUtil.isNull(pageNo) ? 1 : Integer.parseInt(pageNo);
			int pSize = TextUtil.isNull(pageSize) ? 5 : Integer.parseInt(pageSize);
			PageHelper.startPage(pNo, pSize); // 核心分页代码
			page = new PageInfo(workBenchService.customerList(pd));// 列出客户列表
			error = "00";
			msg = "查询成功";
		} catch (Exception e) {
			e.printStackTrace();
			error = "500";
			msg = "接口错误：" + e.getMessage();
		} finally {
			map.put("error", error);
			map.put("msg", msg);
			map.put("isSale", isSale);
			map.put("isOperation", isOperation);
			map.put("result", page);
		}
		return AppUtil.returnObject(new PageData(), map);
	}

	/**
	 * 获取我的联系人列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/linkManList")
	@ResponseBody
	public Object linkManList() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = null;
		String error = null;
		PageInfo<List<PageData>> page = null;
		boolean isSale = false;// 是否销售
		boolean isOperation = false;// 是否运作
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

			// if (!"1".equals(userId)) {
			// pd.put("userId", userId);
			// }
			User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
			String userId = user.getUSER_ID();
			if (isRole(Const.ROLE_SALES_ELITE)) {
				if (!"1".equals(userId)) {
					pd.put("saleId", userId);
				}
			}

			// 销售总监能看到销售精英的客户
			if (isRole(Const.ROLE_SALES_DIRECTOR)) {
				if (!"1".equals(userId)) {
					pd.put("isSaleDirector", 1);
				}
			}

			String pageNo = pd.getString("pageNo");
			String pageSize = pd.getString("pageSize");
			int pNo = TextUtil.isNull(pageNo) ? 1 : Integer.parseInt(pageNo);
			int pSize = TextUtil.isNull(pageSize) ? 5 : Integer.parseInt(pageSize);
			PageHelper.startPage(pNo, pSize); // 核心分页代码
			page = new PageInfo(workBenchService.linkManList(pd));// 列出联系人列表
			error = "00";
			msg = "查询成功";
		} catch (Exception e) {
			e.printStackTrace();
			error = "500";
			msg = "接口错误：" + e.getMessage();
		} finally {
			map.put("error", error);
			map.put("msg", msg);
			map.put("isSale", isSale);
			map.put("isOperation", isOperation);
			map.put("result", page);
		}
		return AppUtil.returnObject(new PageData(), map);
	}

	/**
	 * 获取我的案件列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/orderList")
	@ResponseBody
	public Object orderList() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = null;
		String error = null;
		PageInfo<List<PageData>> page = null;
		boolean isSale = false;// 是否销售
		boolean isOperation = false;// 是否运作
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
			// 销售能看到自己的所有运作中的案件
			if (isRole(Const.ROLE_SALES_ELITE)) {
				if (!"1".equals(userId)) {
					pd.put("saleId", userId);
				}
			}

			// 销售总监能看到销售精英的案件
			if (isRole(Const.ROLE_SALES_DIRECTOR)) {
				if (!"1".equals(userId)) {
					pd.put("isSaleDirector", 1);
				}
			}

			// 公司内部律师(诉讼/仲裁)
			if (isRole(Const.ROLE_IN_ATTORNEY)) {
				if (!"1".equals(userId)) {
					pd.put("lawerId", userId);
				}
			}

			if (isRole(Const.ROLE_OPERATION)) {
				if (!"1".equals(userId)) {
					pd.put("runnerId", userId);
				}
			}

			// 运作总监
			if (isRole(Const.ROLE_OPERATION_DIRECTOR)) {
				if (!"1".equals(userId)) {
					pd.put("isRunDirector", 1);
				}
			}

			// 其余的多能看到

			String pageNo = pd.getString("pageNo");
			String pageSize = pd.getString("pageSize");
			int pNo = TextUtil.isNull(pageNo) ? 1 : Integer.parseInt(pageNo);
			int pSize = TextUtil.isNull(pageSize) ? 5 : Integer.parseInt(pageSize);
			PageHelper.startPage(pNo, pSize); // 核心分页代码
			page = new PageInfo(workBenchService.orderList(pd));// 列出联系人列表
			error = "00";
			msg = "查询成功";
		} catch (Exception e) {
			e.printStackTrace();
			error = "500";
			msg = "接口错误：" + e.getMessage();
		} finally {
			map.put("error", error);
			map.put("msg", msg);
			map.put("isSale", isSale);
			map.put("isOperation", isOperation);
			map.put("result", page);
		}
		return AppUtil.returnObject(new PageData(), map);
	}

	/**
	 * 获取我的审核
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mysh")
	@ResponseBody
	public Object mysh() throws Exception {
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

			User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
			String userId = user.getUSER_ID();
			// 运作总监
			if (isRole(Const.ROLE_OPERATION_DIRECTOR)) {
				if (!"1".equals(userId)) {
					pd.put("flag", "1");
					pd.put("userId", userId);
				}
			}

			// 销售总监
			if (isRole(Const.ROLE_SALES_DIRECTOR)) {
				if (!"1".equals(userId)) {
					pd.put("flag", "2");
					pd.put("userId", userId);
				}
			}

			if (isRole(Const.ROLE_MANAGER)) {
				pd.put("flag", "3");
			}

			String pageNo = pd.getString("pageNo");
			String pageSize = pd.getString("pageSize");
			int pNo = TextUtil.isNull(pageNo) ? 1 : Integer.parseInt(pageNo);
			int pSize = TextUtil.isNull(pageSize) ? 5 : Integer.parseInt(pageSize);
			PageHelper.startPage(pNo, pSize); // 核心分页代码
			page = new PageInfo(workBenchService.shList(pd));// 列出联系人列表
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
	 * 获取我的任务列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/*/myTaskList")
	public ModelAndView myTaskList(Page page) throws Exception {
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

			boolean isSale = false;// 是否销售
			boolean isOperation = false;// 是否运作
			User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
			String userId = user.getUSER_ID();
			/*	if (!"1".equals(userId)) {
					pd.put("userId", userId);
					// 判断当前用户是否为销售
					boolean flag = isRole(Const.ROLE_SALES_ELITE);
					if (flag) {
						// if (!"1".equals(userId)) {
						pd.put("type", 1); // 销售只能看到外访任务，运作的都能看到
						// }
						isSale = true;
						System.out.println("是销售精英");
					} else {
						// 判断当前用户是否为运作
						flag = isRole(Const.ROLE_OPERATION);
						if (flag) {
							// if (!"1".equals(userId)) {
							isOperation = true;
							// }
							System.out.println("是运作");
						}
					}
				} else {
					isSale = true;
					isOperation = true;
				}*/
			// 客服 能看到待开发票
			if (isRole(Const.ROLE_CUSTOMER_SERVICE)) {
				if (!"1".equals(userId)) {
					pd.put("flag", "0");
					pd.put("doId", userId);
				}
			}

			// 运作精英、销售精英
			if (isRole(Const.ROLE_OPERATION) || isRole(Const.ROLE_SALES_ELITE) || isRole(Const.ROLE_PHONE_SALES)) {
				if (!"1".equals(userId)) {
					pd.put("flag", "1");
					pd.put("doId", userId);
				}
			}

			// 运作总监
			if (isRole(Const.ROLE_OPERATION_DIRECTOR)) {
				if (!"1".equals(userId)) {
					pd.put("flag", "2");
					pd.put("doId", userId);

				}
			}
			// 销售总监
			if (isRole(Const.ROLE_SALES_DIRECTOR)) {
				if (!"1".equals(userId)) {
					pd.put("flag", "3");
					pd.put("doId", userId);
				}
			}

			if (isRole(Const.ROLE_MANAGER)) {
				pd.put("flag", "5");
			}

			// 公司内部律师(诉讼/仲裁)
			if (isRole(Const.ROLE_IN_ATTORNEY)) {
				if (!"1".equals(userId)) {
					pd.put("flag", "4");
					pd.put("doId", userId);
				}
			}

			// 运作和运作总监不能看到我的客户
			if (isRole(Const.ROLE_OPERATION_DIRECTOR) || isRole(Const.ROLE_OPERATION) || isRole(Const.ROLE_IN_ATTORNEY)) {
				if (!"1".equals(userId)) {
					pd.put("noCustomer", 1);
				}
			}

			if (isRole(Const.ROLE_OPERATION)) {
				if (!"1".equals(userId)) {
					pd.put("flags", "6");
					pd.put("doId", userId);
				}
			}

			if (isRole(Const.ROLE_SALES_ELITE)) {
				if (!"1".equals(userId)) {
					pd.put("flags", "7");
					pd.put("doId", userId);
				}
			}

			page.setPd(pd);
			List<PageData> list = workBenchService.taskListPage(page); // 列出我的任务列表
			mv.addObject("dataList", list);
			pd.put("isSale", isSale);
			pd.put("isOperation", isOperation);
			mv.addObject("pd", pd);
			mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
			if (TextUtil.isNotNull(pd.getString("myIndex"))) {
				mv.setViewName("business/workBenchManage/index");
			} else {
				mv.setViewName("business/workBenchManage/task_list");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	/**
	 * 获取我的客户列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/myCustomerList")
	public ModelAndView myCustomerList(Page page) throws Exception {
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
			boolean isSale = false;// 是否销售
			boolean isOperation = false;// 是否运作
			User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
			String userId = user.getUSER_ID();
			if ("1".equals(userId)) {
				isSale = true;
				isOperation = true;
			} else {
				// 判断当前用户是否为销售
				boolean flag = isRole(Const.ROLE_SALES_ELITE);
				if (flag) {
					if (!"1".equals(userId)) {
						pd.put("saleId", userId);
						isSale = true;
					}
					System.out.println("是销售精英");
				} else {
					// 判断当前用户是否为运作
					flag = isRole(Const.ROLE_OPERATION);
					if (flag) {
						// if (!"1".equals(userId)) {
						pd.put("runnerId", userId);
						isOperation = true;
						// }
						System.out.println("是运作");
					}
				}
			}

			// 销售总监能看到销售精英的客户
			if (isRole(Const.ROLE_SALES_DIRECTOR)) {
				if (!"1".equals(userId)) {
					pd.put("isSaleDirector", 1);
				}
			}
			page.setPd(pd);
			List<PageData> list = workBenchService.customerListPage(page); // 列出我的客户列表
			mv.addObject("dataList", list);
			pd.put("isSale", isSale);
			pd.put("isOperation", isOperation);
			mv.addObject("pd", pd);
			mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
			mv.setViewName("business/workBenchManage/customer_list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	/**
	 * 获取我的联系人列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/myLinkManList")
	public ModelAndView myLinkManList(Page page) throws Exception {
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

			boolean isSale = false;// 是否销售
			boolean isOperation = false;// 是否运作
			User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
			String userId = user.getUSER_ID();
			if ("1".equals(userId)) {
				isSale = true;
				isOperation = true;
			} else {
				// 判断当前用户是否为销售
				boolean flag = isRole(Const.ROLE_SALES_ELITE);
				if (flag) {
					if (!"1".equals(userId)) {
						pd.put("saleId", userId);
						isSale = true;
					}
					System.out.println("是销售精英");
				} else {
					// 判断当前用户是否为运作
					flag = isRole(Const.ROLE_OPERATION);
					if (flag) {
						// if (!"1".equals(userId)) {
						pd.put("runnerId", userId);
						isOperation = true;
						// }
						System.out.println("是运作");
					}
				}
			}
			page.setPd(pd);
			List<PageData> list = workBenchService.linkManListPage(page); // 列出我的联系人列表
			mv.addObject("dataList", list);
			pd.put("isSale", isSale);
			pd.put("isOperation", isOperation);
			mv.addObject("pd", pd);
			mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
			mv.setViewName("business/workBenchManage/linkman_list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	/**
	 * 获取我的案件列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/*/myOrderList")
	public ModelAndView myOrderList(Page page) throws Exception {
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

			boolean isSale = false;// 是否销售
			boolean isOperation = false;// 是否运作
			boolean isOperationDirector = false;// 是否运作总监
			User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
			String userId = user.getUSER_ID();
			/*if ("1".equals(userId)) {
				isSale = true;
				isOperation = true;
			} else {
				// 判断当前用户是否为销售
				isSale = isRole(Const.ROLE_SALES_ELITE);
				if (isSale) {
					// if (!"1".equals(userId)) {
					pd.put("saleId", userId);
					// }
					System.out.println("是销售精英");
				}
				// 判断当前用户是否为运作
				isOperation = isRole(Const.ROLE_OPERATION);
				if (isOperation) {
					// if (!"1".equals(userId)) {
					pd.put("runnerId", userId);
					// }
					System.out.println("是运作");
				}
				isOperationDirector = isRole(Const.ROLE_OPERATION_DIRECTOR);
				if (isOperationDirector) {
					pd.put("status", 0);// 运作总监能看到所有运作中的
					System.out.println("是运作总监");
				}
			}*/
			if (isRole(Const.ROLE_SALES_ELITE)) {
				if (!"1".equals(userId)) {
					pd.put("saleId", userId);
				}
			}

			// 销售总监能看到销售精英的案件
			if (isRole(Const.ROLE_SALES_DIRECTOR)) {
				if (!"1".equals(userId)) {
					pd.put("isSaleDirector", 1);
				}
			}

			if (isRole(Const.ROLE_IN_ATTORNEY)) {
				if (!"1".equals(userId)) {
					pd.put("lawerId", userId);
				}
			}

			if (isRole(Const.ROLE_OPERATION)) {
				if (!"1".equals(userId)) {
					pd.put("runnerId", userId);
				}
			}

			// 销售总监能看到销售精英的案件
			if (isRole(Const.ROLE_OPERATION_DIRECTOR)) {
				if (!"1".equals(userId)) {
					pd.put("isRunDirector", 1);
				}
			}

			if (isRole(Const.ROLE_CUSTOMER_SERVICE)) {
				if (!"1".equals(userId)) {
					pd.put("isKefu", userId);
				}
			}
			pd.put("action", this.getRequest().getServletPath());
			// System.out.println(">>>>" + this.getRequest().getServletPath());
			page.setPd(pd);
			List<PageData> list = workBenchService.orderListPage(page); // 列出我的案件列表
			List<PageData> listAllPage = workBenchService.listAll(pd);
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
			mv.addObject("dataList", list);
			pd.put("isSale", isSale);
			pd.put("isOperation", isOperation);
			pd.put("isOperationDirector", isOperationDirector);
			mv.addObject("pd", pd);
			mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
			if (TextUtil.isNotNull(pd.getString("s"))) {
				mv.setViewName("business/workBenchManage/case_list");
			} else {
				mv.setViewName("business/workBenchManage/order_list");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	/**
	 * 查看详情
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/views")
	public ModelAndView views() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		if (TextUtil.isNotNull(pd.getString("id"))) {
			String fqr = pd.getString("fqr");
			pd = workBenchService.findWarnById(pd);
			if (TextUtil.isNull(pd.getString("fqr"))) {
				pd.put("fqr", fqr);
			}
			User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
			if (user.getUSER_ID().equals(pd.getString("doId"))) {
				pd.put("mark", "1");
			}

			if ("申请外访".equals(pd.getString("type"))) {
				mv.setViewName("business/workBenchManage/task_view_type3");
			}
			if ("指派外访".equals(pd.getString("type"))) {
				mv.setViewName("business/workBenchManage/task_view_type2");
			}
			if ("待开发票".equals(pd.getString("type"))) {
				PageData pd1 = new PageData();
				pd1.put("id", pd.get("paymentId"));
				pd1 = invoiceService.findById(pd1);
				pd1.put("sy_workwench", 1);
				mv.setViewName("business/workBenchManage/task_view_type1");
				mv.addObject("pd1", pd1);
			}
			if ("待发报告".equals(pd.getString("type"))) {
				mv.setViewName("business/workBenchManage/index_orderreport_view");
			}
			mv.addObject("pd", pd);
		}
		return mv;
	}

	@RequestMapping(value = "/views1")
	public ModelAndView views1() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		if (TextUtil.isNotNull(pd.getString("id"))) {
			pd = workBenchService.findWarnById1(pd);
			mv.setViewName("business/workBenchManage/index_sh_orderreport_view");
			mv.addObject("pd", pd);
		}
		return mv;
	}

	/**
	 * 去任务执行页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goTask")
	public ModelAndView goTask() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String type = pd.getString("type");
		if ("0".equals(type)) {
			pd.put("id", pd.getString("taskId"));
			pd = workBenchService.findTaskById(pd); // 1外访
		}
		mv.addObject("pd", pd);
		mv.addObject("msg", "doTask");
		mv.setViewName("business/workBenchManage/do_task");
		return mv;
	}

	/**
	 * 执行任务
	 * 
	 * @return
	 */
	@RequestMapping(value = "/doTask")
	public ModelAndView doTask() throws Exception {
		String currenTime = DateUtil.getCurrentTime();
		logBefore(logger, Jurisdiction.getUsername() + "执行任务");
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		int id = Integer.parseInt(pd.getString("id"));
		if (id > 0) {
			pd.put("id", id);
			pd.put("remark", TextUtil.transNull(pd.getString("remark")).trim());
			pd.put("editTime", currenTime);// 修改时间
			workBenchService.doTask(pd); // 执行修改

			if (TextUtil.isNotNull(pd.getString("visitId"))) {
				workBenchService.updateVisit(pd);
			}
		}
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 去我的案件任务执行页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goOrderTask")
	public ModelAndView goOrderTask() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		List<PageData> list = null;
		String type = pd.getString("type");
		String url = "";
		// pd.put("applyType", "5".equals(type) ? "0" : "1");
		list = workBenchService.findTaskByOrderId(pd);
		// 新增时防止把不相关的信息带到页面上
		if (null == pd.get("id")) {
			if (!"8".equals(type)) {
				list = new ArrayList<PageData>();
			}
		}
		if ("1".equals(type)) {
			// if (TextUtil.isNull(pd.getString("id"))) {
			// pd.put("flag", "1");// 查询本月数据
			// }
			// List<PageData> currentMonthList = workBenchService.findTaskByOrderId(pd);
			// mv.addObject("currentMonthData", currentMonthList);
			//
			// if (null != currentMonthList && currentMonthList.size() > 0) {
			// PageData attachPd = new PageData();
			// attachPd.put("relateId", currentMonthList.get(0).get("id").toString());
			// attachPd.put("type", 4);
			// List<PageData> attachs = attachService.findAttachByRelateId(attachPd);
			// pd.put("attachs", attachs);
			// }

			for (PageData tmp : list) {
				PageData attachPd = new PageData();
				attachPd.put("orderId", tmp.get("orderId").toString());
				attachPd.put("relateId", tmp.get("id").toString());
				attachPd.put("type", 4);
				List<PageData> attachs = attachService.findAttachByRelateId(attachPd);
				tmp.put("attachs", attachs);
			}

			User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
			String userId = user.getUSER_ID();
			/*if ("1".equals(userId)) {
				pd.put("isOperation", true);
				pd.put("isOperationDirector", true);
			} else {
				// 判断当前用户是否为运作
				boolean isOperation = isRole(Const.ROLE_OPERATION);
				boolean isOperationDirector = isRole(Const.ROLE_OPERATION_DIRECTOR);
				pd.put("isOperation", isOperation);
				pd.put("isOperationDirector", isOperationDirector);
			}*/
			if ("1".equals(userId)) {
				pd.put("isOperation", true);
				pd.put("isOperationDirector", true);
			} else {
				if (isRole(Const.ROLE_OPERATION) || isRole(Const.ROLE_CUSTOMER_SERVICE)) {
					pd.put("isOperation", true);
				} else if (isRole(Const.ROLE_OPERATION_DIRECTOR) || isRole(Const.ROLE_MANAGER)) {
					pd.put("isOperation", true);
					pd.put("isOperationDirector", true);
				}
			}
			url = "business/workBenchManage/orderreport"; // 1案件报告
		} else if ("2".equals(type)) {
			url = "business/workBenchManage/debturgereport";// 2欠款催收
		} else if ("3".equals(type)) {
			if (null != list && list.size() > 0) {
				PageData attachPd = new PageData();
				attachPd.put("orderId", list.get(0).get("orderId").toString());
				attachPd.put("relateId", list.get(0).get("id").toString());
				attachPd.put("type", 5);
				List<PageData> attachs = attachService.findAttachByRelateId(attachPd);
				pd.put("attachs", attachs);
			}
			url = "business/workBenchManage/lawyerletter";// 3律师函
		} else if ("4".equals(type)) {
			boolean hasDetail = false;// 是否有回款明细
			for (PageData tmp : list) {
				if (null != tmp.get("currentPeriods")) {
					hasDetail = true;
					pd.put("hasDetail", hasDetail);
					break;
				}
			}
			url = "business/workBenchManage/paymentplan";// 4回款计划
		} else if ("5".equals(type) || "6".equals(type)) {
			url = "business/workBenchManage/lawsuitapply";// 5、6申请诉讼、仲裁
			// url = "business/workBenchManage/handover_edit";// 5、6申请诉讼、仲裁
		} else if ("7".equals(type)) {
			Page page = getPage();
			page.setPd(pd);
			page.setShowCount(500);
			List<PageData> planList = workBenchService.paymentplanList(page); // 列出还款计划列表
			pd.put("planList", planList);
			url = "business/workBenchManage/paymentdetail";// 7回款明细
		} else if ("8".equals(type)) {
			PageData pd1 = new PageData();
			if (list.size() > 0) {
				//
				String status = list.get(0).get("status").toString();
				String cType = list.get(0).get("cType").toString();
				pd1.put("status", status);
				pd1.put("cType", cType);

				// 运作
				if ("0".equals(status)) {
					if ("0".equals(cType)) {

						String commissionRate = new BigDecimal(Double.valueOf(list.get(0).get("commissionRate").toString()))
								.multiply(new BigDecimal(Double.valueOf(list.get(0).get("discount").toString()))).divide(new BigDecimal(100)).toString();
						/*String commissionaMount = new BigDecimal(Double.valueOf(list.get(0).get("commissionRate").toString()))
								.multiply(new BigDecimal(Double.valueOf(list.get(0).get("discount").toString()))).divide(new BigDecimal(10000))
								.multiply(new BigDecimal(Double.valueOf(list.get(0).get("debtAmount").toString()))).toString();*/
						pd1.put("commissionRate", subZeroAndDot(commissionRate)); // 佣金比例
						/*pd1.put("commissionaMount", subZeroAndDot(commissionaMount));*/// 佣金金额
					}
					if ("1".equals(cType)) {
						pd1.put("commissionaMount", subZeroAndDot(list.get(0).get("fixedMoney").toString()));
					}
				}
				// 诉讼或仲裁
				if ("3".equals(status) || "4".equals(status)) {
					if ("0".equals(cType)) {
						// 佣金比例
						String commissionRate = new BigDecimal(Double.valueOf(list.get(0).get("lawCommissionRate").toString()))
								.multiply(new BigDecimal(Double.valueOf(list.get(0).get("susongdiscount").toString()))).divide(new BigDecimal(100)).toString();
						/*String commissionaMount = new BigDecimal(Double.valueOf(list.get(0).get("lawCommissionRate").toString()))
								.multiply(new BigDecimal(Double.valueOf(list.get(0).get("susongdiscount").toString()))).divide(new BigDecimal(10000))
								.multiply(new BigDecimal(Double.valueOf(list.get(0).get("debtAmount").toString()))).toString();*/
						pd1.put("commissionRate", subZeroAndDot(commissionRate)); // 佣金比例
						/*pd1.put("commissionaMount", subZeroAndDot(commissionaMount)); // 佣金金额
						*/}
					if ("1".equals(cType)) {
						pd1.put("commissionaMount", subZeroAndDot(list.get(0).get("fixedMoney").toString())); // 佣金金额
					}
				}
			}
			mv.addObject("pd1", pd1);
			url = "business/workBenchManage/proximoPaymentdetail";// 8下月回款预估
		}
		mv.addObject("pd", pd);
		mv.addObject("dataList", list);
		mv.addObject("msg", "doOrderTask");
		mv.setViewName(url);
		return mv;
	}

	private static String subZeroAndDot(String s) {
		if (s.indexOf(".") > 0) {
			s = s.replaceAll("0+?$", "");// 去掉多余的0
			s = s.replaceAll("[.]$", "");// 如最后一位是.则去掉
		}
		return s;
	}

	/**
	 * 根据时间获取下月汇款金额
	 * 
	 * 
	 */
	@RequestMapping(value = "/getHkje")
	@ResponseBody
	public Object getHkje() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String error = "";
		String msg = "";
		String result = null;
		try {
			PageData pd = this.getPageData();
			PageData nextMoney = workBenchService.findNextMoney(pd);
			if (null != nextMoney) {
				result = nextMoney.get("onceMoney").toString();
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
	 * 执行我的案件任务
	 * 
	 * @return
	 */
	@RequestMapping(value = "/doOrderTask")
	public ModelAndView doOrderTask() throws Exception {
		String currenTime = DateUtil.getCurrentTime();
		logBefore(logger, Jurisdiction.getUsername() + "执行我的案件任务");
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String type = pd.getString("type");
		String orderId = pd.getString("orderId");
		if ("1".equals(type)) {
			// 1案件报告
			User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
			if ("2".equals(pd.getString("status"))) {
				pd.put("aduiterId", user.getUSER_ID()); // 审核人id
			}
			pd.put("userId", user.getUSER_ID());
			workBenchService.doOrderTask(pd);
			String relateId = pd.get("id").toString();
			String[] fileName = null;// 附件名称
			String filePath = "";// 拼接附件地址以逗号隔开
			// 保存附件信息，定义一个map装对象
			List<String> list2 = new ArrayList<String>();
			if (!"0".equals(relateId)) {
				PageData attachPd = new PageData();
				attachPd.put("orderId", orderId);
				attachPd.put("relateId", relateId);
				attachPd.put("type", 4);
				List<PageData> attachs = attachService.findAttachByRelateId(attachPd);
				for (PageData attach : attachs) {
					list2.add(attach.get("id").toString());
				}
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
				fileName = new String[fileSize.length];
				for (int i = 0; i < fileSize.length; i++) {
					PageData map = new PageData();
					map.put("fileSize", fileSize[i]);
					map.put("realPath", realPath[i]);
					map.put("originalFilename", originalFilename[i]);
					map.put("url", url[i]);
					map.put("uploadTime", uploadTime[i]);
					map.put("uploader", uploader[i]);
					map.put("type", 4);
					map.put("orderId", orderId);
					map.put("relateId", pd.getString("id"));
					map.put("id", attachId[i]);
					if (!"0".equals(attachId[i])) {
						list1.add(attachId[i]);
					} else {
						attachService.save(map);
					}
					fileName[i] = originalFilename[i];
					filePath += realPath[i] + ",";
				}
				list2.removeAll(list1);
				System.out.println(list2);
				if (list2.size() > 0) {
					attachService.delete(list2);
				}
			} else {
				// 删除他原有的相关附件
				if (list2.size() > 0) {
					attachService.delete(list2);
				}
			}
			// 审核通过发送邮件给债权人(邮件地址在填写案件报告时手动填写)
			/*	String status = pd.getString("status");
				if ("2".equals(status)) {
					String email = pd.getString("email");
					if (TextUtil.isNull(email)) {
						// 如果没有填写债权人邮件，根据案件信息获取债权人的邮箱地址
						if (TextUtil.isNotNull(orderId)) {
							PageData orderPd = new PageData();
							orderPd.put("id", orderId);
							orderPd = orderService.findById(orderPd);
							if (null != orderPd.get("customerId")) {
								PageData customerPd = new PageData();
								customerPd.put("id", orderPd.get("customerId").toString());
								customerPd = customerService.findById(customerPd);
								email = customerPd.getString("email");
							}
						}
					}
					// 发送邮件
					if (TextUtil.isNotNull(email)) {
						try {
							SimpleMailSender sms = new SimpleMailSender();
							sms.sendMail(pd.getString("title"), pd.getString("remark"), "2", email, fileName, filePath);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}*/
			String status = pd.getString("status");
			if ("2".equals(status)) {
				// 发起人写报告的人 //执行人客服
				pd.put("userId", user.getUSER_ID());
				PageData serivice = expressService.findServiceId();
				pd.put("doId", serivice.get("userId")); // 客服执行人
				workBenchService.saveWarnTask1(pd);

				PageData pd1 = new PageData();
				pd1.put("orderId", pd.get("orderId"));
				pd1.put("status", pd.get("reportType"));
				pd1.put("markF", 1);
				lawsuitauditService.updateOrder(pd1);
			}
			if (!"2".equals(status) && !"3".equals(status)) {
				mv.addObject("action", "parent");
			}
		} else if ("2".equals(type)) {
			// 2欠款催收
			workBenchService.doOrderTask(pd);
		} else if ("3".equals(type)) {
			// 3律师函
			User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
			pd.put("userId", user.getUSER_ID());
			workBenchService.doOrderTask(pd);
			String relateId = pd.get("id").toString();
			// 保存附件信息，定义一个map装对象
			List<String> list2 = new ArrayList<String>();
			if (!"0".equals(relateId)) {
				PageData attachPd = new PageData();
				attachPd.put("orderId", orderId);
				attachPd.put("relateId", relateId);
				attachPd.put("type", 5);
				List<PageData> attachs = attachService.findAttachByRelateId(attachPd);
				for (PageData attach : attachs) {
					list2.add(attach.get("id").toString());
				}
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
					map.put("type", 5);
					map.put("orderId", orderId);
					map.put("relateId", pd.getString("id"));
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
				// 删除他原有的相关附件
				if (list2.size() > 0) {
					attachService.delete(list2);
				}
			}
		} else if ("4".equals(type)) {
			// 4回款计划
			User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
			pd.put("userId", user.getUSER_ID());
			pd.put("remark1", "有一笔回款计划");
			String planTime = pd.getString("planTime");// 计划开始还款时间
			if (TextUtil.isNotNull(pd.getString("periods"))) {
				int periods = Integer.parseInt(pd.getString("periods"));// 还款期数
				if (periods > 0) {
					// 首先删除原来的计划和回款明细，然后重新生成
					workBenchService.deletePaymentplan(pd);
					workBenchService.deletePaymentdetail(pd);
					pd.put("planTime", planTime);
					pd.put("periods", 1);
					workBenchService.doOrderTask(pd);
					for (int i = 2; i <= periods; i++) {
						planTime = DateUtil.getDateAddMonth(planTime, 1, "yyyy-MM-dd");
						pd.put("planTime", planTime);
						pd.put("periods", i);
						workBenchService.saveCallinfo(pd);
						workBenchService.doOrderTask(pd);
					}
				}
			} else {
				pd.put("periods", null);
				workBenchService.doOrderTask(pd);
				workBenchService.saveCallinfo(pd);
			}
			mv.addObject("action", "parent");
		} else if ("5".equals(type) || "6".equals(type)) {
			// 5,6申请诉讼、仲裁
			// pd.put("applyType", "5".equals(type) ? "0" : "1");
			User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
			pd.put("applyerId", user.getUSER_ID());
			workBenchService.doOrderTask(pd);
		} else if ("7".equals(type)) {
			// 7回款明细
			if (TextUtil.isNotNull(pd.getString("id"))) {
				workBenchService.doOrderTask(pd);
			} else {
				workBenchService.doOrderTask(pd);
				// 生成发票信息(待开票)
				System.out.println("保存后的ID：" + pd.get("id") + "==========================" + pd);
				if (null != pd.get("id") && null != pd.get("orderId")) {
					PageData invoicePd = new PageData();
					invoicePd.put("id", pd.get("orderId").toString());
					invoicePd = orderService.findById(invoicePd);
					invoicePd.put("relateId", Integer.parseInt(pd.get("id").toString()));
					invoicePd.remove("id");
					invoiceService.saveOrUpdate(invoicePd);
					// 生成任务
					User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
					invoicePd.put("userId", user.getUSER_ID());
					PageData serivice = expressService.findServiceId();
					invoicePd.put("doId", serivice.get("userId")); // 客服执行人
					workBenchService.saveWarnTask(invoicePd);
				}
			}
			mv.addObject("action", "parent");
		} else if ("8".equals(type)) {
			// 7回款明细
			workBenchService.doOrderTask(pd);
		}
		// 设任务为已完成
		String taskId = pd.getString("taskId");
		if (TextUtil.isNotNull(taskId)) {
			int id = Integer.parseInt(taskId);
			if (id > 0) {
				pd.put("id", id);
				pd.put("remark", TextUtil.transNull(pd.getString("remark")).trim());
				pd.put("realTime", currenTime);// 执行时间
				if ("1".equals(type)) {
					// 如果是案件报告，提交后才能算完成任务
					if ("1".equals(pd.getString("status")) || "2".equals(pd.getString("status"))) {
						pd.put("isCompleted", 1);// 已完成
					}
				} else {
					pd.put("isCompleted", 1);// 已完成
				}
				pd.put("editTime", currenTime);// 修改时间
				workBenchService.doTask(pd); // 执行修改
			}
		}
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/savePaymentPlan")
	@ResponseBody
	public Object savePaymentPlan() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String error = "";
		String msg = "";
		PageData result = null;
		try {
			PageData pd = this.getPageData();
			pd.put("type", 4);
			User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
			pd.put("userId", user.getUSER_ID());
			pd.put("remark1", "有一笔回款计划");
			String planTime = pd.getString("planTime");// 计划开始还款时间
			if ("0".equals(pd.getString("types"))) {
				int periods = Integer.parseInt(pd.getString("periods"));// 还款期数
				if (periods > 0) {
					// 首先删除原来的计划和回款明细，然后重新生成
					workBenchService.deletePaymentplan(pd);
					// workBenchService.deletePaymentdetail(pd);
					pd.put("planTime", planTime + " 09:30:00");
					pd.put("periods", 1);
					workBenchService.saveCallinfo(pd);
					workBenchService.doOrderTask(pd);
					for (int i = 2; i <= periods; i++) {
						planTime = DateUtil.getDateAddMonth(planTime, 1, "yyyy-MM-dd");
						pd.put("planTime", planTime + " 09:30:00");
						pd.put("periods", i);
						workBenchService.saveCallinfo(pd);
						workBenchService.doOrderTask(pd);
					}
				}
			} else {
				pd.put("periods", null);
				workBenchService.doOrderTask(pd);
				List<PageData> list1 = new ArrayList<PageData>();
				String[] times = pd.getString("time").split(",");
				String[] moneys = pd.getString("money").split(",");
				if (times.length == moneys.length) {
					for (int i = 0; i < times.length; i++) {
						PageData map1 = new PageData();
						map1.put("time", times[i]);
						map1.put("money", moneys[i]);
						map1.put("remark", pd.getString("remark"));
						map1.put("paymentId", pd.get("id").toString());
						pd.put("planTime", times[i] + " 09:30:00");
						workBenchService.saveCallinfo(pd);
						list1.add(map1);
					}
				}
				workBenchService.saveLinks(list1);
			}
			error = "00";
			msg = "新增成功";
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
	 * 修改
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updatePaymentPlan")
	@ResponseBody
	public Object updatePaymentPlan() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String error = "";
		String msg = "";
		PageData result = null;
		try {
			PageData pd = this.getPageData();
			if (TextUtil.isNotNull(pd.getString("id")) || TextUtil.isNotNull(pd.getString("subId"))) {
				workBenchService.updatePaymentPlan(pd);
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
	 * 展示还款计划列表
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/choosePaymentplan")
	public ModelAndView choosePaymentplan(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		try {
			PageData pd = this.getPageData();
			page.setPd(pd);
			page.setShowCount(500);
			List<PageData> cusList = workBenchService.paymentplanList(page); // 列出还款计划列表
			mv.setViewName("business/workBenchManage/choosePaymentplan_list");
			mv.addObject("cusList", cusList);
			mv.addObject("pd", pd);
			mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	/**
	 * 展示案件报告列表(客服、经理、管理员)
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/orderReportList")
	public ModelAndView orderReportList(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		try {
			PageData pd = this.getPageData();
			page.setPd(pd);
			List<PageData> cusList = workBenchService.orderReportList(page); // 列出还款计划列表
			mv.setViewName("business/workBenchManage/orderReport_list");
			mv.addObject("cusList", cusList);
			mv.addObject("pd", pd);
			mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	/**
	 * 展示案件报告列表(客服、经理、管理员)
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/xyHktj")
	public ModelAndView xyHktj(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		try {
			PageData pd = this.getPageData();
			page.setPd(pd);
			List<PageData> cusList = workBenchService.xyhkList(page); // 列出预估列表
			/*List<PageData> cusList1 = workBenchService.xyhkList1(page); // 列出还款计划列表
			// 找出重复的id
			List<String> list1 = new ArrayList<String>();
			List<String> list2 = new ArrayList<String>();
			for (PageData cus : cusList) {
				list1.add(cus.get("id").toString());
			}
			for (PageData cus : cusList1) {
				list2.add(cus.get("id").toString());
			}
			list1.retainAll(list2); // 求交集
			if (list1.size() > 0) {
				for (int i = 0; i < cusList1.size(); i++) {
					for (int j = 0; j < list1.size(); j++) {
						if (cusList1.get(i).get("id").toString().equals(list1.get(j))) {
							cusList1.remove(i);
							break;
						}
					}
				}
			}
			cusList.addAll(cusList1);*/
			BigDecimal hkzje = new BigDecimal(0.0);
			BigDecimal yjzje = new BigDecimal(0.0);
			for (PageData cus : cusList) {
				hkzje = hkzje.add(new BigDecimal(Double.valueOf(cus.get("money").toString())));
				yjzje = yjzje.add(new BigDecimal(Double.valueOf(cus.get("commissionaMount").toString())));
			}
			pd.put("hkzje", hkzje.setScale(2, BigDecimal.ROUND_HALF_UP));
			pd.put("yjzje", yjzje.setScale(2, BigDecimal.ROUND_HALF_UP));

			mv.setViewName("business/workBenchManage/xyhk_list");
			mv.addObject("cusList", cusList);
			mv.addObject("pd", pd);
			mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	/**
	 * 展示案件报告列表(客服、经理、管理员)
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/ajHktj")
	public ModelAndView ajHktj(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		try {
			PageData pd = this.getPageData();
			page.setPd(pd);
			List<PageData> cusList = workBenchService.ajhkList(page); // 列出预估列表
			/*List<PageData> cusList1 = workBenchService.xyhkList1(page); // 列出还款计划列表
			// 找出重复的id
			List<String> list1 = new ArrayList<String>();
			List<String> list2 = new ArrayList<String>();
			for (PageData cus : cusList) {
				list1.add(cus.get("id").toString());
			}
			for (PageData cus : cusList1) {
				list2.add(cus.get("id").toString());
			}
			list1.retainAll(list2); // 求交集
			if (list1.size() > 0) {
				for (int i = 0; i < cusList1.size(); i++) {
					for (int j = 0; j < list1.size(); j++) {
						if (cusList1.get(i).get("id").toString().equals(list1.get(j))) {
							cusList1.remove(i);
							break;
						}
					}
				}
			}
			cusList.addAll(cusList1);*/
			List<PageData> cusList1 = workBenchService.ajhkList1(pd);
			BigDecimal hkzje = new BigDecimal(0.0);
			BigDecimal yjzje = new BigDecimal(0.0);
			for (PageData cus : cusList1) {
				Object money = cus.get("money");
				if (null == money) {
					money = "0";
				}
				hkzje = hkzje.add(new BigDecimal(Double.valueOf(money.toString())));
				Object money1 = cus.get("commissionaMount");
				if (null == money1) {
					money1 = "0";
				}
				yjzje = yjzje.add(new BigDecimal(Double.valueOf(money1.toString())));
			}
			pd.put("hkzje", hkzje.setScale(2, BigDecimal.ROUND_HALF_UP));
			pd.put("yjzje", yjzje.setScale(2, BigDecimal.ROUND_HALF_UP));

			mv.setViewName("business/workBenchManage/ajhk_list");
			mv.addObject("cusList", cusList);
			mv.addObject("pd", pd);
			mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	@RequestMapping(value = "/ajHktj1")
	@ResponseBody
	public Object ajHktj1() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = null;
		String error = null;
		PageInfo<List<PageData>> page = null;
		try {
			PageData pd = this.getPageData();
			String pageNo = pd.getString("pageNo");
			String pageSize = pd.getString("pageSize");
			int pNo = TextUtil.isNull(pageNo) ? 1 : Integer.parseInt(pageNo);
			int pSize = TextUtil.isNull(pageSize) ? 5 : Integer.parseInt(pageSize);
			PageHelper.startPage(pNo, pSize); // 核心分页代码
			page = new PageInfo(workBenchService.ajhkList1(pd)); // 列出预估列表
			List<PageData> cusList1 = workBenchService.ajhkList1(pd);
			BigDecimal hkzje = new BigDecimal(0.0);
			BigDecimal yjzje = new BigDecimal(0.0);
			for (PageData cus : cusList1) {
				Object money = cus.get("money");
				if (null == money) {
					money = "0";
				}
				hkzje = hkzje.add(new BigDecimal(Double.valueOf(money.toString())));
				Object money1 = cus.get("commissionaMount");
				if (null == money1) {
					money1 = "0";
				}
				yjzje = yjzje.add(new BigDecimal(Double.valueOf(money1.toString())));
			}
			map.put("hkzje", hkzje.setScale(2, BigDecimal.ROUND_HALF_UP));
			map.put("yjzje", yjzje.setScale(2, BigDecimal.ROUND_HALF_UP));
			error = "00";
			msg = "查询成功";
		} catch (Exception e) {
			e.printStackTrace();
			error = "500";
			msg = "程序异常";
		} finally {
			map.put("error", error);
			map.put("msg", msg);
			map.put("result", page);
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
	@RequestMapping(value = "/sendEmail")
	@ResponseBody
	public Object saveProcess() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String error = "";
		String msg = "";
		String result = "";
		try {
			PageData pd = this.getPageData();
			String id = pd.getString("id");
			String email = pd.getString("email");
			String linkmanName = pd.getString("linkmanName");
			// 发送邮件
			if (TextUtil.isNotNull(id) && TextUtil.isNotNull(email) && TextUtil.isNotNull(linkmanName)) {
				PageData orderReportInfo = workBenchService.finOrderReportById(pd);
				workBenchService.updateReportLinkman(pd);
				/*String email = orderReportInfo.getString("orderEmail");*/
				/*String email = orderReportInfo.getString("email");*/
				// User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
				// 收件人 //客户名称 //电话 //传真 //邮箱 发件人 日期 主题 债务人 债务金额 账龄 订单编号
				String customerName = orderReportInfo.getString("customerName");
				String fax = orderReportInfo.getString("fax"); // 电话、传真
				// String userName = user.getUSERNAME(); // 发件人
				String tiltle = orderReportInfo.getString("title");
				String runnerName = orderReportInfo.getString("runnerName");
				String debtorName = orderReportInfo.getString("debtorName");
				String debtAmount = orderReportInfo.getString("debtAmount");
				String ageOfDebt = orderReportInfo.getString("ageOfDebt");
				String remark = orderReportInfo.getString("remark");
				String orderNo = orderReportInfo.getString("orderNo");
				String hxjh = orderReportInfo.getString("hxjh");
				String strVar = "";
				if ("1".equals(orderReportInfo.getString("reportType"))) {
					strVar += "<!DOCTYPE html>\n";
					strVar += "<html>\n";
					strVar += "\n";
					strVar += "	<head>\n";
					strVar += "		<meta charset=\"utf-8\">\n";
					strVar += "	<title></title>\n";
					strVar += "		<meta name=\"Keywords\" content=\"\" />\n";
					strVar += "		<meta name=\"description\" content=\"\" />\n";
					strVar += "\n";
					strVar += "		<body style=\"background:#e9e9e9\">\n";
					strVar += "\n";
					strVar += "			<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"750px\" style=\"background: #fff;margin: 0 auto;box-shadow: 0 0 6px 2px #e1e1e1;line-height:2;font-size: 14px;\">\n";
					strVar += "				<thead>\n";
					strVar += "					<tr>\n";
					strVar += "						<th colspan=\"2\" style=\"padding:10px;border-bottom:1px solid #f1f1f1;background: #f4f4f4;\">案件结案报告</th>\n";
					strVar += "					</tr>\n";
					strVar += "				</thead>\n";
					strVar += "				<tr>\n";
					strVar += "					<td width=\"15%\" style=\"padding:0 10px; text-align: right;color:#777\">\n";
					strVar += "						收件人:\n";
					strVar += "					</td>\n";
					strVar += "					<td   style=\"padding:0 10px;\">" + customerName + "</td>\n";
					strVar += "				</tr>\n";
					strVar += "				<tr>\n";
					strVar += "					<td width=\"15%\" style=\"padding:0 10px;text-align: right;color:#777\">\n";
					strVar += "						客户名称:\n";
					strVar += "					</td>\n";
					strVar += "					<td   style=\"padding:0 10px;\">" + customerName + "</td>\n";
					strVar += "				</tr>\n";
					strVar += "				<tr>\n";
					strVar += "					<td width=\"15%\" style=\"padding:0 10px;text-align: right;color:#777\">\n";
					strVar += "						电话:\n";
					strVar += "					</td>\n";
					strVar += "					<td   style=\"padding:0 10px;\">" + fax + "</td>\n";
					strVar += "				</tr>\n";
					strVar += "				<tr>\n";
					strVar += "					<td width=\"15%\" style=\"padding:0 10px;text-align: right;color:#777\">\n";
					strVar += "						传真:\n";
					strVar += "					</td>\n";
					strVar += "					<td   style=\"padding:0 10px;\">" + fax + "</td>\n";
					strVar += "				</tr>\n";
					strVar += "				<tr>\n";
					strVar += "					<td width=\"15%\" style=\"padding:0 10px;text-align: right;color:#777\">\n";
					strVar += "						Email:\n";
					strVar += "					</td>\n";
					strVar += "					<td   style=\"padding:0 10px;\">\n";
					strVar += "						" + email + "</td>\n";
					strVar += "				</tr>\n";
					strVar += "				<tr>\n";
					strVar += "					<td width=\"15%\" style=\"padding:0 10px;text-align: right;color:#777\">\n";
					strVar += "						抄送:\n";
					strVar += "					</td>\n";
					strVar += "					<td   style=\"padding:0 10px;\"></td>\n";
					strVar += "				</tr>\n";
					strVar += "				<tr><td colspan=\"2\" style=\"padding:10px;\"></td></tr>\n";
					strVar += "				<tr>\n";
					strVar += "					<td width=\"15%\" style=\"padding:0 10px;text-align: right;color:#777\">\n";
					strVar += "						发件人:\n";
					strVar += "					</td>\n";
					strVar += "					<td   style=\"padding:0 10px;\">客服 </td>\n";
					strVar += "				</tr>\n";
					strVar += "				<tr>\n";
					strVar += "					<td width=\"15%\" style=\"padding:0 10px;text-align: right;color:#777\">\n";
					strVar += "						公司名称:\n";
					strVar += "					</td>\n";
					strVar += "					<td   style=\"padding:0 10px;\">上海深孚征信(Shang Hai Shen Fu Zheng Xin)</td>\n";
					strVar += "				</tr>\n";
					strVar += "				<tr>\n";
					strVar += "					<td width=\"15%\" style=\"padding:0 10px;text-align: right;color:#777\">\n";
					strVar += "						电话:\n";
					strVar += "					</td>\n";
					strVar += "					<td   style=\"padding:0 10px;\">\n";
					strVar += "						(021)65330189-809</td>\n";
					strVar += "				</tr>\n";
					strVar += "				<tr>\n";
					strVar += "					<td width=\"15%\" style=\"padding:0 10px;text-align: right;color:#777\">\n";
					strVar += "						传真:\n";
					strVar += "					</td>\n";
					strVar += "					<td   style=\"padding:0 10px;\">\n";
					strVar += "						(021)65330189</td>\n";
					strVar += "				</tr>\n";
					strVar += "				<tr>\n";
					strVar += "					<td width=\"15%\" style=\"padding:0 10px;text-align: right;color:#777\">\n";
					strVar += "						Email:\n";
					strVar += "					</td>\n";
					strVar += "					<td style=\"padding:0 10px;\">\n";
					strVar += "						collection_sfzx@shanghaisf.cn</td>\n";
					strVar += "				</tr>\n";
					strVar += "				<tr><td colspan=\"2\" style=\"padding:10px;\"></td></tr>\n";
					strVar += "							<tr>\n";
					strVar += "								<td style=\"width: 15%;color: #777;padding:0 10px;text-align:right\">日期</td>\n";
					strVar += "								<td style=\"padding:0 10px\">" + DateUtil.getCurrentTime() + "</td>\n";
					strVar += "							</tr>\n";
					strVar += "							<tr>\n";
					strVar += "								<td style=\"width: 15%;color: #777;padding:0 10px;text-align:right\">主题</td>\n";
					strVar += "								<td style=\"padding:0 10px\">结案报告 -" + tiltle + "</td>\n";
					strVar += "							</tr>\n";
					strVar += "							<tr>\n";
					strVar += "							\n";
					strVar += "								<td style=\"width: 15%;color: #777;padding:0 10px;text-align:right\">债务人</td>\n";
					strVar += "								<td style=\"padding:0 10px\">" + debtorName + "</td>\n";
					strVar += "								</tr>\n";
					strVar += "							<tr>\n";
					strVar += "								\n";
					strVar += "								<td style=\"width: 15%;color: #777;padding:0 10px;text-align:right\">债务金额</td>\n";
					strVar += "								<td style=\"padding:0 10px\">" + debtAmount + "</td>\n";
					strVar += "								</tr>\n";
					strVar += "							<tr>\n";
					strVar += "								\n";
					strVar += "								<td style=\"width: 15%;color: #777;padding:0 10px;text-align:right\">账龄</td>\n";
					strVar += "								<td style=\"padding:0 10px\">" + ageOfDebt + "</td>\n";
					strVar += "								</tr>\n";
					strVar += "							<tr>\n";
					strVar += "								\n";
					strVar += "								<td style=\"width: 15%;color: #777;padding:0 10px;text-align:right\">订单编号</td>\n";
					strVar += "								<td style=\"padding:0 10px\">" + orderNo + "</td>\n";
					strVar += "								</tr>\n";
					strVar += "							<tr>\n";
					strVar += "								\n";
					strVar += "								<td style=\"width: 15%;color: #777;padding:0 10px;text-align:right\">深孚顾问</td>\n";
					strVar += "								<td style=\"padding:0 10px\">" + runnerName + "</td>\n";
					strVar += "							</tr>\n";
					strVar += "					<td colspan=\"2\" style=\"padding:10px;border-left:3px solid #53c5e7;border-bottom:2px solid #f1f1f1;border-top:1px solid #f1f1f1;background: #f4f4f4;\">案件结案</td>\n";
					strVar += "					</td>\n";
					strVar += "				</tr>\n";
					strVar += "				<tr>\n";
					strVar += "					<td colspan=\"2\" style=\"padding:10px 10px 0 10px;\">\n";
					strVar += "本案，在贵我双方共同努力下</td>\n";
					strVar += "				</tr>\n";
					strVar += "				<tr>\n";
					strVar += "					<td colspan=\"2\" style=\"padding:10px;border-bottom:1px solid #f1f1f1\">\n";
					strVar += "				" + remark + "\n";
					strVar += "					</td>\n";
					strVar += "				</tr>\n";
					strVar += "						<tr>\n";
					strVar += "							<td colspan=\"2\" style=\"padding:10px 10px 0 10px;border-top:1px solid #f1f1f1;background: #f9f9f9;font-size: 12px;color:#999\">感谢您对上海深孚的信任和支持。如对该案有任何意见或建议，欢迎您随时联系上海深孚。</td>\n";
					strVar += "							<tr>\n";
					strVar += "								<tr>\n";
					strVar += "									<td colspan=\"2\" style=\"padding:10px;background: #f9f9f9;font-size: 12px;color:#999\">本报告仅供商业决策参考之用，不得用作法律诉讼的依据。未经上海深孚同意或授权，不得向第三人透露本报告的任何内容。在任何情况下，对由于使用报告所造成的损失，上海深孚不承担任何责任。</td>\n";
					strVar += "									</td>\n";
					strVar += "									<tr>\n";
					strVar += "\n";
					strVar += "			</table>\n";
					strVar += "\n";
					strVar += "		</body>\n";
					strVar += "\n";
					strVar += "</html>\n";
				}
				if ("0".equals(orderReportInfo.getString("reportType"))) {
					strVar += "<!DOCTYPE html>\n";
					strVar += "<html>\n";
					strVar += "\n";
					strVar += "	<head>\n";
					strVar += "		<meta charset=\"utf-8\">\n";
					strVar += "	<title></title>\n";
					strVar += "		<meta name=\"Keywords\" content=\"\" />\n";
					strVar += "		<meta name=\"description\" content=\"\" />\n";
					strVar += "\n";
					strVar += "		<body style=\"background:#e9e9e9\">\n";
					strVar += "\n";
					strVar += "			<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"750px\" style=\"background: #fff;margin: 0 auto;box-shadow: 0 0 6px 2px #e1e1e1;line-height:2;font-size: 14px;\">\n";
					strVar += "				<thead>\n";
					strVar += "					<tr>\n";
					strVar += "						<th colspan=\"2\" style=\"padding:10px;border-bottom:1px solid #f1f1f1;background: #f4f4f4;\">案件进展报告</th>\n";
					strVar += "					</tr>\n";
					strVar += "				</thead>\n";
					strVar += "				<tr>\n";
					strVar += "					<td width=\"15%\" style=\"padding:0 10px; text-align: right;color:#777\">\n";
					strVar += "						收件人:\n";
					strVar += "					</td>\n";
					strVar += "					<td   style=\"padding:0 10px;\">" + customerName + "</td>\n";
					strVar += "				</tr>\n";
					strVar += "				<tr>\n";
					strVar += "					<td width=\"15%\" style=\"padding:0 10px;text-align: right;color:#777\">\n";
					strVar += "						客户名称:\n";
					strVar += "					</td>\n";
					strVar += "					<td   style=\"padding:0 10px;\">" + customerName + "</td>\n";
					strVar += "				</tr>\n";
					strVar += "				<tr>\n";
					strVar += "					<td width=\"15%\" style=\"padding:0 10px;text-align: right;color:#777\">\n";
					strVar += "						电话:\n";
					strVar += "					</td>\n";
					strVar += "					<td   style=\"padding:0 10px;\">" + fax + "</td>\n";
					strVar += "				</tr>\n";
					strVar += "				<tr>\n";
					strVar += "					<td width=\"15%\" style=\"padding:0 10px;text-align: right;color:#777\">\n";
					strVar += "						传真:\n";
					strVar += "					</td>\n";
					strVar += "					<td   style=\"padding:0 10px;\">" + fax + "</td>\n";
					strVar += "				</tr>\n";
					strVar += "				<tr>\n";
					strVar += "					<td width=\"15%\" style=\"padding:0 10px;text-align: right;color:#777\">\n";
					strVar += "						Email:\n";
					strVar += "					</td>\n";
					strVar += "					<td   style=\"padding:0 10px;\">\n";
					strVar += "						" + email + "</td>\n";
					strVar += "				</tr>\n";
					strVar += "				<tr>\n";
					strVar += "					<td width=\"15%\" style=\"padding:0 10px;text-align: right;color:#777\">\n";
					strVar += "						抄送:\n";
					strVar += "					</td>\n";
					strVar += "					<td   style=\"padding:0 10px;\"></td>\n";
					strVar += "				</tr>\n";
					strVar += "				<tr><td colspan=\"2\" style=\"padding:10px;\"></td></tr>\n";
					strVar += "				<tr>\n";
					strVar += "					<td width=\"15%\" style=\"padding:0 10px;text-align: right;color:#777\">\n";
					strVar += "						发件人:\n";
					strVar += "					</td>\n";
					strVar += "					<td   style=\"padding:0 10px;\">客服</td>\n";
					strVar += "				</tr>\n";
					strVar += "				<tr>\n";
					strVar += "					<td width=\"15%\" style=\"padding:0 10px;text-align: right;color:#777\">\n";
					strVar += "						公司名称:\n";
					strVar += "					</td>\n";
					strVar += "					<td   style=\"padding:0 10px;\">上海深孚征信(Shang Hai Shen Fu Zheng Xin)</td>\n";
					strVar += "				</tr>\n";
					strVar += "				<tr>\n";
					strVar += "					<td width=\"15%\" style=\"padding:0 10px;text-align: right;color:#777\">\n";
					strVar += "						电话:\n";
					strVar += "					</td>\n";
					strVar += "					<td   style=\"padding:0 10px;\">\n";
					strVar += "						(021)65330189-809</td>\n";
					strVar += "				</tr>\n";
					strVar += "				<tr>\n";
					strVar += "					<td width=\"15%\" style=\"padding:0 10px;text-align: right;color:#777\">\n";
					strVar += "						传真:\n";
					strVar += "					</td>\n";
					strVar += "					<td   style=\"padding:0 10px;\">\n";
					strVar += "						(021)65330189</td>\n";
					strVar += "				</tr>\n";
					strVar += "				<tr>\n";
					strVar += "					<td width=\"15%\" style=\"padding:0 10px;text-align: right;color:#777\">\n";
					strVar += "						Email:\n";
					strVar += "					</td>\n";
					strVar += "					<td style=\"padding:0 10px;\">\n";
					strVar += "						collection_sfzx@shanghaisf.cn</td>\n";
					strVar += "				</tr>\n";
					strVar += "				<tr><td colspan=\"2\" style=\"padding:10px;\"></td></tr>\n";
					strVar += "							<tr>\n";
					strVar += "								<td style=\"width: 15%;color: #777;padding:0 10px;text-align:right\">日期</td>\n";
					strVar += "								<td style=\"padding:0 10px\">" + DateUtil.getCurrentTime() + "</td>\n";
					strVar += "							</tr>\n";
					strVar += "							<tr>\n";
					strVar += "								<td style=\"width: 15%;color: #777;padding:0 10px;text-align:right\">主题</td>\n";
					strVar += "								<td style=\"padding:0 10px\">进展报告 -" + tiltle + "</td>\n";
					strVar += "							</tr>\n";
					strVar += "							<tr>\n";
					strVar += "							\n";
					strVar += "								<td style=\"width: 15%;color: #777;padding:0 10px;text-align:right\">债务人</td>\n";
					strVar += "								<td style=\"padding:0 10px\">" + debtorName + "</td>\n";
					strVar += "								</tr>\n";
					strVar += "							<tr>\n";
					strVar += "								\n";
					strVar += "								<td style=\"width: 15%;color: #777;padding:0 10px;text-align:right\">债务金额</td>\n";
					strVar += "								<td style=\"padding:0 10px\">" + debtAmount + "</td>\n";
					strVar += "								</tr>\n";
					strVar += "							<tr>\n";
					strVar += "								\n";
					strVar += "								<td style=\"width: 15%;color: #777;padding:0 10px;text-align:right\">账龄</td>\n";
					strVar += "								<td style=\"padding:0 10px\">" + ageOfDebt + "</td>\n";
					strVar += "								</tr>\n";
					strVar += "							<tr>\n";
					strVar += "								\n";
					strVar += "								<td style=\"width: 15%;color: #777;padding:0 10px;text-align:right\">订单编号</td>\n";
					strVar += "								<td style=\"padding:0 10px\">" + orderNo + "</td>\n";
					strVar += "								</tr>\n";
					strVar += "							<tr>\n";
					strVar += "								\n";
					strVar += "								<td style=\"width: 15%;color: #777;padding:0 10px;text-align:right\">深孚顾问</td>\n";
					strVar += "								<td style=\"padding:0 10px\">" + runnerName + "</td>\n";
					strVar += "							</tr>\n";
					strVar += "\n";
					strVar += "				<tr>\n";
					strVar += "					<td colspan=\"4\" style=\"padding:10px;border-left:3px solid #53c5e7;border-bottom:2px solid #f1f1f1;border-top:1px solid #f1f1f1;background: #f4f4f4;\">本期进展</td>\n";
					strVar += "					</td>\n";
					strVar += "				</tr>\n";
					strVar += "				<tr>\n";
					strVar += "					<td colspan=\"4\" style=\"padding:10px 10px 0 10px;\">在认真分析了债务情况和债务人信息的基础上，我司展开了积极的催收工作，目前工作进展情况如下：</td>\n";
					strVar += "				</tr>\n";
					strVar += "				<tr>\n";
					strVar += "					<td colspan=\"4\" style=\"padding:10px;border-bottom:1px solid #f1f1f1\">" + remark + "\n";
					strVar += "					</td>\n";
					strVar += "				</tr>\n";
					strVar += "				<tr>\n";
					strVar += "					<td colspan=\"4\" style=\"padding:10px;border-left:3px solid #53c5e7;border-bottom:2px solid #f1f1f1;border-top:1px solid #f1f1f1;background: #f4f4f4;\">后续计划</td>\n";
					strVar += "				</tr>\n";
					strVar += "						<tr>\n";
					strVar += "							<td colspan=\"4\" style=\"padding:10px;border-bottom:1px solid #f1f1f1\">\n";
					strVar += "								" + hxjh + "</td>\n";
					strVar += "						</tr>\n";
					strVar += "						<tr>\n";
					strVar += "							<td colspan=\"4\" style=\"padding:10px 10px 0 10px;border-top:1px solid #f1f1f1;background: #f9f9f9;font-size: 12px;color:#999\">感谢您对上海深孚的信任和支持。如对该案有任何意见或建议，欢迎您随时联系上海深孚。</td>\n";
					strVar += "							</td>\n";
					strVar += "							<tr>\n";
					strVar += "								<tr>\n";
					strVar += "									<td colspan=\"4\" style=\"padding:10px;background: #f9f9f9;font-size: 12px;color:#999\">本报告仅供商业决策参考之用，不得用作法律诉讼的依据。未经上海深孚同意或授权，不得向第三人透露本报告的任何内容。在任何情况下，对由于使用报告所造成的损失，上海深孚不承担任何责任。</td>\n";
					strVar += "									</td>\n";
					strVar += "									<tr>\n";
					strVar += "\n";
					strVar += "			</table>\n";
					strVar += "\n";
					strVar += "		</body>\n";
					strVar += "\n";
					strVar += "</html>\n";
				}
				if ("2".equals(orderReportInfo.getString("reportType"))) { // 关闭
					strVar += "<!DOCTYPE html>\n";
					strVar += "<html>\n";
					strVar += "\n";
					strVar += "	<head>\n";
					strVar += "		<meta charset=\"utf-8\">\n";
					strVar += "	<title></title>\n";
					strVar += "		<meta name=\"Keywords\" content=\"\" />\n";
					strVar += "		<meta name=\"description\" content=\"\" />\n";
					strVar += "\n";
					strVar += "		<body style=\"background:#e9e9e9\">\n";
					strVar += "\n";
					strVar += "			<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"750px\" style=\"background: #fff;margin: 0 auto;box-shadow: 0 0 6px 2px #e1e1e1;line-height:2;font-size: 14px;\">\n";
					strVar += "				<thead>\n";
					strVar += "					<tr>\n";
					strVar += "						<th colspan=\"2\" style=\"padding:10px;border-bottom:1px solid #f1f1f1;background: #f4f4f4;\">案件关闭报告</th>\n";
					strVar += "					</tr>\n";
					strVar += "				</thead>\n";
					strVar += "				<tr>\n";
					strVar += "					<td width=\"15%\" style=\"padding:0 10px; text-align: right;color:#777\">\n";
					strVar += "						收件人:\n";
					strVar += "					</td>\n";
					strVar += "					<td   style=\"padding:0 10px;\">" + customerName + "</td>\n";
					strVar += "				</tr>\n";
					strVar += "				<tr>\n";
					strVar += "					<td width=\"15%\" style=\"padding:0 10px;text-align: right;color:#777\">\n";
					strVar += "						客户名称:\n";
					strVar += "					</td>\n";
					strVar += "					<td   style=\"padding:0 10px;\">" + customerName + "</td>\n";
					strVar += "				</tr>\n";
					strVar += "				<tr>\n";
					strVar += "					<td width=\"15%\" style=\"padding:0 10px;text-align: right;color:#777\">\n";
					strVar += "						电话:\n";
					strVar += "					</td>\n";
					strVar += "					<td   style=\"padding:0 10px;\">" + fax + "</td>\n";
					strVar += "				</tr>\n";
					strVar += "				<tr>\n";
					strVar += "					<td width=\"15%\" style=\"padding:0 10px;text-align: right;color:#777\">\n";
					strVar += "						传真:\n";
					strVar += "					</td>\n";
					strVar += "					<td   style=\"padding:0 10px;\">" + fax + "</td>\n";
					strVar += "				</tr>\n";
					strVar += "				<tr>\n";
					strVar += "					<td width=\"15%\" style=\"padding:0 10px;text-align: right;color:#777\">\n";
					strVar += "						Email:\n";
					strVar += "					</td>\n";
					strVar += "					<td   style=\"padding:0 10px;\">\n";
					strVar += "						" + email + "</td>\n";
					strVar += "				</tr>\n";
					strVar += "				<tr>\n";
					strVar += "					<td width=\"15%\" style=\"padding:0 10px;text-align: right;color:#777\">\n";
					strVar += "						抄送:\n";
					strVar += "					</td>\n";
					strVar += "					<td   style=\"padding:0 10px;\"></td>\n";
					strVar += "				</tr>\n";
					strVar += "				<tr><td colspan=\"2\" style=\"padding:10px;\"></td></tr>\n";
					strVar += "				<tr>\n";
					strVar += "					<td width=\"15%\" style=\"padding:0 10px;text-align: right;color:#777\">\n";
					strVar += "						发件人:\n";
					strVar += "					</td>\n";
					strVar += "					<td   style=\"padding:0 10px;\">客服</td>\n";
					strVar += "				</tr>\n";
					strVar += "				<tr>\n";
					strVar += "					<td width=\"15%\" style=\"padding:0 10px;text-align: right;color:#777\">\n";
					strVar += "						公司名称:\n";
					strVar += "					</td>\n";
					strVar += "					<td   style=\"padding:0 10px;\">上海深孚征信(Shang Hai Shen Fu Zheng Xin)</td>\n";
					strVar += "				</tr>\n";
					strVar += "				<tr>\n";
					strVar += "					<td width=\"15%\" style=\"padding:0 10px;text-align: right;color:#777\">\n";
					strVar += "						电话:\n";
					strVar += "					</td>\n";
					strVar += "					<td   style=\"padding:0 10px;\">\n";
					strVar += "						(021)65330189-809</td>\n";
					strVar += "				</tr>\n";
					strVar += "				<tr>\n";
					strVar += "					<td width=\"15%\" style=\"padding:0 10px;text-align: right;color:#777\">\n";
					strVar += "						传真:\n";
					strVar += "					</td>\n";
					strVar += "					<td   style=\"padding:0 10px;\">\n";
					strVar += "						(021)65330189</td>\n";
					strVar += "				</tr>\n";
					strVar += "				<tr>\n";
					strVar += "					<td width=\"15%\" style=\"padding:0 10px;text-align: right;color:#777\">\n";
					strVar += "						Email:\n";
					strVar += "					</td>\n";
					strVar += "					<td style=\"padding:0 10px;\">\n";
					strVar += "						collection_sfzx@shanghaisf.cn</td>\n";
					strVar += "				</tr>\n";
					strVar += "				<tr><td colspan=\"2\" style=\"padding:10px;\"></td></tr>\n";
					strVar += "							<tr>\n";
					strVar += "								<td style=\"width: 15%;color: #777;padding:0 10px;text-align:right\">日期</td>\n";
					strVar += "								<td style=\"padding:0 10px\">" + DateUtil.getCurrentTime() + "</td>\n";
					strVar += "							</tr>\n";
					strVar += "							<tr>\n";
					strVar += "								<td style=\"width: 15%;color: #777;padding:0 10px;text-align:right\">主题</td>\n";
					strVar += "								<td style=\"padding:0 10px\">关闭报告 -" + tiltle + "</td>\n";
					strVar += "							</tr>\n";
					strVar += "							<tr>\n";
					strVar += "							\n";
					strVar += "								<td style=\"width: 15%;color: #777;padding:0 10px;text-align:right\">债务人</td>\n";
					strVar += "								<td style=\"padding:0 10px\">" + debtorName + "</td>\n";
					strVar += "								</tr>\n";
					strVar += "							<tr>\n";
					strVar += "								\n";
					strVar += "								<td style=\"width: 15%;color: #777;padding:0 10px;text-align:right\">债务金额</td>\n";
					strVar += "								<td style=\"padding:0 10px\">" + debtAmount + "</td>\n";
					strVar += "								</tr>\n";
					strVar += "							<tr>\n";
					strVar += "								\n";
					strVar += "								<td style=\"width: 15%;color: #777;padding:0 10px;text-align:right\">账龄</td>\n";
					strVar += "								<td style=\"padding:0 10px\">" + ageOfDebt + "</td>\n";
					strVar += "								</tr>\n";
					strVar += "							<tr>\n";
					strVar += "								\n";
					strVar += "								<td style=\"width: 15%;color: #777;padding:0 10px;text-align:right\">订单编号</td>\n";
					strVar += "								<td style=\"padding:0 10px\">" + orderNo + "</td>\n";
					strVar += "								</tr>\n";
					strVar += "							<tr>\n";
					strVar += "								\n";
					strVar += "								<td style=\"width: 15%;color: #777;padding:0 10px;text-align:right\">深孚顾问</td>\n";
					strVar += "								<td style=\"padding:0 10px\">" + runnerName + "</td>\n";
					strVar += "							</tr>\n";
					strVar += "				<tr>\n";
					strVar += "					<td colspan=\"4\" style=\"padding:10px;border-left:3px solid #53c5e7;border-bottom:2px solid #f1f1f1;border-top:1px solid #f1f1f1;background: #f4f4f4;\">案件关闭报告</td>\n";
					strVar += "					</td>\n";
					strVar += "				</tr>\n";
					strVar += "				<tr>\n";
					strVar += "					<td colspan=\"4\" style=\"padding:10px;border-bottom:1px solid #f1f1f1\">" + remark + "\n";
					strVar += "					</td>\n";
					strVar += "				</tr>\n";
					strVar += "				<tr>\n";
					strVar += "					<td colspan=\"4\" style=\"padding:10px;border-left:3px solid #53c5e7;border-bottom:2px solid #f1f1f1;border-top:1px solid #f1f1f1;background: #f4f4f4;\">后续计划</td>\n";
					strVar += "					</td>\n";
					strVar += "					<tr>\n";
					strVar += "						<tr>\n";
					strVar += "							<td colspan=\"4\" style=\"padding:10px;border-bottom:1px solid #f1f1f1\">\n";
					strVar += "								" + hxjh + "</td>\n";
					strVar += "						</tr>\n";
					strVar += "						<tr>\n";
					strVar += "							<td colspan=\"4\" style=\"padding:10px 10px 0 10px;border-top:1px solid #f1f1f1;background: #f9f9f9;font-size: 12px;color:#999\">感谢您对上海深孚的信任和支持。如对该案有任何意见或建议，欢迎您随时联系上海深孚。</td>\n";
					strVar += "							</td>\n";
					strVar += "							<tr>\n";
					strVar += "								<tr>\n";
					strVar += "									<td colspan=\"4\" style=\"padding:10px;background: #f9f9f9;font-size: 12px;color:#999\">本报告仅供商业决策参考之用，不得用作法律诉讼的依据。未经上海深孚同意或授权，不得向第三人透露本报告的任何内容。在任何情况下，对由于使用报告所造成的损失，上海深孚不承担任何责任。</td>\n";
					strVar += "									</td>\n";
					strVar += "									<tr>\n";
					strVar += "\n";
					strVar += "			</table>\n";
					strVar += "\n";
					strVar += "		</body>\n";
					strVar += "\n";
					strVar += "</html>\n";
				}

				if (TextUtil.isNotNull(email)) {
					@SuppressWarnings("unchecked")
					List<PageData> attachs = (List<PageData>) orderReportInfo.get("attachs");
					String[] fileName = null;
					String filePath = "";
					if (attachs.size() > 0) {
						fileName = new String[attachs.size()];
						for (int i = 0; i < attachs.size(); i++) {
							fileName[i] = attachs.get(i).getString("originalFilename");
							filePath += attachs.get(i).getString("realPath") + ",";
						}
					}
					SimpleMailSender sms = new SimpleMailSender();
					sms.sendMail(orderReportInfo.getString("title"), strVar, "2", email, fileName, filePath);
					// 改下发送邮件的状态
					pd.put("editTime", DateUtil.getCurrentTime());
					workBenchService.updateReportStatus(pd);
					error = "00";
					msg = "发送成功";
					// 任务改完完成
					workBenchService.updateWarnInfo(pd);
				} else {
					error = "01";
					msg = "该债务人无邮箱";
				}
			} else {
				error = "01";
				msg = "发送失败";
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
	 * 展示任务列表
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/chooseTask")
	public ModelAndView chooseTask(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		try {
			PageData pd = new PageData();
			pd = this.getPageData();
			page.setPd(pd);
			List<PageData> cusList = workBenchService.relateTasklistPage(page); // 列出要关联的任务列表
			mv.setViewName("business/workBenchManage/chooseTask_list");
			mv.addObject("cusList", cusList);
			mv.addObject("pd", pd);
			mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	/**
	 * 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goTotal")
	public ModelAndView goTotal() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.addObject("pd", pd);
		mv.addObject("msg", "doSaveTotal");
		mv.setViewName("business/workBenchManage/orderreport_recieve");
		return mv;
	}

	@RequestMapping(value = "/doSaveTotal")
	public ModelAndView doSaveTotal() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		pd.put("flag", "1");
		pd.put("editTime", DateUtil.getCurrentTime());
		workBenchService.updateReportStatus(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	@RequestMapping(value = "/viewOrderReportInfo")
	public ModelAndView view() throws Exception {
		ModelAndView mv = this.getModelAndView();
		try {
			PageData pd = new PageData();
			pd = this.getPageData();
			pd = workBenchService.finOrderReportById(pd); // 列出客户列表
			mv.setViewName("business/workBenchManage/orderreport_view");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	/**
	 * 根据日期来查回款明细
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findPayDetail")
	@ResponseBody
	public Object findPayDetail() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String error = "";
		String msg = "";
		PageData result = null;
		try {
			PageData pd = this.getPageData();
			String dateTime = pd.getString("time");
			if (TextUtil.isNotNull(dateTime) && TextUtil.isNotNull(pd.getString("orderId"))) {
				pd.put("timeStart", dateTime.substring(0, 7) + "-01");
				pd.put("timeEnd", getLastTime(dateTime));
				List<PageData> result1 = workBenchService.findPaymentPlan(pd);
				if (result1.size() == 1) {
					result = result1.get(0);
				} else {
					for (PageData pd1 : result1) {
						if (null != pd1.get("periods")) {
							result = pd1;
						}
					}
				}
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

	private static String getLastTime(String dateTime) throws Exception {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		c.setTime(format.parse(dateTime));
		c.set(Calendar.DATE, c.getActualMaximum(Calendar.DATE));
		return format.format(c.getTime());
	}

	@RequestMapping(value = "/doUpdate")
	@ResponseBody
	public Object doUpdate() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String error = "";
		String msg = "";
		PageData result = null;
		try {
			PageData pd = this.getPageData();
			if (workBenchService.doUpdate(pd) > 0) {

				PageData pd1 = workBenchService.findTaskById1(pd);
				pd1.put("wcqk", pd.getString("wcqk"));
				workBenchService.updateVisitInfo(pd1);
				if ("1".equals(pd1.get("type").toString())) {
					User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
					PageData pd4 = new PageData();
					pd4.put("userId", pd1.getString("userId"));
					pd4.put("type", 13);
					pd4.put("relateId", pd.get("id"));
					pd4.put("content", user.getNAME() + "回复了你安排的任务");
					pd4.put("flag", 0);
					customerService.saveNoticInfo(pd4);
					systemWebSocketHandler.sendMessageToUser(pd1.getString("userId").toString(), new TextMessage("notic:" + pd4.get("id")));
				}
				error = "00";
				msg = "任务完成";
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
	 * 驳回、取消
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/doUpdate1")
	@ResponseBody
	public Object doUpdate1() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String error = "";
		String msg = "";
		PageData result = null;
		try {
			PageData pd = this.getPageData();
			if (workBenchService.doUpdate1(pd) > 0) {
				error = "00";
				msg = "修改完成";
				if ("2".equals(pd.getString("status"))) {
					// User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
					PageData pd2 = workBenchService.findById(pd);
					pd.put("userId", pd2.getString("userId"));
					PageData serivice = expressService.findServiceId();
					pd.put("doId", serivice.get("userId")); // 客服执行人
					workBenchService.saveWarnTask1(pd);
					pd = workBenchService.findRepportById(pd);
					PageData pd1 = new PageData();
					pd1.put("orderId", pd.get("orderId"));
					pd1.put("status", pd.get("reportType"));
					pd1.put("markF", 1);
					lawsuitauditService.updateOrder(pd1);
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
