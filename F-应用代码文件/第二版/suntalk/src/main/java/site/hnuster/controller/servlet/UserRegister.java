package site.hnuster.controller.servlet;

import site.hnuster.service.Register;
import site.hnuster.utils.ResponseBuilder;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "userRegisterServlet",value = "/user/register")
public class UserRegister extends HttpServlet {
    @Override
    public void init(){

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.getWriter().print("illegal request,please check your operation!");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String sessionID = req.getRequestedSessionId();
        String email = req.getParameter("email");
        String valCode = req.getParameter("valCode");
        String password = req.getParameter("password");

        try {
            Register registerHelper= new Register(sessionID,email,valCode,password);
            int result = registerHelper.execute();
            if(result==Register.OK){

                Cookie cookie_isLogged = new Cookie("isLogged","1");
//                cookie_isLogged.setPath("/*");
                cookie_isLogged.setMaxAge(60*60*24);

                Cookie cookie_id = new Cookie("id",registerHelper.getId());
//                cookie_id.setPath("/*");
                cookie_id.setMaxAge(60*60*24);

                Cookie cookie_token = new Cookie("sunToken",registerHelper.getToken());
//                cookie_token.setPath("/*");
                cookie_token.setMaxAge(60*60*24);

                resp.addCookie(cookie_isLogged);
                resp.addCookie(cookie_id);
                resp.addCookie(cookie_token);

                ResponseBuilder.response(
                        ResponseBuilder.buildRegisterResponse(ResponseBuilder.OK,"ok")
                        ,resp);
            }else if (result==Register.VAL_CODE_ERROR){
                ResponseBuilder.response(
                        ResponseBuilder.buildRegisterResponse(ResponseBuilder.CONTENT_ERROR,"验证码错误！")
                        ,resp);
            }else if (result==Register.EMAIL_ERROR){
                ResponseBuilder.response(
                        ResponseBuilder.buildRegisterResponse(ResponseBuilder.DUPLICATE_ERROR,"邮箱已被注册！")
                        ,resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ResponseBuilder.response(
                    ResponseBuilder.buildRegisterResponse(ResponseBuilder.SYSTEM_ERROR,"注册异常！")
                    ,resp);
        }

    }

    @Override
    public void destroy() {

    }
}
