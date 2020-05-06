<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
    Document   : assignIsdnStatus
    Created on : Nov 24, 2009, 2:48:35 PM
    Author     : Vunt
    Modified by NamNX 06/04/2010 : Bo sung tinh nang nhap tim kiem theo file
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

<tags:imPanel title="MSG.assign.type.status.isdn">
    <!-- hien thi message -->
    <div>
        <tags:displayResult id="message" property="message" propertyValue="messageParam" type="key"/>
    </div>

    <!-- download log loi -->
    <!-- phan hien thi link download log loi -->
    <div align="center" class="txtError" id="divErrorFilePath">
        <s:if test="#request.errorFilePath != null">
            ${fn:escapeXml(imDef:imGetText('MSG.create.file.error.isdn'))}
            <a class="cursorHand" onclick="downloadErrorFile('${contextPath}/${fn:escapeXml(errorFilePath)}')">
                ${fn:escapeXml(imDef:imGetText('MSG.here'))}
            </a>
        </s:if>
    </div>

    <%--
    <s:if test="assignIsdnTypeForm.pathExpFile!=null && assignIsdnTypeForm.pathExpFile!=''">
        <script>
            window.open('${contextPath}/<s:property escapeJavaScript="true"  value="assignIsdnTypeForm.pathExpFile"/>','','toolbar=yes,scrollbars=yes');
        </script>
        <div>
            <a href='${contextPath}/<s:property escapeJavaScript="true"  value="assignIsdnTypeForm.pathExpFile"/>'<tags:label key="MSG.clickhere.to.download"/></a>
        </div>
    </s:if>--%>

    <s:form action="assignIsdnAction" theme="simple" method="post" id="assignIsdnTypeForm">
