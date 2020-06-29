package com.yocto.service.business.attachInfoManage;

import java.util.List;
import java.util.Map;

import com.yocto.util.PageData;

/**
 * 问题信息接口类
 * 
 * @author wxh
 *
 */
public interface IAttachInfoService {

	public List<PageData> findAttachByRelateId(PageData pd) throws Exception;

	/**
	 * 通过附件ID获取附件信息
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public PageData findById(PageData pd) throws Exception;

	/**
	 * 保存附件信息(单个)
	 * 
	 * @param map
	 * @throws Exception
	 */
	public int save(PageData map) throws Exception;

	/**
	 * 保存附件信息(多个)
	 * 
	 * @param list
	 * @throws Exception
	 */
	public int save(List<Map<String, Object>> list) throws Exception;

	/**
	 * 删除附件
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public int delete(List<String> list) throws Exception;
}
