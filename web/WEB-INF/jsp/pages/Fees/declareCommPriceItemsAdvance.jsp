<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
    Document   : declareCommPriceItemsAdvance
    Created on : Sep 29, 2009, 2:15:35 PM
    Author     : MrSun

--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>

<s:form action="commItemsAction!addOrEditItemFeeChannelAdvance" id="commItemFeeChannelForm" method="POST" theme="simple">
<s:token/>


    <fieldset style="width:100%; background-color: #F5F5F5;">
        <legend>Thông tin phí khoản mục</legend>
        <!-- phan hien thi message -->
        <div>
            <tags:displayResult property="message" id="message" type="key"/>
        </div>

        <!-- phan hien thi thong tin ve phi hoa hong -->

        <s:hidden name="itemChannelForm.itemFeeChannelId" id="itemChannelForm.itemFeeChannelId"/>
        <s:hidden name="itemChannelForm.itemId" id="itemChannelForm.itemId"/>
        <table class="inputTbl">
            <tr>
                <td>Tên khoản mục</td>
                <td colspan="3">
                    <s:textfield name="itemChannelForm.itemName" id="itemChannelForm.itemName" readonly="true" maxlength="80" cssClass="txtInputFull"/>
                </td>
            </tr>
            <%--<tr>
                <td><br/></td>
            </tr>--%>
            <%--<tr>
                <td>Giá tiền (<font color="red">*</font>)</td>
                <td class="oddColumn">
                    <s:textfield name="itemChannelForm.fee" id="itemChannelForm.fee" readonly="!#request.isAddMode" maxlength="10" onkeyup="textFieldNF(this)" cssClass="txtInputFull"/>
                </td>
                <td>VAT (<font color="red">*</font>)</td>
                <td>
                    <s:textfield name="itemChannelForm.vat" id="itemChannelForm.vat" readonly="!#request.isAddMode" maxlength="10" cssClass="txtInputFull"/>
                </td>
            </tr>--%>
            <%--<tr>
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
            </tr>--%>
            <tr>
                <td>Ngày bắt đầu (<font color="red">*</font>)</td>
                <td class="oddColumn">
                    <tags:dateChooser id="itemChannelForm.staDate" property="itemChannelForm.staDate" readOnly="${fn:escapeXml(readonly)}" styleClass="txtInputFull"/>
                </td>
                <td>Ngày kết thúc</td>
                <td class="oddColumn">
                    <tags:dateChooser id="itemChannelForm.endDate" property="itemChannelForm.endDate" readOnly="${fn:escapeXml(readonly)}" styleClass="txtInputFull"/>
                </td>
            </tr>
            <tr>
                <td>Giá tiền (<font color="red">*</font>)</td>
                <td class="oddColumn">
                    <s:textfield name="itemChannelForm.fee" id="itemChannelForm.fee" readonly="!#request.isAddMode" maxlength="10" onkeyup="textFieldNF(this)" cssClass="txtInputFull"/>
                </td>
                <td>VAT (<font color="red">*</font>)</td>
                <td>
                    <s:textfield name="itemChannelForm.vat" id="itemChannelForm.vat" readonly="!#request.isAddMode" maxlength="2" cssClass="txtInputFull"/>
                </td>
            </tr>
        </table>

    </fieldset>

    <div id="listCommPriceItemsAdvanceArea">
        <jsp:include page="listCommPriceItemsAdvance.jsp"/>
    </div>
</s:form>

<!-- phan nut tac vu, dong popup -->
<s:if test="#request.isAddMode == true">
    <div align="center" style="padding-top:20px;">
        <%--<button style="width:80px;" onclick="copyFee()">Áp phí</button>--%>
        <button style="width:80px;" onclick="addPrice()">Đồng ý</button>
        <button style="width:80px;" onclick="cancelAddPrice()">Bỏ qua</button>
    </div>
</s:if>
<s:else>
    <div align="center" style="padding-top:20px;">
        <%--<button style="width:80px;">Áp phí</button>--%>
        <button style="width:80px;">Đồng ý</button>
        <button style="width:80px;" onclick="cancelAddPrice()">Bỏ qua</button>
    </div>
