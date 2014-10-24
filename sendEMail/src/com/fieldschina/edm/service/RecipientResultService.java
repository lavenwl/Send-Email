package com.fieldschina.edm.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dmdelivery.webservice.type.AddRecipientsResultType;
import com.dmdelivery.webservice.type.ArrayOfIntType;
import com.dmdelivery.webservice.type.NewRecipientArrayType;
import com.dmdelivery.webservice.type.NewRecipientType;
import com.dmdelivery.webservice.type.RecipientArrayType;
import com.dmdelivery.webservice.type.RecipientNameValuePairType;
import com.dmdelivery.webservice.type.RecipientResultArrayType;
import com.dmdelivery.webservice.type.RecipientResultType;
import com.dmdelivery.webservice.type.RecipientType;
import com.dmdelivery.webservice.type.RecordResultType;
import com.fieldschina.edm.dao.RecipientDao;
import com.fieldschina.edm.dao.RecipientResultDao;
import com.fieldschina.edm.dao.impl.RecipientDaoImpl;
import com.fieldschina.edm.dao.impl.RecipientResultDaoImpl;
import com.fieldschina.edm.entity.Recipient;
import com.fieldschina.edm.entity.RecipientResult;
import com.fieldschina.edm.util.Util;

/**
 * 用户操作结果逻辑类
 * 
 * @author  LAVEN  E-mail:lavenwl@gmail.com
 * @company FieldsChina.IT.BI
 * @version 创建时间：2014-6-19 下午5:52:55
 */
public class RecipientResultService extends Service{
	private static RecipientResultDao recipientResultDao = null;
	static{
		if(recipientResultDao == null){
			recipientResultDao =  new RecipientResultDaoImpl();
		}
	}
	/**
	 * 将用户结果对象保存到本地数据库
	 * 
	 * @param recipient
	 * @return
	 */
	public boolean addRecipientResultToLocalDatabase(RecipientResult recipientResult) {
		return recipientResultDao.addRecipientResult(recipientResult);
	}
}
