<%@page import="com.viettel.security.util.StringEscapeUtils"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : ListRequestCardNotSale
    Created on : Dec 9, 2015, 10:38:28 AM
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
            request.setAttribute("listRequest", request.getSession().getAttribute("listRequest"));
%>

<sx:div id="displayTagFrame">
    <display:table targets="displayTagFrame" name="listRequest"
                   id="listRequestId" pagesize="20" class="dataTable"
                   cellpadding="1" cellspacing="1"
                   requestURI="approveRequestLockUserAction!pageNavigator.do">
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.073'))}" sortable="false" headerClass="tct" style="width:50px; text-align:center">
            ${fn:escapeXml(listRequestId_rowNum)}
        </display:column>
        <display:column property="reqCode" title="${fn:escapeXml(imDef:imGetText('lbl.ma_yeu_cau'))}" sortable="false" headerClass="sortable" escapeXml="true"/>
        <display:column property="createReqDate" title="${fn:escapeXml(imDef:imGetText('lbl.ngay_yeu_cau'))}" format="{0,date,dd/MM/yyyy}" style="text-align:center" sortable="false" headerClass="sortable"/>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.creater'))}" sortable="false" headerClass="sortable">
            <s:property escapeJavaScript="true"  value="#attr.listRequestId.staffCode"/>
        </display:column>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.status'))}" sortable="false" headerClass="sortable">
            <s:if test="#attr.listRequestId.status == 0">
                ${fn:escapeXml(imDef:imGetText('MSG.Not.Approved.Request'))}
            </s:if>
            <s:elseif test="#attr.listRequestId.status == 1">
                ${fn:escapeXml(imDef:imGetText('MSG.Approved.Request'))}
            </s:elseif>
            <s:else>
                undefined - <s:property escapeJavaScript="true"  value="#attr.listRequestId.status"/>
            </s:else>
        </display:column>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.070'))}" sortable="false" headerClass="tct" style="width:70px; text-align:center; ">
            
            <s:a href="" cssClass="cursorHand" onclick="aOnclick('%{#attr.listRequestId.reqId}')">
                <img src="<%=StringEscapeUtils.escapeHtml(request.getContextPath())%>/share/img/accept_1.png"
                     title="<s:text name="lbl.xem_chi_tiet" />"
                     alt="<s:text name="lbl.xem_chi_tiet" />"/>
            </s:a>
           
        </display:column>
        <display:column title="${fn:escapeXml(imDef:imGetText('lbl.duyet.yeu.cau'))}" sortable="false" headerClass="tct" style="width:70px; text-align:center; ">
            
            <s:a href="" cssClass="cursorHand" onclick="aOnclickAccept('%{#attr.listRequestId.reqId}', %{#attr.listRequestId.status})">
                <img src="<%=StringEscapeUtils.escapeHtml(request.getContextPath())%>/share/img/accept.png"
                     title="<s:text name="lbl.duyet.yeu.cau" />"
                     alt="<s:text name="lbl.duyet.yeu.cau" />"/>
            </s:a>
           
        </display:column>       

    </display:table>

    <br/>
</sx:div >

<script>
    aOnclick = function(reqId) {
        openPopup('approveRequestLockUserAction!lookDetailRequest.do?reqId='+reqId +'&'+token.getTokenParamString(),1050,550);
    }
    
    aOnclickAccept = function(reqId, status) {
        var strConfirm = getUnicodeMsg('<s:property value="getText('cf.duyet_yeu_cau')"/>');
        if (status == 1) {
            alert(getUnicodeMsg('<s:property value="getText('MSG.record.approved')"/>'));
        }    
        else {
            if (confirm(strConfirm)) {
                gotoAction('bodyContent', 'approveRequestLockUserAction!acceptRequest.do?reqId=' + reqId + '&status=' + status + '&'+token.getTokenParamString());
            }
            else {
                return false;
            }
        }
    }
</script>
