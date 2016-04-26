/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChatService;

import BeanModel.MessageListModel;
import BeanModel.MessageModel;
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
public class Chat extends HttpServlet {

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
            try{
                StringBuilder sb=new StringBuilder();
                BufferedReader br=request.getReader();
                String str;
                while( (str = br.readLine()) != null ){
                    sb.append(str);
                }    
                JSONObject obj = new JSONObject(sb.toString());
                int uid=obj.isNull("uid")?-1:obj.getInt("uid");
                int eid=obj.isNull("eid")?-1:obj.getInt("eid");
                int direction=obj.isNull("direction")?-1:obj.getInt("direction");
                String msgbody=obj.isNull("message")?null:obj.getString("message");
                long ptime=obj.isNull("time")?0:obj.getLong("time");
                
                MessageModel mm=ConvertUtils.validateMsg(uid, eid, direction, msgbody, ptime);
                
                Logger.getLogger(Chat.class.getName()).info(obj.toString());

                if(mm==null)
                {
                    response.setStatus(400);
                    out.println(ConvertUtils.getErrorJSON("Invalid input", "one or more user IDs missing,or invalid direction."));
                }
                else if(mm.getDirection()==-1)
                {
                    String resultjson=ServiceDA.terminate(mm);
                    if(resultjson==null)
                    {
                        response.setStatus(400);
                        out.println(ConvertUtils.getErrorJSON("Invalid input", "Failed to terminate. Invalid parameters."));
                    }
                    else
                    {
                        out.println(resultjson);
                    }                    
                }
                else if(mm.getMessage()==null)
                {
                    
                    MessageListModel mlm=ServiceDA.update(mm);
                    if(mlm==null)
                    {
                        response.setStatus(400);
                        out.println(ConvertUtils.getErrorJSON("Invalid input", "Invalid user IDs."));
                    }
                    else
                    {
                        JSONObject result=new JSONObject();
                        JSONArray jarr=new JSONArray();
                        for(int i=0;i<mlm.getMessagelist().size();i++)
                        {
                            JSONObject temp=new JSONObject();
                            temp.put("uid", mlm.getMessagelist().get(i).getUid());
                            temp.put("eid", mlm.getMessagelist().get(i).getEid());
                            temp.put("direction", mlm.getMessagelist().get(i).getDirection());
                            temp.put("message", mlm.getMessagelist().get(i).getMessage());
                            temp.put("time", mlm.getMessagelist().get(i).getTime());
                            jarr.put(temp);
                        }
                        result.put("msglist", jarr);
                        out.println(result.toString());
                    }                    
                }
                else
                {
                    String resultjson=ServiceDA.post(mm);
                    if(resultjson==null)
                    {
                        response.setStatus(400);
                        out.println(ConvertUtils.getErrorJSON("Invalid input", "Failed to send message. Invalid user IDs."));
                    }
                    else
                    {
                        out.println(resultjson);
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
