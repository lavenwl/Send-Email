package com.fieldschina.edm.dao;

import java.util.List;
import com.fieldschina.edm.entity.Recipient;
/**
 * 用户数据库操作类
 * 
 * @author  LAVEN  E-mail:lavenwl@gmail.com
 * @company FieldsChina.IT.BI
 * @version 创建时间：2014-6-19 上午10:29:15
 */
public interface RecipientDao {
	/**
	 * 读取web数据库中每日新增的用户的数据（目前不会用到这个方法）
	 * 
	 * @return	每日新增的数据
	 */
	public List<Recipient> findDayNewRecipients();
	/**
	 * 读取web数据库中全部的用户的数据（分月份分批读取用户数据一次性返回）
	 * 
	 * @return	全部的用户数据
	 */
	public List<Recipient> findAllRecipients();
	/**
	 *  * 插入用户的数据进入本地数据库（将每一个用户的成上传情况全部记录到本地数据库，然而现在是每天全量的上传数据会造成很多的无用的数据生成，所以现在不会使用这个方法）
	 * 
	 * @param recipient	要插入的用户对象
	 * @return 			返回是否插入成功
	 */
	boolean insertRecipient(Recipient recipient);
	/**
	 * 得到创建CSV文件符合条件的用户（此方法现在已经没有用，该功能已经迁移出到control工程）
	 * 
	 * @return
	 */
	public List<Recipient> findMatchRecipient();
	/**
	 * 得到fields内部员工的数据（email）
	 * 
	 * @return
	 */
	public List<Recipient> findFieldsEmail();
	/**
	 * 得到fields内部IT员工的数据（email）
	 * 
	 * @return
	 */
	public List<Recipient> findFieldsITEmail();
	/**
	 * 得到创建CSV文件当月注册时间14天以上的用户（此方法现在已经没有用，该功能已经迁移出到control工程）
	 * 
	 * @return
	 */
	public List<Recipient> findCurrentMonthRegistrationM14Days();
	/**
	 * 得到创建CSV文件注册时间大于7天小于14天的用户（此方法现在已经没有用，该功能已经迁移出到control工程）
	 * 
	 * @return
	 */
	public List<Recipient> findCurrentMonthRegistrationM7L14Days();
}
