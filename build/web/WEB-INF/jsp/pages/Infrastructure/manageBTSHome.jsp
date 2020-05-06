<%-- 
    Document   : manageBTSHome
    Created on : Jun 22, 2016, 2:04:44 PM
    Author     : mov_itbl_dinhdc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>

<s:if test="#session.toEditBTS == 0">
    <div id ="searchBTSDiv">
        <jsp:include page="searchBTS.jsp"></jsp:include>
    </div>
</s:if>
<s:if test="#session.toEditBTS == 1">
    <div id ="addNewBTSDiv">
        <jsp:include page="addNewBTS.jsp"></jsp:include>
    </div>
</s:if>
<s:if test="#session.toEditBTS == 2">
    <div id ="editBTSDiv">
        <jsp:include page="editBTS.jsp"></jsp:include>
    </div>
</s:if>
<s:if test="#session.toEditBTS == 3">
    <div id ="assignAreaBTSDiv">
        <jsp:include page="assignAreaBTS.jsp"></jsp:include>
    </div>
</s:if>
<br/>
<div id="btsList">
    <tags:imPanel title="BTS.BTSPanel.listBTS">
        <jsp:include page="listBTS.jsp"></jsp:include>
    </tags:imPanel>
</div>

<div id ="addBTSByFileDiv">
    <jsp:include page="addBTSByFile.jsp"></jsp:include>
</div>
