package com.fieldschina.edm.entity;
/**
 * 活动实体类
 * 
 * @author  LAVEN  E-mail:lavenwl@gmail.com
 * @company FieldsChina.IT.BI
 * @version 创建时间：2014-6-27 上午9:53:03
 */
public class Campaign {
	private int wpId;			//平台活动ID
	private String name;		//活动的名字
	private String groupIds;	//活动内的basic组的ID
	public String getGroupIds() {
		return groupIds;
	}
	public void setGroupIds(String groupIds) {
		this.groupIds = groupIds;
	}
	public int getWpId() {
		return wpId;
	}
	public void setWpId(int wpId) {
		this.wpId = wpId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String toString(){
		return "wpId:" + wpId + " name:" + name + " groupIds:" + groupIds;
	}
}
