package site.hnuster.controller.websocketchat;


import com.alibaba.fastjson.JSONObject;
import site.hnuster.dao.DBHelper;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

/**
 * @ ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 *
 * GetHttpSession用于访问客户端请求参数，包括Cookie
 */
@ServerEndpoint(value = "/home/chat/websocket",configurator= GetHttpSession.class)
public class SocketChat{
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象
    //它是线程安全的无序的集合，可以将它理解成线程安全的HashSet,他们继承一个共同的父类
    // 用户总连接池记录了所有用户连接，用户连接不可重复，判断依据为HttpSession的Id(唯一标识)
    // 若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static final ConcurrentHashMap<String, SocketChat> webSocketMap = new ConcurrentHashMap<>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private HttpSession userHs;
    private Session session;
    private String sessionId;
    private HashMap<String,String> messageMap;
    /**
     * 连接建立成功调用的方法
     * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(Session session, EndpointConfig conf) {
        this.userHs = (HttpSession) conf.getUserProperties().get("httpsession");
        this.sessionId = this.userHs.getId();//获取的Cookie可能客户端没有，导致在线人数虚高
        this.session = session;
        if(!webSocketMap.containsKey(this.sessionId)){
            webSocketMap.put(this.sessionId,this); //加入set中
            addOnlineCount();                      //在线数加1,使用静态同步方法
        }else{
            //HttpSession没变，但是好像服务器建立了一个新的session，所以每次也更新map里面的session对象
            //现在还不知道这两的关系，以及其他的可能的生命周期依赖关系
            //但是每次客户端刷新，session对象的id都会变
            //System.out.println("已有数据:"+this.sessionId);
            webSocketMap.replace(this.sessionId,this);
            //System.out.println("这是个老用户！"+"，他的sessionID为："+this.sessionId);
        }
        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() throws IOException {
        String key = this.sessionId;
        SocketChat value = webSocketMap.get(key);
        webSocketMap.remove(key,value);  //从map中删除
        subOnlineCount();                //在线数减1
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) throws Exception {//必须接收的消息文件说明
        //格式化客户端发来的message（以map代替json格式）
        messageMap = formatMessage(message);
        //读取messageMap(message)信息
        //  "date":消息发出时间
        //  "type":消息类型（普通(normal)、音视频(av)、其他文件(file)、系统(system)）
        //  "sender":消息发送者（发送者id）
        //  "recipient":消息接受者（所有人:-1，一个人:userid）
        //  "content":消息内容（String）
        String date = messageMap.get("date");
        String type = messageMap.get("type");
        int sender = Integer.parseInt(messageMap.get("sender"));
        int recipient = Integer.parseInt(messageMap.get("recipient"));
        String content = messageMap.get("content");
        messageMap.forEach((key, value)->{
            System.out.println(key+":"+value);
        });
        switch (type){
            case "normal":
                //消息记录入数据库
                //DBHelper dbHelper = new DBHelper();
                //dbHelper.connect();
//                OperateDb db = null;
//                try{
//                    db = new OperateDb();
//                    //消息数量累加
//                    String sql_insert_information =
//                            "INSERT "+
//                                    "INTO user_chat_information(date,type,sender,recipient,content) "+
//                                    "VALUES('"
//                                    +date.replaceAll("'","''")+"','"
//                                    +type.replaceAll("'","''")+"','"
//                                    +sender+"','"
//                                    +recipient+"','"
//                                    +content.replaceAll("'","''")
//                                    +"');";//mysql中，''代表'
//                    db.executeUpdate(sql_insert_information);
//                    db.disconnect();
//                }catch (Exception e){
//                    e.printStackTrace();
//                }finally {
//                    if (db!=null){
//                        db.disconnect();
//                    }
//                }
                break;
            case "system":
                break;
        }
    }
    /**
     * 发生错误时调用
     * @param error 错误对象
     */
    @OnError
    public void onError(Throwable error){
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     * @param messageMap 消息存储对象
     * @throws IOException 抛出IO异常
     */
    public void sendMessage(HashMap<String, String> messageMap) throws IOException{
        //1.将map转化为json的字符串
        JSONObject jsonObject = new JSONObject();
        jsonObject.putAll(messageMap);
        String message = "{"+
                "\"date\":\""+messageMap.get("date")+"\"," +
                "\"type\":\""+messageMap.get("type")+"\"," +
                "\"sender\":\""+messageMap.get("sender")+"\"," +
                "\"recipient\":\""+messageMap.get("recipient")+"\"," +
                "\"content\":\""+messageMap.get("content")+"\"}";
        //2.发送给客户端
        //this.session.getAsyncRemote().sendText(message);//异步
        System.out.println(jsonObject.toJSONString());
        this.session.getBasicRemote().sendText(jsonObject.toJSONString());//同步
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public synchronized void addOnlineCount() {
        SocketChat.onlineCount++;
        webSocketMap.forEach((sessionId, WebSocket)->{
            try {
                //系统消息发给所有人
                WebSocket.sendMessage(getOnlineMsg());
            } catch (IOException e) {
                e.printStackTrace();
                //continue;
            }}
        );
    }

    public synchronized void subOnlineCount() throws IOException {
        SocketChat.onlineCount--;
        webSocketMap.forEach((sessionId, WebSocket)->{
            try {
                //系统消息发给所有人
                WebSocket.sendMessage(getOnlineMsg());
            } catch (IOException e) {
                e.printStackTrace();
                //continue;
            }}
        );
    }

    public HashMap<String,String> getOnlineMsg(){
        String num = String.valueOf(SocketChat.onlineCount);
        HashMap<String,String> online = new HashMap<>();
        online.put("date",String.valueOf(new Date().getTime()));
        online.put("type","system");
        online.put("sender","-2");
        online.put("recipient","-1");
        online.put("content",num);
        return online;
    }

    public static HashMap<String,String> formatMessage(String message){
        //文本消息长度限制为1024字节
        System.out.println(message);
        HashMap<String,String> messageMap = new HashMap<>();
        message = message.substring(2,message.length()-2);
        String[] msgArray = message.split("(\":\")|(\",\")");// ":" ","
        /*
         * "date":消息发出时间
         * "type":消息类型
         * "sender":消息发送者（发送者id）
         * "recipient":消息接受者（所有人，一个人id）
         * "content":消息内容（）
         */
        SimpleDateFormat newDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        messageMap.put("date",newDate.format(new Date()));
        messageMap.put("type",msgArray[1]);
        messageMap.put("sender",msgArray[3]);
        messageMap.put("recipient",msgArray[5]);
        messageMap.put("content",msgArray[7]);
        return messageMap;
    }
}

