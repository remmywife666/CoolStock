package com.example.administrator.coolstock.mySQLConnector;

import com.example.administrator.coolstock.RegisteredP.SendMailThread;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RegisteredMSQL extends Thread{
    /**
     * 声明用户信息
     */
    private static String userId;
    private static String userPassword;

    public RegisteredMSQL(String userId,String userPassword){
        super();
        this.userId=userId;
        this.userPassword=userPassword;
    }

    @Override
    public void run(){
        super.run();
        ConnectLocal(userId,userPassword);
    }
    public  void ConnectLocal(String userId,String userPassword){

        //声明连接对象
        Connection con;
        //驱动程序名
        String driver="com.mysql.cj.jdbc.Driver";
        //URL指定要访问的数据库名mydata
        String url="jdbc:mysql://192.168.1.104:3306/cool_stock?&serverTimezone=UTC";
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
                PreparedStatement insertInfo;
                insertInfo=con.prepareStatement("insert into cs_user_account" +
                        "(userId,userPassword) values(?,?)");
                insertInfo.setString(1,userId);
                insertInfo.setString(2,userPassword);
                insertInfo.executeUpdate();
                insertInfo.close();
                con.close();
            }
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

        public static void main(String[]args){
            RegisteredMSQL registeredMSQL=new RegisteredMSQL("adfaf","dfasdfasd");
            registeredMSQL.start();

//            registeredMSQL.ConnectLocal("adfadf","adfaklsdjflk");
        }
}

