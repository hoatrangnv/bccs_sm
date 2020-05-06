<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
    Document   : declareSaleToPromotionGood
    Created on : Jul 25, 2009, 10:17:03 PM
    Author     : VuNT
    Desc       : Thêm hàng cho một giao dịch bán hàng khuyến mãi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="inventoryDisplay"%>
<%@taglib prefix="imDef" uri="imDef" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
            request.setAttribute("priceList", request.getSession().getAttribute("priceList"));

            //com.viettel.database.DAO.BaseDAOAction baseDAOAction = new com.viettel.database.DAO.BaseDAOAction();
            //request.setAttribute("reqBtnAdd", baseDAOAction.getText("MSG.SAE.025"));
            //request.setAttribute("reqBtnEdit", baseDAOAction.getText("MSG.SAE.033"));
            //request.setAttribute("reqBtnCancel", baseDAOAction.getText("MSG.SAE.034"));


            boolean edit = false;
            String actionType = "";
            if (request.getSession().getAttribute("actionType") != null) {
                actionType = request.getSession().getAttribute("actionType").toString();
            }
            if (actionType.equals("update")) {
                edit = true;
            }
            request.setAttribute("edit", edit);

    com.viettel.database.DAO.BaseDAOAction baseDAOAction = new com.viettel.database.DAO.BaseDAOAction();
    request.setAttribute("reqBtnUpdate", baseDAOAction.getText("MSG.SAE.033"));
    request.setAttribute("reqBtnCancel", baseDAOAction.getText("MSG.SAE.034"));
%>

<script>
    changeStockModel = function() {        
        var stockModelCode  = $('saleToCollaboratorForm.stockModelCode').value;
        updateData('${contextPath}/SaleToPromotionAction!updateListModelPrice.do?stockModelCode='+ stockModelCode);
    }

</script>


<fieldset class="imFieldset">
    <legend>${fn:escapeXml(imDef:imGetText('MSG.SAE.015'))}</legend>
    <div id="addgooddetail" style="width:100%; vertical-align:top;height:280px " >
        <table class="inputTbl3Col">           
            <tr>
                <td class="label"><tags:label key="MSG.SAE.017" required="true"/></td>
                <td class="text">                   
                    <tags:imSearch codeVariable="saleToCollaboratorForm.stockModelCode" nameVariable="saleToCollaboratorForm.stockModelName"
                                   codeLabel="MSG.GOD.007" nameLabel="MSG.GOD.008"
                                   searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                   searchMethodName="getListStockModel"
                                   getNameMethod="getStockModelName"
                                   postAction="changeStockModel();" otherParam="staff_export_channel"
                                   readOnly="${fn:escapeXml(requestScope.edit)}"/>

                    <s:hidden name="saleToCollaboratorForm.stockModelId" id="saleToCollaboratorForm.stockModelId"/>
                    <s:hidden name="saleToCollaboratorForm.telecomServiceId" id="saleToCollaboratorForm.telecomServiceId"/>

                </td>
                <td class="label" align="left">
                    <s:url action="ViewStockDetailAction!prepareViewStockDetail" id="URLViewStock" encode="true" escapeAmp="false">
                        <s:param name="ownerType" value="2"/>
                        <s:param name="ownerId" value="#session.userToken.getUserID()"/>
                    </s:url>
                    <a id="hrefViewStockDetail" href="javascript:viewStockDetail('<s:property escapeJavaScript="true"  value="#attr.URLViewStock"/>')">${fn:escapeXml(imDef:imGetText('MSG.SAE.021'))}</a>
                </td>
            </tr>
            <tr>
                <td class="label"><tags:label key="MSG.SAE.018" required="true"/></td>
                <td class="text" colspan="2">

                    <tags:imSelect name="saleToCollaboratorForm.priceId" theme="simple"
                                   id="priceId" disabled="${fn:escapeXml(requestScope.edit)}"
                                   cssClass="txtInputFull"
                                   headerKey="" headerValue="MSG.SAE.024"
                                   list="priceList"
                                   listKey="priceId" listValue="price"/>                    
                </td>
            </tr>

            <tr>
                <td class="label"><tags:label key="MSG.SAE.019" required="true"/></td>
                <td class="text" colspan="2">
                    <s:textfield name="saleToCollaboratorForm.quantity" id="quantity" maxlength="10" cssClass="txtInputFull" theme="simple"/>
                </td>
            </tr>
            <tr>
                <td class="label"><tags:label key="MSG.SAE.020"/></td>
                <td class="text" colspan="2">
                    <s:textarea name="saleToCollaboratorForm.note" id="saleToCollaboratorForm.note" cssClass="txtInputFull" theme="simple"/>
                </td>
            </tr>
            <tr>
                <td></td>
                <td colspan="2">
                    <s:if test="#session.actionType == 'new'">
                        <tags:submit formId="saleToCollaboratorForm"
                                     cssStyle="width:80px"
                                     targets="inputUpdateGood"
                                     value="MSG.SAE.025"
                                     preAction="SaleToPromotionAction!addGoods.do"
                                     validateFunction="checkValidate();"/>

                        <input type="button" disabled value="${fn:escapeXml(imDef:imGetText('MSG.generates.edit'))}" style="width:80px;">
                        <input type="button" disabled value="${fn:escapeXml(imDef:imGetText('MSG.cancel2'))}" style="width:80px;">
                    </s:if>
                    <s:elseif test="#session.actionType == 'update'">
                        <input type="button" disabled value="${fn:escapeXml(imDef:imGetText('MSG.add'))}" style="width:80px;">

                        <%--tags:submit formId="saleToCollaboratorForm"
                                     cssStyle="width:80px"
                                     targets="inputUpdateGood"
                                     value="MSG.SAE.033"
                                     preAction="SaleToPromotionAction!updateGoods.do"/>

                        <tags:submit formId="saleToCollaboratorForm"
                                     cssStyle="width:80px"
                                     targets="inputUpdateGood"
                                     value="MSG.SAE.034"
                                     preAction="SaleToPromotionAction!changActionType.do"/--%>

                       <sx:submit parseContent="true"
                                   executeScripts="true" 
                                   cssStyle="width:80px"
                                   targets="inputUpdateGood" 
                                   formId="saleToCollaboratorForm" 
                                   value="%{#request.reqBtnUpdate}" 
                                   beforeNotifyTopics="update" 
                                   errorNotifyTopics="errorAction" />

                        <sx:submit parseContent="true" 
                                   executeScripts="true" 
                                   cssStyle="width:80px"
                                   targets="addGood" 
                                   formId="saleToCollaboratorForm" 
                                   value="%{#request.reqBtnCancel}" 
                                   beforeNotifyTopics="cancel" 
                                   errorNotifyTopics="errorAction" />
                    </s:elseif>
                </td>
            </tr>
        </table>
    </div>
