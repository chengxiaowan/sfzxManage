package com.yocto.service.business.customerManage.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yocto.dao.DaoSupport;
import com.yocto.entity.Page;
import com.yocto.service.business.customerManage.ICustomerService;
import com.yocto.util.PageData;

/**
 * 用户信息服务类
 * 
 * @author 修改时间：2015.11.2
 */
@Service("customerService")
public class CustomerService implements ICustomerService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 * 列表
	 * 
	 * @param PageData
	 * @return
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) dao.findForList("CustomerMapper.listPage", page);
	}

	/**
	 * 通过ID获取信息
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@Override
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("CustomerMapper.findById", pd);
	}

	/**
	 * 通过手机号获取信息
	 * 
	 * @param mobilePhone
	 * @throws Exception
	 */
	@Override
	public PageData findByMobilePhone(PageData pd) throws Exception {
		return (PageData) dao.findForObject("CustomerMapper.findByMobilePhone", pd);
	}

	/**
	 * 通过邮箱地址获取信息
	 * 
	 * @param emailAddress
	 * @throws Exception
	 */
	@Override
	public PageData findByName(PageData pd) throws Exception {
		return (PageData) dao.findForObject("CustomerMapper.findByName", pd);
	}

	/**
	 * 保存或修改
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public int saveOrUpdate(PageData pd) throws Exception {
		return dao.save("CustomerMapper.saveOrUpdate", pd);
	}

	/**
	 * 删除
	 * 
	 * @param ids
	 * @throws Exception
	 */
	public void delete(String[] ids) throws Exception {
		dao.delete("CustomerMapper.delete", ids);
	}

	/**
	 * 删除图片
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void delTp(PageData pd) throws Exception {
		dao.update("CustomerMapper.delTp", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> findByIds(String[] coustomerIds) throws Exception {
		return (List<PageData>) dao.findForList("CustomerMapper.findByIds", coustomerIds);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listAll(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("CustomerMapper.listAll", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listSaleCustomer(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("CustomerMapper.listSaleCustomer", pd);
	}

	@Override
	public void saveSaleCustomer(PageData pd) throws Exception {
		dao.save("CustomerMapper.saveSaleCustomer", pd);
	}

	@Override
	public void saveSaleLinkman(PageData pd) throws Exception {
		dao.save("CustomerMapper.saveSaleLinkman", pd);
	}

	@Override
	public PageData findSaleCustomerById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("CustomerMapper.findSaleCustomerById", pd);
	}

	@Override
	public void updateSaleId(PageData pd) throws Exception {
		dao.update("CustomerMapper.updateSaleId", pd);
	}

	@Override
	public void saveSaleProcess(PageData pd) throws Exception {
		dao.save("CustomerMapper.saveSaleProcess", pd);
	}

	@Override
	public int updateProcess(PageData pd) throws Exception {
		return dao.update("CustomerMapper.updateProcess", pd);
	}

	@Override
	public void updateKhgh(PageData pd) throws Exception {
		dao.update("CustomerMapper.updateKhgh", pd);
	}

	@Override
	public PageData findProcessByIdOrCallUuid(PageData pd) throws Exception {
		return (PageData) dao.findForObject("CustomerMapper.findProcessByIdOrCallUuid", pd);
	}

	@Override
	public void saveSaleProcessAbout(PageData pd) throws Exception {
		dao.save("CustomerMapper.saveSaleProcessAbout", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> findProcessInfo(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("CustomerMapper.findProcessInfo", pd);
	}

	@Override
	public void saveLogs(PageData pd) throws Exception {
		dao.save("CustomerMapper.saveLogs", pd);
	}

	@Override
	public void saveCustomer(PageData pd) throws Exception {
		dao.save("CustomerMapper.saveCustomer", pd);
	}

	@Override
	public PageData findAction(PageData pd1) throws Exception {
		return (PageData) dao.findForObject("CustomerMapper.findAction", pd1);
	}

	@Override
	public void updateKhgh1(List<String> list1) throws Exception {
		dao.update("CustomerMapper.updateKhgh1", list1);
	}

	@Override
	public int updateSaleCustomer(PageData pd) throws Exception {
		return dao.update("CustomerMapper.updateSaleCustomer", pd);
	}

	@Override
	public Object findName(PageData pd) throws Exception {
		return dao.findForObject("CustomerMapper.findName", pd);
	}

	@Override
	public void saveTasks(PageData pd) throws Exception {
		dao.save("CustomerMapper.saveTasks", pd);
	}

	@Override
	public void updateKhgh2(List<String> list1) throws Exception {
		dao.update("CustomerMapper.updateKhgh2", list1);
	}

	@Override
	public void updateTasks(PageData pd) throws Exception {
		dao.update("CustomerMapper.updateTasks", pd);
	}

	@Override
	public void delteTask(PageData pd) throws Exception {
		dao.delete("CustomerMapper.delteTask", pd);
	}

	@Override
	public PageData findTaskById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("CustomerMapper.findTaskById", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listQztask(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("CustomerMapper.listQztask", pd);
	}

	@Override
	public void deletes(String[] ids) throws Exception {
		dao.delete("CustomerMapper.deletes", ids);
	}

	@Override
	public void updateCustomer(PageData pd) throws Exception {
		dao.update("CustomerMapper.updateCustomer", pd);
	}

	@Override
	public void saveLinkmans(List<Map<String, String>> list) throws Exception {
		dao.save("CustomerMapper.saveLinkmans", list);
	}

	@Override
	public void saveSaleProcess1(PageData pd) throws Exception {
		dao.save("CustomerMapper.saveSaleProcess1", pd);
	}

	@Override
	public PageData findYzProcessById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("CustomerMapper.findYzProcessById", pd);
	}

	@Override
	public int updateProcess1(PageData pd) throws Exception {
		return dao.update("CustomerMapper.updateProcess1", pd);
	}

	@Override
	public void updateHrghTime(PageData pd1) throws Exception {
		dao.update("CustomerMapper.updateHrghTime", pd1);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> findAllTask() throws Exception {
		return (List<PageData>) dao.findForList("CustomerMapper.findAllTask", null);
	}

	@Override
	public void updateHrghTime1(PageData pd) throws Exception {
		dao.update("CustomerMapper.updateHrghTime1", pd);
	}

	@Override
	public void saveSaleCustomers(List<Map<String, Object>> list) throws Exception {
		dao.save("CustomerMapper.saveSaleCustomers", list);
	}

	@Override
	public void saveSaleProcess1s(List<Map<String, Object>> list) throws Exception {
		dao.save("CustomerMapper.saveSaleProcess1s", list);
	}

	@Override
	public PageData findPhoneSale(PageData pd) throws Exception {
		return (PageData) dao.findForObject("CustomerMapper.findPhoneSale", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> findPhoneSaleByIds(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("CustomerMapper.findPhoneSaleByIds", pd);
	}

	@Override
	public void updateKhgh3(List<String> list2) throws Exception {
		dao.update("CustomerMapper.updateKhgh3", list2);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listAll1(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("CustomerMapper.listAll1", pd);
	}

	@Override
	public void saveNoticInfo(PageData pd3) throws Exception {
		dao.save("CustomerMapper.saveNoticInfo", pd3);
	}

	@Override
	public void updateKhgh5(PageData pd) throws Exception {
		dao.update("CustomerMapper.updateKhgh5", pd);
	}

	@Override
	public void updateOrderInfo(PageData pd) throws Exception {
		dao.update("CustomerMapper.updateOrderInfo", pd);
	}

	@Override
	public void updateKhgh4(List<String> list4) throws Exception {
		dao.update("CustomerMapper.updateKhgh4", list4);
	}

	@Override
	public PageData showProcessDetail(PageData pd) throws Exception {
		return (PageData) dao.findForObject("CustomerMapper.showProcessDetail", pd);
	}

	@Override
	public void saveEvaluate(PageData pd) throws Exception {
		dao.save("CustomerMapper.saveEvaluate", pd);
	}

	@Override
	public void deleteSaleReport(PageData pd) throws Exception {
		dao.delete("CustomerMapper.deleteSaleReport", pd);
	}

	@Override
	public PageData showProcessDetail1(PageData pd) throws Exception {
		return (PageData) dao.findForObject("CustomerMapper.showProcessDetail1", pd);
	}

	@Override
	public void deleteAttach(PageData pd) throws Exception {
		dao.delete("CustomerMapper.deleteAttach", pd);
	}

	@Override
	public PageData findRoleId(PageData pd) throws Exception {
		return (PageData) dao.findForObject("CustomerMapper.findRoleId", pd);
	}

	@Override
	public void delete(PageData pd) throws Exception {
		dao.delete("CustomerMapper.delete1", pd);
	}

	@Override
	public void update(PageData pd) throws Exception {
		dao.update("CustomerMapper.update", pd);
	}

	@Override
	public void save(PageData pd) throws Exception {
		dao.save("CustomerMapper.save", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listWords(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("CustomerMapper.listWords", pd);
	}

	@Override
	public PageData findLastCustomer(PageData pd) throws Exception {
		return (PageData) dao.findForObject("CustomerMapper.findLastCustomer", pd);
	}

	@Override
	public void saveSwgwProcess(PageData pd) throws Exception {
		dao.save("CustomerMapper.saveSwgwProcess", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> findProcess(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("CustomerMapper.findProcess", pd);
	}

	@Override
	public int updateName(PageData name) throws Exception {
		return dao.update("CustomerMapper.updateName", name);
	}

	@Override
	public void updateName1(PageData pd1) throws Exception {
		dao.update("CustomerMapper.updateName1", pd1);
	}

	@Override
	public PageData findDxswgw() throws Exception {
		return (PageData) dao.findForObject("CustomerMapper.findDxswgw", null);
	}

	@Override
	public void updateKhgh5(List<String> list5) throws Exception {
		dao.update("CustomerMapper.updateKhghc5", list5);
	}

	@Override
	public void updateKhgh6(List<String> list6) throws Exception {
		dao.update("CustomerMapper.updateKhgh6", list6);

	}

	@Override
	public void updateKhgh7(List<String> list7) throws Exception {
		dao.update("CustomerMapper.updateKhgh7", list7);
	}

	@Override
	public void updateNoticInfo(PageData pd4) throws Exception {
		dao.update("CustomerMapper.updateNoticInfo", pd4);
	}

	@Override
	public PageData findNoticInfo(PageData pd4) throws Exception {
		return (PageData) dao.findForObject("CustomerMapper.findNoticInfo", pd4);
	}

	@Override
	public void deleteNoticInfo(PageData pd4) throws Exception {
		dao.delete("CustomerMapper.deleteNoticInfo", pd4);
	}

	@Override
	public PageData showSwgwProcessDetail(PageData pd) throws Exception {
		return (PageData) dao.findForObject("CustomerMapper.showSwgwProcessDetail", pd);
	}

	@Override
	public PageData findSalecustomerIdById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("CustomerMapper.findSalecustomerIdById", pd);
	}

	@Override
	public void saveSwgwBgInfo(PageData pd4) throws Exception {
		dao.save("CustomerMapper.saveSwgwBgInfo", pd4);
	}

	@Override
	public PageData finSwgwBgInfo(PageData pd4) throws Exception {
		return (PageData) dao.findForObject("CustomerMapper.finSwgwBgInfo", pd4);
	}

	@Override
	public void upDateSwgwBgInfo(PageData pd4) throws Exception {
		dao.update("CustomerMapper.upDateSwgwBgInfo", pd4);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listSaleCustomer1(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("CustomerMapper.listSaleCustomer1", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listCustomerKhgh(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("CustomerMapper.listCustomerKhgh", pd);
	}

	@Override
	public void updateHrghTime2(PageData pd4) throws Exception {
		dao.update("CustomerMapper.updateHrghTime2",pd4);
	}

	@Override
	public void saveTemporyInfo(List<Map<String,Object>> lisP) throws Exception {
		dao.delete("CustomerMapper.deleteTemporyInfo",null);
		dao.save("CustomerMapper.saveTemporyInfo",lisP);
	}

	@Override
	public void updateHrghTime3() throws Exception {
		dao.update("CustomerMapper.updateHrghTime3",null);
		dao.delete("CustomerMapper.deleteTemporyInfo",null);
	}

}
