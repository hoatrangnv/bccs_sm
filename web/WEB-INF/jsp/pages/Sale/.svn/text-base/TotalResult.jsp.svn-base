

<%--
    Document   : TotalResult
    Created on : Feb 17, 2009, 10:51:45 AM
    Author     : TuanNV
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ page import="com.guhesan.querycrypt.QueryCrypt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<table class="inputTbl2Col">
    <tr>
        <td class="label"><tags:label key="MSG.total.amount.before.tax"/></td>
        <td class="text">
            <s:textfield name="saleToCollaboratorForm.amountNotTax"  theme="simple" onkeyup="textFieldNF(this)" cssStyle="text-align:right" id="saleToCollaboratorForm.amountNotTax" readonly="true" maxlength="1000" cssClass="txtInputFull"/>
        </td>
    </tr>
    <tr>
        <td class="label"> <tags:label key="MSG.money.tax"/></td>
        <td  class="text">
            <s:textfield name="saleToCollaboratorForm.tax"  theme="simple" onkeyup="textFieldNF(this)" cssStyle="text-align:right" id="saleToCollaboratorForm.tax" readonly="true" maxlength="1000" cssClass="txtInputFull"/>
        </td>
    </tr>
    <tr>
        <td class="label"> <tags:label key="MSG.dicountTotal"/></td>
        <td  class="text">
            <s:textfield name="saleToCollaboratorForm.discout"  theme="simple" onkeyup="textFieldNF(this)" cssStyle="text-align:right" id="saleToCollaboratorForm.discout" readonly="true" maxlength="1000" cssClass="txtInputFull"/>
        </td>
    </tr>
    <tr>
        <td class="label"> <tags:label key="MSG.totalPriceAfterRate"/></td>
        <td  class="text">
            <s:textfield name="saleToCollaboratorForm.amountTax"  theme="simple" onkeyup="textFieldNF(this)" cssStyle="text-align:right" id="saleToCollaboratorForm.amountTax" readonly="true" maxlength="1000" cssClass="txtInputFull"/>
        </td>
    </tr>

</table>
<!--/sx:div-->
<script>
    //dinh danh cho truong price
    textFieldNF($('saleToCollaboratorForm.amountNotTax'));
    textFieldNF($('saleToCollaboratorForm.tax'));
    textFieldNF($('saleToCollaboratorForm.discout'));
    textFieldNF($('saleToCollaboratorForm.amountTax'));

    updateSerial=function(stockModelId){
        var Tax = document.getElementById("saleToCollaboratorForm.tax").value;
        var discout = document.getElementById("saleToCollaboratorForm.discout").value;
        var amountNotTax = document.getElementById("saleToCollaboratorForm.amountNotTax").value;
        var amountTax = document.getElementById("saleToCollaboratorForm.amountTax").value;

        gotoAction("listGoods", "saleToCollaboratorAction!refreshListGoods.do?amountNotTax="+ amountNotTax +"&amountTax=" +amountTax+"&discout=" +discout+"&Tax=" +Tax);

    }
    var path = "InputSerialAction!prepareInputSerial.do?ownerType=1&stateId=1&stockModelId="+stockModelId +"&totalReq=" +quantity+"&ownerId="+shopId;
    openPopup(path,800,700);
</script>


