/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.database.DAO;

import com.viettel.im.database.DAO.BaseDAO;
import com.viettel.database.DAO.BaseDAOAction;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.viettel.common.ObjectClientChannel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionMessages;
import java.util.ResourceBundle;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.payment.common.util.DateTimeUtils;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;

/**
 *
 * @author TungTV
 */
public class BaseDAO extends BaseDAOAction {



    private static final Logger log = Logger.getLogger(BaseDAO.class);


    protected HttpServletRequest req;


    protected HttpSession session;


    protected static final String ACTIVE_STATUS = "1";


    protected void saveErrors(HttpServletRequest request, ActionMessages errors) {

        // Remove any error messages attribute if none are required
        if ((errors == null) || errors.isEmpty()) {
            request.removeAttribute(Globals.ERROR_KEY);
            return;
        }

        // Save the error messages we need
        request.setAttribute(Globals.ERROR_KEY, errors);

    }

    /*protected void getReqSession(){
        if (req == null) {
            req = getRequest();
        }
        if (session == null) {
            session = req.getSession();
        }
    }*/
    public static void setLong(PreparedStatement stmt, int parameterIndex, Long value) throws SQLException {
        if (value != null) {
            stmt.setLong(parameterIndex, value.longValue());
        } else {
            stmt.setString(parameterIndex, "");
        }
    }
    //QuocDM1 20150112
    public static void close(Statement stmt) {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (Exception ex) {
        }
    }

    public static void close(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (Exception ex) {
        }
    }

    public static void close(PreparedStatement stmt) {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (Exception ex) {
        }
    }

    public static void close(ResultSet rset) {
        try {
            if (rset != null) {
                rset.close();
            }
        } catch (Exception ex) {
        }
    }
}
