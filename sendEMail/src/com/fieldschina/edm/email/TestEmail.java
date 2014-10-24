package com.fieldschina.edm.email;

import java.io.File;  
import java.util.ArrayList;  
import java.util.List;  
import java.io.File;  
import java.util.ArrayList;  
import java.util.List;  
  
/**
 * 测试使用类
 *  
 * @author  LAVEN  E-mail:lavenwl@gmail.com
 * @company FieldsChina.IT.BI
 * @version 创建时间：2014-7-4 下午5:52:17
 */
public class TestEmail {  
    public static void main(String[] args){  
        List list=new ArrayList();  
        list.add(new File("c:\\WEI_SHI_201312_BillingFcstReportExport.xls"));  
        Server server=new Server("smtp.qq.com","25","laven.wang@sh.fieldschina.com","laven123"); 
        Email email=new Email("测试邮件改版","测试邮件改版","laven.wang@sh.fieldschina.com","543410506@qq.com,lavenwl@gmail.com");  
        email.setAttenchment(list);
        SendMail sendemail=new SendMail();  
        String result = sendemail.send(server,email);  
        System.out.println(result);  
    }  
}