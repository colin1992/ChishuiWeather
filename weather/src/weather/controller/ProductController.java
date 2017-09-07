package weather.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import net.sf.json.JSONObject;
import weather.util.CallDataService;
import weather.util.DocCreater;
import weather.util.WeatherUtils;

@Controller
public class ProductController {
	/**
	 * 制作气象产品
	 * **/
	@RequestMapping("product/makeProduct")
	public void makeProduct(HttpServletRequest request,HttpServletResponse response) throws IOException{
		JSONObject product=new JSONObject();
		product.put("title", request.getParameter("title"));//标题
		product.put("context", request.getParameter("context").replace("product/image/20170502173421temp6h_low.png", request.getParameter("image")).getBytes());//内容
		product.put("type", request.getParameter("type"));//类型
		product.put("reviewer", request.getParameter("reviewer"));
		product.put("image", request.getParameter("image"));
		String author="";
    	try{
    		Subject currentUser = SecurityUtils.getSubject();//获取当前用户
    		author=currentUser.getPrincipal().toString();
    	}catch(Exception e){
    		
    	}
    	product.put("author", author);//拟稿人
		JSONObject responseData=CallDataService.makeProduct(product);
		response.setContentType("text/xml;charset=utf-8");
		PrintWriter out=response.getWriter();
		out.println(responseData);
		out.flush();
		out.close();
	} 
	/***
	 * 删除产品信息
	 * **/
	@RequestMapping("product/delProduct")
	public void delProduct(HttpServletRequest request,HttpServletResponse response) throws IOException{
		long id=Long.parseLong(request.getParameter("id"));//要删除的产品的标识
		JSONObject product=CallDataService.getProductDetial(id);
		if(product.getBoolean("statusCode")){
			String currentPath=request.getSession().getServletContext().getRealPath("")+"product\\doc";
			WeatherUtils.delFile(currentPath+"\\"+product.getJSONObject("product").getString("title")+".doc");
		}
		JSONObject responseData=CallDataService.delProduct(id);	
		response.setContentType("text/xml;charset=utf-8");
		PrintWriter out=response.getWriter();
		out.println(responseData);
		out.flush();
		out.close();
	} 
	/***
	 * 分页模糊查询cp信息，查询参数为类型type，为空字符串时表示全部
	 * **/
	@RequestMapping("product/getPaginationProduct")
	public void getPaginationProduct(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String type=request.getParameter("typeName");//产品类型，查询全部时设置为""
		int col=Integer.parseInt(request.getParameter("rowCount"));//表格列数
		int page=Integer.parseInt(request.getParameter("currentPage"));//当前页码
		JSONObject responseData=CallDataService.getPaginationProduct(type,col,page);	
		response.setContentType("text/xml;charset=utf-8");
		PrintWriter out=response.getWriter();
		out.println(responseData);
		out.flush();
		out.close();
	}
	/***
	 * 查看产品信息详情
	 * **/
	@RequestMapping("product/getProductDetial")
	public void getProductDetial(HttpServletRequest request,HttpServletResponse response) throws IOException{
		long id=Long.parseLong(request.getParameter("id"));//要查看的产品信息的标识
		JSONObject responseData=CallDataService.getProductDetial(id);
		response.setContentType("text/xml;charset=utf-8");
		PrintWriter out=response.getWriter();
		out.println(responseData);
		out.flush();
		out.close();
	} 
	/***
	 * 生成word文档
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value="product/productMake")
	public void productMake(HttpServletRequest request,HttpServletResponse response) throws IOException{
		 DocCreater createDoc = new DocCreater();
		 String imagePath=(String) request.getAttribute("image");
		 createDoc.setTemplatePath("../../templates");
		 createDoc.setTemplateName("productemplate.ftl");
		 String currentPath= request.getServletContext().getResource("/product/doc").getPath().replaceAll("%20", " ");
		 request.getServletContext().getResource("/product/doc").getPath();
		 createDoc.setFilePath(currentPath);
		 System.out.println("doc位置："+currentPath);
		 String fileName=request.getParameter("productTitle")+".doc";
		 createDoc.setFileName(fileName);
    	 Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
    	 String author="";
     	 try{
     		Subject currentUser = SecurityUtils.getSubject();//获取当前用户
     		author=currentUser.getPrincipal().toString();
     	 }catch(Exception e){
     		
     	 }
     	 String index= DocCreater.index<10?"0"+DocCreater.index:String.valueOf(DocCreater.index);
         dataMap.put("proIndex",index);
         dataMap.put("author",author);
         dataMap.put("signer", request.getParameter("reviewer"));
         dataMap.put("time", WeatherUtils.getCurrentTime());
         dataMap.put("temGraph", DocCreater.getImageStr(request.getSession().getServletContext().getRealPath("/")+imagePath));
         String [] rainInfo = request.getParameterValues("rainInfo");
         String [] temInfo = request.getParameterValues("temInfo");
         if(request.getParameter("type").equals("temp")){
        	 dataMap.put("type", "温度");
        	 for(int i =1;i<temInfo.length+1;i++){
            	 dataMap.put("country"+i, temInfo[i-1]);
             }
        	 for(int i=temInfo.length+1;i<25;i++){
        		 dataMap.put("country"+i, " ");
        	 }
         }        	 
         else{
        	 dataMap.put("type", "雨量");
        	 for(int i =1;i<rainInfo.length+1;i++){
            	 dataMap.put("country"+i, rainInfo[i-1]);
             }
        	 for(int i=rainInfo.length+1;i<25;i++){
        		 dataMap.put("country"+i, " ");
        	 }
         }       	          
		 boolean status=createDoc.createWord(dataMap);
		 if(status){
			 DocCreater.index++;
		 }
		 JSONObject responseData=new JSONObject();
		 responseData.put("statusCode", status);
		 responseData.put("image", imagePath);
		 response.setContentType("text/xml;charset=utf-8");
		 PrintWriter out=response.getWriter();
		 out.println(responseData);
		 out.flush();
		 out.close();
	}
	/***
	 * 生成word文档(预警快报)
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value="product/productMakeForWarn")
	public void productMakeForWarn(HttpServletRequest request,HttpServletResponse response) throws IOException{
		 DocCreater createDoc = new DocCreater();
		 createDoc.setTemplatePath("../../templates");
		 createDoc.setTemplateName("warnTemplate.ftl");
		 String currentPath=request.getSession().getServletContext().getRealPath("")+"product\\doc";
		 
		 createDoc.setFilePath(currentPath);
		 String fileName=request.getParameter("productTitle")+".doc";
		 createDoc.setFileName(fileName);
    	 Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
    	 String author="";
     	 try{
     		Subject currentUser = SecurityUtils.getSubject();//获取当前用户
     		author=currentUser.getPrincipal().toString();
     	 }catch(Exception e){
     		
     	 }
         dataMap.put("proIndex",DocCreater.index);
         dataMap.put("author", author);
         dataMap.put("signer", request.getParameter("reviewer"));
         dataMap.put("time", WeatherUtils.getCurrentTime());
     	 dataMap.put("warnDetail", request.getParameter("warnDetail"));
     	 dataMap.put("warnMeasures", request.getParameter("warnMeasures"));
		 boolean status=createDoc.createWord(dataMap);
		 if(status){
			 DocCreater.index++;
		 }
		 JSONObject responseData=new JSONObject();
		 responseData.put("statusCode", status);
		 response.setContentType("text/xml;charset=utf-8");
		 PrintWriter out=response.getWriter();
		 out.println(responseData);
		 out.flush();
		 out.close();
	}
	/***
	 * 获取最新一条气象产品，并显示相关的三条信息，用于游客和leader界面
	 * **/
	@RequestMapping("product/getLastProduct")
	public void getLastProduct(HttpServletRequest request,HttpServletResponse response) throws IOException{
		long id=Long.parseLong(request.getParameter("id"));//要查看的产品信息的标识
		String type=request.getParameter("type");//要查看的产品信息的类型
		JSONObject responseData=CallDataService.getLastProduct(id,type);	
		response.setContentType("text/xml;charset=utf-8");
		PrintWriter out=response.getWriter();
		out.println(responseData);
		out.flush();
		out.close();
	} 
}
