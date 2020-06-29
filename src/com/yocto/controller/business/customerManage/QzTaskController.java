package com.yocto.controller.business.customerManage;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.annotation.Resource;

import com.yocto.util.*;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yocto.controller.base.BaseController;
import com.yocto.quartz.QuartzJobFactory;
import com.yocto.service.business.customerManage.ICustomerService;

@Controller
@RequestMapping(value = "qzTask")
public class QzTaskController extends BaseController {

	@Resource(name = "customerService")
	private ICustomerService customerService;

	@Autowired
	SchedulerFactoryBean schedulerFactoryBean;

	/**
	 * 新增任务
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveTasks")
	@ResponseBody
	public Object saveTasks() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String error = "";
		String msg = "";
		String result = "";
		try {
			PageData pd = this.getPageData();
			customerService.saveTasks(pd);
			ExecutorService executor = Executors.newCachedThreadPool();
			@SuppressWarnings("unused")
			Future<Object> result1 = executor.submit(new Callable<Object>() {
				@Override
				public Integer call() throws Exception {
					String[] saleIds = pd.getString("saleIds").split(",");
					String day = pd.getString("days");
					PageData pd1 = new PageData();
					pd1.put("saleIds", Arrays.asList(saleIds));
					pd1.put("day", day);
					customerService.updateHrghTime(pd1);

					List<PageData> listSaleCustomer = customerService.listSaleCustomer(pd1);

					for (PageData pd2 : listSaleCustomer) {
						if (TextUtil.isNotNull(pd2.get("gjTime").toString())) {
							pd2.put("saleCustomerId", pd2.get("id"));
							pd2.put("hrghTime", getNextTime(pd2.get("gjTime").toString(), Integer.valueOf(day)));
							customerService.updateHrghTime1(pd2);
						}
					}

					pd.put("time", toCronExpression(pd.get("time").toString()));
					addJob(pd);
					return 0;
				}

			});
			/*customerService.saveTasks(pd);
			String[] saleIds = pd.getString("saleIds").split(",");
			String day = pd.getString("days");
			PageData pd1 = new PageData();
			pd1.put("saleIds", Arrays.asList(saleIds));
			pd1.put("day", day);
			customerService.updateHrghTime(pd1);

			List<PageData> listSaleCustomer = customerService.listSaleCustomer(pd1);

			for (PageData pd2 : listSaleCustomer) {
				if (TextUtil.isNotNull(pd2.get("gjTime").toString())) {
					pd2.put("saleCustomerId", pd2.get("id"));
					pd2.put("hrghTime", getNextTime(pd2.get("gjTime").toString(), Integer.valueOf(day)));
					customerService.updateHrghTime1(pd2);
				}
			}

			pd.put("time", toCronExpression(pd.get("time").toString()));
			addJob(pd);*/

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

