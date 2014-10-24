package com.fieldschina.edm.entity;
/**
 * 增加用户的记录类
 * 
 * @author  LAVEN  E-mail:lavenwl@gmail.com
 * @company FieldsChina.IT.BI
 * @version 创建时间：2014-6-25 下午3:27:11
 */
public class RecipientResult {
	private int id;				//记录ID
	private long date;			//记录的日期
	private int sum;			//当日记录的总数
	private int error;			//当日记录的错误的数量
	private int success;		//当日记录的成功的数量
	private int dumplicate;		//当日记录的重复的数量
	private int campaignId;		//当日在这个活动上传的数量
	
	public int getCampaignId() {
		return campaignId;
	}
	public void setCampaignId(int campaignId) {
		this.campaignId = campaignId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public long getDate() {
		return date;
	}
	public void setDate(long date) {
		this.date = date;
	}
	
	public int getSum() {
		return sum;
	}
	public void setSum(int sum) {
		this.sum = sum;
	}
	public int getError() {
		return error;
	}
	public void setError(int error) {
		this.error = error;
	}
	public int getSuccess() {
		return success;
	}
	public void setSuccess(int success) {
		this.success = success;
	}
	public int getDumplicate() {
		return dumplicate;
	}
	public void setDumplicate(int dumplicate) {
		this.dumplicate = dumplicate;
	}
}
