<%-- 
    Document   : ReportReceiveExpense
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
<s:form action="reportReceiveExpenseAction" theme="simple" method="post" id="form">
<s:token/>

    <tags:imPanel title="MSG.criterion.report.create">
        <table class="inputTbl4Col">            
            <tr>    
                <td style="width:10%; "><tags:label key="MSG.generates.unit_code" /></td>
                <td style="width:30%; " class="oddColumn" colspan="2">
                    <tags:imSearch codeVariable="ReportDepositForm.shopCode" nameVariable="ReportDepositForm.shopName"
                                   codeLabel="MSG.RET.045" nameLabel="MSG.RET.046"
                                   searchClassName="com.viettel.im.database.DAO.ReportRevenueDAO"
                                   searchMethodName="getListShop"/>
                </td>
            </tr>
            <tr>
                <td  class="label"><tags:label key="MSG.fromDate" /></td>
                <td  class="text">
                    <tags:dateChooser property="form.fromDate" id="fromDate"  cssStyle="txtInput"/>
                </td>
                <td  class="label"><tags:label key="MSG.generates.to_date" /></td>
                <td  class="text">
                    <tags:dateChooser property="form.toDate" id="toDate" cssStyle="txtInput"/>
                </td>
            </tr>
            <tr>
                <td  class="label"><tags:label key="MSG.report.type" /></td>
                <td colspan="3">
                    <fieldset style="width:100%;">
                        <%--<s:radio name="form.reportDetail"
                                 list="#{false:'Báo cáo tổng hợp &nbsp;&nbsp;', true:'Báo cáo chi tiết &nbsp;&nbsp;'}"/>--%>
                        <tags:imRadio name="form.reportDetail" id="reportDetail"
                                      list="false:MSG.RET.134,true:MSG.RET.127"/>

                    </fieldset>
                </td>
            </tr>
            <tr>
                <td colspan="4" align="center">
                    <tags:submit formId="form"
                                 showLoadingText="true"
                                 cssStyle="width:80px;"
                                 targets="bodyContent"
                                 value="MSG.report"
                                 preAction="reportReceiveExpenseAction!reportReceiveExpense.do" />
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
                            <%--<a href='<s:property escapeJavaScript="true"  value="#request.filePath"/>' <tags:label key="MSG.clickhere.to.download" /></a>--%>
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
    <%--$( 'displayResultMsgClient' ).innerHTML = "Chưa nhập mã chi nhánh/cửa hàng";--%>
                $( 'displayResultMsgClient' ).innerHTML='<s:text name="ERR.RET.038"/>';
                return false;
            }
            var dateExported= dojo.widget.byId("fromDate");
            if(dateExported.domNode.childNodes[1].value.trim() == ""){
    <%--$( 'displayResultMsgClient' ).innerHTML='Chưa nhập ngày báo cáo từ ngày';--%>
                $( 'displayResultMsgClient' ).innerHTML='<s:text name="ERR.RET.002"/>';
                $('fromDate').focus();
                return false;
            }
            if(!isDateFormat(dateExported.domNode.childNodes[1].value)){
    <%--$( 'displayResultMsgClient' ).innerHTML='Ngày báo cáo từ ngày không hợp lệ';--%>
                $( 'displayResultMsgClient' ).innerHTML='<s:text name="ERR.RET.003"/>';
                $('fromDate').focus();
                return false;
            }
        
            var dateExported1 = dojo.widget.byId("toDate");
            if(dateExported1.domNode.childNodes[1].value.trim() == ""){
    <%--$( 'displayResultMsgClient' ).innerHTML='Chưa nhập ngày báo cáo đến ngày';--%>
                $( 'displayResultMsgClient' ).innerHTML='<s:text name="ERR.RET.004"/>';
                $('toDate').focus();
                return false;
            }
            if(!isDateFormat(dateExported1.domNode.childNodes[1].value)){
    <%--$( 'displayResultMsgClient' ).innerHTML='Ngày báo cáo đến ngày không hợp lệ';--%>
                $( 'displayResultMsgClient' ).innerHTML='<s:text name="ERR.RET.005"/>';
                $('toDate').focus();
                return false;
            }
            if(!isCompareDate(dateExported.domNode.childNodes[1].value.trim(),dateExported1.domNode.childNodes[1].value.trim(),"VN")){
    <%--$( 'displayResultMsgClient' ).innerHTML='Ngày báo cáo từ phải nhỏ hơn Ngày báo cáo đến ngày';--%>
                $( 'displayResultMsgClient' ).innerHTML='<s:text name="ERR.RET.006"/>';
                $('fromDate').focus();
                return false;
            }
            return true;
        }
    
        dojo.event.topic.subscribe("form/autoSelectShop", function(value, key, text, widget){
            if(key!=null && value!=null){
                $('form.shopName').value=key;
            }
        
        });
    
</script>
