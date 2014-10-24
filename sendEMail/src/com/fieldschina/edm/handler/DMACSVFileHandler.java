package com.fieldschina.edm.handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import com.fieldschina.edm.entity.ActionCSV;
import com.fieldschina.edm.entity.ItemCSV;
import com.fieldschina.edm.entity.Recipient;
import com.fieldschina.edm.entity.UserCSV;
import com.fieldschina.edm.ftp.FTPServer;
import com.fieldschina.edm.service.ActionCSVService;
import com.fieldschina.edm.service.CSVService;
import com.fieldschina.edm.service.ItemCSVService;
import com.fieldschina.edm.service.RecipientService;
import com.fieldschina.edm.service.UserCSVService;
import com.fieldschina.edm.util.Util;

/**
 * DMA需要的三个CSV文件相关操作类
 * 
 * @author  LAVEN  E-mail:lavenwl@gmail.com
 * @company FieldsChina.IT.BI
 * @version 创建时间：2014-7-3 下午3:39:15
 */
public class DMACSVFileHandler extends Handler{
	private static String USER_CN_CSV = "CNuserinfo";
	private static String USER_EN_CSV = "ENuserinfo";
	private static String CNACTION_CSV = "CNuserlog";
	private static String ENACTION_CSV = "ENuserlog";
	private static String ITEM_CN_CSV = "CNItemlist_source1";
	private static String ITEM_EN_CSV = "ENItemlist_source1";
	private static Map<String, String> FILEMAP = new HashMap<String, String>();
	//暂时存储产品信息的映射，以填充中文用户行为表内的type字段信息，填充完后设置为null。因此：运行用户行为表生成的前提是产品表的生成
	private static Map<Integer, String> CNTYPEMAP = new HashMap<Integer, String>();
	//暂时存储产品信息的映射，以填充英文用户行为表内的type字段信息，填充完后设置为null。因此：运行用户行为表生成的前提是产品表的生成
	private static Map<Integer, String> ENTYPEMAP = new HashMap<Integer, String>();
	//暂时存储产品信息的映射，以填充用户行为表内的pricelevel字段信息，填充完后设置为null。因此：运行用户行为表生成的前提是产品表的生成
	private static Map<Integer, Integer> PRICEMAP = new HashMap<Integer, Integer>();
	/**
	 * 计时器开始的方法
	 */
	public void Timer(){
		try {
			log.info("Timer start！");
			//开始时间的初始化
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd " + Util.getProperty("DMACSVFileHandlerBeginTime"));
			Date startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sdf.format(new Date()));
			//判断当前时间是否已经过了开始时间
			if (System.currentTimeMillis() > startTime.getTime()) {
				startTime = new Date(startTime.getTime() + Util.getTimeProperty("DMACSVFileHandlerSleepTime"));
			}
			//实例化一个定时器
			Timer t = new Timer();
			//定时器任务
			TimerTask task = new TimerTask() {
				public void run() {
					log.info("DMACSVFileHandler createCSVFile Timer Task start！");
					createCSVFile();
					log.info("Timer task end！");
				}
			};
			t.scheduleAtFixedRate(task, startTime, Util.getTimeProperty("DMACSVFileHandlerSleepTime"));
		} catch (ParseException e) {
			log.error(Util.getTrace(e));
		}
	}
	/**
	 * 创建一个CSV文件
	 */
	public void createCSVFile() {
		String begainTime = Util.getBegainTimeFromFolder();
		//创建中国用户信息CSV文件
		createCNUserCSV();
		//创建外国用户信息CSV文件
		createENUserCSV();
		//创建中文产品信息CSV文件
		createCNItemCSV();
		//创建英文产品信息CSV文件
		createENItemCSV();
		if(Util.getProperty("isIncrementalForm").equals("Y")){
			log.debug("incremental form to create Action CSV File");
			//增量创建行为信息CSV文件
			createActionCSVIncremental(begainTime);
		}else{
			log.debug("full variable form to create Action CSV File");
			//全量创建行为信息CSV文件
			createActionCSV();
		}
		//上传文件之前对原来的文件进行备份（基于本地【FTP服务器】的目录操作）
		Util.backupFilesBeforeuploadFile();
		//上传生成的CSV文件
		uploadCSVFiles();
		try {
			log.debug("已经释放连接！");
			DBConnectionManager dbm = DBConnectionManager.getInstance();
			dbm.release();
			dbm.setInstanceNull();
		} catch (Exception e) {
			log.error(Util.getTrace(e));
		}
	}
	/**
	 * 创建中国用户信息CSV文件
	 */
	private void createCNUserCSV() {
		UserCSVService userCSVService = new UserCSVService();
		CSVService cs = new CSVService();
		//得到中国用户信息的数据
		List<UserCSV> list = userCSVService.getAllCNUserCSVs();
		log.debug("得到用户信息的数据CSV文件数量:" + list.size());
		//初始化文件数据
		List exportData = new ArrayList<Map>();
		for(int i = 0; i < list.size(); i++){
			Map row1 = new LinkedHashMap<String, String>();
			row1.put("1", list.get(i).getUsername());
	        row1.put("2", list.get(i).getEmail());
	        row1.put("3", list.get(i).getMobile());
	        row1.put("4", list.get(i).getGender() == 2 ? "null" : list.get(i).getGender());
	        row1.put("5", list.get(i).getProvince());
	        row1.put("6", list.get(i).getCity());
	        row1.put("7", list.get(i).getBrithday());
	        row1.put("8", list.get(i).getRegisterDate());
	        exportData.add(row1);
		}
        LinkedHashMap map = new LinkedHashMap();
        map.put("1", "Username");
        map.put("2", "email");
        map.put("3", "mobile");
        map.put("4", "gender");
        map.put("5", "province");
        map.put("6", "city");
        map.put("7", "birthday");
        map.put("8", "register date");
        String path = System.getProperty("user.dir") + Util.getProperty("DMACSVOutPutPath");
        String name = USER_CN_CSV;
        name += ("_" + Util.getDay() + ".csv");
        FILEMAP.put("cnuser", name);
		cs.createCSVFile(exportData, map, path, name);
		log.debug("create CSV File：" + name + " finished.");
		//清理数据
		list = null;
	}
	/**
	 * 创建外国用户信息CSV文件
	 */
	private void createENUserCSV() {
		UserCSVService userCSVService = new UserCSVService();
		CSVService cs = new CSVService();
		//得到外国用户信息的数据
		List<UserCSV> list = userCSVService.getAllENUserCSVs();
		log.debug("得到用户信息的数据CSV文件数量:" + list.size());
		//初始化文件数据
		List exportData = new ArrayList<Map>();
		for(int i = 0; i < list.size(); i++){
			Map row1 = new LinkedHashMap<String, String>();
			row1.put("1", list.get(i).getUsername());
	        row1.put("2", list.get(i).getEmail());
	        row1.put("3", list.get(i).getMobile());
	        row1.put("4", list.get(i).getGender() == 2 ? "null" : list.get(i).getGender());
	        row1.put("5", list.get(i).getProvince());
	        row1.put("6", list.get(i).getCity());
	        row1.put("7", list.get(i).getBrithday());
	        row1.put("8", list.get(i).getRegisterDate());
	        exportData.add(row1);
		}
        LinkedHashMap map = new LinkedHashMap();
        map.put("1", "Username");
        map.put("2", "email");
        map.put("3", "mobile");
        map.put("4", "gender");
        map.put("5", "province");
        map.put("6", "city");
        map.put("7", "birthday");
        map.put("8", "register date");
        String path = System.getProperty("user.dir") + Util.getProperty("DMACSVOutPutPath");
        String name = USER_EN_CSV;
        name += ("_" + Util.getDay() + ".csv");
        FILEMAP.put("enuser", name);
		cs.createCSVFile(exportData, map, path, name);
		log.debug("create CSV File：" + name + " finished.");
		//清理数据
		list = null;
	}
	/**
	 * 创建中文产品信息CSV文件
	 */
	private void createCNItemCSV() {
		ItemCSVService itemCSVService = new ItemCSVService();
		CSVService cs = new CSVService();
		//得到中文商品信息的数据
		List<ItemCSV> list = itemCSVService.getAllCNItemCSVs();
		log.debug("得到产品信息的数据CSV文件数量:" + list.size());
		//初始化map映射表，方便后面生成行为表是添加type字段和Price_level字段
		for(int i = 0; i < list.size(); i++){
			CNTYPEMAP.put(list.get(i).getItemID(), list.get(i).getType());
			PRICEMAP.put(list.get(i).getItemID(), list.get(i).getPrice1());
		}
		log.debug("得到的map的大小：" + CNTYPEMAP.size());
		//初始化文件数据
		List exportData = new ArrayList<Map>();
		for(int i = 0; i < list.size(); i++){
			Map row1 = new LinkedHashMap<String, String>();
			row1.put("1", list.get(i).getTitle());
	        row1.put("2", list.get(i).getUrl());
	        row1.put("3", list.get(i).getImageUrl());
	        row1.put("4", list.get(i).getImageUrl2());
	        row1.put("5", list.get(i).getPrice1());
	        row1.put("6", list.get(i).getPrice2() == 0 ? list.get(i).getPrice1() : list.get(i).getPrice2());
	        row1.put("7", list.get(i).getPrivce3());
	        row1.put("8", list.get(i).getDesc1());
	        row1.put("9", list.get(i).getDesc2());
	        row1.put("10", list.get(i).getDesc3());
	        row1.put("11", list.get(i).getItemID());
	        row1.put("12", list.get(i).getType());
	        row1.put("13", list.get(i).getBrand());
	        row1.put("14", list.get(i).getPriceLevel());
	        row1.put("15", list.get(i).getTagID());
	        row1.put("16", list.get(i).getGender());
	        row1.put("17", list.get(i).getOnlineDate());
	        row1.put("18", list.get(i).getOfflineDate());
	        row1.put("19", list.get(i).getStock());
	        row1.put("20", list.get(i).getStatus());
	        exportData.add(row1);
		}
        LinkedHashMap map = new LinkedHashMap();
        map.put("1", "Title");
        map.put("2", "URL");
        map.put("3", "image URL");
        map.put("4", "image2 URL");
        map.put("5", "Price1");
        map.put("6", "Price2");
        map.put("7", "Price3");
        map.put("8", "desc1");
        map.put("9", "desc2");
        map.put("10", "desc3");
        map.put("11", "Item ID");
        map.put("12", "Type");
        map.put("13", "Brand");
        map.put("14", "Price level");
        map.put("15", "Tag ID");
        map.put("16", "Gender");
        map.put("17", "online Date");
        map.put("18", "offline Date");
        map.put("19", "Stock");
        map.put("20", "Status");
        String path = System.getProperty("user.dir") + Util.getProperty("DMACSVOutPutPath");
        String name = ITEM_CN_CSV;
        name += ("_" + Util.getDay() + ".csv");
        FILEMAP.put("cnitem", name);
		cs.createCSVFile(exportData, map, path, name);
		log.debug("create CSV File：" + name + " finished.");
		//清理数据
		list = null;
	}
	/**
	 * 创建英文产品信息CSV文件
	 */
	private void createENItemCSV() {
		ItemCSVService itemCSVService = new ItemCSVService();
		CSVService cs = new CSVService();
		//得到英文商品信息的数据
		List<ItemCSV> list = itemCSVService.getAllENItemCSVs();
		log.debug("得到产品信息的数据CSV文件数量:" + list.size());
		//初始化map映射表，方便后面生成行为表是添加type字段
		for(int i = 0; i < list.size(); i++){
			ENTYPEMAP.put(list.get(i).getItemID(), list.get(i).getType());
			PRICEMAP.put(list.get(i).getItemID(), list.get(i).getPrice1());
		}
		log.debug("得到的map的大小：" + ENTYPEMAP.size());
		//初始化文件数据
		List exportData = new ArrayList<Map>();
		for(int i = 0; i < list.size(); i++){
			Map row1 = new LinkedHashMap<String, String>();
			row1.put("1", list.get(i).getTitle());
	        row1.put("2", list.get(i).getUrl());
	        row1.put("3", list.get(i).getImageUrl());
	        row1.put("4", list.get(i).getImageUrl2());
	        row1.put("5", list.get(i).getPrice1());
	        row1.put("6", list.get(i).getPrice2() == 0 ? list.get(i).getPrice1() : list.get(i).getPrice2());
	        row1.put("7", list.get(i).getPrivce3());
	        row1.put("8", list.get(i).getDesc1());
	        row1.put("9", list.get(i).getDesc2());
	        row1.put("10", list.get(i).getDesc3());
	        row1.put("11", list.get(i).getItemID());
	        row1.put("12", list.get(i).getType());
	        row1.put("13", list.get(i).getBrand());
	        row1.put("14", list.get(i).getPriceLevel());
	        row1.put("15", list.get(i).getTagID());
	        row1.put("16", list.get(i).getGender());
	        row1.put("17", list.get(i).getOnlineDate());
	        row1.put("18", list.get(i).getOfflineDate());
	        row1.put("19", list.get(i).getStock());
	        row1.put("20", list.get(i).getStatus());
	        exportData.add(row1);
		}
        LinkedHashMap map = new LinkedHashMap();
        map.put("1", "Title");
        map.put("2", "URL");
        map.put("3", "image URL");
        map.put("4", "image2 URL");
        map.put("5", "Price1");
        map.put("6", "Price2");
        map.put("7", "Price3");
        map.put("8", "desc1");
        map.put("9", "desc2");
        map.put("10", "desc3");
        map.put("11", "Item ID");
        map.put("12", "Type");
        map.put("13", "Brand");
        map.put("14", "Price level");
        map.put("15", "Tag ID");
        map.put("16", "Gender");
        map.put("17", "online Date");
        map.put("18", "offline Date");
        map.put("19", "Stock");
        map.put("20", "Status");
        String path = System.getProperty("user.dir") + Util.getProperty("DMACSVOutPutPath");
        String name = ITEM_EN_CSV;
        name += ("_" + Util.getDay() + ".csv");
        FILEMAP.put("enitem", name);
		cs.createCSVFile(exportData, map, path, name);
		log.debug("create CSV File：" + name + " finished.");
		//清理数据
		list = null;
	}
	/**
	 * 创建行为信息CSV文件（部分）
	 */
	private void createActionCSV() {
		ActionCSVService actionCSVService = new ActionCSVService();
		CSVService cs = new CSVService();
		//按照时间读取数据
		String begainTime = Util.getProperty("DMAActionCSVInitBegineTime");
		String month = Util.getMonth();
		String endTime = Util.getNextMonth(begainTime);
		//得到用户查看行为信息的数据
		List<ActionCSV> list = new ArrayList<ActionCSV>();
		int t = 0;//标志是否是第一次执行运行不同的生成CSV文件的方法
		while(Util.compareDateString(begainTime, month) < 0){
			list = actionCSVService.getAllViewActionCSVs(begainTime, endTime);
			//得到用户购买行为信息的数据
			list.addAll(actionCSVService.getAllPurchasedActionCSVs(begainTime, endTime));
			//得到用户收藏行为的信息数据
			list.addAll(actionCSVService.getAllCollectActionCSVs(begainTime, endTime));
			//得到用户添加购物车信息
			list.addAll(actionCSVService.getAllShoppingCartActionCSVs(begainTime, endTime));
			log.debug("得到行为信息的数据CSV文件数量:" + list.size());
			//添加行为表的type字段，以及过滤tiemid字段内非int类型（字符串：fieldschina）的数据
			int invalid = 0;
			//中文文件type，pricelevel数据生成
			for(int i = 0; i < list.size(); i++){
				try{
					if(list.get(i).getItemID().endsWith("fieldschina") || list.get(i).getEmail() == null){
						list.remove(i);
						continue;
					}
					if(CNTYPEMAP.get(Integer.valueOf(list.get(i).getItemID())) != null)
						list.get(i).setType(CNTYPEMAP.get(Integer.valueOf(list.get(i).getItemID().trim())));
					if(PRICEMAP.get(Integer.valueOf(list.get(i).getItemID())) != null)
						list.get(i).setPriceLevel(Util.getPriceLevel(PRICEMAP.get(Integer.valueOf(list.get(i).getItemID().trim()))));
				}catch(Exception e){
					invalid++;
					list.remove(i);
					continue;
				}
			}
			log.debug("ActionCSV.item有:" + invalid + "个非法数据。");
			//初始化文件数据
			List exportData = new ArrayList<Map>();
			for(int i = 0; i < list.size(); i++){
				Map row1 = new LinkedHashMap<String, String>();
				row1.put("1", list.get(i).getEmail());
		        row1.put("2", list.get(i).getItemID());
		        row1.put("3", list.get(i).getType());
		        row1.put("4", list.get(i).getBrandID());
		        row1.put("5", list.get(i).getPriceLevel());
		        row1.put("6", list.get(i).getTagID());
		        row1.put("7", list.get(i).getAction());
		        row1.put("8", list.get(i).getTimesStamp());
		        exportData.add(row1);
			}
	        LinkedHashMap map = new LinkedHashMap();
	        map.put("1", "email");
	        map.put("2", "Item ID");
	        map.put("3", "Type");
	        map.put("4", "Brand ID");
	        map.put("5", "Price level");
	        map.put("6", "Tag ID");
	        map.put("7", "Action");
	        map.put("8", "Timestamp");
	        String path = System.getProperty("user.dir") + Util.getProperty("DMACSVOutPutPath");
	        String name = CNACTION_CSV;
	        if(t == 0){
	        	name += ("_" + Util.getDay() + ".csv");
	 	        FILEMAP.put("CNaction", name);
	 	        cs.createCSVFile(exportData, map, path, FILEMAP.get("CNaction"));
	        }else{
	        	cs.createCSVFile(exportData, path, FILEMAP.get("CNaction"));
	        }
	        
	        //英文文件type，price level字段数据生成
	        for(int i = 0; i < list.size(); i++){
				try{
					if(list.get(i).getItemID().endsWith("fieldschina") || list.get(i).getEmail() == null){
						list.remove(i);
						continue;
					}
					if(ENTYPEMAP.get(Integer.valueOf(list.get(i).getItemID())) != null)
						list.get(i).setType(ENTYPEMAP.get(Integer.valueOf(list.get(i).getItemID().trim())));
					if(PRICEMAP.get(Integer.valueOf(list.get(i).getItemID())) != null)
						list.get(i).setPriceLevel(Util.getPriceLevel(PRICEMAP.get(Integer.valueOf(list.get(i).getItemID().trim()))));
				}catch(Exception e){
					invalid++;
					list.remove(i);
					continue;
				}
			}
			log.debug("ActionCSV.item有:" + invalid + "个非法数据。");
			//初始化文件数据
			exportData = new ArrayList<Map>();
			for(int i = 0; i < list.size(); i++){
				Map row1 = new LinkedHashMap<String, String>();
				row1.put("1", list.get(i).getEmail());
		        row1.put("2", list.get(i).getItemID());
		        row1.put("3", list.get(i).getType());
		        row1.put("4", list.get(i).getBrandID());
		        row1.put("5", list.get(i).getPriceLevel());
		        row1.put("6", list.get(i).getTagID());
		        row1.put("7", list.get(i).getAction());
		        row1.put("8", list.get(i).getTimesStamp());
		        exportData.add(row1);
			}
	        map = new LinkedHashMap();
	        map.put("1", "email");
	        map.put("2", "Item ID");
	        map.put("3", "Type");
	        map.put("4", "Brand ID");
	        map.put("5", "Price level");
	        map.put("6", "Tag ID");
	        map.put("7", "Action");
	        map.put("8", "Timestamp");
	        path = System.getProperty("user.dir") + Util.getProperty("DMACSVOutPutPath");
	        String name2 = ENACTION_CSV;
	        if(t == 0){
	        	name2 += ("_" + Util.getDay() + ".csv");
	 	        FILEMAP.put("ENaction", name2);
	 	        cs.createCSVFile(exportData, map, path, FILEMAP.get("ENaction"));
	        }else{
	        	cs.createCSVFile(exportData, path, FILEMAP.get("ENaction"));
	        }
	        t++;
			begainTime = endTime;
			endTime = Util.getNextMonth(begainTime);
			list = null;
			exportData = null;
		}
		endTime = Util.getDay();
		list = actionCSVService.getAllViewActionCSVs(begainTime, endTime);
		//得到用户购买行为信息的数据
		list.addAll(actionCSVService.getAllPurchasedActionCSVs(begainTime, endTime));
		//得到用户收藏行为的信息数据
		list.addAll(actionCSVService.getAllCollectActionCSVs(begainTime, endTime));
		//得到用户添加购物车信息
		list.addAll(actionCSVService.getAllShoppingCartActionCSVs(begainTime, endTime));
		log.debug("得到行为信息的数据CSV文件数量:" + list.size());
		//添加行为表的type字段，以及过滤tiemid字段内非int类型（字符串：fieldschina）的数据
		int invalid = 0;
		//中文文件type，pricelevel字段数据生成
		for(int i = 0; i < list.size(); i++){
			try{
				if(list.get(i).getItemID().endsWith("fieldschina") || list.get(i).getEmail() == null){
					list.remove(i);
					continue;
				}
				if(CNTYPEMAP.get(Integer.valueOf(list.get(i).getItemID())) != null)
					list.get(i).setType(CNTYPEMAP.get(Integer.valueOf(list.get(i).getItemID().trim())));
				if(PRICEMAP.get(Integer.valueOf(list.get(i).getItemID())) != null)
					list.get(i).setPriceLevel(Util.getPriceLevel(PRICEMAP.get(Integer.valueOf(list.get(i).getItemID().trim()))));
			}catch(Exception e){
				invalid++;
				list.remove(i);
				continue;
			}
		}
		log.debug("ActionCSV.item有:" + invalid + "个非法数据。");
		//初始化文件数据
		List exportData = new ArrayList<Map>();
		for(int i = 0; i < list.size(); i++){
			Map row1 = new LinkedHashMap<String, String>();
			row1.put("1", list.get(i).getEmail());
	        row1.put("2", list.get(i).getItemID());
	        row1.put("3", list.get(i).getType());
	        row1.put("4", list.get(i).getBrandID());
	        row1.put("5", list.get(i).getPriceLevel());
	        row1.put("6", list.get(i).getTagID());
	        row1.put("7", list.get(i).getAction());
	        row1.put("8", list.get(i).getTimesStamp());
	        exportData.add(row1);
		}
        LinkedHashMap map = new LinkedHashMap();
        map.put("1", "email");
        map.put("2", "Item ID");
        map.put("3", "Type");
        map.put("4", "Brand ID");
        map.put("5", "Price level");
        map.put("6", "Tag ID");
        map.put("7", "Action");
        map.put("8", "Timestamp");
        String path = System.getProperty("user.dir") + Util.getProperty("DMACSVOutPutPath");
        String name = CNACTION_CSV;
        if(t == 0){
        	name += ("_" + Util.getDay() + ".csv");
 	        FILEMAP.put("CNaction", name);
 	        cs.createCSVFile(exportData, map, path, FILEMAP.get("CNaction"));
        }else{
        	cs.createCSVFile(exportData, path, FILEMAP.get("CNaction"));
        }
        
        //英文文件type，price level字段生成
        for(int i = 0; i < list.size(); i++){
			try{
				if(list.get(i).getItemID().endsWith("fieldschina") || list.get(i).getEmail() == null){
					list.remove(i);
					continue;
				}
				if(ENTYPEMAP.get(Integer.valueOf(list.get(i).getItemID())) != null)
					list.get(i).setType(ENTYPEMAP.get(Integer.valueOf(list.get(i).getItemID().trim())));
				if(PRICEMAP.get(Integer.valueOf(list.get(i).getItemID())) != null)
					list.get(i).setPriceLevel(Util.getPriceLevel(PRICEMAP.get(Integer.valueOf(list.get(i).getItemID().trim()))));
			}catch(Exception e){
				invalid++;
				list.remove(i);
				continue;
			}
		}
		log.debug("ActionCSV.item有:" + invalid + "个非法数据。");
		//初始化文件数据
		exportData = new ArrayList<Map>();
		for(int i = 0; i < list.size(); i++){
			Map row1 = new LinkedHashMap<String, String>();
			row1.put("1", list.get(i).getEmail());
	        row1.put("2", list.get(i).getItemID());
	        row1.put("3", list.get(i).getType());
	        row1.put("4", list.get(i).getBrandID());
	        row1.put("5", list.get(i).getPriceLevel());
	        row1.put("6", list.get(i).getTagID());
	        row1.put("7", list.get(i).getAction());
	        row1.put("8", list.get(i).getTimesStamp());
	        exportData.add(row1);
		}
        map = new LinkedHashMap();
        map.put("1", "email");
        map.put("2", "Item ID");
        map.put("3", "Type");
        map.put("4", "Brand ID");
        map.put("5", "Price level");
        map.put("6", "Tag ID");
        map.put("7", "Action");
        map.put("8", "Timestamp");
        path = System.getProperty("user.dir") + Util.getProperty("DMACSVOutPutPath");
        String name2 = ENACTION_CSV;
        if(t == 0){
        	name += ("_" + Util.getDay() + ".csv");
 	        FILEMAP.put("ENaction", name2);
 	        cs.createCSVFile(exportData, map, path, FILEMAP.get("ENaction"));
        }else{
        	cs.createCSVFile(exportData, path, FILEMAP.get("ENaction"));
        }
		log.debug("create CSV File：" + name2 + " finished.");
		//清理数据
		t = 0;
		list = null;
		exportData = null;
		CNTYPEMAP.clear();
		ENTYPEMAP.clear();
		PRICEMAP.clear();
	}
	/**
	 * 创建行为信息CSV文件（增量生成新数据）
	 */
	private void createActionCSVIncremental(String begainTimeNew) {
		ActionCSVService actionCSVService = new ActionCSVService();
		CSVService cs = new CSVService();
		//按照时间读取数据
		String begainTime = Util.getProperty("DMAActionCSVInitBegineTime");
		String endTime = Util.getDay();
		if(Util.getProperty("theWayToFindBegainTime").equals("1")){
			log.debug("createActionCSVIncremental toFindBegainTime is check folder!");
			begainTime = begainTimeNew;
		}else{
			log.debug("createActionCSVIncremental toFindBegainTime is read configration!");
			begainTime = Util.getProperty("DMAActionCSVInitBegineTime");
		}
		log.debug("begain time:" + begainTime);
		//得到用户查看行为信息的数据
		List<ActionCSV> list = new ArrayList<ActionCSV>();
		int t = 0;//标志是否是第一次执行运行不同的生成CSV文件的方法
		list = actionCSVService.getAllViewActionCSVs(begainTime, endTime);
		//得到用户购买行为信息的数据
		list.addAll(actionCSVService.getAllPurchasedActionCSVs(begainTime, endTime));
		//得到用户收藏行为的信息数据
		list.addAll(actionCSVService.getAllCollectActionCSVs(begainTime, endTime));
		//得到用户添加购物车信息
		list.addAll(actionCSVService.getAllShoppingCartActionCSVs(begainTime, endTime));
		log.debug("得到行为信息的数据CSV文件数量:" + list.size());
		//添加行为表的type字段，以及过滤tiemid字段内非int类型（字符串：fieldschina）的数据
		int invalid = 0;
		//中文文件数据type,price level生成
		for(int i = 0; i < list.size(); i++){
			try{
				if(list.get(i).getItemID().endsWith("fieldschina") || list.get(i).getEmail() == null){
					list.remove(i);
					continue;
				}
				if(CNTYPEMAP.get(Integer.valueOf(list.get(i).getItemID())) != null)
					list.get(i).setType(CNTYPEMAP.get(Integer.valueOf(list.get(i).getItemID().trim())));
				if(PRICEMAP.get(Integer.valueOf(list.get(i).getItemID())) != null)
					list.get(i).setPriceLevel(Util.getPriceLevel(PRICEMAP.get(Integer.valueOf(list.get(i).getItemID().trim()))));
			}catch(Exception e){
				invalid++;
				list.remove(i);
				continue;
			}
		}
		log.debug("ActionCSV.item有:" + invalid + "个非法数据。");
		//初始化文件数据
		List exportData = new ArrayList<Map>();
		for(int i = 0; i < list.size(); i++){
			Map row1 = new LinkedHashMap<String, String>();
			row1.put("1", list.get(i).getEmail());
	        row1.put("2", list.get(i).getItemID());
	        row1.put("3", list.get(i).getType());
	        row1.put("4", list.get(i).getBrandID());
	        row1.put("5", list.get(i).getPriceLevel());
	        row1.put("6", list.get(i).getTagID());
	        row1.put("7", list.get(i).getAction());
	        row1.put("8", list.get(i).getTimesStamp());
	        exportData.add(row1);
		}
        LinkedHashMap map = new LinkedHashMap();
        map.put("1", "email");
        map.put("2", "Item ID");
        map.put("3", "Type");
        map.put("4", "Brand ID");
        map.put("5", "Price level");
        map.put("6", "Tag ID");
        map.put("7", "Action");
        map.put("8", "Timestamp");
        String path = System.getProperty("user.dir") + Util.getProperty("DMACSVOutPutPath");
        String name = CNACTION_CSV;
        if(t == 0){
        	name += ("_" + Util.getDay() + ".csv");
 	        FILEMAP.put("CNaction", name);
 	        cs.createCSVFile(exportData, map, path, FILEMAP.get("CNaction"));
        }else{
        	cs.createCSVFile(exportData, path, FILEMAP.get("CNaction"));
        }
        
        //英文文件type，price level数据生成
		for(int i = 0; i < list.size(); i++){
			try{
				if(list.get(i).getItemID().endsWith("fieldschina") || list.get(i).getEmail() == null){
					list.remove(i);
					continue;
				}
				if(ENTYPEMAP.get(Integer.valueOf(list.get(i).getItemID())) != null)
					list.get(i).setType(ENTYPEMAP.get(Integer.valueOf(list.get(i).getItemID().trim())));
				if(PRICEMAP.get(Integer.valueOf(list.get(i).getItemID())) != null)
					list.get(i).setPriceLevel(Util.getPriceLevel(PRICEMAP.get(Integer.valueOf(list.get(i).getItemID().trim()))));
			}catch(Exception e){
				invalid++;
				list.remove(i);
				continue;
			}
		}
		log.debug("ActionCSV.item有:" + invalid + "个非法数据。");
		//初始化文件数据
		exportData = new ArrayList<Map>();
		for(int i = 0; i < list.size(); i++){
			Map row1 = new LinkedHashMap<String, String>();
			row1.put("1", list.get(i).getEmail());
	        row1.put("2", list.get(i).getItemID());
	        row1.put("3", list.get(i).getType());
	        row1.put("4", list.get(i).getBrandID());
	        row1.put("5", list.get(i).getPriceLevel());
	        row1.put("6", list.get(i).getTagID());
	        row1.put("7", list.get(i).getAction());
	        row1.put("8", list.get(i).getTimesStamp());
	        exportData.add(row1);
		}
        map = new LinkedHashMap();
        map.put("1", "email");
        map.put("2", "Item ID");
        map.put("3", "Type");
        map.put("4", "Brand ID");
        map.put("5", "Price level");
        map.put("6", "Tag ID");
        map.put("7", "Action");
        map.put("8", "Timestamp");
        path = System.getProperty("user.dir") + Util.getProperty("DMACSVOutPutPath");
        String name2 = ENACTION_CSV;
        if(t == 0){
        	name2 += ("_" + Util.getDay() + ".csv");
 	        FILEMAP.put("ENaction", name2);
 	        cs.createCSVFile(exportData, map, path, FILEMAP.get("ENaction"));
        }else{
        	cs.createCSVFile(exportData, path, FILEMAP.get("ENaction"));
        }
		log.debug("create CSV File：" + name2 + " finished.");
		//清理数据
		t = 0;
		list = null;
		exportData = null;
		CNTYPEMAP.clear();
		ENTYPEMAP.clear();
		PRICEMAP.clear();
	}
	/**
	 * 上传生成的CSV文件到FTP服务器
	 */
	public void uploadCSVFiles(){
		String path = System.getProperty("user.dir") + Util.getProperty("DMACSVOutPutPath");
		File cnuser = new File(path + "/" + FILEMAP.get("cnuser"));
		File enuser = new File(path + "/" + FILEMAP.get("enuser"));
		File cnitem = new File(path + "/" + FILEMAP.get("cnitem"));
		File enitem = new File(path + "/" + FILEMAP.get("enitem"));
		File cnaction = new File(path + "/" + FILEMAP.get("CNaction"));
		File enaction = new File(path + "/" + FILEMAP.get("ENaction"));
		FTPServer fs = new FTPServer();
		try {
			InputStream in1 = new FileInputStream(cnuser);
			boolean b1 = fs.uploadFile(FILEMAP.get("cnuser"), in1, "cn");
			log.info("上传CNuserCSV表结果：" + b1);
			InputStream in5 = new FileInputStream(enuser);
			boolean b5 = fs.uploadFile(FILEMAP.get("enuser"), in5, "en");
			log.info("上传ENuserCSV表结果：" + b5);
			InputStream in2 = new FileInputStream(cnitem);
			boolean b2 = fs.uploadFile(FILEMAP.get("cnitem"), in2, "cn");
			log.info("上传cnitemCSV表结果：" + b2);
			InputStream in3 = new FileInputStream(enitem);
			boolean b3 = fs.uploadFile(FILEMAP.get("enitem"), in3, "en");
			log.info("上传enitemCSV表结果：" + b3);
			InputStream in4 = new FileInputStream(cnaction);
			boolean b4 = fs.uploadFile(FILEMAP.get("CNaction"), in4, "cn");
			log.info("上传CNactionCSV表结果：" + b4);
			InputStream in6 = new FileInputStream(enaction);
			boolean b6 = fs.uploadFile(FILEMAP.get("ENaction"), in6, "en");
			log.info("上传ENactionCSV表结果：" + b6);
		} catch (Exception e) {
			log.error(Util.getTrace(e));
		}
	}
}
