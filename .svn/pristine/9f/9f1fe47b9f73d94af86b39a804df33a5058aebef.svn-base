package com.yocto.service.business.attachInfoManage.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yocto.dao.DaoSupport;
import com.yocto.service.business.attachInfoManage.IAttachInfoService;
import com.yocto.util.PageData;

/**
 * 附件信息服务类
 * 
 * @author 修改时间：2015.11.2
 */
@Service("attachService")
public class AttachInfoService implements IAttachInfoService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> findAttachByRelateId(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("AttachInfoMapper.findAttachByRelateId", pd);
	}

	/**
	 * 通过附件ID获取附件信息
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("AttachInfoMapper.findAttachById", pd);
	}

	@Override
	public int save(PageData pd) throws Exception {
		return dao.save("AttachInfoMapper.saveAttach", pd);
	}

	@Override
	public int save(List<Map<String, Object>> list) throws Exception {
		return dao.save("AttachInfoMapper.saveMultiAttach", list);
	}

	@Override
	public int delete(List<String> list) throws Exception {
		return dao.delete("AttachInfoMapper.deleteAttach", list);
	}
}
