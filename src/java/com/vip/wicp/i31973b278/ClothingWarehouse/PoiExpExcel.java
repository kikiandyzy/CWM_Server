/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vip.wicp.i31973b278.ClothingWarehouse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;

/**
 *负责从xlsx表格文件中读取数据
 * @author user
 */
public class PoiExpExcel {
    
    //从文件中读取初始化仓库的文件
    public static List<Shelf> getShelves(String filePath){
        List<Shelf> shelves = new ArrayList<>();
        File file = new File(filePath);
        
        if(file.exists() && file.isFile()){
            try{
                
                //获取工作簿
		FileInputStream fs=FileUtils.openInputStream(file);
		HSSFWorkbook workbook=new HSSFWorkbook(fs);
                
                 //获取第一个工作表
		HSSFSheet hs=workbook.getSheetAt(0);
                
                //获取工作表的第一个行号和最后一个行号
	        int firstRow=hs.getFirstRowNum();
	        int lastRow=hs.getLastRowNum();
                 
                //获取第一行的第一个列号和最后一个列号
	        int firstCellNum=hs.getRow(firstRow).getFirstCellNum();
                int lastCellNum=hs.getRow(firstRow).getLastCellNum();
                
                String shelfID = null;
                int storageLocation = 0;
                String clothingID =null;
                int quantity = 0;
                
                //开始遍历工资表，从第二行开始，第一行是标题
                for (int i = firstRow+1; i <=lastRow; i++) {
                    
                    //获取其中一行
                    HSSFRow row=hs.getRow(i);
                    //遍历每行中的每列
                    for (int j = firstCellNum; j <lastCellNum; j++){
                        //获取每一格的内容
                        HSSFCell cell=row.getCell(j);
                        //将内容转化为string形式
                        cell.setCellType(CellType.STRING);
			String value=cell.getStringCellValue();
                        
                        switch(j) {
			    case 0:
                                //第一行是仓库id,但是有可能为空
				if(!value.equals("")) {
                                    shelfID = value;
				}
				break;
                            case 1:
				storageLocation = Integer.valueOf(value);
				break;
                            case 2:
                                 clothingID = value;
				break;
                            case 3:
				if(!value.equals("")) {
                                    quantity = Integer.valueOf(value);
				}else{
                                    quantity = 0;
                                }
				break;
			}
                    }
                    shelves.add(new Shelf(shelfID,storageLocation,clothingID,quantity));
                }
                
                
            }catch(IOException e){
                 LogProduce.log(e,new FileLogFormatter());
            }
        }
        return shelves;
    }
    
    //读入新到货
    public static Map<String,Integer> getClothings(String filePath){
        Map<String,Integer> slothings = new HashMap<>();
        File file = new File(filePath);
        
        if(file.exists() && file.isFile()){
            try{
                
                //获取工作簿
		FileInputStream fs=FileUtils.openInputStream(file);
		HSSFWorkbook workbook=new HSSFWorkbook(fs);
                
                 //获取第一个工作表
		HSSFSheet hs=workbook.getSheetAt(0);
                
                //获取工作表的第一个行号和最后一个行号
	        int firstRow=hs.getFirstRowNum();
	        int lastRow=hs.getLastRowNum();
                 
                //获取第一行的第一个列号和最后一个列号
	        int firstCellNum=hs.getRow(firstRow).getFirstCellNum();
                int lastCellNum=hs.getRow(firstRow).getLastCellNum();
                
                
                String clothingID =null;
                int quantity = 0;
                
                //开始遍历工资表，从第二行开始，第一行是标题
                for (int i = firstRow+1; i <=lastRow; i++) {
                    
                    //获取其中一行
                    HSSFRow row=hs.getRow(i);
                    //遍历每行中的每列
                    for (int j = firstCellNum; j <lastCellNum; j++){
                        //获取每一格的内容
                        HSSFCell cell=row.getCell(j);
                        //将内容转化为string形式
                        cell.setCellType(CellType.STRING);
			String value=cell.getStringCellValue();
                        
                        switch(j) {
			    case 0:
                                //第一行是仓库id,但是有可能为空
				if(!value.equals("")) {
                                    clothingID = value;
				}
				break;
                            case 1:
				quantity = Integer.valueOf(value);
				break;
                           
			}
                    }
                    slothings.put(clothingID, quantity);
                }
            }catch(IOException e){
                 LogProduce.log(e,new FileLogFormatter());
            }
        }
        return slothings;
    }
    
