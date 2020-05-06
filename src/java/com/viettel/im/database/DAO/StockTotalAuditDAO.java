/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.GenResult;
import com.viettel.im.database.BO.StockTotal;
import com.viettel.im.database.BO.StockTotalAudit;
import com.viettel.im.database.BO.StockTotalId;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.poi.hpbf.model.qcbits.QCPLCBit.Type0;
import org.hibernate.Session;

/**
 *
 * @author LamNV5_S
 */
public class StockTotalAuditDAO {

    public static enum SourceType {

        SALE_TRANS,
        STOCK_TRANS,
        CMD_TRANS,
        STICK_TRANS,
        OTHER
    };

    public static GenResult changeStockTotal(Session session, Long ownerId, Long ownerType, Long stockModelId, Long stateId,
            Long qty, Long qtyIssue, Long qtyHang, Long userId, Long reasonId, Long transId, String transCode, String transType, SourceType sourceType, Long maxDebit) throws Exception {
        return changeStockTotal(session.connection(), ownerId, ownerType, stockModelId, stateId,
                qty, qtyIssue, qtyHang, userId, reasonId, transId, transCode, transType, sourceType, maxDebit);
    }

    public static GenResult changeStockTotal(Session session, Long ownerId, Long ownerType, Long stockModelId, Long stateId,
            Long qty, Long qtyIssue, Long qtyHang, Long userId, Long reasonId, Long transId, String transCode, String transType, SourceType sourceType) throws Exception {
        return changeStockTotal(session.connection(), ownerId, ownerType, stockModelId, stateId,
                qty, qtyIssue, qtyHang, userId, reasonId, transId, transCode, transType, sourceType, null);
    }

