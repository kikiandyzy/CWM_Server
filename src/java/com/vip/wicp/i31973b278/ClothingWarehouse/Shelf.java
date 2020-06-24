/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vip.wicp.i31973b278.ClothingWarehouse;

/**
 *货架类
 * @author user
 */
public class Shelf {
    public static int SHEIFQANTITY = 4;
    public static int STORAGEQUANTITY = 12;
	
    private String shelfID; //货架id
    private int storageLocation; //货位
    private String clothingID; //所存储的衣服
    private int quantity; //所存的衣服的数量
    
    public Shelf(){
        this.shelfID = "";
	this.storageLocation = 0;
        this.clothingID = "";
	this.quantity = 0;
    }
    
    public Shelf(String shelfID,int storageLocation) {
	this.shelfID = shelfID;
	this.storageLocation = storageLocation;
        this.clothingID = "";
	this.quantity = 0;
    }
	
    public Shelf(String shelfID,int storageLocation,String clothing,int quantity) {
	this.shelfID = shelfID;
	this.storageLocation = storageLocation;
	this.clothingID = clothing;
	this.quantity = quantity;
    }
    
    public void setLocation(String shelfID,int storageLocation) {
	this.shelfID = shelfID;
	this.storageLocation = storageLocation;
    }
    
    public String show(){
       return getShelfID()+"-"+getStorageLocation()+"-"+getClothingID()+"-"+getQuantity();
    }

    /**
     * @return the shelfID
     */
    public String getShelfID() {
        return shelfID;
    }

    /**
     * @return the storageLocation
     */
    public int getStorageLocation() {
        return storageLocation;
    }

    /**
     * @return the clothingID
     */
    public String getClothingID() {
        return clothingID;
    }

    /**
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }
    
    
}
