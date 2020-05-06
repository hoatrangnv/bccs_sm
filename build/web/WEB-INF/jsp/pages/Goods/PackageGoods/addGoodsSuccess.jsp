<%-- 
    Document   : addGoodsSuccess
    Created on : Oct 18, 2012, 5:48:15 PM
    Author     : os_thangnh10
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib prefix="imDef" uri="imDef" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
           // request.setAttribute("packageGoodsMapId", request.getSession().getAttribute("packageGoodsMapId"));
%>
<script>
    var packageGoodsMapId = '<s:property escapeJavaScript="true"  value="#request.packageGoodsMapId"/>';    
    window.close();
    window.opener.closeAddPackageGoodsDetail(packageGoodsMapId);
</script>

