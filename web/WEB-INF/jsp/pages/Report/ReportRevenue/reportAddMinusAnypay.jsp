<%-- 
    Document   : reportAddMinusAnypay
    Created on : Oct 24, 2012, 10:04:45 AM
    Author     : os_levt1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.*"%>

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
%>


<tags:imPanel title="MSG.criterion.report.create">

    <!-- phan hien thi thong tin message -->
    <div>
        <tags:displayResult property="message" id="message" propertyValue="messageParam" type="key"/>
    </div>

    <!-- phan hien thi tieu chi bao cao -->
    <s:form action="reportAddMinusAnypayAction" theme="simple" method="post" id="reportRevenueForm">
<s:token/>

        <div class="divHasBorder">
            <table class="inputTbl6Col">
                <tr>
                    <td><tags:label key="MSG.chanel.code" /></td>
                    <td>
                        <tags:imSearch codeVariable="reportRevenueForm.staffCode" nameVariable="reportRevenueForm.staffName"
                                       codeLabel="MSG.chanel.code" nameLabel="MSG.chanel.name"
                                       searchClassName="com.viettel.im.database.DAO.AddSubAnyPayDAO"
                                       searchMethodName="getListChannel"
                                       getNameMethod="getListChannel"
                                       otherParam=""
                                       elementNeedClearContent=""
                                       roleList=""/>
                    </td>
                    <td><tags:label key="MSG.report.type" /></td>
                    <td>
                        <tags:imSelect name="reportRevenueForm.objectType" id="objectType"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="L.200045"
                                       list="1:L.200046,2:L.200047"
                                       />
                    </td>

                </tr>
                <tr>
                    <td><tags:label key="MSG.fromDate" required="true" /></td>
                    <td>
                        <tags:dateChooser property="reportRevenueForm.fromDate" id ="fromDate"/>
                    </td>
                    <td><tags:label key="MSG.generates.to_date" required="true" /></td>
                    <td>
                        <tags:dateChooser property="reportRevenueForm.toDate" id ="toDate"/>
                    </td>

                </tr>
            </table>
        </div>
        <div class="divHasBorder" style="margin-top:10px; padding:3px;">
            <tags:imRadio name="reportRevenueForm.reportType" id="reportType"
                          list="1:MSG.RET.110,2:MSG.RET.114"/>
        </div>
    </s:form>

    <!-- phan nut tac vu -->
    <div align="center" style="padding-top:5px; padding-bottom:5px;">
        <tags:submit formId="reportRevenueForm"
                     showLoadingText="true"
                     cssStyle="width:80px;"
                     targets="bodyContent"
                     value="MSG.report"
                     validateFunction="checkValidFields()"
                     preAction="reportAddMinusAnypayAction!reportAddMinus.do"/>
    </div>

    <!-- phan link download bao cao -->
    <%--
    <div>
        <s:if test="#request.reportAccountPath != null">
            <script>
                    window.open('${fn:escapeXml(reportAccountPath)}','','toolbar=yes,scrollbars=yes');
            </script>

            <a href="${fn:escapeXml(reportAccountPath)}">
                <tags:displayResult id="reportAccountMessage" property="reportAccountMessage" type="key"/>
            </a>
        </s:if>
    </div>
    --%>
    <div>
        <s:if test="#request.reportAccountPath != null">
            <script>
                    window.open('${contextPath}/download.do?${fn:escapeXml(reportAccountPath)}', '', 'toolbar=yes,scrollbars=yes');
            </script>

            <a href="${contextPath}/download.do?${fn:escapeXml(reportAccountPath)}">
                <tags:displayResult id="reportRevenueMessage" property="reportRevenueMessage" type="key"/>
            </a>
        </s:if>
    </div>

</tags:imPanel>

<script language="javascript">

    checkValidFields = function() {
        var dateExported= dojo.widget.byId("fromDate");
        if(dateExported.domNode.childNodes[1].value.trim() == ""){
            $( 'message' ).innerHTML='<s:property escapeJavaScript="true"  value="getText('ERR.UTY.028')"/>';
            $('fromDate').focus();
            return false;
        }
        if(!isDateFormat(dateExported.domNode.childNodes[1].value)){
            $( 'message' ).innerHTML='<s:property escapeJavaScript="true"  value="getText('ERR.UTY.030 ')"/>';
            $('fromDate').focus();
            return false;
        }

        var dateExported1 = dojo.widget.byId("toDate");
        if(dateExported1.domNode.childNodes[1].value.trim() == ""){
            $( 'message' ).innerHTML='<s:property escapeJavaScript="true"  value="getText('ERR.UTY.029')"/>';
            $('toDate').focus();
            return false;
        }
        if(!isDateFormat(dateExported1.domNode.childNodes[1].value)){
            $( 'message' ).innerHTML='<s:property escapeJavaScript="true"  value="getText('ERR.UTY.031')"/>';
            $('toDate').focus();
            return false;
        }
        if( !isCompareDate(dateExported.domNode.childNodes[1].value.trim(),dateExported1.domNode.childNodes[1].value.trim(),"VN")){
            $( 'message' ).innerHTML='<s:property escapeJavaScript="true"  value="getText('E.200077')"/>';
            $('fromDate').focus();
            return false;
        }
        if( days_between(dateExported.domNode.childNodes[1].value.trim(),dateExported1.domNode.childNodes[1].value.trim()) > 30){
            $( 'message' ).innerHTML='<s:property escapeJavaScript="true"  value="getText('E.200078')"/>';
            $('fromDate').focus();
            return false;
        }
            return true;
        }
</script>
