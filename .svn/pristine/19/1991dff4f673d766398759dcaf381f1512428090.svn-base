package com.yocto.service.business.invoiceManage;

import java.util.List;

import com.yocto.entity.Page;
import com.yocto.util.PageData;

/**
 * 发票信息接口类
 * 
 * @author wxh
 *
 */
public interface IInvoiceService {

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
	 * 通过发票号码获取信息
	 * 
	 * @param mobilePhone
	 * @return
	 * @throws Exception
	 */
	public PageData findByInvoiceCode(PageData pd) throws Exception;

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

	public void updateTask(PageData pd) throws Exception;
}
