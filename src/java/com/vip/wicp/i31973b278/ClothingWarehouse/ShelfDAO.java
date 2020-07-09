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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 *
 * @author user
 */
public class ShelfDAO {
    
    private static final String QUERYSHELFID = "select * from shelves where shelfID = ? and quantity > 0" ;
    private static final String QUERYCLOTHINGID = "select * from shelves where clothingID = ?" ;
    private static final String QUERYSTORAGELOCATION = "select * from shelves where shelfID = ? and storageLocation = ?" ;
    private static final String TRUNCATETABLE = "truncate table shelves";
    private static final String INSERTTABLE = "insert into shelves (shelfID, storageLocation, clothingID, quantity) values(?,?,?,?)";
    private static final String QUERYALLSHELVES = "select * from shelves where quantity > 0" ;
    private static final String QUERYEMPTYSHELVES = "select * from shelves where quantity = 0" ;
    private static final String UPDATELSHELVES = "update shelves set clothingID = ? , quantity = ? where shelfID = ? and storageLocation = ?";
    private static final String UPDATEQUANTITY = "update shelves set quantity = quantity + ? where shelfID = ? and storageLocation = ?";
    
    /**
    *@param shelfID
    *@return 该货架所有的库存
    */
    public static List<Shelf> queryShelfID(String shelfID,String userName, String password){

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Shelf> shelves = new ArrayList<>();
        //开始连接
        try{
            connection = DBManager.getConnection(userName,password);
            if(shelfID.equals("ALL")){
                preparedStatement = connection.prepareStatement(QUERYALLSHELVES);
            }else{
                preparedStatement = connection.prepareStatement(QUERYSHELFID);
                preparedStatement.setString(1, shelfID);//给上面的参数（？）赋值
            }
            
            resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                String sd = resultSet.getString("shelfID");
                int storageLocation = resultSet.getInt("storageLocation");
                String clothingID = resultSet.getString("clothingID");
                int quantity = resultSet.getInt("quantity");
                shelves.add(new Shelf(sd,storageLocation,clothingID,quantity));
            }
        }catch(SQLException e){
            //Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
            LogProduce.log(e,new FileLogFormatter());
        }finally{
            DBManager.closeAll(connection, preparedStatement, resultSet);
        }
        return shelves;
    }
    
    /*
    *@param clothingID
    *@return 该型号服装所有的库存
    *
    */
    public static List<Shelf> queryClothingID(String clothingID,String userName, String password){

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Shelf> shelves = new ArrayList<>();
        //开始连接
        try{
            connection = DBManager.getConnection(userName,password);
            preparedStatement = connection.prepareStatement(QUERYCLOTHINGID);
            preparedStatement.setString(1, clothingID);//给上面的参数（？）赋值
            resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                String shelfID = resultSet.getString("shelfID");
                int storageLocation = resultSet.getInt("storageLocation");
                int quantity = resultSet.getInt("quantity");
                if(!clothingID.equals("") && quantity != 0)
                shelves.add(new Shelf(shelfID,storageLocation,clothingID,quantity));
            }
        }catch(SQLException e){
            //Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
            LogProduce.log(e,new FileLogFormatter());
        }finally{
            DBManager.closeAll(connection, preparedStatement, resultSet);
        }
        return shelves;
    }
    
    /*
    *@param shelfID
    *@param clothingID
    *@return 该位置的库存呢
    *
    */
    public static Shelf queryStorageLocationID(String shelfID,int storageLocation,String userName, String password){

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        //开始连接
        try{
            connection = DBManager.getConnection(userName,password);
            preparedStatement = connection.prepareStatement(QUERYSTORAGELOCATION);
            preparedStatement.setString(1, shelfID);//给上面的参数（？）赋值
            preparedStatement.setString(2, ""+storageLocation);//给上面的参数（？）赋值
            resultSet = preparedStatement.executeQuery();
            
            if(resultSet.next()){
                String clothingID = resultSet.getString("clothingID");
                int quantity = resultSet.getInt("quantity");
                if(!clothingID.equals("") && quantity != 0)
                return new Shelf(shelfID,storageLocation,clothingID,quantity);
            }
        }catch(SQLException e){
            //Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
            LogProduce.log(e,new FileLogFormatter());
        }finally{
            DBManager.closeAll(connection, preparedStatement, resultSet);
        }
        return null;
    }
    
    //获取所有有效的货架
    public static List<Shelf> getAllShelves(){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Shelf> shelves = new ArrayList<>();
        //开始连接
        try{
            connection = DBManager.getConnection();
            preparedStatement = connection.prepareStatement(QUERYALLSHELVES);
            resultSet = preparedStatement.executeQuery();
            
            
            while(resultSet.next()){
                String shelfID = resultSet.getString("shelfID");
                int storageLocation = resultSet.getInt("storageLocation");
                String clothingID = resultSet.getString("clothingID");
                int quantity = resultSet.getInt("quantity");
                shelves.add(new Shelf(shelfID,storageLocation,clothingID,quantity));
            }
        }catch(SQLException e){
            //Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
            LogProduce.log(e,new FileLogFormatter());
        }finally{
            DBManager.closeAll(connection, preparedStatement, resultSet);
        }
        return shelves;
    }
    
    //判断传过来的订单是否可以处理
    public static boolean canHandleOrder(Map<String,Integer> order,String userName,String password){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean answer = true;
       //开始连接
        try{
            connection = DBManager.getConnection(userName,password);
            preparedStatement = connection.prepareStatement(QUERYCLOTHINGID);
            
            for (Map.Entry<String, Integer> entry : order.entrySet()){
                String clothingID = entry.getKey();
                int count = entry.getValue();
                preparedStatement.setString(1, clothingID);//给上面的参数（？）赋值
                resultSet = preparedStatement.executeQuery();
                int quantity = 0;
                //查看该id的衣服在库存里面又多少件
                while(resultSet.next()){
                    quantity += resultSet.getInt("quantity");
                }
                //购买数量大于剩余数量则退出
                if(count > quantity){
                    answer = false;
                    break;
                }
            }
        }catch(SQLException e){
            //Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
            LogProduce.log(e,new FileLogFormatter());
        }finally{
            DBManager.closeAll(connection, preparedStatement, resultSet);
        }
        return answer;
    }
    
    //处理订单，这里前提是订单能被处理
    public static void HandleOrder(Map<String,Integer> order,String userName,String password){
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        //被修改过的货架名单
        List<Shelf> shelves = new ArrayList<>();
        //开始连接
        try{
            connection = DBManager.getConnection(userName,password);
            preparedStatement = connection.prepareStatement(QUERYCLOTHINGID);
            for (Map.Entry<String, Integer> entry : order.entrySet()){
                //先取出每件衣服的货架
                String clothingID = entry.getKey();
                int count = entry.getValue();
                preparedStatement.setString(1, clothingID);//给上面的参数（？）赋值
                resultSet = preparedStatement.executeQuery();
                
                while(resultSet.next()){
                    int quantity = resultSet.getInt("quantity");
                    String shelfID = resultSet.getString("shelfID");
                    int storageLocation = resultSet.getInt("storageLocation");
                    //如果一个货架就满足需求了
                    if(quantity >= count){
                        quantity -= count;
                        shelves.add(new Shelf(shelfID,storageLocation,clothingID,quantity));
                        break;
                    }else{//不满足继续找下一个货架
                        count -= quantity;
                        shelves.add(new Shelf(shelfID,storageLocation,clothingID,0));
                    }
                }
            }
            
            //进行回写
            updateShelf(shelves,connection);
        }catch(SQLException e){
            //Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
           LogProduce.log(e,new FileLogFormatter());
        }finally{
            DBManager.closeAll(connection, preparedStatement, resultSet);
        }
        
    }
    
    //处理订单后更新数据库
    private static void updateShelf(List<Shelf> shelves,Connection connection){
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
            //开始连接
        try{     
            //插入数据数据
            preparedStatement = connection.prepareStatement(UPDATELSHELVES);
             for(int i=0;i<shelves.size();i++){
                 Shelf shelf = shelves.get(i);
                //给上面的参数（？）赋值
                int quantity = shelf.getQuantity();
                if(0 == quantity){//数量为0就不写入衣服编号了,相当于清空
                    preparedStatement.setString(1, "");
                }else{
                    preparedStatement.setString(1, shelf.getClothingID());
                }
                preparedStatement.setString(2, ""+quantity);
                preparedStatement.setString(3, shelf.getShelfID());
                preparedStatement.setString(4, ""+shelf.getStorageLocation());
                preparedStatement.executeUpdate();
            }
                
        }catch(SQLException e){
                // Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
            LogProduce.log(e,new FileLogFormatter());
        }finally{
            DBManager.closeAll(connection, preparedStatement, resultSet);
               
        }
       return;
    }
    
    public static boolean handleReturnOrder(Map<String,Integer> order,String userName,String password){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean answer = false;
        //开始连接
        try{
            connection = DBManager.getConnection(userName,password);
            
            for (Map.Entry<String, Integer> entry : order.entrySet()){
                String clothingID = entry.getKey();
                int quantity = entry.getValue();
                //先找到该衣服的存储位置
                preparedStatement = connection.prepareStatement(QUERYCLOTHINGID);
                preparedStatement.setString(1, clothingID);//给上面的参数（？）赋值
                resultSet = preparedStatement.executeQuery();
                if(resultSet.next()){
                    //首先查询改衣服的存储位置
                    String shelfID = resultSet.getString("shelfID");
                    int storageLocation = resultSet.getInt("storageLocation");
                    
                    //更新
                    preparedStatement = connection.prepareStatement(UPDATEQUANTITY);
                    preparedStatement.setInt(1, quantity);
                    preparedStatement.setString(2, shelfID);
                    preparedStatement.setString(3, ""+storageLocation);
                    preparedStatement.executeUpdate();
                }
                answer = true;
            }
        }catch(SQLException e){
            //Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
            LogProduce.log(e,new FileLogFormatter());
        }finally{
            DBManager.closeAll(connection, preparedStatement, resultSet);
        }
        return answer;
    }
    
    public static boolean importShelves(Map<String,Integer> clothings,String userName,String password){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean answer = false;
        //开始连接
        try{
            connection = DBManager.getConnection(userName,password);
                
            //查找所有空位
            preparedStatement = connection.prepareStatement(QUERYEMPTYSHELVES);
            resultSet = preparedStatement.executeQuery();
            List<Shelf> shelves = new ArrayList<>();
            int i=0;
            while(resultSet.next()){
                if(i < clothings.size()){
                    String shelfID = resultSet.getString("shelfID");
                    int storageLocation = resultSet.getInt("storageLocation");
                    shelves.add(new Shelf(shelfID,storageLocation));
                    i++;
                }else{
                    break;
                }
            }
            
            preparedStatement = connection.prepareStatement(UPDATELSHELVES);
            i = 0;
            for(Map.Entry<String, Integer> entry : clothings.entrySet()){
                preparedStatement.setString(1, entry.getKey());
                preparedStatement.setInt(2, entry.getValue());
                preparedStatement.setString(3, shelves.get(i).getShelfID());
                preparedStatement.setInt(4, shelves.get(i).getStorageLocation());
                preparedStatement.executeUpdate();
                i++;
            }
            answer = true;
        }catch(SQLException e){
                // Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
            LogProduce.log(e,new FileLogFormatter());
        }finally{
            DBManager.closeAll(connection, preparedStatement, resultSet);
               
        }
       return answer;
    }
    //初始化整个表
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
            LogProduce.log(e,new FileLogFormatter());
        }finally{
            DBManager.closeAll(connection, preparedStatement, resultSet);
               
        }
       return;
    }
}
