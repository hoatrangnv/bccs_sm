<%-- 
    Document   : assignChannelTypeForSerial
    Created on : Sep 7, 2010, 9:49:31 AM
    Author     : Doan Thanh 8
    Desc       : gan kenh cho dai serial hang hoa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib prefix="imDef" uri="imDef" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page import="com.viettel.im.common.util.ResourceBundleUtils" %>

<%
    request.setAttribute("contextPath", request.getContextPath());
%>

<s:form action="assignChannelTypeForSerialAction" theme="simple" method="POST" id="assignChannelTypeForSerialForm">
<s:token/>

    <tags:imPanel title="title.assignChannelTypeForSerial.page">
        <!-- hien thi message -->
        <div>
            <tags:displayResult id="message" property="message" propertyValue="messageParam" type="key"/>
        </div>

        <!-- phan hien thi link download log loi -->
        <div align="center" class="txtError" id="divErrorFilePath">
            <s:if test="#request.errorFilePath != null">
                ${fn:escapeXml(imDef:imGetText('MSG.create.file.error.isdn'))}
                <a class="cursorHand" onclick="downloadErrorFile('${contextPath}/${fn:escapeXml(errorFilePath)}')">
                    ${fn:escapeXml(imDef:imGetText('MSG.here'))}
                </a>
            </s:if>
        </div>

        <!-- thong tin dai serial -->
        <div class="divHasBorder">
            <table class="inputTbl7Col">
                <tr>
                    <td style="width:10%; "></td>
                    <td style="width:25%; "></td>
                    <td style="width:10%; "></td>
                    <td colspan="4"><a onclick="downloadPatternFile()"><tags:label key="MSG.downloadFileHere" /></a></td>
                    <%--<td style="width:10%; "></td>
                    <td style="width:15%; "></td>
                    <td></td>--%>
                </tr>
                <tr>
                    <td><tags:label key="MSG.GOD.104" required="true"/></td>
                    <td class="oddColumn">
                        <tags:imSearch codeVariable="assignChannelTypeForSerialForm.stockModelCode" nameVariable="assignChannelTypeForSerialForm.stockModelName"
                                       codeLabel="MSG.GOD.007" nameLabel="MSG.GOD.008"
                                       searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                       searchMethodName="getListStockModel"
                                       getNameMethod="getStockModelName"/>

                        <s:hidden name="assignChannelTypeForSerialForm.stockModelId"/>
                    </td>
                    <td><tags:label key="MSG.import.type" required="true"/></td>
                    <td colspan="4">
                        <tags:imRadio name="assignChannelTypeForSerialForm.impType"
                                      id="assignChannelTypeForSerialForm.impType"
                                      list="1:MSG.STK.032,2:MSG.STK.031"
                                      onchange="radioClick(this.value)"/>
                    </td>
