package com.fieldschina.edm.dao;

import java.util.List;
import com.fieldschina.edm.entity.Campaign;
/**
 * 活动数据库访问接口
 * 
 * @author  LAVEN  E-mail:lavenwl@gmail.com
 * @company FieldsChina.IT.BI
 * @version 创建时间：2014-6-27 上午9:59:26
 */
public interface CampaignDao {
	/**
	 * 得到所有的活动信息
	 * 
	 * @return	查询到的所有的活动
	 */
	public List<Campaign> getCampaigns();
	/**
	 * 插入一条新的活动(测试使用，准备添加自动更新活动信息功能使用)
	 * 
	 * @param campaign
	 * @return
	 */
	public boolean insertCampaign(Campaign campaign);
	/**
	 * 得到一个活动ID下的组ID
	 * 
	 * @param id
	 * @return	返回当前活动的basic组的ID
	 */
	public List<Integer> getGroupByCampId(int id);
}
