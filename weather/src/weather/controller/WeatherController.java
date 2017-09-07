package weather.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import net.sf.json.JSONObject;
import weather.util.CallDataService;
import weather.util.CallExe;
import weather.util.NumConv;
import weather.util.WeatherUtils;

/***
 * 和产品制作相关的类，用于前后端交互
 * **/
@Controller
public class WeatherController {

	final static String DAT_PATH="product/dat/";//
	final static String IMAGE_PATH="product/image/";
	final static String BLACK_MAP_FILE="product/bln/black_chishui.bln";
	final static String MAIN_MAP_FILE="product/bln/mainArea_chishui.bln";
	final static String STATION_INFO_FILE="product/bln/stationInfo.bln";
	final static String COLOR_FILE="product/color/rainbow.lvl";
	final static String SURFER_MAKER_FILE="product/tools/SurferMaker.exe";
	/***
	 * 获取实况列表
	 * @throws IOException 
	 * **/
	@RequestMapping("weather/getRealTimeWeather")
	public void getRealTimeWeather(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String areaCode = request.getParameter("areaCode");
		int rowCount = Integer.parseInt(request.getParameter("rowCount"));
		int currentPage = Integer.parseInt(request.getParameter("currentPage"));
		JSONObject returnJson = CallDataService.getPageRealTimeWeather(areaCode,rowCount,currentPage);
		response.setContentType("json");
		response.setCharacterEncoding("utf-8");
		response.getWriter().println(returnJson);
	}
	/***
	 * 分页模糊查询监测站的实时监测信息
	 * @throws IOException 
	 * **/
	@RequestMapping("weather/getVagueStationWeather")
	public void getVagueStationWeather(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String areaCode =request.getParameter("areaCode");//区域代号
		String stationName=request.getParameter("stationName");//监测站名称
		int col =Integer.parseInt(request.getParameter("rowCount"));//表格行数
		int currentPage =Integer.parseInt(request.getParameter("currentPage"));//当前页码
		JSONObject returnJson = CallDataService.getVagueStationWeather(areaCode,stationName,col,currentPage);
		System.out.println(returnJson);
		response.setContentType("json");
		response.setCharacterEncoding("utf-8");
		response.getWriter().println(returnJson);
	}
	/**
	 * 获取指定站点的更加详细的实时信息
	 * 
	 * **/
	@RequestMapping("weather/getRealTimeWeatherDetail")
	public void getRealTimeWeatherDetail(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String stationCode = request.getParameter("stationCode");
		//需要的数据接口
		/**
		 * 根据站点code获得站点的实时信息
		 * @param stationCode
		 * @return 该站点的各种站点信息以及实时气象信息
		 * **/
		JSONObject returnJson =CallDataService.getDetialHourWeather(stationCode);
		response.setContentType("json");
		response.setCharacterEncoding("utf-8");
		response.getWriter().println(returnJson);
	}
	/**
	 * 图表页面，获取指定区域的所有站点的对应的气象信息
	 * 
	 * **/
	@RequestMapping("weather/getGraphListWeather")
	public void getGraphListWeather(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String areaCode = request.getParameter("areaCode");
		String element = request.getParameter("element");
		//需要调用的接口
		/**
		 * 输入区域代号和要素，返回这个区域下面所有的监测站对应的这个要素的信息
		 * @param areaCode(String)
		 * @param element(String)
		 * @return 返回一个json（返回对应区域所有站点的对应的element天气信息,以及对应的站点名称，status）
		 * **/
		JSONObject returnJson = CallDataService.getDistribution(areaCode, element);
		response.setContentType("json");
		response.setCharacterEncoding("utf-8");
		response.getWriter().println(returnJson);
	}