	private static String getNextTime(String time, int day) throws Exception {
		Calendar calendarH = Calendar.getInstance();
		calendarH.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time));// 把当前时间赋给日历
		calendarH.add(Calendar.DATE, day);
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendarH.getTime());
	}

	// 任务列表页展示
	@RequestMapping(value = "/showSaleCustomer")
	@ResponseBody
	public Object showSaleCustomer() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = null;
		String error = null;
		PageInfo<PageData> page = null;
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

			/*	if ("2".equals(pd.getString("isKhGh"))) {
					User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
					String userId = user.getUSER_ID();
					// 销售能看到自己的客户
					if (isRole(Const.ROLE_SALES_ELITE) || isRole(Const.ROLE_PHONE_SALES)) {
						if (!"1".equals(userId)) {
							pd.put("saleId", userId);
						}
					}
				}*/
			String pageNo = pd.getString("pageNo");
			String pageSize = pd.getString("pageSize");
			int pNo = TextUtil.isNull(pageNo) ? 1 : Integer.parseInt(pageNo);
			int pSize = TextUtil.isNull(pageSize) ? 10 : Integer.parseInt(pageSize);
			PageHelper.startPage(pNo, pSize); // 核心分页代码
			page = new PageInfo<PageData>(customerService.listQztask(pd));// 列出任务列表
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

	public void addJob(PageData job) throws Exception {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		// 在这里把它设计成一个Job对应一个trigger，两者的分组及名称相同，方便管理，条理也比较清晰
		TriggerKey triggerKey = TriggerKey.triggerKey(job.get("id").toString(), job.get("id").toString());
		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

		// 不存在，创建一个
		if (null == trigger) {
			JobDetail jobDetail = JobBuilder.newJob(QuartzJobFactory.class).withIdentity(job.get("id").toString(), job.get("id").toString()).build();
			jobDetail.getJobDataMap().put("scheduleJob", job);
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.get("time").toString());
			trigger = TriggerBuilder.newTrigger().withIdentity(job.get("id").toString(), job.get("id").toString()).withSchedule(scheduleBuilder).build();
			scheduler.scheduleJob(jobDetail, trigger);
		} else {
			// Trigger已存在，那么更新相应的定时设置
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.get("time").toString());
			// 按新的cronExpression表达式重新构建trigger
			trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
			// 按新的trigger重新设置job执行
			scheduler.rescheduleJob(triggerKey, trigger);
		}
	}

	// 时间转换cron
	private static String toCronExpression(String time) {
		String cron = "";
		if (time.indexOf(":") > 0) {
			String hh = time.split(":")[0].trim();
			String mm = time.split(":")[1].trim();
			if (hh.indexOf("0") == 0 && hh.length() == 2) {
				hh = hh.substring(1);
			}
			if (mm.indexOf("0") == 0 && mm.length() == 2) {
				mm = mm.substring(1);
			}
			cron = "0 " + mm + " " + hh + " * * ? ";
		}
		return cron;
	}

	/**
	 * 修改任务1
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateTasks")
	@ResponseBody
	public Object updateTasks() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String error = "";
		String msg = "";
		String result = "";
		try {
			PageData pd = this.getPageData();
			pd.put("editTime", DateUtil.getCurrentTime());
			customerService.updateTasks(pd);
			ExecutorService executor = Executors.newCachedThreadPool();
			@SuppressWarnings("unused")
			Future<Object> result1 = executor.submit(new Callable<Object>() {
				@Override
				public Integer call() throws Exception {
					PageData pd2 = customerService.findTaskById(pd);
					/*String[] saleIds2 = pd2.getString("saleIds").split(",");
					PageData pd3 = new PageData();
					pd3.put("saleIds", Arrays.asList(saleIds2));
					customerService.updateHrghTime(pd3);*/
					// =====修改任务//
					/*customerService.updateTasks(pd);*/
					String[] saleIds = pd.getString("saleIds").split(",");
					String day = pd.getString("days");
					PageData pd1 = new PageData();
					pd1.put("saleIds", Arrays.asList(saleIds));
					pd1.put("isKhGh",2);
					List<PageData> listSaleCustomer = customerService.listSaleCustomer(pd1);
                    List<Map<String,Object>> lisP = new ArrayList<>();
					for (PageData pd4 : listSaleCustomer) {
                        Map<String,Object> pd5=new HashMap<>(16);
                        pd5.put("saleCustomerId", pd4.get("id"));
                        if(null!=pd4.get("gjTime")&&pd4.get("qdTime").toString().compareTo(pd4.get("gjTime").toString())<=0) {
                            pd5.put("hrghTime", getNextTime(pd4.get("gjTime").toString(), Integer.valueOf(day)));
                        }else{
                            pd5.put("hrghTime", getNextTime(pd4.get("qdTime").toString(), Integer.valueOf(day)));
                        }
                        lisP.add(pd5);
//						customerService.updateHrghTime1(pd4);
					}
                    if(lisP.size()>0){
                        customerService.saveTemporyInfo(lisP);
                        customerService.updateHrghTime3();
                    }
					PageData pd5 = customerService.findTaskById(pd);
					pd5.put("time", toCronExpression(pd.get("time").toString()));
					addJob(pd5);
					return 0;
				}

			});

			/*PageData pd2 = customerService.findTaskById(pd);
			String[] saleIds2 = pd2.getString("saleIds").split(",");
			PageData pd3 = new PageData();
			pd3.put("saleIds", Arrays.asList(saleIds2));
			customerService.updateHrghTime(pd3);
			// =====修改任务//
			customerService.updateTasks(pd);
			String[] saleIds = pd.getString("saleIds").split(",");
			String day = pd.getString("days");
			PageData pd1 = new PageData();
			pd1.put("saleIds", Arrays.asList(saleIds));
			pd1.put("day", day);
			customerService.updateHrghTime(pd1);

			List<PageData> listSaleCustomer = customerService.listSaleCustomer(pd1);

			for (PageData pd4 : listSaleCustomer) {
				if (TextUtil.isNotNull(pd4.get("gjTime").toString())) {
					pd4.put("saleCustomerId", pd4.get("id"));
					pd4.put("hrghTime", getNextTime(pd4.get("gjTime").toString(), Integer.valueOf(day)));
					customerService.updateHrghTime1(pd4);
				}
			}

			pd = customerService.findTaskById(pd);
			pd.put("time", toCronExpression(pd.get("time").toString()));
			addJob(pd);*/
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
	 * 删除任务
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delteTask")
	@ResponseBody
	public Object delteTask() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String error = "";
		String msg = "";
		String result = "";
		try {
			PageData pd = this.getPageData();
			pd = customerService.findTaskById(pd);
			String[] saleIds = pd.getString("saleIds").split(",");
			PageData pd1 = new PageData();
			pd1.put("saleIds", Arrays.asList(saleIds));
			customerService.updateHrghTime(pd1);
			customerService.delteTask(pd);
			deleteJob(pd);
			error = "00";
			msg = "删除成功";
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

	// 将任务和定时解绑
	public void deleteJob(PageData job) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(job.get("id").toString(), job.get("id").toString());
		scheduler.deleteJob(jobKey);
	}
}
