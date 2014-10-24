package com.fieldschina.edm.handler;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.fieldschina.edm.dbconn.DBConnectionManager;
import com.fieldschina.edm.email.Email;
import com.fieldschina.edm.email.SendMail;
import com.fieldschina.edm.email.Server;
import com.fieldschina.edm.entity.Recipient;
import com.fieldschina.edm.service.CSVService;
import com.fieldschina.edm.service.RecipientService;
import com.fieldschina.edm.util.Util;

/**
 * CSV文件相关操作类（此功能以迁至control工程）
 * 
 * @author  LAVEN  E-mail:lavenwl@gmail.com
 * @company FieldsChina.IT.BI
 * @version 创建时间：2014-7-3 下午3:39:15
 */
public class FieldsCSVFileHandler extends Handler{
	private static Map<String, String> map = Util.getCSVFileProperty();
	/**
	 * 计时器开始的方法
	 */
	public void Timer(){
		try {
			log.info("Timer start！");
			//开始时间的初始化
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd " + Util.getProperty("FieldsCSVFileHandlerBeginTime"));
			Date startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sdf.format(new Date()));
			//判断当前时间是否已经过了开始时间
			if (System.currentTimeMillis() > startTime.getTime()) {
				startTime = new Date(startTime.getTime() + Util.getTimeProperty("FieldsCSVFileHandlerSleepTime"));
			}
			//实例化一个定时器
			Timer t = new Timer();
			//定时器任务
			TimerTask task = new TimerTask() {
				public void run() {
					log.info("FieldsCSVFileHandler createCSVFile Timer Task start！");
					createCSVFile();
					sendCSVFile();
					try {
						DBConnectionManager d = DBConnectionManager.getInstance();
					} catch (Exception e) {
						log.error(Util.getTrace(e));
					}
					log.info("Timer task end！");
				}
			};
			t.scheduleAtFixedRate(task, startTime, Util.getTimeProperty("FieldsCSVFileHandlerSleepTime"));
		} catch (ParseException e) {
			log.error(Util.getTrace(e));
		}
	}
	/**
	 * 创建一个CSV文件
	 */
	public void createCSVFile() {
		//创建上月注册用户大于七天的用户
		if(map.containsKey("lastMonthRegistrationM7Days"))
			lastMonthRegistrationM7Days();
		//创建当月注册用户大于七天小于十四天的用户
		if(map.containsKey("currentMonthRegistrationM7L14Days"))
			currentMonthRegistrationM7L14Days();
		//创建当月注册时间大于十四天的用户
		if(map.containsKey("currentMonthRegistrationM14Days"))
			currentMonthRegistrationM14Days();
	}
	/**
	 * 创建当月注册时间大于十四天的用户
	 */
	private void currentMonthRegistrationM14Days() {
		RecipientService rs = new RecipientService();
		CSVService cs = new CSVService();
		//得到当月注册时间大于14天的数据
		List<Recipient> list = rs.findCurrentMonthRegistrationM14Days();
		log.debug("得到当月注册时间大于14天的数据CSV文件数量:" + list.size());
		//初始化文件数据
		List exportData = new ArrayList<Map>();
		for(int i = 0; i < list.size(); i++){
			Map row1 = new LinkedHashMap<String, String>();
	        row1.put("1", list.get(i).getWebId());
	        row1.put("2", list.get(i).getEmail());
	        row1.put("3", list.get(i).getFirstName());
	        row1.put("4", list.get(i).getLastName());
	        row1.put("5", list.get(i).getTelephone());
	        row1.put("6", list.get(i).getPhone());
	        row1.put("7", list.get(i).getRegisteredDate());
	        exportData.add(row1);
		}
        LinkedHashMap map = new LinkedHashMap();
        map.put("1", "id");
        map.put("2", "email");
        map.put("3", "firstname");
        map.put("4", "lastname");
        map.put("5", "telephone");
        map.put("6", "phone");
        map.put("7", "date_added");
        String path = System.getProperty("user.dir") + Util.getProperty("FieldsCSVOutPutPath");
        log.debug("path:" + path);
        String name = (String) this.map.get("currentMonthRegistrationM14Days");
        name += Util.getDay() + ".csv";
        this.map.put("currentMonthRegistrationM14Days", name);
        log.debug("filename:" + name);
		cs.createCSVFile(exportData, map, path, name);
	}
	/**
	 * 创建当月注册用户大于七天小于十四天的用户
	 */
	private void currentMonthRegistrationM7L14Days() {
		RecipientService rs = new RecipientService();
		CSVService cs = new CSVService();
		//得到当月注册时间大于7天的数据
		List<Recipient> list = rs.findCurrentMonthRegistrationM7L14Days();
		log.debug("得到当月注册时间大于7天的数据CSV文件数量:" + list.size());
		//初始化文件数据
		List exportData = new ArrayList<Map>();
		for(int i = 0; i < list.size(); i++){
			Map row1 = new LinkedHashMap<String, String>();
	        row1.put("1", list.get(i).getId());
	        row1.put("2", list.get(i).getEmail());
	        row1.put("3", list.get(i).getFirstName());
	        row1.put("4", list.get(i).getLastName());
	        row1.put("5", list.get(i).getTelephone());
	        row1.put("6", list.get(i).getPhone());
	        row1.put("7", list.get(i).getRegisteredDate());
	        exportData.add(row1);
		}
        LinkedHashMap map = new LinkedHashMap();
        map.put("1", "id");
        map.put("2", "email");
        map.put("3", "firstname");
        map.put("4", "lastname");
        map.put("5", "telephone");
        map.put("6", "phone");
        map.put("7", "date_added");
        String path = System.getProperty("user.dir") + Util.getProperty("FieldsCSVOutPutPath");
        log.debug("path:" + path);
        String name = (String) this.map.get("currentMonthRegistrationM7L14Days");
        name += Util.getDay() + ".csv";
        this.map.put("currentMonthRegistrationM7L14Days", name);
        log.debug("filename:" + name);
		cs.createCSVFile(exportData, map, path, name);
	}
	/**
	 * 创建上月注册用户大于七天的用户
	 */
	private void lastMonthRegistrationM7Days() {
		RecipientService rs = new RecipientService();
		CSVService cs = new CSVService();
		//得到符合标准的数据
		List<Recipient> list = rs.findMatchRecipient();
		log.debug("得到符合标准的数据CSV文件数量:" + list.size());
		//初始化文件数据
		List exportData = new ArrayList<Map>();
		for(int i = 0; i < list.size(); i++){
			Map row1 = new LinkedHashMap<String, String>();
	        row1.put("1", list.get(i).getId());
	        row1.put("2", list.get(i).getEmail());
	        row1.put("3", list.get(i).getFirstName());
	        row1.put("4", list.get(i).getLastName());
	        row1.put("5", list.get(i).getTelephone());
	        row1.put("6", list.get(i).getPhone());
	        row1.put("7", list.get(i).getRegisteredDate());
	        exportData.add(row1);
		}
        LinkedHashMap map = new LinkedHashMap();
        map.put("1", "id");
        map.put("2", "email");
        map.put("3", "firstname");
        map.put("4", "lastname");
        map.put("5", "telephone");
        map.put("6", "phone");
        map.put("7", "date_added");
        String path = System.getProperty("user.dir") + Util.getProperty("FieldsCSVOutPutPath");
        log.debug("path:" + path);
        String name = (String) this.map.get("lastMonthRegistrationM7Days");
        name += Util.getDay() + ".csv";
        this.map.put("lastMonthRegistrationM7Days", name);
        log.debug("filename:" + name);
		cs.createCSVFile(exportData, map, path, name);
	}
	/**
	 * 将CSV文件发送给fields员工
	 */
	public void sendCSVFile(){
		RecipientService rs = new RecipientService();
		//得到fields的员工数据
		List<Recipient> list = rs.findFieldsEmail();
		log.debug("得到fields的员工数据数量:" + list.size());
		//初始化收件人数据
		String receiver = "";
		for(int i = 0; i < list.size(); i++){
			receiver = Util.addString(receiver, list.get(i).getEmail());
		}
		List listFile = new ArrayList();  
		String path = System.getProperty("user.dir") + Util.getProperty("FieldsCSVOutPutPath");
	    log.debug("path:" + path + "\n map:" + map);
	    Iterator it = map.keySet().iterator();
	    while(it.hasNext()){
	    	String name = (String) it.next();
	    	if(!name.equals(map.get(name)))
	    		listFile.add(new File(path + "/" + map.get(name)));
	    }
	    Server server=new Server("smtp.qq.com","25",Util.getProperty("senderemailaddress"),Util.getProperty("senderemailpassword")); 
	    Email email=new Email("Hello!Bye!","CSV file for WebPower",Util.getProperty("senderemailaddress"),receiver);  
	    email.setAttenchment(listFile);
	    SendMail sendemail=new SendMail();  
	    String result = sendemail.send(server,email);
	    //发送完毕后初始化文件名（避免累加文件名）
	    map = Util.getCSVFileProperty();
	    log.info("邮件发送成功！");
	}
}
