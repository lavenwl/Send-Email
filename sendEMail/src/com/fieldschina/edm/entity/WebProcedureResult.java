package com.fieldschina.edm.entity;
/**
 * 记录web端定时更新数据到bi数据库的存储过程是否正确的执行完成，以及记录自己过程的开始时间结束时间到本地的类
 * 
 * @author  LAVEN  E-mail:lavenwl@gmail.com
 * @company FieldsChina.IT.BI
 * @version 创建时间：2014-9-25 下午2:43:40
 */
public class WebProcedureResult {
	private int id;					//数据ID
	private String businessDate;	// 业务数据时间（存储过程开始的时间）
	private int runFlag;			//执行的结果
	private int recordNum;			//此次跑批所处理的数据量
	private String addDate;			//本条数据等成插入时间
	private String wpStartTime;		//本地程序开始执行的时间
	private String wpEndTime;		//本地程序结束执行的时间
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBusinessDate() {
		return businessDate;
	}
	public void setBusinessDate(String businessDate) {
		this.businessDate = businessDate;
	}
	public int getRunFlag() {
		return runFlag;
	}
	public void setRunFlag(int runFlag) {
		this.runFlag = runFlag;
	}
	public int getRecordNum() {
		return recordNum;
	}
	public void setRecordNum(int recordNum) {
		this.recordNum = recordNum;
	}
	public String getAddDate() {
		return addDate;
	}
	public void setAddDate(String addDate) {
		this.addDate = addDate;
	}
	public String getWpStartTime() {
		return wpStartTime;
	}
	public void setWpStartTime(String wpStartTime) {
		this.wpStartTime = wpStartTime;
	}
	public String getWpEndTime() {
		return wpEndTime;
	}
	public void setWpEndTime(String wpEndTime) {
		this.wpEndTime = wpEndTime;
	}
	public String toString(){
		return "id:" + id + " businessDate:" + businessDate + " runFlag:" + runFlag + " recordNum:" + recordNum + " addDate:" + addDate + " wpStartTime:" + wpStartTime + " wpEndTime:" + wpEndTime;
	}
}
