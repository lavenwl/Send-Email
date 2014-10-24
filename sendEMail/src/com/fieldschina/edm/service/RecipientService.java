package com.fieldschina.edm.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dmdelivery.webservice.type.AddRecipientsResultType;
import com.dmdelivery.webservice.type.ArrayOfIntType;
import com.dmdelivery.webservice.type.NewRecipientArrayType;
import com.dmdelivery.webservice.type.NewRecipientType;
import com.dmdelivery.webservice.type.RecipientArrayType;
import com.dmdelivery.webservice.type.RecipientNameValuePairType;
import com.dmdelivery.webservice.type.RecipientResultArrayType;
import com.dmdelivery.webservice.type.RecipientResultType;
import com.dmdelivery.webservice.type.RecipientType;
import com.dmdelivery.webservice.type.RecordResultType;
import com.fieldschina.edm.dao.RecipientDao;
import com.fieldschina.edm.dao.UserActionChangeDao;
import com.fieldschina.edm.dao.impl.RecipientDaoImpl;
import com.fieldschina.edm.dao.impl.UserActionChangeDaoImpl;
import com.fieldschina.edm.entity.ItemCSV;
import com.fieldschina.edm.entity.Recipient;
import com.fieldschina.edm.entity.Recipient2;
import com.fieldschina.edm.entity.UserActionChange;
import com.fieldschina.edm.util.Util;

/**
 * 用户操作逻辑类
 * 
 * @author  LAVEN  E-mail:lavenwl@gmail.com
 * @company FieldsChina.IT.BI
 * @version 创建时间：2014-6-19 下午5:52:55
 */
