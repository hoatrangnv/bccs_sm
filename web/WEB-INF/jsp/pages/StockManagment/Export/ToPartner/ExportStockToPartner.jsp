<%-- 
    Document   : ExportStockToPartner
    Created on : Feb 25, 2010, 2:23:18 PM
    Author     : NamNX
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@ taglib uri="VTdisplaytaglib" prefix="display" %>

<%
            request.setAttribute("contextPath", request.getContextPath());

%>

<tags:imPanel title="MSG.exp.to.partner">
    <!-- phan hien thi thong tin message -->
    <div style="width:100%">
        <tags:displayResult id="message" property="message" propertyValue="messageParam" type="key"/>
    </div>



    <!-- phan hien thi thong tin mat hang can nhap kho -->
    <s:form action="exportStockToPartnerAction!exportToPartner" theme="simple" method="post" id="exportStockToPartnerForm">
<s:token/>

        <div>
            <fieldset class="imFieldsetNoLegend">
                <table class="inputTbl6Col">
                    <tr>
                        <td style="width:15%; "> <tags:label key="MSG.fromStore"/></td>
                        <td class="oddColumn" style="width:25%; ">
                            <tags:imSearch codeVariable="exportStockToPartnerForm.fromOwnerCode" nameVariable="exportStockToPartnerForm.fromOwnerName"
                                           codeLabel="MSG.store.code" nameLabel="MSG.storeName"
                                           searchClassName="com.viettel.im.database.DAO.ReportAccountAgentDAO"
                                           searchMethodName="getListShop"
                                           roleList=""/>
                                           <%--getNameMethod="getShopName"/>--%>
                        </td>
                    </tr>
                    <tr>
                        <td style="width:15%; "><tags:label key="MSG.expNoteId" required="true"/></td>
                        <td class="oddColumn" style="width:25%; ">
                            <s:textfield name="exportStockToPartnerForm.actionCode" id="actionCode" readOnly="true" cssClass="txtInputFull"/>
                        </td>
                        <td style="width:10%; "><tags:label key="MSG.exportDate" required="true"/></td>
                        <td>
                            <tags:dateChooser property="exportStockToPartnerForm.expDate" id="exportStockToPartnerForm.expDate" styleClass="txtInputFull" readOnly="true"/>
                        </td>
                        <td><tags:label key="MSG.partner" required="true"/></td>
                        <td class="oddColumn">
<!--                            --Chọn đối tác---->
                            <tags:imSelect name="exportStockToPartnerForm.partnerId"
                                      id="partnerId"
                                      cssClass="txtInputFull"
                                      headerKey="" headerValue="MSG.GOD.215"
                                      list="listPartners"
                                      listKey="partnerId" listValue="partnerName"/>
                        </td>


                    </tr>                    
                    <tr>
                        <td><tags:label key="MSG.stock.stock.type" required="true"/></td>
                        <td class="oddColumn">
<!--                            --Chọn loại mặt hàng---->
                            <tags:imSelect name="exportStockToPartnerForm.stockTypeId"
                                      id="stockTypeId"
                                      cssClass="txtInputFull"
                                      headerKey="" headerValue="MSG.GOD.216"
                                      list="listStockTypes"
                                      listKey="stockTypeId" listValue="name"
                                      onchange="changeStockType(this.value)"/>
                        </td>                        
                        <td><tags:label key="MSG.generates.goods" required="true"/></td>
                        <td class="oddColumn">
<!--                            --Chọn mặt hàng---->
                            <tags:imSelect name="exportStockToPartnerForm.stockModelId"
                                      id="stockModelId"
                                      cssClass="txtInputFull"
                                      headerKey="" headerValue="MSG.GOD.217"
                                      list="listStockModels"
                                      listKey="stockModelId" listValue="name"
                                      onchange="changeStockModel(this.value)"/>
                        </td>
                        <td class="label"><tags:label key="MSG.generates.status" required="true"/></td>
                        <td class="oddColumn">
