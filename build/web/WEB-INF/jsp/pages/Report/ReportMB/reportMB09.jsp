<%-- 
    Document   : reportMB09
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
<s:form action="reportMB09Action" theme="simple" method="post" id="form">
<s:token/>

    <tags:imPanel title="MSG.criterion.report.create">
        <table class="inputTbl4Col">                        
            <tr>
                <td  class="label"><tags:label key="MSG.fromDate" required="true" /></td>
                <td  class="text">
                    <tags:dateChooser property="form.fromDate" id="fromDate"  cssStyle="txtInput"/>
                </td>       
                <td  class="label"><tags:label key="MSG.generates.to_date" required="true" /></td>
                <td  class="text">
                    <tags:dateChooser property="form.toDate" id="toDate"  cssStyle="txtInput"/>
                </td>                  
            </tr>                       
            <tr>
                <td class="label"><tags:label key="MSG.transaction.type.upgrade_report" required="true" /></td>
                <td>
                    <s:select name = "form.reportType" id = "reportType" list= "#{'1':'Giao dịch thực trên SALE và MB'
                    ,'2':'Giao dịch thực hiện trên MB','3':'Cả 2 loại trên'}"
                              HeaderKey="0" headerValue="--Báo cáo hủy giao dịch--"/>
                </td>
                <td align="center">
                    <tags:submit formId="form"
                                 showLoadingText="true"
                                 cssStyle="width:80px;"
                                 targets="bodyContent"
                                 value="MSG.report"
                                 preAction="reportMB09Action!reportMB09.do" />
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
                            window.open('${contextPath}<s:property escapeJavaScript="true"  value="#request.filePath"/>','','toolbar=yes,scrollbars=yes');
                        </script>
                        <div><a href='${contextPath}<s:property escapeJavaScript="true"  value="#request.filePath"/>' ><tags:label key="MSG.clickhere.to.download" /></a></div>
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
            $( 'displayResultMsgClient' ).innerHTML = "Chưa nhập mã cửa hàng/đại lý";
            return false;
        }
        var dateExported= dojo.widget.byId("fromDate");
        if(dateExported.domNode.childNodes[1].value.trim() == ""){
            $( 'displayResultMsgClient' ).innerHTML='Chưa nhập ngày báo cáo từ';
            $('fromDate').focus();
            return false;
        }
        if(!isDateFormat(dateExported.domNode.childNodes[1].value)){
            $( 'displayResultMsgClient' ).innerHTML='Ngày báo cáo từ không hợp lệ';
            $('fromDate').focus();
            return false;
        }
        
        var dateExported1 = dojo.widget.byId("toDate");
        if(dateExported1.domNode.childNodes[1].value.trim() == ""){
            $( 'displayResultMsgClient' ).innerHTML='Chưa nhập ngày báo cáo đến';
            $('toDate').focus();
            return false;
        }
        if(!isDateFormat(dateExported1.domNode.childNodes[1].value)){
            $( 'displayResultMsgClient' ).innerHTML='Ngày báo cáo đến không hợp lệ';
            $('toDate').focus();
            return false;
        }
        if(!isCompareDate(dateExported.domNode.childNodes[1].value.trim(),dateExported1.domNode.childNodes[1].value.trim(),"VN")){
            $( 'displayResultMsgClient' ).innerHTML='Ngày báo cáo từ phải nhỏ hơn Ngày báo cáo đến';
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
