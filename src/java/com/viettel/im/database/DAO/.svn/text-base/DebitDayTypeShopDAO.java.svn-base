/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.common.util.StringUtils;
import com.viettel.im.database.BO.DebitDayTypeShopBO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

/**
 *
 * @author thinhph2
 */
public class DebitDayTypeShopDAO extends BaseDAOAction{

    public DebitDayTypeShopDAO() {
    }
    
    public List<DebitDayTypeShopBO> findById(Session session, Long id){
        List<DebitDayTypeShopBO> list = new ArrayList<DebitDayTypeShopBO>();
        String sql = " Select id, debit_day_type_id debitDayTypeId, shop_id shopId, status from debit_day_type_shop where id = ? ";
        if(id == null || id <= 0){
            return list;
        }
        try{
            Query query = session.createSQLQuery(sql)
                    .addScalar("id", Hibernate.LONG)
                    .addScalar("debitDayTypeId", Hibernate.LONG)
                    .addScalar("shopId", Hibernate.LONG)
                    .addScalar("status", Hibernate.LONG)
                    .setResultTransformer(Transformers.aliasToBean(DebitDayTypeShopBO.class));
            query.setParameter(0, id);
            list = query.list();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return list;
    }
    
    public boolean checkConstraints(Session session, String shopCode, Long debitDayTypeId){
        System.out.println("shopId|"+shopCode+"|debitDayTypeId|"+debitDayTypeId);
        String sql = " select * from debit_day_type_shop where shop_id = (select shop_id from shop where lower(shop_code) = ? and status = 1) and debit_day_type_id = ? and status = 1 ";
        if(!StringUtils.validString(shopCode) || debitDayTypeId == null){
            return true;
        }
        try{
            Query query = session.createSQLQuery(sql);
            query.setParameter(0, shopCode.toLowerCase());
            query.setParameter(1, debitDayTypeId);
            
            List list = query.list();
            if(!list.isEmpty()){
                return true;
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return false;
    }
}
