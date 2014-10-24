package com.fieldschina.edm.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.fieldschina.edm.dao.ItemCSVDao;
import com.fieldschina.edm.dbconn.DBConnectionManager;
import com.fieldschina.edm.entity.ItemCSV;
import com.fieldschina.edm.entity.UserCSV;
import com.fieldschina.edm.util.Util;
/**
 * 商品信息数据库操作实现类
 * 
 * @author  LAVEN  E-mail:lavenwl@gmail.com
 * @company FieldsChina.IT.BI
 * @version 创建时间：2014-7-8 下午2:40:07
 */
public class ItemCSVDaoImpl implements ItemCSVDao{
	Logger log = Logger.getLogger(ItemCSVDaoImpl.class);//日志记录
	/**
	 * 得到所有的中文描述的商品信息
	 * 
	 * @return 中文描述的商品的信息的集合
	 */
	public List<ItemCSV> getAllCNItemCSVs() {
		List<ItemCSV> list = new ArrayList<ItemCSV>();
		DBConnectionManager dbm = null;
		Connection conn = null;
		ItemCSV r = null;
		try {
			dbm = DBConnectionManager.getInstance();
			conn = dbm.getConnection(Util.getProperty("biwebserver"));
			PreparedStatement ps = conn.prepareStatement("select table1.product_id, name_c, url, url2, price, price2, table1.description, type, ocd.name brand from (select op.product_id, opd.name name_c, CONCAT('http://www.fieldschina.com/zh/item-',op.product_id,'.html') url, " +
					"CONCAT('http://www.fieldschina.com/image/product/',op.image) url2, op.price, ops.price price2, " + 
					"opd.description description, ocd.name type " + 
					"from oc_product op left JOIN " + 
							 "(select * from oc_product_special where date_end > ? or date_end = '0000-00-00') ops on op.product_id = ops.product_id LEFT JOIN " + 
							 "oc_product_description opd on op.product_id = opd.product_id LEFT JOIN " + 
							 "oc_product_to_category optc on op.product_id=optc.product_id LEFT JOIN " + 
							 "oc_category_description ocd on optc.category_id=ocd.category_id where status = 1 and opd.language_id = 2 and optc.is_primary = 1 and ocd.language_id = 2) as table1 LEFT JOIN " + 
"oc_product_to_category optc2 on table1.product_id = optc2.product_id LEFT JOIN " + 
"oc_category_description ocd on optc2.category_id=ocd.category_id INNER JOIN oc_front_menu ofm on optc2.category_id = ofm.cid where ocd.language_id = 2 GROUP BY product_id ORDER BY optc2.is_primary desc");
			ps.setString(1, Util.getDay());
			ResultSet rs = ps.executeQuery();
			//处理返回结果
			while(rs.next()){
				r = new ItemCSV();
				r.setItemID(rs.getInt("product_id"));
				r.setTitle(rs.getString("name_c"));
				r.setUrl(rs.getString("url"));
				r.setImageUrl(rs.getString("url2"));
				r.setPrice1(rs.getInt("price"));
				r.setPrice2(rs.getInt("price2") == 0 ? rs.getInt("price") : rs.getInt("price2"));
				r.setDesc1(Util.deleteHTML(rs.getString("description")));
				r.setType(rs.getString("type"));
				r.setBrand(rs.getString("brand"));
				r.setStatus(1);
				r.setStock(50000);
				list.add(r);
			}
			dbm.freeConnection(Util.getProperty("biwebserver"), conn);
			ps.close();
			rs.close();
		} catch (Exception e) {
			log.error(Util.getTrace(e));
			//程序的容错处理
			if(dbm != null && conn != null){
				dbm.freeConnection(Util.getProperty("biwebserver"), conn);
			}
		}
		return list;
	}
	/**
	 * 得到所有的英文描述的商品信息
	 * 
	 * @return	英文描述的商品信息的集合
	 */
	public List<ItemCSV> getAllENItemCSVs() {
		List<ItemCSV> list = new ArrayList<ItemCSV>();
		DBConnectionManager dbm = null;
		Connection conn = null;
		ItemCSV r = null;
		try {
			dbm = DBConnectionManager.getInstance();
			conn = dbm.getConnection(Util.getProperty("biwebserver"));
			PreparedStatement ps = conn.prepareStatement("select table1.product_id, name_c, url, url2, price, price2, table1.description, type, ocd.name brand from (select op.product_id, opd.name name_c, CONCAT('http://www.fieldschina.com/en/item-',op.product_id,'.html') url, " +
					"CONCAT('http://www.fieldschina.com/image/product/',op.image) url2, op.price, ops.price price2, " + 
					"opd.description description, ocd.name type " + 
					"from oc_product op left JOIN " + 
							 "(select * from oc_product_special where date_end > ? or date_end = '0000-00-00') ops on op.product_id = ops.product_id LEFT JOIN " + 
							 "oc_product_description opd on op.product_id = opd.product_id LEFT JOIN " + 
							 "oc_product_to_category optc on op.product_id=optc.product_id LEFT JOIN " + 
							 "oc_category_description ocd on optc.category_id=ocd.category_id where status = 1 and opd.language_id = 1 and optc.is_primary = 1 and ocd.language_id = 1) as table1 LEFT JOIN " + 
"oc_product_to_category optc2 on table1.product_id = optc2.product_id LEFT JOIN " + 
"oc_category_description ocd on optc2.category_id=ocd.category_id INNER JOIN oc_front_menu ofm on optc2.category_id = ofm.cid where ocd.language_id = 1 GROUP BY product_id ORDER BY optc2.is_primary desc");
			ps.setString(1, Util.getDay());
			ResultSet rs = ps.executeQuery();
			//处理返回结果
			while(rs.next()){
				r = new ItemCSV();
				r.setItemID(rs.getInt("product_id"));
				r.setTitle(rs.getString("name_c"));
				r.setUrl(rs.getString("url"));
				r.setImageUrl(rs.getString("url2"));
				r.setPrice1(rs.getInt("price"));
				r.setPrice2(rs.getInt("price2"));
				r.setDesc1(Util.deleteHTML(rs.getString("description")));
				r.setType(rs.getString("type"));
				r.setBrand(rs.getString("brand"));
				r.setStatus(1);
				r.setStock(50000);
				list.add(r);
			}
			dbm.freeConnection(Util.getProperty("biwebserver"), conn);
			ps.close();
			rs.close();
		} catch (Exception e) {
			log.error(Util.getTrace(e));
			//程序的容错处理
			if(dbm != null && conn != null){
				dbm.freeConnection(Util.getProperty("biwebserver"), conn);
			}
		}
		return list;
	}
	/**
	 * 得到想要字段信息组成的产品集合
	 * 
	 * @return 想要字段信息组成的产品集合
	 */
	public Map<Integer, Integer> getAllProduct() {
		Map<Integer, Integer> resultMap = new HashMap<Integer, Integer>();
		DBConnectionManager dbm = null;
		Connection conn = null;
		ItemCSV r = null;
		try {
			dbm = DBConnectionManager.getInstance();
			conn = dbm.getConnection(Util.getProperty("biwebserver"));
			PreparedStatement ps = conn.prepareStatement("select product_id, product_status from oc_product where status = 1");
			ResultSet rs = ps.executeQuery();
			//处理返回结果
			while(rs.next()){
				resultMap.put(rs.getInt("product_id"), rs.getInt("product_status"));
			}
			dbm.freeConnection(Util.getProperty("biwebserver"), conn);
			ps.close();
			rs.close();
		} catch (Exception e) {
			log.error(Util.getTrace(e));
			//程序的容错处理
			if(dbm != null && conn != null){
				dbm.freeConnection(Util.getProperty("biwebserver"), conn);
			}
		}
		return resultMap;
	}
	public String getProList() {
		String res = "";
		DBConnectionManager dbm = null;
		Connection conn = null;
		ItemCSV r = null;
		try {
			dbm = DBConnectionManager.getInstance();
			conn = dbm.getConnection(Util.getProperty("biwebserver"));
			PreparedStatement ps = conn.prepareStatement("select keywords from oc_search_keywords where language_id = 2");
			ResultSet rs = ps.executeQuery();
			//处理返回结果
			while(rs.next()){
				res = rs.getString("keywords");
			}
			dbm.freeConnection(Util.getProperty("biwebserver"), conn);
			ps.close();
			rs.close();
		} catch (Exception e) {
			log.error(Util.getTrace(e));
			//程序的容错处理
			if(dbm != null && conn != null){
				dbm.freeConnection(Util.getProperty("biwebserver"), conn);
			}
		}
		return res;
	}
}
