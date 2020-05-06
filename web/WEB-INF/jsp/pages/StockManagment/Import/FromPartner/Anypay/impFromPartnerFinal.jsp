<%-- 
    Document   : expToPartnerFinal
    Created on : Sep 3, 2010, 3:37:19 AM
    Author     : Doan Thanh 8
    Desc       : nhap serial va xuat kho cho doi tac
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="display" uri="VTdisplaytaglib" %>
<%@taglib prefix="imDef" uri="imDef" %>
<%@page import="com.guhesan.querycrypt.QueryCrypt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
    request.setAttribute("contextPath", request.getContextPath());
    String pageId = request.getParameter("pageId");
    request.setAttribute("listGoods", request.getSession().getAttribute("lstGoods" + pageId));
    request.setAttribute("listReason", request.getSession().getAttribute("listReason" + pageId));
%>
<s:if test="#request.showDetail == 1">
<sx:div>
    <!-- phan hien thi thong tin message -->
    <div>
        <tags:displayResult property="message" id="message" propertyValue="messageParam" type="key"/>
        <tags:displayResultList property="lstError" type="key"/>
    </div>

    <s:form action="anypayPartnerAction" theme="simple" enctype="multipart/form-data"  method="POST" id="importStockForm">
        <!-- phan hien thi thong tin lenh xuat kho -->
        <div>
            <table style="width:100%" cellspacing="0" cellpadding="0">
                <tr>
                    <td style="width:40%; vertical-align:top">
                        <fieldset class="imFieldset">
                            <legend>${imDef:imGetText('MSG.info.import.note')}</legend>
                            <div style="width:100%; height:300px; overflow:auto;">
                                <s:hidden name="importStockForm.status" id=".status"/>
                                <s:hidden name="importStockForm.actionId" id="importStockForm.actionId"/>
                                <s:hidden name="importStockForm.shopExportId" id="importStockForm.shopExportId"/>

                                <table class="inputTbl2Col">
                                    <tr>
                                        <!-- ma phieu nhap -->
                                        <td><tags:label key="MSG.GOD.132"/></td>
                                        <td>
                                            <s:textfield theme="simple" maxlength="20" name="importStockForm.cmdImportCode" id="importStockForm.cmdImportCode" cssClass="txtInputFull" readonly="true"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <!-- nguoi nhap -->
                                        <td><tags:label key="MSG.importStaff"/></td>
                                        <td>
                                            <s:textfield theme="simple" name="importStockForm.sender" id="importStockForm.sender" cssClass="txtInputFull" readonly="true"  />
                                        </td>

                                    </tr>
                                    <tr>
                                        <!-- ngay nhap -->
                                        <td><tags:label key="MSG.importDate"/></td>
                                        <td>
                                            <tags:dateChooser readOnly="true" property="importStockForm.dateImported" id="importStockForm.dateImported" styleClass="txtInputFull" />
                                        </td>
                                    </tr>
                                    <tr>
                                        <!-- kho nhap hang -->
                                        <td><tags:label key="MSG.importStore"/></td>
                                        <td>
                                            <tags:imSearch codeVariable="importStockForm.shopImportCode" nameVariable="importStockForm.shopImportName"
                                                           codeLabel="MSG.RET.066" nameLabel="MSG.RET.067"
                                                           searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                                           searchMethodName="getListShop"
                                                           getNameMethod="getNameShop"
                                                           readOnly="true"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <!-- doi tac xuat hang -->
                                        <td><tags:label key="MSG.partner"/></td>
                                        <td>
                                            <tags:imSearch codeVariable="importStockForm.shopExportCode" nameVariable="importStockForm.shopExportName"
                                                           codeLabel="MSG.partner.code" nameLabel="MSG.partner.name"
                                                           searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                                           searchMethodName="getListPartner"
                                                           getNameMethod="getPartnerName"
                                                           readOnly="true"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <!-- ly do nhap -->
                                        <td><tags:label key="MSG.reasonImport"/></td>
                                        <td>
                                            <tags:imSelect name="importStockForm.reasonId"
                                                           id="importStockForm.reasonId"
                                                           cssClass="txtInputFull"
                                                           theme="simple"
                                                           headerKey="" headerValue="MSG.GOD.147"
                                                           list="listReason"
                                                           listKey="reasonId" listValue="reasonName"
                                                           disabled="true"/>
                                            <%--<s:hidden name="importStockForm.reasonId"/>--%>
                                        </td>
                                    </tr>
                                    <tr>
                                        <!-- ghi chu -->
                                        <td><tags:label key="MSG.GOD.031"/></td>
                                        <td>
                                            <s:textfield theme="simple" name="importStockForm.note" id="importStockForm.note" maxlength="500" cssClass="txtInputFull" readonly="true"/>
                                        </td>
                                    </tr>
                                </table>

                            </div>
                        </fieldset>
                    </td>
                    <td style="width: 5px;"></td>
                    <td style="vertical-align:top">
                        <fieldset class="imFieldset">
                            <legend>${imDef:imGetText('MSG.detail.goods')}</legend>
                            <div style="width:100%; height:300px; overflow:auto;">
                                <display:table id="tblListGoods" name="listGoods" class="dataTable" requestURI="javascript: void(0)" cellpadding="1" cellspacing="1">
                                    <display:column title="${imDef:imGetText('MSG.GOD.073')}" sortable="false" headerClass="tct" style="text-align:center;">
                                        <s:property value="%{#attr.tblListGoods_rowNum}"/>
                                    </display:column>
                                    <display:column escapeXml="true" title="${imDef:imGetText('MSG.GOD.007')}" property="stockModelCode" sortable="false" headerClass="tct"/>
                                    <display:column escapeXml="true" title="${imDef:imGetText('MSG.GOD.008')}" property="stockModelName" sortable="false" headerClass="tct"/>
                                    <display:column title="${imDef:imGetText('MSG.GOD.331')}" sortable="false" headerClass="tct">
                                        <s:if test="#attr.tblListGoods.stateId ==1"><tags:label key="MSG.GOD.169"/></s:if>
                                        <s:elseif test="#attr.tblListGoods.stateId ==3"><tags:label key="MSG.GOD.170"/></s:elseif>
                                    </display:column>
                                    <display:column title="${imDef:imGetText('MSG.GOD.179')}" property="quantity" format="{0}"  style="text-align:right" sortable="false" headerClass="tct"/>
                                </display:table>
                            </div>
                        </fieldset>
                    </td>
                </tr>
            </table>
        </div>

        <!-- nhap kho -->
        <div style="width:100%; margin-top: 5px; " align="center">
           <s:if test="#request.isPrintMode == 'true'">
            <input type="button" style="width: 100px;" value="${fn:escapeXml(imDef:imGetText('MSG.importToStore'))}" disabled/>
            </s:if>
            <s:else>
                <tags:submit id="imp"
                 confirm="true"
                 confirmText="MSG.confirm.import.store"
                 formId="importStockForm"
                 showLoadingText="true"
                 cssStyle="width:80px; "
                 targets="divImpNoteFromPartner"
                 value="import.stock"
                 preAction="anypayPartnerAction!impFromPartner.do"/>
            </s:else> 
            

        </div>

    </s:form>

</sx:div>
</s:if>

<script>
    updateSerial=function() {
        gotoAction("divExpNoteToPartner", "anypayPartnerAction!refreshListGoods.do?actionId=${fn:escapeXml(importStockForm.actionId)}");
    }

</script>