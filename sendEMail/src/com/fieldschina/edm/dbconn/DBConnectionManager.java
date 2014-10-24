package com.fieldschina.edm.dbconn;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;
import org.apache.log4j.Logger;

import com.fieldschina.edm.util.Util;
/**
 * 数据库连接池管理类
 * 
 * @author  LAVEN  E-mail:lavenwl@gmail.com
 * @company FieldsChina.IT.BI
 * @version 创建时间：2014-6-12 下午3:59:06
 */
public class DBConnectionManager {
	private static DBConnectionManager instance;			//客户端实例
    private static PrintWriter log;							//数据库连接log
    private static Logger log4j = Logger.getLogger(DBConnectionManager.class);
    private static int clients;								//获取连接对象统计
	private Vector<Driver> drivers = new Vector<Driver>();	//不同数据库种类的驱动队列
	private Hashtable<String, DBConnectionPool> pools = new Hashtable<String, DBConnectionPool>();
	/**
	 * 得到数据库管理类的唯一实例
	 * @throws Exception 
	 */
	static synchronized public DBConnectionManager getInstance() throws Exception {
		if (instance == null) {
			instance = new DBConnectionManager();
		}
		clients++;
		//log4j.debug("clients:" + clients);
		return instance;
	}
	public static void setInstanceNull(){
		instance = null;
	}
	/**
	 * 数据库管理类构造函数
	 * @throws Exception 
	 */
	public DBConnectionManager() throws Exception {
		init();
	}
	/**
	 * 释放一个连接
	 * 
	 * @param name	连接池名称
	 * @param con	连接对象
	 */
	public void freeConnection(String name, Connection con) {
		DBConnectionPool pool = (DBConnectionPool) pools.get(name);
		if (pool != null) {
			pool.freeConnection(con);
		}
	}
	/**
	 * 取得一个连接
	 * 
	 * @param name	连接池名称
	 * @return		该连接池下的一个连接
	 */
	public Connection getConnection(String name) {
		DBConnectionPool pool = (DBConnectionPool) pools.get(name);
		//log4j.debug("pool.name:" + name + "clients:" + clients + " pools.checkout:" + pool.getCheckOut() + " pool.freeConnection:" + pool.getFCSum());
		if (pool != null) {
			return pool.getConnection();
		}
		return null;
	}
	/**
	 * 取得一个连接（记录触发者）
	 * 
	 * @param name		连接池名称
	 * @param customer	触发者记录字段
	 * @return			该连接池下的一个连接
	 */
	public Connection getConnection(String name, String customer) {
		DBConnectionPool pool = (DBConnectionPool) pools.get(name);
		if (pool != null) {
			return pool.getConnection(customer);
		}
		return null;
	}
	/**
	 * 关闭所有连接
	 */
	public synchronized void release() {
		//关闭所有连接池内部的连接
		Enumeration<DBConnectionPool> allPools = pools.elements();
		while (allPools.hasMoreElements()) {
			DBConnectionPool pool = (DBConnectionPool) allPools.nextElement();
			pool.release();
		}
		//注销数据库驱动
		Enumeration<Driver> allDrivers = drivers.elements();
		while (allDrivers.hasMoreElements()) {
			Driver driver = (Driver) allDrivers.nextElement();
			try {
				DriverManager.deregisterDriver(driver);
				log("撤消JDBC驱动程序" + driver.getClass().getName());
			} catch (SQLException e) {
				log4j.error(Util.getTrace(e));
				log(e, "无法撤消JDBC驱动程序的注册" + driver.getClass().getName());
			}
		} // end while (allDrivers.hasMoreElements())
	}
	/**
	 * 关闭所有连接（记录触发用户名称）
	 * 
	 * @param customer	记录触发者字段
	 */
	public synchronized void release(String customer) {
		//关闭所有连接池内部的连接
		Enumeration<DBConnectionPool> allPools = pools.elements();
		while (allPools.hasMoreElements()) {
			DBConnectionPool pool = (DBConnectionPool) allPools.nextElement();
			pool.release(customer);
		}
		//注销数据库驱动
		Enumeration<Driver> allDrivers = drivers.elements();
		while (allDrivers.hasMoreElements()) {
			Driver driver = (Driver) allDrivers.nextElement();
			try {
				DriverManager.deregisterDriver(driver);
				log(customer + "撤消JDBC驱动程序" + driver.getClass().getName());
			} catch (SQLException e) {
				log4j.error(Util.getTrace(e));
				log(e, "无法撤消JDBC驱动程序的注册" + driver.getClass().getName());
			}
		}
	}
    /**
     * 创建数据库连接池类
     * @param props 配置文件(连接池参数)
     */
	private void createPools(Properties props) {
		int max = 0; //数据库最大连接数
		//得到配置文件内的所有参数名
		Enumeration<?> propNames = props.propertyNames();
		while (propNames.hasMoreElements()) {
			String name = (String) propNames.nextElement();
			if (name.endsWith(".url")) {
				//得到连接池名称
				String poolName = name.substring(0, name.lastIndexOf("."));
				String url = props.getProperty(poolName + ".url");
				if (url == null) {
					log4j.info("没有连接池" + poolName + "指定的URL");
					continue;
				}
				String user = props.getProperty(poolName + ".user");
				String password = props.getProperty(poolName + ".password");
				String maxconn = props.getProperty(poolName + ".maxconn", "0");
				try {
					max = Integer.valueOf(maxconn).intValue();
				} catch (NumberFormatException e) {
					log4j.error(Util.getTrace(e));
					log4j.info("错误的最大连接数：" + maxconn + ".连接池" + poolName);
					throw new NumberFormatException();
				}
				//创建一个新的连接池
				//log4j.debug("创建一个新的连接池:poolName:" + poolName + " url:" + url + " user:" + user + " password:" + password + " max:" + max);
				DBConnectionPool pool = new DBConnectionPool(poolName, url,
						user, password, max);
				//将新建连接池添加到连接池队列
				pools.put(poolName, pool);
				log("成功创建连接池" + poolName);
			} // end if (name.endsWith(".url"))
		} // end while (propNames.hasMoreElements())
	}
	/**
	 * 初始化数据库管理类
	 * @throws Exception 
	 */
	private void init() throws Exception {
		//读取数据库配置文件
		InputStream is = getClass().getResourceAsStream("db.properties");
		Properties dbProps = new Properties();
		try {
			dbProps.load(is);
		} catch (IOException e) {
			log4j.error(Util.getTrace(e));
			log4j.error("不能读取属性文件。请确保db.properties在你的CLASSPATH中");
			throw new IOException(e);
		}
		//初始化数据库连接日志
		String path = System.getProperty("user.dir") + "logs/DBConnectionManager.log";//在那里点击返回那里路径
		String logFile = dbProps.getProperty("logfile", path);
		try {
			log = new PrintWriter(new FileWriter(logFile, true), true);
		} catch (IOException e) {
			log4j.error(Util.getTrace(e));
			log4j.error("无法打开日志文件：" + logFile);
			throw new IOException(e);
		}
		//注册数据库连接驱动程序
		loadDriver(dbProps);
		//创建连接池
		createPools(dbProps);
	}
    /**
     * 数据库连接驱动程序注册类
     * @param props 配置文件(驱动参数)
     * @throws Exception 
     */
	private void loadDriver(Properties props) throws Exception {
		log4j.debug("DBConnectionManager.loadDriver");
		List<String> list = new ArrayList<String>();
		String driverClasses = props.getProperty("driversMysql");
		if(driverClasses != null){
			list.add(driverClasses);
		}
		String driverClasses2 = props.getProperty("driversOracle");
		if(driverClasses2 != null){
			list.add(driverClasses2);
		}
		StringTokenizer st = new StringTokenizer(driverClasses);
		for(int i = 0; i < list.size(); i++){
			String driverClassName = list.get(i);
			try {
				Driver driver = (Driver) Class.forName(driverClassName).newInstance();
				DriverManager.registerDriver(driver);
				drivers.addElement(driver);
				log("成功注册驱动程序" + driverClassName);
			} catch (Exception e) {
				log4j.error(Util.getTrace(e));
				log4j.error("无法注册驱动程序:" + driverClassName + ",错误" + e);
				throw new Exception(e);
			}
		}
	}
	/**
	 * 数据库log记录消息方法
	 * @param msg	消息对象
	 */
	public static void log(String msg) {
		log.println(new Date() + ":" + msg);
	}
	/**
	 * 数据库记录异常及消息方法
	 * @param e		异常对象
	 * @param msg	消息对象
	 */
	public static void log(Throwable e, String msg) {
		log.println(new Date() + ":" + msg);
		e.printStackTrace(log);
	}
}
/**
 * 连接池类
 * 
 * @author  LAVEN  E-mail:lavenwl@gmail.com
 * @company FieldsChina.IT.BI
 * @version 创建时间：2014-6-12 下午12:49:08
 */
