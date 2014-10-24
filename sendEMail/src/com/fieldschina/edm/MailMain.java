package com.fieldschina.edm;

import org.apache.log4j.Logger;

import com.fieldschina.edm.handler.CheckReportHandler;
import com.fieldschina.edm.handler.DMACSVFileHandler;
import com.fieldschina.edm.handler.DetectionOfCartAndWishListIncrementalDataHandler;
import com.fieldschina.edm.handler.DownLoadDMDReturnFileToODSHandler;
import com.fieldschina.edm.handler.FieldsCSVFileHandler;
import com.fieldschina.edm.handler.GoodsRemindHandler;
import com.fieldschina.edm.handler.IncrementRecipientHandler;
import com.fieldschina.edm.util.Util;

/**
 * 发送邮件启动类
 * 
 * @author  LAVEN  E-mail:lavenwl@gmail.com
 * @company FieldsChina.IT.BI
 * @version 创建时间：2014-6-12 下午5:41:49
 */
public class MailMain {
	public static Logger log = Logger.getLogger(MailMain.class);
	public static void main(String[] args) {
		log.info("ALL script start！");
		
		//每日上传用户数据
		if(Util.getProperty("incrementRecipientHandlerStatus").equals("on")){
			IncrementRecipientHandler incrementRecipientHandler = new IncrementRecipientHandler();
			incrementRecipientHandler.Timer();//incrementRecipientHandler.addRecipientFunction();
		}
//		//生成marketing需要的表格并定时自动发送到邮箱
//		if(Util.getProperty("FieldsCSVFileHandlerStatus").equals("on")){
//			FieldsCSVFileHandler CSVFileHandler = new FieldsCSVFileHandler();
//			CSVFileHandler.Timer();//CSVFileHandler.createCSVFile();//CSVFileHandler.sendCSVFile();		
//		}
		//DMA平台需要的三张表格的定时生成与上传至FTP服务器
		if(Util.getProperty("DMACSVFileHandlerStatus").equals("on")){
			DMACSVFileHandler dMACSVFileHandler = new DMACSVFileHandler();
			dMACSVFileHandler.Timer();//dMACSVFileHandler.createCSVFile();//dMACSVFileHandler.uploadCSVFiles();
		}
		//下载DMD平台的回传文件到本地ods数据库，便于以后的数据分析 
		if(Util.getProperty("DownLoadDMDBackFileToODSStatus").equals("on")){
			DownLoadDMDReturnFileToODSHandler downLoadDMDBackFileToODS = new DownLoadDMDReturnFileToODSHandler();
			downLoadDMDBackFileToODS.Timer();//downLoadDMDBackFileToODS.downloadFile();
		}
		//脚本自动检查并发送检查报告到邮箱
		if(Util.getProperty("CheckReportHandlerStatus").equals("on")){
			CheckReportHandler checkReportHandler = new CheckReportHandler();
			checkReportHandler.Timer();//System.out.println(checkReportHandler.checkReport());//checkReportHandler.sendReport(checkReportHandler.checkReport());
		}
		//检测网站购物车和收藏列表内的数据变动
		if(Util.getProperty("DetectionOfCartAndWishListIncrementalDataHandlerStatus").equals("on")){
			DetectionOfCartAndWishListIncrementalDataHandler detectionOfCartAndWishListIncrementalDataHandler = new DetectionOfCartAndWishListIncrementalDataHandler();
			detectionOfCartAndWishListIncrementalDataHandler.Timer();//detectionOfCartAndWishListIncrementalDataHandler.initData();//detectionOfCartAndWishListIncrementalDataHandler.detactionOfWishList();
		}
		//用户因为缺货而收藏于添加到购物车物品的到货提醒功能
		if(Util.getProperty("TheArrivalOfTheGoodsToRemindHandlerStatus").equals("on")){
			GoodsRemindHandler goodsRemindHandler = new GoodsRemindHandler();
//			goodsRemindHandler.Timer();
			goodsRemindHandler.sendRemindEmail();
		}
	}
}
