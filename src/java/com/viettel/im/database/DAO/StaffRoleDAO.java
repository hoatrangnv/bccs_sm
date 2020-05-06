/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.im.client.form.StaffRoleForm;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author trungdh3_s
 */
public class StaffRoleDAO {

    public static List<StaffRoleForm> getEmailAndIsdn(Session session, Long onwerId, Long onwerType) {
        List<StaffRoleForm> lstStaffRole = new ArrayList<StaffRoleForm>();
        try {

           System.out.println("lay danh sach gui tin nhan va email start ");
            Query query = null;
            if (onwerType == 1L) {
                String sqlShop = "select DISTINCT sr.staff_code,'role' as staff_role,sr.tel,sr.email from staff_role sr, staff s "
                        + "where upper(sr.staff_code) = upper(s.staff_code) and s.shop_id = ? and sr.staff_role in ('ROLE_FINANCE','ROLE_STOREKEEPER','ROLE_PLAN')  ";
                query = session.createSQLQuery(sqlShop);
            } else {
                String sqlStaff = "select DISTINCT sr.staff_code,'Staff' as staff_role,sr.tel,sr.email from staff_role sr, staff s "
                        + "where upper(sr.staff_code) = upper(s.staff_code) and s.staff_id = ?     ";
                query = session.createSQLQuery(sqlStaff);
            }
            query.setParameter(0, onwerId);
            List<Object[]> lstObj = new ArrayList<Object[]>();
            lstObj = query.list();
            for (Object[] obj : lstObj) {
                StaffRoleForm staffRoleForm = new StaffRoleForm();
                staffRoleForm.setStaffCode(obj[0].toString());
                staffRoleForm.setStaffRole(obj[1].toString());
                staffRoleForm.setTel(obj[2].toString());
                staffRoleForm.setEmail(obj[3].toString());
                lstStaffRole.add(staffRoleForm);
            }
            System.out.println("lay danh sach gui tin nhan va email ");
        } catch (Exception e) {
            e.printStackTrace();
            ;
        }
        return lstStaffRole;
    }
}
