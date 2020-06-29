package com.yocto.service.system.logs.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yocto.dao.DaoSupport;
import com.yocto.entity.Page;
import com.yocto.service.system.logs.ILogsService;
import com.yocto.util.PageData;

@Service("logService")
public class LogsService implements ILogsService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listLogs(Page page) throws Exception {
		return (List<PageData>) dao.findForList("LogsMapper.listPage", page);
	}

	@Override
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("LogsMapper.findById", pd);
	}
}
