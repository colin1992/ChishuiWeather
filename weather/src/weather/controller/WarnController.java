package weather.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import net.sf.json.JSONObject;
import weather.util.CallDataService;
import weather.util.DataServerUtil;

/**
 * 预警相关的controller
 * ***/
@Controller
public class WarnController {
	/***获取所有的监测站的基本信息，用于新增预警策略**/
	 @RequestMapping(value="warn/getStationInfo")
	 public void getStationInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		JSONObject responseData=CallDataService.getStationInfo();
		/**send response*/
		response.setContentType("text/xml;charset=utf-8");
		PrintWriter out=response.getWriter();
		out.println(responseData);
		out.flush();
		out.close();
	}
	 /***获取所有人员和人员组别的基本信息，用于新增预警策略**/
	 @RequestMapping(value="warn/getLiaisonsInfo")
	 public void getLiaisonsInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		JSONObject responseData=CallDataService.getLiaisonsInfo();
		/**send response*/
		response.setContentType("text/xml;charset=utf-8");
		PrintWriter out=response.getWriter();
		out.println(responseData);
		out.flush();
		out.close();
	}
		/***
	     * 增加预警策略
	     * 
	     * **/
	    @RequestMapping(value="warn/addWarnStrategy")
	    public void addWarnStrategy(HttpServletRequest request, HttpServletResponse response) throws IOException {
	    	
	    	JSONObject strategy=new JSONObject();
	    	String createUser="";
	    	try{
	    		Subject currentUser = SecurityUtils.getSubject();//获取当前用户
	    		createUser=currentUser.getPrincipal().toString();
	    	}catch(Exception e){
	    		
	    	}
	    	finally{
	    		strategy.put("createUser", createUser);
	    	}
	    	strategy.put("name",request.getParameter("strategyName"));//策略名称
	    	strategy.put("item",request.getParameter("warnType"));//预警要素
	    	strategy.put("param",request.getParameter("warnTypeEle"));//预警参数
	    	strategy.put("threshold",request.getParameter("warnThreshold"));//预警门限
	    	strategy.put("liaisons",request.getParameterValues("selectedPeople"));//告警联络人，为数组，每个元素为联络人的Id
	    	strategy.put("stations",request.getParameterValues("selectedStations"));//策略适用的站点，为数组，每个元素为站点的Id  	
	    	strategy.put("status",request.getParameter("isStart"));//启用状态，为boolean型
	    	strategy.put("infoWay",request.getParameter("infoWay"));//告警方式
	    	JSONObject responseData=CallDataService.addWarnStrategy(strategy);
			/**send response*/
			response.setContentType("text/xml;charset=utf-8");
			PrintWriter out=response.getWriter();
			out.println(responseData);
			out.flush();
			out.close();
		}
	    /***
	     * 修改预警策略
	     * **/
	    @RequestMapping(value="warn/alterWarnStrategy")
	    public void alterWarnStrategy(HttpServletRequest request, HttpServletResponse response) throws IOException {
	    	Long id=Long.parseLong(request.getParameter("id"));//策略ID
	    	JSONObject strategy=new JSONObject();
	    	strategy.put("name",request.getParameter("strategyName"));//策略名称
	    	strategy.put("item",request.getParameter("warnType"));//预警要素
	    	strategy.put("param",request.getParameter("warnTypeEle"));//预警参数
	    	strategy.put("infoWay",request.getParameter("infoWay"));//预警参数
	    	strategy.put("threshold",request.getParameter("warnThreshold"));//预警门限
	    	strategy.put("liaisons",request.getParameterValues("selectedPeople"));//告警联络人，为数组，每个元素为联络人的Id
	    	strategy.put("stations",request.getParameterValues("selectedStations"));//策略适用的站点，为数组，每个元素为站点的Id
	    	strategy.put("status",request.getParameter("isStart"));//启用状态，为boolean型
	    	JSONObject responseData=CallDataService.alterWarnStrategy(id,strategy);
			/**send response*/
			response.setContentType("text/xml;charset=utf-8");
			PrintWriter out=response.getWriter();
			out.println(responseData);
			out.flush();
			out.close();
		}
    /***
     * 删除预警策略
     * **/
    @RequestMapping(value="warn/delWarnStrategy")
    public void delWarnStrategy(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	Long id=Long.parseLong(request.getParameter("id"));//策略ID
		JSONObject responseData=CallDataService.delWarnStrategy(id);
		/**send response*/
		response.setContentType("text/xml;charset=utf-8");
		PrintWriter out=response.getWriter();
		out.println(responseData);
		out.flush();
		out.close();
	}
    /***
   	 *分页获取预警策略，包含用策略名称模糊查询预警策略，如果名字为空字符串，表示查询所有的预警策略
   	 *@return JSONObject 包括
   	 *     pages:总页数
   	 *     statusCode:boolean 标识查询状态
   	 *     warnStrategy：JSONArray 每个元素为一条预警策略的各参数信息 
   	 * ***/
	@RequestMapping(value="warn/getPaginationWarnStrategy")
   	public void getPaginationWarnStrategy(HttpServletRequest request, HttpServletResponse response) throws IOException {
   		int page=Integer.parseInt(request.getParameter("currentPage"));//获取当前页码数
   		int col=Integer.parseInt(request.getParameter("rowCount"));//表格列数
   		String name=request.getParameter("straName");//策略名称   		
   		name=(name==null)?"":name;
   		JSONObject responseData=CallDataService.getPaginationWarnStrategy(col,page,name);
   		/**send response*/
   		response.setContentType("text/xml;charset=utf-8");
   		PrintWriter out=response.getWriter();
   		out.println(responseData);
   		out.flush();
   		out.close();
   	} 
   	/***
   	 *查看预警策略详情
   	 * ***/
   	@RequestMapping(value="warn/getWarnStrategyDetial")
   	public void getWarnStrategyDetial(HttpServletRequest request, HttpServletResponse response) throws IOException {
   		Long id=Long.parseLong(request.getParameter("id"));
   		JSONObject responseData=CallDataService.getWarnStrategyDetial(id);
   		/**send response*/
   		response.setContentType("text/xml;charset=utf-8");
   		PrintWriter out=response.getWriter();
   		out.println(responseData);
   		out.flush();
   		out.close();
   	} 
   	/***
   	 *通过名称模糊查询预警策略
   	 *@return JSONObject 包括
   	 *      statusCode:boolean 标识查询状态
   	 *      warnStrategy：JSONArray 每个元素为一条预警策略的各参数信息 
   	 * ***/
    @RequestMapping(value="warn/getWarnStrategyByName")
   	public void getWarnStrategyByName(HttpServletRequest request, HttpServletResponse response) throws IOException {
   		String name=request.getParameter("name");
   		JSONObject responseData=CallDataService.getWarnStrategyByName(name);
   		/**send response*/
   		response.setContentType("text/xml;charset=utf-8");
   		PrintWriter out=response.getWriter();
   		out.println(responseData);
   		out.flush();
   		out.close();
   	}
	/****
	 * 分页获取预警信息***/
	@RequestMapping(value="warn/getPaginationWarns")
	public void getPaginationWarns(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Boolean status;
		if(request.getParameter("warnStatus").equals("null"))
			status = null;
		else
			status = Boolean.parseBoolean(request.getParameter("warnStatus"));//处理状态
		int col = Integer.parseInt(request.getParameter("rowCount"));//表格行数
		int page = Integer.parseInt(request.getParameter("currentPage"));//当前页码
		JSONObject returnJson = CallDataService.getPaginationWarns(status,col,page);
		response.setContentType("json");
		response.setCharacterEncoding("utf-8");
		response.getWriter().println(returnJson);
	}
    /***
     * 查看预警详情
     * **/
    @RequestMapping(value="warn/getWarnDetial")
    public void getWarnDetial(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	Long id=Long.parseLong(request.getParameter("id"));//告警标识
    	JSONObject responseData=CallDataService.getWarnDetial(id);
		/**send response*/
		response.setContentType("text/xml;charset=utf-8");
		PrintWriter out=response.getWriter();
		out.println(responseData);
		out.flush();
		out.close();
	}
    /***
     * 删除告警
     *@param id long:告警标识
     *@return JSONObject 包含以下字段：<br/>
     *   statusCode: boolean，false表示查询失败；true表示查找成功<br/>
     *   err:String 当statusCode为false时有效，表示失败的具体原因<br/> 
     * ***/
    @RequestMapping(value="warn/delWarn")
    public void delWarn(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	Long id=Long.parseLong(request.getParameter("id"));//告警标识
    	JSONObject responseData=CallDataService.delWarn(id);
		/**send response*/
		response.setContentType("text/xml;charset=utf-8");
		PrintWriter out=response.getWriter();
		out.println(responseData);
		out.flush();
		out.close();
	}
    /***
     * 发布告警
     *@param id long:告警标识
     *@return JSONObject 包含以下字段：<br/>
     *   statusCode: boolean，false表示发布失败；true表示发布成功<br/>
     *   err:String 当statusCode为false时有效，表示失败的具体原因<br/> 
     * ***/
    @RequestMapping(value="warn/releaseWarn")
    public void releaseWarn(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	JSONObject warn=new JSONObject();
    	warn.put("liaisons", request.getParameterValues("selectedPeople"));//通知人员的标识，为数组
    	warn.put("stations", request.getParameterValues("selectedStations"));//告警监测站的标识，为数组
    	warn.put("item", request.getParameter("warnType"));//告警要素，rainfall、temp
    	warn.put("param", request.getParameter("warnTypeEle"));//告警参数， 1h、3h、6h、12h、24h、low、ave、high
    	warn.put("infoWay", request.getParameter("infoWay"));//告警通知方式  由0、1组成的长度为3的字符串，分别表示短信、电话、邮件，为1表示采用，0表示不采用
    	warn.put("context", request.getParameter("warnContent"));//告警详细内容 
    	warn.put("title", request.getParameter("strategyName"));//告警标题  
    	warn.put("status", request.getParameter("status"));//告警状态，boolean
    	String account="操作人员";
    	try{
    		Subject currentUser = SecurityUtils.getSubject();//获取当前用户
     	    account=currentUser.getPrincipal().toString();
    	}catch(Exception e){
    		
    	}
    	warn.put("source", account);//告警状态，boolean
    	JSONObject responseData=CallDataService.releaseWarn(warn);
		/**send response*/
		response.setContentType("text/xml;charset=utf-8");
		PrintWriter out=response.getWriter();
		out.println(responseData);
		out.flush();
		out.close();
	}
    /***
	  * 解除告警
	  *@param id long:告警标识
	  *@return JSONObject 包含以下字段：<br/>
	  *  statusCode: boolean，false表示解除失败；true表示解除成功<br/>
	  *  err:String 当statusCode为false时有效，表示失败的具体原因<br/> 
     * @throws IOException 
	  * ***/
    @RequestMapping(value="warn/relieveWarn")
    public void relieveWarn(HttpServletRequest request, HttpServletResponse response) throws IOException{
    	Long id = Long.parseLong(request.getParameter("id"));
    	JSONObject responseData=CallDataService.relieveWarn(id);
		/**send response*/
		response.setContentType("text/xml;charset=utf-8");
		PrintWriter out=response.getWriter();
		out.println(responseData);
		out.flush();
		out.close();
	}
    @RequestMapping(value="warn/getWarnAudio")
    public void test(HttpServletRequest request, HttpServletResponse response) throws IOException{
    	String context=request.getParameter("context");
    	//将中文参数经过编码后传送，否则会出现乱码
    	String params="context="+URLEncoder.encode(context, "utf-8"); 
  		try {
  			URI uri = new URI("http",DataServerUtil.host+":"+DataServerUtil.port,"/dataServer/warn/getWarnAudio",params,"");
			URL url=uri.toURL();//重定向到数据库服务器，请求百度api进行语音转换
			response.sendRedirect(url.toString());
		  	     
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
