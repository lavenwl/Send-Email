package com.fieldschina.edm.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.fieldschina.edm.config.Config;
import com.fieldschina.edm.dao.RecipientDao;
import com.fieldschina.edm.dbconn.DBConnectionManager;
import com.fieldschina.edm.entity.Recipient;
import com.fieldschina.edm.util.Util;
/**
 * 接收者（DMD平台用户）数据库操作类
 * 
 * @author  LAVEN  E-mail:lavenwl@gmail.com
 * @company FieldsChina.IT.BI
 * @version 创建时间：2014-7-23 下午4:05:45
 */
public class RecipientDaoImpl implements RecipientDao{
	Logger log = Logger.getLogger(RecipientDaoImpl.class);// 日志记录
	/**
	 * 读取web数据库中每日新增的用户的数据（目前不会用到这个方法）
	 * 
	 * @return	每日新增的数据
	 */
	public List<Recipient> findDayNewRecipients() {
		List<Recipient> list = new ArrayList<Recipient>();
		DBConnectionManager dbm = null;
		Connection conn = null;
		Recipient r = null;
		try {
			dbm = DBConnectionManager.getInstance();
			conn = dbm.getConnection(Util.getProperty("webserver"));
			PreparedStatement ps = conn.prepareStatement("select customer_id, first_name, last_name, email, nationality1, nationality2, city, " +
					"day_of_birthday, month_of_birthday, year_of_birthday, membership_level, gender, registered_date, first_order_date, vip, " +
					"last_order_date, active_status, app_service, order_frequency_30days, order_frequency_60days, order_frequency_90days," +
					"family_status, current_month_order_amount, total_order_amount, current_month_order_number, total_order_number from t_recipient_edm where registered_date > ?");
			ps.setString(1, Util.getYesterday());
			ResultSet rs = ps.executeQuery();
			//处理返回结果
			while(rs.next()){
				r = new Recipient();
				r.setWebId(rs.getInt("customer_id"));
				r.setFirstName(rs.getString("first_name"));
				r.setLastName(rs.getString("last_name"));
				r.setEmail(rs.getString("email"));
				r.setNationality1(rs.getString("nationality1"));
				r.setNationality2(rs.getString("nationality2"));
				r.setCity(rs.getString("city"));
				r.setBirthdayDay(rs.getInt("day_of_birthday"));
				r.setBirthdayMonth(rs.getInt("month_of_birthday"));
				r.setBirthdayYear(rs.getInt("year_of_birthday"));
				r.setMembershipLevel(rs.getInt("membership_level"));
				r.setVip(rs.getString("vip"));
				r.setSex(rs.getInt("gender"));
				r.setRegisteredDate(Util.formateTime(new Date(rs.getTimestamp("registered_date").getTime())));
				r.setFirstOrderDate(Util.formateTime(new Date(rs.getTimestamp("first_order_date").getTime())));
				r.setLastOrderDate(Util.formateTime(new Date(rs.getTimestamp("last_order_date").getTime())));
				r.setActiveStatus(String.valueOf(rs.getInt("active_status")));
				r.setAppService(rs.getString("app_service"));
				r.setOrder30Days(rs.getString("order_frequency_30days"));
				r.setOrder60Days(rs.getString("order_frequency_60days"));
				r.setOrder90Days(rs.getString("order_frequency_90days"));
				r.setFamilyStatus(rs.getString("family_status"));
				r.setCurrentMonthOrderAmount(rs.getInt("current_month_order_amount"));
				r.setTotalOrderAmount(rs.getInt("total_order_amount"));
				r.setCurrentMonthOrderNumber(rs.getInt("current_month_order_number"));
				r.setTotalOrderNumber(rs.getInt("total_order_number"));
				list.add(r);
			}
			dbm.freeConnection(Util.getProperty("webserver"), conn);
			ps.close();
			rs.close();
		} catch (Exception e) {
			log.error(Util.getTrace(e));
			//程序的容错处理
			if(dbm != null && conn != null){
				dbm.freeConnection(Util.getProperty("webserver"), conn);
			}
		}
		return list;
	}
	/**
	 * 读取web数据库中全部的用户的数据（分月份分批读取用户数据一次性返回）
	 * 
	 * @return	全部的用户数据
	 */
	public List<Recipient> findAllRecipients() {
		String begainTime = "2010-05-01";
		String month = Util.getMonth();
		String endTime = Util.getNextMonth(begainTime);
		//返回结果的集合，分批次的读取添加，最后一次返回
		List<Recipient> list = new ArrayList<Recipient>();
		DBConnectionManager dbm = null;
		Connection conn = null;
		Recipient r = null;
		try {
			dbm = DBConnectionManager.getInstance();
			conn = dbm.getConnection(Util.getProperty("webserver"));
			while(Util.compareDateString(begainTime, month) < 0){//比较即将读取的月份的信息是否是当月的信息， 如果是当月的信息，那么则使用循环外面的最后的一次读取（读取到月初到当前日期的数据）
				PreparedStatement ps = conn.prepareStatement("select customer_id, first_name, last_name, email, nationality1, nationality2, city, " +
						"day_of_birthday, month_of_birthday, year_of_birthday, membership_level, gender, registered_date, first_order_date, vip, " +
						"last_order_date, active_status, app_service, order_frequency_30days, order_frequency_60days, order_frequency_90days," +
						"family_status, current_month_order_amount, total_order_amount, current_month_order_number, total_order_number from t_recipient_edm " +
						"where registered_date >= ? " +
						"and registered_date < ?");
				ps.setString(1, begainTime);
				ps.setString(2, endTime);
				ResultSet rs = ps.executeQuery();
				//处理返回结果
				while(rs.next()){
					r = new Recipient();
					r.setWebId(rs.getInt("customer_id"));
					r.setFirstName(rs.getString("first_name"));
					r.setLastName(rs.getString("last_name"));
					r.setEmail(rs.getString("email"));
					r.setNationality1(rs.getString("nationality1"));
					r.setNationality2(rs.getString("nationality2"));
					r.setCity(rs.getString("city"));
					r.setBirthdayDay(rs.getInt("day_of_birthday"));
					r.setBirthdayMonth(rs.getInt("month_of_birthday"));
					r.setBirthdayYear(rs.getInt("year_of_birthday"));
					r.setMembershipLevel(rs.getInt("membership_level"));
					r.setVip(rs.getString("vip"));
					r.setSex(rs.getInt("gender"));
					r.setRegisteredDate(rs.getTimestamp("registered_date") == null ? "1900-01-01 12:00:00" : Util.formateTime(new Date(rs.getTimestamp("registered_date").getTime())));
					r.setFirstOrderDate(rs.getTimestamp("first_order_date") == null ? "1900-01-01 12:00:00" : Util.formateTime(new Date(rs.getTimestamp("first_order_date").getTime())));
					r.setLastOrderDate(rs.getTimestamp("last_order_date") == null ? "1900-01-01 12:00:00" : Util.formateTime(new Date(rs.getTimestamp("last_order_date").getTime())));
					r.setActiveStatus(String.valueOf(rs.getInt("active_status")));
					r.setAppService(rs.getString("app_service"));
					r.setOrder30Days(rs.getString("order_frequency_30days"));
					r.setOrder60Days(rs.getString("order_frequency_60days"));
					r.setOrder90Days(rs.getString("order_frequency_90days"));
					r.setFamilyStatus(rs.getString("family_status"));
					r.setCurrentMonthOrderAmount(rs.getInt("current_month_order_amount"));
					r.setTotalOrderAmount(rs.getInt("total_order_amount"));
					r.setCurrentMonthOrderNumber(rs.getInt("current_month_order_number"));
					r.setTotalOrderNumber(rs.getInt("total_order_number"));
					list.add(r);
				} // end while(rs.next()){//结果处理
				log.debug("读取recipients数据：开始时间：" + begainTime + "结束时间：" + endTime + "当前list大小：" + list.size());
				begainTime = endTime;
				endTime = Util.getNextMonth(begainTime);
			} // end while(Util.compareDateString(begainTime, month) < 0)
			endTime = Util.getDay();
			PreparedStatement ps = conn.prepareStatement("select customer_id, first_name, last_name, email, nationality1, nationality2, city, " +
					"day_of_birthday, month_of_birthday, year_of_birthday, membership_level, gender, registered_date, first_order_date, vip, " +
					"last_order_date, active_status, app_service, order_frequency_30days, order_frequency_60days, order_frequency_90days," +
					"family_status, current_month_order_amount, total_order_amount, current_month_order_number, total_order_number from t_recipient_edm " +
					"where registered_date >= ? " +
					"and registered_date < ?");
			ps.setString(1, begainTime);
			ps.setString(2, endTime);
			ResultSet rs = ps.executeQuery();
			//处理返回结果
			while(rs.next()){
				r = new Recipient();
				r.setWebId(rs.getInt("customer_id"));
				r.setFirstName(rs.getString("first_name"));
				r.setLastName(rs.getString("last_name"));
				r.setEmail(rs.getString("email"));
				r.setNationality1(rs.getString("nationality1"));
				r.setNationality2(rs.getString("nationality2"));
				r.setCity(rs.getString("city"));
				r.setBirthdayDay(rs.getInt("day_of_birthday"));
				r.setBirthdayMonth(rs.getInt("month_of_birthday"));
				r.setBirthdayYear(rs.getInt("year_of_birthday"));
				r.setMembershipLevel(rs.getInt("membership_level"));
				r.setVip(rs.getString("vip"));
				r.setSex(rs.getInt("gender"));
				r.setRegisteredDate(rs.getTimestamp("registered_date") == null ? "1900-01-01 12:00:00" : Util.formateTime(new Date(rs.getTimestamp("registered_date").getTime())));
				r.setFirstOrderDate(rs.getTimestamp("first_order_date") == null ? "1900-01-01 12:00:00" : Util.formateTime(new Date(rs.getTimestamp("first_order_date").getTime())));
				r.setLastOrderDate(rs.getTimestamp("last_order_date") == null ? "1900-01-01 12:00:00" : Util.formateTime(new Date(rs.getTimestamp("last_order_date").getTime())));
				r.setActiveStatus(String.valueOf(rs.getInt("active_status")));
				r.setAppService(rs.getString("app_service"));
				r.setOrder30Days(rs.getString("order_frequency_30days"));
				r.setOrder60Days(rs.getString("order_frequency_60days"));
				r.setOrder90Days(rs.getString("order_frequency_90days"));
				r.setFamilyStatus(rs.getString("family_status"));
				r.setCurrentMonthOrderAmount(rs.getInt("current_month_order_amount"));
				r.setTotalOrderAmount(rs.getInt("total_order_amount"));
				r.setCurrentMonthOrderNumber(rs.getInt("current_month_order_number"));
				r.setTotalOrderNumber(rs.getInt("total_order_number"));
				list.add(r);
			}
			log.debug("最后一次读取recipients数据：开始时间：" + begainTime + "结束时间：" + endTime + "当前list大小：" + list.size());
			dbm.freeConnection(Util.getProperty("webserver"), conn);
			ps.close();
			rs.close();
		} catch (Exception e) {
			log.error(Util.getTrace(e));
			//程序的容错处理
			if(dbm != null && conn != null){
				dbm.freeConnection(Util.getProperty("webserver"), conn);
			}
		}
		return list;
	}
	/**
	 *  * 插入用户的数据进入本地数据库（将每一个用户的成上传情况全部记录到本地数据库，然而现在是每天全量的上传数据会造成很多的无用的数据生成，所以现在不会使用这个方法）
	 * 
	 * @param recipient	要插入的用户对象
	 * @return 			返回是否插入成功
	 */
	public boolean insertRecipient(Recipient recipient) {
		boolean b = false;
		DBConnectionManager dbm = null;
		Connection conn = null;
		Recipient r = null;
		try {
			dbm = DBConnectionManager.getInstance();
			conn = dbm.getConnection(Util.getProperty("localserver"));
			PreparedStatement ps = conn.prepareStatement("insert into recipients (web_id, wp_id, email, exception, campaign_id) values (?,?,?,?,?)");
			ps.setInt(1, recipient.getWebId());
			ps.setInt(2, recipient.getWpId());
			ps.setString(3, recipient.getEmail());
			ps.setString(4, Util.sub200string(recipient.getException()));
			ps.setInt(5, recipient.getCampaignId());
			//ps.setInt(8, recipient.isSuccess() ? 1 : 0);
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
	 * 得到创建CSV文件符合条件的用户（此方法现在已经没有用，该功能已经迁移出到control工程）
	 * 
	 * @return
	 */
	public List<Recipient> findMatchRecipient() {
		List<Recipient> list = new ArrayList<Recipient>();
		DBConnectionManager dbm = null;
		Connection conn = null;
		Recipient r = null;
		try {
			dbm = DBConnectionManager.getInstance();
			conn = dbm.getConnection(Util.getProperty("biwebserver"));
			PreparedStatement ps = conn.prepareStatement("select customer_id, email, firstname, lastname, telephone, phone, date_added from oc_customer where date_added > ? and date_added < ? and date_added < ?");
			ps.setString(1, Util.getLastMonth());
			ps.setString(2, Util.get7DaysAgo());
			ps.setString(3, Util.getMonth());
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				r = new Recipient();
				r.setWebId(rs.getInt("customer_id"));
				r.setEmail(rs.getString("email"));
				r.setFirstName(rs.getString("firstname"));
				r.setLastName(rs.getString("lastname"));
				r.setTelephone(rs.getString("telephone"));
				r.setPhone(rs.getString("phone"));
				r.setRegisteredDate(rs.getTimestamp("date_added") == null ? "1900-01-01 12:00:00" : Util.formateTime(new Date(rs.getTimestamp("date_added").getTime())));
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
	 * 得到fields内部员工的数据（email）
	 * 
	 * @return
	 */
	public List<Recipient> findFieldsEmail() {
		List<Recipient> list = new ArrayList<Recipient>();
		DBConnectionManager dbm = null;
		Connection conn = null;
		Recipient r = null;
		try {
			dbm = DBConnectionManager.getInstance();
			conn = dbm.getConnection(Util.getProperty("localserver"));
			PreparedStatement ps = conn.prepareStatement("select email from fields_email where flag = 1");
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				r = new Recipient();
				r.setEmail(rs.getString("email"));
				list.add(r);
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
	 * 得到fields内部IT员工的数据（email）
	 * 
	 * @return
	 */
	public List<Recipient> findFieldsITEmail() {
		List<Recipient> list = new ArrayList<Recipient>();
		DBConnectionManager dbm = null;
		Connection conn = null;
		Recipient r = null;
		try {
			dbm = DBConnectionManager.getInstance();
			conn = dbm.getConnection(Util.getProperty("localserver"));
			PreparedStatement ps = conn.prepareStatement("select email from fields_email where flag = 1 and department = 'IT'");
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				r = new Recipient();
				r.setEmail(rs.getString("email"));
				list.add(r);
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
	 * 得到创建CSV文件当月注册时间14天以上的用户（此方法现在已经没有用，该功能已经迁移出到control工程）
	 * 
	 * @return
	 */
	public List<Recipient> findCurrentMonthRegistrationM14Days() {
		List<Recipient> list = new ArrayList<Recipient>();
		DBConnectionManager dbm = null;
		Connection conn = null;
		Recipient r = null;
		try {
			dbm = DBConnectionManager.getInstance();
			conn = dbm.getConnection(Util.getProperty("biwebserver"));
			PreparedStatement ps = conn.prepareStatement("select customer_id, email, firstname, lastname, telephone, phone, date_added from oc_customer where date_added > ? and date_added < ?");
			ps.setString(1, Util.getMonth());
			ps.setString(2, Util.get14DaysAgo());
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				r = new Recipient();
				r.setWebId(rs.getInt("customer_id"));
				r.setEmail(rs.getString("email"));
				r.setFirstName(rs.getString("firstname"));
				r.setLastName(rs.getString("lastname"));
				r.setTelephone(rs.getString("telephone"));
				r.setPhone(rs.getString("phone"));
				r.setRegisteredDate(rs.getTimestamp("date_added") == null ? "1900-01-01 12:00:00" : Util.formateTime(new Date(rs.getTimestamp("date_added").getTime())));
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
	 * 得到创建CSV文件注册时间大于7天小于14天的用户（此方法现在已经没有用，该功能已经迁移出到control工程）
	 * 
	 * @return
	 */
	public List<Recipient> findCurrentMonthRegistrationM7L14Days() {
		List<Recipient> list = new ArrayList<Recipient>();
		DBConnectionManager dbm = null;
		Connection conn = null;
		Recipient r = null;
		try {
			dbm = DBConnectionManager.getInstance();
			conn = dbm.getConnection(Util.getProperty("biwebserver"));
			PreparedStatement ps = conn.prepareStatement("select customer_id, email, firstname, lastname, telephone, phone, date_added from oc_customer where date_added > ? and date_added < ? and date_added > ?");
			ps.setString(1, Util.getMonth());
			ps.setString(2, Util.get7DaysAgo());
			ps.setString(3, Util.get14DaysAgo());
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				r = new Recipient();
				r.setWebId(rs.getInt("customer_id"));
				r.setEmail(rs.getString("email"));
				r.setFirstName(rs.getString("firstname"));
				r.setLastName(rs.getString("lastname"));
				r.setTelephone(rs.getString("telephone"));
				r.setPhone(rs.getString("phone"));
				r.setRegisteredDate(rs.getTimestamp("date_added") == null ? "1900-01-01 12:00:00" : Util.formateTime(new Date(rs.getTimestamp("date_added").getTime())));
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

}
