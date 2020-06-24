/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vip.wicp.i31973b278.ClothingWarehouse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *Data Access Object数据库访问对象
 * @author user
 */
public class UserDAO {
    private static final String QUERYUSER = "select * from user where UserName = ?" ; 
    //先写一个查询函数
    public static User queryUser(String userName){
        
       Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        //开始连接
        try{
            connection = DBManager.getConnection();
            preparedStatement = connection.prepareStatement(QUERYUSER);
            preparedStatement.setString(1, userName);//给上面的参数（？）赋值
            resultSet = preparedStatement.executeQuery();
            User user = new User();
            if(resultSet.next()){
                user.setUserName(resultSet.getString("UserName"));
                user.setPassword(resultSet.getString("Password"));
                return user;
            }else{
                return null;
            }
        }catch(SQLException ex){
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            DBManager.closeAll(connection, preparedStatement, resultSet);
        }
        return null;
    }
}