<s:token/>

        <!-- phan thong tin tim kiem dai so can gan loai so -->
        <table class="inputTbl6Col">
            <tr>
                <td style="width:13%; "><tags:label key="MSG.service.type" required="true"/></td>
                <td class="oddColumn" style="width:20%; ">
                    <tags:imSelect name="assignIsdnTypeForm.stockTypeId"
                                   id="assignIsdnTypeForm.stockTypeId"
                                   cssClass="txtInputFull" disabled="false"
                                   headerKey="1" headerValue="MSG.mobileNumber"
                                   list ="2: MSG.homephoneNumber,3:MSG.pstnNumber"
                                   />
                </td>
                <td><tags:label key="MSG.importType" required="true"/></td>
                <td colspan="3">

                    <s:radio name="assignIsdnTypeForm.impType"
                                 id="assignIsdnTypeForm.impType"
                                 list="#{'1':'Input by number range',
                                         '2':'Input from file (<a onclick=downloadPatternFile()> download sample data file here</a>)'}"
                                 onchange="radioClick(this.value)"/>

                    
                    <%--tags:imRadio name="assignIsdnTypeForm.impType"
                                  id="assignIsdnTypeForm.impType"
                                  list="1:MSG.importByListIsdn, 2:MSG.ISN.001"
                                  onchange="radioClick(this.value)"/--%>
                </td>
            </tr>
        </table>

        <div class="divHasBorder">
            <table class="inputTbl6Col">
                <tr>
                    <td style="width:13%; "><tags:label key="MSG.generates.file_data" required="true"/></td>
                    <td style="width:50%; ">
                        <!-- Edit by hieptd -->
                        <tags:imFileUpload name="assignIsdnTypeForm.clientFileName"
                                           id="assignIsdnTypeForm.clientFileName"
                                           serverFileName="assignIsdnTypeForm.serverFileName"
                                           cssStyle="width:100%;"
                                           extension="xls"/>
                    </td>
                    <td>
                        <tags:submit formId="assignIsdnTypeForm"
                                     showLoadingText="true"
                                     cssStyle="width:80px;"
                                     id="btnAssignIsdnTypeFromFile"
                                     targets="bodyContent"
                                     validateFunction="checkValidateImportFromFile()"
                                     value="MSG.generates.execute"
                                     preAction="assignIsdnAction!assignStatusForIsdnByFile.do"/>
                    </td>
                </tr>
            </table>
        </div>

        <div class="divHasBorder" style="margin-top: 3px;">
            <table class="inputTbl6Col">
                <tr>
                    <td style="width:13%; "><tags:label key="MSG.isdn.area" required="true"/></td>
                    <td class="oddColumn" style="width:37%; ">
                        <table style="width:100%; border-spacing:0px; ">
                            <tr>
                                <td>
                                    <s:textfield name="assignIsdnTypeForm.fromIsdn" id="assignIsdnTypeForm.fromIsdn" maxlength="15" cssClass="txtInputFull"/>
                                </td>
                                <td style="width:10px; text-align:center">-</td>
                                <td>
                                    <s:textfield name="assignIsdnTypeForm.toIsdn" id="assignIsdnTypeForm.toIsdn" maxlength="15" cssClass="txtInputFull"/>
                                </td>
                            </tr>
                        </table>
                    </td>
                    <td style="width:10%; "><tags:label key="MSG.status"/></td>
                    <td class="oddColumn" style="width:15%; ">
                        <tags:imSelect name="assignIsdnTypeForm.status"
                                       id="assignIsdnTypeForm.status"
                                       cssClass="txtInputFull" disabled="false"
                                       list="1:MSG.newIsdn, 3:MSG.stopIsdn "
                                       headerKey="" headerValue="MSG.GOD.189"/>
                    </td>
                    <td style="width:10%; "> <tags:label key="MSG.isdn_type"/></td>
                    <td>
                        <tags:imSelect name="assignIsdnTypeForm.isdnType"
                                       id="assignIsdnTypeForm.isdnType"
                                       cssClass="txtInputFull"
                                       disabled="false"
                                       list="-1:MSG.ISN.027,1:MSG.ISN.005,2:MSG.ISN.004, 3:MSG.ISN.003 "
                                       headerKey="" headerValue="MSG.chooseIsdnType"/>
                    </td>
                </tr>
                <tr>
                    <td><tags:label key="MSG.fromIsdnStore"/></td>
                    <td class="oddColumn">
                        <tags:imSearch codeVariable="assignIsdnTypeForm.shopCode" nameVariable="assignIsdnTypeForm.shopName"
                                       codeLabel="MSG.store.code" nameLabel="MSG.storeName"
                                       searchClassName="com.viettel.im.database.DAO.assignIsdnStatusDAO"
                                       searchMethodName="getListShop"/>
                    </td>
                    <td><tags:label key="MSG.ISN.055" required="true"/></td>
                    <td class="oddColumn">
                        <tags:imSelect name="assignIsdnTypeForm.newIsdnStatus"
                                       id="assignIsdnTypeForm.newIsdnStatus"
                                       cssClass="txtInputFull"
                                       disabled="false"
                                       list="1:MSG.newIsdn, 3:MSG.stopIsdn"
                                       headerKey="" headerValue="MSG.GOD.189"/>
                    </td>
                    <td> <tags:label key="MSG.ISN.056" required="true"/></td>
                    <td>
                        <tags:imSelect name="assignIsdnTypeForm.newIsdnType"
                                       id="assignIsdnTypeForm.newIsdnType"
                                       cssClass="txtInputFull"
                                       disabled="false"
                                       list="1:MSG.ISN.005,2:MSG.ISN.004, 3:MSG.ISN.003 "
                                       headerKey="" headerValue="MSG.chooseIsdnType"/>
                    </td>
                </tr>
            </table>

            <div align="center" style="vertical-align:top; ">
                <tags:submit formId="assignIsdnTypeForm"
                             showLoadingText="true"
                             cssStyle="width:80px;"
                             id="btnSearchIsdnRange"
                             targets="bodyContent"
                             validateFunction="checkValidate()"
                             value="MSG.createList"
                             preAction="assignIsdnAction!searchIsdnRange.do"/>
                <tags:submit formId="assignIsdnTypeForm"
                             showLoadingText="true"
                             cssStyle="width:80px;"
                             id="btnUpdate"
                             targets="bodyContent"
                             validateFunction="checkValidateToAssignIsdnType()"
                             value="MSG.update"
                             preAction="assignIsdnAction!assignIsdnType.do"/>
            </div>
        </div>

        <div>
            <jsp:include page="listAssignIsdnType.jsp"/>
        </div>

    </s:form>
