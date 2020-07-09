/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vip.wicp.i31973b278.ClothingWarehouse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 *数据库连接管理类，负责数据库连接的创建、管理和销毁
 * @author user
 */
public class DBManager extends HttpServlet{
    public ServletConfig config;
    private static String USERNAME;
    private static String PASSWORD;
    private static String URL;
    private static Connection CONNECTION = null;
    
    /**
     *
     * @param config
     * @throws javax.servlet.ServletException
     */
    @Override
    public void init(ServletConfig config) throws ServletException{
        super.init(config);
        this.config = config;
        USERNAME = config.getInitParameter("DBUserName");
        PASSWORD = config.getInitParameter("DBPassword");
        URL = config.getInitParameter("ConnectionURL");
        //这里是从web.xml配置文件中读取初始化参数
        
    }
    
    //建立连接并获取连接对象
    public static Connection getConnection(){
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();//反射
            CONNECTION = DriverManager.getConnection(URL,USERNAME,PASSWORD);
        }catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return CONNECTION;
    }
    
    //建立连接并获取连接对象
    public static Connection getConnection(String userName, String password){
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();//反射
            CONNECTION = DriverManager.getConnection(URL,userName,password);
        }catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return CONNECTION;
    }
    
    
    //关闭链接资源和结果集
    public static void closeAll(Connection connection, Statement statement,ResultSet resultSet){
        try{
            if(connection != null) connection.close();
            if(statement != null) statement.close();
            if(resultSet != null) resultSet.close();
        }catch(SQLException ex){
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
