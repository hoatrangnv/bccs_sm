<%-- 
    Document   : MappingDslamSaleService.jsp
    Created on : Aug 11, 2014, 4:17:38 PM
    Author     : TruongNQ5
--%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.*"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@ taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib prefix="imDef" uri="imDef" %>
<%
    request.setAttribute("contextPath", request.getContextPath());
//    request.setAttribute("resultFile", request.getSession().getAttribute("resultFile"));
%>
<div id="mappingDslamSaleServiceId">
    <s:form action="" theme="simple" method="post" id="MappingDslamSaleServiceForm">
        <tags:imPanel title="title.MappingDslamSaleService.page">
            <div class="divHasBorder">
                <table class="mappingType" style="width:100%" >
                    <tr>
                        <td colspan="6" style="width:100%">
                            <tags:imRadio name="MappingDslamSaleServiceForm.mappingType"
                                          id="MappingDslamSaleServiceForm.mappingType"
                                          list="1:MSG.imByFile,2:MSG.impBySequence"
                                          onchange="radioClick(this.value)"/>
                        </td>
                    </tr>
                    <tr id="trMappingBySequence">
                        <td style="width:10%"><tags:label key="MSG.Dslam" required="true"/></td>
                        <td style="width:30%">
                            <tags:imSearch codeVariable="MappingDslamSaleServiceForm.dslamCode" nameVariable="MappingDslamSaleServiceForm.dslamName"
                                           codeLabel="MSG.code" nameLabel="MSG.name"
                                           searchClassName="com.viettel.im.database.DAO.MappingDslamSaleServiceDAO"
                                           searchMethodName="getListDslam"/>
                        </td>
                        <td style="width:10%"><tags:label key="MSG.SaleService" required="true"/></td>
                        <td style="width:30%">
                            <tags:imSearch codeVariable="MappingDslamSaleServiceForm.saleServiceCode" nameVariable="MappingDslamSaleServiceForm.saleServiceName"
                                           codeLabel="MSG.code" nameLabel="MSG.name"
                                           searchClassName="com.viettel.im.database.DAO.MappingDslamSaleServiceDAO"
                                           searchMethodName="getListSaleService"/>
                        </td>
                        <td style="width:10%"><tags:label key="MSG.status" required="true"/></td>
                        <td style="width:10%">
                            <tags:imSelect name="MappingDslamSaleServiceForm.status"
                                           id="status" 
                                           cssClass="txtInputFull" headerValue="MSG.GOD.189"
                                           list="1:MSG.active, 0:MSG.GOD.284"/>
                        </td>
                    </tr>
                    <tr id="trMappingByFile">
                        <td style="width: 10%"><tags:label key="MSG.generates.file_data" required="true"/></td>
                        <td style="">
                            <tags:imFileUpload
                                name="MappingDslamSaleServiceForm.clientFileName"
                                id="MappingDslamSaleServiceForm.clientFileName"
                                serverFileName="MappingDslamSaleServiceForm.serverFileName" cssStyle="width:650px;" extension="xls;txt"/>
                        </td>
                        <td align="left" style="" >
                            <a href="${contextPath}/share/pattern/mapping_dslam_temp.xls">Download template</a>
                        </td>
                    </tr>
                </table>
                <div align="center" style="padding-top:5px; padding-bottom:5px;">
                    <tags:submit formId="MappingDslamSaleServiceForm"
                                 showLoadingText="true"
                                 cssStyle="width:80px;"
                                 targets="KetQuaVaNut"
                                 value="MSG.find"
                                 preAction="mappingDslamSaleServiceAction!search.do"/>
                    <tags:submit formId="MappingDslamSaleServiceForm"
                                 showLoadingText="true"
                                 cssStyle="width:80px;"
                                 targets="KetQuaVaNut"
                                 value="MSG.GOD.010"
                                 validateFunction="checkValidFields()"
                                 preAction="mappingDslamSaleServiceAction!add.do"/>
                    <tags:submit formId="MappingDslamSaleServiceForm"
                                 showLoadingText="true"
                                 cssStyle="width:80px;"
                                 targets="KetQuaVaNut"
                                 value="MSG.Export.Excel"
                                 preAction="mappingDslamSaleServiceAction!exportExcel.do"/>
                </div>

            </div>
            <div id="KetQuaVaNut">
                <jsp:include page="listMappingDslamSaleService.jsp"/>
            </div>
        </tags:imPanel>
    </s:form>
</div>
<script type="text/javascript">
    $('MappingDslamSaleServiceForm.dslamCode').select();
    $('MappingDslamSaleServiceForm.dslamCode').focus();
    $('trMappingByFile').style.display = 'none';
    $('trMappingBySequence').style.display = '';
//xu ly su kien khi thay doi kieu nhap hang
    radioClick = function(value) {
        if (value == 2) {
            $('trMappingBySequence').style.display = '';
            $('trMappingByFile').style.display = 'none';
            $('MappingDslamSaleServiceForm.clientFileName').value = null;
            $('messageList').innerHTML = '';
        } else if (value == 1) {
            $('trMappingByFile').style.display = '';
            $('trMappingBySequence').style.display = 'none';
            $('MappingDslamSaleServiceForm.dslamCode').value = "";
            $('MappingDslamSaleServiceForm.dslamName').value = "";
            $('MappingDslamSaleServiceForm.saleServiceCode').value = "";
            $('MappingDslamSaleServiceForm.saleServiceName').value = "";
            $('status').value = null;
            $('messageList').innerHTML = '';
        }
    }
    checkValidFields = function() {
        var mappingType = document.getElementsByName('MappingDslamSaleServiceForm.mappingType');
        var clientFileName = document.getElementById("MappingDslamSaleServiceForm.clientFileName");

        if (mappingType[0].checked) {
            var dslamCode = document.getElementById("MappingDslamSaleServiceForm.dslamCode");
            var saleServiceCode = document.getElementById("MappingDslamSaleServiceForm.saleServiceCode");
            var status = document.getElementById("status");
            if (dslamCode.value == null || dslamCode.value == "") {
                $('messageList').innerHTML = '<s:property value="getText('MSG.GOD.287')"/>';
                $('MappingDslamSaleServiceForm.dslamCode').focus();
                return false;
            }
            if (saleServiceCode.value == null || saleServiceCode.value == "") {
                $('messageList').innerHTML = '<s:property value="getText('MSG.GOD.287')"/>';
                $('MappingDslamSaleServiceForm.saleServiceCode').focus();
                return false;
            }
            if (status.value == null) {
                $('messageList').innerHTML = '<s:property value="getText('ERR.INF.063')"/>';
                $('status').focus();
                return false;
            }
            return true;
        } else {
            if (trim(clientFileName.value).length == 0) {
                $('messageList').innerHTML = '<s:property value="getText('MSG.INFRA.004')"/>';
                clientFileName.focus();
                return false;
            }
            return true;
        }
    }
</script>
