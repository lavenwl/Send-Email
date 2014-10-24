package com.fieldschina.edm.handler;

import org.apache.log4j.Logger;
/**
 * handler父类，用于其他handler子类的继承
 * 
 * @author  LAVEN  E-mail:lavenwl@gmail.com
 * @company FieldsChina.IT.BI
 * @version 创建时间：2014-7-23 下午3:34:35
 */
public class Handler {
	protected static Logger log = null;
	//静态程序块，初始化log对象
	static{
		if(log == null){
			log = Logger.getLogger(Handler.class);
		}
	}
}
