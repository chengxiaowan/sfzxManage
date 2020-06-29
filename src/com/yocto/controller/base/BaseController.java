package com.yocto.controller.base;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.yocto.entity.Page;
import com.yocto.entity.system.Role;
import com.yocto.entity.system.User;
import com.yocto.service.system.role.RoleManager;
import com.yocto.service.system.user.UserManager;
import com.yocto.util.Const;
import com.yocto.util.Jurisdiction;
import com.yocto.util.Logger;
import com.yocto.util.PageData;
import com.yocto.util.QiNiuUtil;
import com.yocto.util.UuidUtil;
import com.yocto.util.context.SpringContextUtils;

/**
 * @author 修改时间：2015、12、11
 */
public class BaseController {

	protected Logger logger = Logger.getLogger(this.getClass());

	/**
	 * new PageData对象
	 * 
	 * @return
	 */
	public PageData getPageData() {
		return new PageData(this.getRequest());
	}

	/**
	 * 得到ModelAndView
	 * 
	 * @return
	 */
	public ModelAndView getModelAndView() {
		return new ModelAndView();
	}

	/**
	 * 得到request对象
	 * 
	 * @return
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
	}

	/**
	 * 得到32位的uuid
	 * 
	 * @return
	 */
	public String get32UUID() {
		return UuidUtil.get32UUID();
	}

	/**
	 * 得到分页列表的信息
	 * 
	 * @return
	 */
	public Page getPage() {
		return new Page();
	}

	public static void logBefore(Logger logger, String interfaceName) {
		logger.info("");
		logger.info("start");
		logger.info(interfaceName);
	}

	public static void logAfter(Logger logger) {
		logger.info("end");
		logger.info("");
	}

	/**
	 * 得到七牛上传的uploadToken
	 * 
	 * @return
	 */
	public String getUpToken(String bucketName) {
		return QiNiuUtil.getUpToken(bucketName);
	}

	/**
	 * 校验当前用户是否为roleId角色
	 * 
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public boolean isRole(String roleId) throws Exception {
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
		// 判断当前用户是否为客服
		boolean flag = false;
		if ("1".equals(user.getUSER_ID())) {
			flag = true;
		} else {
			PageData pd = new PageData();
			pd.put("ROLE_ID", roleId);
			UserManager userService = SpringContextUtils.getBean("userService", UserManager.class);
			if (null != userService) {
				List<PageData> userRoleList = userService.listAllUserByRoldId(pd);
				for (PageData tmp : userRoleList) {
					String userId = tmp.getString("USER_ID");
					if (userId.equals(user.getUSER_ID())) {
						flag = true;
						break;
					}
				}
			}
		}
		return flag;
	}

	// 获取某一员工的组(销售组、商务顾问组) return 1,2 0(代表其他组)
	public String getGroup() throws Exception {
		User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
		String flag = "0";
		// 判断当前用户所在的组
		RoleManager roleService = SpringContextUtils.getBean("roleService", RoleManager.class);
		if (null != roleService) {
			Role role = roleService.getRoleById(user.getROLE_ID());
			if (Const.ROLE_SALES_GROUP.equals(role.getPARENT_ID())) {
				flag = "1";
			}
			if (Const.ROLE_SWGWS_GROUP.equals(role.getPARENT_ID())) {
				flag = "2";
			}
			if (Const.ROLE_OPERATION_GROUP.equals(role.getPARENT_ID())) {
				flag = "3";
			}
			if (Const.ROLE_JLMANAGER_GROUP.equals(role.getPARENT_ID())) {
				flag = "4";
			}
		}
		return flag;
	}

	// 根据USER_ID判断当前用户所在的组
	public String getGroup(PageData pd) throws Exception {
		String flag = "0";
		RoleManager roleService = SpringContextUtils.getBean("roleService", RoleManager.class);
		UserManager userService = SpringContextUtils.getBean("userService", UserManager.class);
		if (null != roleService && null != userService) {
			PageData user = userService.findById(pd);
			Role role = roleService.getRoleById(user.getString("ROLE_ID"));
			if (Const.ROLE_SALES_GROUP.equals(role.getPARENT_ID())) {
				flag = "1";
			}
			if (Const.ROLE_SWGWS_GROUP.equals(role.getPARENT_ID())) {
				flag = "2";
			}
			if (Const.ROLE_OPERATION_GROUP.equals(role.getPARENT_ID())) {
				flag = "3";
			}
		}
		return flag;
	}
}
