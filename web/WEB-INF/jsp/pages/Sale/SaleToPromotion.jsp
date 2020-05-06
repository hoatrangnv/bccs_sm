<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
    Document   : SaleToPromotion
    Created on : Jul 25, 2009, 2:39:12 AM
    Author     : Vunt
    Desc       : ban hang khuyen mai
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
            request.setAttribute("reasonList", request.getSession().getAttribute("reasonList"));
%>


<s:form action="SaleToPromotionAction" theme="simple" method="post" id="saleToCollaboratorForm">
<s:token/>

    <s:hidden name="staff_export_channel" id="staff_export_channel" value="staff_export_channel"/>
    <tags:imPanel title="MSG.SAE.077">
        <!-- phan nhap thong tin chi tiet ve giao dich -->
        <fieldset class="imFieldset">
            <legend>${fn:escapeXml(imDef:imGetText('MSG.SAE.002'))}</legend>
            <table class="inputTbl6Col">
                <tr>
                    <td class="label"><tags:label key="MSG.customer" required="true"/> </td>
                    <td class="text">
                        <s:textfield name="saleToCollaboratorForm.custName" id="custName" maxlength="100" cssClass="txtInputFull"/>
                    </td>
                    <td class="label"><tags:label key="MSG.sale.date" required="true"/></td>
                    <td class="text">
                        <tags:dateChooser property="saleToCollaboratorForm.dateSale"  id="dateSale" styleClass="txtInput"  insertFrame="false"
                                          readOnly="true"/>
                    </td>
                    <td class="label"><tags:label key="MSG.sale.reason" required="true"/></td>
                    <td class="text">
                        <tags:imSelect name="saleToCollaboratorForm.reasonId"
                                       id="reasonId"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.SAE.013"
                                       list="reasonList"
                                       listKey="reasonId" listValue="reasonName"
                                       onchange=""/>
                    </td>
                </tr>
                <tr>
                    <td class="label"><tags:label key="MSG.company"/> </td>
                    <td class="text">
                        <s:textfield name="saleToCollaboratorForm.company" id="company" maxlength="100" cssClass="txtInputFull"/>
                    </td>
                    <td class="label"><tags:label key="MSG.phone.number"/>
                    <td class="text">
                        <s:textfield name="saleToCollaboratorForm.TeleNumber" id="TeleNumber" maxlength="100" cssClass="txtInputFull"/>
                    </td>
                    <td class="label"><tags:label key="MSG.address"/>
                    <td class="text">
                        <s:textfield name="saleToCollaboratorForm.address" id="address" maxlength="100" cssClass="txtInputFull"/>
                    </td>

                </tr>
                <tr>
                    <td class="label"><tags:label key="MSG.tax.code"/>
                    <td class="text">
                        <s:textfield name="saleToCollaboratorForm.Tin" id="Tin" maxlength="100" cssClass="txtInputFull"/>
                    </td>
                </tr>
                <tr>
                    <td class="label"><tags:label key="MSG.comment"/></td>
                    <td  colspan="5">
                        <s:textarea  name="saleToCollaboratorForm.NoteSaleTrans"  id="description"  cssClass="txtInputFull"/>
                    </td>
                </tr>
            </table>
        </fieldset>

        <sx:div id="inputUpdateGood">
            <jsp:include page="saleToPromotionInputUpdateGoods.jsp"/>
        </sx:div>
        <table style="width:100%;">
            <tr>
                <td colspan="3" align="center">
                    <br />
                    <tags:submit cssStyle="width:100px;" confirm="true" confirmText="MSG.confirm.create.trans"
                                 preAction="SaleToPromotionAction!saveSaleTransRetail.do"
                                 formId="saleToCollaboratorForm" targets="bodyContent"
                                 value="MSG.createTrans" validateFunction="validate()"/>
                </td>
            </tr>


        </tags:imPanel>


    </table>
</s:form>


