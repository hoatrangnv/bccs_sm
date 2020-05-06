<%-- 
    Document   : assignIsdnPrice
    Cap nhap gia so dep
    Created on : 10/06/2009
    Author     : ThanhNC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@ taglib uri="VTdisplaytaglib" prefix="display"%>

<%

            request.setAttribute("contextPath", request.getContextPath());
%>
<s:form action="assignIsdnPriceAction!assignIsdnPrice" theme="simple"  enctype="multipart/form-data" method="post" id="assingIsdnPriceForm">
<s:token/>

    <tags:imPanel title="MSG.import.info">

        <table class="inputTbl6Col" style="width:100%">
            <tr>
                <td class="label"><tags:label key="MSG.ISN.037" required="true"/></td>
                <td class="text">

                    <tags:imSelect name="assignIsdnPriceForm.stockTypeId"
                              id="assignIsdnPriceForm.stockTypeId"
                              cssClass="txtInputFull"
                              headerKey="" headerValue="MSG.GOD.271"
                              list="1:MSG.mobileNumber,2:MSG.homephoneNumber,3:MSG.pstnNumber"/>
                </td>
                <td class="label"><tags:label key="MSG.attachmentFile" required="true"/></td>
                <td class="text">
                    <s:file id="assignIsdnPriceForm.impFile" name="assignIsdnPriceForm.impFile" size="60"/>
                </td>
                <td class="text">
                    <a onclick=downloadPatternFile()><tags:label key="MSG.download.file.here" required="true"/></a>
                </td>
                <td class="text">
                    <span style="padding:0px; margin:0px;" id="spanInput">
                        <input type="button" onclick="assignIsdnPrice()" value="<s:text name="MSG.ISN.015"/>" style="width:100px;"/>
                    </span>
                </td>

            </tr>

            <tr>
                <td colspan="5" align="center">
                    <tags:displayResult id="resultAssignIsdnPriceClient" property="resultAssignIsdnPrice" type="key"/>
                </td>
            </tr>
            <tr>
                <td colspan="5" align="center">

                    <s:if test="assignIsdnPriceForm.pathLogFile!=null && assignIsdnPriceForm.pathLogFile!=''">
                        <script>
                            window.open('${contextPath}<s:property escapeJavaScript="true"  value="assignIsdnPriceForm.pathLogFile"/>','','toolbar=yes,scrollbars=yes');
                        </script>
                        <div><a href='${contextPath}<s:property escapeJavaScript="true"  value="assignIsdnPriceForm.pathLogFile"/>' > <tags:label key="MSG.download2.file.here" required="true"/></a></div>
                    </s:if>
                </td>
            </tr>
        </table>
    </tags:imPanel>

</s:form>
<script type="text/javascript" language="javascript">
    blurSXSubmit = function() {
        var btn = $('spanInput').getElementsByTagName('input')[0];
        btn.blur();
    }
    
    validateForm=function(){
        var stockTypeId=$('assignIsdnPriceForm.stockTypeId');
        var impFile=$('assignIsdnPriceForm.impFile');
        if(stockTypeId.value==null || stockTypeId.value == "") {
            $('resultAssignIsdnPriceClient').innerHTML='<s:text name="assignIsdnPrice.error.noStockType"/>';
            stockTypeId.focus();
            return false;
        }        
        if(impFile.value==null || impFile.value == "") {
            $('resultAssignIsdnPriceClient').innerHTML='<s:text name="assignIsdnPrice.error.noFileAttachment"/>';
            impFile.focus();
            return false;
        }

        return true;
    }
    assignIsdnPrice = function ()
    {
        if(validateForm()){
            initProgress();
            // TuTM1 04/03/2012 Fix ATTT CSRF
            document.getElementById("assingIsdnPriceForm").action="assignIsdnPriceAction!assignIsdnPrice.do";
            document.getElementById("assingIsdnPriceForm").submit();
        }

    }
    downloadPatternFile = function() {
        window.open('${contextPath}/share/pattern/assignIsdnPricePattern.xls','','toolbar=yes,scrollbars=yes');
    }


</script>
