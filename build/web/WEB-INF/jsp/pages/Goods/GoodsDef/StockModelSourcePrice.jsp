
<%--
    Copyright YYYY Viettel Telecom. All rights reserved.
    VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.

    Document   : StockModelSourcePrice
    Created on : Aug 13, 2011, 9:00:51 AM
    Author     : kdvt_phuoctv
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.*"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    if (request.getAttribute("listStockType") == null) {
        request.setAttribute("listStockType", request.getSession().getAttribute("listStockType"));
    }
    request.setAttribute("contextPath", request.getContextPath());
%>
<tags:imPanel title="label.stockModel.sourcePrice.update">
    <s:form action="stockModelSourcePriceAction" theme="simple" method="post" id="smspForm">
<s:token/>

        <fieldset class="imFieldset">
            <s:hidden name="smspForm.stockModelId" id="smspForm.stockModelId"/>
            <s:hidden name="smspForm.prevStockModelCode" id="smspForm.prevStockModelCode"/>
            <s:hidden name="smspForm.prevStockTypeId" id="smspForm.prevStockTypeId"/>
            <s:hidden name="smspForm.prevSourcePrice" id="smspForm.prevSourcePrice"/>
            <table class="inputTbl6Col">
                <tr>
                    <td colspan="6" align="center">
                        <tags:displayResult id="displayResultMsgClient" property="returnMsg" propertyValue="paramMsg" type="key" />
                    </td>
                </tr>
                <tr>
                    <td class="label" style="width:10%"><tags:label key="MSG.GOD.type" required="true"/><%--Loại hàng hóa--%></td>
                    <td>
                        <tags:imSelect name="smspForm.stockTypeId"
                                       id="smspForm.stockTypeId"
                                       cssClass="txtInputFull" disabled="${fn:escapeXml(isUpdating)}"
                                       theme="simple" onchange="changeStockType();"
                                       headerKey="" headerValue="label.option"
                                       list="listStockType" listKey="stockTypeId" listValue="name"
                                       />
                    </td>
                    <td class="label"><tags:label key="MSG.DET.011" required="true"/><%--Mã hàng hóa--%></td>
                    <td>
                        <tags:imSearch codeVariable="smspForm.stockModelCode" nameVariable="smspForm.name"
                                       codeLabel="MSG.DET.011" nameLabel="MSG.DET.012" readOnly="${fn:escapeXml(isUpdating)}"
                                       searchClassName="com.viettel.im.database.DAO.StockModelDAO"
                                       searchMethodName="getListStockModel"
                                       getNameMethod="getStockModelName"
                                       otherParam="smspForm.stockTypeId"
                                       roleList=""/>
                    </td>
                    <td class="label"><tags:label key="label.sourcePrice" required="true"/><%--Giá gốc--%></td>
                    <td>
                        <s:textfield name="smspForm.sourcePrice" id="smspForm.sourcePrice" maxlength="12" cssClass="txtInputFull"/>
                    </td>
                </tr>
            </table>
            <div align="center" style="vertical-align:top">
                <s:if test="#request.isUpdating != null">
                    <%--Cập nhật--%>
                    <tags:submit targets="bodyContent" formId="smspForm"
                                 cssStyle="width:80px;" value="MSG.update"
                                 validateFunction="validateInput();"
                                 confirm="true" confirmText="MSG.GOD.017"
                                 showLoadingText="true"
                                 preAction="stockModelSourcePriceAction!updateSMSP.do"
                                 />
                    <%--Hủy bỏ--%>
                    <tags:submit targets="bodyContent" formId="smspForm"
                                 cssStyle="width:80px;" value="MSG.GOD.018"
                                 showLoadingText="true"
                                 preAction="stockModelSourcePriceAction!preparePageStockModelSourcePrice.do"
                                 />
                </s:if>
                <s:else>
                    <%--Tìm kiếm--%>
                    <tags:submit targets="bodyContent" formId="smspForm"
                                 cssStyle="width:80px;" value="MSG.GOD.009"
                                 showLoadingText="true" validateFunction="validateInputSearch();"
                                 preAction="stockModelSourcePriceAction!searchSMSP.do"
                                 />
                </s:else>
            </div>
        </fieldset>
    </s:form>
    <br>
    <fieldset class="imFieldset">
        <legend>
            <s:text name="MSG.search.result"/>
        </legend>
        <sx:div id="divSearchResult" theme="simple">
            <jsp:include page="ListStockModelSourcePrice.jsp"/>
        </sx:div>
    </fieldset>
</tags:imPanel>
<script type="text/javascript">
    changeStockType = function() {
        document.getElementById("smspForm.stockModelCode").value = "";
        document.getElementById("smspForm.name").value = "";
    }

    validateInput = function() {
        var stockTypeId = document.getElementById("smspForm.stockTypeId");
        var sourcePrice = document.getElementById("smspForm.sourcePrice");
        if(stockTypeId.value == null || stockTypeId.value == "") {
            /*Chưa chọn loại hàng hóa*/
            $("displayResultMsgClient").innerHTML = "<s:text name="ERR.SAE.031"/>";
            stockTypeId.focus();
            return false;
        }
        if(sourcePrice.value == null || sourcePrice.value == "") {
            /*Chưa nhập giá gốc*/
            $("displayResultMsgClient").innerHTML = "<s:text name="ERR.LIMIT.0019"/>";
            sourcePrice.focus();
            return false;
        }
        sourcePrice.value = trim(sourcePrice.value);
        if(!isDouble(sourcePrice.value)) {
            /*nhap ko dung kieu Long*/
            $("displayResultMsgClient").innerHTML = "<s:text name="ERR.LIMIT.0020"/>";
            sourcePrice.focus();
            return false;
        }
        if(sourcePrice.value < 0 ) {
            /*nhap ko dung kieu Long*/
            $("displayResultMsgClient").innerHTML = "<s:text name="ERR.LIMIT.0020"/>";
            sourcePrice.focus();
            return false;
        }

        return true;
    }
    validateInputSearch = function() {
        var sourcePrice = document.getElementById("smspForm.sourcePrice");
        sourcePrice.value = trim(sourcePrice.value);
        if(sourcePrice.value != null && sourcePrice.value != "") {
            if(!isDouble(sourcePrice.value)) {
                /*nhap ko dung kieu Long*/
                $("displayResultMsgClient").innerHTML = "<s:text name="ERR.LIMIT.0020"/>";
                sourcePrice.focus();
                return false;
            }
            if(sourcePrice.value < 0 ) {
                /*nhap ko dung kieu Long*/
                $("displayResultMsgClient").innerHTML = "<s:text name="ERR.LIMIT.0020"/>";
                sourcePrice.focus();
                return false;
            }
        }
        return true;
    }
</script>
