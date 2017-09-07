function validateForm(){
		if($("#user").val()==""){
			$(".msg").text("用户名不能为空");
			return false;
		}
		else if($("#password").val()==""){
			$(".msg").text("密码不能为空");
			return false;
		}
		else if($("#valid").val()==""){
			$(".msg").text("验证码不能为空");
			return false;
		}
		else if(!$.idcode.validateCode()){
			$(".msg").text("验证码错误。");
			return false;
		}
		return true;
}
$(document).ready(function() {
    $.idcode.setCode();
    $("#loginForm").validate({
		rules: {
			userName: {
				required: true
			},
			password: {
				required: true
			},
			valid: {
				required: true
			},
		},
		messages: {
			userName: {
				required: "请输入用户名"
			},
			password: {
				required: "请输入密码"
			},
			valid:{
				required: "请输入验证码"
			}
		}
	});
    $.validator.setDefaults({
    	submitHandler: function() {
    		return false;
    	}
    });
	$("#getPassword").click(function() {
		console.log("get pword");
		$("#loadPasswordDiv").load("login/html/getPassword.html"); 
		$("#loadPassword").css({"display":"block"});
	});	
});
function loginCheck(){
	if(!validateForm()){
		return;
	}
    $.ajax({
		cache: false,
		dataType:'json',
		type: "post",
		url:"dologin",
		data: $('#loginForm').serialize(),
		async: false,
		success: function(json) {
			if(json.statusCode){//登录成功
				localStorage.clear();
				var mainPage="index.html";
				if(json.isLeader){
					mainPage="mainL.html";
				}
				window.location.replace(mainPage);
			}else{//登录失败
				$(".msg").text(json.msg);
			}
		}
	});
 }
