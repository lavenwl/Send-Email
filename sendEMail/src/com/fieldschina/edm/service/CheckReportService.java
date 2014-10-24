package com.fieldschina.edm.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import com.fieldschina.edm.util.Util;
/**
 * 检查log日志是否有报错
 * 
 * @author  LAVEN  E-mail:lavenwl@gmail.com
 * @company FieldsChina.IT.BI
 * @version 创建时间：2014-7-23 下午3:31:36
 */
public class CheckReportService extends Service{
	/**
	 * 检测log日志是否有报错
	 * 
	 * @return
	 */
	public String checkReport(){
		String result = "昨日日志检查：\n";
		String path = "logs/send.log" + Util.getYesterday() + ".log";
		File file = new File(path);
		BufferedReader csvFileInputStream = null;
		String s = null;
		boolean b = false;
		try {
			if(file.exists()){
				log.debug("检查昨日日志文件。");
				csvFileInputStream = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"), 1024);
				while ((s = csvFileInputStream.readLine()) != null){
					if(s.contains("[ERROR]")){
						result = result + s + "\n";
						b = true;
					}
					if(!s.startsWith("[")){
						result = result + s + "\n";
					}else{
						b= false;
					}
				}
			}
			
			result = result + "今日日志检查：\n";
			path = "logs/send.log";
			file = new File(path);
			if(file.exists()){
				log.debug("检查今日日志文件。");
				csvFileInputStream = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"), 1024);
				s = null;
				b = false;
				while ((s = csvFileInputStream.readLine()) != null){
					if(s.contains("[ERROR]")){
						result = result + s + "\n";
						b = true;
					}
					if(!s.startsWith("[")){
						result = result + s + "\n";
					}else{
						b= false;
					}
				}
			}
			
		} catch (UnsupportedEncodingException e) {
			log.error(Util.getTrace(e));
		} catch (FileNotFoundException e) {
			log.error(Util.getTrace(e));
		} catch (IOException e) {
			log.error(Util.getTrace(e));
		}
		return result;
	}
}
