/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vip.wicp.i31973b278.ClothingWarehouse;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class ShelfDAO {
    
    private static final String QUERSHELVES = "select * from shelves where shelfID = ?" ;
    private static final String TRUNCATETABLE = "truncate table shelves";
    private static final String INSERTTABLE = "insert into shelves (shelfID, storageLocation, clothingID, quantity) values(?,?,?,?)";
    
    //一个查询函数
    public static Shelf queryUser(String shelfID){

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        //开始连接
        try{
            connection = DBManager.getConnection();
            preparedStatement = connection.prepareStatement(QUERSHELVES);
            preparedStatement.setString(1, shelfID);//给上面的参数（？）赋值
            resultSet = preparedStatement.executeQuery();
            
            if(resultSet.next()){
                int storageLocation = resultSet.getInt("storageLocation");
                String clothingID = resultSet.getString("clothingID");
                int quantity = resultSet.getInt("quantity");
                return new Shelf(shelfID,storageLocation,clothingID,quantity);
            }else{
                return null;
            }
        }catch(SQLException e){
            //Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
            LogProduce.log(e);
        }finally{
            DBManager.closeAll(connection, preparedStatement, resultSet);
        }
        return null;
    }
    
    //更新整个表
    public static void initShelves(List<Shelf> shelves){
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
            
            //开始连接
        try{
            connection = DBManager.getConnection();
                
                //首先删除数据库中shelves表中的原有数据
            preparedStatement = connection.prepareStatement(TRUNCATETABLE);
            preparedStatement.executeUpdate();
                 
                //插入数据数据
            preparedStatement = connection.prepareStatement(INSERTTABLE);
             for(int i=0;i<shelves.size();i++){
                    //给上面的参数（？）赋值
                preparedStatement.setString(1, shelves.get(i).getShelfID());
                preparedStatement.setString(2, ""+shelves.get(i).getStorageLocation());
                preparedStatement.setString(3, shelves.get(i).getClothingID());
                preparedStatement.setString(4, ""+shelves.get(i).getQuantity());
                preparedStatement.executeUpdate();
            }
                
        }catch(SQLException e){
                // Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
            LogProduce.log(e);
        }finally{
            DBManager.closeAll(connection, preparedStatement, resultSet);
               
        }
       return;
    }
}
