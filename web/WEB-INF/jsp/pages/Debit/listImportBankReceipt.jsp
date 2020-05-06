<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : listImportBankReceipt
    Created on : Oct 28, 2010, 10:55:30 AM
    Author     : tronglv
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
%>

<s:if test="#request.lstBankReceiptTrans != null && #request.lstBankReceiptTrans.size() != 0">
    <tags:imPanel title="MSG.DET.131">

        <display:table name="lstBankReceiptTrans" targets="lstBRMArea" id="trans" pagesize="100" class="dataTable" cellpadding="1" cellspacing="1" requestURI="importBRMAction!pageNavigator.do">
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.DET.078'))}" sortable="false" headerClass="tct">
                <div align="center">
                    <s:property escapeJavaScript="true"  value="%{#attr.trans_rowNum}"/>
                </div>
            </display:column>
            
            <display:column escapeXml="true"  property="staffCode" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.111'))}" sortable="false" headerClass="sortable"  style ="width:200px;"/>
            <display:column property="transDate" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.143'))}" format="{0,date,dd/MM/yyyy HH:mm:ss}" sortable="false" headerClass="sortable" />
                
            
            <%--display:column property="accountNo" title="${fn:escapeXml(imDef:imGetText('MSG.DET.120'))}" sortable="false" headerClass="sortable" style ="width:150px;"/>
            <display:column escapeXml="true"  property="bankCode" title="${fn:escapeXml(imDef:imGetText('MSG.bank.code'))}" sortable="false" headerClass="sortable" style ="width:80px;"/>
            <display:column escapeXml="true"  property="bagCode" title="${fn:escapeXml(imDef:imGetText('MSG.DET.129'))}" sortable="false" headerClass="sortable" style ="width:80px;"/>
            <display:column escapeXml="true"  property="staffCode" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.111'))}" sortable="false" headerClass="sortable"/>
            <display:column property="transDate" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.143'))}" format="{0,date,dd/MM/yyyy HH:mm:ss}" sortable="false" headerClass="sortable" style ="width:120px;"/--%>

            <%--display:column property="countSussess" title="${fn:escapeXml(imDef:imGetText('MSG.DET.145'))}" sortable="false" headerClass="sortable" style ="width:75px;"/>
            <display:column property="countError" title="${fn:escapeXml(imDef:imGetText('MSG.DET.146'))}" sortable="false" headerClass="sortable"  style ="width:75px;"/--%>

            <%--display:column property="note" title="${fn:escapeXml(imDef:imGetText('MSG.DET.076'))}" sortable="false" headerClass="sortable"/--%>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.SAE.146'))}" sortable="false" headerClass="sortable">
                <div align="center">

                    <s:url action="importBRMAction!viewIBRDetail" id="URL1">
                        <s:token/>
                            <s:param name="transId" value="#attr.trans.transId"/>
                            <s:param name="isError" value="0"/>
                        <s:param name="struts.token.name" value="'struts.token'"/>
                        <s:param name="struts.token" value="struts.token"/>
                        </s:url>
                        <sx:a targets="viewBRDetailArea" href="%{#URL1}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                            <img src="${contextPath}/share/img/accept.png"
                                 title="<s:property escapeJavaScript="true"  value ="getText('MSG.DET.144')"/>"
                                 alt="<s:property escapeJavaScript="true"  value ="getText('MSG.DET.144')"/>"
                                 />
                        </sx:a>

                    <%--s:if test="#attr.trans.countSussess > 0 ">
                        <s:url action="importBRMAction!viewIBRDetail" id="URL1">
                            <s:param name="transId" value="#attr.trans.transId"/>
                            <s:param name="isError" value="0"/>
                        </s:url>
                        <sx:a targets="viewBRDetailArea" href="%{#URL1}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                            <img src="${contextPath}/share/img/accept.png"
                                 title="<s:property escapeJavaScript="true"  value ="getText('MSG.DET.144')"/>"
                                 alt="<s:property escapeJavaScript="true"  value ="getText('MSG.DET.144')"/>"
                                 />
                        </sx:a>
                    </s:if> <s:else>-</s:else>
                    &nbsp;&nbsp;
                    <s:if test="#attr.trans.countError > 0 ">
                        <s:url action="importBRMAction!viewIBRDetail" id="URL2">
                            <s:param name="transId" value="#attr.trans.transId"/>
                            <s:param name="isError" value="1"/>
                        </s:url>
                        <sx:a targets="viewBRDetailArea" href="%{#URL2}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                            <img src="${contextPath}/share/img/accept.png"
                                 title="<s:property escapeJavaScript="true"  value ="getText('MSG.DET.143')"/>"
                                 alt="<s:property escapeJavaScript="true"  value ="getText('MSG.DET.143')"/>"
                                 />
                        </sx:a>
                    </s:if> <s:else>-</s:else--%>
                </div>
            </display:column>

            <display:footer> <tr> <td colspan="7" style="color:green">${fn:escapeXml(imDef:imGetText('MSG.SAE.147'))}:<s:property escapeJavaScript="true"  value="%{#request.lstBankReceiptTrans.size()}" /></td> <tr> </display:footer>
            </display:table>
        </tags:imPanel>
    </s:if>
    <%--s:else>
        <s:property escapeJavaScript="true"  value ="getText('MSG.not.found.records')"/>
    </s:else--%>
