package weather.controller;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import net.sf.json.JSONObject;
import weather.util.CallDataService;

@Controller
public class ResourceController {
 
    /****************监测站相关***************/
    /****
     * 获取所有监测站点的信息用于绘制GIS
     * **/
    @RequestMapping("station/getStationsForGIS")
    public void getStationsForGIS(HttpServletRequest request,HttpServletResponse response) throws IOException{
    	JSONObject responseData=CallDataService.getStationsForGIS();
    	response.setContentType("text/xml;charset=utf-8");
    	PrintWriter out=response.getWriter();
		out.println(responseData);
		out.flush();
		out.close();
    }   
    /**增加监测站*/
	@RequestMapping("station/addStation")// 
    public void addStation(HttpServletRequest request,HttpServletResponse response) throws IOException{
		JSONObject station=new JSONObject();//用于存放前端下发的个监测站参数
		station.put("name", request.getParameter("staName"));//监测站名称
		station.put("code", request.getParameter("staCode"));//监测站编号
		station.put("type", request.getParameter("staType"));//监测站类型
		station.put("cnty", request.getParameter("staCountry"));//监测站所在乡镇
		station.put("description", request.getParameter("staDesc"));//监测站描述
		station.put("areaCode", request.getParameter("areaCode"));//地区编号
		station.put("lat", request.getParameter("staLat"));//监测站纬度
		station.put("lng", request.getParameter("staLng"));//监测站经度
		JSONObject responseData=CallDataService.addStation(station);
		response.setContentType("text/xml;charset=utf-8");
    	PrintWriter out=response.getWriter();
		out.println(responseData);
		out.flush();
		out.close();
	}   
   /**修改监测站
     * */
	@RequestMapping("station/modifyStation")//
    public void modifyStation(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String code=request.getParameter("staCode");//监测站代号
		JSONObject station=new JSONObject();//用于存放前端下发的个监测站参数
		station.put("name", request.getParameter("staName"));//监测站名称
		station.put("type", request.getParameter("staType"));//监测站类型
		station.put("description", request.getParameter("staDesc"));//监测站描述
		station.put("lat", request.getParameter("staLat"));//监测站纬度
		station.put("lng", request.getParameter("staLng"));//监测站经度
		JSONObject responseData=CallDataService.modifyStation(code,station);
    	response.setContentType("text/xml;charset=utf-8");
    	PrintWriter out=response.getWriter();
		out.println(responseData);
		out.flush();
		out.close();
    }
	/**
	 * 删除监测站*/
	@RequestMapping("station/delStation")
    public void deleteStation(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String code=request.getParameter("infoId");//监测站编号
		JSONObject responseData=CallDataService.delStation(code);
		response.setContentType("text/xml;charset=utf-8");
		PrintWriter out=response.getWriter();
		out.println(responseData);
		out.flush();
		out.close();
	}
	/**
	 * 查看监测站详情
	 * */
	@RequestMapping("station/checkStationDetial")
    public void checkStationDetial(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String code=request.getParameter("infoCode");//监测站编号
		JSONObject responseData=CallDataService.checkStationDetial(code);
		response.setContentType("text/xml;charset=utf-8");
		PrintWriter out=response.getWriter();
		out.println(responseData);
		out.flush();
		out.close();
	}  	
	/**
	 * 分页获取监测站概要信息，用于基础信息列表
	 * */
	@RequestMapping("station/getStationsOutline")
    public void getStationsOutline(HttpServletRequest request,HttpServletResponse response) throws IOException{
		int col=Integer.parseInt(request.getParameter("rowCount"));//表格行数
		int currentPage=Integer.parseInt(request.getParameter("currentPage"));//当前页码数
		String name=request.getParameter("infoSearch");//监测站名称
		name=name==null?"":name;
		JSONObject responseData=CallDataService.getStationsOutline(col,currentPage,name);
		response.setContentType("text/xml;charset=utf-8");
		PrintWriter out=response.getWriter();
		out.println(responseData);
		out.flush();
		out.close();
	} 
/********易受灾站点*******/
	
