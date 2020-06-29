package com.yocto.entity.business.yjManage;

import java.util.List;

public class YjVo {

	private Integer parentId;
	private Integer type;
	private List<Yj> yjList;
	private String flag;

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public List<Yj> getYjList() {
		return yjList;
	}

	public void setYjList(List<Yj> yjList) {
		this.yjList = yjList;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

}
