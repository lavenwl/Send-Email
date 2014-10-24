package com.fieldschina.edm.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import com.fieldschina.edm.config.Config;
import com.fieldschina.edm.entity.Recipient;
import com.fieldschina.edm.entity.DMDReturnFile;
/**
 * 工具类
 * 
 * @author  LAVEN  E-mail:lavenwl@gmail.com
 * @company FieldsChina.IT.BI
 * @version 创建时间：2014-6-19 下午1:26:43
 */
public class Util {
	private static Logger log = Logger.getLogger(Util.class);
	//计时器开始时间记录集合
	private static Map<String, Long> begin = new HashMap<String, Long>();
	/**
	 * 测试使用主方法
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		getBegainTimeFromFolder();
//		getFilesList();
//		System.out.println(getDay());
//		System.out.println(getYesterday());
//		System.out.println(getTime());
//		String test = "teststring";
//		System.out.println(test.substring(0, 4));
	}
	/**
	 * 得到当前的时间
	 * 
	 * @return	当前时间
	 */
	public static String getTime(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date());
	}
	/**
	 * 得到今天的日期
	 * 
	 * @return	当天的日期
	 */
	public static String getDay(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(new Date());
	}
	/**
	 * 得到今天的日期和时间
	 * 
	 * @return	返回今天的日期和时间
	 */
	public static String getDayTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date());
	}
	/**
	 * 得到昨天的日期
	 * 
	 * @return	昨天的日期
	 */
	public static String getYesterday(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(new Date(new Date().getTime()-24*60*60*1000));
	}
	/**
	 * 得到当前月份
	 * 
	 * @return	当前月份
	 */
	public static String getMonth(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-01");
		return sdf.format(new Date());
	}
	/**
	 * 得到上个月份
	 * 
	 * @return	上个月份
	 */
	public static String getLastMonth(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-01");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -1);
		return sdf.format(c.getTime());
	}
	/**
	 * 更具传入的时间返回上一个月
	 * 
	 * @param begainTime	传入的月份
	 * @return				上一个月份
	 */
	public static String getLastMonth(String begainTime){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-01");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(sdf2.parse(begainTime));
		} catch (ParseException e) {
			log.error(Util.getTrace(e));
		}
		c.add(Calendar.MONTH, -1);
		return sdf.format(c.getTime());
	}
	/**
	 * 更具传入的时间返回下一个月
	 * 
	 * @param begainTime	传入的月份
	 * @return				下一个月份
	 */
	public static String getNextMonth(String begainTime){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-01");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(sdf2.parse(begainTime));
		} catch (ParseException e) {
			log.error(Util.getTrace(e));
		}
		c.add(Calendar.MONTH, 1);
		return sdf.format(c.getTime());
	}
	/**
	 * 更具传入的时间返回下一个月
	 * 
	 * @param begainTime	传入的月份
	 * @return				下一个月份
	 */
	public static int compareDateString(String time1, String time2){
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		Calendar t1 = Calendar.getInstance();
		Calendar t2 = Calendar.getInstance();
		try {
			t1.setTime(sdf2.parse(time1));
			t2.setTime(sdf2.parse(time2));
		} catch (ParseException e) {
			log.error(Util.getTrace(e));
		}
		int t = t1.compareTo(t2);
		return t;
	}
	/**
	 * 得到七天前日期
	 * 
	 * @return	昨天的日期
	 */
	public static String get7DaysAgo(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -1);
		return sdf.format(c.getTime());
	}
	/**
	 * 得到十四天前日期
	 * 
	 * @return
	 */
	public static String get14DaysAgo() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -2);
		return sdf.format(c.getTime());
	}
	/**
	 * 控制字符串的长度在200一下，保证存入数据库的数据长度小于字段长度
	 * 
	 * @param string	传入字符串
	 * @return			字符串
	 */
	public static String sub200string(String string){
		return string.length() > 200 ? string.substring(0, 200) : string;
		
	}
	/**
	 * 计时器开始
	 * 
	 * @param name	计时器名称
	 */
	public static void  begin(String name){
		begin.put(name, System.currentTimeMillis());
	}
	/**
	 * 计时器结束
	 * 
	 * @param name
	 * @return
	 */
	public static long end(String name){
		return System.currentTimeMillis() - begin.get(name);
	}
	/**
	 * 得到配置文件中的参数
	 * 
	 * @param property
	 * @return
	 */
	public static String getProperty(String property){
		return Config.getInstance().getProperty(property);
	}
	/**
	 * 得到事件类型的配置文件中的参数
	 * 
	 * @param property
	 * @return
	 */
	public static long getTimeProperty(String property){
		return Long.valueOf(Config.getInstance().getProperty(property))*60*1000;
		
	}
	/**
	 * 累加数据上传结果反馈
	 * 
	 * @param resultMap
	 * @param map
	 * @return
	 */
	public static Map mapAddMap(Map resultMap, Map map) {
		int success = Integer.valueOf(String.valueOf(resultMap.get("success") == null ? 0 : resultMap.get("success")));
		int error = Integer.valueOf(String.valueOf(resultMap.get("error") == null ? 0 : resultMap.get("error")));
		int dumplicate = Integer.valueOf(String.valueOf(resultMap.get("dumplicate") == null ? 0 : resultMap.get("dumplicate")));
		int sum = Integer.valueOf(String.valueOf(resultMap.get("sum") == null ? 0 : resultMap.get("sum")));

		int success2 = Integer.valueOf(String.valueOf(map.get("success") == null ? 0 : map.get("success")));
		int error2 = Integer.valueOf(String.valueOf(map.get("error") == null ? 0 : map.get("error")));
		int dumplicate2 = Integer.valueOf(String.valueOf(map.get("dumplicate") == null ? 0 : map.get("dumplicate")));
		int sum2 = Integer.valueOf(String.valueOf(map.get("sum") == null ? 0 : map.get("sum")));

		resultMap.put("success", success + success2);
		resultMap.put("error", error + error2);
		resultMap.put("dumplicate", dumplicate + dumplicate2);
		resultMap.put("sum", sum + sum2);
		return resultMap;
	}
	/**
	 * 设置用户的生日信息
	 * 
	 * @param r
	 * @param date
	 * @return
	 */
	public static Recipient setRecipientBirthday(Recipient r, Date date) {
		if(date != null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String sf = sdf.format(date);
			String[] ss = sf.split("-");
			r.setBirthdayYear(Integer.valueOf(ss[0]));
			r.setBirthdayMonth(Integer.valueOf(ss[1]));
			r.setBirthdayDay(Integer.valueOf(ss[2]));
		}else{
			r.setBirthdayYear(1900);
			r.setBirthdayMonth(01);
			r.setBirthdayDay(01);
		}
		return r;
	}
	/**
	 * 将date类型的数据转换成一定格式的字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String formateTime(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}
	/**
	 * 将String类型(yyyy-MM-dd HH:mm:ss)的数据转换成SQLDate
	 * 
	 * @param date
	 * @return
	 * @throws ParseException 
	 */
	public static Date paraseStringToTime(String s) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date;
		date = sdf.parse(s);
		java.sql.Date sdate = new java.sql.Date(date.getTime());
		return sdate;
	}
	/**
	 * 将一个“2,5,9,7,6”类型的数据转换成list类型的集合
	 * 
	 * @param string
	 * @return
	 */
	public static List<Integer> paraseStringToInt(String string){
		List<Integer> list = new ArrayList<Integer>();
		String[] ss = string.split(",");
		for(int i = 0; i < ss.length; i++){
			int n = Integer.valueOf(ss[i].trim());
			list.add(n);
		}
		return list;
	}
	/**
	 * 将一个int类型的变量添加到“2,3,5,4，”形式的字符串中
	 * 
	 * @param string
	 * @param o
	 * @return
	 */
	public static String addIntToString(String string, int o) {
		List<Integer> list = new ArrayList<Integer>();
		if(string == null){
			string = "";
		}else{
			String[] ss = string.split(",");
			for(int i = 0; i < ss.length; i++){
				int n = Integer.valueOf(ss[i]);
				list.add(n);
			}
		}
		list.add(o);
		String result = list.toString();
		char[] ca = result.toCharArray();
		for(int j = 0; j < ca.length; j++){
			if(ca[j] == ' '){
				ca[j] = '0';
			}
		}
		result = String.copyValueOf(ca);
		result = result.substring(1, result.length() - 1);
		return result;
	}
	/**
	 * 将字符串相加得到类似结构“***，****，**，*******”
	 * 
	 * @param email
	 * @return
	 */
	public static String addString(String receiver, String email) {
		return receiver + "," + email;
	}
	/**
	 * 得到FieldsCSV文件相关的（csv.开头的）所有配置信息
	 * 
	 * @return
	 */
	public static Map<String, String> getCSVFileProperty() {
		Map<String, String> map = new HashMap<String, String>();
		Enumeration<?> propNames = Config.getInstance().propertyNames();
		while (propNames.hasMoreElements()) {
			String name = (String) propNames.nextElement();
			if (name.startsWith("csv.")) {
				map.put(getProperty(name), getProperty(name));
			} // end if (name.endsWith(".url"))
		} // end while (propNames.hasMoreElements())
		return map;
	}
	/**
	 * 设置配置文件内的配置信息（这个功能不可用！涉及配置文件的重写，所有的注释信息会消失，并且格式会乱掉）
	 * 
	 * @param key
	 * @param value
	 */
	public static void setProperty(String key, String value) {
		Config.setProperty(key, value);
	}
	/**
	 * 将exception对象的全部错误信息转换成String类型数据，用于log记录exception的全部信息
	 * 
	 * @param t
	 * @return
	 */
	public static String getTrace(Throwable t) {
        StringWriter stringWriter= new StringWriter();
        PrintWriter writer= new PrintWriter(stringWriter);
        t.printStackTrace(writer);
        StringBuffer buffer= stringWriter.getBuffer();
        return buffer.toString();
    }
	/**
	 * 得到csv文件的名字，用于上传csv文件到FTP服务器，因为本地生成的文件与上传的文件的名称不同，此方法用于转换两个名称
	 * 
	 * @param filename
	 * @return
	 */
	public static String getCSVFileName(String filename) {
		return filename.substring(2, filename.length()).substring(0, filename.indexOf('2') - 2);
	}
	/**
	 * 得到csv文件的名字，用于上传csv文件到FTP服务器，因为本地生成的文件与上传的文件的名称不同，此方法用于转换两个名称
	 * 
	 * @param filename
	 * @return
	 */
	public static String getCSVFileName2(String filename) {
		return filename.substring(2, filename.length());
	}
	/**
	 * 过滤字符串内的所有HTML的标记及实体对象（用于生成商品文件中商品描述的过滤）
	 * 
	 * @param s
	 * @return
	 */
	public static String deleteHTML(String s){
		Pattern p ;
		Matcher m;
		String reg = "&lt;[^&gt;]+&gt;";
		String reg2 = "&lt;(.*)&gt;";
		String reg3 = "<[^>]+>";
		p = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
		m = p.matcher(s);
		s = m.replaceAll("");
		p = Pattern.compile(reg2, Pattern.CASE_INSENSITIVE);
		m = p.matcher(s);
		s = m.replaceAll("");
		p = Pattern.compile(reg3, Pattern.CASE_INSENSITIVE);
		m = p.matcher(s);
		s = m.replaceAll("");
		s = s.replace("&amp;", "&");
		s = s.replace("nbsp;", " ");
		return s;
	}
	/**
	 * 得到商品的价格区间，用于填充用户行为CSV文件的priceLevel字段
	 * 
	 * @param price
	 * @return
	 */
	public static String getPriceLevel(Integer price) {
		if(price > 0 && price <= 100)
			return "0-100";
		else if(price > 100 && price <= 200)
			return "100-200";
		else if(price > 200 && price <= 300)
			return "200-300";
		else if(price > 300 && price <= 400)
			return "300-400";
		else if(price > 400 && price <= 500)
			return "400-500";
		else if(price > 500 && price <= 800)
			return "500-800";
		else
			return "800以上";
	}
	/**
	 * 将DMD回传文件中的一行数据（String）转换成程序内部的DMD回传文件实体类型
	 * 
	 * @param s
	 * @return
	 */
	public static DMDReturnFile formateStringToDMDReturnFile(String s) {
		String[] ss = (s + "t").split(",");
		ss[ss.length - 1] = ss[ss.length - 1].substring(0, ss[ss.length - 1].length() - 1);
		DMDReturnFile dMDReturnFile = new DMDReturnFile();
		dMDReturnFile.setdMDCampaignName(ss[0]);
		dMDReturnFile.setdMDMailingName(ss[1]);
		dMDReturnFile.setdMDType(ss[2]);
		dMDReturnFile.setEmail(ss[3]);
		dMDReturnFile.setId(ss[4].length() > 0 ? Integer.valueOf(ss[4]) : 0);
		dMDReturnFile.setCreateDate(ss[5]);
		dMDReturnFile.setdMDLogDate(ss[6]);
		dMDReturnFile.setdMDClickName(ss[7]);
		dMDReturnFile.setdMDClickUrl(ss[8]);
		dMDReturnFile.setdMDBounceMessage(ss[9]);
		dMDReturnFile.setdMDipAddress(ss[10]);
		dMDReturnFile.setdMDClient(ss[11]);
		return dMDReturnFile;
	}
	/**
	 * 将时间格式的字符串（yyyy/MM/dd）转换格式（yyyy-MM-dd）
	 * 
	 * @param createDate
	 * @return
	 */
	public static String changeTimeFormat(String createDate) {
		if(createDate.length() <= 1){
			return "1910-01-01 00:00:00";
		}else{
			return createDate.replace("/", "-");
		}
	}
	/**
	 * 在上传csv文件到FTP服务器上前，对FTP服务器的上传目录进行备份并清理
	 * 
	 */
	public static void backupFilesBeforeuploadFile(){
        File from1 = new File(Util.getProperty("ftp.backupFilesBeforeuploadFileFrom1"));
        File from2 = new File(Util.getProperty("ftp.backupFilesBeforeuploadFileFrom2"));
        File to1 = new File(Util.getProperty("ftp.backupFilesBeforeuploadFileTo1"));
        File to2 = new File(Util.getProperty("ftp.backupFilesBeforeuploadFileTo2"));
        try {
            FileUtils.copyDirectory(from1, to1);
            FileUtils.cleanDirectory(from1);
            FileUtils.copyDirectory(from2, to2);
            FileUtils.cleanDirectory(from2);
        } catch (Exception e) {
        	log.error(Util.getTrace(e));
        }
    }
	/**
	 * 在下载了dmd回传文件并添加到了数据库后对本地下载目录进行清空，以及对FTP服务器的下载目录进行备份和清空
	 * 
	 */
	public static void backupFileAfterdownloadFile(){
		 File from = new File(Util.getProperty("ftp.backupFileAfterdownloadFileFrom"));
	     File to = new File(Util.getProperty("ftp.backupFileAfterdownloadFileTo"));
	     String path = System.getProperty("user.dir") + Util.getProperty("ftp.downloadclientpath");
	     File local = new File(path);
	     try {
	    	 //修改新的文件名添加时间戳
	    	 addTimestamp(from);
	    	 //拷贝文件到新的目录
	         FileUtils.copyDirectory(from, to);
	         FileUtils.cleanDirectory(from);
	         FileUtils.cleanDirectory(local);
	     } catch (Exception e) {
	    	 log.error(Util.getTrace(e));
	     }
	}
	/**
	 * 对文件的文件名添加时间戳，用于备份DMD回传文件时添加时间戳
	 * 
	 * @param from
	 */
	private static void addTimestamp(File from) {
		File[] files = from.listFiles();
    	for(int i = 0; i < files.length; i++){
    		if(files[i].getName().endsWith(".csv"))
    			files[i].renameTo(new File(getNewPath(files[i])));
    	}
	}
	/**
	 * 得到新的文件路径（添加时间），用于备份文件时更改文件的文件名的一个方法的参数生成
	 * 
	 * @param file
	 * @return
	 */
	private static String getNewPath(File file) {
		String s = file.getPath();
    	String s1 = s.substring(0, s.lastIndexOf("\\") + 1);
    	String s2 = s.substring(s.lastIndexOf("\\") + 1, s.length());
    	s2 = getDay() + s2;
		return s1 + s2;
	}
	/**
	 * 将web端oc_customer.Wishlist字段解析成itemid的list集合
	 * 
	 * @param wishlist
	 * @return
	 */
	public static List<Integer> formWishlistToList(String wishlist){
		List<Integer> list = null;
		if(wishlist != null){
			if(wishlist.length() > 3){
				list = new ArrayList<Integer>();
				String wishlist2 = wishlist;
//				log.debug("formWishlistToList:" + wishlist2);
				String s = wishlist2.substring(wishlist2.indexOf("{") + 1, wishlist2.length() - 1);
				String[] ss = s.split(";");
				for(int i = 0; i < ss.length; i++){
					if(i % 2 != 0){//Wishlist
						list.add(getInteger(ss[i]));
					}
				}
			}
			
		}
		
		return list;
	}
	/**
	 * 将web端oc_customer.cart字段解析成itemid的list集合
	 * 
	 * @param cart
	 * @return
	 */
	public static List<Integer> formCartToList(String cart){
		List<Integer> list = null;
		if(cart != null){
			if(cart.length() > 5){
				list = new ArrayList<Integer>();
				String wishlist2 = cart;
				String s = wishlist2.substring(wishlist2.indexOf("{") + 1, wishlist2.length() - 1);
				String[] ss = s.split(";");
				for(int i = 0; i < ss.length; i++){
					if(i % 2 == 0){//cart
						list.add(getInteger(ss[i]));
					}
				}
			}
			
		}
		
		return list;
	}
	/**
	 * 将web端oc_customer.Wishlist字段解析成customerid, itemid的map集合
	 * 
	 * @param wishlist
	 * @return
	 */
	public static Map<Integer, String> formWishlistToMap(String wishlist){
		Map<Integer, String> map = null;
		if(wishlist != null){
			if(wishlist.length() > 3){
				map = new HashMap<Integer, String>();
				String wishlist2 = wishlist;
//				log.debug("formWishlistToList:" + wishlist2);
				String s = wishlist2.substring(1, wishlist2.length() - 1);
				String[] ss = s.split(",");
				for(int i = 0; i < ss.length; i++){
						map.put(Integer.valueOf(ss[i].trim()), "");
				}
			}
		}
		return map;
	}
	/**
	 * 将web端oc_customer.cart字段解析成itemid,""的map集合
	 * 
	 * @param cart
	 * @return
	 */
	public static Map<Integer, String> formCartToMap(String cart){
//		log.debug("formCartToMap.cart:" + cart);
		Map<Integer, String> map = null;
		if(cart != null){
			if(cart.length() > 3){
				map = new HashMap<Integer, String>();
				String wishlist2 = cart;
				String s = wishlist2.substring(1, wishlist2.length() - 1);
				String[] ss = s.split(",");
				for(int i = 0; i < ss.length; i++){
						map.put(Integer.valueOf(ss[i].trim()), "");
				}
//				log.debug("formCartToMap.map:" + map);
			}
			
		}
		
		return map;
	}
	/**
	 * function is needed in formCartToList
	 * 
	 * @param string
	 * @return
	 */
	private static Integer getInteger(String string) {
		int ret = 0;
		try{
			if(string.startsWith("i")){
				ret = Integer.valueOf(string.substring(string.indexOf(":") + 1, string.length()));
			}else{
				ret = Integer.valueOf(string.substring(string.indexOf("\"") + 1, string.length() - 1));
			}
		}catch(Exception e){
			log.error(Util.getTrace(e) + "\n String:" + string);
		}
		return ret;
	}
	/**
	 * 检索文件夹下的文件命名为***_2014-07-30.csv的文件得到时间段字符串2014-07-30
	 * 
	 */
	public static String getBegainTimeFromFolder() {
		//[2500Email.csv, Itemlist_source1_2014-07-30.csv, userinfo_2014-07-30.csv, userlog_2014-07-30.csv]
		List<String> list = getFilesList();
		//[2014-07-30, 2014-07-30, 2014-07-30]
		List<String> list2 = new ArrayList();
		for(int i = 0; i < list.size(); i++){
			String s = list.get(i);
			if(list.get(i).contains("_"))
				list2.add(s.substring(s.indexOf(".") - 10, s.indexOf(".")));
		}
		if(list2.size() <= 0){
			String path = System.getProperty("user.dir") + Util.getProperty("DMACSVOutPutPath");
			log.error("path:" + path + " 没有合法的CSV文件，无法获得ActionCSV文件增量上传的开始时间！返回昨天时间");
			list2.add(getYesterday());
		}
		Collections.sort(list2);
		return list2.get(list2.size() - 1);
	}
	/**
	 * 得到存放CSV文件的文件夹下的所有文件的文件名
	 * 
	 * @return
	 */
	private static List<String> getFilesList() {
		List<String> list = new ArrayList<String>();
		String path = System.getProperty("user.dir") + Util.getProperty("DMACSVOutPutPath");
		File from = new File(path);
		File[] files;  
        files = from.listFiles(new FileFilterCSV(".csv")); 
        for(int i = 0; i < files.length; i++){
        	list.add(files[i].getName());
        }
		return list;
	}
	/**
	 * 将map集合转变成list集合
	 * 
	 * @param storeRemindMap
	 * @return
	 */
	public static List<Integer> formRemindMapToList(Map<Integer, String> storeRemindMap) {
		List<Integer> list = new ArrayList<Integer>();
		Iterator it = storeRemindMap.keySet().iterator();
		while(it.hasNext()){
			list.add((Integer) it.next());
		}
		log.debug("重新组合后的提醒list列表：" + list.toString());
		return list;
	}
}
