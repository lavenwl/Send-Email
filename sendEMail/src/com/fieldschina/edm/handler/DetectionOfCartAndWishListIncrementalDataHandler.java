package com.fieldschina.edm.handler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.fieldschina.edm.service.UserActionChangeService;
import com.fieldschina.edm.util.Util;
/**
 * 监听web端收藏列表及购物车新增数据功能
 * 
 * @author  LAVEN  E-mail:lavenwl@gmail.com
 * @company FieldsChina.IT.BI
 * @version 创建时间：2014-7-24 下午5:01:00
 */
public class DetectionOfCartAndWishListIncrementalDataHandler extends Handler{
	private UserActionChangeService userActionChangeService = null;
	public UserActionChangeService getUserActionChangeService(){
		if(userActionChangeService == null)
			userActionChangeService = new UserActionChangeService();
		return userActionChangeService;
	}
	/**
	 * 计时器开始的方法
	 */
	public void Timer(){
		try {
			log.info("DetectionOfCartAndWishListIncrementalDataHandler Timer start！");
			//开始时间的初始化
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd " + Util.getProperty("DetectionOfCartAndWishListIncrementalDataHandlerBeginTime"));
			Date startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sdf.format(new Date()));
			//判断当前时间是否已经过了开始时间
			if (System.currentTimeMillis() > startTime.getTime()) {
				startTime = new Date(startTime.getTime() + Util.getTimeProperty("DetectionOfCartAndWishListIncrementalDataHandlerSleepTime"));
			}
			//实例化一个定时器
			Timer t = new Timer();
			//定时器任务
			TimerTask task = new TimerTask() {
				public void run() {
					log.info("DetectionOfCartAndWishListIncrementalDataHandler Timer Task start！");
					detactionOfWishList();
					log.info("DetectionOfCartAndWishListIncrementalDataHandler Timer task end！");
				}
			};
			t.scheduleAtFixedRate(task, startTime, Util.getTimeProperty("DetectionOfCartAndWishListIncrementalDataHandlerSleepTime"));
		} catch (ParseException e) {
			 log.error(Util.getTrace(e));
		}
	}
	//初始化数据（数据库表内数据的一次性添加）
	public void initData(){
		this.getUserActionChangeService().initData();
	}
	//检测收藏列表购物车的数据变化
	public void detactionOfWishList(){
		this.getUserActionChangeService().detactionOfWishList();
	}
}
