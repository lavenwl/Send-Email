package com.fieldschina.edm.email;

    import org.apache.commons.lang.StringUtils;  
    import org.apache.log4j.LogManager;  
    import org.apache.log4j.Logger;  

import com.fieldschina.edm.util.Util;

    import javax.activation.DataHandler;  
    import javax.activation.FileDataSource;  
    import javax.mail.*;  
    import javax.mail.internet.*;  
    import java.io.UnsupportedEncodingException;  
    import java.util.HashMap;  
    import java.util.Map;  
import java.util.Properties;  
    /**
     * 发送邮件逻辑类
     * 
     * @author  LAVEN  E-mail:lavenwl@gmail.com
     * @company FieldsChina.IT.BI
     * @version 创建时间：2014-7-4 下午5:50:44
     */
    public class SendMail {  
    	
        private static final Logger logger = LogManager.getLogger(SendMail.class);  //日志记录
        
        public String send(Server server,Email email){  
            String result=null;  
            Session session=null;  
            Properties prop=System.getProperties();  
            prop.put("mail.smtp.host",server.getHost());  
            prop.put("mail.smtp.sendpartial","true");  
            prop.put("mail.smtp.port",server.getPort());  
            if(StringUtils.isBlank(server.getUsername())){					//是否是匿名邮件服务器  
                 session = Session.getDefaultInstance(prop, null);  
            }else{  
                 prop.put("mail.smtp.auth","true");  
                 EmailAuhor auhor=new EmailAuhor(server.getUsername(),server.getPassword());  
                 session=Session.getInstance(prop,auhor);  
            }  
      
            try {  
                MimeMessage message=new MimeMessage(session);  				//创建邮件  
                message.setFrom(new InternetAddress(email.getFrom()));  	//设置发件人地址  
                InternetAddress[] toAddr=InternetAddress.parse(email.getAddto());  
                message.addRecipients(Message.RecipientType.TO, toAddr);  
                message.setSubject(email.getTitle());  
                Multipart multipart = new MimeMultipart();   				//设置发送内容  
                MimeBodyPart contentPart= new MimeBodyPart();  
                contentPart.setText(email.getContext());  
                multipart.addBodyPart(contentPart);  
                //设置附件  
                if(email.getAttenchment()!=null && email.getAttenchment().size()>0){  
                 for(int i=0;i<email.getAttenchment().size();i++){  
                     MimeBodyPart attachmentPart=new MimeBodyPart();  
                     FileDataSource source=new FileDataSource(email.getAttenchment().get(i));  
                     attachmentPart.setDataHandler(new DataHandler(source));  
                     attachmentPart.setFileName(MimeUtility.encodeWord(email.getAttenchment().get(i).getName(), "gb2312", null));  
                     multipart.addBodyPart(attachmentPart);  
                 }  
                }  
                message.setContent(multipart);  
                //登录服务器  
                if(StringUtils.isBlank(server.getUsername())){  
                    Transport.send(message);  
                }else {  
                    Transport transport = session.getTransport("smtp");  
                    transport.connect();  
                    transport.sendMessage(message,message.getAllRecipients());  
                    transport.close();  
                }  
                logger.info("发送成功" + email.getFrom());  
                result="发送成功";  
            } catch (MessagingException mex) {  
            	logger.error(Util.getTrace(mex));
                result="邮箱服务器发送错误！";  
                if(mex instanceof AuthenticationFailedException){  
                    result="用户名或密码错误!";  
                }  
                mex.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.  
            } catch (UnsupportedEncodingException e) {  
            	logger.error(Util.getTrace(e));
                result="系统发生异常";  
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.  
            }  
            return result;  
      
        }  
    }  