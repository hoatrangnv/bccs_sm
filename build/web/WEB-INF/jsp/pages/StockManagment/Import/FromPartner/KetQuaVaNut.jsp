<%-- 
    Document   : KetQuaVaNut
    Created on : Jun 27, 2014, 12:11:32 AM
    Author     : thuannx1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="display" uri="VTdisplaytaglib" %>
<%@taglib prefix="imDef" uri="imDef" %>
<%@page import="com.guhesan.querycrypt.QueryCrypt" %>

<sx:div id="listImpCmdToPartner">
    <jsp:include page="expImpNoteToPartner.jsp"/>
</sx:div>
<div align="center" id="notePrintPath">
    <jsp:include page="notePrintPath.jsp"/>
</div>
