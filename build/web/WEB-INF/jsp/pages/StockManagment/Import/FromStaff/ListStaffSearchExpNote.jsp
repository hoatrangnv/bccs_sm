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
<%request.setAttribute("contextPath", request.getContextPath());%>

<s:if test="#request.lstSearchStockTrans != null && #request.lstSearchStockTrans.size()>0">
     <tags:displayResult id="resultCreateExpCmdClient" property="resultCreateExp" type="key"/>
     <tags:displayResultList property="lstError" type="key"/>
     <s:token/>
    <display:table id="trans" name="lstSearchStockTrans" class="dataTable" requestURI="javascript: void(0)" cellpadding="1" cellspacing="1">
        <display:column  title="${fn:escapeXml(imDef:imGetText('MSG.GOD.073'))}" sortable="false" headerClass="tct">
            <s:property escapeJavaScript="true"  value="%{#attr.trans_rowNum}"/>
        </display:column>
        <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MSG.GOD.205'))}" property="id.actionCode"/>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.206'))}" property="createDatetime" format="{0,date,dd/MM/yyyy}"  sortable="false" headerClass="tct"/>
        <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MSG.GOD.184'))}" property="fromOwnerName" sortable="false" headerClass="tct"/>
        <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MSG.GOD.185'))}" property="toOwnerName" sortable="false" headerClass="tct"/>
        <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MSG.GOD.158'))}" property="reasonName" sortable="false" headerClass="tct"/>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.013'))}" sortable="false" headerClass="tct">
            <s:if test="#attr.trans.stockTransStatus ==3">
                    ${fn:escapeXml(imDef:imGetText('MSG.GOD.193'))}
<!--                    Đang chờ nhập-->
                </s:if>
                <s:if test="#attr.trans.stockTransStatus ==4">
                    ${fn:escapeXml(imDef:imGetText('MSG.GOD.192'))}
<!--                    Đã nhập kho-->
                </s:if>
                <s:if test="#attr.trans.stockTransStatus ==5">
                    ${fn:escapeXml(imDef:imGetText('MSG.GOD.196'))}
<!--                    Đã huỷ-->
                </s:if>
                <s:if test="#attr.trans.stockTransStatus ==6">
                    ${fn:escapeXml(imDef:imGetText('MSG.GOD.197'))}
<!--                    Đã từ chối nhập-->
                </s:if>
        </display:column>
        <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MSG.GOD.031'))}" style="width:200px" property="note" sortable="false" headerClass="tct"/>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.207'))}" sortable="false" headerClass="tct">
            <div align="center">
            <s:if test="#attr.trans.stockTransStatus ==3"> <!-- chi nhung giao dịch đã xuat kho moi duoc lap lenh nhap -->
                <s:url action="StaffImportStockFromShop!prepareCreateImpStockFromNote" id="URL" encode="true" escapeAmp="false">
                    <s:param name="actionId" value="#attr.trans.id.actionId"/>
                    <s:param name="struts.token.name" value="'struts.token'"/>
                    <s:param name="struts.token" value="struts.token"/>
                </s:url>
                <sx:a targets="searchArea" href="%{#URL}" executeScripts="true" parseContent="true" errorNotifyTopics="errorAction">
                   ${fn:escapeXml(imDef:imGetText('MSG.GOD.230'))}
<!--                   Xác nhận nhập-->
                </sx:a>
                 <%--
                <s:url action="StaffImportStockFromShop!rejectImpStockFromShop" id="URLReject" encode="true" escapeAmp="false">
                    <s:param name="actionId" value="#attr.trans.id.actionId"/>
                </s:url>
                <a onclick="rejectTrans('searchArea','<s:property escapeJavaScript="true"  value="#attr.URLReject"/>')">Từ chối nhập</a>
                 --%>
            </s:if>
            </div>
        </display:column>
    </display:table>

</s:if>
<script>
    rejectTrans=function(target, action){
        //if(confirm("Bạn có thực sự muốn từ chối nhập hàng?"))
        if(confirm("'<s:text name="MSG.STK.005"/>'")){
            gotoAction(target, action);
        }
    }
</script>

