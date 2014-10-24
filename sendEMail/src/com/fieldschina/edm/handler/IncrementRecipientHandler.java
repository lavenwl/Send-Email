package com.fieldschina.edm.handler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import java.util.Timer;
import org.apache.log4j.Logger;

import com.fieldschina.edm.ClientTest;
import com.fieldschina.edm.dao.RecipientResultDao;
import com.fieldschina.edm.dbconn.DBConnectionManager;
import com.fieldschina.edm.entity.WebProcedureResult;
import com.fieldschina.edm.entity.Campaign;
import com.fieldschina.edm.entity.Recipient;
import com.fieldschina.edm.entity.RecipientResult;
import com.fieldschina.edm.service.RecipientResultService;
import com.fieldschina.edm.service.RecipientService;
import com.fieldschina.edm.service.WebProcedureResultService;
import com.fieldschina.edm.util.Filter;
import com.fieldschina.edm.util.Util;
/**
 * 每日上传用户数据到DMD平台
 * 
 * @author  LAVEN  E-mail:lavenwl@gmail.com
 * @company FieldsChina.IT.BI
 * @version 创建时间：2014-7-23 下午3:33:53
 */
public class IncrementRecipientHandler extends Handler{
	Logger log = Logger.getLogger(IncrementRecipientHandler.class);
	/**
	 * 计时器开始的方法
	 */
	public void Timer(){
		try {
			log.info("Timer start！");
			//开始时间的初始化
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd " + Util.getProperty("incrementRecipientHandlerBeginTime"));
			Date startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sdf.format(new Date()));
			//判断当前时间是否已经过了开始时间
			if (System.currentTimeMillis() > startTime.getTime()) {
				startTime = new Date(startTime.getTime() + Util.getTimeProperty("incrementRecipientHandlerSleepTime"));
			}
			//实例化一个定时器
			Timer t = new Timer();
			//定时器任务
			TimerTask task = new TimerTask() {
				public void run() {
					try{
						log.info("IncrementRecipientHandler addRecipientFunction Timer Task start！");
						for(int i = 0; i < (Integer.valueOf(Util.getProperty("waitProcedureTime")) / 5); i++){
							//检测web端的存储过程是否执行成功
							WebProcedureResult webProcedureResult = isProcedureFinished();
							if(webProcedureResult != null){
								webProcedureResult.setWpStartTime(Util.getDayTime());
								addRecipientFunction();
								webProcedureResult.setWpEndTime(Util.getDayTime());
								WebProcedureResultService webProcedureResultService = new WebProcedureResultService();
								webProcedureResultService.updateToLocal(webProcedureResult);
								//log.debug("可以执行addRecipientFunction()");
								try {
									log.debug("已经释放连接！");
									DBConnectionManager dbm = DBConnectionManager.getInstance();
									dbm.release();
									dbm.setInstanceNull();
								} catch (Exception e) {
									log.error(Util.getTrace(e));
								}
								break;
							}else{
								log.debug("不可以执行addRecipientFunction()");
								try {
									Thread.sleep(1000 * 60 * 5);
								} catch (InterruptedException e) {
									log.error(Util.getTrace(e));
								}
							}
						}
						log.info("Timer task end！");
					}catch(Exception e){
						log.error(Util.getTrace(e));
					}
				}
			};
			t.scheduleAtFixedRate(task, startTime, Util.getTimeProperty("incrementRecipientHandlerSleepTime"));
		} catch (ParseException e) {
			log.error(Util.getTrace(e));
		}
	}
	/**
	 * 获取web存储过程是否执行完毕的表内的数据，并拿到数据组成实体对象（方便同步到本地数据库）
	 * 
	 * @return
	 */
	private WebProcedureResult isProcedureFinished() {
		WebProcedureResultService webProcedureResultService = new WebProcedureResultService();
		return webProcedureResultService.isProcedureFinished();
	}
	/**
	 * 添加用户方法
	 */
	public void addRecipientFunction(){
		RecipientService rs = new RecipientService();
		//得到新增用户集合
		long a = System.currentTimeMillis();
		//List<Recipient> list = rs.findDailyRecipient(); 	//得到日增的用户
		log.debug("findAllRecipients...");
		List<Recipient> list = rs.findAllRecipient();    	//得到全部的用户
		//List<Recipient> list = ClientTest.getTestRecipientList(); 	//得到测试用户
		long b = System.currentTimeMillis();
		log.debug("读取数据使用的时间：" + (b - a) + " 得到数据的总长度：" + list.size());
		if(list.size() > 0){
			//对得到的数据进行不同活动的分类返回一个包含多个活动新用户集合的集合
			Map<Integer, List<Recipient>> campaignMap = classifyCampaign(list);
			//添加数据到webpower平台
			Iterator it = campaignMap.keySet().iterator();
			while(it.hasNext()){
				int i = (Integer) it.next();
				log.debug("过滤后数据分配结果：map.key:" + i + " map.value.size:" + campaignMap.get(i).size());
				if(campaignMap.get(i).size() != 0){
					addRecipientsOneTime(i, campaignMap.get(i));
				}
			} // end of while(it.hasNext())
		} // end of if(list.size() > 0)
		//插入表格内脚本运行结束的时间
	}
	/**
	 * 对得到的数据对不同的活动进行不同的分类
	 * 
	 * @param list	需要分类的原始数据
	 * @return	包含多个活动新用户集合的集合
	 */
	private Map<Integer, List<Recipient>> classifyCampaign(List<Recipient> list) {
		//得到当前活跃的活动（及需要时时上传数据的活动）
		List<Integer> campaignList = Util.paraseStringToInt(Util.getProperty("activeCampaignIDS"));
		//初始化各个活动的数据集合
		Map<Integer, List<Recipient>> campaignMap = new HashMap<Integer, List<Recipient>>();
		for(int i = 0; i < campaignList.size(); i++){
			campaignMap.put(campaignList.get(i), new ArrayList<Recipient>());
		}
		//对每个对象进行条件过滤分配到响应的活动数据集合
		for(int i = 0; i < list.size(); i++){
			//得到对象符合的活动的id的集合
			List<Integer> cList = Filter.classifyCampaign(list.get(i));
			//将对象添加到对应的活动数据集合中
			for(int j = 0; j < cList.size(); j++){
				campaignMap.get(cList.get(j)).add(list.get(i));
			}
		}
		return campaignMap;
	}
	/**
	 * 逐个增加用户到DMD
	 * 
	 * @param list
	 */
	public void addRecipientOneByOne(List<Recipient> list){
		RecipientService rs = new RecipientService();
		List<Long> tlist = new ArrayList<Long>();		//记录时间使用对象
		for(int i = 0; i < list.size(); i++){
			long a = System.currentTimeMillis();
			Recipient r = list.get(i);
			rs.addRecipient(r);
			long b = System.currentTimeMillis();
			tlist.add(b-a);
			log.debug("单个用户(" + i + ")添加使用时间：" + (b - a));
		}
		long s = 0;
		for(int j = 0; j < tlist.size(); j++){
			s = s + tlist.get(j);
		}
		log.debug("单次上传的平均时间：" + (s/list.size()));
	}
	/**
	 * 批量上传新增用户数据
	 * @param i2  活动ID
	 * 
	 * @param list
	 */
	public void addRecipientsOneTime(int i2, List<Recipient> list){
		Map resultMap = new HashMap<String, Integer>();
		//log.debug("list:" + list.toString());
		int n = (list.size()/900) + 1;
		//log.debug("n:" + n);
		for(int i = 0; i < n; i++){
			List<Recipient> list1 = null;
			if(i == (n - 1)){
				//log.debug("sublist:" + i * 900 + " sublist:" + list.size());
				list1 = list.subList(i * 900, list.size());
			}else{
				list1 = list.subList(i * 900, (i * 900) + 900);
			}
			//log.debug("list1:" + list1 + " list1.size:" + list1.size());
			if(list1 != null){
				RecipientService rs = new RecipientService();
				//log.debug("list1.size:" + list1.size());
				long a = System.currentTimeMillis();
				Map map = rs.addRecipients(i2, list1);
				//累加每次的结果
				resultMap = Util.mapAddMap(resultMap, map);
				long b = System.currentTimeMillis();
				log.debug("第" + i + "次总时间：" + (b - a) + "平均使用时间：" + (b -a) / list.size() + " 得到的结果是：" + map.toString());
			} // end of if(list1 != null)
		} // end of for(int i = 0; i < n; i++)
		//记录每次上传的结果（每天）
		resultMap.put("campaignId", i2);
		saveLogAddRecipient(resultMap);
		log.debug("完成addRecipientsOneTime。结果：" + resultMap.toString());
	} // end of public void addRecipientsOneTime(List<Recipient> list)
	/**
	 * 保存上传用户信息的日志对象
	 * 
	 * @param resultMap
	 */
	private void saveLogAddRecipient(Map resultMap) {
		RecipientResult recipientResult = new RecipientResult();
		recipientResult.setSum(Integer.valueOf(String.valueOf(resultMap.get("sum"))));
		recipientResult.setError(Integer.valueOf(String.valueOf(resultMap.get("error"))));
		recipientResult.setSuccess(Integer.valueOf(String.valueOf(resultMap.get("success"))));
		recipientResult.setDumplicate(Integer.valueOf(String.valueOf(resultMap.get("dumplicate"))));
		recipientResult.setCampaignId(Integer.valueOf(String.valueOf(resultMap.get("campaignId"))));
		RecipientResultService recipientResultService = new RecipientResultService();
		recipientResultService.addRecipientResultToLocalDatabase(recipientResult);
	}
	
}
