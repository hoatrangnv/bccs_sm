<%-- 
    Document   : ProcessStockExport
    Created on : Oct 2, 2014, 4:22:58 PM
    Author     : thindm
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
<s:if test="#request.msgPermission == null || #request.msgPermission == ''">
<tags:imPanel title="title.processStockExp.page">
    <!-- phan hien thi thong tin message -->
    <div style="width:100%">
        <tags:displayResult id="message" property="message" propertyValue="messageParam" type="key"/>
        <tags:displayResultList property="lstError" type="key"/>
    </div>
    <!-- phan hien thi thong tin mat hang can nhap kho -->
    <s:form action="processStockExportAction!exportToPartner" theme="simple" method="post" id="exportStockToPartnerForm">
        <div>
            <fieldset class="imFieldsetNoLegend">
                <table class="inputTbl6Col">
                    <tr>
                        <td style="width:15%; "> <tags:label key="MSG.generates.unit_code" /></td>
                        <td class="oddColumn" style="width:25%; " >
                            <tags:imSearch codeVariable="exportStockToPartnerForm.fromOwnerCode" nameVariable="exportStockToPartnerForm.fromOwnerName"
                                           codeLabel="MSG.store.code" nameLabel="MSG.storeName"
                                           searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                           searchMethodName="getListShop"
                                           getNameMethod="getShopName"
                                           target="Test"
                                           updateDataAfterSelectGotoTrungdh3="${contextPath}/addStaffCodeCTVDBAction!getDataForUnitCode.do"
                                           roleList=""
                                           />
                            <%--getNameMethod="getShopName"/>--%>
                        </td>

                        <td style="width:15%; "> <tags:label key="MSG.staff.code"/></td>
                        <td class="oddColumn" style="width:20%; ">
                            <tags:imSearch codeVariable="exportStockToPartnerForm.staffCode" nameVariable="exportStockToPartnerForm.staffName"
                                           codeLabel="MSG.staff.code" nameLabel="MSG.staff.name"
                                           searchClassName="com.viettel.im.database.DAO.ProcessStockExportDAO"
                                           searchMethodName="getListStaff"
                                           getNameMethod="getListStaff"
                                           otherParam="exportStockToPartnerForm.fromOwnerCode"
                                           />
                            <%--getNameMethod="getShopName"/>--%>
                        </td>

                    </tr>
                    <tr>
                   
                        <td style="width:15%; "><tags:label key="MSG.expNoteId" required="true" /></td>
                        <td class="oddColumn" style="width:25%; "id ="Test">
                            <s:textfield name="exportStockToPartnerForm.actionCode" 
                                         id="actionCode"  
                                         cssClass="txtInputFull" 
                                         readOnly="true"
                                         />
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
                        <td class="oddColumn" style="width:25%; ">
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

                    <tr>
                        <td><tags:label key="MSG.GOD.141"  required="true"/></td>
                        <td class="text">
                            <s:textarea name="exportStockToPartnerForm.note" id="note"  cssClass="txtInputFull" maxLength="250" cssStyle="width:120%"/>
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
                        <td style="width:15%"><tags:label key="MSG.imType" required="true"/></td>
                        <td class="text" colspan="7" style="width:90%">
                            <tags:imRadio name="exportStockToPartnerForm.impType"
                                          id="exportStockToPartnerForm.impType"
                                          list="2:MES.CHL.047,1:MSG.byQuantity,3:MSG.GOD.220"
                                          onchange="radioClick(this.value);"/>
                        </td >
                    </tr>
                    <tr id="trImpByFile">
                        <td  style="width:10%"><tags:label key="MSG.generates.file_data" required="true"/></td>
                        <td class="text" colspan="7" style="width:90%">
                            <tags:imFileUpload name="exportStockToPartnerForm.clientFileName" id="exportStockToPartnerForm.clientFileName" serverFileName="exportStockToPartnerForm.serverFileName" cssStyle="width:500px;" extension="xls;xlsx"/>
                        </td>
                    </tr>

                    <tr id="trImpByQuantity">
                        <td style="width:10%;"><tags:label key="MSG.quantity" required="true"/></td>
                        <td class="text" style="width:15%">
                            <s:textfield name="exportStockToPartnerForm.quantity" id="quantity"  cssClass="txtInputFull" maxLength="10"/>
                        </td>
                        <td style="width:75%">&nbsp;</td>
                    </tr>
                    <tr id="trFromToSerial">
                        <td  style="width:10%"><tags:label key="MSG.GOD.227" required="true"/></td>
                        <td class="text" style="width:15%">
                            <s:textfield name="exportStockToPartnerForm.fromSerial" maxlength="20"  id="exportStockToPartnerForm.fromSerial"  cssClass="txtInputFull"  onblur="calculateSerialQuantity();" />
                        </td>
                        <td  style="width:10%"><tags:label key="MSG.GOD.228" required="true"/></td>
                        <td class="text" style="width:15%">
                            <s:textfield name="exportStockToPartnerForm.toSerial" maxlength="20" id="exportStockToPartnerForm.toSerial"  cssClass="txtInputFull" onblur="calculateSerialQuantity();"/>
                        </td>
                        <td class="label" style="width:10%"><tags:label key="MSG.quanlity" required="true"/></td>
                        <td class="text" style="width:15%">
                            <s:textfield name="exportStockToPartnerForm.serialQuantity" maxlength="20" id="exportStockToPartnerForm.serialQuantity" cssClass="txtInputFull" readonly="true" onkeyup="textFieldNF(this)"/>
                        </td>
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
                         confirmText="MSG.STK.066"
                         preAction="processStockExportAction!exportToPartner.do"/>

        </span>

        <!--                </table>

                    </fieldset>-->

    </s:form>
