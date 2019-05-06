package com.example.administrator.coolstock.mySQLConnector;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Test {
    private static final String DBDRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DBURL = "jdbc:mysql://202.182.107.228:3306/cool_stock?useSSL=false";
//    private static final String DBURL = "jdbc:mysql://10.0.2.2:3306/cool_stock?&serverTimezone=UTC";
    private static final String DBUSER = "root";
    private static final String DBPASSWORD = "VlH@dKLOcy!R73d8";

    public  boolean linkMysql() {
        Connection conn=null;
        PreparedStatement stmt=null;
        try {
            Class.forName(DBDRIVER).newInstance();
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
        try{
            conn = DriverManager.getConnection(DBURL,DBUSER,DBPASSWORD);
            String sql = "insert into cs_user_account(userId,userPassword) values(?,?)";
            stmt= conn.prepareStatement(sql);
            PreparedStatement ps=null;
            ps=(PreparedStatement)conn.prepareStatement(sql);
            ps.setString(1,"asdasdasdasdasdasdasdas");
            ps.setString(2,"asdasdasdasdasd");
            ps.executeUpdate();
            ps.close();
            stmt.close();;
            conn.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        finally {
            if(conn!=null){
                try {
                    conn.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        }

    }

    public static void main(String[]args){
       Test test=new Test();
       test.linkMysql();
    }
}
