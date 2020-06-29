package com.yocto.service.business.visitManage;

import java.util.List;

import com.yocto.entity.Page;
import com.yocto.util.PageData;

public interface IVisitService {

	List<PageData> list(Page page) throws Exception;

	void save(PageData pd) throws Exception;

	PageData findById(PageData pd) throws Exception;

	void update(PageData pd) throws Exception;

	void saveWarnInfo(PageData pd) throws Exception;

	void delete(String[] arrayIds) throws Exception;

	void updateWranInfo(PageData pd) throws Exception;

	void updateWranInfo1(PageData pd) throws Exception;

}
