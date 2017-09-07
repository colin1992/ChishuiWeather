package weather.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/***
 * 查询数据库服务器的工具类
 * 
 * ***/
public class CallDataService {
	 
	/*********角色相关********/
	 /**
	  * 获取权限信息，用于查看、编辑、新增角色时的权限列表
	  *@return JSONObject 包含:<br/>
	  *     statusCode boolean：表明状态,true表示查询到权限信息，false表明查找失败<br/>
	  *     err String：statusCode为false时有效，表明失败的具体原因<br/>
	  *     perms JSONObject：statusCode为true时有效,表明具体的角色信息，为key:JSONArray的格式，key表示权限组名称，JSONArray表示当前权限组的各子权限,
	  *     每个元素为一个JSONObject，以key-value的形式呈现权限信息，包含：<br/>
	  *     &nbsp;&nbsp;name String:权限名称
	  *     &nbsp;&nbsp;id long:权限标识   
	  * ***/
	  public static JSONObject getPermsInfo(){
		 DataServerUtil dataService=new DataServerUtil("role/getPermsInfo");
		  String params="";
		  return dataService.getQueryData(params);  
	  }
	 /**
	  * 新增角色
	  * @param role JSONObject 包含<br/>
	  *    name：角色名称<br/>
	  *    description：角色描述<br/>
	  *    perms：角色权限，为一个序列化的数组，数组中的每一项为一个权限的ID<br/>
	  *    account：新增用户<br/>
	  * @return JSONObject 包含<br/>
	  *     statusCode： 为boolean型，表明状态<br/>
	  *     err：String 表明失败原因
	  * **/
	  public static JSONObject addRole(JSONObject role) {
		  DataServerUtil dataService=new DataServerUtil("role/addRole");
		  String params="role="+role;
		  return dataService.getQueryData(params);  
	  }
	  /***
		* 分页获取角色列表
		*@param name String:角色名称，为空字符串时表示全部
		*@param col int:表格行数
		*@param page int:当前页码
		*@return JSONObject 包括以下参数<br/>
		*   &nbsp;&nbsp; statusCode boolean:表明状态<br/>
		*   &nbsp;&nbsp; err String:statusCode为false时有效，表示执行失败的详细原因<br/>
		*   &nbsp;&nbsp; totalPage int:statusCode为true时有效，总页码数<br/>
		*   &nbsp;&nbsp; roles JSONArray:statusCode为true时有效，表示当前页码的角色信息，每个元素为一个JSONObject，包括以下参数：<br/>
		*   &nbsp;&nbsp;&nbsp;&nbsp; id long:角色标识
		*   &nbsp;&nbsp;&nbsp;&nbsp; name String：角色名称 
		*   &nbsp;&nbsp;&nbsp;&nbsp; createDate String：创建时间 
		*   &nbsp;&nbsp;&nbsp;&nbsp; createUser String：创建时用户
		*   &nbsp;&nbsp;&nbsp;&nbsp; description String：描述 
	  * **/
	  public static JSONObject getPaginationRole(String name,int col,int page){
		  DataServerUtil dataService=new DataServerUtil("role/getPaginationRole");
		  String params="name="+name+"&col="+col+"&page="+page;
		  return dataService.getQueryData(params);  
	  }
	  /***
		* 查看角色详情
		*@param id long:角色标识
		*@return JSONObject 包括以下参数<br/>
		*   &nbsp;&nbsp; statusCode boolean:表明状态<br/>
		*   &nbsp;&nbsp; err String:statusCode为false时有效，表示执行失败的详细原因<br/>
		*   &nbsp;&nbsp; role JSONObject:statusCode为true时有效，表示角色的详细信息，包括以下参数：<br/>
		*   &nbsp;&nbsp;&nbsp;&nbsp; perms long[]:权限标识
		*   &nbsp;&nbsp;&nbsp;&nbsp; name String：角色名称 
		*   &nbsp;&nbsp;&nbsp;&nbsp; createDate String：创建时间 
		*   &nbsp;&nbsp;&nbsp;&nbsp; createUser String：创建时用户
		*   &nbsp;&nbsp;&nbsp;&nbsp; description String：描述 
	  * **/
	  public static JSONObject getRoleDetial(long id){
		  DataServerUtil dataService=new DataServerUtil("role/getRoleDetial");
		  String params="id="+id;
		  return dataService.getQueryData(params);  
	  }
	  /**
	   * 修改角色
	   * @param id Long 角色标识
	   * @param role JSONObject 包含
	   *    name：角色名称<br/>
	   *    description：角色描述<br/>
	   *    perms：角色权限，为一个序列化的数组，数组中的每一项为一个权限的ID<br/>
	   *    account：新增用户<br/>
	    @return JSONObject 包含<br/>
	   *     statusCode： 为boolean型，表明状态<br/>
	   *     err：String 表明失败原因<br/>
	   * **/
	  public static JSONObject alterRole(Long id,JSONObject role) {
		  DataServerUtil dataService=new DataServerUtil("role/alterRole");
		  String params="id="+id+"&role="+role;
		  return dataService.getQueryData(params);  
	  } 
	  /**
	   * 删除角色
	   *@param id Long 角色标识
	   *@return JSONObject 包含
	   *     statusCode： 为boolean型，表明状态
	   *     err：String 表明失败原因
	   * **/
	  public static JSONObject delRole(Long id) {
		  DataServerUtil dataService=new DataServerUtil("role/delRole");
		  String params="id="+id;
		  return dataService.getQueryData(params);  
	  }
	 /*********用户相关***********/ 
	 /** @param name String:用户输入的账号
	   * @return JSONObject 包含:<br/>
	   *     statusCode boolean:表示执行结果，true表示执行成功，false表示失败<br/>
	   *     err String: 表明失败原因，当statusCode为false时有效<br/>
	   *     isAvailable boolean:表明当前账号的可用性，当statusCode为true时有效,false表示账户不可用，true表示账号可用
	   * **/
	  public static JSONObject isAccountAvailable(String name) {
		  DataServerUtil dataService=new DataServerUtil("user/isAccountAvailable");
		  String params="name="+name;
		  params="";
		  return dataService.getQueryData(params);  
	  }
	  
	  /**
	   * 新增用户时获取角色信息
	   * @return JSONObject 包含<br/>
	   *     statusCode boolean：表明状态<br/>
	   *     err String ：statusCode为false时有效，表明失败原因<br/>
	   *     roles JSONArray:statusCode为true时有效，表明所有可用角色信息，每个元素为一个JSONObject，包含以下参数：<br/>
	   *     &nbsp;&nbsp;&nbsp;&nbsp; name String：角色名称<br/>
	   *     &nbsp;&nbsp;&nbsp;&nbsp; id long :角色标识
	   * **/
	  public static JSONObject getRoleInfo() {
		  DataServerUtil dataService=new DataServerUtil("user/getRoleInfo");
		  String params="";
		  return dataService.getQueryData(params);  
	  }
	  
