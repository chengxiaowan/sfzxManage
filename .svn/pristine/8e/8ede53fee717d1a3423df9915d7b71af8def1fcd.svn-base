package com.yocto.service.system.user;

import java.util.List;

import com.yocto.entity.Page;
import com.yocto.entity.system.User;
import com.yocto.util.PageData;

/**
 * 用户接口类
 * 
 * @author 修改时间：2015.11.2
 */
public interface UserManager {

	/**
	 * 登录判断
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getUserByNameAndPwd(PageData pd) throws Exception;

	/**
	 * 更新登录时间
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void updateLastLogin(PageData pd) throws Exception;

	/**
	 * 通过用户ID获取用户信息和角色信息
	 * 
	 * @param USER_ID
	 * @return
	 * @throws Exception
	 */
	public User getUserAndRoleById(String USER_ID) throws Exception;

	/**
	 * 通过USERNAEME获取数据
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByUsername(PageData pd) throws Exception;

	/**
	 * 列出某角色下的所有用户
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listAllUserByRoldId(PageData pd) throws Exception;

	/**
	 * 保存用户IP
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void saveIP(PageData pd) throws Exception;

	/**
	 * 用户列表
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listUsers(Page page) throws Exception;

	/**
	 * 通过邮箱获取数据
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByUE(PageData pd) throws Exception;

	/**
	 * 通过编号获取数据
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByUN(PageData pd) throws Exception;

	/**
	 * 通过id获取数据
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findById(PageData pd) throws Exception;

	/**
	 * 修改用户
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void editU(PageData pd) throws Exception;

	/**
	 * 保存用户
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void saveU(PageData pd) throws Exception;

	/**
	 * 删除用户
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void deleteU(PageData pd) throws Exception;

	/**
	 * 批量删除用户
	 * 
	 * @param USER_IDS
	 * @throws Exception
	 */
	public void deleteAllU(String[] USER_IDS) throws Exception;

	/**
	 * 用户列表(全部)
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listAllUser(PageData pd) throws Exception;

	/**
	 * 获取总数
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public PageData getUserCount(String value) throws Exception;

	public void saveU(User user) throws Exception;

	public void editU(User user) throws Exception;

	public void delTp(PageData pd) throws Exception;

	public List<PageData> listAll(PageData pd) throws Exception;

	public int updatePassword(PageData pd) throws Exception;

	public void updateCustomerInfo(PageData pd) throws Exception;

	public void updateOrderInfo(PageData pd) throws Exception;

	public void updateKhgh(PageData pd) throws Exception;

	public void updateKhgh1(PageData pd) throws Exception;

	public void updateContractInfo(PageData pd) throws Exception;

	public void updateCustomerInfo1(PageData pd) throws Exception;

	public void editU1(User user) throws Exception;

}
