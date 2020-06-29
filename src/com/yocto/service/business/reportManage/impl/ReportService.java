package com.yocto.service.business.reportManage.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yocto.dao.DaoSupport;
import com.yocto.service.business.reportManage.IReportService;
import com.yocto.util.PageData;

@Service("reportService")
public class ReportService implements IReportService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> findSalePhoneReport(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("ReportMapper.findSalePhoneReport", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> findReportDetail(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("ReportMapper.findReportDetail", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listFroecast(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("ReportMapper.listFroecast", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listAll(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("ReportMapper.listAll", pd);
	}

	@Override
	public void saveForecast(PageData pd) throws Exception {
		dao.save("ReportMapper.saveForecast", pd);
	}

	@Override
	public void updateForecast(PageData pd) throws Exception {
		dao.update("ReportMapper.updateForecast", pd);
	}

	@Override
	public PageData showXsycReport(PageData pd) throws Exception {
		return (PageData) dao.findForObject("ReportMapper.showXsycReport", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> showXsycReportDetail(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("ReportMapper.showXsycReportDetail", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> showXshkReport(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("ReportMapper.showXshkReport", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> showXshkReport1(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("ReportMapper.showXshkReport1", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> customerResource(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("ReportMapper.customerResource", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> customerResourceDetail(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("ReportMapper.customerResourceDetail", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> showXspmReport(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("ReportMapper.showXspmReport", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> showXspmReportDetail(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("ReportMapper.showXspmReportDetail", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> showHkjlReport(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("ReportMapper.showHkjlReport", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> showHkjlReportDetail(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("ReportMapper.showHkjlReportDetail", pd);
	}

	@Override
	public void saveReportData(PageData pd) throws Exception {
		dao.save("ReportMapper.saveReportData", pd);
	}

	@Override
	public void updateReportData(PageData pd) throws Exception {
		dao.update("ReportMapper.updateReportData", pd);
	}

	@Override
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("ReportMapper.findById", pd);
	}

	@Override
	public void deleteAboutAttach(PageData pd) throws Exception {
		dao.delete("ReportMapper.deleteAboutAttach", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> showReportInfo(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("ReportMapper.showReportInfo", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> ReportTj(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("ReportMapper.ReportTj", pd);
	}

	@Override
	public void saveDpEvaluate(PageData pd) throws Exception {
		dao.save("ReportMapper.saveDpEvaluate", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> showSwgwYjmb(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("ReportMapper.showSwgwYjmb", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> showSwgwVisitReport(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("ReportMapper.showSwgwVisitReport", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> showSwgwVisitReportDetail(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("ReportMapper.showSwgwVisitReportDetail", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> showImportantCustomer(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("ReportMapper.showImportantCustomer", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> showImportantCustomerResource(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("ReportMapper.showImportantCustomerResource", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> showDxyjReport(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("ReportMapper.showDxyjReport", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> showDxyjReportDetail(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("ReportMapper.showDxyjReportDetail", pd);
	}

}
