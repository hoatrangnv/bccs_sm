<%-- 
    Document   : cableNo
    Created on : Jun 12, 2011, 5:34:57 AM
    Author     : MrSun
--%>



<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<%@taglib uri="VTdisplaytaglib" prefix="display"%>

<%
    if (request.getAttribute("listCableNo") == null) {
        request.setAttribute("listCableNo", request.getSession().getAttribute("listCableNo"));
    }
    request.setAttribute("contextPath", request.getContextPath());
%>


 <jsp:include page="listCableNoManagement.jsp"/>