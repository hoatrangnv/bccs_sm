<%-- 
    Document   : listExpCmdToPartner
    Created on : Sep 2, 2010, 10:57:30 PM
    Author     : Doan Thanh 8
    Desc       : danh sach cac lenh xuat kho cho doi tac
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="display" uri="VTdisplaytaglib" %>
<%@taglib prefix="imDef" uri="imDef" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page import="com.guhesan.querycrypt.QueryCrypt" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
            request.setAttribute("listImpCmd", request.getSession().getAttribute("listImpCmd"));
%>
<div>
    <tags:displayResult property="message" id="message" propertyValue="messageParam" type="key"/>
    <!-- hien thi link download phieu xuat trong truong hop in phieu -->
    <div>
        <s:if test="importStockForm.exportUrl !=null && importStockForm.exportUrl!=''">
                <script>
                    window.open('${contextPath}<s:property escapeJavaScript="true"  value="importStockForm.exportUrl"/>','','toolbar=yes,scrollbars=yes');
                </script>
                <div><a href='${contextPath}<s:property escapeJavaScript="true"  value="importStockForm.exportUrl"/>' ><tags:label key="MSG.download2.file.here"/></a></div>

            </s:if>
    </div>    
    <fieldset class="imFieldset">
        <legend>${imDef:imGetText('MSG.listSearResult')}</legend>
        <display:table id="tblListImpCmd" name="listImpCmd" class="dataTable" pagesize="15" targets="divCreateImpCmdFromPartner" requestURI="anypayPartnerAction!pageNavigatorCmd.do" >
            <display:column title="${imDef:imGetText('MSG.GOD.073')}" sortable="false" headerClass="tct" style="text-align:center">
                <s:property value="%{#attr.tblListImpCmd_rowNum}"/>
            </display:column>
            <display:column escapeXml="true" title="${imDef:imGetText('MSG.GOD.205')}" property="actionCode"/>
            <display:column title="${imDef:imGetText('MSG.GOD.206')}" property="createDatetime" format="{0,date,dd/MM/yyyy}"  sortable="false" headerClass="tct"/>
            <display:column escapeXml="true" title="${imDef:imGetText('MSG.partner')}" property="fromOwnerName" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true" title="${imDef:imGetText('MSG.GOD.185')}" property="toOwnerName" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true" title="${imDef:imGetText('MSG.reasonImport')}" property="reasonName" sortable="false" headerClass="tct"/>
            
            <display:column title="${imDef:imGetText('MSG.GOD.013')}" sortable="false" headerClass="tct">
            <s:if test="#attr.tblListImpCmd.stockTransStatus ==3">
                    <s:text name="status.created.imported"/>
                </s:if>
                <s:elseif test="#attr.tblListImpCmd.stockTransStatus ==4">
                    <s:text name="MSG.GOD.192"/>
                </s:elseif>
                <s:elseif test="#attr.tblListImpCmd.stockTransStatus ==5">
                    <s:text name="status.destroyed"/>
                </s:elseif>
                <s:elseif test="#attr.tblListImpCmd.stockTransStatus ==6">
                    <s:text name="MSG.GOD.197"/>
                </s:elseif>
                <s:elseif test="#attr.tblListImpCmd.stockTransStatus ==1">
                    <s:text name="status.created.cmd"/>
                </s:elseif>
                 <s:elseif test="#attr.tblListImpCmd.stockTransStatus ==2">
                    <s:text name="status.created.make"/>
                </s:elseif>
                <s:else>
                    <s:property value="#attr.tblListImpCmd.stockTransStatus"/> - Undefined
                </s:else>

            </display:column>
                    
            <display:column escapeXml="true" title="${imDef:imGetText('MSG.GOD.031')}" style="width:100px" property="note" sortable="false" headerClass="tct"/>
            <!-- view lenh -->
            <display:column title="${imDef:imGetText('MSG.detail')}" sortable="false" headerClass="tct">
                <div align="center">
                    <s:if test="#attr.tblListImpCmd.stockTransStatus != 5"> <%-- chi view nhung lenh chua bi huy --%>
                        <s:url action="anypayPartnerAction!viewDetailCommand" id="URL" encode="true" escapeAmp="false">
                            <s:param name="actionId" value="#attr.tblListImpCmd.actionId"/>
                        </s:url>
                        <sx:a targets="divExpCmdToPartner" href="%{#URL}" executeScripts="true" parseContent="true" errorNotifyTopics="errorAction">
                            ${fn:escapeXml(imDef:imGetText('MSG.detail'))}
                        </sx:a>
                    </s:if>
            </display:column>
            <display:column title="${imDef:imGetText('MSG.GOD.207')}" sortable="false" headerClass="tct">
                <div align="center">
                    <!-- huy lenh -->
                    <s:if test="#attr.tblListImpCmd.stockTransStatus ==1 && #attr.tblListImpCmd.userCode == searchStockForm.sender"> <%-- chi huy nhung lenh da lap lenh --%>
                        <s:url action="anypayPartnerAction!destroyImpCmd" id="URL" encode="true" escapeAmp="false">
                            <s:param name="actionId" value="#attr.tblListImpCmd.actionId"/>
                        </s:url>
                        &nbsp;&nbsp;
                        <a onclick="destroyImpCmd('<s:property value="#attr.URL"/>')" style="cursor:pointer">
                            ${imDef:imGetText('command.cancel')}
                        </a>
                    </s:if>
                    <!-- huy lenh -->
                    </div>
            </display:column>
            <display:column title="${imDef:imGetText('print.command')}" sortable="false" headerClass="tct">
                <div align="center">
                    <s:if test="#attr.tblListImpCmd.stockTransStatus != 5"> <%-- chi in nhung lenh chua huy --%>
                    <s:url action="anypayPartnerAction!printImpCmd" id="URL1" escapeAmp="false">
                        <s:param name="actionId" value="#attr.tblListImpCmd.actionId"/>
                    </s:url>
                    <sx:a targets="divCreateImpCmdFromPartner" href="%{#URL1}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                        <img src="${contextPath}/share/img/accept.png" title="${fn:escapeXml(imDef:imGetText('MSG.printImportNote'))}" alt="${fn:escapeXml(imDef:imGetText('MSG.printImportNote'))}"/>
                    </sx:a>
                    </s:if>
                </div>
            </display:column>
        </display:table>
    </fieldset>
</div>
<sx:div id="divExpCmdToPartner">
    <jsp:include page="detailCommand.jsp"/>
</sx:div>
<script>
    destroyImpCmd= function(url){
        var strConfirmMessage  = getUnicodeMsg('<s:text name="MSG.STK.004"/>');
        if(confirm(strConfirmMessage)){
            gotoAction("divCreateImpCmdFromPartner", url  + "&" + token.getTokenParamString(),"searchStockForm");
        }
    }
</script>