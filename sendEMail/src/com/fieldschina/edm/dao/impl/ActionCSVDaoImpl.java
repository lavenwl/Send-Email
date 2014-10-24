package com.fieldschina.edm.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.fieldschina.edm.dao.ActionCSVDao;
import com.fieldschina.edm.dbconn.DBConnectionManager;
import com.fieldschina.edm.entity.ActionCSV;
import com.fieldschina.edm.entity.UserCSV;
import com.fieldschina.edm.util.Util;

/**
 * 用户行为数据库操作实现类
 * 
 * @author  LAVEN  E-mail:lavenwl@gmail.com
 * @company FieldsChina.IT.BI
 * @version 创建时间：2014-7-8 下午2:41:03
 */
public class ActionCSVDaoImpl implements ActionCSVDao{
	Logger log = Logger.getLogger(ActionCSVDaoImpl.class);//日志记录
	/**
	 * 得到所有的用户查看行为数据
	 * 
	 * @return	所有的用户查看行为数据
	 */
	public List<ActionCSV> getAllViewActionCSVs(String begainTime, String endTime) {
		List<ActionCSV> list = new ArrayList<ActionCSV>();//用于存放得到返回的结果
		DBConnectionManager dbm = null;
		Connection conn = null;
		ActionCSV r = null;
		try {
			dbm = DBConnectionManager.getInstance();
			conn = dbm.getConnection(Util.getProperty("piwikwebserver"));
			PreparedStatement ps = conn.prepareStatement("SELECT " + 
				"piwik_log_visit.custom_var_v1 email, " +  
				"SUBSTRING_INDEX(SUBSTRING_INDEX(piwik_log_action.`name`,'-',-1),'.',1) itemid, " + 
				"piwik_log_link_visit_action.server_time time " +  
				"FROM " + 
				"`piwik_log_visit` " + 
				"LEFT JOIN `piwik_log_link_visit_action` ON piwik_log_visit.idvisit = piwik_log_link_visit_action.idvisit " + 
				"LEFT JOIN piwik_log_action ON piwik_log_link_visit_action.idaction_url = piwik_log_action.idaction " + 
				"WHERE " + 
				"piwik_log_visit.visit_last_action_time >= ? " + 
				"AND piwik_log_visit.visit_last_action_time < ? " + 
				"AND piwik_log_visit.custom_var_k1 = 'email' " + 
				"AND piwik_log_action. NAME LIKE '%item%' " + 
				"ORDER BY " + 
				"piwik_log_visit.visit_last_action_time DESC");
			ps.setString(1, begainTime);
			ps.setString(2, endTime);
			ResultSet rs = ps.executeQuery();
			//处理返回结果
			while(rs.next()){
				r = new ActionCSV();
				r.setEmail(rs.getString("email"));
				r.setItemID(rs.getString("itemid"));
				r.setTimesStamp(Util.formateTime(new Date(rs.getTimestamp("time").getTime())));
				r.setAction(1);
				list.add(r);
			}
			log.debug("读取ViewAction数据：开始时间：" + begainTime + "结束时间：" + endTime + "当前list大小：" + list.size());
			dbm.freeConnection(Util.getProperty("piwikwebserver"), conn);
			ps.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			//容错处理，记录下来并将未用完的connection关闭掉
			log.error(Util.getTrace(e));
			if(dbm != null && conn != null){
				dbm.freeConnection(Util.getProperty("piwikwebserver"), conn);
			}
		}
		return list;
	}
	/**
	 * 得到所有的用户购买行为数据
	 * 
	 * @return	所有的用户购买行为数据
	 */
	public List<ActionCSV> getAllPurchasedActionCSVs(String begainTime, String endTime) {
		List<ActionCSV> list = new ArrayList<ActionCSV>();
		DBConnectionManager dbm = null;
		Connection conn = null;
		ActionCSV r = null;
		try {
			dbm = DBConnectionManager.getInstance();
			conn = dbm.getConnection(Util.getProperty("biwebserver"));
			PreparedStatement ps = conn.prepareStatement("select " +
					"oo.email email, oop.product_id itemid, oo.date_added time from " +
					"oc_order_product oop " +
					"left join oc_order oo on oop.order_id = oo.order_id " +
					"where oo.date_added >= ? " +
					"and oo.date_added < ?");
			ps.setString(1, begainTime);
			ps.setString(2, endTime);
			ResultSet rs = ps.executeQuery();
			//处理返回结果
			while(rs.next()){
				r = new ActionCSV();
				r.setEmail(rs.getString("email"));
				r.setItemID(rs.getString("itemid"));
				r.setTimesStamp(Util.formateTime(new Date(rs.getTimestamp("time").getTime())));
				r.setAction(2);
				list.add(r);
			}
			log.debug("读取PurchasedAction数据：开始时间：" + begainTime + "结束时间：" + endTime + "当前list大小：" + list.size());
			dbm.freeConnection(Util.getProperty("biwebserver"), conn);
			ps.close();
			rs.close();
		} catch (Exception e) {
			log.error(Util.getTrace(e));
			//容错处理
			if(dbm != null && conn != null){
				dbm.freeConnection(Util.getProperty("biwebserver"), conn);
			}
		}
		return list;
	}
	/**
	 * 得到所有的用户收藏行为数据
	 * 
	 * @return	所有的用户收藏行为数据
	 */
	public List<ActionCSV> getAllCollectActionCSVs(String begainTime, String endTime) {
		List<ActionCSV> list = new ArrayList<ActionCSV>();
		DBConnectionManager dbm = null;
		Connection conn = null;
		ActionCSV r = null;
		try {
			dbm = DBConnectionManager.getInstance();
			conn = dbm.getConnection(Util.getProperty("localserver"));
			PreparedStatement ps = conn.prepareStatement("select email, item_id itemid, start_time time from favourite_action where start_time >= ? and start_time < ?");//mysql使用
			ps.setString(1, begainTime);
			ps.setString(2, endTime);
			ResultSet rs = ps.executeQuery();
			//处理结果
			while(rs.next()){
				r = new ActionCSV();
				r.setEmail(rs.getString("email"));
				r.setItemID(rs.getString("itemid"));
				r.setTimesStamp(Util.formateTime(new Date(rs.getTimestamp("time").getTime())));
				r.setAction(3);
				list.add(r);
			}
			log.debug("读取CollectionAction数据：开始时间：" + begainTime + "结束时间：" + endTime + "当前list大小：" + list.size());
			dbm.freeConnection(Util.getProperty("localserver"), conn);
			ps.close();
			rs.close();
		} catch (Exception e) {
			log.error(Util.getTrace(e));
			//容错处理
			if(dbm != null && conn != null){
				dbm.freeConnection(Util.getProperty("localserver"), conn);
			}
		}
		return list;
	}
	/**
	 * 得到所有的用户添加购物车行为数据
	 * 
	 * @return	所有的用户添加购物车行为数据
	 */
	public List<ActionCSV> getAllShoppingCartActionCSVs(String begainTime, String endTime) {
		List<ActionCSV> list = new ArrayList<ActionCSV>();
		DBConnectionManager dbm = null;
		Connection conn = null;
		ActionCSV r = null;
		try {
			dbm = DBConnectionManager.getInstance();
			conn = dbm.getConnection(Util.getProperty("localserver"));
			PreparedStatement ps = conn.prepareStatement("select email, item_id itemid, start_time time from shopping_cart_action where start_time >= ? and start_time < ?");//mysql使用
			ps.setString(1, begainTime);
			ps.setString(2, endTime);
			ResultSet rs = ps.executeQuery();
			//处理返回结果
			while(rs.next()){
				r = new ActionCSV();
				r.setEmail(rs.getString("email"));
				r.setItemID(rs.getString("itemid"));
				r.setTimesStamp(Util.formateTime(new Date(rs.getTimestamp("time").getTime())));
				r.setAction(4);
				list.add(r);
			}
			log.debug("读取shoppingcartAction数据：开始时间：" + begainTime + "结束时间：" + endTime + "当前list大小：" + list.size());
			dbm.freeConnection(Util.getProperty("localserver"), conn);
			ps.close();
			rs.close();
		} catch (Exception e) {
			log.error(Util.getTrace(e));
			//容错处理
			if(dbm != null && conn != null){
				dbm.freeConnection(Util.getProperty("localserver"), conn);
			} // end if(dbm != null && conn != null)
		} // end try
		return list;
	} // end public List<ActionCSV> getAllShoppingCartActionCSVs(String begainTime, String endTime) 
}
