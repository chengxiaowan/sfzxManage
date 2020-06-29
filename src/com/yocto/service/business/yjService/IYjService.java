package com.yocto.service.business.yjService;

import java.util.List;

import com.yocto.entity.business.yjtcManage.YjtcRelate;
import com.yocto.util.PageData;

public interface IYjService {

	void addYjzb(PageData pd) throws Exception;

	void deleteYjzb(PageData pd) throws Exception;

	List<PageData> showYjzbDetail(PageData pd) throws Exception;

	void deleteYjzb1(PageData pd) throws Exception;

	void insertBatch(List<PageData> list1) throws Exception;

	List<PageData> showYjzbList(PageData pd) throws Exception;

	void addJxxz(PageData pd) throws Exception;

	List<PageData> showYjtcList() throws Exception;

	List<PageData> showYjxzDetail(PageData pd) throws Exception;

	void deleteYjxz(PageData pd) throws Exception;

	void deleteDx(PageData pd) throws Exception;

	List<PageData> showYDxzDetail(PageData pd) throws Exception;

	void insertBatchDx(List<PageData> list1) throws Exception;

	List<PageData> showTsyg(PageData pd) throws Exception;

	PageData findIsExsits(PageData pd) throws Exception;

	PageData findIsExsits1(PageData pd) throws Exception;

	PageData findIsExsits2(PageData pd) throws Exception;

	void addDxOrTs(PageData pd) throws Exception;

	List<PageData> showRoleList(PageData pd) throws Exception;

	void updateById(PageData pd) throws Exception;

	PageData findAllDetail(PageData pd) throws Exception;

	void deleteById(PageData pd) throws Exception;

	List<PageData> showTcgzList() throws Exception;

	List<PageData> showRoleListByFdzb(PageData pd) throws Exception;

	PageData findIsExsits3(PageData fdStandard) throws Exception;

	void saveTcgz(PageData pd1) throws Exception;

	void insertBatchTc(List<YjtcRelate> list) throws Exception;

	void updateTcgz(PageData pd1) throws Exception;

	void deleteAbout(PageData pd1) throws Exception;

	PageData findById(PageData pd) throws Exception;

	void deleteTcgzById(PageData pd) throws Exception;

	PageData getMoneyByUserId(PageData pd) throws Exception;

	List<PageData> showTcgzList1(PageData pd) throws Exception;

	List<PageData> showYgxzOrTc(PageData pd) throws Exception;

	List<PageData> findUserByGroup(PageData pd) throws Exception;

	PageData findDxByUserId(PageData user) throws Exception;

	PageData findDhlByUserId(PageData user) throws Exception;

	PageData findJe2(PageData pd1) throws Exception;

	void insertYgxzInfo(PageData zxz) throws Exception;

	List<PageData> getDktcGw(PageData pd) throws Exception;

	PageData findLastReportInfo(PageData pd) throws Exception;

	PageData findLastReportInfo1(PageData pd) throws Exception;

	void insertUserReportInfo(PageData report) throws Exception;

	PageData findLastReportInfo2(PageData pd) throws Exception;

	PageData findLastReportInfo3(PageData pd) throws Exception;

	void insertVisitInfo(PageData pd) throws Exception;

	PageData findCountVisit(PageData pdswgw) throws Exception;

	List<PageData> findJe3(PageData pd1) throws Exception;

	void insertYjzb(PageData pd) throws Exception;

	List<PageData> findTargetRole() throws Exception;

	void update(PageData userRoles) throws Exception;

	PageData findwxDkje(PageData user) throws Exception;

	List<PageData> findUserByGroup1() throws Exception;

	PageData findbfOrQd(PageData pd1) throws Exception;

	List<PageData> showNoticDetail(PageData pd) throws Exception;

	void updateIsSure(PageData pd) throws Exception;

	void updateXz(PageData pd) throws Exception;

	void updateYgxz(PageData pd1) throws Exception;

	List<PageData> showSwgw(PageData pd) throws Exception;

}
