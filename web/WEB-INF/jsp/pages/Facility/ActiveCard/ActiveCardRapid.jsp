<%-- 
    Document   : ActiveCardRapid
    Created on : Aug 31, 2010, 1:42:54 PM
    Author     : TheTM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
            request.setAttribute("contextPath", request.getContextPath());
%>

<tags:imPanel title="MSG.serial.find.info">
    <!-- phan hien thi thong tin message -->
    <div>
        <tags:displayResult property="message" id="message" propertyValue="messageParam" type="key"/>
    </div>

    <s:form action="activeCard" theme="simple" method="post" id="activeCardForm">
<s:token/>

        <!-- phan hien thi thong tin can tra cuu -->
        <div class="divHasBorder">
            <table class="inputTbl4Col">
                <tr>
                    <td><tags:label key="MSG.range.serial" /></td>
                    <td>
                        <table style="width:100%; border-spacing:3px; ">
                            <tr>
                                <td style="width:50%;">
                                    <s:textfield name="activeCardForm.fromSerial" id="activeCardForm.fromSerial" maxlength="25" cssClass="txtInputFull"/>
                                </td>
                                <td>-</td>
                                <td style="width:50%;">
                                    <s:textfield name="activeCardForm.toSerial" id="activeCardForm.toSerial" maxlength="25" cssClass="txtInputFull" onfocus="focusOnToSerial();"/>
                                </td>
                            </tr>
                        </table>
                    </td>
                    <td class="label">&nbsp;</td>
                </tr>
            </table>

            <!-- phan nut tac vu -->
            <div align="center" style="padding-top:3px;">
                <tags:submit formId="activeCardForm"
                             validateFunction="checkValidFields()"
                             showLoadingText="true"
                             cssStyle="width:80px;"
                             targets="bodyContent"
                             value="MSG.active.card"
                             preAction="activeCard!activeCardRapid.do"/>
            </div>
        </div>
        <!-- Ket qua kich hoat -->
        <s:if test="#attr.errFilePath != null">
            <script type="text/javascript" language="JavaScript">
                window.open('${fn:escapeXml(errFilePath)}','','toolbar=yes,scrollbars=yes');
            </script>
            <a href='${fn:escapeXml(errFilePath)}' ><tags:label key="MSG.click.here.to.download" /></a>

        </s:if>
    </s:form>
</tags:imPanel>

<script language="javascript">

    //focus vao truong dau tien
    $('activeCardForm.fromSerial').select();
    $('activeCardForm.fromSerial').focus();


    //xu ly su kienn focus tren textBox toIsdn
    focusOnToSerial = function () {
        var fromSerial = trim($('activeCardForm.fromSerial').value);
        var toSerial = trim($('activeCardForm.toSerial').value);
        if(toSerial == "") {
            $('activeCardForm.toSerial').value = fromSerial;
        }
    }

    //ham kiem tra du lieu cac truong co hop le hay ko
    checkValidFields = function() {
        var fromSerial = trim($('activeCardForm.fromSerial').value);
        var toSerial = trim($('activeCardForm.toSerial').value);

        if(fromSerial == "" ||fromSerial ==null ) {
            $('message').innerHTML =  '<s:text name = "ERR.ACTIVE.CARD.0011"/>';
            $('activeCardForm.fromSerial').select();
            $('activeCardForm.fromSerial').focus();
            return false;
        }

        if(toSerial ==null || toSerial == "" ) {
            $('message').innerHTML =  '<s:text name = "ERR.ACTIVE.CARD.0011"/>';
            $('activeCardForm.toSerial').select();
            $('activeCardForm.toSerial').focus();
            return false;
        }

        if(fromSerial != "" && !isInteger(trim($('activeCardForm.fromSerial').value))) {
            $('message').innerHTML =  '<s:text name = "ERR.FAC.ISDN.001"/>';
            $('activeCardForm.fromSerial').select();
            $('activeCardForm.fromSerial').focus();
            return false;
        }
        if(toSerial != "" && !isInteger(trim($('activeCardForm.toSerial').value))) {
            $('message').innerHTML =  '<s:text name = "ERR.FAC.ISDN.002"/>';
            $('activeCardForm.toSerial').select();
            $('activeCardForm.toSerial').focus();
            return false;
        }

        if(fromSerial != "" && toSerial != "" && (fromSerial * 1 > toSerial * 1)) {
            $('message').innerHTML =  '<s:text name = "ERR.FAC.ISDN.003"/>';
            $('activeCardForm.fromSerial').select();
            $('activeCardForm.fromSerial').focus();
            return false;
        }

        //trim cac truong can thiet
        $('activeCardForm.fromSerial').value = trim($('activeCardForm.fromSerial').value);
        $('activeCardForm.toSerial').value = trim($('activeCardForm.toSerial').value);

        return true;
    }
</script>
