/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.security.util;

import com.viettel.im.database.BO.CashDebitInfo;
import com.viettel.im.database.BO.ChannelHandsetDetail;
import com.viettel.im.database.BO.DebitTrans;
import com.viettel.im.database.BO.DebitTransDetail;
import com.viettel.im.database.BO.DebitUser;
import com.viettel.im.database.BO.ExportCheckSerial;
import com.viettel.im.database.BO.ReportHandsetInfo;
import com.viettel.im.database.BO.RequestBorrowCash;
import com.viettel.vas.util.ConnectionPoolManager;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author MinhNH
 */
public class DbProcessor {

    public Logger logger = Logger.getLogger(DbProcessor.class);
    private String loggerLabel = "DbProcessorAbstract: ";

    public Connection getConnection(String dbName) throws Exception {
//        ConnectionPoolManager.loadConfig("D:\\Restart\\WEB_SM\\etc\\database.xml");
        ConnectionPoolManager.loadConfig("../etc/database.xml");
        return ConnectionPoolManager.getConnection(dbName);
    }

    public DbProcessor() {
        //Them ghi Log4j
        String log4JPath = ResourceBundle.getBundle("config").getString("logfilepath");
        PropertyConfigurator.configure(log4JPath);
    }

    public void logTime(String strLog, long timeSt) {
        long timeEx = System.currentTimeMillis() - timeSt;
        StringBuilder br = new StringBuilder();
        if (timeEx >= 10000) {
            br.setLength(0);
            br.append(loggerLabel).
                    append("Slow db: ").
                    append(strLog).
                    append(": ").
                    append(timeEx).
                    append(" ms");

            logger.warn(br);
        } else {
            br.setLength(0);
            br.append(loggerLabel).
                    append(strLog).
                    append(": ").
                    append(timeEx).
                    append(" ms");

            logger.info(br);
        }
    }

    /*
     * Author: lamnt
     * update cho kho giam tru gia canh
     */
    public int updateStockTransAction(Long reason, String fileUpload, String actionCode) throws Exception {
        Connection connection = null;
        PreparedStatement ps = null;
        StringBuilder br = new StringBuilder();
        String sql = "";
        int res = 0;
        long startTime = System.currentTimeMillis();
        try {
            connection = getConnection("dbsm");
            sql = "UPDATE stock_trans_action "
                    + " SET reason=? , file_upload=? "
                    + " where action_code=? ";
            ps = connection.prepareStatement(sql);
            ps.setLong(1, reason);
            ps.setString(2, fileUpload);
            ps.setString(3, actionCode);
            res = ps.executeUpdate();
            logger.info("End enableExport time " + (System.currentTimeMillis() - startTime));
        } catch (Exception ex) {
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR enableExport: ");
            logger.error(br + ex.toString());
            res = 0;
        } finally {
            closeStatement(ps);
            closeConnection(connection);
            return res;
        }
    }

