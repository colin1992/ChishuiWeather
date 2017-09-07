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
/****基本主控配置控制器***/
public class ConfigureController {
	/****获取邮件服务器信息***/
	@RequestMapping("configure/emailServer/getServer")
	public void getServer(HttpServletRequest request,HttpServletResponse response){
		 JSONObject responseData=CallDataService.getEmailServer();
		 response.setCharacterEncoding("utf-8");
		 response.setContentType("json/text");
		 try {
			PrintWriter out=response.getWriter();
			out.println(responseData);
			out.flush();
			out.close();
		 } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }	
		response.setCharacterEncoding("utf-8");
		response.setContentType("json/text");
		try {
			PrintWriter out=response.getWriter();
			out.println(responseData);
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	/***
	 * 检查邮件服务器的可用性邮件服务器
	 * **/
	@RequestMapping("configure/emailServer/checkServer")
	public void checkServer(HttpServletRequest request,HttpServletResponse response){
		 JSONObject emailServer=new JSONObject();
		 emailServer.put("address",request.getParameter("address"));
		 emailServer.put("port",request.getParameter("port"));
		 emailServer.put("account",request.getParameter("account"));
		 emailServer.put("pword",request.getParameter("pword"));
		 JSONObject responseData=CallDataService.checkEmailServer(emailServer);
		 response.setCharacterEncoding("utf-8");
		 response.setContentType("json/text");
		 try {
			PrintWriter out=response.getWriter();
			out.println(responseData);
			out.flush();
			out.close();
		 } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }	
	}
	/****配置邮件服务器***/
	@RequestMapping("configure/emailServer/setServer")
	public void setServer(HttpServletRequest request,HttpServletResponse response){
		 JSONObject emailServer=new JSONObject();
		 emailServer.put("address",request.getParameter("address"));
		 emailServer.put("port",request.getParameter("port"));
		 emailServer.put("account",request.getParameter("account"));
		 emailServer.put("pword",request.getParameter("pword"));
		 JSONObject responseData=CallDataService.setEmailServer(emailServer);
		 response.setCharacterEncoding("utf-8");
		 response.setContentType("json/text");
		 try {
			PrintWriter out=response.getWriter();
			out.println(responseData);
			out.flush();
			out.close();
		 } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }	
		response.setCharacterEncoding("utf-8");
		response.setContentType("json/text");
		try {
			PrintWriter out=response.getWriter();
			out.println(responseData);
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}
