package weather.shiro.realm;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

import net.sf.json.JSONObject;
import weather.util.CallDataService;

public class ShiroDaoRealm extends AuthorizingRealm{
   /**用于鉴定权限**/
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		//获取当前登录的用户名
		String account = (String) super.getAvailablePrincipal(principals);
		//用户的角色
		List<String> roles=new ArrayList<String>();  
		//角色所拥有的权限
		List<String> permissions = new ArrayList<String>();
		JSONObject json=CallDataService.getPerms(account);
		@SuppressWarnings("unchecked")
		List<String>  perms=(List<String>) json.get("perms");
		if(json.getBoolean("statusCode")){
				roles.add(json.getString("role"));
				 Subject currentUser=SecurityUtils.getSubject();//获取当前用户
				 currentUser.getSession().setTimeout(28800000); //设置登录有效时间为八小时
				 for (String pmss : perms) {
					if(!StringUtils.isEmpty(pmss)){
						permissions.add(pmss);
					}
				}
		}else{
			throw new AuthorizationException();
		}
		//给当前用户设置角色
		info.addRoles(roles);
		//给当前用户设置权限
        info.addStringPermissions(permissions); 
		return info;
		
	}

	/**
	 *  认证回调函数,验证用户输入的账户密码是否匹配，登录时调用.
	 *  获取用户信息
	 */
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		/**通过表单获取的用户信息**/
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
	    JSONObject accountInfo=CallDataService.getLoginInfo(token.getUsername());
		if (accountInfo.getBoolean("statusCode")) {
			return new SimpleAuthenticationInfo(accountInfo.getString("account"),accountInfo.getString("psw")," ");
		} else {
			return null;
		}
	}	
}