<%-- 
    Document   : expImpNoteToPartner
    Created on : Jun 26, 2014, 3:41:31 PM
    Author     : thuannx1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
            request.setAttribute("contextPath", request.getContextPath());

%>
<tags:imPanel title="MSG.GOD.126">
    <s:form action="" theme="simple" method="post" id="importStockFormChild">
        <s:token/>
        <s:hidden name="importStockFormChild.actionId" />
        <div class="divHasBorder">
            <table class="inputTbl6Col">
                <tr>
                    <td > <tags:label key="MSG.toStore" required="true"/></td>
                    <td class="oddColumn" style="width: 25%">
                        <table>
                            <tr>
                                <td style="width: 30%">
                                    <s:textfield name="importStockFormChild.toOwnerCode" maxLength="25" readOnly="true" id="importStockFormChild.toOwnerCode" cssClass="txtInputFull" readonly="true"/>
                                </td>
                                <td style="width: 70%">
                                    <s:textfield name="importStockFormChild.toOwnerName" maxLength="25" readOnly="true" id="importStockFormChild.toOwnerName" cssClass="txtInputFull" readonly="true"/>
                                </td>
                            </tr>
                        </table>
                    </td>
                    <td > <tags:label key="MSG.GOD.127" required="true"/></td>
                    <td class="oddColumn">
                        <s:textfield name="importStockFormChild.actionCode" maxLength="25" readOnly="true" id="actionCode" cssClass="txtInputFull"/>
                    </td>
                    <td><tags:label key="MSG.importDate" required="true"/></td>
                    <td>
                        <tags:dateChooser property="importStockFormChild.importDate" id="importStockFormChild.importDate" styleClass="txtInputFull" readOnly="true"/>
                    </td>
                </tr>
                <tr>
                    <td><tags:label key="MSG.partner" required="true"/></td>
                    <td class="oddColumn">
                        <table>
                            <tr>
                                <td style="width: 30%">
                                    <s:textfield name="importStockFormChild.fromOwnerCode" maxLength="25" readOnly="true" id="importStockFormChild.fromOwnerCode" cssClass="txtInputFull" readonly="true"/>
                                </td>
                                <td style="width: 70%">
                                    <s:textfield name="importStockFormChild.fromOwnerName" maxLength="25" readOnly="true" id="importStockFormChild.fromOwnerName" cssClass="txtInputFull" readonly="true"/>
                                </td>
                            </tr>
                        </table>
                    </td>
                    <td><tags:label key="MSG.reasonImport" required="true"/></td>
                    <td class="oddColumn">
                        <s:textfield theme="simple"  name="importStockFormChild.reasonName" maxlength="10" id="importStockFormChild.reasonName" cssClass="txtInputFull" readonly="true"/>
                    </td>
                    <td><tags:label key="MSG.comment" required="false"/></td>
                    <td class="oddColumn">
                        <s:textfield theme="simple"  name="importStockFormChild.note" maxlength="500" id="importStockFormChild.note" cssClass="txtInputFull" readonly="true"/>
                    </td>
                </tr>
                <tr>
                    <td><tags:label key="MSG.stock.stock.type" required="true"/></td>
                    <td class="oddColumn">
                        <s:textfield name="importStockFormChild.stockTypeName" maxLength="25" readOnly="true" id="importStockFormChild.stockTypeName" cssClass="txtInputFull" readonly="true"/>
                    </td>
                    <td><tags:label key="MSG.quanlity" required="true"/></td>
                    <td class="oddColumn">
                        <s:textfield theme="simple"  name="importStockFormChild.quantity" maxlength="10" id="importStockFormChild.quantity" cssClass="txtInputFull" readonly="true"/>
                    </td>
                    <td><tags:label key="MSG.import.bill.code" required="true"/></td>
                    <td class="oddColumn">
                        <s:textfield theme="simple"  name="importStockFormChild.noteImpCode" id="importStockFormChild.noteImpCode" cssClass="txtInputFull" readonly="true"/>
                    </td>
                </tr>
                <tr>
                    <td><tags:label key="MSG.generates.goods" required="true"/></td>
                    <td class="oddColumn">
                        <table>
                            <tr>
                                <td style="width: 30%">
                                    <s:textfield name="importStockFormChild.stockModelCode" maxLength="25" readOnly="true" id="importStockFormChild.stockModelCode" cssClass="txtInputFull" readonly="true"/>
                                </td>
                                <td style="width: 70%">
                                    <s:textfield name="importStockFormChild.stockModelName" maxLength="25" readOnly="true" id="importStockFormChild.stockModelName" cssClass="txtInputFull" readonly="true"/>
                                </td>
                            </tr>
                        </table>
                    </td>
                    <td><tags:label key="MSG.stateId" required="true"/></td>
                    <td class="oddColumn">
                        <tags:imSelect name="importStockFormChild.stateId"
                                       id="stateId" disabled="true"
                                       cssClass="txtInputFull"
                                       list="1:MSG.GOD.new, 3:MSG.GOD.error"/>
                    </td>
                </tr>
            </table>
        </div>
        <s:if test="#session.createImpNoteSuccess == 1">
            <input type="button" value="<s:text name = "MSG.createExportNote"/>" style="width:80px;" disabled/>
            <input type="button" value="<s:text name = "MSG.GOD.201"/>" style="width:80px;" disabled/>
        </s:if>
        <s:if test="#session.createImpNoteSuccess == 2">
            <tags:submit id="exp" confirm="true" confirmText="MSG.confirm.create.imp.note" formId="importStockFormChild" showLoadingText="true" targets="KetQuaVaNut"
                         value="MSG.createImportNote" validateFunction="validateForm()"
                         preAction="CreateImpNoteStockFromPartnerAction!createDeliverNote.do"/>
            <input type="button" value="<s:text name = "MSG.GOD.209"/>" style="width:80px;" disabled/>
        </s:if>
        <s:if test="#session.createImpNoteSuccess == 3">
            <input type="button" value="<s:text name = "MSG.createImportNote"/>" style="width:80px;" disabled/>
            <tags:submit id="btnPrintExpNote"
                         formId="importStockFormChild"
                         cssStyle="width: 100px;"
                         showLoadingText="true"
                         targets="notePrintPath"
                         value="MSG.GOD.209"
                         preAction="CreateImpNoteStockFromPartnerAction!printExpNote.do"/>
        </s:if>
    </s:form>
</tags:imPanel>
<script type="text/javascript">
    validateForm = function(){
        return true;
    }
</script>