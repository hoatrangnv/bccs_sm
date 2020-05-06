<%-- 
    Document   : ImpStockFromPartner
    Created on : Jun 27, 2014, 1:28:34 PM
    Author     : thuannx1
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

<tags:imPanel title="title.ImpStockFromPartner.page">
    
    <!-- phan tim kiem lenh xuat de tao phieu xuat -->
    <div class="divHasBorder">
        <s:form action="" theme="simple" enctype="multipart/form-data"  method="POST" id="importStockForm">
            <s:token/>
            <s:hidden name="importStockForm.actionType" />
            <s:hidden name="importStockForm.transType" />
            <s:hidden name="importStockForm.toOwnerId" id="toOwnerId"/>
            <s:hidden name="importStockForm.toOwnerType" id="toOwnerType"/>
            <s:hidden name="importStockForm.fromOwnerType" id="fromOwnerType"/>

            <table class="inputTbl6Col" >
                <tr>
                    <!-- ma giao dich -->
                    <td><tags:label key="MSG.GOD.183"/></td>
                    <td class="oddColumn">
                        <s:textfield name="importStockForm.actionCode" id="importStockForm.actionCode" cssClass="txtInputFull"/>
                    </td>
                    <!-- trang thai giao dich -->
                    <td><tags:label key="MSG.GOD.013"/></td>
                    <td class="oddColumn">
                        <tags:imSelect name="importStockForm.transStatus"
                                       id="transStatus"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.GOD.189"
                                       list="1:MSG.GOD.190, 2:MSG.GOD.191,3:status.created.imported,5:MSG.GOD.196"/>
                    </td>
                    <td></td>
                    <td></td>
                    <s:hidden name="importStockForm.fromOwnerCode"/>
                    <s:hidden name="importStockForm.fromOwnerName"/>
                </tr>
                <tr>
                    <!-- doi tac xuat hang -->
                    <td><tags:label key="MSG.partner"/></td>
                    <td class="oddColumn">
                        <tags:imSelect name="importStockForm.partnerId"
                                       id="partnerId"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.GOD.215"
                                       list="listPartners"
                                       listKey="partnerId" listValue="partnerName"/>
                    </td>
                    <!-- tu ngay -->
                    <td><tags:label key="MSG.GOD.117"/></td>
                    <td class="oddColumn">
                        <tags:dateChooser property="importStockForm.fromDate" id="importStockForm.fromDate" styleClass="txtInputFull"/>
                    </td>
                    <td><tags:label key="MSG.GOD.118"/></td>
                    <td>
                        <tags:dateChooser property="importStockForm.toDate" id="importStockForm.toDate" styleClass="txtInputFull"/>
                    </td>
                </tr>
            </table>

            <div style="width:100%; margin-top: 5px; margin-bottom: 5px;" align="center">
                <tags:submit formId="importStockForm"
                             showLoadingText="true"
                             targets="divImpNoteToPartner"
                             cssStyle="width: 80px;"
                             value="MSG.GOD.009"
                             preAction="ImpStockFromPartner!searchImpNoteToPartner.do"/>
            </div>

        </s:form>
    </div>

    <!-- phan hien thi ket qua tim kiem + lap phieu xuat kho cho doi tac -->
    <sx:div id="divImpNoteToPartner">
        <jsp:include page="listImpNoteToPartner.jsp"/>
    </sx:div>

</tags:imPanel>
<script type="text/javascript">
    $('importStockForm.actionCode').focus();
</script>