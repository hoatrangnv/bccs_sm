<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : StockGoodsCollaborator.jsp
    Created on : Feb 17, 2009, 10:51:45 AM
    Author     : ThanhNC1
    Modified   : tamdt1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%@ page import="com.guhesan.querycrypt.QueryCrypt" %>

<%
    boolean isEdit = false;
    boolean notEdit = true;
    if (request.getSession().getAttribute("isEdit") != null) {
        isEdit = Boolean.parseBoolean(request.getSession().getAttribute("isEdit").toString());
    }
    notEdit = !isEdit;
    String nonEdit = new Boolean(notEdit).toString();
    String edit = new Boolean(isEdit).toString();
    request.setAttribute("edit", edit);
    request.setAttribute("nonEdit", nonEdit);

    if (request.getAttribute("isEdit") == null) {
        request.setAttribute("isEdit", false);
    }
%>
<%
    request.setAttribute("contextPath", request.getContextPath());
    request.setAttribute("listTelecomService", request.getSession().getAttribute("listTelecomService"));
    request.setAttribute("modelList", request.getSession().getAttribute("modelList"));
    request.setAttribute("priceList", request.getSession().getAttribute("priceList"));

    com.viettel.database.DAO.BaseDAOAction baseDAOAction = new com.viettel.database.DAO.BaseDAOAction();
    request.setAttribute("reqBtnAdd", baseDAOAction.getText("MSG.SAE.025"));
    request.setAttribute("reqBtnEdit", baseDAOAction.getText("MSG.SAE.033"));
    request.setAttribute("reqBtnCancel", baseDAOAction.getText("MSG.SAE.034"));

%>

<table class="inputTbl2Col" >
    <tr>
        <td class="label"><tags:label key="MSG.TYP.001" required="true"/></td>
        <td class="text" colspan="2">            
            <tags:imSelect name="saleToCollaboratorForm.typeOfSale" theme="simple"
                           id="saleToCollaboratorForm.typeOfSale" disabled="${fn:escapeXml(requestScope.isEdit)}"
                           cssClass="txtInputFull"
                           list="2:MSG.TYP.003, 1:MSG.TYP.002"
                           onchange="resetForm();"/>
        </td>
    </tr>
    <tr>
        <td class="label"><tags:label key="MSG.generates.service" required="true"/></td>
        <td class="text" colspan="2">            
            <s:if test="#request.isEdit == true">                
                <s:hidden name="saleToCollaboratorForm.stockModelId" id="stockModelId_Hidden"/>
            </s:if>

            <tags:imSelect name="saleToCollaboratorForm.telecomServiceId" theme="simple"
                           id="telecomServiceId" disabled="${fn:escapeXml(requestScope.isEdit)}"
                           cssClass="txtInputFull"
                           headerKey="" headerValue="MSG.SAE.022"
                           list="listTelecomService"
                           listKey="telecomServiceId" listValue="telServiceName"/>
        </td>
    </tr>
    <tr>
        <td class="label"><tags:label key="MSG.sale.goods" required="true"/></td>
        <td  class="text">
            <%--tags:imSelect name="saleToCollaboratorForm.stockModelId" theme="simple"
                           id="stockModelId" disabled="${fn:escapeXml(requestScope.isEdit)}"
                           cssClass="txtInputFull"
                           headerKey="" headerValue="MSG.SAE.023"
                           list="modelList"
                           listKey="stockModelId" listValue="name"
                           onchange="changeStockModel();"/--%>

            <tags:imSearch codeVariable="saleToCollaboratorForm.stockModelCode" nameVariable="saleToCollaboratorForm.stockModelName"
                           codeLabel="MSG.GOD.007" nameLabel="MSG.GOD.008"
                           searchClassName="com.viettel.im.database.DAO.SaleToCollaboratorDAO"
                           searchMethodName="getListStockModel"
                           getNameMethod="getStockModelName" otherParam="saleToCollaboratorForm.typeOfSale"
                           postAction="changeStockModel();"
                           readOnly="${fn:escapeXml(requestScope.isEdit)}"/>

            <s:hidden name="saleToCollaboratorForm.itemVat" id="itemVat"/>
        </td>
        <td  class="label" align="left">

            <s:url action="ViewStockDetailAction!prepareViewStockDetail" id="URLViewStock" encode="true" escapeAmp="false">
                <s:param name="ownerType" value="2"/>
                <s:param name="ownerId" value="#session.userToken.getUserID"/>
                <s:param name="stockModelId" value="saleToCollaboratorForm.stockModelId"/>
                <s:param name="stateId" value="1"/>
            </s:url>

            <%--a id="hrefViewStockDetail" href="javascript:viewStockDetail('<s:property escapeJavaScript="true"  value="#attr.URLViewStock"/>')"> Xem kho</a--%>
            <input type="button" id="hrefViewStockDetail" onclick="viewStockDetail('<s:property escapeJavaScript="true"  value="#attr.URLViewStock"/>')" value="${fn:escapeXml(imDef:imGetText('MSG.SAE.021'))}"/>
        </td>

    </tr>
    <tr>
        <td  class="label"><tags:label key="MSG.price" required="true"/></td>
        <td  class="text" colspan="2">
            <tags:imSelect name="saleToCollaboratorForm.priceId" theme="simple"
                           id="priceId" disabled="${fn:escapeXml(requestScope.isEdit)}"
                           cssClass="txtInputFull"
                           headerKey="" headerValue="MSG.SAE.024"
                           list="priceList"
                           listKey="priceId" listValue="price"
                           onchange=""/>

        </td>
    </tr>
    <tr>
        <td  class="label"><tags:label key="MSG.quantity" required="true"/></td>
        <td  class="text" colspan="2">
            <s:textfield name="saleToCollaboratorForm.quantity" theme="simple" id="saleToCollaboratorForm.quantity" maxlength="10" cssClass="txtInputFull"/>
        </td>
    </tr>
    <tr>
        <td  class="label"><tags:label key="MSG.comment"/></td>
        <td class="text" colspan="2">
            <s:textarea name="saleToCollaboratorForm.note" theme="simple" id="note" maxlength="500" cssClass="txtInputFull"/>
        </td>
    </tr>
