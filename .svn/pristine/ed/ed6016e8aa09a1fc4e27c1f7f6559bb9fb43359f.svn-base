package com.yocto.service.business.invoiceManage.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yocto.dao.DaoSupport;
import com.yocto.entity.Page;
import com.yocto.service.business.invoiceManage.IInvoiceService;
import com.yocto.util.PageData;

/**
 * 发票信息服务类
 * 
 * @author 修改时间：2015.11.2
 */
@Service("invoiceService")
public class InvoiceService implements IInvoiceService {

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
		return (List<PageData>) dao.findForList("InvoiceMapper.listPage", page);
	}

	/**
	 * 通过ID获取信息
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("InvoiceMapper.findById", pd);
	}

	/**
	 * 通过发票号码获取信息
	 * 
	 * @param mobilePhone
	 * @throws Exception
	 */
	@Override
	public PageData findByInvoiceCode(PageData pd) throws Exception {
		return (PageData) dao.findForObject("InvoiceMapper.findByInvoiceCode", pd);
	}

	/**
	 * 保存或修改
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public int saveOrUpdate(PageData pd) throws Exception {
		 return dao.save("InvoiceMapper.saveOrUpdate", pd);
	}

	/**
	 * 删除
	 * 
	 * @param ids
	 * @throws Exception
	 */
	public void delete(String[] ids) throws Exception {
		dao.delete("InvoiceMapper.delete", ids);
	}

	@Override
	public void updateTask(PageData pd) throws Exception {
		dao.update("InvoiceMapper.updateTask", pd);

	}
}