	  /**
	   * 新增用户
	   * @param user JSONObject 包含<br/>
	   *    roleId long：角色标识<br/>
	   *    name  String：账户名称<br/>
	   *    pword String：账户密码<br/>
	   *    email String：账户邮箱<br/>
	   *    phone String：账户手机号<br/> 
	   *    description String：用户描述<br/>
	   * @return JSONObject 包含<br/>
	   *     statusCode boolean：表明状态<br/>
	   *     err String ：statusCode为false时有效，表明失败原因
	   * **/
	  public static JSONObject addUser(JSONObject user) {
		  DataServerUtil dataService=new DataServerUtil("user/addUser");
		  String params="user="+user;
		  return dataService.getQueryData(params);  
	  }
	  /***
		* 分页获取用户列表
		*@param name String:用户名称，为空字符串时表示全部
		*@param Long roleId:为null时表示全部
		*@param col int:表格行数
		*@param page int:当前页码
		*@return JSONObject 包括以下参数<br/>
		*   &nbsp;&nbsp; statusCode boolean:表明状态<br/>
		*   &nbsp;&nbsp; err String:statusCode为false时有效，表示执行失败的详细原因<br/>
		*   &nbsp;&nbsp; totalPage int:statusCode为true时有效，总页码数<br/>
		*   &nbsp;&nbsp; users JSONArray:statusCode为true时有效，表示当前页码的用户信息，每个元素为一个JSONObject，包括以下参数：<br/>
		*   &nbsp;&nbsp;&nbsp;&nbsp; id long:用户标识
		*   &nbsp;&nbsp;&nbsp;&nbsp; name String：账户名称 
		*   &nbsp;&nbsp;&nbsp;&nbsp; roleName String：角色名称
		*   &nbsp;&nbsp;&nbsp;&nbsp; phone String：手机号
		*   &nbsp;&nbsp;&nbsp;&nbsp; email String：邮箱
		*   &nbsp;&nbsp;&nbsp;&nbsp; description String：描述 
	  * **/
	  public static JSONObject getPaginationUser(String name,Long roleId,int col,int page){
		  DataServerUtil dataService=new DataServerUtil("user/getPaginationUser");
		  String params="roleId="+roleId+"&name="+name+"&col="+col+"&page="+page;
		  return dataService.getQueryData(params);  
	  }
	  /**
	   * 修改用户
	   * @param name String 用户账号
	   * @param user JSONObject 包含
	   *    roleId long：角色标识<br/>
	   *    email String：账户邮箱<br/>
	   *    phone String：账户手机号<br/> 
	   *    description String：用户描述<br/>
	    @return JSONObject 包含<br/>
	   *     statusCode： 为boolean型，表明状态<br/>
	   *     err：String 表明失败原因<br/>
	   * **/
	  public static JSONObject alterUser(String name,JSONObject user) {
		  DataServerUtil dataService=new DataServerUtil("user/alterUser");
		  String params="name="+name+"&user="+user;
		  return dataService.getQueryData(params);  
	  } 
	  /**
	   * 删除用户
	   *@param id Long 用户标识
	   *@return JSONObject 包含
	   *     statusCode： 为boolean型，表明状态
	   *     err：String 表明失败原因
	   * **/
	  public static JSONObject delUser(Long id) {
		  DataServerUtil dataService=new DataServerUtil("user/delUser");
		  String params="id="+id;
		  return dataService.getQueryData(params);  
	  }	  
	  
	/****人员相关***/
	  /****
	     * 分页获取人员组信息
	     * @param name String:组别名称，为空字符串时表示所有
	     * @param col  int:前端表格列数
	     * @param page int:当前页码数
	     * @return JSONObject,包含以下字段：<br/>
		 *   &nbsp;&nbsp; statusCode boolean:表明状态<br/>
		 *   &nbsp;&nbsp; err String:statusCode为false时有效，表示执行失败的详细原因<br/>
		 *   &nbsp;&nbsp; totalPage int:总页数<br/>
		 *   &nbsp;&nbsp; units JSONArray:当前页码各人员组条目的详细信息，每个元素为一个JSONObject包括以下字段：<br/>
		 *   &nbsp;&nbsp; &nbsp;&nbsp;name String:组别名称
		 *   &nbsp;&nbsp; &nbsp;&nbsp;id long:组别标识
		 *   &nbsp;&nbsp; &nbsp;&nbsp;description String:组别描述
		 *   &nbsp;&nbsp; &nbsp;&nbsp;createUser String:创建人员
		 *   &nbsp;&nbsp; &nbsp;&nbsp;createDate String:创建时间
	     * **/
	  public static JSONObject getPaginationUnit(String name,int col,int page){
		  DataServerUtil dataService=new DataServerUtil("liaisonUnit/getPaginationUnit");
		  String params="name="+name+"&col="+col+"&page="+page;
		  return dataService.getQueryData(params);  
	  }
	   /****
	    * 新增人员组
	    * @param unit JSONObject:新增人员组的详细信息，包含以下字段：<br/>
	    * 	&nbsp;&nbsp; name String:人员组名称<br/>
	    *  &nbsp;&nbsp; description String:人员组描述<br/>
	    *  &nbsp;&nbsp; createUser String:操作人员<br/>
		* @return JSONObject 包括两个参数<br/>
		*   &nbsp;&nbsp; statusCode boolean:表明状态<br/>
		*   &nbsp;&nbsp; err String:statusCode为false时有效，表示执行失败的详细原因<br/>
	    * **/
	  public static JSONObject addLiaisonUnit(JSONObject unit){
		  DataServerUtil dataService=new DataServerUtil("liaisonUnit/addLiaisonUnit");
		  String params="unit="+unit;
		  return dataService.getQueryData(params);  
	  }
	  /****
	    * 修改人员组
	    * @param id long：要修改的人员组的标识
	    * @param unit JSONObject:人员组的详细信息，包含以下字段：<br/>
	    *  &nbsp;&nbsp; name String:人员组名称<br/>
	    *  &nbsp;&nbsp; description String:人员组描述<br/>
		* @return JSONObject 包括两个参数<br/>
		*   &nbsp;&nbsp; statusCode boolean:表明状态<br/>
		*   &nbsp;&nbsp; err String:statusCode为false时有效，表示执行失败的详细原因<br/>
	    * **/
	  public static JSONObject alterLiaisonUnit(long id,JSONObject unit){
		  DataServerUtil dataService=new DataServerUtil("liaisonUnit/alterLiaisonUnit");
		  String params="id="+id+"&unit="+unit;
		  return dataService.getQueryData(params);  
	  }
	  /****
	    * 删除人员组
	      @param id long：要修改的人员组的标识
		* @return JSONObject 包括两个参数<br/>
		*   &nbsp;&nbsp; statusCode boolean:表明状态<br/>
		*   &nbsp;&nbsp; err String:statusCode为false时有效，表示执行失败的详细原因<br/>
	    * **/
	  public static JSONObject delLiaisonUnit(long id){
		  DataServerUtil dataService=new DataServerUtil("liaisonUnit/delLiaisonUnit");
		  String params="id="+id;
		  return dataService.getQueryData(params);  
	  }
	  /***
	   * 新增人员时获取各人员组的标识和名称键值对
	   * @return JSONObject 包括两个参数<br/>
		*   &nbsp;&nbsp; statusCode boolean:表明状态<br/>
		*   &nbsp;&nbsp; err String:statusCode为false时有效，表示执行失败的详细原因<br/>
		*   &nbsp;&nbsp; units JSONArray:statusCode为true时有效，表示当前系统中存在的人员组信息，每个元素为JSONObject，包含以下字段<br/>:
		*   &nbsp;&nbsp; &nbsp;&nbsp; name String:人员组名称 
		*   &nbsp;&nbsp; &nbsp;&nbsp; id long：人员组标识
	   * **/
	  public static JSONObject getUnitInfo(){
		  DataServerUtil dataService=new DataServerUtil("liaison/getUnitInfo");
		  String params="";
		  return dataService.getQueryData(params);  
	  }
	  /***
	   * 新增人员
	   * @param liaison JSONObject:新建人员的参数信息，包含以下字段：<br/>
	   * &nbsp;&nbsp; unitId long:人员组标识
	   * &nbsp;&nbsp; name String:人员姓名
	   * &nbsp;&nbsp; phone String:手机号
	   * &nbsp;&nbsp; email String:邮箱
	   * &nbsp;&nbsp; description String:人员描述 
	   * @return JSONObject 包括两个参数<br/>
		*   &nbsp;&nbsp; statusCode boolean:表明状态<br/>
		*   &nbsp;&nbsp; err String:statusCode为false时有效，表示执行失败的详细原因<br/>
	   * **/
	  public static JSONObject addLiaison(JSONObject liaison){
		  DataServerUtil dataService=new DataServerUtil("liaison/addLiaison");
		  String params="liaison="+liaison;
		  return dataService.getQueryData(params); 
	  }
	  /***
	   * 修改人员
	   * @param id long:人员标识
	   * @param liaison JSONObject:人员的参数信息，包含以下字段：<br/>
	   * &nbsp;&nbsp; unitId long:人员组标识
	   * &nbsp;&nbsp; name String:人员姓名
	   * &nbsp;&nbsp; phone String:手机号
	   * &nbsp;&nbsp; email String:邮箱
	   * &nbsp;&nbsp; description String:人员描述 
	   * @return JSONObject 包括两个参数<br/>
		*   &nbsp;&nbsp; statusCode boolean:表明状态<br/>
		*   &nbsp;&nbsp; err String:statusCode为false时有效，表示执行失败的详细原因<br/>
	   * **/
	  public static JSONObject alterLiaison(long id,JSONObject liaison){
		  DataServerUtil dataService=new DataServerUtil("liaison/alterLiaison");
		  String params="id="+id+"&liaison="+liaison;
		  return dataService.getQueryData(params); 
	  }
	  /***
	   * 分页获取人员列表
	   * @param unitId Long:人员组标识,为null时表示全部
	   * @param name String:人员名称，为空字符串时表示全部
	   * @param col int:表格行数
	   * @param page int:当前页码
	   * @return JSONObject 包括以下参数<br/>
		*   &nbsp;&nbsp; statusCode boolean:表明状态<br/>
		*   &nbsp;&nbsp; err String:statusCode为false时有效，表示执行失败的详细原因<br/>
		*   &nbsp;&nbsp; totalPage int:statusCode为true时有效，总页码数<br/>
		*   &nbsp;&nbsp; liaisons JSONArray:statusCode为true时有效，表示当前页码的人员信息，每个元素为一个JSONArray，包括以下参数：<br/>
		*   &nbsp;&nbsp;&nbsp;&nbsp; id long:人员标识
		*   &nbsp;&nbsp;&nbsp;&nbsp; name String：人员姓名 
		*   &nbsp;&nbsp;&nbsp;&nbsp; unitName String：人员组名称
		*   &nbsp;&nbsp;&nbsp;&nbsp; phone String：手机号 
		*   &nbsp;&nbsp;&nbsp;&nbsp; email String：邮箱 
		*   &nbsp;&nbsp;&nbsp;&nbsp; description String：描述 
	   * **/
	  public static JSONObject getPaginationLiaison(Long unitId,String name,int col,int page){
		  DataServerUtil dataService=new DataServerUtil("liaison/getPaginationLiaison");
		  String params="unitId="+unitId+"&name="+name+"&col="+col+"&page="+page;
		  return dataService.getQueryData(params); 
	  }
	  /***
	   * 删除人员
	   * @param id long:人员标识
	   * @return JSONObject 包括两个参数<br/>
		*   &nbsp;&nbsp; statusCode boolean:表明状态<br/>
		*   &nbsp;&nbsp; err String:statusCode为false时有效，表示执行失败的详细原因<br/>
	   * **/
	  public static JSONObject delLiaison(long id){
		  DataServerUtil dataService=new DataServerUtil("liaison/delLiaison");
		  String params="id="+id;
		  return dataService.getQueryData(params); 
	  }
	/*****账户相关****/
	 /***
	   * 登录账户
	   * @param account 用户账号
	   * @return JSONObject
	   * **/
	  public static JSONObject getLoginInfo(String account){
		  DataServerUtil dataService=new DataServerUtil("account/getLoginInfo");
		  String params="account="+account;
		  return dataService.getQueryData(params);  
	  }
	  /***
	   * 获取账户角色与权限
	   * @param account 用户账号
	   * @return JSONObject
	   * **/
	  public static JSONObject getPerms(String account){
		  DataServerUtil dataService=new DataServerUtil("account/getPermission");
		  String params="account="+account;
		  return dataService.getQueryData(params);  
	  }
	  
