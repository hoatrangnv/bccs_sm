<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : reportStockTrans
    Created on : Aug 17, 2009, 2:01:25 PM
    Author     : MrSun
    Desc       : Bao cao/The kho
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
    request.setAttribute("lstStockType", request.getSession().getAttribute("lstStockType"));
%>

<tags:imPanel title="MSG.criterion.report.create">
    <%--<fieldset style="width:100%; background-color:#F5F5F5;">--%>
    <%--<legend>Tiêu chí lập báo cáo</legend>--%>
    <!-- phan hien thi thong tin message -->

    <div>
        <tags:displayResult id="displayResultMsgClient"  property="returnMsg" propertyValue="returnMsgValue" type="key"/>
    </div>

    <!-- phan hien thi tieu chi bao cao -->
    <s:form action="reportStockTransAction" theme="simple" method="post" id="reportStockTransForm">
<s:token/>

        <table class="inputTbl8Col">            
            <tr>
                <td class="label"><tags:label key="MSG.stock.type" required="true" /> </td>
                <td class="text">
                    <%--<s:select name="reportStockTransForm.departmentKind"
                              formId="reportStockTransForm"
                              id="departmentKind"
                              cssClass="txtInputFull"
                              headerKey="" headerValue="--Chọn loại MH--"
                              list="#{'1':'Kho đơn vị','2':'Kho nhân viên'}"
                              headerKey="" headerValue="--Chọn loại kho--"
                              onchange="notify('departmentKind','shopCode','select1','%{#request.contextPath}/reportStockTransAction!getShopCode.do');"
                              />--%>
                    <tags:imSelect name="reportStockTransForm.departmentKind"
                                   id="departmentKind"
                                   cssClass="txtInputFull"
                                   headerKey="" headerValue="MSG.RET.176"
                                   list="1:MSG.RET.106,2:MSG.RET.107"                                   
                                   onchange="notify('departmentKind','shopCode','select1','${requestScope.contextPath}/reportStockTransAction!getShopCode.do');"
                                   />
                </td>
                <%--<td class="label">Mã kho </td>--%>
                <%--
                <td class="label">Mã kho</td>
                <td class="text">
                    <sx:autocompleter name="reportStockTransForm.shopCode"
                                      id="shopCode"
                                      keyName="reportStockTransForm.shopId"
                                      cssClass="txtInputFull"
                                      href="reportStockTransAction!getShopCode.do"
                                      formId="reportStockTransForm"
                                      loadOnTextChange="true"
                                      loadMinimumCount="1"
                                      maxlength="25"
                                      valueNotifyTopics="reportStockTransAction/getShopName"/>
                </td>
                <td class="label">Tên kho</td>
                <td colspan="3" class="text">
                    <s:textfield name="reportStockTransForm.shopName" id="shopName" readonly="true" cssClass="txtInputFull"/>
                </td>
                --%>
                <td><tags:label key="MSG.store.code" /></td>
                <td colspan="3">
                    <tags:imSearch codeVariable="reportStockTransForm.shopCode" nameVariable="reportStockTransForm.shopName"
                                   codeLabel="MSG.RET.066" nameLabel="MSG.RET.067"
                                   searchClassName="com.viettel.im.database.DAO.StockCommonDAO"
                                   searchMethodName="getListShopOrStaff"
                                   getNameMethod="getListShopOrStaff"
                                   otherParam="departmentKind"
                                   />                   

                </td>  
            </tr>
            <tr>
                <td class="label"><tags:label key="MSG.GOD.027" required="true" /> </td>
                <td class="text">
                    <%--                <s:select name="reportStockTransForm.stockTypeId"
                                              id="stockTypeId"
                                              cssClass="txtInputFull"
                                              headerKey="" headerValue="--Chọn loại MH--"
                                              list="%{#session.lstStockType!=null ? #session.lstStockType : #{}}"
                                              listKey="stockTypeId" listValue="name"
                                              onchange="changeStockType(this.value)"/>--%>
                    <tags:imSelect name="reportStockTransForm.stockTypeId"
                                   id="stockTypeId"
                                   cssClass="txtInputFull"
                                   headerKey="" headerValue="MSG.RET.175"
                                   list="lstStockType"
                                   listKey="stockTypeId" listValue="name"
                                   onchange="changeStockType(this.value)"
                                   />
                </td>
                <td class="label"><tags:label key="MSG.generates.goods" required="true" /> </td>
                <td class="text">
                    <%--<s:select name="reportStockTransForm.stockModelId"
                              id="stockModelId"
                              cssClass="txtInputFull"
                              headerKey="" headerValue="--Chọn mặt hàng--"
                              list="%{#request.lstStockModel!=null ? #request.lstStockModel : #{}}"
                              listKey="stockModelId" listValue="name"/>--%>
                    <tags:imSelect name="reportStockTransForm.stockModelId"
                                   id="stockModelId"
                                   cssClass="txtInputFull"
                                   headerKey="" headerValue="MSG.RET.168"
                                   list="lstStockModel"
                                   listKey="stockModelId" listValue="name"                                   
                                   />

                </td>
                <td class="label"><tags:label key="MSG.fromDate" required="true" /></td>
                <td class="text">
                    <tags:dateChooser property="reportStockTransForm.fromDate" id="fromDate"/>
                </td>
                <td class="label"><tags:label key="MSG.generates.to_date" required="true" /></td>
                <td class="text">
                    <tags:dateChooser property="reportStockTransForm.toDate" id="toDate"/>
                </td>
            </tr>
            <%--tr>
                <td><tags:label key="MSG.generates.status" required="true"/></td>
                <td>
                    <%--<s:select name="reportStockTransForm.status"
                              id="status"
                              cssClass="txtInputFull"
                              headerKey="" headerValue="--Chọn trạng thái--"
                              list="#{'1':'Hàng mới', '3':'Hàng hỏng'}"/>>
                    <tags:imSelect name="reportStockTransForm.status" id="status"
                                   cssClass="txtInputFull"
                                   headerKey="" headerValue="MSG.RET.177"
                                   list="1:MSG.RET.108,3:MSG.RET.109"
                                   />
                </td>
                <td colspan="6"></td>
            </tr--%>

        </table>

    </s:form>

    <!-- phan nut tac vu -->
    <div align="center" style="padding-top:5px; padding-bottom:5px;">
        <tags:submit formId="reportStockTransForm"
                     showLoadingText="true"
                     cssStyle="width:80px;"
                     targets="resultArea"
                     value="MSG.report"
                     preAction="reportStockTransAction!reportStockTrans.do" validateFunction="checkValidFields()"/>
    </div>

    <!-- phan link download bao cao -->


    <sx:div id="resultArea">
        <jsp:include page="../../Sale/returnMsg.jsp"></jsp:include>
    </sx:div>

    <%--<div>
        <s:if test="#request.reportStockTransPath != null">
            <script>
                window.open('${contextPath}/${fn:escapeXml(reportStockTransPath)}','','toolbar=yes,scrollbars=yes');
            </script>
            <a href="${contextPath}/${fn:escapeXml(reportStockTransPath)}">
                <tags:displayResult id="reportStockTransMessage" property="reportStockTransMessage" propertyValue="reportStockTransMessageValue" type="key"/>
            </a>
        </s:if>
        <s:else>
            <div>
                <tags:displayResult id="displayResultMsgClient" property="reportStockTransMessage" propertyValue="reportStockTransMessageValue" type="key"/>
            </div>
        </s:else>
    </div>--%>
    <%--</fieldset>--%>
