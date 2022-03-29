package site.hnuster.service;

import site.hnuster.Start;
import site.hnuster.utils.Encoder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 权限验证类，用于验证用户权限(token)，使用户能够保持登录状态
 */
public class Authority {
    private String id;
    private String nonce;
    private String token;
    private boolean enabled;

    public Authority(){

    }

    public Authority(String id, String token) {
        this.id = id;
        this.token = token;
        nonce = makeNonce();
        enabled = false;
    }

    public boolean checkToken(){
        if(Start.checkToken(this.id,this.token)==Start.OK){
            enabled = true;
        }
        return enabled;
    }

    public Authority makeToken(){
        List<String> paraList = new ArrayList<>();
        paraList.add(this.id);
        paraList.add(this.nonce);
        this.token = Encoder.sha1(paraList);
        Start.addToken(this.id,this.token);
        System.out.println("添加了一个用户："+id+token);
        return this;
    }

    public String getId() {
        return id;
    }

    public Authority setId(String id) {
        this.id = id;
        return this;
    }

    public String getToken() {
        return token;
    }

    private String makeNonce(){
        long t = new Date().getTime();
        String tt = String.valueOf(t);
        tt = (char)(new Random(t).nextInt(30)+60) + tt + "SunTalk";
        return tt;
    }

}