</table>

<div align="center">
    <s:if test="#request.isEdit == false">
        <tags:submit targets="bodyContent" 
                     formId="saleToCollaboratorForm"
                     cssStyle="width:80px;"
                     value="MSG.add"
                     preAction="saleToCollaboratorAction!addGoods.do"
                     confirm="true" confirmText="MSG.confirm.add"
                     validateFunction="checkValidate();"
                     showLoadingText="true"/>

        <input type="button" disabled value="${fn:escapeXml(imDef:imGetText('MSG.generates.edit'))}" style="width:80px;">
        <input type="button" disabled value="${fn:escapeXml(imDef:imGetText('MSG.cancel2'))}" style="width:80px;">
    </s:if>
    <s:else>
        <input type="button" disabled value="${fn:escapeXml(imDef:imGetText('MSG.add'))}" style="width:80px;">

        <tags:submit targets="bodyContent" 
                     formId="saleToCollaboratorForm"
                     cssStyle="width:80px;"
                     value="MSG.SAE.033"
                     preAction="saleToCollaboratorAction!editGoods.do"
                     validateFunction="checkValidate();"
                     showLoadingText="true"/>

        <tags:submit targets="inputGoods" 
                     formId="saleToCollaboratorForm"
                     cssStyle="width:80px;"
                     value="MSG.SAE.034"
                     preAction="saleToCollaboratorAction!cancelEditGoods.do"
                     showLoadingText="true"/>
    </s:else>

</div>

