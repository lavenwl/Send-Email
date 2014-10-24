package com.fieldschina.edm.util;

import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;

/**
 * 队列对象
 * 
 * @author  LAVEN  E-mail:lavenwl@gmail.com
 * @company FieldsChina.IT.BI
 * @version 创建时间：2014-6-20 下午2:53:39
 */
public class Queue {
	 private LinkedList list;	//队列实例
	 private String name;		//队列名字
	 /**
	  * 构造器
	  * 
	  * @param name	队列名
	  */
	 public Queue(String name){
		 if(list == null){
			  list=new LinkedList();
		 }
		 this.name = name;
	 }
	 /**
	  * 无参数构造器
	  */
	 public Queue(){
		 if(list == null){
			  list=new LinkedList();
		 }
	 }
	 /**
	  * 队列元素的个数
	  * 
	  * @return	现有队列的长度
	  */
	 public int size(){
		 return list.size();
	 }
	 /**
	  * 增加队列元素
	  * 
	  * @param obj	新队列元素对象
	  */
	 public void enqueue(Object obj){
		 list.addLast(obj); 
	 }
	 /**
	  * ????????
	  * 置换头元素
	  */
	 public void nextHeader(){
		 Object obj = new Object();
		 Object o = list.set(0, 0);
		 o = obj;
	 }
	 /**
	  * 清空队列
	  */
	 public void clear(){
		 list.clear();
	 }
	 
	/**
	 * 删除队列内的一个元素
	 * 
	 * @return
	 */
	 public Object dequeue(){
		 return list.removeFirst();
	 }
	 
	 /**
	  * 浏览对头元素
	  * 
	  * @return
	  */
	 public Object front(){
		 return list.peekFirst();
	 }

	 /**
	  * 判断队列是否为空
	  * 
	  * @return
	  */
	 public boolean isEmpty(){
		 return list.isEmpty();
	 }

	/**
	 *测试使用方法 
	 * 
	 * @param args
	 */
	 public static void main(String[] args) {
		 
	 }
}
