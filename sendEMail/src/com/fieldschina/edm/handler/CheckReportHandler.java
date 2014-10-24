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
import com.fieldschina.edm.service.CheckReportService;
import com.fieldschina.edm.service.RecipientService;
import com.fieldschina.edm.util.Util;
/**
 * 定时监测脚本执行情况，将结果发送到我邮箱
 * 
 * @author  LAVEN  E-mail:lavenwl@gmail.com
 * @company FieldsChina.IT.BI
 * @version 创建时间：2014-7-23 上午9:05:49
 */
public class CheckReportHandler extends Handler{
	private static Map<String, String> map = Util.getCSVFileProperty();
	/**
	 * 计时器开始的方法
	 */
	public void Timer(){
		try {
			log.info("CheckReportHandler Timer start！");
			//开始时间的初始化
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd " + Util.getProperty("CheckReportHandlerBeginTime"));
			Date startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sdf.format(new Date()));
			//判断当前时间是否已经过了开始时间
			if (System.currentTimeMillis() > startTime.getTime()) {
				startTime = new Date(startTime.getTime() + Util.getTimeProperty("CheckReportHandlerSleepTime"));
			}
			//实例化一个定时器
			Timer t = new Timer();
			//定时器任务
			TimerTask task = new TimerTask() {
				public void run() {
					log.info("CheckReportHandler Timer Task start！");
					sendReport(checkReport());
					log.info("CheckReportHandler Timer task end！");
				}
			};
			t.scheduleAtFixedRate(task, startTime, Util.getTimeProperty("CheckReportHandlerSleepTime"));
		} catch (ParseException e) {
			 log.error(Util.getTrace(e));
		}
	}
	/**
	 * 检查log文件是否有error报错，得到具体的错误信息
	 * 
	 * @return
	 */
	public String checkReport(){
		CheckReportService checkReportService = new CheckReportService();
		return checkReportService.checkReport();
	}
	/**
	 * 将检查报告发送给fieldsIT员工
	 */
	public void sendReport(String report){
		RecipientService rs = new RecipientService();
		//得到fields的员工数据
		List<Recipient> list = rs.findFieldsITEmail();
		log.debug("得到fields的IT员工数据数量:" + list.size() + "是：" + list.toString());
		//初始化收件人数据
		String receiver = "";
		for(int i = 0; i < list.size(); i++){
			receiver = Util.addString(receiver, list.get(i).getEmail());
		}
	    Server server=new Server("smtp.qq.com","25",Util.getProperty("senderemailaddress"),Util.getProperty("senderemailpassword")); 
	    Email email=new Email(report,"sendEmail check report",Util.getProperty("senderemailaddress"),receiver);  
	    //添加附件
//	    email.setAttenchment(listFile);
	    SendMail sendemail=new SendMail();  
	    String result = sendemail.send(server,email);
	    //发送完毕后初始化文件名（避免累加文件名）
	    map = Util.getCSVFileProperty();
	    log.info("邮件发送成功！");
	}
}
