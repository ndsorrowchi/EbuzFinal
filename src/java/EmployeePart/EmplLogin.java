/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EmployeePart;

import BeanModel.EmplBean;
import BeanModel.EmplLoginModel;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import DataAccess.EmployeeService;
import Utils.ConvertUtils;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;

/**
 *
 * @author chiming
 */
public class EmplLogin extends HttpServlet {

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
        RequestDispatcher rderr = getServletContext().getRequestDispatcher("/emperror.jsp");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            try {
                HttpSession session = request.getSession(false);
                if(session==null)
                {
                    throw new NullPointerException("No session tracked. You can only access here via the official website.");
                }
                String id = request.getParameter("id");
                String pwd = request.getParameter("password");
                EmplLoginModel model=ConvertUtils.validateEmpl(id, pwd);
                EmplBean bn = EmployeeService.Login(model);                
                session.setAttribute("emplbean", bn);
                response.sendRedirect("EmplHome");
            } catch (Exception ex) {
                Logger.getLogger(EmplLogin.class.getName()).log(Level.SEVERE, null, ex);
                String errorMessage=ex.getClass().getSimpleName()+ ex.getCause()==null?ex.getMessage():ex.getCause().getMessage();
                request.setAttribute("errmsg", errorMessage);
                rderr.forward(request, response);
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
        return "It Handles Employee Login";
    }// </editor-fold>

}