</fieldset>


<script>
     


    checkValidate = function(){
        
        var priceId = document.getElementById("priceId");
        var quantity = document.getElementById("quantity");
        var note = document.getElementById("saleToCollaboratorForm.note");
        quantity.value=trim(quantity.value);
        note.value=trim(note.value);



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
            $('displayResultMsg').innerHTML='<s:text name="MSG.SAE.028"/>';
            priceId.focus();
            return false;
        }
        if(quantity == null || trim(quantity.value).length <= 0 ){
            $('displayResultMsg').innerHTML='<s:text name="MSG.SAE.078"/>';
            quantity.focus();
            return false;
        }


        if(trim(quantity.value) == "0" ){
            $('displayResultMsg').innerHTML='<s:text name="MSG.SAE.079"/>';
            quantity.focus();
            return false;
        }
        
        if( !isInteger( trim(quantity.value) ) ){
            $('displayResultMsg').innerHTML='<s:text name="ERR.SAE.020"/>';
            quantity.focus();
            return false;
        }
        if(trim(quantity.value).length >10 ){
            $('displayResultMsg').innerHTML='<s:text name="ERR.SAE.021"/>';
            quantity.focus();
            return false;
        }
        
        if(trim(note.value).length >500 ){
            $('displayResultMsg').innerHTML='<s:text name="ERR.SAE.022"/>';
            note.focus();
            return false;
        }
        
        if (isHtmlTagFormat(trim(note.value))){
            $('displayResultMsg').innerHTML='<s:text name="ERR.SAE.015"/>';
            note.focus();
            return false;
        }

       
                
        return true;

    }

    viewStockDetail=function(path){
        path += "&stockModelCode="+$('saleToCollaboratorForm.stockModelCode').value;
        openPopup(path, 900, 700);
    }


    dojo.event.topic.subscribe("update", function(event, widget){
        widget.href ='SaleToPromotionAction!updateGoods.do?' + token.getTokenParamString();
    });

    dojo.event.topic.subscribe("cancel", function(event, widget){
        widget.href ='SaleToPromotionAction!changActionType.do?' + token.getTokenParamString();
    });


</script>
