package com.viettel.im.client.form;

import com.viettel.im.common.Constant;
import java.io.File;
import org.apache.struts.action.ActionForm;

/**
 *
 * author   : Doan Thanh 8
 * date     : 01/09/2010
 * desc     : form tao lenh xuat tu file
 *
 */
public class CreateExpCmdFromFileForm extends ActionForm {
    private String serverFileName; //ten file tren server trong truogn hop upload
    private String clientFileName; //ten file tren client trong truogn hop upload

    public String getClientFileName() {
        return clientFileName;
    }

    public void setClientFileName(String clientFileName) {
        this.clientFileName = clientFileName;
    }

    public String getServerFileName() {
        return serverFileName;
    }

    public void setServerFileName(String serverFileName) {
        this.serverFileName = serverFileName;
    }

    
}
