package site.hnuster.controller.servlet;

import cn.dsna.util.images.ValidateCode;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@WebServlet(name = "validateCodeManagerServlet",value = "/micro/validatecode")
public class ValidateCodeManager extends HttpServlet {
    public static final HashMap<String,String> validateCodeMap = new HashMap<>();
    @Override
    public void init(){

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String sessionID = req.getRequestedSessionId();

        ValidateCode code = new ValidateCode(200,30,4,20);
        if (validateCodeMap.containsKey(sessionID)){
            validateCodeMap.replace(sessionID, code.getCode());
        }else {
            validateCodeMap.put(sessionID, code.getCode());
        }
        code.write(resp.getOutputStream());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doGet(req, resp);
    }

    @Override
    public void destroy() {

    }
}
