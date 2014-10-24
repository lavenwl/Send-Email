package com.fieldschina.edm.service;

import com.fieldschina.edm.dao.WebProcedureResultDao;
import com.fieldschina.edm.dao.impl.WebProcedureResultDaoImpl;
import com.fieldschina.edm.entity.WebProcedureResult;
/**
 * 操作表（网站存储过程执行结果）本地逻辑类
 * 
 * @author  LAVEN  E-mail:lavenwl@gmail.com
 * @company FieldsChina.IT.BI
 * @version 创建时间：2014-7-23 下午2:35:15
 */
public class WebProcedureResultService extends Service{
	WebProcedureResultDao webProcedureResultDao = null;
	public WebProcedureResultDao getwebProcedureResultDao(){
		if(webProcedureResultDao == null){
			webProcedureResultDao = new WebProcedureResultDaoImpl();
		}
		return webProcedureResultDao;
	}
	/**
	 * 检查web端数据库的存储过程
	 * 
	 * @return
	 */
	public WebProcedureResult isProcedureFinished() {
		return getwebProcedureResultDao().isProcedureFinished();
	}
	/**
	 * 更新（直接对数据库进行修改）web端存储过程的结束时间（不可用）因为此账号没有修改权限
	 * 
	 * @return
	 */
	public boolean updateWpEndTime() {
		return getwebProcedureResultDao().updateWpEndTime();
	}
	/**
	 * 更新一条数据到本地数据库
	 * 
	 * @param webProcedureResult
	 * @return
	 */
	public boolean updateToLocal(WebProcedureResult webProcedureResult) {
		return getwebProcedureResultDao().updateToLocal(webProcedureResult);
	}

}
