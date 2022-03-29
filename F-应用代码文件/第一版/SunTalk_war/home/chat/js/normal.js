function update_img(){
	let i = 1;
	function start(){
		setInterval("document.getElementById('left').style.backgroundImage='url(img/0'+(i++%6)+'.jpg)'",2000);
	}
	start();
}
function check_my_identity(){
	window.location.href="html/main/admin.html";
}
function check_my_device(){
	function getDevice(){
	    let sUserAgent= navigator.userAgent.toLowerCase(); 
	    let bIsIpad= sUserAgent.match(/ipad/i) == "ipad"; 
	    let bIsIphoneOs= sUserAgent.match(/iphone/i) == "iphone";
	    let bIsMidp= sUserAgent.match(/midp/i) == "midp";
	    let bIsUc7= sUserAgent.match(/rv:1.2.3.4/i) == "rv:1.2.3.4";
	    let bIsUc= sUserAgent.match(/ucweb/i) == "ucweb";
	    let bIsAndroid= sUserAgent.match(/android/i) == "android";
	    let bIsCE= sUserAgent.match(/windows ce/i) == "windows ce";
	    let bIsWM= sUserAgent.match(/windows mobile/i) == "windows mobile";
	    if(bIsAndroid){
	         return "android";
	    }
	    else if(bIsIpad||bIsIphoneOs){
	         return "ios";
	    }
	    else return "pc";
	}
	
    let device = getDevice();
    let ios = 'index_phone.html',
	    android = 'index_phone.html';
    if(device=="android"){
	   window.location.href=android;
    }
    else if(device=="ios"){
	   window.location.href=ios;
    }else{
	    // window.location.href="copy-index.html";
    }
}
function exit(){
	let xhr=new XMLHttpRequest();
	xhr.open('GET','../../jsp/exit.jsp');
	xhr.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	xhr.onload=function(){}
	xhr.send();
}
function initEmoji(){//初始化内置表情
	const emojiList = [
		"[tv_白眼]", "[tv_doge]", "[tv_坏笑]", "[tv_难过]", "[tv_生气]",
		"[tv_委屈]", "[tv_斜眼笑]", "[tv_呆]", "[tv_发怒]", "[tv_惊吓]",
		"[tv_呕吐]", "[tv_思考]", "[tv_微笑]", "[tv_疑问]", "[tv_大哭]",
		"[tv_鼓掌]", "[tv_抠鼻]", "[tv_亲亲]", "[tv_调皮]", "[tv_笑哭]",
		"[tv_晕]", "[tv_点赞]", "[tv_害羞]", "[tv_睡着]", "[tv_色]",
		"[tv_吐血]", "[tv_无奈]", "[tv_再见]", "[tv_流汗]", "[tv_偷笑]",
		"[tv_抓狂]", "[tv_黑人问号]", "[tv_困]", "[tv_打脸]", "[tv_闭嘴]",
		"[tv_鄙视]", "[tv_腼腆]", "[tv_馋]", "[tv_可爱]", "[tv_发财]",
		"[tv_生病]", "[tv_流鼻血]", "[tv_尴尬]", "[tv_大佬]", "[tv_流泪]",
		"[tv_冷漠]", "[tv_皱眉]", "[tv_鬼脸]", "[tv_调侃]", "[tv_目瞪口呆]"
	];
	const emojiMap={
		"[tv_白眼]":"img/emoji/01.jpg",
		"[tv_doge]":"img/emoji/02.jpg",
		"[tv_坏笑]":"img/emoji/03.jpg",
		"[tv_难过]":"img/emoji/04.jpg",
		"[tv_生气]":"img/emoji/05.jpg",
		"[tv_委屈]":"img/emoji/06.jpg",
		"[tv_斜眼笑]":"img/emoji/07.jpg",
		"[tv_呆]":"img/emoji/08.jpg",
		"[tv_发怒]":"img/emoji/09.jpg",
		"[tv_惊吓]":"img/emoji/10.jpg",
		"[tv_呕吐]":"img/emoji/11.jpg",
		"[tv_思考]":"img/emoji/12.jpg",
		"[tv_微笑]":"img/emoji/13.jpg",
		"[tv_疑问]":"img/emoji/14.jpg",
		"[tv_大哭]":"img/emoji/15.jpg",
		"[tv_鼓掌]":"img/emoji/16.jpg",
		"[tv_抠鼻]":"img/emoji/17.jpg",
		"[tv_亲亲]":"img/emoji/18.jpg",
		"[tv_调皮]":"img/emoji/19.jpg",
		"[tv_笑哭]":"img/emoji/20.jpg",
		"[tv_晕]":"img/emoji/21.jpg",
		"[tv_点赞]":"img/emoji/22.jpg",
		"[tv_害羞]":"img/emoji/23.jpg",
		"[tv_睡着]":"img/emoji/24.jpg",
		"[tv_色]":"img/emoji/25.jpg",
		"[tv_吐血]":"img/emoji/26.jpg",
		"[tv_无奈]":"img/emoji/27.jpg",
		"[tv_再见]":"img/emoji/28.jpg",
		"[tv_流汗]":"img/emoji/29.jpg",
		"[tv_偷笑]":"img/emoji/30.jpg",
		"[tv_抓狂]":"img/emoji/31.jpg",
		"[tv_黑人问号]":"img/emoji/32.jpg",
		"[tv_困]":"img/emoji/33.jpg",
		"[tv_打脸]":"img/emoji/34.jpg",
		"[tv_闭嘴]":"img/emoji/35.jpg",
		"[tv_鄙视]":"img/emoji/36.jpg",
		"[tv_腼腆]":"img/emoji/37.jpg",
		"[tv_馋]":"img/emoji/38.jpg",
		"[tv_可爱]":"img/emoji/39.jpg",
		"[tv_发财]":"img/emoji/40.jpg",
		"[tv_生病]":"img/emoji/41.jpg",
		"[tv_流鼻血]":"img/emoji/42.jpg",
		"[tv_尴尬]":"img/emoji/43.jpg",
		"[tv_大佬]":"img/emoji/44.jpg",
		"[tv_流泪]":"img/emoji/45.jpg",
		"[tv_冷漠]":"img/emoji/46.jpg",
		"[tv_皱眉]":"img/emoji/47.jpg",
		"[tv_鬼脸]":"img/emoji/.48jpg",
		"[tv_调侃]":"img/emoji/49.jpg",
		"[tv_目瞪口呆]":"img/emoji/50.jpg"
	}
	let ulObj = document.getElementById("emoji");
	for(let i = 0;i < 45;i++){
		let liObj = document.createElement("li");
		liObj.className = "tv_emoji";
		let divObj = document.createElement("div");
		divObj.className = "tv_emoji_box";
		divObj.alt = emojiList[i];
		divObj.style.backgroundImage = "url("+emojiMap[emojiList[i]]+")";
		divObj.onclick = function (){aadEmoji('text_textarea_box',emojiList[i])};

		liObj.appendChild(divObj);
		ulObj.appendChild(liObj);
	}
}
function changeElementEye(elementId){
	let eye = document.getElementById(elementId);
	/* 在这里获取到的style属性初始值为空，不知道为什么
	 * 但是第二次不为空（值为hidden时）
	 */
	if(eye.style.visibility==""||eye.style.visibility=="hidden"){
		eye.style.visibility="visible";
	}else {
		eye.style.visibility="hidden";
	}
}
function aadEmoji(textarea_id,emojiName){
	let textarea = document.getElementById(textarea_id);
	textarea.value = textarea.value+emojiName;
}