	/**
	 * 新增易受灾点
	 * **/
	@RequestMapping("vulner/addVulner")
	public void addVulner(HttpServletRequest request,HttpServletResponse response) throws IOException{
		JSONObject vulner=new JSONObject();
		/***封装易受灾点信息，具体信息由前端下发***/
		vulner.put("name", request.getParameter("vName"));//易受灾地名称
		vulner.put("cnty", request.getParameter("vPosition"));//乡镇
		vulner.put("lat", request.getParameter("vLat"));//纬度
		vulner.put("lng", request.getParameter("vLng"));//经度
		vulner.put("ecpName", request.getParameter("vEcp"));//联系人
		vulner.put("ecpPhone", request.getParameter("vECPPhone"));//联系人手机号
		vulner.put("ecpEmail", request.getParameter("vECPEmail"));//紧急联系人邮件
		vulner.put("resPop", request.getParameter("vPeopleNum"));//常住人口
		vulner.put("type", request.getParameter("vType"));//易受灾类型
		vulner.put("evaRoute", request.getParameter("vEsLine"));//疏散路线
		vulner.put("description", request.getParameter("description"));//描述
		JSONObject responseData=CallDataService.addVulner(vulner);	
		response.setContentType("text/xml;charset=utf-8");
		PrintWriter out=response.getWriter();
		out.println(responseData);
		out.flush();
		out.close();
	} 
	/***
	 * 修改易受灾点
	 * **/
	@RequestMapping("vulner/alterVulner")
	public void alterVulner(HttpServletRequest request,HttpServletResponse response) throws IOException{
		//易受灾点ID
		long id=Long.parseLong(request.getParameter("id"));
		JSONObject vulner=new JSONObject();
		/***封装易受灾点信息，具体信息由前端下发***/
		vulner.put("name", request.getParameter("vName"));//易受灾地名称
		vulner.put("cnty", request.getParameter("vPosition"));//乡镇
		vulner.put("lat", request.getParameter("vLat"));//纬度
		vulner.put("lng", request.getParameter("vLng"));//经度
		vulner.put("ecpName", request.getParameter("vEcp"));//联系人
		vulner.put("ecpPhone", request.getParameter("vECPPhone"));//联系人手机号
		vulner.put("ecpEmail", request.getParameter("vECPEmail"));//紧急联系人邮件
		vulner.put("resPop", request.getParameter("vPeopleNum"));//常住人口
		vulner.put("type", request.getParameter("vType"));//易受灾类型
		vulner.put("evaRoute", request.getParameter("vEsLine"));//疏散路线
		vulner.put("description", request.getParameter("description"));
		JSONObject responseData=CallDataService.modifyVulner(id,vulner);	
		response.setContentType("text/xml;charset=utf-8");
		PrintWriter out=response.getWriter();
		out.println(responseData);
		out.flush();
		out.close();
	} 
	/***
	 * 删除易受灾点
	 * **/
	@RequestMapping("vulner/delVulner")
	public void delVulner(HttpServletRequest request,HttpServletResponse response) throws IOException{
		//易受灾点ID
		long id=Long.parseLong(request.getParameter("infoId"));
		JSONObject responseData=CallDataService.delVulner(id);	
		response.setContentType("text/xml;charset=utf-8");
		PrintWriter out=response.getWriter();
		out.println(responseData);
		out.flush();
		out.close();
	} 
/**获取所有容易受灾的站点*/
	@RequestMapping("vulner/getAllVulners")
	public void getAllVulners(HttpServletRequest request,HttpServletResponse response) throws IOException{
		JSONObject responseData=CallDataService.getAllVulners();
		response.setContentType("text/xml;charset=utf-8");
		PrintWriter out=response.getWriter();
		out.println(responseData);
		out.flush();
		out.close();
	} 
	/**查看易受灾点详情*/
	@RequestMapping("vulner/checkVulnerDetial")
	public void checkVulnerDetial(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Long id=Long.parseLong(request.getParameter("id"));
		JSONObject responseData=CallDataService.checkVulnerDetial(id);
		response.setContentType("text/xml;charset=utf-8");
		PrintWriter out=response.getWriter();
		out.println(responseData);
		out.flush();
		out.close();
	} 
	/***
	 * 分页模糊查找易受灾点信息
	 * **/
	@RequestMapping("vulner/getPaginationVulner")
	public void getPaginationVulner(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String title = request.getParameter("infoSearch");
		int col = Integer.parseInt(request.getParameter("rowCount"));
		int page = Integer.parseInt(request.getParameter("currentPage"));
		JSONObject responseData=CallDataService.getPaginationVulner(title,col,page);
		response.setContentType("text/xml;charset=utf-8");
		PrintWriter out=response.getWriter();
		out.println(responseData);
		out.flush();
		out.close();
	} 
}