    public static GenResult changeStockTotal(Connection conn, Long ownerId, Long ownerType, Long stockModelId, Long stateId,
            Long qty, Long qtyIssue, Long qtyHang, Long userId, Long reasonId, Long transId, String transCode, String transType, SourceType sourceType, Long maxDebit) throws Exception {
        String params = MessageFormat.format("ownerId:{0}, ownerType:{1}, stateId:{2}, stockModelId:{3}",
                ownerId, ownerType, stateId, stockModelId);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        System.out.println("Tracelog start changeStockTotal ownerId " + ownerId + " stockModelId " + stockModelId
                + " stateId " + stateId + " qty " + qty + " qtyIssue " + qtyIssue + " qtyHang " + qtyHang + " userId " + userId
                + " transId " + transId + " maxDebit " + maxDebit
                + " time " + sdf.format(new Date()));
        boolean upResult = false;
        qty = getSafeQuantity(qty);
        qtyIssue = getSafeQuantity(qtyIssue);
        qtyHang = getSafeQuantity(qtyHang);

        //Ghi log audit
        StockTotalAudit sta = new StockTotalAudit();
        sta.setOwnerId(ownerId);
        sta.setOwnerType(ownerType);
        sta.setUserId(userId);
        sta.setReasonId(reasonId);
        sta.setTransId(transId);
        sta.setTransCode(transCode);
        sta.setTransType(Long.valueOf(transType));
        sta.setSourceType(longValue(sourceType));
        sta.setStockModelId(stockModelId);
        sta.setStateId(stateId);

        sta.setQty(qty);
        sta.setQtyIssue(qtyIssue);
        sta.setQtyHang(qtyHang);
        GenResult result = new GenResult();
        StockTotal stockTotal = null;
        try {
            stockTotal = findStockTotal(conn, ownerId, ownerType, stockModelId, stateId);
        } catch (Exception ex) {
            result.setDescription("Kho dang duoc xu ly trong giao dich khac, vui long thu lai sau");
            System.out.println("Tracelog Kho dang duoc xu ly trong giao dich khac " + ownerId + " transid " + transId + " stockModelId " + stockModelId
                    + " error " + ex.toString());
            return result;
        }

        if (stockTotal == null && (qty < 0L || qtyIssue < 0L || qtyHang < 0L)) {
            result.setDescription("Khong tim thay kho voi thong tin (" + params + ")");
            System.out.println("Tracelog Khong tim thay kho hang " + ownerId + " transid " + transId + " stockModelId " + stockModelId);
            return result;
        }

        if (stockTotal == null) {
//            Huynq13 start add to check really not exist stockTotal, before create new stock_total
            System.out.println("Tracelog check really not exist stockTotal, before create new stock_total " + ownerId + " transid " + transId
                    + " stockModelId " + stockModelId);
            StockTotal stockTotal2 = null;
            try {
                stockTotal2 = findStockTotalNoKip(conn, ownerId, ownerType, stockModelId, stateId);
                if (stockTotal2 != null) {
                    result.setDescription("Kho dang duoc xu ly trong giao dich khac");
                    System.out.println("Tracelog kho dang duoc xu ly trong giao dich khac " + ownerId + " transid " + transId
                            + " stockModelId " + stockModelId);
                    return result;
                }
            } catch (Exception ex) {
                result.setDescription("Kho dang duoc xu ly trong giao dich khac, vui long thu lai sau");
                System.out.println("Tracelog kho dang duoc xu ly trong giao dich khac " + ownerId + " transid " + transId
                        + " stockModelId " + stockModelId + " exception " + ex.toString());
                return result;
            }
//            Huynq13 start end to check really not exist stockTotal            
            StockTotalId stockTotalId = new StockTotalId();
            stockTotalId.setOwnerType(ownerType);
            stockTotalId.setOwnerId(ownerId);
            stockTotalId.setStateId(stateId);
            stockTotalId.setStockModelId(stockModelId);

            stockTotal = new StockTotal();
            stockTotal.setId(stockTotalId);
            stockTotal.setModifiedDate(new Date());
            stockTotal.setQuantity(qty);
            stockTotal.setQuantityIssue(qtyIssue);
            stockTotal.setQuantityHang(qtyHang);
            //SO luong phai boc tham mac dinh bang 0
            stockTotal.setQuantityDial(0L);
            stockTotal.setStatus(Constant.STATUS_ACTIVE);
            //TuanNV added 120622
            stockTotal.setLimitQuantity(qty);
            //End of TuanNV
            upResult = insertStockTotal(conn, stockTotal);
            if (!upResult) {
                result.setDescription("khong the tao moi stock_total");
                System.out.println("Tracelog khong the tao moi stock_total " + ownerId + " transid " + transId
                        + " stockModelId " + stockModelId);
                return result;
            }

            sta.setQtyBf(0L);
            sta.setQtyIssueBf(0L);
            sta.setQtyHangBf(0L);
        } else {
            stockTotal.setQuantity(getSafeQuantity(stockTotal.getQuantity()));
            stockTotal.setQuantityIssue(getSafeQuantity(stockTotal.getQuantityIssue()));
            stockTotal.setQuantityHang(getSafeQuantity(stockTotal.getQuantityHang()));

            if (stockTotal.getQuantity() + qty < 0L) {
                result.setDescription("So luong hang (quantity) trong kho khong du");
                System.out.println("Tracelog so luong hang (quantity) trong kho khong du " + ownerId + " transid " + transId
                        + " stockModelId " + stockModelId);
                return result;
            }
            if (stockTotal.getQuantityIssue() + qtyIssue < 0) {
                result.setDescription("So luong hang dap ung (quantity issue) trong kho khong đu");
                System.out.println("Tracelog so luong dap ung (quantity issue) trong kho khong đu " + ownerId + " transid " + transId
                        + " stockModelId " + stockModelId);
                return result;
            }

            sta.setQtyBf(stockTotal.getQuantity());
            sta.setQtyIssueBf(stockTotal.getQuantityIssue());
            sta.setQtyHangBf(stockTotal.getQuantityHang());
            //Cap nhat lai StockTotal
            stockTotal.setQuantity(stockTotal.getQuantity() + qty);
            stockTotal.setQuantityIssue(stockTotal.getQuantityIssue() + qtyIssue);
            stockTotal.setQuantityHang(stockTotal.getQuantityHang() + qtyHang);
            upResult = updateStockTotal(conn, stockTotal);
            if (!upResult) {
                result.setDescription("Khong cap nhat duoc so luong hang hoa");
                System.out.println("Tracelog khong cap nhat duoc so luong hang hoa " + ownerId + " transid " + transId
                        + " stockModelId " + stockModelId);
                return result;
            } else {
                System.out.println("Tracelog cap nhat hang hoa thanh cong " + ownerId + " transid " + transId
                        + " stockModelId " + stockModelId);
            }
        }

        //Ghi log audit sau khi giam tru so luong
        sta.setQtyAf(stockTotal.getQuantity());
        sta.setQtyIssueAf(stockTotal.getQuantityIssue());
        sta.setQtyHangAf(stockTotal.getQuantityHang());
        upResult = saveStockTotalAudit(conn, sta);
        if (!upResult) {
            result.setDescription("Khong cap nhat duoc lich su tac dong kho");
            System.out.println("Tracelog khong cap nhat duoc lich su tac dong kho " + ownerId + " transid " + transId
                    + " stockModelId " + stockModelId);
            return result;
        }

        result.setSuccess(true);
        System.out.println("Tracelog success " + ownerId + " transid " + transId
                + " stockModelId " + stockModelId);
        return result;

    }

