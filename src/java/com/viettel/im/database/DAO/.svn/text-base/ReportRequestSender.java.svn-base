/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.common.ObjectClientChannel;
import com.viettel.common.ViettelMsg;
import com.viettel.im.common.ReportConstant;
import java.util.ResourceBundle;

/**
 *
 * @Author      :   Tuanpv
 * @CreateDate  :   24/02/2010
 * @Purpose     :   Send request tạo xuất báo cáo sang Report Server và nhận về link download file báo cáo
 */
public class ReportRequestSender
{
    //Biến dùng để tạo kết nối tới ReportServer và gửi đi yêu cầu xuất báo cáo
    private static ObjectClientChannel client;

    /**
     * @Author          :   TuanPV
     * @CreateDate      :   24/02/2010
     * @Purpose         :   Gửi yêu cầu xuất báo cáo sang ReportServer
     * @param request   :   Đối tượng lưu các điều kiện xuất báo cáo
     * @return          :   Link download file báo cáo xuất ra     
     */
    public static ViettelMsg sendRequest(ViettelMsg request) throws Exception
    {
        try
        {

            if(client == null || (!client.isConnected()))
            {
                ResourceBundle resource = ResourceBundle.getBundle("report_server");
                String serverIp = resource.getString("ip");
                int serverPort = Integer.parseInt(resource.getString("port"));
                String userName = resource.getString("username");
                String password = resource.getString("password");                
                client = new ObjectClientChannel(serverIp, serverPort, userName, password, true);
                client.setReceiverTimeout(ReportConstant.REPORT_MESSAGE_TIME_OUT);
                client.connect();
            }

            request.set("locale", com.viettel.database.DAO.BaseDAOAction.locale);

            /*2012-12-24*/
            request.set("VERSION","2");            

            ViettelMsg response = client.send(request);
            return response;
        }catch(Exception ex)
        {
            ex.printStackTrace();
            throw ex;
        }        
    }
}
