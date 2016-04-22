/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import org.json.JSONObject;

/**
 *
 * @author chiming
 */
public class ConvertUtils {
    private ConvertUtils(){}
    //--------------------------
    
    public static final String GetExceptionJson(Exception e)
    {
        String errmsg=e.getCause()==null?e.getMessage():e.getCause().getMessage();
        JSONObject jo = new JSONObject();
        jo.put("error", e.getClass().getSimpleName());
        jo.put("details", errmsg);
        return jo.toString();
    }
    
    
}
