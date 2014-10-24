package com.fieldschina.edm.handler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.fieldschina.edm.service.DownLoadDMDReturnFileToODSService;
import com.fieldschina.edm.util.Util;
/**
 * 下载DMD回传文件到本地数据库
 * 
 * @author  LAVEN  E-mail:lavenwl@gmail.com
 * @company FieldsChina.IT.BI
 * @version 创建时间：2014-7-23 下午3:38:49
 */
public class DownLoadDMDReturnFileToODSHandler extends Handler{
	/**
	 * 计时器开始的方法
	 */
	public void Timer(){
		try {
			log.info("Timer start！");
			//开始时间的初始化
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd " + Util.getProperty("DownLoadDMDBackFileToODSBeginTime"));
			Date startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sdf.format(new Date()));
			//判断当前时间是否已经过了开始时间
			if (System.currentTimeMillis() > startTime.getTime()) {
				startTime = new Date(startTime.getTime() + Util.getTimeProperty("DownLoadDMDBackFileToODSSleepTime"));
			}
			//实例化一个定时器
			Timer t = new Timer();
			//定时器任务
			TimerTask task = new TimerTask() {
				public void run() {
					log.info("DownLoadDMDBackFileToODS createCSVFile Timer Task start！");
					downloadFile();
					//下载文件之后对原来的文件进行备份（基于本地【FTP服务器】的目录操作）
					Util.backupFileAfterdownloadFile();
					log.info("Timer task end！");
				}
			};
			t.scheduleAtFixedRate(task, startTime, Util.getTimeProperty("DownLoadDMDBackFileToODSSleepTime"));
		} catch (ParseException e) {
			 log.error(Util.getTrace(e));
		}
	}
	/**
	 * 下载DMD回传文件到本地数据库
	 * 
	 */
	public void downloadFile(){
		DownLoadDMDReturnFileToODSService downLoadDMDReturnFileToODSService = new DownLoadDMDReturnFileToODSService();
		//将FTP服务器上的文件下载到本地
		downLoadDMDReturnFileToODSService.downloadFiles();
		//读取本地的下载的文件进行处理
		downLoadDMDReturnFileToODSService.getDownloadFiles();
	}
}
