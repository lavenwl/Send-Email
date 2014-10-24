package com.fieldschina.edm.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.fieldschina.edm.dao.WebProcedureResultDao;
import com.fieldschina.edm.dbconn.DBConnectionManager;
import com.fieldschina.edm.entity.WebProcedureResult;
import com.fieldschina.edm.entity.UserCSV;
import com.fieldschina.edm.util.Util;
/**
 * web存储过程结果数据库操作类
 * 
 * @author  LAVEN  E-mail:lavenwl@gmail.com
 * @company FieldsChina.IT.BI
 * @version 创建时间：2014-7-23 下午4:04:59
 */
public class WebProcedureResultDaoImpl implements WebProcedureResultDao{
	Logger log = Logger.getLogger(WebProcedureResultDao.class);//日志记录
	/**
	 * 检查存储过程是否执行完毕
	 * 
	 * @return
	 */
	public WebProcedureResult isProcedureFinished() {
		boolean result = false;
		DBConnectionManager dbm = null;
		Connection conn = null;
		UserCSV r = null;
		WebProcedureResult webProcedureResult = null;
		try {
			dbm = DBConnectionManager.getInstance();
			conn = dbm.getConnection(Util.getProperty("webserver"));
			PreparedStatement ps = conn.prepareStatement("select * from web_proc_result where add_date > ? and add_date < ? and run_flag = 1");
			ps.setString(1, Util.getDay());
			ps.setString(2, Util.getDayTime());
			log.debug("检测数据库内的存储过程是否完成时的时间范围：day:" + Util.getDay() + "daytime:" + Util.getDayTime());
			ResultSet rs = ps.executeQuery();
			// 处理返回结果
			while(rs.next()){
				webProcedureResult = new WebProcedureResult();
				webProcedureResult.setId(rs.getInt("id"));
				webProcedureResult.setBusinessDate(rs.getTimestamp("business_date") == null ? "1900-01-01 12:00:00" : Util.formateTime(new Date(rs.getTimestamp("business_date").getTime())));
				webProcedureResult.setRunFlag(rs.getInt("run_flag"));
				webProcedureResult.setRecordNum(rs.getInt("record_num"));
				webProcedureResult.setAddDate(rs.getTimestamp("add_date") == null ? "1900-01-01 12:00:00" : Util.formateTime(new Date(rs.getTimestamp("add_date").getTime())));
				result = true;
			}
			dbm.freeConnection(Util.getProperty("webserver"), conn);
			ps.close();
			rs.close();
			//如果web端记录已经插入，则插入脚本开始运行时间
//			if(result){
//				//同步数据到本地数据库
//				updateToLocal(webProcedureResult);
//				updateWpStartTime();
//			}
		} catch (Exception e) {
			log.error(Util.getTrace(e));
			//容错处理
			if(dbm != null && conn != null){
				dbm.freeConnection(Util.getProperty("webserver"), conn);
			}
		}
		log.debug("返回result:" + result);
		return webProcedureResult;
	}
	/**
	 * 同步web数据库中存储过程执行结果记录表到本地数据库方便添加自己脚本执行的开始结束数据
	 * 
	 * @param webProcedureResult
	 */
	public boolean updateToLocal(WebProcedureResult webProcedureResult) {
		log.debug("开始执行同步到本地数据库的数据webProcedureResult:" + webProcedureResult.toString());
		boolean result = false;
		DBConnectionManager dbm = null;
		Connection conn = null;
		UserCSV r = null;
		try {
			dbm = DBConnectionManager.getInstance();
			conn = dbm.getConnection(Util.getProperty("localserver"));
//			PreparedStatement ps2 = conn.prepareStatement("insert into web_proc_result (id, business_date, run_flag, record_num, add_date, wpStartTime, wpEndTime) values (?,?,?,?,?,?,?)");
			
			PreparedStatement ps = conn.prepareStatement("insert into web_proc_result (id, business_date, run_flag, record_num, add_date, wp_start_time, wp_end_time) values (?,to_date(?,'yyyy-mm-dd hh24:mi:ss'),?,?,to_date(?,'yyyy-mm-dd hh24:mi:ss'),to_date(?,'yyyy-mm-dd hh24:mi:ss'),to_date(?,'yyyy-mm-dd hh24:mi:ss'))");
			ps.setInt(1, webProcedureResult.getId());
			ps.setString(2, webProcedureResult.getBusinessDate());
			ps.setInt(3, webProcedureResult.getRunFlag());
			ps.setInt(4, webProcedureResult.getRecordNum());
			ps.setString(5, webProcedureResult.getAddDate());
			ps.setString(6, webProcedureResult.getWpStartTime());
			ps.setString(7, webProcedureResult.getWpEndTime());
			int rs = ps.executeUpdate();
			if(rs == 1){
				result = true;
			}
			dbm.freeConnection(Util.getProperty("localserver"), conn);
			ps.close();
		} catch (Exception e) {
			log.error(Util.getTrace(e));
			if(dbm != null && conn != null){
				dbm.freeConnection(Util.getProperty("localserver"), conn);
			}
		}
		return result;
	}
	/**
	 * 插入脚本开始运行的时间
	 * 
	 * @return
	 */
	public boolean updateWpStartTime() {
		boolean result = false;
		DBConnectionManager dbm = null;
		Connection conn = null;
		UserCSV r = null;
		try {
			dbm = DBConnectionManager.getInstance();
			conn = dbm.getConnection(Util.getProperty("localserver"));
			PreparedStatement ps = conn.prepareStatement("update web_proc_result set wp_start_time = ? where add_date > ? and add_date < ? and run_flag = 1");
			ps.setString(1, Util.getDayTime());
			ps.setString(2, Util.getDay());
			ps.setString(3, Util.getDayTime());
			log.debug("day:" + Util.getDay() + "daytime:" + Util.getDayTime());
			int rs = ps.executeUpdate();
			if(rs == 1){
				result = true;
			}
			dbm.freeConnection(Util.getProperty("localserver"), conn);
			ps.close();
		} catch (Exception e) {
			log.error(Util.getTrace(e));
			if(dbm != null && conn != null){
				dbm.freeConnection(Util.getProperty("localserver"), conn);
			}
		}
		return result;
	}
	
	/**
	 * 插入脚本结束运行的时间
	 * 
	 * @return
	 */
	public boolean updateWpEndTime() {
		boolean result = false;
		DBConnectionManager dbm = null;
		Connection conn = null;
		UserCSV r = null;
		try {
			dbm = DBConnectionManager.getInstance();
			conn = dbm.getConnection(Util.getProperty("localserver"));
			PreparedStatement ps = conn.prepareStatement("update web_proc_result set wp_end_time = ? where add_date > ? and add_date < ? and run_flag = 1");
			ps.setString(1, Util.getDayTime());
			ps.setString(2, Util.getDay());
			ps.setString(3, Util.getDayTime());
			log.debug("day:" + Util.getDay() + "daytime:" + Util.getDayTime());
			int rs = ps.executeUpdate();
			if(rs == 1){
				result = true;
			}
			dbm.freeConnection(Util.getProperty("localserver"), conn);
			ps.close();
		} catch (Exception e) {
			log.error(Util.getTrace(e));
			if(dbm != null && conn != null){
				dbm.freeConnection(Util.getProperty("localserver"), conn);
			}
		}
		return result;
	}

}
