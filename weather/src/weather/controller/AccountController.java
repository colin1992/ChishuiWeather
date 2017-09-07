package weather.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import net.sf.json.JSONObject;
import weather.util.CallDataService;

/***
 * 
 * 账户相关操作
 * **/
@Controller
public class AccountController {
	/**
	 * 登录
	 * ***/
	@RequestMapping(value="dologin")  
	public  void login(HttpServletRequest request,HttpServletResponse response) throws IOException { 
    	String msg="";  
        String userName=request.getParameter("userName");  
        String password=request.getParameter("password");  
        JSONObject responseData=new JSONObject();
        UsernamePasswordToken token=new UsernamePasswordToken(userName, password);  
        token.setRememberMe(true);
        boolean statusCode=false;
        Subject subject=SecurityUtils.getSubject();  
        try {  
            subject.login(token);  
            if (subject.isAuthenticated()) {  
            	statusCode=true; 
            } 
        } catch (IncorrectCredentialsException e) {  
            msg = "账号或密码错误";  
        } catch (ExcessiveAttemptsException e) {  
            msg = "登录失败次数过多";  
        } catch (LockedAccountException e) {  
            msg = "帐号已被锁定。";  
        } catch (DisabledAccountException e) {  
            msg = "帐号已被禁用. The account for username " + token.getPrincipal() + " was disabled.";  
            System.out.println(msg);  
        } catch (ExpiredCredentialsException e) {  
            msg = "帐号已过期. the account for username " + token.getPrincipal() + "  was expired.";       
        } catch (UnknownAccountException e) {  
        	e.printStackTrace();
            msg = "用户名不存在.";  
        } catch (UnauthorizedException e) {  
            msg ="您没有得到相应的授权！" + e.getMessage();  
         } 
        responseData=CallDataService.getPerms(userName);
        boolean isLeader=false;
        if(responseData.getBoolean("statusCode")){
        	String role=responseData.getString("role");
        	if(role.contains("领导")){
        		isLeader=true;
        	}
        }
        responseData.clear();
        /**回传数据**/
        responseData.put("statusCode", statusCode);
        responseData.put("isLeader", isLeader);
        if(!statusCode){
            responseData.put("msg", msg);
        }
		response.setContentType("text/xml");
		response.setCharacterEncoding("utf-8");
		PrintWriter out=response.getWriter();
		out.println(responseData);
		out.flush();
		out.close(); 
    }  
	/**
	 *注销**/
	@RequestMapping("account/logout")
	public void logout(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try{
			Subject currentUser = SecurityUtils.getSubject();//获取当前用户
			currentUser.logout();
		}catch(Exception e){
			
		}
		JSONObject responseData=new JSONObject();
		responseData.put("statusCode", true);
		response.setContentType("text/xml");
		response.setCharacterEncoding("utf-8");
		PrintWriter out=response.getWriter();
		out.println(responseData);
		out.flush();
		out.close();
	  } 
  /***
	 * 读取用户信息
	 * **/
  @RequestMapping("account/getAccountInfo")
  public void getAccountInfo(HttpServletRequest request,HttpServletResponse response) throws IOException{
	  /**回传数据**/
	  String account="";
	  if(request.getParameter("account")!=null){
		  account=request.getParameter("account");
	  }
	  else{
		  try{
			  Subject currentUser=SecurityUtils.getSubject();//获取当前用户
			  account=currentUser.getPrincipal().toString();  
		  }catch(Exception e){
			//  e.printStackTrace();
		  }
	  }  
	  //调用数据库服务器获取账户信息
	  JSONObject responseData=CallDataService.getAccountInfo(account);
	  response.setContentType("text/xml"); 
	  response.setCharacterEncoding("utf-8");
	  PrintWriter out=response.getWriter();
	  out.println(responseData);
	  out.flush();
	  out.close(); 
  } 
  /**修改密码**/
	@RequestMapping("account/modifyPword")
	public void modifyPword(HttpServletRequest request,HttpServletResponse response) throws IOException{
		 String account="";
		  try{
			  Subject currentUser=SecurityUtils.getSubject();//获取当前用户
			  account=currentUser.getPrincipal().toString();  
		  }catch(Exception e){
			//  e.printStackTrace();
		  }
		  String oldPword=request.getParameter("oldPword");
		  String newPword=request.getParameter("newPword");
		  JSONObject responseData=CallDataService.modifyAccountPword(account, oldPword, newPword);
		  response.setContentType("text/xml"); 
		  response.setCharacterEncoding("utf-8");
		  PrintWriter out=response.getWriter();
		  out.println(responseData);
		  out.flush();
		  out.close(); 
	}
	/**修改邮箱**/
	@RequestMapping("account/modifyInfo/modifyEmail")
	public void modifyEmail(HttpServletRequest request,HttpServletResponse response) throws IOException{
		 String account="";
		  try{
			  Subject currentUser=SecurityUtils.getSubject();//获取当前用户
			  account=currentUser.getPrincipal().toString();  
		  }catch(Exception e){
			//  e.printStackTrace();
		  }
		  String pword=request.getParameter("pword");
		  String email=request.getParameter("email");
		  JSONObject responseData=CallDataService.modifyAccountEmail(account, pword, email);
		  response.setContentType("text/xml"); 
		  response.setCharacterEncoding("utf-8");
		  PrintWriter out=response.getWriter();
		  out.println(responseData);
		  out.flush();
		  out.close(); 
	}
	/**修改手机号**/
	 @RequestMapping("account/modifyInfo/modifyPhone")
	public void modifyPhone(HttpServletRequest request,HttpServletResponse response) throws IOException{
		 String account="";
		  try{
			  Subject currentUser=SecurityUtils.getSubject();//获取当前用户
			  account=currentUser.getPrincipal().toString();  
		  }catch(Exception e){
			//  e.printStackTrace();
		  }
		  String pword=request.getParameter("pword");
		  String phone=request.getParameter("phone");
		  JSONObject responseData=CallDataService.modifyAccountPhone(account, pword, phone);
		  response.setContentType("text/xml"); 
		  response.setCharacterEncoding("utf-8");
		  PrintWriter out=response.getWriter();
		  out.println(responseData);
		  out.flush();
		  out.close(); 
	}
  /******角色相关操作******/  
  /**
   * 获取权限信息，用于查看、编辑、新增角色时的权限列表
   * ***/
	  @RequestMapping("role/getPermsInfo")
	  public void getPermsInfo(HttpServletRequest request,HttpServletResponse response) throws IOException{
			JSONObject responseData=CallDataService.getPermsInfo();
			System.out.println(responseData);
			response.setContentType("text/xml;charset=utf-8");
			PrintWriter out=response.getWriter();
			out.println(responseData);
			out.flush();
			out.close();
	  }
	  
