<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
    Document   : declareCommPriceItems
    Created on : Mar 27, 2009, 9:58:38 AM
    Author     : DatPV
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>

<fieldset style="width:100%; background-color: #F5F5F5;">
    <legend>Thông tin phí khoản mục</legend>
    <!-- phan hien thi message -->
    <div>
        <tags:displayResult property="message" id="message" type="key"/>
    </div>

    <!-- phan hien thi thong tin ve phi hoa hong -->
    <s:form action="commItemsAction!addOrEditItemFeeChannel" id="commItemFeeChannelForm" method="POST" theme="simple">
<s:token/>

        <s:hidden name="itemChannelForm.itemFeeChannelId" id="itemChannelForm.itemFeeChannelId"/>
        <s:hidden name="itemChannelForm.itemId" id="itemChannelForm.itemId"/>
        <table class="inputTbl">
            <tr>
                <td>Tên khoản mục</td>
                <td class="oddColumn">
                    <s:textfield name="itemChannelForm.itemName" id="itemChannelForm.itemName" readonly="true" maxlength="80" cssClass="txtInputFull"/>
                </td>
            </tr>
            <tr>
                <td>Giá tiền (<font color="red">*</font>)</td>
                <td class="oddColumn">
                    <%--<s:textfield name="itemChannelForm.fee" id="itemChannelForm.fee" readonly="!#request.isAddMode" maxlength="10" onkeyup="textFieldNF(this)" cssClass="txtInputFull"/>--%>
                    <s:textfield name="itemChannelForm.fee" id="itemChannelForm.fee" maxlength="10" onkeyup="textFieldNF(this)" cssClass="txtInputFull"/>
                </td>
                <td>VAT (<font color="red">*</font>)</td>
                <td>
                    <%--<s:textfield name="itemChannelForm.vat" id="itemChannelForm.vat" readonly="!#request.isAddMode" maxlength="10" cssClass="txtInputFull"/>--%>
                    <s:textfield name="itemChannelForm.vat" id="itemChannelForm.vat" maxlength="10" cssClass="txtInputFull"/>
                </td>
            </tr>
            <tr>
                <td>Trạng thái (<font color="red">*</font>)</td>
                <td class="oddColumn">
                    <s:select name="itemChannelForm.status" id="itemChannelForm.status"
                              cssClass="txtInputFull"
                              list="#{'1':'Hiệu lực','2':'Không hiệu lực'}"
                              headerKey="" headerValue="--Chọn trạng thái--"/>
                </td>
                <td>Đối tượng áp phí (<font color="red">*</font>)</td>
                <td>
                    <s:select name="itemChannelForm.channelTypeId" id="itemChannelForm.channelTypeId"
                              cssClass="txtInputFull"
                              headerKey="" headerValue="--Chọn đối tượng áp phí--"
                              list="%{#request.listChannelType != null ? #request.listChannelType : #{}}"
                              listKey="channelTypeId" listValue="name"/>
                </td>
            </tr>
            <tr>
                <td>Ngày bắt đầu (<font color="red">*</font>)</td>
                <td class="oddColumn">
                    <tags:dateChooser id="itemChannelForm.staDate" property="itemChannelForm.staDate" styleClass="txtInputFull" readOnly="${fn:escapeXml(readonly)}" styleClass="txtInputFull"/>
                </td>
                <td>Ngày kết thúc</td>
                <td>
                    <tags:dateChooser id="itemChannelForm.endDate" property="itemChannelForm.endDate" styleClass="txtInputFull" readOnly="${fn:escapeXml(readonly)}" styleClass="txtInputFull"/>
                </td>
            </tr>
        </table>
    </s:form>

    <!-- phan nut tac vu, dong popup -->
    <div align="center" style="padding-top:20px;">
        <button style="width:80px;" onclick="addPrice()">Cập nhật</button>
        <button style="width:80px;" onclick="cancelAddPrice()">Bỏ qua</button>
    </div>

</fieldset>

<s:if test="#request.feeMode == 'closePopup'">

    <script language="javascript">
        window.opener.refreshListCommFees();
        window.close();
    </script>

</s:if>

<script language="javascript">
    
    //dinh danh cho truong price
    textFieldNF($('itemChannelForm.fee'));

    //focus vao truogn dau tien
    $('itemChannelForm.fee').select();
    $('itemChannelForm.fee').focus();

    addPrice = function() {
        $('commItemFeeChannelForm').submit();
        /*
        if(checkRequiredFields() && checkValidFields()) {
            $('commItemFeeChannelForm').submit();
        }
        */
    }

    cancelAddPrice = function() {
        window.close();
    }

    //ham kiem tra cac truong bat buoc
    checkRequiredFields = function() {
        if(trim($('priceForm.price').value) == "") {
            alert('Giá mặt hàng không được để trống');
            $('priceForm.price').select();
            $('priceForm.price').focus();
            return false;
        }
        if(trim($('priceForm.vat').value) == "") {
            alert('VAT không được để trống');
            $('priceForm.vat').select();
            $('priceForm.vat').focus();
            return false;
        }
        /*
        if(trim($('saleServicesPriceForm.staDate').value) == "") {
            alert('Ngày bắt đầu không được để trống');
            $('saleServicesPriceForm.staDate').select();
            $('saleServicesPriceForm.staDate').focus();
            return false;
        }
        */
        if(trim($('priceForm.pricePolicy').value) == "") {
            alert('Chọn Chính sách giá');
            $('priceForm.pricePolicy').focus();
            return false;
        }
        if(trim($('priceForm.status').value) == "") {
            alert('Chọn trạng thái');
            $('priceForm.status').focus();
            return false;
        }

        return true;
    }

    //ham kiem tra du lieu cac truong co hop le hay ko
    checkValidFields = function() {
        if(!isInteger(trim($('priceForm.price').value))) {
            alert('Giá dịch vụ phải là số không âm');
            $('priceForm.price').select();
            $('priceForm.price').focus();
            return false;
        }
        if(!isInteger(trim($('priceForm.vat').value))) {
            alert('VAT phải là số không âm');
            $('priceForm.vat').select();
            $('priceForm.vat').focus();
            return false;
        }
        if($('priceForm.vat').value < 0 || $('priceForm.vat').value > 100) {
            alert('VAT phải là số không âm nằm trong khoảng 0 đến 100');
            $('priceForm.vat').select();
            $('priceForm.vat').focus();
            return false;
        }

        return true;
    }



</script>
