package com.yocto.service.business.yjService.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yocto.dao.DaoSupport;
import com.yocto.entity.business.yjtcManage.YjtcRelate;
import com.yocto.service.business.yjService.IYjService;
import com.yocto.util.PageData;

@Service("yjService")
public class YjService implements IYjService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	@Override
	public void addYjzb(PageData pd) throws Exception {
		dao.save("YjMapper.addYjzb", pd);
	}

	@Override
	public void deleteYjzb(PageData pd) throws Exception {
		dao.delete("YjMapper.deleteYjzb", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> showYjzbDetail(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("YjMapper.showYjzbDetail", pd);
	}

	@Override
	public void deleteYjzb1(PageData pd) throws Exception {
		dao.delete("YjMapper.deleteYjzb1", pd);
	}

	@Override
	public void insertBatch(List<PageData> list1) throws Exception {
		dao.save("YjMapper.insertBatch", list1);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> showYjzbList(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("YjMapper.showYjzbList", pd);
	}

	@Override
	public void addJxxz(PageData pd) throws Exception {
		dao.save("YjMapper.addJxxz", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> showYjtcList() throws Exception {
		return (List<PageData>) dao.findForList("YjMapper.showYjtcList", null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> showYjxzDetail(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("YjMapper.showYjxzDetail", pd);
	}

	@Override
	public void deleteYjxz(PageData pd) throws Exception {
		dao.delete("YjMapper.deleteYjxz", pd);
	}

	@Override
	public void deleteDx(PageData pd) throws Exception {
		dao.delete("YjMapper.deleteDx", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> showYDxzDetail(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("YjMapper.showYDxzDetail", pd);
	}

	@Override
	public void insertBatchDx(List<PageData> list1) throws Exception {
		dao.save("YjMapper.insertBatchDx", list1);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> showTsyg(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("YjMapper.showTsyg", pd);
	}

	@Override
	public PageData findIsExsits(PageData pd) throws Exception {
		return (PageData) dao.findForObject("YjMapper.findIsExsits", pd);
	}

	@Override
	public PageData findIsExsits1(PageData pd) throws Exception {
		return (PageData) dao.findForObject("YjMapper.findIsExsits1", pd);
	}

	@Override
	public PageData findIsExsits2(PageData pd) throws Exception {
		return (PageData) dao.findForObject("YjMapper.findIsExsits2", pd);
	}

	@Override
	public void addDxOrTs(PageData pd) throws Exception {
		dao.save("YjMapper.addDxOrTs", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> showRoleList(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("YjMapper.showRoleList", pd);
	}

	@Override
	public void updateById(PageData pd) throws Exception {
		dao.update("YjMapper.updateById", pd);
	}

	@Override
	public PageData findAllDetail(PageData pd) throws Exception {
		return (PageData) dao.findForObject("YjMapper.findAllDetail", pd);
	}

	@Override
	public void deleteById(PageData pd) throws Exception {
		dao.delete("YjMapper.deleteById", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> showTcgzList() throws Exception {
		return (List<PageData>) dao.findForList("YjMapper.showTcgzList", null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> showRoleListByFdzb(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("YjMapper.showRoleListByFdzb", pd);
	}

	@Override
	public PageData findIsExsits3(PageData fdStandard) throws Exception {
		return (PageData) dao.findForObject("YjMapper.findIsExsits3", fdStandard);
	}

	@Override
	public void saveTcgz(PageData pd1) throws Exception {
		dao.save("YjMapper.saveTcgz", pd1);
	}

	@Override
	public void insertBatchTc(List<YjtcRelate> list) throws Exception {
		dao.save("YjMapper.insertBatchTc", list);
	}

	@Override
	public void updateTcgz(PageData pd1) throws Exception {
		dao.update("YjMapper.updateTcgz", pd1);
	}

	@Override
	public void deleteAbout(PageData pd1) throws Exception {
		dao.delete("YjMapper.deleteAbout", pd1);
	}

	@Override
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("YjMapper.findById", pd);
	}

	@Override
	public void deleteTcgzById(PageData pd) throws Exception {
		dao.delete("YjMapper.deleteTcgzById", pd);
	}

	@Override
	public PageData getMoneyByUserId(PageData pd) throws Exception {
		return (PageData) dao.findForObject("YjMapper.getMoneyByUserId", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> showTcgzList1(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("YjMapper.showTcgzList1", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> showYgxzOrTc(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("YjMapper.showYgxzOrTc", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> findUserByGroup(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("YjMapper.findUserByGroup", pd);
	}

	@Override
	public PageData findDxByUserId(PageData user) throws Exception {
		return (PageData) dao.findForObject("YjMapper.findDxByUserId", user);
	}

	@Override
	public PageData findDhlByUserId(PageData user) throws Exception {
		return (PageData) dao.findForObject("YjMapper.findDhlByUserId", user);
	}

	@Override
	public PageData findJe2(PageData pd1) throws Exception {
		return (PageData) dao.findForObject("YjMapper.findJe2", pd1);
	}

	@Override
	public void insertYgxzInfo(PageData zxz) throws Exception {
		dao.save("YjMapper.insertYgxzInfo", zxz);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getDktcGw(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("YjMapper.getDktcGw", pd);
	}

	@Override
	public PageData findLastReportInfo(PageData pd) throws Exception {
		return (PageData) dao.findForObject("YjMapper.findLastReportInfo", pd);
	}

	@Override
	public PageData findLastReportInfo1(PageData pd) throws Exception {
		return (PageData) dao.findForObject("YjMapper.findLastReportInfo1", pd);
	}

	@Override
	public void insertUserReportInfo(PageData report) throws Exception {
		dao.save("YjMapper.insertUserReportInfo", report);
	}

	@Override
	public PageData findLastReportInfo2(PageData pd) throws Exception {
		return (PageData) dao.findForObject("YjMapper.findLastReportInfo2", pd);
	}

	@Override
	public PageData findLastReportInfo3(PageData pd) throws Exception {
		return (PageData) dao.findForObject("YjMapper.findLastReportInfo3", pd);
	}

	@Override
	public void insertVisitInfo(PageData pd) throws Exception {
		dao.save("YjMapper.insertVisitInfo", pd);
	}

	@Override
	public PageData findCountVisit(PageData pdswgw) throws Exception {
		return (PageData) dao.findForObject("YjMapper.findCountVisit", pdswgw);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> findJe3(PageData pd1) throws Exception {
		return (List<PageData>) dao.findForList("YjMapper.findJe3", pd1);
	}

	@Override
	public void insertYjzb(PageData pd) throws Exception {
		dao.save("YjMapper.insertYjzb", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> findTargetRole() throws Exception {
		return (List<PageData>) dao.findForList("YjMapper.findTargetRole", null);
	}

	@Override
	public void update(PageData userRole) throws Exception {
		dao.update("YjMapper.update", userRole);
	}

	@Override
	public PageData findwxDkje(PageData user) throws Exception {
		return (PageData) dao.findForObject("YjMapper.findwxDkje", user);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> findUserByGroup1() throws Exception {
		return (List<PageData>) dao.findForList("YjMapper.findUserByGroup1", null);
	}

	@Override
	public PageData findbfOrQd(PageData pd1) throws Exception {
		return (PageData) dao.findForObject("YjMapper.findbfOrQd", pd1);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> showNoticDetail(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("YjMapper.showNoticDetail", pd);
	}

	@Override
	public void updateIsSure(PageData pd) throws Exception {
		dao.update("YjMapper.updateIsSure", pd);
	}

	@Override
	public void updateXz(PageData pd) throws Exception {
		dao.update("YjMapper.updateXz", pd);
	}

	@Override
	public void updateYgxz(PageData pd1) throws Exception {
		dao.update("YjMapper.updateYgxz", pd1);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> showSwgw(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("YjMapper.showSwgw", pd);
	}
}