	  /***
	   * 获取账户信息
	   * @param account 用户账号
	   * @return JSONObject
	   * **/
	  public static JSONObject getAccountInfo(String account){
		  DataServerUtil dataService=new DataServerUtil("account/getAccountInfo");
		  String params="account="+account;
		  return dataService.getQueryData(params);  
	  }
	  /***
	   * 修改账户密码
	   * @param account String  用户账号
	   * @param oldPword String 旧密码
	   * @param newPword String 新密码
	   * @return JSONObject
	   * **/
	  public static JSONObject modifyAccountPword(String account,String oldPword,String newPword){
		  DataServerUtil dataService=new DataServerUtil("account/modifyPword");
		  String params="account="+account+"&oldPword="+oldPword+"&newPword="+newPword;
		  return dataService.getQueryData(params);  
	  }
	  /***
	   * 修改账户邮箱信息
	   * @param account String  用户账号
	   * @param pword String 密码
	   * @param email String 邮箱
	   * @return JSONObject
	   * **/
	  public static JSONObject modifyAccountEmail(String account,String pword,String email){
		  DataServerUtil dataService=new DataServerUtil("account/modifyInfo/modifyEmail");
		  String params="account="+account+"&pword="+pword+"&email="+email;
		  return dataService.getQueryData(params);  
	  }
	  /***
	   * 修改账户手机号
	   * @param account String  用户账号
	   * @param pword String 密码
	   * @param phone String 手机号
	   * @return JSONObject
	   * **/
	  public static JSONObject modifyAccountPhone(String account,String pword,String phone){
		  DataServerUtil dataService=new DataServerUtil("account/modifyInfo/modifyPhone");
		  String params="account="+account+"&pword="+pword+"&phone="+phone;
		  return dataService.getQueryData(params);  
	  }
	/*******预警相关*********/
	  /***
	   * 新增预警策略时获取所有监测站信息
	   * @return JSONObject包含:<br/>
	   *   &nbsp;&nbsp;&nbsp;&nbsp;   statusCode： 为boolean型，表明状态<br/>
	   *   &nbsp;&nbsp;&nbsp;&nbsp;   err：String 当statusCode为false时有效，表明失败原因<br/>
	   *   &nbsp;&nbsp;&nbsp;&nbsp;   stations:当statusCode为true时有效，表示各监测站的信息。为JSONArray，每个元素为一个JSONObject，包含以下字段：<br/>
	   *   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; code:站点编号<br/>
	   *   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; name:站点名称<br/>
	   *   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; areaCode:区域编号  
	   * ***/
	  public static JSONObject getStationInfo(){
		  DataServerUtil dataService=new DataServerUtil("warn/getStationInfo");
		  String params="";
		  return dataService.getQueryData(params);  
	  }
	  /***
	   * 新增预警策略时获取所有人员和人员组信息
	   * @return JSONObject包含:<br/>
	   *   &nbsp;&nbsp;&nbsp;&nbsp;   statusCode： 为boolean型，表明状态<br/>
	   *   &nbsp;&nbsp;&nbsp;&nbsp;   err：String 当statusCode为false时有效，表明失败原因<br/>
	   *   &nbsp;&nbsp;&nbsp;&nbsp;   liaisonUnits:当statusCode为true时有效，表示各人员组的信息。为JSONArray，每个元素为一个JSONObject，包含以下字段：<br/>
	   *   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; name:组别名称<br/>
	   *   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; id:组别标识  
	   *   &nbsp;&nbsp;&nbsp;&nbsp;   liaisons:当statusCode为true时有效，表示人员的信息。为JSONArray，每个元素为一个JSONObject，包含以下字段：<br/>
	   *   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; name:人员名称<br/>
	   *   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; id:人员标识  
	   *   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; unitId:组别标识 
	   * ***/
	  public static JSONObject getLiaisonsInfo(){
		  DataServerUtil dataService=new DataServerUtil("warn/getLiaisonsInfo");
		  String params="";
		  return dataService.getQueryData(params);  
	  }	  
	  