<!--                            --Chọn trạng thái---->
                            <tags:imSelect name="exportStockToPartnerForm.status" id="status"
                                      cssClass="txtInputFull" disabled="false"
                                      list="1:MSG.GOD.242,3:MSG.GOD.243"
                                      headerKey="" headerValue="MSG.GOD.189"/>
                        </td>
                    </tr>
                </table>
            </fieldset>
        </div>

        <br />

        <div>
            <fieldset class="imFieldsetNoLegend">
                <table class="inputTbl8Col" style="width:100%" >
                    <tr>
                        <td class="label" style="width:10%"><tags:label key="MSG.exportType" required="true"/></td>
                        <td class="text" colspan="7" style="width:90%">
                            <tags:imRadio name="exportStockToPartnerForm.impType"
                                     id="exportStockToPartnerForm.impType"
                                     list="1:MSG.GOD.219,2:MSG.GOD.220,3:MSG.GOD.221"
                                     onchange="radioClick(this.value)"/>
                        </td>
                    </tr>
                    <tr id="trImpByFile">
                        <td  class="label" style="width:10%"><tags:label key="MSG.generates.file_data" required="true"/></td>
                        <td class="text" colspan="7" style="width:90%">
                            <tags:imFileUpload name="exportStockToPartnerForm.clientFileName" id="exportStockToPartnerForm.clientFileName" serverFileName="exportStockToPartnerForm.serverFileName" cssStyle="width:500px;" extension="xls"/>
                        </td>
                    </tr>
                    <tr id="trImpBySerial">
                        <td class="label" style="width:10%"><tags:label key="MSG.fromSerial" required="true"/></td>
                        <td class="text" style="width:15%">
                            <s:textfield name="exportStockToPartnerForm.fromSerial" id="exportStockToPartnerForm.fromSerial" maxlength="20" cssClass="txtInputFull" onblur="calculateSerialQuantity();"/>
                        </td>
                        <td class="label" style="width:10%"><tags:label key="MSG.toSerial" required="true"/></td>
                        <td class="text" style="width:15%">
                            <s:textfield name="exportStockToPartnerForm.toSerial" id="exportStockToPartnerForm.toSerial" maxlength="20" cssClass="txtInputFull" onblur="calculateSerialQuantity();"/>
                        </td>
                        <td class="label" style="width:10%"><tags:label key="MSG.quanlity" required="true"/></td>
                        <td class="text" style="width:15%">
                            <s:textfield name="exportStockToPartnerForm.serialQuantity" maxlength="10" id="exportStockToPartnerForm.serialQuantity" cssClass="txtInputFull" readonly="true"/>
                        </td>

                    </tr>
                    <tr id="trImpByQuantity">
                        <td class="label" style="width:10%"><tags:label key="MSG.quanlity" required="true"/></td>
                        <td class="text" style="width:15%">
                            <s:textfield name="exportStockToPartnerForm.quantity" maxlength="20" id="exportStockToPartnerForm.quantity" cssClass="txtInputFull"/>
                        </td>
                        <td style="width:75%">&nbsp;</td>
                    </tr>
                </table>
            </fieldset>
        </div>
        <br/>
        <span id="btnImportFromPartner" align="center">
            <tags:submit formId="exportStockToPartnerForm"
                         showLoadingText="true"
                         cssStyle="width:80px;"
                         targets="bodyContent"
                         value="MSG.export"
                         validateFunction="checkValidate()"
                         confirm="true"
                         confirmText="MSG.confirm.export.to.partner"
                         preAction="exportStockToPartnerAction!exportToPartner.do"/>
        </span>

    </s:form>


</tags:imPanel>



