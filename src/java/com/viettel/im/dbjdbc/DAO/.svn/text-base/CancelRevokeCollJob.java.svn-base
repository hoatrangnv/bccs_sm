/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.dbjdbc.DAO;

import com.viettel.im.common.util.WriteLogUtil;
import java.util.ResourceBundle;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author LamNV
 */
public class CancelRevokeCollJob implements Job {

    /**
     * <p>
     * Empty constructor for job initilization
     * </p>
     * <p>
     * Quartz requires a public empty constructor so that the
     * scheduler can instantiate the class whenever it needs.
     * </p>
     */
    public CancelRevokeCollJob() {
    }

    @Override
    public void execute(JobExecutionContext context)
        throws JobExecutionException {
        try {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("config");
            String filePath = resourceBundle.getString("FILE_PATH_LOG_JOB");
            System.out.println(System.getProperty("user.dir"));
            
            WriteLogUtil logUtil = new WriteLogUtil(filePath, "CancelRevokeColl", WriteLogUtil.LOG_MODE_DAILY);
            System.out.println("------- Bat dau Job huy giao dich qua han -------");
            logUtil.writeLog("------- Bat dau Job huy giao dich qua han -------");            
            CancelRevokeCollAction crcAction = new CancelRevokeCollAction();
            crcAction.releaseAllPendingTrans();
            System.out.println("------- Ket thuc Job huy giao dich qua han -------");
            logUtil.writeLog("------- Ket thuc Job huy giao dich qua han -------");            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
