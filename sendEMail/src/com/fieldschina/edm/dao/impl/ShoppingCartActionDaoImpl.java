package com.fieldschina.edm.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;

import org.apache.log4j.Logger;

import com.fieldschina.edm.dao.ShoppingCartActionDao;
import com.fieldschina.edm.dbconn.DBConnectionManager;
import com.fieldschina.edm.entity.FavouriteAction;
import com.fieldschina.edm.entity.ShoppingCartAction;
import com.fieldschina.edm.util.Util;
/**
 * 购物车数据监听数据库操作实现类
 * 
 * @author  LAVEN  E-mail:lavenwl@gmail.com
 * @company FieldsChina.IT.BI
 * @version 创建时间：2014-7-24 下午4:58:26
 */
public class ShoppingCartActionDaoImpl implements ShoppingCartActionDao{
	Logger log = Logger.getLogger(ShoppingCartActionDaoImpl.class);//日志记录
	/**
	 * 插入一条新的数据
	 * 
	 */
	public boolean insertShoppingCartAction(ShoppingCartAction shoppingCartAction) {
		boolean b = false;
		DBConnectionManager dbm = null;
		Connection conn = null;
		try {
			dbm = DBConnectionManager.getInstance();
			conn = dbm.getConnection(Util.getProperty("localserver"));
			PreparedStatement ps = conn.prepareStatement("insert into shopping_cart_action(customer_id, email, item_id, start_time, end_time) values (?,?,?,?,?)");
			ps.setInt(1, shoppingCartAction.getCustomerId());
			ps.setString(2, shoppingCartAction.getEmail());
			ps.setInt(3, shoppingCartAction.getItemId());
			ps.setDate(4, (Date) Util.paraseStringToTime(shoppingCartAction.getStartTime()));
			ps.setDate(5, (Date) Util.paraseStringToTime(shoppingCartAction.getEndTime()));
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
	 * 查询所有的数据
	 * 
	 */
	public List<ShoppingCartAction> findAllShoppingCartAction() {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 查询部分符合时间条件的数据
	 * 
	 */
	public List<ShoppingCartAction> findSomeShoppingCartAction() {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 更新一条新的数据
	 * 
	 */
	public boolean updateShoppingCartAction(ShoppingCartAction shoppingCartAction) {
		log.debug("updateShoppingCartAction.endtime:" + shoppingCartAction.getEndTime() );
		boolean b = false;
		DBConnectionManager dbm = null;
		Connection conn = null;
		try {
			dbm = DBConnectionManager.getInstance();
			conn = dbm.getConnection(Util.getProperty("localserver"));
			PreparedStatement ps = conn.prepareStatement("update shopping_cart_action set end_time = ? where customer_id = ? and item_id = ?");
			ps.setInt(2, shoppingCartAction.getCustomerId());
			ps.setInt(3, shoppingCartAction.getItemId());
			ps.setDate(1, (Date) Util.paraseStringToTime(shoppingCartAction.getEndTime()));
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

}
