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
public class TestServlet extends HttpServlet {

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
            
            Map<String, List<Shelf>> params = new HashMap<>();
            JSONObject jsonObject = new JSONObject();
            
           
            List<Shelf> shelves = null;
            
            if(!shelves.isEmpty()){
                params.put("shlves",shelves);
            }
             
            jsonObject.put("params", params);
            out.write(jsonObject.toString());
        }
        
    }

    private Boolean verifyLogin(String userName, String password) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        User user = UserDAO.queryUser(userName);//查询是否存在该用户
        return null != user && password.equals(user.getPassword());
    }
    
   
}
//http://i31973b278.wicp.vip/ClothingWarehouse/TestServlet
//http://i31973b278.wicp.vip/ClothingWarehouse/TestServlet?AccountNumber=yzy&Password=874432636
//http://i31973b278.wicp.vip/ClothingWarehouse/LoginServlet?AccountNumber=yzy&Password=874432636