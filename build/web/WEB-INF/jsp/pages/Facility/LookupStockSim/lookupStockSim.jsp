<%-- 
    Document   : lookupStockSim
    Created on : Dec 10, 2009, 3:46:00 PM
    Author     : AnDV
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
%>
<s:form action="lookupStockSimAction" theme="simple" enctype="multipart/form-data"  method="post" id="lookUpStockSimForm">
<s:token/>

    <tags:imPanel title="MSG.find.info">

        <!-- hien thi message -->
        <div>
            <tags:displayResult id="displayResultMsgClient" property="message" propertyValue="messageParam" type="key" />
        </div>

        <table class="inputTbl6Col">
            <tr>
                <td class="label"><tags:label key="MSG.generates.switchboard"/></td>
                <td class="text">
                    <tags:imSelect name="lookUpStockSimForm.hlrId" id="hlrId"
                              cssClass="txtInputFull" disabled="false"
                              list="1:1,2:2,3:3,4:4,5:5,6:6,7:7,8:8"
                              headerKey="" headerValue="MSG.FAC.ChooseExchange"/>
                </td>
                <td class="label"><tags:label key="MSG.generates.HLRStatus"/></td>
                <td class="text">
                    <tags:imSelect name="lookUpStockSimForm.hlrStatus" id="hlrStatus"
                              cssClass="txtInputFull" disabled="false"
                              list="1:MSG.not.registed,2:MSG.registed,3:MSG.removed"
                              headerKey="" headerValue="MSG.FAC.AssignPrice.ChooseStatus"/>
                </td>
                <td class="label"><tags:label key="MSG.generates.AUCStatus"/></td>
                <td class="text">
                    <tags:imSelect name="lookUpStockSimForm.aucStatus" id="aucStatus"
                              cssClass="txtInputFull" disabled="false"
                              list="1:MSG.registed,0:MSG.not.registed"
                              headerKey="" headerValue="MSG.FAC.AssignPrice.ChooseStatus"/>
                </td>
            </tr>
            <tr>
                <td class="label"><tags:label key="MSG.sim.type" required="false"/></td>
                <td class="text">
                    <tags:imSelect name="lookUpStockSimForm.stockModelId" id="stockModelId"
                              cssClass="txtInputFull" disabled="false"
                              list="144:MSG.prepaid,
                                      145:MSG.postpaid"
                              headerKey="" headerValue="MSG.FAC.AssignPrice.ChooseStatus"/>
                </td>


                <td class="label"><tags:label key="MSG.sim.from_imsi"/></td>
                <td class="text">
                    <s:textfield name="lookUpStockSimForm.startImsi" id="startImsi" maxlength="15" cssClass="txtInputFull"/>
                </td>

                <td class="label"><tags:label key="MSG.sim.to_imsi"/></td>
                <td class="text">
                    <s:textfield name="lookUpStockSimForm.endImsi" id="endImsi" maxlength="15" cssClass="txtInputFull"/>
                </td>

            </tr>
            <tr>

                <td class="label"><tags:label key="MSG.generates.from_serial"/></td>
                <td class="text">
                    <s:textfield name="lookUpStockSimForm.startSerial" id="startSerial" maxlength="25" cssClass="txtInputFull"/>
                </td>
                <td class="label"><tags:label key="MSG.generates.to_serial"/></td>
                <td class="text">
                    <s:textfield name="lookUpStockSimForm.endSerial" id="endSerial" maxlength="25" cssClass="txtInputFull"/>
                </td>

            </tr>

        </table>
        <%--phan nut tim kiem--%>


        <sx:submit  parseContent="true" executeScripts="true"
                    formId="lookUpStockSimForm" loadingText="Đang thực hiện..."
                    showLoadingText="true" targets="bodyContent"
                    cssStyle="width:80"
                    key="MSG.generates.find"  beforeNotifyTopics="lookupStockSimAction/searchStockSim"/>

    </tags:imPanel>
    <br/>

    <%--list Sim--%>

    <tags:imPanel title="MSG.return.info">

        <div id="lstStockSim" style="width:100%; height:400px; overflow:auto;">
            <jsp:include page="listLookupStockSim.jsp"/>

        </div>
    </tags:imPanel>
    <br/>



    <%--Hiển thị thông tin Sim--%>
    <div id="detailSim">
        <table class="inputTbl8Col">
            <tr>
                <td class="label"><tags:label key="MSG.UAC.reg.date"/></td>
                <td class="text">
                    <s:textfield name="lookUpStockSimForm.aucRegDate" id="aucRegDate" maxlength="50" cssClass="txtInputFull"/>
                </td>
                <td class="label"><tags:label key="MSG.UAC.kill.date"/></td>
                <td class="text">
                    <s:textfield name="lookUpStockSimForm.aucRemoveDate" id="aucRemoveDate" maxlength="50" cssClass="txtInputFull"/>
                </td>

                <td class="label"><tags:label key="MSG.PUK"/></td>
                <td class="text">
                    <s:textfield name="lookUpStockSimForm.puk" id="puk" maxlength="8" cssClass="txtInputFull"/>
                </td>
                <td class="label"><tags:label key="MSG.pin2"/></td>
                <td class="text">
                    <s:textfield name="lookUpStockSimForm.puk2" id="puk2" maxlength="8" cssClass="txtInputFull"/>
                </td>
            </tr>
            <tr>
                <td class="label"><tags:label key="MSG.dktd.date"/> </td>
                <td class="text">
                    <s:textfield name="lookUpStockSimForm.hlrRegDate" id="hlrRegDate" maxlength="50" cssClass="txtInputFull"/>
                </td>

                <td class="label"> <tags:label key="MSG.kill.td.date"/></td>
                <td class="text">
                    <s:textfield name="lookUpStockSimForm.hlrRemoveDate" id="hlrRemoveDate" maxlength="50" cssClass="txtInputFull"/>
                </td>
                <td class="label"><tags:label key="MSG.pin"/></td>
                <td class="text">
                    <s:textfield name="lookUpStockSimForm.pin" id="pin" maxlength="4" cssClass="txtInputFull"/>
                </td>
                <td class="label"><tags:label key="MSG.pin2"/></td>
                <td class="text">
                    <s:textfield name="lookUpStockSimForm.pin2" id="pin2" maxlength="4" cssClass="txtInputFull"/>
                </td>
            </tr>

            <tr>
                <td class="label"><tags:label key="MSG.alrogithm"/> </td>
                <td class="text">
                    <s:textfield name="lookUpStockSimForm.a3a8" id="a3a8" maxlength="1" cssClass="txtInputFull"/>
                </td>
                <td class="label"><tags:label key="MSG.adm"/></td>
                <td class="text">
                    <s:textfield name="lookUpStockSimForm.adm1" id="adm1" maxlength="8" cssClass="txtInputFull"/>
                </td>

                <td class="label"><tags:label key="MSG.shop"/></td>
                <td class="text" colspan="3">
                    <s:textfield name="lookUpStockSimForm.name" id="name" maxlength="100" cssClass="txtInputFull"/>
                </td>
            </tr>
            <tr>

                <td class="label"><tags:label key="MSG.generates.imsi"/></td>
                <td class="text" colspan="3">
                    <s:textfield name="lookUpStockSimForm.serial" id="serial" maxlength="50" cssClass="txtInputFull"/>
                </td>
                <td class="label"><tags:label key="MSG.EKI"/></td>
                <td class="text" colspan="3">
                    <s:textfield name="lookUpStockSimForm.eki" id="eki" maxlength="32" cssClass="txtInputFull"/>
                </td>

            </tr>
        </table>


    </div>
</s:form>
<script type="text/javascript">

    //lang nghe, xu ly su kien onclick tren nut tim kiem
    dojo.event.topic.subscribe("lookupStockSimAction/searchStockSim", function(event, widget){
        reset();
        widget.href = "lookupStockSimAction!searchStockSim.do";
    });
    reset=function(){
        $('eki').value="";
        $('adm1').value="";
        $('a3a8').value="";
        $('aucRegDate').value="";
        $('aucRemoveDate').value="";
        $('puk').value="";
        $('puk2').value="";
        $('pin').value="";
        $('pin2').value="";
        $('hlrRemoveDate').value="";
        $('hlrRegDate').value="";
        $('serial').value="";
    }


</script>


