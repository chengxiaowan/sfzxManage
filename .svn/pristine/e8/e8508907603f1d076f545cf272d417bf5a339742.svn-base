package com.yocto.service.business.mainManage.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yocto.dao.DaoSupport;
import com.yocto.service.business.mainManage.IMainService;
import com.yocto.util.PageData;

@Service("mainService")
public class MainService implements IMainService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	@Override
	public PageData findCj(PageData pd) throws Exception {
		return (PageData) dao.findForObject("MainMapper.findCj", pd);
	}

	@Override
	public PageData findyyx(PageData pd) throws Exception {
		return (PageData) dao.findForObject("MainMapper.findyyx", pd);
	}

	@Override
	public PageData findgj(PageData pd) throws Exception {
		return (PageData) dao.findForObject("MainMapper.findgj", pd);
	}

	@Override
	public PageData findyc(PageData pd) throws Exception {
		return (PageData) dao.findForObject("MainMapper.findyc", pd);
	}

	@Override
	public void saveSaleyc(PageData pd) throws Exception {
		dao.save("MainMapper.saveSaleyc", pd);
	}

	@Override
	public Object findName(PageData pd) throws Exception {
		return dao.findForObject("MainMapper.findName", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> showSaleyc(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("MainMapper.showSaleyc", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> findReportDetail(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("MainMapper.findReportDetail", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> findVisitDetail(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("MainMapper.findVisitDetail", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> showRate(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("MainMapper.showRate", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> showXsld(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("MainMapper.showXsld", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> showXszs(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("MainMapper.showXszs", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> searchKeywords(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("MainMapper.searchKeywords", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> showCustomer(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("MainMapper.showCustomer", pd);
	}

	@Override
	public PageData findDebtorDetail(PageData pd) throws Exception {
		return (PageData) dao.findForObject("MainMapper.findDebtorDetail", pd);
	}

	@Override
	public void saveSaleyc1(PageData pd) throws Exception {
		dao.save("MainMapper.saveSaleyc1", pd);
	}

	/*@Override
	public PageData findyc1(PageData pd) throws Exception {
		return (PageData) dao.findForObject("MainMapper.findyc1", pd);
	}
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> findSalePhoneReport(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("MainMapper.findSalePhoneReport", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> showxsldDetail(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("MainMapper.showxsldDetail", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> showSwgwXstj(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("MainMapper.showSwgwXstj", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> showSwgwYjmb(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("MainMapper.showSwgwYjmb", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> showYzsjdk(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("MainMapper.showYzsjdk", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> showDhlAndYxDhl(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("MainMapper.showDhlAndYxDhl", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> showYxkhs(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("MainMapper.showYxkhs", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> showHt(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("MainMapper.showHt", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> findDhlAndYxkhsBySaleId(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("MainMapper.findDhlAndYxkhsBySaleId", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> findSalePhoneReport1(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("MainMapper.findSalePhoneReport1", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> showSwgwYjmb1(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("MainMapper.showSwgwYjmb1", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> showYzsjdk1(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("MainMapper.showYzsjdk1", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> showBfl(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("MainMapper.showBfl", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listbfls(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("MainMapper.listbfls", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> showDxSwgwXstj(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("MainMapper.showDxSwgwXstj", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> showDxSwgwYjmb1(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("MainMapper.showDxSwgwYjmb1", pd);
	}

	@Override
	public int update(PageData pd) throws Exception {
		return dao.update("MainMapper.update", pd);
	}

	@Override
	public void insert(PageData pd) throws Exception {
		dao.save("MainMapper.insert", pd);
	}

	@Override
	public PageData findObject(PageData pd) throws Exception {
		return (PageData) dao.findForObject("MainMapper.findObject", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> findRoles(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("MainMapper.findRoles", pd);
	}

}
