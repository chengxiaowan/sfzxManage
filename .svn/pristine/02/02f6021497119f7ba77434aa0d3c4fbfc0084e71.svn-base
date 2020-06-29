package com.yocto.service.business.contractManage;

import java.util.List;
import java.util.Map;

import com.yocto.entity.Page;
import com.yocto.util.PageData;

public interface IContractService {

	List<PageData> list(Page page) throws Exception;

	/**
	 * 插入一条合同信息
	 * 
	 * @param pd
	 * @throws Exception
	 */
	void save(PageData pd) throws Exception;

	void saveAttach(List<Map<String, Object>> list) throws Exception;

	PageData findById(PageData pd) throws Exception;

	void saveAttach1(PageData map) throws Exception;

	void deleteAttach(List<String> list2) throws Exception;

	void update(PageData pd) throws Exception;

	void delete(String[] arrayIds) throws Exception;

	List<PageData> listAll(PageData pd) throws Exception;

	List<PageData> list1(Page page) throws Exception;

	void updateCustomerSaleId(PageData pd) throws Exception;

	List<PageData> sslist(Page page) throws Exception;

	List<PageData> getDebtorInfo(PageData pd) throws Exception;

	PageData findById1(PageData pd) throws Exception;

	List<PageData> showAllContract(PageData pd) throws Exception;

	List<PageData> findOrderInfoByContractId(PageData pd) throws Exception;

}
