package com.winterchen.utilCommon;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * 
 * 基础工具类
 * @author lipengfei
 *
 */
public class BaseUtils {
	
	private static final String REGEX_MOBILE = "(134[0-8]\\d{7})" + "|(" + "((13([0-3]|[5-9]))" +"|149" +"|15([0-3]|[5-9])" + "|166" +"|17(3|[5-8])" + "|18[0-9]" + "|19[8-9]" + ")" + "\\d{8}" +")";
	
	/**
     * 正则表达式：验证汉字
     */
    public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5],{0,}$";
    
	 /** 
	  * 验证手机号
     * 大陆号码或香港号码均可 
     */  
    public static boolean isPhoneLegal(String str)throws PatternSyntaxException {  
        return isChinaPhoneLegal(str) || isHKPhoneLegal(str)||isZJPhone(str)||isFixedPhone(str);  
    }  
  
    /** 
     * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数 
     * 此方法中前三位格式有： 
     * 13+任意数 
     * 15+除4的任意数 
     * 18+除1和4的任意数 
     * 17+除9的任意数 
     * 147 
     */  
    public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {  
        Pattern p = Pattern.compile(REGEX_MOBILE);  
        Matcher m = p.matcher(str);  
        return m.matches();  
    }  
  
    /** 
     * 香港手机号码8位数，5|6|8|9开头+7位任意数 
     */  
    public static boolean isHKPhoneLegal(String str)throws PatternSyntaxException {  
        String regExp = "^(5|6|8|9)\\d{7}$";  
        Pattern p = Pattern.compile(regExp);  
        Matcher m = p.matcher(str);  
        return m.matches();  
    }  
    
    /**   * 电话号码验证   * 
     * @author ：lipengfei
     * 
     * @param  str   
     * @return 验证通过返回true   
     * 
     */  
    public static boolean isZJPhone(String str) throws PatternSyntaxException  {
    	Pattern p1 = null, p2 = null;      
    	Matcher m = null;      
    	boolean b = false;      
    	p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$");  
    	// 验证带区号的      
    	p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");         
    	// 验证没有区号的      
    	if (str.length() > 9) {
    		m = p1.matcher(str);         
    		b = m.matches();      
    		} else {          
    			m = p2.matcher(str);         
    			b = m.matches();      
    		}      
    	return b;  
    }
    
    /**	 
     * * 区号+座机号码+分机号码	 
     * * @param fixedPhone	 
     * * @return
     * 	 */
    public static boolean isFixedPhone(String fixedPhone){
    	String reg="(?:(\\(\\+?86\\))(0[0-9]{2,3}\\-?)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?)|" +
    				"(?:(86-?)?(0[0-9]{2,3}\\-?)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?)";		
    	return Pattern.matches(reg, fixedPhone);
    }

    /**
     * 验证邮箱格式
     * @param email
     * @return
     */
    public static boolean checkEmail(String email){
    	// 验证邮箱的正则表达式 
    	String format = "[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?";
	    if (email.matches(format)){ 
	    	 return true;// 邮箱名合法，返回true 
	    }else{
	    	  return false;// 邮箱名不合法，返回false
	    }
    } 
    
    /**
     * 校验汉字
     * 
     * @param chinese
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isChinese(String chinese) {
        return Pattern.matches(REGEX_CHINESE, chinese);
    }
    
    /**
     * 验证邮箱格式
     * @param email
     * @return
     */
    public static boolean isEmail(String email){
    	// 验证邮箱的正则表达式 
    	String format = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    	if (email.matches(format)){ 
    		return true;// 邮箱名合法，返回true 
    	}else{
    		return false;// 邮箱名不合法，返回false
    	}
    } 
    
    /**
     * 验证图片格式
     * @param str
     * @return
     * 
     */
    public static boolean checkImg(String str){
    	String regExp = ".+(.JPEG|.jpeg|.JPG|.jpg|.png|.gif|.psd|.dxf|.raw|.wmf|.tiff)$";
    	Pattern p = Pattern.compile(regExp);  
        Matcher m = p.matcher(str);  
        return m.matches();  

    }
    
    /**
     *
     * @param time     厂家签约时间
     * @return M****
     */
    public static String getVendorno(String no,Date time){
    	String a ="M";
    	String c=DateUtils.getCurrentDate2(time).substring(2,8);
    	return a+no+c;
    }

    /**
     * 自动生成服务类型编号
     * @param no
     * @param time
     * @return
     */
    public static String getServiceno(String no,Date time){
        String s = "S";
        String c = DateUtils.getCurrentDate2(time).substring(2,8);
        return s+c+no;
    }
    
    /**
     * 自动生成营养师平台商品类型编号
     * @return T**
     */
    public static String getTypeNo(String no){
    	String str ="T";
    	return str+no;
    }
    
    /**
     * 自动生成营养师平台商品编号
     * @param time 上传时间
     * @return
     */
    public static String getProductNo(String no,String vendorno,String typeno,Date time){
    	String a = "G";
    	String b = vendorno.substring(1,5);
    	String c = typeno.substring(1,3);
    	String d =DateUtils.getCurrentDate2(time).substring(2,8);
    	return a+b+c+no+d;
    }
    
    /**
     *
     * @param time    购买时间
     * @return
     */
    public static String getServiceNo(String numberno,String productno,Date time){
    	String a ="S";
    	String b =productno.substring(1,10);
    	String c =DateUtils.getCurrentDate2(time).substring(2,8);
    	return a+b+c+numberno;
    }
    
    /*
      * 判断是否为整数 
      * @param str 传入的字符串 
      * @return 是整数返回true,否则返回false 
    */

      public static boolean isInteger(String str) {  
            Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");  
            return pattern.matcher(str).matches();  
      }
      
      /**     
       * 判断是否是手机号    
       * @param tel 手机号 
       * @return boolean true:是  false:否    
       */    
      public static boolean isMobile(String tel) {
    	  return Pattern.matches(REGEX_MOBILE, tel);
      }
}