</tags:imPanel>

<script language="javascript">

    checkValidFields = function() {

        if ($('departmentKind').value.trim() == ""){
    <%--$( 'displayResultMsgClient' ).innerHTML = "Chưa chọn loại kho";--%>
                $( 'displayResultMsgClient' ).innerHTML='<s:text name="ERR.RET.049"/>';
                $('departmentKind').focus();
                return false;
            }<%--
var _myWidget1 = dojo.widget.byId("shopCode");
if (_myWidget1.domNode.childNodes[1].value.trim() == ""){
    _myWidget1.textInputNode.focus();
    $( 'displayResultMsgClient' ).innerHTML = "Chưa nhập mã cửa hàng/đại lý";
    return false;
}--%>

        if ($('stockTypeId').value.trim() == ""){
            $('stockTypeId').focus();
    <%--$( 'displayResultMsgClient' ).innerHTML = "Chưa chọn loại hàng hoá cần báo cáo";--%>
                $( 'returnMsgClient' ).innerHTML='<s:text name="ERR.RET.050"/>';
                return false;
            }

            if ($('stockModelId').value.trim() == ""){
                $('stockModelId').focus();
    <%--$( 'displayResultMsgClient' ).innerHTML = "Chưa chọn hàng hoá cần báo cáo";--%>
                $( 'displayResultMsgClient' ).innerHTML='<s:text name="ERR.RET.051"/>';
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
            
            
//            if ($('status').value.trim() == ""){
//                $('status').focus();
//                $( 'returnMsgClient' ).innerHTML='<s:text name="E.100009"/>';
//                return false;
//            }
        
            return true;
        }

        //xu ly su kien sau khi chon 1 ma cua hang
        dojo.event.topic.subscribe("reportStockTransAction/getShopName", function(value, key, text, widget){
            $('shopName').value = "";
            if (key != undefined) {
                getInputText("reportStockTransAction!getShopName.do?target=shopName&shopId=" + key + "&departmentKind="+$('departmentKind').value);
            } else {

            }
        });

        //xu ly su kien sau khi chon 1 ma nhan vien
        dojo.event.topic.subscribe("reportStockTransAction/getStaffName", function(value, key, text, widget){
            $('staffName').value = "";

            if (key != undefined) {
                getInputText("reportStockTransAction!getStaffName.do?target=staffName&staffId=" + key);
            } else {

            }
        });

        //cap nhat lai danh sach mat hang, danh sach tap luat
        changeStockType = function(stockTypeId) {
            updateData("${contextPath}/reportStockTransAction!changeStockType.do?target=stockModelId&stockTypeId=" + stockTypeId);
        }

</script>
