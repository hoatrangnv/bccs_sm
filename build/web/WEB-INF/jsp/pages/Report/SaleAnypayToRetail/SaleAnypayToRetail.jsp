<%-- 
    Document   : SaleAnypayToRetail
    Created on : Feb 7, 2012, 3:07:01 PM
    Author     : haint
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@page import="java.util.*"%>

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>

<%
    request.setAttribute("contextPath", request.getContextPath());
%>
<s:form action="ReportSaleAnypayToRetailAction" theme="simple" method="post" id="buyAndNotBuyForm">
<s:token/>

    <tags:imPanel title="reportSaleAnypayToRetail">
        <table class="inputTbl4Col">
            <tr>
                <td class ="label"><tags:label key="MSG.generates.unit_code" required="true" /> </td>
                <td colspan="1" class="text">
                    <tags:imSearch codeVariable="buyAndNotBuyForm.shopCode" nameVariable="buyAndNotBuyForm.shopName"
                                   codeLabel="MSG.RET.032" nameLabel="MSG.RET.033"
                                   searchClassName="com.viettel.im.database.DAO.ReportAccountAgentDAO"
                                   searchMethodName="getListShop"
                                   roleList=""/>
                </td>
                <td class="label"><tags:label key="MSG.staff.code" required="false"/></td>
                <td colspan="1" class="text">
                    <tags:imSearch codeVariable="buyAndNotBuyForm.staffCode" nameVariable="buyAndNotBuyForm.staffName"
                                   codeLabel="MSG.GOD.007" nameLabel="MES.CHL.033"
                                   searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                   searchMethodName="getListStaff"
                                   getNameMethod="getStaffName"
                                   otherParam="buyAndNotBuyForm.shopCode" 
                                   />
                </td>
            </tr>
            <tr>
                <td  class="label"><tags:label key="MSG.saleMethod" required="false" /></td>
                <td class="text">
                <tags:imSelect name="buyAndNotBuyForm.saleType"
                                   id="buyAndNotBuyForm.buyingModelId"
                                   cssClass="txtInputFull"
                                   theme="simple"
                                   headerKey="" headerValue="MSG.SAE.163"
                                   list="1:BCCS, 2:SIMTOOLKIT"/>
                </td>
            </tr>
            <tr>
                <td  class="label"><tags:label key="MSG.fromDate" required="true" /></td>
                <td  class="text">
                    <tags:dateChooser property="buyAndNotBuyForm.fromDate" id="fromDate"/>
                </td>
                <td  class="label"><tags:label key="MSG.generates.to_date" required="true" /></td>
                <td  class="text">
                    <tags:dateChooser property="buyAndNotBuyForm.toDate" id="toDate"/>
                </td>
            </tr>

            <tr>
                <td colspan="4" align="center">
                    <tags:submit formId="buyAndNotBuyForm"
                                 showLoadingText="true"
                                 cssStyle="width:80px;"
                                 targets="bodyContent"
                                 validateFunction = "checkValidFields()"
                                 value="MSG.report"
                                 preAction="ReportSaleAnypayToRetailAction!exportReport.do"/>
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
                                window.open('<s:property escapeJavaScript="true"  value="#request.filePath"/>','','toolbar=yes,scrollbars=yes');
                        </script>
                        <div>
                            <%--<a href='${contextPath}<s:property escapeJavaScript="true"  value="#request.filePath"/>' >Bấm vào đây để download nếu trình duyệt không tự động download về.</a>--%>
                            <a href='<s:property escapeJavaScript="true"  value="#request.filePath"/>' ><tags:label key="MSG.clickhere.to.download" required="false" /></a>
                        </div>
                    </s:if>
                </td>
            </tr>
        </table>
    </tags:imPanel>
</s:form>


<script language="javascript">
    
    $('buyAndNotBuyForm.shopCode').focus();

    checkValidFields = function() {

        var shopCode= document.getElementById("buyAndNotBuyForm.shopCode");
        if(shopCode.value==""){
            //chua chon KitMovitel
            $( 'displayResultMsgClient' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.ISN.028')"/>';
            shopCode.focus();
            return false;
        }
            
        var dateExported= dojo.widget.byId("fromDate");
        if(dateExported.domNode.childNodes[1].value.trim() == ""){
    <%--$( 'displayResultMsgClient' ).innerHTML='Chưa nhập ngày báo cáo từ ngày';--%>
                $( 'displayResultMsgClient' ).innerHTML='<s:text name="ERR.UTY.028"/>';
                $('fromDate').focus();
                return false;
            }
            if(!isDateFormat(dateExported.domNode.childNodes[1].value)){
    <%--$( 'displayResultMsgClient' ).innerHTML='Ngày báo cáo từ ngày không hợp lệ';--%>
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
        var i = 1;
        for(i = 1; i < 4; i=i+1) {
            var radioId = "reportDetail" + i;
            if($(radioId).checked == true) {
                changeReportType(i);
                break;
            }

        }
        

</script>

