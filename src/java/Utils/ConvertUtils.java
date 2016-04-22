/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.security.MessageDigest;
import org.json.JSONObject;
import javax.xml.bind.DatatypeConverter;
import BeanModel.*;

/**
 *
 * @author chiming
 */
public class ConvertUtils {
    private ConvertUtils(){}
    //--------------------------
    
    public static final String getExceptionJson (Exception e)throws Exception{
        String errmsg=e.getCause()==null?e.getMessage():e.getCause().getMessage();
        JSONObject jo = new JSONObject();
        jo.put("error", e.getClass().getSimpleName());
        jo.put("details", errmsg);
        return jo.toString();
    }
    
    public static final String getMD5(String str) throws Exception{
        String hash = DatatypeConverter.printHexBinary(
                MessageDigest.getInstance("MD5").digest(str.getBytes("UTF-8")));
        return hash;
    }
    
    public static final EmplLoginModel validateEmpl(String eid, String pwd){
        if(eid == null || pwd == null)
            return null;
        try{
            int id=Integer.parseInt(eid);
            // if the system can do md5, then the db should have stored md5 inside.
            // otherwise, it should have everything plain
            boolean md5able = EnvSingleton.getInstance().isMD5Available();
            String password=md5able?ConvertUtils.getMD5(pwd):pwd;
            return new EmplLoginModel(id, password);
        }
        catch(Exception e){
            return null;
        }
        
    }
}