</tags:imPanel>

<script type="text/javascript" language="javascript">

        
    downloadErrorFile = function(errorFileUrl) {
        window.open(errorFileUrl, '', 'toolbar=yes,scrollbars=yes');
    }

    //focus vao truong dau tien
    //$('assignIsdnTypeForm.shopCode').select();
    //$('assignIsdnTypeForm.shopCode').focus();
    //$('btnAssignIsdnTypeFromFile').disabled=true;
    //disableImFileUpload('assignIsdnTypeForm.clientFileName', true);
    <%--    $('btnAssignIsdnTypeFromFile').style.visibility  = 'hidden';--%>


        checkValidateToAssignIsdnType = function() {
            var rowId = document.getElementsByName('assignIsdnTypeForm.isdnIdList');
            var existCbHasChecked = false;
            for(var i = 0; i < rowId.length; i++){
                if($('arrSelectedFormId_' + rowId[i].value).checked == true){
                    existCbHasChecked = true;
                    break;
                }
            }
            if(existCbHasChecked){
    <%--var newType = document.getElementsByName('assignIsdnTypeForm.newIsdnType');
    var newStatus = document.getElementsByName('assignIsdnTypeForm.newIsdnStatus');
    if (trim(newType.value) == "" && trim(newStatus.value) == ""){
        $('message').innerHTML = "!!!Lỗi. Chọn ít nhất một loại tác động để thực hiện";
        $('assignIsdnTypeForm.newIsdnType').focus();
        return false;
    }--%>
                return true;
            }else{
                $('message').innerHTML = "";
                $('divErrorFilePath').innerHTML = "";
                //                $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.ISN.025')"/>';
                $('message').innerHTML= '<s:text name="ERR.ISN.025"/>';
                //$('message').innerHTML = "!!!Lỗi. Chọn ít nhất 1 dải số để gán loại số";
                $('cbCheckAll').focus();
                return false;
            }
        }

        checkValidateImportFromFile = function() {
            //truong loai dich vu
            if(trim($('assignIsdnTypeForm.stockTypeId').value) == "") {
                $('message').innerHTML = "";
                $('divErrorFilePath').innerHTML = "";
                //                $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.ISN.001')"/>';
                $('message').innerHTML= '<s:text name="ERR.ISN.001"/>';
                //$('message').innerHTML = "!!!Lỗi. Chọn loại dịch vụ";
                $('assignIsdnTypeForm.stockTypeId').focus();
                return false;
            }


            //nhap hang theo file -> ten file ko duoc de trong


            if($('assignIsdnTypeForm.clientFileName').value == "") {
                $('message').innerHTML = "";
                $('divErrorFilePath').innerHTML = "";
                //                $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.ISN.027')"/>';
                $('message').innerHTML= '<s:text name="ERR.ISN.027"/>';
                //$('message').innerHTML = "!!!Lỗi. Chọn file dữ liệu";
                $('assignIsdnTypeForm.clientFileName').focus();
                return false;
            }
            return true;
        }

        checkValidate = function() {
        
            var i = 1;
            for(i = 1; i < 3; i=i+1) {
                var radioId = "assignIsdnTypeForm.impType" + i;
                if($(radioId).checked == true) {
                    break;
                }

            }

            if(i == 2) {
                //lay du lieu tu file

            } else {
                //du lieu tu nhap


            
                var result = checkRequiredFields() && checkValidFields();
                if(result) {
                    //startProgressBar();
                }
                return (result);
            }
        }
        //ham kiem tra cac truong bat buoc
        checkRequiredFields = function() {
            //truong ma cua hang
            /*
            if(trim($('assignIsdnTypeForm.shopCode').value) == "") {
       $('message').innerHTML= '<s:text name="ERR.ISN.028"/>';
             *         //$('message').innerHTML = "!!!Lỗi. Mã cửa hàng không được để trống";
                $('assignIsdnTypeForm.shopCode').select();
                $('assignIsdnTypeForm.shopCode').focus();
                return false;
            }*/
            //truong loai dich vu
            if(trim($('assignIsdnTypeForm.stockTypeId').value) == "") {
                $('message').innerHTML = "";
                $('divErrorFilePath').innerHTML = "";
                $('message').innerHTML= '<s:text name="ERR.ISN.001"/>';
                //$('message').innerHTML = "!!!Lỗi. Chọn loại dịch vụ";
                $('assignIsdnTypeForm.stockTypeId').focus();
                return false;
            }
            //truong tu so
            if(trim($('assignIsdnTypeForm.fromIsdn').value) == "") {
                $('message').innerHTML = "";
                $('divErrorFilePath').innerHTML = "";
                $('message').innerHTML= '<s:text name="ERR.ISN.030"/>';
                //$('message').innerHTML = "!!!Lỗi. Dải số không được để trống";
                $('assignIsdnTypeForm.fromIsdn').select();
                $('assignIsdnTypeForm.fromIsdn').focus();
                return false;
            }
            //truong den so
            if(trim($('assignIsdnTypeForm.toIsdn').value) == "") {
                $('message').innerHTML = "";
                $('divErrorFilePath').innerHTML = "";
                $('message').innerHTML= '<s:text name="ERR.ISN.030"/>';
                //$('message').innerHTML = "!!!Lỗi. Dải số không được để trống";
                $('assignIsdnTypeForm.toIsdn').select();
                $('assignIsdnTypeForm.toIsdn').focus();
                return false;
            }

            return true;
        }

        //kiem tra tinh hop le cua cac truong
        checkValidFields = function() {
            //truong tu so phai la so nguyen duong
            if(!isInteger(trim($('assignIsdnTypeForm.fromIsdn').value))) {
                $('message').innerHTML = "";
                $('divErrorFilePath').innerHTML = "";
                $('message').innerHTML= '<s:text name="ERR.ISN.032"/>';
                //$('message').innerHTML = "!!!Lỗi. Dải số phải là số không âm";
                $('assignIsdnTypeForm.fromIsdn').select();
                $('assignIsdnTypeForm.fromIsdn').focus();
                return false;
            }
            //truong cuoi dai phai la so nguyen duong
            if(!isInteger(trim($('assignIsdnTypeForm.toIsdn').value))) {
                $('message').innerHTML = "";
                $('divErrorFilePath').innerHTML = "";
                $('message').innerHTML= '<s:text name="ERR.ISN.032"/>';
                //$('message').innerHTML = "!!!Lỗi. Dải số phải là số không âm";
                $('assignIsdnTypeForm.toIsdn').select();
                $('assignIsdnTypeForm.toIsdn').focus();
                return false;
            }
            //so dau dai va so cuoi dai phai co chieu dai = nhau
            var startStockIsdn = trim($('assignIsdnTypeForm.fromIsdn').value);
            var endStockIsdn = trim($('assignIsdnTypeForm.toIsdn').value);
            if(startStockIsdn.length != endStockIsdn.length) {
                $('message').innerHTML = "";
                $('divErrorFilePath').innerHTML = "";
                $('message').innerHTML= '<s:text name="ERR.ISN.009"/>';
                //$('message').innerHTML = "!!!Lỗi. Số đầu dải và số cuối dải phải có chiều dài bằng nhau";
                $('assignIsdnTypeForm.fromIsdn').select();
                $('assignIsdnTypeForm.fromIsdn').focus();
                return false;
            }
            //so dau dai khong duoc lon hon so cuoi dai
            if(startStockIsdn * 1 > endStockIsdn * 1) {
                $('message').innerHTML = "";
                $('divErrorFilePath').innerHTML = "";
                $('message').innerHTML= '<s:text name="ERR.ISN.010"/>';
                //$('message').innerHTML = "!!!Lỗi. Số đầu dải không được lớn hơn số cuối dải";
                $('assignIsdnTypeForm.fromIsdn').select();
                $('assignIsdnTypeForm.fromIsdn').focus();
                return false;
            }

            //trim cac truong can thiet
            $('assignIsdnTypeForm.shopCode').value = trim($('assignIsdnTypeForm.shopCode').value);
            $('assignIsdnTypeForm.fromIsdn').value = trim($('assignIsdnTypeForm.fromIsdn').value);
            $('assignIsdnTypeForm.toIsdn').value = trim($('assignIsdnTypeForm.toIsdn').value);

            return true;
        }
        downloadPatternFile = function() {
            window.open('${contextPath}/share/pattern/assignIsdnStatusPattern.xls','','toolbar=yes,scrollbars=yes');
        }

        //xu ly su kien khi thay doi kieu nhap
        radioClick = function(value){
            
            if(value == 2) {
                //reset cac gia tri
                $('assignIsdnTypeForm.stockTypeId').value = 1;
                $('assignIsdnTypeForm.isdnType').value = -1;
                $('assignIsdnTypeForm.status').value = 1;
                $('assignIsdnTypeForm.fromIsdn').value = "";
                $('assignIsdnTypeForm.toIsdn').value = "";
                //enable va disable cac truong can thiet
                $('assignIsdnTypeForm.shopCode').disabled = true;
                $('assignIsdnTypeForm.shopName').disabled = true;
                $('assignIsdnTypeForm.isdnType').disabled = true;
                $('assignIsdnTypeForm.status').disabled = true;
                $('assignIsdnTypeForm.fromIsdn').disabled = true;
                $('assignIsdnTypeForm.toIsdn').disabled = true;
                $('assignIsdnTypeForm.newIsdnStatus').disabled = true;
                $('assignIsdnTypeForm.newIsdnType').disabled = true;
                $('btnAssignIsdnTypeFromFile').disabled=false;
                $('btnSearchIsdnRange').disabled  = true;
                $('btnUpdate').disabled  = true;

                disableImFileUpload('assignIsdnTypeForm.clientFileName', false);
            } else {
                $('assignIsdnTypeForm.clientFileName').value = "";
                //enable va disable cac truong can thiet

                $('assignIsdnTypeForm.shopCode').disabled = false;
                $('assignIsdnTypeForm.shopName').disabled = false;
                $('assignIsdnTypeForm.isdnType').disabled = false;
                $('assignIsdnTypeForm.status').disabled = false;
                $('assignIsdnTypeForm.fromIsdn').disabled = false;
                $('assignIsdnTypeForm.toIsdn').disabled = false;
                $('assignIsdnTypeForm.newIsdnStatus').disabled = false;
                $('assignIsdnTypeForm.newIsdnType').disabled = false;
                $('btnAssignIsdnTypeFromFile').disabled=true;
                $('btnSearchIsdnRange').disabled  = false;
                $('btnUpdate').disabled  = false;
                disableImFileUpload('assignIsdnTypeForm.clientFileName', true);
            }

        }

        var i = 1;
        for(i = 1; i < 3; i=i+1) {
            var radioId = "assignIsdnTypeForm.impType" + i;
            if($(radioId).checked == true) {
                break;
            }

        }
        radioClick(i);

</script>
