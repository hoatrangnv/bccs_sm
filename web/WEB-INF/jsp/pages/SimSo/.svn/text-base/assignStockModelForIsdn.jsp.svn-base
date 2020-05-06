<%@page import="com.viettel.security.util.StringEscapeUtils"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : assignStockModelForIsdn
    Created    : May 13, 2009, 3:39:02 PM
    Author     : tamdt1
    Modified   : NamNX
    Note       : cap nhat so isdn thanh mat hang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%@page import="com.viettel.im.common.util.ResourceBundleUtils" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
%>

<s:form action="assignStockModelForIsdnAction" theme="simple" method="POST" id="assignStockModelForIsdnForm">
<s:token/>

    <tags:imPanel title="MSG.assign.stockmodel.to.isdn">
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
        <table class="inputTbl6Col">
            <tr>
                <td style="width:13%; "><tags:label key="MSG.service.type" required="true"/></td>
                <td class="oddColumn" style="width:20%; ">
                    <tags:imSelect name="assignStockModelForIsdnForm.stockTypeId"
                                   id="assignStockModelForIsdnForm.stockTypeId"
                                   list="2:MSG.homephoneNumber,3:MSG.pstnNumber"
                                   cssClass="txtInputFull"
                                   headerKey="1" headerValue="MSG.mobileNumber"
                                   onchange="changeStockType(this.value)"/>
                </td>
                <td><tags:label key="MSG.importType" required="true"/></td>
                <td colspan="3">

                     <s:radio name="assignStockModelForIsdnForm.impType"
                                 id="assignStockModelForIsdnForm.impType"
                                 list="#{'1':'Input by number range',
                                         '2':'Input from file (<a onclick=downloadPatternFile()> download sample data file here</a>)'}"
                                 onchange="radioClick(this.value)"/>

                    <%--tags:imRadio name="assignStockModelForIsdnForm.impType"
                                  id="assignStockModelForIsdnForm.impType"
                                  list="1:MSG.importByListIsdn,
                                  2:MSG.ISN.001"
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
                        <tags:imFileUpload name="assignStockModelForIsdnForm.clientFileName"
                                           id="assignStockModelForIsdnForm.clientFileName"
                                           serverFileName="assignStockModelForIsdnForm.serverFileName"
                                           cssStyle="width:100%;"
                                           extension="xls"/>
                    </td>
                    <td style="text-align: right; width:85px;">
                        <tags:submit formId="assignStockModelForIsdnForm"
                                     showLoadingText="true"
                                     cssStyle="width:80px;"
                                     id="btnUpdateByFile"
                                     targets="bodyContent"
                                     value="MSG.update"
                                     validateFunction="checkValidateBeforeAssign()"
                                     preAction="assignStockModelForIsdnAction!assignStockModelForIsdn.do"
                                     showProgressDiv="true"
                                     showProgressClass="com.viettel.im.database.DAO.AssignStockModelForIsdnDAO"
                                     showProgressMethod="updateProgressDiv"/>

                    </td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
            </table>
        </div>

        <div class="divHasBorder" style="margin-top: 3px;">
            <table class="inputTbl6Col">
                <tr>
                    <td style="width:13%; "><tags:label key="MSG.isdn.area" required="true"/></td>
                    <td class="oddColumn">
                        <table style="width:100%; border-spacing:0px; ">
                            <tr>
                                <td style="width:50%;">
                                    <s:textfield name="assignStockModelForIsdnForm.fromIsdn" id="assignStockModelForIsdnForm.fromIsdn" maxlength="25" cssClass="txtInputFull"/>
                                </td>
                                <td>-</td>
                                <td style="width:50%;">
                                    <s:textfield name="assignStockModelForIsdnForm.toIsdn" id="assignStockModelForIsdnForm.toIsdn" maxlength="25" cssClass="txtInputFull"/>
                                </td>
                            </tr>
                        </table>
                    </td>
                    <td><tags:label key="MSG.fromIsdnStore"/></td>
                    <td>
                        <tags:imSearch codeVariable="assignStockModelForIsdnForm.shopCode" nameVariable="assignStockModelForIsdnForm.shopName"
                                       codeLabel="MSG.store.code" nameLabel="MSG.storeName"
                                       searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                       searchMethodName="getListShop"
                                       getNameMethod="getShopName"/>
                    </td>
                </tr>
                <tr>
                    <%--
                    <td> <tags:label key="MSG.interval.price"/></td>
                    <td class="oddColumn">
                        <table style="width:100%; border-spacing:0px; ">
                            <tr>
                                <td style="width:50%;">
                                    <s:textfield name="assignStockModelForIsdnForm.fromPrice" id="assignStockModelForIsdnForm.fromPrice" onkeyup="textFieldNF(this)" maxlength="15" cssClass="txtInputFull" cssStyle="text-align:right; "/>
                                </td>
                                <td>-</td>
                                <td style="width:50%;">
                                    <s:textfield name="assignStockModelForIsdnForm.toPrice" id="assignStockModelForIsdnForm.toPrice" onkeyup="textFieldNF(this)" maxlength="15" cssClass="txtInputFull" cssStyle="text-align:right; "/>
                                </td>
                            </tr>
                        </table>
                    </td>
                    --%>
                    <td><tags:label key="MSG.generates.goods"/></td>
                    <td class="oddColumn">
                        <tags:imSearch codeVariable="assignStockModelForIsdnForm.stockModelCode" nameVariable="assignStockModelForIsdnForm.stockModelName"
                                       codeLabel="MSG.stockModelId" nameLabel="MSG.stockModelName"
                                       searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                       searchMethodName="getListStockModel"
                                       getNameMethod="getStockModelName"
                                       otherParam="assignStockModelForIsdnForm.stockTypeId"/>
                    </td>
                    <td><tags:label key="MSG.newGoods" required="true"/></td>
                    <td>
                        <tags:imSearch codeVariable="assignStockModelForIsdnForm.newStockModelCode" nameVariable="assignStockModelForIsdnForm.newStockModelName"
                                       codeLabel="MSG.stockModelId" nameLabel="MSG.stockModelName"
                                       searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                       searchMethodName="getListStockModel"
                                       getNameMethod="getStockModelName"
                                       otherParam="assignStockModelForIsdnForm.stockTypeId"/>
                    </td>
                </tr>
                <%--
                <tr>
                    <td> <tags:label key="MSG.isdn.beati.rules"/></td>
                    <td class="oddColumn">
                        <tags:imSelect name="assignStockModelForIsdnForm.groupFilterRuleCode"
                                       id="assignStockModelForIsdnForm.groupFilterRuleCode"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.selectRuleType"
                                       list="listGroupFilter"
                                       listKey="groupFilterRuleCode" listValue="name"
                                       onchange="changeGroupFilterRuleCode(this.value);"/>
                    </td>
                    <td><tags:label key="MSG.isdn.beati.group.rule"/></td>
                    <td>
                        <tags:imSelect name="assignStockModelForIsdnForm.filterTypeId"
                                       id="assignStockModelForIsdnForm.filterTypeId"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.chooseFilterType"
                                       list="listFilterType"
                                       listKey="filterTypeId" listValue="name"
                                       onchange="changeFilterType(this.value);"/>
                    </td>
                </tr>
                <tr>
                    <td><tags:label key="MSG.isdn.beauti.rule"/></td>
                    <td class="oddColumn">
                        <tags:imSelect name="assignStockModelForIsdnForm.rulesId"
                                       id="assignStockModelForIsdnForm.rulesId"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.selectRule"
                                       list="listRule"
                                       listKey="rulesId" listValue="name"/>
                    </td>
                    <td><tags:label key="MSG.newGoods" required="true"/></td>
                    <td>
                        <tags:imSearch codeVariable="assignStockModelForIsdnForm.newStockModelCode" nameVariable="assignStockModelForIsdnForm.newStockModelName"
                                       codeLabel="MSG.stockModelId" nameLabel="MSG.stockModelName"
                                       searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                       searchMethodName="getListStockModel"
                                       getNameMethod="getStockModelName"
                                       otherParam="assignStockModelForIsdnForm.stockTypeId"/>
                    </td>
                </tr>
                --%>
            </table>

            <div align="center" style="vertical-align:top; ">
                <tags:submit formId="assignStockModelForIsdnForm"
                             showLoadingText="true"
                             cssStyle="width:80px;"
                             id="btnSearch"
                             targets="bodyContent"
                             value="MSG.createList"
                             validateFunction="checkValidateBeforeSearch()"
                             preAction="assignStockModelForIsdnAction!searchIsdnRange.do"/>
                <tags:submit formId="assignStockModelForIsdnForm"
                             showLoadingText="true"
                             cssStyle="width:80px;"
                             id="btnUpdate"
                             targets="bodyContent"
                             value="MSG.update"
                             validateFunction="checkValidateBeforeAssign()"
                             preAction="assignStockModelForIsdnAction!assignStockModelForIsdn.do"
                             showProgressDiv="true"
                             showProgressClass="com.viettel.im.database.DAO.AssignStockModelForIsdnDAO"
                             showProgressMethod="updateProgressDiv"/>
            </div>

        </div>

        <div>
            <jsp:include page="assignStockModelForIsdnList.jsp"/>
        </div>

    </tags:imPanel>
