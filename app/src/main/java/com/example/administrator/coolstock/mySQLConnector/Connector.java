package com.example.administrator.coolstock.mySQLConnector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Connector {
    public static void ConnectLocal(){
        //声明连接对象
        Connection con;
        //驱动程序名
        String driver="com.mysql.cj.jdbc.Driver";
        //URL指定要访问的数据库名mydata
        String url="jdbc:mysql://localhost:3306/cool_stock";
        //MySQL配置的用户名
        String user="root";
        //MySQL配置时的密码
        String password="123456";
        try{
            //加载驱动程序
            Class.forName(driver);
            //getConnect()方法，连接数据库
            con=DriverManager.getConnection(url,user,password);
            if (!con.isClosed()) {
                //创建statement类对象，用来执行SQL语句
                Statement statement=con.createStatement();
                //要执行的SQL语句
                String sql="select*from cs_user_account";
                //ResultSet类，用来存放获取的结果集
                ResultSet rs=statement.executeQuery(sql);
                while(rs.next()){
                    System.out.print(rs.getString("userId"));
                    System.out.print(rs.getString("userPassword"));
                }
                rs.close();
                con.close();
            }
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void main(String []args){
        ConnectLocal();
    }
}

