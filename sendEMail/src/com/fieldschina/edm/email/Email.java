package com.fieldschina.edm.email;

import java.io.File;  
import java.util.List;  
  
/**
 * Email实体类
 * 
 * @author  LAVEN  E-mail:lavenwl@gmail.com
 * @company FieldsChina.IT.BI
 * @version 创建时间：2014-7-4 下午5:49:03
 */
public class Email {  
    private String context;			//邮件正文  
    private String title;			//邮件标题  
    private List<File> attenchment;	//附件列表  
    private String addto;			//收件人地址  
    private String from;			//发件人地址  
  /**
   * 类构造器
   * 
   * @param context
   * @param title
   * @param from
   * @param addto
   */
    public Email(String context, String title, String from, String addto) {  
        this.context = context;  
        this.title = title;  
        this.from = from;  
        this.addto = addto;  
    }  
  /**
   * 类构造器
   * 
   * @param context
   * @param title
   * @param attenchment
   * @param addto
   * @param from
   */
    public Email(String context, String title, List<File> attenchment, String addto, String from) {  
        this.context = context;  
        this.title = title;  
        this.attenchment = attenchment;  
        this.addto = addto;  
        this.from = from;  
    }  
  
    public String getContext() {  
        return context;  
    }  
  
    public void setContext(String context) {  
        this.context = context;  
    }  
  
    public String getTitle() {  
        return title;  
    }  
  
    public void setTitle(String title) {  
        this.title = title;  
    }  
  
    public List<File> getAttenchment() {  
        return attenchment;  
    }  
  
    public void setAttenchment(List<File> attenchment) {  
        this.attenchment = attenchment;  
    }  
  
    public String getAddto() {  
        return addto;  
    }  
  
    public void setAddto(String addto) {  
        this.addto = addto;  
    }  
  
    public String getFrom() {  
        return from;  
    }  
  
    public void setFrom(String from) {  
        this.from = from;  
    }  
  
  
}  