	  /*****增加预警策略
	   * @param strategy JSONObject
	   * @return JSONObject 包含<br/>
	   *     statusCode： 为boolean型，表明状态<br/>
	   *     err：String 表明失败原因
	   * ****/
	  public static JSONObject addWarnStrategy(JSONObject strategy){
		  DataServerUtil dataService=new DataServerUtil("warn/addWarnStrategy");
		  String params="warnStrategy="+strategy.toString();
		  return dataService.getQueryData(params);  
	  }
	  /*****修改预警策略
	   * @param strategy JSONObject
	   * @param id Long 要修改策略的id
	   * @return JSONObject 包含<br/>
	   *     statusCode： 为boolean型，表明状态<br/>
	   *     err：String 表明失败原因
	   * ****/
	  public static JSONObject alterWarnStrategy(Long id,JSONObject strategy){
		  DataServerUtil dataService=new DataServerUtil("warn/alterWarnStrategy");
		  String params="id="+id+"&warnStrategy="+strategy.toString();
		  return dataService.getQueryData(params);  
	  }
	  /*****删除预警策略
	   * @param id Long 要删除策略的id
	   * @return JSONObject 包含<br/>
	   *     statusCode： 为boolean型，表明状态<br/>
	   *     err：String 表明失败原因
	   * ****/
	  public static JSONObject delWarnStrategy(Long id){
		  DataServerUtil dataService=new DataServerUtil("warn/delWarnStrategy");
		  String params="id="+id;
		  return dataService.getQueryData(params);  
	  }
	  /***
	   	 *分页获取预警策略
	   	 *@param page:当前页数
	   	 *@param col:表格的行数 
	   	 *@return JSONObject 包括
	   	 *     pages:总页数
	   	 *     statusCode:boolean 标识查新状态
	   	 *     warnStrategy：JSONArray 符合条件的预警策略，每个元素为一条预警策略的各参数信息 
	   	 *          一个预警条目为一个JSONObject，包括以下key-value<br/>
	   	 *           id：策略标识<br/>
	   	 *           name：策略名称<br/>
	   	 *           item：预警类别<br/>
	   	 *           param：预警参数<br/>
	   	 *           threshold：预警门限<br/>
	   	 *           createUser：创建用户<br/>
	   	 *           createDate：创建时间<br/>
	   	 *           status：启用状态，已启用或未启用
	   	 *     
	   	 * ***/
	  public static JSONObject getPaginationWarnStrategy(int col,int page,String name){
		  DataServerUtil dataService=new DataServerUtil("warn/getPaginationWarnStrategy");
		  String params="col="+col+"&page="+page+"&name="+name;
		  return dataService.getQueryData(params);  
	  }
	  /***
	   * 查看某条预警策略的详细信息
	   * @param id
	   * @return JSONObject 包括<br/>
	   *     pages:总页数<br/>
	   *     statusCode:boolean 标识查新状态<br/>
	   *     warnStrategy：JSONObject 预警策略条目的各参数信息，包括以下key-value<br/>
	   *           id：策略标识<br/>
	   *           name：策略名称<br/>
	   *           item：预警类别<br/>
	   *           param：预警参数<br/>
	   *           threshold：预警门限<br/>
	   *           createUser：创建用户<br/>
	   *           createDate：创建时间<br/>
	   *           status：启用状态，已启用或未启用<br/>
	   *           liaisons:预警人员，为数组，每个元素为预警人员的Id<br/>
	   *           stations:适用站点，为数组，每个元素为监测站点的代号
	   * **/
	  public static JSONObject getWarnStrategyDetial(Long id){
		  DataServerUtil dataService=new DataServerUtil("warn/getWarnStrategyDetial");
		  String params="id="+id;
		  return dataService.getQueryData(params);  
	  }
	  /***
	   * 根据名称查找预警策略
	   * @param name:String 策略名称
	   * @return JSONObject 包括<br/>
	   *       statusCode:boolean 标识查新状态<br/>
	   	 *     warnStrategy：如果statusCode为true，JSONArray 每个元素为一条预警策略的各参数信息, 
	   	 *     	   	   一个预警条目为一个JSONObject，包括以下key-value<br/>
	   	 *           id：策略标识<br/>
	   	 *           name：策略名称<br/>
	   	 *           item：预警类别<br/>
	   	 *           param：预警参数<br/>
	   	 *           threshold：预警门限<br/>
	   	 *           createUser：创建用户<br/>
	   	 *           createDate：创建时间<br/>
	   	 *           status：启用状态，已启用或未启用
	   * **/
	  public static JSONObject getWarnStrategyByName(String name){
		  DataServerUtil dataService=new DataServerUtil("warn/getWarnStrategyByName");
		  String params="name="+name;
		  return dataService.getQueryData(params);  
	  }
	   /***
	     * 通过告警处理状态、表格列数已经当前页码分页查找预警信息
	     * @param status Boolean :预警状态,为true表示已解除，false表示未解除，null表示全部
	     * @param col int:表格列数
	     * @param page int:当前页码
	     * @return JSONObject 包含以下字段：<br/>
	     *   statusCode: boolean，false表示查询失败；true表示查找成功<br/>
	     *   err:String 当statusCode为false时有效，表示失败的具体原因<br/>
	     *   totalPage:int 当statusCode为true时有效，表示符合条件的告警信息的页码数<br/>
	     *   warns：JSONArray，当statusCode为true时有效，表示符合条件的告警记录，每一个元素为一个JSONObject，包含以下字段：<br/>
	     *   &nbsp;&nbsp;&nbsp;&nbsp; id：long，告警标识
	     *   &nbsp;&nbsp;&nbsp;&nbsp; title：String,告警标题
	     *   &nbsp;&nbsp;&nbsp;&nbsp; time：String,告警时间
	     *   &nbsp;&nbsp;&nbsp;&nbsp; status：String，告警状态，已解除或未解除
	     *   &nbsp;&nbsp;&nbsp;&nbsp; source：String，告警来源
	     * **/
	  public static JSONObject getPaginationWarns(Boolean status,int col,int page){
		  DataServerUtil dataService=new DataServerUtil("warn/getPaginationWarns");
		  String params="status="+status+"&col="+col+"&page="+page;
		  return dataService.getQueryData(params);  
	  }
	  /***
	     * 通过告警标识获取告警详情
	     * @param id long:告警标识
	     * @return JSONObject 包含以下字段：<br/>
	     *   statusCode: boolean，false表示查询失败；true表示查找成功<br/>
	     *   err:String 当statusCode为false时有效，表示失败的具体原因<br/>
	     *   totalPage:int 当statusCode为true时有效，表示符合条件的告警信息的页码数<br/>
	     *   JSONObject，当statusCode为true时有效，表示当前告警的详细信息，包含以下字段：<br/>
	     *   &nbsp;&nbsp;&nbsp;&nbsp; time：String，告警时间
	     *   &nbsp;&nbsp;&nbsp;&nbsp; type：String，告警类别，rainfall或temp
	     *   &nbsp;&nbsp;&nbsp;&nbsp; param：String，告警参数 1h、3h、6h、12h、24h、low、ave、high
	     *   &nbsp;&nbsp;&nbsp;&nbsp; infoWay：String,告警方式  由0、1组成的长度为3的字符串，分别表示短信、电话、邮件，为1表示采用，0表示不采用
	     *   &nbsp;&nbsp;&nbsp;&nbsp; context：String,告警详细内容 
	     *   &nbsp;&nbsp;&nbsp;&nbsp; stations：Long[],发生告警的各监测站的标识
	     *   &nbsp;&nbsp;&nbsp;&nbsp; liaisons：Long[],发送告警通知的各人员标识
	     *   &nbsp;&nbsp;&nbsp;&nbsp; title：String,告警标题  
	     *   &nbsp;&nbsp;&nbsp;&nbsp; status：String，告警状态，已解除或未解除
	     *   &nbsp;&nbsp;&nbsp;&nbsp; source：String，告警来源
	     * **/
	  public static JSONObject getWarnDetial(long id){
		  DataServerUtil dataService=new DataServerUtil("warn/getWarnDetial");
		  String params="id="+id;
		  return dataService.getQueryData(params);  
	  }
	  /***
	    * 删除告警
	    *@param id long:告警标识
	    *@return JSONObject 包含以下字段：<br/>
	    *   statusCode: boolean，false表示查询失败；true表示查找成功<br/>
	    *   err:String 当statusCode为false时有效，表示失败的具体原因<br/> 
	    * ***/
	  public static JSONObject delWarn(long id){
		  DataServerUtil dataService=new DataServerUtil("warn/delWarn");
		  String params="id="+id;
		  return dataService.getQueryData(params);  
	  }
	  /**
	   * 发布预警信息
	   * @param warn JSONObject:包含预警详细信息的JSONObject，必须含有以下内容：<br/>
	   *  &nbsp;&nbsp;&nbsp;&nbsp; time：String，告警时间
       *   &nbsp;&nbsp;&nbsp;&nbsp; item：String，告警类别，rainfall或temp
       *   &nbsp;&nbsp;&nbsp;&nbsp; param：String，告警参数 1h、3h、6h、12h、24h、low、ave、high
       *   &nbsp;&nbsp;&nbsp;&nbsp; infoWay：String,告警方式  由0、1组成的长度为3的字符串，分别表示短信、电话、邮件，为1表示采用，0表示不采用
       *   &nbsp;&nbsp;&nbsp;&nbsp; context：String,告警详细内容 
       *   &nbsp;&nbsp;&nbsp;&nbsp; stations：String[],发生告警的各监测站的编号
       *   &nbsp;&nbsp;&nbsp;&nbsp; liaisons：Long[],发送告警通知的各人员标识
       *   &nbsp;&nbsp;&nbsp;&nbsp; title：String,告警标题  
       *   &nbsp;&nbsp;&nbsp;&nbsp; status：String，告警状态，已解除或未解除
       *   &nbsp;&nbsp;&nbsp;&nbsp; source：String，告警来源，发布告警的人员
	   * @return JSONObject 包含以下字段：<br/>
	   *  statusCode: boolean，false表示发布失败；true表示发布成功<br/>
	   *  err:String 当statusCode为false时有效，表示失败的具体原因<br/> 
     * ***/
	  public static JSONObject releaseWarn(JSONObject warn){
		  DataServerUtil dataService=new DataServerUtil("warn/releaseWarn");
		  String params="warn="+warn;
		  return dataService.getQueryData(params);  
	  }
	 /***
	  * 解除告警
	  *@param id long:告警标识
	  *@return JSONObject 包含以下字段：<br/>
	  *  statusCode: boolean，false表示解除失败；true表示解除成功<br/>
	  *  err:String 当statusCode为false时有效，表示失败的具体原因<br/> 
	  * ***/
	  public static JSONObject relieveWarn(long id){
		  DataServerUtil dataService=new DataServerUtil("warn/relieveWarn");
		  String params="id="+id;
		  return dataService.getQueryData(params);  
	  }
	  
