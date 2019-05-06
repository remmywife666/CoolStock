package com.example.administrator.coolstock.mySQLConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBService {

    private Connection conn=null; //打开数据库对象
    private PreparedStatement ps=null; //操作整合sql语句的对象
    private ResultSet rs=null; //查询结果的集合

    //DBService对象
    public static DBService dbService=null;

    /**
     * 构造方法 私有化
     */
    public DBService(){

    }

    /**
     * 获取MySQL数据库单例类对象
     */
    public static DBService getDbService(){
        if (dbService==null){
            dbService=new DBService();
        }
        return dbService;
    }

    /**
     * 插入注册信息
     */
    public void insertUserMSG(String userId,String userPassword){
        conn=DBOpenHelper.getConn();
        //MYSQL语句
        String sql="insert into cs_user_account(userId,userPassword) values(?,?)";
        try {
            boolean closed=conn.isClosed();
            if ((conn!=null)&&(!closed)){
                ps=(PreparedStatement)conn.prepareStatement(sql);
                ps.setString(1,userId);
                ps.setString(2,userPassword);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBOpenHelper.closeAll(conn,ps);  //关闭相关操作
    }

    public static void main(String[]args){
        DBService db=new DBService();
        db.insertUserMSG("dasasfasdfa","asdasdasdasd");
    }
}
