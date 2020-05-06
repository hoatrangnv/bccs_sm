<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : saleCollToRetailInputGood
    Created on : Aug 31, 2010, 1:49:48 PM
    Author     : tronglv
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
    if (request.getAttribute("isEdit") != null) {
        isEdit = Boolean.parseBoolean(request.getAttribute("isEdit").toString());
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


<fieldset class="imFieldset">
    <legend><s:text name = "MSG.SAE.015"/></legend>
    <div style="width:100%; height:400px; vertical-align:top">



        <table class="inputTbl2Col" >
            <tr>
                <s:hidden name="saleToCollaboratorForm.telecomServiceId" id="telecomServiceId"/>

                <td class="label"><tags:label key="MSG.sale.goods" required="true"/></td>
                <td  class="text">

                    <s:hidden name="from_owner_type" id="from_owner_type" value="2"/>
                    <s:hidden name="channel_sale" id="channel_sale" value="channel_sale"/>

                    <tags:imSearch codeVariable="saleToCollaboratorForm.stockModelCode" nameVariable="saleToCollaboratorForm.stockModelName"
                                   codeLabel="MSG.GOD.007" nameLabel="MSG.GOD.008"
                                   searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                   searchMethodName="getListStockModel"
                                   getNameMethod="getStockModelName"
                                   otherParam="channel_sale;saleToCollaboratorForm.agentCodeSearch;agentTypeIdSearch"
                                   postAction="changeStockModel();"
                                   readOnly="${fn:escapeXml(requestScope.isEdit)}"/>

                    <s:hidden name="saleToCollaboratorForm.stockModelId"/>
                    <s:hidden name="saleToCollaboratorForm.itemVat" id="itemVat"/>

                </td>
                <td  class="label" align="left">
                    <s:url action="ViewStockDetailAction!prepareViewStockDetail" id="URLViewStock" encode="true" escapeAmp="false">
                        <s:param name="ownerType" value="2"/>
                    </s:url>
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

                <s:if test="#request.isEdit == true">
                    <%--<s:hidden name="saleToCollaboratorForm.priceId"/>--%>
                </s:if>
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
                <tags:submit targets="bodyContent" formId="saleToCollaboratorForm" cssStyle="width:80px;"
                             value="MSG.SAE.025" preAction="saleCollToRetailAction!addGoods.do"
                             validateFunction="checkValidate();"
                             showLoadingText="true"/>

                <input type="button" disabled value="${fn:escapeXml(imDef:imGetText('MSG.SAE.033'))}" style="width:80px;">
                <input type="button" disabled value="${fn:escapeXml(imDef:imGetText('MSG.SAE.034'))}" style="width:80px;">
            </s:if>
            <s:else>
                <input type="button" disabled value="${fn:escapeXml(imDef:imGetText('MSG.SAE.025'))}" style="width:80px;">

                <tags:submit targets="bodyContent" formId="saleToCollaboratorForm" cssStyle="width:80px;"
                             value="MSG.SAE.033" preAction="saleCollToRetailAction!editGoods.do"
                             validateFunction="checkValidate();"
                             showLoadingText="true"/>

                <tags:submit targets="inputGoods" formId="saleToCollaboratorForm" cssStyle="width:80px;"
                             value="MSG.SAE.034" preAction="saleCollToRetailAction!cancelEditGoods.do"
                             validateFunction="checkValidate();"
                             showLoadingText="true"/>
            </s:else>
        </div>

    </div>
</fieldset>

<script>

    checkValidate = function(){
        document.getElementById("saleToCollaboratorForm.quantity").value =document.getElementById("saleToCollaboratorForm.quantity").value.trim();
        var priceId = document.getElementById("priceId");
        var quantity = document.getElementById("saleToCollaboratorForm.quantity");
        var note = document.getElementById("note");

        //kiem tra ma mat hang ko duoc de trong
        if(trim($('saleToCollaboratorForm.stockModelCode').value) == "") {
            $('displayResultMsg').innerHTML = '<s:text name="MSG.GOD.035"/>';
            $('saleToCollaboratorForm.stockModelCode').select();
            $('saleToCollaboratorForm.stockModelCode').focus();
            return false;
        }
        //truong ma mat hang khong duoc chua cac ky tu dac biet
        if(!isValidInput($('saleToCollaboratorForm.stockModelCode').value, false, false)) {
            $('displayResultMsg' ).innerHTML = '<s:text name="MSG.GOD.038"/>';
            $('saleToCollaboratorForm.stockModelCode').select();
            $('saleToCollaboratorForm.stockModelCode').focus();
            return false;
        }

        if(priceId.value==null || priceId.value ==''){
            $('displayResultMsg').innerHTML= '<s:text name="ERR.SAE.018"/>';
            //$('displayResultMsg').innerHTML="Chưa chọn đơn giá"
            priceId.focus();
            return false;
        }
        if(quantity == null || trim(quantity.value).length <= 0 ){
            $('displayResultMsg').innerHTML= '<s:text name="ERR.SAE.019"/>';
            //$('displayResultMsg').innerHTML="Số lượng hàng bán phải >0"
            quantity.focus();
            return false;
        }
        if(!isInteger(trim(quantity.value)) || quantity. value.trim()==0){
            $('displayResultMsg').innerHTML= '<s:text name="ERR.SAE.020"/>';
            //$('displayResultMsg').innerHTML="Số lượng phải là số nguyên dương"
            quantity.focus();
            return false;
        }
        if(trim(quantity.value).length >10 ){
            $('displayResultMsg').innerHTML= '<s:text name="ERR.SAE.021"/>';
            //$('displayResultMsg').innerHTML="Số lượng nhập vượt quá giới hạn"
            quantity.focus();
            return false;
        }
        if(trim(note.value).length >500 ){
            $('displayResultMsg').innerHTML= '<s:text name="ERR.SAE.022"/>';
            //$('displayResultMsg').innerHTML="Ghi chú nhập vượt quá giới hạn"
            note.focus();
            return false;
        }

        $('saleToCollaboratorForm.quantity').value=$('saleToCollaboratorForm.quantity').value.trim();
        return true;
    }

    viewStockDetail=function(path){
        path += "&stockModelCode="+$('saleToCollaboratorForm.stockModelCode').value;
        path += "&ownerCode="+$('saleToCollaboratorForm.agentCodeSearch').value;
        openPopup(path, 900, 700);
    }


    inputSerial =function (shopId){
        var stockModelId =$('stockModelId').value;
        var quantity= $('saleToCollaboratorForm.quantity').value;
        if(stockModelId ==''){
            $('displayResultMsg').innerHTML= '<s:text name="ERR.SAE.017"/>';
            //$('displayResultMsg').innerHTML="Chưa chọn mặt hàng";
            return false;
        }
        if(quantity==''){
            $('displayResultMsg').innerHTML= '<s:text name="ERR.SAE.016"/>';
            //            $('displayResultMsg').innerHTML="Chưa nhập số lượng";
            return false;
        }
        var path = "InputSerialAction!prepareInputSerial.do?ownerType=1&stateId=1&stockModelId="+stockModelId +"&totalReq=" +quantity+"&ownerId="+shopId;
        openPopup(path,800,700);
    }

    //Chọn hàng hoá
    changeStockModel = function() {
        $('displayResultMsg').innerHTML="";
        var stockModelCode  = $('saleToCollaboratorForm.stockModelCode').value;
        updateData('${contextPath}/saleCollToRetailAction!updateListModelPrice.do?stockModelCode=' + stockModelCode + '&agentCodeSearch=' + $('saleToCollaboratorForm.agentCodeSearch').value.trim());

    }
   
</script>
