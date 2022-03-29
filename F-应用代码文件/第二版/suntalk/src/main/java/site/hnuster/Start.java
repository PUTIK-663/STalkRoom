package site.hnuster;

import java.util.Date;
import java.util.HashMap;

public final class Start {
    public static final int ERROR = -1;//错误的
    public static final int INVALID = -2;//失效的
    public static final int OVERDUE = 0;//超时的
    public static final int OK = 1;//正确的
    private static final HashMap<String,String> tokens = new HashMap<>();
    private static final HashMap<String,Long> tokensDue = new HashMap<>();//token加入时间

    public static int checkToken(String id,String token){
        if(!tokens.containsKey(id)){
            System.out.println("不存在用户");
            return ERROR;
        }else if(!tokens.get(id).equals(token)){
            System.out.println(token);
            System.out.println(tokens.get(id));
            System.out.println("失效！");
            return INVALID;
        }else if((new Date().getTime() - tokensDue.get(id)) > 1000*3600*24){
            System.out.println("时间过期");
            return OVERDUE;
        }
        System.out.println("没问题！");
        return OK;
    }

    public static void addToken(String id, String token) {
        if (tokens.containsKey(id)){
            tokens.replace(id, token);
            tokensDue.replace(id, new Date().getTime());
        } else {
            tokens.put(id,token);
            tokensDue.put(id,new Date().getTime());
        }
    }
}
