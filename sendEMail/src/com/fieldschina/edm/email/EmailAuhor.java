package com.fieldschina.edm.email;
  
import javax.mail.Authenticator;  
import javax.mail.PasswordAuthentication;  
    /**
     * Email验证数据实体类
     * 
     * @author  LAVEN  E-mail:lavenwl@gmail.com
     * @company FieldsChina.IT.BI
     * @version 创建时间：2014-7-4 下午5:50:03
     */
    public class EmailAuhor extends  Authenticator {  
        private String username;  
        private String password;  
      
        public EmailAuhor(String username, String password) {  
            this.username = username;  
            this.password = password;  
        }  
        public EmailAuhor(){  
            this.username = username;  
            this.password = password;  
        }  
        public PasswordAuthentication getPasswordAuthentication(){  
          return new PasswordAuthentication(username, password);  
        }  
      
    }  