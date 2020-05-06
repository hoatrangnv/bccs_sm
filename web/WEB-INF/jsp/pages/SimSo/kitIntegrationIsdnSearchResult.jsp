<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : kitIntegrationIsdnSearchResult
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
            request.setAttribute("numberList", request.getSession().getAttribute("numberList"));
%>



<!-- thong tin dai so -->
<tags:imPanel title="MSG.info.range.isdn">
    <!-- phan hien thi message -->
    <div>
        <tags:displayResult id="messageIsdn" property="message" type="key"/>
    </div>
    <div align="center" style="color:red; font-size:11px;">
        <s:if test="#request.errLogMessage != null">
            <s:text name="%{#request.errLogMessage}" /> <!-- hien thi message: neu he thong khogn tu dong download, tai tep log loi -->
            <a href="${contextPath}/${fn:escapeXml(errLogPath)}">
                tại đây
            </a>
            <script>
                $('form.startStockIsdn').disabled = true;
                $('form.endStockIsdn').disabled = true;
            </script>

        </s:if>
    </div>

    <s:form action="kitIntegrationAction!addIsdnRange.do" method="POST" id="isdnRangeForm" theme="simple">
<s:token/>

        <s:hidden name="form.isdnTotalQuantity" id="form.isdnTotalQuantity" />
        <table class="inputTbl4Col">
            <tr>
                <td><tags:label key="MSG.importType" required="true"/></td>
                <td colspan="3">

                     <s:radio name="form.impIsdnType"
                                 id="form.impIsdnType"
                                 list="#{'1':'By serial / IMEI', '2':'According to the file (<a onclick=downloadSimPatternFile()> download sample data file here </a> )'}"
                                 onchange="radioImpIsdnTypeClick(this.value)"/>
                     
                    <%--tags:imRadio name="form.impIsdnType"
                             id="form.impIsdnType"
                             list="1:MSG.GOD.220,2:MSG.ISN.010"
                             onchange="radioImpIsdnTypeClick(this.value)"/--%>
                </td>
            </tr>
            <tr>
                <td style="width:25%; "><tags:label key="MSG.isdn.area" required="true"/></td>
                <td style="width:35%; ">
                    <s:textfield name="form.startStockIsdn" id="form.startStockIsdn" maxlength="12" cssClass="txtInputFull"/>
                </td>
                <td style="width:10px; ">-</td>
                <td>
                    <s:textfield name="form.endStockIsdn" id="form.endStockIsdn" maxlength="12" cssClass="txtInputFull"/>
                </td>
            </tr>
            <tr>
                <td><tags:label key="MSG.generates.file_data" required="true"/></td>
                <td colspan="3">
                    <tags:imFileUpload 
                        id="form.clientIsdnFileName"
                        name="form.clientIsdnFileName"
                        serverFileName="form.serverIsdnFileName" cssStyle="width:100%; " extension="txt"/>
                </td>
            </tr>
            <tr>
                <td align="center" colspan="4">
 
                    <tags:submit formId="isdnRangeForm"
                                 validateFunction="checkValidIsdnRange()"
                                 showLoadingText="true"
                                 cssStyle="width:80px;"
                                 targets="divListIsdn"
                                 value="MSG.add.range"
                                 preAction="kitIntegrationAction!addIsdnRange.do"/>
                </td>
            </tr>
        </table>
    </s:form>

    <!-- danh sach dai so -->
    <div style="height:350px; overflow:auto; ">
        <!-- hien thi link dowload chi tiet so isdn -->

        <s:if test="#request.detailIsdnRangePath != null">
            <div>
                <a href="${contextPath}/${fn:escapeXml(detailIsdnRangePath)}">
                    <tags:displayResult id="detailIsdnRangeMessage" property="detailIsdnRangeMessage" propertyValue="detailIsdnRangeMessageParam" type="key"/>
                </a>
            </div>
        </s:if>

        <display:table name="numberList" id="number"
                       class="dataTable" cellpadding="1" cellspacing="1">
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.073'))}" sortable="false" style="text-align:center" headerClass="tct">
                ${fn:escapeXml(number_rowNum)}
            </display:column>
            <display:column escapeXml="true"  property="startStockIsdn" title="${fn:escapeXml(imDef:imGetText('MSG.ISN.011'))}" sortable="false" headerClass="sortable"/>
            <display:column escapeXml="true"  property="endStockIsdn" title="${fn:escapeXml(imDef:imGetText('MSG.ISN.012'))}" sortable="false" headerClass="sortable"/>
            <display:column escapeXml="true"  property="isdnQuantity" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.179'))}" sortable="false" headerClass="sortable"/>
            <%--display:column title="${fn:escapeXml(imDef:imGetText('MSG.RET.116'))}" sortable="false" headerClass="sortable" style="text-align:center">
                <sx:a afterNotifyTopics="/after" executeScripts="true" targets="divListIsdn" href="%{#attr.contextPath}/kitIntegrationAction!detailIsdnRange.do?formId=%{#attr.number.formId}" beforeNotifyTopics="/before">
                    ${fn:escapeXml(imDef:imGetText('MSG.RET.116'))}
                </sx:a>
            </display:column--%>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.091'))}" sortable="false" headerClass="sortable" style="text-align:center">
                <s:a href="" onclick="delIsdnRange(%{#attr.number.formId})">
                    ${fn:escapeXml(imDef:imGetText('MSG.GOD.091'))}
                </s:a>
            </display:column>
        </display:table>
    </div>

    <div style="font-family: Arial,Helvetica,sans-serif; font-size:15px;">
        ${fn:escapeXml(imDef:imGetText('MSG.ISN.014'))}: <s:property escapeJavaScript="true"  value="form.isdnTotalQuantity"/>
    </div>
