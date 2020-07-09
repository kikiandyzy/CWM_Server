/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vip.wicp.i31973b278.ClothingWarehouse;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;


/**
 *
 * @author user
 */
public class QueryServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet QueryServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet QueryServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        doPost(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        // 设置响应内容类型  
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        
        try (PrintWriter out = response.getWriter()) {
            
            
            JSONArray jsonArray = new JSONArray();
            List<Shelf> shelves = new ArrayList<>();
            List<User> users = new ArrayList<>();
            String userName = request.getParameter("userName").trim();
            String password = request.getParameter("Password").trim();
            
            //获得请求中传来的操作和相应参数
            String operation = request.getParameter("operation").trim();
            
            switch(operation){
                case "queryShelfID":
                    String shlefID = request.getParameter("shelfID").trim();
                    shelves = ShelfDAO.queryShelfID(shlefID,userName,password);
                    //输出记录
                    PoiExpExcel.CheckRecord(shelves, userName);
                    break;
                case "queryClothingID":
                    String clothingID = request.getParameter("clothingID").trim();
                    
                    shelves = ShelfDAO.queryClothingID(clothingID,userName,password);
                    break;
                case "queryStorageLocation":
                    shlefID = request.getParameter("shelfID").trim();
                    int storageLocation = Integer.valueOf(request.getParameter("storageLocation").trim());
                    
                    shelves.add(ShelfDAO.queryStorageLocationID(shlefID, storageLocation,userName,password));
                    break;
                case "queryEmployee":
                    users = UserDAO.queryEmployee();
                    break;
                case "import":
                    File file = new File(Global.getProjectconFigurationPath()+"\\newClothings.xls");
                    Map<String,Integer> clothings = PoiExpExcel.getClothings(file.getAbsolutePath());
                    boolean success = ShelfDAO.importShelves(clothings, userName, password);
                    if(success){
                        int clothingCount = 0;
                        int quantity = 0;
                        for(Map.Entry<String, Integer> entry : clothings.entrySet()){
                            clothingCount +=1;
                            quantity += entry.getValue();
                        }
                        jsonArray.add("一共导入"+clothingCount+"种衣服，总计"+quantity+"件");
                        file.delete();
                    }else{
                        jsonArray.add("导入失败");
                    }
                    break;
            }
            
            
            if(!shelves.isEmpty()){
                 for(Shelf shelf:shelves){
                     jsonArray.add(shelf);
                 }
                 
            }
            if(!users.isEmpty()){
                for(User user:users){
                    jsonArray.add(user);
                }
                
            }
            out.write(jsonArray.toString());
        }catch(Exception e){
            LogProduce.log(e,new FileLogFormatter());
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    
    
}
/*
http://i31973b278.wicp.vip/ClothingWarehouse/QueryServlet
http://i31973b278.wicp.vip/ClothingWarehouse/QueryServlet?operation=queryShelfID&shelfID=A&userName=yzy&Password=874432636
http://i31973b278.wicp.vip/ClothingWarehouse/QueryServlet?operation=queryShelfID&shelfID=ALL&userName=yzy&Password=874432636
http://i31973b278.wicp.vip/ClothingWarehouse/QueryServlet?operation=queryEmployee&userName=yzy&Password=874432636
http://i31973b278.wicp.vip/ClothingWarehouse/QueryServlet?operation=queryClothingID&clothingID=test&userName=yzy&Password=874432636
http://i31973b278.wicp.vip/ClothingWarehouse/QueryServlet?operation=queryStorageLocation&shelfID=A&storageLocation=1&userName=yzy&Password=874432636
http://i31973b278.wicp.vip/ClothingWarehouse/QueryServlet?operation=import&userName=yzy&Password=874432636
*/