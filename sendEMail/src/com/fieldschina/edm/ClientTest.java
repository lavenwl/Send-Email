package com.fieldschina.edm;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import com.dmdelivery.webservice.*;
import com.dmdelivery.webservice.type.ArrayOfIntType;
import com.dmdelivery.webservice.type.DMdeliveryLoginType;
import com.dmdelivery.webservice.type.NewGroupType;
import com.dmdelivery.webservice.type.NewRecipientType;
import com.dmdelivery.webservice.type.RecipientNameValuePairType;
import com.dmdelivery.webservice.type.RecipientType;
import com.dmdelivery.webservice.type.RecordResultType;
import com.fieldschina.edm.config.Config;
import com.fieldschina.edm.dao.impl.ActionCSVDaoImpl;
import com.fieldschina.edm.dao.impl.ItemCSVDaoImpl;
import com.fieldschina.edm.dao.impl.RecipientDaoImpl;
import com.fieldschina.edm.dao.impl.UserCSVDaoImpl;
import com.fieldschina.edm.dao.impl.WebProcedureResultDaoImpl;
import com.fieldschina.edm.entity.ActionCSV;
import com.fieldschina.edm.entity.Campaign;
import com.fieldschina.edm.entity.ItemCSV;
import com.fieldschina.edm.entity.Recipient;
import com.fieldschina.edm.entity.UserCSV;
import com.fieldschina.edm.handler.IncrementRecipientHandler;
import com.fieldschina.edm.service.CampaignService;
import com.fieldschina.edm.service.DownLoadDMDReturnFileToODSService;
import com.fieldschina.edm.service.RecipientService;
import com.fieldschina.edm.util.Util;
/**
 * 代码功能测试类，正式工程不需要
 * 
 * @author  LAVEN  E-mail:lavenwl@gmail.com
 * @company FieldsChina.IT.BI
 * @version 创建时间：2014-7-23 下午2:28:36
 */
