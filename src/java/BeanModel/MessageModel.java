/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BeanModel;

import java.io.Serializable;

/**
 *
 * @author chiming
 */
public class MessageModel extends Object implements Serializable{
    private int uid;
    private int eid;
    private int direction;
    private String message;
    private long time;
   
    public MessageModel(){eid=uid=-1;direction=-1;message=null;time=0;}
    public MessageModel(int uid, int eid, int direction, String message, long time)
    {
        this.uid=uid;
        this.eid=eid;
        this.direction=direction;
        this.message=message;
        this.time=time;
    }
    
    public int getUid(){return uid;}
    public void setUid(int fromid){uid=fromid;}
    
    public int getEid(){return eid;}
    public void setEid(int toid){eid=toid;}
    
    public int getDirection(){return direction;}
    public void setDirection(int dir){direction=dir;}
    
    public String getMessage(){return message;}
    public void setMessage(String msg){message=msg;}
        
    public long getTime(){return time;}
    public void setTime(long timestamp){time=timestamp;}     
}