	/****基础信息相关****/  
	  
	  /***
		 * 新增基础信息
		 * @param basis JSON格式的字符串，包含易受灾点的各字段信息，含有以下字段：<br/>
		 *   title String:基信标题<br/>
		 *   context String:基信内容<br/>
		 *   description String:描述<br/>
		 * @return JSONObject  包含以下信息：<br/>:
		 *   statusCode boolean:表示执行结果<br/>
		 *   err String:当statusCode为false时有效，表示失败原因
		 * ***/
	  public static JSONObject addBasis(JSONObject basis){
		  DataServerUtil dataService=new DataServerUtil("basis/addBasis");
		  String params="basis="+basis;
		  return dataService.getQueryData(params);  
	  }
	  /***
		 * 修改基础信息
		 * @param id long:要修改的基信的标识
		 * @param basis JSON格式的字符串，包含易受灾点的各字段信息，含有以下字段：<br/>
		 *   title String:基信标题<br/>
		 *   context String:基信内容<br/>
		 *   description String:描述<br/>
		 * @return JSONObject  包含以下信息：<br/>:
		 *   statusCode boolean:表示执行结果<br/>
		 *   err String:当statusCode为false时有效，表示失败原因
		 * ***/
	  public static JSONObject modifyBasis(long id,JSONObject basis){
		  DataServerUtil dataService=new DataServerUtil("basis/modifyBasis");
		  String params="id="+id+"&basis="+basis;
		  return dataService.getQueryData(params);  
	  }	  
	  /***
		 * 删除基础信息
		 * @param id long:要删除的基信的标识
		 * @return JSONObject  包含以下信息：<br/>:
		 *   statusCode boolean:表示执行结果<br/>
		 *   err String:当statusCode为false时有效，表示失败原因
		 * ***/
	  public static JSONObject delBasis(long id){
		  DataServerUtil dataService=new DataServerUtil("basis/delBasis");
		  String params="id="+id;
		  return dataService.getQueryData(params);  
	  }	
	  /***
	     * 通过基信标题、表格列数以及当前页码分页查找基础信息
	     * @param title String :基信标题，空字符串表示全部
	     * @param col int:表格列数
	     * @param page int:当前页码
	     * @return JSONObject 包含以下字段：<br/>
	     *   statusCode: boolean，false表示查询失败；true表示查找成功<br/>
	     *   err:String 当statusCode为false时有效，表示失败的具体原因<br/>
	     *   totalPage:int 当statusCode为true时有效，表示符合条件的基信的页码数<br/>
	     *   basis：JSONArray，当statusCode为true时有效，表示符合条件的基信记录，每一个元素为一个JSONObject，包含以下字段：<br/>
	     *   &nbsp;&nbsp;&nbsp;&nbsp; id：long，基信标识
	     *   &nbsp;&nbsp;&nbsp;&nbsp; title：String,基信标题
	     *   &nbsp;&nbsp;&nbsp;&nbsp; description： String基信描述
	     * **/
	  public static JSONObject getPaginationBasis(String title,int col,int page){
		  DataServerUtil dataService=new DataServerUtil("basis/getPaginationBasis");
		  String params="title="+title+"&col="+col+"&page="+page;
		  return dataService.getQueryData(params);  
	  }
	  /***
	     * 通过基信标识获取基信详情
	     * @param id long:基信标识
	     * @return JSONObject 包含以下字段：<br/>
	     *   statusCode: boolean，false表示查询失败；true表示查找成功<br/>
	     *   err:String 当statusCode为false时有效，表示失败的具体原因<br/>
	     *   JSONObject，当statusCode为true时有效，表示当前告警的详细信息，包含以下字段：<br/>
	     *   &nbsp;&nbsp;&nbsp;&nbsp; title：String，基信标题<br/>
	     *   &nbsp;&nbsp;&nbsp;&nbsp; context：String，基信内容</br>
	     *   &nbsp;&nbsp;&nbsp;&nbsp; description：String，基信描述</br>
	     * **/
	  public static JSONObject getBasisDetial(long id){
		  DataServerUtil dataService=new DataServerUtil("basis/getBasisDetial");
		  String params="id="+id;
		  return dataService.getQueryData(params);  
	  } 
	/***易受灾点相关****/
	 /**
	  * 新增易受灾点
	  *  @param vulner JSONObject封装易受灾点信息的JSON对象,包含以下字段：<br/>
	  *  		name: 易受灾点名称<br/>
	  *         cnty：所在乡镇<br/>
	  *         lat：纬度<br/>
	  *         lng：经度<br/>
	  *         ecpName：紧急联系人<br/>
	  *         ecpPhone：联系人手机号<br/>
	  *         ecpEmail：联系人邮箱<br/>
	  *         resPop：常住人口<br/>
	  *         evaRoute：疏散路线<br/>
	  *         type：易受灾类型<br/>
	  *  @return   JSONObject包括:<br/>
	   *       statusCode:boolean 标识查新状态<br/>
	   *       err:错误原因     
	 * ***/  
	  public static JSONObject addVulner(JSONObject vulner){
		  DataServerUtil dataService=new DataServerUtil("vulner/addVulner");
		  String params="vulner="+vulner.toString();
		  return dataService.getQueryData(params);  
	  }
    /**
     * 修改易受灾点
     *  @param id long 要修改的易受灾点的ID
     *  @param vulner JSONObject封装易受灾点信息的JSON对象,包含以下字段：<br/>
	  *  		name: 易受灾点名称<br/>
	  *         cnty：所在乡镇<br/>
	  *         lat：纬度<br/>
	  *         lng：经度<br/>
	  *         ecpName：紧急联系人<br/>
	  *         ecpPhone：联系人手机号<br/>
	  *         ecpEmail：联系人邮箱<br/>
	  *         resPop：常住人口<br/>
	  *         evaRoute：疏散路线<br/>
	  *         type：易受灾类型<br/>
	  *  @return   JSONObject包括:<br/>
	   *       statusCode:boolean 标识查新状态<br/>
	   *       err:错误原因     
	 * ***/  	  
	  public static JSONObject modifyVulner(long id,JSONObject vulner){
		  DataServerUtil dataService=new DataServerUtil("vulner/modifyVulner");
		  String params="id="+id+"&vulner="+vulner.toString();
		  return dataService.getQueryData(params);  
	  } 
   /**
	* 删除易受灾点
	* @param id long 要删除的易受灾点ID
	* @return   JSONObject包括:<br/>
	*       statusCode:boolean 标识状态<br/>
	*       err:错误原因      
   * **/	  
	 public static JSONObject delVulner(long id){
		 DataServerUtil dataService=new DataServerUtil("vulner/delVulner");
		 String params="id="+id;
		 return dataService.getQueryData(params);  
	 } 
	 /**查看易受灾点详情
	  *  @param id long 易受灾点ID
	  *  @return JSONObject 包含以下字段：<br/>
	 *          statusCode:boolean 标识状态<br/>
	 *          err:错误信息，statusCode 为false时有效
	 *          vulner:JSONA 为JSONObject，包含以下字段:<br/>
	 *         			id: 易受灾点标识<br/>
	 *            		name: 易受灾点名称<br/>
	 *         			cnty：所在乡镇<br/>
	 *         			lat：纬度<br/>
	 *         			lng：经度<br/>
	 *         			ecpName：紧急联系人<br/>
	 *                  description:描述<br/>
	 *         			ecpPhone：联系人手机号<br/>
	 *         			ecpEmail：联系人邮箱<br/>
	 *        			resPop：常住人口<br/>
	 *         			evaRoute：疏散路线<br/>
	 *         			type：易受灾类型<br/>
	 * ***/
	 public static JSONObject checkVulnerDetial(long id){
		 DataServerUtil dataService=new DataServerUtil("vulner/checkDetail");
		 String params="id="+id;
		 return dataService.getQueryData(params);  
	 } 
   /**
	 * 获取所有易受灾点
	 * @return JSONObject 包含以下字段：<br/>
	 *          statusCode:boolean 标识状态<br/>
	 *          vulners:JSONArray 为所有的易受灾点，每个元素为一个JSONObject，包含以下字段:<br/>
	 *         			id: 易受灾点标识<br/>
	 *            		name: 易受灾点名称<br/>
	  *         		cnty：所在乡镇<br/>
	  *         		lat：纬度<br/>
	  *         		lng：经度<br/>
	  *        			description:描述<br/>
	  *         		ecpName：紧急联系人<br/>
	  *         		ecpPhone：联系人手机号<br/>
	  *         		ecpEmail：联系人邮箱<br/>
	  *        			resPop：常住人口<br/>
	  *         		evaRoute：疏散路线<br/>
	  *         		type：易受灾类型<br/>
    * **/	  
	  public static JSONObject getAllVulners(){
		  DataServerUtil dataService=new DataServerUtil("vulner/getAllVulners");
		  String params="";
		  return dataService.getQueryData(params);  
	  } 
	  /***
	     * 通过标题、表格列数以及当前页码分页查找易受灾点信息
	     * @param title String :易受灾点标题，空字符串表示全部
	     * @param col int:表格列数
	     * @param page int:当前页码
	     * @return JSONObject 包含以下字段：<br/>
	     *   statusCode: boolean，false表示查询失败；true表示查找成功<br/>
	     *   err:String 当statusCode为false时有效，表示失败的具体原因<br/>
	     *   totalPage:int 当statusCode为true时有效，表示符合条件的易受灾点的页码数<br/>
	     *   vulners：JSONArray，当statusCode为true时有效，表示符合条件的基信记录，每一个元素为一个JSONObject，包含以下字段：<br/>
	     *   &nbsp;&nbsp;&nbsp;&nbsp; id：long，易受灾点标识
	     *   &nbsp;&nbsp;&nbsp;&nbsp; title：String,易受灾端标题
	     *   &nbsp;&nbsp;&nbsp;&nbsp; description： String易受灾点描述
	     * **/
	  public static JSONObject getPaginationVulner(String title ,int col,int page){
		  DataServerUtil dataService=new DataServerUtil("vulner/getPaginationVulner");
		  String params="title="+title+"&col="+col+"&page="+page;
		  return dataService.getQueryData(params);  
	  }
	/*********************监测站相关************************/  
	  /***
	   * 新增监测站
	   * @param station JSONObject,新增监测站的详细信息，包含以下信息<br/>
	   *      &nbsp;&nbsp;&nbsp;&nbsp;  name:监测站名称<br/>
	   *      &nbsp;&nbsp;&nbsp;&nbsp;  areaCode:所在地区编号<br/>
	   *      &nbsp;&nbsp;&nbsp;&nbsp;  code:监测站编号 <br/>
	   *      &nbsp;&nbsp;&nbsp;&nbsp;  type:监测站类型 ,雨量、温度或者混合型<br/>
	   *      &nbsp;&nbsp;&nbsp;&nbsp;  description：描述<br/>
	   *      &nbsp;&nbsp;&nbsp;&nbsp;  cnty：监测站所在乡镇<br/>    
	   *      &nbsp;&nbsp;&nbsp;&nbsp;  lng:经度<br/>
	   *      &nbsp;&nbsp;&nbsp;&nbsp;  lat：纬度<br/>
	   *@return  JSONObject:包含以下两个字段：<br/>     
	   *      &nbsp;&nbsp;&nbsp;&nbsp; statusCode:boolean 执行状态<br/> 
	   *      &nbsp;&nbsp;&nbsp;&nbsp; err:错误信息                
	   * ***/
	  public static JSONObject addStation(JSONObject station){
    	  DataServerUtil dataService=new DataServerUtil("station/addStation");
		  String params="station="+station;
		  return dataService.getQueryData(params);  
      }
	  /**
	   * 查看监测站详情
	   * @param code String 监测站代号
	   * @return JSONObject 包含以下参数:<br/>
	   *   &nbsp;&nbsp; statusCode： 为boolean型，表明状态<br/>
	   *   &nbsp;&nbsp; err：String 错误信息<br/>
	   *   &nbsp;&nbsp; station：监测站的详细参数，为JSONObject，包含以下字段：<br/> 
	   *   &nbsp;&nbsp;&nbsp;&nbsp; name:名称 <br/> 
	   *   &nbsp;&nbsp;&nbsp;&nbsp; code:编号 <br/> 
	   *   &nbsp;&nbsp;&nbsp;&nbsp; lat: 纬度<br/> 
	   *   &nbsp;&nbsp;&nbsp;&nbsp; lng: 经度<br/> 
	   *   &nbsp;&nbsp;&nbsp;&nbsp; description:描述<br/> 
	   *   &nbsp;&nbsp;&nbsp;&nbsp; type:类型<br/>  
	   *   &nbsp;&nbsp;&nbsp;&nbsp; cnty:所在乡镇 <br/> 
	   *   &nbsp;&nbsp;&nbsp;&nbsp; areaName:所在地区 <br/> 
	   *   
	   * */
	  public static JSONObject checkStationDetial(String code){
    	  DataServerUtil dataService=new DataServerUtil("station/checkStationDetial");
		  String params="code="+code;
		  return dataService.getQueryData(params);  
      }
	  /***
	   * 修改监测站
	   * @param code String 监测站编号
	   * @param station JSONObject 要修改的key-value，包含以下字段：<br/>
	   *  &nbsp;&nbsp;&nbsp;&nbsp; description:监测站描述<br/>
	   *  &nbsp;&nbsp;&nbsp;&nbsp; name：监测站名称<br/>
	   *  &nbsp;&nbsp;&nbsp;&nbsp; type：监测站类型<br/>
	   *  &nbsp;&nbsp;&nbsp;&nbsp; lat：监测站纬度<br/>
	   *  &nbsp;&nbsp;&nbsp;&nbsp; lng：监测站经度<br/.
	   * @return  JSONObject:包含以下两个字段：<br/>     
	   *  &nbsp;&nbsp;&nbsp;&nbsp; statusCode:boolean 执行状态<br/> 
	   *  &nbsp;&nbsp;&nbsp;&nbsp; err:错误信息     
	   * **/
	  public static JSONObject modifyStation(String code,JSONObject station){
    	  DataServerUtil dataService=new DataServerUtil("station/modifyStation");
		  String params="station="+station+"&code="+code;
		  return dataService.getQueryData(params);  
      }
	  /***
	   * 删除监测站
	   * @param code String 欲删除的监测站的编号
	   * @return  JSONObject:包含以下两个字段：<br/>     
	   *  &nbsp;&nbsp;&nbsp;&nbsp; statusCode:boolean 执行状态<br/> 
	   *  &nbsp;&nbsp;&nbsp;&nbsp; err:错误信息     
	   * ***/
	  public static JSONObject delStation(String code){
    	  DataServerUtil dataService=new DataServerUtil("station/delStation");
		  String params="code="+code;
		  return dataService.getQueryData(params);  
      }
    /***分页获取监测站点概要，用于基础信息列表
	* @param col int:前端表格的行数 
	* @param page int：当前页数
	* @param name String:监测站名称，用于模糊查询，为空字符串时查找所有
	 *@return JSONObject 包括以下参数：<br/>
	 *   &nbsp;&nbsp; statusCode： 为boolean型，表明状态<br/>
	 *   &nbsp;&nbsp; err： 为String，当statusCode为false时有效，表明失败原因<br/>
	 *   &nbsp;&nbsp; totalPage： 为int，当statusCode为true时有效，表明总页数<br/>
	 *   &nbsp;&nbsp;stations:JSONArray，当statusCode为true时有效，表示各监测站点信息，每个元素为一个JSONObject，包含以下字段：<br/>
	 *   &nbsp;&nbsp;&nbsp;&nbsp; code：监测站编号<br/>
	 *   &nbsp;&nbsp;&nbsp;&nbsp; name：监测站名称<br/>
	 *   &nbsp;&nbsp;&nbsp;&nbsp; description：监测站描述<br/>
	 */
      public static JSONObject getStationsOutline(int col,int page,String name){
    	  DataServerUtil dataService=new DataServerUtil("station/getStationsOutline");
		  String params="col="+col+"&page="+page+"&name="+name;
		  return dataService.getQueryData(params);  
      }
      /***获取所有监测站点的名称和
	   * 经纬度信息用于绘制GIS地图
	   * @return JSONObject 包括两个参数<br/>
	   *   &nbsp;&nbsp; statusCode： 为boolean型，表明状态<br/>
	   *   &nbsp;&nbsp;stations:JSONArray，各监测站点，为每个元素为一个JSONObject，包含以下字段：<br/>
	   *   &nbsp;&nbsp;&nbsp;&nbsp; name:监测站名称<br/>
	   *   &nbsp;&nbsp;&nbsp;&nbsp; lng：监测站经度<br/>
	   *   &nbsp;&nbsp;&nbsp;&nbsp; lat：监测站纬度<br/>
	   *   &nbsp;&nbsp;&nbsp;&nbsp; rainfall_1h：小时降雨量<br/>
	   *   &nbsp;&nbsp;&nbsp;&nbsp; temp_ave：平均温度<br/>
	   *   &nbsp;&nbsp;&nbsp;&nbsp; humi_re：相对湿度<br/>
	   * **/
      public static JSONObject getStationsForGIS(){
    	  DataServerUtil dataService=new DataServerUtil("station/getStationsForGIS");
    	  String items="rainfall_1h,temp_ave,humi_re";
		  String params="items="+items;
		  return dataService.getQueryData(params);  
      }
	/*********************监测站点气象相关**********************/
	 
