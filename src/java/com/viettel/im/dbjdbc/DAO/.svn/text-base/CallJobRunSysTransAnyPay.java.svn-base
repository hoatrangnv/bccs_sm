/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.dbjdbc.DAO;

import com.viettel.database.DAO.BaseDAOAction;
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
import com.viettel.im.common.util.WriteLogUtil;
import java.util.ResourceBundle;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
/**
 *
 * @author Vunt
 */
public class CallJobRunSysTransAnyPay implements Job{
    
    public CallJobRunSysTransAnyPay() {
    }

    @Override
    public void execute(JobExecutionContext context)
        throws JobExecutionException {
        try {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("config");
            String filePath = resourceBundle.getString("FILE_PATH_LOG_JOB");
            System.out.println(System.getProperty("user.dir"));

            WriteLogUtil logUtil = new WriteLogUtil(filePath, "CallJobRunSysTransAnyPay", WriteLogUtil.LOG_MODE_DAILY);
            System.out.println("------- bat dau job dong bo -------");
            logUtil.writeLog("------- Bat dau Job dong bo -------");
            //goi procedure run_syn_trans_anypay()
            
            Connection connection = JdbcConnectionFactory.getConnection();
            String strSQL = "{call run_syn_trans_anypay()}";
            CallableStatement Execute = connection.prepareCall(strSQL);
            Execute.execute();
            System.out.println("------- Ket thuc Job dong bo -------");
            logUtil.writeLog("------- ket thuc Job dong bo -------");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
