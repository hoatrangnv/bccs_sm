<%-- 
    Document   : reportDebit
    Created on : Nov 2, 2010, 10:50:54 AM
    Author     : tronglv
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">


<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
            request.setAttribute("contextPath", request.getContextPath());
%>

<tags:imPanel title="MSG.criterion.report.create">
    <s:form method="POST" id="form" action="reportDebitAction" theme="simple">
<s:token/>

        <div align="center">
            <tags:displayResult id="returnMsgClient" property="returnMsg" propertyValue="returnMsgValue" type="key"/>
        </div>
        <div align="center">
            <table class="inputTbl4Col">
                <tr>
                    <td class="label"><tags:label key="MSG.generates.unit_code" required="true" /> </td>
                    <td class="text" colspan="3">
                        <tags:imSearch codeVariable="form.shopCode" nameVariable="form.shopName"
                                       codeLabel="MSG.RET.032" nameLabel="MSG.RET.033"
                                       searchClassName="com.viettel.im.database.DAO.ReportDebitDAO"
                                       searchMethodName="getListShop"
                                       getNameMethod="getNameShop"
                                       roleList=""/>
                    </td>
                </tr>
                <tr>
                    <td  class="label"><tags:label key="MSG.fromDate" required="true" /></td>
                    <td  class="text">
                        <tags:dateChooser property="form.fromDate" id="fromDate"/>
                    </td>
                    <td  class="label"><tags:label key="MSG.generates.to_date" required="true" /></td>
                    <td  class="text">
                        <tags:dateChooser property="form.toDate" id="toDate"/>
                    </td>
                </tr>
                <%--tr>
                    <td class ="label"><tags:label key="MSG.report.type" required="true" /> </td>
                    <td colspan="3" class ="text">
                        <fieldset style="width:100%;">
                            <tags:imRadio name="form.reportType" id="reportType"
                                          list="0:MSG.RET.121,1:MSG.RET.127"/>
                        </fieldset>
                    </td>
                </tr--%>
                <tr>
<!--                    mac dinh la bao cao tong hop-->
                    <s:hidden name="form.reportType" value="0"/>

                    <td colspan="4" align="center">
                        <tags:submit formId="form"
                                     showLoadingText="true"
                                     cssStyle="width:80px;"
                                     targets="bodyContent"
                                     value="MSG.report"
                                     preAction="reportDebitAction!reportDebit.do" validateFunction="validateData();"/>
                    </td>
                </tr>               
                <tr>
                    <td colspan="4" align="center">
                        <s:if test="#request.filePath !=null && #request.filePath!=''">
                            <script>
                                window.open('${contextPath}/download.do?${fn:escapeXml(filePath)}', '', 'toolbar=yes,scrollbars=yes');
                                <%--window.open('${contextPath}<s:property escapeJavaScript="true"  value="#request.filePath"/>','','toolbar=yes,scrollbars=yes');--%>
                                <%--window.open('<s:property escapeJavaScript="true"  value="#request.filePath"/>','','toolbar=yes,scrollbars=yes');--%>
                            </script>
                            <div>
                                <a href="${contextPath}/download.do?${fn:escapeXml(filePath)}">
                                    <tags:displayResult id="reportRevenueMessage" property="reportRevenueMessage" type="key"/>
                                </a>
                                <%--<a href='${contextPath}<s:property escapeJavaScript="true"  value="#request.filePath"/>' >Bấm vào đây để download nếu trình duyệt không tự động download về.</a>--%>
                                <%--<a href='<s:property escapeJavaScript="true"  value="#request.filePath"/>' ><tags:label key="MSG.clickhere.to.download" /></a>--%>
                            </div>
                        </s:if>
                    </td>
                </tr>
            </table>
        </div>
    </s:form>
</tags:imPanel>


<script>
    validateData = function(){
        return true;
    }

</script>
