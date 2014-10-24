package com.fieldschina.edm.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.fieldschina.edm.dao.UserActionChangeDao;
import com.fieldschina.edm.dbconn.DBConnectionManager;
import com.fieldschina.edm.entity.Recipient;
import com.fieldschina.edm.entity.UserActionChange;
import com.fieldschina.edm.util.Util;

public class UserActionChangeDaoImpl implements UserActionChangeDao{
	Logger log = Logger.getLogger(UserActionChangeDaoImpl.class);//日志记录
	/**
	 *  得到用户数据在web端的当前数据
	 *  
	 * @return 	用户数据在web端的当前数据
	 */
	public List<UserActionChange> findAllUserActionChangeFromWeb() {
		//存储返回需要的集合
		List<UserActionChange> list = new ArrayList<UserActionChange>();
		boolean b = false;
		DBConnectionManager dbm = null;
		Connection conn = null;
		UserActionChange r = null;
		try {
			dbm = DBConnectionManager.getInstance();
			conn = dbm.getConnection(Util.getProperty("biwebserver"));
			PreparedStatement ps = conn.prepareStatement("select customer_id,email, email, cart, wishlist from oc_customer");
			ResultSet rs = ps.executeQuery();
			//处理返回结果
			while(rs.next()){
				r = new UserActionChange();
				r.setCustomerId(rs.getInt("customer_id"));
				r.setEmail(rs.getString("email"));
				r.setCart(rs.getString("cart"));
				r.setWishlist(rs.getString("wishlist"));
				list.add(r);
			}
			b = true;
			dbm.freeConnection(Util.getProperty("biwebserver"), conn);
			ps.close();
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
	 * 得到用户数据在本地的当前数据
	 * 
	 * @return	用户数据在local的当前数据
	 */
	public List<UserActionChange> findAllUserActionChangeFromLocal() {
		//存储返回需要的集合
		List<UserActionChange> list = new ArrayList<UserActionChange>();
		boolean b = false;
		DBConnectionManager dbm = null;
		Connection conn = null;
		UserActionChange r = null;
		try {
			dbm = DBConnectionManager.getInstance();
			conn = dbm.getConnection(Util.getProperty("localserver"));
			PreparedStatement ps = conn.prepareStatement("select customer_id, email, wishlist, cart, store_remind from user_action_change");
			ResultSet rs = ps.executeQuery();
			//处理返回结果
			while(rs.next()){
				r = new UserActionChange();
				r.setCustomerId(rs.getInt("customer_id"));
				r.setEmail(rs.getString("email"));
				r.setCart(rs.getString("cart"));
				r.setWishlist(rs.getString("wishlist"));
				r.setStoreRemind(rs.getString("store_remind"));
				list.add(r);
			}
			b = true;
			dbm.freeConnection(Util.getProperty("localserver"), conn);
			ps.close();
		} catch (Exception e) {
			log.error(Util.getTrace(e));
			//程序容错处理
			if(dbm != null && conn != null){
				dbm.freeConnection(Util.getProperty("localserver"), conn);
			}
		}
		return list;
	}
	/**
	 * 插入一条新产生的用户变动数据
	 * 
	 * @param userActionChange	新的数据
	 * @return	是否插入成功
	 */
	public boolean insertUserActionChange(UserActionChange userActionChange) {
		boolean b = false;
		DBConnectionManager dbm = null;
		Connection conn = null;
		try {
			dbm = DBConnectionManager.getInstance();
			conn = dbm.getConnection(Util.getProperty("localserver"));
			PreparedStatement ps = conn.prepareStatement("insert into user_action_change(customer_id, email, wishlist, cart, store_remind) values (?,?,?,?,?)");
			ps.setInt(1, userActionChange.getCustomerId());
			ps.setString(2, userActionChange.getEmail());
			ps.setString(3, userActionChange.getWishlist());
			ps.setString(4, userActionChange.getCart());
			ps.setString(5, userActionChange.getStoreRemind());
			int rs = ps.executeUpdate();
			if(rs == 1)
				b = true;
			dbm.freeConnection(Util.getProperty("localserver"), conn);
			ps.close();
		} catch (Exception e) {
			log.error(Util.getTrace(e));
			//程序容错处理
			if(dbm != null && conn != null){
				dbm.freeConnection(Util.getProperty("localserver"), conn);
			}
		}
		return b;
	}
	/**
	 * 更新一条用户的行为变动数据
	 * 
	 * @param userActionChange
	 * @return	是否更新成功
	 */
	public boolean updateUserActionChange(UserActionChange userActionChange) {
		boolean b = false;
		DBConnectionManager dbm = null;
		Connection conn = null;
		try {
			dbm = DBConnectionManager.getInstance();
			conn = dbm.getConnection(Util.getProperty("localserver"));
			PreparedStatement ps = conn.prepareStatement("update user_action_change set email = ?, wishlist = ?, cart = ?, store_remind = ? where customer_id = ?");
			ps.setInt(5, userActionChange.getCustomerId());
			ps.setString(1, userActionChange.getEmail());
			ps.setString(2, userActionChange.getWishlist());
			ps.setString(3, userActionChange.getCart());
			ps.setString(4, userActionChange.getStoreRemind());
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
	/**
	 * 产生一条无用的数据
	 * 
	 * @param email	用户的Email
	 * @return	是否删除成功
	 */
	public boolean deleteStoreRemind(String email) {
		boolean b = false;
		DBConnectionManager dbm = null;
		Connection conn = null;
		try {
			dbm = DBConnectionManager.getInstance();
			conn = dbm.getConnection(Util.getProperty("localserver"));
			PreparedStatement ps = conn.prepareStatement("update user_action_change set store_remind = '' where email = ?");
			ps.setString(1, email);
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