    private static boolean saveStockTotalAudit(Connection conn, StockTotalAudit stockTotalAudit) throws Exception {
        PreparedStatement stmt = null;

        try {
            String query = "INSERT INTO sm.stock_total_audit(owner_id, owner_type, user_Id, reason_id, trans_id, trans_code, trans_type, source_type, "
                    + "  qty, qty_issue, qty_hang, qty_bf, qty_issue_bf, qty_hang_bf, qty_af, qty_issue_af, qty_hang_af, create_date, stock_model_id, state_id, syn_status,status) "
                    + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, sysdate, ?, ?, 0,?) ";
            stmt = conn.prepareStatement(query);
            int index = 1;
            setLong(stmt, index++, stockTotalAudit.getOwnerId());
            setLong(stmt, index++, stockTotalAudit.getOwnerType());
            setLong(stmt, index++, stockTotalAudit.getUserId());
            setLong(stmt, index++, stockTotalAudit.getReasonId());
            setLong(stmt, index++, stockTotalAudit.getTransId());
            stmt.setString(index++, stockTotalAudit.getTransCode());
            setLong(stmt, index++, stockTotalAudit.getTransType());
            setLong(stmt, index++, stockTotalAudit.getSourceType());
            setLong(stmt, index++, stockTotalAudit.getQty());
            setLong(stmt, index++, stockTotalAudit.getQtyIssue());
            setLong(stmt, index++, stockTotalAudit.getQtyHang());
            setLong(stmt, index++, stockTotalAudit.getQtyBf());
            setLong(stmt, index++, stockTotalAudit.getQtyIssueBf());
            setLong(stmt, index++, stockTotalAudit.getQtyHangBf());
            setLong(stmt, index++, stockTotalAudit.getQtyAf());
            setLong(stmt, index++, stockTotalAudit.getQtyIssueAf());
            setLong(stmt, index++, stockTotalAudit.getQtyHangAf());
            setLong(stmt, index++, stockTotalAudit.getStockModelId());
            setLong(stmt, index++, stockTotalAudit.getStateId());
            setLong(stmt, index++, Constant.STATUS_ACTIVE);

            int result = stmt.executeUpdate();
            if (result == 1) {
                return true;
            }

        } catch (Exception re) {
            throw re;
        }