    public double getMoneySaleTrans(String staffCode, long staffId) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuilder br = new StringBuilder();
        String sql = "";
        double result = 0;
        long startTime = System.currentTimeMillis();
        try {
            connection = getConnection("dbsm");
//            Start queue from 01/06/2018 because it's start time to manage sale limit
            sql = "select sum(a.amount_tax) total_money from sm.sale_trans a "
                    + " where a.sale_trans_date > '17-july-2018' and a.staff_id = ? "
                    + " and (a.clear_debit_status is null or a.clear_debit_status <> '1') and a.status not in (4, 6)"
                    + " and not exists (select * from sm.sale_trans_order where sale_trans_id = a.sale_trans_id and is_check = 3)";
            ps = connection.prepareStatement(sql);
            ps.setLong(1, staffId);
            rs = ps.executeQuery();
            while (rs.next()) {
                result = rs.getDouble("total_money");
            }
            logger.info("End getMoneySaleTrans staffCode " + staffCode
                    + " result " + result + " time "
                    + (System.currentTimeMillis() - startTime));
        } catch (Exception ex) {
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR getMoneySaleTrans: ").
                    append(sql).append("\n")
                    .append(" staffCode ")
                    .append(staffCode)
                    .append(" result ")
                    .append(result);
            logger.error(br + ex.toString());
        } finally {
            closeStatement(ps);
            closeResultSet(rs);
            closeConnection(connection);
            return result;
        }
    }

    public double getMoneyDepositTrans(String staffCode, long staffId) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuilder br = new StringBuilder();
        String sql = "";
        double result = 0;
        long startTime = System.currentTimeMillis();
        try {
            connection = getConnection("dbsm");
//            Start queue from 01/06/2018 because it's start time to manage sale limit
            sql = "select sum(amount) total_money from sm.deposit "
                    + " where create_date > '17-july-2018' and staff_id = ? "
                    + " and (a.clear_debit_status is null or a.clear_debit_status <> '1') "
                    + " and status in (0,1) and type = 1";
            ps = connection.prepareStatement(sql);
            ps.setLong(1, staffId);
            rs = ps.executeQuery();
            while (rs.next()) {
                result = rs.getDouble("total_money");
            }
            logger.info("End getMoneyDepositTrans staffCode " + staffCode
                    + " result " + result + " time "
                    + (System.currentTimeMillis() - startTime));
        } catch (Exception ex) {
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR getMoneyDepositTrans: ").
                    append(sql).append("\n")
                    .append(" staffCode ")
                    .append(staffCode)
                    .append(" result ")
                    .append(result);
            logger.error(br + ex.toString());
        } finally {
            closeStatement(ps);
            closeResultSet(rs);
            closeConnection(connection);
            return result;
        }
    }

    public double getMoneyPaymentTrans(String staffCode, long staffId) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuilder br = new StringBuilder();
        String sql = "";
        double result = 0;
        long startTime = System.currentTimeMillis();
        try {
            connection = getConnection("dbsm");
//            Start queue from 01/06/2018 because it's start time to manage sale limit
            sql = "select sum(payment_amount) total_money from payment.payment_contract a where a.create_date > '17-july-2018' "
                    + " and a.collection_staff_id = ? and "
                    + " (a.clear_debit_status is null or a.clear_debit_status <> '1') "
                    + " and a.status = 1 and a.payment_type = '00'"
                    + " and not exists (select * from payment.payment_bank_slip where payment_id = a.payment_id and status = 3)";
            ps = connection.prepareStatement(sql);
            ps.setLong(1, staffId);
            rs = ps.executeQuery();
            while (rs.next()) {
                result = rs.getDouble("total_money");
            }
            logger.info("End getMoneyPaymentTrans staffCode " + staffCode
                    + " result " + result + " time "
                    + (System.currentTimeMillis() - startTime));
        } catch (Exception ex) {
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR getMoneyPaymentTrans: ").
                    append(sql).append("\n")
                    .append(" staffCode ")
                    .append(staffCode)
                    .append(" result ")
                    .append(result);
            logger.error(br + ex.toString());
        } finally {
            closeStatement(ps);
            closeResultSet(rs);
            closeConnection(connection);
            return result;
        }
    }

    public boolean checkHaveTransOverMoney(String staffCode, long staffId, String limitMoney, Double moneyTransaction) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuilder br = new StringBuilder();
        String sql = "";
        boolean result = false;
        long startTime = System.currentTimeMillis();
        try {
            double limitMoneyNumber = Double.valueOf(limitMoney);
            double currentTotalMoney = 0;
            connection = getConnection("dbsm");
//            Start queue from 01/06/2018 because it's start time to manage sale limit
            sql = "select sum(amount_tax) total_money from sale_trans "
                    + " where sale_trans_date > '01-jun-2018' and staff_id = ? "
                    + " and (clear_debit_status is null or clear_debit_status <> '1') and status <> 4";
            ps = connection.prepareStatement(sql);
            ps.setLong(1, staffId);
            rs = ps.executeQuery();
            while (rs.next()) {
                currentTotalMoney = rs.getDouble("total_money");
                if (limitMoneyNumber < currentTotalMoney + moneyTransaction) {
                    result = true;
                    break;
                }
            }
            logger.info("End checkHaveTransOverMoney staffCode " + staffCode
                    + " limitMoney " + limitMoney + " currentTotalMoney " + currentTotalMoney
                    + " result " + result + " time "
                    + (System.currentTimeMillis() - startTime));
        } catch (Exception ex) {
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR checkHaveTransOverMoney: ").
                    append(sql).append("\n")
                    .append(" staffCode ")
                    .append(staffCode)
                    .append(" limitMoney ")
                    .append(limitMoney)
                    .append(" result ")
                    .append(result);
            logger.error(br + ex.toString());
            logger.error(logException(startTime, ex));
        } finally {
            closeStatement(ps);
            closeResultSet(rs);
            closeConnection(connection);
            return result;
        }
    }

    public long countSerial(String sql, long status, BigInteger fromserial, BigInteger toserial,
            long ownerId, long stockModelType, long stateId) {
        ResultSet rs = null;
        Connection connection = null;
        PreparedStatement ps = null;
        long result = 0;
        StringBuilder br = new StringBuilder();
        try {
            connection = getConnection("dbsm");
            ps = connection.prepareStatement(sql);
            ps.setLong(1, status);
            ps.setString(2, fromserial.toString());
            ps.setString(3, toserial.toString());
            ps.setLong(4, ownerId);
            ps.setLong(5, stockModelType);
            ps.setLong(6, stateId);
            rs = ps.executeQuery();
            while (rs.next()) {
                result = rs.getLong("result");
                break;
            }
        } catch (Exception ex) {
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR countSerial: ").
                    append(sql).append("\n").append(status)
                    .append(" ")
                    .append(fromserial)
                    .append(" ")
                    .append(toserial)
                    .append(" ")
                    .append(" ")
                    .append(ownerId)
                    .append(" ")
                    .append(stockModelType)
                    .append(" ")
                    .append(stateId);
            logger.error(br + ex.toString());
        } finally {
            closeResultSet(rs);
            closeStatement(ps);
            closeConnection(connection);
            return result;
        }
    }

    //LinhNBV start modified on May 21 2018: Add method check BankDocumentNo
    public boolean checkExistDocumentNoInPaymentSystem(String bankDocumentNo) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuilder br = new StringBuilder();
        String sql = "";
        boolean result = false;
        long startTime = System.currentTimeMillis();
        try {
            connection = getConnection("dbPayment");
            sql = "select * from payment_bank_slip where lower(bank_document_no) = lower(?) and status <> 2";
            ps = connection.prepareStatement(sql);
            ps.setString(1, bankDocumentNo);
            rs = ps.executeQuery();
            while (rs.next()) {
                String bankDoc = rs.getString("bank_document_no");
                if (bankDoc != null && !bankDoc.isEmpty()) {
                    result = true;
                    break;
                }
            }
            logger.info("End checkExistDocumentNoInPaymentSystem bankDocumentNo " + bankDocumentNo
                    + " result " + result + " time "
                    + (System.currentTimeMillis() - startTime));
        } catch (Exception ex) {
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR checkExistDocumentNoInPaymentSystem: ").
                    append(sql).append("\n")
                    .append(" bankDocumentNo ")
                    .append(bankDocumentNo)
                    .append(" result ")
                    .append(result);
            logger.error(br + ex.toString());
            logger.error(logException(startTime, ex));
        } finally {
            closeStatement(ps);
            closeResultSet(rs);
            closeConnection(connection);
            return result;
        }
    }

    public boolean checkExistDocumentNoInInventorySystem(String bankDocumentNo) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuilder br = new StringBuilder();
        String sql = "";
        boolean result = false;
        long startTime = System.currentTimeMillis();
        try {
            connection = getConnection("dbsm");
            sql = "select * from Sale_Trans_Order where is_Check <> 2 and ( lower(order_Code) = lower(?) or lower(order_Code2) = lower(?) or lower(order_Code3) = lower(?) ) ";
            ps = connection.prepareStatement(sql);
            ps.setString(1, bankDocumentNo);
            ps.setString(2, bankDocumentNo);
            ps.setString(3, bankDocumentNo);
            rs = ps.executeQuery();
            while (rs.next()) {
                String bankDoc = rs.getString("order_code");
                if (bankDoc != null && !bankDoc.isEmpty()) {
                    result = true;
                    break;
                }
            }
            logger.info("End checkExistDocumentNoInInventorySystem bankDocumentNo " + bankDocumentNo
                    + " result " + result + " time "
                    + (System.currentTimeMillis() - startTime));
        } catch (Exception ex) {
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR checkExistDocumentNoInInventorySystem: ").
                    append(sql).append("\n")
                    .append(" bankDocumentNo ")
                    .append(bankDocumentNo)
                    .append(" result ")
                    .append(result);
            logger.error(br + ex.toString());
            logger.error(logException(startTime, ex));
        } finally {
            closeStatement(ps);
            closeResultSet(rs);
            closeConnection(connection);
            return result;
        }
    }

    //LinhNBV start modified on Jan 13 2018: add method send sms
    public int sendSms(String isdn, String message, String channel) {
        int result = 0;
        PreparedStatement ps = null;
        Connection connection = null;
        long startTime = System.currentTimeMillis();
        String sql = "INSERT INTO mt VALUES(MT_SEQ.NEXTVAL,0,?,?,0,?,SYSDATE)";
        try {
            logger.info("Start insert mt to send sms isdn " + isdn);
            connection = getConnection("dbsm");
            if (!isdn.startsWith("258")) {
                isdn = "258" + isdn;
            }
            System.out.println("isdn  :" + isdn);
            ps = connection.prepareStatement(sql);
            ps.setString(1, isdn);
            ps.setString(2, message);
            ps.setString(3, channel);
            System.out.println("channel  :" + channel);
            result = ps.executeUpdate();
            logger.info("End insert mt isdn " + isdn
                    + " Duration " + (System.currentTimeMillis() - startTime));
        } catch (Exception ex) {
            System.out.println("Exception  :");
            logger.error("Had error insert mt isdn" + isdn);
            logger.error(logException(startTime, ex));
            result = 0;
            throw ex;
        } finally {
            closeStatement(ps);
            closeConnection(connection);
            System.out.println("result  :" + result);
            return result;
        }
    }

    public int updateStateSaleTransOrder(String orderCode, Long staffId) throws Exception {
        Connection connection = null;
        PreparedStatement ps = null;
        StringBuilder br = new StringBuilder();
        String sql = "";
        int res = 0;
        long startTime = System.currentTimeMillis();
        try {
            connection = getConnection("dbsm");
            sql = "update sale_trans_order set is_check = 3 where order_code = ? and staff_id = ? ";
            ps = connection.prepareStatement(sql);
            ps.setString(1, orderCode);
            ps.setLong(2, staffId);
            res = ps.executeUpdate();
            logger.info("End updateStateSaleTransOrder time " + (System.currentTimeMillis() - startTime));
        } catch (Exception ex) {
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR updateStateSaleTransOrder: ");
            logger.error(br + ex.toString());
            res = 0;
        } finally {
            closeStatement(ps);
            closeConnection(connection);
            return res;
        }
    }

    public int[] insertExportCheckSerial(List<ExportCheckSerial> listExportCheck, String userName, Long stockTransId) throws Exception {
        int result[] = null;
        PreparedStatement ps = null;
        Connection connection = null;
        long startTime = System.currentTimeMillis();
        StringBuilder br = new StringBuilder();
        String sqlExportCheck = "INSERT INTO export_check_serial (EXPORT_CHECK_SERIAL_ID,STOCK_TRANS_ID,FROM_SERIAL,TO_SERIAL,"
                + " QUANTITY_REQUIRE,QUANTITY_REAL,RESULT_CHECK,REQUEST_TIME,CREATE_USER,STOCK_MODEL_ID,OWNER_ID,"
                + "STATE_ID,NODE_NAME,CLUSTER_NAME,RESPONSE_TIME,DESCRIPTION,DURATION) "
                + "VALUES(export_check_serial_seq.nextval, ?,?,?,?,null,NULL,sysdate,?,?,?,?,NULL,NULL,NULL,NULL,NULL)";
        try {
            logger.info("Start insertExportCheckSerial " + userName + " StockTransId " + stockTransId);
            br.setLength(0);
            connection = getConnection("dbsm");
            ps = connection.prepareStatement(sqlExportCheck);
            for (ExportCheckSerial transLog : listExportCheck) {
                br.append("[").
                        append(transLog.getFromSerial()).
                        append("|").
                        append(transLog.getToSerial()).
                        append("]|");
                ps.setLong(1, transLog.getStockTransId());
                ps.setString(2, transLog.getFromSerial());
                ps.setString(3, transLog.getToSerial());
                ps.setLong(4, transLog.getQuantityRequire());
                ps.setString(5, transLog.getCreateUser());
                ps.setLong(6, transLog.getStockModelId());
                ps.setLong(7, transLog.getOwnerId());
                ps.setLong(8, transLog.getStateId());
                ps.addBatch();
            }
            result = ps.executeBatch();
            logger.info("End insertExportCheckSerial " + userName + " StockTransId " + stockTransId
                    + " Duration " + (System.currentTimeMillis() - startTime) + " detail Serial " + br.toString());
        } catch (Exception ex) {
            logger.error("Had error insertExportCheckSerial " + userName + " StockTransId " + stockTransId);
            logger.error(logException(startTime, ex));
            throw ex;
        } finally {
            closeStatement(ps);
            closeConnection(connection);
            return result;
        }
    }

    public int[] insertExportSerial(List<ExportCheckSerial> listExportCheck, String userName, Long stockTransId) throws Exception {
        int result[] = null;
        PreparedStatement ps = null;
        Connection connection = null;
        long startTime = System.currentTimeMillis();
        StringBuilder br = new StringBuilder();
        String sqlExportSerial = "INSERT INTO export_serial (export_serial_ID,STOCK_TRANS_ID,FROM_SERIAL,TO_SERIAL,"
                + " STOCK_MODEL_ID, OWNER_ID, old_status, CREATE_USER, request_time, NEW_STATUS, table_name, FROM_OWNER_TYPE_ID) "
                + "VALUES(export_serial_seq.nextval, ?,?,?,?,?,?,?,sysdate, ?, ?, ?)";
        try {
            logger.info("Start insertExportSerial " + userName + " StockTransId " + stockTransId);
            br.setLength(0);
            connection = getConnection("dbsm");
            ps = connection.prepareStatement(sqlExportSerial);
            for (ExportCheckSerial transLog : listExportCheck) {
                br.append("[").
                        append(transLog.getFromSerial()).
                        append("|").
                        append(transLog.getToSerial()).
                        append("]|");
                ps.setLong(1, transLog.getStockTransId());
                ps.setString(2, transLog.getFromSerial());
                ps.setString(3, transLog.getToSerial());
                ps.setLong(4, transLog.getStockModelId());
                ps.setLong(5, transLog.getOwnerId());
                ps.setLong(6, transLog.getOldStatus());
                ps.setString(7, userName);
                ps.setLong(8, transLog.getNewStatus());
                ps.setString(9, transLog.getTableName());
                ps.setLong(10, transLog.getFromOwnerTypeId());
                ps.addBatch();
            }
            result = ps.executeBatch();
            logger.info("End insertExportSerial " + userName + " StockTransId " + stockTransId
                    + " Duration " + (System.currentTimeMillis() - startTime) + " detail Serial " + br.toString());
        } catch (Exception ex) {
            logger.error("Had error insertExportSerial " + userName + " StockTransId " + stockTransId);
            logger.error(logException(startTime, ex));
            throw ex;
        } finally {
            closeStatement(ps);
            closeConnection(connection);
            return result;
        }
    }

    public int[] insertImportSerial(List<ExportCheckSerial> listExportCheck, String userName, Long stockTransId) throws Exception {
        int result[] = null;
        PreparedStatement ps = null;
        Connection connection = null;
        long startTime = System.currentTimeMillis();
        StringBuilder br = new StringBuilder();
        String sqlExportSerial = "INSERT INTO import_serial (import_serial_ID,STOCK_TRANS_ID,FROM_SERIAL,TO_SERIAL,"
                + " STOCK_MODEL_ID, FROM_OWNER_ID, TO_OWNER_ID, to_owner_type_id, old_status, CREATE_USER, request_time, NEW_STATUS, table_name, FROM_OWNER_TYPE_ID) "
                + "VALUES(import_serial_seq.nextval, ?,?,?,?,?,?, ?,?,?,sysdate, ?, ?, ?)";
        try {
            logger.info("Start insertImportSerial " + userName + " StockTransId " + stockTransId);
            br.setLength(0);
            connection = getConnection("dbsm");
            ps = connection.prepareStatement(sqlExportSerial);
            for (ExportCheckSerial transLog : listExportCheck) {
                br.append("[").
                        append(transLog.getFromSerial()).
                        append("|").
                        append(transLog.getToSerial()).
                        append("]|");
                ps.setLong(1, transLog.getStockTransId());
                ps.setString(2, transLog.getFromSerial());
                ps.setString(3, transLog.getToSerial());
                ps.setLong(4, transLog.getStockModelId());
                ps.setLong(5, transLog.getFromOwnerId());
                ps.setLong(6, transLog.getToOwnerId());
                ps.setLong(7, transLog.getToOwnerTypeId());
                ps.setLong(8, transLog.getOldStatus());
                ps.setString(9, userName);
                ps.setLong(10, transLog.getNewStatus());
                ps.setString(11, transLog.getTableName());
                ps.setLong(12, transLog.getFromOwnerTypeId());
                ps.addBatch();
            }
            result = ps.executeBatch();
            logger.info("End insertImportSerial " + userName + " StockTransId " + stockTransId
                    + " Duration " + (System.currentTimeMillis() - startTime) + " detail Serial " + br.toString());
        } catch (Exception ex) {
            logger.error("Had error insertImportSerial " + userName + " StockTransId " + stockTransId);
            logger.error(logException(startTime, ex));
            throw ex;
        } finally {
            closeStatement(ps);
            closeConnection(connection);
            return result;
        }
    }

    public int[] insertExportSerialChannelId(List<ExportCheckSerial> listExportCheck, String userName, Long stockTransId) throws Exception {
        int result[] = null;
        PreparedStatement ps = null;
        Connection connection = null;
        long startTime = System.currentTimeMillis();
        StringBuilder br = new StringBuilder();
        String sqlExportSerial = "INSERT INTO export_serial (export_serial_ID,STOCK_TRANS_ID,FROM_SERIAL,TO_SERIAL,"
                + " STOCK_MODEL_ID, OWNER_ID, old_status, CREATE_USER, request_time, channel_type_id, NEW_STATUS, table_name, FROM_OWNER_TYPE_ID) "
                + "VALUES(export_serial_seq.nextval, ?,?,?,?,?,?,?,sysdate, ?, ?, ?, ?)";
        try {
            logger.info("Start insertExportSerialChannelId " + userName + " StockTransId " + stockTransId);
            br.setLength(0);
            connection = getConnection("dbsm");
            ps = connection.prepareStatement(sqlExportSerial);
            for (ExportCheckSerial transLog : listExportCheck) {
                br.append("[").
                        append(transLog.getFromSerial()).
                        append("|").
                        append(transLog.getToSerial()).
                        append("]|");
                ps.setLong(1, transLog.getStockTransId());
                ps.setString(2, transLog.getFromSerial());
                ps.setString(3, transLog.getToSerial());
                ps.setLong(4, transLog.getStockModelId());
                ps.setLong(5, transLog.getOwnerId());
                ps.setLong(6, transLog.getOldStatus());
                ps.setString(7, userName);
                ps.setLong(8, transLog.getChannelTypeId());
                ps.setLong(9, transLog.getNewStatus());
                ps.setString(10, transLog.getTableName());
                ps.setLong(11, transLog.getFromOwnerTypeId());
                ps.addBatch();
            }
            result = ps.executeBatch();
            logger.info("End insertExportSerialChannelId " + userName + " StockTransId " + stockTransId
                    + " Duration " + (System.currentTimeMillis() - startTime) + " detail Serial " + br.toString());
        } catch (Exception ex) {
            logger.error("Had error insertExportSerialChannelId " + userName + " StockTransId " + stockTransId);
            logger.error(logException(startTime, ex));
            throw ex;
        } finally {
            closeStatement(ps);
            closeConnection(connection);
            return result;
        }
    }

    public int[] insertImportSerialChannelId(List<ExportCheckSerial> listExportCheck, String userName, Long stockTransId) throws Exception {
        int result[] = null;
        PreparedStatement ps = null;
        Connection connection = null;
        long startTime = System.currentTimeMillis();
        StringBuilder br = new StringBuilder();
        String sqlExportSerial = "INSERT INTO import_serial (import_serial_ID,STOCK_TRANS_ID,FROM_SERIAL,TO_SERIAL,"
                + " STOCK_MODEL_ID, FROM_OWNER_ID, TO_OWNER_ID, to_owner_type_id, old_status, CREATE_USER, request_time, channel_type_id, NEW_STATUS, table_name, FROM_OWNER_TYPE_ID) "
                + "VALUES(import_serial_seq.nextval, ?,?,?,?,?,?,?,?,?,sysdate, ?, ?, ?, ?)";
        try {
            logger.info("Start insertImportSerialChannelId " + userName + " StockTransId " + stockTransId);
            br.setLength(0);
            connection = getConnection("dbsm");
            ps = connection.prepareStatement(sqlExportSerial);
            for (ExportCheckSerial transLog : listExportCheck) {
                br.append("[").
                        append(transLog.getFromSerial()).
                        append("|").
                        append(transLog.getToSerial()).
                        append("]|");
                ps.setLong(1, transLog.getStockTransId());
                ps.setString(2, transLog.getFromSerial());
                ps.setString(3, transLog.getToSerial());
                ps.setLong(4, transLog.getStockModelId());
                ps.setLong(5, transLog.getFromOwnerId());
                ps.setLong(6, transLog.getToOwnerId());
                ps.setLong(7, transLog.getToOwnerTypeId());
                ps.setLong(8, transLog.getOldStatus());
                ps.setString(9, userName);
                ps.setLong(10, transLog.getChannelTypeId());
                ps.setLong(11, transLog.getNewStatus());
                ps.setString(12, transLog.getTableName());
                ps.setLong(13, transLog.getFromOwnerTypeId());
                ps.addBatch();
            }
            result = ps.executeBatch();
            logger.info("End insertImportSerialChannelId " + userName + " StockTransId " + stockTransId
                    + " Duration " + (System.currentTimeMillis() - startTime) + " detail Serial " + br.toString());
        } catch (Exception ex) {
            logger.error("Had error insertImportSerialChannelId " + userName + " StockTransId " + stockTransId);
            logger.error(logException(startTime, ex));
            throw ex;
        } finally {
            closeStatement(ps);
            closeConnection(connection);
            return result;
        }
    }

    //LinhNBV start modified on Jan 14 2018: Get phone of finance staff
    public List<String> getIsdnFinanceStaff(String shopCode) {
        List<String> lstIsdn = new ArrayList<String>();
        PreparedStatement ps = null;
        Connection connection = null;
        ResultSet rs = null;
        long startTime = System.currentTimeMillis();
        String sql = "select u.cellphone, u.user_name from vsa_v3.users u where \n"
                + "u.status = 1 \n"
                + "and u.user_id in \n"
                + "(\n"
                + "    select user_id from vsa_v3.role_user where is_active = 1 and role_id in (select role_id from vsa_v3.roles where status = 1 and role_code in ('BR_FINANCE_APPROVE'))\n"
                + ")\n"
                + "and u.user_name in \n"
                + "(\n"
                + "    select lower(staff_code) from staff where status = 1 and shop_id in (select shop_id from shop where status = 1 and lower(shop_code) = lower(?))\n"
                + ")";
        try {
            logger.info("Start getIsdnOf Finance branch shopCode " + shopCode);
            connection = getConnection("dbsm");
            ps = connection.prepareStatement(sql);
            ps.setString(1, shopCode);
            rs = ps.executeQuery();
            while (rs.next()) {
                String cellPhone = rs.getString("cellphone");
                lstIsdn.add(cellPhone);
            }

        } catch (Exception ex) {
            logger.error("Had error getIsdnFinance shopCode " + shopCode);
            logger.error(logException(startTime, ex));
            throw ex;
        } finally {
            closeStatement(ps);
            closeConnection(connection);
            return lstIsdn;
        }
    }

    //LinhNBV start modified on Jan 14 2018: Get phone of finance staff
    public String getIsdnStaff(String staffCode) {
        String isdn = null;
        PreparedStatement ps = null;
        Connection connection = null;
        ResultSet rs = null;
        long startTime = System.currentTimeMillis();
        String sql = "select u.cellphone from vsa_v3.users u where \n"
                + "u.status = 1 and lower(u.user_name) = lower(?)";
        try {
            logger.info("Start getIsdnOf staff ----- staff Code " + staffCode);
            connection = getConnection("dbsm");
            ps = connection.prepareStatement(sql);
            ps.setString(1, staffCode);
            rs = ps.executeQuery();
            while (rs.next()) {
                isdn = rs.getString("cellphone");

            }

        } catch (Exception ex) {
            logger.error("Had error get isdn staff ---- staff code " + staffCode);
            logger.error(logException(startTime, ex));
            throw ex;
        } finally {
            closeStatement(ps);
            closeConnection(connection);
            return isdn;
        }
    }

    public boolean isStaffFinance(String userName) {
        boolean result = false;
        PreparedStatement ps = null;
        Connection connection = null;
        ResultSet rs = null;
        long startTime = System.currentTimeMillis();
        String sql = "select * from vsa_v3.role_user \n"
                + "where user_id = (select user_id from vsa_v3.users where lower(user_name) = lower(?) and status = 1)\n"
                + "and is_active = 1 \n"
                + "and role_id in (select role_id from vsa_v3.roles where status = 1 and role_code in ('BR_FINANCE_APPROVE'))";
        try {
            logger.info("Start staff finance or not " + userName);
            connection = getConnection("dbsm");
            ps = connection.prepareStatement(sql);
            ps.setString(1, userName);
            rs = ps.executeQuery();
            while (rs.next()) {
                Long roleId = rs.getLong("role_id");
                if (roleId != 0L) {
                    result = true;
                    break;
                }
            }
        } catch (Exception ex) {
            logger.error("Had error check finance staff " + userName);
            logger.error(logException(startTime, ex));
            result = false;
            throw ex;
        } finally {
            closeStatement(ps);
            closeConnection(connection);
            return result;
        }
    }

    public ExportCheckSerial checkSerial(String userName, Long stockTransId) throws Exception {
        ExportCheckSerial result = null;
        PreparedStatement ps = null;
        Connection connection = null;
        ResultSet rs = null;
        long startTime = System.currentTimeMillis();
        String sqlCheck = "select from_serial, to_serial, result_check, quantity_real from export_check_serial where stock_trans_id = ? order by response_time asc";
        try {
            logger.info("Start checkSerial " + userName + " StockTransId " + stockTransId);
            connection = getConnection("dbsm");
            ps = connection.prepareStatement(sqlCheck);
            ps.setLong(1, stockTransId);
            rs = ps.executeQuery();
            int countSuccess = 0;
            int countTotal = 0;
            while (rs.next()) {
                countTotal++;
                String resultCheck = rs.getString("result_check");
                if (resultCheck != null && "1".equals(resultCheck)) {
                    result = new ExportCheckSerial();
                    result.setFromSerial(rs.getString("from_serial"));
                    result.setToSerial(rs.getString("to_serial"));
                    result.setQuantityReal(rs.getLong("quantity_real"));
                    result.setResultCode("1"); //it means has atleast one range serial invalid
                    return result;
                } else if (resultCheck != null && "0".equals(resultCheck)) {
                    countSuccess++;
                }
            }
            logger.info("End checkSerial " + userName + " StockTransId " + stockTransId
                    + " Duration " + (System.currentTimeMillis() - startTime) + " result "
                    + (result == null ? "null" : result.getResultCode()));
            if (countSuccess == countTotal) {
                result = new ExportCheckSerial();
                result.setResultCode("0"); // all batch serial is correct
                return result;
            } else {
                return null;
            }
        } catch (Exception ex) {
            logger.error("Had error insertExportCheckSerial " + userName + " StockTransId " + stockTransId);
            logger.error(logException(startTime, ex));
            throw ex;
        } finally {
            closeStatement(ps);
            closeConnection(connection);
            return result;
        }
    }

    public boolean checkTransExporting(String userName, Long stockTransId) throws Exception {
        boolean result = false;
        PreparedStatement ps = null;
        Connection connection = null;
        ResultSet rs = null;
        long startTime = System.currentTimeMillis();
        String sqlCheck = "select stock_trans_id from export_serial where stock_trans_id = ? and result_export is null";
        try {
            logger.info("Start checkTransRuning " + userName + " StockTransId " + stockTransId);
            connection = getConnection("dbsm");
            ps = connection.prepareStatement(sqlCheck);
            ps.setLong(1, stockTransId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Long resultCheck = rs.getLong("stock_trans_id");
                if (resultCheck > 0) {
                    result = true;
                }
            }
            logger.info("End checkTransRuning " + userName + " StockTransId " + stockTransId
                    + " Duration " + (System.currentTimeMillis() - startTime) + " result "
                    + result);
            return result;
        } catch (Exception ex) {
            logger.error("Had error checkTransRuning " + userName + " StockTransId " + stockTransId);
            logger.error(logException(startTime, ex));
            throw ex;
        } finally {
            closeStatement(ps);
            closeConnection(connection);
            return result;
        }
    }

    public boolean checkSerialInStock(BigInteger serial, String tableName, long status, long stateId, long ownerId, long stockModelId) throws Exception {
        boolean result = false;
        PreparedStatement ps = null;
        Connection connection = null;
        ResultSet rs = null;
        long startTime = System.currentTimeMillis();
        String sqlCheck = "select status from " + tableName + " where to_number(serial) = ? and owner_id = ? and status = ? "
                + " and state_id = ? and stock_model_id = ?";
        try {
            logger.info("Start checkSerialInStock " + serial + " tableName " + tableName + " ownerId" + ownerId
                    + " stock_model_id " + stockModelId);
            connection = getConnection("dbsm");
            ps = connection.prepareStatement(sqlCheck);
            ps.setString(1, serial.toString());
            ps.setLong(2, ownerId);
            ps.setLong(3, status);
            ps.setLong(4, stateId);
            ps.setLong(5, stockModelId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Long resultCheck = rs.getLong("status");
                if (resultCheck >= 0) {
                    result = true;
                    break;
                }
            }
            logger.info("End checkSerialInStock serial " + serial + " tableName " + tableName + " ownerId"
                    + ownerId + " stock_model_id " + stockModelId + " result " + result);
            return result;
        } catch (Throwable ex) {
            logger.error("Had error checkSerialInStock serial" + serial + " tableName " + tableName + " ownerId" + ownerId);
            logger.error(logException(startTime, ex));
            throw ex;
        } finally {
            closeStatement(ps);
            closeConnection(connection);
            return result;
        }
    }

    public boolean checkSerialExported(String serial, Long ownerId) throws Exception {
        boolean result = false;
        PreparedStatement ps = null;
        Connection connection = null;
        ResultSet rs = null;
        long startTime = System.currentTimeMillis();
        String sqlCheck = "select export_serial_id from export_serial where OWNER_ID = ? and (from_serial = ? or to_serial = ?) "
                + " and new_status = 3 and (result_export is null or result_export not in (0, 3, 6, 13))";
        try {
            logger.info("Start checkSerialExported " + serial);
            connection = getConnection("dbsm");
            ps = connection.prepareStatement(sqlCheck);
            ps.setLong(1, ownerId);
            ps.setString(2, serial);
            ps.setString(3, serial);
            rs = ps.executeQuery();
            while (rs.next()) {
                Long resultCheck = rs.getLong("export_serial_id");
                if (resultCheck > 0) {
                    result = true;
                    break;
                }
            }
            logger.info("End checkSerialExported serial " + serial + " result " + result);
            return result;
        } catch (Exception ex) {
            logger.error("Had error checkSerialExported serial" + serial);
            logger.error(logException(startTime, ex));
            throw ex;
        } finally {
            closeStatement(ps);
            closeConnection(connection);
            return result;
        }
    }

    public boolean checkSerialInStockForOnlyOneSerial(ArrayList<BigInteger> listSerial,
            String tableName, long status, long stateId, long ownerId, long stockModelId) throws Exception {
        boolean result = false;
        PreparedStatement psTruncate = null;
        PreparedStatement psInsert = null;
        PreparedStatement psSelect = null;
        Connection connection = null;
        ResultSet rs = null;
        long startTime = System.currentTimeMillis();
//        Step 1, truncate table check_batch_serial
        String sqlTruncate = "delete check_batch_serial where status = ? and state_id = ? and owner_id = ? and stock_model_id = ? ";
        String sqlInsert = "insert into check_batch_serial (serial, status, state_id, owner_id, stock_model_id) "
                + " values (to_number(?), ?, ?, ?, ?) ";
        String sqlCheck = "select count(a.serial) soluong from check_batch_serial a, " + tableName + " b "
                + "where a.serial = b.serial "
                + " and b.owner_id = ? and b.status = ? and b.state_id = ? and b.stock_model_id = ? "
                + " and a.owner_id = ? and a.status = ? and a.state_id = ? and a.stock_model_id = ? ";
        try {
            logger.info("Start checkSerialInStockForOnlyOneSerial ownerId " + ownerId);
            connection = getConnection("dbsm");
            psTruncate = connection.prepareStatement(sqlTruncate);
            psTruncate.setLong(1, status);
            psTruncate.setLong(2, stateId);
            psTruncate.setLong(3, ownerId);
            psTruncate.setLong(4, stockModelId);
            psTruncate.executeUpdate();
            psInsert = connection.prepareStatement(sqlInsert);
            for (BigInteger serial : listSerial) {
                psInsert.setString(1, serial.toString());
                psInsert.setLong(2, status);
                psInsert.setLong(3, stateId);
                psInsert.setLong(4, ownerId);
                psInsert.setLong(5, stockModelId);
                psInsert.addBatch();
            }
            psInsert.executeBatch();
            psSelect = connection.prepareStatement(sqlCheck);
            psSelect.setLong(1, ownerId);
            psSelect.setLong(2, status);
            psSelect.setLong(3, stateId);
            psSelect.setLong(4, stockModelId);
            psSelect.setLong(5, ownerId);
            psSelect.setLong(6, status);
            psSelect.setLong(7, stateId);
            psSelect.setLong(8, stockModelId);
            rs = psSelect.executeQuery();
            while (rs.next()) {
                int resultCheck = rs.getInt("soluong");
                if (resultCheck == listSerial.size()) {
                    result = true;
                    break;
                }
            }
            logger.info("End checkSerialInStockForOnlyOneSerial ownerId " + ownerId + " result " + result);
            try {
                psTruncate.executeUpdate();
            } catch (Exception e) {
                logger.info("ReExecute failt to delete check_batch_serial checkSerialInStockForOnlyOneSerial ownerId "
                        + ownerId + " result " + result + " exception " + e.toString());
            }
            return result;
        } catch (Exception ex) {
            logger.error("Had error checkSerialExported ownerId " + ownerId);
            logger.error(logException(startTime, ex));
            throw ex;
        } finally {
            closeStatement(psTruncate);
            closeStatement(psInsert);
            closeStatement(psSelect);
            closeConnection(connection);
            return result;
        }
    }

    public boolean checkTransImporting(String userName, Long stockTransId) throws Exception {
        boolean result = false;
        PreparedStatement ps = null;
        Connection connection = null;
        ResultSet rs = null;
        long startTime = System.currentTimeMillis();
        String sqlCheck = "select stock_trans_id from import_serial where stock_trans_id = ? and RESULT_IMPORT is null";
        try {
            logger.info("Start checkTransImporting " + userName + " StockTransId " + stockTransId);
            connection = getConnection("dbsm");
            ps = connection.prepareStatement(sqlCheck);
            ps.setLong(1, stockTransId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Long resultCheck = rs.getLong("stock_trans_id");
                if (resultCheck > 0) {
                    result = true;
                }
            }
            logger.info("End checkTransImporting " + userName + " StockTransId " + stockTransId
                    + " Duration " + (System.currentTimeMillis() - startTime) + " result "
                    + result);
            return result;
        } catch (Exception ex) {
            logger.error("Had error checkTransImporting " + userName + " StockTransId " + stockTransId);
            logger.error(logException(startTime, ex));
            throw ex;
        } finally {
            closeStatement(ps);
            closeConnection(connection);
            return result;
        }
    }

    public long enableExport(String userName, Long stockTransId) {
        Connection connection = null;
        PreparedStatement ps = null;
        StringBuilder br = new StringBuilder();
        String sql = "";
        int res = 0;
        long startTime = System.currentTimeMillis();
        try {
            connection = getConnection("dbsm");
            sql = "update export_serial set enable_export = 1 "
                    + " where stock_trans_id = ?";
            ps = connection.prepareStatement(sql);
            logger.info("Start enableExport username " + userName + " stockTransId " + stockTransId);
            ps.setLong(1, stockTransId);
            res = ps.executeUpdate();
            logger.info("End enableExport time " + (System.currentTimeMillis() - startTime));
        } catch (Exception ex) {
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR enableExport: ");
            logger.error(br + ex.toString());
            res = 0;
        } finally {
            closeStatement(ps);
            closeConnection(connection);
            return res;
        }
    }

    public long rollbackExport(String userName, Long stockTransId) {
        Connection connection = null;
        PreparedStatement ps = null;
        StringBuilder br = new StringBuilder();
        String sql = "";
        int res = 0;
        long startTime = System.currentTimeMillis();
        try {
            connection = getConnection("dbsm");
            sql = "insert into export_serial(export_serial_id, stock_trans_id, result_export, description, request_time, create_user) "
                    + "values (export_serial_seq.nextval, ?, '7', 'Cancel export note, so have to rollback export', sysdate, ?)";
            ps = connection.prepareStatement(sql);
            logger.info("Start rollbackExport username " + userName + " stockTransId " + stockTransId);
            ps.setLong(1, stockTransId);
            ps.setString(2, userName);
            res = ps.executeUpdate();
            logger.info("End rollbackExport time " + (System.currentTimeMillis() - startTime));
        } catch (Exception ex) {
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR rollbackExport: ");
            logger.error(br + ex.toString());
            res = 0;
        } finally {
            closeStatement(ps);
            closeConnection(connection);
            return res;
        }
    }

    public long enableImport(String userName, Long stockTransId) {
        Connection connection = null;
        PreparedStatement ps = null;
        StringBuilder br = new StringBuilder();
        String sql = "";
        int res = 0;
        long startTime = System.currentTimeMillis();
        try {
            connection = getConnection("dbsm");
            sql = "update import_serial set ENABLE_IMPORT = 1 "
                    + " where stock_trans_id = ?";
            ps = connection.prepareStatement(sql);
            logger.info("Start enableImport username " + userName + " stockTransId " + stockTransId);
            ps.setLong(1, stockTransId);
            res = ps.executeUpdate();
            logger.info("End enableImport time " + (System.currentTimeMillis() - startTime));
        } catch (Exception ex) {
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR enableImport: ");
            logger.error(br + ex.toString());
            res = 0;
        } finally {
            closeStatement(ps);
            closeConnection(connection);
            return res;
        }
    }

    public boolean checkOverLimitBeforeSale(String staffCode) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuilder br = new StringBuilder();
        String sql = "";
        boolean result = false;
        long startTime = System.currentTimeMillis();
        long transId = 0;
        try {
            connection = getConnection("dbsm");
//            Start queue from 01/06/2018 because it's start time to manage sale limit
            sql = "select * from staff where lower(staff_code) = lower(?) and status = 1 \n"
                    + "and ((limit_money is not null and limit_day is not null and limit_approve_user is not null)\n"
                    + "and (limit_over_status <> 1 or limit_over_status is null))";
            ps = connection.prepareStatement(sql);
            ps.setString(1, staffCode);
            rs = ps.executeQuery();
            while (rs.next()) {
                transId = rs.getLong("staff_id");
                if (transId > 0) {
                    result = true;
                    break;
                }
            }
            logger.info("End checkOverLimitBeforeSale staffCode " + staffCode
                    + " result " + result + " transId " + transId + " time "
                    + (System.currentTimeMillis() - startTime));
        } catch (Exception ex) {
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR checkOverLimitBeforeSale: ").
                    append(sql).append("\n")
                    .append(" staffCode ")
                    .append(staffCode)
                    .append(" result ")
                    .append(result);
            logger.error(br + ex.toString());
        } finally {
            closeStatement(ps);
            closeResultSet(rs);
            closeConnection(connection);
            return result;
        }
    }

    public double getLimitMoneyByStaffCode(String staffCode) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuilder br = new StringBuilder();
        String sql = "";
        boolean result = false;
        long startTime = System.currentTimeMillis();
        double sum = 0;
        try {
            connection = getConnection("dbsm");
//            Start queue from 01/06/2018 because it's start time to manage sale limit
            sql = "select limit_money from staff where lower(staff_code) = lower(?) and status = 1";
            ps = connection.prepareStatement(sql);
            ps.setString(1, staffCode);
            rs = ps.executeQuery();
            while (rs.next()) {
                sum = rs.getDouble("limit_money");
            }
            logger.info("End getLimitMoneyByStaffCode with " + staffCode + " result " + result + " limit_money: " + sum + " time "
                    + (System.currentTimeMillis() - startTime));
        } catch (Exception ex) {
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR getLimitMoneyByStaffCode: ").
                    append(sql).append("\n")
                    .append(" staffCode ")
                    .append(staffCode)
                    .append("limit_money")
                    .append(sum)
                    .append(" result ")
                    .append(result);
            logger.error(br + ex.toString());
        } finally {
            closeStatement(ps);
            closeResultSet(rs);
            closeConnection(connection);
            return sum;
        }
    }

    public double sumSaleTransDebitByStaffCode(String staffCode) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuilder br = new StringBuilder();
        String sql = "";
        boolean result = false;
        long startTime = System.currentTimeMillis();
        double sum = 0;
        try {
            connection = getConnection("dbsm");
//            Start queue from 01/06/2018 because it's start time to manage sale limit
            sql = "select sum(amount_tax) as total from sm.sale_trans a where a.sale_trans_date > '17-july-2018' and a.staff_id = (select staff_id from staff where lower(staff_code) = lower(?)) "
                    + " and (a.clear_debit_status is null or a.clear_debit_status <> '1') "
                    + " and a.status <> 4 "
                    + " and not exists (select * from sm.sale_trans_order where sale_trans_id = a.sale_trans_id and is_check = 3)"
                    + " and not exists (select * from sm.agent_trans_order_his where sale_trans_id = a.sale_trans_id and is_check = 3)";
            ps = connection.prepareStatement(sql);
            ps.setString(1, staffCode);
            rs = ps.executeQuery();
            while (rs.next()) {
                sum = rs.getDouble("total");
            }
            logger.info("End sumSaleTransDebitByStaffCode with " + staffCode + " result " + result + " total debit: " + sum + " time "
                    + (System.currentTimeMillis() - startTime));
        } catch (Exception ex) {
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR sumSaleTransDebitByStaffCode: ").
                    append(sql).append("\n")
                    .append(" staffCode ")
                    .append(staffCode)
                    .append(" total debit")
                    .append(sum)
                    .append(" result ")
                    .append(result);
            logger.error(br + ex.toString());
        } finally {
            closeStatement(ps);
            closeResultSet(rs);
            closeConnection(connection);
            return sum;
        }
    }

    public double sumSaleTransPostPaidOfAgent(String staffCode) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuilder br = new StringBuilder();
        String sql = "";
        boolean result = false;
        long startTime = System.currentTimeMillis();
        double sum = 0;
        try {
            connection = getConnection("dbsm");
            sql = "select sum(s.amount_tax) as total from sale_trans s \n"
                    + "where (s.clear_debit_status is null or s.clear_debit_status <> 1) and s.status not in (4)\n"
                    + "and s.sale_trans_date > '01-jul-2019' and amount_tax > 0 and s.staff_id = (select staff_id from sm.staff where upper(staff_code) = upper(?))\n"
                    + "and (exists (select 1 from sm.sale_trans_order where sale_trans_id = s.sale_trans_id and is_check = 3 and pay_method = 4 \n"
                    + ") and (reason_id <> 201948 or reason_id is null) \n"
                    + "or exists (select 1 from sm.agent_trans_order_his where sale_trans_id = s.sale_trans_id and is_check = 3 and pay_method = 4 \n"
                    + ") and (reason_id <> 201948 or reason_id is null))\n"
                    + "order by s.sale_trans_date desc";
            ps = connection.prepareStatement(sql);
            ps.setString(1, staffCode);
            rs = ps.executeQuery();
            while (rs.next()) {
                sum = rs.getDouble("total");
            }
            logger.info("End sumSaleTransPostPaidOfAgent with " + staffCode + " result " + result + " total debit: " + sum + " time "
                    + (System.currentTimeMillis() - startTime));
        } catch (Exception ex) {
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR sumSaleTransPostPaidOfAgent: ").
                    append(sql).append("\n")
                    .append(" staffCode ")
                    .append(staffCode)
                    .append(" total debit")
                    .append(sum)
                    .append(" result ")
                    .append(result);
            logger.error(br + ex.toString());
        } finally {
            closeStatement(ps);
            closeResultSet(rs);
            closeConnection(connection);
            return sum;
        }
    }

    public double sumDepositDebitByStaffCode(String staffCode) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuilder br = new StringBuilder();
        String sql = "";
        boolean result = false;
        long startTime = System.currentTimeMillis();
        double sum = 0;
        try {
            connection = getConnection("dbsm");
//            Start queue from 01/06/2018 because it's start time to manage sale limit
            sql = "select sum(amount) as total from sm.deposit where create_date > '17-july-2018' and staff_id = (select staff_id from staff where lower(staff_code) = lower(?)) and\n"
                    + "(clear_debit_status is null or clear_debit_status <> '1')\n"
                    + "and status in (0,1) and type = 1";
            ps = connection.prepareStatement(sql);
            ps.setString(1, staffCode);
            rs = ps.executeQuery();
            while (rs.next()) {
                sum = rs.getDouble("total");
            }
            logger.info("End sumDepositDebitByStaffCode with " + staffCode + " result " + result + " total debit: " + sum + " time "
                    + (System.currentTimeMillis() - startTime));
        } catch (Exception ex) {
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR sumDepositDebitByStaffCode: ").
                    append(sql).append("\n")
                    .append(" staffCode ")
                    .append(staffCode)
                    .append(" total debit")
                    .append(sum)
                    .append(" result ")
                    .append(result);
            logger.error(br + ex.toString());
        } finally {
            closeStatement(ps);
            closeResultSet(rs);
            closeConnection(connection);
            return sum;
        }
    }

    public double sumPaymentDebitByStaffCode(String staffCode) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuilder br = new StringBuilder();
        String sql = "";
        boolean result = false;
        long startTime = System.currentTimeMillis();
        double sum = 0;
        try {
            connection = getConnection("dbPayment");
//            Start queue from 01/06/2018 because it's start time to manage sale limit
            sql = "select sum(payment_amount) as total from payment.payment_contract a where a.create_date > '17-july-2018' and a.collection_staff_id = (select collection_staff_id from collection_staff where lower(collection_staff_code) = lower(?))\n"
                    + "and (a.clear_debit_status is null or a.clear_debit_status <> '1')\n"
                    + "and a.status = 1 and a.payment_type = '00'\n"
                    + "and not exists (select * from payment.payment_bank_slip where payment_id = a.payment_id and status = 3)";
            ps = connection.prepareStatement(sql);
            ps.setString(1, staffCode);
            rs = ps.executeQuery();
            while (rs.next()) {
                sum = rs.getDouble("total");
            }
            logger.info("End sumSaleTransDebitByStaffCode with " + staffCode + " result " + result + " total debit: " + sum + " time "
                    + (System.currentTimeMillis() - startTime));
        } catch (Exception ex) {
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR sumSaleTransDebitByStaffCode: ").
                    append(sql).append("\n")
                    .append(" staffCode ")
                    .append(staffCode)
                    .append(" total debit")
                    .append(sum)
                    .append(" result ")
                    .append(result);
            logger.error(br + ex.toString());
        } finally {
            closeStatement(ps);
            closeResultSet(rs);
            closeConnection(connection);
            return sum;
        }
    }

    public double sumSaleFloatDebitByStaffCode(String staffCode) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuilder br = new StringBuilder();
        String sql = "";
        boolean result = false;
        long startTime = System.currentTimeMillis();
        double sum = 0;
        try {
            connection = getConnection("dbsm");
            sql = "select sum(amount_tax) as total from sm.sale_emola_float a where a.staff_id = (select staff_id from staff where lower(staff_code) = lower(?)) "
                    + " and (a.clear_debit_status is null or a.clear_debit_status <> '1') "
                    + " and a.status <> 4 ";
            ps = connection.prepareStatement(sql);
            ps.setString(1, staffCode);
            rs = ps.executeQuery();
            while (rs.next()) {
                sum = rs.getDouble("total");
            }
            logger.info("End sumSaleTransDebitByStaffCode with " + staffCode + " result " + result + " total debit: " + sum + " time "
                    + (System.currentTimeMillis() - startTime));
        } catch (Exception ex) {
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR sumSaleTransDebitByStaffCode: ").
                    append(sql).append("\n")
                    .append(" staffCode ")
                    .append(staffCode)
                    .append(" total debit")
                    .append(sum)
                    .append(" result ")
                    .append(result);
            logger.error(br + ex.toString());
        } finally {
            closeStatement(ps);
            closeResultSet(rs);
            closeConnection(connection);
            return sum;
        }
    }

    public void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                conn = null;
            } catch (SQLException ex) {
                logger.warn(loggerLabel + "Close Statement: " + ex);
                conn = null;
            }
        }
    }

    public void closeStatement(PreparedStatement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
                stmt = null;
            } catch (SQLException ex) {
                logger.warn(loggerLabel + "ERROR close Statement: " + ex.getMessage());
                stmt = null;
            }
        }
    }

    public void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
                rs = null;
            } catch (SQLException ex) {
                logger.warn(loggerLabel + "ERROR close ResultSet: " + ex.getMessage());
                rs = null;
            }
        }
    }

    public static String logException(long times, Throwable ex) {
        long timeEx = System.currentTimeMillis() - times;
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        return "Error: " + sw.toString() + " Time: " + timeEx;
    }

    public long saveEventLogsVSA(String userLogin, String staffCode, String staffCodeNew) {
        Connection connection = null;
        PreparedStatement ps = null;
        StringBuilder br = new StringBuilder();
        String sql = "";
        int res = 0;
        long startTime = System.currentTimeMillis();
        try {
            connection = getConnection("dbVsa");
            sql = " insert into event_log (event_id,event_date,user_name,action,description,ip,mac,wan) values(log_seq.nextval,sysdate,?,'CONVERT CHANNEL',?,'0.0.0.0','E0:2A:82:9A:0A::9','10.229.45.163')  ";
            ps = connection.prepareStatement(sql);
            logger.info("Start saveEventLogsVSA staffCode " + staffCode + " staffCodeNew " + staffCodeNew);
            ps.setString(1, userLogin);
            ps.setString(2, "Conver channel from:" + staffCode + " to:" + staffCodeNew);
            res = ps.executeUpdate();
            logger.info("End saveEventLogsVSA time " + (System.currentTimeMillis() - startTime));
        } catch (Exception ex) {
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR saveEventLogsVSA: ");
            logger.error(br + ex.toString());
            res = 0;
        } finally {
            closeStatement(ps);
            closeConnection(connection);
            return res;
        }
    }

    public long saveEventLogsVSAV3(String actor, String userName, String action, String description) {
        Connection connection = null;
        PreparedStatement ps = null;
        StringBuilder br = new StringBuilder();
        String sql = "";
        int res = 0;
        long startTime = System.currentTimeMillis();
        try {
            connection = getConnection("dbVsa");
            sql = "insert into event_log (event_id,event_date,actor,user_name,action,description,ip,mac,wan) "
                    + "values(log_seq.nextval,sysdate,?,?,?,?,'','',sys_context('USERENV','IP_ADDRESS'))";
            ps = connection.prepareStatement(sql);
            logger.info("Start saveEventLogsVSAV3 actor " + actor + " action " + action);
            ps.setString(1, actor);
            ps.setString(2, userName);
            ps.setString(3, action);
            ps.setString(4, description);
            res = ps.executeUpdate();
            logger.info("End saveEventLogsVSAV2 time " + (System.currentTimeMillis() - startTime));
        } catch (Exception ex) {
            ex.printStackTrace();
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR saveEventLogsVSAV3: ");
            logger.error(br + ex.toString());
            res = 0;
        } finally {
            closeStatement(ps);
            closeConnection(connection);
            return res;
        }
    }

    public boolean checkRoleBankDocumentManagement(String userName, long objectId) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuilder br = new StringBuilder();
        String sql = "";
        boolean result = false;
        long startTime = System.currentTimeMillis();
        try {
            connection = getConnection("dbsm");
            sql = "select * from vsa_v3.role_user where role_id in (select role_id from vsa_v3.role_object where object_id = ?)\n"
                    + "and user_id = (select user_id from vsa_v3.users where user_name = ?)";
            ps = connection.prepareStatement(sql);
            ps.setLong(1, objectId);
            ps.setString(2, userName.toLowerCase());
            rs = ps.executeQuery();
            while (rs.next()) {
                result = true;
            }
            logger.info("End checkRoleBankDocumentManagement userName " + userName + "objectId: " + objectId
                    + " result " + result + " time "
                    + (System.currentTimeMillis() - startTime));
        } catch (Exception ex) {
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR checkRoleBankDocumentManagement: ").
                    append(sql).append("\n")
                    .append(" userName ")
                    .append(userName)
                    .append(" objectId ")
                    .append(objectId)
                    .append(" result ")
                    .append(result);
            logger.error(br + ex.toString());
            logger.error(logException(startTime, ex));
        } finally {
            closeStatement(ps);
            closeResultSet(rs);
            closeConnection(connection);
            return result;
        }
    }

    public long getChannelTypeId(String channelTypeCode) {
        ResultSet rs = null;
        Connection connection = null;
        PreparedStatement ps = null;
        long result = 0;
        String sql = "select * from channel_type where status = 1 and upper(prefix_object_code) = upper(?)";
        StringBuilder br = new StringBuilder();
        try {
            connection = getConnection("dbsm");
            ps = connection.prepareStatement(sql);
            ps.setString(1, channelTypeCode);
            rs = ps.executeQuery();
            while (rs.next()) {
                result = rs.getLong("channel_type_id");
                break;
            }
            logger.info("End getChannelTypeId, code: " + channelTypeCode + ", channelTypeId: " + result);
        } catch (Exception ex) {
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR getChannelTypeId, code: ").
                    append(sql).append("\n").append(channelTypeCode);
            logger.error(br + ex.toString());
        } finally {
            closeResultSet(rs);
            closeStatement(ps);
            closeConnection(connection);
            return result;
        }
    }

    public boolean checkPermissionCreateVipChannel(String staffCode) {
        ResultSet rs = null;
        Connection connection = null;
        PreparedStatement ps = null;
        boolean result = false;
        StringBuilder br = new StringBuilder();
        String sql = "";
        long startTime = System.currentTimeMillis();
        try {
            connection = getConnection("dbsm");
            sql = "select * from sm.vip_channel_permission where status = 1 and upper(staff_code) = upper(?)";
            ps = connection.prepareStatement(sql);
            ps.setString(1, staffCode);
            rs = ps.executeQuery();
            while (rs.next()) {
                String name = rs.getString("staff_code");
                if (name != null && name.length() > 0) {
                    result = true;
                    break;
                }
            }
            logger.info("End checkPermissionCreateVipChannel staffCode " + staffCode + " result " + result + " time "
                    + (System.currentTimeMillis() - startTime));
        } catch (Exception ex) {
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR checkPermissionCreateVipChannel: ").
                    append(sql).append("\n")
                    .append(" staffCode ")
                    .append(staffCode)
                    .append(" result ")
                    .append(result);
            logger.error(br + ex.toString());
        } finally {
            closeResultSet(rs);
            closeStatement(ps);
            closeConnection(connection);
            return result;
        }
    }

    public boolean checkPermissionCreateChannel(String staffCode, Long channelTypeId) {
        ResultSet rs = null;
        Connection connection = null;
        PreparedStatement ps = null;
        boolean result = false;
        StringBuilder br = new StringBuilder();
        String sql = "";
        long startTime = System.currentTimeMillis();
        try {
            connection = getConnection("dbsm");
            sql = "select * from sm.create_channel_permission where status = 1 and upper(staff_code) = upper(?) and channel_type_id = ?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, staffCode);
            ps.setLong(2, channelTypeId);
            rs = ps.executeQuery();
            while (rs.next()) {
                String name = rs.getString("staff_code");
                if (name != null && name.length() > 0) {
                    result = true;
                    break;
                }
            }
            logger.info("End checkPermissionCreateChannel staffCode " + staffCode + ", channelTypeId: " + channelTypeId + " result " + result + " time "
                    + (System.currentTimeMillis() - startTime));
        } catch (Exception ex) {
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR checkPermissionCreateChannel: ").
                    append(sql).append("\n")
                    .append(" staffCode ")
                    .append(staffCode)
                    .append(" channelTypeId ")
                    .append(channelTypeId);
            logger.error(br + ex.toString());
        } finally {
            closeResultSet(rs);
            closeStatement(ps);
            closeConnection(connection);
            return result;
        }
    }

    public boolean checkChannelBelongsBranch(String staffCode, String channelCode) {
        ResultSet rs = null;
        Connection connection = null;
        PreparedStatement ps = null;
        boolean result = false;
        StringBuilder br = new StringBuilder();
        String sql = "";
        long startTime = System.currentTimeMillis();
        try {
            connection = getConnection("dbsm");
            sql = "select * from staff where lower(staff_code) = lower(?) and status = 1\n"
                    + "and exists (select 1 from shop where staff.shop_id = shop_id \n"
                    + "and shop_path like '%'||(select shop_path from shop \n"
                    + "where shop_id = (select shop_id from staff where lower(staff_code) = lower(?)))||'%')";
            ps = connection.prepareStatement(sql);
            ps.setString(1, channelCode);
            ps.setString(2, staffCode);
            rs = ps.executeQuery();
            while (rs.next()) {
                String name = rs.getString("staff_code");
                if (name != null && name.length() > 0) {
                    result = true;
                    break;
                }
            }
            logger.info("End checkChannelBelongsBranch staffCode " + staffCode + ", channelCode: " + channelCode + " result " + result + " time "
                    + (System.currentTimeMillis() - startTime));
        } catch (Exception ex) {
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR checkChannelBelongsBranch: ").
                    append(sql).append("\n")
                    .append(" staffCode ")
                    .append(staffCode)
                    .append(" channelCode ")
                    .append(channelCode);
            logger.error(br + ex.toString());
        } finally {
            closeResultSet(rs);
            closeStatement(ps);
            closeConnection(connection);
            return result;
        }
    }

    public boolean checkRole(String roleCode, String userName) {
        ResultSet rs = null;
        Connection connection = null;
        PreparedStatement ps = null;
        boolean result = false;
        StringBuilder br = new StringBuilder();
        String sql = "";
        long startTime = System.currentTimeMillis();
        try {
            connection = getConnection("dbsm");
            sql = "select * from vsa_v3.role_user where is_active = 1 and role_id = (select role_id from vsa_v3.roles where upper(role_code) = upper(?) and status = 1) \n"
                    + "and user_id = (select user_id from vsa_v3.users where lower(user_name) = lower(?) and status = 1)";
            ps = connection.prepareStatement(sql);
            ps.setString(1, roleCode);
            ps.setString(2, userName);
            rs = ps.executeQuery();
            while (rs.next()) {
                result = true;
                break;
            }
            logger.info("End checkRole userName " + userName + ", roleCode: " + roleCode + " result " + result + " time "
                    + (System.currentTimeMillis() - startTime));
        } catch (Exception ex) {
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR checkRole: ").
                    append(sql).append("\n")
                    .append(" userName ")
                    .append(userName)
                    .append(" roleCode ")
                    .append(roleCode);
            logger.error(br + ex.toString());
        } finally {
            closeResultSet(rs);
            closeStatement(ps);
            closeConnection(connection);
            return result;
        }
    }

    public boolean checkBankAccountImport(String bankCode, String accountBank) {
        ResultSet rs = null;
        Connection connection = null;
        PreparedStatement ps = null;
        boolean result = false;
        StringBuilder br = new StringBuilder();
        String sql = "";
        long startTime = System.currentTimeMillis();
        try {
            connection = getConnection("dbsm");
            sql = "select * from bank_tranfer_account where upper(bank_code) = upper(?) and upper(bank_account) = upper(?) and status = 1";
            ps = connection.prepareStatement(sql);
            ps.setString(1, bankCode);
            ps.setString(2, accountBank);
            rs = ps.executeQuery();
            while (rs.next()) {
                result = true;
                break;
            }
            logger.info("End checkBankAccountImport bankCode " + bankCode + ", accountBank: " + accountBank + " result " + result + " time "
                    + (System.currentTimeMillis() - startTime));
        } catch (Exception ex) {
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR checkBankAccountImport: ").
                    append(sql).append("\n")
                    .append(" bankCode ")
                    .append(bankCode)
                    .append(" accountBank ")
                    .append(accountBank);
            logger.error(br + ex.toString());
        } finally {
            closeResultSet(rs);
            closeStatement(ps);
            closeConnection(connection);
            return result;
        }
    }

    public boolean checkScratchCardInStock(Long ownerId, int status) {
        ResultSet rs = null;
        Connection connection = null;
        PreparedStatement ps = null;
        boolean result = false;
        StringBuilder br = new StringBuilder();
        String sql = "";
        long startTime = System.currentTimeMillis();
        try {
            connection = getConnection("dbsm");
            sql = "select * from sm.stock_card where owner_id = ? and status = ?";
            ps = connection.prepareStatement(sql);
            ps.setLong(1, ownerId);
            ps.setInt(2, status);
            rs = ps.executeQuery();
            while (rs.next()) {
                result = true;
                break;
            }
            logger.info("End checkScratchCardInStock ownerId " + ownerId + ", status: " + status + " result " + result + " time "
                    + (System.currentTimeMillis() - startTime));
        } catch (Exception ex) {
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR checkScratchCardInStock: ").
                    append(sql).append("\n")
                    .append(" ownerId ")
                    .append(ownerId)
                    .append(" status ")
                    .append(status);
            logger.error(br + ex.toString());
        } finally {
            closeResultSet(rs);
            closeStatement(ps);
            closeConnection(connection);
            return result;
        }
    }

    public boolean checkSimInStock(Long ownerId, int status) {
        ResultSet rs = null;
        Connection connection = null;
        PreparedStatement ps = null;
        boolean result = false;
        StringBuilder br = new StringBuilder();
        String sql = "";
        long startTime = System.currentTimeMillis();
        try {
            connection = getConnection("dbsm");
            sql = "select * from sm.stock_sim where owner_id = ? and status = ?";
            ps = connection.prepareStatement(sql);
            ps.setLong(1, ownerId);
            ps.setInt(2, status);
            rs = ps.executeQuery();
            while (rs.next()) {
                result = true;
                break;
            }
            logger.info("End checkSimInStock ownerId " + ownerId + ", status: " + status + " result " + result + " time "
                    + (System.currentTimeMillis() - startTime));
        } catch (Exception ex) {
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR checkSimInStock: ").
                    append(sql).append("\n")
                    .append(" ownerId ")
                    .append(ownerId)
                    .append(" status ")
                    .append(status);
            logger.error(br + ex.toString());
        } finally {
            closeResultSet(rs);
            closeStatement(ps);
            closeConnection(connection);
            return result;
        }
    }

    public boolean checkHandsetInStock(Long ownerId, int status) {
        ResultSet rs = null;
        Connection connection = null;
        PreparedStatement ps = null;
        boolean result = false;
        StringBuilder br = new StringBuilder();
        String sql = "";
        long startTime = System.currentTimeMillis();
        try {
            connection = getConnection("dbsm");
            sql = "select * from sm.stock_handset where owner_id = ? and status = ?";
            ps = connection.prepareStatement(sql);
            ps.setLong(1, ownerId);
            ps.setInt(2, status);
            rs = ps.executeQuery();
            while (rs.next()) {
                result = true;
                break;
            }
            logger.info("End checkHandsetInStock ownerId " + ownerId + ", status: " + status + " result " + result + " time "
                    + (System.currentTimeMillis() - startTime));
        } catch (Exception ex) {
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR checkHandsetInStock: ").
                    append(sql).append("\n")
                    .append(" ownerId ")
                    .append(ownerId)
                    .append(" status ")
                    .append(status);
            logger.error(br + ex.toString());
        } finally {
            closeResultSet(rs);
            closeStatement(ps);
            closeConnection(connection);
            return result;
        }
    }

    public boolean checkKitInStock(Long ownerId, int status) {
        ResultSet rs = null;
        Connection connection = null;
        PreparedStatement ps = null;
        boolean result = false;
        StringBuilder br = new StringBuilder();
        String sql = "";
        long startTime = System.currentTimeMillis();
        try {
            connection = getConnection("dbsm");
            sql = "select * from sm.stock_kit where owner_id = ? and status = ?";
            ps = connection.prepareStatement(sql);
            ps.setLong(1, ownerId);
            ps.setInt(2, status);
            rs = ps.executeQuery();
            while (rs.next()) {
                result = true;
                break;
            }
            logger.info("End checkKitInStock ownerId " + ownerId + ", status: " + status + " result " + result + " time "
                    + (System.currentTimeMillis() - startTime));
        } catch (Exception ex) {
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR checkKitInStock: ").
                    append(sql).append("\n")
                    .append(" ownerId ")
                    .append(ownerId)
                    .append(" status ")
                    .append(status);
            logger.error(br + ex.toString());
        } finally {
            closeResultSet(rs);
            closeStatement(ps);
            closeConnection(connection);
            return result;
        }
    }

    public boolean checkImportExportTransactionPending(Long ownerId) {
        ResultSet rs = null;
        Connection connection = null;
        PreparedStatement ps = null;
        boolean result = false;
        StringBuilder br = new StringBuilder();
        String sql = "";
        long startTime = System.currentTimeMillis();
        try {
            connection = getConnection("dbsm");
            sql = "select * from sm.V_STOCK_TRANS_SUSPEND where to_owner_id = ?";
            ps = connection.prepareStatement(sql);
            ps.setLong(1, ownerId);
            rs = ps.executeQuery();
            while (rs.next()) {
                result = true;
                break;
            }
            logger.info("End checkImportExportTransactionPending ownerId " + ownerId + " result " + result + " time "
                    + (System.currentTimeMillis() - startTime));
        } catch (Exception ex) {
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR checkImportExportTransactionPending: ").
                    append(sql).append("\n")
                    .append(" ownerId ")
                    .append(ownerId);
            logger.error(br + ex.toString());
        } finally {
            closeResultSet(rs);
            closeStatement(ps);
            closeConnection(connection);
            return result;
        }
    }

    public int onOffUserVsa(String userName, int status) {
        Connection connection = null;
        PreparedStatement ps = null;
        StringBuilder br = new StringBuilder();
        String sql = "";
        int res = 0;
        long startTime = System.currentTimeMillis();
        try {
            connection = getConnection("dbVsa");
            sql = "update users set status = ? "
                    + " where lower(user_name) = lower(?)";
            ps = connection.prepareStatement(sql);
            logger.info("Start onOffUserVsa username " + userName);
            ps.setInt(1, status);
            ps.setString(2, userName);
            res = ps.executeUpdate();
            logger.info("End onOffUserVsa time " + (System.currentTimeMillis() - startTime) + ", result: " + res);
        } catch (Exception ex) {
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR onOffUserVsa: ");
            logger.error(br + ex.toString());
            res = 0;
        } finally {
            closeStatement(ps);
            closeConnection(connection);
            return res;
        }
    }

    public int revertBankDocumentPending(String revertUser, Long bankTranferInfoId) {
        Connection connection = null;
        PreparedStatement ps = null;
        StringBuilder br = new StringBuilder();
        String sql = "";
        int res = 0;
        long startTime = System.currentTimeMillis();
        try {
            connection = getConnection("dbsm");
            sql = "update bank_tranfer_info set status = 1,  revert_time= sysdate, revert_user = ?, \n"
                    + "revert_desc = 'FinanceDept reverted, pending time: ' || to_char(nvl(update_time,sysdate),'yyyy-mm-dd hh24:mi:ss') "
                    + "where bank_tranfer_info_id = ? and status = 5";
            ps = connection.prepareStatement(sql);
            logger.info("Start revertBankDocumentPending revertUser " + revertUser + ", bankTranferInfoId: " + bankTranferInfoId);
            ps.setString(1, revertUser);
            ps.setLong(2, bankTranferInfoId);
            res = ps.executeUpdate();
            logger.info("End revertBankDocumentPending time " + (System.currentTimeMillis() - startTime) + ", result: " + res);
        } catch (Exception ex) {
            ex.printStackTrace();
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR revertBankDocumentPending: ");
            logger.error(br + ex.toString());
            res = 0;
        } finally {
            closeStatement(ps);
            closeConnection(connection);
            return res;
        }
    }

    public String getReceiverName(String agentReceiverId) {
        ResultSet rs = null;
        Connection connection = null;
        PreparedStatement ps = null;
        String result = "";
        StringBuilder br = new StringBuilder();
        String sql = "";
        long startTime = System.currentTimeMillis();
        try {
            connection = getConnection("dbsm");
            sql = "select receiver_name||'|'||id_no||'|'||contact_phone as receiver_name from sm.agent_receiver_info where agent_receiver_info_id = to_number(?)";
            ps = connection.prepareStatement(sql);
            ps.setString(1, agentReceiverId);
            rs = ps.executeQuery();
            while (rs.next()) {
                result = rs.getString("receiver_name");
                break;
            }
            logger.info("End getReceiverName agentReceiverId " + agentReceiverId + " result " + result + " time "
                    + (System.currentTimeMillis() - startTime));
        } catch (Exception ex) {
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR getReceiverName: ").
                    append(sql).append("\n")
                    .append(" agentReceiverId ")
                    .append(agentReceiverId);
            logger.error(br + ex.toString());
        } finally {
            closeResultSet(rs);
            closeStatement(ps);
            closeConnection(connection);
            return result;
        }
    }

    public List<RequestBorrowCash> getListRequestBorrowCash(String staffCode, String fromDate, String toDate, boolean isBod, boolean isFinanceHO, String shopCode, String status) {
        ResultSet rs = null;
        Connection connection = null;
        PreparedStatement ps = null;
        List<RequestBorrowCash> listRequest = new ArrayList<RequestBorrowCash>();
        StringBuilder br = new StringBuilder();
        String sql = "";
        long startTime = System.currentTimeMillis();
        try {
            connection = getConnection("dbsm");
            sql = "select emola_debit_log_id as request_id, amount, to_char(log_time,'dd/MM/yyyy') as create_date, (select name from sm.staff where status = 1 and a.action_user = staff_code) as name, \n"
                    + "action_user, branch||'BR' as branch, branch||'_Borrow money request' as pay_for, (select debit_limit from emola_debit_config where branch = a.branch) as current_limit, \n"
                    + "current_debit as opening_balance, action_otp, approve_otp,(case when src_money = 'ERP' then 'Approved' when a.emola_debit_trans_id is not null then (select (case when status = 2 then 'Rejected' when status = 0 then 'Not yet' else 'Approved' end) as state from sm.emola_debit_trans \n"
                    + "where a.emola_debit_trans_id = emola_debit_trans_id) end) as finance_approve,(case when src_money = 'ERP' then 'Confirmed' when a.emola_debit_trans_id is not null then (select (case when status = 3 then 'Confirmed' else 'Not yet' end) as state from sm.emola_debit_trans \n"
                    + "where trans_type = 1 and  a.emola_debit_trans_id = emola_debit_trans_id) end) as staff_confirm from emola_debit_log a where action_type = 1 ";
            if (!isBod && !isFinanceHO) {
                sql = sql + "and upper(action_user) = '" + staffCode.toUpperCase() + "'";
            } else if (isBod) {
                if (!"MV".equals(shopCode) && shopCode.length() == 5) {
                    sql = sql + "and branch = '" + shopCode.substring(0, 3) + "' ";
                } else {
                    sql = sql + "and branch = '" + shopCode + "' ";
                }

            } else if (isFinanceHO) {
                if (!"MV".equals(shopCode)) {
                    if (shopCode.length() == 5) {
                        sql = sql + "and branch = '" + shopCode.substring(0, 3) + "' ";
                    } else {
                        sql = sql + "and branch = '" + shopCode + "' ";
                    }
                }
            }
            sql = sql + "and log_time > to_date(?,'dd/MM/yyyy') and log_time < to_date(?,'dd/MM/yyyy') + 1\n";
            if ("1".equals(status)) {//Finance approve
                sql = sql + "and (emola_debit_trans_id is null or (emola_debit_trans_id is not null and exists(select 1 from sm.emola_debit_trans where trans_type = 1 \n"
                        + "and a.emola_debit_trans_id = emola_debit_trans_id and status in (1,3))))";
            } else if ("2".equals(status)) {//Finance not yet approve
                sql = sql + "and (emola_debit_trans_id is not null and exists(select 1 from sm.emola_debit_trans where trans_type = 1 \n"
                        + "and a.emola_debit_trans_id = emola_debit_trans_id and status in (0)))";
            } else if ("3".equals(status)) {//Staff confirmed
                sql = sql + "and (emola_debit_trans_id is null or (emola_debit_trans_id is not null and exists(select 1 from sm.emola_debit_trans where trans_type = 1 \n"
                        + "and a.emola_debit_trans_id = emola_debit_trans_id and status = 3)))";
            } else if ("4".equals(status)) {//Staff not yet confirmed
                sql = sql + "and (emola_debit_trans_id is not null and exists(select 1 from sm.emola_debit_trans where trans_type = 1 \n"
                        + "and a.emola_debit_trans_id = emola_debit_trans_id and status in (0,1)))";
            } else if ("5".equals(status)) {
                sql = sql + "and (emola_debit_trans_id is not null and exists(select 1 from sm.emola_debit_trans where trans_type = 1 \n"
                        + "and a.emola_debit_trans_id = emola_debit_trans_id and status in (2)))";
            } else {
                sql = sql + "and (emola_debit_trans_id is null or (emola_debit_trans_id is not null and exists(select 1 from sm.emola_debit_trans where trans_type = 1 \n"
                        + "and a.emola_debit_trans_id = emola_debit_trans_id)))";
            }
            ps = connection.prepareStatement(sql);
            ps.setString(1, fromDate);
            ps.setString(2, toDate);
            rs = ps.executeQuery();
            while (rs.next()) {
                Long requestId = rs.getLong("request_id");
                Long amount = rs.getLong("amount");
                String createDate = rs.getString("create_date");
                String name = rs.getString("name");
                String branch = rs.getString("branch");
                String payFor = rs.getString("pay_for");
                Long currentLimit = rs.getLong("current_limit");
                Long openingBalance = rs.getLong("opening_balance");
                String actionOtp = rs.getString("action_otp");
                String approveOtp = rs.getString("approve_otp");
                String financeApprove = rs.getString("finance_approve");
                String staffConfirm = rs.getString("staff_confirm");
                String actionUser = rs.getString("action_user");

                RequestBorrowCash request = new RequestBorrowCash();
                request.setRequestId(requestId);
                request.setAmount(amount);
                request.setCreateDate(createDate);
                request.setName(name);
                request.setStaffCode(actionUser.toUpperCase());
                request.setBranch(branch);
                request.setPayFor(payFor);
                request.setCurrentLimit(currentLimit);
                request.setOpeningBalance(openingBalance);
                request.setActionOtp(actionOtp);
                request.setApproveOtp(approveOtp);
                request.setFinanceApprove(financeApprove);
                request.setStaffConfirm(staffConfirm);

                listRequest.add(request);
            }
            logger.info("Sql query: " + sql);
            logger.info("End getRequestBorrowCash staffCode " + staffCode + " totalRecord: " + listRequest.size() + ", time "
                    + (System.currentTimeMillis() - startTime));
        } catch (Exception ex) {
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR getRequestBorrowCash: ").
                    append(sql).append("\n")
                    .append(" staffCode ")
                    .append(staffCode);
            logger.error(br + ex.toString());
        } finally {
            closeResultSet(rs);
            closeStatement(ps);
            closeConnection(connection);
            return listRequest;
        }
    }

    public RequestBorrowCash getRequestBorrowCash(Long requestId) {
        ResultSet rs = null;
        Connection connection = null;
        PreparedStatement ps = null;
        RequestBorrowCash request = null;
        StringBuilder br = new StringBuilder();
        String sql = "";
        long startTime = System.currentTimeMillis();
        try {
            connection = getConnection("dbsm");
            sql = "select src_money, emola_debit_log_id as request_id, amount, to_char(log_time,'dd/MM/yyyy') as create_date, (select name from sm.staff where status = 1 and a.action_user = staff_code) as name, action_user, branch||'BR' as branch,\n"
                    + "branch||'_Borrow money request' as pay_for, (select debit_limit from emola_debit_config where branch = a.branch) as current_limit, current_debit as opening_balance from emola_debit_log a where emola_debit_log_id = ?";
            ps = connection.prepareStatement(sql);
            ps.setLong(1, requestId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Long amount = rs.getLong("amount");
                String createDate = rs.getString("create_date");
                String name = rs.getString("name");
                String staffCode = rs.getString("action_user");
                String branch = rs.getString("branch");
                String payFor = rs.getString("pay_for");
                Long currentLimit = rs.getLong("current_limit");
                Long openingBalance = rs.getLong("opening_balance");
                String srcMoney = rs.getString("src_money");
                request = new RequestBorrowCash();
                request.setRequestId(requestId);
                request.setAmount(amount);
                request.setCreateDate(createDate);
                request.setName(name);
                request.setStaffCode(staffCode);
                request.setBranch(branch);
                request.setPayFor(payFor);
                request.setCurrentLimit(currentLimit);
                request.setOpeningBalance(openingBalance);
                request.setSrcMoney(srcMoney);

                break;
            }
            logger.info("End getRequestBorrowCash requestId " + requestId + ", time "
                    + (System.currentTimeMillis() - startTime));
        } catch (Exception ex) {
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR getRequestBorrowCash: ").
                    append(sql).append("\n")
                    .append(" requestId ")
                    .append(requestId);
            logger.error(br + ex.toString());
        } finally {
            closeResultSet(rs);
            closeStatement(ps);
            closeConnection(connection);
            return request;
        }
    }

    public boolean checkBodConfigBorrowCash(String userName) {
        ResultSet rs = null;
        Connection connection = null;
        PreparedStatement ps = null;
        boolean result = false;
        StringBuilder br = new StringBuilder();
        String sql = "";
        long startTime = System.currentTimeMillis();
        try {
            connection = getConnection("dbsm");
            sql = "select * from sm.emola_debit_config where lower(bod_user) = lower(?)";
            ps = connection.prepareStatement(sql);
            ps.setString(1, userName);
            rs = ps.executeQuery();
            while (rs.next()) {
                result = true;
                break;
            }
            logger.info("End checkBodConfigBorrowCash userName " + userName + " result " + result + " time "
                    + (System.currentTimeMillis() - startTime));
        } catch (Exception ex) {
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR checkBodConfigBorrowCash: ").
                    append(sql).append("\n")
                    .append(" userName ")
                    .append(userName);
            logger.error(br + ex.toString());
        } finally {
            closeResultSet(rs);
            closeStatement(ps);
            closeConnection(connection);
            return result;
        }
    }

    public boolean checkObjectCodeUserVsa(String userName, String objectCode) {
        ResultSet rs = null;
        Connection connection = null;
        PreparedStatement ps = null;
        boolean result = false;
        StringBuilder br = new StringBuilder();
        String sql = "";
        long startTime = System.currentTimeMillis();
        try {
            connection = getConnection("dbsm");
            sql = "select * from vsa_v3.role_user where user_id = (select user_id from vsa_v3.users where lower(user_name) = lower(?) and status = 1)\n"
                    + "and role_id in (select role_id from vsa_v3.role_object where object_id = (select object_id from vsa_v3.objects \n"
                    + "where object_code = ?))";
            ps = connection.prepareStatement(sql);
            ps.setString(1, userName);
            ps.setString(2, objectCode);
            rs = ps.executeQuery();
            while (rs.next()) {
                result = true;
                break;
            }
            logger.info("End checkObjectCodeUserVsa userName " + userName + ", objectCode: " + objectCode + ", result: " + result + " time "
                    + (System.currentTimeMillis() - startTime));
        } catch (Exception ex) {
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR checkObjectCodeUserVsa: ").
                    append(sql).append("\n")
                    .append(" userName ")
                    .append(userName)
                    .append(" objectCode ")
                    .append(objectCode);
            logger.error(br + ex.toString());
        } finally {
            closeResultSet(rs);
            closeStatement(ps);
            closeConnection(connection);
            return result;
        }
    }

    public boolean checkShopCode(String staffCode, String shopCode) {
        ResultSet rs = null;
        Connection connection = null;
        PreparedStatement ps = null;
        boolean result = false;
        StringBuilder br = new StringBuilder();
        String sql = "";
        long startTime = System.currentTimeMillis();
        try {
            connection = getConnection("dbsm");
            sql = "select * from sm.shop where status = 1 and shop_id = (select shop_id from sm.staff where status = 1 and lower(staff_code) = lower(?)) and shop_code = ?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, staffCode);
            ps.setString(2, shopCode);
            rs = ps.executeQuery();
            while (rs.next()) {
                result = true;
                break;
            }
            logger.info("End checkShopCode staffCode " + staffCode + " result " + result + " time "
                    + (System.currentTimeMillis() - startTime));
        } catch (Exception ex) {
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR checkShopCode: ").
                    append(sql).append("\n")
                    .append(" staffCode ")
                    .append(staffCode);
            logger.error(br + ex.toString());
        } finally {
            closeResultSet(rs);
            closeStatement(ps);
            closeConnection(connection);
            return result;
        }
    }

    public List<CashDebitInfo> getListBranchBorrowCash(String userName, String fromDate, String toDate) {
        ResultSet rs = null;
        Connection connection = null;
        PreparedStatement ps = null;
        List<CashDebitInfo> list = new ArrayList<CashDebitInfo>();
        StringBuilder br = new StringBuilder();
        String sql = "";
        long startTime = System.currentTimeMillis();
        try {
            connection = getConnection("dbsm");
            sql = "select distinct(branch) as branch from sm.emola_debit_log where log_time > to_date(?,'yyyy-MM-dd') and log_time < to_date(?,'yyyy-MM-dd') + 1";
            ps = connection.prepareStatement(sql);
            ps.setString(1, fromDate);
            ps.setString(2, toDate);
            rs = ps.executeQuery();
            while (rs.next()) {
                String branch = rs.getString("branch");
                CashDebitInfo cashDebitInfo = new CashDebitInfo();
                cashDebitInfo.setBranch(branch);
                list.add(cashDebitInfo);
            }
            logger.info("End getListBranchBorrowCash staffCode " + userName + " total record: " + list.size() + ", time "
                    + (System.currentTimeMillis() - startTime));
        } catch (Exception ex) {
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR getListBranchBorrowCash: ").
                    append(sql).append("\n")
                    .append(" staffCode ")
                    .append(userName);
            logger.error(br + ex.toString());
        } finally {
            closeResultSet(rs);
            closeStatement(ps);
            closeConnection(connection);
            return list;
        }
    }

    public List<CashDebitInfo> getListBranchConfigCash() {
        ResultSet rs = null;
        Connection connection = null;
        PreparedStatement ps = null;
        List<CashDebitInfo> list = new ArrayList<CashDebitInfo>();
        StringBuilder br = new StringBuilder();
        String sql = "";
        long startTime = System.currentTimeMillis();
        try {
            connection = getConnection("dbsm");
            sql = "select * from emola_debit_config";
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                String branch = rs.getString("branch");
                CashDebitInfo cashDebitInfo = new CashDebitInfo();
                cashDebitInfo.setBranch(branch);
                list.add(cashDebitInfo);
            }
            logger.info("End getListBranchConfigCash, total record: " + list.size() + ", time "
                    + (System.currentTimeMillis() - startTime));
        } catch (Exception ex) {
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR getListBranchConfigCash: ");
            logger.error(br + ex.toString());
        } finally {
            closeResultSet(rs);
            closeStatement(ps);
            closeConnection(connection);
            return list;
        }
    }

    public List<DebitUser> getListDebitUser(String branch, String fromDate, String toDate, boolean isStaff, String staffCode) {
        ResultSet rs = null;
        Connection connection = null;
        PreparedStatement ps = null;
        List<DebitUser> list = new ArrayList<DebitUser>();
        StringBuilder br = new StringBuilder();
        String sql = "";
        long startTime = System.currentTimeMillis();
        try {
//            connection = getConnection("dbsm");
//            sql = "select branch, (case when (select name from sm.staff where status = 1 and a.debit_user = staff_code) is null then a.debit_user ||' - Branch' else ((select name from sm.staff where status = 1 \n"
//                    + "and a.debit_user = staff_code)) end) as name, debit_user, ('TEST') as employee_code, (select cellphone from vsa_v3.users where lower(a.debit_user) = user_name and status = 1) as isdn,\n"
//                    + "(select nvl(sum((case when (action_type in (7,8) or ((action_type = 3 or action_type = 0) and ewallet_errcode = '01') or (action_type = 0 and emola_debit_trans_id is not null) or (action_type = 0 and bank_document is not null and bank_amount is not null and bank_name is not null)) then current_debit - amount \n"
//                    + "when (src_money = 'ERP' or action_type in (5,6,9,10) or (action_type = 2 and ewallet_errcode = '01')) then current_debit + amount else current_debit end)),0) as debit_opening \n"
//                    + "from sm.emola_debit_log  where log_time < to_date(?,'yyyy-MM-dd') and debit_user = a.debit_user and log_time = (select max(log_time) from sm.emola_debit_log where log_time < to_date(?,'yyyy-MM-dd') \n"
//                    + "and upper(debit_user) = upper(a.debit_user))) as debit_opening, (select nvl(sum((case when (action_type in (7,8) or ((action_type = 3 or action_type = 0) and ewallet_errcode = '01') or (action_type = 0 and emola_debit_trans_id is not null) or action_type = 0 and bank_document is not null and bank_amount is not null and bank_name is not null) then current_debit - amount when (src_money = 'ERP' or action_type in (5,6,9,10) or (action_type = 2 and ewallet_errcode = '01')) then current_debit + amount else current_debit end)),0) as debit_ending from sm.emola_debit_log b where log_time > to_date(?,'yyyy-MM-dd') and log_time < to_date(?,'yyyy-MM-dd') + 1\n"
//                    + "and upper(debit_user) = upper(a.debit_user) and log_time = (select max(log_time) from sm.emola_debit_log where log_time > to_date(?,'yyyy-MM-dd') and log_time < to_date(?,'yyyy-MM-dd') + 1\n"
//                    + "and upper(debit_user) = upper(a.debit_user) and (ewallet_errcode = '01' or ewallet_errcode is null))) as debit_ending,\n"
//                    + "(case when (select limit_money * (select nvl(debit_rate_limit,0)/100 from sm.emola_debit_config where branch = ?) as debit_limit_staff from sm.staff where lower(staff_code) = lower(a.debit_user) and status = 1) is not null \n"
//                    + "then (select limit_money * (select nvl(debit_rate_limit,0)/100 from sm.emola_debit_config where branch = ?) as debit_limit_staff from sm.staff where lower(staff_code) = lower(a.debit_user) and status = 1) \n"
//                    + "else (select debit_limit from sm.emola_debit_config where branch = ?) end) as currency_limit from sm.emola_debit_info a where branch = ?";
            connection = getConnection("report");
            sql = "select branch, (case when (select name from sm.staff@cus_view where status = 1 and a.debit_user = staff_code) is null then a.debit_user ||' - Branch' else ((select name from sm.staff@cus_view where status = 1 \n"
                    + "and a.debit_user = staff_code)) end) as name, debit_user, (select employee_code from ghr.employee@erp_view where status = 1 and mobile_number = (select cellphone from vsa_v3.users@cus_view \n"
                    + "where status = 1 and user_name = lower(a.debit_user)) and rownum < 2) as employee_code, (select cellphone from vsa_v3.users@cus_view where lower(a.debit_user) = user_name and status = 1) as isdn,\n"
                    + "(select nvl(sum((case when (action_type in (7,8) or ((action_type = 3 or action_type = 0) and ewallet_errcode = '01') or (action_type = 0 and emola_debit_trans_id is not null) or (action_type = 0 and bank_document is not null and bank_amount is not null and bank_name is not null)) then current_debit - amount \n"
                    + "when (src_money = 'ERP' or action_type in (5,6,9,10) or (action_type = 2 and ewallet_errcode = '01')) then current_debit + amount else current_debit end)),0) as debit_opening \n"
                    + "from sm.emola_debit_log@cus_view  where log_time < to_date(?,'yyyy-MM-dd') and debit_user = a.debit_user and log_time = (select max(log_time) from sm.emola_debit_log@cus_view where log_time < to_date(?,'yyyy-MM-dd') \n"
                    + "and upper(debit_user) = upper(a.debit_user))) as debit_opening, (select nvl(sum((case when (action_type in (7,8) or ((action_type = 3 or action_type = 0) and ewallet_errcode = '01') or (action_type = 0 and emola_debit_trans_id is not null) or action_type = 0 and bank_document is not null and bank_amount is not null and bank_name is not null) then current_debit - amount when (src_money = 'ERP' or action_type in (5,6,9,10) or (action_type = 2 and ewallet_errcode = '01')) then current_debit + amount else current_debit end)),0) as debit_ending from sm.emola_debit_log@cus_view b where log_time > to_date(?,'yyyy-MM-dd') and log_time < to_date(?,'yyyy-MM-dd') + 1\n"
                    + "and upper(debit_user) = upper(a.debit_user) and log_time = (select max(log_time) from sm.emola_debit_log@cus_view where log_time > to_date(?,'yyyy-MM-dd') and log_time < to_date(?,'yyyy-MM-dd') + 1\n"
                    + "and upper(debit_user) = upper(a.debit_user) and (ewallet_errcode = '01' or ewallet_errcode is null))) as debit_ending,\n"
                    + "(case when (select limit_money * (select nvl(debit_rate_limit,0)/100 from sm.emola_debit_config@cus_view where branch = ?) as debit_limit_staff from sm.staff@cus_view where lower(staff_code) = lower(a.debit_user) and status = 1) is not null \n"
                    + "then (select limit_money * (select nvl(debit_rate_limit,0)/100 from sm.emola_debit_config@cus_view where branch = ?) as debit_limit_staff from sm.staff@cus_view where lower(staff_code) = lower(a.debit_user) and status = 1) \n"
                    + "else (select debit_limit from sm.emola_debit_config@cus_view where branch = ?) end) as currency_limit from sm.emola_debit_info@cus_view a where branch = ?";
            if (isStaff) {
                sql = sql + " and upper(debit_user) = ?";
            }
            ps = connection.prepareStatement(sql);
            ps.setString(1, fromDate);
            ps.setString(2, fromDate);
            ps.setString(3, fromDate);
            ps.setString(4, toDate);
            ps.setString(5, fromDate);
            ps.setString(6, toDate);
            ps.setString(7, branch);
            ps.setString(8, branch);
            ps.setString(9, branch);
            ps.setString(10, branch);
            if (isStaff) {
                ps.setString(8, staffCode.toUpperCase());
            }
            rs = ps.executeQuery();
            int i = 1;
            while (rs.next()) {
                String name = rs.getString("name");
                String strDebitUser = rs.getString("debit_user");
                String employeeCode = rs.getString("employee_code");
                String isdn = rs.getString("isdn");
                long debitOpening = rs.getLong("debit_opening");
                long debitEnding = rs.getLong("debit_ending");
                long currencyLimit = rs.getLong("currency_limit");

                DebitUser debitUser = new DebitUser();
                debitUser.setSequence(i);
                debitUser.setBranch(branch);
                debitUser.setName(name);
                debitUser.setDebitUser(strDebitUser);
                debitUser.setEmployeeCode(employeeCode);
                debitUser.setIsdn(isdn);
                debitUser.setDebitOpening(debitOpening);
                debitUser.setDebitEnding(debitEnding);
                debitUser.setCurrencyLimit(currencyLimit);

                list.add(debitUser);
                i++;
            }
            logger.info("End getListDebitUser branch " + branch + " total record: " + list.size() + ", time "
                    + (System.currentTimeMillis() - startTime));
        } catch (Exception ex) {
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR getListDebitUser: ").
                    append(sql).append("\n")
                    .append(" branch ")
                    .append(branch);
            logger.error(br + ex.toString());
        } finally {
            closeResultSet(rs);
            closeStatement(ps);
            closeConnection(connection);
            return list;
        }
    }

    public List<DebitTrans> getListUserHaveDebitBorrowCash(String branch, boolean isStaff, String staffCode, String fromDate, String toDate) {
        ResultSet rs = null;
        Connection connection = null;
        PreparedStatement ps = null;
        List<DebitTrans> list = new ArrayList<DebitTrans>();
        StringBuilder br = new StringBuilder();
        String sql = "";
        long startTime = System.currentTimeMillis();
        try {
            connection = getConnection("dbsm");
            sql = "select distinct(debit_user) as debit_user from sm.emola_debit_log where log_time > to_date(?,'yyyy-MM-dd') and log_time < to_date(?,'yyyy-MM-dd') + 1 ";
            if (isStaff) {
                sql = sql + "and upper(debit_user) = ?";
            } else {
                sql = sql + "and branch = ?";
            }
            ps = connection.prepareStatement(sql);
            ps.setString(1, fromDate);
            ps.setString(2, toDate);
            if (isStaff) {
                ps.setString(3, staffCode.toUpperCase());
            } else {
                ps.setString(3, branch);
            }
            rs = ps.executeQuery();
            int i = 1;
            while (rs.next()) {
                String debitUser = rs.getString("debit_user");

                DebitTrans debitTrans = new DebitTrans();
                debitTrans.setSequence(i);
                debitTrans.setDebitUser(debitUser);
                list.add(debitTrans);
                i++;
            }
            logger.info("End getListUserHaveDebitBorrowCash branch " + branch + " total record: " + list.size() + ", time "
                    + (System.currentTimeMillis() - startTime));
        } catch (Exception ex) {
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR getListUserHaveDebitBorrowCash: ").
                    append(sql).append("\n")
                    .append(" branch ")
                    .append(branch);
            logger.error(br + ex.toString());
        } finally {
            closeResultSet(rs);
            closeStatement(ps);
            closeConnection(connection);
            return list;
        }
    }

    public List<DebitTransDetail> getListDebitTransDetail(String debitUser, String fromDate, String toDate, String branch) {
        ResultSet rs = null;
        Connection connection = null;
        PreparedStatement ps = null;
        List<DebitTransDetail> list = new ArrayList<DebitTransDetail>();
        StringBuilder br = new StringBuilder();
        String sql = "";
        long startTime = System.currentTimeMillis();
        try {
            connection = getConnection("dbsm");
            sql = "select emola_debit_log_id as transaction_id, branch, debit_user, action_otp, approve_otp,to_char(log_time,'dd/MM/yyyy hh24:mi:ss') as transaction_date, (case when (action_type = 0 and emola_debit_trans_id is not null) then 'Clear debit to Branch'\n"
                    + "when (action_type = 0 and emola_debit_trans_id is null and bank_document is null and bank_amount is null and bank_name is null) then 'Clear debit to Company (e-Mola)'\n"
                    + "when (action_type = 0 and emola_debit_trans_id is null and bank_document is not null and bank_amount is not null and bank_name is not null) then 'Clear debit to Company (Bank transfer)'\n"
                    + "when action_type = 1 then 'Borrow cash from company' when action_type = 2 then 'Deposit float' when action_type = 3 then 'With draw money'\n"
                    + "when action_type = 4 then 'Request clear cash to branch' when action_type = 6 then 'Branch receive money clear from staff' \n"
                    + "when action_type = 7 then 'Borrow money for staff' "
                    + "when action_type in (8,9,10) then 'Revert for Transaction code: '||revert_trans_id else 'Confirm cash' end) as transaction_type,\n"
                    + "(case when (src_money = 'ERP' and action_type = 1) then 'MOVITEL' when (action_type = 0 and emola_debit_trans_id is null) then 'MOVITEL' \n"
                    + "when (action_type = 0 and emola_debit_trans_id is not null) then branch||'BR'\n"
                    + "when (src_money <> 'ERP' and action_type = 1) then branch||'BR' when (action_type = 2) then 'CUSTOMER' when (action_type = 3) then 'CUSTOMER'\n"
                    + "when action_type in (8,9) then 'CUSTOMER' when action_type = 10 then branch||'BR'\n"
                    + "when (action_type in (5)) then branch||'BR'  when (action_type = 6) then action_user when (action_type = 7) then action_user else 'N/A' end) as client, current_debit as before_balance, \n"
                    + "(case when src_money = 'ERP' or action_type in (5,6,9,10) or (action_type = 2 and ewallet_errcode = '01') then amount else 0 end) as arising_debit,\n"
                    + "(case when (action_type in (7,8) or ((action_type = 3 or action_type = 0) and ewallet_errcode = '01') or (action_type = 0 and emola_debit_trans_id is not null) \n"
                    + "or (action_type = 0 and bank_document is not null and bank_amount is not null and bank_name is not null)) then amount else 0 end) as clear_debit,\n"
                    + "(current_debit + (case when src_money = 'ERP' or action_type in (5,6,9,10) or (action_type = 2 and ewallet_errcode = '01') \n"
                    + "then amount else 0 end) - (case when (action_type in (7,8) or (((action_type = 3 or action_type = 0) and ewallet_errcode = '01') \n"
                    + "or (action_type = 0 and bank_document is not null and bank_amount is not null and bank_name is not null)) or (action_type = 0 and emola_debit_trans_id is not null)) then amount else 0 end)) as after_balance \n"
                    + "from sm.emola_debit_log  where log_time > to_date(?,'yyyy-MM-dd') and log_time < to_date(?,'yyyy-MM-dd') + 1 and (ewallet_errcode = '01' or ewallet_errcode is null) \n"
                    + "and (action_type not in (1,4) or (action_type = 1 and src_money = 'ERP')) and upper(debit_user) = ? and branch = ? order by branch asc, debit_user,log_time asc";
            ps = connection.prepareStatement(sql);
            ps.setString(1, fromDate);
            ps.setString(2, toDate);
            ps.setString(3, debitUser.toUpperCase());
            ps.setString(4, branch);
            rs = ps.executeQuery();
            int i = 1;
            while (rs.next()) {
                long transactionId = rs.getLong("transaction_id");
                String actionOtp = rs.getString("action_otp");
                String approveOtp = rs.getString("approve_otp");
                String transactionDate = rs.getString("transaction_date");
                String transactionType = rs.getString("transaction_type");
                String client = rs.getString("client");
                long beforeBalance = rs.getLong("before_balance");
                long arisingDebit = rs.getLong("arising_debit");
                long clearDebit = rs.getLong("clear_debit");
                long afterBalance = rs.getLong("after_balance");

                DebitTransDetail debitTrans = new DebitTransDetail();
                debitTrans.setTransactionId(transactionId);
                debitTrans.setBranch(branch);
                debitTrans.setActionOtp(actionOtp);
                debitTrans.setApproveOtp(approveOtp);
                debitTrans.setTransactionDate(transactionDate);
                debitTrans.setTransactionType(transactionType);
                debitTrans.setClient(client);
                debitTrans.setBeforeBalance(beforeBalance);
                debitTrans.setArisingDebit(arisingDebit);
                debitTrans.setClearDebit(clearDebit);
                debitTrans.setAfterBalance(afterBalance);
                list.add(debitTrans);
                i++;
            }
            logger.info("End getListDebitTransDetail debitUser " + debitUser + " total record: " + list.size() + ", time "
                    + (System.currentTimeMillis() - startTime));
        } catch (Exception ex) {
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR getListDebitTransDetail: ").
                    append(sql).append("\n")
                    .append(" debitUser ")
                    .append(debitUser);
            logger.error(br + ex.toString());
        } finally {
            closeResultSet(rs);
            closeStatement(ps);
            closeConnection(connection);
            return list;
        }
    }

    public boolean checkExistsTransBorrowCash(String staffCode, String fromDate, String toDate) {
        ResultSet rs = null;
        Connection connection = null;
        PreparedStatement ps = null;
        boolean result = false;
        StringBuilder br = new StringBuilder();
        String sql = "";
        long startTime = System.currentTimeMillis();
        try {
            connection = getConnection("dbsm");
            sql = "select * from sm.emola_debit_log  where log_time > to_date(?,'yyyy-MM-dd') and log_time < to_date(?,'yyyy-MM-dd') + 1\n"
                    + "and upper(debit_user) = ?  and log_time = (select max(log_time) from sm.emola_debit_log where log_time > to_date(?,'yyyy-MM-dd') and log_time < to_date(?,'yyyy-MM-dd') + 1\n"
                    + "and upper(debit_user) = ? and (ewallet_errcode = '01' or ewallet_errcode is null))";
            ps = connection.prepareStatement(sql);
            ps.setString(1, fromDate);
            ps.setString(2, toDate);
            ps.setString(3, staffCode.toUpperCase());
            ps.setString(4, fromDate);
            ps.setString(5, toDate);
            ps.setString(6, staffCode.toUpperCase());
            rs = ps.executeQuery();
            while (rs.next()) {
                result = true;
                break;
            }
            logger.info("End checkExistsTransBorrowCash staffCode " + staffCode + " result " + result + " time "
                    + (System.currentTimeMillis() - startTime));
        } catch (Exception ex) {
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR checkExistsTransBorrowCash: ").
                    append(sql).append("\n")
                    .append(" staffCode ")
                    .append(staffCode);
            logger.error(br + ex.toString());
        } finally {
            closeResultSet(rs);
            closeStatement(ps);
            closeConnection(connection);
            return result;
        }
    }

    public long getDebitOpening(String staffCode, String fromDate) {
        ResultSet rs = null;
        Connection connection = null;
        PreparedStatement ps = null;
        long debitOpening = 0;
        StringBuilder br = new StringBuilder();
        String sql = "";
        long startTime = System.currentTimeMillis();
        try {
            connection = getConnection("dbsm");
            sql = "select nvl(sum((case when (action_type in (7,8) or ((action_type = 3 or action_type = 0) and ewallet_errcode = '01') or (action_type = 0 and emola_debit_trans_id is not null) or (action_type = 0 and bank_document is not null and bank_amount is not null and bank_name is not null)) then current_debit - amount \n"
                    + "when (src_money = 'ERP' or action_type in (5,6,9,10) or (action_type = 2 and ewallet_errcode = '01')) then current_debit + amount else current_debit end)),0) as debit_opening \n"
                    + "from sm.emola_debit_log  where log_time < to_date(?,'yyyy-MM-dd') + 1  and upper(debit_user) = ? and log_time = (select max(log_time) from sm.emola_debit_log where log_time < to_date(?,'yyyy-MM-dd') + 1 \n"
                    + "and upper(debit_user) = ?)";
            ps = connection.prepareStatement(sql);
            ps.setString(1, fromDate);
            ps.setString(2, staffCode.toUpperCase());
            ps.setString(3, fromDate);
            ps.setString(4, staffCode.toUpperCase());
            rs = ps.executeQuery();
            while (rs.next()) {
                debitOpening = rs.getLong("debit_opening");
                break;
            }
            logger.info("End getDebitOpening staffCode " + staffCode + " result " + debitOpening + " time "
                    + (System.currentTimeMillis() - startTime));
        } catch (Exception ex) {
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR getDebitOpening: ").
                    append(sql).append("\n")
                    .append(" staffCode ")
                    .append(staffCode);
            logger.error(br + ex.toString());
        } finally {
            closeResultSet(rs);
            closeStatement(ps);
            closeConnection(connection);
            return debitOpening;
        }
    }

    public long getDebitBranch(String branch) {
        ResultSet rs = null;
        Connection connection = null;
        PreparedStatement ps = null;
        long debitBranch = 0;
        StringBuilder br = new StringBuilder();
        String sql = "";
        long startTime = System.currentTimeMillis();
        try {
            connection = getConnection("dbsm");
            sql = "select debit_limit from sm.emola_debit_config where branch = ?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, branch);
            rs = ps.executeQuery();
            while (rs.next()) {
                debitBranch = rs.getLong("debit_limit");
                break;
            }
            logger.info("End getDebitBranch branch " + branch + " result " + debitBranch + " time "
                    + (System.currentTimeMillis() - startTime));
        } catch (Exception ex) {
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR getDebitBranch: ").
                    append(sql).append("\n")
                    .append(" branch ")
                    .append(branch);
            logger.error(br + ex.toString());
        } finally {
            closeResultSet(rs);
            closeStatement(ps);
            closeConnection(connection);
            return debitBranch;
        }
    }

    public long getCollectionStaffId(Long paymentId) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuilder br = new StringBuilder();
        String sql = "";
        long result = 0;
        long startTime = System.currentTimeMillis();
        try {
            connection = getConnection("dbPayment");
            sql = "select * from payment.payment_contract where create_date > '17-jul-2018' and payment_id = ?";
            ps = connection.prepareStatement(sql);
            ps.setLong(1, paymentId);
            rs = ps.executeQuery();
            while (rs.next()) {
                result = rs.getLong("collection_staff_id");
                break;
            }
            logger.info("End getCollectionStaffId paymentId " + paymentId
                    + " result " + result + " time "
                    + (System.currentTimeMillis() - startTime));
        } catch (Exception ex) {
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR getCollectionStaffId: ").
                    append(sql).append("\n")
                    .append(" paymentId ")
                    .append(paymentId);
            logger.error(br + ex.toString());
            logger.error(logException(startTime, ex));
            result = -1;
        } finally {
            closeStatement(ps);
            closeResultSet(rs);
            closeConnection(connection);
            return result;
        }
    }

    public int updatePaymentForCleanByHand(Long paymentId) {
        Connection connection = null;
        PreparedStatement ps = null;
        StringBuilder br = new StringBuilder();
        String sql = "";
        int res = 0;
        long startTime = System.currentTimeMillis();
        try {
            connection = getConnection("dbPayment");
            sql = "update payment_contract a set a.clear_debit_status=1, a.clear_debit_time=sysdate, a.clear_debit_user='IT', a.clear_debit_request_id=0 \n"
                    + "where a.create_date > to_date('17-07-2018 11:07:02','DD-MM-YYYY HH24:MI:SS')\n"
                    + "and a.payment_id = ? \n"
                    + "and a.clear_debit_status is null\n"
                    + "and not exists (select * from payment.payment_bank_slip where payment_id = a.payment_id and status = 3)";
            ps = connection.prepareStatement(sql);
            logger.info("Start updatePaymentForCleanByHand staffCode, paymentId: " + paymentId);
            ps.setLong(1, paymentId);
            res = ps.executeUpdate();
            logger.info("End updatePaymentForCleanByHand time " + (System.currentTimeMillis() - startTime) + ", result: " + res);
        } catch (Exception ex) {
            ex.printStackTrace();
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR updatePaymentForCleanByHand: ");
            logger.error(br + ex.toString());
            res = 0;
        } finally {
            closeStatement(ps);
            closeConnection(connection);
            return res;
        }
    }

    public RequestBorrowCash getEmolaDebitLog(Long requestId) {
        ResultSet rs = null;
        Connection connection = null;
        PreparedStatement ps = null;
        RequestBorrowCash request = null;
        StringBuilder br = new StringBuilder();
        String sql = "";
        long startTime = System.currentTimeMillis();
        try {
            connection = getConnection("dbsm");
            sql = "select * from sm.emola_debit_log where emola_debit_log_id = ? and revert_trans_id is null \n"
                    + "and (action_type in (2,3) or (action_type = 0 and ewallet_errcode = '01'))";
            ps = connection.prepareStatement(sql);
            ps.setLong(1, requestId);
            rs = ps.executeQuery();
            while (rs.next()) {
                String staffCode = rs.getString("action_user");
                String branch = rs.getString("branch");
                Long amount = rs.getLong("amount");
                String debitUser = rs.getString("debit_user");
                Long actionType = rs.getLong("action_type");
                String bankDocument = rs.getString("bank_document");
                String bankAmount = rs.getString("bank_amount");
                String bankName = rs.getString("bank_name");
                Long emolaDebitTransId = rs.getLong("emola_debit_trans_id");

                request = new RequestBorrowCash();
                request.setActionType(actionType);
                request.setBankDocument(bankDocument);
                request.setBankAmount(bankAmount);
                request.setBankName(bankName);
                request.setEmolaDebitTransId(emolaDebitTransId);
                request.setDebitUser(debitUser);
                request.setRequestId(requestId);
                request.setAmount(amount);
                request.setStaffCode(staffCode);
                request.setBranch(branch);
                break;
            }
            logger.info("End getEmolaDebitLog requestId " + requestId + ", time "
                    + (System.currentTimeMillis() - startTime));
        } catch (Exception ex) {
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR getEmolaDebitLog: ").
                    append(sql).append("\n")
                    .append(" requestId ")
                    .append(requestId);
            logger.error(br + ex.toString());
        } finally {
            closeResultSet(rs);
            closeStatement(ps);
            closeConnection(connection);
            return request;
        }
    }

    public String getShopPathByStaffCode(String staffCode) {
        ResultSet rs = null;
        Connection connection = null;
        PreparedStatement ps = null;
        String shopPath = "";
        StringBuilder br = new StringBuilder();
        String sql = "";
        long startTime = System.currentTimeMillis();
        try {
            connection = getConnection("dbsm");
            sql = "select shop_path from sm.shop where shop_id = (select shop_id from sm.staff where lower(staff_code) = lower(?) and status = 1)";
            ps = connection.prepareStatement(sql);
            ps.setString(1, staffCode);
            rs = ps.executeQuery();
            while (rs.next()) {
                shopPath = rs.getString("shop_path");
                break;
            }
            logger.info("End getShopPathByStaffCode staffCode " + staffCode + " shopPath " + shopPath + " time "
                    + (System.currentTimeMillis() - startTime));
        } catch (Exception ex) {
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR getShopPathByStaffCode: ").
                    append(sql).append("\n")
                    .append(" staffCode ")
                    .append(staffCode)
                    .append(" shopPath ")
                    .append(shopPath);
            logger.error(br + ex.toString());
        } finally {
            closeResultSet(rs);
            closeStatement(ps);
            closeConnection(connection);
            return shopPath;
        }
    }

    public String getShopCode(String shopId) {
        ResultSet rs = null;
        Connection connection = null;
        PreparedStatement ps = null;
        String shopCode = "";
        StringBuilder br = new StringBuilder();
        String sql = "";
        long startTime = System.currentTimeMillis();
        try {
            connection = getConnection("dbsm");
            sql = "select * from sm.shop where status = 1 and shop_id = to_number(?)";
            ps = connection.prepareStatement(sql);
            ps.setString(1, shopId);
            rs = ps.executeQuery();
            while (rs.next()) {
                shopCode = rs.getString("shop_code");;
                break;
            }
            logger.info("End getShopCode shopId " + shopId + ", shopCode: " + shopCode + " time "
                    + (System.currentTimeMillis() - startTime));
        } catch (Exception ex) {
            ex.printStackTrace();
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR getShopCode: ").
                    append(sql).append("\n")
                    .append(" shopId ")
                    .append(shopId);
            logger.error(br + ex.toString());
        } finally {
            closeResultSet(rs);
            closeStatement(ps);
            closeConnection(connection);
            return shopCode;
        }
    }

    public Long getDebitLimit(String userName) {
        ResultSet rs = null;
        Connection connection = null;
        PreparedStatement ps = null;
        Long debitLimit = 0L;
        StringBuilder br = new StringBuilder();
        String sql = "";
        long startTime = System.currentTimeMillis();
        try {
            connection = getConnection("dbsm");
            sql = "select * from sm.emola_debit_config where lower(bod_user) = lower(?)";
            ps = connection.prepareStatement(sql);
            ps.setString(1, userName);
            rs = ps.executeQuery();
            while (rs.next()) {
                debitLimit = rs.getLong("debit_limit");
                break;
            }
            logger.info("End getDebitLimit userName " + userName + ", getDebitLimit: " + debitLimit + " time "
                    + (System.currentTimeMillis() - startTime));
        } catch (Exception ex) {
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR getDebitLimit: ").
                    append(sql).append("\n")
                    .append(" userName ")
                    .append(userName);
            logger.error(br + ex.toString());
        } finally {
            closeResultSet(rs);
            closeStatement(ps);
            closeConnection(connection);
            return debitLimit;
        }
    }

    public Long getDebitCurrentAmount(String staffCode) {
        ResultSet rs = null;
        Connection connection = null;
        PreparedStatement ps = null;
        Long debitCurrentAmount = null;
        StringBuilder br = new StringBuilder();
        String sql = "";
        long startTime = System.currentTimeMillis();
        try {
            connection = getConnection("dbsm");
            sql = "select nvl(sum(debit_current_amount),0) as total_debit_staff from emola_debit_info where lower(debit_user) = lower(?)";
            ps = connection.prepareStatement(sql);
            ps.setString(1, staffCode);
            rs = ps.executeQuery();
            while (rs.next()) {
                debitCurrentAmount = rs.getLong("total_debit_staff");
                break;
            }
            logger.info("End getDebitCurrentAmount staffCode " + staffCode + ", debitCurrentAmount: " + debitCurrentAmount + " time "
                    + (System.currentTimeMillis() - startTime));
        } catch (Exception ex) {
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR getDebitCurrentAmount: ").
                    append(sql).append("\n")
                    .append(" staffCode ")
                    .append(staffCode);
            logger.error(br + ex.toString());
        } finally {
            closeResultSet(rs);
            closeStatement(ps);
            closeConnection(connection);
            return debitCurrentAmount;
        }
    }

    public Long getEmolaDebitLimitStaff(String shopId, String staffCode) {
        ResultSet rs = null;
        Connection connection = null;
        PreparedStatement ps = null;
        Long emolaDebitLimitStaff = 0L;
        StringBuilder br = new StringBuilder();
        String sql = "";
        long startTime = System.currentTimeMillis();
        try {
            connection = getConnection("dbsm");
            sql = "select limit_money * (select nvl(debit_rate_limit,0)/100 from sm.emola_debit_config where branch = (select substr(shop_code,0,3) as branch from sm.shop\n"
                    + "where status = 1 and shop_id = to_number(?))) as debit_limit_staff from sm.staff where upper(staff_code) = upper(?) and status = 1";
            ps = connection.prepareStatement(sql);
            ps.setString(1, shopId);
            ps.setString(2, staffCode);
            rs = ps.executeQuery();
            while (rs.next()) {
                emolaDebitLimitStaff = rs.getLong("debit_limit_staff");
                break;
            }
            logger.info("End getEmolaDebitLimitStaff staffCode " + staffCode + ", emolaDebitLimitStaff: " + emolaDebitLimitStaff + " time "
                    + (System.currentTimeMillis() - startTime));
        } catch (Exception ex) {
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR getEmolaDebitLimitStaff: ").
                    append(sql).append("\n")
                    .append(" staffCode ")
                    .append(staffCode);
            logger.error(br + ex.toString());
        } finally {
            closeResultSet(rs);
            closeStatement(ps);
            closeConnection(connection);
            return emolaDebitLimitStaff;
        }
    }

    public String getShopIdOfUserDebit(String debitUser) {
        ResultSet rs = null;
        Connection connection = null;
        PreparedStatement ps = null;
        String shopId = "";
        StringBuilder br = new StringBuilder();
        String sql = "";
        long startTime = System.currentTimeMillis();
        try {
            connection = getConnection("dbsm");
            sql = "select * from sm.shop where shop_code = (select branch||'BR' from sm.emola_debit_info where upper(debit_user) = upper(?)) and status = 1";
            ps = connection.prepareStatement(sql);
            ps.setString(1, debitUser);
            rs = ps.executeQuery();
            while (rs.next()) {
                shopId = rs.getString("shop_id");;
                break;
            }
            logger.info("End getShopIdOfUserDebit debitUser " + debitUser + ", shopId: " + shopId + " time "
                    + (System.currentTimeMillis() - startTime));
        } catch (Exception ex) {
            ex.printStackTrace();
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR getShopIdOfUserDebit: ").
                    append(sql).append("\n")
                    .append(" debitUser ")
                    .append(debitUser);
            logger.error(br + ex.toString());
        } finally {
            closeResultSet(rs);
            closeStatement(ps);
            closeConnection(connection);
            return shopId;
        }
    }

    public int clearMoneyBorrow(String debitUser, Long cashAmount, Long floatAmount) {
        Connection connection = null;
        PreparedStatement ps = null;
        StringBuilder br = new StringBuilder();
        String sql = "";
        int result = 0;
        long startTime = System.currentTimeMillis();
        try {
            connection = getConnection("dbsm");
            sql = "update emola_debit_info set debit_current_amount = debit_current_amount - ?, "
                    + "last_update_time = sysdate, debit_cash_amount = debit_cash_amount - ?, debit_float_amount = debit_float_amount - ? where lower(debit_user) = lower(?)";
            ps = connection.prepareStatement(sql);
            Long totalAmount = cashAmount + floatAmount;
            ps.setLong(1, totalAmount);
            ps.setLong(2, cashAmount);
            ps.setLong(3, floatAmount);
            ps.setString(4, debitUser);
            result = ps.executeUpdate();
            logger.info("End clearMoneyBorrow debitAmount " + totalAmount + " debitUser " + debitUser + " result " + result + " time "
                    + (System.currentTimeMillis() - startTime));
        } catch (Exception ex) {
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR clearMoneyBorrow: ").
                    append(sql).append("\n")
                    .append(" debitAmount ")
                    .append(cashAmount + floatAmount)
                    .append(" debitUser ")
                    .append(debitUser)
                    .append(" result ")
                    .append(result);
            logger.error(br + ex.toString());
        } finally {
            closeStatement(ps);
            closeConnection(connection);
            return result;
        }
    }

    public boolean checkClearDebitFor(String debitUser) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuilder br = new StringBuilder();
        String sql = "";
        boolean result = false;
        long startTime = System.currentTimeMillis();
        try {
            connection = getConnection("dbsm");
            sql = "select * from sm.emola_debit_config where branch = ?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, debitUser);
            rs = ps.executeQuery();
            while (rs.next()) {
                result = true;
                break;
            }
            logger.info("End checkClearDebitFor debitUser " + debitUser
                    + " result " + result + " time "
                    + (System.currentTimeMillis() - startTime));
        } catch (Exception ex) {
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR checkClearDebitFor: ").
                    append(sql).append("\n")
                    .append(" debitUser ")
                    .append(debitUser);
            logger.error(br + ex.toString());
            logger.error(logException(startTime, ex));
        } finally {
            closeStatement(ps);
            closeResultSet(rs);
            closeConnection(connection);
            return result;
        }
    }

    public int insertEmolaDebitLog(Long emolaDebitLogId, String shopId, String actionUser, int actionType, Long amount, String actionOtp, String approveOtp, String srcMoney,
            Long currentLimit, String ewalletRequest, String ewalletResponse, String ewalletErrorCode, String ewalletVoucher, String ewalletRequestId,
            String ewalletOrgRequestId, String agentId, String oldAgentId, String agentMobile, Long emolaDebitTransId, String feeWithdraw, int clearType,
            Long currentDebit, String debitUser, String bankDocument, String bankAmount, String bankName, String revertUser) {
        Connection connection = null;
        PreparedStatement ps = null;
        StringBuilder br = new StringBuilder();
        String sql = "";
        int result = 0;
        logger.info("parameter: shopId: " + shopId + ", actionType: " + actionType + ", actionOtp: " + actionOtp + ", approveOtp: " + approveOtp + ",srcMoney: " + srcMoney + ","
                + "currentLimit: " + currentLimit + ", ewalletRequest: " + ewalletRequest + ", ewalletResponse: " + ewalletResponse
                + ", ewalletErr: " + ewalletErrorCode + ", voucher: " + ewalletVoucher + ", emolaDebitTransId: " + emolaDebitTransId
                + ", feeWithdraw: " + feeWithdraw + ", clearType: " + clearType + ", currentDebit: " + currentDebit + ", debitUser: " + debitUser
                + ", bankDocument: " + bankDocument + ", bankAmount: " + bankAmount + ", bankName: " + bankName + ", revertUser: " + revertUser);
        long startTime = System.currentTimeMillis();
        try {

            connection = getConnection("dbsm");
            sql = "insert into emola_debit_log (branch, action_user, action_type, amount, log_time, action_otp, approve_otp, src_money, current_limit, \n"
                    + "ewallet_request, ewallet_response, ewallet_errcode, ewallet_voucher, ewallet_request_id, ewallet_org_request_id, agent_id, old_agent_id, agent_mobile, "
                    + "emola_debit_trans_id, emola_fee_withdraw, clear_type, current_debit, debit_user, bank_document, bank_name, bank_amount, emola_debit_log_id, revert_user)\n"
                    + "values ((select substr(shop_code,0,3) as branch from sm.shop where status = 1 and shop_id = to_number(?)),\n"
                    + "?,?,?,sysdate,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            ps = connection.prepareStatement(sql);
            ps.setString(1, shopId);
            ps.setString(2, actionUser.toUpperCase());
            ps.setInt(3, actionType);
            ps.setLong(4, amount);
            ps.setString(5, actionOtp);
            ps.setString(6, approveOtp);
            ps.setString(7, srcMoney);
            ps.setLong(8, currentLimit);
            ps.setString(9, ewalletRequest);
            ps.setString(10, ewalletResponse);
            ps.setString(11, ewalletErrorCode);
            ps.setString(12, ewalletVoucher);
            ps.setString(13, ewalletRequestId);
            ps.setString(14, ewalletOrgRequestId);
            ps.setString(15, agentId);
            ps.setString(16, oldAgentId);
            ps.setString(17, agentMobile);
            if (emolaDebitTransId > 0) {
                ps.setLong(18, emolaDebitTransId);
            } else {
                ps.setString(18, "");
            }
            ps.setString(19, feeWithdraw);
            if (clearType >= 0) {
                ps.setInt(20, clearType);
            } else {
                ps.setString(20, "");
            }
            ps.setLong(21, currentDebit);
            ps.setString(22, debitUser);
            if (bankDocument != null && !bankDocument.isEmpty()) {
                ps.setString(23, bankDocument);
            } else {
                ps.setString(23, "");
            }
            if (bankName != null && !bankName.isEmpty()) {
                ps.setString(24, bankName);
            } else {
                ps.setString(24, "");
            }
            if (bankAmount != null && !bankAmount.isEmpty()) {
                ps.setString(25, bankAmount);
            } else {
                ps.setString(25, "");
            }
            ps.setLong(26, emolaDebitLogId);
            if (revertUser != null && !revertUser.isEmpty()) {
                ps.setString(27, revertUser);
            } else {
                ps.setString(27, "");
            }
            result = ps.executeUpdate();
            logger.info("End insertEmolaDebitLog actionUser: " + actionUser + " result " + result + " time "
                    + (System.currentTimeMillis() - startTime));
        } catch (Exception ex) {
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR insertEmolaDebitLog: ").
                    append(sql).append("\n")
                    .append(" actionUser ")
                    .append(actionUser);
            logger.error(br + ex.toString());
            result = -1;
        } finally {
            closeStatement(ps);
            closeConnection(connection);
            return result;
        }
    }

    public Long getSequence(String sequenceName, String dbName) {
        ResultSet rs1 = null;
        Connection connection = null;
        Long sequenceValue = null;
        String sqlMo = "select " + sequenceName + ".nextval as sequence from dual";
        PreparedStatement psMo = null;
        try {
            connection = getConnection(dbName);
            psMo = connection.prepareStatement(sqlMo);
            rs1 = psMo.executeQuery();
            while (rs1.next()) {
                sequenceValue = rs1.getLong("sequence");
            }
            logger.info("End getSequence sequenceName " + sequenceName);
        } catch (Exception ex) {
            logger.error("ERROR getSequence " + sequenceName);
        } finally {
            closeResultSet(rs1);
            closeStatement(psMo);
            closeConnection(connection);
        }
        return sequenceValue;
    }

    public int updateRevertTransaction(Long transRevertId, Long emolaDebitLogId) {
        Connection connection = null;
        PreparedStatement ps = null;
        StringBuilder br = new StringBuilder();
        String sql = "";
        int result = 0;
        long startTime = System.currentTimeMillis();
        try {
            connection = getConnection("dbsm");
            sql = "update emola_debit_log set revert_trans_id = ? where emola_debit_log_id = ?";
            ps = connection.prepareStatement(sql);
            ps.setLong(1, transRevertId);
            ps.setLong(2, emolaDebitLogId);
            result = ps.executeUpdate();
            logger.info("End updateRevertTransaction transRevertId " + transRevertId + " emolaDebitLogId " + emolaDebitLogId + " result " + result + " time "
                    + (System.currentTimeMillis() - startTime));
        } catch (Exception ex) {
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR updateRevertTransaction: ").
                    append(sql).append("\n")
                    .append(" transRevertId ")
                    .append(transRevertId)
                    .append(" emolaDebitLogId ")
                    .append(emolaDebitLogId);
            logger.error(br + ex.toString());
        } finally {
            closeStatement(ps);
            closeConnection(connection);
            return result;
        }
    }

    public int updateDepositForCleanByHand(Long depositId, String bankTranferId) {
        Connection connection = null;
        PreparedStatement ps = null;
        StringBuilder br = new StringBuilder();
        String sql = "";
        int res = 0;
        long startTime = System.currentTimeMillis();
        try {
            connection = getConnection("dbsm");
            sql = "update deposit set clear_debit_status = 1, clear_debit_time = sysdate, clear_debit_user = 'IT',"
                    + "clear_debit_request_id = ? where deposit_id = ? and type = 1 and status in (0,1) "
                    + "and (clear_debit_status is null or clear_debit_status <> 1) and create_date > '17-jul-2018' and amount > 0";
            ps = connection.prepareStatement(sql);
            logger.info("Start updateDepositForCleanByHand staffCode, depositId: " + depositId);
            ps.setString(1, bankTranferId);
            ps.setLong(2, depositId);
            res = ps.executeUpdate();
            logger.info("End updateDepositForCleanByHand time " + (System.currentTimeMillis() - startTime) + ", result: " + res);
        } catch (Exception ex) {
            ex.printStackTrace();
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR updateDepositForCleanByHand: ");
            logger.error(br + ex.toString());
            res = 0;
        } finally {
            closeStatement(ps);
            closeConnection(connection);
            return res;
        }
    }

    public int updateSaleFloatForCleanByHand(Long saleFloatId, String bankTranferId) {
        Connection connection = null;
        PreparedStatement ps = null;
        StringBuilder br = new StringBuilder();
        String sql = "";
        int res = 0;
        long startTime = System.currentTimeMillis();
        try {
            connection = getConnection("dbsm");
            sql = "update sale_emola_float set clear_debit_status = 1, clear_debit_time = sysdate, clear_debit_user = 'IT',"
                    + "clear_debit_request_id = ? where sale_trans_id = ? and (clear_debit_status is null or clear_debit_status <> 1) "
                    + "and status <> 4 and amount_tax > 0  and reason_id = 201948";
            ps = connection.prepareStatement(sql);
            logger.info("Start updateSaleFloatForCleanByHand staffCode, saleFloatId: " + saleFloatId);
            ps.setString(1, bankTranferId);
            ps.setLong(2, saleFloatId);
            res = ps.executeUpdate();
            logger.info("End updateSaleFloatForCleanByHand time " + (System.currentTimeMillis() - startTime) + ", result: " + res);
        } catch (Exception ex) {
            ex.printStackTrace();
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR updateSaleFloatForCleanByHand: ");
            logger.error(br + ex.toString());
            res = 0;
        } finally {
            closeStatement(ps);
            closeConnection(connection);
            return res;
        }
    }

    public long getSaleFloatStaffId(Long saleFloatId) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuilder br = new StringBuilder();
        String sql = "";
        long result = 0;
        long startTime = System.currentTimeMillis();
        try {
            connection = getConnection("dbsm");
            sql = "select * from sm.sale_emola_float where sale_trans_id = ?";
            ps = connection.prepareStatement(sql);
            ps.setLong(1, saleFloatId);
            rs = ps.executeQuery();
            while (rs.next()) {
                result = rs.getLong("staff_id");
                break;
            }
            logger.info("End getSaleFloatStaffId saleFloatId " + saleFloatId
                    + " result " + result + " time "
                    + (System.currentTimeMillis() - startTime));
        } catch (Exception ex) {
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR getSaleFloatStaffId: ").
                    append(sql).append("\n")
                    .append(" saleFloatId ")
                    .append(saleFloatId);
            logger.error(br + ex.toString());
            logger.error(logException(startTime, ex));
            result = -1;
        } finally {
            closeStatement(ps);
            closeResultSet(rs);
            closeConnection(connection);
            return result;
        }
    }

    public int assignRoleSaleHandset(String branch, String channelCode, String actionUser) {
        Connection connection = null;
        PreparedStatement ps = null;
        StringBuilder br = new StringBuilder();
        String sql = "";
        int result = 0;
        long startTime = System.currentTimeMillis();
        try {

            connection = getConnection("dbProduct");
            sql = "insert into product.product_connect_kit_hs_user (branch, staff_code, status, create_time, add_user)\n"
                    + "values (?,?,1,sysdate,?)";
            ps = connection.prepareStatement(sql);
            ps.setString(1, branch.toUpperCase());
            ps.setString(2, channelCode.toUpperCase());
            ps.setString(3, actionUser.toUpperCase());
            result = ps.executeUpdate();
            logger.info("End assignRoleSaleHandset channelCode: " + channelCode + " result " + result + " time "
                    + (System.currentTimeMillis() - startTime));
        } catch (Exception ex) {
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR assignRoleSaleHandset: ").
                    append(sql).append("\n")
                    .append(" channelCode ")
                    .append(channelCode);
            logger.error(br + ex.toString());
            result = -1;
        } finally {
            closeStatement(ps);
            closeConnection(connection);
            return result;
        }
    }

    public boolean checkChannelAlreadyAssginRole(String channelCode) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuilder br = new StringBuilder();
        String sql = "";
        boolean result = false;
        long startTime = System.currentTimeMillis();
        try {
            connection = getConnection("dbProduct");
            sql = "select * from product_connect_kit_hs_user where status = 1 and lower(staff_code) = lower(?)";
            ps = connection.prepareStatement(sql);
            ps.setString(1, channelCode);
            rs = ps.executeQuery();
            while (rs.next()) {
                result = true;
                break;
            }
            logger.info("End checkChannelAlreadyAssginRole channelCode " + channelCode
                    + " result " + result + " time "
                    + (System.currentTimeMillis() - startTime));
        } catch (Exception ex) {
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR checkChannelAlreadyAssginRole: ").
                    append(sql).append("\n")
                    .append(" channelCode ")
                    .append(channelCode);
            logger.error(br + ex.toString());
            logger.error(logException(startTime, ex));
        } finally {
            closeStatement(ps);
            closeResultSet(rs);
            closeConnection(connection);
            return result;
        }
    }

    public boolean checkChannelAlreadyAssginRoleStudent(String channelCode) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuilder br = new StringBuilder();
        String sql = "";
        boolean result = false;
        long startTime = System.currentTimeMillis();
        try {
            connection = getConnection("dbProduct");
            sql = "select * from main_product_connect_kit where main_product = 'STUDENT' and status = 1 and lower(for_user) like ?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, "%" + channelCode.toLowerCase() + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                result = true;
                break;
            }
            logger.info("End checkChannelAlreadyAssginRoleStudent channelCode " + channelCode
                    + " result " + result + " time "
                    + (System.currentTimeMillis() - startTime));
        } catch (Exception ex) {
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR checkChannelAlreadyAssginRoleStudent: ").
                    append(sql).append("\n")
                    .append(" channelCode ")
                    .append(channelCode);
            logger.error(br + ex.toString());
            logger.error(logException(startTime, ex));
        } finally {
            closeStatement(ps);
            closeResultSet(rs);
            closeConnection(connection);
            return result;
        }
    }

    public int removeRoleSaleHandset(String channelCode, String actionUser) throws Exception {
        Connection connection = null;
        PreparedStatement ps = null;
        StringBuilder br = new StringBuilder();
        String sql = "";
        int res = 0;
        long startTime = System.currentTimeMillis();
        try {
            connection = getConnection("dbProduct");
            sql = "update product.product_connect_kit_hs_user set status = 0, cancel_user = ?, cancel_time = sysdate where status = 1 and lower(staff_code) = lower(?) ";
            ps = connection.prepareStatement(sql);
            ps.setString(1, actionUser.toUpperCase());
            ps.setString(2, channelCode);
            res = ps.executeUpdate();
            logger.info("End removeRoleSaleHandset time " + (System.currentTimeMillis() - startTime));
        } catch (Exception ex) {
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR removeRoleSaleHandset: ");
            logger.error(br + ex.toString());
            res = 0;
        } finally {
            closeStatement(ps);
            closeConnection(connection);
            return res;
        }
    }

    public List<ReportHandsetInfo> getListBranchReportHandsetInfo(String userName) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuilder br = new StringBuilder();
        String sql = "";
        List<ReportHandsetInfo> list = new ArrayList<ReportHandsetInfo>();
        long startTime = System.currentTimeMillis();
        try {
            connection = getConnection("dbProduct");
            sql = "select distinct branch as branch from product.product_connect_kit_hs_user where status = 1";
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                String branch = rs.getString("branch");
                ReportHandsetInfo report = new ReportHandsetInfo();
                report.setBranch(branch);
                list.add(report);
            }
            logger.info("End getListBranchReportHandsetInfo userName " + userName
                    + " total record: " + list.size() + " time "
                    + (System.currentTimeMillis() - startTime));
        } catch (Exception ex) {
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR getListBranchReportHandsetInfo: ").
                    append(sql).append("\n")
                    .append(" userName ")
                    .append(userName);
            logger.error(br + ex.toString());
            logger.error(logException(startTime, ex));
        } finally {
            closeStatement(ps);
            closeResultSet(rs);
            closeConnection(connection);
            return list;
        }
    }

    public List<ChannelHandsetDetail> getChannelHandsetDetail(String branch) {
        ResultSet rs = null;
        Connection connection = null;
        PreparedStatement ps = null;
        List<ChannelHandsetDetail> list = new ArrayList<ChannelHandsetDetail>();
        StringBuilder br = new StringBuilder();
        String sql = "";
        long startTime = System.currentTimeMillis();
        try {
            connection = getConnection("dbsm");
            sql = "SELECT   (SELECT   (CASE\n"
                    + "                        WHEN b.staff_owner_id IS NULL\n"
                    + "                        THEN\n"
                    + "                            staff_code\n"
                    + "                        ELSE\n"
                    + "                            (SELECT   staff_code\n"
                    + "                               FROM   sm.staff\n"
                    + "                              WHERE   status IN (0, 1, 2)\n"
                    + "                                      AND staff_id = b.staff_owner_id)\n"
                    + "                    END)\n"
                    + "                       AS staff_code\n"
                    + "            FROM   sm.staff b\n"
                    + "           WHERE   status IN (0, 1, 2)\n"
                    + "                   AND LOWER (a.staff_code) = LOWER (staff_code))\n"
                    + "             AS management_staff,\n"
                    + "         a.staff_code AS channel_code,\n"
                    + "         (SELECT   (CASE WHEN status = 1 THEN 'Active' ELSE 'Inactive' END)\n"
                    + "                       AS channel_status\n"
                    + "            FROM   sm.staff\n"
                    + "           WHERE   status IN (0, 1, 2)\n"
                    + "                   AND LOWER (a.staff_code) = LOWER (staff_code))\n"
                    + "             AS channel_name,\n"
                    + "         (SELECT   name\n"
                    + "            FROM   sm.staff\n"
                    + "           WHERE   status IN (0, 1, 2)\n"
                    + "                   AND LOWER (a.staff_code) = LOWER (staff_code))\n"
                    + "             AS channel_status,\n"
                    + "         (CASE\n"
                    + "              WHEN EXISTS\n"
                    + "                       (SELECT   1\n"
                    + "                          FROM   product.main_product_connect_kit\n"
                    + "                         WHERE       status = 1\n"
                    + "                                 AND main_product = 'STUDENT'\n"
                    + "                                 AND for_user LIKE '%' || a.staff_code || '%')\n"
                    + "              THEN\n"
                    + "                  'SPECIAL'\n"
                    + "              ELSE\n"
                    + "                  'PROMOTION'\n"
                    + "          END)\n"
                    + "             AS price_policy,\n"
                    + "         (SELECT   (CASE\n"
                    + "                        WHEN prefix_object_code IS NOT NULL\n"
                    + "                        THEN\n"
                    + "                            prefix_object_code\n"
                    + "                        ELSE\n"
                    + "                            code\n"
                    + "                    END)\n"
                    + "            FROM   sm.channel_type\n"
                    + "           WHERE   channel_type_id =\n"
                    + "                       (SELECT   channel_type_id\n"
                    + "                          FROM   sm.staff\n"
                    + "                         WHERE   status = 1\n"
                    + "                                 AND LOWER (staff_code) =\n"
                    + "                                        LOWER (a.staff_code)))\n"
                    + "             AS channel_type,\n"
                    + "         (SELECT   cellphone\n"
                    + "            FROM   vsa_v3.users\n"
                    + "           WHERE       status = 1\n"
                    + "                   AND user_name = LOWER (a.staff_code)\n"
                    + "                   AND ROWNUM < 2)\n"
                    + "             AS number_phone, branch\n"
                    + "  FROM   product.product_connect_kit_hs_user a\n"
                    + " WHERE   a.status = 1 ";
            if (!branch.isEmpty()) {
                sql = sql + " and branch = ?";
            }
            ps = connection.prepareStatement(sql);
            if (!branch.isEmpty()) {
                ps.setString(1, branch);
            }
            rs = ps.executeQuery();
            long i = 1;
            while (rs.next()) {
                String managementStaff = rs.getString("management_staff");
                String channelCode = rs.getString("channel_code");
                String channelName = rs.getString("channel_name");
                String channelStatus = rs.getString("channel_status");
                String pricePolicy = rs.getString("price_policy");
                String channelType = rs.getString("channel_type");
                String numberPhone = rs.getString("number_phone");
                String tmpBranch = rs.getString("branch");

                ChannelHandsetDetail channel = new ChannelHandsetDetail();
                channel.setSequence(i);
                channel.setManagementStaff(managementStaff);
                channel.setChannelName(channelName);
                channel.setChannelStatus(channelStatus);
                channel.setPricePolicy(pricePolicy);
                channel.setChannelCode(channelCode);
                channel.setChannelType(channelType);
                channel.setNumberPhone(numberPhone);
                channel.setBranch(tmpBranch);

                list.add(channel);
                i++;
            }
            logger.info("End getChannelHandsetDetail branch " + branch + " total record: " + list.size() + ", time "
                    + (System.currentTimeMillis() - startTime));
        } catch (Exception ex) {
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR getChannelHandsetDetail: ").
                    append(sql).append("\n")
                    .append(" branch ")
                    .append(branch);
            logger.error(br + ex.toString());
        } finally {
            closeResultSet(rs);
            closeStatement(ps);
            closeConnection(connection);
            return list;
        }
    }

    public boolean checkStaffActive(String staffCode) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuilder br = new StringBuilder();
        String sql = "";
        boolean result = false;
        long startTime = System.currentTimeMillis();
        long transId = 0;
        try {
            connection = getConnection("dbsm");
            sql = "select * from staff where lower(staff_code) = lower(?) and status = 1";
            ps = connection.prepareStatement(sql);
            ps.setString(1, staffCode);
            rs = ps.executeQuery();
            while (rs.next()) {
                transId = rs.getLong("staff_id");
                if (transId > 0) {
                    result = true;
                    break;
                }
            }
            logger.info("End checkStaffActive staffCode " + staffCode
                    + " result " + result + " transId " + transId + " time "
                    + (System.currentTimeMillis() - startTime));
        } catch (Exception ex) {
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR checkStaffActive: ").
                    append(sql).append("\n")
                    .append(" staffCode ")
                    .append(staffCode)
                    .append(" result ")
                    .append(result);
            logger.error(br + ex.toString());
        } finally {
            closeStatement(ps);
            closeResultSet(rs);
            closeConnection(connection);
            return result;
        }
    }

    public int assignPermissionConnectStudent(String channelCode) throws Exception {
        Connection connection = null;
        PreparedStatement ps = null;
        StringBuilder br = new StringBuilder();
        String sql = "";
        int res = 0;
        long startTime = System.currentTimeMillis();
        try {
            connection = getConnection("dbProduct");
            sql = "update product.main_product_connect_kit set for_user = for_user||'|'||? where main_product = 'STUDENT' and status = 1";
            ps = connection.prepareStatement(sql);
            ps.setString(1, channelCode.toUpperCase());
            res = ps.executeUpdate();
            logger.info("End assignPermissionConnectStudent time " + (System.currentTimeMillis() - startTime));
        } catch (Exception ex) {
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR assignPermissionConnectStudent: ");
            logger.error(br + ex.toString());
            res = 0;
        } finally {
            closeStatement(ps);
            closeConnection(connection);
            return res;
        }
    }

    public int removePermissionConnectStudent(String channelCode) throws Exception {
        Connection connection = null;
        PreparedStatement ps = null;
        StringBuilder br = new StringBuilder();
        String sql = "";
        int res = 0;
        long startTime = System.currentTimeMillis();
        try {
            connection = getConnection("dbProduct");
            sql = "update product.main_product_connect_kit set for_user = replace(for_user,'|'||?) where main_product = 'STUDENT' and status = 1";
            ps = connection.prepareStatement(sql);
            ps.setString(1, channelCode.toUpperCase());
            res = ps.executeUpdate();
            logger.info("End assignPermissionConnectStudent time " + (System.currentTimeMillis() - startTime));
        } catch (Exception ex) {
            br.setLength(0);
            br.append(loggerLabel).append(new Date()).
                    append("\nERROR assignPermissionConnectStudent: ");
            logger.error(br + ex.toString());
            res = 0;
        } finally {
            closeStatement(ps);
            closeConnection(connection);
            return res;
        }
    }
}
