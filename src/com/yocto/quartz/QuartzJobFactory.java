package com.yocto.quartz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.yocto.service.business.customerManage.ICustomerService;
import com.yocto.util.DateUtil;
import com.yocto.util.PageData;
import com.yocto.util.context.SpringContextUtils;

@DisallowConcurrentExecution
public class QuartzJobFactory implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("============定时任务开启==============");
		PageData pd = (PageData) context.getMergedJobDataMap().get("scheduleJob");
		findSaleCustomer(pd);
		System.out.println("============定时任务结束==============");
	}

	// 任务
	public void findSaleCustomer(PageData cus) {
		// 1.查询所有的潜在客户
		PageData pd = new PageData();
		pd.put("isKhGh", 2);
		if (null != cus.get("saleIds")) {
			pd.put("saleIds", Arrays.asList(cus.get("saleIds").toString().split(",")));
		}
		try {
			ICustomerService customerService = SpringContextUtils.getBean("customerService", ICustomerService.class);
			List<PageData> list = customerService.listSaleCustomer(pd);
			List<String> list1 = new ArrayList<String>(); // 存放客户公海0
			List<String> list2 = new ArrayList<String>(); // 存放有意向客户公海 1
			List<String> list3 = new ArrayList<String>(); // 存放无意向客户公海 3
			List<String> list4 = new ArrayList<String>(); // 存放大客户客户公海 4
			List<String> list5 = new ArrayList<String>(); // 存放有意向存疑客户公海5
			List<String> list6 = new ArrayList<String>(); // 存放新三板 6
			List<String> list7 = new ArrayList<String>(); // 上市公司7
			/*List<String> list5 = new ArrayList<String>();*/
			for (PageData pd1 : list) {
				if (null != pd1.get("xchrghTime")) {
					pd1.put("hqdTime", pd1.get("xchrghTime"));
					/*PageData pd2 = customerService.findAction(pd1);
					if (null == pd2) {*/
					// 这么多没有做过日志
					String nowTime = DateUtil.getCurrentTime();
					String hrghTime = pd1.getString("hqdTime");
					// 如果当前时间>=划入公海时间
					if (nowTime.compareTo(hrghTime) >= 0) {
						if ("0".equals(pd1.get("type").toString())) {
							list1.add(pd1.get("id").toString());
						} else if ("1".equals(pd1.get("type").toString())) {
							list2.add(pd1.get("id").toString());
						} else if ("3".equals(pd1.get("type").toString())) {
							list3.add(pd1.get("id").toString());
						} else if ("4".equals(pd1.get("type").toString())) {
							list4.add(pd1.get("id").toString());
						} else if ("5".equals(pd1.get("type").toString())) {
							list5.add(pd1.get("id").toString());
						} else if ("6".equals(pd1.get("type").toString())) {
							list6.add(pd1.get("id").toString());
						} else if ("7".equals(pd1.get("type").toString())) {
							list7.add(pd1.get("id").toString());
						}

						/*if ("2".equals(pd1.get("gjStatus").toString())) {
							list5.add(pd1.get("id").toString());
						}*/

					}
					/*}*/
				}
			}
			// 将其转移至客户公海
			/*if (list1.size() > 0) {
				if ("0".equals(cus.get("type").toString())) {
					customerService.updateKhgh1(list1);
				} else {
					customerService.updateKhgh2(list1);
				}
			}*/
			if (list1.size() > 0) {
				customerService.updateKhgh1(list1);
			}
			if (list2.size() > 0) {
				customerService.updateKhgh3(list2);
			}
			if (list3.size() > 0) {
				customerService.updateKhgh2(list3);
			}
			if (list4.size() > 0) {
				customerService.updateKhgh4(list4);
			}
			if (list5.size() > 0) {
				customerService.updateKhgh5(list5);
			}
			if (list6.size() > 0) {
				customerService.updateKhgh6(list6);
			}
			if (list7.size() > 0) {
				customerService.updateKhgh7(list7);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
