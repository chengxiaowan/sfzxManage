package com.yocto.service.business.orderManage.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yocto.dao.DaoSupport;
import com.yocto.entity.Page;
import com.yocto.service.business.orderManage.IOrderService;
import com.yocto.util.PageData;

@Service("orderService")
public class OrderService implements IOrderService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) dao.findForList("OrderMapper.listPage", page);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> showCountryData(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("OrderMapper.showCountryData", pd);
	}

	@Override
	public int saveDebtorAndCustomer(PageData pd) throws Exception {
		return dao.save("OrderMapper.saveDebtorAndCustomer", pd);
	}

	@Override
	public void save(PageData pd) throws Exception {
		dao.save("OrderMapper.save", pd);
	}

	@Override
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("OrderMapper.findById", pd);
	}

	@Override
	public void saveAttach(List<Map<String, Object>> list) throws Exception {
		dao.save("OrderMapper.saveAttach", list);
	}

	@Override
	public void update(PageData pd) throws Exception {
		dao.update("OrderMapper.update", pd);
	}

	@Override
	public void updateLinks(PageData pd) throws Exception {
		dao.update("OrderMapper.updateLinks", pd);
	}

	@Override
	public void delteLinks(PageData pd) throws Exception {
		dao.delete("OrderMapper.delteLinks", pd);
	}

	@Override
	public void saveAttach1(PageData pd) throws Exception {
		dao.save("OrderMapper.saveAttach1", pd);
	}

	@Override
	public void saveLinksAndOrder(PageData pd) throws Exception {
		dao.save("OrderMapper.saveLinksAndOrder", pd);
	}

	@Override
	public void updatLinksAndOrder(PageData pd) throws Exception {
		dao.update("OrderMapper.updatLinksAndOrder", pd);
	}

	@Override
	public void delteLinksAndOrder(PageData pd) throws Exception {
		dao.delete("OrderMapper.delteLinksAndOrder", pd);
	}

	@Override
	public void delete(String[] arrayIds) throws Exception {
		dao.delete("OrderMapper.delete", arrayIds);
	}

	@Override
	public int updateGuiDang(PageData pd) throws Exception {
		return dao.update("OrderMapper.updateGuiDang", pd);
	}

	@Override
	public int updateRun(PageData pd) throws Exception {
		return dao.update("OrderMapper.updateRun", pd);
	}

	@Override
	public void saveTotal(PageData pd) throws Exception {
		dao.save("OrderMapper.saveTotal", pd);

	}

	@Override
	public void updateTotal(PageData pd) throws Exception {
		dao.update("OrderMapper.updateTotal", pd);
	}

	@Override
	public PageData findTotalById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("OrderMapper.findTotalById", pd);
	}

	@Override
	public Object findByOrderNo(PageData pd) throws Exception {
		return dao.findForObject("OrderMapper.findByOrderNo", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> findReportInfo(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("OrderMapper.findReportInfo", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> findPayment(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("OrderMapper.findPayment", pd);
	}

	@Override
	public int saveProcess(PageData pd) throws Exception {
		return dao.save("OrderMapper.saveProcess", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> findProcessInfo(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("OrderMapper.findProcessInfo", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> finddebtor(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("OrderMapper.finddebtor", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> findHkjh(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("OrderMapper.findHkjh", pd);
	}

	@Override
	public int updateProcess(PageData pd) throws Exception {
		return dao.update("OrderMapper.updateProcess", pd);
	}

	@Override
	public void saveDebtor(PageData pd) throws Exception {
		dao.save("OrderMapper.saveDebtor", pd);
	}

	@Override
	public void updateDebtor(PageData pd) throws Exception {
		dao.update("OrderMapper.updateDebtor", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> findAjjj(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("OrderMapper.findAjjj", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listAll(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("OrderMapper.listAll", pd);
	}

	@Override
	public void saveLinkManInfo(PageData pd) throws Exception {
		dao.save("OrderMapper.saveLinkManInfo", pd);
	}

	@Override
	public void deleteAttachs(PageData pd) throws Exception {
		dao.delete("OrderMapper.deleteAttachs", pd);
	}

	@Override
	public void updateDebtor1(PageData pd) throws Exception {
		dao.update("OrderMapper.updateDebtor1", pd);
	}

	@Override
	public void updateGjStatus(PageData pd) throws Exception {
		dao.update("OrderMapper.updateGjStatus", pd);
	}

	@Override
	public void saveCallInfo(PageData pd) throws Exception {
		dao.save("OrderMapper.saveCallInfo", pd);
	}

	@Override
	public void updateCallInfo(PageData pd) throws Exception {
		dao.update("OrderMapper.updateCallInfo", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getMonthData(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("OrderMapper.getMonthData", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getXyhklist(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("OrderMapper.getXyhklist", pd);
	}

	@Override
	public void saveorUpdateMonthData(PageData pd) throws Exception {
		dao.save("OrderMapper.saveorUpdateMonthData", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listMonthReport(Page page) throws Exception {
		return (List<PageData>) dao.findForList("OrderMapper.listMonthReportlistPage", page);
	}

	@Override
	public PageData findMonthDataById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("OrderMapper.findMonthDataById", pd);
	}

	@Override
	public void updateCallInfo1(PageData pd1) throws Exception {
		dao.update("OrderMapper.updateCallInfo1", pd1);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> findNoticInfo(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("OrderMapper.findNoticInfo", pd);
	}

	@Override
	public void updateNoticInfo(PageData pd) throws Exception {
		dao.update("OrderMapper.updateNoticInfo", pd);
	}

	// 根据市区id获取区县字段
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> showAllArea(List<String> qxId) throws Exception {
		return (List<PageData>) dao.findForList("OrderMapper.showAllArea", qxId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listAll1(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("OrderMapper.listAll1", pd);
	}
}
