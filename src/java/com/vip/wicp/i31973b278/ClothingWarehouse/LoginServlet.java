/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vip.wicp.i31973b278.ClothingWarehouse;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;

/**
 *
 * @author user
 */
public class LoginServlet extends HttpServlet {

    

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
            
            Map<String, String> params = new HashMap<>();
            JSONObject jsonObject = new JSONObject();
            
           
            
            //获得请求中传来的用户名和密码
            String accountNumber = request.getParameter("AccountNumber").trim();
            String password = request.getParameter("Password").trim();
            
          
            
            //密码验证结果
            Boolean verifyResult = verifyLogin(accountNumber, password);
            //Boolean verifyResult = true;
            

            if (verifyResult) {
                params.put("Result", "success");
            } else {
                params.put("Result", "failed");
            }
            
            initShelf();
            jsonObject.put("params", params);
            out.write(jsonObject.toString());
        }
        
    }

    private Boolean verifyLogin(String userName, String password) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        User user = UserDAO.queryUser(userName);//查询是否存在该用户
        return null != user && password.equals(user.getPassword());
    }
    
    //初始化数据库中的shelf表
    private void initShelf(){
        File file = new File(Global.getProjectconFigurationPath()+"\\clothings.xls");
        if(file.exists()){
            List<Shelf> shelves = PoiExpExcel.getShelves(file.getAbsolutePath());
            ShelfDAO.initShelves(shelves);
            file.delete();
        }
       return;
    }

}
    //登录 http://localhost:8080/cat.jpg
    //外网登录 http://3043f01k26.qicp.vip/kiki.png
    //http://i31973b278.wicp.vip/ClothingWarehouse/LoginServlet?AccountNumber=yzy&Password=874432636
    //http://i31973b278.wicp.vip/BambooMr/LoginServlet?AccountNumber=yzy&Password=874432636