package com.fieldschina.edm.dbconn;

import java.sql.Connection;

import org.apache.log4j.Logger;

import com.fieldschina.edm.config.Config;
/**
 * 测试DBConnedtionMannager使用的测试类
 * 
 * @author  LAVEN  E-mail:lavenwl@gmail.com
 * @company FieldsChina.IT.BI
 * @version 创建时间：2014-9-25 下午2:08:07
 */
public class Test {
	private static Logger log = Logger.getLogger(Config.class);	//日志记录对象
	
	public static void main(String[] args) {
		try{
			//测试DBConnectionManager.java
			DBConnectionManager dbp = DBConnectionManager.getInstance();
			DBConnectionManager dbp2 = DBConnectionManager.getInstance();
			Connection conn = dbp.getConnection("localserver");
			log.debug("Loading:serverId:" + " Connection:co:" + conn);
			dbp.freeConnection("localserver", conn);
			Connection conne = dbp.getConnection("localserver");
			log.debug("Loading:serverId:" + " Connection:co:" + conne);
			dbp.freeConnection("localserver", conne);
			Connection conn3 = dbp.getConnection("localserver");
			log.debug("Loading:serverId:" + " Connection:co:" + conn3);
			dbp.freeConnection("localserver", conn3);
			
//			//测试config.java
//			Config config = new Config();
//			log.debug(config.getProperty("company"));
//			config.setProperty("company", "FIELDSCHINA");
//			for(int i = 0; i < 100; i++){
//				log.debug(config.getProperty("company"));
//				try {
//					Thread.sleep(1000);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
