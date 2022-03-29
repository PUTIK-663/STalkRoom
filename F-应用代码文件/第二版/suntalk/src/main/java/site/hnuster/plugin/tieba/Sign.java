package site.hnuster.plugin.tieba;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import site.hnuster.log.ConsolePrint;
import site.hnuster.plugin.tieba.exception.TieBaFollowException;
import site.hnuster.plugin.tieba.exception.TieBaTBException;
import site.hnuster.plugin.tieba.util.Encryption;
import site.hnuster.utils.CookieBuilder;
import site.hnuster.utils.SimpleRequest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Sign {
    public static final String LIKE_URL = "https://tieba.baidu.com/mo/q/newmoindex";//获取用户所有关注贴吧接口
    public static final String TBS_URL = "http://tieba.baidu.com/dc/common/tbs";//获取用户的tbs接口
    public static final String SIGN_URL = "http://c.tieba.baidu.com/c/c/forum/sign";//贴吧签到接口

    private static final String cookieName = "BDUSS";//用户登录cookie名(BDUSS)
    private String cookie;
    private final List<String> follow = new ArrayList<>();//用户所关注的贴吧
    private final List<String> success = new ArrayList<>();//签到成功的贴吧列表
    private String tbs;//用户的tbs
    private static Integer followNum = 0;//用户所关注的贴吧数量

    public String execute(String cookieContent){
        try{
            CookieBuilder cookieBuilder = new CookieBuilder();
            cookie = cookieBuilder.addCookie(cookieName,cookieContent).toString();
            init();
            runSign(cookie);
            return "共 {"+followNum+"} 个贴吧 - 成功: {"+success.size()+"} - 失败: {"+(followNum-success.size())+"}";
        }catch (Exception e){
            ConsolePrint.printShort(e);
        }
        return "签到异常";
    }

    private void init() throws TieBaTBException, TieBaFollowException {
        getTbs(cookie);
        getFollow(cookie);
    }

    /**
     * 进行登录，获得 tbs ，签到的时候需要用到这个参数
     */
    private void getTbs(String cookie) throws TieBaTBException {
        try{
            JSONObject jsonObject = SimpleRequest.toJSON(SimpleRequest.get(TBS_URL,cookie));
            if("1".equals(jsonObject.getString("is_login"))){
                System.out.println("获取tbs成功");
                tbs = jsonObject.getString("tbs");
            } else{
                System.out.println("获取tbs失败 -- " + jsonObject);
            }
        } catch (Exception e){
            throw new TieBaTBException(e);
        }
    }

    /**
     * 获取用户所关注的贴吧列表
     */
    private void getFollow(String cookie) throws TieBaFollowException {
        try{
            JSONObject jsonObject = SimpleRequest.toJSON(SimpleRequest.get(LIKE_URL,cookie));
            JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("like_forum");
            followNum = jsonArray.size();
            // 获取用户所有关注的贴吧
            for (Object array : jsonArray) {
                if("0".equals(((JSONObject) array).getString("is_sign"))){
                    // 将为签到的贴吧加入到 follow 中，待签到
                    follow.add(((JSONObject) array).getString("forum_name").replace("+","%2B"));
                } else{
                    // 将已经成功签到的贴吧，加入到 success
                    success.add(((JSONObject) array).getString("forum_name"));
                }
            }
        } catch (Exception e){
            throw new TieBaFollowException(e);
        }
    }

    /**
     * 开始进行签到，每一轮性将所有未签到的贴吧进行签到，一共进行5轮，如果还未签到完就立即结束
     * 一般一次只会有少数的贴吧未能完成签到，为了减少接口访问次数，每一轮签到完等待10分钟，如果在过程中所有贴吧签到完则结束。
     */
    private void runSign(String cookie) throws Exception {
        if (tbs.equals("")) throw new TieBaTBException(new Throwable("tbs为空"));
        // 当执行 5 轮所有贴吧还未签到成功就结束操作
        Integer maxRunTimes = 5;
        while(success.size()<followNum&&maxRunTimes>0){
            Iterator<String> iterator = follow.iterator();
            while(iterator.hasNext()){
                String s = iterator.next();
                String rotation = s.replace("%2B","+");
                String body = "kw="+s
                        +"&tbs="+tbs
                        +"&sign="+ Encryption.enCodeMd5("kw="+rotation+"tbs="+tbs+"tiebaclient!!!");
                JSONObject post = SimpleRequest.toJSON(SimpleRequest.post(SIGN_URL,body
                        ,cookie,"tieba.baidu.com"));
                if("0".equals(post.getString("error_code"))){
                    iterator.remove();
                    success.add(rotation);
                    System.out.println(rotation + ": " + "签到成功");
                } else {
                    System.out.println(rotation + ": " + "签到失败");
                }
            }
            if (success.size() != followNum){
                // 为防止短时间内多次请求接口，触发风控，设置每一轮签到完等待 10 分钟
                Thread.sleep(1000 * 60 * 10);
                /*
                 * 重新获取 tbs
                 * 尝试解决以前第 1 次签到失败，剩余 4 次循环都会失败的错误。
                 */
                getTbs(cookie);
            }
            maxRunTimes--;
            System.out.println(maxRunTimes);
        }
    }
}