</s:form>


<script language="javascript">

    //focus vao truong dau tien
    //$('assignStockModelForIsdnForm.fromIsdn').select();
    //$('assignStockModelForIsdnForm.fromIsdn').focus();

    downloadErrorFile = function(errorFileUrl) {
        window.open(errorFileUrl, '', 'toolbar=yes,scrollbars=yes');
    }

    //cap nhat lai danh sach mat hang, danh sach tap luat
    changeStockType = function(stockTypeId) {
        // TuTM1 04/03/2012 Fix ATTT CSRF
        updateData("${contextPath}/assignStockModelForIsdnAction!updateListGroupFilterRule.do?target=assignStockModelForIsdnForm.groupFilterRuleCode,assignStockModelForIsdnForm.filterTypeId,assignStockModelForIsdnForm.rulesId&stockTypeId=" 
            + stockTypeId + "&" + token.getTokenParamString());
    }

    //xu ly su kien khi GroupFilterRuleCode change
    changeGroupFilterRuleCode = function(groupFilterRuleCode) {
        // TuTM1 04/03/2012 Fix ATTT CSRF
        updateData("${contextPath}/assignStockModelForIsdnAction!updateListFilterType.do?target=assignStockModelForIsdnForm.filterTypeId,assignStockModelForIsdnForm.rulesId&groupFilterRuleCode=" 
            + groupFilterRuleCode + "&" + token.getTokenParamString());
    }

    //xu ly su kien khi FilterType change
    changeFilterType = function(filterId) {
        // TuTM1 04/03/2012 Fix ATTT CSRF
        updateData("${contextPath}/assignStockModelForIsdnAction!updateListRule.do?target=assignStockModelForIsdnForm.rulesId&filterId=" 
            + filterId + "&" + token.getTokenParamString());
    }

    checkValidateBeforeSearch = function () {
        var i = 1;
        for(i = 1; i < 3; i=i+1) {
            var radioId = "assignStockModelForIsdnForm.impType" + i;
            if($(radioId).checked == true) {
                break;
            }

        }
        if(i == 2) {
            //neu lay du lieu tu file
            if($('assignStockModelForIsdnForm.clientFileName').value == "") {
                $('message').innerHTML = "";
                $('divErrorFilePath').innerHTML = "";
                $('message').innerHTML= '<s:text name="ERR.ISN.027"/>';
                //$('message').innerHTML = "!!!Lỗi. Chọn file dữ liệu";
                $('assignStockModelForIsdnForm.clientFileName').select();
                $('assignStockModelForIsdnForm.clientFileName').focus();
                return false;
            }

        } else {
            //neu nhap du lieu theo dai
            //1> kiem tra cac truong can thiet
            if(trim($('assignStockModelForIsdnForm.fromIsdn').value) == "") {
                $('message').innerHTML = "";
                $('divErrorFilePath').innerHTML = "";
                $('message').innerHTML= '<s:text name="ERR.ISN.014"/>';
                //$('message').innerHTML = "!!!Lỗi. Trường từ số không được để trống";
                $('assignStockModelForIsdnForm.fromIsdn').select();
                $('assignStockModelForIsdnForm.fromIsdn').focus();
                return false;
            }

            if(trim($('assignStockModelForIsdnForm.toIsdn').value) == "") {
                $('message').innerHTML = "";
                $('divErrorFilePath').innerHTML = "";
                $('message').innerHTML= '<s:text name="ERR.ISN.112"/>';
                //$('message').innerHTML = "!!!Lỗi. Trường đến số không được để trống";
                $('assignStockModelForIsdnForm.toIsdn').select();
                $('assignStockModelForIsdnForm.toIsdn').focus();
                return false;
            }

            //2> kiem tra tinh hop le cua cac truong
            var fromIsdn = $('assignStockModelForIsdnForm.fromIsdn').value;
            var toIsdn = $('assignStockModelForIsdnForm.toIsdn').value;
            if(!isInteger(trim(fromIsdn))) {
                $('message').innerHTML = "";
                $('divErrorFilePath').innerHTML = "";
                $('message').innerHTML= '<s:text name="ERR.ISN.113"/>';
                //$('message').innerHTML = "!!!Lỗi. Trường từ số phải là số không âm";
                $('assignStockModelForIsdnForm.fromIsdn').select();
                $('assignStockModelForIsdnForm.fromIsdn').focus();
                return false;
            }
            if(!isInteger(trim(toIsdn))) {
                $('message').innerHTML = "";
                $('divErrorFilePath').innerHTML = "";
                $('message').innerHTML= '<s:text name="ERR.ISN.114"/>';
                //$('message').innerHTML = "!!!Lỗi. Trường đến số phải là số không âm";
                $('assignStockModelForIsdnForm.toIsdn').select();
                $('assignStockModelForIsdnForm.toIsdn').focus();
                return false;
            }
            if(fromIsdn * 1 > toIsdn * 1) {
                $('message').innerHTML = "";
                $('divErrorFilePath').innerHTML = "";
                $('message').innerHTML= '<s:text name="ERR.ISN.115"/>';
                //$('message').innerHTML = "!!!Lỗi. Trường từ số không được lớn hơn trường đến số";
                $('assignStockModelForIsdnForm.fromIsdn').select();
                $('assignStockModelForIsdnForm.fromIsdn').focus();
                return false;
            }

            var shopCode = $('assignStockModelForIsdnForm.shopCode').value;
            if(shopCode != null && shopCode != '' && !isValidInput(trim(shopCode), false, false)) {
                $('message').innerHTML = "";
                $('divErrorFilePath').innerHTML = "";
                $('message').innerHTML= '<s:text name="ERR.ISN.116"/>';
                //$('message').innerHTML = "!!!Lỗi. Mã kho lấy số chỉ được chứa các ký tự A-Z, a-z, 0-9, _";
                $('assignStockModelForIsdnForm.shopCode').select();
                $('assignStockModelForIsdnForm.shopCode').focus();
                return false;
            }

            var stockModelCode = $('assignStockModelForIsdnForm.stockModelCode').value;
            if(stockModelCode != null && stockModelCode != '' && !isValidInput(trim(stockModelCode), false, false)) {
                $('message').innerHTML = "";
                $('divErrorFilePath').innerHTML = "";
                $('message').innerHTML= '<s:text name="ERR.ISN.117"/>';
                //$('message').innerHTML = "!!!Lỗi. Mã mặt hàng chỉ được chứa các ký tự A-Z, a-z, 0-9, _";
                $('assignStockModelForIsdnForm.stockModelCode').select();
                $('assignStockModelForIsdnForm.stockModelCode').focus();
                return false;
            }

            /*
            var fromPrice = $('assignStockModelForIsdnForm.fromPrice').value;
            var toPrice = $('assignStockModelForIsdnForm.toPrice').value;
            fromPrice = fromPrice.replace(/\,/g,""); //loai bo dau , trong chuoi
            toPrice = toPrice.replace(/\,/g,""); //loai bo dau , trong chuoi

            if(fromPrice != null && fromPrice != '' && !isInteger(trim(fromPrice))) {
                $('message').innerHTML= '<s:text name="ERR.ISN.118"/>';
                //$('message').innerHTML = "!!!Lỗi. Trường từ giá phải là số không âm";
                $('assignStockModelForIsdnForm.fromPrice').select();
                $('assignStockModelForIsdnForm.fromPrice').focus();
                return false;
            }

            if(toPrice != null && toPrice != '' && !isInteger(trim(toPrice))) {
                $('message').innerHTML= '<s:text name="ERR.ISN.119"/>';
                //$('message').innerHTML = "!!!Lỗi. Trường đến giá phải là số không âm";
                $('assignStockModelForIsdnForm.toPrice').select();
                $('assignStockModelForIsdnForm.toPrice').focus();
                return false;
            }

            if(fromPrice != null && fromPrice != '' && toPrice != null && toPrice != '' && fromPrice * 1 > toPrice * 1) {
                $('message').innerHTML= '<s:text name="ERR.ISN.120"/>';
                //$('message').innerHTML = "!!!Lỗi. Trường từ giá không được lớn hơn trường đến giá";
                $('assignStockModelForIsdnForm.fromPrice').select();
                $('assignStockModelForIsdnForm.fromPrice').focus();
                return false;
            }
            */

            //kiem tra so luong dai so khong duoc vuot qua max
            var maxAssignIsdnToGood = '<%=StringEscapeUtils.escapeHtml(ResourceBundleUtils.getResource("MAX_ASSIGN_ISDN_TO_GOOD"))%>';
            if((toIsdn*1 - fromIsdn*1 + 1) > maxAssignIsdnToGood * 1) {
                $('message').innerHTML = "";
                $('divErrorFilePath').innerHTML = "";
                $('message').innerHTML= '<s:text name="ERR.ISN.012"/>';
                //$('message').innerHTML = "!!!Lỗi. Số lượng số tìm kiếm vượt quá " + maxAssignIsdnToGood + " số";
                $('assignStockModelForIsdnForm.fromIsdn').select();
                $('assignStockModelForIsdnForm.fromIsdn').focus();
                return false;
            }

            //trim cac truong can thiet
            $('assignStockModelForIsdnForm.fromIsdn').value = trim($('assignStockModelForIsdnForm.fromIsdn').value);
            $('assignStockModelForIsdnForm.toIsdn').value = trim($('assignStockModelForIsdnForm.toIsdn').value);
            //$('assignStockModelForIsdnForm.fromPrice').value = trim($('assignStockModelForIsdnForm.fromPrice').value);
            //$('assignStockModelForIsdnForm.toPrice').value = trim($('assignStockModelForIsdnForm.toPrice').value);
            $('assignStockModelForIsdnForm.shopCode').value = trim($('assignStockModelForIsdnForm.shopCode').value);
            $('assignStockModelForIsdnForm.stockModelCode').value = trim($('assignStockModelForIsdnForm.stockModelCode').value);
        }

        return true;
    }

    checkValidateBeforeAssign = function () {
        var newStockModelCode = $('assignStockModelForIsdnForm.newStockModelCode').value;
        if(newStockModelCode != null && newStockModelCode != '' && !isValidInput(trim(newStockModelCode), false, false)) {
            $('message').innerHTML = "";
            $('divErrorFilePath').innerHTML = "";
            $('message').innerHTML= '<s:text name="ERR.ISN.117"/>';
            //$('message').innerHTML = "!!!Lỗi. Mã mặt hàng chỉ được chứa các ký tự A-Z, a-z, 0-9, _";
            $('assignStockModelForIsdnForm.newStockModelCode').select();
            $('assignStockModelForIsdnForm.newStockModelCode').focus();
            return false;
        }

        //
        $('assignStockModelForIsdnForm.newStockModelCode').value = trim($('assignStockModelForIsdnForm.newStockModelCode').value);

        return true;
    }

    //xu ly su kien khi thay doi kieu nhap du lieu
    radioClick = function(value){
        if(value == 2) {
            //disable cac component phuc vu viec nhap so theo dai
            //$('assignStockModelForIsdnForm.stockTypeId').disabled = true;
            $('assignStockModelForIsdnForm.shopCode').disabled = true;
            //$('assignStockModelForIsdnForm.groupFilterRuleCode').disabled = true;
            //$('assignStockModelForIsdnForm.filterTypeId').disabled = true;
            //$('assignStockModelForIsdnForm.rulesId').disabled = true;
            $('assignStockModelForIsdnForm.stockModelCode').disabled = true;
            $('assignStockModelForIsdnForm.newStockModelCode').disabled = true;
            $('assignStockModelForIsdnForm.fromIsdn').disabled = true;
            $('assignStockModelForIsdnForm.toIsdn').disabled = true;
            //$('assignStockModelForIsdnForm.fromPrice').disabled = true;
            //$('assignStockModelForIsdnForm.toPrice').disabled = true;
            $('btnSearch').disabled  = true;
            $('btnUpdate').disabled  = true;
            $('btnUpdateByFile').disabled  = false;

            //enable component phuc vu viec nhap so theo file
            disableImFileUpload('assignStockModelForIsdnForm.clientFileName', false);

        } else {
            //disable cac component phuc vu viec nhap so theo dai
            //$('assignStockModelForIsdnForm.stockTypeId').disabled = false;
            $('assignStockModelForIsdnForm.shopCode').disabled = false;
            //$('assignStockModelForIsdnForm.groupFilterRuleCode').disabled = false;
            //$('assignStockModelForIsdnForm.filterTypeId').disabled = false;
            //$('assignStockModelForIsdnForm.rulesId').disabled = false;
            $('assignStockModelForIsdnForm.stockModelCode').disabled = false;
            $('assignStockModelForIsdnForm.newStockModelCode').disabled = false;
            $('assignStockModelForIsdnForm.fromIsdn').disabled = false;
            $('assignStockModelForIsdnForm.toIsdn').disabled = false;
            //$('assignStockModelForIsdnForm.fromPrice').disabled = false;
            //$('assignStockModelForIsdnForm.toPrice').disabled = false;
            $('btnSearch').disabled  = false;
            $('btnUpdate').disabled  = false;
            $('btnUpdateByFile').disabled  = true;


            //enable component phuc vu viec nhap so theo file
            disableImFileUpload('assignStockModelForIsdnForm.clientFileName', true);
        }
    }

    downloadPatternFile = function() {
        window.open('${contextPath}/share/pattern/assignStockModelForIsdnPattern.xls','','toolbar=yes,scrollbars=yes');
    }

    //update viec an hien cac vung, tuy thuoc vao kieu nhap du lieu
    var i = 1;
    for(i = 1; i < 3; i=i+1) {
        var radioId = "assignStockModelForIsdnForm.impType" + i;
        if($(radioId).checked == true) {
            radioClick(i);
            break;
        }

    }

</script>
