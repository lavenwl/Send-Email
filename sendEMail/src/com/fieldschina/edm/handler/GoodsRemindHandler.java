package com.fieldschina.edm.handler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.fieldschina.edm.entity.Recipient2;
import com.fieldschina.edm.service.RecipientService;
import com.fieldschina.edm.util.Util;
/**
 * 定时发送用户的到货提醒邮件（未完成。测试阶段）
 * 
 * @author  LAVEN  E-mail:lavenwl@gmail.com
 * @company FieldsChina.IT.BI
 * @version 创建时间：2014-9-25 下午3:33:15
 */
public class GoodsRemindHandler extends Handler{
	/**
	 * 计时器开始的方法
	 */
	public void Timer(){
		try {
			log.info("GoodsRemindHandler Timer start！");
			//开始时间的初始化
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd " + Util.getProperty("TheArrivalOfTheGoodsToRemindHandlerBeginTime"));
			Date startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sdf.format(new Date()));
			//判断当前时间是否已经过了开始时间
			if (System.currentTimeMillis() > startTime.getTime()) {
				startTime = new Date(startTime.getTime() + Util.getTimeProperty("TheArrivalOfTheGoodsToRemindHandlerSleepTime"));
			}
			//实例化一个定时器
			Timer t = new Timer();
			//定时器任务
			TimerTask task = new TimerTask() {
				public void run() {
					log.info("GoodsRemindHandler Timer Task start！");
//					sendReport(checkReport());
					log.info("GoodsRemindHandler Timer task end！");
				}
			};
			t.scheduleAtFixedRate(task, startTime, Util.getTimeProperty("TheArrivalOfTheGoodsToRemindHandlerSleepTime"));
		} catch (ParseException e) {
			 log.error(Util.getTrace(e));
		}
	}
	public void sendRemindEmail(){
		//得到需要更新发送到货提醒的用户
		RecipientService recipientService = new RecipientService();
		List<Recipient2> list = recipientService.findRemindRecipients();
		log.debug("得到的要发送到货提醒的用户的集合list:" + list.size());
		//发送到货提醒
		recipientService.sendGoodsRemind(list);
	}
}
