package com.fieldschina.edm.service;

import org.apache.log4j.Logger;

import com.dmdelivery.webservice.DMdeliverySoapAPI;
import com.dmdelivery.webservice.DMdeliverySoapAPIPort;
import com.dmdelivery.webservice.ObjectFactory;
import com.dmdelivery.webservice.type.DMdeliveryLoginType;
import com.fieldschina.edm.config.Config;
/**
 * 逻辑类的父类，用户其他子类继承
 * 
 * @author  LAVEN  E-mail:lavenwl@gmail.com
 * @company FieldsChina.IT.BI
 * @version 创建时间：2014-7-23 下午3:04:10
 */
public class Service {
	protected static ObjectFactory oFactory = null;
	protected static DMdeliveryLoginType login = null;
	protected static DMdeliverySoapAPIPort service = null;
	protected static Logger log = null;
	/**
	 * 静态程序块，初始化逻辑类需要的类
	 * 
	 */
	static{
		if(oFactory == null){
			oFactory = new ObjectFactory();
		}
		if(login == null){
			login = new DMdeliveryLoginType();
			login.setUsername(Config.getInstance().getProperty("username"));
			login.setPassword(Config.getInstance().getProperty("password"));
		}
		if(service == null){
			DMdeliverySoapAPI API = new DMdeliverySoapAPI();
			service = API.getDMdeliverySoapAPIPort();
		}
		if(log == null){
			log = Logger.getLogger(Service.class);
		}
	}
}
