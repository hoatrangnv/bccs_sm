<%-- 
    Document   : saleToPunishInputGood
    Created on : Nov 16, 2010, 3:32:22 AM
    Author     : NamLT
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

            String pageId = request.getParameter("pageId");
            request.setAttribute("lstPrice", request.getSession().getAttribute("lstPrice" + pageId));
%>

<fieldset class="imFieldset">
    <legend><s:text name = "MSG.SAE.015"/></legend>
    <div style="width:100%; height:400px; vertical-align:top">
        <table class="inputTbl2Col">
            <tr>
                <td class="label"><tags:label key="MSG.SAE.017" required="true"/></td>
                <td  class="text">
                    <s:hidden name="channel_sale" id="channel_sale" value="channel_sale"/>
                    <s:hidden id="stockModelId" name = "form.saleTransDetailForm.stockModelId"/>
                    <s:if test="form.saleTransDetailForm == null || form.saleTransDetailForm.stockModelId == null ">
                        <tags:imSearch codeVariable="form.saleTransDetailForm.stockModelCode" nameVariable="form.saleTransDetailForm.stockModelName"
                                       codeLabel="MSG.GOD.007" nameLabel="MSG.GOD.008"
                                       searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                       searchMethodName="getListStockModel"
                                       getNameMethod="getStockModelName"
                                       otherParam="channel_sale;form.receiverCode;form.channelTypeId"
                                       postAction="changeStockModel();"
                                       readOnly="false"/>
                    </s:if><s:else>
                        <tags:imSearch codeVariable="form.saleTransDetailForm.stockModelCode" nameVariable="form.saleTransDetailForm.stockModelName"
                                       codeLabel="MSG.GOD.007" nameLabel="MSG.GOD.008"
                                       searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                       searchMethodName="getListStockModel"
                                       getNameMethod="getStockModelName"
                                       otherParam="channel_sale;form.receiverCode;form.channelTypeId"
                                       postAction="changeStockModel();"
                                       readOnly="true"/>
                    </s:else>
                </td>
                <td class="label" align="left">
                    <s:url action="ViewStockDetailAction!prepareViewStockDetail" id="URLViewStock" encode="true" escapeAmp="false">
                        <s:param name="ownerId" value="0"/>
                    </s:url>
                    <a id="hrefViewStockDetail" href="javascript:viewStockDetail('<s:property escapeJavaScript="true"  value="#attr.URLViewStock"/>')">
                        <s:text name="MSG.SAE.021"/>
                    </a>
                </td>
            </tr>
            <tr>
                <td class="label"><tags:label key="MSG.SAE.018" required="true"/></td>
                <td  class="text" colspan="2">
                    <s:if test="form.saleTransDetailForm == null || form.saleTransDetailForm.stockModelId == null ">
                        <tags:imSelect name="form.saleTransDetailForm.priceId"
                                       theme="simple"
                                       id="priceId" disabled="false"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.SAE.024"
                                       list="lstPrice"
                                       listKey="priceId" listValue="priceName"
                                       onchange=""/>
                    </s:if>
                    <s:else>
                        <tags:imSelect name="form.saleTransDetailForm.priceId"
                                       theme="simple"
                                       id="priceId" disabled="true"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.SAE.024"
                                       list="lstPrice"
                                       listKey="priceId" listValue="priceName"
                                       onchange=""/>
                    </s:else>
                </td>
            </tr>
            <tr>
                <td class="label"><tags:label key="MSG.SAE.019" required="true"/></td>
                <td   class="text" colspan="2">
                    <s:textfield name="form.saleTransDetailForm.quantity" id="form.saleTransDetailForm.quantity" maxlength="10" cssClass="txtInputFull" theme="simple"/>
                </td>				
                <s:hidden name = "form.saleTransDetailForm.note" id = "form.saleTransDetailForm.note" value = ""/>
            </tr>

            <%--tr>
                <td class="label"><tags:label key="MSG.SAE.020"/></td>
                <td  class="text" colspan="2">
                    <s:textarea name="form.saleTransDetailForm.note" id="form.saleTransDetailForm.note" cssClass="txtInputFull" theme="simple"/>
                </td>
            </tr--%>

        </table>
        <div align="center">
            <s:if test="form.saleTransDetailForm == null || form.saleTransDetailForm.stockModelId == null ">
                <tags:submit id="addGoods"
                             formId="form"
                             showLoadingText="true"
                             cssStyle="width: 80px;"
                             targets="saleToPunishDetailArea"
                             value="MSG.SAE.025"
                             preAction="saleToPunishAction!addGoods.do"
                             validateFunction="checkValidate()"/>
                <input type="button" disabled value="<s:text name = "MSG.SAE.033"/>" style="width:80px;">
                <input type="button" disabled value="<s:text name = "MSG.SAE.034"/>" style="width:80px;">
            </s:if>
            <s:else>
                <input type="button" disabled value="<s:text name = "MSG.SAE.025"/>" style="width:80px;">
                <tags:submit id="editGoods"
                             formId="form"
                             showLoadingText="true"
                             cssStyle="width: 80px;"
                             targets="saleToPunishDetailArea"
                             value="MSG.SAE.033"
                             preAction="saleToPunishAction!editGoods.do"
                             validateFunction="checkValidate()"/>
                <tags:submit id="cancelGoods"
                             formId="form"
                             showLoadingText="true"
                             cssStyle="width: 80px;"
                             targets="saleToPunishDetailArea"
                             value="MSG.SAE.034"
                             preAction="saleToPunishAction!cancelEditGoods.do"
                             />
            </s:else>
        </div>
    </div>