<script>

                checkValidate = function() {
                    var telecomServiceId = document.getElementById("telecomServiceId");
                    //        var stockModelId = document.getElementById("stockModelId");
                    var priceId = document.getElementById("priceId");
                    var quantity = document.getElementById("saleToCollaboratorForm.quantity");
                    var note = document.getElementById("note");
                    document.getElementById("saleToCollaboratorForm.quantity").value = quantity.value.trim();

                    if (telecomServiceId.value == null || telecomServiceId.value == '') {
                        $('displayResultMsg').innerHTML = '<s:text name="ERR.SAE.016"/>';
                        //$('displayResultMsg').innerHTML="Chưa chọn loại dịch vụ"
                        telecomServiceId.focus();
                        return false;
                    }

                    //kiem tra ma mat hang ko duoc de trong
                    if (trim($('saleToCollaboratorForm.stockModelCode').value) == "") {
                        $('displayResultMsg').innerHTML = '<s:text name="MSG.GOD.035"/>';
                        $('saleToCollaboratorForm.stockModelCode').select();
                        $('saleToCollaboratorForm.stockModelCode').focus();
                        return false;
                    }

                    //truong ma mat hang khong duoc chua cac ky tu dac biet
                    if (!isValidInput($('saleToCollaboratorForm.stockModelCode').value, false, false)) {
                        $('displayResultMsg').innerHTML = '<s:text name="MSG.GOD.038"/>';
                        $('saleToCollaboratorForm.stockModelCode').select();
                        $('saleToCollaboratorForm.stockModelCode').focus();
                        return false;
                    }

                    if (priceId.value == null || priceId.value == '') {
                        $('displayResultMsg').innerHTML = '<s:text name="ERR.SAE.018"/>';
                        //$('displayResultMsg').innerHTML="Chưa chọn đơn giá"
                        priceId.focus();
                        return false;
                    }
                    if (quantity == null || trim(quantity.value).length <= 0) {
                        $('displayResultMsg').innerHTML = '<s:text name="ERR.SAE.019"/>';
                        //$('displayResultMsg').innerHTML="Số lượng hàng bán phải >0"
                        quantity.focus();
                        return false;
                    }
                    if (!isInteger(trim(quantity.value)) || quantity.value.trim() == 0) {
                        $('displayResultMsg').innerHTML = '<s:text name="ERR.SAE.020"/>';
                        //$('displayResultMsg').innerHTML="Số lượng phải là số nguyên dương"
                        quantity.focus();
                        return false;
                    }
                    if (trim(quantity.value).length > 10) {
                        $('displayResultMsg').innerHTML = '<s:text name="ERR.SAE.021"/>';
                        //$('displayResultMsg').innerHTML="Số lượng nhập vượt quá giới hạn"
                        quantity.focus();
                        return false;
                    }
                    if (trim(note.value).length > 500) {
                        $('displayResultMsg').innerHTML = '<s:text name="ERR.SAE.022"/>';
                        //$('displayResultMsg').innerHTML="Ghi chú nhập vượt quá giới hạn"
                        note.focus();
                        return false;
                    }

                    $('saleToCollaboratorForm.quantity').value = $('saleToCollaboratorForm.quantity').value.trim();

                    return true;
                }

                viewStockDetail = function(path) {
                    openPopup(path, 900, 700);
                }

                inputSerial = function(shopId) {
                    var stockModelId = $('stockModelId').value;
                    var quantity = $('saleToCollaboratorForm.quantity').value;
                    if (stockModelId == '') {
                        $('displayResultMsg').innerHTML = '<s:text name="ERR.SAE.017"/>';
                        //$('displayResultMsg').innerHTML="Chưa chọn mặt hàng";
                        //alert("Chua chon mat hang");
                        return false;
                    }
                    if (quantity == '') {
                        $('displayResultMsg').innerHTML = '<s:text name="ERR.SAE.016"/>';
                        $('displayResultMsg').innerHTML = "Chưa nhập số lượng";
                        //alert("Chua nhap so luong");
                        return false;
                    }
                    var path = "InputSerialAction!prepareInputSerial.do?ownerType=1&stateId=1&stockModelId=" + stockModelId + "&totalReq=" + quantity + "&ownerId=" + shopId;
                    openPopup(path, 800, 700);
                }

                //Chọn hàng hoá
                changeStockModel = function() {
                    $('displayResultMsg').innerHTML = "";
                    var typeOfSale = $('saleToCollaboratorForm.typeOfSale').value;
                    
                    var staffId = $('saleToCollaboratorForm.agentCodeSearch').value;
                    if (staffId == null || trim(staffId) == "") {
                        $('displayResultMsg').innerHTML = '<s:text name="MSG.SAE.073"/>';
                        return;
                    }

                    var stockModelCode = $('saleToCollaboratorForm.stockModelCode').value;
                    updateData('${contextPath}/saleToCollaboratorAction!updateListModelPrice.do?stockModelCode=' + stockModelCode + '&staffId=' + staffId + '&typeOfSale=' + typeOfSale);
                }

                resetForm = function() {
                    $('saleToCollaboratorForm.quantity').value = null;
                    $('saleToCollaboratorForm.stockModelCode').value = null;
                    $('saleToCollaboratorForm.stockModelName').value = null;
                }

</script>
