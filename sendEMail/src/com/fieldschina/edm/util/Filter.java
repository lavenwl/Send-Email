package com.fieldschina.edm.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.fieldschina.edm.entity.Recipient;

/**
 * 活动过滤器类
 * 
 * @author  LAVEN  E-mail:lavenwl@gmail.com
 * @company FieldsChina.IT.BI
 * @version 创建时间：2014-6-27 下午3:04:09
 */
public class Filter {
	private static Logger log = Logger.getLogger(Filter.class);
	public static List<Integer> classifyCampaign(Recipient recipient){
		List<Integer> returnList = new ArrayList<Integer>();
		//得到当前活跃的活动（及需要时时上传数据的活动）并转换数据
		List<Integer> campaignList = Util.paraseStringToInt(Util.getProperty("activeCampaignIDS"));
		Map<Integer, String> campaignMap = new HashMap<Integer, String>();
		for(int i = 0; i < campaignList.size(); i++){
			campaignMap.put(campaignList.get(i), "");
		}
		//添加每个活动的过滤条件
		//活动（testAPI）的条件过滤
		if(campaignMap.containsKey(3)){
			if(true){
				recipient.setCampaignIds(Util.addIntToString(recipient.getCampaignIds(), 3));
				returnList.add(3);
			}
		}
		//活动（training20140624 ）的条件过滤
		if(campaignMap.containsKey(6)){
			if(recipient.getEmail().equals("training20140624@training20140624.com")){
				recipient.setCampaignIds(Util.addIntToString(recipient.getCampaignIds(), 6));
				returnList.add(6);
			}	
		}
		//活动（ALICE [TEST] ）的条件过滤
		if(campaignMap.containsKey(5)){
			if(recipient.getEmail().equals("ALICE[TEST]@ALICE[TEST].com")){
				recipient.setCampaignIds(Util.addIntToString(recipient.getCampaignIds(), 5));
				returnList.add(5);
			}	
		}
		//活动（Alextest Event ）的条件过滤
		if(campaignMap.containsKey(4)){
			if(recipient.getEmail().equals("AlextestEvent@AlextestEvent.com")){
				recipient.setCampaignIds(Util.addIntToString(recipient.getCampaignIds(), 4));
				returnList.add(4);
			}	
		}
		//活动（5000Mail test ）的条件过滤
		if(campaignMap.containsKey(2)){
			if(recipient.getEmail().equals("5000Mail@5000Mail.com")){
				recipient.setCampaignIds(Util.addIntToString(recipient.getCampaignIds(), 2));
				returnList.add(2);
			}
		}
		//活动（CHN MKT）的条件过滤
		if(campaignMap.containsKey(8)){
			if(recipient.getNationality1() != null){
				if(recipient.getNationality1().equals("China") || recipient.getNationality1().equals("china")){
					recipient.setCampaignIds(Util.addIntToString(recipient.getCampaignIds(), 8));
					returnList.add(8);
				}
			}
		}
		//活动（EXP MKT）的条件过滤
		if(campaignMap.containsKey(9)){
			if(recipient.getNationality1() != null){
				if(!(recipient.getNationality1().equals("China") || recipient.getNationality1().equals("china"))){
					recipient.setCampaignIds(Util.addIntToString(recipient.getCampaignIds(), 9));
					returnList.add(9);
				}
			}
		}
		//活动（VIP MKT）的条件过滤
		if(campaignMap.containsKey(10)){
			if(recipient.getVip().equals("Y")){
				recipient.setCampaignIds(Util.addIntToString(recipient.getCampaignIds(), 10));
				returnList.add(10);
			}
		}
		//活动（WEP OPER）的条件过滤
		if(campaignMap.containsKey(11)){
			if(recipient.getNationality1() != null){
				if(recipient.getNationality1().equals("China") || recipient.getNationality1().equals("china")){
					recipient.setCampaignIds(Util.addIntToString(recipient.getCampaignIds(), 11));
					returnList.add(11);
				}
			}
		}
		//活动（CHN MKT）的条件过滤
		if(campaignMap.containsKey(13)){
			if(recipient.getNationality1() != null){
				if(recipient.getNationality1().equals("China") || recipient.getNationality1().equals("china")){
					recipient.setCampaignIds(Util.addIntToString(recipient.getCampaignIds(), 13));
					returnList.add(13);
				}
			}
		}
		//活动（EXP MKT）的条件过滤
		if(campaignMap.containsKey(14)){
			if(recipient.getNationality1() != null){
				if(!(recipient.getNationality1().equals("China") || recipient.getNationality1().equals("china"))){
					recipient.setCampaignIds(Util.addIntToString(recipient.getCampaignIds(), 14));
					returnList.add(14);
				}
			}
		}
		//活动（VIP MKT）的条件过滤
		if(campaignMap.containsKey(15)){
			if(recipient.getVip().equals("Y")){
				recipient.setCampaignIds(Util.addIntToString(recipient.getCampaignIds(), 15));
				returnList.add(15);
			}
		}
		//活动（WEP OPER）的条件过滤
		if(campaignMap.containsKey(16)){
			if(recipient.getNationality1() != null){
				if(recipient.getNationality1().equals("China") || recipient.getNationality1().equals("china")){
					recipient.setCampaignIds(Util.addIntToString(recipient.getCampaignIds(), 16));
					returnList.add(16);
				}
			}
		}
		if(returnList.size() == 0){
			log.debug("存在一个不属于任何活动的用户：" + recipient.toString());
		}
		return returnList;
	}
}
