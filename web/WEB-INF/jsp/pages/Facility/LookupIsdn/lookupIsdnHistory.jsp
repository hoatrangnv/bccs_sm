<%-- 
    Document   : lookupIsdnHistory
    Created on : Sep 10, 2010, 8:51:57 AM
    Author     : TheTM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
        request.setAttribute("contextPath", request.getContextPath());
%>

<tags:imPanel title="MSG.find.isdn.history">
    <!-- phan hien thi thong tin message -->
    <div>
        <tags:displayResult property="message" id="message" propertyValue="messageParam" type="key"/>
    </div>

    <!-- phan hien thi thong tin can tra cuu -->
    <div class="divHasBorder">
        <s:form action="lookupIsdnHistoryAction" theme="simple" method="post" id="lookupIsdnHistoryForm">
<s:token/>

            <table class="inputTbl6Col">
                <tr>
                    <td><tags:label key="MSG.generates.service"/></td>
                    <td class="oddColumn">
                        <tags:imSelect name="lookupIsdnHistoryForm.stockTypeId"
                                       id="stockTypeId"
                                       cssClass="txtInputFull"
                                       list="1:MSG.Mobile,2:MSG.Homephone,3:MSG.Pstn"/>
                    </td>
                    <td><tags:label key="MSG.isdnLookup" required="true"/></td>
                    <td>
                        <s:textfield name="lookupIsdnHistoryForm.isdn" id="isdn" maxlength="25" cssClass="txtInputFull"/>
                    </td>
                    <td><tags:label key="MSG.columnName"/></td>
                    <td>
                        <tags:imSelect name="lookupIsdnHistoryForm.columnName"
                                       id="columnName"
                                       cssClass="txtInputFull"
                                       list="PRICE:MSG.Price,OWNER_ID:MSG.OwnerId"/>
                    </td>
                </tr>
                <tr>
                    <td><tags:label key="MSG.date.start" required="true"/></td>
                    <td>
                        <tags:dateChooser property="lookupIsdnHistoryForm.startDate" id="startDate" styleClass="txtInputFull" />
                    </td>
                    <td><tags:label key="MSG.date.end"/></td>
                    <td>
                        <tags:dateChooser property="lookupIsdnHistoryForm.endDate" styleClass="txtInputFull" />
                    </td>
                    <td><tags:label key="MSG.actionUser"/></td>
                    <td>
                        <s:textfield name="lookupIsdnHistoryForm.actionUser" id="isdn" maxlength="25" cssClass="txtInputFull"/>
                    </td>
                </tr>
            </table>
        </s:form>

        <!-- phan nut tac vu -->
        <div align="center" style="padding-top:3px;">
            <tags:submit formId="lookupIsdnHistoryForm"
                         showLoadingText="true"
                         validateFunction="checkValidFields();"
                         cssStyle="width:80px;"
                         targets="bodyContent"
                         value="MSG.generates.find"
                         preAction="lookupIsdnHistoryAction!lookupIsdnHistory.do"/>
            <tags:submit formId="lookupIsdnHistoryForm"
                         showLoadingText="true"
                         validateFunction="checkValidFields();"
                         cssStyle="width:80px;"
                         targets="bodyContent"
                         value="MSG.report"
                         preAction="lookupIsdnHistoryAction!exportIsdnHistory.do"/>
        </div>
    </div>

    <s:if test="#request.listLookupIsdnHistory != null">
        <div>
            <jsp:include page="listLookupIsdnHistory.jsp"/>
        </div>
    </s:if>

</tags:imPanel>

<s:if test="#request.listLookupIsdnHistory != null && #request.listLookupIsdnHistory.size() > 0">
    <s:if test="lookupIsdnHistoryForm.pathExpFile!=null && lookupIsdnHistoryForm.pathExpFile!=''">
        <script>
            window.open('${contextPath}<s:property escapeJavaScript="true"  value="lookupIsdnHistoryForm.pathExpFile"/>','','toolbar=yes,scrollbars=yes');
        </script>
        <div><a href='${contextPath}<s:property escapeJavaScript="true"  value="lookupIsdnHistoryForm.pathExpFile"/>' ><tags:label key="MSG.download2.file.here"/></a></div>
    </s:if>
</s:if>

<script language="javascript">

     $('stockTypeId').focus();
    checkValidFields = function() {
        var isdn = trim($('isdn').value);
        if(trim($('isdn').value) == "") {
//            $('message').innerHTML ='<s:property escapeJavaScript="true"  value="getError('ERR.FAC.ISDN.029')"/>';
            $('message').innerHTML ='<s:text name="ERR.FAC.ISDN.029"/>';
            $('isdn').select();
            $('isdn').focus();
            return false;
        }

        var wgStartDate= dojo.widget.byId("startDate");
        if(trim(wgStartDate.domNode.childNodes[1].value) == "") {
//            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.FAC.ISDN.030')"/>';
            $('message').innerHTML = '<s:text name="ERR.FAC.ISDN.030"/>';
            wgStartDate.domNode.childNodes[1].select();
            wgStartDate.domNode.childNodes[1].focus();
            return false;
        }

        if(!isInteger(trim(isdn))) {
//            $('message').innerHTML ='<s:property escapeJavaScript="true"  value="getError('ERR.FAC.ISDN.031')"/>';
            $('message').innerHTML ='<s:text name="ERR.FAC.ISDN.031"/>';
            $('isdn').select();
            $('isdn').focus();
            return false;
        }

        return true;
    }

</script>


