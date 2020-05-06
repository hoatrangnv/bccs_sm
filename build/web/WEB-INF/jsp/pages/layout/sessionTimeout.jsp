<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ page import="java.util.ResourceBundle"%>
<%@ page import="java.util.Locale"%>
<%@ page import="java.net.URLEncoder"%>
<%@ taglib uri="/WEB-INF/config/vsa-defs.tld" prefix="vp" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
The taglib directive below imports the JSTL library. If you uncomment it,
you must also add the JSTL library to the project. The Add Library... action
on Libraries node in Projects view can be used to add the JSTL 1.1 library.
--%>
<%--
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
--%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%
        ResourceBundle rb1 = null;
        Locale locale = new Locale("en","US");
        rb1 = ResourceBundle.getBundle("cas",locale);
        request.setAttribute("logoutUrl", rb1.getString("logoutUrl") + "?service=" + URLEncoder.encode(rb1.getString("service"), "UTF-8"));
%>
<script type="text/javascript">
    window.location.href = "${fn:escapeXml(logoutUrl)}";
</script>