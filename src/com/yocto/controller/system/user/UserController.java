package com.yocto.controller.system.user;

import java.io.File;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;
import com.yocto.controller.base.BaseController;
import com.yocto.entity.Page;
import com.yocto.entity.system.Role;
import com.yocto.entity.system.User;
import com.yocto.service.business.customerManage.ICustomerService;
import com.yocto.service.system.menu.MenuManager;
import com.yocto.service.system.role.RoleManager;
import com.yocto.service.system.user.UserManager;
import com.yocto.util.AppUtil;
import com.yocto.util.Const;
import com.yocto.util.FileDownload;
import com.yocto.util.FileUpload;
import com.yocto.util.GetPinyin;
import com.yocto.util.Jurisdiction;
import com.yocto.util.ObjectExcelRead;
import com.yocto.util.ObjectExcelView;
import com.yocto.util.PageData;
import com.yocto.util.PathUtil;
import com.yocto.util.QiNiuUtil;
import com.yocto.util.TextUtil;
import com.yocto.util.TimeUtil;
import com.yocto.util.Tools;

/**
 * 类名称：UserController 创建人： @author 更新时间：2015年11月3日
 * 
 * @version
 */
@Controller
@RequestMapping(value = "/user")
public class UserController extends BaseController {

	String menuUrl = "user/listUsers.do"; // 菜单地址(权限用)
	@Resource(name = "userService")
	private UserManager userService;
	@Resource(name = "roleService")
	private RoleManager roleService;
	@Resource(name = "menuService")
	private MenuManager menuService;
	@Resource(name = "customerService")
	private ICustomerService customerService;

