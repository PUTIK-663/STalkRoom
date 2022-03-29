package site.hnuster.controller.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebFilter(filterName = "validateCodeManagerFilter",value = "/micro/validatecode")
public class ValidateCodeManager implements Filter,RequestChecking {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String date = servletRequest.getParameter("date");
        List<String> paraList = new ArrayList<>();
        paraList.add(date);

        if (ckAnyNull(paraList)||ckAnyEmpty(paraList)){
            servletResponse.getWriter().print("");
        }else {
            filterChain.doFilter(servletRequest, servletResponse);
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
