<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : viewLookupSerialHistory
    Created on : Jan 29, 2010, 8:56:49 AM
    Author     : TheTM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
            //if (request.getAttribute("invoiceListHistory") == null) {
            //    request.setAttribute("invoiceListHistory", request.getSession().getAttribute("invoiceListHistory"));
            //}
            request.setAttribute("contextPath", request.getContextPath());
%>

<tags:imPanel title="MSG.FAC.find.serial">
    <!-- phan hien thi thong tin can tra cuu -->
    <div class="divHasBorder">
        <s:form action="lookupSerialAction!viewLookUpSerialHistory.do" onsubmit="return validateForm()" theme="simple" method="post" id="lookupSerialForm">
<s:token/>

            <table class="inputTbl4Col">                
                <tr>
                    <td style="width:15%; "><tags:label key="MSG.generates.goods" /></td>
                    <td class="oddColumn" style="width:30%; ">
                        <s:textfield name="lookupSerialForm.stockModelCode" id="lookupSerialForm.stockModelCode" maxlength="25" cssClass="txtInputFull" readonly="true"/>
                        <s:hidden name="lookupSerialForm.status" id="lookupSerialForm.status"/>
                    </td>
                    <td style="width:15%; "><tags:label key="MSG.generates.imsi" /></td>
                    <td class="oddColumn" style="width:30%; ">
                        <s:textfield name="lookupSerialForm.fromSerial" id="lookupSerialForm.fromSerial" maxlength="25" cssClass="txtInputFull" readonly="true"/>
                    </td>
                </tr>
                <tr>
                    <td><tags:label key="MSG.fromDate" required="true"/></td>
                    <td class="oddColumn">
                        <tags:dateChooser property="lookupSerialForm.fromDate" id="lookupSerialForm.fromDate"/>
                    </td>
                    <td><tags:label key="MSG.generates.to_date" required="true"/></td>
                    <td class="oddColumn">
                        <tags:dateChooser property="lookupSerialForm.toDate" id="lookupSerialForm.toDate"/>
                    </td>
                    <td>
                        <input type="submit"  value="<s:property escapeJavaScript="true"  value="getError('MSG.generates.find')" />" style="width: 80px; "/>
                        <%--
                                                <tags:submit formId="lookupSerialForm"
                                                             showLoadingText="true"
                                                             cssStyle="width:80px;"
                                                             targets="bodyContent"
                                                             value="MSG.generates.find"
                                                             preAction="lookupSerialAction!viewLookUpSerialHistory.do"/>--%>
                    </td>
                </tr>
                <tr>
                    <td colspan="5" align="center">
                        <tags:displayResult id="message" property="message" propertyValue="messageParam" type="key"/>
                    </td>
                </tr>
            </table>
        </s:form>
    </div>

    <div>
        <fieldset class="imFieldset">
            <legend>${fn:escapeXml(imDef:imGetText('MSG.FAC.find.serial.history'))}</legend>
            <div style="width:100%; height:350px; overflow:auto;">
                <display:table targets="displayTagFrame" name="invoiceListHistory"
                               id="tblSerialListHistory" class="dataTable"
                               cellpadding="1" cellspacing="1">
                    <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generate.order_no'))}" sortable="false" headerClass="tct" style="width:40px; text-align:center">
                        ${fn:escapeXml(tblSerialListHistory_rowNum)}
                    </display:column>
                    <display:column escapeXml="true" property="stockTransId" title="${fn:escapeXml(imDef:imGetText('MSG.transaction.code'))}" style="width:10px; text-align:center" />
                    <display:column escapeXml="true" property="stockTransType" title="${fn:escapeXml(imDef:imGetText('MSG.trade.type'))}" style="width:40px; text-align:center" />
                    <display:column escapeXml="true" property="exportStore" title="${fn:escapeXml(imDef:imGetText('MSG.fromStore'))}" />
                    <display:column escapeXml="true" property="importStore" title="${fn:escapeXml(imDef:imGetText('MSG.toStore'))}"/>
                    <display:column escapeXml="true" property="stockTransStatus" title="${fn:escapeXml(imDef:imGetText('MSG.FAC.TransStatus'))}"/>
                    <display:column escapeXml="true" property="userSerial" title="${fn:escapeXml(imDef:imGetText('MSG.FAC.SubmitUser'))}"/>
                    <display:column property="creatDate" title="${fn:escapeXml(imDef:imGetText('MSG.DET.036'))}" format="{0,date,dd/MM/yyyy HH:mm:ss}" sortable="false"  style="width:10%;text-align:center" headerClass="tct"/>
                </display:table>
            </div>
        </fieldset>
    </div>

</tags:imPanel>
<script>
    validateForm = function(){
        message= document.getElementById("message");
        var fromDate= dojo.widget.byId("lookupSerialForm.fromDate");
        if(fromDate.domNode.childNodes[1].value ==null || fromDate.domNode.childNodes[1].value==''){
            message.innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.UTY.028')"/>';
            //
            fromDate.domNode.childNodes[1].focus();
            return false;
        }
        if(!isDateFormat(fromDate.domNode.childNodes[1].value)){
            message.innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.UTY.030')"/>';
            fromDate.domNode.childNodes[1].focus();
            return false;
        }
        var toDate= dojo.widget.byId("lookupSerialForm.toDate");
        if(toDate.domNode.childNodes[1].value ==null || toDate.domNode.childNodes[1].value==''){
            message.innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.UTY.029')"/>';
            //
            toDate.domNode.childNodes[1].focus();
            return false;
        }
        if(!isDateFormat(toDate.domNode.childNodes[1].value)){
            message.innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.UTY.031')"/>';
            toDate.domNode.childNodes[1].focus();
            return false;
        }

        return true;
    }
</script>