	/**
	 * 显示用户列表
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/listUsers")
	public ModelAndView listUsers(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		String lastLoginStart = pd.getString("lastLoginStart"); // 开始时间
		String lastLoginEnd = pd.getString("lastLoginEnd"); // 结束时间
		if (lastLoginStart != null && !"".equals(lastLoginStart)) {
			pd.put("lastLoginStart", lastLoginStart + " 00:00:00");
		}
		if (lastLoginEnd != null && !"".equals(lastLoginEnd)) {
			pd.put("lastLoginEnd", lastLoginEnd + " 23:59:59");
		}
		page.setPd(pd);
		List<PageData> userList = userService.listUsers(page); // 列出用户列表
		// pd.put("ROLE_ID", "1");
		List<Role> roleList = roleService.listAllRoles(pd);// 列出所有系统用户角色
		mv.setViewName("system/user/user_list");
		mv.addObject("userList", userList);
		mv.addObject("roleList", roleList);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	/**
	 * 删除用户
	 * 
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteU")
	public void deleteU(PrintWriter out) throws Exception {
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return;
		} // 校验权限
		logBefore(logger, Jurisdiction.getUsername() + "删除user");
		PageData pd = new PageData();
		pd = this.getPageData();
		userService.deleteU(pd);
		out.write("success");
		out.close();
	}

	/**
	 * 去新增用户页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goAddU")
	public ModelAndView goAddU() throws Exception {
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// pd.put("ROLE_ID", "1");
		List<Role> roleList = roleService.listAllRoles(pd);// 列出所有系统用户角色
		mv.setViewName("system/user/user_edit");
		mv.addObject("msg", "saveU");
		mv.addObject("pd", pd);
		mv.addObject("roleList", roleList);
		return mv;
	}

	/**
	 * 保存用户
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveU")
	public ModelAndView saveU(HttpServletRequest request, @RequestParam(value = "tp", required = false) MultipartFile[] files, @RequestParam(value = "PHONE", required = true) String PHONE,
			@RequestParam(value = "NAME", required = true) String NAME, @RequestParam(value = "QQ", required = true) String QQ, @RequestParam(value = "EMAIL", required = true) String EMAIL,
			@RequestParam(value = "USERNAME", required = true) String USERNAME, @RequestParam(value = "weChatId", required = true) String weChatId,
			@RequestParam(value = "BZ", required = true) String BZ, @RequestParam(value = "ROLE_ID", required = true) String ROLE_ID, @RequestParam(value = "isQuit", required = true) String isQuit,
			@RequestParam(value = "quitTime", required = false) String quitTime, @RequestParam(value = "isUrl", required = true) String isUrl,
			@RequestParam(value = "PASSWORD", required = true) String PASSWORD, @RequestParam(value = "NUMBER", required = true) String NUMBER) throws Exception {
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		logBefore(logger, Jurisdiction.getUsername() + "新增user");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd.put("USERNAME", USERNAME);
		/*pd.put("PHONE", PHONE);
		pd.put("NAME", NAME);
		pd.put("QQ", QQ);
		pd.put("EMAIL", EMAIL);
		pd.put("USERNAME", USERNAME);
		pd.put("weChatId", weChatId);
		pd.put("BZ", BZ);
		pd.put("ROLE_ID", ROLE_ID);
		pd.put("NUMBER", NUMBER);
		pd.put("USER_ID", this.get32UUID()); // ID 主键
		pd.put("LAST_LOGIN", ""); // 最后登录时间
		pd.put("IP", ""); // IP
		pd.put("STATUS", "0"); // 状态
		pd.put("SKIN", "default");
		pd.put("RIGHTS", "");*/
		User user = new User();
		user.setPHONE(PHONE);
		user.setNAME(NAME);
		user.setQQ(QQ);
		user.setEMAIL(EMAIL);
		user.setUSERNAME(USERNAME);
		user.setWeChatId(weChatId);
		user.setBZ(BZ);
		user.setROLE_ID(ROLE_ID);
		user.setNUMBER(NUMBER);
		user.setIsQuit(isQuit);
		user.setIsUrl(isUrl);
		user.setQuitTime(quitTime);
		user.setUSER_ID(this.get32UUID());
		user.setLAST_LOGIN("");
		user.setIP("");
		user.setSTATUS("0");
		user.setSKIN("default");
		user.setRIGHTS("");
		user.setPASSWORD(new SimpleHash("SHA-1", USERNAME, PASSWORD) + "");
		// pd.put("PASSWORD", new SimpleHash("SHA-1", USERNAME, PASSWORD)); // 密码加密
		if (null != files && files.length > 0) {
			// 循环获取file数组中得文件
			for (int i = 0; i < files.length; i++) {
				MultipartFile mfile = files[i];
				if (null != mfile && !mfile.isEmpty()) {
					CommonsMultipartFile cf = (CommonsMultipartFile) mfile;
					DiskFileItem fi = (DiskFileItem) cf.getFileItem();
					File file = fi.getStoreLocation();
					String filePath = file.getAbsolutePath();
					if (TextUtil.isNotNull(filePath)) {
						String dir = "user/";
						String prefix = fi.getName().substring(fi.getName().lastIndexOf("."));
						String qnKey = dir + get32UUID() + prefix;
						// 上传到七牛云存储
						if (QiNiuUtil.upload(filePath, qnKey, Const.QN_BUCKETNAME)) {
							String url = Const.QN_DOMAIN + qnKey;
							String pic = QiNiuUtil.download(url);
							user.setQnKey(qnKey);
							user.setPic(pic);
							user.setOriginalPicName(fi.getName().substring(fi.getName().lastIndexOf("\\") + 1));
						}
					}
				}
			}
		} else {
			System.out.println("上传失败");
		}
		if (null == userService.findByUsername(pd)) { // 判断用户名是否存在
			userService.saveU(user); // 执行保存
			mv.addObject("msg", "success");
		} else {
			mv.addObject("msg", "failed");
		}
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 判断用户名是否存在
	 * 
	 * @return
	 */
	@RequestMapping(value = "/hasU")
	@ResponseBody
	public Object hasU() {
		Map<String, String> map = new HashMap<String, String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			if (userService.findByUsername(pd) != null) {
				errInfo = "error";
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo); // 返回结果
		return AppUtil.returnObject(new PageData(), map);
	}

	/**
	 * 判断邮箱是否存在
	 * 
	 * @return
	 */
	@RequestMapping(value = "/hasE")
	@ResponseBody
	public Object hasE() {
		Map<String, String> map = new HashMap<String, String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			if (userService.findByUE(pd) != null) {
				errInfo = "error";
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo); // 返回结果
		return AppUtil.returnObject(new PageData(), map);
	}