</fieldset>


<script type="text/javascript" language="javascript">
    
    dojo.event.topic.subscribe("editGoods", function(event, widget){        
        if (checkValidate())
            alert($('form.saleTransDetailForm.quantity').value);
        widget.href = "saleToPunishAction!editGoods.do?"+ token.getTokenParamString();
    });
        
    dojo.event.topic.subscribe("cancelEditGoods", function(event, widget){
        widget.href = "saleToPunishAction!cancelEditGoods.do?"+ token.getTokenParamString();
    });
    
    checkValidate = function(){
        var priceId = document.getElementById("priceId");
        var quantity = document.getElementById("form.saleTransDetailForm.quantity");
        var note = document.getElementById("form.saleTransDetailForm.note");
        quantity.value=trim(quantity.value);
        note.value=trim(note.value);
    
        //kiem tra ma loai KPP ko duoc de trong
        if(trim($('form.channelTypeId').value) == "") {
            $('displayResultMsg').innerHTML = '<s:text name="ERR.STAFF.0009"/>';
            $('form.channelTypeId').select();
            $('form.channelTypeId').focus();
            return false;
        }
        //kiem tra ma KPP ko duoc de trong
        if(trim($('form.receiverCode').value) == "") {
            $('displayResultMsg').innerHTML = '<s:text name="E.200054"/>';
            $('form.receiverCode').select();
            $('form.receiverCode').focus();
            return false;
        }
        //kiem tra ma KPP ko duoc de trong
        if(trim($('form.receiverName').value) == "") {
            $('displayResultMsg').innerHTML = '<s:text name="E.200054"/>';
            $('form.receiverCode').select();
            $('form.receiverCode').focus();
            return false;
        }
       
        //kiem tra ma mat hang ko duoc de trong
        if(trim($('form.saleTransDetailForm.stockModelCode').value) == "") {
            $('displayResultMsg').innerHTML = '<s:text name="MSG.GOD.035"/>';
            $('form.saleTransDetailForm.stockModelCode').select();
            $('form.saleTransDetailForm.stockModelCode').focus();
            return false;
        }

        //truong ma mat hang khong duoc chua cac ky tu dac biet
        if(!isValidInput($('form.saleTransDetailForm.stockModelCode').value, false, false)) {
            $('displayResultMsg' ).innerHTML = '<s:text name="MSG.GOD.038"/>';
            $('form.saleTransDetailForm.stockModelCode').select();
            $('form.saleTransDetailForm.stockModelCode').focus();
            return false;
        }

        if(priceId.value==null || priceId.value ==''){
            $('displayResultMsg').innerHTML='<s:text name="MSG.SAE.028"/>';
            priceId.focus();
            return false;
        }
        if(quantity == null || trim(quantity.value).length <= 0 ){
            $('displayResultMsg').innerHTML='<s:text name="MSG.SAE.029"/>';
            quantity.select();
            quantity.focus();
            return false;
        }
        if(!isInteger(trim(quantity.value)) || quantity. value.trim()==0){

            $('displayResultMsg').innerHTML='<s:text name="MSG.SAE.030"/>';
            quantity.select();
            quantity.focus();
            return false;
        }
        if(trim(quantity.value).length >10 ){
            $('displayResultMsg').innerHTML='<s:text name="MSG.SAE.031"/>';
            quantity.select();
            quantity.focus();
            return false;
        }
        if(trim(note.value).length >500 ){
            $('displayResultMsg').innerHTML='<s:text name="MSG.SAE.032"/>';
            note.select();
            note.focus();
            return false;
        }
        
        return true;
    }
    
    viewStockDetail=function(path){
        var receiverCode= document.getElementById("form.receiverCode");
        if (receiverCode.value ==''){
            alert(getUnicodeMsg('<s:text name="E.200054"/>'));
        }
        else{        
            path = path + "&channelTypeId="+trim(document.getElementById("form.channelTypeId").value);
            path = path + "&ownerCode="+trim(document.getElementById("form.receiverCode").value);
            path = path + "&ownerType=2";
            openDialog(path, 900, 700,false);
        }
    }
    
    changeStockModel = function() {
        
        $('displayResultMsg').innerHTML="";
        if ($('form.channelTypeId').value == ''){
            $('displayResultMsg').innerHTML="";
            $('form.channelTypeId').focus();
            return;
        }
        if (trim($('form.receiverCode').value) == ''){
            $('displayResultMsg').innerHTML="";
            $('form.receiverCode').focus();
            return;
        }
        if (trim($('form.saleTransDetailForm.stockModelCode').value) == ''){
            $('displayResultMsg').innerHTML="";
            $('form.saleTransDetailForm.stockModelCode').focus();
            return;
        }        
        updateData('${contextPath}/saleToPunishAction!updateListGoodsPrice.do?stockModelCode=' + trim($('form.saleTransDetailForm.stockModelCode').value) + '&channelTypeId=' + $('form.channelTypeId').value+ '&receiverCode=' + trim($('form.receiverCode').value));
    }    
   
</script>