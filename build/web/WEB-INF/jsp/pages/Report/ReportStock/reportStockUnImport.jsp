<%-- 
    Document   : reportStockUnImport
    Created on : Jun 1, 2010, 10:11:57 AM
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

<%
            request.setAttribute("contextPath", request.getContextPath());
%>

<s:form action="reportStockUnImportAction" theme="simple" method="post" id="reportStockUnImportForm">
<s:token/>

    <tags:imPanel title="MSG.criterion.report.create">
        <table class="inputTbl4Col">
            <tr>
                <td class="label"><tags:label key="MSG.generates.unit_code" required="true" /> </td>
                <td class="text">
                    <tags:imSearch codeVariable="reportStockUnImportForm.shopCode" nameVariable="reportStockUnImportForm.shopName"
                                   codeLabel="MSG.RET.032" nameLabel="MSG.RET.033"
                                   searchClassName="com.viettel.im.database.DAO.SaleManagmentDAO"
                                   searchMethodName="getListShop"
                                   elementNeedClearContent="reportStockUnImportForm.staffCode;reportStockUnImportForm.staffName"
                                   getNameMethod="getNameShop"
                                   roleList=""/>
                </td>
                <td class="label"><tags:label key="MSG.staff.code" /></td>
                <td class="text">
                    <tags:imSearch codeVariable="reportStockUnImportForm.staffCode" nameVariable="reportStockUnImportForm.staffName"
                                   codeLabel="MSG.RET.047" nameLabel="MSG.RET.048"
                                   searchClassName="com.viettel.im.database.DAO.SaleManagmentDAO"
                                   searchMethodName="getListStaff"
                                   otherParam="reportStockUnImportForm.shopCode"
                                   getNameMethod="getNameStaff"
                                   roleList=""/>

                </td>
            </tr>
            <tr>
                <td  class="label"><tags:label key="MSG.fromDate" required="true" /></td>
                <td  class="text">
                    <tags:dateChooser property="reportStockUnImportForm.fromDate" id="fromDate"/>
                </td>
                <td  class="label"><tags:label key="MSG.generates.to_date" required="true" /></td>
                <td  class="text">
                    <tags:dateChooser property="reportStockUnImportForm.toDate" id="toDate"/>
                </td>
            </tr>
            <tr>
                <td class ="label"><tags:label key="MSG.report.type" required="true" /> </td>
                <td colspan="1" class ="text">
                    <fieldset style="width:100%;">
                        <%--<s:radio name="reportStockUnImportForm.reportType" id ="reportType" list="#{'1':'Tổng hợp', '2':'Chi tiết'}" />--%>
                        <tags:imRadio name="reportStockUnImportForm.reportType" id="reportType"
                                      list="1:MSG.RET.115,2:MSG.RET.116"/>
                    </fieldset>
                </td>                
            </tr>
            <tr>
                <td colspan="4" align="center">
                    <tags:submit formId="reportStockUnImportForm"
                                 showLoadingText="true"
                                 cssStyle="width:80px;"
                                 targets="bodyContent"
                                 value="MSG.report"
                                 preAction="reportStockUnImportAction!exportReport.do" validateFunction="checkValidFields()"/>
                </td>
            </tr>
            <tr>
                <td colspan="4" align="center">
                    <tags:displayResult id="returnMsg" property="returnMsg" propertyValue="returnMsgValue" type="key"/>
                </td>
            </tr>
            <tr>
                <td colspan="4" align="center">
                    <s:if test="#request.filePath !=null && #request.filePath!=''">
                        <script>
                            window.open('${contextPath}/download.do?${fn:escapeXml(filePath)}', '', 'toolbar=yes,scrollbars=yes');
                            <%--window.open('${contextPath}<s:property escapeJavaScript="true"  value="#request.filePath"/>','','toolbar=yes,scrollbars=yes');
                                window.open('<s:property escapeJavaScript="true"  value="#request.filePath"/>','','toolbar=yes,scrollbars=yes');--%>
                        </script>
                        <div>
                            <a href="${contextPath}/download.do?${fn:escapeXml(filePath)}">
                                <tags:displayResult id="reportStockImportPendingMessage" property="reportStockImportPendingMessage" type="key"/>
                            </a>
                            <%--<a href='${contextPath}<s:property escapeJavaScript="true"  value="#request.filePath"/>' >Bấm vào đây để download nếu trình duyệt không tự động download về.</a>
                            <a href='<s:property escapeJavaScript="true"  value="#request.filePath"/>' ><s:text name="MSG.clickhere.to.download" /></a>--%>
                        </div>
                    </s:if>
                </td>
            </tr>
        </table>
    </tags:imPanel>
</s:form>


<script language="javascript">

    $('reportStockUnImportForm.shopCode').focus();

    checkValidFields = function() {
        var shopCode = document.getElementById("reportStockUnImportForm.shopCode");
        if(shopCode.value==null || shopCode.value.trim()==""){
    <%--$( 'returnMsg' ).innerHTML = "Chưa nhập mã đơn vị";--%>
                $( 'returnMsg' ).innerHTML='<s:text name="ERR.RET.035"/>';
                shopCode.focus();
                return false;
            }

            var dateExported= dojo.widget.byId("fromDate");
            if(dateExported.domNode.childNodes[1].value.trim() == ""){
    <%--$( 'returnMsg' ).innerHTML='Chưa nhập ngày báo cáo từ ngày';--%>
                $( 'returnMsg' ).innerHTML='<s:text name="ERR.RET.002"/>';
                $('fromDate').focus();
                return false;
            }
            if(!isDateFormat(dateExported.domNode.childNodes[1].value)){
    <%--$( 'returnMsg' ).innerHTML='Ngày báo cáo từ ngày không hợp lệ';--%>
                $( 'returnMsg' ).innerHTML='<s:text name="ERR.RET.003"/>';
                $('fromDate').focus();
                return false;
            }

            var dateExported1 = dojo.widget.byId("toDate");
            if(dateExported1.domNode.childNodes[1].value.trim() == ""){
    <%--$( 'returnMsg' ).innerHTML='Chưa nhập ngày báo cáo đến ngày';--%>
                $( 'returnMsg' ).innerHTML='<s:text name="ERR.RET.004"/>';
                $('toDate').focus();
                return false;
            }
            if(!isDateFormat(dateExported1.domNode.childNodes[1].value)){
    <%--$( 'returnMsg' ).innerHTML='Ngày báo cáo đến ngày không hợp lệ';--%>
                $( 'returnMsg' ).innerHTML='<s:text name="ERR.RET.005"/>';
                $('toDate').focus();
                return false;
            }
            if(!isCompareDate(dateExported.domNode.childNodes[1].value.trim(),dateExported1.domNode.childNodes[1].value.trim(),"VN")){
    <%--$( 'returnMsg' ).innerHTML='Ngày báo cáo từ phải nhỏ hơn Ngày báo cáo đến ngày';--%>
                $( 'returnMsg' ).innerHTML='<s:text name="ERR.RET.006"/>';
                $('fromDate').focus();
                return false;
            }

            return true;
        }
    
</script>
