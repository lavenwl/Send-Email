package com.fieldschina.edm.entity;

import java.util.Date;

/**
 * 用户实例类
 * 
 * @author  LAVEN  E-mail:lavenwl@gmail.com
 * @company FieldsChina.IT.BI
 * @version 创建时间：2014-6-19 上午9:45:09
 */
public class Recipient {
	
	private int id;						//数据ID
	private int webId;					//web端的ID
	private int wpId;					//WP端的ID
	private String firstName;			//用户的名字
	private String lastName;			//用户的姓
	private int sex;					//用户的性别
	private String email;				//用户的email
	private String telephone;			//用户固定电话
	private String phone;				//用户移动电话
	private String registeredDate;		//用户注册时间
	private String firstOrderDate;		//用户首单时间
	private String lastOrderDate; 		//用户最近下单时间
	private String activeStatus; 		//用户活跃状态
	private String familyStatus;		//用户家庭情况
	private int birthdayYear;			//用户生日年
	private int birthdayMonth;  		//用户生日月
	private int birthdayDay;    		//用户生日天
	private String city;				//用户所在城市
	private String nationality1;		//人工归类国籍
	private String nationality2;		//注册国籍
	//private long addTime;				//数据添加时间
	private String exception;			//添加数据时异常
	//private boolean isSuccess;		//是否添加成功
	private String campaignIds; 		//用户适合上传的活动的ID的集合
	private int campaignId;				//数据上传错误时记录此条数据发生在那个活动下面
	private String groupIds;			//用户适合上传的活动下的组的Id的集合（存在不同活动下的组ID重复问题，现在不可用）
	private int membershipLevel;		//用户会员等级
	private String vip;					//是否为vip用户
	private String appService;			//订单来源
	private String order30Days;			//下单频率
	private String order60Days;			//下单频率
	private String order90Days;			//下单频率
	private int currentMonthOrderAmount;//当月下单金额
	private int totalOrderAmount;		//累计下单金额
	private int currentMonthOrderNumber;//当月下单数量
	private int TotalOrderNumber;		//累计下单数量
	private String department;			//内部邮件时使用：fields部门
	
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getCampaignId() {
		return campaignId;
	}
	public void setCampaignId(int campaignId) {
		this.campaignId = campaignId;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getCampaignIds() {
		return campaignIds;
	}
	public void setCampaignIds(String campaignIds) {
		this.campaignIds = campaignIds;
	}
	public String getGroupIds() {
		return groupIds;
	}
	public void setGroupIds(String groupIds) {
		this.groupIds = groupIds;
	}
	public String getVip() {
		return vip;
	}
	public void setVip(String vip) {
		this.vip = vip;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getWebId() {
		return webId;
	}
	public void setWebId(int webId) {
		this.webId = webId;
	}
	public int getWpId() {
		return wpId;
	}
	public void setWpId(int wpId) {
		this.wpId = wpId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getException() {
		return exception;
	}
	public void setException(String exception) {
		this.exception = exception;
	}
	public String getRegisteredDate() {
		return registeredDate;
	}
	public void setRegisteredDate(String registeredDate) {
		this.registeredDate = registeredDate;
	}
	public String getActiveStatus() {
		return activeStatus;
	}
	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}
	public String getFamilyStatus() {
		return familyStatus;
	}
	public void setFamilyStatus(String familyStatus) {
		this.familyStatus = familyStatus;
	}
	
	public int getMembershipLevel() {
		return membershipLevel;
	}
	public void setMembershipLevel(int membershipLevel) {
		this.membershipLevel = membershipLevel;
	}
	public String getAppService() {
		return appService;
	}
	public void setAppService(String appService) {
		this.appService = appService;
	}
	public int getCurrentMonthOrderAmount() {
		return currentMonthOrderAmount;
	}
	public void setCurrentMonthOrderAmount(int currentMonthOrderAmount) {
		this.currentMonthOrderAmount = currentMonthOrderAmount;
	}
	public int getTotalOrderAmount() {
		return totalOrderAmount;
	}
	public void setTotalOrderAmount(int totalOrderAmount) {
		this.totalOrderAmount = totalOrderAmount;
	}
	public int getCurrentMonthOrderNumber() {
		return currentMonthOrderNumber;
	}
	public void setCurrentMonthOrderNumber(int currentMonthOrderNumber) {
		this.currentMonthOrderNumber = currentMonthOrderNumber;
	}
	public int getTotalOrderNumber() {
		return TotalOrderNumber;
	}
	public void setTotalOrderNumber(int totalOrderNumber) {
		TotalOrderNumber = totalOrderNumber;
	}
	public int getBirthdayYear() {
		return birthdayYear;
	}
	public void setBirthdayYear(int birthdayYear) {
		this.birthdayYear = birthdayYear;
	}
	public int getBirthdayMonth() {
		return birthdayMonth;
	}
	public void setBirthdayMonth(int birthdayMonth) {
		this.birthdayMonth = birthdayMonth;
	}
	public int getBirthdayDay() {
		return birthdayDay;
	}
	public void setBirthdayDay(int birthdayDay) {
		this.birthdayDay = birthdayDay;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getNationality1() {
		return nationality1;
	}
	public void setNationality1(String nationality1) {
		this.nationality1 = nationality1;
	}
	public String getNationality2() {
		return nationality2;
	}
	public void setNationality2(String nationality2) {
		this.nationality2 = nationality2;
	}
	public String toString(){
		return "id:" + id + " webId:" + webId + " wpId:" + wpId + " firstName:" + firstName + " lastname:" + lastName + " Email:" + email + " exception:" 
				+ exception + " birthdayYear:" + birthdayYear + " birthdayMonth:" + birthdayMonth + " birthdayDay:" + birthdayDay + " registeredDate:" + registeredDate;
	}
	public String getFirstOrderDate() {
		return firstOrderDate;
	}
	public void setFirstOrderDate(String firstOrderDate) {
		this.firstOrderDate = firstOrderDate;
	}
	public String getLastOrderDate() {
		return lastOrderDate;
	}
	public void setLastOrderDate(String lastOrderDate) {
		this.lastOrderDate = lastOrderDate;
	}
	public String getOrder30Days() {
		return order30Days;
	}
	public void setOrder30Days(String order30Days) {
		this.order30Days = order30Days;
	}
	public String getOrder60Days() {
		return order60Days;
	}
	public void setOrder60Days(String order60Days) {
		this.order60Days = order60Days;
	}
	public String getOrder90Days() {
		return order90Days;
	}
	public void setOrder90Days(String order90Days) {
		this.order90Days = order90Days;
	}
}
