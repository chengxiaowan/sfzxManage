package com.yocto.entity.business.yjtcManage;

import java.util.List;

public class YjtcVo {

	private Integer id;
	private String name;
	private Integer tcStandard;
	private Integer fdStandard;
	private Integer yjStandard;
	private String roleId; // 岗位
	private List<YjtcRelate> yjtcList;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getTcStandard() {
		return tcStandard;
	}

	public void setTcStandard(Integer tcStandard) {
		this.tcStandard = tcStandard;
	}

	public Integer getFdStandard() {
		return fdStandard;
	}

	public void setFdStandard(Integer fdStandard) {
		this.fdStandard = fdStandard;
	}

	public Integer getYjStandard() {
		return yjStandard;
	}

	public void setYjStandard(Integer yjStandard) {
		this.yjStandard = yjStandard;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public List<YjtcRelate> getYjtcList() {
		return yjtcList;
	}

	public void setYjtcList(List<YjtcRelate> yjtcList) {
		this.yjtcList = yjtcList;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
