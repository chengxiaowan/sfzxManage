package com.yocto.service.business.orderManage;

import java.util.List;

import com.yocto.entity.Page;
import com.yocto.util.PageData;

public interface IHandoverService {

	List<PageData> list(Page page) throws Exception;

	void save(PageData pd) throws Exception;

	PageData findById(PageData pd) throws Exception;

	void update(PageData pd) throws Exception;

	void delete(String[] arrayIds) throws Exception;

	void updateTask(PageData pd) throws Exception;

	List<PageData> listAll() throws Exception;

	void save1(PageData pd) throws Exception;

}
