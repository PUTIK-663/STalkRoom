package site.hnuster.controller.filter;

import site.hnuster.Start;
import site.hnuster.service.Authority;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebFilter(filterName = "PluginFilter",value = "/home/plugin/service")
public class Plugin implements Filter,RequestChecking{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String isLogged = "0";
        String id = "";
        String token = "";
        String serviceType = servletRequest.getParameter("serviceType");
        String cookieContent = servletRequest.getParameter("cookieContent");
        Cookie[] cookie = ((HttpServletRequest)servletRequest).getCookies();
        for (Cookie c:cookie){
            switch (c.getName()) {
                case "isLogged":
                    isLogged = c.getValue() == null ? "0" : c.getValue();
                    break;
                case "sunToken":
                    token = c.getValue() == null ? "" : c.getValue();
                    break;
                case "id":
                    id = c.getValue() == null ? "" : c.getValue();
                    break;
            }
        }
        List<String> paraList = new ArrayList<>();
        paraList.add(isLogged);
        paraList.add(id);
        paraList.add(token);
        paraList.add(serviceType);
        paraList.add(cookieContent);
        if(!isLogged.equals("1")|| Start.checkToken(id,token)!=Start.OK||ckAnyEmpty(paraList)){
            System.out.println("未登录，重定向至登录界面！");
            ((HttpServletResponse)servletResponse).sendRedirect("/");
        }else{
            filterChain.doFilter(servletRequest,servletResponse);
        }
    }

    @Override
    public void destroy() {

    }

    @Override
    public boolean ckType(List<?> ckList) {
        return false;
    }
}