</tags:imPanel>

<script language="javascript">

        checkValidIsdnRange = function() {
            if(checkRequiredFieldsForIsdn() && checkValidFieldsForIsdn()) {
                return true;
            } else {
                return false;
            }
        }



        radioImpIsdnTypeClick = function(impType) {
            if(impType == 2) {
                //theo file
                $('form.startStockIsdn').value = "";
                $('form.endStockIsdn').value = "";
                $('form.startStockIsdn').disabled = true;
                $('form.endStockIsdn').disabled = true;
                disableImFileUpload('form.clientIsdnFileName', false);
            } else {
                //theo dai
                $('form.startStockIsdn').disabled = false;
                $('form.endStockIsdn').disabled = false;
                $('form.clientIsdnFileName').value = "";
                disableImFileUpload('form.clientIsdnFileName', true);
            }
        }

        detailIsdnRange = function(formId) {
            // TuTM1 04/03/2012 Fix ATTT CSRF
            gotoAction("divListIsdn", "${contextPath}/kitIntegrationAction!detailIsdnRange.do?formId=" 
                + formId + "&" + token.getTokenParamString());


        }

        //
        delIsdnRange = function(formId) {
            if(confirm('Bạn chắc chắn muốn hủy dải số khỏi danh sách?')) {
                // TuTM1 04/03/2012 Fix ATTT CSRF
                gotoAction("divListIsdn", "${contextPath}/kitIntegrationAction!delIsdnRange.do?formId=" 
                    + formId + "&" + token.getTokenParamString());
            }
        }

        //ham kiem tra cac truong bat buoc
        checkRequiredFieldsForIsdn = function() {
            var selectedIndex = 1;
            for(selectedIndex = 1; selectedIndex < 3; selectedIndex=selectedIndex+1) {
                var radioId = "form.impIsdnType" + selectedIndex;
                if($(radioId).checked == true) {
                    break;
                }
            }
            if(selectedIndex == 1) {
                //nhap theo dai
                //truong so dau dai
                if(trim($('form.startStockIsdn').value) == "") {
                    $('messageIsdn').innerHTML = '<s:text name="ERR.ISN.003"/>';
                    $('form.startStockIsdn').select();
                    $('form.startStockIsdn').focus();
                    return false;
                }
                //truong so cuoi dai
                if(trim($('form.endStockIsdn').value) == "") {
                    $('messageIsdn').innerHTML = '<s:text name="ERR.ISN.004"/>';
                    $('form.endStockIsdn').select();
                    $('form.endStockIsdn').focus();
                    return false;
                }
            } else {
                //nhap theo file
                if(trim($('form.clientIsdnFileName').value) == "") {
//                    $('messageIsdn').innerHTML = "!!!Lỗi. Chọn file dữ liệu";
                    $('messageIsdn').innerHTML = '<s:text name="ERR.ISN.027"/>';
                    $('form.clientIsdnFileName').select();
                    $('form.clientIsdnFileName').focus();
                    return false;
                }
            }

            return true;
        }

        //kiem tra tinh hop le cua cac truong
        checkValidFieldsForIsdn = function() {
            var selectedIndex = 1;
            for(selectedIndex = 1; selectedIndex < 3; selectedIndex=selectedIndex+1) {
                var radioId = "form.impIsdnType" + selectedIndex;
                if($(radioId).checked == true) {
                    break;
                }
            }
            if(selectedIndex == 1) {
                //truong so dau dai la so nguyen duong
                if(!isInteger(trim($('form.startStockIsdn').value))) {
//                    $('messageIsdn').innerHTML = "!!!Lỗi. Số đầu dải phải là số không âm";
                    $('messageIsdn').innerHTML = '<s:text name="ERR.ISN.006"/>';
                    $('form.startStockIsdn').select();
                    $('form.startStockIsdn').focus();
                    return false;
                }
                //truong so cuoi dai phai la so nguyen duong
                if(!isInteger(trim($('form.endStockIsdn').value))) {
//                    $('messageIsdn').innerHTML = "!!!Lỗi. Số cuối dải phải là số không âm";
                    $('messageIsdn').innerHTML = '<s:text name="ERR.ISN.007"/>';
                    $('form.endStockIsdn').select();
                    $('form.endStockIsdn').focus();
                    return false;
                }
                //so dau dai va so cuoi dai phai co chieu dai = nhau
                var startIsdn = trim($('form.startStockIsdn').value);
                var endIsdn = trim($('form.endStockIsdn').value);
                if(startIsdn.length != endIsdn.length) {
//                    $('messageIsdn').innerHTML = "!!!Lỗi. Số đầu dải và số cuối dải phải có chiều dài bằng nhau";
                    $('messageIsdn').innerHTML = '<s:text name="ERR.ISN.009"/>';
                    $('form.startStockIsdn').select();
                    $('form.startStockIsdn').focus();
                    return false;
                }
                //so dau dai khong duoc lon hon so cuoi dai
                if(startIsdn > endIsdn) {
//                    $('messageIsdn').innerHTML = "!!!Lỗi. Số đầu dải không được lớn hơn số cuối dải";
                    $('messageIsdn').innerHTML = '<s:text name="ERR.ISN.010"/>';
                    $('form.startStockIsdn').select();
                    $('form.startStockIsdn').focus();
                    return false;
                }
            }
        

            return true;
        }

        //alert('ukie');

        //update viec an hien cac vung, tuy thuoc vao kieu nhap
        var selectedIsdnIndex = 1;
        for(selectedIsdnIndex = 1; selectedIsdnIndex < 3; selectedIsdnIndex=selectedIsdnIndex+1) {
            var radioId = "form.impIsdnType" + selectedIsdnIndex;
            if($(radioId).checked == true) {
                radioImpIsdnTypeClick(selectedIsdnIndex);
                break;
            }
        }
        dojo.event.topic.subscribe("/after", function(event, widget){
            resetProgress();

        });
        dojo.event.topic.subscribe("/before", function(event, widget){
            initProgress();

        });
        downloadIsdnPatternFile = function() {
            window.open('${contextPath}/share/pattern/kitIntegrationIsdnPattern.xls','','toolbar=yes,scrollbars=yes');
        }

                   
          


</script>

<%--onclick="detailIsdnRange(%{#attr.number.formId})"--%>
