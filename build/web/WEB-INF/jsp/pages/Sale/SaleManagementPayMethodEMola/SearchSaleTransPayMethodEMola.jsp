<%-- 
    Document   : SearchSaleTransPayMethodEMola
    Created on : Aug 3, 2016, 4:34:05 PM
    Author     : mov_itbl_dinhdc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
            request.setAttribute("contextPath", request.getContextPath());

%>

<tags:imPanel title="SEARCH_SALE_TRANS_EMOLA">
    <s:form action="saleTransPayMethodEMolaAction" theme="simple" method="post" id="saleTransEmolaForm">
        <s:token/>
        
        <table class="inputTbl6Col">
            <tr>
                <td class="label"><tags:label key="MSG.SDT.EMola" required="false" required="true"/></td>
                <td class="text">
                    <s:textfield name="saleTransEmolaForm.isdnEmola" id="saleTransEmolaForm.isdnEmola" maxlength="10" cssClass="txtInputFull"/>
                </td>
                <td class="label"><tags:label key="MSG.SaleTrans.Code"/></td>
                <td class="text">
                    <s:textfield name="saleTransEmolaForm.saleTransCode" id="saleTransEmolaForm.saleTransCode" maxlength="100" cssClass="txtInputFull"/>
                </td>
                <td class="label"><tags:label key="MSG.CustName"/></td>
                <td class="text">
                    <s:textfield name="saleTransEmolaForm.custName" id="saleTransEmolaForm.tin" maxlength="100" cssClass="txtInputFull"/>
                </td>
            </tr>
            <tr>
                <td  class="label"> <tags:label key="MSG.order.date.from" required="true"/></td>
                <td colspan="3" >
                    <tags:dateChooser property="saleTransEmolaForm.fromDate" id="saleTransEmolaForm.fromDate"  styleClass="txtInputFull"/>
                </td>
                <td  class="label"> <tags:label key="MSG.order.date.to" required="true"/></td>
                <td>
                    <tags:dateChooser property="saleTransEmolaForm.toDate" id="saleTransEmolaForm.toDate" styleClass="txtInputFull"/>
                </td>
            </tr>
            <tr>
                <td colspan="6">
                    <tags:displayResult id="message" property="message" propertyValue="messageParam" type="key" />
                </td>
            </tr>
        </table>
        <br />        
        <div style="width:100%" align="center">
            <tags:submit targets="bodyContent" formId="saleTransEmolaForm"
                         value="MSG.find"
                         cssStyle="width:80px;"
                         preAction="saleTransPayMethodEMolaAction!searchSaleTransEmola.do"
                         showLoadingText="true"
                         validateFunction="validateFormSearch()"/>
        </div>
         
        <div>
            <fieldset class="imFieldset">
                <legend>${fn:escapeXml(imDef:imGetText('MSG.SaleTransPayMethodEmola.list'))}</legend>
                <sx:div id="ResultSearch">
                    <jsp:include page="ListSaleTransPayMethodEMola.jsp"/>
                </sx:div>
            </fieldset>
        </div>        
    </s:form>
</tags:imPanel>
<script type="text/javascript" language="javascript">
    validateFormSearch = function(){
        var isdnEmola = document.getElementById("saleTransEmolaForm.isdnEmola");
        if (trim(isdnEmola.value).length == 0){
                <%--"Bạn chưa nhap isdn"--%>
                $('message').innerHTML = '<s:property value="getText('ERR.SIK.002')"/>';
                isdnEmola.focus();
                return false;
         }
            
        if (trim(isdnEmola.value).length != 0){
            if(!isNumberFormat(isdnEmola.value)){
                $('message').innerHTML = '<s:property value="getText('ERR.CHL.158')"/>';
                isdnEmola.focus();
                return false;
            }
        }   
        
         var fromDate= dojo.widget.byId("saleTransEmolaForm.fromDate");
         var toDate = dojo.widget.byId("saleTransEmolaForm.toDate");
            if(fromDate.domNode.childNodes[1].value.trim() == ""){
    <%--$( 'displayResultMsgClient' ).innerHTML='Chưa nhập ngày báo cáo từ ngày';--%>
                $( 'message' ).innerHTML='<s:text name="ERR.RET.002"/>';
                $('saleTransEmolaForm.fromDate').focus();
                return false;
            }
            if(!isDateFormat(fromDate.domNode.childNodes[1].value)){
    <%--$( 'displayResultMsgClient' ).innerHTML='Ngày báo cáo từ ngày không hợp lệ';--%>
                $( 'message' ).innerHTML='<s:text name="ERR.RET.003"/>';
                $('saleTransEmolaForm.fromDate').focus();
                return false;
            }        
            if(toDate.domNode.childNodes[1].value.trim() == ""){
    <%--$( 'displayResultMsgClient' ).innerHTML='Chưa nhập ngày báo cáo đến ngày';--%>
                $( 'message' ).innerHTML='<s:text name="ERR.RET.004"/>';
                $('saleTransEmolaForm.fromDate').focus();
                return false;
            }
            if(!isDateFormat(toDate.domNode.childNodes[1].value)){
    <%--$( 'displayResultMsgClient' ).innerHTML='Ngày báo cáo đến ngày không hợp lệ';--%>
                $( 'message' ).innerHTML='<s:text name="ERR.RET.005"/>';
                $('saleTransEmolaForm.fromDate').focus();
                return false;
            }
            if(!isCompareDate(fromDate.domNode.childNodes[1].value.trim(),toDate.domNode.childNodes[1].value.trim(),"VN")){
    <%--$( 'displayResultMsgClient' ).innerHTML='Ngày báo cáo từ phải nhỏ hơn Ngày báo cáo đến ngày';--%>
                $( 'message' ).innerHTML='<s:text name="ERR.RET.006"/>';
                $('saleTransEmolaForm.fromDate').focus();
                return false;
            }
        return true;
    }
</script>