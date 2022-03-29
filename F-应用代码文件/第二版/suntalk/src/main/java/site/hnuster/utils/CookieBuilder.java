package site.hnuster.utils;

public class CookieBuilder {

    private final StringBuilder cookie = new StringBuilder();

    public CookieBuilder(){

    }

    @Override
    public String toString(){
        return cookie.toString();
    }

    public CookieBuilder addCookie(String cookieName,String cookieContent){
        cookie.append(cookieName).append("=").append(cookieContent).append(";");
        return this;
    }

}