	  /***
	   * 分页获取实况列表的数据
	   * @param areaCode 区域代号
	   * @param col 表格的行数
	   * @param page 当前页码数
	   * @return JSONObject 包含以下字段：<br/>
	   *     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  statusCode： 为boolean型，表明状态<br/>
	   *     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  err:String,当statusCode为false时该字段有效，表明查询失败的原因<br/>
	   *     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; totalPage：int 当statusCode为true时该字段有效，表示总页码数<br/>
	   *     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  stations：JSONArray，当 statusCode为true时有效，包含符合条件的监测站的实况信息，每个元素为一个JSONObject，包含以下信息：<br/>
	   *     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  area:监测站所属区域<br/>  
	   *     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  cnty:监测站所在乡镇<br/>  
	   *     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  code:监测站代号<br/>  
	   *     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  weather:监测站天气实况，为JSONOBject，包含以下字段：<br/>  
	   *     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  rainfall_1h:一小时雨量<br/>  
	   *     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  rainfall_1h:三小时雨量<br/>  
	   *     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  rainfall_6h:六小时雨量<br/>  
	   *     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  rainfall_12h:十二小时雨量<br/> 
	   *     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  temp_ave:平均温度<br/>  
	   *     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  temp_low:最低温度<br/>   
	   *     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  temp_high:最高温度<br/>   
	   * **/
	  public static JSONObject getPageRealTimeWeather(String areaCode,int col,int page){
		  DataServerUtil dataService=new DataServerUtil("weather/getPageRealTimeWeather");
		  String params="areaCode="+areaCode+"&col="+col+"&page="+page;
		  return dataService.getQueryData(params);  
	  }	
	  /***
	   * 根据监测站名称和区域编号模糊查询站点实况
	   * @param areaCode 区域代号，如果查询所有区域，将该字符串设置为空字符串
	   * @param stationName 监测站名称,如果查询全部监测站，将该字段设置为空字符串
	   * @param col 表格的行数
	   * @param page 当前页码数
	   * @return JSONObject 包含以下字段：<br/>
	   *     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  statusCode： 为boolean型，表明状态<br/>
	   *     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  err:String,当statusCode为false时该字段有效，表明查询失败的原因<br/>
	   *     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; totalPage：int 当statusCode为true时该字段有效，表示总页码数<br/>
	   *     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  stations：JSONArray，当 statusCode为true时有效，包含符合条件的监测站的实况信息，每个元素为一个JSONObject，包含以下信息：<br/>
	   *     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  area:监测站所属区域<br/>  
	   *     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  cnty:监测站所在乡镇<br/>  
	   *     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  code:监测站代号<br/>  
	   *     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  weather:监测站天气实况，为JSONOBject，包含以下字段：<br/>  
	   *     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  rainfall_1h:一小时雨量<br/>  
	   *     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  rainfall_1h:三小时雨量<br/>  
	   *     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  rainfall_6h:六小时雨量<br/>  
	   *     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  rainfall_12h:十二小时雨量<br/> 
	   *     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  temp_ave:平均温度<br/>  
	   *     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  temp_low:最低温度<br/>   
	   *     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  temp_high:最高温度<br/>   
	   * **/
	  public static JSONObject getVagueStationWeather(String areaCode,String stationName,int col,int page){
		  DataServerUtil dataService=new DataServerUtil("weather/getVagueStationWeather");
		  String params="areaCode="+areaCode+"&col="+col+"&page="+page+"&stationName="+stationName;
		  return dataService.getQueryData(params);  
	  }	
	  /***
	   * 获取某个监测站的详细实况信息
	   * @param stationCode 监测站代号
	   * @return JSONObject
	   * **/
	  public static JSONObject getDetialHourWeather(String stationCode){
		  DataServerUtil dataService=new DataServerUtil("weather/getDetialHourWeather");
		  String params="stationCode="+stationCode;
		  return dataService.getQueryData(params);  
	  }
	  /***
	   * 获取某个地区的特定天气参数分布数据
	   * @param areaCode 地区代号
	   * @param item 天气要素，对应数据库的字段，包括以下情形：<br/>
	   *   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;       temp_ave 平均温度<br/>
	   *   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;       temp_high最高温度<br/>
	   *   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;       temp_low 最低温度<br/>
	   *   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;       rainfall_1h 一小时雨量<br/>
	   *   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;       rainfall_3h 3小时雨量<br/>
	   *   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;       rainfall_6h 6小时雨量<br/>
	   *   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;       rainfall_12h 12小时雨量<br/>
	   *   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;       rainfall_24h 24小时雨量<br/>
	   * @return JSONObject，包括以下字段：<br/>
	   *   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;       statusCode:boolean 标识查找状态<br/>
	   *   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;       err:String 查找失败的原因，当statusCode为false时有效<br/>
	   *   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;       weathers:JSONArray各站点天气数值组，每个元素是一个JSONObject，包含一个站点的信息,有以下两个字段:<br/>
	   *   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;                stationName:监测站名称<br/>
	   *   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;                value:对应天气要素的值    
	   * **/
	  public static JSONObject getDistribution(String areaCode,String item){
		  areaCode=areaCode.isEmpty()?"520381":areaCode;
		  DataServerUtil dataService=new DataServerUtil("weather/getDistribution");
		  String params="areaCode="+areaCode+"&item="+item;
		  return dataService.getQueryData(params);  
	  }
  /***分布图相关**/	  
		  /****
		   * 用于访问数据库服务器，获取各监测站降雨量信息，用于绘制降雨量分布图
		   * @param areaCode 地区编号
		   * @param time String 需要获取的时间，1h、3h、6h、12h、24h
		   * @param item 气象要素 rainfall、temp、humi
		   * @return List<Map<String,Object>> 每个元素为一个map，包括以下key-value
		   * stationCode name value lat  lng  
		   * ***/
	  public static JSONObject getWeatherForGraph(String areaCode,String item, String time){
		  List<Map<String,Object>> weatherData=new ArrayList<>();
		  if(areaCode.isEmpty()){//如果为空，默认为赤水市
			  areaCode="520381";
		  }
		  JSONObject response=new JSONObject();
		  DataServerUtil dataService=new DataServerUtil("graph/getHourItem");
		  String param="areaCode="+areaCode+"&item="+item+"&time="+time;
		  JSONObject queryResult=dataService.getQueryData(param);
		  if(queryResult.getBoolean("statusCode")){
			  JSONArray rainfalls=JSONArray.fromObject(queryResult.get("rainfall"));
			  for(int i=0;i<rainfalls.size();i++){
				  Map<String,Object> rain=new HashMap<>();
				  JSONObject railfall=rainfalls.getJSONObject(i);
				  @SuppressWarnings("unchecked")
				  Iterator<String> it=railfall.keys();
				  while(it.hasNext()){
					  String key=(String) it.next();
					  rain.put(key, railfall.get(key));
				  }
				  weatherData.add(rain);
			  }
			  response.put("weather", weatherData);
		  }else{
			  response.put("err", queryResult.get("err"));
		  }
		 response.put("statusCode", queryResult.get("statusCode"));
		return response;
	  }
	  /**
		  * 根据开始时间、截止时间获取赤水市各监测站的温度或雨量信息
		  * @param startTime 开始时间 形如20170310020000
		  * @param endTime 截止时间  形如20170310020000
		  * @param item 气象要素,rainfall、temp_ave、temp_high、temp_low
		  * @return 返回的是一个JSONObject；
		  * statusCode true表示成功  false表示失败；
		 * 
		  * **/
	  public static JSONObject getHisWeatherForGraph(String startTime,String endTime, String item){
		  List<Map<String,Object>> weatherData=new ArrayList<>();
		  JSONObject response=new JSONObject();
		  DataServerUtil dataService=new DataServerUtil("graph/getHisHourItem");
		  String param="startTime="+startTime+"&item="+item+"&endTime="+endTime;
		  JSONObject queryResult=dataService.getQueryData(param);
		  if(queryResult.getBoolean("statusCode")){
			  JSONArray rainfalls=JSONArray.fromObject(queryResult.get("infos"));
			  for(int i=0;i<rainfalls.size();i++){
				  Map<String,Object> rain=new HashMap<>();
				  JSONObject railfall=rainfalls.getJSONObject(i);
				  @SuppressWarnings("unchecked")
				  Iterator<String> it=railfall.keys();
				  while(it.hasNext()){
					  String key=(String) it.next();
					  rain.put(key, railfall.get(key));
				  }
				  weatherData.add(rain);
			  }
			  response.put("weather", weatherData);
		  }else{
			  response.put("err", queryResult.get("err"));
		  }
		 response.put("statusCode", queryResult.get("statusCode"));
		return response;
	  }
/****气象产品相关****/  
	  
