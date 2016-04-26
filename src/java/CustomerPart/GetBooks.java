/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CustomerPart;

import BeanModel.BookListModel;
import DataAccess.ProductDA;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

/**
 *
 * @author chiming
 */
public class GetBooks extends HttpServlet {

    //private String successJson="{\"status\":\"success\"}";
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
        response.setContentType("text/plain;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String type=request.getParameter("category");
            String keywords=request.getParameter("keywords");
            boolean good=false;
            try{
                if(type!=null&&keywords!=null)
                {
                    if(type.equals("all"))
                    {
                        BookListModel bl=ProductDA.getSearchResult(keywords);
                        JSONObject jo=new JSONObject(bl);
                        String str=jo.toString();
                        good=true;
                        out.println(str);
                    }
                    else
                    {
                        BookListModel bl=ProductDA.getComboSearchResult(keywords,type);
                        JSONObject jo=new JSONObject(bl);
                        String str=jo.toString();
                        good=true;
                        out.println(str);                        
                    }
                }
                else if(type==null&&keywords!=null)
                {
                    BookListModel bl=ProductDA.getSearchResult(keywords);
                    JSONObject jo=new JSONObject(bl);
                    String str=jo.toString();
                    good=true;
                    out.println(str);                    
                }
                else if(type!=null&&keywords==null)
                {
                    if(type.equals("all"))
                    {
                        BookListModel bl=ProductDA.getAll();
                        JSONObject jo=new JSONObject(bl);
                        String str=jo.toString();
                        good=true;
                        out.println(str);
                    }
                    else
                    {
                        BookListModel bl=ProductDA.getFromCategory(type);
                        JSONObject jo=new JSONObject(bl);
                        String str=jo.toString();
                        good=true;
                        out.println(str);                        
                    }                    
                }
                else
                {
                    BookListModel bl=ProductDA.getAll();
                    JSONObject jo=new JSONObject(bl);
                    String str=jo.toString();
                    good=true;
                    out.println(str);
                }
                if(!good)
                {
                    response.setStatus(400);
                    out.println(this.failJson);
                }                
            }catch(Exception ex){
                out.println(this.failJson);
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
