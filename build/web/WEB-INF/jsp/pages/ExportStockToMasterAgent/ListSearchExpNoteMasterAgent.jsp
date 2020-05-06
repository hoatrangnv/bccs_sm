<%@page import="com.viettel.security.util.StringEscapeUtils"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : ListSearchExpNoteMasterAgent
    Created on : SEP 13, 2016, 10:38:28 AM
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
            request.setAttribute("listSearchExpNoteMasterAgent", request.getSession().getAttribute("listSearchExpNoteMasterAgent"));
%>
<s:if test="#request.listSearchExpNoteMasterAgent != null && #request.listSearchExpNoteMasterAgent.size()>0">
    
    <display:table targets="searchArea" name="listSearchExpNoteMasterAgent"
                   id="listSearchExpNoteMasterAgentId" pagesize="20" class="dataTable"
                   requestURI="StockTransMasterAgentAction!searchExpNoteMasterAgent.do">
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.073'))}" sortable="false" headerClass="tct" style="width:50px; text-align:center">
            ${fn:escapeXml(listSearchExpNoteMasterAgentId_rowNum)}
        </display:column>
        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.205'))}" property="actionCode"/>
        <display:column  title="${fn:escapeXml(imDef:imGetText('MSG.GOD.206'))}" property="createDatetime" format="{0,date,dd/MM/yyyy}"  sortable="false" headerClass="tct"/>
        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.184'))}" property="fromOwnerName" sortable="false" headerClass="tct"/>
        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.185'))}" property="toOwnerName" sortable="false" headerClass="tct"/>
        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.158'))}" property="reasonName" sortable="false" headerClass="tct"/>
        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.013'))}" property="statusName" sortable="false" headerClass="tct">
        </display:column>
       
        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.approved'))}" sortable="false" headerClass="tct">
            <s:if test="#attr.listSearchExpNoteMasterAgentId.fileAcceptStatus == 0">
                ${fn:escapeXml(imDef:imGetText('MSG.Not.Approved.Request'))}
            </s:if>
            <s:elseif test="#attr.listSearchExpNoteMasterAgentId.fileAcceptStatus == 1">
                ${fn:escapeXml(imDef:imGetText('MSG.Approved.Request'))}
            </s:elseif>
            <s:elseif test="#attr.listSearchExpNoteMasterAgentId.fileAcceptStatus == 2">
                ${fn:escapeXml(imDef:imGetText('MSG.Confirm.Exp'))}
            </s:elseif>
            <s:else>
                Not Accept <s:property escapeJavaScript="true"  value="#attr.listSearchExpNoteMasterAgentId.fileAcceptStatus"/>
            </s:else>
        </display:column>
                
        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.031'))}" style="width:200px" property="note" sortable="false" headerClass="tct"/>
        
        <display:column title="Download File" sortable="false" headerClass="tct" style="text-align:center; width:50px;">
            <s:if test="#attr.listSearchExpNoteMasterAgentId.fileAcceptNote != null">
                    <s:url action="StockTransMasterAgentAction!downloadFileAccept" id="URLDOWN">
                        <s:param name="stockTransId" value="#attr.listSearchExpNoteMasterAgentId.stockTransId"/>
                    </s:url>
                    &nbsp;&nbsp;
                    <a onclick="downloadFileAccept('<s:property escapeJavaScript="true"  value="#attr.URLDOWN"/>')">
                        <img src="<%=StringEscapeUtils.escapeHtml(request.getContextPath())%>/share/img/download_icon.gif"
                         title="<s:text name="Download File" />"
                         alt="<s:text name="Download File" />"/>
                    </a>    

            </s:if>
        </display:column>  
                    
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.207'))}" sortable="false" headerClass="tct">
            <div align="center">
                <s:if test="#attr.listSearchExpNoteMasterAgentId.stockTransStatus == 2 && #attr.listSearchExpNoteMasterAgentId.fileAcceptStatus == 0">
                <s:url action="StockTransMasterAgentAction!cancelExpTransNotAccept" id="URL" encode="true" escapeAmp="false">
                    <s:token/>
                    <s:param name="struts.token.name" value="'struts.token'"/>
                    <s:param name="struts.token" value="struts.token"/>
                    <s:param name="actionId" value="#attr.listSearchExpNoteMasterAgentId.actionId"/>
                    <s:param name="stockTransId" value="#attr.listSearchExpNoteMasterAgentId.stockTransId"/>
                </s:url>
                &nbsp;&nbsp;
                <a onclick="cancelExpNotAccept('<s:property escapeJavaScript="true"  value="#attr.URL"/>')">
                    <tags:label key="MSG.GOD.204"/>
                    <!--Huỷ giao dịch-->
                </a>

                </s:if>
            </div>
        </display:column>
        <display:column title="Print" sortable="false" headerClass="tct" style="text-align:center; width:50px;">
            <s:if test="#attr.listSearchExpNoteMasterAgentId.fileAcceptStatus == 0 && #attr.listSearchExpNoteMasterAgentId.stockTransStatus == 1">
                <!--In Phieu -->
                    <s:url action="StockTransMasterAgentAction!printExpCmd" id="URL3">
                        <s:param name="stockTransId" value="#attr.listSearchExpNoteMasterAgentId.stockTransId"/>
                    </s:url>
                    &nbsp;&nbsp;
                    <a onclick="printExpCmd('<s:property escapeJavaScript="true"  value="#attr.URL3"/>')">
                        <img src="<%=StringEscapeUtils.escapeHtml(request.getContextPath())%>/share/img/accept_1.png"
                         title="<s:text name="Print" />"
                         alt="<s:text name="Print" />"/>
                    </a>
            </s:if>
         </display:column>  
                    
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.070'))}" sortable="false" headerClass="tct" style="width:70px; text-align:center; ">
            
            <s:a href="" cssClass="cursorHand" onclick="aOnclick('%{#attr.listSearchExpNoteMasterAgentId.stockTransId}')">
                <img src="<%=StringEscapeUtils.escapeHtml(request.getContextPath())%>/share/img/accept_1.png"
                     title="<s:text name="lbl.xem_chi_tiet" />"
                     alt="<s:text name="lbl.xem_chi_tiet" />"/>
            </s:a>
           
        </display:column>
        <display:column title="${fn:escapeXml(imDef:imGetText('lbl.duyet.yeu.cau'))}" sortable="false" headerClass="tct" style="width:70px; text-align:center; ">
            <s:if test="#attr.listSearchExpNoteMasterAgentId.fileAcceptStatus == 0 && #attr.listSearchExpNoteMasterAgentId.stockTransStatus == 2">

                <s:a href="" cssClass="cursorHand" onclick="aOnclickAccept('%{#attr.listSearchExpNoteMasterAgentId.stockTransId}', %{#attr.listSearchExpNoteMasterAgentId.fileAcceptStatus})">
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
    
    
    aOnclickAccept = function(stockTransId, status) {
       if (status == 1 || status == 2) {
            alert(getUnicodeMsg('<s:property value="getText('MSG.record.approved')"/>'));
            return false;
       } else {
           gotoAction('bodyContent', 'StockTransMasterAgentAction!prepageAcceptStockTransNote.do?stockTransId=' + stockTransId + '&status=' + status + '&'+token.getTokenParamString());
       }
    }
    
    cancelExpNotAccept= function(url){
        //if(confirm("Bạn có thực sự muốn huỷ giao dịch?"))
        var strConfirmMessage  = getUnicodeMsg('<s:text name="MSG.STK.004"/>');
        if(confirm(strConfirmMessage)){
            gotoAction("bodyContent", url,"exportStockForm");
        }
    }
    
   printExpCmd= function(url){
       gotoAction("bodyContent", url,"exportStockForm");
   }
    
   downloadFileAccept = function(urlDownLoad) {
        gotoAction("bodyContent", urlDownLoad,"exportStockForm");
   } 
</script>

