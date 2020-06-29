package com.yocto.service.business.contractManage.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yocto.dao.DaoSupport;
import com.yocto.entity.Page;
import com.yocto.service.business.contractManage.IContractService;
import com.yocto.util.PageData;

@Service("contractService")
public class ContractService implements IContractService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) dao.findForList("ContractMapper.listPage", page);
	}

	@Override
	public void save(PageData pd) throws Exception {
		dao.save("ContractMapper.save", pd);
	}

	@Override
	public void saveAttach(List<Map<String, Object>> list) throws Exception {
		dao.save("ContractMapper.saveAttach", list);
	}

	@Override
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("ContractMapper.findById", pd);
	}

	@Override
	public void saveAttach1(PageData map) throws Exception {
		dao.save("ContractMapper.saveAttach1", map);
	}

	@Override
	public void deleteAttach(List<String> list2) throws Exception {
		dao.delete("ContractMapper.deleteAttach", list2);
	}

	@Override
	public void update(PageData pd) throws Exception {
		dao.update("ContractMapper.update", pd);
	}

	@Override
	public void delete(String[] arrayIds) throws Exception {
		dao.delete("ContractMapper.delete", arrayIds);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listAll(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("ContractMapper.listAll", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> list1(Page page) throws Exception {
		return (List<PageData>) dao.findForList("ContractMapper.showlistPage", page);
	}

	@Override
	public void updateCustomerSaleId(PageData pd) throws Exception {
		dao.update("ContractMapper.updateCustomerSaleId", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> sslist(Page page) throws Exception {
		return (List<PageData>) dao.findForList("ContractMapper.sslistPage", page);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getDebtorInfo(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("ContractMapper.getDebtorInfo", pd);
	}

	@Override
	public PageData findById1(PageData pd) throws Exception {
		return (PageData) dao.findForObject("ContractMapper.findById1", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> showAllContract(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("ContractMapper.showAllContract", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> findOrderInfoByContractId(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("ContractMapper.findOrderInfoByContractId", pd);
	}

}
