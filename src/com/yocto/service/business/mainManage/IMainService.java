package com.yocto.service.business.mainManage;

import java.util.List;

import com.yocto.util.PageData;

public interface IMainService {

	PageData findCj(PageData pd) throws Exception;

	PageData findyyx(PageData pd) throws Exception;

	PageData findgj(PageData pd) throws Exception;

	PageData findyc(PageData pd) throws Exception;

	void saveSaleyc(PageData pd) throws Exception;

	Object findName(PageData pd) throws Exception;

	List<PageData> showSaleyc(PageData pd) throws Exception;

	List<PageData> findReportDetail(PageData pd) throws Exception;

	List<PageData> findVisitDetail(PageData pd) throws Exception;

	List<PageData> showRate(PageData pd) throws Exception;

	List<PageData> showXsld(PageData pd) throws Exception;

	List<PageData> showXszs(PageData pd) throws Exception;

	List<PageData> searchKeywords(PageData pd) throws Exception;

	List<PageData> showCustomer(PageData pd) throws Exception;

	PageData findDebtorDetail(PageData pd) throws Exception;

	void saveSaleyc1(PageData pd) throws Exception;

	/*PageData findyc1(PageData pd) throws Exception;*/

	List<PageData> findSalePhoneReport(PageData pd) throws Exception;

	List<PageData> showxsldDetail(PageData pd) throws Exception;

	List<PageData> showSwgwXstj(PageData pd) throws Exception;

	List<PageData> showSwgwYjmb(PageData pd) throws Exception;

	List<PageData> showYzsjdk(PageData pd) throws Exception;

	List<PageData> showDhlAndYxDhl(PageData pd) throws Exception;

	List<PageData> showYxkhs(PageData pd) throws Exception;

	List<PageData> showHt(PageData pd) throws Exception;

	List<PageData> findDhlAndYxkhsBySaleId(PageData pd) throws Exception;

	List<PageData> findSalePhoneReport1(PageData pd) throws Exception;

	List<PageData> showSwgwYjmb1(PageData pd) throws Exception;

	List<PageData> showYzsjdk1(PageData pd) throws Exception;

	List<PageData> showBfl(PageData pd) throws Exception;

	List<PageData> listbfls(PageData pd) throws Exception;

	List<PageData> showDxSwgwXstj(PageData pd) throws Exception;

	List<PageData> showDxSwgwYjmb1(PageData pd) throws Exception;

	int update(PageData pd) throws Exception;

	void insert(PageData pd) throws Exception;

	PageData findObject(PageData pd) throws Exception;

	List<PageData> findRoles(PageData pd) throws Exception;

}
