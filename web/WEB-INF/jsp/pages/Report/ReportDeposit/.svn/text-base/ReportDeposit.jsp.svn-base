<%-- 
    Document   : ReportDeposit
    Created on : Nov 28, 2009, 8:28:28 AM
    Author     : User
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


<!-- phan hien thi tieu chi bao cao -->
<s:form action="reportDepositAction" theme="simple" method="post" id="ReportDepositForm">
<s:token/>

    <tags:imPanel title="MSG.RET.034">
        <table class="inputTbl4Col">
            <tr>
                <%--<td >Mã chi nhánh (<font color="red">*</font>)</td>--%>
                <td><tags:label key="MSG.RET.041" required="true"/></td>
                <td >
                    <tags:imSearch codeVariable="ReportDepositForm.shopCodeBranch" nameVariable="ReportDepositForm.shopNameBranch"
                                   codeLabel="MSG.RET.041" nameLabel="MSG.RET.042"
                                   searchClassName="com.viettel.im.database.DAO.ReportDepositDAO"
                                   searchMethodName="getListBranch"
                                   elementNeedClearContent="ReportDepositForm.shopCode;ReportDepositForm.shopName"
                                   getNameMethod="getNameBranch"/>
                </td>
                <%--<td >Mã cửa hàng (<font color="red">*</font>)</td>--%>
                <td><tags:label key="MSG.RET.028" required="true"/></td>
                <td >
                    <tags:imSearch codeVariable="ReportDepositForm.shopCode" nameVariable="ReportDepositForm.shopName"
                                   codeLabel="MSG.RET.028" nameLabel="MSG.RET.029"
                                   searchClassName="com.viettel.im.database.DAO.ReportDepositDAO"
                                   searchMethodName="getListShop"
                                   otherParam="ReportDepositForm.shopCodeBranch"                              
                                   getNameMethod="getNameShop"/>
                </td>
            </tr>
            <tr>
                <%--<td  class="label">Từ ngày (<font color="red">*</font>)</td>--%>
                <td><tags:label key="MSG.RET.036" required="true"/></td>
                <td  class="text">
                    <tags:dateChooser property="ReportDepositForm.fromDate" id="fromDate"/>
                </td>
                <%--<td  class="label">Đến ngày (<font color="red">*</font>)</td>--%>
                <td><tags:label key="MSG.RET.037" required="true"/></td>
                <td  class="text">
                    <tags:dateChooser property="ReportDepositForm.toDate" id="toDate"/>
                </td>
                <td colspan="4">&nbsp</td>
            </tr>
            <tr>
                <td colspan="8">
                    <fieldset style="width:100%;">
                        <%--<s:radio name="ReportDepositForm.reportDetail" id = "reportDetail"
                                 list="#{'0':'Báo cáo thu tiền đặt cọc &nbsp;&nbsp;', '1':'Báo cáo chi tiền đặt cọc &nbsp;&nbsp;', '2':'Báo cáo thu chi theo KH &nbsp;&nbsp;', '3':'Báo cáo tổng hợp &nbsp;&nbsp;'}"/>--%>
                        <tags:imRadio name="ReportDepositForm.reportDetail" id="reportDetail"
                          list="0:MSG.RET.131,1:MSG.RET.132,2:MSG.RET.133,3:MSG.RET.134"/>
                                 
                    </fieldset>
                </td>
            </tr>
            <tr>
                <td colspan="8" align="center">
                    <tags:submit formId="ReportDepositForm"
                                 showLoadingText="true"
                                 cssStyle="width:80px;"
                                 targets="bodyContent"
                                 value="MSG.RET.040"
                                 preAction="reportDepositAction!exportReportDepositReport.do" validateFunction="checkValidFields()"/>
                </td>
            </tr>
            <tr>
                <td colspan="8" align="center">
                    <tags:displayResult id="displayResultMsgClient" property="displayResultMsgClient" propertyValue="returnMsgValue" type="key"/>                    
                </td>                
            </tr>
            <tr>
                <td colspan="8" align="center">
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
                            <%--<a href='<s:property escapeJavaScript="true"  value="#request.filePath"/>' >Bấm vào đây để download nếu trình duyệt không tự động download về.</a>--%>
                            <%--<div><a href='<s:property escapeJavaScript="true"  value="#request.filePath"/>'><tags:label key="MSG.clickhere.to.download" /></a></div>--%>
                        </div>
                    </s:if>
                </td>
            </tr>
        </table>
    </tags:imPanel>
</s:form>




<script language="javascript">
    //$('ReportDepositForm.shopCodeBranch').focus();
    checkValidFields = function() {                                 
        var shopCodeBranch = document.getElementById("ReportDepositForm.shopCodeBranch");                        
        if(shopCodeBranch.value==null || shopCodeBranch.value.trim()==""){            
            <%--$( 'displayResultMsgClient' ).innerHTML = "Chưa nhập mã chi nhánh";--%>
            $('displayResultMsgClient').innerHTML='<s:text name="ERR.RET.032"/>';
            shopCodeBranch.focus();
            return false;
        }                        
        //var typeReport = document.getElementById("reportDetail");  
        //alert(typeReport.value);       
        //&& (typeReport.value ="0" || typeReport.value = "1")         
        var i = 0;
        for(i = 0; i < 4; i=i+1) {
            var radioId = "reportDetail" + i;
            if($(radioId).checked == true) {
                break;
            }
        }        
        var _myWidget1 = document.getElementById("ReportDepositForm.shopCode");                
        if((_myWidget1.value==null || _myWidget1.value.trim()=="") && (i == 0 || i == 1) ){            
            <%--$( 'displayResultMsgClient' ).innerHTML = "Chưa nhập mã cửa hàng";--%>
            $('displayResultMsgClient').innerHTML='<s:text name="ERR.RET.033"/>';
            _myWidget1.focus();
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
            <%--$( 'displayResultMsgClient' ).innerHTML='Ngày báo cáo từ phải nhỏ hơn ngày báo cáo đến ngày';--%>
            $('displayResultMsgClient').innerHTML='<s:text name="ERR.RET.006"/>';
            $('fromDate').focus();
            return false;
        }
        return true;
    }
    
    dojo.event.topic.subscribe("ReportDepositForm/autoSelectShop", function(value, key, text, widget){
        if(key!=null && value!=null){
            $('ReportDepositForm.shopName').value=key;
        }
        
    });
    
    </script>
