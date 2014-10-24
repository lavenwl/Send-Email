package com.fieldschina.edm.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dmdelivery.webservice.type.ArrayOfIntType;
import com.dmdelivery.webservice.type.CampaignArrayType;
import com.dmdelivery.webservice.type.CampaignType;
import com.dmdelivery.webservice.type.NewRecipientType;
import com.dmdelivery.webservice.type.RecordResultType;
import com.fieldschina.edm.dao.CampaignDao;
import com.fieldschina.edm.dao.impl.CampaignDaoImpl;
import com.fieldschina.edm.entity.Campaign;
import com.fieldschina.edm.util.Util;

/**
 * 活动逻辑实现类
 * 
 * @author  LAVEN  E-mail:lavenwl@gmail.com
 * @company FieldsChina.IT.BI
 * @version 创建时间：2014-6-27 上午10:04:34
 */
public class CampaignService extends Service {
	private static CampaignDao campaignDao = null;
	static{
		if(campaignDao == null){
			campaignDao = new CampaignDaoImpl();
		}
	}
	/**
	 * 得到本地数据库中的活动信息
	 * 
	 * @return	本地的活动信息
	 */
	public List<Campaign> getCampaigns(){
		return campaignDao.getCampaigns();
	}
	/**
	 * 更新本地数据库的活动信息
	 * 
	 * @param list	将集合更新至本地
	 * @return		是否更新成功
	 */
	public boolean updateCampaigns(List<Campaign> list){
		boolean b = true;
		//查询所存在的活动列表
		List<Campaign> eList = getCampaigns();
		log.debug("查询本地已存在的活动：" + eList.size());
		//将存在的活动的ID保存至map中方面后面查询使用
		Map<Integer, String> map = new HashMap<Integer, String>();
		for(int i = 0; i < eList.size(); i++){
			map.put(eList.get(i).getWpId(), "");
		}
		//执行更新活动操作
		for(int j = 0; j < list.size(); j++){
			if(!map.containsKey(list.get(j).getWpId())){
				log.debug("插入了一个活动，ID：" + list.get(j).getWpId());
				boolean a = campaignDao.insertCampaign(list.get(j));
				if(a == false){
					b = a;
				}
			}
		}
		return b;
	}
	/**
	 * 将webpower平台的活动信息更新到本地
	 * 
	 * @return	是否更新成功
	 */
	public boolean updateCampaignsFromWp(){
		boolean b = false;
		try{
			// 初始化API需要的变量类型
			CampaignArrayType result = oFactory.createCampaignArrayType();	//返回结果类
			//发送API请求，接收返回结果
			result = service.getCampaigns(login);			//登陆信息
			log.debug("CampaignResult.size:" + result.getCampaign().size());
			//处理API返回的结果
			List<CampaignType> listCampaignType = result.getCampaign();
			List<Campaign> listCampaign = new ArrayList<Campaign>();
			for(int i = 0; i < listCampaignType.size(); i++){
				Campaign c = new Campaign();
				c.setWpId(listCampaignType.get(i).getId());
				c.setName(listCampaignType.get(i).getName());
				listCampaign.add(c);
			}
			log.debug("listCampaign.size:" + listCampaign.size());
			if(listCampaign.size() > 0){
				updateCampaigns(listCampaign);
			}
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.toString());
		}
		return b;
	
	}
	/**
	 * 更具活动Id得到该活动的全量用户组
	 * 
	 * @param id	活动ID
	 * @return		本活动下的全量组Id
	 */
	public int getGroupByCampId(int id) {
		List<Integer> list = campaignDao.getGroupByCampId(id);
		int result = 0;
		//分析全量组逻辑
		if(list == null || list.size() > 1){
			log.error("数据库中groupId设置有问题！");
		}else{
			result = list.get(0);
		}
		return result;
	}
}
