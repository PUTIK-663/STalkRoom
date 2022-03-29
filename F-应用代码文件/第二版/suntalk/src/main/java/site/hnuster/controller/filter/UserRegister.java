package site.hnuster.controller.filter;

import site.hnuster.utils.ResponseBuilder;
import site.hnuster.utils.SimpleRequest;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebFilter(filterName = "userRegisterFilter",value = "/user/register")
public class UserRegister implements Filter,RequestChecking {
    private int maxLength;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        maxLength = 32;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        String email = servletRequest.getParameter("email");
        String valCode = servletRequest.getParameter("valCode");
        String password = servletRequest.getParameter("password");
        String timestamp = servletRequest.getParameter("timestamp");

        List<String> paraList = new ArrayList<>();
        paraList.add(email);
        paraList.add(valCode);
        paraList.add(password);
        paraList.add(timestamp);

        if (ckAnyNull(paraList)||ckAnyEmpty(paraList)||ckLength(paraList,maxLength)){
            ResponseBuilder.response(
                    ResponseBuilder.buildRegisterResponse(ResponseBuilder.ERROR,"非法的请求参数！")
                    ,servletResponse);
        }else{
            List<String> emailList = new ArrayList<>();
            emailList.add(email);
            if (ckType(emailList)){
                filterChain.doFilter(servletRequest, servletResponse);
            }else {
                ResponseBuilder.response(
                        ResponseBuilder.buildRegisterResponse(ResponseBuilder.SYNTAX_ERROR,"错误的邮件地址！")
                        ,servletResponse);
            }
        }
    }

    @Override
    public void destroy() {

    }

    @Override
    public boolean ckType(List<?> ckList) {
        boolean ok = true;
        for (Object s:ckList){
            String str = (String) s;
            if (!str.contains("@")||str.indexOf("@")==str.length()-1){
                ok = false;
                break;
            }
            try{
                if (SimpleRequest.get(
                        "https://"+str.substring(str.indexOf("@")+1)
                        ,""
                ).getStatusLine().getStatusCode()!=200){
                    ok = false;
                    break;
                }
            }catch (Exception e){
                ok = false;
            }
        }
        return ok;
    }
}