	  /***
		 * 产品制作
		 * @param product JSON格式的字符串，包含产品的各字段信息，含有以下字段：<br/>
		 *   type String:产品类型<br/>
		 *   title String:产品标题<br/>
		 *   context String:产品内容<br/>
		 *   author String:拟稿人<br/>
		 * @return JSONObject  包含以下信息：<br/>:
		 *   statusCode boolean:表示执行结果<br/>
		 *   err String:当statusCode为false时有效，表示失败原因
		 * ***/
	  public static JSONObject makeProduct(JSONObject product){
		  DataServerUtil dataService=new DataServerUtil("product/makeProduct");
		  String params="product="+product;
		  return dataService.getQueryData(params);  
	  }
	  /***
		 * 删除产品
		 * @param id long:要删除的cp的标识
		 * @return JSONObject  包含以下信息：<br/>:
		 *   statusCode boolean:表示执行结果<br/>
		 *   err String:当statusCode为false时有效，表示失败原因
		 * ***/
	  public static JSONObject delProduct(long id){
		  DataServerUtil dataService=new DataServerUtil("product/delProduct");
		  String params="id="+id;
		  return dataService.getQueryData(params);  
	  }	
	  /***
	     * 通过产品类型、表格列数以及当前页码分页查找气象产品信息
	     * @param type String:产品类型，空字符串表示全部
	     * @param col int:表格列数
	     * @param page int:当前页码
	     * @return JSONObject 包含以下字段：<br/>
	     *   statusCode: boolean，false表示查询失败；true表示查找成功<br/>
	     *   err:String 当statusCode为false时有效，表示失败的具体原因<br/>
	     *   totalPage:int 当statusCode为true时有效，表示符合条件的基信的页码数<br/>
	     *   products：JSONArray，当statusCode为true时有效，表示符合条件的产品记录，每一个元素为一个JSONObject，包含以下字段：<br/>
	     *   &nbsp;&nbsp;&nbsp;&nbsp; id：long，产品标识
	     *   &nbsp;&nbsp;&nbsp;&nbsp; title：String,产品标题
	     *   &nbsp;&nbsp;&nbsp;&nbsp; type：String,产品类型
	     *   &nbsp;&nbsp;&nbsp;&nbsp; author：String拟稿人
	     *   &nbsp;&nbsp;&nbsp;&nbsp; createDate：String创建时间
	     * **/
	  public static JSONObject getPaginationProduct(String type,int col,int page){
		  DataServerUtil dataService=new DataServerUtil("product/getPaginationProduct");
		  String params="type="+type+"&col="+col+"&page="+page;
		  return dataService.getQueryData(params);  
	  }
	  /***
	     * 通过产品标识获取产品详情
	     * @param id long:产品标识
	     * @return JSONObject 包含以下字段：<br/>
	     *   statusCode: boolean，false表示查询失败；true表示查找成功<br/>
	     *   err:String 当statusCode为false时有效，表示失败的具体原因<br/>
	     *   product JSONObject，当statusCode为true时有效，表示当前产品详细信息，包含以下字段：<br/>
	     *   &nbsp;&nbsp;&nbsp;&nbsp; title：String，产品标题<br/>
	     *   &nbsp;&nbsp;&nbsp;&nbsp; context：String，产品内容</br>
	     *   &nbsp;&nbsp;&nbsp;&nbsp; author：String，拟稿人</br>
	     * **/	
	  public static JSONObject getProductDetial(long id){
		  DataServerUtil dataService=new DataServerUtil("product/getProductDetial");
		  String params="id="+id;
		  return dataService.getQueryData(params);  
	  } 
	  /***
	     * 通过产品标识获取产品详情和相关的三条产品的概要信息
	     * @param id long:产品标识，为0时查询最新一条
	     * @return JSONObject 包含以下字段：<br/>
	     *   statusCode: boolean，false表示查询失败；true表示查找成功<br/>
	     *   err:String 当statusCode为false时有效，表示失败的具体原因<br/>
	     *   product JSONObject，当statusCode为true时有效，表示当前产品详细信息，包含以下字段：<br/>
	     *   &nbsp;&nbsp;&nbsp;&nbsp; title：String，产品标题<br/>
	     *   &nbsp;&nbsp;&nbsp;&nbsp; context：String，产品内容</br>
	     *   &nbsp;&nbsp;&nbsp;&nbsp; author：String，拟稿人</br>
	     *   &nbsp;&nbsp;&nbsp;&nbsp; time：String，发布时间</br>
	     *   &nbsp;&nbsp;&nbsp;&nbsp; reviewer：String，审核人</br>
	     *   relevants JSONArray:相关产品，每个元素为一个JSON对象，包括以下字段</br>:
	     *   	id :产品标识
	     *      title:产品名称
	     *      image:图片url 
	     * **/	
	  public static JSONObject getLastProduct(long id,String type){
		  DataServerUtil dataService=new DataServerUtil("product/getLastProduct");
		  String params="id="+id+"&type="+type;
		  return dataService.getQueryData(params);  
	  } 
	 /***配置相关***/