</s:else>

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


    copyFee = function() {
        if (!validateFields())
            return;
        if(document.getElementById('tblCommItems') == null){
            return;
        }
        var i = 0;
        var tbl = $( 'tblCommItems' );
        var inputs = [];
        var isCopy = false;
        inputs = tbl.getElementsByTagName( "input" );
        for( i = 1; i < inputs.length; i++ ) {
            if( inputs[i].type == "checkbox" && inputs[i].checked == true ) {
                inputs[i-2].value = $('itemChannelForm.fee').value;
                inputs[i-1].value = $('itemChannelForm.vat').value;
                isCopy = true;
            }
        }
        if (isCopy)
            $( 'message' ).innerHTML = "!!! Đã áp phí cho các đối tượng được chọn";
        else
            $( 'message' ).innerHTML = "!!! Bạn phải chọn đối tượng trong danh sách";
        return;
    }

    addPrice = function() {
        if (!validateFields())
            return;
        if(!isCheckBoxChecked()){
            $( 'message' ).innerHTML = "!!! Bạn phải chọn đối tượng trong danh sách";
            return;
        }
        if(confirm("Bạn có thực sự muốn thêm mới phí KM?")){
            $('commItemFeeChannelForm').submit();
        }
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
    <%--checkRequiredFields = function() {
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
    }--%>

        //ham kiem tra du lieu cac truong co hop le hay ko
        validateFields = function() {
            $('itemChannelForm.fee').value = trim($('itemChannelForm.fee').value);
            if($('itemChannelForm.fee').value == "") {
                $( 'message' ).innerHTML = "!!! Giá tiền không được trống";
                $('itemChannelForm.fee').select();
                $('itemChannelForm.fee').focus();
                return false;
            }
            if($('itemChannelForm.fee').value <=0) {
                $( 'message' ).innerHTML = "!!! Giá tiền phải là số dương";
                $('itemChannelForm.fee').select();
                $('itemChannelForm.fee').focus();
                return false;
            }
            var fee = trim($('itemChannelForm.fee').value.replace(/\,/g,""));
            if(fee != "" && !isInteger(fee)) {
                $('message').innerHTML = "!!! Giá tiền phải là số dương";
                $('itemChannelForm.fee').select();
                $('itemChannelForm.fee').focus();
                return false;
            }
            var dateStart= dojo.widget.byId("itemChannelForm.staDate");
            var dateEnd= dojo.widget.byId("itemChannelForm.endDate");
            if(dateEnd.domNode.childNodes[1].value !="" &&  dateStart.domNode.childNodes[1].value.trim() != "" && !isCompareDate(dateStart.domNode.childNodes[1].value.trim(),dateEnd.domNode.childNodes[1].value.trim(),"VN")){
                $( 'message' ).innerHTML='Ngày bắt đầu phải nhỏ hơn ngày kết thúc';
                dateStart.domNode.childNodes[1].focus();
                return false;
            }
            if(dateStart.domNode.childNodes[1].value ==null || dateStart.domNode.childNodes[1].value==''){
                $('message').innerHTML='!!!Lỗi. Bạn chưa nhập ngày bắt đầu';
                dateStart.domNode.childNodes[1].focus();
                return false;
            }


            $('itemChannelForm.vat').value = trim($('itemChannelForm.vat').value);
            if(!isInteger(trim($('itemChannelForm.vat').value))) {
                $( 'message' ).innerHTML = "!!! VAT phải là số";
                $('itemChannelForm.vat').select();
                $('itemChannelForm.vat').focus();
                return false;
            }
            if($('itemChannelForm.vat').value == "") {
                $( 'message' ).innerHTML = "!!! VAT không được trống";
                $('itemChannelForm.vat').select();
                $('itemChannelForm.vat').focus();
                return false;
            }
            if($('itemChannelForm.vat').value < 0 || $('itemChannelForm.vat').value > 100) {
                $( 'message' ).innerHTML = "!!! VAT phải là số không âm nằm trong khoảng 0 đến 100";
                $('itemChannelForm.vat').select();
                $('itemChannelForm.vat').focus();
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
            if($('priceForm.price').value <= 0) {
                alert('Giá phải là số dương');
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
