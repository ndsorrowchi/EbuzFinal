/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CustomerPart;

import BeanModel.ShoppingCart;
import BeanModel.UserBean;
import BeanModel.UserRegisterModel;
import DataAccess.CustomerDA;
import Utils.ConvertUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author chiming
 */
public class Checkout extends HttpServlet {

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
        RequestDispatcher rderr = getServletContext().getRequestDispatcher("/error.jsp");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            try {
                    HttpSession session = request.getSession(false);
                    if(session==null)
                    {
                        throw new NullPointerException("No session tracked. You can only access here via the official website.");
                    }
                    if(session.getAttribute("userbean")==null)
                    {
                        throw new NullPointerException("You should login to finish purchase.");
                    }
                    ShoppingCart sc=(ShoppingCart) session.getAttribute("cart");
                    UserBean ub=(UserBean) session.getAttribute("userbean");
                    if(sc==null)
                    {
                        sc=new ShoppingCart();
                        session.setAttribute("cart", sc);
                    }
                    short res = CustomerDA.Buy(sc,ub.getUid());
                    if(res!=-1)
                    {
                        sc.clear();
                        response.sendRedirect("userorders.jsp");
                    }
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.jsp");
                    rd.forward(request, response);
                } catch (Exception ex) {
                    Logger.getLogger(UserRegister.class.getName()).log(Level.SEVERE, null, ex);
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
        return "Short description";
    }// </editor-fold>

}
