/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BeanModel;

import java.io.Serializable;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author chiming
 */

public class MessageListModel extends Object implements Serializable {

    private ArrayList<MessageModel> msglist;
    
    public MessageListModel(){}
    public MessageListModel(ArrayList<MessageModel> msglist)
    {
        this.msglist=msglist;
    }
    
    public ArrayList<MessageModel> getMessagelist(){return this.msglist;}
    public void setMessagelist(ArrayList<MessageModel> msglist){this.msglist=msglist;}
    
}
