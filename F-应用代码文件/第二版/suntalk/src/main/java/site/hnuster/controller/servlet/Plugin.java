package site.hnuster.controller.servlet;

import site.hnuster.plugin.Launcher;
import site.hnuster.plugin.tieba.Sign;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;

@WebServlet(name = "pluginServlet",value = "/home/plugin/service")
public class Plugin extends HttpServlet {

    @Override
    public void init() throws ServletException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        String id = "";
        Cookie[] cookie = req.getCookies();
        for (Cookie c:cookie){
            if(c.getName().equals("id")) {
                id = c.getValue()==null ? "" : c.getValue();
            }
        }
        String cookieContent = req.getParameter("cookieContent");
        cookieContent = cookieContent==null ? "" : cookieContent;
        if (Launcher.cookieMap.containsKey(id)){
            Launcher.cookieMap.replace(id,cookieContent);
            resp.getWriter().print(Calendar.getInstance().getTime() +"<br>更新cookie！<br>"
                    + new Sign().execute(cookieContent)+"<br>----------");
        }else {
            Launcher.cookieMap.put(id,cookieContent);
            resp.getWriter().print(Calendar.getInstance().getTime() +"<br>加入cookie！<br>"
                    + new Sign().execute(cookieContent)+"<br>----------");
        }
    }

    @Override
    public void destroy() {

    }

}
