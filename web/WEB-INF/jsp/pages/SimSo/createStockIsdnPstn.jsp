<%@page import="com.viettel.security.util.StringEscapeUtils"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
    Document   : importPSTN
    Created on : Jan 15, 2008, 2:54:01 PM
    Author     : Tuannv
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.viettel.im.common.util.ResourceBundleUtils" %>
<%--<%@page import="com.guhesan.querycrypt.QueryCrypt" %>--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="imDef" uri="imDef" %>


<%
            request.setAttribute("contextPath", request.getContextPath());
            String pageId = request.getParameter("pageId");
            request.setAttribute("viewExcelPstntoDLU", request.getSession().getAttribute("viewExcelPstntoDLU" + pageId));


%>
<script type="text/javascript" language="javascript">
    transition = function (data){
        if (data == "districtChangePstn") {
            gotoAction("bodyContent", 'processPSTNAction!preparePage.do?form.location=' + document.getElementById("location").value+"&"+ token.getTokenParamString());
        }
    }
    deletePstn = function (id){
        var isDelete = confirm(getUnicodeMsg('MSG.ISN.042'));
        if(!isDelete){
            return false;
        }
        gotoAction("bodyContent", 'processPSTNAction!deletePstn.do?stockIsdnPstnId='+id+"&"+ token.getTokenParamString());
    }
    pageNavigator = function (ajaxTagId, pageNavigator, pageNumber){
        gotoAction("bodyContent", 'processPSTNAction!pageNavigator.do?' + pageNavigator + '=' + pageNumber+"&"+ token.getTokenParamString());
    }
    updateStatus = function (){
        // TuTM1 04/03/2012 Fix ATTT CSRF
        gotoAction("bodyContent", 'importPSTNAction!preparePage.do' + "?" + token.getTokenParamString());
        return false;
    }

</script>

<c:set var="viewExcelPstntoDLU" scope="page" value="${fn:escapeXml(imDef:checkAuthority(requestScope.viewExcelPstntoDLU, request))}" />

<tags:imPanel title="title.createStockIsdnPstn.page">
    <!-- hien thi message, tamdt1 -->
    <div id="divMesage">
        <tags:displayResult property="message" id="message" type="key" propertyValue="messageParam"/>
    </div>
        
    <s:form action="processPSTNAction!assignPstnToDLU" method="POST" id="ImportPSTNForm" theme="simple">
