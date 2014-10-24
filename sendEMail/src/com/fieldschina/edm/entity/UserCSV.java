package com.fieldschina.edm.entity;
/**
 * 生成userCSV表（用户信息表）的单条数据类型
 * 
 * @author  LAVEN  E-mail:lavenwl@gmail.com
 * @company FieldsChina.IT.BI
 * @version 创建时间：2014-7-8 下午2:10:17
 */
public class UserCSV {
	private String Username;	//用户名
	private String email;		//用户邮箱
	private String mobile;		//电话
	private int gender;			//性别（男：1；女：2）
	private String province;	//必填(省份)
	private String city;		//城市（与前面的省份是从属关系）
	private String brithday;	//生日(用于计算年龄，格式yyyy-mm-dd)
	private String registerDate;//注册日期
	public String getUsername() {
		return Username;
	}
	public void setUsername(String username) {
		Username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getBrithday() {
		return brithday;
	}
	public void setBrithday(String brithday) {
		this.brithday = brithday;
	}
	public String getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}
	public String toString(){
		return "email:" + email + " gender:" + gender + " mobile:" + mobile;
	}
}
