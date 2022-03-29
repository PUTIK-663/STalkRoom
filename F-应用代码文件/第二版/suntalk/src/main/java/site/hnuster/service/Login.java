package site.hnuster.service;

import site.hnuster.dao.DBHelper;
import site.hnuster.utils.Encoder;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * 登录类，用于登录功能实现
 */
public class Login {
    private String id;
    private String timestamp;
    private String nonce;
    private String aur;
    private boolean success;

    public Login(){

    }

    public Login(String id, String timestamp, String nonce, String aur) {
        this.id = id;
        this.timestamp = timestamp;
        this.nonce = nonce;
        this.aur = aur;
        this.success=false;
    }

    public boolean execute() throws Exception {
        List<String> paraList = new ArrayList<>();
        paraList.add(id);
        paraList.add(timestamp);
        paraList.add(nonce);
        DBHelper dbHelper = new DBHelper();
        dbHelper.connect();
        ResultSet rs = dbHelper.queryUserPassword(this.id);
        if (rs.next()){
            if (rs.getString(1)!=null){
                paraList.add(rs.getString(1));
                success = (Encoder.sha1(paraList).equals(aur));
            }
        }
        dbHelper.disconnect();
        return success;
    }

    public String getId() {
        return id;
    }

    public Login setId(String id) {
        this.id = id;
        return this;
    }

    public String getAur() {
        return aur;
    }

    public Login setAur(String aur) {
        this.aur = aur;
        return this;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public Login setTimestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public String getNonce() {
        return nonce;
    }

    public Login setNonce(String nonce) {
        this.nonce = nonce;
        return this;
    }
}
