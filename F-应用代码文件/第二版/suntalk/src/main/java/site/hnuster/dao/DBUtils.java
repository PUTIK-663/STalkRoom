package site.hnuster.dao;

import java.sql.*;

/**
 * 封装的数据库操作工具
 */
class DBUtils{
    private Connection connection = null;
    private static final int ERROR = -1;
    public DBUtils(Connection connection){
        this.connection = connection;
    }

    //查询数据库
    public ResultSet executeQuery(String sql) throws Exception{
        System.out.println("executeQuery(). sql = " + sql);
        PreparedStatement pstm = connection.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        // 执行查询
        return pstm.executeQuery();
    }

    // 插入
    // executeUpdate 的返回值是一个整数，指示受影响的行数（即更新计数）。
    // executeUpdate用于执行 INSERT、UPDATE 或 DELETE 语句
    // 以及 SQL DDL（数据定义语言）语句，例如 CREATE TABLE 和 DROP TABLE。

    // 执行增、删、改语句的方法
    public int executeUpdate(String sql) throws Exception{
        int count = 0;
        System.out.println(sql);
        Statement stmt = connection.createStatement();
        count = stmt.executeUpdate(sql);
        return count;
    }
}
