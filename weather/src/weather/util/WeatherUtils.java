package weather.util;

import java.io.File;
import java.util.Calendar;
public class WeatherUtils {
	/***
	 * 通过获取日历的方式获取年月日时分的时间形式
	 * @return String 形如2017年3月1日19时23分的时间
	 * **/
   public static String getCurrentTime(){
	   Calendar ca = Calendar.getInstance();                       
	   return ca.get(Calendar.YEAR) +"年"+ 
	   		  (ca.get(Calendar.MONTH)+1) +"月"+
	          ca.get(Calendar.DATE) + "日"+
	   		  ca.get(Calendar.HOUR_OF_DAY )+"时"+
	          ca.get(Calendar.MINUTE)+"分";       
   }
   /**
    * 删除文件，可以是文件或文件夹
    *
    * @param fileName
    *            要删除的文件名
    * @return 删除成功返回true，否则返回false
    */
   public static boolean delFile(String fileName) {
       File file = new File(fileName);
       if (!file.exists()) {
           return true;
       } else {
           if (file.isFile()){
               return file.delete();
           }else{
        	   return true;
           }
       }
   }
}
