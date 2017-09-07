package weather.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;

import net.sf.json.JSONObject;
/***
 *  
 * 设计的方案是一台服务器用于连接内网、专程用于与CIMISS数据库同步、实现物理隔离
 * 该类用于访问数据库服务器：采用方式为rest
 * 创建时间：2017/3/14
 * @author 心之&所系
 * ***/
public class DataServerUtil {

	  public  static  String host;//数据库服务器地址
	  public  static  int port;//数据库服务器端口
	  private   String path="/dataServer/";//路径
	  private final int timeoutInMilliSeconds=1000*2 ;//连接超时时间 2 seconds
	  //public static String dataServer;
	  static{
		    InputStream in=null;
		  	try {  
		  		//读取数据库服务器相关配置信息
		  		in =DataServerUtil.class.getResourceAsStream("/properties/dataServer.properties" );
	  		    Properties prop = new Properties();  
	            prop.load(in); //以key-value的方式载入配置文件
	                //一定要在修改值之前关闭fis  
	            in.close(); 
	            Enumeration<?> keys = prop.propertyNames();  
	            while (keys.hasMoreElements()) {  
	               String key = (String)keys.nextElement();  
	               String value = prop.getProperty(key); 
	               if(key.contains("port")){
	                   port=Integer.parseInt(value);
	               }
	               else if(key.contains("url")){
	                   host=value;
	               }
	               prop.remove(key);
	           } 
	        } catch (Exception e) {  
	            System.err.println("exception occured when try to  reading Server Configuration");
	            e.printStackTrace();
	        }  
		  	finally{//确保文件输入流正常关闭
		  		//dataServer=host+":"+port+path;
		  			if(in!=null){
			  			try {
							in.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		  			}
		  	}
	  }
	  /***
	   * 构造函数一
	   * **/
	  public DataServerUtil(String host,int port){
		  DataServerUtil.host=host;
		  DataServerUtil.port=port;
	  }  
	  public DataServerUtil(String url){
		  path+=url;
	  }
	 
	  /**
	   *采用rest方式连接数据库服务器获取数据
	   *@param params String 包含请求参数的URL
	   *@return JSONObject 数据库服务器返回的数据，JSON对象，包含状态码 statusCode,为false表明查找失败
	   */
	  public JSONObject getQueryData(String params){
	    JSONObject response=new JSONObject();
	    try {
		      URI uri = new URI("http",host+":"+port,path,params,"");
		      URL url=uri.toURL();
		      HttpURLConnection con = (HttpURLConnection) url.openConnection();   
		      con.setRequestProperty("Content-type", "text/html");
		      con.setRequestProperty("Accept-Charset", "utf-8");
		      con.setRequestProperty("contentType", "utf-8");
		      //设置连接属性   
		      con.setDoOutput(true);// 使用 URL 连接进行输出   
		      con.setDoInput(true);// 使用 URL 连接进行输入   
		      con.setUseCaches(false);// 忽略缓存   
		      con.setRequestMethod("POST");// 设置URL请求方法 
		      con.setConnectTimeout(this.timeoutInMilliSeconds); 
		      BufferedReader reader=null;
		      reader = new BufferedReader(new InputStreamReader(con.getInputStream(),"utf-8"));
		      StringBuilder retStr=new StringBuilder();
		      String line = reader.readLine();
		      while (line!=null) {
		        retStr.append(line).append("\r\n");
		        line = reader.readLine();
		      }
		      reader.close();
		    //连接成功，回传数据为数据库服务器的回复数据，注意，只能用String转JSONObject而不能用StringBuilder
		      response=JSONObject.fromObject(retStr.toString());
	     } catch (ConnectException e) {
	    	    response.put("statusCode",false);
	    	    response.put("err","连接数据服务器超时，请稍后重试，若一直出现该故障，请检查数据服务器状态是否正常。");
		        e.printStackTrace();
	     }
	    catch(Exception e1){
	    	 response.put("statusCode",false);
	    	 response.put("err","数据库服务器回复异常，请稍后重试，若一直出现该故障，请检查数据服务器状态是否正常。");
	    	 e1.printStackTrace();
	    }
	    return response;
	  }

}