<!--                    2:MSG.STK.031, -->
                </tr>
                <tr id="divImpByFile">
                    <td><tags:label key="MSG.store.code" required="true"/></td>
                    <td class="oddColumn">
                        <tags:imSearch codeVariable="assignChannelTypeForSerialForm.shopCodeNew" nameVariable="assignChannelTypeForSerialForm.shopNameNew"
                                       codeLabel="MSG.store.code" nameLabel="MSG.storeName"
                                       searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                       searchMethodName="getListShop"
                                       getNameMethod="getShopName"/>
                    </td>
                    <td><tags:label key="MSG.generates.file_data" required="true"/></td>
                    <td colspan="2">
                        <tags:imFileUpload name="assignChannelTypeForSerialForm.clientFileName"
                                           id="assignChannelTypeForSerialForm.clientFileName"
                                           serverFileName="assignChannelTypeForSerialForm.serverFileName"
                                           cssStyle="width:100%;" extension="xls"/>
                    </td>                    
                    <td colspan="2">
                        <div style="text-align: right;">
                            <tags:submit formId="assignChannelTypeForSerialForm"
                                         showLoadingText="true"
                                         cssStyle="width:80px;"
                                         id="btnFilePreview"
                                         targets="bodyContent"
                                         value="File Preview"
                                         validateFunction="checkValidateBeforePreview()"
                                         preAction="assignChannelTypeForSerialAction!filePreview.do"/>
                            <tags:submit formId="assignChannelTypeForSerialForm"
                                         showLoadingText="true"
                                         cssStyle="width:80px;"
                                         id="btnUpdateByFile"
                                         targets="bodyContent"
                                         value="MSG.update"
                                         validateFunction="checkValidateBeforeAssign()"
                                         preAction="assignChannelTypeForSerialAction!assignChannelTypeForSerial.do"
                                         showProgressDiv="true"
                                         showProgressClass="com.viettel.im.database.DAO.AssignChannelTypeForSerialDAO"
                                         showProgressMethod="updateProgressDiv"/>
                        </div>
                    </td>
                </tr>
                <tr id="divImpByRange">
                    <td><tags:label key="MSG.range.serial" required="true"/></td>
                    <td class="oddColumn">
                        <table style="width:100%; border-spacing:0px; ">
                            <tr>
                                <td style="width:50%;">
                                    <s:textfield name="assignChannelTypeForSerialForm.fromSerial" id="assignChannelTypeForSerialForm.fromSerial" maxlength="25" cssClass="txtInputFull"/>
                                </td>
                                <td>-</td>
                                <td style="width:50%;">
                                    <s:textfield name="assignChannelTypeForSerialForm.toSerial" id="assignChannelTypeForSerialForm.toSerial" maxlength="25" cssClass="txtInputFull"/>
                                </td>
                            </tr>
                        </table>
                    </td>
                    <td><tags:label key="MSG.store.code" required="true"/></td>
                    <td class="oddColumn" >
                        <tags:imSearch codeVariable="assignChannelTypeForSerialForm.shopCode" nameVariable="assignChannelTypeForSerialForm.shopName"
                                       codeLabel="MSG.store.code" nameLabel="MSG.storeName"
                                       searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                       searchMethodName="getListShop"
                                       getNameMethod="getShopName"/>
                    </td>
                    <td><tags:label key="MSG.STK.047"/></td>
                    <td class="oddColumn">
                        <tags:imSelect name="assignChannelTypeForSerialForm.channelTypeId"
                                       id="assignChannelTypeForSerialForm.channelTypeId"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.GOD.266"
                                       list="listChannelType"
                                       listKey="channelTypeId" listValue="name"/>
                    </td>
                    <td style="text-align: right;">
                        <tags:submit formId="assignChannelTypeForSerialForm"
                                     showLoadingText="true"
                                     cssStyle="width:80px;"
                                     id="btnSearch"
                                     targets="bodyContent"
                                     value="MSG.createList"
                                     validateFunction="checkValidateBeforeSearch()"
                                     preAction="assignChannelTypeForSerialAction!searchSerialRange.do"/>
                    </td>
                </tr>
            </table>
        </div>

        <div>
            <jsp:include page="assignChannelTypeForSerialList.jsp"/>
        </div>

        <div class="divHasBorder" style="margin-top: 3px;" id="divImpByRangeUpdate">
            <table>
                <tr>
                    <td style="width:10%; "></td>
                    <td style="width:25%; "></td>
                    <td style="width:10%; "></td>
                    <td style="width:25%; "></td>
                    <td style="width:10%; "><tags:label key="MSG.STK.048" required="true"/></td>
                    <td style="width:15%; ">
                        <tags:imSelect name="assignChannelTypeForSerialForm.newChannelTypeId"
                                       id="assignChannelTypeForSerialForm.newChannelTypeId"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.GOD.266"
                                       list="listChannelType"
                                       listKey="channelTypeId" listValue="name"/>
                    </td>
                    <td>
                        <tags:submit formId="assignChannelTypeForSerialForm"
                                     showLoadingText="true"
                                     cssStyle="width:80px;"
                                     id="btnUpdate"
                                     targets="bodyContent"
                                     value="MSG.update"
                                     validateFunction="checkValidateBeforeAssign()"
                                     preAction="assignChannelTypeForSerialAction!assignChannelTypeForSerial.do"
                                     showProgressDiv="true"
                                     showProgressClass="com.viettel.im.database.DAO.AssignChannelTypeForSerialDAO"
                                     showProgressMethod="updateProgressDiv"/>
                    </td>
                </tr>
            </table>
        </div>

    </tags:imPanel>
