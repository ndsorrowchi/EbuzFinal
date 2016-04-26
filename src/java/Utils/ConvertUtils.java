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
import org.json.JSONException;

/**
 *
 * @author chiming
 */
public class ConvertUtils {
    private ConvertUtils(){}
    //--------------------------
    
    public static final String getExceptionJson (Exception e){
        try{
            String errmsg=e.getCause()==null?e.getMessage():e.getCause().getMessage();
            JSONObject jo = new JSONObject();
            jo.put("error", e.getClass().getSimpleName());
            jo.put("details", errmsg);
            return jo.toString();
        }catch (JSONException je)//always be able to do it, this function never throws exception
        {
            String errmsg=e.getCause()==null?e.getMessage():e.getCause().getMessage();
            String jsonstr=String.format("{\"error\":\"%s\",\"details\":\"%s\"}", e.getClass().getSimpleName(),errmsg);
            return jsonstr;
        }
    }
    
        // User Customized Json
    public static String getErrorJSON(String title, String message)
    {
        String res="{\"error\":\""+title+"\",\"details\":\""+message+"\"}";
        return res;
    } 
    
    public static final String getMD5(String str) throws Exception{
        String hash = DatatypeConverter.printHexBinary(
                MessageDigest.getInstance("MD5").digest(str.getBytes("UTF-8")));
        return hash;
    }
    
    public static final EmplLoginModel validateEmpl(String eid, String pwd){
        if(eid == null || pwd == null)
            return null;
        if(eid.equals("") || pwd.equals(""))
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
    
    public static final UserLoginModel validateUser(String eid, String pwd){
        if(eid == null || pwd == null)
            return null;
        if(eid.equals("") || pwd.equals(""))
            return null;
        try{
            // if the system can do md5, then the db should have stored md5 inside.
            // otherwise, it should have everything plain
            boolean md5able = EnvSingleton.getInstance().isMD5Available();
            String password=md5able?ConvertUtils.getMD5(pwd):pwd;
            return new UserLoginModel(eid, password);
        }
        catch(Exception e){
            return null;
        }
        
    }
    
    public static final UserRegisterModel validateUserReg(String eid, String pwd, String nickname){
        if(eid == null || pwd == null||nickname== null)
            return null;
        if(eid.equals("") || pwd.equals("") || nickname.equals(""))
            return null;
        try{
            // if the system can do md5, then the db should have stored md5 inside.
            // otherwise, it should have everything plain
            boolean md5able = EnvSingleton.getInstance().isMD5Available();
            String password=md5able?ConvertUtils.getMD5(pwd):pwd;
            return new UserRegisterModel(eid, password,nickname);
        }
        catch(Exception e){
            return null;
        }
    }
    
    public static final MessageModel validateMsg(int uid, int eid, int direction, String msg, long time){
        if(eid<0 || uid <0)
            return null;
        if(direction<-1 || direction>1)
            return null;
        return new MessageModel(uid, eid, direction, msg, time);
    }    

    
    public static final String getJsonFromSerializable (Object obj)throws Exception{
        JSONObject jo = new JSONObject(obj);
        return jo.toString();
    }    
    
}