    //导入订单
    public static List<Order> importOrder(String filePath){
        //List<Order> orders = new ArrayList<>();
        Map<String,Order> orderMap = new HashMap<>();
        File file = new File(filePath);
        
        if(file.exists() && file.isFile()){
            try{
                
                //获取工作簿
		FileInputStream fs=FileUtils.openInputStream(file);
		HSSFWorkbook workbook=new HSSFWorkbook(fs);
                
                 //获取第一个工作表
		HSSFSheet hs=workbook.getSheetAt(0);
                
                //获取工作表的第一个行号和最后一个行号
	        int firstRow=hs.getFirstRowNum();
	        int lastRow=hs.getLastRowNum();
                 
                //获取第一行的第一个列号和最后一个列号
	        int firstCellNum=hs.getRow(firstRow).getFirstCellNum();
                int lastCellNum=hs.getRow(firstRow).getLastCellNum();
                
                String orderID = null;
                String clothingID =null;
                int quantity = 0;
                
                //开始遍历工资表，从第二行开始，第一行是标题
                for (int i = firstRow+1; i <=lastRow; i++) {
                    
                    //获取其中一行
                    HSSFRow row=hs.getRow(i);
                    //遍历每行中的每列
                    for (int j = firstCellNum; j <lastCellNum; j++){
                        //获取每一格的内容
                        HSSFCell cell=row.getCell(j);
                        //将内容转化为string形式
                        cell.setCellType(CellType.STRING);
			String value=cell.getStringCellValue();
                        
                        switch(j) {
			    case 0:
                                //第一行是订单id,但是有可能为空
				if(!value.equals("")) {
                                    orderID = value;
				}
				break;
                            case 1:
				clothingID = value;
				break;
                            case 2:
                                quantity = Integer.valueOf(value);
                                break;
			}
                    }
                    
                    if(orderMap.containsKey(orderID)){
                        //已经存在
                       orderMap.get(orderID).getOrder().put(clothingID, quantity);
                    }else{
                        orderMap.put(orderID,newOrder(orderID,clothingID,quantity));
                    }
                }
            }catch(IOException e){
                 LogProduce.log(e,new FileLogFormatter());
            }
        }
        List<Order> orderList = new ArrayList<>();
        for(Map.Entry<String,Order> entry:orderMap.entrySet()){
            orderList.add(entry.getValue());
        }
        return orderList;
    }
    
    
    public static void CheckRecord(List<Shelf> shelves,String userName){
        try{
            //第一步，创建一个workbook对应一个excel文件
            HSSFWorkbook workbook = new HSSFWorkbook();
            //第二步，在workbook中创建一个sheet对应excel中的sheet
            HSSFSheet sheet = workbook.createSheet("盘点记录");
            //添加第零行
            int i=0;
            HSSFRow row = sheet.createRow(i);
            //第四步，创建单元格，设置表头
            HSSFCell cell = row.createCell(0);
            cell.setCellValue("货架");
            cell = row.createCell(1);
            cell.setCellValue("位置");
            cell = row.createCell(2);
            cell.setCellValue("服装");
            cell = row.createCell(3);
            cell.setCellValue("数量");
            
            //填充数据
            i++;
            for(Shelf shelf:shelves){
                row = sheet.createRow(i);
                for(int j=0;j<4;j++){
                    cell = row.createCell(j);
                    switch(j){
                        case 0:
                            cell.setCellValue(shelf.getShelfID());
                            break;
                        case 1:
                            cell.setCellValue(shelf.getStorageLocation());
                            break;
                        case 2:
                            cell.setCellValue(shelf.getClothingID());
                            break;
                        case 3:
                            cell.setCellValue(shelf.getQuantity());
                            break;
                    }
                }
                i++;
            }
            
            //写入文件
            FileOutputStream fos = new FileOutputStream(Global.getProjectconFigurationPath()+"\\checkRecord\\"+getFileName(userName));
            workbook.write(fos);
            fos.close();

        }catch(IOException e){
             LogProduce.log(e,new FileLogFormatter());
        }
    }
    
    //合成一个订单
    private static Order newOrder(String orderID,String clothingID,int quantity){
        Map<String,Integer> order = new HashMap<>();
        order.put(clothingID, quantity);
        return new Order(orderID,order);
    }
    
    private static String getFileName(String userName){
        DateFormat df = new SimpleDateFormat("_yyyy_MM_dd_HH_mm_ss");
        return userName+df.format(new Date())+".xls";
    }
}
