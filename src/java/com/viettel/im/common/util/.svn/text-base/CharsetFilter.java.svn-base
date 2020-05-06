/*
 * CharsetFilter.java
 *
 * Created on August 6, 2007, 10:49 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.viettel.im.common.util;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Vu Thi Thu Huong
 */
public class CharsetFilter implements Filter {

    private FilterConfig config = null;
    private String defaultEncode = "UTF-8";
    private static int firstRequest = 0;
    public void init(FilterConfig config) throws ServletException {
        this.config = config;
        if (config.getInitParameter("Charset") != null) {
            defaultEncode = config.getInitParameter("Charset");
            String s = null;
        }
    }

    public void destroy() {
        this.config = null;
    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        ServletRequest srequest = request;
        srequest.setCharacterEncoding(defaultEncode);
        HttpServletRequest hreq = (HttpServletRequest) request;
        HttpServletResponse hres = (HttpServletResponse) response;
        HttpSession session = hreq.getSession();
        if (session.isNew()) {
            if (firstRequest == 0) {
                firstRequest++;
            } 
            else
            {
                String LoginPageURL = hreq.getContextPath() + "/login/login.jsp";
                hres.sendRedirect("index.do");
                return;
            }
        }
        chain.doFilter(request, response);
    }
}
