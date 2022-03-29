package site.hnuster.dao;

import java.sql.*;
import java.util.Map;
/*
 *连接效率与资源利用待优化-->数据库连接池
 */

public class DBHelper implements DBStrings{
    private Connection connection = null;
    private DBUtils dbUtils = null;

    /**
     * 连接数据库
     */
    public void connect() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");// 加载数据库驱动
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+DATABASE_NAME+"?serverTimezone=UTC",
                "root","Mysql_key_2021");// 链接数据库
        if (connection!=null){
            System.out.println("连接成功:"+connection);
            dbUtils = new DBUtils(connection);
        }else {
            System.out.println("连接失败！");
        }
    }

    /**
     * 断开数据库连接
     */
    public void disconnect(){
        try{
            if (connection != null) {
                connection.close();
                connection = null;
            }
            if (dbUtils!=null){
                dbUtils=null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 插入数据(user_account表)：整行插入user_account表
     * @param id 用户id
     * @param pwd 用户密码
     */
    public int insertTableUserAccount(String id,String pwd) throws Exception {
        return dbUtils.executeUpdate(buildInsertTableUserAccountSQL(id,pwd));
    }

    /**
     * 插入数据(user_account_info表)：整行插入user_account_info表
     * @param id 用户id
     * @param email 用户邮箱
     */
    public int insertTableUserAccountInfo(String id,String email) throws Exception {
        return dbUtils.executeUpdate(buildInsertTableUserAccountInfoSQL(id,email));
    }

    /**
     * 更新数据(user_account表)：更新用户密码
     * @param id 用户id
     * @param pwd 用户密码
     */
    public int updateUserPassword(String id,String pwd) throws Exception {
        return dbUtils.executeUpdate(buildUpdateUserPasswordSQL(id,pwd));
    }

    /**
     * 更新数据(user_account_info表)：更新用户信息
     * @param id 用户id
     * @param info 需要更新的数据
     */
    public int updateUserInfo(String id,Map<String,String> info) throws Exception {
        return dbUtils.executeUpdate(buildUpdateUserInfoSQL(id,info));
    }

    /**
     * 调试用操作
     * 查询数据(user_account表)：按行查询user_account表
     * @param id 用户id
     */
    public ResultSet queryUserAccountErrorTest(String id) throws Exception {
        return dbUtils.executeQuery(buildQueryTableUserAccountSQL(id));
    }

    /**
     * 查询数据(user_account表)：查询所有用户ID
     */
    public ResultSet queryUserMaxID() throws Exception {
        return dbUtils.executeQuery(buildQueryUserMaxIDSQL());
    }

    /**
     * 查询数据(user_account表)：查询用户注册日期
     * @param id 用户id
     */
    public ResultSet queryUserRegisterDay(String id) throws Exception {
        return dbUtils.executeQuery(buildQueryUserRegisterDaySQL(id));
    }

    /**
     * 查询数据(user_account表)：查询用户密码
     * @param id 用户id
     */
    public ResultSet queryUserPassword(String id) throws Exception {
        return dbUtils.executeQuery(buildQueryUserPasswordSQL(id));
    }

    /**
     * 查询数据(user_account_info表)：查询用户所有信息
     * @param id 用户id
     */
    public ResultSet queryUserInfo(String id) throws Exception {
        return dbUtils.executeQuery(buildQueryUserInfoSQL(id));
    }

    public ResultSet queryUserEmail() throws Exception{
        return dbUtils.executeQuery(buildQueryUserEmailSQL());
    }

    /**
     * 查询数据(user_account_info表)：查询用户头像、昵称与地区
     * @param id 用户id
     */
    public ResultSet queryUserPNA(String id) throws Exception {
        return dbUtils.executeQuery(buildQueryUserPortraitAndNicknameAndAreaSQL(id));
    }
}
