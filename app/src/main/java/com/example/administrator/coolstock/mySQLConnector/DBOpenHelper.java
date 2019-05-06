package com.example.administrator.coolstock.mySQLConnector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBOpenHelper {

    //MYSQL驱动   连接URL   用户名 密码
    private static String driver="com.mysql.cj.jdbc.Driver";
    private static String url="jdbc:mysql://192.168.1.104:3306/cool_stock?&serverTimezone=UTC";
    private static String user="root";
    private static String password="123456";

    /**
     * 连接数据库
     */
    public static Connection getConn(){
        Connection conn=null;
        try{
            Class.forName(driver); //获取MYSQL驱动
            conn=(Connection)DriverManager.getConnection("jdbc:mysql://192.168.1.104:3306/cool_stock?&serverTimezone=UTC"
                    ,"root","123456"); //获取连接
        }catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return conn;
    }

    /**
     *  关闭数据库
     */
    public static void closeAll(Connection conn, PreparedStatement ps){
        if (conn!=null){
            try{
                conn.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        if (ps!=null){
            try{
                ps.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭数据库
     */

    public static void closeAll(Connection conn, PreparedStatement ps, ResultSet rs){
        if (conn!=null){
            try{
                conn.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        if (ps!=null){
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (rs!=null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

}
