package site.hnuster.utils;

import com.alibaba.fastjson.*;

import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ResponseBuilder {
    public static final String OK = "1";//成功
    public static final String SYSTEM_ERROR = "2";//系统异常错误
    public static final String ERROR = "0";//笼统的错误
    public static final String CONTENT_ERROR = "-1";//参数内容错误
    public static final String SYNTAX_ERROR = "-2";//参数语法错误
    public static final String DUPLICATE_ERROR = "-3";//限制重复参数错误

    public static void response(String json, ServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().print(json);
    }

    public static String buildLoginResponse(String code,String msg){
        Map<String,Object> map = new HashMap<>();
        map.put("code",code);
        map.put("msg",msg);
        JSONObject jsonObject = new JSONObject(map);
        return jsonObject.toString();
    }

    public static String buildRegisterResponse(String code,String msg){
        Map<String,Object> map = new HashMap<>();
        map.put("code",code);
        map.put("msg",msg);
        JSONObject jsonObject = new JSONObject(map);
        return jsonObject.toString();
    }

}
