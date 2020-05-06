<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : processStockImp
    Created on : Aug 27, 2010, 5:43:23 PM
    Author     : Doan Thanh 8
    Desc       : chuc nang xu ly kho hang cho PKH
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
            request.setAttribute("contextPath", request.getContextPath());

%>

<tags:imPanel title="MSG.import.from.partner">
    <!-- phan hien thi thong tin message -->
    <div style="width:100%">
        <tags:displayResult id="message" property="message" propertyValue="messageParam" type="key"/>
    </div>

    <!-- phan hien thi link download -->
    <div align="center">
        <s:if test="#request.errLogMessage != null">
            <s:text name="%{#request.errLogMessage}"/> <!-- hien thi message: neu he thong khogn tu dong download, tai tep log loi -->
            <a href="${contextPath}/${fn:escapeXml(errLogPath)}">${fn:escapeXml(imDef:imGetText('MSG.here'))}</a>
        </s:if>
    </div>

    <!-- phan hien thi link download phieu nhap kho -->
    <div align="center">
        <s:if test="#request.notePrintPath !=null && #request.notePrintPath != ''">
            <script>
                window.open('${contextPath}/${fn:escapeXml(notePrintPath)}','','toolbar=yes,scrollbars=yes');
            </script>
            <div><a href='${contextPath}/${fn:escapeXml(notePrintPath)}' ><tags:label key="MSG.download2.file.here"/></a></div>
        </s:if>
    </div>


    <!-- phan hien thi thong tin mat hang can nhap kho -->
    <s:form action="importFromPartnerAction!importFromPartner" theme="simple" method="post" id="importStockForm">
