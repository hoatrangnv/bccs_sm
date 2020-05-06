<%-- 
    Document   : inputGoodsV3
    Created on : Dec 26, 2012, 3:30:00 PM
    Author     : trungdh3_s
--%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>

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
                    <s:hidden id="stockModelId" name = "form.saleTransDetailForm.stockModelId"/>
                    <s:if test="form.saleTransDetailForm == null || form.saleTransDetailForm.stockModelId == null ">
                        <tags:imSearch codeVariable="form.saleTransDetailForm.stockModelCode" nameVariable="form.saleTransDetailForm.stockModelName"
                                       codeLabel="MSG.GOD.007" nameLabel="MSG.GOD.008"
                                       searchClassName="com.viettel.im.database.DAO.SaleToRetailV3DAO"
                                       searchMethodName="getListStockModel"
                                       getNameMethod="getStockModelName" otherParam="staff_export_channel"
                                       postAction="changeStockModel();"
                                       readOnly="false"/>
                    </s:if><s:else>
                        <tags:imSearch codeVariable="form.saleTransDetailForm.stockModelCode" nameVariable="form.saleTransDetailForm.stockModelName"
                                       codeLabel="MSG.GOD.007" nameLabel="MSG.GOD.008"
                                       searchClassName="com.viettel.im.database.DAO.SaleToRetailV3DAO"
                                       searchMethodName="getListStockModel"
                                       getNameMethod="getStockModelName"
                                       postAction="changeStockModel();"
                                       readOnly="true"/>
                    </s:else>
                </td>
                <td class="label" align="left">
                    <s:url action="ViewStockDetailAction!prepareViewStockDetail" id="URLViewStock" encode="true" escapeAmp="false">
                        <s:param name="ownerType" value="2"/>
                        <s:param name="ownerId" value="#session.userToken.userID"/>
                        <s:param name="stockModelId" value="form.saleTransDetailForm.stockModelCode"/>
                    </s:url>
                    <a id="hrefViewStockDetail" href="javascript:viewStockDetail('<s:property escapeJavaScript="true"  value="#attr.URLViewStock"/>')">
                        <s:text name="MSG.SAE.021"/>
                    </a>
                </td>
            </tr>
            <tr>
                <td class="label"><tags:label key="MSG.SAE.018" required="true"/></td>
                <td  class="text" colspan="2">
                    <%--tags:imSelect name="form.saleTransDetailForm.priceId"
                                   theme="simple"
                                   id="priceId" disabled="${fn:escapeXml(requestScope.edit)}"
                                   cssClass="txtInputFull"
                                   headerKey="" headerValue="MSG.SAE.024"
                                   list="listPrice"
                                   listKey="priceId" listValue="priceName"
                                   onchange=""/--%>
                    <s:if test="form.saleTransDetailForm == null || form.saleTransDetailForm.stockModelId == null ">
                        <tags:imSelect name="form.saleTransDetailForm.priceId"
                                       theme="simple"
                                       id="priceId" disabled="${fn:escapeXml(requestScope.edit)}"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.SAE.024"
                                       list="lstPrice"
                                       listKey="priceId" listValue="priceName"
                                       onchange=""/>
                    </s:if><s:else>
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
                    <s:textfield name="form.saleTransDetailForm.quantity" id="quantity" maxlength="10" cssClass="txtInputFull" theme="simple"/>
                </td>
            </tr>
            <tr>
                <td class="label"><tags:label key="MSG.SAE.020"/></td>
                <td  class="text" colspan="2">
                    <s:textarea name="form.saleTransDetailForm.note" id="note" cssClass="txtInputFull" theme="simple"/>
                </td>
            </tr>
        </table>
        <div align="center">
            <s:if test="form.saleTransDetailForm == null || form.saleTransDetailForm.stockModelId == null ">
                <tags:submit id="addGoods"
                             formId="form"
                             showLoadingText="true"
                             cssStyle="width: 80px;"
                             targets="saleToRetailDetailDiv"
                             value="Add"
                             preAction="saleToRetailV3Action!addGoods.do"
                             validateFunction="checkValidate()"/>
                <input type="button" disabled value="<s:text name = "Edit"/>" style="width:80px;">
                <input type="button" disabled value="<s:text name = "Cancel"/>" style="width:80px;">
            </s:if>
            <s:else>
                <input type="button" disabled value="<s:text name = "Add"/>" style="width:80px;">
                <tags:submit id="editGoods"
                             formId="form"
                             showLoadingText="true"
                             cssStyle="width: 80px;"
                             targets="saleToRetailDetailDiv"
                             value="Edit"
                             preAction="saleToRetailV3Action!editGoods.do"
                             validateFunction="checkValidate()"/>
                <tags:submit id="cancelGoods"
                             formId="form"
                             showLoadingText="true"
                             cssStyle="width: 80px;"
                             targets="saleToRetailDetailDiv"
                             value="Cancel"
                             preAction="saleToRetailV3Action!cancelEditGoods.do"/>
            </s:else>
        </div>
    </div>
</fieldset>

<script type="text/javascript" language="javascript">
    changeStockModel = function() {
        var stockModelCode  = $('form.saleTransDetailForm.stockModelCode').value;
        updateData('${contextPath}/saleToRetailV3Action!updateListModelPrice.do?stockModelCode='+ stockModelCode);
    }

    viewStockDetail=function(path){
        openPopup(path, 800, 600);
    }

    checkValidate = function(){
        var priceId = document.getElementById("priceId");
        var quantity = document.getElementById("quantity");
        var note = document.getElementById("note");
        quantity.value=trim(quantity.value);
        note.value=trim(note.value);

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

</script>
