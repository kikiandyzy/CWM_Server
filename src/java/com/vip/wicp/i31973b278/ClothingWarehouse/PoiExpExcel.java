/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vip.wicp.i31973b278.ClothingWarehouse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
                 Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return shelves;
    }
    
    
}
