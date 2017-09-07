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
/***基础信息相关controller***/
public class BasisController {
	/**
	 * 新增基础信息
	 * **/
	@RequestMapping("basis/addBasis")
	public void addBasis(HttpServletRequest request,HttpServletResponse response) throws IOException{
		JSONObject basis=new JSONObject();
		basis.put("title", request.getParameter("title"));//标题
		basis.put("context",request.getParameter("context").getBytes());//内容 
		basis.put("description", request.getParameter("description"));//描述
		JSONObject responseData=CallDataService.addBasis(basis);	
		response.setContentType("text/xml;charset=utf-8");
		PrintWriter out=response.getWriter();
		out.println(responseData);
		out.flush();
		out.close();
	} 
	/***
	 * 修改基础信息
	 * **/
	@RequestMapping("basis/modifyBasis")
	public void modifyBasis(HttpServletRequest request,HttpServletResponse response) throws IOException{
		long id=Long.parseLong(request.getParameter("id"));//要修改的基础信息的标识
		JSONObject basis=new JSONObject();
		basis.put("title", request.getParameter("title"));//标题
		basis.put("context", request.getParameter("context").getBytes());//内容
		basis.put("description", request.getParameter("description"));//描述
		JSONObject responseData=CallDataService.modifyBasis(id,basis);	
		response.setContentType("text/xml;charset=utf-8");
		PrintWriter out=response.getWriter();
		out.println(responseData);
		out.flush();
		out.close();
	} 
	/***
	 * 删除基础信息
	 * **/
	@RequestMapping("basis/delBasis")
	public void delBasis(HttpServletRequest request,HttpServletResponse response) throws IOException{
		long id=Long.parseLong(request.getParameter("infoId"));//要删除的基础信息的标识
		JSONObject responseData=CallDataService.delBasis(id);	
		response.setContentType("text/xml;charset=utf-8");
		PrintWriter out=response.getWriter();
		out.println(responseData);
		out.flush();
		out.close();
	} 
	/***
	 * 分页模糊查询基础信息，查询参数为标题title，为空字符串时表示全部
	 * **/
	@RequestMapping("basis/getPaginationBasis")
	public void getPaginationBasis(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String title=request.getParameter("infoSearch");//基信标题，查询全部时设置为""
		int col=Integer.parseInt(request.getParameter("rowCount"));//表格列数
		int page=Integer.parseInt(request.getParameter("currentPage"));//当前页码		
		JSONObject responseData=CallDataService.getPaginationBasis(title,col,page);	
		response.setContentType("text/xml;charset=utf-8");
		PrintWriter out=response.getWriter();
		out.println(responseData);
		out.flush();
		out.close();
	}
	/***
	 * 查看基础信息详情
	 * **/
	@RequestMapping("basis/getBasisDetial")
	public void getBasisDetial(HttpServletRequest request,HttpServletResponse response) throws IOException{
		long id=Long.parseLong(request.getParameter("infoCode"));//要查看的基础信息的标识
		JSONObject responseData=CallDataService.getBasisDetial(id);	
		response.setContentType("text/xml;charset=utf-8");
		PrintWriter out=response.getWriter();
		out.println(responseData);
		out.flush();
		out.close();
	} 
}
