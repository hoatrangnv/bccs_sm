<%-- 
    Document   : ManagementActionOfUser
    Created on : Jun 30, 2016, 2:23:55 PM
    Author     : mov_itbl_dinhdc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
%>
<tags:imPanel title="MENU.REPORT.MANAGEMENT.ACTION.USER">
<s:form action="reportManagementActionOfUserAction" theme="simple" method="post" id="managementActionOfUserForm">
    <s:token/>

    
        <div>
            <tags:displayResult id="displayResultMsg" property="displayResultMsg" propertyValue="displayResultParam" type="key"/>
        </div>

        <table class="inputTbl4Col">
            <tr>
                <td  class="label"><tags:label key="MSG.fromDate" required="true" /> </td>
                <td  class="text">
                    <tags:dateChooser property="managementActionOfUserForm.fromDate" id="managementActionOfUserForm.fromDate"/>
                </td>
                <td  class="label"><tags:label key="MSG.generates.to_date" required="true" /> </td>
                <td  class="text">
                    <tags:dateChooser property="managementActionOfUserForm.toDate" id="managementActionOfUserForm.toDate"/>
                </td>
            </tr>
            
            <tr>
                <td style="width:15%"><tags:label key="MSG.report.type" required="true" /></td>
                <td class="oddColumn" style="width:25%">
                    <tags:imSelect name="managementActionOfUserForm.typeReport"
                                   id="managementActionOfUserForm.typeReport"
                                   cssClass="txtInputFull"
                                   headerKey="" headerValue="L.200013"
                                   list="0:Type.Staff.Movitel,
                                       1:Type.Channel"/>
                </td>
            </tr>

            <tr>
                <td colspan="4" align="center">
                    <tags:submit formId="managementActionOfUserForm"
                                 showLoadingText="true"
                                 cssStyle="width:80px;"
                                 targets="bodyContent"
                                 validateFunction = "checkValidFields()"
                                 value="MSG.report"
                                 preAction="reportManagementActionOfUserAction!exportReport.do"/>
                </td>
            </tr>
            
            <tr>
                <td colspan="4" align="center">
                    <s:if test="#request.reportPath !=null && #request.reportPath!=''">
                        <script>
                            window.open('${contextPath}/download.do?${fn:escapeXml(reportPath)}', '', 'toolbar=yes,scrollbars=yes');
                        </script>
                        <div>
                            <a href="${contextPath}/download.do?${fn:escapeXml(reportPath)}">
                                    <tags:displayResult id="reportMessage" property="reportMessage" type="key"/>
                            </a>
                        </div>
                    </s:if>
                </td>
            </tr>
        </table>
    
</s:form>
</tags:imPanel>


<script language="javascript">
    checkValidFields = function(){
        
        var typeReport = document.getElementById("managementActionOfUserForm.typeReport");
            if (trim(typeReport.value).length == 0){
                <%--"Bạn chưa mã Shop"--%>
                $('displayResultMsg').innerHTML = '<s:property value="getText('error.not.select.unit.export.competitor')"/>';
                typeReport.focus();
                return false;
            }
          var dateExported= dojo.widget.byId("managementActionOfUserForm.fromDate");
            if(dateExported.domNode.childNodes[1].value.trim() == ""){
    <%--$( 'displayResultMsgClient' ).innerHTML='Chưa nhập ngày báo cáo từ ngày';--%>
                $('displayResultMsg').innerHTML = '<s:property value="getText('ERR.RET.002')"/>';
                $('managementActionOfUserForm.fromDate').focus();
                return false;
            }
            if(!isDateFormat(dateExported.domNode.childNodes[1].value)){
    <%--$( 'displayResultMsgClient' ).innerHTML='Ngày báo cáo từ ngày không hợp lệ';--%>
                $('displayResultMsg').innerHTML='<s:property value="getText('ERR.RET.003')"/>';
                $('managementActionOfUserForm.fromDate').focus();
                return false;
            }        
          var toDateExported = dojo.widget.byId("managementActionOfUserForm.toDate");
            if(toDateExported.domNode.childNodes[1].value.trim() == ""){
    <%--$( 'displayResultMsgClient' ).innerHTML='Chưa nhập ngày báo cáo đến ngày';--%>
                $('displayResultMsg').innerHTML='<s:property value="getText('ERR.RET.004')"/>';
                $('managementActionOfUserForm.toDate').focus();
                return false;
            }
            if(!isDateFormat(toDateExported.domNode.childNodes[1].value)){
    <%--$( 'displayResultMsgClient' ).innerHTML='Ngày báo cáo đến ngày không hợp lệ';--%>
                $('displayResultMsg').innerHTML='<s:property value="getText('ERR.RET.005')"/>/>';
                $('managementActionOfUserForm.toDate').focus();
                return false;
            }
            if(!isCompareDate(dateExported.domNode.childNodes[1].value.trim(),toDateExported.domNode.childNodes[1].value.trim(),"VN")){
    <%--$( 'displayResultMsgClient' ).innerHTML='Ngày báo cáo từ phải nhỏ hơn Ngày báo cáo đến ngày';--%>
                $('displayResultMsg').innerHTML='<s:property value="getText('ERR.RET.002')"/>';
                $('managementActionOfUserForm.fromDate').focus();
                return false;
            }  
             if(days_between(dateExported.domNode.childNodes[1].value.trim(),toDateExported.domNode.childNodes[1].value.trim()) > 31){
    <%--$( 'displayResultMsgClient' ).innerHTML='Ngày báo cáo từ phải nhỏ hơn Ngày báo cáo đến ngày';--%>
                $('displayResultMsg').innerHTML='<s:property value="getText('E.200078')"/>';
                $('managementActionOfUserForm.toDate').focus();
                return false;
            }  
                
        return true;
    }


</script>

