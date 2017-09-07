package weather.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import net.sf.json.JSONObject;
import weather.util.CallDataService;

@Controller
public class AssistantController {
	/****用于显示GIS易受灾点***/
	@RequestMapping("vulner/getVulnerInfoForGis")
	public void getAllVulners(HttpServletRequest request,HttpServletResponse response) throws IOException{
		JSONObject responseData=CallDataService.getAllVulners();	
		response.setContentType("text/xml;charset=utf-8");
		PrintWriter out=response.getWriter();
		out.println(responseData);
		out.flush();
		out.close();
	}	
	/***
	 * 根据开始时间、截止时间和天气要素绘制分布图
	 * ***/
	@RequestMapping("product/makeHisGraph")
	public void makeHisGraph(HttpServletRequest request,HttpServletResponse response) throws IOException{
		JSONObject responseData=CallDataService.getHisWeatherForGraph(request.getParameter("startTime"),
								request.getParameter("endTime"),request.getParameter("item"));	
		request.setAttribute("result", responseData);
		try {
			request.getRequestDispatcher("/product/makeGraph").forward(request,response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
}
