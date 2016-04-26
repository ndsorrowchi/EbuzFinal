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
public class FaqModel extends Object implements Serializable{
    private String title;
    private String content;
    
    public FaqModel(){title=null;content=null;}
    public FaqModel(String _title,String _content){title=_title;content=_content;}
    
    public String getTitle(){return title;}
    public void setTitle(String _title){title=_title;}
    
    public String getContent(){return content;}
    public void setContent(String _content){content=_content;}
}
