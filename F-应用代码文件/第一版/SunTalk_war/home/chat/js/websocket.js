let websocket = null;
let type;
let sender;
let recipient;
let content;
//判断当前浏览器是否支持WebSocket
if ('WebSocket' in window) {
    // websocket = new WebSocket("ws://"+(window.location.href).substr(6)+"websocket");
    websocket = new WebSocket("ws://localhost:8080/websocket");
} else {
    alert('当前浏览器 Not support websocket');
}
//连接发生错误监听
websocket.addEventListener('error',function (){
    alert("连接发生错误");
    console.log("WebSocket连接发生错误！");
});

//连接成功建立监听
websocket.addEventListener('open',function (){
    console.log("连接成功！");
});

//连接关闭监听
websocket.addEventListener('close',function (e){
    console.log("连接关闭！");
    console.log(e);
    // let obj = document.getElementById("msg-container");
    // obj.innerHTML = "";
    // obj.innerText = "断开连接！";
    // obj.style.textAlign="center";
    // obj.style.fontSize = "70px";
});

//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
window.onbeforeunload = function () {
    closeWebSocket();
}

//关闭WebSocket连接
function closeWebSocket() {
    websocket.close();
}
//
// //接收到消息监听
// websocket.addEventListener('message',function (event){
//     //1.格式化string为json
//     let messageJson = JSON.parse(event.data);
//     //2.读取json信息，并处理信息
//     switch (messageJson["message_type"]){
//         case "normal":
//             //创建聊天气泡
//             createMsgDiv(messageJson,"normal","0");
//             break;
//     }
// });
//
// //发送消息
// function send(textArea) {
//     /*
//      * 如何判断是否发送成功？
//      * 答：目前还不知道
//      */
//     //1.创建message json，用于存储message的所有信息
//     //  "type":消息类型（normal）
//     //  "sender":消息发送者（发送者id）
//     //  "recipient":消息接受者（所有人:-1）
//     //  "content":消息内容（）
//     recipient = "-1";
//     content = textArea.value;
//     let msArray = [type,sender,recipient,content];
//     let messageJson = creatJson(msArray);
//     //2.读取json信息，并处理信息
//     //3.发送json到服务器由服务器处理
//     textArea.value="";//清空消息输入框
//     createMsgDiv(messageJson,"normal","0");
//     websocket.send(JSON.stringify(messageJson));
//     //4.1发送成功则改变消息气泡发送状态
//     /*
//      * 给出提示，GIF？
//      */
//     //4.2发送失败则给出友好提示
//     function creatJson(msArray){//JSON构造器
//         return {
//             "type": msArray[0],
//             "sender": msArray[1],
//             "recipient": msArray[2],
//             "content": msArray[3]
//         };
//     }
// }
//
// //创建新的消息气泡
// //0为自己，1为别人
// function createMsgDiv(messageJson,type,who){
//     let msgContainer = document.getElementById("msg-container");
//     let msgBox = document.createElement("div");
//     let msgPortrait = document.createElement("div");
//     let portraitImg = document.createElement("img");
//     if (who==="0"){
//         portraitImg.src = "http://hnuster.site/sad.jpg";
//         portraitImg.alt = "加载失败";
//         msgPortrait.appendChild(portraitImg);
//         let msgMainSend = document.createElement("div");
//         let msgContentSend = document.createElement("div");
//         msgMainSend.appendChild(msgContentSend);
//         let msgFill = document.createElement("div");
//         msgBox.className = "msg-box";
//         msgPortrait.className = "msg-portrait";
//         msgMainSend.className = "msg-main-send";
//         msgContentSend.className = "msg-content-send";
//         msgFill.className = "msg-fill";
//         msgBox.appendChild(msgFill);
//         msgBox.appendChild(msgMainSend);
//         msgBox.appendChild(msgPortrait);
//         msgContainer.appendChild(msgBox);
//         msgContentSend.innerHTML = messageJson["content"];
//     }else if(who==="1"){
//         portraitImg.src = "http://hnuster.site/sad.jpg";
//         portraitImg.alt = "加载失败";
//         msgPortrait.appendChild(portraitImg);
//         let msgMainReceive = document.createElement("div");
//         let msgContentReceive = document.createElement("div");
//         msgMainReceive.innerHTML = messageJson["sender"] + "<br>";
//         msgMainReceive.appendChild(msgContentReceive);
//         let msgFill = document.createElement("div");
//         msgBox.className = "msg-box";
//         msgPortrait.className = "msg-portrait";
//         msgMainReceive.className = "msg-main-receive";
//         msgContentReceive.className = "msg-content-receive";
//         msgFill.className = "msg-fill";
//         msgBox.appendChild(msgPortrait);
//         msgBox.appendChild(msgMainReceive);
//         msgBox.appendChild(msgFill);
//         msgContainer.appendChild(msgBox);
//         msgContentReceive.innerHTML = messageJson["content"];
//     }
//     msgContainer.scrollIntoView({block:'end', behavior:'smooth', inline:'center'});
// }
// function getAccountInfo(userid){
//     let xhr=new XMLHttpRequest();
//     xhr.open('POST','../../jsp/getAccountInfo.jsp',false);//false为同步处理，方便最后获取返回值
//     xhr.setRequestHeader("Content-type","application/x-www-form-urlencoded");
//     xhr.send("comments=ask"+"&userid="+userid);
//     return JSON.parse(xhr.responseText);
// }