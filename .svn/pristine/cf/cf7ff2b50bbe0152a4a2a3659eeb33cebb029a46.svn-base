package com.yocto.service.business.orderManage.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yocto.dao.DaoSupport;
import com.yocto.entity.Page;
import com.yocto.service.business.orderManage.IBackdetailService;
import com.yocto.util.PageData;

@Service("backdetailService")
public class BackdetailService implements IBackdetailService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) dao.findForList("BackdetailMapper.listPage", page);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listLawyer(Page page) throws Exception {
		return (List<PageData>) dao.findForList("BackdetailMapper.lawyerlistPag", page);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listPlan(Page page) throws Exception {
		return (List<PageData>) dao.findForList("BackdetailMapper.planlistPage", page);
	}
}