</s:form>


<script language="javascript">
    
    //$("assignChannelTypeForSerialForm.impType1").checked = true;

    //focus vao truong dau tien
    //$('assignStockModelForIsdnForm.fromIsdn').select();
    //$('assignStockModelForIsdnForm.fromIsdn').focus();

    downloadErrorFile = function(errorFileUrl) {
        window.open(errorFileUrl, '', 'toolbar=yes,scrollbars=yes');
    }

    checkValidateBeforeSearch = function () {
        var i = 1;
        for(i = 1; i < 3; i=i+1) {
            var radioId = "assignChannelTypeForSerialForm.impType" + i;
            if($(radioId).checked == true) {
                break;
            }

        }

        var stockModelCode = trim($('assignChannelTypeForSerialForm.stockModelCode').value);
        if(stockModelCode == "") {
            $('message').innerHTML = "";
            $('divErrorFilePath').innerHTML = "";
            //            $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getError('assignChannelTypeForSerial.error.requiredFieldsEmpty')"/>'; //$('message').innerHTML = "!!!Lỗi. Trường từ số không được để trống";
            $('message').innerHTML= '<s:text name = "assignChannelTypeForSerial.error.requiredFieldsEmpty"/>';
            $('assignChannelTypeForSerialForm.stockModelCode').select();
            $('assignChannelTypeForSerialForm.stockModelCode').focus();
            return false;
        }


        if(!isValidInput(trim(stockModelCode), false, false)) {
            $('message').innerHTML = "";
            $('divErrorFilePath').innerHTML = "";
            //            $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getError('assignChannelTypeForSerial.error.invalidStockModelCode')"/>';//$('message').innerHTML = "!!!Lỗi. Mã mặt hàng chỉ được chứa các ký tự A-Z, a-z, 0-9, _";
            $('message').innerHTML= '<s:text name = "assignChannelTypeForSerial.error.invalidStockModelCode"/>';
            $('assignChannelTypeForSerialForm.stockModelCode').select();
            $('assignChannelTypeForSerialForm.stockModelCode').focus();
            return false;
        }


        if(i == 2) {
            //neu lay du lieu tu file
            if($('assignChannelTypeForSerialForm.clientFileName').value == "") {
                $('message').innerHTML = "";
                $('divErrorFilePath').innerHTML = "";
                //                $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.ISN.027')"/>'; //$('message').innerHTML = "!!!Lỗi. Chọn file dữ liệu";
                $('message').innerHTML= '<s:text name = "ERR.ISN.027"/>';
                $('assignChannelTypeForSerialForm.clientFileName').select();
                $('assignChannelTypeForSerialForm.clientFileName').focus();
                return false;
            }

        } else {
        
            
            
            //neu nhap du lieu theo dai
            //1> kiem tra cac truong can thiet
            if(trim($('assignChannelTypeForSerialForm.fromSerial').value) == "") {
                $('message').innerHTML = "";
                $('divErrorFilePath').innerHTML = "";
                //                $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getError('assignChannelTypeForSerial.error.requiredFieldsEmpty')"/>'; //$('message').innerHTML = "!!!Lỗi. Trường từ số không được để trống";
                $('message').innerHTML= '<s:text name="assignChannelTypeForSerial.error.requiredFieldsEmpty"/>'; //$('message').innerHTML = "!!!Lỗi. Trường từ số không được để trống";
                $('assignChannelTypeForSerialForm.fromSerial').select();
                $('assignChannelTypeForSerialForm.fromSerial').focus();
                return false;
            }

            if(trim($('assignChannelTypeForSerialForm.toSerial').value) == "") {
                $('message').innerHTML = "";
                $('divErrorFilePath').innerHTML = "";
                //$('message').innerHTML= '<s:property escapeJavaScript="true"  value="getError('assignChannelTypeForSerial.error.requiredFieldsEmpty')"/>'; //$('message').innerHTML = "!!!Lỗi. Trường từ số không được để trống";
                $('message').innerHTML= '<s:text name="assignChannelTypeForSerial.error.requiredFieldsEmpty"/>'; //$('message').innerHTML = "!!!Lỗi. Trường từ số không được để trống";
                $('assignChannelTypeForSerialForm.toSerial').select();
                $('assignChannelTypeForSerialForm.toSerial').focus();
                return false;
            }

            //2> kiem tra tinh hop le cua cac truong
            var fromSerial = $('assignChannelTypeForSerialForm.fromSerial').value;
            var toSerial = $('assignChannelTypeForSerialForm.toSerial').value;
            if(!isInteger(trim(fromSerial))) {
                $('message').innerHTML = "";
                $('divErrorFilePath').innerHTML = "";
                //                $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getError('assignChannelTypeForSerial.error.serialIsNotPositiveInteger')"/>'; //$('message').innerHTML = "!!!Lỗi. Trường từ số phải là số không âm";
                $('message').innerHTML= '<s:text name="assignChannelTypeForSerial.error.serialIsNotPositiveInteger"/>'; //$('message').innerHTML = "!!!Lỗi. Trường từ số phải là số không âm";
                $('assignChannelTypeForSerialForm.fromSerial').select();
                $('assignChannelTypeForSerialForm.fromSerial').focus();
                return false;
            }
            if(!isInteger(trim(toSerial))) {
                $('message').innerHTML = "";
                $('divErrorFilePath').innerHTML = "";
                //$('message').innerHTML= '<s:property escapeJavaScript="true"  value="getError('assignChannelTypeForSerial.error.serialIsNotPositiveInteger')"/>'; //$('message').innerHTML = "!!!Lỗi. Trường từ số phải là số không âm";
                $('message').innerHTML= '<s:text name="assignChannelTypeForSerial.error.serialIsNotPositiveInteger"/>'; //$('message').innerHTML = "!!!Lỗi. Trường từ số phải là số không âm";
                $('assignChannelTypeForSerialForm.toSerial').select();
                $('assignChannelTypeForSerialForm.toSerial').focus();
                return false;
            }
            if(fromSerial * 1 > toSerial * 1) {
                $('message').innerHTML = "";
                $('divErrorFilePath').innerHTML = "";
                //                $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getError('assignChannelTypeForSerial.error.invalidIsdn')"/>';//$('message').innerHTML = "!!!Lỗi. Trường từ số không được lớn hơn trường đến số";
                $('message').innerHTML= '<s:text name="assignChannelTypeForSerial.error.invalidIsdn"/>';//$('message').innerHTML = "!!!Lỗi. Trường từ số không được lớn hơn trường đến số";
                $('assignChannelTypeForSerialForm.fromSerial').select();
                $('assignChannelTypeForSerialForm.fromSerial').focus();
                return false;
            }

            //Modified by : TrongLV 
            //Modify date : 2011-09-20        
            var shopCode = trim($("assignChannelTypeForSerialForm.shopCode").value);
            if(shopCode == "") {
                $('message').innerHTML = "";
                $('divErrorFilePath').innerHTML = "";
                $('message').innerHTML= '<s:text name="assignChannelTypeForSerial.error.requiredFieldsEmpty"/>'; 
                $('assignChannelTypeForSerialForm.shopCode').select();
                $('assignChannelTypeForSerialForm.shopCode').focus();
                return false;
            }
            
            if(shopCode != null && shopCode != '' && !isValidInput(trim(shopCode), false, false)) {
                $('message').innerHTML = "";
                $('divErrorFilePath').innerHTML = "";
                //                $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getError('assignChannelTypeForSerial.error.invalidShopCode')"/>';//$('message').innerHTML = "!!!Lỗi. Mã kho lấy số chỉ được chứa các ký tự A-Z, a-z, 0-9, _";
                $('message').innerHTML= '<s:text name="assignChannelTypeForSerial.error.invalidShopCode"/>';//$('message').innerHTML = "!!!Lỗi. Mã kho lấy số chỉ được chứa các ký tự A-Z, a-z, 0-9, _";
                $('assignChannelTypeForSerialForm.shopCode').select();
                $('assignChannelTypeForSerialForm.shopCode').focus();
                return false;
            }

            //trim cac truong can thiet
            $('assignChannelTypeForSerialForm.fromSerial').value = trim($('assignChannelTypeForSerialForm.fromSerial').value);
            $('assignChannelTypeForSerialForm.toSerial').value = trim($('assignChannelTypeForSerialForm.toSerial').value);
            $('assignChannelTypeForSerialForm.shopCode').value = trim($('assignChannelTypeForSerialForm.shopCode').value);
            $('assignChannelTypeForSerialForm.stockModelCode').value = trim($('assignChannelTypeForSerialForm.stockModelCode').value);
        }

        return true;
    }

    checkValidateBeforeAssign = function () {
        var i = 1;
        for(i = 1; i < 3; i=i+1) {
            var radioId = "assignChannelTypeForSerialForm.impType" + i;
            if($(radioId).checked == true) {
                break;
            }

        }
        if(i == 2) {
            //neu lay du lieu tu file
            var stockModelCode = trim($('assignChannelTypeForSerialForm.stockModelCode').value);
            if(stockModelCode == "") {
                $('message').innerHTML = "";
                $('divErrorFilePath').innerHTML = "";
                //                $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getError('assignChannelTypeForSerial.error.requiredFieldsEmpty')"/>'; //$('message').innerHTML = "!!!Lỗi. Trường từ số không được để trống";
                $('message').innerHTML= '<s:text name="assignChannelTypeForSerial.error.requiredFieldsEmpty"/>'; //$('message').innerHTML = "!!!Lỗi. Trường từ số không được để trống";
                $('assignChannelTypeForSerialForm.stockModelCode').select();
                $('assignChannelTypeForSerialForm.stockModelCode').focus();
                return false;
            }


            if(!isValidInput(trim(stockModelCode), false, false)) {
                $('message').innerHTML = "";
                $('divErrorFilePath').innerHTML = "";
                //                $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getError('assignChannelTypeForSerial.error.invalidStockModelCode')"/>';//$('message').innerHTML = "!!!Lỗi. Mã mặt hàng chỉ được chứa các ký tự A-Z, a-z, 0-9, _";
                $('message').innerHTML= '<s:text name="assignChannelTypeForSerial.error.invalidStockModelCode"/>';//$('message').innerHTML = "!!!Lỗi. Mã mặt hàng chỉ được chứa các ký tự A-Z, a-z, 0-9, _";
                $('assignChannelTypeForSerialForm.stockModelCode').select();
                $('assignChannelTypeForSerialForm.stockModelCode').focus();
                return false;
            }

            if($('assignChannelTypeForSerialForm.clientFileName').value == "") {
                $('message').innerHTML = "";
                $('divErrorFilePath').innerHTML = "";
                //                $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.ISN.027')"/>'; //$('message').innerHTML = "!!!Lỗi. Chọn file dữ liệu";
                $('message').innerHTML= '<s:text name="ERR.ISN.027"/>'; //$('message').innerHTML = "!!!Lỗi. Chọn file dữ liệu";
                $('assignChannelTypeForSerialForm.clientFileName').select();
                $('assignChannelTypeForSerialForm.clientFileName').focus();
                return false;
            }

            if($('assignChannelTypeForSerialForm.shopCodeNew').value == "") {
                $('message').innerHTML = "";
                $('divErrorFilePath').innerHTML = "";
                $('message').innerHTML= '<s:text name="assignChannelTypeForSerial.error.requiredFieldsEmpty"/>'; //$('message').innerHTML = "!!!Lỗi. Chọn file dữ liệu";
                $('assignChannelTypeForSerialForm.shopCodeNew').select();
                $('assignChannelTypeForSerialForm.shopCodeNew').focus();
                return false;
            }
        } else {
        
            //Modified by : TrongLV 
            //Modify date : 2011-09-20        
            var shopCode = trim($("assignChannelTypeForSerialForm.shopCode").value);
            if(shopCode == "") {
                $('message').innerHTML = "";
                $('divErrorFilePath').innerHTML = "";
                $('message').innerHTML= '<s:text name="assignChannelTypeForSerial.error.requiredFieldsEmpty"/>'; 
                $('assignChannelTypeForSerialForm.shopCode').select();
                $('assignChannelTypeForSerialForm.shopCode').focus();
                return false;
            }
            
        
            var newChannelTypeId = trim($('assignChannelTypeForSerialForm.newChannelTypeId').value);
            if(newChannelTypeId == "") {
                $('message').innerHTML = "";
                $('divErrorFilePath').innerHTML = "";
                //                $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getError('MSG.GOD.023')"/>'; //$('message').innerHTML = "!!!Lỗi. Chọn file dữ liệu";
                $('message').innerHTML= '<s:text name="MSG.GOD.023"/>'; //$('message').innerHTML = "!!!Lỗi. Chọn file dữ liệu";
                $('assignChannelTypeForSerialForm.newChannelTypeId').select();
                $('assignChannelTypeForSerialForm.newChannelTypeId').focus();
                return false;
            }
        }

        return true;
    }

    checkValidateBeforePreview = function () {
        var i = 1;
        for(i = 1; i < 3; i=i+1) {
            var radioId = "assignChannelTypeForSerialForm.impType" + i;
            if($(radioId).checked == true) {
                break;
            }

        }
        if(i == 2) {
            //neu lay du lieu tu file
            if($('assignChannelTypeForSerialForm.clientFileName').value == "") {
                $('message').innerHTML = "";
                $('divErrorFilePath').innerHTML = "";
                //$('message').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.ISN.027')"/>'; //$('message').innerHTML = "!!!Lỗi. Chọn file dữ liệu";
                $('message').innerHTML= '<s:text name="ERR.ISN.027"/>'; //$('message').innerHTML = "!!!Lỗi. Chọn file dữ liệu";
                $('assignChannelTypeForSerialForm.clientFileName').select();
                $('assignChannelTypeForSerialForm.clientFileName').focus();
                return false;
            }
            
            var stockModelCode = trim($('assignChannelTypeForSerialForm.stockModelCode').value);
            if(stockModelCode == "") {
                $('message').innerHTML = "";
                $('divErrorFilePath').innerHTML = "";
                //                $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getError('assignChannelTypeForSerial.error.requiredFieldsEmpty')"/>'; //$('message').innerHTML = "!!!Lỗi. Trường từ số không được để trống";
                $('message').innerHTML= '<s:text name="assignChannelTypeForSerial.error.requiredFieldsEmpty"/>'; //$('message').innerHTML = "!!!Lỗi. Trường từ số không được để trống";
                $('assignChannelTypeForSerialForm.stockModelCode').select();
                $('assignChannelTypeForSerialForm.stockModelCode').focus();
                return false;
            }


            if(!isValidInput(trim(stockModelCode), false, false)) {
                $('message').innerHTML = "";
                $('divErrorFilePath').innerHTML = "";
                //                $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getError('assignChannelTypeForSerial.error.invalidStockModelCode')"/>';//$('message').innerHTML = "!!!Lỗi. Mã mặt hàng chỉ được chứa các ký tự A-Z, a-z, 0-9, _";
                $('message').innerHTML= '<s:text name="assignChannelTypeForSerial.error.invalidStockModelCode"/>';//$('message').innerHTML = "!!!Lỗi. Mã mặt hàng chỉ được chứa các ký tự A-Z, a-z, 0-9, _";
                $('assignChannelTypeForSerialForm.stockModelCode').select();
                $('assignChannelTypeForSerialForm.stockModelCode').focus();
                return false;
            }

            if($('assignChannelTypeForSerialForm.shopCodeNew').value == "") {
                $('message').innerHTML = "";
                $('divErrorFilePath').innerHTML = "";
                $('message').innerHTML= '<s:text name="assignChannelTypeForSerial.error.requiredFieldsEmpty"/>'; //$('message').innerHTML = "!!!Lỗi. Chọn file dữ liệu";
                $('assignChannelTypeForSerialForm.shopCodeNew').select();
                $('assignChannelTypeForSerialForm.shopCodeNew').focus();
                return false;
            }
        }

        return true;
    }

    //xu ly su kien khi thay doi kieu nhap du lieu
    radioClick = function(value){

        if(value == 2) {
            //disable cac component phuc vu viec nhap so theo dai
            $('assignChannelTypeForSerialForm.shopCode').disabled = true;
            $('assignChannelTypeForSerialForm.fromSerial').disabled = true;
            $('assignChannelTypeForSerialForm.toSerial').disabled = true;
            $('assignChannelTypeForSerialForm.channelTypeId').disabled = true;
            $('assignChannelTypeForSerialForm.newChannelTypeId').disabled = true;
            $('btnSearch').disabled  = true;
            $('btnUpdate').disabled  = true;
            $('btnUpdateByFile').disabled  = false;
            $('btnFilePreview').disabled  = false;

            //enable component phuc vu viec nhap so theo file
            disableImFileUpload('assignChannelTypeForSerialForm.clientFileName', false);

            $('divImpByFile').style.display = '';
            $('divImpByRange').style.display = 'none';
            $('divImpByRangeUpdate').style.display = 'none';

        } else {
            //disable cac component phuc vu viec nhap so theo dai
            $('assignChannelTypeForSerialForm.shopCode').disabled = false;
            $('assignChannelTypeForSerialForm.fromSerial').disabled = false;
            $('assignChannelTypeForSerialForm.toSerial').disabled = false;
            $('assignChannelTypeForSerialForm.channelTypeId').disabled = false;
            $('assignChannelTypeForSerialForm.newChannelTypeId').disabled = false;
            $('btnSearch').disabled  = false;
            $('btnUpdate').disabled  = false;
            $('btnUpdateByFile').disabled  = true;
            $('btnFilePreview').disabled  = true;

            //enable component phuc vu viec nhap so theo file
            disableImFileUpload('assignChannelTypeForSerialForm.clientFileName', true);

            $('divImpByFile').style.display = 'none';
            $('divImpByRange').style.display = '';
            $('divImpByRangeUpdate').style.display = 'block';
        }
    }

    downloadPatternFile = function() {
        window.open('${contextPath}/share/pattern/assignChannelTypeForSerialPattern.xls','','toolbar=yes,scrollbars=yes');
    }

    //update viec an hien cac vung, tuy thuoc vao kieu nhap du lieu
    var i = 1;
    for(i = 1; i < 3; i=i+1) {
        var radioId = "assignChannelTypeForSerialForm.impType" + i;
        if($(radioId).checked == true) {
            radioClick(i);
            break;
        }

    }

</script>
