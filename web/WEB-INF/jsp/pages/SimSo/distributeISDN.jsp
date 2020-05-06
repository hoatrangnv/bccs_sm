<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
    Document   : distributeISDN
    Created on : Jan 15, 2008, 2:54:01 PM
    Author     : TuanNV
    Modified   : tamdt1
Phan phoi so
--%>

<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.guhesan.querycrypt.QueryCrypt" %>

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
%>

<s:form action="DistributeISDNAction!addIsdnRange"  theme="simple" method="post" id="distributeIsdnForm">
<s:token/>

    <tags:imPanel title="MSG.distribute.isdn">

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

        <!-- thong tin dai so -->
        <div class="divHasBorder">
            <table class="inputTbl6Col">
                <tr>
                    <td><tags:label key="MSG.service.type" required="true"/></td>
                    <td class="oddColumn">
                        <tags:imSelect name="distributeIsdnForm.serviceType" id="serviceType"
                                  list="2:MSG.homephoneNumber,3:PSTN"
                                  cssClass="txtInputFull"
                                  headerKey="1" headerValue="Mobile"/>
                    </td>
                    <td><tags:label key="MSG.import.type" required="true"/></td>
                    <td colspan="3">

                         <s:radio name="distributeIsdnForm.impType"
                                 id="distributeIsdnForm.impType"
                                 list="#{'1':'Input by number range',
                                         '2':'Input from file (<a onclick=downloadPatternFile()> download sample data file here</a>)'}"
                                 onchange="radioClick(this.value)"/>

                        <%--tags:imRadio name="distributeIsdnForm.impType"
                                 id="distributeIsdnForm.impType"
                                 list="1:MSG.importByListIsdn,
                                         2:MSG.ISN.001"
                                 onchange="radioClick(this.value)"/--%>
                    </td>
                </tr>
                <tr>
                    <td style="width:10%; "><tags:label key="MSG.isdn.area" required="true"/></td>
                    <td class="oddColumn" style="width:23%; ">
                        <table style="width:100%; border-spacing:0px; ">
                            <tr>
                                <td style="width:50%;">
                                    <s:textfield name="distributeIsdnForm.startIsdn" id="startIsdn" cssClass="txtInputFull" maxlength="15"/>
                                </td>
                                <td>-</td>
                                <td style="width:50%;">
                                    <s:textfield name="distributeIsdnForm.endIsdn" id="endIsdn" cssClass="txtInputFull" maxlength="15"/>
                                </td>
                            </tr>
                        </table>
                    </td>
                    <td style="width:10%; "><tags:label key="MSG.fromIsdnStore" required="true"/></td>
                    <td class="oddColumn" style="width:23%; ">
                        <tags:imSearch codeVariable="distributeIsdnForm.fromShopCode" nameVariable="distributeIsdnForm.fromShopName"
                                       codeLabel="MSG.store.code" nameLabel="MSG.storeName"
                                       searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                       searchMethodName="getListShop"
                                       getNameMethod="getShopName"/>
                    </td>
                    <td style="width:10%; "><tags:label key="MSG.toIsdnStore" required="true"/></td>
                    <td>
                        <tags:imSearch codeVariable="distributeIsdnForm.shopCode" nameVariable="distributeIsdnForm.shopName"
                                       codeLabel="MSG.store.code" nameLabel="MSG.storeName"
                                       searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                       searchMethodName="getListShop"
                                       getNameMethod="getShopName"/>
                    </td>
                </tr>
                <tr>
                    <td> <tags:label key="MSG.isdnStatus"/></td>
                    <td class="oddColumn">
                        <tags:imSelect name="distributeIsdnForm.status" id="status" cssClass="txtInputFull"
                                  list="1:MSG.newIsdn,3:MSG.stopIsdn"
                                  headerKey="" headerValue="MSG.ISN.002"/>
                    </td>
                    <td> <tags:label key="MSG.generates.goods"/></td>
                    <td class="oddColumn">
                        <tags:imSearch codeVariable="distributeIsdnForm.stockModelCode" nameVariable="distributeIsdnForm.stockModelName"
                                       codeLabel="MSG.GOD.007" nameLabel="MSG.GOD.008"
                                       searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                       searchMethodName="getListStockModel"
                                       getNameMethod="getStockModelName"
                                       otherParam="serviceType"/>
                    </td>
                    <td> <tags:label key="MSG.isdn_type"/> </td>
                    <td>
                        <tags:imSelect name="distributeIsdnForm.isdnType" id="isdnType" cssClass="txtInputFull"
                                  list="3:MSG.ISN.003,2:MSG.ISN.004,1:MSG.ISN.005"
                                  headerKey="" headerValue="MSG.chooseIsdnType"/>
                    </td>
                </tr>
                <tr>
                    <td> <tags:label key="MSG.newIsdnType"/></td>
                    <td class="oddColumn">
                        <tags:imSelect name="distributeIsdnForm.newIsdnType" id="newIsdnType"
                                  cssClass="txtInputFull"
                                  list="3:MSG.ISN.003,2:MSG.ISN.004,1:MSG.ISN.005"
                                  headerKey="" headerValue="MSG.ISN.006"/>
                    </td>
                    <td><tags:label key="MSG.generates.file_data" required="true"/></td>
                    <td colspan="2">
                        <!-- Edit by hieptd -->
                        <tags:imFileUpload name="distributeIsdnForm.clientFileName"
                                           id="distributeIsdnForm.clientFileName"
                                           serverFileName="distributeIsdnForm.serverFileName"
                                           cssStyle="width:100%;" extension="xls"/>
                    </td>
                    <td>
                        <tags:submit formId="distributeIsdnForm"
                                     showLoadingText="true"
                                     cssStyle="width:80px;"
                                     targets="bodyContent"
                                     value="MSG.createList"
                                     id="btnSearchIsdnRange"
                                     validateFunction="checkValidateSearchIsdnRange()"
                                     preAction="DistributeISDNAction!searchIsdnRange.do"
                                     showProgressDiv="true"
                                     showProgressClass="com.viettel.im.database.DAO.DistributeISDNDAO"
                                     showProgressMethod="updateProgressDiv"/>
                        <tags:submit formId="distributeIsdnForm"
                                     showLoadingText="true"
                                     cssStyle="width:80px;"
                                     targets="bodyContent"
                                     value="MSG.distribute"
                                     validateFunction="checkValidate()"
                                     preAction="DistributeISDNAction!distributeNumber.do"
                                     showProgressDiv="true"
                                     showProgressClass="com.viettel.im.database.DAO.DistributeISDNDAO"
                                     showProgressMethod="updateProgressDiv"/>
                    </td>
                </tr>
            </table>
        </div>

        <div>
            <jsp:include page="listDistributeIsdn.jsp"/>
        </div>

    </tags:imPanel>

