$(document).ready(function () {
	setAccountInfo();//填充用户名
    /**点击账户logo，弹出账户管理界面,点击其他位置关闭用户管理页面**/
    window.onload=function() {
        var oLink=document.getElementById('account');
        var oBox=document.getElementById('accinfo');
        oLink.onclick=function(ev) {
            var ev = ev || window.event;
            oBox.style.display='block';
            ev.cancelBubble=true;

        };
        oBox.onclick=function(ev) {
            var ev = ev || window.event;
            ev.cancelBubble=true;
        };
        document.onclick=function() {
            oBox.style.display='none';
        };
    }

    $(".accmng").click(function() {
        $("#loaddiv").html("");
        $("#loaddiv").load("html/account/accmng.html");
        $("#load").css({"display":"block"});
        $(".accinfo").css({"display":"none"});
    });

    $(".pswd").click(function() {
        $("#loaddiv").html("");
        $("#loaddiv").load("html/account/pswd.html");
        $("#load").css({"display":"block"});
        $(".accinfo").css({"display":"none"});
    });

    $(".accmng").click(function() {
        $("#loaddiv").load("html/account/accmng.html");
        $("#load").css({"display":"block"});
        $(".accinfo").css({"display":"none"});
    });

    $(".pswd").click(function() {
        $("#loaddiv").load("html/account/pswd.html");
        $("#load").css({"display":"block"});
        $(".accinfo").css({"display":"none"});
    });
})
/**
 * 获取当前登录用户的用户名**/
function setAccountInfo() {
	$.ajax({
		type: 'post', //数据发送方式
		dataType: 'json', //接受数据格式
		async: false,
		url: 'account/getAccountInfo',
		data: '',
		success: function (json) {
			if (json.statusCode) {
				$(".userAccount").text(json.account);
				$(".username").text(json.account);
				$(".email").text(json.email);
				$(".phone").text(json.phone);
			}
		}
	})
}

/**
 * 注销账号**/
function logout() {
	$.ajax({
		type: 'post', //数据发送方式
		dataType: 'json', //接受数据格式
		async: false,
		url: 'account/logout',
		data: '',
		success: function (json) {
			if (json.statusCode) {
				localStorage.clear();
				window.location="Login.html";
			}
		},
		error:function(XMLHttpRequest,Error){
			localStorage.clear();
			window.location="Login.html";
		}
	})
}
/***div拖拽***/
var offset_x;
var offset_y;
function startMove(oEvent,divId){
	var whichButton;
	if(document.all&&oEvent.button==1)
		  whichButton=true;
   else {
	   if(oEvent.button==0)
		whichButton=true;
	}
	if(whichButton){ 
	    var oDiv=divId;
		offset_x=parseInt(oEvent.clientX-oDiv.offsetLeft);
		offset_y=parseInt(oEvent.clientY-oDiv.offsetTop);
		document.documentElement.onmousemove=function(mEvent){    
		    var eEvent;
		    if(document.all){
		          eEvent=event;
		    }
		    else{
		       eEvent=mEvent;
		    }
		    var oDiv=divId;
		    var x=eEvent.clientX-offset_x;
		    var y=eEvent.clientY-offset_y;
		    oDiv.style.left=(x)+"px";
		    oDiv.style.top=(y)+"px";
	   }
   }
}
//停止移动
function stopMove(oEvent){
	document.documentElement.onmousemove=null; 
}

