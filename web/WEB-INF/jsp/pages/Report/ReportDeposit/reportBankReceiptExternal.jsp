<%-- 
    Document   : reportBankReceiptExternal
    Created on : Nov 12, 2010, 2:37:55 PM
    Author     : tronglv
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.*"%>

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<s:form action="reportBankReceiptInExAction" theme="simple" method="post" id="form">
<s:token/>

    <tags:imPanel title="MSG.RET.034">
        <table class="inputTbl4Col">
            <tr>
                <td><tags:label key="MSG.RET.032" /></td>
                <td colspan="3">
                    <tags:imSearch codeVariable="form.shopCode" nameVariable="form.shopName"
                                   codeLabel="MSG.RET.032" nameLabel="MSG.RET.033"
                                   searchClassName="com.viettel.im.database.DAO.ReportBankReceiptInExDAO"
                                   searchMethodName="getListShop"/>
                </td>
            </tr>           
            <tr>
                <td class="label"><tags:label key="MSG.RET.036" /></td>
                <td  class="text">
                    <tags:dateChooser property="form.fromDate" id="fromDate"  cssStyle="txtInput"/>
                </td>
                <td class="label"><tags:label key="MSG.RET.037" /></td>
                <td  class="text">
                    <tags:dateChooser property="form.toDate" id="toDate" cssStyle="txtInput"/>
                </td>
            </tr>
            <%--tr>
                <td class="label"><tags:label key="MSG.RET.038" /></td>
                <td colspan="3">
                    <fieldset style="width:100%;">
                        <tags:imRadio name="form.reportType"
                                      id="reportDetail"
                                      list="0:MSG.RET.121,1:MSG.RET.127"/>
<!--                        0: tong thop; 1: chi tiet-->
                    </fieldset>
                </td>
            </tr>
            <tr>
                <td class="label"><tags:label key="MSG.RET.039" /></td>
                <td colspan="3">
                    <fieldset style="width:100%;">
                        <tags:imRadio name="form.status"
                                      id="receiptStatus"
                                      list="1:MSG.RET.128,3:MSG.RET.129,0:MSG.RET.130"/>
<!--                        1:chua duyet; 3: da duyet; 0: ca 2-->
                    </fieldset>
                </td>
            </tr--%>
            
            <s:hidden name="form.reportType" id="reportDetail" value="1"/>
            <s:hidden name="form.status" id="receiptStatus" value="0"/>
            
            <tr>
                <td colspan="4" align="center">
                    <tags:submit formId="form"
                                 showLoadingText="true"
                                 cssStyle="width:80px;"
                                 targets="bodyContent"
                                 value="MSG.RET.040"
                                 preAction="reportBankReceiptInExAction!reportBankReceiptExternal.do" />
                </td>
            </tr>
            <tr>
                <td colspan="4" align="center">
                    <tags:displayResult id="displayResultMsgClient" property="returnMsg" propertyValue="returnMsgValue" type="key"/>
                </td>
            </tr>
            <tr>
                <td colspan="4" align="center">
                    <s:if test="#request.filePath !=null && #request.filePath!=''">
                        <script>
                            window.open('${contextPath}/download.do?${fn:escapeXml(filePath)}', '', 'toolbar=yes,scrollbars=yes');
                            <%--window.open('<s:property escapeJavaScript="true"  value="#request.filePath"/>','','toolbar=yes,scrollbars=yes');--%>
                        </script>
                        <div>
                             <a href="${contextPath}/download.do?${fn:escapeXml(filePath)}">
                                <tags:displayResult id="reportRevenueMessage" property="reportRevenueMessage" type="key"/>
                            </a>
                            <%--<div><a href='<s:property escapeJavaScript="true"  value="#request.filePath"/>'><tags:label key="MSG.clickhere.to.download" /></a></div>--%>
                        </div>
                    </s:if>
                </td>
            </tr>
        </table>
    </tags:imPanel>
</s:form>

<script language="javascript">
    checkValidFields = function() {
        var _myWidget1 = dojo.widget.byId("form.shopCode");
        if(_myWidget1.textInputNode.value==null || _myWidget1.textInputNode.value.trim()==""){
            _myWidget1.textInputNode.focus();
    <%--$( 'displayResultMsgClient' ).innerHTML = "Chưa nhập mã cửa hàng/đại lý";--%>
                $('displayResultMsgClient').innerHTML='<s:text name="ERR.RET.010"/>';
                return false;
            }
            var dateExported= dojo.widget.byId("fromDate");
            if(dateExported.domNode.childNodes[1].value.trim() == ""){
    <%--$( 'displayResultMsgClient' ).innerHTML='Chưa nhập ngày báo cáo từ ngày';--%>
                $('displayResultMsgClient').innerHTML='<s:text name="ERR.RET.002"/>';
                $('fromDate').focus();
                return false;
            }
            if(!isDateFormat(dateExported.domNode.childNodes[1].value)){
    <%--$( 'displayResultMsgClient' ).innerHTML='Ngày báo cáo từ ngày không hợp lệ';--%>
                $('displayResultMsgClient').innerHTML='<s:text name="ERR.RET.003"/>';
                $('fromDate').focus();
                return false;
            }

            var dateExported1 = dojo.widget.byId("toDate");
            if(dateExported1.domNode.childNodes[1].value.trim() == ""){
    <%--$( 'displayResultMsgClient' ).innerHTML='Chưa nhập ngày báo cáo đến ngày';--%>
                $('displayResultMsgClient').innerHTML='<s:text name="ERR.RET.004"/>';
                $('toDate').focus();
                return false;
            }
            if(!isDateFormat(dateExported1.domNode.childNodes[1].value)){
    <%--$( 'displayResultMsgClient' ).innerHTML='Ngày báo cáo đến ngày không hợp lệ';--%>
                $('displayResultMsgClient').innerHTML='<s:text name="ERR.RET.005"/>';
                $('toDate').focus();
                return false;
            }
            if(!isCompareDate(dateExported.domNode.childNodes[1].value.trim(),dateExported1.domNode.childNodes[1].value.trim(),"VN")){
    <%--$( 'displayResultMsgClient' ).innerHTML='Ngày báo cáo từ ngày phải nhỏ hơn ngày báo cáo đến ngày';--%>
                $('displayResultMsgClient').innerHTML='<s:text name="ERR.RET.006"/>';
                $('fromDate').focus();
                return false;
            }
            return true;
        }
</script>