class DBConnectionPool {
	private static Logger log4j = Logger.getLogger(DBConnectionManager.class); //log记录对象
	private Vector freeConnections = new Vector(); 	//空闲连接队列
	private int checkOut;							//正在使用的连接数量
	private int maxconn;							//最大连接数
	private String name;							//连接池的名称
	private String password;						//连接使用用户的密码
	private String URL;								//连接的具体参数
	private String user;							//连接使用用户的名称

	/**
	 * 连接池类构造器
	 * @param name		连接池名称
	 * @param URL		连接池路径参数
	 * @param user		连接池用户名称
	 * @param password	连接池用户密码
	 * @param maxconn	连接池最大连接数
	 */
	public DBConnectionPool(String name, String URL, String user,
			String password, int maxconn) {
		this.name = name;
		this.URL = URL;
		this.password = password;
		this.user = user;
		this.maxconn = maxconn;
	}
	/**
	 * 释放一个连接
	 * 
	 * @param con	连接对象
	 */
	public synchronized void freeConnection(Connection con) {
		freeConnections.addElement(con);
		checkOut--;
		//唤醒所有在此方法wait()的线程
		notifyAll();
	}
	/**
	 * 获得一个连接
	 * 
	 * @return	连接对象
	 */
	public synchronized Connection getConnection() {
		Connection con = null;
		if (freeConnections.size() > 0) {
			//连接队列中得到一个连接
			//log4j.debug("连接队列中得到一个连接");
			con = (Connection) freeConnections.firstElement();
			freeConnections.removeElementAt(0);
			try {
				//检测连接是否已关闭
				if (con.isClosed()) {
					DBConnectionManager.log("从连接池" + name + "删除一个连接");
					con = getConnection();
				}
			} catch (SQLException e) {
				log4j.error(Util.getTrace(e));
				log4j.error("从连接池" + name + "删除一个连接" + "/n" + e.getMessage());
				DBConnectionManager.log("从连接池" + name + "删除一个连接");
				con = getConnection();
			}
		} else if (maxconn == 0 || checkOut < maxconn) {
			//log4j.debug("newConnection得到一个连接");
			con = newConnection();
		}
		//得到连接后更新正在使用的连接数量
		if (con != null) {
			checkOut++;
		}
		return con;
	}
	/**
	 * 得到一个连接（记录触发用户名）
	 * 
	 * @param customer	触发用户记录字段
	 * @return			连接对象
	 */
	public synchronized Connection getConnection(String customer) {
		Connection con = null;
		if (freeConnections.size() > 0) {
			//连接队列中得到一个连接
			con = (Connection) freeConnections.firstElement();
			freeConnections.removeElementAt(0);
			try {
				//检测连接是否已关闭
				if (con.isClosed()) {
					DBConnectionManager.log("从连接池" + name + "删除一个连接");
					con = getConnection();
				}
			} catch (SQLException e) {
				log4j.error(Util.getTrace(e));
				log4j.error("从连接池" + name + "删除一个连接" + "/n" + e.getMessage());
				DBConnectionManager.log("从连接池" + name + "删除一个连接");
				con = getConnection();
			}
		} else if (maxconn == 0 || checkOut < maxconn) {
			con = newConnection(customer);
		}
		//得到连接后更新正在使用的连接数量
		if (con != null) {
			checkOut++;
		}
		return con;
	}
	/**
	 * 关闭连接池中的所有连接
	 */
	public void release() {
		//得到所有的空闲连接
		Enumeration allConnections = freeConnections.elements();
		while (allConnections.hasMoreElements()) {
			Connection con = (Connection) allConnections.nextElement();
			try {
				con.close();
				DBConnectionManager.log("关闭连接池" + name + "中的连接");
			} catch (SQLException e) {
				log4j.error(Util.getTrace(e));
				DBConnectionManager.log(e, "无法关闭连接池" + name + "中的连接");
			}
		}
		//移除所有的空闲连接
		freeConnections.removeAllElements();
	}
	/**
	 * 关闭连接池中的所有连接（记录触发用户名称）
	 * 
	 * @param customer	触发用户记录变量
	 */
	public void release(String customer) {
		//得到空闲连接队列年的所有对象
		Enumeration allConnections = freeConnections.elements();
		while (allConnections.hasMoreElements()) {
			Connection con = (Connection) allConnections.nextElement();
			try {
				con.close();
				DBConnectionManager.log(customer + "关闭连接池" + name + "中的连接");
			} catch (SQLException e) {
				log4j.error(Util.getTrace(e));
				DBConnectionManager.log(e, "无法关闭连接池" + name + "中的连接");
			}
		}
		//移除空闲队列内的所有对象
		freeConnections.removeAllElements();
	}
	/**
	 * 创建一个新的连接
	 * 
	 * @return	连接对象
	 */
	private Connection newConnection() {
		Connection con = null;
		try {
			con = DriverManager.getConnection(URL, user, password);
			DBConnectionManager.log("连接池" + name + "创建一个新的连接");
		} catch (SQLException e) {
			log4j.error(Util.getTrace(e));
			log4j.error("无法创建下列URL的连接" + URL + "/n" + e.getMessage());
			DBConnectionManager.log(e, "无法创建下列URL的连接" + URL);
			return null;
		}
		return con;
	}
	/**
	 * 创建一个新的连接（记录触发用户名）
	 * 
	 * @param customer	记录触发用户使用变量
	 * @return			连接对象
	 */
	private Connection newConnection(String customer) {
		Connection con = null;
		try {
			con = DriverManager.getConnection(URL, user, password);
			DBConnectionManager.log(customer + "从连接池" + name + "创建一个新的连接");
		} catch (SQLException e) {
			log4j.error(Util.getTrace(e));
			log4j.error("无法创建下列URL的连接" + URL + "/n" + e.getMessage());
			DBConnectionManager.log(e, "无法创建下列URL的连接" + URL);
			return null;
		}
		return con;
	}
	/**
	 * 得到正在使用的连接数量(测试使用)
	 * 
	 * @return	正在使用的链接数量
	 */
	public int getCheckOut(){
		return checkOut;
	}
	/**
	 * 得到空闲连接数
	 * 
	 * @return
	 */
	public int getFCSum(){
		return freeConnections.size();
	}
}