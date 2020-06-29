package com.yocto.controller.system.logs;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yocto.controller.base.BaseController;
import com.yocto.entity.Page;
import com.yocto.service.system.logs.ILogsService;
import com.yocto.util.Jurisdiction;
import com.yocto.util.PageData;
import com.yocto.util.TextUtil;

@Controller
@RequestMapping(value = "/logs")
public class LogsController extends BaseController {

	String menuUrl = "logs/listLogs.do"; // 菜单地址(权限用)
	@Resource(name = "logService")
	private ILogsService LogsService;

	/**
	 * 展示异常信息列表
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "listLogs")
	public ModelAndView getListLogs(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
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
		List<PageData> logsList = LogsService.listLogs(page);
		mv.setViewName("system/logs/logs_list");
		mv.addObject("logsList", logsList);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	/**
	 * 查看
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/view")
	public ModelAndView view() throws Exception {
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = LogsService.findById(pd); // 根据ID读取
		mv.setViewName("system/logs/logs_view");
		mv.addObject("pd", pd);
		return mv;
	}
}
