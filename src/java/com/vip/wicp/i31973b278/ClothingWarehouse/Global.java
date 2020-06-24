/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vip.wicp.i31973b278.ClothingWarehouse;

import java.io.File;

/**
 *
 * @author user
 */
public class Global {
    public static String getProjectRootPath(){    
        String rootPath=Thread.currentThread().getContextClassLoader().getResource("").getPath();    
        rootPath = rootPath.substring(1,rootPath.indexOf("WEB-INF"));
        File file = new File(rootPath);
        return file.getAbsolutePath();
    }
    
    public static String getProjectconFigurationPath(){    
        String path = getProjectRootPath();
        File file = new File(path);
        path = file.getParent()+"\\CWM";
        return path;
    }


}
