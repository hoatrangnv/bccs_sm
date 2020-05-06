<%--
    Document   : createInvoiceNotSaleInputGood
    Created on : Sep 28, 2009, 00:00:00 AM
    Author     : TrongLV
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@ taglib uri="VTdisplaytaglib" prefix="inventoryDisplay"%>

<%
            request.setAttribute("contextPath", request.getContextPath());
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>

        <tags:imPanel title="MSG.import.detail">
            <div style="width:100%; height:200px; vertical-align:top">
                <table class="inputTbl2Col">
                    <tr>
                        <td class="label"><tags:label key="MSG.service.type" required="true"/></td>
                        <td class="text">
                            <s:select name="form.itemTelecomServiceId" theme="simple"
                                      id="telecomServiceId" disabled="form.itemIsEdit"
                                      cssClass="txtInput"
                                      headerKey="" headerValue="--Chọn loại dịch vụ--"
                                      list="%{#session.lstTelecom != null?#session.lstTelecom:#{}}"
                                      listKey="telecomServiceId" listValue="telServiceName"
                                      onchange="changeTelecom();"/>
                            <s:hidden name="form.itemIsEdit"/>

                            <s:hidden name="form.itemEditTelecomServiceId"/>
                        </td>
                    </tr>
                    <tr>
                        <td  class="label"><tags:label key="MSG.reasonFine" required="true"/></td>
                        <td  class="text">
                            <s:select name="form.itemId" theme="simple"
                                      id="fineItemsId" disabled="form.itemIsEdit"
                                      cssClass="txtInput"
                                      headerKey="" headerValue="--Chọn lý do--"
                                      list="%{#session.lstFineItem != null?#session.lstFineItem:#{}}"
                                      listKey="fineItemsId" listValue="fineItemsName"
                                      onchange="changeStockModel();"/>
                            <s:hidden name="form.itemEditId"/>
                        </td>
                    </tr>
                    <tr >
                        <td  class="label" ><tags:label key="MSG.amount" required="true"/></td>
                        <td  class="text">
                            <s:select name="form.itemPriceId" theme="simple"
                                      id="fineItemPriceId" disabled="form.itemIsEdit"
                                      cssClass="txtInput"
                                      headerKey="" headerValue="--Chọn giá--"
                                      list="%{#session.lstFineItemPrice != null?#session.lstFineItemPrice:#{}}"
                                      listKey="fineItemPriceId" listValue="price"
                                      onchange=""/>
                            <s:hidden name="form.itemEditPriceId"/>
                            <s:hidden name="form.itemEditVAT"/>

                            <s:hidden name="form.itemQuantity" id="quantity" maxlength="10" cssClass="txtInput" theme="simple" value="1"/>
                        </td>
                    </tr>

                    <%--<tr>
                        <td>Số lượng (<font color="red">*</font>)</td>
                        <td>
                            <s:textfield name="form.itemQuantity" id="quantity" maxlength="10" cssClass="txtInput" theme="simple"/>
                        </td>
                    </tr>--%>

                    <tr>
                        <td  class="label"><tags:label key="MSG.comment"/></td>
                        <td  class="text">
                            <s:textarea name="form.itemNote" id="note" cssClass="txtInput" theme="simple"/>
                        </td>
                    </tr>
                </table>

                <%--TrongLV--%>
                <div align="center">
                    <s:if test="form.itemIsEdit == false ">
                        <tags:submit confirm="false" confirmText="MSG.confirm.add"
                                     formId="form"
                                     cssStyle="width:80px;"
                                     targets="createInvoiceNotSaleDetailArea"
                                     validateFunction="checkValidate()"
                                     value="Thêm" preAction="SaleTransInvoiceAction!addGoods.do"/>
                    </s:if>
                    <s:else>
                        <sx:submit parseContent="true" executeScripts="true"
                                   cssStyle="width:80px;"
                                   targets="createInvoiceNotSaleDetailArea" formId="form" key="MSG.generates.edit" beforeNotifyTopics="saleRetailAction/insert"
                                   errorNotifyTopics="errorAction" />
                        <sx:submit parseContent="true" executeScripts="true" targets="inputGood" formId="form" key="MSG.cancel2" beforeNotifyTopics="saleRetailAction/abort"
                                   cssStyle="width:80px;"
                                   errorNotifyTopics="errorAction" />
                    </s:else>
                </div>
            </div>
        </tags:imPanel>

    </body>
