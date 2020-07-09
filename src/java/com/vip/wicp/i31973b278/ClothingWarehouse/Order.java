/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vip.wicp.i31973b278.ClothingWarehouse;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *订单类
 * @author user
 */
public class Order {
    private String ID;
    private String date;
    private Map<String,Integer> order;
    
    public Order(String ID,Map<String,Integer> order){
        this.ID = ID;
        this.order = order;
        this.date = String.format("%tc", new Date());
    }
    
   
    
    /**
     * @return the ID
     */
    public String getID() {
        return ID;
    }

    /**
     * @param ID the ID to set
     */
    public void setID(String ID) {
        this.ID = ID;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return the order
     */
    public Map<String,Integer> getOrder() {
        return order;
    }

    /**
     * @param order the order to set
     */
    public void setOrder(Map<String,Integer> order) {
        this.order = order;
    }
    
    public void addOrder(String id,int quantity){
        if(order.isEmpty()) order = new HashMap<>();
        order.put(id, quantity);
    }
    
}
