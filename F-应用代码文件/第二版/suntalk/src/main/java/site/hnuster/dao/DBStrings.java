package site.hnuster.dao;

import java.util.Map;

/**
 * 该接口封装了数据库相关的：数据库名、表名、列名以及操作语句
 *
 * 之所以把操作语句也写死，同时把数据库操作工具类权限设置为仅同包可用，
 * 是因为其实用户需要数据库操作是固定的，所以不需要可变的数据库操作，
 * 故以上操作可以提高数据库数据安全，防止不当操作将数据库数据损坏
 * 当然缺点是对数据库操作的灵活性可能会受到影响
 */
interface DBStrings {
    String DATABASE_NAME = "stalk";
    String COLUMN_ID = "_id";
    //对user_account表的增删改查
    String TABLE_USER_ACCOUNT = "user_account";
    String COLUMN_USER_ID = "uid";
    String COLUMN_USER_PWD = "password";
    String COLUMN_USER_REGISTER_DAY = "birthday";
    default String buildInsertTableUserAccountSQL(String id,String pwd){
        return "INSERT INTO "+TABLE_USER_ACCOUNT+"("+COLUMN_USER_ID+","+COLUMN_USER_PWD+")"
                +" VALUES"+"('"+id+"','"+pwd+"');";
    }
    default String buildUpdateUserPasswordSQL(String id,String pwd){
        return "UPDATE "+TABLE_USER_ACCOUNT+" SET "+COLUMN_USER_PWD+"="+pwd
                +" WHERE "+COLUMN_USER_ID+"='"+id+"';";
    }
    default String buildQueryTableUserAccountSQL(String id){
        if (id.equals("")) id="*";
        return "SELECT "+id
                +" FROM "+TABLE_USER_ACCOUNT+";";
    }
    default String buildQueryUserMaxIDSQL(){
        return "SELECT "+COLUMN_USER_ID
                +" FROM "+TABLE_USER_ACCOUNT
                +" WHERE "+COLUMN_USER_ID+"="+"(SELECT MAX("+COLUMN_USER_ID+") FROM "+TABLE_USER_ACCOUNT+");";
                //+" WITH(TABLOCK);";//为表加锁，保证查询到的id始终是最新的，虽然影响了登录的体验;加不了，啊哈哈哈曹
    }
    default String buildQueryUserRegisterDaySQL(String id){
        return "SELECT "+COLUMN_USER_REGISTER_DAY
                +" FROM "+TABLE_USER_ACCOUNT
                +" WHERE "+COLUMN_USER_ID+"='"+id+"';";
    }
    default String buildQueryUserPasswordSQL(String id){
        return "SELECT "+COLUMN_USER_PWD
                +" FROM "+TABLE_USER_ACCOUNT
                +" WHERE "+COLUMN_USER_ID+"='"+id+"';";
    }
    //对user_account_information表的增删改查
    String TABLE_USER_ACCOUNT_INFO = "user_account_information";
    String COLUMN_USER_EMAIL = "email";
    String COLUMN_USER_PORTRAIT = "portrait";
    String COLUMN_USER_NICKNAME = "nickname";
    String COLUMN_USER_GENDER = "gender";
    String COLUMN_USER_BIRTHDAY = "birthday";
    String COLUMN_USER_AREA = "area";
    String COLUMN_USER_MOTTO = "motto";
    default String buildInsertTableUserAccountInfoSQL(String id,String email){
        return "INSERT INTO "+TABLE_USER_ACCOUNT_INFO+"("+COLUMN_USER_ID+","+COLUMN_USER_EMAIL+")"
                +" VALUES('"+id+"','"+email+"');";
    }
    default String buildUpdateUserInfoSQL(String id, Map<String,String> info){
        return "UPDATE "+TABLE_USER_ACCOUNT_INFO+" SET "
                +COLUMN_USER_PORTRAIT+"='"+info.get(COLUMN_USER_PORTRAIT)+"',"
                +COLUMN_USER_NICKNAME+"='"+info.get(COLUMN_USER_NICKNAME)+"',"
                +COLUMN_USER_GENDER+"='"+info.get(COLUMN_USER_GENDER)+"',"
                +COLUMN_USER_BIRTHDAY+"='"+info.get(COLUMN_USER_BIRTHDAY)+"',"
                +COLUMN_USER_AREA+"='"+info.get(COLUMN_USER_AREA)+"',"
                +COLUMN_USER_MOTTO+"='"+info.get(COLUMN_USER_MOTTO)
                +"' WHERE "+COLUMN_USER_ID+"='"+id+"';";
    }
    default String buildQueryUserInfoSQL(String id){
        return "SELECT * FROM "+TABLE_USER_ACCOUNT_INFO
                +" WHERE "+COLUMN_USER_ID+"='"+id+"';";
    }
    default String buildQueryUserEmailSQL(){
        return "SELECT "+COLUMN_USER_EMAIL
                +" FROM "+TABLE_USER_ACCOUNT_INFO+";";
    }
    default String buildQueryUserPortraitAndNicknameAndAreaSQL(String id){
        return "SELECT "+COLUMN_USER_PORTRAIT+" AND "+COLUMN_USER_NICKNAME+" AND "+COLUMN_USER_AREA
                +" FROM "+TABLE_USER_ACCOUNT_INFO+" WHERE "+COLUMN_USER_ID+"='"+id+"';";
    }
}
