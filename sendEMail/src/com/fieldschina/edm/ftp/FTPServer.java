package com.fieldschina.edm.ftp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

import com.fieldschina.edm.util.Util;
/**
 * FTP上传下载操作类
 * 
 * @author  LAVEN  E-mail:lavenwl@gmail.com
 * @company FieldsChina.IT.BI
 * @version 创建时间：2014-7-10 下午6:07:48
 */
public class FTPServer {
	private static Logger log = Logger.getLogger(FTPServer.class);							//日志记录
	private static String URL = Util.getProperty("ftp.url");								//FTP服务器的URL
	private static int PORT = Integer.valueOf(Util.getProperty("ftp.port"));				//FTP服务器的端口
	private static String USERNAME = Util.getProperty("ftp.username");						//FTP服务器的用户名
	private static String PASSWORD = Util.getProperty("ftp.password");						//FTP服务器的密码
	private static String CNPATH = Util.getProperty("ftp.cnpath");							//FTP服务器上存储中文文件的目录
	private static String ENPATH = Util.getProperty("ftp.enpath");							//FTP服务器上存储英文文件的目录
	private static String DOWNLOADSERVERPATH = Util.getProperty("ftp.downloadserverpath");	//FTP服务器上的下载文件的目录
	private static String DOWNLOADCLIENTPATH = Util.getProperty("ftp.downloadclientpath");	//FTP服务器上下载的文件的本地存储目录
	/**
	* Description: 向FTP服务器上传文件
	* @param url FTP服务器hostname
	* @param port FTP服务器端口
	* @param username FTP登录账号
	* @param password FTP登录密码
	* @param path FTP服务器保存目录
	* @param filename 上传到FTP服务器上的文件名
	* @param input    输入流
	* @return 成功返回true，否则返回false
	*/
	public boolean uploadFile(String filename, InputStream input, String nationality) {
	   // 初始表示上传失败
	   boolean success = false;
	   // 创建FTPClient对象
	   FTPClient ftp = new FTPClient();
	   try {
		    int reply;
		    // 连接FTP服务器
		    // 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
		    ftp.connect(URL, PORT);
		    // 登录ftp
		    ftp.login(USERNAME, PASSWORD);
		    // 看返回的值是不是230，如果是，表示登陆成功
		    reply = ftp.getReplyCode();
		    // 以2开头的返回值就会为真
		    if (!FTPReply.isPositiveCompletion(reply)) {
			     ftp.disconnect();
			     return success;
		    }
		    String path = CNPATH;
		    if(nationality.equals("en")){
		    	path = ENPATH;
		    }
		    // 转到指定上传目录
		    ftp.changeWorkingDirectory(path);
		    // 将上传文件存储到指定目录
		    ftp.storeFile(Util.getCSVFileName2(filename), input);
		    // 关闭输入流
		    input.close();
		    // 退出ftp
		    ftp.logout();
		    // 表示上传成功
		    success = true;
	   } catch (IOException e) {
		   log.error(Util.getTrace(e));
	   } finally {
		    if (ftp.isConnected()) {
			     try {
			    	 ftp.disconnect();
			     } catch (IOException ioe) {
			    	 log.error(Util.getTrace(ioe));
			     }
		    }
	   }
	   return success;
	}
	/**
	 * Description: 从FTP服务器下载文件
	* @param url FTP服务器hostname
	* @param port   FTP服务器端口
	* @param username FTP登录账号
	* @param password   FTP登录密码
	* @param remotePath   FTP服务器上的相对路径
	* @param fileName 要下载的文件名
	* @param localPath 下载后保存到本地的路径
	* @return
	 */
	public boolean downFile(String remotePath, String fileName, String localPath) {
		   // 初始表示下载失败
		   boolean success = false;
		   // 创建FTPClient对象
		   FTPClient ftp = new FTPClient();
		   try {
			    int reply;
			    // 连接FTP服务器
			    // 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			    ftp.connect(URL, PORT);
			    // 登录ftp
			    ftp.login(USERNAME, PASSWORD);
			    reply = ftp.getReplyCode();
			    if (!FTPReply.isPositiveCompletion(reply)) {
				     ftp.disconnect();
				     return success;
			    }     // 转到指定下载目录
			    ftp.changeWorkingDirectory(remotePath);
			    // 列出该目录下所有文件
			    FTPFile[] fs = ftp.listFiles();
			    // 遍历所有文件，找到指定的文件
			    for (FTPFile ff : fs) {
				     if (ff.getName().equals(fileName)) {
				      // 根据绝对路径初始化文件
				      File localFile = new File(localPath + "/" + ff.getName());
				      // 输出流
				      OutputStream is = new FileOutputStream(localFile);
				      // 下载文件
				      ftp.retrieveFile(ff.getName(), is);
				      	is.close();
				     }
			    }
			    // 退出ftp
			    ftp.logout();
			    // 下载成功
			    success = true;
		   } catch (IOException e) {
			   log.error(Util.getTrace(e));
		   } finally {
			    if (ftp.isConnected()) {
				     try {
				    	 ftp.disconnect();
				     } catch (IOException ioe) {
				    	 log.error(Util.getTrace(ioe));
				     }
			    }
		   }
		   return success;
	}
	/**
	 * Description: 从FTP服务器下载文件们
	* @param url FTP服务器hostname
	* @param port   FTP服务器端口
	* @param username FTP登录账号
	* @param password   FTP登录密码
	* @param remotePath   FTP服务器上的相对路径
	* @param fileName 要下载的文件名
	* @param localPath 下载后保存到本地的路径
	* @return
	 */
	public boolean downFiles() {
		   // 初始表示下载失败
		   boolean success = false;
		   // 创建FTPClient对象
		   FTPClient ftp = new FTPClient();
		   try {
			    int reply;
			    // 连接FTP服务器
			    // 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			    ftp.connect(URL, PORT);
			    // 登录ftp
			    ftp.login(USERNAME, PASSWORD);
			    reply = ftp.getReplyCode();
			    if (!FTPReply.isPositiveCompletion(reply)) {
				     ftp.disconnect();
				     return success;
			    }     // 转到指定下载目录
			    System.out.println(ftp.changeWorkingDirectory(DOWNLOADSERVERPATH));
			    // 列出该目录下所有文件
			    FTPFile[] fs = ftp.listFiles();
			    // 遍历所有文件，找到指定的文件
			    for (FTPFile ff : fs) {
				     if (ff.getName().endsWith(".csv")) {
				      // 根据绝对路径初始化文件
			    	log.debug("得到路径下的文件：" + DOWNLOADCLIENTPATH + "//" + ff.getName());
			    	String path = System.getProperty("user.dir") + DOWNLOADCLIENTPATH;
				      File localFile = new File(path + "/" + ff.getName());
				      // 输出流
				      OutputStream is = new FileOutputStream(localFile);
				      // 下载文件
				      ftp.retrieveFile(ff.getName(), is);
				      	is.close();
				     }
			    }
			    // 退出ftp
			    ftp.logout();
			    // 下载成功
			    success = true;
		   } catch (IOException e) {
			   log.error(Util.getTrace(e));
		   } finally {
			    if (ftp.isConnected()) {
				     try {
				    	 ftp.disconnect();
				     } catch (IOException ioe) {
				    	 log.error(Util.getTrace(ioe));
				     }
			    }
		   }
		   return success;
	}
}
