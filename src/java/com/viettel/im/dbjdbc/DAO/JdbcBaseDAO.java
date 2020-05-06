/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.dbjdbc.DAO;
import java.sql.Connection;

/**
 *
 * @author Admin
 */
public class JdbcBaseDAO {
    /* LamNV ADD START */
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    
  
}