	/**
	 * 判断编码是否存在
	 * 
	 * @return
	 */
	@RequestMapping(value = "/hasN")
	@ResponseBody
	public Object hasN() {
		Map<String, String> map = new HashMap<String, String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			if (userService.findByUN(pd) != null) {
				errInfo = "error";
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo); // 返回结果
		return AppUtil.returnObject(new PageData(), map);
	}

	/**
	 * 去修改用户页面(系统用户列表修改)
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goEditU")
	public ModelAndView goEditU() throws Exception {
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		if ("1".equals(pd.getString("USER_ID"))) {
			return null;
		} // 不能修改admin用户
			// pd.put("ROLE_ID", "1");
		List<Role> roleList = roleService.listAllRoles(pd); // 列出所有系统用户角色
		mv.addObject("fx", "user");
		pd = userService.findById(pd); // 根据ID读取
		mv.setViewName("system/user/user_edit");
		mv.addObject("msg", "editU");
		mv.addObject("pd", pd);
		mv.addObject("roleList", roleList);
		return mv;
	}

	/**
	 * 去修改用户页面(个人修改)
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goEditMyU")
	public ModelAndView goEditMyU() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.addObject("fx", "head");
		// pd.put("ROLE_ID", "1");
		List<Role> roleList = roleService.listAllRoles(pd); // 列出所有系统用户角色
		pd.put("USERNAME", Jurisdiction.getUsername());
		pd = userService.findByUsername(pd); // 根据用户名读取
		mv.setViewName("system/user/user_edit");
		mv.addObject("msg", "editU");
		mv.addObject("pd", pd);
		mv.addObject("roleList", roleList);
		return mv;
	}

	/**
	 * 查看用户
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
		if ("admin".equals(pd.getString("USERNAME"))) {
			return null;
		} // 不能查看admin用户
			// pd.put("ROLE_ID", "1");
		List<Role> roleList = roleService.listAllRoles(pd); // 列出所有系统用户角色
		pd = userService.findByUsername(pd); // 根据ID读取
		mv.setViewName("system/user/user_view");
		mv.addObject("msg", "editU");
		mv.addObject("pd", pd);
		mv.addObject("roleList", roleList);
		return mv;
	}

	/**
	 * 去修改用户页面(在线管理页面打开)
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goEditUfromOnline")
	public ModelAndView goEditUfromOnline() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		if ("admin".equals(pd.getString("USERNAME"))) {
			return null;
		} // 不能查看admin用户
			// pd.put("ROLE_ID", "1");
		List<Role> roleList = roleService.listAllRoles(pd); // 列出所有系统用户角色
		pd = userService.findByUsername(pd); // 根据ID读取
		mv.setViewName("system/user/user_edit");
		mv.addObject("msg", "editU");
		mv.addObject("pd", pd);
		mv.addObject("roleList", roleList);
		return mv;
	}

	/**
	 * 修改用户 {PHONE=18706119455, NAME=1, USER_ID=7f7d2314eefa410584ad29049d51504d, QQ=123, tp=, chkpwd=2222, EMAIL=99@qq.com, USERNAME=s, PASSWORD=2222, BZ=sss, weChatId=123,
	 * ROLE_ID=68f8e4a39efe47c7bb869e9d15ab925d, NUMBER=s}
	 */
	@RequestMapping(value = "/editU")
	public ModelAndView editU(HttpServletRequest request, @RequestParam(value = "tp", required = false) MultipartFile[] files, @RequestParam(value = "PHONE", required = true) String PHONE,
			@RequestParam(value = "USER_ID", required = true) String USER_ID, @RequestParam(value = "NAME", required = true) String NAME, @RequestParam(value = "QQ", required = true) String QQ,
			@RequestParam(value = "EMAIL", required = true) String EMAIL, @RequestParam(value = "USERNAME", required = true) String USERNAME,
			@RequestParam(value = "weChatId", required = true) String weChatId, @RequestParam(value = "BZ", required = true) String BZ,
			@RequestParam(value = "ROLE_ID", required = true) String ROLE_ID, @RequestParam(value = "PASSWORD", required = true) String PASSWORD,
			@RequestParam(value = "NUMBER", required = true) String NUMBER, @RequestParam(value = "isQuit", required = true) String isQuit,
			@RequestParam(value = "quitTime", required = false) String quitTime, @RequestParam(value = "qnKey", required = true) String qnKey,
			@RequestParam(value = "pic", required = true) String pic, @RequestParam(value = "isUrl", required = true) String isUrl,
			@RequestParam(value = "originalPicName", required = true) String originalPicName) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "修改user");
		ModelAndView mv = this.getModelAndView();
		TimeUtil tu = new TimeUtil();
		PageData pd = new PageData();
		// pd.put("USER_ID", USER_ID);
		// pd.put("PHONE", PHONE);
		// pd.put("NAME", NAME);
		// pd.put("QQ", QQ);
		// pd.put("EMAIL", EMAIL);
		pd.put("USERNAME", USERNAME);
		pd.put("USER_ID", USER_ID);
		// pd.put("weChatId", weChatId);
		// pd.put("BZ", BZ);
		pd.put("PASSWORD", PASSWORD);
		pd.put("ROLE_ID", ROLE_ID);
		// pd.put("NUMBER", NUMBER);
		User user = new User();
		user.setPHONE(PHONE);
		user.setNAME(NAME);
		user.setUSER_ID(USER_ID);
		user.setQQ(QQ);
		user.setEMAIL(EMAIL);
		user.setUSERNAME(USERNAME);
		user.setWeChatId(weChatId);
		user.setBZ(BZ);
		user.setIsUrl(isUrl);
		user.setROLE_ID(ROLE_ID);
		user.setNUMBER(NUMBER);
		user.setIsQuit(isQuit);
		user.setQuitTime(quitTime);
		if (!Jurisdiction.getUsername().equals(pd.getString("USERNAME"))) { // 如果当前登录用户修改用户资料提交的用户名非本人
			if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
				return null;
			} // 校验权限 判断当前操作者有无用户管理查看权限
			if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
				return null;
			} // 校验权限判断当前操作者有无用户管理修改权限
			if ("admin".equals(pd.getString("USERNAME")) && !"admin".equals(Jurisdiction.getUsername())) {
				return null;
			} // 非admin用户不能修改admin
		} else { // 如果当前登录用户修改用户资料提交的用户名是本人，则不能修改本人的角色ID
			pd.put("ROLE_ID", userService.findByUsername(pd).getString("ROLE_ID")); // 对角色ID还原本人角色ID
		}
		if (pd.getString("PASSWORD") != null && !"".equals(pd.getString("PASSWORD"))) {
			/*pd.put("PASSWORD", new SimpleHash("SHA-1", pd.getString("USERNAME"), pd.getString("PASSWORD")).toString());*/
			user.setPASSWORD(new SimpleHash("SHA-1", pd.getString("USERNAME"), pd.getString("PASSWORD")).toString());
		}
		if ("1".equals(isQuit)) {
			// 销售精英
			if ("2".equals(this.getGroup(pd))) {
				pd.put("USER_ID", USER_ID);
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
			}
			// 内部运作
			/*if ("3".equals(this.getGroup(pd))) {
				pd.put("flag", "2");
				userService.updateOrderInfo(pd);
			}*/

			// 电访销售
			if ("1".equals(this.getGroup(pd))) {
				userService.updateKhgh(pd);
			}
		}
		if (null != files && files.length > 0) {
			// 循环获取file数组中得文件
			for (int i = 0; i < files.length; i++) {
				MultipartFile mfile = files[i];
				if (null != mfile && !mfile.isEmpty()) {
					CommonsMultipartFile cf = (CommonsMultipartFile) mfile;
					DiskFileItem fi = (DiskFileItem) cf.getFileItem();
					File file = fi.getStoreLocation();

					String filePath = file.getAbsolutePath();
					if (TextUtil.isNotNull(filePath)) {
						String dir = "baobeiluru/";
						String prefix = fi.getName().substring(fi.getName().lastIndexOf("."));
						String qnKey1 = dir + get32UUID() + prefix;
						// 上传到七牛云存储
						if (QiNiuUtil.upload(filePath, qnKey1, Const.QN_BUCKETNAME)) {
							String url = Const.QN_DOMAIN + qnKey1;
							String pic1 = QiNiuUtil.download(url);
							/*pd.put("qnKey", qnKey1);
							pd.put("pic", pic1);
							pd.put("originalPicName", fi.getName().substring(fi.getName().lastIndexOf("\\") + 1));*/
							user.setQnKey(qnKey1);
							user.setPic(pic1);
							user.setOriginalPicName(fi.getName().substring(fi.getName().lastIndexOf("\\") + 1));
						}
					}
				}
			}
		} else {
			/*			user.setQnKey(qnKey);
						user.setPic(pic);
						user.setOriginalPicName(originalPicName);*/
			user.setQnKey(qnKey);
			user.setPic(pic);
			user.setOriginalPicName(originalPicName);
		}
		/*pd = userService.findById(pd);
		if (!ROLE_ID.equals(pd.getString("ROLE_ID"))) {
			PageData pd4 = new PageData();
			pd4.put("userId", USER_ID);
			pd4.put("roleId", pd.getString("ROLE_ID"));
			pd4.put("time", tu.getNowTime("yyyy-MM"));
			pd = customerService.finSwgwBgInfo(pd4);
			if (null != pd) {
				customerService.upDateSwgwBgInfo(pd4);
			} else {
				customerService.saveSwgwBgInfo(pd4);
			}
		}*/
		userService.editU(user); // 执行修改
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	@RequestMapping(value = "/isQuit")
	@ResponseBody
	public Object isQuit() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData result = null;
		String msg = null;
		String error = null;
		try {
			PageData pd = this.getPageData();
			if ("1".equals(pd.getString("isQuit"))) {
				// 销售精英
				if ("2".equals(this.getGroup(pd))) {
					pd.put("USER_ID", pd.getString("USER_ID"));
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
				// 内部运作
				/*if ("3".equals(this.getGroup(pd))) {
					pd.put("flag", "2");
					userService.updateOrderInfo(pd);
				}*/

				// 电访销售
				if ("1".equals(this.getGroup(pd))) {
					userService.updateKhgh(pd);
				}
				User user = new User();
				user.setUSER_ID(pd.getString("USER_ID"));
				user.setIsQuit("1");
				userService.editU1(user);

				error = "00";
				msg = "离职成功";
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
	 * 批量删除
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAllU")
	@ResponseBody
	public Object deleteAllU() throws Exception {
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return null;
		} // 校验权限
		logBefore(logger, Jurisdiction.getUsername() + "批量删除user");
		PageData pd = new PageData();
		Map<String, Object> map = new HashMap<String, Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String USER_IDS = pd.getString("USER_IDS");
		if (null != USER_IDS && !"".equals(USER_IDS)) {
			String ArrayUSER_IDS[] = USER_IDS.split(",");
			userService.deleteAllU(ArrayUSER_IDS);
			pd.put("msg", "ok");
		} else {
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}

	/**
	 * 导出用户信息到EXCEL
	 * 
	 * @return
	 */
	@RequestMapping(value = "/excel")
	public ModelAndView exportExcel() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			if (Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
				String keywords = pd.getString("keywords"); // 关键词检索条件
				if (TextUtil.isNotNull(keywords)) {
					pd.put("keywords", new String(keywords.trim().getBytes("ISO8859-1"), "UTF-8"));
				}
				String lastLoginStart = pd.getString("lastLoginStart"); // 开始时间
				String lastLoginEnd = pd.getString("lastLoginEnd"); // 结束时间
				if (lastLoginStart != null && !"".equals(lastLoginStart)) {
					pd.put("lastLoginStart", lastLoginStart + " 00:00:00");
				}
				if (lastLoginEnd != null && !"".equals(lastLoginEnd)) {
					pd.put("lastLoginEnd", lastLoginEnd + " 23:59:59");
				}
				Map<String, Object> dataMap = new HashMap<String, Object>();
				List<String> titles = new ArrayList<String>();
				titles.add("用户名"); // 1
				titles.add("编号"); // 2
				titles.add("姓名"); // 3
				titles.add("职位"); // 4
				titles.add("手机"); // 5
				titles.add("邮箱"); // 6
				titles.add("最近登录"); // 7
				titles.add("上次登录IP"); // 8
				dataMap.put("titles", titles);
				List<PageData> userList = userService.listAllUser(pd);
				List<PageData> varList = new ArrayList<PageData>();
				for (int i = 0; i < userList.size(); i++) {
					PageData vpd = new PageData();
					vpd.put("var1", userList.get(i).getString("USERNAME")); // 1
					vpd.put("var2", userList.get(i).getString("NUMBER")); // 2
					vpd.put("var3", userList.get(i).getString("NAME")); // 3
					vpd.put("var4", userList.get(i).getString("ROLE_NAME")); // 4
					vpd.put("var5", userList.get(i).getString("PHONE")); // 5
					vpd.put("var6", userList.get(i).getString("EMAIL")); // 6
					vpd.put("var7", userList.get(i).getString("LAST_LOGIN")); // 7
					vpd.put("var8", userList.get(i).getString("IP")); // 8
					varList.add(vpd);
				}
				dataMap.put("varList", varList);
				ObjectExcelView erv = new ObjectExcelView(); // 执行excel操作
				mv = new ModelAndView(erv, dataMap);
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}

	/**
	 * 打开上传EXCEL页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goUploadExcel")
	public ModelAndView goUploadExcel() throws Exception {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("system/user/uploadexcel");
		return mv;
	}

	/**
	 * 下载模版
	 * 
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/downExcel")
	public void downExcel(HttpServletResponse response, HttpServletRequest request) throws Exception {
		FileDownload.fileDownload(response, request, PathUtil.getClasspath() + Const.FILEPATHFILE + "Users.xls", "Users.xls");
	}

	/**
	 * 从EXCEL导入到数据库
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/readExcel")
	public ModelAndView readExcel(@RequestParam(value = "excel", required = false) MultipartFile file) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		}
		if (null != file && !file.isEmpty()) {
			String filePath = PathUtil.getClasspath() + Const.FILEPATHFILE; // 文件上传路径
			String fileName = FileUpload.fileUp(file, filePath, "userexcel"); // 执行上传
			List<PageData> listPd = (List) ObjectExcelRead.readExcel(filePath, fileName, 2, 0, 0); // 执行读EXCEL操作,读出的数据导入List 2:从第3行开始；0:从第A列开始；0:第0个sheet
			/*存入数据库操作======================================*/
			pd.put("RIGHTS", ""); // 权限
			pd.put("LAST_LOGIN", ""); // 最后登录时间
			pd.put("IP", ""); // IP
			pd.put("STATUS", "0"); // 状态
			pd.put("SKIN", "default"); // 默认皮肤
			// pd.put("ROLE_ID", "1");
			List<Role> roleList = roleService.listAllRoles(pd);// 列出所有系统用户角色
			pd.put("ROLE_ID", roleList.get(0).getROLE_ID()); // 设置角色ID为随便第一个
			/**
			 * var0 :编号 var1 :姓名 var2 :手机 var3 :邮箱 var4 :备注
			 */
			for (int i = 0; i < listPd.size(); i++) {
				pd.put("USER_ID", this.get32UUID()); // ID
				pd.put("NAME", listPd.get(i).getString("var1")); // 姓名

				String USERNAME = GetPinyin.getPingYin(listPd.get(i).getString("var1")); // 根据姓名汉字生成全拼
				pd.put("USERNAME", USERNAME);
				if (userService.findByUsername(pd) != null) { // 判断用户名是否重复
					USERNAME = GetPinyin.getPingYin(listPd.get(i).getString("var1")) + Tools.getRandomNum();
					pd.put("USERNAME", USERNAME);
				}
				pd.put("BZ", listPd.get(i).getString("var4")); // 备注
				if (Tools.checkEmail(listPd.get(i).getString("var3"))) { // 邮箱格式不对就跳过
					pd.put("EMAIL", listPd.get(i).getString("var3"));
					if (userService.findByUE(pd) != null) { // 邮箱已存在就跳过
						continue;
					}
				} else {
					continue;
				}
				pd.put("NUMBER", listPd.get(i).getString("var0")); // 编号已存在就跳过
				pd.put("PHONE", listPd.get(i).getString("var2")); // 手机号

				pd.put("PASSWORD", new SimpleHash("SHA-1", USERNAME, "123").toString()); // 默认密码123
				if (userService.findByUN(pd) != null) {
					continue;
				}
				userService.saveU(pd);
			}
			/*存入数据库操作======================================*/
			mv.addObject("msg", "success");
		}
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 删除图片
	 * 
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value = "/deltp")
	public void deltp(PrintWriter out) throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		/*String PATH = pd.getString("PATH"); // 获取图片的映射路径
		String REALPATH=pd.getString("REALPATH");//获取图片的真实路径
		DelAllFile.delFolder(REALPATH); // 删除图片
		if (PATH != null&&REALPATH!=null) {*/
		userService.delTp(pd); // 删除数据库中图片数据
		/*	}*/
		out.write("success");
		out.close();
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
	}

	/**
	 * 选择系统用户列表
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/chooseUser")
	public ModelAndView chooseUserList(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		try {
			PageData pd = this.getPageData();
			String keywords = pd.getString("keywords"); // 关键词检索条件
			if (null != keywords && !"".equals(keywords)) {
				pd.put("keywords", keywords.trim());
			}
			String lastLoginStart = pd.getString("lastLoginStart"); // 开始时间
			String lastLoginEnd = pd.getString("lastLoginEnd"); // 结束时间
			if (lastLoginStart != null && !"".equals(lastLoginStart)) {
				pd.put("lastLoginStart", lastLoginStart + " 00:00:00");
			}
			if (lastLoginEnd != null && !"".equals(lastLoginEnd)) {
				pd.put("lastLoginEnd", lastLoginEnd + " 23:59:59");
			}
			String ROlE_ID = pd.getString("ROLE_ID");
			String[] roleIdArray = ROlE_ID.split(",");
			pd.put("ROLE_ID", ROlE_ID);
			pd.put("roleIds", Arrays.asList(roleIdArray));
			page.setPd(pd);
			List<PageData> userList = userService.listUsers(page); // 列出用户列表
			// pd.put("ROLE_ID", "1");
			// List<Role> roleList = roleService.listAllRoles(pd);// 列出所有系统用户角色
			mv.setViewName("system/user/chooseUser_list");
			mv.addObject("userList", userList);
			// mv.addObject("roleList", roleList);
			pd.put("lastLoginStart", lastLoginStart);
			pd.put("lastLoginEnd", lastLoginEnd);
			mv.addObject("pd", pd);
			mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	@RequestMapping(value = "/getUserInfo")
	@ResponseBody
	public Object getUserInfo() throws Exception {
		PageData pd = this.getPageData();
		String ROlE_ID = pd.getString("ROLE_ID");
		String[] roleIdArray = ROlE_ID.split(",");
		pd.put("key", pd.getString("data[q]"));
		if (!"".equals(Arrays.asList(roleIdArray).get(0))) {
			pd.put("roleIds", Arrays.asList(roleIdArray));
		}
		List<PageData> result = new ArrayList<PageData>();
		try {
			String pageNo = pd.getString("page");
			String pageSize = pd.getString("pageSize");
			int pNo = TextUtil.isNull(pageNo) ? 1 : Integer.parseInt(pageNo);
			int pSize = TextUtil.isNull(pageSize) ? 10 : Integer.parseInt(pageSize);
			PageHelper.startPage(pNo, pSize); // 核心分页代码
			result = userService.listAll(pd);
		} catch (Exception e) {
			logger.error(e);
		}
		return JSONArray.fromObject(result);
	}

	@RequestMapping(value = "/getUserInfo1")
	@ResponseBody
	public Object getUserInfo1() throws Exception {
		PageData pd = this.getPageData();
		String ROlE_ID = pd.getString("ROLE_ID");
		String[] roleIdArray = ROlE_ID.split(",");
		pd.put("key", pd.getString("data[q]"));
		if (!"".equals(Arrays.asList(roleIdArray).get(0))) {
			pd.put("roleIds", Arrays.asList(roleIdArray));
		}
		List<PageData> result = new ArrayList<PageData>();
		try {
			result = userService.listAll(pd);
		} catch (Exception e) {
			logger.error(e);
		}
		return JSONArray.fromObject(result);
	}
}
