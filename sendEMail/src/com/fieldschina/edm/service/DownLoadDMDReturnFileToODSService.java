package com.fieldschina.edm.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import com.fieldschina.edm.dao.DMDReturnFileDao;
import com.fieldschina.edm.dao.impl.DMDReturnFileDaoImpl;
import com.fieldschina.edm.entity.DMDReturnFile;
import com.fieldschina.edm.ftp.FTPServer;
import com.fieldschina.edm.util.FileFilterCSV;
import com.fieldschina.edm.util.Util;
/**
 * 下载DMD平台回传文件到本地数据库的操作逻辑类
 * 
 * @author  LAVEN  E-mail:lavenwl@gmail.com
 * @company FieldsChina.IT.BI
 * @version 创建时间：2014-7-23 下午3:10:20
 */
public class DownLoadDMDReturnFileToODSService extends Service{
	/**
	 * 下载文件到本地
	 * @return
	 */
	public boolean downloadFiles(){
		FTPServer ftpServer = new FTPServer();
		return ftpServer.downFiles();
	}
	/**
	 * 读取文件到程序
	 * @return
	 */
	public boolean getDownloadFiles(){
		boolean b = false;
		String path = System.getProperty("user.dir") + Util.getProperty("ftp.downloadclientpath");   
        File root = new File(path);  
        if (!root.exists())  
            root.mkdir();  
        File[] files;  
        files = root.listFiles(new FileFilterCSV(".csv"));  
        if (files.length != 0)  
            for (int i = 0; i < files.length; i++){
            	log.debug("get download file:" + files[i].getName());
            	BufferedReader fileInputStream = null;
                try {
					fileInputStream = new BufferedReader(new InputStreamReader(new FileInputStream(files[i]), "GB2312"), 1024);
					//表头去除
					fileInputStream.readLine();
					String s = null;
					do{
						//内容读取
						s = fileInputStream.readLine();
						if(s != null){
							DMDReturnFile dMDReturnFile = Util.formateStringToDMDReturnFile(s);
							//将读取的数据插入到数据库
							insertDMAReturnFile(dMDReturnFile);
						}
					}while(s != null);
					b = true;
				} catch (UnsupportedEncodingException e) {
					log.error(Util.getTrace(e));
				} catch (FileNotFoundException e) {
					log.error(Util.getTrace(e));
				} catch (IOException e) {
					log.error(Util.getTrace(e));
				} finally { 
			           try { 
			        	   fileInputStream.close(); 
			            } catch (IOException e) { 
			            	log.error(Util.getTrace(e));
			           } 
			       } 
            }
		return false;
	}
	/**
	 * 写入数据到数据库
	 * @param dMDReturnFile
	 * @return
	 */
	public boolean insertDMAReturnFile(DMDReturnFile dMDReturnFile){
		DMDReturnFileDao dMDReturnFileDaoImpl = new DMDReturnFileDaoImpl();
		return dMDReturnFileDaoImpl.insertDMAReturnFile(dMDReturnFile);
	}
}