        return false;
    }

    private static boolean insertStockTotal(Connection conn, StockTotal stockTotal) throws Exception {
        PreparedStatement stmt = null;
        try {
            String query = "INSERT INTO sm.stock_total(owner_id, owner_type, stock_model_id, state_id, quantity, quantity_issue, quantity_hang, modified_date) "
                    + " VALUES (?, ?, ?, ?,?,?,?,sysdate) ";
            stmt = conn.prepareStatement(query);
            stmt.setLong(1, stockTotal.getId().getOwnerId());
            stmt.setLong(2, stockTotal.getId().getOwnerType());
            stmt.setLong(3, stockTotal.getId().getStockModelId());
            stmt.setLong(4, stockTotal.getId().getStateId());
            stmt.setLong(5, stockTotal.getQuantity());
            stmt.setLong(6, stockTotal.getQuantityIssue());
            BaseDAO.setLong(stmt, 7, stockTotal.getQuantityHang());


            int result = stmt.executeUpdate();
            if (result == 1) {
                return true;
            }

        } catch (Exception re) {
            throw re;
        }

        return false;
    }

    private static boolean updateStockTotal(Connection conn, StockTotal stockTotal) {
        PreparedStatement stmt = null;
        try {
            String query = "update sm.stock_total set quantity = ?, quantity_issue = ?, quantity_hang = ?, modified_date = sysdate "
                    + " where owner_id = ? and owner_type = ? and stock_model_id = ? and state_id = ? ";
            stmt = conn.prepareStatement(query);
            stmt.setLong(1, stockTotal.getQuantity());
            stmt.setLong(2, stockTotal.getQuantityIssue());
            stmt.setLong(3, stockTotal.getQuantityHang());
            stmt.setLong(4, stockTotal.getId().getOwnerId());
            stmt.setLong(5, stockTotal.getId().getOwnerType());
            stmt.setLong(6, stockTotal.getId().getStockModelId());
            stmt.setLong(7, stockTotal.getId().getStateId());

            int result = stmt.executeUpdate();
            if (result == 1) {
                return true;
            }

        } catch (Exception re) {
            System.out.println("tracelog exception updateStockTotal " + stockTotal.getId());
            re.printStackTrace();
            return false;
        }

        return false;
    }

    private static StockTotal findStockTotal(Connection conn, Long ownerId, Long ownerType, Long stockModelId, Long stateId) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        StockTotal stockTotal = null;
        try {
            String query = "SELECT * FROM sm.stock_total where owner_Id = ? "
                                        + " and owner_type = ? and stock_model_id = ? and state_id = ? FOR UPDATE SKIP LOCKED ";
            stmt = conn.prepareStatement(query);
            stmt.setLong(1, ownerId);
            stmt.setLong(2, ownerType);
            stmt.setLong(3, stockModelId);
            stmt.setLong(4, stateId);

            rs = stmt.executeQuery();

            if (rs.next()) {
                stockTotal = new StockTotal();
                StockTotalId id = new StockTotalId();
                id.setOwnerId(ownerId);
                id.setOwnerType(ownerType);
                id.setStateId(stateId);
                id.setStockModelId(stockModelId);

                stockTotal.setId(id);
                stockTotal.setQuantity(rs.getLong("quantity"));
                stockTotal.setQuantityIssue(rs.getLong("quantity_issue"));
                stockTotal.setQuantityHang(rs.getLong("quantity_hang"));
                stockTotal.setStatus(rs.getLong("status"));
            }

        } catch (Exception re) {
            throw re;
        }

        return stockTotal;
    }

//    Huynq13 add to check really exist StockTotal record
    private static StockTotal findStockTotalNoKip(Connection conn, Long ownerId, Long ownerType, Long stockModelId, Long stateId) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        StockTotal stockTotal = null;
        try {
            String query = "SELECT * FROM sm.stock_total where owner_Id = ? "
                    + " and owner_type = ? and stock_model_id = ? and state_id = ? ";
            stmt = conn.prepareStatement(query);
            stmt.setLong(1, ownerId);
            stmt.setLong(2, ownerType);
            stmt.setLong(3, stockModelId);
            stmt.setLong(4, stateId);

            rs = stmt.executeQuery();

            if (rs.next()) {
                stockTotal = new StockTotal();
                StockTotalId id = new StockTotalId();
                id.setOwnerId(ownerId);
                id.setOwnerType(ownerType);
                id.setStateId(stateId);
                id.setStockModelId(stockModelId);

                stockTotal.setId(id);
                stockTotal.setQuantity(rs.getLong("quantity"));
                stockTotal.setQuantityIssue(rs.getLong("quantity_issue"));
                stockTotal.setQuantityHang(rs.getLong("quantity_hang"));
                stockTotal.setStatus(rs.getLong("status"));
            }

        } catch (Exception re) {
            throw re;
        }

        return stockTotal;
    }

    private static void setLong(PreparedStatement stmt, int index, Long value) throws Exception {

        if (value == null) {
            stmt.setNull(index, java.sql.Types.NUMERIC);
        } else {
            stmt.setLong(index, value);
        }

    }

    private static Long longValue(SourceType sourceType) {
        switch (sourceType) {
            case SALE_TRANS:
                return 1L;
            case STOCK_TRANS:
                return 2L;
            case CMD_TRANS:
                return 3L;
            case STICK_TRANS:
                return 4L;
            case OTHER:
                return 5L;
        }
        return 6L;
    }

    private static Long getSafeQuantity(Long value) {
        if (value == null) {
            return 0L;
        }
        return value;
    }
}
