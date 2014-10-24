package com.fieldschina.edm.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.fieldschina.edm.dao.UserCSVDao;
import com.fieldschina.edm.dbconn.DBConnectionManager;
import com.fieldschina.edm.entity.Recipient;
import com.fieldschina.edm.entity.UserCSV;
import com.fieldschina.edm.util.Util;
/**
 * 用户信息数据库操作实现类
 * 
 * @author  LAVEN  E-mail:lavenwl@gmail.com
 * @company FieldsChina.IT.BI
 * @version 创建时间：2014-7-8 下午2:39:35
 */
public class UserCSVDaoImpl implements UserCSVDao{
	Logger log = Logger.getLogger(UserCSVDaoImpl.class);//日志记录
	/**
	 * 得到所有的中国用户信息
	 * 
	 * @return
	 */
	public List<UserCSV> getAllCNUserCSVs() {
		List<UserCSV> list = new ArrayList<UserCSV>();
		DBConnectionManager dbm = null;
		Connection conn = null;
		UserCSV r = null;
		try {
			dbm = DBConnectionManager.getInstance();
			conn = dbm.getConnection(Util.getProperty("biwebserver"));
			PreparedStatement ps = conn.prepareStatement("select email, phone, sex from oc_customer where nationality_status = 1");
			ResultSet rs = ps.executeQuery();
			//处理返回结果
			while(rs.next()){
				r = new UserCSV();
				r.setEmail(rs.getString("email"));
				r.setMobile(rs.getString("phone"));
				r.setGender(rs.getInt("sex") == 1 ? 1 : rs.getInt("sex") == 2 ? 0 : 2);
				list.add(r);
			}
			dbm.freeConnection(Util.getProperty("biwebserver"), conn);
			ps.close();
			rs.close();
		} catch (Exception e) {
			log.error(Util.getTrace(e));
			//程序容错处理
			if(dbm != null && conn != null){
				dbm.freeConnection(Util.getProperty("biwebserver"), conn);
			}
		}
		return list;
	}
	/**
	 * 得到所有的外国用户信息
	 * 
	 * @return
	 */
	public List<UserCSV> getAllENUserCSVs() {
		//返回结果集合
		List<UserCSV> list = new ArrayList<UserCSV>();
		DBConnectionManager dbm = null;
		Connection conn = null;
		UserCSV r = null;
		try {
			dbm = DBConnectionManager.getInstance();
			conn = dbm.getConnection(Util.getProperty("biwebserver"));
			PreparedStatement ps = conn.prepareStatement("select email, phone, sex from oc_customer where nationality_status = 0");
			ResultSet rs = ps.executeQuery();
			//处理返回集合
			while(rs.next()){
				r = new UserCSV();
				r.setEmail(rs.getString("email"));
				r.setMobile(rs.getString("phone"));
				r.setGender(rs.getInt("sex") == 1 ? 1 : rs.getInt("sex") == 2 ? 0 : 2);
				list.add(r);
			}
			dbm.freeConnection(Util.getProperty("biwebserver"), conn);
			ps.close();
			rs.close();
		} catch (Exception e) {
			log.error(Util.getTrace(e));
			// 程序容错处理
			if(dbm != null && conn != null){
				dbm.freeConnection(Util.getProperty("biwebserver"), conn);
			}
		}
		return list;
	}

}