<script language="javascript">

    //focus vao truong dau tien
    $('actionCode').select();
    $('actionCode').focus();


    //NamNX
    calculateSerialQuantity = function () {
        var fromSerial = trim($('exportStockToPartnerForm.fromSerial').value);
        var toSerial = trim($('exportStockToPartnerForm.toSerial').value);
        if (fromSerial.length == 0 || fromSerial.length == 0 ){
            $('exportStockToPartnerForm.serialQuantity').value='';
            return;
        }
        
        $('exportStockToPartnerForm.serialQuantity').value = toSerial - fromSerial + 1;
        if ($('exportStockToPartnerForm.serialQuantity').value<=0) {
            $('exportStockToPartnerForm.serialQuantity').value='';
        }

    }

   



    checkValidate = function() {
        var result = checkRequiredFields() && checkValidFields();
        if(result) {
            //startProgressBar();
        }
        return result;
    }

 
    //xu ly su kien khi thay doi loai mat hang
    changeStockType = function(stockTypeId) {
        updateData("${contextPath}/importFromPartnerAction!getComboboxSource.do?target=stockModelId,profilePattern&stockTypeId=" + stockTypeId);
    }

    //xu ly su kien khi thay doi mat  hang
    changeStockModel = function(stockModelId) {
        updateData("${contextPath}/importFromPartnerAction!updateProfilePattern.do?target=profilePattern&stockModelId=" + stockModelId);
    }

    //xu ly su kien khi thay doi kieu nhap hang
    radioClick = function(value){

        if(value == 2) {
            $('trImpByFile').style.display = 'none';
            $('trImpBySerial').style.display = '';
            $('trImpByQuantity').style.display = 'none';


        } else if (value == 3) {

            $('trImpByFile').style.display = 'none';
            $('trImpBySerial').style.display = 'none';
            $('trImpByQuantity').style.display = '';

        } else {

            $('trImpByFile').style.display = '';
            $('trImpBySerial').style.display = 'none';
            $('trImpByQuantity').style.display = 'none';
        }
    }

    //ham kiem tra cac truong bat buoc
    checkRequiredFields = function() {

        if(trim($('actionCode').value) == "") {
            $('message').innerHTML= '<s:text name="ERR.STK.019"/>';
            //$('message').innerHTML = "!!!Lỗi. Bạn chưa chọn xuất mã phiếu xuất";
            $('actionCode').select();
            $('actionCode').focus();
            return false;
        }

        if(trim($('actionCode').value).length > 20) {
            $('message').innerHTML= '<s:text name="ERR.STK.020"/>';
            //$('message').innerHTML = "!!!Lỗi. Mã phiếu xuất không được lớn hơn 20 ký tự!";
            $('actionCode').select();
            $('actionCode').focus();
            return false;
        }
        $('actionCode').value = trim($('actionCode').value);

        if(isHtmlTagFormat($('actionCode').value)){
            $('message').innerHTML= '<s:text name="ERR.STK.021"/>';
            //$( 'message' ).innerHTML = "Không nên nhập thẻ html vào mã phiếu xuất";
            $('actionCode').select();
            return;
        }


        var impDate = dojo.widget.byId("exportStockToPartnerForm.expDate");
        var strImpDate = impDate.domNode.childNodes[1].value;
        if(trim(strImpDate) == "") {
            $('message').innerHTML= '<s:text name="ERR.STK.022"/>';
            //$('message').innerHTML = "!!!Lỗi. Ngày xuất không được để trống";
            impDate.domNode.childNodes[1].select();
            impDate.domNode.childNodes[1].focus();
            return false;
        }


        if(trim($('partnerId').value) == "") {
            $('message').innerHTML= '<s:text name="ERR.STK.023"/>';
            //$('message').innerHTML = "!!!Lỗi. Bạn chưa chọn đối tác";
            $('partnerId').focus();
            return false;
        }

        if(trim($('stockTypeId').value) == "") {
            $('message').innerHTML= '<s:text name="ERR.STK.024"/>';
            //$('message').innerHTML = "!!!Lỗi. Bạn chưa chọn loại hàng hàng hoá";
            $('stockTypeId').focus();
            return false;
        }
        if(trim($('stockModelId').value) == "") {
            $('message').innerHTML= '<s:text name="ERR.STK.025')"/>';
            //$('message').innerHTML = "!!!Lỗi. Bạn chưa chọn hàng hoá";
            $('stockModelId').focus();
            return false;
        }

        if(trim($('status').value) == "") {
            $('message').innerHTML= '<s:text name="ERR.STK.026"/>';
            //$('message').innerHTML = "!!!Lỗi. Bạn chưa chọn trạng thái";
            $('status').select();
            $('status').focus();
            return false;
        }


        var selectedIndex = 1;
        for(selectedIndex = 1; selectedIndex < 4; selectedIndex=selectedIndex+1) {
            var radioId = "exportStockToPartnerForm.impType" + selectedIndex;
            if($(radioId).checked == true) {
                break;
            }
        }
        if(selectedIndex == 1) {
            //nhap hang theo file -> ten file ko duoc de trong
            if($('exportStockToPartnerForm.clientFileName').value == "") {
                $('message').innerHTML= '<s:text name="ERR.STK.027"/>';
                //$('message').innerHTML = "!!!Lỗi. Bạn chưa chọn file dữ liệu";
                $('exportStockToPartnerForm.clientFileName').focus();
                return false;
            }
        } else if (selectedIndex == 2) {
            //nhap hang theo dai -> dai serial khong duoc de trong
            if(trim($('exportStockToPartnerForm.fromSerial').value) == "") {
                $('message').innerHTML= '<s:text name="ERR.STK.028"/>';
                //$('message').innerHTML = "!!!Lỗi. Serial đầu dải không được để trống";
                $('exportStockToPartnerForm.fromSerial').select();
                $('exportStockToPartnerForm.fromSerial').focus();
                return false;
            }
            if(trim($('exportStockToPartnerForm.toSerial').value) == "") {
                $('message').innerHTML= '<s:text name="ERR.STK.029"/>';
                //$('message').innerHTML = "!!!Lỗi. Serial cuối dải không được để trống";
                $('exportStockToPartnerForm.toSerial').select();
                $('exportStockToPartnerForm.toSerial').focus();
                return false;
            }
            if(trim($('exportStockToPartnerForm.serialQuantity').value) == "") {
                $('message').innerHTML= '<s:text name="ERR.STK.030"/>';
                //$('message').innerHTML = "!!!Lỗi. Số lượng serial không được để trống";
                $('exportStockToPartnerForm.serialQuantity').select();
                $('exportStockToPartnerForm.serialQuantity').focus();
                return false;
            }

        } else if (selectedIndex == 3) {
            //nhap hang theo so luong -> so luong khong duoc de trong
            if(trim($('exportStockToPartnerForm.quantity').value) == "") {
                $('message').innerHTML= '<s:text name="ERR.STK.031"/>';
                //$('message').innerHTML = "!!!Lỗi. Số lượng không được để trống";
                $('exportStockToPartnerForm.quantity').select();
                $('exportStockToPartnerForm.quantity').focus();
                return false;
            }
        } else {
            //


        }

        return true;
    }

    //ham kiem tra du lieu cac truong co hop le hay ko
    checkValidFields = function() {


        var selectedIndex = 1;
        for(selectedIndex = 1; selectedIndex < 4; selectedIndex=selectedIndex+1) {
            var radioId = "exportStockToPartnerForm.impType" + selectedIndex;
            if($(radioId).checked == true) {
                break;
            }
        }

        if (selectedIndex == 2) {
            //nhap hang theo dai -> dai serial va so luong serial phai la so khogn am
            if(!isInteger(trim($('exportStockToPartnerForm.fromSerial').value))) {
                $('message').innerHTML= '<s:text name="ERR.STK.032"/>';
                //$('message').innerHTML = "!!!Lỗi. Serial đầu dải phải là số không âm";
                $('exportStockToPartnerForm.fromSerial').select();
                $('exportStockToPartnerForm.fromSerial').focus();
                return false;
            }
            if(!isInteger(trim($('exportStockToPartnerForm.toSerial').value))) {
                $('message').innerHTML= '<s:text name="ERR.STK.033"/>';
                //$('message').innerHTML = "!!!Lỗi. Serial cuối dải phải là số không âm";
                $('exportStockToPartnerForm.toSerial').select();
                $('exportStockToPartnerForm.toSerial').focus();
                return false;
            }
            if(!isInteger(trim($('exportStockToPartnerForm.serialQuantity').value))) {
                $('message').innerHTML= '<s:text name="ERR.STK.033"/>';
                //$('message').innerHTML = "!!!Lỗi. Số lượng serial phải là số không âm";
                $('exportStockToPartnerForm.serialQuantity').select();
                $('exportStockToPartnerForm.serialQuantity').focus();
                return false;
            }

            //so dau dai khong duoc lon hon so cuoi dai
            var fromSerial = trim($('exportStockToPartnerForm.fromSerial').value);
            var toSerial = trim($('exportStockToPartnerForm.toSerial').value);
            if(fromSerial * 1 > toSerial * 1) {
                $('message').innerHTML= '<s:text name="ERR.STK.034"/>';
                //$('message').innerHTML = "!!!Lỗi. Serial đầu dải không được lớn hơn serial cuối dải";
                $('exportStockToPartnerForm.fromSerial').select();
                $('exportStockToPartnerForm.fromSerial').focus();
                return false;
            }

            //trim cac truong can thiet
            $('exportStockToPartnerForm.fromSerial').value = trim($('exportStockToPartnerForm.fromSerial').value);
            $('exportStockToPartnerForm.toSerial').value = trim($('exportStockToPartnerForm.toSerial').value);
            $('exportStockToPartnerForm.serialQuantity').value = trim($('exportStockToPartnerForm.serialQuantity').value);

        } else if (selectedIndex == 3) {
            //nhap hang theo so luong -> so luong phai la so khong am
            
            if(!isInteger(trim($('exportStockToPartnerForm.quantity').value))) {
                $('message').innerHTML= '<s:text name="ERR.STK.035"/>';
                //$('message').innerHTML = "!!!Lỗi. Số lượng phải là số không âm";
                $('exportStockToPartnerForm.quantity').select();
                $('exportStockToPartnerForm.quantity').focus();
                return false;
            }
            if (trim($('exportStockToPartnerForm.quantity').value) * 1 <= 0){
                $('message').innerHTML= '<s:text name="ERR.STK.036"/>';
                //$('message').innerHTML = "!!!Lỗi. Số lượng phải là số không âm";
                $('exportStockToPartnerForm.quantity').select();
                $('exportStockToPartnerForm.quantity').focus();
                return false;
            }

            //trim cac truong can thiet
            $('exportStockToPartnerForm.quantity').value = trim($('exportStockToPartnerForm.quantity').value);

        } else {
            //


        }

        return true;
    }

    //update viec an hien cac vung, tuy thuoc vao kieu nhap hang
    var i = 1;
    for(i = 1; i < 4; i=i+1) {
        var radioId = "exportStockToPartnerForm.impType" + i;
        if($(radioId).checked == true) {
            radioClick(i);
            break;
        }

    }

</script>
