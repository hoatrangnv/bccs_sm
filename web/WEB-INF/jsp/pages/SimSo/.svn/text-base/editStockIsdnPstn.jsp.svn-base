<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
    Document   : importPSTN
    Created on : Jan 15, 2008, 2:54:01 PM
    Author     : Tuannv
--%>

<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.guhesan.querycrypt.QueryCrypt" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="imDef" uri="imDef" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
%>

<s:form action="processPSTNAction!editPstnComplete.do" id="importPSTNForm" method="post" theme="simple">
<s:token/>

    <fieldset style="width:100%;">
        <legend class="transparent">${fn:escapeXml(imDef:imGetText('MSG.ISN.058'))}</legend>
        <!-- hien thi message, tamdt1 -->
        <div>
            <tags:displayResult id="message" property="message"/>
        </div>

        <!-- phan thong tin ve DLU cua so PSTN -->
        <table class="inputTbl6Col">
            <tr>
                <td><tags:label key="MSG.pstnNumber" required="true"/></td>
                <td  class="oddColumn">
                    <s:textfield name ="form.stockPstnNumber" id="stockPstnNumber"  readonly="true" cssClass="txtInputFull"/>
                </td>

                <td style="width:10%; " class="label">
                    <tags:label key="MSG.place" required="true"/>
                </td>
                <td style="width:20%; " class="text" colspan="1">


                    <tags:imSearch codeVariable="form.location" nameVariable="form.provinceName"
                                   codeLabel="MSG.province.code" nameLabel="MSG.province.name"
                                   searchClassName="com.viettel.im.database.DAO.AssignDslamAreaDAO"
                                   searchMethodName="getListProvince"
                                   getNameMethod="getListProvinceName"
                                   postAction="changeLocation()"
                                   roleList="f9ProvinceChangeDLU" readOnly="true"/>
                </td>
            </tr>


            <tr>

                <td style="width:10%; " class="label">
                    <tags:label  key="MSG.DLUCode" required="true"  />
                </td>
                <td style="width:20%;" colspan="1">
                    <%--  <s:select name="form.dluId"
                                id="dslamId"
                                cssClass="txtInputFull"
                                headerKey="" headerValue="--Chọn DLU--"
                                list="%{#request.listDlus != null ? #request.listDlus : #{}}"
                                listKey="dslamId" listValue="name"/>--%>
                    <tags:imSearch codeVariable="form.dslamCode" nameVariable="form.dslamName"
                                   codeLabel="MSG.DLUCode" nameLabel="MSG.DLUName"
                                   searchClassName="com.viettel.im.database.DAO.StockIsdnAssignPstnToDluDAO"
                                   searchMethodName="getListDslam"
                                   otherParam="form.location"
                                   getNameMethod="getListDslamName"
                                   readOnly="true"


                                   roleList=""/>
                </td>



                <td  class="oddColumn">

                </td>
            </tr>
            <tr>
                <td>
                    <tags:label  key="MSG.port" required="true"  />
                </td>
                <td class="oddColumn">
                    <s:textfield name="form.startPortPstn" id="startPortPstn" maxlength="10" cssClass="txtInputFull"/>
                </td>
                <td>
                    <tags:label  key="MSG.module" required="true"  />
                </td>
                <td>
                    <s:textfield name="form.pstnModule" id="pstnModule" maxlength="10" cssClass="txtInputFull"/>
                </td>
            </tr>
            <tr>
                <td>
                    <tags:label  key="MSG.Rack" required="true"  />
                </td>
                <td class="oddColumn">
                    <s:textfield name="form.pstnRack" id="pstnRack" maxlength="10" cssClass="txtInputFull"/>
                </td>
                <td>
                    <tags:label  key="MSG.Shelf" required="true"  />
                </td>
                <td>
                    <s:textfield name="form.pstnShelf" id="pstnShelf" maxlength="10" cssClass="txtInputFull"/>
                </td>
            </tr>
            <tr>
                <td>
                    <tags:label  key="MSG.slot" required="true"  />
                </td>
                <td class="oddColumn">
                    <s:textfield name="form.pstnSlot" id="pstnSlot" maxlength="10" cssClass="txtInputFull"/>
                </td>
                <td>
                    <tags:label  key="MSG.DAS" required="true"  />
                </td>
                <td>
                    <s:textfield name="form.pstnDas" id="pstnDas" maxlength="10" cssClass="txtInputFull"/>
                </td>
            </tr>
            <tr>
                <td>
                   <tags:label  key="MSG.device.type"/>
                </td>
                <td class="oddColumn">
                    <s:textfield name="form.pstnDeviceType" maxlength="10" id="pstnDeviceType" cssClass="txtInputFull"/>
                </td>
            </tr>
        </table>
    </fieldset>
