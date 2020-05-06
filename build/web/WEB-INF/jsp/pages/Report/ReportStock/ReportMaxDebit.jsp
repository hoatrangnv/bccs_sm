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

<s:form action="reportMaxDebitAction" theme="simple" method="post" id="reportDepositForm">
    <s:token/>

    <tags:imPanel title="MENU.200014">
        <table class="inputTbl4Col">
            <tr>
                <td style="width: 20%"><tags:label key="MSG.shop.code" required="true"/></td>
                <td  class="oddColumn">
                    <tags:imSearch codeVariable="reportDepositForm.shopCode" nameVariable="reportDepositForm.shopName"
                                   codeLabel="MSG.RET.028" nameLabel="MSG.RET.029"
                                   searchClassName="com.viettel.im.database.DAO.ReportMaxDebitDAO"
                                   searchMethodName="getListShop"
                                   getNameMethod="getShopName"
                                   elementNeedClearContent=""
                                   roleList=""/>
                </td>
            </tr>            
            <tr>
                
                <td align="center" colspan="2">
                    <tags:submit formId="reportDepositForm"
                                 showLoadingText="true"
                                 cssStyle="width:80px;"
                                 targets="bodyContent"
                                 value="MSG.report"
                                 preAction="reportMaxDebitAction!exportReportMaxDebit.do" validateFunction="checkValidFields()"/>
                </td>
            </tr>
            <tr>
                
                <td colspan="2" align="center"  width="100%">
                    <tags:displayResult id="resultMessageId" property="resultMessage1"/>
                </td>
            </tr>
            <tr>
                <td colspan="3" align="center">
                    <s:if test="#request.filePath !=null && #request.filePath!=''">
                        <script>
                            window.open('${contextPath}/download.do?${fn:escapeXml(filePath)}', '', 'toolbar=yes,scrollbars=yes');
                            <%--
                            window.open('${contextPath}<s:property escapeJavaScript="true"  value="#request.filePath"/>','','toolbar=yes,scrollbars=yes');
                                window.open('<s:property escapeJavaScript="true"  value="#request.filePath"/>','','toolbar=yes,scrollbars=yes');--%>
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