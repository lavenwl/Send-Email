package com.fieldschina.edm.entity;
/**
 * DMD回传文件实体类
 * 
 * @author  LAVEN  E-mail:lavenwl@gmail.com
 * @company FieldsChina.IT.BI
 * @version 创建时间：2014-7-23 下午3:43:23
 */
public class DMDReturnFile {
	private String dMDCampaignName;		//活动名称
	private String dMDMailingName;		//邮件名称
	private String dMDType;				//记录类型
	private String email;				//收件人地址
	private int id;						//活动id
	private String createDate;			//收件人记录创建时间
	private String dMDLogDate;			//记录产生时间
	private String dMDClickName;		//邮件中链接的名字
	private String dMDClickUrl;			//链接的地址
	private String dMDBounceMessage;	//邮件回弹产生的讯息
	private String dMDipAddress;		//操作人的IP地址
	private String dMDClient;			//查看邮件的客户端和平台
	public String getdMDCampaignName() {
		return dMDCampaignName;
	}
	public void setdMDCampaignName(String dMDCampaignName) {
		this.dMDCampaignName = dMDCampaignName;
	}
	public String getdMDMailingName() {
		return dMDMailingName;
	}
	public void setdMDMailingName(String dMDMailingName) {
		this.dMDMailingName = dMDMailingName;
	}
	public String getdMDType() {
		return dMDType;
	}
	public void setdMDType(String dMDType) {
		this.dMDType = dMDType;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getdMDLogDate() {
		return dMDLogDate;
	}
	public void setdMDLogDate(String dMDLogDate) {
		this.dMDLogDate = dMDLogDate;
	}
	public String getdMDClickName() {
		return dMDClickName;
	}
	public void setdMDClickName(String dMDClickName) {
		this.dMDClickName = dMDClickName;
	}
	public String getdMDClickUrl() {
		return dMDClickUrl;
	}
	public void setdMDClickUrl(String dMDClickUrl) {
		this.dMDClickUrl = dMDClickUrl;
	}
	public String getdMDBounceMessage() {
		return dMDBounceMessage;
	}
	public void setdMDBounceMessage(String dMDBounceMessage) {
		this.dMDBounceMessage = dMDBounceMessage;
	}
	public String getdMDipAddress() {
		return dMDipAddress;
	}
	public void setdMDipAddress(String dMDipAddress) {
		this.dMDipAddress = dMDipAddress;
	}
	public String getdMDClient() {
		return dMDClient;
	}
	public void setdMDClient(String dMDClient) {
		this.dMDClient = dMDClient;
	}
	@Override
	public String toString() {
		return "DMDReturnFile [dMDCampaignName=" + dMDCampaignName
				+ ", dMDMailingName=" + dMDMailingName + ", dMDType=" + dMDType
				+ ", email=" + email + ", id=" + id + ", createDate="
				+ createDate + ", dMDLogDate=" + dMDLogDate + ", dMDClickName="
				+ dMDClickName + ", dMDClickUrl=" + dMDClickUrl
				+ ", dMDBounceMessage=" + dMDBounceMessage + ", dMDipAddress="
				+ dMDipAddress + ", dMDClient=" + dMDClient + "]";
	}
	
}
