/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vip.wicp.i31973b278.ClothingWarehouse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;



/**
 *
 * @author user
 */
public class OrderServlet extends HttpServlet {

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
            out.println("<title>Servlet OrderServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet OrderServlet at " + request.getContextPath() + "</h1>");
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
        //processRequest(request, response);
        // 设置响应内容类型  
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        JSONArray josnArray = new JSONArray();
        
        
        try (PrintWriter out = response.getWriter()) {
            
            //获得请求中传来的操作和相应参数
            String operation = request.getParameter("operation").trim();
            switch(operation){
                case "refresh":
                    josnArray.add(getRandomOrder());//随机给出一个订单
                    break;
                case "handle":
                    //先读取客户端传过来的订单
                    String data = request.getParameter("orders").trim();
                    Gson gson = new Gson();
                    List<Order> orders = gson.fromJson(data,new TypeToken<List<Order>>(){}.getType());
                    
                    //获取连接的操作人员的账号密码
                    String userName = request.getParameter("userName").trim();
                    String password = request.getParameter("password").trim();
                    for(Order order:orders){
                        //先判断能不能处理
                        if(ShelfDAO.canHandleOrder(order.getOrder(), userName, password)){
                            ShelfDAO.HandleOrder(order.getOrder(), userName, password);
                        }else{
                            //不能处理的订单返回
                            josnArray.add(order);
                        }
                    }
                    //回应
                    break;
                case "salesReturn"://退货
                    data = request.getParameter("orders").trim();
                    gson = new Gson();
                    orders = gson.fromJson(data,new TypeToken<List<Order>>(){}.getType());
                    userName = request.getParameter("userName").trim();
                    password = request.getParameter("password").trim();
                    boolean answer = true;
                    for(Order order:orders){
                        answer &= ShelfDAO.handleReturnOrder(order.getOrder(), userName, password);
                    }
                    josnArray.add(""+answer);
                case "import"://导入
                    String type = request.getParameter("type").trim();
                    List<Order> hh = new ArrayList<>();
                    File file;
                    if(type.equals("xls")){
                        file = new File(Global.getProjectconFigurationPath()+"\\order.xls");
                        hh = PoiExpExcel.importOrder(file.getAbsolutePath());
                        file.delete();
                    }else if(type.equals("xml")){
                        file = new File(Global.getProjectconFigurationPath()+"\\order.xml");
                        hh = DOM.importOrder(file.getAbsolutePath());
                        file.delete();
                    }
                   
                    
                    for(Order order:hh){
                        josnArray.add(order);
                    }
                    break;
            }
            out.write(josnArray.toString());
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
    
    private Order getRandomOrder(){
        List<Shelf> shelves = ShelfDAO.getAllShelves();
        Random random = new Random();
        //随机从中取一个货架出来
        Shelf shelf = shelves.get(random.nextInt(shelves.size()));
        //取一个合法有效的数量
        int quantity = random.nextInt(shelf.getQuantity())+1;
        
        Map<String,Integer> order = new HashMap<>();
        order.put(shelf.getClothingID(), quantity);
        
        return new Order(getRandomID(),order);
    } 
    
    
    private String getRandomID(){
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0;i<8;i++){
            //0-1取值
            int judge = random.nextInt(2);
            switch(judge){
                case 0:
                    stringBuilder.append(random.nextInt(10));
                    break;
                case 1:
                    char c=(char)(int)(Math.random()*26+97);
                    stringBuilder.append(c);
                    break;
            }
        }
        return stringBuilder.toString();
    }
    
 
}
/*
http://i31973b278.wicp.vip/ClothingWarehouse/OrderServlet?operation=refresh
http://i31973b278.wicp.vip/ClothingWarehouse/OrderServlet?operation=import&type=xls
http://i31973b278.wicp.vip/ClothingWarehouse/OrderServlet?operation=import&type=xml
*/