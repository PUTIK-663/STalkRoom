package site.hnuster.service;

import site.hnuster.controller.servlet.ValidateCodeManager;
import site.hnuster.dao.DBHelper;
import site.hnuster.exception.RegisterException;

import java.sql.ResultSet;

/**
 * 注册类，用于注册功能的实现
 */
public class Register {
    public static final int OK = 1;
    public static final int VAL_CODE_ERROR = -1;
    public static final int EMAIL_ERROR = -2;
    private String sessionID;
    private String email;
    private String valCode;
    private String password;
    private String id;
    private String token;

    public Register(){

    }

    public Register(String sessionID,String email, String valCode, String password) {
        this.sessionID = sessionID;
        this.email = email;
        this.valCode = valCode;
        this.password = password;
    }

    public int execute() throws Exception {
        if (ValidateCodeManager.validateCodeMap.get(sessionID)==null){
            return VAL_CODE_ERROR;
        }
        if (!ValidateCodeManager.validateCodeMap.get(sessionID).equals(valCode)){
            return VAL_CODE_ERROR;
        }
        DBHelper dbHelper = new DBHelper();
        dbHelper.connect();
        ResultSet rs = dbHelper.queryUserEmail();
        while (rs.next()){
            if (rs.getString(1).equals(this.email)){
                return EMAIL_ERROR;
            }
        }
        applyNewAccount(dbHelper);
        dbHelper.disconnect();
        return OK;
    }

    private void applyNewAccount(DBHelper dbHelper) throws Exception {
        ResultSet rsID = dbHelper.queryUserMaxID();rsID.next();
        int id = rsID.getInt(1)+1;
        try{
            this.id = String.valueOf(id);
            int success = dbHelper.insertTableUserAccount(this.id,this.password);
            if (success<=0) {
                throw new RegisterException("Insert failed,maybe there is a conflict in DB"
                        ,new Throwable("Insert nothing!"));
            }
            success = dbHelper.insertTableUserAccountInfo(this.id,this.email);
            if (success<=0) {
                throw new RegisterException("Insert failed,maybe there is a conflict in DB"
                        ,new Throwable("Insert nothing!"));
            }
            this.token = new Authority(this.id,"").makeToken().getToken();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getSessionID() {
        return sessionID;
    }

    public Register setSessionID(String sessionID) {
        this.sessionID = sessionID;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Register setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getValCode() {
        return valCode;
    }

    public Register setValCode(String valCode) {
        this.valCode = valCode;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Register setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getId() {
        return id;
    }

    public Register setId(String id) {
        this.id = id;
        return this;
    }

    public String getToken() {
        return token;
    }

    public Register setToken(String token) {
        this.token = token;
        return this;
    }
}