<script type="text/javascript" language="javascript">
    validate = function(){
        var custName = document.getElementById("custName");
        custName.value=trim(custName.value);
        var company = document.getElementById("company");
        company.value=trim(company.value);
        var tin = document.getElementById("Tin");
        tin.value=trim(tin.value);
        var teleNumber = document.getElementById("TeleNumber");
        teleNumber.value=trim(teleNumber.value);
        var address = document.getElementById("address");
        address.value=trim(address.value);
        var description = document.getElementById("description");
        description.value=trim(description.value);
        var dateExported= dojo.widget.byId("dateSale");

        if (trim(custName.value)== null || trim(custName.value) == ""){
            $('displayResultMsg').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.SAE.001')"/>';
            //$('displayResultMsg').innerHTML="Bạn phải nhập tên khách hàng"
            custName.focus();
            return false;
        }

        if (trim(custName.value).length >100){
            $('displayResultMsg').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.SAE.002')"/>';
            //$('displayResultMsg').innerHTML="Tên khách hàng nhập quá giới hạn"
            custName.focus();
            return false;
        }
        if (isHtmlTagFormat(trim(custName.value))){
            $('displayResultMsg').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.SAE.003')"/>';
            //$('displayResultMsg').innerHTML="Tên khách hàng không được nhập định dạng thẻ HTML"
            custName.focus();
            return false;
        }
        if(compareDates(GetDateSys(),dateExported.domNode.childNodes[1].value)){
            $('displayResultMsg').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.SAE.004')"/>';
            //$('displayResultMsg').innerHTML='Ngày xuất không được lớn hơn ngày hiện tại';
            dateExported.domNode.childNodes[1].focus();
            return false;
        }


        if(trim(company.value).length >100){
            $('displayResultMsg').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.SAE.005')"/>';
            //$('displayResultMsg').innerHTML="Tên công ty nhập quá giới hạn"
            company.focus();
            return false;
        }
        if (isHtmlTagFormat(trim(company.value))){
            $('displayResultMsg').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.SAE.006')"/>';
            //$('displayResultMsg').innerHTML="Tên công ty không được nhập định dạng thẻ HTML"
            company.focus();
            return false;
        }

        if(trim(tin.value).length >100){
            $('displayResultMsg').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.SAE.007')"/>';
            //$('displayResultMsg').innerHTML="Mã số thuế nhập quá giới hạn"
            tin.focus();
            return false;
        }
        if (isHtmlTagFormat(trim(tin.value))){
            $('displayResultMsg').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.SAE.008')"/>';
            //$('displayResultMsg').innerHTML="Mã số thuế không được nhập định dạng thẻ HTML"
            tin.focus();
            return false;
        }

        if(trim(teleNumber.value).length >12){
            $('displayResultMsg').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.SAE.009')"/>';
            //$('displayResultMsg').innerHTML="Số điện thoại nhập quá giới hạn"
            teleNumber.focus();
            return false;
        }
        if (isHtmlTagFormat(trim(teleNumber.value))){
            $('displayResultMsg').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.SAE.010')"/>';
            //$('displayResultMsg').innerHTML="Số điện thoại không được nhập định dạng thẻ HTML"
            teleNumber.focus();
            return false;
        }
        if (!isPhoneNumber(trim(teleNumber.value))){
            $('displayResultMsg').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.SAE.011')"/>';
            //$('displayResultMsg').innerHTML="Số điện thoại nhập không chính xác"
            teleNumber.focus();
            return false;
        }

        if(trim(address.value).length >100){
            $('displayResultMsg').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.SAE.012')"/>';
            //$('displayResultMsg').innerHTML="Địa chỉ nhập quá giới hạn"
            address.focus();
            return false;
        }
        if (isHtmlTagFormat(trim(address.value))){
            $('displayResultMsg').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.SAE.013')"/>';
            //$('displayResultMsg').innerHTML="Địa chỉ không được nhập định dạng thẻ HTML"
            address.focus();
            return false;
        }

        if(trim(description.value).length >200){
            $('displayResultMsg').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.SAE.014')"/>';
            //$('displayResultMsg').innerHTML="Ghi chú nhập quá giới hạn"
            description.focus();
            return false;
        }
        if (isHtmlTagFormat(trim(description.value))){
            $('displayResultMsg').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.SAE.015')"/>';
            //$('displayResultMsg').innerHTML="Ghi chú không được nhập định dạng thẻ HTML"
            description.focus();
            return false;
        }

        return true;
    }
    //
    //    salerChange = function() {
    //        var other = document.getElementById("other").checked;
    //        var saler = document.getElementById("salerId").value;
    //        var custName = document.getElementById("custName").value;
    //        var company = document.getElementById("company").value;
    //        var tin = document.getElementById("Tin").value;
    //        var dateSale = document.getElementById("dateSale").value;
    //        var reasonId = document.getElementById("reasonId").value;
    //        var payMethodId = document.getElementById("payMethodId").value;
    //        var temp = 'saleToCollaboratorForm.other=' + other;
    //        temp = temp + '&saleToCollaboratorForm.salerId=' + saler;
    //        temp = temp + '&saleToCollaboratorForm.custName=' + custName;
    //        temp = temp + '&saleToCollaboratorForm.company=' + company;
    //        temp = temp + '&saleToCollaboratorForm.tin=' + tin;
    //        temp = temp + '&saleToCollaboratorForm.dateSale=' + dateSale;
    //        temp = temp + '&saleToCollaboratorForm.reasonId=' + reasonId;
    //        temp = temp + '&saleToCollaboratorForm.payMethodId=' + payMethodId;
    //        gotoAction("bodyContent",'SaleToPromotionAction!saleTransInfo.do?' + temp);
    //    }
    //
    updateSerial = function() {
        gotoAction("goodList",'SaleToPromotionAction!updateGoodList.do');
    }

    //    changUnit = function() {
    //        var value = document.getElementById("other").checked;
    //        if (value == true) {
    //            document.getElementById("salerId").disabled  = false;
    //        } else if (value == false) {
    //            document.getElementById("salerId").disabled = true;
    //            document.getElementById("salerId").value = "";
    //            gotoAction("bodyContent",'SaleToPromotionAction!saleTransRefresh.do?');
    //        }
    //    }
    //
    function GetDateSys() {
        var time = new Date();
        var dd=time.getDate();
        var mm=time.getMonth()+1;
        var yy=time.getFullYear() ;
        var temp = '';
        if (dd < 10) dd = '0' + dd;
        if (mm < 10) mm = '0' + mm;
        temp = dd + "/" + mm + "/" + yy ;
        return(temp);
    }
</script>

