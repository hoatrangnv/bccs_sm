/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.im.client.bean.AnyPayTransFPTBean;
import com.viettel.im.common.util.ResourceBundleUtils;
import java.util.List;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.hibernate.Query;
import org.hibernate.Hibernate;
import org.hibernate.transform.Transformers;
import oracle.jdbc.*;
import com.viettel.im.client.bean.DbInfo;
import org.hibernate.Session;

/**
 *
 * @author KienPV
 */
public class SaleToAccountAnyPayFPTDAO {

    String DB_LINK_ANYPAY = "IM_ANYPAY";
    CallableStatement Execute;
    String ANYPAY_URL = "jdbc:oracle:thin:@10.228.33.12:1521:vas2";
    String ANYPAY_USERNAME = "ANYPAY_OWNER";
    String ANYPAY_PASSWORD = "ANYPAY2011";
    String SCHEMA_ANYPAY = "ANYPAY_OWNER";
    String ANYPAY_PROC_CREATE_AGENT = "ANYPAY_OWNER.AGENT_PKG.PROC_CREATE_AGENT";
    String ANYPAY_PROC_RECREATE_AGENT = "ANYPAY_OWNER.AGENT_PKG.PROC_RECREATE_AGENT";
    String ANYPAY_PROC_UPDATE_STATUS_AGENT = "ANYPAY_OWNER.AGENT_PKG.PROC_UPDATE_STATUS_AGENT";
    String ANYPAY_PROC_RESET_PASSWORD = "ANYPAY_OWNER.AGENT_PKG.PROC_RESET_PASSWORD";
    String ANYPAY_PROC_UPDATE_AGENT = "ANYPAY_OWNER.AGENT_PKG.PROC_UPDATE_AGENT";
    String ANYPAY_PROC_UPDATE_ICCID_AGENT = "ANYPAY_OWNER.AGENT_PKG.PROC_UPDATE_ICCID_AGENT";
    String ANYPAY_PROC_UPDATE_MPIN = "ANYPAY_OWNER.AGENT_PKG.PROC_UPDATE_MPIN";
    String ANYPAY_PROC_ADD_BONUS_BALANCE_FROM_SM = "ANYPAY_OWNER.AGENT_PKG.PROC_ADD_BONUS_BALANCE_FROM_SM";
    String ANYPAY_PROC_ADD_BONUS_BALANCE = "ANYPAY_OWNER.AGENT_PKG.PROC_ADD_BONUS_BALANCE";
    String ANYPAY_ADD_BONUS_BALANCE_AUTO = "ANYPAY_OWNER.AGENT_PKG.ADD_BONUS_BALANCE_AUTO";
    String ANYPAY_VIEW_TRANS_VIEW = "ANYPAY_OWNER.TRANS_VIEW@IM_ANYPAY";
    
    String ANYPAY_RECOVER_SALE_ANYPAY_FROM_SM = "ANYPAY_OWNER.AGENT_PKG.RECOVER_SALE_ANYPAY_FROM_SM";

    String ANYPAY_PROC_RECOVER_SALE_ANYPAY = "ANYPAY_OWNER.SALE_ANYPAY_PKG.PROC_RECOVER_SALE_ANYPAY";

    String ANYPAY_DESTROY_RELOAD = "ANYPAY_OWNER.AGENT_PKG.DESTROY_RELOAD";
    String ANYPAY_PROC_DESTROY_TRANFER = "ANYPAY_OWNER.DESTROY_TRANFER_PKG.PROC_DESTROY_TRANFER";
    
    String ANYPAY_PROC_SALE_ANYPAY_FROM_SM = "ANYPAY_OWNER.SALE_ANYPAY_PKG.PROC_SALE_ANYPAY_FROM_SM";

    public Connection getConnection() throws SQLException {
        loadParam();
        Connection connection = DriverManager.getConnection(ANYPAY_URL, ANYPAY_USERNAME, ANYPAY_PASSWORD);
        return connection;

    }

