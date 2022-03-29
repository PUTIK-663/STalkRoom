package site.hnuster.controller.servlet;

import site.hnuster.service.Authority;
import site.hnuster.service.Login;
import site.hnuster.utils.ResponseBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "userLoginServlet",value = "/user/login")
public class UserLogin extends HttpServlet {
    @Override
    public void init() throws ServletException {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.getWriter().print("illegal request,please check your operation!");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String timestamp = req.getParameter("timestamp");
        String nonce = req.getParameter("nonce");
        String aur = req.getParameter("aur");
        try {
            if (new Login(id,timestamp,nonce,aur).execute()){
                //登录成功，返回token等cookie信息给前端
                Cookie cookie_isLogged = new Cookie("isLogged","1");
                cookie_isLogged.setPath("/");
                cookie_isLogged.setMaxAge(60*60*24);

                Cookie cookie_id = new Cookie("id",id);
                cookie_id.setPath("/");
                cookie_id.setMaxAge(60*60*24);

                Cookie cookie_token = new Cookie("sunToken",new Authority(id,"").makeToken().getToken());
                cookie_token.setPath("/");
                cookie_token.setMaxAge(60*60*24);

                resp.addCookie(cookie_isLogged);
                resp.addCookie(cookie_id);
                resp.addCookie(cookie_token);
                //响应登录成功信息
                ResponseBuilder.response(
                        ResponseBuilder.buildLoginResponse(ResponseBuilder.OK,"欧了")
                        ,resp);
            }else {
                //返回登录失败
                ResponseBuilder.response(
                        ResponseBuilder.buildLoginResponse(ResponseBuilder.ERROR,"用户名或密码错误")
                        ,resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {
        
    }
}
