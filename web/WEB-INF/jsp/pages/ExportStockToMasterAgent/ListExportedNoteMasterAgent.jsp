<%@page import="com.viettel.security.util.StringEscapeUtils"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : ListSearchExpNoteMasterAgent
    Created on : SEP 21, 2016, 10:38:28 AM
    Author     : mov_itbl_dinhdc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
            request.setAttribute("listExportedNoteMasterAgent", request.getSession().getAttribute("listExportedNoteMasterAgent"));
%>
<s:if test="#request.listExportedNoteMasterAgent != null && #request.listExportedNoteMasterAgent.size()>0">
    
    <display:table targets="searchArea" name="listExportedNoteMasterAgent"
                   id="listExportedNoteMasterAgentId" pagesize="20" class="dataTable"
                   requestURI="StockTransMasterAgentAction!searchExportedNoteMasterAgent.do">
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.073'))}" sortable="false" headerClass="tct" style="width:50px; text-align:center">
            ${fn:escapeXml(listExportedNoteMasterAgentId_rowNum)}
        </display:column>
        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.205'))}" property="actionCode"/>
        <display:column  title="${fn:escapeXml(imDef:imGetText('MSG.GOD.206'))}" property="createDatetime" format="{0,date,dd/MM/yyyy}"  sortable="false" headerClass="tct"/>
        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.184'))}" property="fromOwnerName" sortable="false" headerClass="tct"/>
        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.185'))}" property="toOwnerName" sortable="false" headerClass="tct"/>
        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.158'))}" property="reasonName" sortable="false" headerClass="tct"/>
        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.013'))}" property="statusName" sortable="false" headerClass="tct">
        </display:column>
       
        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.approved'))}" sortable="false" headerClass="tct">
            <s:if test="#attr.listExportedNoteMasterAgentId.fileAcceptStatus == 0">
                ${fn:escapeXml(imDef:imGetText('MSG.Not.Approved.Request'))}
            </s:if>
            <s:elseif test="#attr.listExportedNoteMasterAgentId.fileAcceptStatus == 1">
                ${fn:escapeXml(imDef:imGetText('MSG.Approved.Request'))}
            </s:elseif>
            <s:elseif test="#attr.listExportedNoteMasterAgentId.fileAcceptStatus == 2">
                ${fn:escapeXml(imDef:imGetText('MSG.Confirm.Exp'))}
            </s:elseif>
            <s:else>
                Not Confirm <s:property escapeJavaScript="true"  value="#attr.listExportedNoteMasterAgentId.fileAcceptStatus"/>
            </s:else>
        </display:column>
                
        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.031'))}" style="width:200px" property="note" sortable="false" headerClass="tct"/>
        <display:column title="Download File" sortable="false" headerClass="tct" style="text-align:center; width:50px;">
            <s:if test="#attr.listExportedNoteMasterAgentId.fileAcceptConfirm != null">
                    <s:url action="StockTransMasterAgentAction!downloadFileComfirm" id="URLDOWN">
                        <s:param name="stockTransId" value="#attr.listExportedNoteMasterAgentId.stockTransId"/>
                    </s:url>
                    &nbsp;&nbsp;
                    <a onclick="downloadFileComfirm('<s:property escapeJavaScript="true"  value="#attr.URLDOWN"/>')">
                        <img src="<%=StringEscapeUtils.escapeHtml(request.getContextPath())%>/share/img/download_icon.gif"
                         title="<s:text name="Download File" />"
                         alt="<s:text name="Download File" />"/>
                    </a>    
            </s:if>
        </display:column>      
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.070'))}" sortable="false" headerClass="tct" style="width:70px; text-align:center; ">
            
            <s:a href="" cssClass="cursorHand" onclick="aOnclick('%{#attr.listExportedNoteMasterAgentId.stockTransId}')">
                <img src="<%=StringEscapeUtils.escapeHtml(request.getContextPath())%>/share/img/accept_1.png"
                     title="<s:text name="lbl.xem_chi_tiet" />"
                     alt="<s:text name="lbl.xem_chi_tiet" />"/>
            </s:a>
           
        </display:column>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.195'))}" sortable="false" headerClass="tct" style="width:70px; text-align:center; ">
            <s:if test="#attr.listExportedNoteMasterAgentId.fileAcceptStatus == 1 && #attr.listExportedNoteMasterAgentId.stockTransStatus == 3">
                <s:a href="" cssClass="cursorHand" onclick="aOnclickConfirm('%{#attr.listExportedNoteMasterAgentId.stockTransId}', %{#attr.listExportedNoteMasterAgentId.fileAcceptStatus})">
                    <img src="<%=StringEscapeUtils.escapeHtml(request.getContextPath())%>/share/img/accept.png"
                         title="<s:text name="lbl.duyet.yeu.cau" />"
                         alt="<s:text name="lbl.duyet.yeu.cau" />"/>
                </s:a>
            </s:if>       
      </display:column>     
    </display:table>
</s:if>
            

<script>
    aOnclick = function(stockTransId) {
        openPopup('StockTransMasterAgentAction!lookDetailStockTrans.do?stockTransId='+stockTransId +'&'+token.getTokenParamString(),1050,550);
    }
    
    
    aOnclickConfirm = function(stockTransId, status) {
       if (status == 2) {
            alert(getUnicodeMsg('<s:property value="getText('StockTrans.Confirmed')"/>'));
            return false;
       }
       gotoAction('bodyContent', 'StockTransMasterAgentAction!prepageConfirmStockTransNote.do?stockTransId=' + stockTransId + '&status=' + status + '&'+token.getTokenParamString());
    }
    
    downloadFileComfirm = function(urlDownLoad) {
        gotoAction("bodyContent", urlDownLoad,"exportStockForm");
   } 
</script>


