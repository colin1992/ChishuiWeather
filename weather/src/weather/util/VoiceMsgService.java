package weather.util;


import org.json.JSONException;
import org.json.JSONObject;
import weather.voice.ucpass.rest.client.JsonReqClient;

public class VoiceMsgService {
	public static final String accountSid ="15e27e5da177f29651bb9c99c4f5a1b8";
	public static final String authToken ="a031bf8c265f40c72dc6252d8b4c0afe";
	public static final String appId ="b13168d13e854336a2227402c183d45c";
	static JsonReqClient jsonReqClient = new JsonReqClient();
	/**
	 * 发送语音验证码
	 * @param phone 输入被通知人员的手机号
	 * @param verifyCode 发送验证码，为0-9的整数，且为4位或者6位
	 * @return 格式为json  status表示发送是否成功true表示成功，false表示失败  如果发送失败会返回respCode，为String类型，是具体的错误代号
	 * @throws JSONException 
	 * **/
	public static  JSONObject sendVoiceCode(String phone, String verifyCode){
		JSONObject result = null;
		try {
			result = new JSONObject(jsonReqClient.voiceCode(accountSid, authToken, appId, phone, verifyCode));
			if(((JSONObject) result.get("resp")).get("respCode").equals("000000")){
				result.put("status", true);
			}
			else{
				result.put("status", false);
				result.put("respCode", ((JSONObject) result.get("resp")).get("respCode"));
			}
			result.remove("resp");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;           		
	}
	/**
	 * 发送语音通知
	 * @param phone 输入被通知人员的手机号
	 * @param content 模板参数值,形式为json格式，如{“var1”:“aa”,“var2”:“bb”}
	 * @param playTimes 语音播放的次数,可选1,2,3
	 * @return boolean status表示发送是否成功true表示成功
	 * **/
	public static boolean sendVoiceMessage(String phone,String content,String playTimes){
		String type = "2"; //2{文字模板}
		String templateId = "1"; //模板id
		net.sf.json.JSONObject msg= new net.sf.json.JSONObject();
		msg.put("content", content);
		 /*
		    使用平台发送语音通知之前必须签建立模板，模板参数值,形式为json格式，如{“var1”:“aa”,“var2”:“bb”}，
		    将模板中定义一个参数content表示发送的内容，此处将消息内容封装为json格式
		*/
		boolean status=false;
		try {
			JSONObject result = new JSONObject(jsonReqClient.voiceMessage(accountSid, authToken, appId, phone,type, templateId,msg.toString(), playTimes));
			if(((JSONObject) result.get("resp")).get("respCode").equals("000000")){
				status=true;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status; 
	}
	/**
	 * 发送短信通知
	 * @param templateId 模板ID
	 * @param to 被通知人员手机号
	 * @param param 短信模板中需要填充的内容
	 * @return 格式为json  status表示发送是否成功true表示成功，false表示失败  如果发送失败会返回respCode，为String类型，是具体的错误代号
	 * @throws JSONException 
	 */
	public static JSONObject smsMessage(String to, String param) throws JSONException{	
		String templateId = "1";
		JSONObject result = null;
		result = new JSONObject(jsonReqClient.templateSMS(accountSid, authToken,
				appId, templateId, to, param));
		if(((JSONObject) result.get("resp")).get("respCode").equals("000000")){
			result.put("status", true);
		}
		else{
			result.put("status", false);
			result.put("respCode", ((JSONObject) result.get("resp")).get("respCode"));
		}
		result.remove("resp");
		return result;  
	}
	public static void main(String[] args) throws JSONException {
		// TODO Auto-generated method stub
		System.out.println(VoiceMsgService.sendVoiceCode("15528123706", "5678"));
		//System.out.println(VoiceMsgService.voiceMessage("", "恭喜你成功了！", "3"));
	}
}
