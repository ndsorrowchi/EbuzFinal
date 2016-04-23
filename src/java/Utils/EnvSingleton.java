/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.security.MessageDigest;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author chiming
 */
public class EnvSingleton {
    	
    private static EnvSingleton instance = null;
    
    private boolean MD5Available = true;
    
    private EnvSingleton()
    {
        try{
            String hash = DatatypeConverter.printHexBinary(
                MessageDigest.getInstance("MD5").digest("BalaBala".getBytes("UTF-8")));
        }catch(Exception e){
            MD5Available = false;
        }
        this.MD5Available=false;
    }

    public static EnvSingleton getInstance()
    {
        if(instance == null)
        {
             instance = new EnvSingleton();
        }
        return instance;
    } 
    
    public boolean isMD5Available(){
        return this.MD5Available;
    }
}