</s:form>

<script type="text/javascript">

    //focus vao truong dau tien
    $('startIsdn').select();
    $('startIsdn').focus();

    downloadPatternFile = function() {
        window.open('${contextPath}/share/pattern/distributeIsdnPattern.xls','','toolbar=yes,scrollbars=yes');
    }

    downloadErrorFile = function(errorFileUrl) {
        window.open(errorFileUrl, '', 'toolbar=yes,scrollbars=yes');
    }

    //
    checkValidateSearchIsdnRange = function(){
        var i = 1;
        for(i = 1; i < 3; i=i+1) {
            var radioId = "distributeIsdnForm.impType" + i;
            if($(radioId).checked == true) {
                break;
            }

        }
        if(i == 2) {
            //neu lay du lieu tu file
            if(trim($('serviceType').value) == "") {
                $('message').innerHTML = "";
                $('divErrorFilePath').innerHTML = "";
                $('message').innerHTML= '<s:text name="ERR.ISN.001"/>';
                //$('message').innerHTML = "!!!Lỗi. Chọn loại dịch vụ";
                $('serviceType').focus();
                return false;
            }
            if($('distributeIsdnForm.clientFileName').value == "") {
                $('message').innerHTML = "";
                $('divErrorFilePath').innerHTML = "";
                $('message').innerHTML= '<s:text name="ERR.ISN.027"/>';
                //$('message').innerHTML = "!!!Lỗi. Chọn file dữ liệu";
                $('distributeIsdnForm.clientFileName').select();
                $('distributeIsdnForm.clientFileName').focus();
                return false;
            }

        } else {
            //neu nhap du lieu theo dai so
            if(trim($('serviceType').value) == "") {
                $('message').innerHTML = "";
                $('divErrorFilePath').innerHTML = "";
                $('message').innerHTML= '<s:text name="ERR.ISN.001"/>';
                //$('message').innerHTML = "!!!Lỗi. Chọn loại dịch vụ";
                $('serviceType').focus();
                return false;
            }            
            if(trim($('distributeIsdnForm.fromShopCode').value) == "") {
                $('message').innerHTML = "";
                $('divErrorFilePath').innerHTML = "";
                $('message').innerHTML= '<s:text name="ERR.ISN.039"/>';
                //$('message').innerHTML = "!!!Lỗi. Kho lấy số không được để trống";
                $('distributeIsdnForm.shopCode').select();
                $('distributeIsdnForm.fromShopCode').focus();
                return false;
            }
            if(trim($('startIsdn').value) == "") {
                $('message').innerHTML = "";
                $('divErrorFilePath').innerHTML = "";
                $('message').innerHTML= '<s:text name="ERR.ISN.040"/>';
                //$('message').innerHTML = "!!!Lỗi. Số đầu dải không được để trống";
                $('startIsdn').select();
                $('startIsdn').focus();
                return false;
            }
            if(trim($('endIsdn').value) == "") {
                $('message').innerHTML = "";
                $('divErrorFilePath').innerHTML = "";
                $('message').innerHTML= '<s:text name="ERR.ISN.052"/>';
                //$('message').innerHTML = "!!!Lỗi. Số cuối dải không được để trống";
                $('endIsdn').select();
                $('endIsdn').focus();
                return false;
            }

            var startIsdn = trim($('startIsdn').value);
            var endIsdn = trim($('endIsdn').value);

            if(!isInteger(trim(startIsdn))) {
                $('message').innerHTML = "";
                $('divErrorFilePath').innerHTML = "";
                $('message').innerHTML= '<s:text name="ERR.ISN.006"/>';
                //$('message').innerHTML = "!!!Lỗi. Số đầu dải phải là số không âm";
                $('startIsdn').select();
                $('startIsdn').focus();
                return false;
            }
            if(!isInteger(trim(endIsdn))) {
                $('message').innerHTML = "";
                $('divErrorFilePath').innerHTML = "";
                $('message').innerHTML= '<s:text name="ERR.ISN.007"/>';
                //$('message').innerHTML = "!!!Lỗi. Số cuối dải phải là số không âm";
                $('endIsdn').select();
                $('endIsdn').focus();
                return false;
            }
            if(startIsdn.length != endIsdn.length) {
                $('message').innerHTML = "";
                $('divErrorFilePath').innerHTML = "";
                $('message').innerHTML= '<s:text name="ERR.ISN.061"/>';
                //$('message').innerHTML = "!!!Lỗi. Số đầu dải và số cuối dải có độ dài không bằng nhau";
                $('startIsdn').select();
                $('startIsdn').focus();
                return false;
            }
            if(startIsdn > endIsdn) {
                $('message').innerHTML = "";
                $('divErrorFilePath').innerHTML = "";
                $('message').innerHTML= '<s:text name="ERR.ISN.062"/>';
                //$('message').innerHTML = "!!!Lỗi. Số đầu dải lớn hơn số cuối dải";
                $('startIsdn').select();
                $('startIsdn').focus();
                return false;
            }
            //trim cac truong can thiet
            $('startIsdn').value = trim($('startIsdn').value);
            $('endIsdn').value = trim($('endIsdn').value);
        }
        return true;
    }
    
    checkValidate = function() {
        var result = checkRequiredFields() && checkValidFields();
        return result;
    }

    //ham kiem tra cac truong bat buoc
    checkRequiredFields = function() {
        //
        var i = 1;
        for(i = 1; i < 3; i=i+1) {
            var radioId = "distributeIsdnForm.impType" + i;
            if($(radioId).checked == true) {
                break;
            }

        }
        if(i == 2) {
            //neu lay du lieu tu file
            if(trim($('serviceType').value) == "") {
                $('message').innerHTML = "";
                $('divErrorFilePath').innerHTML = "";
                $('message').innerHTML= '<s:text name="ERR.ISN.001"/>';
                //$('message').innerHTML = "!!!Lỗi. Chọn loại dịch vụ";
                $('serviceType').focus();
                return false;
            }
            if($('distributeIsdnForm.clientFileName').value == "") {
                $('message').innerHTML = "";
                $('divErrorFilePath').innerHTML = "";
                $('message').innerHTML= '<s:text name="ERR.ISN.027"/>';
                //$('message').innerHTML = "!!!Lỗi. Chọn file dữ liệu";
                $('distributeIsdnForm.clientFileName').select();
                $('distributeIsdnForm.clientFileName').focus();
                return false;
            }

        } else {
            //neu nhap du lieu theo dai so
            if(trim($('serviceType').value) == "") {
                $('message').innerHTML = "";
                $('divErrorFilePath').innerHTML = "";
                $('message').innerHTML= '<s:text name="ERR.ISN.001"/>';
                //$('message').innerHTML = "!!!Lỗi. Chọn loại dịch vụ";
                $('serviceType').focus();
                return false;
            }
            if(trim($('startIsdn').value) == "") {
                $('message').innerHTML = "";
                $('divErrorFilePath').innerHTML = "";
                $('message').innerHTML= '<s:text name="ERR.ISN.040"/>';
                //$('message').innerHTML = "!!!Lỗi. Số đầu dải không được để trống";
                $('startIsdn').select();
                $('startIsdn').focus();
                return false;
            }
            if(trim($('endIsdn').value) == "") {
                $('message').innerHTML = "";
                $('divErrorFilePath').innerHTML = "";
                $('message').innerHTML= '<s:text name="ERR.ISN.057"/>';
                //$('message').innerHTML = "!!!Lỗi. Số cuối dải không được để trống";
                $('endIsdn').select();
                $('endIsdn').focus();
                return false;
            }
            if(trim($('distributeIsdnForm.shopCode').value) == "") {
                $('message').innerHTML = "";
                $('divErrorFilePath').innerHTML = "";
                $('message').innerHTML= '<s:text name="ERR.ISN.058"/>';
                //$('message').innerHTML = "!!!Lỗi. Kho nhận số không được để trống";
                $('endIsdn').select();
                $('endIsdn').focus();
                return false;
            }
        }

        return true;
    }

    //ham kiem tra du lieu cac truong co hop le hay ko
    checkValidFields = function() {
        //
        var i = 1;
        for(i = 1; i < 3; i=i+1) {
            var radioId = "distributeIsdnForm.impType" + i;
            if($(radioId).checked == true) {
                break;
            }

        }

        if(i == 2) {
            //lay du lieu tu file

        } else {
            var startIsdn = trim($('startIsdn').value);
            var endIsdn = trim($('endIsdn').value);

            if(!isInteger(trim(startIsdn))) {
                $('message').innerHTML = "";
                $('divErrorFilePath').innerHTML = "";
                $('message').innerHTML= '<s:text name="ERR.ISN.006"/>';
                //$('message').innerHTML = "!!!Lỗi. Số đầu dải phải là số không âm";
                $('startIsdn').select();
                $('startIsdn').focus();
                return false;
            }
            if(!isInteger(trim(endIsdn))) {
                $('message').innerHTML = "";
                $('divErrorFilePath').innerHTML = "";
                $('message').innerHTML= '<s:text name="ERR.ISN.007"/>';
                //$('message').innerHTML = "!!!Lỗi. Số cuối dải phải là số không âm";
                $('endIsdn').select();
                $('endIsdn').focus();
                return false;
            }
            if(startIsdn.length != endIsdn.length) {
                $('message').innerHTML = "";
                $('divErrorFilePath').innerHTML = "";
                $('message').innerHTML= '<s:text name="ERR.ISN.061"/>';
                //$('message').innerHTML = "!!!Lỗi. Số đầu dải và số cuối dải có độ dài không bằng nhau";
                $('startIsdn').select();
                $('startIsdn').focus();
                return false;
            }
            if(startIsdn > endIsdn) {
                $('message').innerHTML = "";
                $('divErrorFilePath').innerHTML = "";
                $('message').innerHTML= '<s:text name="ERR.ISN.062"/>';
                //$('message').innerHTML = "!!!Lỗi. Số đầu dải lớn hơn số cuối dải";
                $('startIsdn').select();
                $('startIsdn').focus();
                return false;
            }
            //trim cac truong can thiet
            $('startIsdn').value = trim($('startIsdn').value);
            $('endIsdn').value = trim($('endIsdn').value);

        }

        return true;
    }

    //xu ly su kien khi thay doi kieu nhap hang
    radioClick = function(value){
        if(value == 2) {
            //disable cac component phuc vu viec nhap so theo dai
            //$('serviceType').disabled = true;
            $('distributeIsdnForm.fromShopCode').disabled = true;
            $('status').disabled = true;
            $('distributeIsdnForm.stockModelCode').disabled = true;
            $('isdnType').disabled = true;
            $('startIsdn').disabled = true;
            $('endIsdn').disabled = true;
            $('distributeIsdnForm.shopCode').disabled = true;
            $('newIsdnType').disabled = true;
            document.getElementById("btnSearchIsdnRange").disabled = true;

            //enable component phuc vu viec nhap so theo file
            disableImFileUpload('distributeIsdnForm.clientFileName', false);

        } else {
            //enable cac component phuc vu viec nhap so theo dai
            $('serviceType').disabled = false;
            $('distributeIsdnForm.fromShopCode').disabled = false;
            $('status').disabled = false;
            $('distributeIsdnForm.stockModelCode').disabled = false;
            $('isdnType').disabled = false;
            $('startIsdn').disabled = false;
            $('endIsdn').disabled = false;
            $('distributeIsdnForm.shopCode').disabled = false;
            $('newIsdnType').disabled = false;
            document.getElementById("btnSearchIsdnRange").disabled = false;
            
            //disable component phuc vu viec nhap so theo file
            disableImFileUpload('distributeIsdnForm.clientFileName', true);
        }
    }

    //update viec an hien cac vung, tuy thuoc vao kieu nhap hang
    var i = 1;
    for(i = 1; i < 3; i=i+1) {
        var radioId = "distributeIsdnForm.impType" + i;
        if($(radioId).checked == true) {
            radioClick(i);
            break;
        }

    }

</script>

