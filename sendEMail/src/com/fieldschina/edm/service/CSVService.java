package com.fieldschina.edm.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import com.fieldschina.edm.util.Util;

/**
 * CSV文件相关逻辑类
 * 
 * @author  LAVEN  E-mail:lavenwl@gmail.com
 * @company FieldsChina.IT.BI
 * @version 创建时间：2014-7-3 下午3:21:35
 */
public class CSVService extends Service{
	/**
	 * 创建CSV文件工具
	 * 
	 * @param exportData	具体数据信息
	 * @param rowMapper		数据列的字段名
	 * @param outPutPath	输出文件的路径
	 * @param filename		输出文件的文件名
	 * @return				返回的文件
	 */
	 public static File createCSVFile(List exportData, LinkedHashMap rowMapper, String outPutPath, String filename) {

	        File csvFile = null;
	        BufferedWriter csvFileOutputStream = null;
	        try {
	            csvFile = new File(outPutPath + "/" + filename);
	            File parent = csvFile.getParentFile();
	            if (parent != null && !parent.exists()) {
	                parent.mkdirs();
	            }
	            csvFile.createNewFile();
	            // GB2312使正确读取分隔符","
	            csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), "GB2312"), 1024);
	            // 写入文件头部
	            for (Iterator propertyIterator = rowMapper.entrySet().iterator(); propertyIterator.hasNext();) {
	                java.util.Map.Entry propertyEntry = (java.util.Map.Entry) propertyIterator.next();
	                csvFileOutputStream.write("\"" + propertyEntry.getValue().toString() + "\"");
	                if (propertyIterator.hasNext()) {
	                    csvFileOutputStream.write(",");
	                }
	            }
	            csvFileOutputStream.newLine();
	            // 写入文件内容
	            for (Iterator iterator = exportData.iterator(); iterator.hasNext();) { 
	            	LinkedHashMap row = (LinkedHashMap) iterator.next(); 
	               // log.debug("row:" + row);
	                for (Iterator propertyIterator = row.entrySet().iterator(); propertyIterator.hasNext();) { 
	                    java.util.Map.Entry propertyEntry = (java.util.Map.Entry) propertyIterator.next();
	                    if(propertyEntry == null || propertyEntry.getValue() == null){
	                    	 csvFileOutputStream.write("\"\""); 
	                    }else{
	                    	 csvFileOutputStream.write("\"" +  propertyEntry.getValue().toString() + "\""); 
	                    }
	                   if (propertyIterator.hasNext()) { 
	                       csvFileOutputStream.write(","); 
	                    } 
	               } 
	                if (iterator.hasNext()) { 
	                   csvFileOutputStream.newLine(); 
	                } 
	           } 
	            csvFileOutputStream.flush(); 
	        } catch (Exception e) { 
	        	log.error(Util.getTrace(e));
	        } finally { 
	           try { 
	                csvFileOutputStream.close(); 
	            } catch (IOException e) { 
	            	log.error(Util.getTrace(e));
	           } 
	       } 
	        return csvFile;
	    }
	 /**
	  * 自增的将数据添加到CSV文件里面，现在不会用到。
	  * 
	  * @param exportData
	  * @param rowMapper
	  * @param outPutPath
	  * @param sourcefilename
	  * @param filename
	  * @return
	  */
	 public static File addCSVFile(List exportData, LinkedHashMap rowMapper, String outPutPath, String sourcefilename, String filename) {
		 	//读文件需要的类
		 	File csvInFile = null;
		 	BufferedReader csvFileInputStream = null;
		 	//写文件需要的类
	        File csvFile = null;
	        BufferedWriter csvFileOutputStream = null;
	        try {
	        	//读文件
	        	csvInFile = new File(outPutPath + "/" + sourcefilename + ".csv");
//	        	System.out.println("csvInFile:" + csvInFile.getPath());
//	        	File parentIn = csvInFile.getParentFile();
//	        	if(parentIn != null && !parentIn.exists()){
//	        		parentIn.mkdirs();
//	        	}//展开这段代码会对原本的文件进行覆盖
	        	csvInFile.createNewFile();
	        	//写文件
	            csvFile = new File(outPutPath + "/" + filename + ".csv");
	        	System.out.println("csvFile:" + csvFile.getPath());
	            File parent = csvFile.getParentFile();
	            if (parent != null && !parent.exists()) {
	                parent.mkdirs();
	            }
	            csvFile.createNewFile();
	            
	            // GB2312使正确读取分隔符","读文件
	            csvFileInputStream = new BufferedReader(new InputStreamReader(new FileInputStream(csvInFile), "GB2312"), 1024);
	            
	            // GB2312使正确读取分隔符","写文件
	            csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), "GB2312"), 1024);
	            String input = null;
	            System.out.println("csvFileOutputStream:" + csvFileOutputStream);
	            boolean b = true;//是否建立表头行
	            while((input = csvFileInputStream.readLine()) != null)  
	            {  
	            	b = false;
	            	csvFileOutputStream.write(input);  
	                //newLine()方法写入与操作系统相依的换行字符，依执行环境当时的OS来决定该输出那种换行字符  
	            	csvFileOutputStream.newLine();  
	            }  
	            if(b){
	                // 写入文件头部
	                for (Iterator propertyIterator = rowMapper.entrySet().iterator(); propertyIterator.hasNext();) {
	                    java.util.Map.Entry propertyEntry = (java.util.Map.Entry) propertyIterator.next();
	                    csvFileOutputStream.write("\"" + propertyEntry.getValue().toString() + "\"");
	                    if (propertyIterator.hasNext()) {
	                        csvFileOutputStream.write(",");
	                    }
	                }
	                csvFileOutputStream.newLine();
	                csvFileOutputStream.write("此文件非自增文件（缺少文件：" + filename + ".csv）,,,");
	                csvFileOutputStream.newLine();
	            }
	       
	            // 写入文件内容
	            for (Iterator iterator = exportData.iterator(); iterator.hasNext();) { 
	            	LinkedHashMap row = (LinkedHashMap) iterator.next(); 
	               // log.debug("row:" + row);
	                for (Iterator propertyIterator = row.entrySet().iterator(); propertyIterator.hasNext();) { 
	                    java.util.Map.Entry propertyEntry = (java.util.Map.Entry) propertyIterator.next();
	                    if(propertyEntry == null || propertyEntry.getValue() == null){
	                    	 csvFileOutputStream.write("\"\""); 
	                    }else{
	                    	 csvFileOutputStream.write("\"" +  propertyEntry.getValue().toString() + "\""); 
	                    }
	                   if (propertyIterator.hasNext()) { 
	                       csvFileOutputStream.write(","); 
	                    } 
	               } 
	                if (iterator.hasNext()) { 
	                   csvFileOutputStream.newLine(); 
	                } 
	           } 
	            csvFileOutputStream.flush(); 
	        } catch (Exception e) {
	        	log.error(Util.getTrace(e));
	        } finally { 
	           try { 
	                csvFileOutputStream.close(); 
	            } catch (IOException e) { 
	            	log.error(Util.getTrace(e));
	           } 
	       } 
	        return csvFile;
	    }
	 /**
		 * 创建CSV文件工具（不会添加数据列的字段名），及累加生成CSV文件
		 * 
		 * @param exportData	具体数据信息	
		 * @param outPutPath	输出文件的路径
		 * @param filename		输出文件的文件名
		 * @return				返回的文件
		 */
	public void createCSVFile(List exportData, String outPutPath, String filename) {

        File csvFile = null;
        BufferedWriter csvFileOutputStream = null;
        try {
            csvFile = new File(outPutPath + "/" + filename);
            File parent = csvFile.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            csvFile.createNewFile();
            // GB2312使正确读取分隔符","
            csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile, true), "GB2312"), 1024);