<s:token/>

        <div class="divHasBorder">
            <!-- hien thi thong tin dai PSTN -->
            <table class="inputTbl6Col">
                <tr>
                    <td style="width:10%; " class="label"><tags:label  key="MSG.province" required="true"  /></td>
                    <td style="width:23%; " class="text">
                        <tags:imSearch codeVariable="form.provinceCode" nameVariable="form.provinceName"
                                       codeLabel="MSG.province.code" nameLabel="MSG.province.name"
                                       searchClassName="com.viettel.im.database.DAO.AssignDslamAreaDAO"
                                       searchMethodName="getListProvince"
                                       getNameMethod="getListProvinceName"
                                       postAction="changeLocation()"
                                       roleList="f9ProvinceChangeDLU"/>
                    </td>
                    <td class="label" style="width:10%"><tags:label  key="MSG.provincePSTN"  /></td>
                    <td class="text" style="width:23%">
                        <s:textfield name="form.stockPstnDistrict" id="form.stockPstnDistrict" onfocus="changeLocation()" maxlength="15" cssClass="txtInputFull" readonly="true"/>
                    </td>
                    <td style="width:10%; " class="label"><tags:label  key="MSG.DLUCode"  /></td>
                    <td class="text">
                        <tags:imSearch codeVariable="form.dslamCode" nameVariable="form.dslamName"
                                       codeLabel="MSG.DLUCode" nameLabel="MSG.DLUName"
                                       searchClassName="com.viettel.im.database.DAO.StockIsdnAssignPstnToDluDAO"
                                       searchMethodName="getListDslam"
                                       getNameMethod="getListDslamName"
                                       otherParam="form.provinceCode"
                                       roleList="" readOnly="false"/>
                    </td>
                </tr>
                <tr>
                    <td class="label">
                        <tags:label  key="MSG.first.number"  />
                    </td>
                    <td class="text">
                        <s:textfield name="form.stockPstnHeader" id="stockPstnHeader" maxlength="15" cssClass="txtInputFull"/>
                    </td>
                    <td class="label">
                        <tags:label  key="MSG.fromIsdn" required="true"  />
                    </td>
                    <td class="text">
                        <s:textfield name="form.startStockPstn" id="startStockPstn" maxlength="15" cssClass="txtInputFull"/>
                    </td>
                    <td class="label">
                        <tags:label  key="MSG.toIsdn" required="true"  />
                    </td>
                    <td class="text">
                        <s:textfield name="form.endStockPstn" id="endStockPstn" maxlength="15" cssClass="txtInputFull"/>
                    </td>
                </tr>
                <tr>
                    <td class="label">
                        <tags:label  key="MSG.fromPort" required="true"  />
                    </td>
                    <td class="text">
                        <s:textfield name="form.startPortPstn" id="startPortPstn" maxlength="10" cssClass="txtInputFull"/>
                    </td>
                    <td class="label">
                        <tags:label  key="MSG.toPort" required="true"  />
                    </td>
                    <td class="text">
                        <s:textfield name="form.endPortPstn" id="endPortPstn" maxlength="10" cssClass="txtInputFull"/>
                    </td>
                    <td class="label">
                        <tags:label  key="MSG.module" required="true"   />
                    </td>
                    <td class="text">
                        <s:textfield name="form.pstnModule" id="pstnModule" maxlength="10" cssClass="txtInputFull"/>
                    </td>
                </tr>
                <tr>
                    <td class="label">
                        <tags:label  key="MSG.Rack" required="true"  />
                    </td>
                    <td class="text">
                        <s:textfield name="form.pstnRack" id="pstnRack" maxlength="10" cssClass="txtInputFull"/>
                    </td>
                    <td class="label">
                        <tags:label  key="MSG.Shelf" required="true"  />
                    </td>
                    <td class="text">
                        <s:textfield name="form.pstnShelf" id="pstnShelf" maxlength="10" cssClass="txtInputFull"/>
                    </td>
                    <td class="label">
                        <tags:label  key="MSG.slot" required="true"  />
                    </td>
                    <td class="text">
                        <s:textfield name="form.pstnSlot" id="pstnSlot" maxlength="10" cssClass="txtInputFull"/>
                    </td>
                </tr>
                <tr>
                    <td class="label">
                        <tags:label  key="MSG.DAS" required="true"  />
                    </td>
                    <td class="text">
                        <s:textfield name="form.pstnDas" id="pstnDas" maxlength="10" cssClass="txtInputFull"/>
                    </td>
                    <td class="label">
                        <tags:label  key="MSG.device.type"   />
                    </td>
                    <td class="text">
                        <s:textfield name="form.pstnDeviceType" id="pstnDeviceType" maxlength="10" cssClass="txtInputFull"/>
                    </td>

                    <td class="label">
                        <tags:label  key="MSG.status"  />
                    </td>
                    <td class="text" >
                        <tags:imSelect name="form.status"
                                       id="form.status"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.FAC.AssignPrice.ChooseStatus"
                                       list="1:NewISDN, 2:UsingISDN,3:SuspendISDN,5:LockISDN"
                                       />


                    </td>
                </tr>
                <tr>
                    <td class="label">
                        <tags:label  key="MSG.isdn_type"  />
                    </td>

                    <td class="text" >
                        <tags:imSelect name="form.isdnType"
                                       id="form.isdnType"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.chooseIsdnType"
                                       list="1:PrepaidISDN, 2:PostPaidISDN,3:SpecialISDN,4:FakeISDN"
                                       />


                    </td>
                </tr>
            </table>

            <!-- phan nut tac vu -->
            <div align="center" style="width:100%; padding-bottom:5px;">
                <tags:submit formId="ImportPSTNForm"
                             showLoadingText="true"
                             cssStyle="width:80px;"
                             targets="bodyContent"
                             value="MSG.assignDLU"
                             validateFunction="checkValidate()"
                             preAction="processPSTNAction!assignPstnToDslam.do" />

                <tags:submit formId="ImportPSTNForm"
                             showLoadingText="true"
                             cssStyle="width:80px;"
                             targets="divListRange"
                             value="MSG.find"
                             validateFunction="checkValidFieldsSearch()"
                             preAction="processPSTNAction!listStockIsdnPstn.do"/>

                <tags:submit formId="ImportPSTNForm"
                             showLoadingText="true"
                             cssStyle="width:80px;"
                             targets="bodyContent"
                             value="MSG.delete" confirm="true" confirmText="MSG.ISN.061"
                             validateFunction="checkSelected();"
                             preAction="processPSTNAction!actionDeletePstnDLU.do"/>

                <tags:submit formId="ImportPSTNForm"
                             showLoadingText="true"
                             cssStyle="width:100px;"
                             targets="bodyContent"
                             value="MSG.report.export" confirm="true" confirmText="MSG.ISN.033"
                             validateFunction="checkValidFieldsSearch()"
                             preAction="processPSTNAction!actionExport2Excel.do"/>
                         
            </div>

        </div>

        <!-- phan hien thi link download bao cao -->
        <div>
            <s:if test="#request.pathExpFile!=null">
                <script>
                    window.open('${contextPath}<s:property escapeJavaScript="true"  value="form.pathExpFile"/>','','toolbar=yes,scrollbars=yes');
                </script>
                <div><a href='${contextPath}<s:property escapeJavaScript="true"  value="form.pathExpFile"/>' ><s:text name="MSG.GOD.272"/></a></div>
            </s:if>
        </div>
          
        <!-- danh sach cac so gan cho dlu -->
        <fieldset class="imFieldset">
            <legend>${fn:escapeXml(imDef:imGetText('MSG.list.pstn.assign.DLU'))}</legend>
            <sx:div id="divListRange">
                <jsp:include page="createStockIsdnPstnSearchResult.jsp"/>
            </sx:div>
        </fieldset>
        
        <!-- phan hien thi nhap thong tin tu file excel (neu duoc phan quyen) -->
        <s:if test = "#attr.viewExcelPstntoDLU">
            <br />
            <tags:imPanel title="MSG.import.info.from.excel">
            
                <table class="inputTbl4Col" style="text-align:left; width:100%;">
                    <tr>
                        <td class="label" align="left"> <tags:label  key="MSG.attachmentFile" required="true"  /></td>
                        
                        <td class="text" colspan="2" align="left">
                            <tags:imFileUpload
                                cssStyle="width:100%"
                                name="form.clientFileName"
                                id="form.clientFileName"
                                serverFileName="form.serverFileName" extension="xls;txt"/>
                            <!--s:file id="form.impFile" name="form.impFile" size="60"/-->
                        </td>
                        <td class="text" class="label" align="left">
                            <!-- <span style="padding:0px; margin:0px;" id="spanInput">
                                <input type="button" onclick="assignPstnToDLUimport()" value="import" style="width:80px;"/>
                            </span>
                            -->
                            <tags:submit formId="ImportPSTNForm"
                                         showLoadingText="true"
                                         cssStyle="width:100px;"
                                         targets="bodyContent"
                                         value="MSG.import"
                                         confirm="true" confirmText="MSG.ISN.062"
                                         preAction="processPSTNAction!assignPstnToDLU.do"
                                         showProgressDiv="true"
                                         showProgressClass="com.viettel.im.database.DAO.StockIsdnAssignPstnToDluDAO"
                                         showProgressMethod="updateProgressDiv"/>
                            <a onclick=downloadPatternFile()> <s:text name="MSG.download.file.here"/> </a>
                        </td>
                        <%--<td class="text"  align="left">
                        
                    </td>--%>
                    </tr>
                    
                    <tr>
                        <td colspan="5" align="center">
                            <tags:displayResult id="resultAssignIsdnPriceClient" property="resultAssignIsdnPrice" type="key"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="5" align="center">
                        
                            <s:if test="form.pathLogFile!=null && form.pathLogFile!=''">
                                <script>
                                window.open('${contextPath}<s:property escapeJavaScript="true"  value="form.pathLogFile"/>','','toolbar=yes,scrollbars=yes');
                                </script>
                                <div><a href='${contextPath}<s:property escapeJavaScript="true"  value="form.pathLogFile"/>' ><s:text name="MSG.clickhere.to.download"/>.</a></div>
                            </s:if>
                        </td>
                    </tr>
                </table>
            </tags:imPanel>
        </s:if>
        
        <s:hidden id="isdnNumber" value="#attr.importPstn.id" />
        
    </s:form>


