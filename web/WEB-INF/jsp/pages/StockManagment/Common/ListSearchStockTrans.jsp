<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<%--
    Document   : ExportStockToUnderlyingNote
    Created on : Feb 17, 2009, 10:51:45 AM
    Author     : ThanhNC1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib prefix="imDef" uri="imDef" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%
            request.setAttribute("contextPath", request.getContextPath());
            // request.setAttribute("lstSearchStockTrans", request.getSession().getAttribute("lstSearchStockTrans"));
%>
<div style="width:100%">
<tags:imPanel title="">
    <div align="center" style="width:100%">
            <tags:displayResult id="searchMsgClient" property="searchMsg"/>
            <br/>
        </div>
        <div align="center" style="width:100%">
            <tags:submit id="searchButton" confirm="false" formId="exportStockForm" showLoadingText="true" targets="searchArea" value="MSG.GOD.009" preAction="StockTransManagmentAction!searchTrans.do"
                         cssStyle="width:120px;"
                         />
            <tags:submit id="exportExcel" confirm="false" formId="exportStockForm" showLoadingText="true" targets="searchArea" value="Excel..." preAction="StockTransManagmentAction!expStockTransToExcel.do"
                         cssStyle="width:120px;"
                         />
        </div>
        <div align="center" style="width:100%">
        <s:if test="exportStockForm.exportUrl !=null && exportStockForm.exportUrl!=''">
                <script>
                    window.open('${contextPath}<s:property escapeJavaScript="true"  value="exportStockForm.exportUrl"/>','','toolbar=yes,scrollbars=yes');
                </script>
                <div><a href='${contextPath}<s:property escapeJavaScript="true"  value="exportStockForm.exportUrl"/>' ><tags:label key="MSG.download2.file.here"/></a></div>

            </s:if>
        </div>
            </tags:imPanel>

    <tags:imPanel title="MSG.list.trans">        
        

        <div style="width:100%; height:350px; overflow:auto;">
        
        <display:table id="trans" name="lstSearchStockTrans" class="dataTable" pagesize="500" targets="searchArea" requestURI="StockTransManagmentAction!searchTrans.do" cellpadding="0" cellspacing="0">
            <display:column  title="${fn:escapeXml(imDef:imGetText('MSG.GOD.073'))}" sortable="false" headerClass="tct" style="width:20px;text-align:center">
                <s:property escapeJavaScript="true"  value="%{#attr.trans_rowNum}"/>
            </display:column>
            <%--display:column title="Loại giao dịch" style="width:80px"--%>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.233'))}" style="width:80px">
                <s:if test="#attr.trans.stockTransType==1 && #attr.trans.actionType==1">
                    <s:property escapeJavaScript="true"  value="getText('MSG.GOD.234')"/>
                    <!--                    Lệnh xuất-->
                </s:if>
                <s:if test="#attr.trans.stockTransType==1 && #attr.trans.actionType==2">
                    <s:property escapeJavaScript="true"  value="getText('MSG.GOD.235')"/>
                    <!--                    Phiếu xuất-->
                </s:if>
                <s:if test="#attr.trans.stockTransType==2 && #attr.trans.actionType==1">
                    <s:property escapeJavaScript="true"  value="getText('MSG.GOD.236')"/>
                    <!--                    Lệnh nhập-->
                </s:if>
                <s:if test="#attr.trans.stockTransType==2 && #attr.trans.actionType==2">
                    <s:property escapeJavaScript="true"  value="getText('MSG.GOD.237')"/>
                    <!--                    Phiếu phập-->
                </s:if>
                <s:if test="#attr.trans.stockTransType==3">
                    <s:property escapeJavaScript="true"  value="getText('MSG.GOD.238')"/>
                    <!--                    Phiếu thu hồi-->
                </s:if>
            </display:column>
            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.183'))}" property="id.actionCode"/>

            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.206'))}" property="createDatetime" format="{0,date,dd/MM/yyyy}"  sortable="false" headerClass="tct"/>
            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.184'))}" property="fromOwnerName" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.239'))}" property="userCreate" sortable="false" headerClass="tct"/>

            <%--display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.013'))}" property="statusName" sortable="false" headerClass="tct"/--%>

            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.013'))}" property="statusName" sortable="false" headerClass="tct"/>

            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.185'))}" property="toOwnerName" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.240'))}" property="reasonName" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.031'))}" property="note" style="width:200px" sortable="false" headerClass="tct"/>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.070'))}" sortable="false" headerClass="tct" style="width:50px">
                <div align="center">
                    <s:url action="StockTransManagmentAction!viewStockTransDetail" id="URL" encode="true" escapeAmp="false">
                        <s:param name="actionId" value="#attr.trans.id.actionId"/>
                    </s:url>
                    <sx:a targets="detailArea" href="%{#URL}" executeScripts="true" parseContent="true" errorNotifyTopics="errorAction">
                        <s:property escapeJavaScript="true"  value="getText('MSG.GOD.070')"/>
                        <!--                        Chi tiết-->
                    </sx:a>
                </div>
            </display:column>
            <display:footer> <tr> <td colspan="12" style="color:green">
                        <s:property escapeJavaScript="true"  value="getText('MSG.GOD.241')"/> : <s:property escapeJavaScript="true"  value="%{#request.lstSearchStockTrans.size()}" /></td> <tr> </display:footer>
                </display:table>
                
                </div>
        
            </tags:imPanel>

    <br/>

    <s:if test="#request.lstSearchStockTrans != null && #request.lstSearchStockTrans.size()>0">
        <div style="width:100%">
            <sx:div id="detailArea">
                <jsp:include page="TransDetail.jsp"/>

            </sx:div>
        </div>
    </s:if>
</div>