<s:token/>

        <s:hidden name="importStockForm.actionId"/>
        <s:hidden name="importStockForm.expSimActionId"/>
        <s:hidden name="importStockForm.expSimActionCode"/>

        <div class="divHasBorder">
            <table class="inputTbl6Col">
                <tr>
                    <td style="width:13%; "> <tags:label key="MSG.toStore" required="true"/></td>
                    <td class="oddColumn" style="width:20%; ">
                        <tags:imSearch codeVariable="importStockForm.toOwnerCode"  nameVariable="importStockForm.toOwnerName"
                                       codeLabel="MSG.RET.066" nameLabel="MSG.RET.067"
                                       searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                       searchMethodName="getListShop"
                                       getNameMethod="getShopName"/>
                    </td>
                    <td style="width:13%; "> <tags:label key="MSG.import.bill.code" required="true"/></td>
                    <td class="oddColumn" style="width:20%; ">
                        <s:textfield name="importStockForm.actionCode" readOnly="true" maxLength="25" id="actionCode" cssClass="txtInputFull"/>
                    </td>
                    <td style="width:13%; "><tags:label key="MSG.importDate" required="true"/></td>
                    <td>
                        <tags:dateChooser property="importStockForm.impDate" id="importStockForm.impDate" styleClass="txtInputFull"/>
                    </td>
                </tr>
                <tr>
                    <td><tags:label key="MSG.partner" required="true"/></td>
                    <td class="oddColumn">
                        <tags:imSelect name="importStockForm.partnerId"
                                       id="partnerId"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.GOD.215"
                                       list="listPartners"
                                       listKey="partnerId" listValue="partnerName" onchange="" />
                    </td>
                    <td rowspan="2"> <tags:label key="MSG.profileTemplate"/></td>
                    <td colspan="3" rowspan="2">
                        <s:textarea name="importStockForm.profilePattern" id="profilePattern" readonly="true" cssClass="txtInputFull"/>
                    </td>
                </tr>
                <tr>
                    <td><tags:label key="MSG.stock.stock.type" required="true"/></td>
                    <td class="oddColumn">
                        <tags:imSelect name="importStockForm.stockTypeId"
                                       id="stockTypeId"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.GOD.216"
                                       list="listStockTypes"
                                       listKey="stockTypeId" listValue="name"
                                       onchange="changeStockType(this.value)"/>
                    </td>
                </tr>
                <tr>
                    <td><tags:label key="MSG.generates.goods" required="true"/></td>
                    <td class="oddColumn">
                        <tags:imSelect name="importStockForm.stockModelId"
                                       id="stockModelId"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.GOD.217"
                                       list="listStockModels"
                                       listKey="stockModelId" listValue="name"
                                       onchange="changeStockModel(this.value)"/>
                    </td>
                    <td><tags:label key="MSG.stateId" required="true"/></td>
                    <td class="oddColumn">
                        <tags:imSelect name="importStockForm.stateId"
                                       id="stateId"
                                       cssClass="txtInputFull"
                                       list="1:MSG.GOD.new, 3:MSG.GOD.error"/>
                    </td>
                    <td id="lblImportKit" style="display:none"><tags:label key="MSG.selectSimType" required="true"/></td>
                    <td id="cboImportKit" style="display:none">
                        <tags:imSelect name="importStockForm.stockModelSimId"
                                       id="stockModelSimId"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.GOD.218"
                                       list="listStockModelSim"
                                       listKey="stockModelSimId" listValue="name"/>
                        <!--font color="red" style="font-style:oblique"><tags:label key="MSG.select.sim.sub.store"/></font-->
                    </td>
                    <td id="lblImportSim1" style="display:none"><tags:label key="MSG.kind" required="true"/></td>
                    <td id="txtImportSim1" style="display:none" class="oddColumn">
                        <s:textfield name="importStockForm.kind" id="kind" maxLength="2" cssClass="txtInputFull"/>
                    </td>
                    <td id="lblImportSim2" style="display:none"><tags:label key="MSG.A3A8" required="true"/></td>
                    <td id="txtImportSim2" style="display:none" class="oddColumn" >
                        <s:textfield name="importStockForm.a3a8" id="a3a8" maxLength="1" cssClass="txtInputFull"/>
                    </td>
                </tr>
            </table>
        </div>

        <br />

        <div class="divHasBorder">
            <table class="inputTbl8Col" style="width:100%" >
                <tr>
                    <td class="label" style="width:10%"><tags:label key="MSG.imType" required="true"/></td>
                    <td class="text" colspan="7" style="width:90%">
                        <%--tags:imRadio name="importStockForm.impType"
                                      id="importStockForm.impType"
                                      list="1:MSG.GOD.219,2:MSG.GOD.220,3:MSG.GOD.221"
                                      onchange="radioClick(this.value)"/--%>
                        <tags:imRadio name="importStockForm.impType"
                                      id="importStockForm.impType"
                                      list="1:MSG.GOD.219"
                                      onchange="radioClick(this.value)"/>
                    </td>
                </tr>
                <tr id="trImpByFile">
                    <td  class="label" style="width:10%"><tags:label key="MSG.generates.file_data" required="true"/></td>
                    <td class="text" colspan="7" style="width:90%">
                        <tags:imFileUpload 
                            name="importStockForm.clientFileName"
                            id="importStockForm.clientFileName"
                            serverFileName="importStockForm.serverFileName" cssStyle="width:500px;" extension="txt;xls"/>
                    </td>
                </tr>
                <tr id="trImpBySerial">
                    <td class="label" style="width:10%"><tags:label key="MSG.fromSerial" required="true"/></td>
                    <td class="text" style="width:15%">
                        <s:textfield name="importStockForm.fromSerial" id="importStockForm.fromSerial" maxlength="20" cssClass="txtInputFull" onblur="calculateSerialQuantity();"/>
                    </td>
                    <td class="label" style="width:10%"><tags:label key="MSG.toSerial" required="true"/></td>
                    <td class="text" style="width:15%">
                        <s:textfield name="importStockForm.toSerial" id="importStockForm.toSerial" maxlength="20" cssClass="txtInputFull" onblur="calculateSerialQuantity();"/>
                    </td>
                    <td class="label" style="width:10%"><tags:label key="MSG.quanlity" required="true"/></td>
                    <td class="text" style="width:15%">
                        <s:textfield name="importStockForm.serialQuantity" maxlength="10" id="importStockForm.serialQuantity" cssClass="txtInputFull" readonly="true"/>
                    </td>
                    <td class="label" style="width:10%"> <tags:label key="MSG.distanceStep"/></td>
                    <td class="text" style="width:15%">
                        <tags:imSelect name="importStockForm.distanceStep"
                                       id="distanceStep"
                                       cssClass="txtInputFull"
                                       headerKey="0" headerValue="MSG.GOD.222"
                                       list="listDistance"
                                       listKey="value" listValue="name"
                                       onchange="changeDistanceStep(this.value)"/>
                    </td>
                </tr>
                <tr id="trImpByQuantity">
                    <td class="label" style="width:10%"><tags:label key="MSG.quantity" required="true"/></td>
                    <td class="text" style="width:15%">
                        <s:textfield name="importStockForm.quantity" maxlength="20" id="importStockForm.quantity" cssClass="txtInputFull"/>
                    </td>
                    <td style="width:75%">&nbsp;</td>
                </tr>
            </table>
        </div>
        <span id="btnImportFromPartner" align="center">
            <tags:submit formId="importStockForm"
                         showLoadingText="true"
                         cssStyle="width:80px;"
                         targets="bodyContent"
                         value="MSG.importGoods"
                         validateFunction="checkValidate()"
                         preAction="processStockImpAction!importFromPartner.do"
                         showProgressDiv="true"/>
        </span>
        <span id="btnCheckSerial" align="center">
            <tags:submit formId="importStockForm"
                         showLoadingText="true"
                         cssStyle="width:80px;"
                         targets="divListSerialRange"
                         value="MSG.check"
                         validateFunction="checkValidSerial()"
                         preAction="importFromPartnerAction!checkSerialRange.do"
                         />
            <%--sx:submit parseContent="true" executeScripts="true"
                       formId="importStockForm" loadingText="Đang thực hiện..."
                       cssStyle="width:80px;"
                       showLoadingText="true" targets="divListSerialRange"
                       value="Kiểm tra"  beforeNotifyTopics="importFromPartnerAction/checkSerialRange"/--%>

        </span>
        <span>
            <tags:submit formId="importStockForm"
                         showLoadingText="true"
                         cssStyle="width:80px;"
                         targets="divListSerialRange"
                         value="MSG.reset"
                         validateFunction="resetInput()"
                         preAction="importFromPartnerAction!resetInput.do"/>
        </span>
        
        <div id="divListSerialRange">
            <s:if test="listSerialRange != null and listSerialRange.size > 0">
                <jsp:include page="listSerialRange.jsp"/>
            </s:if>
        </div>
    </s:form>

    <div>
        <tags:progressDiv updateUrl="${contextPath}/importFromPartnerAction!updateProgressDiv.do"/>
    </div>


    <!-- phan hien thi progressbar -->
    <%--div>
        <table class="inputTbl">
            <tr>
                <td>
                    <tags:progressBar intervalTime="5000" updateUrl="${contextPath}/importFromPartnerAction!updateProgressBar.do"/>
                </td>
            </tr>
        </table>
    </div--%>
