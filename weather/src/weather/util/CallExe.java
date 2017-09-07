package weather.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/***
 * 用于调用指定可执行文件 
 * @author 心之&所系
 * ***/
public class CallExe {
    /**
	 * 可带传入参数的可执行文件调用
	 * 
	*@param exePath String 包含可执行程序名的完整路径，如（D:\\C#.exe） 也可以在其后追加传入的参数 每个参数用空格隔开
	**/
	public static boolean runExec(String command){
    	BufferedReader errs=null;
    	BufferedReader results=null;
    	try{
    		Process process=new ProcessBuilder(command.split(" ")).start();
    		results=new BufferedReader(new InputStreamReader(process.getInputStream()));//获取执行进程时的输出信息
    		String s;
    		while((s=results.readLine())!=null){
    			System.out.println(s);
    		}
    		errs=new BufferedReader(new InputStreamReader(process.getErrorStream()));//获取执行进程时的错误信息
    		while((s=errs.readLine())!=null){
    			System.err.println(s);
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    		return false;
    	}
    	finally{
    		if(errs!=null){
        		try {
					errs.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    		if(results!=null){
        		try {
        			results.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    	}
    	return true;
    }
	/**
	 * 字符串组的方式调用
	 * @param para String[] para[0]为执行文件的完整路径
	 *                      para[1-...]为传入的参数 即为main中的String[] args 
	 * @return boolean 调用程序状态
	 * **/
	public static boolean runExec(String[] para){
		BufferedReader errs=null;
    	BufferedReader results=null;
		try {
			Runtime run = Runtime.getRuntime();
			Process process=run.exec(para);
			results=new BufferedReader(new InputStreamReader(process.getInputStream()));
    		String s;
    		while((s=results.readLine())!=null){
    			System.out.println(s);
    		}
    		errs=new BufferedReader(new InputStreamReader(process.getErrorStream()));
    		while((s=errs.readLine())!=null){
    			System.err.println(s);
    		}
		} 
		catch (Exception e) {
			e.printStackTrace();
    		return false;
		}
		finally{
    		if(errs!=null){
        		try {
					errs.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    		if(results!=null){
        		try {
        			results.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    	}
		return true;
	}
	/****
	 * 创建.dat文件
	 * @param path String 存放生成dat文件的路径
	 * @param fileName String 生成文件的名称 包含.dat
	 * @param datData Map数组 每个元素为一个Map对象，包含 lat、lng、value三组key-value
	 * @return boolean 表明文件的创建状态
	 * 
	 * ***/
	public static boolean createDat(String path,String fileName,List<Map<String,Object>>datData){
		boolean status=true;
		BufferedWriter datWriter=null;
		try {
			datWriter=new BufferedWriter(new FileWriter(path+"/"+fileName));
			String line="A  B  C\r\n";
			datWriter.write(line);
			for(Map<String,Object> data :datData){
				line=data.get("lng")+"  "+data.get("lat")+"  "+data.get("value")+"\r\n";
				datWriter.write(line);
			}
		} catch (IOException e) {
			status=false;
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//创建文件
		finally{
			if(datWriter!=null){
				try {
					datWriter.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return status;
	}
	/**
	 * main 函数：调用示例
	 * **/
	public static void main(String[] args){
		String path="D:/file/";
		String stationFile="D:/file/stationInfo.bln";
		String datFileName="data.dat";
		String exePath="D:/MyEXE/SurferMaker.exe";//Surfer的完整路径
		String datFile =path+"/"+ datFileName;//"D:/file/data.dat";//数据文件的完整路径  包括三列数据分别为经度、纬度、值 
        String imagePath = "D:/file/";//存储生成图片的文件夹路径
        String imageName = "chishuirainbows";//生成图片的名称
        String blnFileName = "D:/file/mainArea_chishui.bln";//地图文件
        String colorName = "D:/file/Lvl/rainbow24.lvl";//着色文件
        String blackFileName = "D:/file/black_chishui.bln";//底图文件，为分布图之前的地区行政图 
        String imageTitle = "雨量分布图";//图片标题
        /***需要绘制分布图地区的经纬度范围***/
        String lngMin="105.60";
        String lngMax="106.25";
        String latMin="28.2666";
        String latMax="28.7666";
		String[] para={
				        exePath,stationFile,datFile,imagePath,
						imageName,blnFileName,colorName,
						blackFileName,imageTitle
						,lngMin,lngMax,latMin,latMax//包含限定经纬度范围的调用方式
					  };
		double[][]da={
				{105.74,  28.45,   19.79}, 
			    {105.80,  28.34,   33.26}, 
			    {105.82,  28.47,   10.26}, 
			    {105.79,  28.41,   30.26}, 
			    {105.82,  28.67,   30.26}, 
			    {105.84,  28.47,   30.31}, 
			    {105.89,  28.72,   16.61}, 
			    {105.94,  28.28,   19.79}, 
			    {105.97,  28.32,   31.26}, 
			    {105.67,  28.55,   15.31}, 
			    {105.87,  28.60,   16.61},
			    {106.11,  28.45,   11.79},
			    {105.99,  28.76,   33.26}, 
			    {106.14,  28.37,   35.31},
			    {106.21,  28.45,   46.61}
			};
		List<Map<String,Object>> datPara=new ArrayList<>();
		for(int i=0;i<da.length;i++){
			Map<String,Object> pa=new HashMap<String,Object>();
			pa.put("lng", da[i][0]);
			pa.put("lat", da[i][1]);
			pa.put("value",da[i][2]);
			datPara.add(pa);
		}
		boolean status=createDat(path,datFileName,datPara);
		String result=status?"create dat sucess!":"create dat failed!";
		System.out.println(result);
		status=runExec(para);
		 result=status?"create image sucess!":"create image failed!";
		System.out.println(result);
	}
}
