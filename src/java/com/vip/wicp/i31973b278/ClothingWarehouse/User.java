/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vip.wicp.i31973b278.ClothingWarehouse;

/**
 *登录用户
 * @author user
 */
public class User {
    
    //用户姓名
    private String userName;

    //用户密码
    private String password;
    
    private String identity;
    
    public User(){
        
    }
    
    public User(String userName,String password,String identity){
        this.userName = userName;
        this.password = password;
        this.identity = identity;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getIdentity(){
        return identity;
    }
    
    public void setIdentity(String identity){
        this.identity = identity;
    }
}