    private void loadParam() {
        try {
            DbInfo info = ResourceBundleUtils.getDbInfoEncrypt("connectUrlEv");
            ANYPAY_USERNAME = info.getUserName();
            ANYPAY_PASSWORD = info.getPassWord();
            ANYPAY_URL = info.getConnStr();
            SCHEMA_ANYPAY = ResourceBundleUtils.getResource("schemaEvoucher");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public SaleToAccountAnyPayFPTDAO() {
        loadParam();
    }

//    public SaleToAccountAnyPayFPTDAO(String strIP, String strSID,String strPort, String strUserName, String strPassWord) {
//        this.strIP = strIP;
//        this.strSID = strSID;
//        this.strPort = strPort;
//        this.strUserName = strUserName;
//        this.strPassWord = strPassWord;
//    }
    /**
     * Ban hang TCDT
     */
    public String[] saleToAccountAnyPay(Connection connection, Long stockModelID, Long agentID, Long Amount, Date createDate, String strUser, String strHost) throws Exception {

        String[] strResult = new String[3];
        try {
            loadParam();
            connection.setAutoCommit(false);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//            String strSQL = "{call " + schemaEvoucher + ".AGENT_PKG.sale_anypay_from_sm(?,?,?,?,to_date(?,'dd/mm/yyyy'),?,?,?,?,?)}";
            String strSQL = "{call " + this.ANYPAY_PROC_SALE_ANYPAY_FROM_SM + "(?,?,?,?,to_date(?,'dd/mm/yyyy'),?,?,?,?,?)}";
            Execute = connection.prepareCall(strSQL);
            Execute.setLong(1, 123);
            Execute.setLong(2, stockModelID);
            Execute.setString(3, String.valueOf(agentID));
            Execute.setLong(4, Amount);
            Execute.setString(5, dateFormat.format(createDate));
            Execute.setString(6, strUser);
            Execute.setString(7, strHost);
            Execute.registerOutParameter(8, OracleTypes.CHAR);
            Execute.registerOutParameter(9, OracleTypes.CHAR);
            Execute.registerOutParameter(10, OracleTypes.NUMBER);
            Execute.execute();
            String strErrorCode = Execute.getString(8);
            String strError = Execute.getString(9);
            Long lTransId = Execute.getLong(10);

            if (strErrorCode != null) {
                strResult[0] = strErrorCode;
            } else {
                strResult[0] = "";
            }
            if (strError != null) {
                strResult[1] = strError;
            } else {
                if (lTransId.equals(0L)) {
                    strResult[1] = "Không tạo đuợc giao dich";
                } else {
                    strResult[1] = "";
                }
            }
            if (lTransId != null) {
                strResult[2] = String.valueOf(lTransId);
            } else {
                strResult[2] = "";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            if (Execute != null) {
                Execute.close();
            }
        }

        return strResult;
    }

    public List<AnyPayTransFPTBean> getTransfer(Session session, Long lTransID, String startDate, String endDate, String strTransType, String strTransStatus) {
        try {
            String queryString = "";
            queryString += "SELECT trans_id AS transId ";
            queryString += "    ,trans_type transType ";
            queryString += "    ,create_date AS createDate ";
            queryString += "    ,last_modified AS lastModify ";
            queryString += "    ,process_status AS processStatus ";
            queryString += "    ,trans_amount AS amount";
            queryString += "    ,source_msisdn AS sourceIsdn ";
            queryString += "    ,target_msisdn AS targetIsdn ";
            queryString += "  FROM " + this.ANYPAY_VIEW_TRANS_VIEW + "  ";
            queryString += " WHERE 1=1 ";
            queryString += "    AND rownum <= 1000 ";
            queryString += "    AND trans_type not like 'D%' ";
            queryString += "    AND trans_type not like 'SALE%' ";
            queryString += "    AND (trans_type like 'LOAD%' ";
            queryString += "    or trans_type like 'TRANS%') ";
            //if (lTransID != null) {
            //    queryString += "    AND trans_id =" + lTransID + " ";
            //}
            //if (startDate != null) {
            //    queryString += " AND last_modified>=to_date('" + startDate + "','yyyy-MM-DD')";
            //}
            //if (endDate != null) {
            //    queryString += " AND last_modified<=to_date('" + endDate + "','yyyy-MM-DD') + 1";
            //}
            //if (strTransType != null && !strTransType.equals("")) {
            //    queryString += " AND trans_type='" + strTransType + "'";
            //}
            //if (strTransStatus != null && !strTransStatus.equals("")) {
            //    queryString += " AND process_status='" + strTransStatus + "'";
            //}

            if (lTransID != null) {
                queryString += "    AND trans_id =? ";
            }
            if (startDate != null) {
                queryString += " AND last_modified>=to_date('?','yyyy-MM-DD')";
            }
            if (endDate != null) {
                queryString += " AND last_modified<=to_date('?','yyyy-MM-DD') + 1";
            }
            if (strTransType != null && !strTransType.equals("")) {
                queryString += " AND trans_type='?'";
            }
            if (strTransStatus != null && !strTransStatus.equals("")) {
                queryString += " AND process_status='?'";
            }

            Query queryObject = session.createSQLQuery(queryString).
                    addScalar("transId", Hibernate.LONG).
                    addScalar("transType", Hibernate.STRING).
                    addScalar("createDate", Hibernate.DATE).
                    addScalar("lastModify", Hibernate.DATE).
                    addScalar("processStatus", Hibernate.STRING).
                    addScalar("amount", Hibernate.LONG).
                    addScalar("sourceIsdn", Hibernate.STRING).
                    addScalar("targetIsdn", Hibernate.STRING).
                    setResultTransformer(Transformers.aliasToBean(AnyPayTransFPTBean.class));
            queryObject.setParameter(0, lTransID);
            queryObject.setParameter(1, startDate);
            queryObject.setParameter(2, endDate);
            queryObject.setParameter(3, strTransType);
            queryObject.setParameter(4, strTransStatus);
            return queryObject.list();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    //trang thai 3 - tao giao dich ,5 - ? ,9 - huy giao dich

    // transType = 'SALE'
    public String destroyAnyPayTrans(Connection connection,
            Long lTransID, String strUser, String strHost) throws SQLException {
        String strError = null;
        try {
            connection.setAutoCommit(false);
//            String strSQL = "{call " + schemaEvoucher + ".AGENT_PKG.recover_sale_anypay_from_sm(?,?,?,?)}";
//            String strSQL = "{call " + this.ANYPAY_RECOVER_SALE_ANYPAY_FROM_SM + "(?,?,?,?)}";
            String strSQL = "{call " + this.ANYPAY_PROC_RECOVER_SALE_ANYPAY + "(?,?,?,?)}";
            Execute = connection.prepareCall(strSQL);
            Execute.setLong(1, lTransID);
            Execute.setString(2, strUser);
            Execute.setString(3, strHost);
            Execute.registerOutParameter(4, OracleTypes.VARCHAR);
            Execute.execute();
            strError = Execute.getString(4);

        } catch (Exception ex) {
            strError = "-1";
            ex.fillInStackTrace();
        } finally {
            if (Execute != null) {
                Execute.close();
                Execute = null;
            }
        }
        return strError;
    }

    // transType = 'LOAD'
    public String destroyEload(
            Long lTransID) throws SQLException {
        String strError = null;
        Connection connection = null;
        try {
            loadParam();
            connection = DriverManager.getConnection(ANYPAY_URL, ANYPAY_USERNAME, ANYPAY_PASSWORD);
//            String strSQL = "{call " + schemaEvoucher + ".AGENT_PKG.destroy_reload(?,?)}";
            String strSQL = "{call " + this.ANYPAY_DESTROY_RELOAD + "(?,?)}";
            Execute =
                    connection.prepareCall(strSQL);
            Execute.setString(1, String.valueOf(lTransID));
            Execute.registerOutParameter(2, OracleTypes.VARCHAR);
            Execute.execute();
            strError =
                    Execute.getString(2);
            if (strError != null) {
                connection.rollback();
            } else {
                strError = "";
                connection.commit();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (Execute != null) {
                Execute.close();
            }
        }
        return strError;
    }

    // transType = 'TRANS'
    public String destroyTransfer(
            Long lTransID) throws SQLException {
        String strMessage = null;
        String error = null;
        Connection connection = null;
        try {
            loadParam();
            connection = DriverManager.getConnection(ANYPAY_URL, ANYPAY_USERNAME, ANYPAY_PASSWORD);
//            String strSQL = "{call " + schemaEvoucher + ".AGENT_PKG.destroy_tranfer(?,?)}";
            String strSQL = "{call " + this.ANYPAY_PROC_DESTROY_TRANFER + "(?,?,?)}";
            Execute =
                    connection.prepareCall(strSQL);
            Execute.setString(1, String.valueOf(lTransID));
            Execute.registerOutParameter(2, OracleTypes.VARCHAR);
            Execute.registerOutParameter(3, OracleTypes.VARCHAR);
            Execute.execute();
            strMessage =
                    Execute.getString(2);
            error =
                    Execute.getString(3);
            if (strMessage != null) {
                connection.rollback();
            } else {
                strMessage = "";
                connection.commit();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (Execute != null) {
                Execute.close();
            }
        }
        return strMessage;
    }
}