public class ClientTest {
	public static void main(String[] args) {
		
		RecipientService recipientService = new RecipientService();
		recipientService.sendGoodsRemindEmail(0,"", 0);
		
		
		
		
//		ItemCSVDaoImpl ItemCSVDaoImpl = new ItemCSVDaoImpl();
//		Map<Integer, Integer> map = ItemCSVDaoImpl.getAllProduct();
//		System.out.println("map:" + map.toString());
		
//		System.out.println("[1]".substring(1, "[1]".length()-1));
//		DownLoadDMDReturnFileToODSService downLoadDMDReturnFileToODSService = new DownLoadDMDReturnFileToODSService();
//		System.out.println(downLoadDMDReturnFileToODSService.downloadFiles());
		
//		int i = 0;
//		do{
//			i++;
//			System.out.println(i);
//		}while(i < 10);
		
//		System.out.println("EDMdmaCSVFileHandlerBeginTime:" + Util.getProperty("EDMdmaCSVFileHandlerBeginTime"));
//		System.out.println("DMAActionCSVInitNowTime:" + Util.getProperty("DMAActionCSVInitNowTime"));
//		Util.setProperty("DMAActionCSVInitNowTime", "2014-02-04");
//		System.out.println("DMAActionCSVInitNowTime:" + Util.getProperty("DMAActionCSVInitNowTime"));
		
//		System.out.println(Util.getDayTime());
		
//		String s = "t";
//		System.out.println(s.substring(0,0));
		
//		System.out.println("robbietest,mymmail,Sent,aaaaa@163.com,24617,,2012/3/31 13:50:00,,,,,d".split(",").length);
//		String s = "robbietest,mymmail,Sent,aaaaa@163.com,24617,,2012/3/31 13:50:00,,,,,fd";
//		String[] ss = (s + "t").split(",");
//		ss[ss.length - 1] = ss[ss.length - 1].substring(0, ss[ss.length - 1].length() - 1);
//		for(int i = 0; i < ss.length; i++){
//			System.out.println("i:" + i + " :" + ss[i]);
//		}
		
//		Util.backupFilesBeforeuploadFile();
//		Util.backupFileAfterdownloadFile();
		
		
//		UserCSVDaoImpl ucdi = new UserCSVDaoImpl();
//		List<UserCSV> list = ucdi.getAllUserCSVs();
//		System.out.println("list:" + list.size() + " " + list.get(1).toString());
		
		
		
//		ItemCSVDaoImpl icdi = new ItemCSVDaoImpl();
//		List<ItemCSV> list = icdi.getAllItemCSVs();
//		System.out.println("list:" + list.size() + " " + list.get(1).toString());
		
		
		
//		ActionCSVDaoImpl acdi = new ActionCSVDaoImpl();
//		List<ActionCSV> list = acdi.getAllActionCSVs();
//		System.out.println("list:" + list.size() + " " + list.get(1).toString());
		
		
		
//		System.out.println(Util.addIntToString("5,6,9,8,2", 200));
//		System.out.println(Util.paraseStringToInt(Util.addIntToString("5,6,9,8,2", 200)));
		
//		CampaignService cs = new CampaignService();
//		cs.updateCampaignsFromWp();
//		List<Campaign> list = cs.getCampaigns();
//		for(int i = 0; i < list.size(); i++){
//			System.out.println(list.get(i).toString());
//		}
		
		
		
//		WebProcedureResultDaoImpl webProcedureResultDaoImpl = new WebProcedureResultDaoImpl();
//		System.out.println(webProcedureResultDaoImpl.isProcedureFinished());
		
		
//		String s = "CNItemlist2014-07-14.csv";
//		System.out.println(s.substring(2, s.length()).substring(0, s.indexOf('2') - 2));
		
		
		
		
		
//		String[] text = new String[]{"dd","yy"};
//		
//		Util.begin("1");
//		System.out.println(Util.end("1"));

//		try {
//			String time = "yyyy-MM-dd " + Util.getProperty("incrementRecipientHandlerBeginTime");
//			System.out.println(time);
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd " + Util.getProperty("incrementRecipientHandlerBeginTime"));
//			Date startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sdf.format(new Date()));
//			System.out.println("starttime:" + startTime);
//			if (System.currentTimeMillis() > startTime.getTime()) {
//				System.out.println("addtime:" + Util.getTimeProperty("incrementRecipientHandlerSleepTime"));
//				startTime = new Date(startTime.getTime() + Util.getTimeProperty("incrementRecipientHandlerSleepTime"));
//			}
//			System.out.println("starttime:" + startTime);
//			Timer t = new Timer();
//			TimerTask task = new TimerTask() {
//				public void run() {
//					
//					System.out.println("runtime:" + System.currentTimeMillis());
//				}
//			};
//			t.scheduleAtFixedRate(task, startTime, Util.getTimeProperty("incrementRecipientHandlerSleepTime"));
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	
		
//		IncrementRecipientHandler ih = new IncrementRecipientHandler();
//		ih.run();
		
		
		
		
		
//		Recipient r = new Recipient();
//		r.setEmail("11ap12i@ap12i.com");
//		r.setWebId(100134);
//		r.setFirstName("firs2");
//		r.setLastName("last2");
//		r.setException("OK");
//		r.setSex(1);
//		RecipientDaoImpl rdi = new RecipientDaoImpl();
//		rdi.insertRecipient(r);
		
		
		
//		Date date = new Date();
//		System.out.println(date);
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		try {
//			long l = sdf.parse("2014-01-01 22:00:00").getTime();
//			System.out.println(l);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		String sf = sdf.format(date);
//		String[] ss = sf.split("-");
//		System.out.println(ss[0]);
//		System.out.println(ss[1]);
//		System.out.println(ss[2]);
//		System.out.println(date.toString());
//		System.out.println(date.getYear());
//		System.out.println(date.getMonth());
//		System.out.println(date.getDay());
		
		
		
		
//		Recipient r = new Recipient();
//		r.setEmail("11ap12i@ap12i.com");
//		r.setWebId(100134);
//		r.setFirstName("firs2");
//		r.setLastName("last2");
//		r.setSex(1);
//		Recipient rs = new Recipient();
//		rs.setEmail("11ap23i@api3.com");
//		rs.setWebId(100135);
//		rs.setFirstName("firstna3");
//		rs.setLastName("lastna3");
//		rs.setSex(2);
//		System.out.println(r.toString());
//		RecipientService rss = new RecipientService();
//		List<Recipient> list = new ArrayList<Recipient>();
//		list.add(r);
//		list.add(rs);
//		rss.addRecipients(list);
//		System.out.println("recipients完毕！");//list.size() + "/n" + list.toString());
		
		
		
//System.out.println(new java.sql.Date(new java.util.Date().getTime()));
//		for(int i = 0; i < 100; i++){
//			System.out.println(Config.getInstance().getProperty("campaignID"));
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		
//		try {
		// get an instance of the service
		DMdeliverySoapAPI API = new DMdeliverySoapAPI();
		DMdeliverySoapAPIPort service = API.getDMdeliverySoapAPIPort();
		// const variables
//		System.out.println("config:" + Config.getInstance().getProperty("username"));
		DMdeliveryLoginType login = new DMdeliveryLoginType("Wangwenlong", "Wangwenlong@123");
		int campaignId = 3; //Campaign ID
		int senderId = 1;
		// session variables
		RecordResultType result;
		int groupId=86;
		int mailingId = 100;
		int recipientId;
//		// create a new test group named Sample Group
//		NewGroupType group = new NewGroupType("Sample group2", true, "This group is added by an example2");
//		result = service.addGroup(login, campaignId, group);
//		System.out.println("result.status:" + result.getStatus() + "Error creating group " + result.getStatusMsg());
//		if (result.getStatus() == "ERROR")
//		throw new RuntimeException("Error creating group " + result.getStatusMsg());
//		else
//		groupId = result.getId(); // Group ID produced from database, group is used to group recipients addresses
//		System.out.println("得到的groupid:" + groupId);
		// add a single recipient
//		List<Integer> groupIdList = new ArrayList<Integer>();
//		groupIdList.add(groupId);
//		result = service.addRecipient(login,
//		campaignId,
//		new ArrayOfIntType(groupIdList),
//		new NewRecipientType("7517501@qq.com","Wang","Laven"),
//		true, // add duplicates to group
//		true); // overwrite existing recipients (matched on email)
//		if (result.getStatus() == "ERROR"){
//		throw new RuntimeException("Error creating group " + result.getStatusMsg());
//		}else{
//		recipientId = result.getId(); // return recipientid for send single mailing
//		}
		//Send single mailing
//		Boolean resul;
//		resul = service.sendSingleMailing(login, 3, 26, 2);
//		if(resul == false){
//		throw new RuntimeException("Error send single mailing.");
//		}
//		System.out.println("result:" + resul);
//		//import csv file, and then send email
//		RecordResultType result = service.importRemoteCSVSendMailing(
//		login,
//		907, //campaign id
//		89, //group id
//		"ftp://username:password@180.168.67.74/test.csv",
//		"utf-8", //csvCharset
//		",", //separator character
//		"\"", //enclosure character
//		true, //Whether or not to add this recipient when exist.
//		true, //overwrite
//		5097, //mail id
//		"robbie.wu@webpower.asia", //result mail
//		0, //filter id
//		new String[]{"cn"}, //lang
//		"", // (AD) prefix
//		"" //callback, email address or URL
//		);
//		// summarized statistics for a mailing sent
//		MailingStatsSummaryResultType result = service.getMailingStatsSummary(
//		login,
//		907, //campaign id
//		5097 //mail id
//		)
//		result.getTotal_opens() //open number
//		result.getTotal_delivered() //delivered number
//		...
//		..
		
//		EmailLogDao eld = new EmailLogDaoImpl();
//		List<EmailLog> list = eld.findEmailLogs();
//		if(list != null){
//			System.out.println("list.size:" + list.size());
//			System.out.println("list:" + list.toString());
//		}
		
		
		
		
//		System.out.println(getItemCSVList());
//		List<ItemCSV> list = getItemCSVList();
//		String str = "";
//		for(int i = 0; i < 1; i++){
//			ItemCSV item = list.get(i);
//			str = str + "\"name_" + (i + 1) + "\":\"" + item.getTitle() + "\",";
//			str = str + "\"image1_" + (i + 1) + "\":\"" + item.getImageUrl() + "\",";
//			str = str + "\"price1_" + (i + 1) + "\":\"" + item.getPrice1() + "\",";
//			str = str + "\"price2_" + (i + 1) + "\":\"" + item.getPrice2() + "\",";
//			str = str + "\"Link_" + (i + 1) + "\":\"" + item.getUrl() + "\",";
//		}
//		System.out.println(str);
//		str = str.substring(0, str.length() - 1);
//		System.out.println(str);	
//		str = "{" + str + "}";
//		System.out.println(str);
		
	}
	
	
	public static List<ItemCSV> getItemCSVList(){
		List<ItemCSV> list = new ArrayList<ItemCSV>();
		ItemCSV item = new ItemCSV();
		item.setItemID(1);
		item.setDesc1("desc1");
		item.setTitle("商品1");
		item.setPrice1(111);
		item.setPrice2(222);
		item.setUrl("http://www.fieldschina.com/zh/item-7286.html");
		item.setImageUrl("http://www.fieldschina.com/image/product/201406/1402912609989183.jpg");
		list.add(item);
		ItemCSV item2 = new ItemCSV();
		item2.setItemID(2);
		item2.setDesc1("desc2");
		item2.setTitle("商品2");
		item2.setPrice1(1112);
		item2.setPrice2(2222);
		item2.setUrl("http://www.fieldschina.com/zh/item-7286.html");
		item2.setImageUrl("http://www.fieldschina.com/image/product/201406/1402912609989183.jpg");
		list.add(item2);
		ItemCSV item3 = new ItemCSV();
		item3.setItemID(3);
		item3.setDesc1("desc3");
		item3.setTitle("商品3");
		item3.setPrice1(1113);
		item3.setPrice2(2223);
		item3.setUrl("http://www.fieldschina.com/zh/item-7286.html");
		item3.setImageUrl("http://www.fieldschina.com/image/product/201406/1402912609989183.jpg");
		list.add(item3);
		ItemCSV item4 = new ItemCSV();
		item4.setItemID(4);
		item4.setDesc1("desc4");
		item4.setTitle("商品4");
		item4.setPrice1(1114);
		item4.setPrice2(2224);
		item4.setUrl("http://www.fieldschina.com/zh/item-7286.html");
		item4.setImageUrl("http://www.fieldschina.com/image/product/201406/1402912609989183.jpg");
		list.add(item4);
		return list;
	}
	
	public static List<Recipient> getTestRecipientList(){
		List<Recipient> list = new ArrayList<Recipient>();
		Recipient r = new Recipient();
		r.setEmail("testAPI@testAPI.com");
		r.setWebId(1005551);
		r.setFirstName("firs2");
		r.setLastName("last2");
		r.setException("OK");
		r.setSex(1);
		list.add(r);
		Recipient r2 = new Recipient();
		r2.setEmail("training20140624@training20140624.com");
		r2.setWebId(1005552);
		r2.setFirstName("firs2");
		r2.setLastName("last2");
		r2.setException("OK");
		r2.setSex(1);
		list.add(r2);
		Recipient r3 = new Recipient();
		r3.setEmail("ALICE[TEST]@ALICE[TEST].com");
		r3.setWebId(1005553);
		r3.setFirstName("firs2");
		r3.setLastName("last2");
		r3.setException("OK");
		r3.setSex(1);
		list.add(r3);
		Recipient r4 = new Recipient();
		r4.setEmail("AlextestEvent@AlextestEvent.com");
		r4.setWebId(1005554);
		r4.setFirstName("firs2");
		r4.setLastName("last2");
		r4.setException("OK");
		r4.setSex(1);
		list.add(r4);
		Recipient r5 = new Recipient();
		r5.setEmail("5000Mail@5000Mail.com");
		r5.setWebId(1005555);
		r5.setFirstName("firs2");
		r5.setLastName("last2");
		r5.setException("OK");
		r5.setSex(1);
		list.add(r5);
		return list;
	}
}
