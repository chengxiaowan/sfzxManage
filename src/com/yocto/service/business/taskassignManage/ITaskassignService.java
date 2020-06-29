package com.yocto.service.business.taskassignManage;

import java.util.List;

import com.yocto.entity.Page;
import com.yocto.util.PageData;

public interface ITaskassignService {

	List<PageData> listVisit(Page page) throws Exception;

	List<PageData> listOrderReport(Page page) throws Exception;

	void saveVisit(PageData pd) throws Exception;

	void saveTasks(PageData pd) throws Exception;

	void saveOrderReport(PageData pd) throws Exception;

	List<PageData> listdebtRoport(Page page) throws Exception;

	void saveDebt(PageData pd) throws Exception;

	List<PageData> listLawers(Page page) throws Exception;

	void saveLawyer(PageData pd) throws Exception;

	void update(PageData pd) throws Exception;

	void updateTasks(PageData pd) throws Exception;

	PageData findTasks(PageData pd) throws Exception;

	void deleteTasks(PageData pd) throws Exception;

}
