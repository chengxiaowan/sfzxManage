package com.yocto.service.business.customerManage;

import java.util.List;
import java.util.Map;

import com.yocto.entity.Page;
import com.yocto.util.PageData;

/**
 * 用户信息接口类
 * 
 * @author wxh
 *
 */
public interface ICustomerService {

	/**
	 * 列表
	 * 
	 * @param PageData
	 * @return
	 * @throws Exception
	 */
	public List<PageData> list(Page page) throws Exception;

	/**
	 * 通过ID获取信息
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public PageData findById(PageData pd) throws Exception;

	/**
	 * 通过手机号获取信息
	 * 
	 * @param mobilePhone
	 * @return
	 * @throws Exception
	 */
	public PageData findByMobilePhone(PageData pd) throws Exception;

	/**
	 * 通过邮箱地址获取信息
	 * 
	 * @param emailAddress
	 * @return
	 * @throws Exception
	 */
	public PageData findByName(PageData pd) throws Exception;

	/**
	 * 保存或修改
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public int saveOrUpdate(PageData pd) throws Exception;

	/**
	 * 删除
	 * 
	 * @param ids
	 * @throws Exception
	 */
	public void delete(String[] ids) throws Exception;

	/**
	 * 删除图片
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void delTp(PageData pd) throws Exception;

	public List<PageData> findByIds(String[] coustomerIds) throws Exception;

	public List<PageData> listAll(PageData pd) throws Exception;

	public List<PageData> listSaleCustomer(PageData pd) throws Exception;

	public void saveSaleCustomer(PageData pd) throws Exception;

	public void saveSaleLinkman(PageData pd) throws Exception;

	public PageData findSaleCustomerById(PageData pd) throws Exception;

	public void updateSaleId(PageData pd) throws Exception;

	public void saveSaleProcess(PageData pd) throws Exception;

	public int updateProcess(PageData pd) throws Exception;

	public void updateKhgh(PageData pd) throws Exception;

	public PageData findProcessByIdOrCallUuid(PageData pd) throws Exception;

	public void saveSaleProcessAbout(PageData pd) throws Exception;

	public List<PageData> findProcessInfo(PageData pd) throws Exception;

	public void saveLogs(PageData pd) throws Exception;

	public void saveCustomer(PageData pd) throws Exception;

	public PageData findAction(PageData pd1) throws Exception;

	public void updateKhgh1(List<String> list1) throws Exception;

	public int updateSaleCustomer(PageData pd) throws Exception;

	public Object findName(PageData pd) throws Exception;

	public void saveTasks(PageData pd) throws Exception;

	public void updateKhgh2(List<String> list1) throws Exception;

	public void updateTasks(PageData pd) throws Exception;

	public void delteTask(PageData pd) throws Exception;

	public PageData findTaskById(PageData pd) throws Exception;

	public List<PageData> listQztask(PageData pd) throws Exception;

	public void deletes(String[] ids) throws Exception;

	public void updateCustomer(PageData pd) throws Exception;

	public void saveLinkmans(List<Map<String, String>> list) throws Exception;

	public void saveSaleProcess1(PageData pd) throws Exception;

	public PageData findYzProcessById(PageData pd) throws Exception;

	public int updateProcess1(PageData pd) throws Exception;

	public void updateHrghTime(PageData pd1) throws Exception;

	public List<PageData> findAllTask() throws Exception;

	public void updateHrghTime1(PageData pd) throws Exception;

	public void saveSaleCustomers(List<Map<String, Object>> list) throws Exception;

	public void saveSaleProcess1s(List<Map<String, Object>> list2) throws Exception;

	public PageData findPhoneSale(PageData pd) throws Exception;

	public List<PageData> findPhoneSaleByIds(PageData pd) throws Exception;

	public void updateKhgh3(List<String> list2) throws Exception;

	public List<PageData> listAll1(PageData pd) throws Exception;

	public void saveNoticInfo(PageData pd3) throws Exception;

	public void updateKhgh5(PageData pd) throws Exception;

	public void updateOrderInfo(PageData pd) throws Exception;

	public void updateKhgh4(List<String> list4) throws Exception;

	public PageData showProcessDetail(PageData pd) throws Exception;

	public void saveEvaluate(PageData pd) throws Exception;

	public void deleteSaleReport(PageData pd) throws Exception;

	public PageData showProcessDetail1(PageData pd) throws Exception;

	public void deleteAttach(PageData pd) throws Exception;

	public PageData findRoleId(PageData pd) throws Exception;

	public void delete(PageData pd) throws Exception;

	public void update(PageData pd) throws Exception;

	public void save(PageData pd) throws Exception;

	public List<PageData> listWords(PageData pd) throws Exception;

	public PageData findLastCustomer(PageData pd) throws Exception;

	public void saveSwgwProcess(PageData pd) throws Exception;

	public List<PageData> findProcess(PageData pd) throws Exception;

	public int updateName(PageData name) throws Exception;

	public void updateName1(PageData pd1) throws Exception;

	public PageData findDxswgw() throws Exception;

	public void updateKhgh5(List<String> list5) throws Exception;

	public void updateKhgh6(List<String> list6) throws Exception;

	public void updateKhgh7(List<String> list7) throws Exception;

	public void updateNoticInfo(PageData pd4) throws Exception;

	public PageData findNoticInfo(PageData pd4) throws Exception;

	public void deleteNoticInfo(PageData pd4) throws Exception;

	public PageData showSwgwProcessDetail(PageData pd) throws Exception;

	public PageData findSalecustomerIdById(PageData pd) throws Exception;

	public void saveSwgwBgInfo(PageData pd4) throws Exception;

	public PageData finSwgwBgInfo(PageData pd4) throws Exception;

	public void upDateSwgwBgInfo(PageData pd4) throws Exception;

	public List<PageData> listSaleCustomer1(PageData pageData) throws Exception;

	public List<PageData> listCustomerKhgh(PageData pd) throws Exception;

	void updateHrghTime2(PageData pd4) throws Exception;

	void saveTemporyInfo(List<Map<String,Object>> lisP)throws Exception;

	void updateHrghTime3()throws Exception;
}
