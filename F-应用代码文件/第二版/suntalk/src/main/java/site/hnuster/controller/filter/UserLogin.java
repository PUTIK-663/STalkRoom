package site.hnuster.controller.filter;

import site.hnuster.utils.ResponseBuilder;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebFilter(filterName = "userLoginFilter",value = "/user/login")
public class UserLogin implements Filter,RequestChecking {
    private int maxLength;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        maxLength = 256;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        String id = servletRequest.getParameter("id");
        String timestamp = servletRequest.getParameter("timestamp");
        String nonce = servletRequest.getParameter("nonce");
        String aur = servletRequest.getParameter("aur");

        List<String> paraList = new ArrayList<>();
        paraList.add(id);
        paraList.add(timestamp);
        paraList.add(nonce);
        paraList.add(aur);

        if (ckAnyNull(paraList)||ckAnyEmpty(paraList)||ckLength(paraList,maxLength)){
            ResponseBuilder.response(
                    ResponseBuilder.buildLoginResponse(ResponseBuilder.ERROR,"用户名或密码错误！")
                    ,servletResponse);
        }else {
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