</html>
<script type="text/javascript" language="javascript">
    checkValidate = function(){
        var telecomServiceId = document.getElementById("telecomServiceId");
        var stockModelId = document.getElementById("fineItemsId");
        var priceId = document.getElementById("fineItemPriceId");
        var quantity = document.getElementById("quantity");
        var note = document.getElementById("note");
        document.getElementById("quantity").value =quantity.value.trim();
        quantity.value=trim(quantity.value);
        note.value=trim(note.value);

        if(telecomServiceId.value==null || telecomServiceId.value ==''){
            $('returnMsgClient').innerHTML= '<s:text name="ERR.SAE.016"/>';
            //$('returnMsgClient').innerHTML="Chưa chọn loại dịch vụ"
            telecomServiceId.focus();
            return false;
        }
        if(stockModelId.value==null || stockModelId.value ==''){
            $('returnMsgClient').innerHTML= '<s:text name="ERR.SAE.057"/>';
            //$('returnMsgClient').innerHTML="Chưa chọn lý do phạt"
            stockModelId.focus();
            return false;
        }
        if(priceId.value==null || priceId.value ==''){
            $('returnMsgClient').innerHTML= '<s:text name="ERR.SAE.058"/>';
            //$('returnMsgClient').innerHTML="Chưa chọn giá tiền"
            priceId.focus();
            return false;
        }
        if(quantity == null || trim(quantity.value).length <= 0 ){
            $('returnMsgClient').innerHTML= '<s:text name="ERR.SAE.019"/>';
            //$('returnMsgClient').innerHTML="Số lượng hàng bán phải >0"
            quantity.focus();
            return false;
        }
        if(!isInteger(trim(quantity.value)) || quantity. value.trim()==0){
            $('returnMsgClient').innerHTML= '<s:text name="ERR.SAE.020"/>';
            //$('returnMsgClient').innerHTML="Số lượng phải là số nguyên dương"
            quantity.focus();
            return false;
        }
        if(trim(quantity.value).length >10 ){
            $('returnMsgClient').innerHTML= '<s:text name="ERR.SAE.059"/>';
            //$('returnMsgClient').innerHTML="Số lượng nhập vượt quá giới hạn"
            quantity.focus();
            return false;
        }
        if(trim(note.value).length >500 ){
            $('returnMsgClient').innerHTML= '<s:text name="ERR.SAE.022"/>';
            //$('returnMsgClient').innerHTML="Ghi chú nhập vượt quá giới hạn"
            note.focus();
            return false;
        }

        $('saleToCollaboratorForm.quantity').value=$('saleToCollaboratorForm.quantity').value.trim();

        return true;
    }
    viewStockDetail=function(path){
        //if(document.getElementById("stockModelId").value ==0)
        //{
        //  alert("Chưa chọn mặt hàng");
        document.getElementById("stockModelId").focus();
        //} else {
        openPopup(path, 900, 700);
        //}
    }
    changeTelecom = function() {
        var telecomServiceId = $('telecomServiceId').value;
        updateData("${contextPath}/SaleTransInvoiceAction!getRetailModel.do?telecomServiceId="+ telecomServiceId);
    }
    changeStockModel = function() {
        var fineItemId = $('fineItemsId').value;
        updateData("${contextPath}/SaleTransInvoiceAction!updateStockModelPrice.do?fineItemId="+ fineItemId);
    }
    dojo.event.topic.subscribe("saleRetailAction/insert", function(event, widget){
        if(!checkValidate()){
            event.cancel=true;
        }
        $('quantity').value=$('quantity').value.trim();
        widget.href = "SaleTransInvoiceAction!updateGoods.do";
    });
    dojo.event.topic.subscribe("saleRetailAction/abort", function(event, widget){        
        $('quantity').value ='';
        widget.href = "SaleTransInvoiceAction!changActionType.do";
    });
</script>