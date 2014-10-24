package com.fieldschina.edm.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

import org.apache.log4j.Logger;

import com.fieldschina.edm.dao.DMDReturnFileDao;
import com.fieldschina.edm.dbconn.DBConnectionManager;
import com.fieldschina.edm.entity.DMDReturnFile;
import com.fieldschina.edm.entity.Recipient;
import com.fieldschina.edm.util.Util;
/**
 * DMD回传文件数据库操作类
 * 
 * @author  LAVEN  E-mail:lavenwl@gmail.com
 * @company FieldsChina.IT.BI
 * @version 创建时间：2014-7-23 下午4:06:20
 */
public class DMDReturnFileDaoImpl implements DMDReturnFileDao{
	Logger log = Logger.getLogger(DMDReturnFileDaoImpl.class);//日志记录
	/**
	 * 插入一条数据到本地数据库
	 * @param dMDReturnFile
	 * @return 是否插入成功
	 */
	public boolean insertDMAReturnFile(DMDReturnFile dMDReturnFile) {
		boolean b = false;
		DBConnectionManager dbm = null;
		Connection conn = null;
		Recipient r = null;
		try {
			dbm = DBConnectionManager.getInstance();
			conn = dbm.getConnection(Util.getProperty("localODSserver"));
			PreparedStatement ps = conn.prepareStatement("insert into ods_dma_returns (DMDCAMPAIGNNAME, DMDMAILINGNAME, DMDTYPE, EMAIL, ID,CREATE_DATE,DMDLOGDATE,DMDCLICKNAME,DMDCLICKURL,DMDBOUNCEMESSAGE,DMDIPADDRESS,DMDCLIENT) values (?,?,?,?,?,?,?,?,?,?,?,?)");
			ps.setString(1, dMDReturnFile.getdMDCampaignName());
			ps.setString(2, dMDReturnFile.getdMDMailingName());
			ps.setString(3, dMDReturnFile.getdMDType());
			ps.setString(4, dMDReturnFile.getEmail());
			ps.setInt(5, dMDReturnFile.getId());
			ps.setDate(6, (Date) Util.paraseStringToTime(Util.changeTimeFormat(dMDReturnFile.getCreateDate())));
			ps.setDate(7, (Date) Util.paraseStringToTime(Util.changeTimeFormat(dMDReturnFile.getdMDLogDate())));
			ps.setString(8, dMDReturnFile.getdMDClickName());
			ps.setString(9, dMDReturnFile.getdMDClickUrl());
			ps.setString(10, dMDReturnFile.getdMDBounceMessage());
			ps.setString(11, dMDReturnFile.getdMDipAddress());
			ps.setString(12, dMDReturnFile.getdMDClient());
			int rs = ps.executeUpdate();
			b = true;
			dbm.freeConnection(Util.getProperty("localODSserver"), conn);
			ps.close();
		} catch (Exception e) {
			log.error(Util.getTrace(e));
			//程序的容错处理
			if(dbm != null && conn != null){
				dbm.freeConnection(Util.getProperty("localODSserver"), conn);
			}
		}
		return b;
	}

}