	  /**
	   * 测试邮件服务器
	   * @param emailServer JSONObject:邮件服务器的相关参数，包含以下信息:<br/>
	   * 	address String:服务器地址<br/>
	   *    port    int:服务器端口<br/>
	   *    account String:用户账号<br/>
	   *    pword String:邮箱密码<br/>
	   @return JSONObject 包含以下字段：<br/>
	     *   statusCode: boolean，false表示当前配置可用；true表示当前配置不可用<br/>
	     *   err:String 当statusCode为false时有效，表示失败的具体原因<br/>
	   * **/
	  public static JSONObject checkEmailServer(JSONObject emailServer){
		  DataServerUtil dataService=new DataServerUtil("configure/emailServer/checkServer");
		  String params="emailServer="+emailServer;
		  return dataService.getQueryData(params);  
	  } 
	  /**
	   * 配置邮件服务器
	   * @param emailServer JSONObject:邮件服务器的相关参数，包含以下信息:<br/>
	   * 	address String:服务器地址<br/>
	   *    port    int:服务器端口<br/>
	   *    account String:用户账号<br/>
	   *    pword String:邮箱密码<br/>
	   @return JSONObject 包含以下字段：<br/>
	     *   statusCode: boolean，false表示当前配置成功；true表示当前配置失败<br/>
	     *   err:String 当statusCode为false时有效，表示失败的具体原因<br/>
	   * **/
	  public static JSONObject setEmailServer(JSONObject emailServer){
		  DataServerUtil dataService=new DataServerUtil("configure/emailServer/setServer");
		  String params="emailServer="+emailServer;
		  return dataService.getQueryData(params);  
	  } 
	  /**获取邮件服务器信息
	   * 
	   @return JSONObject 包含以下字段：<br/>
	     *   statusCode: boolean，false表示当前配置成功；true表示当前配置失败<br/>
	     *   err:String 当statusCode为false时有效，表示失败的具体原因<br/>
	     *   emailService JSONObject:当statusCode为true时有效，表示邮件服务器的基本信息，包含以下字段：<br/>
	     *    address String:服务器地址<br/>
	     *    port    int:服务器端口<br/>
	     *    account String:用户账号<br/> 
	     * **/
	  public static JSONObject getEmailServer(){
		  DataServerUtil dataService=new DataServerUtil("configure/emailServer/getServer");
		  String params="";
		  return dataService.getQueryData(params);  
	  } 
	  /***用于测试**/
		public static void  main(String[] as){
			JSONObject product=new JSONObject();
			product.put("author", "李四");
			product.put("type", "雨情快报");
			product.put("title", "2017年6月6日早间雨情快报");
			product.put("context", "2017年6月6日早间雨情快报，测试");
			JSONObject responseData=checkVulnerDetial(1l);	
			
			System.out.println(responseData); 
		}
}
 