package com.viettel.im.download;

import com.viettel.im.client.bean.KitIntegrationBean;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DownloadKit extends Download {
    
    private String TAB = "\t";

    private String ENTER = "\r\n";

    public static int MAX_USER_RECORD_READ = 50;

    private List<KitIntegrationBean> list = new ArrayList();

    public DownloadKit(HttpServletRequest request,
            HttpServletResponse response, List list) {
        super(request, response);
        this.list = list;
    }

    public String formatData(Object data) throws Exception {
        StringBuffer dataLineBuf = new StringBuffer();
        KitIntegrationBean kitInfo = (KitIntegrationBean) data;
        dataLineBuf.append(kitInfo.getImsi());
        dataLineBuf.append(TAB);
        dataLineBuf.append(kitInfo.getIccid());
        dataLineBuf.append(TAB);
        dataLineBuf.append(kitInfo.getIsdn());
        dataLineBuf.append(ENTER);
        return dataLineBuf.toString();
    }


    public List getDatas() throws Exception {
        return this.list;
    }

    @Override
    public String getFileFooter() {
        return "";
    }

    public String getFileHeader() throws Exception {
        return "";
    }
}
