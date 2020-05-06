package com.viettel.im.download;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class Download {


    public boolean useHeader = true;


    public boolean useFooter = false;


    public OutputStreamWriter out = null;


    protected HttpServletRequest request;


    protected HttpServletResponse response;


    public Download(HttpServletRequest request,
            HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    
    public void writeData() {
        if ((request == null) || (response == null)) {
            return;
        }
        try {
                    char[] buf = null;

                    /* Get datas. */
                    List datas = getDatas();

                    if (useHeader) {
                        String header = getFileHeader();
                        buf = header.toCharArray();
                        if (buf != null && buf.length >= 0) {
                            out.write(buf);
                        }
                    }
                    
                    /* write data. */
                    if ((datas != null) && (datas.size() != 0)) {

                        for (int i = 0; i < datas.size(); i++) {
                            Object data = datas.get(i);
                            String line = formatData(data);
                            buf = line.toCharArray();
                            if (buf != null && buf.length >= 0) {
                                    out.write(buf);
                            }
                         }
                        out.flush();
                    }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public abstract List getDatas() throws Exception;


    public abstract String formatData(Object data) throws Exception;


    public abstract String getFileHeader() throws Exception;


    public abstract String getFileFooter();

}
