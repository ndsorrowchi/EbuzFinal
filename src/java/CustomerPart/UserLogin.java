/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CustomerPart;

import BeanModel.UserBean;
import BeanModel.UserLoginModel;
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
public class UserLogin extends HttpServlet {

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
        response.setContentType("text/plain;charset=UTF-8");
        //RequestDispatcher rderr = getServletContext().getRequestDispatcher("/error.jsp");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            try {
                HttpSession session = request.getSession(false);
                if(session==null)
                {
                    throw new NullPointerException("No session tracked. You can only access here via the official website.");
                }
                String id = request.getParameter("email");
                String pwd = request.getParameter("password");
                UserLoginModel model=ConvertUtils.validateUser(id, pwd);
                UserBean bn = CustomerDA.Login(model);
                if(bn!=null)
                {
                    session.setAttribute("userbean", bn);
                }
                else{
                    throw new NullPointerException("Invalid Input. Please check and try later.");
                }
                    
                
                out.println("{\"status\":\"success\",\"message\":\"You are successfully signed in.\",\"nickname\":\""+bn.getNickname()+"\",\"userid\":"+bn.getUid()+"}");
                //Logger.getLogger(UserRegister.class.getName()).log(Level.INFO, "{\"status\":\"success\",\"message\":\"You are successfully signed in.\",\"nickname\":\""+bn.getNickname()+"\",\"userid\":"+bn.getUid()+"}");
                //RequestDispatcher rd = getServletContext().getRequestDispatcher("/UserHome");
                //rd.forward(request, response);
            } catch (Exception ex) {
                Logger.getLogger(UserRegister.class.getName()).log(Level.SEVERE, null, ex);
                //String errorMessage=ex.getClass().getSimpleName()+ ex.getCause()==null?ex.getMessage():ex.getCause().getMessage();
                
                //request.setAttribute("errmsg", errorMessage);
                //rderr.forward(request, response);
                out.println("{\"status\":\"fail\",\"message\":\"Sign in failed. Please make sure the input is correct. Otherwise please call service at (412)XXX-XXXX.\"}");
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