	  /**
	   * 增加角色
	   * ***/
	  @RequestMapping("role/addRole")
	  public void addRole(HttpServletRequest request,HttpServletResponse response) throws IOException{
	      	JSONObject role =new JSONObject();//读取新增角色的信息
	      	String addUser="工作人员";
	      	try{
	      		Subject currentUser = SecurityUtils.getSubject();//获取当前用户
	      		 addUser=currentUser.getPrincipal().toString();
	      	}catch(Exception e){
	      		
	      	}
	      	role.put("name", request.getParameter("roleName"));
	      	role.put("description", request.getParameter("description"));
	      	role.put("account", addUser);
	      	role.put("perms", request.getParameterValues("selectedPerms"));//perms为一个前端下发的数组，数组中各项为权限ID
			JSONObject responseData=CallDataService.addRole(role);
			response.setContentType("text/xml;charset=utf-8");
			PrintWriter out=response.getWriter();
			out.println(responseData);
			out.flush();
			out.close();
	  }
	  /**
	   * 分页获取角色信息
	   * ***/
	  @RequestMapping("role/getPaginationRole")
	  public void getPaginationRole(HttpServletRequest request,HttpServletResponse response) throws IOException{
			String name=request.getParameter("groupName");//角色名称，查询全部时设置为""
			int col=Integer.parseInt(request.getParameter("rowCount"));//表格列数
			int page=Integer.parseInt(request.getParameter("currentPage"));//当前页码
			JSONObject responseData=CallDataService.getPaginationRole(name,col,page);
			response.setContentType("text/xml;charset=utf-8");
			PrintWriter out=response.getWriter();
			out.println(responseData);
			out.flush();
			out.close();
	  }
	  /**
	   * 查看角色详情
	   * ***/
	  @RequestMapping("role/getRoleDetial")
	  public void getRoleDetial(HttpServletRequest request,HttpServletResponse response) throws IOException{
			Long id=Long.parseLong(request.getParameter("roleId"));//角色标识
			JSONObject responseData=CallDataService.getRoleDetial(id);
			response.setContentType("text/xml;charset=utf-8");
			PrintWriter out=response.getWriter();
			out.println(responseData);
			out.flush();
			out.close();
	  }
	  /**
	   * 修改角色
	   * ***/
	  @RequestMapping("role/alterRole")
	  public void alterRole(HttpServletRequest request,HttpServletResponse response) throws IOException{
	      	JSONObject role =new JSONObject();//读取新增角色的信息
	      	String account="工作人员";
	      	try{
	      		Subject currentUser = SecurityUtils.getSubject();//获取当前用户
	      		account=currentUser.getPrincipal().toString();
	      	}catch(Exception e){
	      		
	      	}
		    Long id=Long.parseLong(request.getParameter("id"));
	      	role.put("name", request.getParameter("roleName"));
	      	role.put("description", request.getParameter("description"));
	      	role.put("account", account);
	      	role.put("perms", request.getParameterValues("selectedPerms"));//perms为一个前端下发的数组，数组中各项为权限ID
			JSONObject responseData=CallDataService.alterRole(id,role);
			response.setContentType("text/xml;charset=utf-8");
			PrintWriter out=response.getWriter();
			out.println(responseData);
			out.flush();
			out.close();
	  }  
	  /**
	   * 删除角色
	   * ***/
	  @RequestMapping("role/delRole")
	  public void delRole(HttpServletRequest request,HttpServletResponse response) throws IOException{
		    Long id=Long.parseLong(request.getParameter("id"));
			JSONObject responseData=CallDataService.delRole(id);
			response.setContentType("text/xml;charset=utf-8");
			PrintWriter out=response.getWriter();
			out.println(responseData);
			out.flush();
			out.close();
	  }
	  /**
	   * 获取所有的用户角色，在增加用户的时候供用户选择
	 * @throws IOException 
	   * **/
	  @RequestMapping("role/getAllRole")
	  public void getRoleInfo(HttpServletRequest request,HttpServletResponse response) throws IOException{
		    JSONObject responseData=CallDataService.getRoleInfo();
			response.setContentType("text/xml;charset=utf-8");
			PrintWriter out=response.getWriter();
			out.println(responseData);
			out.flush();
			out.close();
	  }
	  /*******用户相关******/
	  /***
	   * 检查用户输入账号的可用性**/
	  @RequestMapping("user/isAccountAvailable")
	  public void isAccountAvailable(HttpServletRequest request,HttpServletResponse response) throws IOException{
	      	String name=request.getParameter("");//账户名称
			JSONObject responseData=CallDataService.isAccountAvailable(name);
			response.setContentType("text/xml;charset=utf-8");
			PrintWriter out=response.getWriter();
			out.println(responseData);
			out.flush();
			out.close();
	  }
	  /***
	   * 新增用户**/
	  @RequestMapping("user/addUser")
	  public void addUser(HttpServletRequest request,HttpServletResponse response) throws IOException{
	      	JSONObject user =new JSONObject();//新增用户的信息
	      	user.put("name", request.getParameter("name"));//账户名称
	      	user.put("description", request.getParameter("description"));//用户描述
	      	user.put("roleId", Long.parseLong(request.getParameter("roleId")));//角色标识
	      	user.put("pword", request.getParameter("pword"));//账户密码
	      	user.put("phone", request.getParameter("phone"));//手机号
	      	user.put("email", request.getParameter("email"));//邮箱
			JSONObject responseData=CallDataService.addUser(user);
			response.setContentType("text/xml;charset=utf-8");
			PrintWriter out=response.getWriter();
			out.println(responseData);
			out.flush();
			out.close();
	  }
	  /**
	   * 分页获取用户信息
	   * ***/
	  @RequestMapping("user/getPaginationUser")
	  public void getPaginationUser(HttpServletRequest request,HttpServletResponse response) throws IOException{
			Long roleId = null;
			if(!request.getParameter("roleId").equals("null"))
			roleId = Long.parseLong(request.getParameter("roleId"));//角色标识，查询全部时设置为null
			String name=request.getParameter("name");//账户名称，为""表示查询所有
			int col=Integer.parseInt(request.getParameter("rowCount"));//表格列数
			int page=Integer.parseInt(request.getParameter("currentPage"));//当前页码
			JSONObject responseData=CallDataService.getPaginationUser(name,roleId,col,page);
			response.setContentType("text/xml;charset=utf-8");
			PrintWriter out=response.getWriter();
			out.println(responseData);
			out.flush();
			out.close();
	  }
	  /**
	   * 修改用户
	   * ***/
	  @RequestMapping("user/alterUser")
	  public void alterUser(HttpServletRequest request,HttpServletResponse response) throws IOException{
	      	JSONObject user =new JSONObject();
		    String name =request.getParameter("name");//账户名称，注意：账户名不可修改
			user.put("description", request.getParameter("description"));//用户描述
	      	user.put("roleId", Long.parseLong(request.getParameter("roleId")));//角色标识
	      	user.put("phone", request.getParameter("phone"));//手机号
	      	user.put("email", request.getParameter("email"));//邮箱
			JSONObject responseData=CallDataService.alterUser(name,user);
			response.setContentType("text/xml;charset=utf-8");
			PrintWriter out=response.getWriter();
			out.println(responseData);
			out.flush();
			out.close();
	  }  
	  /**
	   * 删除用户
	   * ***/
	  @RequestMapping("user/delUser")
	  public void delUser(HttpServletRequest request,HttpServletResponse response) throws IOException{
		    Long id=Long.parseLong(request.getParameter("id"));
			JSONObject responseData=CallDataService.delUser(id);
			response.setContentType("text/xml;charset=utf-8");
			PrintWriter out=response.getWriter();
			out.println(responseData);
			out.flush();
			out.close();
	  }
}