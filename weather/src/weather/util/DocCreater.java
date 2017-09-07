package weather.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.LinkedHashMap;
import java.util.Map;

import Decoder.BASE64Encoder;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
/***
 * 用于读取.ftl格式的freeMaker模板生成word文档,生成实例后必须用setter方法
 * 初始化设置模板路径、模板名称、输出文件路径、输出文件名称
 * @author 心之&所系
 * @version  V1.0 
 * ****/
public class DocCreater {
	//freeMarker模板配置
    private Configuration configuration;
    public static long index=1L;
    /**freeMarker模板路径,为相对路径，是模板所在的文件夹相对当前类的路径
     * 如当前类的路径为src/weather.DocCreater 模板所在文件夹为src/templates
     * 则模板路径为../templates
     * **/
    private String templatePath;
    //freeMarker模板的名字
    private String templateName;
    //生成文件名
    private String fileName="tempDoc.doc";
    // 生成文件路径
    private String filePath="Template";
    //获取输出文件名
    public String getFileName() {
        return fileName;
    }
    //设置输出文件名
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    //获取输出文件路径
    public String getFilePath() {
        return filePath;
    }
    //设置输出文件路径
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    //获取模板路径
    public String getTemplatePath(){
        return templateName;
    }
    //设置模板路径
    public void setTemplatePath(String templatePath){
        this.templatePath=templatePath;
    }
    //获取模板名称
    public String getTemplateName(){
        return templateName;
    }
    //设置模板名称
    public void setTemplateName(String templateName){
        this.templateName = templateName;
    }
    //构造器
    public DocCreater() {
       configuration = new Configuration(Configuration.VERSION_2_3_23);
       configuration.setDefaultEncoding("UTF-8");
    }
    public Configuration getConfiguration(){
    	return this.configuration;
    }
    /***
     * 根据模板和传入的键值对，生成word文档
     * @param dataMap Map<String,Object>() String-Object键值对，key必须与模板中的key相对应
     *        图片文件必须转为二进制文本  
     * @return boolean 表示文档生成与否        
     * ***/
    public boolean createWord( Map<String, Object> dataMap){
    	 boolean status=true;
         configuration.setClassForTemplateLoading(this.getClass(),templatePath); // FTL文件所存在的位置
        // System.out.print(this.get);
         try{
        	 Template template = configuration.getTemplate(templateName);//获取模板
             //文件夹路径方式可能为D:/file的形式也可能为D:/file/
             String file=filePath.endsWith("/")?(filePath+fileName):(filePath+"/"+fileName);
             File outFile = new File(file);//输出文件路径及文件名
             Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile),"UTF-8"));
             template.process(dataMap, out);
             out.close();
         }catch(IOException|TemplateException e){
        	 status= false;
        	 // TODO Auto-generated catch block
 			 e.printStackTrace();
         }
         return status;
    }
    /***
     * 根据文件路径返回文件数据的二进制格式
     * 用于转换需要插入的图片文件
     * @param imgFile String 图片的完整路径
     * @return String 二进制图片文件或""
     * ***/
    public static String getImageStr(String imgFile) {
    	//输入文件流
        InputStream in=null;
        String image="";
        //字节组，用于储存图片文件数据
        byte[] data=null;
        try {
			in=new FileInputStream(imgFile);
			data=new byte[in.available()];
	        in.read(data);
	        in.close();
	        BASE64Encoder encoder=new BASE64Encoder();
	        //将图片文件进行base64编码
	        image=encoder.encode(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return image;
    }
    /**
     * main函数，示例
     * ***/
    public static void main(String[] args) {
    	 DocCreater test = new DocCreater();
    	 test.setTemplatePath("../../templates");
    	 test.setTemplateName("rainProduct.ftl");
    	 test.setFilePath("D:/Template");
    	 test.setFileName("rainProduct2.doc");
    	 Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
         dataMap.put("proIndex", "01");
         dataMap.put("author", "张东平");
         dataMap.put("signer", "陈 宇");
         dataMap.put("time", WeatherUtils.getCurrentTime());
         dataMap.put("title", "3月1日08时～3月2日13时全市降雨量实况");
         dataMap.put("rainGraph", getImageStr("D:/rainfall.png"));
		 boolean status=test.createWord(dataMap);
		 String result=status?"create sucess!":"create fail!";
		 System.out.print(result);
     }
}
