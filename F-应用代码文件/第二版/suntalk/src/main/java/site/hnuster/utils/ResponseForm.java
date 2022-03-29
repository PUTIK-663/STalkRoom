
package site.hnuster.utils;

import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public interface ResponseForm {
    public static final int CODE_ERROR_NULL = 1101;
    int CODE_ERROR_INCOMPLETE = 1102;
    int CODE_ERROR_ILLEGAL = 1103;
    int CODE_INCORRECT_VAL_WRONG = 1201;
    int CODE_INCORRECT_DATA_WRONG = 1202;
    int CODE_OK = 2000;

    /**
     * <p>1100系列，参数语法错误</p>
     * <p>1101：参数为空时的响应</p>
     * @param resp 响应对象
     * @throws IOException IO异常
     */
    default void resp1101(ServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.print("{\"respCode\":\""+CODE_ERROR_NULL+"\"," +
                "\"descr\":\"null!\"," +
                "\"content\":\"null\"}");//参数为空
    }

    /**
     * <p>1100系列，参数语法错误</p>
     * <p>1102：参数不完整时的响应，如要求参数必须包含用户名与密码，但是参数值包含了用户名</p>
     * @param resp 响应对象
     * @throws IOException IO异常
     */
    default void resp1102(ServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.print("{\"respCode\":\""+CODE_ERROR_INCOMPLETE+"\"," +
                "\"descr\":\"incomplete data!\"," +
                "\"content\":\"null\"}");//参数不完整
    }
    /**
     * <p>1100系列：参数语法错误</p>
     * <p>1103：参数为非法格式时的响应，例如要求参数为int型但是参数为char类型</p>
     * @param resp 响应对象
     * @throws IOException IO异常
     */
    default void resp1103(ServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.print("{\"respCode\":\""+CODE_ERROR_ILLEGAL+"\"," +
                "\"descr\":\"illegal data!\"," +
                "\"content\":\"null\"}");//非法格式的参数
    }

    /**
     * <p>1200系列，参数语义错误</p>
     * <p>1201：验证码错误时的响应</p>
     * @param resp 响应对象
     * @throws IOException IO异常
     */
    default void resp1201(ServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.print("{\"respCode\":\""+CODE_INCORRECT_VAL_WRONG+"\"," +
                "\"descr\":\"wrong validate code!\"," +
                "\"content\":\"null\"}");//验证码错误
    }

    /**
     * <p>1200系列，参数语义错误</p>
     * <p>1202：参数不对称错误时的响应，如用户输入的用户名、密码或者其他需要
     * 与数据库里已经存在的数据相比较时不相等</p>
     * @param resp 响应对象
     * @throws IOException IO异常
     */
    default void resp1202(ServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.print("{\"respCode\":\""+CODE_INCORRECT_DATA_WRONG+"\"," +
                "\"descr\":\"wrong data!\"," +
                "\"content\":\"null\"}");//参数错误
    }
    //2000，返回正确的数据
    void resp2000(ServletResponse resp) throws IOException;
}
