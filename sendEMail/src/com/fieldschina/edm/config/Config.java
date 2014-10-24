package com.fieldschina.edm.config;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.fieldschina.edm.util.Util;
/**
 * 配置文件读取类
 * 
 * @author  LAVEN  E-mail:lavenwl@gmail.com
 * @company FieldsChina.IT.BI
 * @version 创建时间：2014-6-12 下午5:03:15
 */
public class Config {
	private static Logger log = Logger.getLogger(Config.class);	//日志记录对象
	private static Properties dbProps = null;					//配置文件对象
	private static Config instance = null;						//本类的一个实例
	/**
	 * 配置文件类构造器
	 * 
	 */
	public Config() {
		//初始化配置文件
		init();
	}
	/**
	 * 初始化配置文件方法
	 * 
	 */
	private void init(){
		dbProps = new Properties();
		//读取数据库配置文件
		try {
			//new 一个新的类
//			log.debug("config.properties路径：" + System.getProperty("user.dir"));//测试使用检测工程运行的当前目录
			//String path = System.getProperty("user.dir") + "/config.properties";//fatjar包使用路径
			String path = "src/config.properties";//Eclipse内部运行路径
			//每次运行时都添加新的对象，以保证对象内部读取到的配置文件的值是最新的
			InputStream is = new FileInputStream(path);
			//属于类的方法，得到后停留在缓存内，类文件不被GM就不会改变
			//InputStream is = getClass().getResourceAsStream("/config.properties");
			dbProps.load(is);
		} catch (IOException e) {
			log.error(Util.getTrace(e));
			log.error("不能读取属性文件。请确保config.properties在你的CLASSPATH中");
		}
	}
	/**
	 * 得到当前实体
	 * @return 本类内部唯一实体对象
	 */
	public static Config getInstance(){
		if(instance == null){
			instance = new Config();
		}
		return instance;
	}
	/**
	 * 根据配置属性名得到属性值
	 * 
	 * @param name	属性名
	 * @return		属性值
	 */
	public static String getProperty(String name){
		return dbProps.getProperty(name, null);
	}
	/**
	 * 根据配置属性名修改属性的值（测试方法，方法内部路径只能临时使用在MyEclipse中）
	 * 
	 * @param key	属性名
	 * @param value	属性值
	 * @return		是否修改成功
	 */
	public static boolean setProperty(String key, String value){
		boolean b = false;
		try{
			dbProps.setProperty(key, value);
			FileOutputStream fos=new FileOutputStream("src/config.properties");
			//dbProps对象通过fos输出流将对象写入到固定目录下面，同属添加注释：company name
			dbProps.store(fos, "company name");
			fos.close();
			b = true;
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return b;
	}
	/**
	 * 测试使用方法
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		//测试修改配置文件内的属性值，特别注意：现在这个功能不可用，如果覆盖会重写配置文件，将一些属性删除。
		Config config = new Config();
		//先打印配置文件内的company属性的值
		log.debug(config.getProperty("company"));
		//修改配置文件内的company属性的值，进入内部会发现此操作不是单纯的修改一个属性，而是将整个配置文件的属性重新的写入到配置文件中，导致格式不同和一些没有读到的属性在重写如配置文件时丢失。
		config.setProperty("company", "FIELDSCHINA");
		//修改后的配置文件内company属性的值，确实被修改。
		log.debug(config.getProperty("company"));
		for(int i = 0; i < 100; i++){
			//config.setProperty("company", "FIELDSCHINA" + i);
			log.debug(config.getProperty("company"));
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				log.error(Util.getTrace(e));
			} // end try
		} // end for(int i = 0; i < 100; i++)
	}// end main(String[] args)
	/**
	 * 得到所有属性集合
	 * 
	 * @return 	配置文件的所有属性集合
	 */
	public Enumeration<?> propertyNames() {
		return dbProps.propertyNames();
	}
}
