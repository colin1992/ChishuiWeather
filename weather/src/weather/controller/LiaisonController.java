package weather.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import net.sf.json.JSONObject;
import weather.util.CallDataService;

@Controller
/***
 * 用于维护人员组和人员信息
 * 
 * ***/
public class LiaisonController {
	/**
	 * 新增人员组
	 * **/
   @RequestMapping("liaisonUnit/addLiaisonUnit")
   public void addLiaisonUnit(HttpServletRequest request,HttpServletResponse response) throws IOException{
		JSONObject unit=new JSONObject();
		/***封装用户组信息，具体信息由前端下发***/
		unit.put("name", request.getParameter("groupName"));//名称
		unit.put("description", request.getParameter("description"));//描述
		String account="操作人员";
	    try{
	    	Subject currentUser = SecurityUtils.getSubject();//获取当前用户
	     	account=currentUser.getPrincipal().toString();
	    }catch(Exception e){
	    	
	    }
	    unit.put("createUser", account);
		JSONObject responseData=CallDataService.addLiaisonUnit(unit);	
		response.setContentType("text/xml;charset=utf-8");
		PrintWriter out=response.getWriter();
		out.println(responseData);
		out.flush();
		out.close();
   }  
   /**
	 * 分页获取人员组
	 * **/
	@RequestMapping("liaisonUnit/getPaginationUnit")
	public void getPaginationUnit(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String name=request.getParameter("groupName");//用户组名称
		int col=Integer.parseInt(request.getParameter("rowCount"));//表格列数
		int page=Integer.parseInt(request.getParameter("currentPage"));//当前页数
		JSONObject responseData=CallDataService.getPaginationUnit(name,col,page);	
		response.setContentType("text/xml;charset=utf-8");
		PrintWriter out=response.getWriter();
		out.println(responseData);
		out.flush();
		out.close();
	 }  
	/**
	 * 修改人员组
	 * **/
	@RequestMapping("liaisonUnit/alterLiaisonUnit")
	public void alterLiaisonUnit(HttpServletRequest request,HttpServletResponse response) throws IOException{
		long id=Long.parseLong(request.getParameter("groupId"));//人员组标识
		JSONObject unit=new JSONObject();
		/***封装用户组信息，具体信息由前端下发***/
		unit.put("name", request.getParameter("name"));//名称
		unit.put("description", request.getParameter("description"));//描述
		JSONObject responseData=CallDataService.alterLiaisonUnit(id,unit);	
		response.setContentType("text/xml;charset=utf-8");
		PrintWriter out=response.getWriter();
		out.println(responseData);
		out.flush();
		out.close();
	}  
	/**
	 * 删除人员组
	 * **/
	@RequestMapping("liaisonUnit/delLiaisonUnit")
	public void delLiaisonUnit(HttpServletRequest request,HttpServletResponse response) throws IOException{
		long id=Long.parseLong(request.getParameter("groupId"));//人员组标识
		JSONObject responseData=CallDataService.delLiaisonUnit(id);	
		response.setContentType("text/xml;charset=utf-8");
		PrintWriter out=response.getWriter();
		out.println(responseData);
		out.flush();
		out.close();
	}  
	/**
	 * 新增人员时获取各人员组的标识和名称键值对
	 * **/
	@RequestMapping("liaison/getUnitInfo")
	public void getUnitInfo(HttpServletRequest request,HttpServletResponse response) throws IOException{
		JSONObject responseData=CallDataService.getUnitInfo();	
		response.setContentType("text/xml;charset=utf-8");
		PrintWriter out=response.getWriter();
		out.println(responseData);
		out.flush();
		out.close();
	}  
	/**
	 * 新增人员
	 * 
	 * **/
	@RequestMapping("liaison/addLiaison")
	public void addLiaison(HttpServletRequest request,HttpServletResponse response) throws IOException{
		JSONObject liaison=new JSONObject();
		liaison.put("unitId", Long.parseLong(request.getParameter("unitId")));//人员组标识
		liaison.put("name", request.getParameter("name"));//人员姓名
		liaison.put("phone", request.getParameter("phone"));//手机号
		liaison.put("email", request.getParameter("email"));//邮箱
		liaison.put("description", request.getParameter("description"));//描述
		JSONObject responseData=CallDataService.addLiaison(liaison);	
		response.setContentType("text/xml;charset=utf-8");
		PrintWriter out=response.getWriter();
		out.println(responseData);
		out.flush();
		out.close();
	}  
	/**
	 * 修改人员
	 * 
	 * **/
	@RequestMapping("liaison/alterLiaison")
	public void alterLiaison(HttpServletRequest request,HttpServletResponse response) throws IOException{
		long id=Long.parseLong(request.getParameter("personId"));//人员标识
		JSONObject liaison=new JSONObject();
		liaison.put("unitId", Long.parseLong(request.getParameter("groupId")));//人员组标识
		liaison.put("name", request.getParameter("name"));//人员姓名
		liaison.put("phone", request.getParameter("phone"));//手机号
		liaison.put("email", request.getParameter("email"));//邮箱
		liaison.put("description", request.getParameter("description"));//描述
		JSONObject responseData=CallDataService.alterLiaison(id,liaison);	
		response.setContentType("text/xml;charset=utf-8");
		PrintWriter out=response.getWriter();
		out.println(responseData);
		out.flush();
		out.close();
	}  
	/**
	 * 分页获取人员列表
	 * 
	 * **/
	@RequestMapping("liaison/getPaginationLiaison")
	public void getPaginationLiaison(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Long unitId = null;
		if(!request.getParameter("unitId").equals("null"))
		    unitId=Long.parseLong(request.getParameter("unitId"));//人员组标识，查询全部时设置为null
		String name=request.getParameter("name");//人员名称，查询全部时设置为""
		int col=Integer.parseInt(request.getParameter("rowCount"));//表格列数
		int page=Integer.parseInt(request.getParameter("currentPage"));//当前页码
		JSONObject responseData=CallDataService.getPaginationLiaison(unitId,name,col,page);	
		response.setContentType("text/xml;charset=utf-8");
		PrintWriter out=response.getWriter();
		out.println(responseData);
		out.flush();
		out.close();
	}  
	/**
	 * 删除人员
	 * 
	 * **/
	@RequestMapping("liaison/delLiaison")
	public void delLiaison(HttpServletRequest request,HttpServletResponse response) throws IOException{
		long id=Long.parseLong(request.getParameter("id"));//人员标识
		JSONObject responseData=CallDataService.delLiaison(id);	
		response.setContentType("text/xml;charset=utf-8");
		PrintWriter out=response.getWriter();
		out.println(responseData);
		out.flush();
		out.close();
	}  
}
