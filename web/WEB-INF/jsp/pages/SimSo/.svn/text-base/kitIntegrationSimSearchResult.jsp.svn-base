<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : kitIntegrationSimSearchResult
    Created on : Feb 18, 2009, 11:43:14 AM
    Author     : tuannv1
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
            request.setAttribute("simList", request.getSession().getAttribute("simList"));
%>

<!-- thong tin dai sim -->
<tags:imPanel title="MSG.info.range.sim">
    <!-- phan hien thi message -->
    <div>
        <tags:displayResult id="messageSim" property="message" type="key"/>
    </div>
    <div align="center" style="color:red; font-size:11px;">
        <s:if test="#request.errLogMessage != null">
            <s:text name="%{#request.errLogMessage}"/> <!-- hien thi message: neu he thong khogn tu dong download, tai tep log loi -->
            <a href="${contextPath}/${fn:escapeXml(errLogPath)}">
                tại đây
            </a>
            <script language="javascript">
                $('form.firstSimSerial').disabled = true;
                $('form.endSimSerial').disabled = true;
            </script>
        </s:if>
    </div>

    <s:form action="kitIntegrationAction!addSimRange.do" method="POST" id="simRangeForm" theme="simple">
<s:token/>

        <s:hidden name="form.simTotalQuantity" id="form.simTotalQuantity" />

        <table class="inputTbl4Col">
            <tr>
                <td><tags:label key="MSG.importType" required="true"/></td>
                <td colspan="3">

                     <s:radio name="form.impSimType"
                                 id="form.impSimType"
                                 list="#{'1':'By serial / IMEI', '2':'According to the file (<a onclick=downloadSimPatternFile()> download sample data file here </a> )'}"
                                 onchange="radioImpSimTypeClick(this.value)"/>
                     
                    <%--tags:imRadio name="form.impSimType"
                             id="form.impSimType"
                             list="1:MSG.GOD.220,2:MSG.ISN.010"
                             onchange="radioImpSimTypeClick(this.value)"/--%>
                </td>
            </tr>
            <tr>
                <td style="width:25%; "><tags:label key="MSG.range.serial" required="true"/></td>
                <td style="width:35%; ">
                    <s:textfield name="form.firstSimSerial" id="form.firstSimSerial" maxlength="20" cssClass="txtInputFull"/>
                </td>
                <td style="width:10px; ">-</td>
                <td>
                    <s:textfield name="form.endSimSerial" id="form.endSimSerial" maxlength="20" cssClass="txtInputFull"/>
                </td>
            </tr>
            <tr>
                <td> <tags:label key="MSG.generates.file_data" required="true"/> </td>
                <td colspan="3">
                    <tags:imFileUpload 
                        id="form.clientSimFileName"
                        name="form.clientSimFileName"
                        serverFileName="form.serverSimFileName" cssStyle="width:100%; " extension="txt"/>
                </td>
            </tr>
            <tr>
                <td align="center" colspan="4">

                    <tags:submit formId="simRangeForm"
                                 validateFunction="checkValidSimRange()"
                                 showLoadingText="true"
                                 cssStyle="width:80px;"
                                 targets="divListSim"
                                 value="MSG.add.range"
                                 preAction="kitIntegrationAction!addSimRange.do"/>
                </td>
            </tr>
        </table>
    </s:form>

    <!-- danh sach dai sim -->
    <div style="height:350px; overflow:auto; ">
        <!-- hien thi link dowload chi tiet so isdn -->
        <s:if test="#request.detailSimRangePath != null">
            <div>
                <a href="${contextPath}/${fn:escapeXml(detailSimRangePath)}">
                    <tags:displayResult id="detailSimRangeMessage" property="detailSimRangeMessage" propertyValue="detailSimRangeMessageParam" type="key"/>
                </a>
            </div>
        </s:if>
        <display:table name="simList" id="sim"
                       class="dataTable" cellpadding="1" cellspacing="1">
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.073'))}" sortable="false" headerClass="tct" style="text-align:center">
                ${fn:escapeXml(sim_rowNum)}
            </display:column>
            <display:column escapeXml="true"  property="firstSimSerial" title="${fn:escapeXml(imDef:imGetText('MSG.ISN.011'))}" sortable="false" headerClass="sortable"/>
            <display:column escapeXml="true"  property="endSimSerial" title="${fn:escapeXml(imDef:imGetText('MSG.ISN.012'))}" sortable="false" headerClass="sortable"/>
            <display:column escapeXml="true"  property="simQuantity" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.179'))}" sortable="false" headerClass="sortable"/>
            <%--display:column title="${fn:escapeXml(imDef:imGetText('MSG.RET.116'))}" sortable="false" headerClass="sortable" style="text-align:center">
                <sx:a afterNotifyTopics="/after" executeScripts="true" targets="divListSim" href="%{#attr.contextPath}/kitIntegrationAction!detailSimRange.do?formId=%{#attr.sim.formId}" beforeNotifyTopics="/before">
                    ${fn:escapeXml(imDef:imGetText('MSG.RET.116'))}
                </sx:a>
            </display:column--%>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.091'))}" sortable="false" headerClass="sortable" style="text-align:center">
                <s:a href="" onclick="delSimRange(%{#attr.sim.formId})">
                    ${fn:escapeXml(imDef:imGetText('MSG.GOD.091'))}
                </s:a>
            </display:column>
        </display:table>
    </div>

    <div style="font-family: Arial,Helvetica,sans-serif; font-size:15px;">
        ${fn:escapeXml(imDef:imGetText('MSG.ISN.013'))}: <s:property escapeJavaScript="true"  value="form.simTotalQuantity"/>
    </div>

