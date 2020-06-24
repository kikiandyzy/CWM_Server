/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vip.wicp.i31973b278.ClothingWarehouse;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 日志输出
 * @author user
 */
public class LogProduce {
    public static String NAME = "com.vip.wicp.i31973b278.ClothingWarehouse";
    
    public static boolean IFSET = false;
    
    //输出printTrackSpace()相同的信息
    private static String errorTrackSpace(Exception e) {
	StringBuffer sb = new StringBuffer();
	if (e != null) {
	    for (StackTraceElement element : e.getStackTrace()) {
	        sb.append("\r\n\t").append(element);
	    }
	}
	return sb.length() == 0 ? null : sb.toString();
    }
    
    private static void setLogger() throws IOException{
        //方法参数是Logger的名称，当名称相同时候，同一个名称的Logger只创建一个，实现各个类中共享
        Logger myLogger = Logger.getLogger(NAME);
        
        //在工程目录下创建log文件夹
        String filePath = Global.getProjectconFigurationPath()+"\\log";
        File dir = new File(filePath);
        if(!dir.exists() || !dir.isDirectory())
        dir.mkdir();

        //创建fileHandler 自定义类实例，用于在文件保存日志  
        FileHandler fileHandler;
        fileHandler = new FileHandler(filePath+"\\ClothingWarehouse.%u.%g.txt",1000,2,true);
        fileHandler.setFormatter(new FileLogFormatter());
        fileHandler.setLevel(Level.INFO);
        myLogger.addHandler(fileHandler);
    }
    
    public static void log(Exception e){
       try{
           if(!IFSET) setLogger();
           Logger myLogger = Logger.getLogger(NAME);
	   myLogger.log(Level.INFO, "exception toString and track space : {}\r\n"+ e.toString());
	   myLogger.log(Level.INFO, errorTrackSpace(e));
       }catch(IOException ie){
           Logger.getLogger(LogProduce.class.getName()).log(Level.SEVERE, null, ie);
       }
        
    }
}
