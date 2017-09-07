package weather.voice.models;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "voiceNotify")
public class VoiceNotify {
	private String appId;
	private String to;
	private String type;
	private String content;
	private String playTimes;
	/**
	 * set方法
	 * **/
	public void setAppId(String appId){
		this.appId = appId;
	}
	public void setTo(String to){
		this.to = to;
	}
	public void setType(String type){
		this.type = type;
	}
	public void setContent(String content){
		this.content = content;
	}
	public void setPlayTimes(String playTimes){
		this.playTimes = playTimes;
	}
	/**
	 * get方法
	 * **/
	public String getAppId(){
		return this.appId;
	}
	public String getTo(){
		return this.to;
	}
	public String getType(){
		return this.type;
	}
	public String getContent(){
		return this.content;
	}
	public String getPlayTimes(){
		return this.playTimes;
	}
}
