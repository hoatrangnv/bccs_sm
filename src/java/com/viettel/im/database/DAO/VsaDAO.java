/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.im.database.BO.VsaUser;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

/**
 *
 * @author kdvt_lamnv5
 */
public class VsaDAO {

    private static String VSA_SCHEMA = "VSA_SDN.";
    private static String INSERT_USER_STMT =
            " INSERT INTO " + VSA_SCHEMA + "users (user_id, user_right, user_name, "
            + " password, status, email, cellphone, "
            + " identity_card, full_name, passwordchanged, profile_id, "
            + " ip, dept_id, dept_level, pos_id, create_date) "
            + " VALUES   (?, 0, ?, "
            + " 'fEqNCco3Yq9h5ZUglD3CZJT4lBs=', 1, 'stk@viettel.com.vn', '123456', "
            + " '123456', ?, 1, 1, '127.0.0.1', 106, '000105000106', 1, sysdate) ";

    /**
     * Tao user ben VSA
     * @param session
     * @param user
     * @return
     */
    public static boolean insertUser(Session session, VsaUser user) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            Long userId = getSequence(session, VSA_SCHEMA + "user_id_seq");
            user.setUserId(userId);

            stmt = session.connection().prepareStatement(INSERT_USER_STMT);
            stmt.setLong(1, userId);
            stmt.setString(2, user.getUserName());
            stmt.setString(3, user.getFullName());

            int result = stmt.executeUpdate();
            if (result > 0) {
                return true;
            }

        } catch (Exception re) {
            throw re;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (rs != null) {
                rs.close();
            }
        }

        return false;
    }

    /**
     * Gan role cho user
     * @param userId
     * @param roleId
     * @return
     * @throws Exception
     */
    public static boolean assignRole(Session session, Long userId, Long roleId) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String sql = "INSERT INTO " + VSA_SCHEMA + "role_user (ROLE_ID,IS_ADMIN,USER_ID,IS_ACTIVE) VALUES(?,0,?,1)";
            stmt = session.connection().prepareStatement(sql);
            stmt.setLong(1, roleId);
            stmt.setLong(2, userId);

            int result = stmt.executeUpdate();
            if (result > 0) {
                return true;
            }

        } catch (Exception re) {
            throw re;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (rs != null) {
                rs.close();
            }
        }

        return false;
    }

    /**
     * Lay sequence
     * @param session
     * @param sequenceName
     * @return
     * @throws Exception
     */
    public static Long getSequence(Session session, String sequenceName) throws Exception {
        String sql = "select " + sequenceName + ".NEXTVAL from dual";
        SQLQuery query = session.createSQLQuery(sql);
        List lst = query.list();
        if (lst.size() == 0) {
            return null;
        }
        return Long.parseLong(lst.get(0).toString());
    }
}