public class RecipientService extends Service{
	private static RecipientDao recipientDao = null;
	private static CampaignService campaignService = null;
	UserActionChangeService userActionChangeService = null;
	public UserActionChangeService getUserActionChangeService(){
		if(userActionChangeService == null)
			userActionChangeService = new UserActionChangeService();
		return userActionChangeService;
	}
	static{
		if(recipientDao == null){
			recipientDao = new RecipientDaoImpl();
		}
		if(campaignService == null){
			campaignService = new CampaignService(); 
		}
	}
	/**
	 * 增加单个的用户，并将增加记录保存至本地数据库
	 * 
	 * @param recipient	需要保存的用户实例
	 * @return			是否保存成功
	 */
	public boolean addRecipient(Recipient recipient){
		boolean b = false;
		try{
			// 初始化API需要的变量类型
			RecordResultType result = oFactory.createRecordResultType();	//返回结果类
			ArrayOfIntType groupIdList = oFactory.createArrayOfIntType();	//组ID的集合
			NewRecipientType nRecipient = oFactory.createNewRecipientType();//对象工厂类
			int campaignId = 3; 											//活动ID
			int groupId = 86;												//组ID
			//对变量进行赋值
			groupIdList.addGroup(groupId);
			nRecipient.setRecipientParameter("webid", recipient.getWebId());
			nRecipient.setRecipientParameter("email", recipient.getEmail());
			nRecipient.setRecipientParameter("firstname", recipient.getFirstName());
			nRecipient.setRecipientParameter("lastname", recipient.getLastName());
			nRecipient.setRecipientParameter("gender", recipient.getSex() == 1 ? "男" : (recipient.getSex() == 2 ? "女" : "未知"));
			nRecipient.setRecipientParameter("birthdayyear", recipient.getBirthdayYear());
			nRecipient.setRecipientParameter("birthdaymonth", recipient.getBirthdayMonth());
			nRecipient.setRecipientParameter("birthdayday", recipient.getBirthdayDay());
			nRecipient.setRecipientParameter("registereddate", recipient.getRegisteredDate());
			nRecipient.setRecipientParameter("city", recipient.getCity());
			nRecipient.setRecipientParameter("nationality1", recipient.getNationality1());
			nRecipient.setRecipientParameter("nationality2", recipient.getNationality2());
			nRecipient.setRecipientParameter("membership_level", recipient.getMembershipLevel());
			nRecipient.setRecipientParameter("vip", recipient.getVip());
			nRecipient.setRecipientParameter("first_order_date", recipient.getFirstOrderDate());
			nRecipient.setRecipientParameter("last_order_date", recipient.getLastOrderDate());
			nRecipient.setRecipientParameter("active_status", recipient.getActiveStatus());
			nRecipient.setRecipientParameter("app_service", recipient.getAppService());
			nRecipient.setRecipientParameter("order_frequency_30days", recipient.getOrder30Days());
			nRecipient.setRecipientParameter("order_frequency_60days", recipient.getOrder60Days());
			nRecipient.setRecipientParameter("order_frequency_90days", recipient.getOrder90Days());
			nRecipient.setRecipientParameter("family_status", recipient.getFamilyStatus());
			nRecipient.setRecipientParameter("current_month_order_amount", recipient.getCurrentMonthOrderAmount());
			nRecipient.setRecipientParameter("total_order_amount", recipient.getTotalOrderAmount());
			nRecipient.setRecipientParameter("current_month_order_number", recipient.getCurrentMonthOrderNumber());
			nRecipient.setRecipientParameter("total_order_number", recipient.getTotalOrderNumber());
			//发送API请求，接收返回结果
			result = service.addRecipient(login,			//登陆信息
											campaignId,		//活动ID
											groupIdList,	//组ID的集合
											nRecipient,		//新用户的信息
											true, 			// add duplicates to group
											true); 			// overwrite existing recipients (matched on email)
			//处理API返回的结果
			recipient.setException(result.getStatus());
			recipient.setCampaignId(campaignId);
			if (result.getStatus() == "ERROR"){
				log.error("添加新用户至DMD平台发生错误：" + result.getStatusMsg());
				recipient.setException(Util.sub200string(result.getStatusMsg()));
			}else{
				recipient.setWpId(result.getId()); // return recipientid for send single mailing
				//recipient.setSuccess(true);
				b = true;
			}
			if(b)
				b =addRecipientToLocalDatabase(recipient);
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.toString());
		}
		return b;
	}
	/**
	 * 批量增加用户，并将增加记录保存至本地数据库
	 * @param id 		活动ID
	 * 
	 * @param recipient	需要保存的用户实例
	 */
	public Map<String, Integer> addRecipients(int id, List<Recipient> recipientList){
		try{
			// 初始化API需要的变量类型
			Map<String, Integer> resultMap = new HashMap<String, Integer>();
			Map<Integer, Recipient> recipientMap = new HashMap<Integer, Recipient>();	//辅助数据结构
			AddRecipientsResultType result = oFactory.createAddRecipientsResultType();	//返回结果类
			ArrayOfIntType groupIdList = oFactory.createArrayOfIntType();				//组ID的集合
			NewRecipientType nRecipient = oFactory.createNewRecipientType();			//对象工厂类
			NewRecipientArrayType recipientL = oFactory.createNewRecipientArrayType();	//对象工厂类
			int campaignId = id; 														//活动ID
			int groupId = campaignService.getGroupByCampId(id);							//组ID
			log.debug("发送前得到的groupID：" + groupId);
			//对变量进行赋值
			groupIdList.addGroup(groupId);
			for(int i = 0; i < recipientList.size(); i++){
				Recipient recipient = recipientList.get(i);
				nRecipient = oFactory.createNewRecipientType();
				nRecipient.setRecipientParameter("webid", recipient.getWebId());
				nRecipient.setRecipientParameter("email", recipient.getEmail());
				nRecipient.setRecipientParameter("firstname", recipient.getFirstName());
				nRecipient.setRecipientParameter("lastname", recipient.getLastName());
				nRecipient.setRecipientParameter("gender", recipient.getSex() == 1 ? "男" : (recipient.getSex() == 2 ? "女" : "未知"));
				nRecipient.setRecipientParameter("birthdayyear", recipient.getBirthdayYear());
				nRecipient.setRecipientParameter("birthdaymonth", recipient.getBirthdayMonth());
				nRecipient.setRecipientParameter("birthdayday", recipient.getBirthdayDay());
				nRecipient.setRecipientParameter("registereddate", recipient.getRegisteredDate());
				nRecipient.setRecipientParameter("city", recipient.getCity());
				nRecipient.setRecipientParameter("nationality1", recipient.getNationality1());
				nRecipient.setRecipientParameter("nationality2", recipient.getNationality2());
				nRecipient.setRecipientParameter("membership_level", recipient.getMembershipLevel());
				nRecipient.setRecipientParameter("vip", recipient.getVip());
				nRecipient.setRecipientParameter("first_order_date", recipient.getFirstOrderDate());
				nRecipient.setRecipientParameter("last_order_date", recipient.getLastOrderDate());
				nRecipient.setRecipientParameter("active_status", recipient.getActiveStatus());
				nRecipient.setRecipientParameter("app_service", recipient.getAppService());
				nRecipient.setRecipientParameter("order_frequency_30days", recipient.getOrder30Days());
				nRecipient.setRecipientParameter("order_frequency_60days", recipient.getOrder60Days());
				nRecipient.setRecipientParameter("order_frequency_90days", recipient.getOrder90Days());
				nRecipient.setRecipientParameter("family_status", recipient.getFamilyStatus());
				nRecipient.setRecipientParameter("current_month_order_amount", recipient.getCurrentMonthOrderAmount());
				nRecipient.setRecipientParameter("total_order_amount", recipient.getTotalOrderAmount());
				nRecipient.setRecipientParameter("current_month_order_number", recipient.getCurrentMonthOrderNumber());
				nRecipient.setRecipientParameter("total_order_number", recipient.getTotalOrderNumber());
				recipientL.addRecipient(nRecipient);
				recipientMap.put(recipient.getWebId(), recipient);
			}
			//发送API请求，接收返回结果
			result = service.addRecipients(login,			//登陆信息
											campaignId,		//活动ID
											groupIdList,	//组ID的集合
											recipientL,		//新用户的集合信息
											true, 			// add duplicates to group
											true); 			// overwrite existing recipients (matched on email)
			//处理API返回结果
			Util.begin("addRecipientsReturn");
			log.debug("处理API返回结果:" + result.getStatus());
			resultMap.put("sum", recipientMap.size());//记录返回集合的数量统计并保存数据库
			if (result.getStatus().equals("ERROR")){
				//处理返回的成功的集合
				RecipientArrayType recipientArrayType = result.getSuccessful();
				if(recipientArrayType != null){
					List<RecipientType> recipientListResult = recipientArrayType.getRecipients();
					resultMap.put("success", recipientListResult.size());//记录返回集合的数量统计并保存数据库
//					for(int j = 0; j < recipientListResult.size(); j++){
//						Recipient r = null;
//						RecipientType recipientType = recipientListResult.get(j);
//						List<RecipientNameValuePairType> recipientNameValuePairTypeList = recipientType.getFields();
//						for(int t = 0; t < recipientNameValuePairTypeList.size(); t++){
//							RecipientNameValuePairType recipientNameValuePairType = recipientNameValuePairTypeList.get(t);
//							if(recipientNameValuePairType.getName().equals("webid")){
//								r = recipientMap.get(Integer.valueOf(recipientNameValuePairType.getValue()));
//							}
//						}
//						log.debug("if:success:r:" + r.getEmail());
//						if(r != null){
//							r.setException("OK");
//							r.setSuccess(true);
//							r.setWpId(recipientType.getId());
//							r.setCampaignId(campaignId);
//							boolean bo = addRecipientToLocalDatabase(r);
//							log.debug("bo:" + bo);
//						}
//					} // end of for(int j = 0; j < recipientListResult.size(); j++)
				} // end of if(recipientArrayType != null)
				//处理返回的重复的集合
				RecipientResultArrayType recipientResultArrayType = result.getDuplicates();
				if(recipientResultArrayType != null){
					List<RecipientResultType> recipientResultListResult = recipientResultArrayType.getRecipient();
					resultMap.put("dumplicate", recipientResultListResult.size());//记录返回集合的数量统计并保存数据库
//					for(int j = 0; j < recipientResultListResult.size(); j++){
//						Recipient r = null;
//						int index = 0;
//						RecipientResultType recipientResultType = recipientResultListResult.get(j);
//						List<RecipientNameValuePairType> recipientNameValuePairTypeList = recipientResultType.getFields();
//						for(int t = 0; t < recipientNameValuePairTypeList.size(); t++){
//							RecipientNameValuePairType recipientNameValuePairType = recipientNameValuePairTypeList.get(t);
//							if(recipientNameValuePairType.getName().equals("webid")){
//								r = recipientMap.get(Integer.valueOf(recipientNameValuePairType.getValue()));
//							}
//							if(recipientNameValuePairType.getName().equals("id")){
//								//log.debug("addRecipients返回的重复中存在ID属性：" + index);
//								index = Integer.valueOf(recipientNameValuePairType.getValue());
//							}
//						}
//						log.debug("if:dumplicate:r:" + r.getEmail());
//						if(r != null){
//							r.setException("DUMPLICATE");
//							r.setSuccess(true);
//							r.setWpId(index);
//							r.setCampaignId(campaignId);
//							boolean bo = addRecipientToLocalDatabase(r);
//							log.debug("bo:" + bo);
//						}else{
//							log.error("对象空指针异常！");
//						}
//					} // end of for(int j = 0; j < recipientResultListResult.size(); j++)
				} // end of if(recipientResultArrayType != null)
				//处理返回的错误的集合
				RecipientResultArrayType recipientResultArrayType2 = result.getErrors();
				if(recipientResultArrayType2 != null){
					List<RecipientResultType> recipientResultListResult2 = recipientResultArrayType2.getRecipient();
					resultMap.put("error", recipientResultListResult2.size());//记录返回集合的数量统计并保存数据库
					for(int j = 0; j < recipientResultListResult2.size(); j++){
						Recipient r = null;
						int index = 0;
						RecipientResultType recipientResultType = recipientResultListResult2.get(j);
						List<RecipientNameValuePairType> recipientNameValuePairTypeList = recipientResultType.getFields();
						for(int t = 0; t < recipientNameValuePairTypeList.size(); t++){
							RecipientNameValuePairType recipientNameValuePairType = recipientNameValuePairTypeList.get(t);
							if(recipientNameValuePairType.getName().equals("webid")){
								r = recipientMap.get(Integer.valueOf(recipientNameValuePairType.getValue()));
							}
							if(recipientNameValuePairType.getName().equals("id")){
								log.debug("addRecipients返回的重复中存在ID属性：" + index);
								index = Integer.valueOf(recipientNameValuePairType.getValue());
							}
						}
						//log.debug("if:error:r:" + r.getEmail());
						if(r != null){
							r.setException(Util.sub200string(recipientResultType.getDMDmessage()));
							//r.setSuccess(false);
							r.setWpId(index);
							r.setCampaignId(campaignId);
							boolean bo = addRecipientToLocalDatabase(r);
							//log.debug("bo:" + bo);
						}else{
							log.error("对象空指针异常！");
						}
					} // end of for(int j = 0; j < recipientResultListResult2.size(); j++)
				} // end of if(recipientResultArrayType2 != null)
			}else{ // else of if (result.getStatus() == "ERROR")
				//处理返回的成功的集合
				RecipientArrayType recipientArrayType = result.getSuccessful();
				if(recipientArrayType != null){
					List<RecipientType> recipientListResult = recipientArrayType.getRecipients();
					resultMap.put("success", recipientListResult.size());//记录返回集合的数量统计并保存数据库
//					for(int j = 0; j < recipientListResult.size(); j++){
//						Recipient r = null;
//						RecipientType recipientType = recipientListResult.get(j);
//						List<RecipientNameValuePairType> recipientNameValuePairTypeList = recipientType.getFields();
//						for(int t = 0; t < recipientNameValuePairTypeList.size(); t++){
//							RecipientNameValuePairType recipientNameValuePairType = recipientNameValuePairTypeList.get(t);
//							if(recipientNameValuePairType.getName().equals("webid")){
//								r = recipientMap.get(Integer.valueOf(recipientNameValuePairType.getValue()));
//							}
//						}
//						log.debug("else:success:r:" + r.getEmail());
//						if(r != null){
//							r.setException("OK");
//							r.setSuccess(true);
//							r.setWpId(recipientType.getId());
//							r.setCampaignId(campaignId);
//							boolean bo = addRecipientToLocalDatabase(r);
//							log.debug("bo:" + bo);
//						}
//					} // end of for(int j = 0; j < recipientListResult.size(); j++)
				} // end of if(recipientArrayType != null)
				//处理返回的重复的集合
				RecipientResultArrayType recipientResultArrayType = result.getDuplicates();
				if(recipientResultArrayType != null){
					//log.debug("得到重复集合：" + recipientResultArrayType.getRecipient().size());
					List<RecipientResultType> recipientResultListResult = recipientResultArrayType.getRecipient();
					resultMap.put("dumplicate", recipientResultListResult.size());//记录返回集合的数量统计并保存数据库
//					for(int j = 0; j < recipientResultListResult.size(); j++){
//						Recipient r = null;
//						int index = 0;
//						RecipientResultType recipientResultType = recipientResultListResult.get(j);
//						List<RecipientNameValuePairType> recipientNameValuePairTypeList = recipientResultType.getFields();
//						for(int t = 0; t < recipientNameValuePairTypeList.size(); t++){
//							RecipientNameValuePairType recipientNameValuePairType = recipientNameValuePairTypeList.get(t);
//							log.debug("重复的属性的value：" + recipientNameValuePairType.getValue());
//							if(recipientNameValuePairType.getName().equals("webid")){
//								r = recipientMap.get(Integer.valueOf(recipientNameValuePairType.getValue()));
//							}
//							if(recipientNameValuePairType.getName().equals("id")){
//								log.debug("addRecipients返回的重复中存在ID属性：" + index);
//								index = Integer.valueOf(recipientNameValuePairType.getValue());
//								log.debug("addRecipients返回的重复中存在ID属性：" + index);
//							}
//						}
//						log.debug("else:dumplicate:r:" + r.getEmail());
//						if(r != null){
//							r.setException("DUMPLICATE");
//							r.setSuccess(true);
//							r.setWpId(index);
//							r.setCampaignId(campaignId);
//							boolean bo = addRecipientToLocalDatabase(r);
//							log.debug("bo:" + bo);
//						}else{
//							log.error("对象空指针异常！");
//						} // end of if(r != null)
//					} // end of for(int j = 0; j < recipientResultListResult.size(); j++)
				} // end of if(recipientResultArrayType != null)
			} // end of if (result.getStatus() == "ERROR")
			log.debug("处理返回结果使用的时间：" + Util.end("addRecipientsReturn"));
			return resultMap;
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.toString());
		} // end of try{}catch(){}
		return null;
	}
	/**
	 * 将用户对象保存到本地数据库
	 * 
	 * @param recipient
	 * @return
	 */
	private boolean addRecipientToLocalDatabase(Recipient recipient) {
		return recipientDao.insertRecipient(recipient);
	}
	/**
	 * 得到每天新增用户的数据
	 * 
	 * @return
	 */
	public List<Recipient> findDailyRecipient(){
		return recipientDao.findDayNewRecipients();
	}
	/**
	 * 得到全部用户的数据
	 * 
	 * @return
	 */
	public List<Recipient> findAllRecipient(){
		return recipientDao.findAllRecipients();
	}
	/**
	 * 得到符合条件的用户数据
	 * 
	 * @return
	 */
	public List<Recipient> findMatchRecipient() {
		return recipientDao.findMatchRecipient();
	}
	/**
	 * 得到fields内部员工的数据（Email）
	 * 
	 * @return
	 */
	public List<Recipient> findFieldsEmail() {
		return recipientDao.findFieldsEmail();
	}
	/**
	 * 得到fields内部IT员工的数据（Email）
	 * 
	 * @return
	 */
	public List<Recipient> findFieldsITEmail() {
		return recipientDao.findFieldsEmail();
	}
	/**
	 * 得到当前月注册大于十四天的用户
	 * 
	 * @return
	 */
	public List<Recipient> findCurrentMonthRegistrationM14Days() {
		return recipientDao.findCurrentMonthRegistrationM14Days();
	}
	/**
	 * 得到当前月注册大于七天小于十四天的用户
	 * 
	 * @return
	 */
	public List<Recipient> findCurrentMonthRegistrationM7L14Days() {
		return recipientDao.findCurrentMonthRegistrationM7L14Days();
	}
	/**
	 * 得到需要到货提醒的用户的信息
	 * 
	 * @return
	 */
	public List<Recipient2> findRemindRecipients() {
		//返回的用户数据集合
		List<Recipient2> list = new ArrayList<Recipient2>();
		//查询到的用户基础数据数据集合
		List<UserActionChange> listuac = new ArrayList<UserActionChange>();
		//需要匹配的商品数据集合
		ItemCSVService itemCSVService = new ItemCSVService();
		List<ItemCSV> listItems = itemCSVService.getAllCNItemCSVs();
		//本周热门搜索
		String proList = itemCSVService.getProList();//得到中文的热门搜索
		log.debug("得到的本周热搜词汇：" + proList);
		//转换更适合匹配的map集合
		Map<Integer, ItemCSV> itemMap = new HashMap<Integer, ItemCSV>();
		for(int j = 0; j < listItems.size(); j++){
			itemMap.put(listItems.get(j).getItemID(), listItems.get(j));
		}
		UserActionChangeDao userActionChangeDao = new UserActionChangeDaoImpl();
		listuac = userActionChangeDao.findAllUserActionChangeFromLocal();
		UserActionChange userActionChange = new UserActionChange();
		for(int i = 0; i < listuac.size(); i++){
			userActionChange = listuac.get(i);
			if(userActionChange.getStoreRemind().length() >2){
				userActionChange.setStoreRemindList(Util.paraseStringToInt(userActionChange.getStoreRemind().substring(1, userActionChange.getStoreRemind().length()-1)));
				String products = getProductsName(userActionChange.getStoreRemindList(), itemMap);
				if(products.length() > 1 && userActionChange.getStoreRemindList().size() > 0 && itemMap.containsKey(userActionChange.getStoreRemindList().get(0))){//临时判断
					Recipient2 recipient2 = new Recipient2();
					if(proList.length() > 2){
						recipient2.setProList(proList);
					}
					recipient2.setEmail(userActionChange.getEmail());
					recipient2.setWarnProducts(products);
					for(int k = 0; k < userActionChange.getStoreRemindList().size(); k++){
						if(recipient2.getList() == null)
							recipient2.setList(new ArrayList());
						if(itemMap.get(userActionChange.getStoreRemindList().get(k)) != null)
							recipient2.getList().add(itemMap.get(userActionChange.getStoreRemindList().get(k)));
					}
					recipient2.setJsonProducts(getJsonProducts(recipient2.getList(), recipient2.getProList()));
					recipient2.setAddDate(Util.getDayTime());
					list.add(recipient2);
				}else{
					log.debug("存在异常数据，赠送或内卖或礼券产品被检测出来，直接删掉数据。userActionChange:" + userActionChange.toString());
					getUserActionChangeService().deleteStoreRemind(userActionChange.getEmail());
				}
			}
		}
		if(list.size() > 0)
			log.debug("返回的recipients2.size:" + list.size() + " list:" + list.get(0).toString() + "\n" + list.get(1).toString());
		return list;
	}
	/**
	 * 将list格式的数据转换成JSON格式的数据，（未完成，测试使用代码未删除）
	 * 
	 * @param list
	 * @param proStr 
	 * @return
	 */
	private String getJsonProducts(List<ItemCSV> list, String proStr) {
		String[] ss = proStr.split(",");
		String str = "";
		for(int i = 0; i < list.size(); i++){
//			for(int i = 0; i < 1; i++){//测试使用
			ItemCSV item = list.get(i);
			str = str + "\"name_" + (i + 1) + "\":\"" + (item.getTitle() == null ? "" : item.getTitle()) + "\",";
			str = str + "\"image1_" + (i + 1) + "\":\"" + item.getImageUrl() + "\",";
			str = str + "\"price1_" + (i + 1) + "\":\"" + item.getPrice1() + "\",";
			str = str + "\"price2_" + (i + 1) + "\":\"" + item.getPrice2() + "\",";
			str = str + "\"desc_" + (i + 1) + "\":\"" + item.getDesc1() + "\",";
			str = str + "\"Link_" + (i + 1) + "\":\"" + item.getUrl() + "\",";
		}
		if(ss.length > 3){
			for(int t = 0; t < ss.length; t++){
				str = str + "\"proStr_" + (t + 1) + "\":\"" + ss[t] + "\",";
			}
		}
		str = str.substring(0, str.length() - 1);
		str = "{" + str + "}";
		return str;
	}
	/**
	 * 得到各个商品的名称
	 * @param storeRemindList
	 * @param itemMap
	 * @return
	 */
	private String getProductsName(List<Integer> storeRemindList, Map<Integer, ItemCSV> itemMap) {
		String productName = "";
		for(int i = 0; i < storeRemindList.size(); i++){
			if(itemMap.containsKey(storeRemindList.get(i))){
				productName += ", " + itemMap.get(storeRemindList.get(i)).getTitle();
			}
		}
		if(productName.length() > 1)
			productName = productName.substring(2, productName.length());
		return productName;
	}
	/**
	 * 发送到货提醒邮件
	 * 
	 * @param list
	 */
	public void sendGoodsRemind(List<Recipient2> list) {
		if(!(list == null && list.size() <= 0)){
			for(int i = 0; i < list.size(); i++){
				Recipient2 recipient = list.get(i);
				int num = recipient.getList().size();
				log.debug("该用户需要提醒的商品的个数" + num);
				//更新收件人数据
				int index = addRecipient2(recipient);
				log.debug("更新了用户recipient.EMIAL:" + recipient.getEmail() + " 返回的ID：index:" + index);
				//发送邮件给收件人
				if(index != 0){
					sendGoodsRemindEmail(index, recipient.getEmail(), num);
				}
			}
		}
	}

	/**
	 * 发送到货提醒邮件
	 * @param num 
	 * 
	 * @param list
	 */
	public void sendGoodsRemindEmail(int index, String email, int num) {
		Boolean resul;
		log.debug("即将要发送的产品的信息：id：" + index + " email:" + email + " 产品个数：" + num + " 得到的ID：" + Integer.valueOf(Util.getProperty("EmailID" + num)));
		resul = service.sendSingleMailing(login, Integer.valueOf(Util.getProperty("campaignID")), 216, 2);//都临时发送到我的QQ邮箱
		if(resul == false){
			log.error("Error send single mailing.");
		}
		log.debug("发送结果：" + resul);
		if(resul == true){
			//到货提醒邮件发送成功
			//getUserActionChangeService().deleteStoreRemind(email);
			log.debug("这里删除用户的提醒数据，测试阶段不删除：Email：" + email);
		}
	}
	/**
	 * 增加单个的用户，并将增加记录保存至本地数据库
	 * 
	 * @param recipient	需要保存的用户实例
	 * @return			是否保存成功
	 */
	public int addRecipient2(Recipient2 recipient){
		int index = 0;
		boolean b = false;
		try{
			// 初始化API需要的变量类型
			RecordResultType result = oFactory.createRecordResultType();	//返回结果类
			ArrayOfIntType groupIdList = oFactory.createArrayOfIntType();	//组ID的集合
			NewRecipientType nRecipient = oFactory.createNewRecipientType();//对象工厂类
			int campaignId = 18; 											//活动ID
			int groupId = 81;												//组ID
			//对变量进行赋值
			groupIdList.addGroup(groupId);
			nRecipient.setRecipientParameter("warn_products", recipient.getWarnProducts());
			nRecipient.setRecipientParameter("email", recipient.getEmail());
			nRecipient.setRecipientParameter("json_products", recipient.getJsonProducts());
			log.debug("上传的一个用户(" + recipient.getEmail() + ")的JSON格式的数据：" + recipient.getJsonProducts());
			//发送API请求，接收返回结果
			result = service.addRecipient(login,			//登陆信息
											campaignId,		//活动ID
											groupIdList,	//组ID的集合
											nRecipient,		//新用户的信息
											true, 			// add duplicates to group
											true); 			// overwrite existing recipients (matched on email)
			if (result.getStatus() == "ERROR"){
				log.error("添加新用户至DMD平台发生错误：" + result.getStatusMsg());
			}else{
				index = result.getId(); // return recipientid for send single mailing
				b = true;
			}
			//待完善（将发送过得到货提醒保存到本地数据库）
//			if(b)
//				b =addRecipientToLocalDatabase(recipient);
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.toString());
		}
		return index;
	}
}
