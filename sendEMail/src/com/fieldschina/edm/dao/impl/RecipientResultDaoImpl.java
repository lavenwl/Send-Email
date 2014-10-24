package com.fieldschina.edm.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import org.apache.log4j.Logger;
import com.fieldschina.edm.dao.RecipientResultDao;
import com.fieldschina.edm.dbconn.DBConnectionManager;
import com.fieldschina.edm.entity.Recipient;
import com.fieldschina.edm.entity.RecipientResult;
import com.fieldschina.edm.util.Util;
/**
 * 增加记录记录访问数据库实现类
 * 
 * @author  LAVEN  E-mail:lavenwl@gmail.com
 * @company FieldsChina.IT.BI
 * @version 创建时间：2014-6-25 下午3:40:49
 */
public class RecipientResultDaoImpl implements RecipientResultDao{
	Logger log = Logger.getLogger(RecipientResultDaoImpl.class);//日志记录
	/**
	 * 添加用户的log记录
	 * 
	 * @return
	 */
	public boolean addRecipientResult(RecipientResult recipientResult) {
		boolean b = false;
		DBConnectionManager dbm = null;
		Connection conn = null;
		Recipient r = null;
		try {
			dbm = DBConnectionManager.getInstance();
			conn = dbm.getConnection(Util.getProperty("localserver"));
			PreparedStatement ps = conn.prepareStatement("insert into add_recipients_result (sum, error, success, dumplicate, campaign_id) values (?,?,?,?,?)");
			ps.setInt(1, recipientResult.getSum());
			ps.setInt(2, recipientResult.getError());
			ps.setInt(3, recipientResult.getSuccess());
			ps.setInt(4, recipientResult.getDumplicate());
			ps.setInt(5, recipientResult.getCampaignId());
			int rs = ps.executeUpdate();
			b = true;
			dbm.freeConnection(Util.getProperty("localserver"), conn);
			ps.close();
		} catch (Exception e) {
			log.error(Util.getTrace(e));
			//程序容错
			if(dbm != null && conn != null){
				dbm.freeConnection(Util.getProperty("localserver"), conn);
			}
		}
		return b;
	
	}

}
