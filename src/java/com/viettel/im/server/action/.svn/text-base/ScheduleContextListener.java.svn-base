/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.server.action;

import com.viettel.im.common.util.WriteLogUtil;
import com.viettel.im.dbjdbc.DAO.CallJobRunSysTransAnyPay;
import com.viettel.im.dbjdbc.DAO.CancelRevokeCollJob;
import java.util.Date;
import java.util.ResourceBundle;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerUtils;
import org.quartz.impl.StdSchedulerFactory;

/**
 *
 * @author LamNV
 * @purpose day la listener duoc start/stop cung voi ung dung.
 * listener nay dung de lap lich cho job huy cac giao dich thu hoi hang cua CTV
 */
public final class ScheduleContextListener /*extends ProcessThreadMX*/
        implements ServletContextListener {

    public static final String RELEASE_EXPIRED_TRANS_TRIGGER = "ReleaseExpiredTransTrigger";
    public static final String RELEASE_EXPIRED_TRANS_THREAD = "ReleaseExpiredTransThread";
    public static final String RESUM_ANYPAY_TRANS_TRIGGER = "RESUMANYPAYTRANSTRIGGER";
    public static final String RESUM_ANYPAY_TRANS_THREAD = "RESUMANYPAYTRANSTHREAD";
    
    Scheduler sched = null;    
    private ServletContext context;

    
    @Override
    public void contextDestroyed(ServletContextEvent event) {
        try {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("config");
            String filePath = resourceBundle.getString("FILE_PATH_LOG_JOB");
            WriteLogUtil logUtil = new WriteLogUtil(filePath, "CancelRevokeColl", WriteLogUtil.LOG_MODE_DAILY);

            if(null != sched && sched.isStarted()){
                System.out.println("------- Bat dau tat lich chay thread -----------");
                logUtil.writeLog("------- Bat dau tat lich chay thread -----------");
                sched.shutdown(false);
                sched = null;
                System.out.println("------- Da tat xong lich chay thread -----------");
                logUtil.writeLog("------- Da tat xong lich chay thread -----------");
            }

            this.context = null;
        } catch (SchedulerException ex) {
            Logger.getLogger(ScheduleContextListener.class.getName()).log(Level.FATAL, null, ex);
        }

    }


    //This method is invoked when the Web Application
    //is ready to service requests
    @Override
    public void contextInitialized(ServletContextEvent event) {
        this.context = event.getServletContext();

        try {
            Log log = LogFactory.getLog(ScheduleContextListener.class);
            // First we must get a reference to a scheduler
            ResourceBundle resourceBundle = ResourceBundle.getBundle("config");
            String filePath = resourceBundle.getString("FILE_PATH_LOG_JOB");
            WriteLogUtil logUtil = new WriteLogUtil(filePath, "CancelRevokeColl", WriteLogUtil.LOG_MODE_DAILY);

            // Initiate a Schedule Factory
            SchedulerFactory schedulerFactory = new StdSchedulerFactory();
            // Retrieve a scheduler from schedule factory
            sched = schedulerFactory.getScheduler();

            String[] triggerGroups;
            String[] triggers;

            triggerGroups = sched.getTriggerGroupNames();
            if (triggerGroups.length > 0) {
                for (int i = 0; i < triggerGroups.length; i++) {
                    triggers = sched.getTriggerNames(triggerGroups[i]);
                    for (int j = 0; j < triggers.length; j++) {
                        Trigger tg = sched.getTrigger(triggers[j], triggerGroups[i]);

                        if (tg instanceof SimpleTrigger && tg.getName().equals(RELEASE_EXPIRED_TRANS_TRIGGER)) {
                            // reschedule the job
                            sched.rescheduleJob(triggers[j], triggerGroups[i], tg);
                            // unschedule the job
                            //sched.unscheduleJob(triggersInGroup[j], triggerGroups[i]);
                        }
                    }
                }
            } else {
                //cua LamNV
                Trigger trigger = TriggerUtils.makeHourlyTrigger();//.makeDailyTrigger(0, 0); //bat dau vao 0h hang ngay
                trigger.setStartTime(TriggerUtils.getEvenHourDate(new Date()));
                trigger.setName(RELEASE_EXPIRED_TRANS_TRIGGER);
                trigger.setVolatility(true);

                JobDetail job = new JobDetail(RELEASE_EXPIRED_TRANS_THREAD, null, CancelRevokeCollJob.class);
                job.setRequestsRecovery(true);
                job.setVolatility(true);
                sched.scheduleJob(job, trigger);

                //VuNT viet dong bo AnyPay
                Trigger triggerAnyPay = TriggerUtils.makeDailyTrigger(3, 0);//.makeDailyTrigger(0, 0); //bat dau vao 0h hang ngay
                triggerAnyPay.setStartTime(TriggerUtils.getEvenHourDate(new Date()));
                triggerAnyPay.setName(RESUM_ANYPAY_TRANS_TRIGGER);
                triggerAnyPay.setVolatility(true);

                JobDetail jobAnyPay = new JobDetail(RESUM_ANYPAY_TRANS_THREAD, null, CallJobRunSysTransAnyPay.class);
                jobAnyPay.setRequestsRecovery(true);
                jobAnyPay.setVolatility(true);
                
                sched.scheduleJob(jobAnyPay, triggerAnyPay);


            }

            logUtil.writeLog("------- Bat dau bat Scheduler cho lap lich JOB ----------");
            System.out.println("------- Bat dau bat Scheduler cho lap lich JOB ----------");
            // start the sched
            sched.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