</tags:imPanel>



<script language="javascript">

    //focus vao truong dau tien
    $('importStockForm.toOwnerCode').select();
    $('importStockForm.toOwnerCode').focus();
    $('btnCheckSerial').style.visibility  = 'hidden';

    resetInput=function(){
        $('actionCode').value='';
        $('actionCode').focus();
        $('btnCheckSerial').style.visibility  = 'hidden';
        $('btnImportFromPartner').style.visibility  = 'visible';
        $('importStockForm.impType1').checked = 'true';
        $('importStockForm.fromSerial').value  = '';
        $('importStockForm.toSerial').value  = '';
        $('importStockForm.serialQuantity').value  = '';

        $('partnerId').value  = '';
        $('stockTypeId').value  = '';
        $('stockModelId').value  = '';
        $('distanceStep').value  = '0';
        radioClick('1');
        changeStockType('');
        return true;

    }
    //NamNX
    calculateSerialQuantity = function () {
        var fromSerial = trim($('importStockForm.fromSerial').value);
        var toSerial = trim($('importStockForm.toSerial').value);
        var from = new BigNumber(fromSerial);
        var to = new BigNumber(toSerial);
        var quantity = to.subtract(from).add(1);

        $('importStockForm.serialQuantity').value = quantity;
        if ($('importStockForm.serialQuantity').value < 0) {
            $('importStockForm.serialQuantity').value = 0;
        }

    }
    string_subtract = function (str1, str2) {
        var pos = str1.indexOf(str2);
        if( pos == -1 ){
            return str1;
        }
        else {
            var result = str1.substr(0, pos) + str1.substr(pos + str2.length);
            return result;
        }
    }
    changeDistanceStep = function(value){
        if(value==0) {
            $('btnCheckSerial').style.visibility  = 'hidden';
            $('divListSerialRange').style.visibility  = 'hidden';
            $('btnImportFromPartner').style.visibility  = 'visible';
        } else {
            $('btnImportFromPartner').style.visibility  = 'hidden';
            $('btnCheckSerial').style.visibility  = 'visible';
            $('divListSerialRange').style.visibility  = 'visible';
        }
    }
    checkValidSerial=function(){

        var selectedIndex = 1;
        for(selectedIndex = 1; selectedIndex < 4; selectedIndex=selectedIndex+1) {
            var radioId = "importStockForm.impType" + selectedIndex;
            if($(radioId).checked == true) {
                break;
            }
        }
        if(selectedIndex == 1) {

        } else if (selectedIndex == 2) {
            //nhap hang theo dai -> dai serial khong duoc de trong
            if(trim($('importStockForm.fromSerial').value) == "") {
                $('message').innerHTML= '<s:text name="ERR.STK.028"/>';
                //$('message').innerHTML = "!!!Lỗi. Serial đầu dải không được để trống";
                $('importStockForm.fromSerial').select();
                $('importStockForm.fromSerial').focus();
                return false;
            }
            if(trim($('importStockForm.toSerial').value) == "") {
                $('message').innerHTML= '<s:text name="ERR.STK.029"/>';
                //$('message').innerHTML = "!!!Lỗi. Serial cuối dải không được để trống";
                $('importStockForm.toSerial').select();
                $('importStockForm.toSerial').focus();
                return false;
            }
            if(trim($('importStockForm.serialQuantity').value) == "") {
                $('message').innerHTML= '<s:text name="ERR.STK.030"/>';
                //$('message').innerHTML = "!!!Lỗi. Số lượng serial không được để trống";
                $('importStockForm.serialQuantity').select();
                $('importStockForm.serialQuantity').focus();
                return false;
            }

            if(!isInteger(trim($('importStockForm.fromSerial').value))) {
                $('message').innerHTML= '<s:text name="ERR.STK.032"/>';
                //$('message').innerHTML = "!!!Lỗi. Serial đầu dải phải là số không âm";
                $('importStockForm.fromSerial').select();
                $('importStockForm.fromSerial').focus();
                return false;
            }
            if(!isInteger(trim($('importStockForm.toSerial').value))) {
                $('message').innerHTML= '<s:text name="ERR.STK.033"/>';
                //$('message').innerHTML = "!!!Lỗi. Serial cuối dải phải là số không âm";
                $('importStockForm.toSerial').select();
                $('importStockForm.toSerial').focus();
                return false;
            }
            if(!isInteger(trim($('importStockForm.serialQuantity').value))) {
                $('message').innerHTML= '<s:text name="ERR.STK.036"/>';
                //$('message').innerHTML = "!!!Lỗi. Số lượng serial phải là số không âm";
                $('importStockForm.serialQuantity').select();
                $('importStockForm.serialQuantity').focus();
                return false;
            }
            //so dau dai khong duoc lon hon so cuoi dai
            var fromSerial = trim($('importStockForm.fromSerial').value);
            var toSerial = trim($('importStockForm.toSerial').value);
            if(fromSerial * 1 > toSerial * 1) {
                $('message').innerHTML= '<s:text name="ERR.STK.034"/>';
                //$('message').innerHTML = "!!!Lỗi. Serial đầu dải không được lớn hơn serial cuối dải";
                $('importStockForm.fromSerial').select();
                $('importStockForm.fromSerial').focus();
                return false;
            }

        } else if (selectedIndex == 3) {
            //nhap hang theo so luong -> so luong khong duoc de trong
        }
        return true;
    }


    //lang nghe, xu ly su kien khi click nut "Kiem tra"
    dojo.event.topic.subscribe("importFromPartnerAction/checkSerialRange", function(event, widget){
        if (checkValidSerial()) {
            setAction(widget,'divListSerialRange','importFromPartnerAction!checkSerialRange.do');
            $('btnImportFromPartner').style.visibility  = 'visible';
        } else {
            event.cancel = true;
        }
    });

    checkValidate = function() {
        var result = checkRequiredFields() && checkValidFields();
        if(result) {
            //startProgressBar();
        }
        return result;
    }

    /*
    submitForm = function(){
        if(checkRequiredFields() && checkValidFields()) {
            //
            startProgressBar();

            blurSXSubmit();
            initProgress();
            document.getElementById("importStockForm").submit();
            return true;
        } else {
            return false;
        }


    }*/

    //xu ly su kien khi thay doi loai mat hang
    changeStockType = function(stockTypeId) {
        //Neu la KIT
        if(stockTypeId=='8'){
            $('lblImportKit').style.display='';
            $('cboImportKit').style.display='';
            $('lblImportSim1').style.display='none';
            $('txtImportSim1').style.display='none';
            $('lblImportSim2').style.display='none';
            $('txtImportSim2').style.display='none';
            updateData("${contextPath}/importFromPartnerAction!getComboboxSource.do?target=stockModelId,profilePattern,stockModelSimId&stockTypeId=" + stockTypeId);
        }else{
            $('lblImportKit').style.display='none';
            $('cboImportKit').style.display='none';
            if(stockTypeId =='4' || stockTypeId=='5'){
                $('lblImportSim1').style.display='';
                $('txtImportSim1').style.display='';
                $('lblImportSim2').style.display='';
                $('txtImportSim2').style.display='';
            } else{
                $('lblImportSim1').style.display='none';
                $('txtImportSim1').style.display='none';
                $('lblImportSim2').style.display='none';
                $('txtImportSim2').style.display='none';
            }
            updateData("${contextPath}/importFromPartnerAction!getComboboxSource.do?target=stockModelId,profilePattern&stockTypeId=" + stockTypeId);
        }


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
            if($('distanceStep').value==0) {
                $('btnCheckSerial').style.visibility  = 'hidden';
                $('divListSerialRange').style.visibility  = 'hidden';
            } else {
                $('btnImportFromPartner').style.visibility  = 'hidden';
                $('btnCheckSerial').style.visibility  = 'visible';
                $('divListSerialRange').style.visibility  = 'visible';
            }

        } else if (value == 3) {
            $('btnCheckSerial').style.visibility  = 'hidden';
            $('btnImportFromPartner').style.visibility  = 'visible';
            $('divListSerialRange').style.visibility  = 'hidden';
            $('trImpByFile').style.display = 'none';
            $('trImpBySerial').style.display = 'none';
            $('trImpByQuantity').style.display = '';

        } else {
            $('btnCheckSerial').style.visibility  = 'hidden';
            $('btnImportFromPartner').style.visibility  = 'visible';
            $('divListSerialRange').style.visibility  = 'hidden';
            $('trImpByFile').style.display = '';
            $('trImpBySerial').style.display = 'none';
            $('trImpByQuantity').style.display = 'none';
        }
    }

    //ham kiem tra cac truong bat buoc
    checkRequiredFields = function() {

        if(trim($('actionCode').value) == "") {
            $('message').innerHTML= '<s:text name="ERR.STK.037"/>';
            //$('message').innerHTML = "!!!Lỗi. Nhập mã phiếu nhập";
            $('actionCode').select();
            $('actionCode').focus();
            return false;
        }

        if(trim($('partnerId').value) == "") {
            $('message').innerHTML= '<s:text name="ERR.STK.038"/>';
            //$('message').innerHTML = "!!!Lỗi. Chọn đối tác";
            $('partnerId').focus();
            return false;
        }

        var impDate = dojo.widget.byId("importStockForm.impDate");
        var strImpDate = impDate.domNode.childNodes[1].value;
        if(trim(strImpDate) == "") {
            $('message').innerHTML= '<s:text name="ERR.STK.039"/>';
            //$('message').innerHTML = "!!!Lỗi. Ngày nhập không được để trống";
            impDate.domNode.childNodes[1].select();
            impDate.domNode.childNodes[1].focus();
            return false;
        }

        if(trim($('stockTypeId').value) == "") {
            $('message').innerHTML= '<s:text name="ERR.STK.041"/>';
            //$('message').innerHTML = "!!!Lỗi. Chọn loại mặt hàng";
            $('stockTypeId').focus();
            return false;
        }

        if(trim($('stockModelId').value) == "") {
            $('message').innerHTML= '<s:text name="ERR.STK.040"/>';
            //$('message').innerHTML = "!!!Lỗi. Chọn mặt hàng";
            $('stockModelId').focus();
            return false;
        }
        if( trim($('stockTypeId').value) == "8" && trim(($('stockModelSimId').value)) =="" ) {
            $('message').innerHTML= '<s:text name="ERR.STK.042"/>';
            //$('message').innerHTML = "!!!Lỗi. Chọn loại sim";
            $('stockModelSimId').focus();
            return false;
        }
        if( trim($('stockTypeId').value) == "4" || trim($('stockTypeId').value) == "5") {
            if($('kind')==null || $('kind').value==''){
                $('message').innerHTML= '<s:text name="ERR.STK.043"/>';
                //$('message').innerHTML = "!!!Lỗi. Nhập KIND";
                $('kind').focus();
                return false;
            }
            $('kind').value= trim($('kind').value);
            if(!isInteger(trim($('kind').value))) {
                $('message').innerHTML= '<s:text name="ERR.STK.044"/>';
                //$('message').innerHTML = "!!!Lỗi. KIND phải là số không âm";
                $('kind').focus();
                return false;
            }
            if($('a3a8')==null || $('a3a8').value==''){
                $('message').innerHTML= '<s:text name="ERR.STK.045"/>';
                //$('message').innerHTML = "!!!Lỗi. Nhập A3A8";
                $('a3a8').focus();
                return false;
            }
            $('a3a8').value= trim($('a3a8').value);
            if(!isInteger(trim($('a3a8').value))) {
                $('message').innerHTML= '<s:text name="ERR.STK.046"/>';
                //$('message').innerHTML = "!!!Lỗi. A3A8 phải là số không âm";
                $('a3a8').focus();
                return false;
            }


        }
        var selectedIndex = 1;
        for(selectedIndex = 1; selectedIndex < 4; selectedIndex=selectedIndex+1) {
            var radioId = "importStockForm.impType" + selectedIndex;
            if($(radioId).checked == true) {
                break;
            }
        }
        if(selectedIndex == 1) {
            //nhap hang theo file -> ten file ko duoc de trong
            /*
            if($('importStockForm.impFile').value == "") {
                $('message').innerHTML = "!!!Lỗi. Chọn file dữ liệu";
                $('importStockForm.impFile').focus();
                return false;
            }*/
            if($('importStockForm.clientFileName').value == "") {
                $('message').innerHTML= '<s:text name="ERR.STK.047"/>';
                //$('message').innerHTML = "!!!Lỗi. Chọn file dữ liệu";
                $('importStockForm.clientFileName').focus();
                return false;
            }
        } else if (selectedIndex == 2) {
            //nhap hang theo dai -> dai serial khong duoc de trong
            if(trim($('importStockForm.fromSerial').value) == "") {
                $('message').innerHTML= '<s:text name="ERR.STK.028"/>';
                //$('message').innerHTML = "!!!Lỗi. Serial đầu dải không được để trống";
                $('importStockForm.fromSerial').select();
                $('importStockForm.fromSerial').focus();
                return false;
            }
            if(trim($('importStockForm.toSerial').value) == "") {
                $('message').innerHTML= '<s:text name="ERR.STK.029"/>';
                //$('message').innerHTML = "!!!Lỗi. Serial cuối dải không được để trống";
                $('importStockForm.toSerial').select();
                $('importStockForm.toSerial').focus();
                return false;
            }
            if(trim($('importStockForm.serialQuantity').value) == "") {
                $('message').innerHTML= '<s:text name="ERR.STK.030"/>';
                //$('message').innerHTML = "!!!Lỗi. Số lượng serial không được để trống";
                $('importStockForm.serialQuantity').select();
                $('importStockForm.serialQuantity').focus();
                return false;
            }

        } else if (selectedIndex == 3) {
            //nhap hang theo so luong -> so luong khong duoc de trong
            if(trim($('importStockForm.quantity').value) == "") {
                $('message').innerHTML= '<s:text name="ERR.STK.031"/>';
                //$('message').innerHTML = "!!!Lỗi. Số lượng không được để trống";
                $('importStockForm.quantity').select();
                $('importStockForm.quantity').focus();
                return false;
            }
        } else {
            //


        }

        return true;
    }

    //ham kiem tra du lieu cac truong co hop le hay ko
    checkValidFields = function() {
        /*
        var impDate = dojo.widget.byId("importStockForm.impDate");
        var strImpDate = impDate.domNode.childNodes[1].value;
        if(!checkFormatDate(trim(strImpDate) == "")) {
            $('message').innerHTML = "!!!Lỗi. Ngày nhập không đúng định dạng dd/MM/yyyy";
            impDate.domNode.childNodes[1].select();
            impDate.domNode.childNodes[1].focus();
            return false;
        }
         */

        var selectedIndex = 1;
        for(selectedIndex = 1; selectedIndex < 4; selectedIndex=selectedIndex+1) {
            var radioId = "importStockForm.impType" + selectedIndex;
            if($(radioId).checked == true) {
                break;
            }
        }

        if (selectedIndex == 2) {
            //nhap hang theo dai -> dai serial va so luong serial phai la so khogn am
            if(!isInteger(trim($('importStockForm.fromSerial').value))) {
                $('message').innerHTML= '<s:text name="ERR.STK.032"/>';
                //$('message').innerHTML = "!!!Lỗi. Serial đầu dải phải là số không âm";
                $('importStockForm.fromSerial').select();
                $('importStockForm.fromSerial').focus();
                return false;
            }
            if(!isInteger(trim($('importStockForm.toSerial').value))) {
                $('message').innerHTML= '<s:text name="ERR.STK.033"/>';
                //$('message').innerHTML = "!!!Lỗi. Serial cuối dải phải là số không âm";
                $('importStockForm.toSerial').select();
                $('importStockForm.toSerial').focus();
                return false;
            }
            if(!isInteger(trim($('importStockForm.serialQuantity').value))) {
                $('message').innerHTML= '<s:text name="ERR.STK.036"/>';
                //$('message').innerHTML = "!!!Lỗi. Số lượng serial phải là số không âm";
                $('importStockForm.serialQuantity').select();
                $('importStockForm.serialQuantity').focus();
                return false;
            }

            //so dau dai khong duoc lon hon so cuoi dai
            var fromSerial = trim($('importStockForm.fromSerial').value);
            var toSerial = trim($('importStockForm.toSerial').value);
            if(fromSerial * 1 > toSerial * 1) {
                $('message').innerHTML= '<s:text name="ERR.STK.034"/>';
                //$('message').innerHTML = "!!!Lỗi. Serial đầu dải không được lớn hơn serial cuối dải";
                $('importStockForm.fromSerial').select();
                $('importStockForm.fromSerial').focus();
                return false;
            }

            //trim cac truong can thiet
            $('importStockForm.fromSerial').value = trim($('importStockForm.fromSerial').value);
            $('importStockForm.toSerial').value = trim($('importStockForm.toSerial').value);
            $('importStockForm.serialQuantity').value = trim($('importStockForm.serialQuantity').value);

        } else if (selectedIndex == 3) {
            //nhap hang theo so luong -> so luong phai la so khong am
            if(!isInteger(trim($('importStockForm.quantity').value))) {
                $('message').innerHTML= '<s:text name="ERR.STK.035"/>';
                //$('message').innerHTML = "!!!Lỗi. Số lượng phải là số không âm";
                $('importStockForm.quantity').select();
                $('importStockForm.quantity').focus();
                return false;
            }

            //trim cac truong can thiet
            $('importStockForm.quantity').value = trim($('importStockForm.quantity').value);

        } else {
            //


        }

        return true;
    }

    //update viec an hien cac vung, tuy thuoc vao kieu nhap hang
    var i = 1;
    for(i = 1; i < 4; i=i+1) {
        var radioId = "importStockForm.impType" + i;
        if($(radioId).checked == true) {
            radioClick(i);
            break;
        }

    }

    //Code de tinh toan cong tru nhan chia cho so lon

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