</s:form>

<!-- phan nut tac vu, dong popup -->
<div align="center" style="padding-top:20px;">
    <button style="width:80px;" onclick="editStockIsdnPstn()"><tags:label  key="MSG.accept"  /></button>
    <button style="width:80px;" onclick="cancelEditStockIsdnPstn()"><tags:label  key="MSG.INF.047"  /></button>
</div>

<s:if test="#request.editStockIsdnPstnMode == 'closePopup'">

    <script language="javascript">
        window.opener.refreshListIsdnPstn();
        window.close();
    </script>

</s:if>

<script language="javascript">

    //focus vao truong dau tien
    <%--$('dslamId').focus();--%>

        document.getElementById('form.dslamName').style.width="150px";
        editStockIsdnPstn = function() {
            if(checkRequiredFields() && checkValidFields() && confirm(getUnicodeMsg('<s:text name="MSG.ISN.059" />'))) {
                $('importPSTNForm').submit();
            }
        }

        cancelEditStockIsdnPstn = function() {
            window.close();
        }

        //ham kiem tra cac truong bat buoc
        checkRequiredFields = function() {
            //
            if(trim($('form.dslamCode').value) == "") {
                $('message').innerHTML = '<s:text name="ERR.ISN.092"/>';
                $('form.dslamCode').focus();
                return false;
            }
            //
            if(trim($('startPortPstn').value) == "") {
                $('message').innerHTML = '<s:text name="ERR.ISN.149"/>';
                $('startPortPstn').select();
                $('startPortPstn').focus();
                return false;
            }
            //
            if(trim($('pstnModule').value) == "") {
                $('message').innerHTML = '<s:text name="ERR.ISN.069"/>';
                $('pstnModule').select();
                $('pstnModule').focus();
                return false;
            }
            //
            if(trim($('pstnRack').value) == "") {
                $('message').innerHTML =  '<s:text name="ERR.ISN.070"/>';
                $('pstnRack').select();
                $('pstnRack').focus();
                return false;
            }
            //
            if(trim($('pstnShelf').value) == "") {
                $('message').innerHTML = '<s:text name="ERR.ISN.071"/>';
                $('pstnShelf').select();
                $('pstnShelf').focus();
                return false;
            }
            //
            if(trim($('pstnSlot').value) == "") {
                $('message').innerHTML =  '<s:text name="ERR.ISN.072"/>';
                $('pstnSlot').select();
                $('pstnSlot').focus();
                return false;
            }
            //
            if(trim($('pstnDas').value) == "") {
                $('message').innerHTML =  '<s:text name="ERR.ISN.073"/>';
                $('pstnDas').select();
                $('pstnDas').focus();
                return false;
            }
            //
           <%-- if(trim($('pstnDeviceType').value) == "") {
                $('message').innerHTML = '<s:text name="ERR.ISN.074')"/>';
                $('pstnDeviceType').select();
                $('pstnDeviceType').focus();
                return false;
            }--%>

            return true;
        }

        //kiem tra tinh hop le cua cac truong
        checkValidFields = function() {
            //
            if(!isInteger(trim($('startPortPstn').value))) {
                $('message').innerHTML =  '<s:text name="ERR.ISN.150"/>';
                $('startPortPstn').select();
                $('startPortPstn').focus();
                return false;
            }
            //
            if(!isInteger(trim($('pstnModule').value))) {
                $('message').innerHTML = '<s:text name="ERR.ISN.080"/>';
                $('pstnModule').select();
                $('pstnModule').focus();
                return false;
            }
            //
            if(!isInteger(trim($('pstnRack').value))) {
                $('message').innerHTML = '<s:text name="ERR.ISN.081"/>';
                $('pstnRack').select();
                $('pstnRack').focus();
                return false;
            }
            //
            if(!isInteger(trim($('pstnShelf').value))) {
                $('message').innerHTML = '<s:text name="ERR.ISN.082"/>';
                $('pstnShelf').select();
                $('pstnShelf').focus();
                return false;
            }
            //
            if(!isInteger(trim($('pstnSlot').value))) {
                $('message').innerHTML = '<s:text name="ERR.ISN.083"/>';
                $('pstnSlot').select();
                $('pstnSlot').focus();
                return false;
            }
            //
            if(!isInteger(trim($('pstnDas').value))) {
                $('message').innerHTML = '<s:text name="ERR.ISN.084"/>';
                $('pstnDas').select();
                $('pstnDas').focus();
                return false;
            }
        
            return true;
        }


</script>

