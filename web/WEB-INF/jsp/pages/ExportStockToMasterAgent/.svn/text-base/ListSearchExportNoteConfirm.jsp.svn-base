<%-- 
    Document   : ListSearchExportNoteConfirm
    Created on : Sep 16, 2016, 4:17:26 PM
    Author     : mov_itbl_dinhdc
--%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="VTdisplaytaglib" prefix="display"%>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
%>


<s:if test="#request.lstSearchStockTransComfirm != null && #request.lstSearchStockTransComfirm.size()>0">
    <!-- phan hien thi message -->
    <div>
        <tags:displayResult id="message" property="message" propertyValue="messageParam" type="key"/>
    </div>
    <display:table id="trans" name="lstSearchStockTransComfirm" class="dataTable" pagesize="15"
                   targets="searchArea" requestURI="StockTransMasterAgentAction!searchExpTransConfirm.do" >
        <display:column  title="${fn:escapeXml(imDef:imGetText('MSG.GOD.073'))}" sortable="false" headerClass="tct">
            <s:property escapeJavaScript="true"  value="%{#attr.trans_rowNum}"/>
        </display:column>
        <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MSG.GOD.205'))}" property="actionCode"/>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.206'))}" property="createDatetime" format="{0,date,dd/MM/yyyy}"  sortable="false" headerClass="tct"/>
        <display:column  escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.184'))}" property="fromOwnerName" sortable="false" headerClass="tct"/>
        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.185'))}" property="toOwnerName" sortable="false" headerClass="tct"/>
        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.158'))}" property="reasonName" sortable="false" headerClass="tct"/>

        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.013'))}" property="statusName" sortable="false" headerClass="tct">
            </display:column>
        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.generates.status'))}" sortable="false" headerClass="tct">
            <s:if test="#attr.trans.fileAcceptStatus == 0">
                ${fn:escapeXml(imDef:imGetText('MSG.Not.Approved.Request'))}
            </s:if>
            <s:elseif test="#attr.trans.fileAcceptStatus == 1">
                ${fn:escapeXml(imDef:imGetText('MSG.Approved.Request'))}
            </s:elseif>
            <s:elseif test="#attr.trans.fileAcceptStatus == 2">
                ${fn:escapeXml(imDef:imGetText('MSG.Confirm.Exp'))}
            </s:elseif>
            <s:else>
                Not Accept <s:property escapeJavaScript="true"  value="#attr.trans.fileAcceptStatus"/>
            </s:else>
        </display:column> 

        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.031'))}" style="width:100px" property="note" sortable="false" headerClass="tct"/>

        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.207'))}" sortable="false" headerClass="tct">
            <div align="center">
                <s:if test="#attr.trans.stockTransStatus ==2"> <%-- chi nhung phieu chua xuat kho moi thuc hien xuat kho dc --%>
                    <s:url action="StockTransMasterAgentAction!prepareExpStockFromNote" id="URL" encode="true" escapeAmp="false">
                        <s:param name="actionId" value="#attr.trans.actionId"/>
                    </s:url>
                    <sx:a targets="searchArea" href="%{#URL}" executeScripts="true" parseContent="true" errorNotifyTopics="errorAction">
                        <tags:label key="MSG.GOD.200"/>
<!--                        Xuất kho-->
                    </sx:a>
                </s:if>
                <%--
                <s:if test="#attr.trans.stockTransStatus <3"> <!-- chi huy nhung lenh chua xuat kho -->
                    <s:url action="StockTransMasterAgentAction!cancelExpTrans" id="URL" encode="true" escapeAmp="false">
                        <s:token/>
                        <s:param name="struts.token.name" value="'struts.token'"/>
                        <s:param name="struts.token" value="struts.token"/>
                        <s:param name="actionId" value="#attr.trans.actionId"/>
                    </s:url>
                    &nbsp;&nbsp;
                    <a onclick="cancelExp('<s:property escapeJavaScript="true"  value="#attr.URL"/>')">
                        <tags:label key="MSG.GOD.204"/>
<!--                        Huỷ giao dịch-->
                    </a>

                
               </s:if>
                --%>
            </div>    
        </display:column>
    </display:table>

</s:if>
<script>
    
</script>
