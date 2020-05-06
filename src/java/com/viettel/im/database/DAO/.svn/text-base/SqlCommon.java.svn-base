package com.viettel.im.database.DAO;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
     /**
     * @author BinhTD
     * @date: 18-Jan-2010
     * @purpose:
     *         
     */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlCommon {
    public static void closeStatement(PreparedStatement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (Exception e) {
        }
    }

    public static void closeResultSet(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (Exception e) {
        }

    }

    public static boolean commitOrRollback(Connection conn)
    {
        if (conn == null)
            return false;
        try{
            conn.commit();
            return true;
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
            try {
                conn.rollback();
            }
            catch (SQLException ex1) {
                ex1.printStackTrace();
                return false;
            }
            return false;
        }
    }

    public static boolean commitOnly(Connection conn)
    {
        if (conn == null)
            return false;
        try {
            conn.commit();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static boolean rollback(Connection conn)
    {
        if (conn == null)
            return false;
        try {
            conn.rollback();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
