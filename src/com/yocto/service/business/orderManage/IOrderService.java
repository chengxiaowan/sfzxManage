package com.yocto.service.business.orderManage;

import java.util.List;
import java.util.Map;

import com.yocto.entity.Page;
import com.yocto.util.PageData;

public interface IOrderService {

	List<PageData> list(Page page) throws Exception;

	List<PageData> showCountryData(PageData pd) throws Exception;

	int saveDebtorAndCustomer(PageData pd) throws Exception;

	void save(PageData pd) throws Exception;

	PageData findById(PageData pd) throws Exception;

	void saveAttach(List<Map<String, Object>> list) throws Exception;

	void update(PageData pd) throws Exception;

	void updateLinks(PageData pd) throws Exception;

	void delteLinks(PageData pd) throws Exception;

	void saveAttach1(PageData map) throws Exception;

	void saveLinksAndOrder(PageData pd) throws Exception;

	void updatLinksAndOrder(PageData pd) throws Exception;

	void delteLinksAndOrder(PageData pd) throws Exception;

	void delete(String[] arrayIds) throws Exception;

	int updateGuiDang(PageData pd) throws Exception;

	int updateRun(PageData pd) throws Exception;

	void saveTotal(PageData pd) throws Exception;

	void updateTotal(PageData pd) throws Exception;

	PageData findTotalById(PageData pd) throws Exception;

	Object findByOrderNo(PageData pd) throws Exception;

	List<PageData> findReportInfo(PageData pd) throws Exception;

	List<PageData> findPayment(PageData pd) throws Exception;

	int saveProcess(PageData pd) throws Exception;

	List<PageData> findProcessInfo(PageData pd) throws Exception;

	List<PageData> finddebtor(PageData pd) throws Exception;

	List<PageData> findHkjh(PageData pd) throws Exception;

	int updateProcess(PageData pd) throws Exception;

	void saveDebtor(PageData pd) throws Exception;

	void updateDebtor(PageData pd) throws Exception;

	List<PageData> findAjjj(PageData pd) throws Exception;

	List<PageData> listAll(PageData pd) throws Exception;

	void saveLinkManInfo(PageData pd) throws Exception;

	void deleteAttachs(PageData pd) throws Exception;

	void updateDebtor1(PageData pd) throws Exception;

	void updateGjStatus(PageData pd) throws Exception;

	void saveCallInfo(PageData pd) throws Exception;

	void updateCallInfo(PageData pd) throws Exception;

	List<PageData> getMonthData(PageData pd) throws Exception;

	List<PageData> getXyhklist(PageData pd) throws Exception;

	void saveorUpdateMonthData(PageData pd) throws Exception;

	List<PageData> listMonthReport(Page page) throws Exception;

	PageData findMonthDataById(PageData pd) throws Exception;

	void updateCallInfo1(PageData pd1) throws Exception;

	List<PageData> findNoticInfo(PageData pd) throws Exception;

	void updateNoticInfo(PageData pd) throws Exception;

	List<PageData> showAllArea(List<String> qxId) throws Exception;

	List<PageData> listAll1(PageData pd) throws Exception;

}
