<%-- 
    Document   : reportRenvenueVipCustomer
    Created on : Mar 25, 2015, 8:32:35 AM
    Author     : Toancx
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@page import="java.util.*"%>

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
    request.setAttribute("contextPath", request.getContextPath());
%>
<s:form action="reportRenvenueVipCustomerAction" theme="simple" method="post" id="reportForm">
<s:token/>

    <tags:imPanel title="reportRenvenueVipCustomer">
        <table class="inputTbl4Col">
            <tr>
                <td class ="label"><tags:label key="MSG.generates.unit_code" required="true" /> </td>
                <td colspan="1" class="text">
                    <tags:imSearch codeVariable="reportForm.shopCode" nameVariable="reportForm.shopName"
                                   codeLabel="MSG.RET.032" nameLabel="MSG.RET.033"
                                   searchClassName="com.viettel.im.database.DAO.ReportRevenueVipCustomerDAO"
                                   elementNeedClearContent="reportForm.staffCode;reportForm.staffName"
                                   searchMethodName="getListShop"
                                   roleList=""/>
                </td>
                <td class="label"><tags:label key="MSG.staff.code" required="false"/></td>
                <td colspan="1" class="text">
                    <tags:imSearch codeVariable="reportForm.staffCode" nameVariable="reportForm.staffName"
                                   codeLabel="MSG.GOD.007" nameLabel="MES.CHL.033"
                                   searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                   searchMethodName="getListStaff"
                                   getNameMethod="getStaffName"
                                   otherParam="reportForm.shopCode" 
                                   />
                </td>
            </tr>
            <tr>
                <td  class="label"><tags:label key="MSG.custMobile" required="false" /></td>
                <td class="text">
                <s:textfield name="reportForm.custMobile" id="reportForm.custMobile" maxlength="20" />
                </td>
            </tr>
            <tr>
                <td  class="label"><tags:label key="MSG.fromDate" required="true" /></td>
                <td  class="text">
                    <tags:dateChooser property="reportForm.fromDate" id="fromDate"/>
                </td>
                <td  class="label"><tags:label key="MSG.generates.to_date" required="true" /></td>
                <td  class="text">
                    <tags:dateChooser property="reportForm.toDate" id="toDate"/>
                </td>
            </tr>

            <tr>
                <td colspan="4" align="center">
                    <tags:submit formId="reportForm"
                                 showLoadingText="true"
                                 cssStyle="width:80px;"
                                 targets="bodyContent"
                                 validateFunction = "checkValidFields()"
                                 value="MSG.report"
                                 preAction="reportRevenueVipCustomerAction!exportReport.do"/>
                </td>
            </tr>
            <tr>
                <td colspan="4" align="center">
                    <tags:displayResult id="displayResultMsgClient" property="displayResultMsgClient" propertyValue="returnMsgValue" type="key"/>
                </td>                
            </tr>
            <tr>
                <td colspan="4" align="center">
                    <s:if test="#request.filePath !=null && #request.filePath!=''">
                        <script>
                            <%--window.open('${contextPath}<s:property escapeJavaScript="true"  value="#request.filePath"/>','','toolbar=yes,scrollbars=yes');--%>                                
                                window.open('${fn:escapeXml(contextPath)}/download.do?<s:property value="#request.filePath"/>', '', 'toolbar=yes,scrollbars=yes');
                        </script>
                        <div>
                            <%--<a href='${contextPath}<s:property escapeJavaScript="true"  value="#request.filePath"/>' >Bấm vào đây để download nếu trình duyệt không tự động download về.</a>--%>                            
                            <a href='${fn:escapeXml(contextPath)}/download.do?<s:property value="#request.filePath"/>' ><s:property escapeJavaScript="true"  value="%{getText('MSG.clickhere.to.download')}"/>
                        </div>
                    </s:if>
                </td>
            </tr>
        </table>
    </tags:imPanel>
</s:form>


<script language="javascript">
    
    $('reportForm.shopCode').focus();

    checkValidFields = function() {
        var shopCode= document.getElementById("reportForm.shopCode");
        if(shopCode.value==""){
            $( 'displayResultMsgClient' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.ISN.028')"/>';
            shopCode.focus();
            return false;
        }
            
        var dateExported= dojo.widget.byId("fromDate");
        if(dateExported.domNode.childNodes[1].value.trim() == ""){
                $( 'displayResultMsgClient' ).innerHTML='<s:text name="ERR.UTY.028"/>';
                $('fromDate').focus();
                return false;
        }
        
        dateExported = dojo.widget.byId("toDate");
        if(!isDateFormat(dateExported.domNode.childNodes[1].value)){
            $( 'displayResultMsgClient' ).innerHTML='<s:text name="ERR.UTY.029"/>';
            $('fromDate').focus();
            return false;
        }
        
        var fromDate = dojo.widget.byId("fromDate");
        var toDate= dojo.widget.byId("toDate");
        if(!isCompareDate(fromDate.domNode.childNodes[1].value.trim(),toDate.domNode.childNodes[1].value.trim(),"VN")){
            $('displayResultMsgClient').innerHTML='<s:text name="ERR.RET.006"/>';
            $('fromDate').focus();
            return false;
        }
        return true;
    }       

</script>