function modifyPortrait(){
	let src = prompt("输入自定义头像网址,退出重新登录生效:\n(注：个别网址图片可能不可用)",
		"https://qq.yh31.com/tp/zjbq/202101142041489799.jpg");
	if (src==null) ;
	else execute(id,src);
	function execute(id,src){
		checkImgExists(src).then(()=>{
			//success callback
			let xhr=new XMLHttpRequest();
			xhr.open('POST','../../jsp/modifyInfo.jsp');
			xhr.setRequestHeader("Content-type","application/x-www-form-urlencoded");
			xhr.onload=function(){
				if(xhr.responseText=="ok"){
					alert("修改成功！");
					document.getElementById("portrait").style.backgroundImage="url("+src+")";
				}else {
					alert("修改失败！");
				}
			}
			xhr.send("comments=modifyInfo&"+"portrait="+src+"&userid="+id);
			console.log('有效链接')
		}).catch(()=>{
			//fail callback
			alert("图片暂时不可用，修改失败！");
			console.log('无效链接')
		})
	}
}
function checkImgExists(imgurl) {
	return new Promise(function(resolve, reject) {
		var ImgObj = new Image();
		ImgObj.src = imgurl;
		ImgObj.onload = function(res) {
			resolve(res);
		}
		ImgObj.onerror = function(err) {
			reject(err)
		}
	})
}

function setOnlineNum(){
	let xhr=new XMLHttpRequest();
	xhr.open('GET','../../jsp/getOnlineNum.jsp');
	xhr.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	xhr.onload=function(){
		if(xhr.responseText.length<10){
			document.getElementById("number").innerText = "当前在线人数："+xhr.responseText;
		}
	}
	xhr.send();
}

function sessionListener(){
	let xhr=new XMLHttpRequest();
	xhr.open('GET','../../jsp/sessionListener.jsp');
	xhr.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	xhr.onload=function(){
		if (xhr.responseText!==sender) {
			//alert("登录信息发生变化!");
			// window.location.reload();
			// window.location.href = "../../";
		}
	}
	xhr.send();
}