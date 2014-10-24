package com.fieldschina.edm.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.fieldschina.edm.dao.CampaignDao;
import com.fieldschina.edm.dbconn.DBConnectionManager;
import com.fieldschina.edm.entity.Campaign;
import com.fieldschina.edm.entity.Recipient;
import com.fieldschina.edm.util.Util;
/**
 * DMD平台活动数据库操作类
 * 
 * @author  LAVEN  E-mail:lavenwl@gmail.com
 * @company FieldsChina.IT.BI
 * @version 创建时间：2014-7-23 下午4:06:40
 */
public class CampaignDaoImpl implements CampaignDao{
	Logger log = Logger.getLogger(CampaignDaoImpl.class);//日志记录
	/**
	 * 得到所有的活动信息
	 * 
	 * @return	查询到的所有的活动
	 */
	public List<Campaign> getCampaigns() {
		List<Campaign> list = new ArrayList<Campaign>();
		DBConnectionManager dbm = null;
		Connection conn = null;
		Campaign c = null;
		try {
			dbm = DBConnectionManager.getInstance();
			conn = dbm.getConnection(Util.getProperty("localserver"));
			PreparedStatement ps = conn.prepareStatement("select wp_id, name, group_ids from campaigns");
			ResultSet rs = ps.executeQuery();
			//处理返回结果
			while(rs.next()){
				c = new Campaign();
				c.setWpId(rs.getInt("wp_id"));
				c.setName(rs.getString("name"));
				c.setGroupIds(rs.getString("group_ids"));
				list.add(c);
			}
			dbm.freeConnection(Util.getProperty("localserver"), conn);
			ps.close();
			rs.close();
		} catch (Exception e) {
			log.error(Util.getTrace(e));
			//程序的容错处理
			if(dbm != null && conn != null){
				dbm.freeConnection(Util.getProperty("localserver"), conn);
			}
		}
		return list;
	}
	/**
	 * 插入一条新的活动(测试使用，准备添加自动更新活动信息功能使用)
	 * 
	 * @param campaign
	 * @return
	 */
	public boolean insertCampaign(Campaign campaign) {
		boolean b = false;
		DBConnectionManager dbm = null;
		Connection conn = null;
		Recipient r = null;
		try {
			dbm = DBConnectionManager.getInstance();
			conn = dbm.getConnection(Util.getProperty("localserver"));
			PreparedStatement ps = conn.prepareStatement("insert into campaigns (wp_id, name) values (?,?)");
			ps.setInt(1, campaign.getWpId());
			ps.setString(2, campaign.getName());
			int rs = ps.executeUpdate();
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
	 * 得到一个活动ID下的组ID
	 * 
	 * @param id
	 * @return	返回当前活动的basic组的ID
	 */
	public List<Integer> getGroupByCampId(int id) {
		String groupIds = null;
		DBConnectionManager dbm = null;
		Connection conn = null;
		Campaign c = null;
		try {
			dbm = DBConnectionManager.getInstance();
			conn = dbm.getConnection(Util.getProperty("localserver"));
			PreparedStatement ps = conn.prepareStatement("select group_ids from campaigns where wp_id = ?");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			//处理返回结果
			while(rs.next()){
				groupIds = rs.getString("group_ids");
				log.debug("得到的活动：" + id + "的groupids:" + groupIds);
			}
			dbm.freeConnection(Util.getProperty("localserver"), conn);
			ps.close();
			rs.close();
		} catch (Exception e) {
			log.error(Util.getTrace(e));
			//程序的容错处理
			if(dbm != null && conn != null){
				dbm.freeConnection(Util.getProperty("localserver"), conn);
			}
		}
		if(groupIds != null){
			return Util.paraseStringToInt(groupIds);
		}else{
			log.error("没有得到活动：" + id + "的组ID");
		}
		return null;
	}

}