</tags:imPanel>

<script type="text/javascript" language="javascript">




    //focus vao truong dau tien
    //$('form.provinceCode').focus();
    $('form.stockPstnDistrict').focus();
    //changeLocation();

    checkValidateSearch = function() {
        return true;
    }

    checkValidate = function() {
        var result = checkRequiredFields() && checkValidFields();
        return result;
    }




        refreshListIsdnPstn = function() {
            var isdnNumber=document.getElementById("isdnNumber").value;
            gotoAction("divListRange", "${contextPath}/processPSTNAction!refreshListIsdnPstn.do?"+ token.getTokenParamString(),"ImportPSTNForm");
            $('messageList').innerHTML = '<s:text name="MSG.INF.00035"/>';

        }


        changeLocation = function() {

            var location = $('form.provinceCode').value;
            if (trim(location) == ""){
                $('form.stockPstnDistrict').value = "";
                $('form.dslamCode').value = "";
                return;
            }

            updateData('${contextPath}/processPSTNAction!getDluCombobox.do?target=form.dslamCode,form.stockPstnDistrict&location='+ location+"&"+ token.getTokenParamString());
        }
        assignPstnToDLUimport = function ()
        {
            initProgress();
            document.getElementById("ImportPSTNForm").action="processPSTNAction!assignPstnToDLU.do?"+ token.getTokenParamString();
            document.getElementById("ImportPSTNForm").submit();
         
        }

        //ham kiem tra cac truong bat buoc
        checkRequiredFields = function() {
            //
            if(trim($('form.provinceCode').value) == "") {
                $('message').innerHTML = '<s:text name="ERR.ISN.002"/>';
                $('form.provinceCode').select();
                $('form.provinceCode').focus();
                return false;
            }

            //
            if(trim($('form.dslamCode').value) == "") {
                $('message').innerHTML = '<s:text name="ERR.ISN.064"/>';
                $('form.dslamCode').select();
                $('form.dslamCode').focus();
                return false;
            }

            //truong so dau dai
            if(trim($('startStockPstn').value) == "") {
                $('message').innerHTML = '<s:text name="ERR.ISN.003"/>';
                $('startStockPstn').select();
                $('startStockPstn').focus();
                return false;
            }

            //truong so cuoi dai
            if(trim($('endStockPstn').value) == "") {
                $('message').innerHTML ='<s:text name="ERR.ISN.004"/>';
                $('endStockPstn').select();
                $('endStockPstn').focus();
                return false;
            }

            //
            if(trim($('startPortPstn').value) == "") {
                $('message').innerHTML = '<s:text name="ERR.ISN.067"/>';
                $('startPortPstn').select();
                $('startPortPstn').focus();
                return false;
            }

            //
            if(trim($('endPortPstn').value) == "") {
                $('message').innerHTML = '<s:text name="ERR.ISN.068"/>';
                $('endPortPstn').select();
                $('endPortPstn').focus();
                return false;
            }

            //
            if(trim($('pstnModule').value) == "") {
                $('message').innerHTML ='<s:text name="ERR.ISN.069"/>';
                $('pstnModule').select();
                $('pstnModule').focus();
                return false;
            }

            //
            if(trim($('pstnRack').value) == "") {
                $('message').innerHTML = '<s:text name="ERR.ISN.070"/>';
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
                $('message').innerHTML = '<s:text name="ERR.ISN.072"/>';
                $('pstnSlot').select();
                $('pstnSlot').focus();
                return false;
            }

            //
            if(trim($('pstnDas').value) == "") {
                $('message').innerHTML = '<s:text name="ERR.ISN.073"/>';
                $('pstnDas').select();
                $('pstnDas').focus();
                return false;
            }

            //

            return true;
        }

        //kiem tra tinh hop le cua cac truong
        checkValidFields = function() {
            if(trim($('stockPstnHeader').value) != "" && !isInteger(trim($('stockPstnHeader').value))) {
                $('message').innerHTML ='<s:text name="ERR.ISN.093"/>';
                $('stockPstnHeader').select();
                $('stockPstnHeader').focus();
                return false;
            }
            //truong dau dai phai la so nguyen duong
            if(!isInteger(trim($('startStockPstn').value))) {
                $('message').innerHTML = '<s:text name="ERR.ISN.075"/>';
                $('startStockPstn').select();
                $('startStockPstn').focus();
                return false;
            }
            //truong cuoi dai phai la so nguyen duong
            if(!isInteger(trim($('endStockPstn').value))) {
                $('message').innerHTML =  '<s:text name="ERR.ISN.007"/>';
                $('endStockPstn').select();
                $('endStockPstn').focus();
                return false;
            }
            //
            if(!isInteger(trim($('startPortPstn').value))) {
                $('message').innerHTML = '<s:text name="ERR.ISN.078"/>';
                $('startPortPstn').select();
                $('startPortPstn').focus();
                return false;
            }
            //
            if(!isInteger(trim($('endPortPstn').value))) {
                $('message').innerHTML = '<s:text name="ERR.ISN.098"/>';
                $('endPortPstn').select();
                $('endPortPstn').focus();
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
                $('message').innerHTML ='<s:text name="ERR.ISN.081"/>';
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
            //so dau dai va so cuoi dai phai co chieu dai = nhau
            var startStockPstn = trim($('startStockPstn').value);
            var endStockPstn = trim($('endStockPstn').value);

            if(startStockPstn.length != endStockPstn.length) {
                $('message').innerHTML = '<s:text name="ERR.ISN.009"/>';
                $('startStockPstn').select();
                $('startStockPstn').focus();
                return false;
            }
            //so dau dai khong duoc lon hon so cuoi dai
            if(startStockPstn*1 > endStockPstn*1) {
                $('message').innerHTML = '<s:text name="ERR.ISN.010"/>';
                $('startStockPstn').select();
                $('startStockPstn').focus();
                return false;
            }
            //chieu dai toan bo so khong duoc vuot 15 ky tu
            var isdn = trim($('stockPstnHeader').value) + startStockPstn;
            if(isdn.length > 15) {
                $('message').innerHTML = '<s:text name="ERR.ISN.106"/>';
                $('stockPstnHeader').select();
                $('stockPstnHeader').focus();
                return false;
            }


            var startPortPstn = trim($('startPortPstn').value);
            var endPortPstn = trim($('endPortPstn').value);
            //port dau dai khong duoc lon hon port cuoi dai
            if(startPortPstn*1 > endPortPstn*1) {
                $('message').innerHTML = '<s:text name="ERR.ISN.108"/>';
                $('startPortPstn').select();
                $('startPortPstn').focus();
                return false;
            }
            //
            if((endStockPstn*1 - startStockPstn*1) != (endPortPstn*1 - startPortPstn*1)) {
                $('message').innerHTML =  '<s:text name="ERR.ISN.089"/>';
                $('startStockPstn').select();
                $('startStockPstn').focus();
                return false;
            }

            //kiem tra so luong so duoc gan DLU khong duoc vuot qua so luong cho phép
            var maxIsdnAssignToDlu = '<%=StringEscapeUtils.escapeHtml(ResourceBundleUtils.getResource("MAX_ISDN_ASSIGN_TO_DLU"))%>';
            if((endStockPstn*1 - startStockPstn*1 + 1) > maxIsdnAssignToDlu) {
                $('message').innerHTML =  '<s:text name="ERR.ISN.109"/>'  + maxIsdnAssignToDlu + " số";
                $('startStockPstn').select();
                $('startStockPstn').focus();
                return false;
            }

            //trim cac truong can thiet
            $('stockPstnHeader').value = trim($('stockPstnHeader').value);
            $('startStockPstn').value = trim($('startStockPstn').value);
            $('endStockPstn').value = trim($('endStockPstn').value);
            $('startPortPstn').value = trim($('startPortPstn').value);
            $('endPortPstn').value = trim($('endPortPstn').value);
            $('pstnModule').value = trim($('pstnModule').value);
            $('pstnRack').value = trim($('pstnRack').value);
            $('pstnShelf').value = trim($('pstnShelf').value);
            $('pstnSlot').value = trim($('pstnSlot').value);
            $('pstnDas').value = trim($('pstnDas').value);
            $('pstnDeviceType').value = trim($('pstnDeviceType').value);


            return true;
        }

        //kiem tra tinh hop le cua cac truong khi tim kiem
        checkValidFieldsSearch = function() {
            $('messageList').innerHTML = "";
            $('message').innerHTML = "";
            if(trim($('form.provinceCode').value) == "") {
                $('message').innerHTML = '<s:text name="ERR.ISN.002"/>';
                $('form.provinceCode').select();
                $('form.provinceCode').focus();
                return false;
            }





            if(trim($('stockPstnHeader').value) != "" && !isInteger(trim($('stockPstnHeader').value))) {
                $('message').innerHTML =  '<s:text name="ERR.ISN.006"/>';
                $('stockPstnHeader').select();
                $('stockPstnHeader').focus();
                return false;
            }
            if(trim($('stockPstnHeader').value) != "" && trim($('startStockPstn').value) == "" && trim($('endStockPstn').value) == "" ) {
                $('message').innerHTML =  '<s:text name="ERR.ISN.094"/>';
                $('stockPstnHeader').select();
                $('stockPstnHeader').focus();
                return false;
            }

            //truong dau dai phai la so nguyen duong
            if(trim($('startStockPstn').value) != "" && !isInteger(trim($('startStockPstn').value))) {
                $('message').innerHTML =  '<s:text name="ERR.ISN.075"/>';
                $('startStockPstn').select();
                $('startStockPstn').focus();
                return false;
            }
            //truong cuoi dai phai la so nguyen duong
            if(trim($('endStockPstn').value) != "" && !isInteger(trim($('endStockPstn').value))) {
                $('message').innerHTML = '<s:text name="ERR.ISN.007"/>';
                $('endStockPstn').select();
                $('endStockPstn').focus();
                return false;
            }
            //
            if(trim($('startPortPstn').value) != "" && !isInteger(trim($('startPortPstn').value))) {
                $('message').innerHTML = '<s:text name="ERR.ISN.078"/>';
                $('startPortPstn').select();
                $('startPortPstn').focus();
                return false;
            }
            //
            if(trim($('endPortPstn').value) != "" && !isInteger(trim($('endPortPstn').value))) {
                $('message').innerHTML ='<s:text name="ERR.ISN.098"/>';
                $('endPortPstn').select();
                $('endPortPstn').focus();
                return false;
            }
            //
            if(trim($('pstnModule').value) != "" && !isInteger(trim($('pstnModule').value))) {
                $('message').innerHTML = '<s:text name="ERR.ISN.080"/>';
                $('pstnModule').select();
                $('pstnModule').focus();
                return false;
            }
            //
            if(trim($('pstnRack').value) != "" &&  !isInteger(trim($('pstnRack').value))) {
                $('message').innerHTML = '<s:text name="ERR.ISN.081"/>';
                $('pstnRack').select();
                $('pstnRack').focus();
                return false;
            }
            //
            if(trim($('pstnShelf').value) != "" && !isInteger(trim($('pstnShelf').value))) {
                $('message').innerHTML = '<s:text name="ERR.ISN.082"/>';
                $('pstnShelf').select();
                $('pstnShelf').focus();
                return false;
            }
            //
            if(trim($('pstnSlot').value) != "" && !isInteger(trim($('pstnSlot').value))) {
                $('message').innerHTML = '<s:text name="ERR.ISN.083"/>';
                $('pstnSlot').select();
                $('pstnSlot').focus();
                return false;
            }
            //
            if(trim($('pstnDas').value) != "" &&  !isInteger(trim($('pstnDas').value))) {
                $('message').innerHTML = '<s:text name="ERR.ISN.084"/>';
                $('pstnDas').select();
                $('pstnDas').focus();
                return false;
            }
            //so dau dai va so cuoi dai phai co chieu dai = nhau
            var startStockPstn = trim($('startStockPstn').value);
            var endStockPstn = trim($('endStockPstn').value);

            if(startStockPstn != "" && endStockPstn != "" && startStockPstn.length != endStockPstn.length) {
                $('message').innerHTML = '<s:text name="ERR.ISN.009"/>';
                $('startStockPstn').select();
                $('startStockPstn').focus();
                return false;
            }
            //so dau dai khong duoc lon hon so cuoi dai
            if(startStockPstn !="" && endStockPstn != "" &&  startStockPstn*1 > endStockPstn*1) {
                $('message').innerHTML = '<s:text name="ERR.ISN.010"/>';
                $('startStockPstn').select();
                $('startStockPstn').focus();
                return false;
            }
            //chieu dai toan bo so khong duoc vuot 15 ky tu
            var isdnto = trim($('stockPstnHeader').value) + startStockPstn;
            var isdnend = trim($('stockPstnHeader').value) + endStockPstn;
            if(isdnto.length > 15) {
                $('message').innerHTML = '<s:text name="ERR.ISN.106"/>';
                $('stockPstnHeader').select();
                $('stockPstnHeader').focus();
                return false;
            }
            var isdnend = trim($('stockPstnHeader').value) + endStockPstn;
            if(isdnend.length > 15) {
                $('message').innerHTML = '<s:text name="ERR.ISN.106"/>';
                $('stockPstnHeader').select();
                $('stockPstnHeader').focus();
                return false;
            }


            var startPortPstn = trim($('startPortPstn').value);
            var endPortPstn = trim($('endPortPstn').value);
            //port dau dai khong duoc lon hon port cuoi dai
            if(startPortPstn != "" && endPortPstn != "" && startPortPstn*1 > endPortPstn*1) {
                $('message').innerHTML = '<s:text name="ERR.ISN.088"/>';
                $('startPortPstn').select();
                $('startPortPstn').focus();
                return false;
            }
            //


            //kiem tra so luong so duoc gan DLU khong duoc vuot qua so luong cho phép
            var maxIsdnAssignToDlu = '<%=StringEscapeUtils.escapeHtml(ResourceBundleUtils.getResource("MAX_ISDN_ASSIGN_TO_DLU"))%>';
            if((endStockPstn*1 - startStockPstn*1 + 1) > maxIsdnAssignToDlu) {
                $('message').innerHTML = '<s:text name="ERR.ISN.109"/>' + maxIsdnAssignToDlu + " số";
                $('startStockPstn').select();
                $('startStockPstn').focus();
                return false;
            }

            //trim cac truong can thiet
            $('stockPstnHeader').value = trim($('stockPstnHeader').value);
            $('startStockPstn').value = trim($('startStockPstn').value);
            $('endStockPstn').value = trim($('endStockPstn').value);
            $('startPortPstn').value = trim($('startPortPstn').value);
            $('endPortPstn').value = trim($('endPortPstn').value);
            $('pstnModule').value = trim($('pstnModule').value);
            $('pstnRack').value = trim($('pstnRack').value);
            $('pstnShelf').value = trim($('pstnShelf').value);
            $('pstnSlot').value = trim($('pstnSlot').value);
            $('pstnDas').value = trim($('pstnDas').value);
            $('pstnDeviceType').value = trim($('pstnDeviceType').value);


            return true;
        }
        downloadPatternFile = function() {
            window.open('${contextPath}/share/pattern/assignPstnToDluPattern.xls','','toolbar=yes,scrollbars=yes');
        }

        changeMessage= function() {
            $('message').innerHTML ='<s:text name="ERR.ISN.041"/>';
        }






</script>
