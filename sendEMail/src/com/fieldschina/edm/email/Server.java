package com.fieldschina.edm.email;

/**
 * 服务器实体类
 * 
 * @author  LAVEN  E-mail:lavenwl@gmail.com
 * @company FieldsChina.IT.BI
 * @version 创建时间：2014-7-4 下午5:51:29
 */
public class Server {  
	
    private String host;//邮件服务器主机名  
    private String port;//邮件服务器端口号  
    private String username;//用户名  
    private String password;//密码  
  
    public Server(String host, String port, String username, String password) {  
        this.host = host;  
        this.port = port;  
        this.username = username;  
        this.password = password;  
    }  
  
    public String getHost() {  
        return host;  
    }  
  
    public void setHost(String host) {  
        this.host = host;  
    }  
  
    public String getPort() {  
        return port;  
    }  
  
    public void setPort(String port) {  
        this.port = port;  
    }  
  
  
  
    public String getUsername() {  
        return username;  
    }  
  
    public void setUsername(String username) {  
        this.username = username;  
    }  
  
    public String getPassword() {  
        return password;  
    }  
  
    public void setPassword(String password) {  
        this.password = password;  
    }  
}  