//            // 写入文件头部
//            for (Iterator propertyIterator = rowMapper.entrySet().iterator(); propertyIterator.hasNext();) {
//                java.util.Map.Entry propertyEntry = (java.util.Map.Entry) propertyIterator.next();
//                csvFileOutputStream.write("\"" + propertyEntry.getValue().toString() + "\"");
//                if (propertyIterator.hasNext()) {
//                    csvFileOutputStream.write(",");
//                }
//            }
            csvFileOutputStream.newLine();
            // 写入文件内容
            for (Iterator iterator = exportData.iterator(); iterator.hasNext();) { 
            	LinkedHashMap row = (LinkedHashMap) iterator.next(); 
               // log.debug("row:" + row);
                for (Iterator propertyIterator = row.entrySet().iterator(); propertyIterator.hasNext();) { 
                    java.util.Map.Entry propertyEntry = (java.util.Map.Entry) propertyIterator.next();
                    if(propertyEntry == null || propertyEntry.getValue() == null){
                    	 csvFileOutputStream.write("\"\""); 
                    }else{
                    	 csvFileOutputStream.write("\"" +  propertyEntry.getValue().toString() + "\""); 
                    }
                   if (propertyIterator.hasNext()) { 
                       csvFileOutputStream.write(","); 
                    } 
               } 
                if (iterator.hasNext()) { 
                   csvFileOutputStream.newLine(); 
                } 
           } 
            csvFileOutputStream.flush(); 
        } catch (Exception e) { 
        	log.error(Util.getTrace(e));
        } finally { 
           try { 
                csvFileOutputStream.close(); 
            } catch (IOException e) { 
            	log.error(Util.getTrace(e));
           } 
       } 
    }
	 
}
