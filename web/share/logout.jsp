<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/WEB-INF/config/vsa-defs.tld" prefix="vp" %>
<%@ page import="java.util.ResourceBundle"%>
<%@ page import="java.util.Locale"%>
<%@ page import="java.net.URLEncoder"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%
        ResourceBundle rb1 = null;
        Locale locale = new Locale("en", "US");
        rb1 = ResourceBundle.getBundle("cas", locale);
        request.setAttribute("logoutUrl", rb1.getString("logoutUrl") + "?service=" + URLEncoder.encode(rb1.getString("service"), "UTF-8"));
%>


<vp:logout var="netID" scope="session" logoutUrl="${fn:escapeXml(logoutUrl)}" />
<!--vp:logout var="netID" scope="session" logoutUrl="https://192.168.176.190:8443/Passport/logout?service=http://192.168.176.190:2381/Inventory_2" /-->
<!--vp:logout var="netID" scope="session" logoutUrl="https://192.168.176.190:8443/Passport/logout?service=http://192.168.176.190:7777/Inventory_2" /-->

