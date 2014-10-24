package com.fieldschina.edm.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;

import org.apache.log4j.Logger;

import com.fieldschina.edm.dao.FavouriteActionDao;
import com.fieldschina.edm.dbconn.DBConnectionManager;
import com.fieldschina.edm.entity.FavouriteAction;
import com.fieldschina.edm.util.Util;
/**
 * 收藏数据监听数据库操作实现类
 * 
 * @author  LAVEN  E-mail:lavenwl@gmail.com
 * @company FieldsChina.IT.BI
 * @version 创建时间：2014-7-24 下午4:59:06
 */
public class FavouriteActionDaoImpl implements FavouriteActionDao{
	Logger log = Logger.getLogger(FavouriteActionDaoImpl.class);//日志记录
	/**
	 * 插入一条新的数据
	 * 
	 */
	public boolean insertFavouriteAction(FavouriteAction favouriteAction) {
		boolean b = false;
		DBConnectionManager dbm = null;
		Connection conn = null;
		try {
			dbm = DBConnectionManager.getInstance();
			conn = dbm.getConnection(Util.getProperty("localserver"));
			PreparedStatement ps = conn.prepareStatement("insert into favourite_action(customer_id, email, item_id, start_time, end_time) values (?,?,?,?,?)");
			ps.setInt(1, favouriteAction.getCustomerId());
			ps.setString(2, favouriteAction.getEmail());
			ps.setInt(3, favouriteAction.getItemId());
			ps.setDate(4, (Date) Util.paraseStringToTime(favouriteAction.getStartTime()));
			ps.setDate(5, (Date) Util.paraseStringToTime(favouriteAction.getEndTime()));
			int rs = ps.executeUpdate();
			if(rs == 1)
				b = true;
			dbm.freeConnection(Util.getProperty("localserver"), conn);
			ps.close();
		} catch (Exception e) {
			log.error(Util.getTrace(e));
			if(dbm != null && conn != null){
				dbm.freeConnection(Util.getProperty("localserver"), conn);
			}
		}
		return b;
	}
	/**
	 * 查询所有的数据
	 * 
	 */
	public List<FavouriteAction> findAllFavouriteAction() {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 查询部分符合时间条件的数据
	 * 
	 */
	public List<FavouriteAction> findSomeFavouriteAction() {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 更新一条新的数据
	 * 
	 */
	public boolean updateFavouriteAction(FavouriteAction favouriteAction) {
		boolean b = false;
		DBConnectionManager dbm = null;
		Connection conn = null;
		try {
			dbm = DBConnectionManager.getInstance();
			conn = dbm.getConnection(Util.getProperty("localserver"));
			PreparedStatement ps = conn.prepareStatement("update favourite_action set end_time = ? where customer_id = ? and item_id = ?");
			ps.setInt(2, favouriteAction.getCustomerId());
			ps.setInt(3, favouriteAction.getItemId());
			ps.setDate(1, (Date) Util.paraseStringToTime(favouriteAction.getEndTime()));
			int rs = ps.executeUpdate();
			if(rs == 1)
				b = true;
			dbm.freeConnection(Util.getProperty("localserver"), conn);
			ps.close();
		} catch (Exception e) {
			log.error(Util.getTrace(e));
			//程序的容错处理
			if(dbm != null && conn != null){
				dbm.freeConnection(Util.getProperty("localserver"), conn);
			}
		}
		return b;
	}

}
