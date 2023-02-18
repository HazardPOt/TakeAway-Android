package com.POt.pot_meituan.logic;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBOpenHelper {

    private static String driver = "com.mysql.cj.jdbc.Driver";// mysql 驱动
    private static String ip = "localhost:3306";  // 安装了 mysql 的电脑的 ip 地址
    private static String dbName = "springboot-vue";    // 要连接的数据库
    private static String url = "jdbc:mysql://localhost:3306/springboot-vue?serverTimezone=GMT%2B8";    // mysql 数据库连接 url
    private static String user = "root";    // 用户名
    private static String password = "Woshitudou0205"; // 密码

    private static Connection sConnection;

    /**
     * 连接数据库
     */
    public static void getConnection() throws SQLException {
        if (sConnection == null) {
            try {
                Class.forName(driver);  // 获取 mysql 驱动
                sConnection = DriverManager.getConnection(url, user, password);   // 获取连接
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //return sConnection;
        Statement stmt = sConnection.createStatement();
        String sql = "SELECT shopname, shopdescribe, shopimg FROM shop";
        ResultSet rs = stmt.executeQuery(sql);
        while(rs.next()){
            // 通过字段检索
            String shopname  = rs.getString("shopname");
            String shopdescribe = rs.getString("shopdescribe");
            Blob shopimg = rs.getBlob("shopimg");
            System.out.print(shopname);
        }

    }

    /**
     * 关闭数据库
     */
    public static void closeConnection() {
        if (sConnection != null) {
            try {
                sConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}