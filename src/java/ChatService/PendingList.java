/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChatService;

import BeanModel.UserListModel;
import DataAccess.ServiceDA;
import Utils.ConvertUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author chiming
 */
public class PendingList extends HttpServlet {

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
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            try{
                String eidstr=request.getParameter("eid");
                
                int eid=Integer.parseInt(eidstr);

                if(eid<0)
                {
                    response.setStatus(400);
                    out.println(ConvertUtils.getErrorJSON("Invalid input", "one or more user IDs missing,or invalid direction."));
                }
                else
                {
                    
                    UserListModel mlm=ServiceDA.getPendingList(eid);
                    if(mlm==null)
                    {
                        response.setStatus(400);
                        out.println(ConvertUtils.getErrorJSON("Invalid input", "Invalid IDs."));
                    }
                    else
                    {
                        JSONObject result=new JSONObject(mlm);
                        //Logger.getLogger(PendingList.class.getName()).info(result.toString());
                        out.println(result.toString());
                    }                    
                }
            } catch(Exception ex)
            {
                Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
                response.setStatus(400);
                out.println(ConvertUtils.getExceptionJson(ex));
            }            
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
        processRequest(request, response);
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
        processRequest(request, response);
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
