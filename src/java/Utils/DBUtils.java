/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


/**
 *
 * @author chiming
 */
public class DBUtils {
    private DBUtils(){}
    
    public static final Connection getConnFromPool() throws NamingException, SQLException{
        InitialContext initialContext = new InitialContext();
        Context context = (Context)initialContext.lookup("java:comp/env");
        DataSource ds = (DataSource) context.lookup("ConnPool");
        Connection con = ds.getConnection();
        return con;
    }
}