</tags:imPanel>

<script language="javascript">

    //focus vao truong dau tien
    $('form.firstSimSerial').select();
    $('form.firstSimSerial').focus();


    radioImpSimTypeClick = function(impType) {
        if(impType == 2) {
            //theo file
            $('form.firstSimSerial').value = "";
            $('form.endSimSerial').value = "";
            $('form.firstSimSerial').disabled = true;
            $('form.endSimSerial').disabled = true;
            disableImFileUpload('form.clientSimFileName', false);
        } else {
            //theo dai
            $('form.firstSimSerial').disabled = false;
            $('form.endSimSerial').disabled = false;
            $('form.clientSimFileName').value = "";
            disableImFileUpload('form.clientSimFileName', true);
        }
    }

    //
    detailSimRange = function(formId) {
        // TuTM1 04/03/2012 Fix ATTT CSRF
        gotoAction("divListSim", "${contextPath}/kitIntegrationAction!detailSimRange.do?formId=" 
            + formId + "&" + token.getTokenParamString());
    }

    //
    delSimRange = function(formId) {
        if(confirm('Bạn chắc chắn muốn hủy dải số khỏi danh sách?')) {
            // TuTM1 04/03/2012 Fix ATTT CSRF
            gotoAction("divListSim", "${contextPath}/kitIntegrationAction!delSimRange.do?formId=" 
                + formId + "&" + token.getTokenParamString());
        }
    }


    //ham kiem tra cac truong bat buoc
    checkRequiredFieldsForSim = function() {
        var selectedIndex = 1;
        for(selectedIndex = 1; selectedIndex < 3; selectedIndex=selectedIndex+1) {
            var radioId = "form.impSimType" + selectedIndex;
            if($(radioId).checked == true) {
                break;
            }
        }
        if(selectedIndex == 1) {
            //nhap theo dai
            //truong serial dau dai
            if(trim($('form.firstSimSerial').value) == "") {
//                $('messageSim').innerHTML = "!!!Lỗi. Trường serial đầu dải không được để trống";
                $('messageIsdn').innerHTML = '<s:text name="ERR.ISN.118"/>';
                $('form.firstSimSerial').select();
                $('form.firstSimSerial').focus();
                return false;
            }
            //truong serial cuoi dai
            if(trim($('form.endSimSerial').value) == "") {
//                $('messageSim').innerHTML = "!!!Lỗi. Trường serial cuối dải không được để trống";
                $('messageIsdn').innerHTML = '<s:text name="ERR.ISN.119"/>';
                $('form.endSimSerial').select();
                $('form.endSimSerial').focus();
                return false;
            }
        } else {
            //nhap theo file
            if(trim($('form.clientSimFileName').value) == "") {
//                $('messageSim').innerHTML = "!!!Lỗi. Chọn file dữ liệu";
                $('messageIsdn').innerHTML = '<s:text name="ERR.STK.047"/>';
                $('form.clientSimFileName').select();
                $('form.clientSimFileName').focus();
                return false;
            }
        }

        return true;
    }

    //kiem tra tinh hop le cua cac truong
    checkValidFieldsForSim = function() {
        var selectedIndex = 1;
        for(selectedIndex = 1; selectedIndex < 3; selectedIndex=selectedIndex+1) {
            var radioId = "form.impSimType" + selectedIndex;
            if($(radioId).checked == true) {
                break;
            }
        }
        if(selectedIndex == 1) {
            //truong serial dau dai la so nguyen duong
            if(!isInteger(trim($('form.firstSimSerial').value))) {
//                $('messageSim').innerHTML = "!!!Lỗi. Serial đầu dải phải là số không âm";
                $('messageIsdn').innerHTML = '<s:text name="ERR.STK.032"/>';
                $('form.firstSimSerial').select();
                $('form.firstSimSerial').focus();
                return false;
            }
            //truong serail cuoi dai phai la so nguyen duong
            if(!isInteger(trim($('form.endSimSerial').value))) {
//                $('messageSim').innerHTML = "!!!Lỗi. Serial cuối dải phải là số không âm";
                $('messageIsdn').innerHTML = '<s:text name="ERR.STK.033"/>';
                $('form.endSimSerial').select();
                $('form.endSimSerial').focus();
                return false;
            }
            //so dau dai va so cuoi dai phai co chieu dai = nhau
            var startSerial = trim($('form.firstSimSerial').value);
            var endSerial = trim($('form.endSimSerial').value);
            if(startSerial.length != endSerial.length) {
//                $('messageSim').innerHTML = "!!!Lỗi. Serial đầu dải và số cuối dải phải có chiều dài bằng nhau";
                $('messageIsdn').innerHTML = '<s:text name="ERR.ISN.120"/>';
                $('form.firstSimSerial').select();
                $('form.firstSimSerial').focus();
                return false;
            }
            //so dau dai khong duoc lon hon so cuoi dai
            if(startSerial > endSerial) {
//                $('messageSim').innerHTML = "!!!Lỗi. Serial đầu dải không được lớn hơn serial cuối dải";
                $('messageIsdn').innerHTML = '<s:text name="ERR.STK.034"/>';
                $('form.firstSimSerial').select();
                $('form.firstSimSerial').focus();
                return false;
            }
        }
        

        return true;
    }

    checkValidSimRange = function() {
        if(checkRequiredFieldsForSim() && checkValidFieldsForSim()) {
            return true;
        } else {
            return false;
        }
    }

    //
    //update viec an hien cac vung, tuy thuoc vao kieu nhap
    var selectedSimIndex = 1;
    for(selectedSimIndex = 1; selectedSimIndex < 3; selectedSimIndex=selectedSimIndex+1) {
        var radioId = "form.impSimType" + selectedSimIndex;
        if($(radioId).checked == true) {
            radioImpSimTypeClick(selectedSimIndex);
            break;
        }
    }
    dojo.event.topic.subscribe("/after", function(event, widget){
        resetProgress();

    });
    dojo.event.topic.subscribe("/before", function(event, widget){
        initProgress();

    });
     downloadSimPatternFile = function() {
        window.open('${contextPath}/share/pattern/kitIntegrationSimPattern.xls','','toolbar=yes,scrollbars=yes');
    }


</script>


