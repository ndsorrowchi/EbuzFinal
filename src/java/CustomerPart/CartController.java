/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CustomerPart;

import BeanModel.BookModel;
import BeanModel.ShoppingCart;
import BeanModel.ShoppingCartItem;
import DataAccess.ProductDA;
import Utils.ConvertUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author chiming
 */
@WebServlet(name = "CartController", urlPatterns = {"/CartController"})
public class CartController extends HttpServlet {

    private HashMap<String,String> Commands= new HashMap<String, String>(){{
       put("addtocart","AddToCart"); 
       put("increment","Increment");
       put("decrement","Decrement");
       put("clear","ClearCart");
       put("remove","Remove");
    }};

    private String successJson="{\"status\":\"success\"}";
    private String failJson="{\"status\":\"fail\"}";
    
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
            HttpSession session=request.getSession();
            ShoppingCart sc=(ShoppingCart) session.getAttribute("cart");
            if(sc==null)
            {
                sc=new ShoppingCart();
                session.setAttribute("cart", sc);
            }
            boolean good=false;
            String command = request.getParameter("command");
            if(command!=null&&Commands.get(command.toLowerCase())!=null)
            {
                command=Commands.get(command.toLowerCase());
                if(command.equals("AddToCart"))
                {
                    if(request.getParameter("bid")!=null)
                    {
                        String bid=(String)request.getParameter("bid");
                        int id=Integer.parseInt(bid);
                        BookModel bm=ProductDA.getById(id);
                        if(bm!=null)
                        {
                            sc.addItem(bm);
                            out.println(this.successJson);
                            good=true;
                        }
                    }
                }
                else if(command.equals("Increment"))
                {
                    if(request.getParameter("bid")!=null)
                    {
                        String bid=(String)request.getParameter("bid");
                        int id=Integer.parseInt(bid);
                        ShoppingCartItem ci=sc.findById(id);
                        if(ci!=null)
                        {
                            ci.incrementQuantity();
                            out.println(this.successJson);
                            good=true;
                        }
                    }                    
                }
                else if(command.equals("Decrement"))
                {
                    if(request.getParameter("bid")!=null)
                    {
                        String bid=(String)request.getParameter("bid");
                        int id=Integer.parseInt(bid);
                        ShoppingCartItem ci=sc.findById(id);
                        if(ci!=null)
                        {
                            if(ci.getQuantity()>1)
                            {
                                ci.decrementQuantity();

                                out.println(this.successJson);
                                good=true;
                            }
                        }
                    }                     
                }
                else if(command.equals("Remove"))
                {
                    if(request.getParameter("bid")!=null)
                    {
                        String bid=(String)request.getParameter("bid");
                        int id=Integer.parseInt(bid);
                        ShoppingCartItem ci=sc.findById(id);
                        if(ci!=null)
                        {
                            sc.remove(ci.getProduct());
                            out.println(this.successJson);
                            good=true;
                        }
                    } 
                }
                else{
                    sc.clear();
                    out.println(this.successJson);
                    good=true;
                }
            }
            if(!good)
            {out.println(failJson);}
        } catch (Exception ex) {
            Logger.getLogger(CartController.class.getName()).log(Level.SEVERE, null, ex);
            try (PrintWriter out = response.getWriter()) {
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
