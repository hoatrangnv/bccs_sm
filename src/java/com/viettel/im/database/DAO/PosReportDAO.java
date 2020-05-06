/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.GeneralPosReportBO;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;

/**
 *
 * @author Tran Van Tung
 */
public class PosReportDAO extends BaseDAOAction {

    private static Connection mcnMain;

    public GeneralPosReportBO countSumTransVT(Date startDate, Date endDate, String cardType)
            throws Exception {
        try {
            StringBuffer sql = new StringBuffer();
            sql.append(" SELECT count(amount), sum(amount) ");
            sql.append(" FROM ");
            //sql.append(" " + table + " a ");
            sql.append(" sale_trans_post a ");
            sql.append(" , card_type_pos ct ");
            sql.append(" WHERE 1 = 1 ");
            sql.append(" AND ");
            sql.append(" CREATE_DATE >= ? ");
            sql.append(" AND ");
            sql.append(" CREATE_DATE <= ? ");
            sql.append(" AND ");
            sql.append(" to_number(a.bin) <= to_number(ct.end_bin) ");
            sql.append(" AND ");
            sql.append(" to_number(a.bin) >= to_number(ct.start_bin) ");
            sql.append(" AND ");
            sql.append(" ct.card_type = ? ");
            sql.append(" AND ");
            sql.append(" a.status = ? ");
            Query queryObject = getSession().createSQLQuery(sql.toString());

//            PreparedStatement stm = mcnMain.prepareStatement(sql.toString());
            System.out.println(" countSumTrans " + sql.toString());
            queryObject.setParameter(0, startDate);
            queryObject.setParameter(1, endDate);
            queryObject.setParameter(2, cardType);
            queryObject.setParameter(3, Constant.STATUS_ACTIVE);
            List list = queryObject.list();


            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                Object[] objs = (Object[]) iterator.next();
                Object total = (Object) objs[0];
                Object sum = (Object) objs[1];

                GeneralPosReportBO generalPosReportBO = new GeneralPosReportBO();
                if (total != null) {
                    generalPosReportBO.setTotalTrans(total.toString());
                }
                if (sum != null) {
                    generalPosReportBO.setTotalAmount(Long.valueOf(sum.toString()));
                } else {
                    generalPosReportBO.setTotalAmount(0L);
                }
                return generalPosReportBO;
            }
        } catch (Exception e) {
            throw e;
        }
        return null;
    }

    public GeneralPosReportBO countSumTransMB(Date startDate, Date endDate, String cardType)
            throws Exception {
        try {
            StringBuffer sql = new StringBuffer();
            sql.append(" SELECT count(amount), sum(amount) ");
            sql.append(" FROM ");
            //sql.append(" " + table + " a ");
            sql.append(" mb_sale_pos a ");
            sql.append(" , card_type_pos ct ");
            sql.append(" WHERE 1 = 1 ");
            sql.append(" AND ");
            sql.append(" CREATE_DATE >= ? ");
            sql.append(" AND ");
            sql.append(" CREATE_DATE <= ? ");
            sql.append(" AND ");
            sql.append(" to_number(a.bin) <= to_number(ct.end_bin) ");
            sql.append(" AND ");
            sql.append(" to_number(a.bin) >= to_number(ct.start_bin) ");
            sql.append(" AND ");
            sql.append(" ct.card_type = ? ");
            Query queryObject = getSession().createSQLQuery(sql.toString());

//            PreparedStatement stm = mcnMain.prepareStatement(sql.toString());
            System.out.println(" countSumTrans " + sql.toString());
            queryObject.setParameter(0, startDate);
            queryObject.setParameter(1, endDate);
            queryObject.setParameter(2, cardType);
            List list = queryObject.list();
//            stm.setDate(1, startDate);
//            stm.setDate(2, endDate);
//            stm.setString(3, cardType);

//            ResultSet rs = stm.executeQuery();
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                Object[] objs = (Object[]) iterator.next();
                Object total = (Object) objs[0];
                Object sum = (Object) objs[1];

                //Object objs = (Object) iterator.next();                
//            }
//            if (rs.next()) {
//                Object total = (Object) rs.getObject(1);
//                
//                Object sum = (Object) rs.getObject(2);
                GeneralPosReportBO generalPosReportBO = new GeneralPosReportBO();
                if (total != null) {
                    generalPosReportBO.setTotalTrans(total.toString());
                }
                if (sum != null) {
                    generalPosReportBO.setTotalAmount(Long.valueOf(sum.toString()));
                } else {
                    generalPosReportBO.setTotalAmount(0L);
                }
                return generalPosReportBO;
            }
        } catch (Exception e) {
            throw e;
        }
        return null;
    }

    public GeneralPosReportBO countSumExtraMBBankTrans(Date startDate, Date endDate, String cardType)
            throws Exception {
        try {
            StringBuffer sql = new StringBuffer();
            sql.append(" SELECT count(amount), sum(amount) ");
            sql.append(" FROM ");
            sql.append(" mb_sale_pos mb ");
            sql.append(" , card_type_pos ct ");
            sql.append(" WHERE 1 = 1 ");
            sql.append(" AND ");
            sql.append(" mb.CREATE_DATE >= ? ");
            sql.append(" AND ");
            sql.append(" mb.CREATE_DATE <= ? ");

            sql.append(" AND ");
            sql.append(" to_number(mb.bin) <= to_number(ct.end_bin) ");
            sql.append(" AND ");
            sql.append(" to_number(mb.bin) >= to_number(ct.start_bin) ");
            sql.append(" AND ");
            sql.append(" ct.card_type = ? ");

            sql.append(" AND ");
            sql.append(" NOT exists ( ");
            sql.append(" SELECT 1 FROM SALE_TRANS_POST pm , card_type_pos ct  ");
            sql.append(" WHERE  1 = 1  ");
            sql.append(" AND  pm.CREATE_DATE >= ? ");
            sql.append(" AND ");
            sql.append(" pm.CREATE_DATE <= ? ");
            sql.append(" AND ");

            sql.append(" to_number(pm.bin) <= to_number(ct.end_bin) ");
            sql.append(" AND ");
            sql.append(" to_number(pm.bin) >= to_number(ct.start_bin) ");
            sql.append(" AND ");
            sql.append(" ct.card_type = ? ");
            sql.append(" AND ");
            sql.append(" pm.Status = ? ");
            sql.append(" AND ");
            sql.append(" mb.reference = pm.reference_no) ");
            Query queryObject = getSession().createSQLQuery(sql.toString());

//            PreparedStatement stm = mcnMain.prepareStatement(sql.toString());
            System.out.println(" countSumExtraMBBankTrans " + sql.toString());
            System.out.println(" startDate " + startDate);
            System.out.println(" endDate " + endDate);
            System.out.println(" cardType " + cardType);
            queryObject.setParameter(0, startDate);
            queryObject.setParameter(1, endDate);
            queryObject.setParameter(2, cardType);
            queryObject.setParameter(3, startDate);
            queryObject.setParameter(4, endDate);
            queryObject.setParameter(5, cardType);
            queryObject.setParameter(6, Constant.STATUS_ACTIVE);

            List list = queryObject.list();
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                Object[] objs = (Object[]) iterator.next();
                Object total = (Object) objs[0];
                Object sum = (Object) objs[1];
                GeneralPosReportBO generalPosReportBO = new GeneralPosReportBO();
                if (total != null) {
                    generalPosReportBO.setTotalTrans(total.toString());
                }
                if (sum != null) {
                    generalPosReportBO.setTotalAmount(Long.valueOf(sum.toString()));
                } else {
                    generalPosReportBO.setTotalAmount(0L);
                }
                return generalPosReportBO;
            }



        } catch (Exception e) {
            throw e;
        }
        return null;
    }

    public GeneralPosReportBO countSumSameTrans(Date startDate, Date endDate, String cardType)
            throws Exception {
        try {
            StringBuffer sql = new StringBuffer();
            sql.append(" SELECT count(amount), sum(amount) ");
            sql.append(" FROM ");
            sql.append(" sale_trans_post pm ");
            sql.append(" , card_type_Pos ct ");
            sql.append(" WHERE 1 = 1 ");
            sql.append(" AND ");
            sql.append(" pm.CREATE_DATE >= ? ");
            sql.append(" AND ");
            sql.append(" pm.CREATE_DATE <= ? ");

            sql.append(" AND ");
            sql.append(" to_number(pm.bin) <= to_number(ct.end_bin) ");
            sql.append(" AND ");
            sql.append(" to_number(pm.bin) >= to_number(ct.start_bin) ");
            sql.append(" AND ");
            sql.append(" ct.card_type = ? ");
            sql.append(" AND ");
            sql.append(" pm.status = ? ");

            sql.append(" AND ");
            sql.append(" exists ( ");
            sql.append(" SELECT 1 FROM MB_SALE_POS mb  ");
            sql.append(" , card_type_Pos ct ");
            sql.append(" WHERE  1 = 1  ");
            sql.append(" AND  mb.create_date >= ? ");
            sql.append(" AND ");
            sql.append(" mb.create_date <= ? ");
            sql.append(" AND ");

            sql.append(" to_number(mb.bin) <= to_number(ct.end_bin) ");
            sql.append(" AND ");
            sql.append(" to_number(mb.bin) >= to_number(ct.start_bin) ");
            sql.append(" AND ");
            sql.append(" ct.card_type = ? ");

            sql.append(" AND ");
            sql.append(" mb.REFERENCE = pm.REFERENCE_NO) ");
            Query queryObject = getSession().createSQLQuery(sql.toString());
//            PreparedStatement stm = mcnMain.prepareStatement(sql.toString());
            System.out.println(" countSumSameTrans " + sql.toString());
            queryObject.setParameter(0, startDate);
            queryObject.setParameter(1, endDate);
            queryObject.setParameter(2, cardType);
            queryObject.setParameter(3, Constant.STATUS_ACTIVE);

            queryObject.setParameter(4, startDate);
            queryObject.setParameter(5, endDate);
            queryObject.setParameter(6, cardType);

            List list = queryObject.list();
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                Object[] objs = (Object[]) iterator.next();
                Object total = (Object) objs[0];
                Object sum = (Object) objs[1];

                GeneralPosReportBO generalPosReportBO = new GeneralPosReportBO();
                if (total != null) {
                    generalPosReportBO.setTotalTrans(total.toString());
                }
                if (sum != null) {
                    generalPosReportBO.setTotalAmount(Long.valueOf(sum.toString()));
                } else {
                    generalPosReportBO.setTotalAmount(0L);
                }
                return generalPosReportBO;
            }
        } catch (Exception e) {
            throw e;
        }
        return null;
    }

    public GeneralPosReportBO countSumExtraPaymentTrans(Date startDate, Date endDate, String cardType)
            throws Exception {
        try {
            StringBuffer sql = new StringBuffer();
            sql.append(" SELECT count(amount), sum(amount) ");
            sql.append(" FROM ");
            sql.append(" sale_trans_post pm ");
            sql.append(" , card_type_Pos ct ");
            sql.append(" WHERE 1 = 1 ");
            sql.append(" AND ");
            sql.append(" pm.CREATE_DATE >= ? ");
            sql.append(" AND ");
            sql.append(" pm.CREATE_DATE <= ? ");

            sql.append(" AND ");
            sql.append(" to_number(pm.bin) <= to_number(ct.end_bin) ");
            sql.append(" AND ");
            sql.append(" to_number(pm.bin) >= to_number(ct.start_bin) ");
            sql.append(" AND ");
            sql.append(" ct.card_type = ? ");
            sql.append(" AND ");
            sql.append(" pm.status = ? ");

            sql.append(" AND ");

            sql.append(" NOT exists ( ");
            sql.append(" SELECT 1 FROM MB_SALE_POS mb  ");
            sql.append(" , card_type_Pos ct ");
            sql.append(" WHERE  1 = 1  ");
            sql.append(" AND mb.CREATE_DATE >= ? ");
            sql.append(" AND ");
            sql.append(" mb.CREATE_DATE <= ? ");

            sql.append(" AND ");
            sql.append(" to_number(mb.bin) <= to_number(ct.end_bin) ");
            sql.append(" AND ");
            sql.append(" to_number(mb.bin) >= to_number(ct.start_bin) ");
            sql.append(" AND ");
            sql.append(" ct.card_type = ? ");
            sql.append(" AND ");
            sql.append(" mb.REFERENCE = pm.REFERENCE_NO) ");
            Query queryObject = getSession().createSQLQuery(sql.toString());
            queryObject.setParameter(0, startDate);
            queryObject.setParameter(1, endDate);
            queryObject.setParameter(2, cardType);
            queryObject.setParameter(3, Constant.STATUS_ACTIVE);
            queryObject.setParameter(4, startDate);
            queryObject.setParameter(5, endDate);
            queryObject.setParameter(6, cardType);

            List list = queryObject.list();
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                Object[] objs = (Object[]) iterator.next();
                Object total = (Object) objs[0];
                Object sum = (Object) objs[1];
                GeneralPosReportBO generalPosReportBO = new GeneralPosReportBO();
                if (total != null) {
                    generalPosReportBO.setTotalTrans(total.toString());
                }
                if (sum != null) {
                    generalPosReportBO.setTotalAmount(Long.valueOf(sum.toString()));
                } else {
                    generalPosReportBO.setTotalAmount(0L);
                }
                return generalPosReportBO;
            }
        } catch (Exception e) {
            mcnMain.rollback();
            throw e;
        }
        return null;
    }

    public static Connection getMcnMain() {
        return mcnMain;
    }

    public static void setMcnMain(Connection mcnMain) {
        PosReportDAO.mcnMain = mcnMain;
    }

    public static void main(String[] args) throws Exception {
        try {
            PosReportDAO posReportDAO = new PosReportDAO();
            Date startDate = new Date(109, 11, 1);
            Date endDate = new Date(110, 1, 2);
            System.out.println(startDate);
            System.out.println(endDate);
            posReportDAO.countSumTransMB(startDate, endDate, "");
            posReportDAO.countSumExtraMBBankTrans(startDate, endDate, "");
            posReportDAO.countSumExtraPaymentTrans(startDate, endDate, "");
        } catch (Exception e) {

        }
    }
}
