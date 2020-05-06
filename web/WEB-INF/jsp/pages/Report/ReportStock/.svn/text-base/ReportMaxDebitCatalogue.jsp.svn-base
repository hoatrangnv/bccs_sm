<%-- 
    Document   : ReportMaxDebit
    Created on : Dec 4, 2012, 8:58:38 AM
    Author     : ITHC
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@page import="java.util.*"%>

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<%
            request.setAttribute("contextPath", request.getContextPath());
%>
<s:form action="reportMaxDebitCatalogueAction" theme="simple" method="post" id="stockDepositForm">
    <s:token/>
    <tags:imPanel title="MENU.200015">
        <table class="inputTbl4Col">
            <tr>
                <td  class="label" ><tags:label key="MSG.STK.042" required="false" /></td>
                <td class="text" width="80%">
                    <tags:imSelect name="stockDepositForm.chanelTypeId"
                                   id="stockDepositForm.chanelTypeId"
                                   cssClass="txtInputFull"
                                   theme="simple"
                                   headerKey="" headerValue="MSG.RET.162"
                                   list="listChannelType"  listKey="channelTypeId" listValue="name"/>
                </td>
            </tr>
            <tr>
                <td class="label"><tags:label key="L.100033"/></td>
                    <td  width="80%">
                        <tags:imSearch codeVariable="stockDepositForm.stockModelCode" nameVariable="stockDepositForm.stockModelName"
                                       codeLabel="L.100033" nameLabel="L.100034"
                                       searchClassName="com.viettel.im.database.DAO.ReportMaxDebitCatalogueDAO"
                                       searchMethodName="getListStockModel"
                                       getNameMethod="getStockModelName"/>
                    </td>
            </tr>            
            <tr>
                <td colspan="2" align="center">
                    <tags:submit formId="stockDepositForm"
                                 showLoadingText="true"
                                 cssStyle="width:80px;"
                                 targets="bodyContent"
                                 value="MSG.report"
                                 preAction="reportMaxDebitCatalogueAction!exportReportMaxDebitCatalogue.do" validateFunction="checkValidFields()"/>
                </td>
            </tr>
             <tr>
                <td colspan="3" align="center"  width="100%">
                    <tags:displayResult id="displayResultMsgClient" property="displayResultMsgClient"/>
                </td>
            </tr>
            <tr>
                <td colspan="4" align="center">
                    <s:if test="#request.filePath !=null && #request.filePath!=''">
                        <script>
                            window.open('${contextPath}/download.do?${fn:escapeXml(filePath)}', '', 'toolbar=yes,scrollbars=yes');
                            <%--
                            window.open('${contextPath}<s:property escapeJavaScript="true"  value="#request.filePath"/>','','toolbar=yes,scrollbars=yes');
                                window.open('<s:property escapeJavaScript="true"  value="#request.filePath"/>','','toolbar=yes,scrollbars=yes'); --%>
                        </script>
                        <div>
                            <a href="${contextPath}/download.do?${fn:escapeXml(filePath)}">
                                <tags:displayResult id="reportStockImportPendingMessage" property="reportStockImportPendingMessage" type="key"/>
                            </a>
                            <%--
                            <a href='${contextPath}<s:property escapeJavaScript="true"  value="#request.filePath"/>'>
                            
                            <a href='<s:property escapeJavaScript="true"  value="#request.filePath"/>'>
                                <tags:label key="MSG.clickhere.to.download" /></a>--%>
                        </div>
                     </s:if>
                </td>
            </tr>
        </table>
    </tags:imPanel>
</s:form>

<script type="text/javascript">
    checkValidFields = function() {
        return true;
    }
</script>