	 /***
	    * 分布图
	    * **/
		@SuppressWarnings("unchecked")
		@RequestMapping("product/makeGraph")
	   public void realTimeRainGraph(HttpServletRequest request,HttpServletResponse response) throws IOException{
		    String currentPath=request.getSession().getServletContext().getRealPath("");
		    String areaCode="520381";//赤水地区编号
		    String param=request.getParameter("param");//要素：1h、3h、6h、12h、24h 
		    String item=request.getParameter("item");
		    String imageTitle ="";//图片标题
		    switch(item){
		       case"rainfall":
		    	   imageTitle="雨量分布图";
		       break;
		       case"temp":imageTitle="温度分布图";
		       break;
			default:
				break;
		    }
		    JSONObject responseData=new JSONObject();//定义回传JSON
		    boolean statusCode=false;//定义回传状态码
			String stationFile=currentPath+STATION_INFO_FILE;
			String datFileName=NumConv.currentFormatTime()+param+".dat";
			String exePath=currentPath+SURFER_MAKER_FILE;//Surfer的完整路径
			String datFile =currentPath+DAT_PATH+ datFileName;//"D:/file/data.dat";//数据文件的完整路径  包括三列数据分别为经度、纬度、值 
	        String imagePath =currentPath+IMAGE_PATH;//存储生成图片的文件夹路径
	        String imageName = NumConv.currentFormatTime()+item+param;//生成图片的名称
	        String blnFileName = currentPath+MAIN_MAP_FILE;//地图文件
	        String colorName = currentPath+COLOR_FILE;//着色文件
	        String blackFileName =currentPath+BLACK_MAP_FILE;//底图文件，为分布图之前的地区行政图
	       
	        /***需要绘制分布图地区的经纬度范围***/
	        String lngMin="105.60";
	        String lngMax="106.25";
	        String latMin="28.2666";
	        String latMax="28.7666";
			String para[]={
							exePath,stationFile,datFile,imagePath,
							imageName,blnFileName,colorName,
							blackFileName,imageTitle,
							lngMin,lngMax,latMin,latMax//包含限定经纬度范围的调用方式
						  };
			//查询数据库服务器
			JSONObject queryResult=CallDataService.getWeatherForGraph(areaCode,item,param);
			List<Map<String,Object>> weathers=new ArrayList<>();
			if(queryResult.getBoolean("statusCode")){
				weathers=(List<Map<String, Object>>) queryResult.get("weather");
			}
			if(!weathers.isEmpty()){//获取数据成功，绘制图片
				//生成.dat文件
				statusCode=CallExe.createDat(currentPath+DAT_PATH,datFileName,weathers);
				if(statusCode){//生成文件成功
					statusCode=CallExe.runExec(para);
					class CheckThread extends Thread{
						public void run(){
							    Timer timer = new Timer(); 
							    timer.scheduleAtFixedRate(new TimerTask(){
									int waitCount=0;
									public void run() {  
										 File file =new File(imagePath+imageName+".png"); 
								    	 waitCount++;
								    	 if(file .exists()||waitCount>=50){
								    		 timer.cancel();
								    		 CheckThread.this.interrupt();//用中断的方式结束查询线程	
								    	 }
								    }  
								}, 1500,500); 
								try {
									Thread.sleep(54*500);
								} catch (Exception e) {
									// TODO Auto-generated catch block
								}
						}
					}
					CheckThread checkThread=new CheckThread();
					checkThread.start();
					try {
						checkThread.join();
					} catch (Exception e) {
						e.printStackTrace();
						// TODO Auto-generated catch block
					}finally{
						File file =new File(imagePath+imageName+".png");
						statusCode=file .exists();
					}
					if(statusCode){
						try {
							Thread.sleep(500);
						} catch (Exception e) {
							e.printStackTrace();
							// TODO Auto-generated catch block
						}
						responseData.put("image", "product/image/"+imageName+".png");
					}else{
						responseData.put("err","生成分布图失败，请稍后重试。");
					}
				}
				if(!statusCode){
					responseData.put("err","生成分布图失败，请稍后重试。");
				}
			}else{
				responseData.put("err","生成分布图失败，"+queryResult.getString("err"));
			}
			WeatherUtils.delFile(datFile);
			WeatherUtils.delFile(imagePath+imageName+".grd");
			WeatherUtils.delFile(imagePath+imageName+".png.gsr2");
			String forward=request.getParameter("forward");//是否跳转，跳转的话说明当前处理只用于生成图片，其他任务交给后续servlet
			if(forward!=null){
				try {
					request.setAttribute("image", "product/image/"+imageName+".png");
					request.getRequestDispatcher("/"+forward).forward(request,response);
				} catch (ServletException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			response.setContentType("application/json;charset=UTF-8");
			responseData.put("statusCode", statusCode);
			response.getWriter().println(responseData);
	   }
}