</tags:imPanel>
</s:if>
<s:else>
    <tags:displayResult property="msgPermission" id="msgPermission" type="key"/>
</s:else>
<script language="javascript">

    $('actionCode').focus();

    checkValidate = function() {
        var result = checkRequiredFields();
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
        updateData("${contextPath}/importFromPartnerAction!updateProfilePattern.do?target=profilePattern&stockModelId=" + stockModelId+"&"+token.getTokenParamString());
    }

    //ham kiem tra cac truong bat buoc
    checkRequiredFields = function() {
        $('message').innerHTML="";

        if(trim($('actionCode').value) == "") {
            $('message').innerHTML= '<s:property value="getText('ERR.STK.019')"/>';
            //$('message').innerHTML = "!!!Lỗi. Bạn chưa chọn xuất mã phiếu xuất";
            $('actionCode').select();
            $('actionCode').focus();
            return false;
        }

        if(trim($('actionCode').value).length > 50) {
            $('message').innerHTML= '<s:property value="getText('ERR.STK.020')"/>';
            //$('message').innerHTML = "!!!Lỗi. Mã phiếu xuất không được lớn hơn 20 ký tự!";
            $('actionCode').select();
            $('actionCode').focus();
            return false;
        }
        $('actionCode').value = trim($('actionCode').value);

        if(isHtmlTagFormat($('actionCode').value)){
            $('message').innerHTML= '<s:property value="getText('ERR.STK.021')"/>';
            //$( 'message' ).innerHTML = "Không nên nhập thẻ html vào mã phiếu xuất";
            $('actionCode').select();
            return;
        }


        var impDate = dojo.widget.byId("exportStockToPartnerForm.expDate");
        var strImpDate = impDate.domNode.childNodes[1].value;
        if(trim(strImpDate) == "") {
            $('message').innerHTML= '<s:property value="getText('ERR.STK.022')"/>';
            //$('message').innerHTML = "!!!Lỗi. Ngày xuất không được để trống";
            impDate.domNode.childNodes[1].select();
            impDate.domNode.childNodes[1].focus();
            return false;
        }


        if(trim($('partnerId').value) == "") {
            $('message').innerHTML= '<s:property value="getText('ERR.STK.023')"/>';
            //$('message').innerHTML = "!!!Lỗi. Bạn chưa chọn đối tác";
            $('partnerId').focus();
            return false;
        }

        if(trim($('stockTypeId').value) == "") {
            $('message').innerHTML= '<s:property value="getText('ERR.STK.024')"/>';
            //$('message').innerHTML = "!!!Lỗi. Bạn chưa chọn loại hàng hàng hoá";
            $('stockTypeId').focus();
            return false;
        }
        if(trim($('stockModelId').value) == "") {
            $('message').innerHTML= '<s:property value="getText('ERR.STK.025')"/>';
            //$('message').innerHTML = "!!!Lỗi. Bạn chưa chọn hàng hoá";
            $('stockModelId').focus();
            return false;
        }

        if(trim($('status').value) == "") {
            $('message').innerHTML= '<s:property value="getText('ERR.STK.026')"/>';
            //$('message').innerHTML = "!!!Lỗi. Bạn chưa chọn trạng thái";
            $('status').select();
            $('status').focus();
            return false;
        }
        var note = document.getElementById("note");
                if(trim(note.value) == "") {
                    $('message').innerHTML= '<s:property value="getText('ProcessStockExpDAO.018')"/>';
                    //$('message').innerHTML = "!!!Lỗi. Bạn chưa chọn xuất mã phiếu xuất";
                    $('note').select();
                    $('note').focus();
                    return false;
                }


        if(isHtmlTagFormat(note.value)){
            $('message').innerHTML= "!!!Lỗi.Ghi chú không được chứa thẻ HTML";
            //$( 'message' ).innerHTML = "Không nên nhập thẻ html vào mã phiếu xuất";
            $('note').focus();
            return false;
        }
           
        //nhap hang theo file -> ten file ko duoc de trong
        if( ($('exportStockToPartnerForm.clientFileName').value == "") &&  $('exportStockToPartnerForm.impType2').checked == true) {
            $('message').innerHTML= '<s:property value="getText('ERR.STK.027')"/>';
            //$('message').innerHTML = "!!!Lỗi. Bạn chưa chọn file dữ liệu";
            $('exportStockToPartnerForm.clientFileName').focus();
            return false;
        }

        //nhap hang theo SL -> phai nhap SL
        if( trim($('quantity').value)== "" && $('exportStockToPartnerForm.impType1').checked == true) {
            $('message').innerHTML= '<s:property value="getText('ProcessStockExpDAO.004')"/>';
            //$('message').innerHTML = "!!!Lỗi. Bạn chưa chọn file dữ liệu";
            $('quantity').focus();
            return false;
        }
        
        //var quantity = document.getElementById("quantity");

        if(!isInteger(trim($('quantity').value)) && $('exportStockToPartnerForm.impType1').checked == true) {
            $('message').innerHTML= '<s:property value="getText('ERR.STK.035')"/>';
            //$('message').innerHTML = "!!!Lỗi. Số lượng phải là số không âm";
            $('quantity').select();
            $('quantity').focus();
            return false;
        }
        if (trim($('quantity').value) * 1 <= 0 && $('exportStockToPartnerForm.impType1').checked == true){
            $('message').innerHTML= '<s:property value="getText('ERR.STK.036')"/>';
            //$('message').innerHTML = "!!!Lỗi. Số lượng phải là số không âm";
            $('quantity').select();
            $('quantity').focus();
            return false;
        }

        // Check validate cho fromSerial
        if( trim($('exportStockToPartnerForm.fromSerial').value)== "" && $('exportStockToPartnerForm.impType3').checked == true) {
            $('message').innerHTML= '<s:property value="getText('ProcessStockExpDAO.008')"/>';
            //$('message').innerHTML = "!!!Lỗi. Bạn chưa chọn file dữ liệu";
            $('quantity').focus();
            return false;
        }

        

        if(!isInteger(trim($('exportStockToPartnerForm.fromSerial').value)) && $('exportStockToPartnerForm.impType3').checked == true) {
            $('message').innerHTML= '<s:property value="getText('ERR.SAE.026')"/>';
            //$('message').innerHTML = "!!!Lỗi. Số lượng phải là số không âm";
            $('quantity').select();
            $('quantity').focus();
            return false;
        }
        if (trim($('exportStockToPartnerForm.fromSerial').value) * 1 <= 0 && $('exportStockToPartnerForm.impType3').checked == true){
            $('message').innerHTML= '<s:property value="getText('ERR.SAE.026')"/>';
            //$('message').innerHTML = "!!!Lỗi. Số lượng phải là số không âm";
            $('quantity').select();
            $('quantity').focus();
            return false;
        }


        // Check validate toSerial

        if( trim($('exportStockToPartnerForm.toSerial').value)== "" && $('exportStockToPartnerForm.impType3').checked == true) {
            $('message').innerHTML= '<s:property value="getText('ProcessStockExpDAO.011')"/>';
            //$('message').innerHTML = "!!!Lỗi. Bạn chưa chọn file dữ liệu";
            $('quantity').focus();
            return false;
        }



        if(!isInteger(trim($('exportStockToPartnerForm.toSerial').value)) && $('exportStockToPartnerForm.impType3').checked == true) {
            $('message').innerHTML= '<s:property value="getText('ERR.SAE.028')"/>';
            //$('message').innerHTML = "!!!Lỗi. Số lượng phải là số không âm";
            $('quantity').select();
            $('quantity').focus();
            return false;
        }
        if (trim($('exportStockToPartnerForm.toSerial').value) * 1 <= 0 && $('exportStockToPartnerForm.impType3').checked == true){
            $('message').innerHTML= '<s:property value="getText('ERR.SAE.028')"/>';
            //$('message').innerHTML = "!!!Lỗi. Số lượng phải là số không âm";
            $('quantity').select();
            $('quantity').focus();
            return false;
        }

        //alert(eval(trim($('exportStockToPartnerForm.fromSerial').value)) - eval(trim($('exportStockToPartnerForm.toSerial').value)));
        var fromSerial = trim($('exportStockToPartnerForm.fromSerial').value);
        var toSerial = trim($('exportStockToPartnerForm.toSerial').value);
        var from = new BigNumber(fromSerial);
        var to = new BigNumber(toSerial);
        // alert (from.subtract(to));
        if (from.subtract(to)>0 ){
            $('message').innerHTML= '<s:property value="getText('ERR.SAE.029')"/>';
            //$('message').innerHTML = "!!!Lỗi. Số lượng phải là số không âm";
            $('exportStockToPartnerForm.fromSerial').select();
            $('exportStockToPartnerForm.fromSerial').focus();
            return false;
        }


        //trim cac truong can thiet
        $('quantity').value = trim($('quantity').value);
       

        //so dau dai khong duoc lon hon so cuoi dai
        //        var fromSerial = trim($('exportStockToPartnerForm.fromSerial').value);
        //        var toSerial = trim($('exportStockToPartnerForm.toSerial').value);
        //        var from1 = new BigNumber(fromSerial);
        //        var to1 = new BigNumber(toSerial);
        //        if(from1 * 1 > to1 * 1) {
        //            $('message').innerHTML= '<s:property value="getText('ERR.STK.034')"/>';
        //            //$('message').innerHTML = "!!!Lá»—i. Serial Ä‘áº§u dáº£i khÃ´ng Ä‘Æ°á»£c lá»›n hÆ¡n serial cuá»‘i dáº£i";
        //            $('exportStockToPartnerForm.fromSerial').select();
        //            $('importStockForm.fromSerial').focus();
        //            return false;
        //        }
        //        if(to1 * 1 > from1 * 1+50000) {
        //            $('message').innerHTML= '<s:property value="getText('ERR.STK.131')"/>';
        //            //$('message').innerHTML = "!!!Lá»—i. Serial Ä‘áº§u dáº£i khÃ´ng Ä‘Æ°á»£c lá»›n hÆ¡n serial cuá»‘i dáº£i";
        //            $('exportStockToPartnerForm.fromSerial').select();
        //            $('exportStockToPartnerForm.fromSerial').focus();
        //            return false;
        //        }

        var fromSerial = trim($('exportStockToPartnerForm.fromSerial').value);
        var toSerial = trim($('exportStockToPartnerForm.toSerial').value);
        var from = new BigNumber(fromSerial);
        var to = new BigNumber(toSerial);
        var constant = new BigNumber(50000);
        var oneB= new BigNumber(1);

        if ($('exportStockToPartnerForm.impType3').checked == true && from.subtract(to)>0 ){
            $('message').innerHTML= '<s:property value="getText('ERR.STK.034')"/>';
            //$('message').innerHTML = "!!!Lỗi. Số lượng phải là số không âm";
            $('exportStockToPartnerForm.fromSerial').select();
            $('exportStockToPartnerForm.fromSerial').focus();
            return false;
        }

        if ( $('exportStockToPartnerForm.impType3').checked == true && from<=0){
            $('message').innerHTML= '<s:property value="getText('MSG.generates.from_serial_IMEI0')"/>';
            //$('message').innerHTML = "!!!Lỗi. Số lượng phải là số không âm";
            $('exportStockToPartnerForm.fromSerial').select();
            $('exportStockToPartnerForm.fromSerial').focus();
            return false;
        }

        if ( $('exportStockToPartnerForm.impType3').checked == true && to<=0){
            $('message').innerHTML= '<s:property value="getText('MSG.generates.to_serial_IMEI0')"/>';
            //$('message').innerHTML = "!!!Lỗi. Số lượng phải là số không âm";
            $('exportStockToPartnerForm.toSerial').select();
            $('exportStockToPartnerForm.toSerial').focus();
            return false;
        }

        if( $('exportStockToPartnerForm.impType3').checked == true &&  to.subtract(from) >49999)  {
            $('message').innerHTML= '<s:property value="getText('ERR.STK.131')"/>';
            //$('message').innerHTML = "!!!Lỗi. Serial đầu dải không được lớn hơn serial cuối dải";
            $('exportStockToPartnerForm.fromSerial').select();
            $('exportStockToPartnerForm.fromSerial').focus();
            return false;
        }
       
        return true;

    }

        




    

    radioClick = function(value){

        if(value == 2) {
            $('trImpByFile').style.display = '';
            $('trFromToSerial').style.display = 'none';
            $('trImpByQuantity').style.display = 'none';
        }

        else if (value == 1) {

            $('trImpByFile').style.display = 'none';
            $('trFromToSerial').style.display = 'none';
            $('trImpByQuantity').style.display = '';
            $('quantity').focus();

        }
        else if (value == 3) {

            $('trImpByFile').style.display = 'none';
            $('trFromToSerial').style.display = '';
            $('trImpByQuantity').style.display = 'none';
            $('exportStockToPartnerForm.fromSerial').focus();

        }


    }

    calculateSerialQuantity = function () {

      
        var fromSerial = trim($('exportStockToPartnerForm.fromSerial').value);
        var toSerial = trim($('exportStockToPartnerForm.toSerial').value);
        var from = new BigNumber(fromSerial);
        var to = new BigNumber(toSerial);
        if (fromSerial*1 > 0 && toSerial*1 >0){
          
            var quantity = to.subtract(from).add(1);
       
            var tmp1 = addSeparatorsNF(quantity, '.', '.', ',');


            $('exportStockToPartnerForm.serialQuantity').value = tmp1;
            if ($('exportStockToPartnerForm.serialQuantity').value < 0) {
                $('exportStockToPartnerForm.serialQuantity').value = 0;
            }
            textFieldNF('exportStockToPartnerForm.serialQuantity');
        }
        else {
            $('exportStockToPartnerForm.serialQuantity').value = 0;
        }
    }
    

    var i = 1;
    for(i = 1; i < 4; i=i+1) {
        var radioId = "exportStockToPartnerForm.impType" + i;
        if($(radioId).checked == true) {
            radioClick(i);
            break;
        }

    }
    BigNumber = function(n, p, r){
        var o = this, i;
        if(n instanceof BigNumber){
            for(i in {precision: 0, roundType: 0, _s: 0, _f: 0}) o[i] = n[i];
            o._d = n._d.slice();
            return;
        }
        o.precision = isNaN(p = Math.abs(p)) ? BigNumber.defaultPrecision : p;
        o.roundType = isNaN(r = Math.abs(r)) ? BigNumber.defaultRoundType : r;
        o._s = (n += "").charAt(0) == "-";
        o._f = ((n = n.replace(/[^\d.]/g, "").split(".", 2))[0] = n[0].replace(/^0+/, "") || "0").length;
        for(i = (n = o._d = (n.join("") || "0").split("")).length; i; n[--i] = +n[i]);
        o.round();
    };
    with({$: BigNumber, o: BigNumber.prototype}){
        $.ROUND_HALF_EVEN = ($.ROUND_HALF_DOWN = ($.ROUND_HALF_UP = ($.ROUND_FLOOR = ($.ROUND_CEIL = ($.ROUND_DOWN = ($.ROUND_UP = 0) + 1) + 1) + 1) + 1) + 1) + 1;
        $.defaultPrecision = 40;
        $.defaultRoundType = $.ROUND_HALF_UP;
        o.add = function(n){
            if(this._s != (n = new BigNumber(n))._s)
                return n._s ^= 1, this.subtract(n);
            var o = new BigNumber(this), a = o._d, b = n._d, la = o._f,
            lb = n._f, n = Math.max(la, lb), i, r;
            la != lb && ((lb = la - lb) > 0 ? o._zeroes(b, lb, 1) : o._zeroes(a, -lb, 1));
            i = (la = a.length) == (lb = b.length) ? a.length : ((lb = la - lb) > 0 ? o._zeroes(b, lb) : o._zeroes(a, -lb)).length;
            for(r = 0; i; r = (a[--i] = a[i] + b[i] + r) / 10 >>> 0, a[i] %= 10);
            return r && ++n && a.unshift(r), o._f = n, o.round();
        };
        o.subtract = function(n){
            if(this._s != (n = new BigNumber(n))._s)
                return n._s ^= 1, this.add(n);
            var o = new BigNumber(this), c = o.abs().compare(n.abs()) + 1, a = c ? o : n, b = c ? n : o, la = a._f, lb = b._f, d = la, i, j;
            a = a._d, b = b._d, la != lb && ((lb = la - lb) > 0 ? o._zeroes(b, lb, 1) : o._zeroes(a, -lb, 1));
            for(i = (la = a.length) == (lb = b.length) ? a.length : ((lb = la - lb) > 0 ? o._zeroes(b, lb) : o._zeroes(a, -lb)).length; i;){
                if(a[--i] < b[i]){
                    for(j = i; j && !a[--j]; a[j] = 9);
                    --a[j], a[i] += 10;
                }
                b[i] = a[i] - b[i];
            }
            return c || (o._s ^= 1), o._f = d, o._d = b, o.round();
        };
        o.multiply = function(n){
            var o = new BigNumber(this), r = o._d.length >= (n = new BigNumber(n))._d.length, a = (r ? o : n)._d,
            b = (r ? n : o)._d, la = a.length, lb = b.length, x = new BigNumber, i, j, s;
            for(i = lb; i; r && s.unshift(r), x.set(x.add(new BigNumber(s.join("")))))
                for(s = (new Array(lb - --i)).join("0").split(""), r = 0, j = la; j; r += a[--j] * b[i], s.unshift(r % 10), r = (r / 10) >>> 0);
            return o._s = o._s != n._s, o._f = ((r = la + lb - o._f - n._f) >= (j = (o._d = x._d).length) ? this._zeroes(o._d, r - j + 1, 1).length : j) - r, o.round();
        };
        o.divide = function(n){
            if((n = new BigNumber(n)) == "0")
                throw new Error("Division by 0");
            else if(this == "0")
                return new BigNumber;
            var o = new BigNumber(this), a = o._d, b = n._d, la = a.length - o._f,
            lb = b.length - n._f, r = new BigNumber, i = 0, j, s, l, f = 1, c = 0, e = 0;
            r._s = o._s != n._s, r.precision = Math.max(o.precision, n.precision),
            r._f = +r._d.pop(), la != lb && o._zeroes(la > lb ? b : a, Math.abs(la - lb));
            n._f = b.length, b = n, b._s = false, b = b.round();
            for(n = new BigNumber; a[0] == "0"; a.shift());
            out:
                do{
                for(l = c = 0, n == "0" && (n._d = [], n._f = 0); i < a.length && n.compare(b) == -1; ++i){
                    (l = i + 1 == a.length, (!f && ++c > 1 || (e = l && n == "0" && a[i] == "0")))
                        && (r._f == r._d.length && ++r._f, r._d.push(0));
                    (a[i] == "0" && n == "0") || (n._d.push(a[i]), ++n._f);
                    if(e)
                        break out;
                    if((l && n.compare(b) == -1 && (r._f == r._d.length && ++r._f, 1)) || (l = 0))
                        while(r._d.push(0), n._d.push(0), ++n._f, n.compare(b) == -1);
                }
                if(f = 0, n.compare(b) == -1 && !(l = 0))
                    while(l ? r._d.push(0) : l = 1, n._d.push(0), ++n._f, n.compare(b) == -1);
                for(s = new BigNumber, j = 0; n.compare(y = s.add(b)) + 1 && ++j; s.set(y));
                n.set(n.subtract(s)), !l && r._f == r._d.length && ++r._f, r._d.push(j);
            }
            while((i < a.length || n != "0") && (r._d.length - r._f) <= r.precision);
            return r.round();
        };
        o.mod = function(n){
            return this.subtract(this.divide(n).intPart().multiply(n));
        };
        o.pow = function(n){
            var o = new BigNumber(this), i;
            if((n = (new BigNumber(n)).intPart()) == 0) return o.set(1);
            for(i = Math.abs(n); --i; o.set(o.multiply(this)));
            return n < 0 ? o.set((new BigNumber(1)).divide(o)) : o;
        };
        o.set = function(n){
            return this.constructor(n), this;
        };
        o.compare = function(n){
            var a = this, la = this._f, b = new BigNumber(n), lb = b._f, r = [-1, 1], i, l;
            if(a._s != b._s)
                return a._s ? -1 : 1;
            if(la != lb)
                return r[(la > lb) ^ a._s];
            for(la = (a = a._d).length, lb = (b = b._d).length, i = -1, l = Math.min(la, lb); ++i < l;)
                if(a[i] != b[i])
                    return r[(a[i] > b[i]) ^ a._s];
            return la != lb ? r[(la > lb) ^ a._s] : 0;
        };
        o.negate = function(){
            var n = new BigNumber(this); return n._s ^= 1, n;
        };
        o.abs = function(){
            var n = new BigNumber(this); return n._s = 0, n;
        };
        o.intPart = function(){
            return new BigNumber((this._s ? "-" : "") + (this._d.slice(0, this._f).join("") || "0"));
        };
        o.valueOf = o.toString = function(){
            var o = this;
            return (o._s ? "-" : "") + (o._d.slice(0, o._f).join("") || "0") + (o._f != o._d.length ? "." + o._d.slice(o._f).join("") : "");
        };
        o._zeroes = function(n, l, t){
            var s = ["push", "unshift"][t || 0];
            for(++l; --l;  n[s](0));
            return n;
        };
        o.round = function(){
            if("_rounding" in this) return this;
            var $ = BigNumber, r = this.roundType, b = this._d, d, p, n, x;
            for(this._rounding = true; this._f > 1 && !b[0]; --this._f, b.shift());
            for(d = this._f, p = this.precision + d, n = b[p]; b.length > d && !b[b.length -1]; b.pop());
            x = (this._s ? "-" : "") + (p - d ? "0." + this._zeroes([], p - d - 1).join("") : "") + 1;
            if(b.length > p){
                n && (r == $.DOWN ? false : r == $.UP ? true : r == $.CEIL ? !this._s
                : r == $.FLOOR ? this._s : r == $.HALF_UP ? n >= 5 : r == $.HALF_DOWN ? n > 5
                : r == $.HALF_EVEN ? n >= 5 && b[p - 1] & 1 : false) && this.add(x);
                b.splice(p, b.length - p);
            }
            return delete this._rounding, this;
        };
    }

    
</script>
