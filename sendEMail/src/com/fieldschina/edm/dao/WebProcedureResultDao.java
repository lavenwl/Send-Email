package com.fieldschina.edm.dao;

import com.fieldschina.edm.entity.WebProcedureResult;
/**
 * web存储过程执行结果相关数据库操作接口
 * 
 * @author  LAVEN  E-mail:lavenwl@gmail.com
 * @company FieldsChina.IT.BI
 * @version 创建时间：2014-7-23 下午3:57:18
 */
public interface WebProcedureResultDao {
	/**
	 * 检查存储过程是否执行完毕
	 * 
	 * @return
	 */
	public WebProcedureResult isProcedureFinished();
	/**
	 * 更新存储过程的结束时间（直接修改数据库），不可用，当前账号没有修改权限
	 * 
	 * @return
	 */
	public boolean updateWpEndTime();
	/**
	 * 更新一条数据到本地数据库
	 * 
	 * @param webProcedureResult
	 * @return
	 */
	public boolean updateToLocal(WebProcedureResult webProcedureResult);
}
