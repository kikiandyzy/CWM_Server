/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vip.wicp.i31973b278.ClothingWarehouse;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
 
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
 
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author user
 */
public class DOM {
    public static List<Order> importOrder(String filePath){
        List<Order> orderList = new ArrayList<>();
        File file = new File(filePath);
        if(file.exists() && file.isFile()){
            //创建DocumentBuilderFactory对象  
            try {
                DocumentBuilderFactory a = DocumentBuilderFactory.newInstance();  
                //创建DocumentBuilder对象  
                DocumentBuilder b = a.newDocumentBuilder();  
                //通过DocumentBuilder对象的parse方法返回一个Document对象  
                Document document = b.parse(file.getAbsolutePath()); 
                //通过Document对象的getElementsByTagName()返根节点的一个list集合  
                NodeList orderNodeList = document.getElementsByTagName("order");  
                for(int i =0; i<orderNodeList.getLength(); i++){
                    //获取id的属性值
                    
                    String orderID = document.getElementsByTagName("ID").item(i).getFirstChild().getNodeValue();
                    
                    NodeList orderNodes = document.getElementsByTagName("mapOrder").item(i).getChildNodes();
                    Map<String,Integer> orders = new HashMap<>();
                    
                    String clothingID = "";
                    int quantity = 0;
                    for(int j=0;j<orderNodes.getLength();j++) {
                        Node child = orderNodes.item(j);
                        switch(child.getNodeName()) {
                            case "clothingID":
                                clothingID = child.getFirstChild().getNodeValue();
                                break;
                            case "quantity":
                                quantity = Integer.valueOf(child.getFirstChild().getNodeValue());
                                break;
                        }
                        if(!clothingID.equals("") && quantity != 0){
                            orders.put(clothingID, quantity);
                            clothingID = "";
                            quantity = 0;
                        }
                    }
                    orderList.add(new Order(orderID,orders));
                }  
            } catch (Exception e) {  
                 LogProduce.log(e,new FileLogFormatter());
            } 
        }
        return orderList;
    }
}
