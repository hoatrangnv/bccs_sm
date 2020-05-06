<%-- 
    Document   : portFormat
    Created on : May 31, 2010, 11:23:12 AM
    Author     : tronglv
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>



<tags:imPanel title="MSG.port.identifier.info">

    <!-- hien thi MSG -->
    <div>
        <tags:displayResult id="returnMsgClient" property="returnMsg" propertyValue="returnMsgValue" type="key"/>
    </div>

    <!-- thong tin bras -->
    <s:form action="portFormatAction" theme="simple" enctype="multipart/form-data"  method="post" id="portFormatForm">
<s:token/>

        <table class="inputTbl4Col" style="width:100%" >

            <s:hidden name="portFormatForm.id" id="portFormatForm.id"/>
            <input type="hidden" id="portFormarForm.editPortFormat" value="${fn:escapeXml(editPortFormat)}" />
            <tr>                
                <td class="label"><tags:label key="MSG.device" required="true"/> </td>
                <td class="text">

                    <s:if test="#request.editPortFormat == 'editPortFormat'">
                        <tags:imSearch codeVariable="portFormatForm.eqCode" nameVariable="portFormatForm.eqName"
                                       codeLabel="MSG.device.code" nameLabel="MSG.device.type"
                                       searchClassName="com.viettel.im.database.DAO.PortFormatDAO"
                                       searchMethodName="getListEq"
                                       getNameMethod="getListEqName"
                                       roleList="" readOnly="true"/>
                    </s:if>
                    <s:else>
                        <tags:imSearch codeVariable="portFormatForm.eqCode" nameVariable="portFormatForm.eqName"
                                       codeLabel="MSG.device.code" nameLabel="MSG.device.type"
                                       searchClassName="com.viettel.im.database.DAO.PortFormatDAO"
                                       searchMethodName="getListEq"
                                       getNameMethod="getListEqName"
                                       roleList=""/>
                    </s:else>



                </td>
                <td class="label"><tags:label key="MSG.area.type" required="true"/> </td>
                <td class="text">
                    <s:if test="#request.editPortFormat == 'editPortFormat'">
                        <tags:imSelect name="portFormatForm.type" id="portFormatForm.type"
                                       cssClass="txtInputFull" disabled="true" value="${fn:escapeXml(portFormatType)}"
                                       list="1:MSG.INF.042,
                                       2:MSG.ip,
                                       3:MSG.port.eth"
                                       headerKey="" headerValue="MSG.area.type"/>
                    </s:if>
                    <s:else>
                        <tags:imSelect name="portFormatForm.type" id="portFormatForm.type"
                                       cssClass="txtInputFull"
                                       list="1:MSG.INF.042,
                                       2:MSG.ip,
                                       3:MSG.port.eth"
                                       headerKey="" headerValue="MSG.area.type"/>
                    </s:else>



                </td>
            </tr>
            <tr>
                <td class="label"><tags:label key="MSG.generates.status" required="true"/> </td>
                <td class="text">
                    <tags:imSelect name="portFormatForm.status" id="portFormatForm.status"
                                   cssClass="txtInputFull"
                                   list="1:MSG.active,0: MSG.inactive"
                                   headerKey="" headerValue="MSG.FAC.AssignPrice.ChooseStatus"/>
                </td>
            </tr>
            <tr>
                <td class="label"><tags:label key="MSG.identifier" required="true"/> </td>
                <td class="text" colspan="3">
                    <s:textarea name="portFormatForm.portFormat" id="portFormatForm.portFormat" cols="3" cssClass="txtInputFull" rows="3"/>
                </td>
            </tr>           
        </table>

        <s:if test=" portFormatForm.id != null && (portFormatForm.id * 1) > 0 ">
            <div align="center" style="vertical-align:top; padding-top:10px;">
                <tags:submit targets="bodyContent" formId="portFormatForm"
                             confirm="true" confirmText="MSG.INF.00026"
                             showLoadingText="true" cssStyle="width:85px;"
                             value="MSG.generates.edit" preAction="portFormatAction!editPortFormat.do"
                             validateFunction="validatePortFormat()"
                             />
                <tags:submit targets="bodyContent" formId="portFormatForm"
                             confirm="false"
                             showLoadingText="true" cssStyle="width:85px;"
                             value="MSG.INF.047" preAction="portFormatAction!cancelEditPortFormat.do"
                             />
            </div>
        </s:if>
        <s:else>
            <div align="center" style="vertical-align:top; padding-top:10px;">
                <tags:submit targets="bodyContent" formId="portFormatForm"
                             confirm="true" confirmText="MSG.INF.00027"
                             showLoadingText="true" cssStyle="width:85px;"
                             value="MSG.generates.addNew" preAction="portFormatAction!addPortFormat.do"
                             validateFunction="validatePortFormat()"
                             />
                <tags:submit targets="bodyContent" formId="portFormatForm"
                             confirm="false"
                             showLoadingText="true" cssStyle="width:85px;"
                             value="MSG.find" preAction="portFormatAction!searchPortFormat.do"
                             />

            </div>



        </s:else>

    </s:form>
</tags:imPanel>
<br />
<div id="portFormatList">
    <tags:imPanel title="MSG.port.identifier.list">
        <jsp:include page="portFormatList.jsp"/>
    </tags:imPanel>
</div>



<script type="text/javascript" language="javascript">

    validatePortFormat = function(){
        if (trim($('portFormatForm.eqCode').value) == ""){
//            $('returnMsgClient').innerHTML ='<s:property escapeJavaScript="true"  value="getError('ERR.INF.103')" />';
            $('returnMsgClient').innerHTML ='<s:text name="ERR.INF.103" />';
            $('portFormatForm.eqCode').focus();
            $('portFormatForm.eqCode').select();
            return false;
        }
        if (trim($('portFormatForm.type').value) == ""){
//            $('returnMsgClient').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.INF.104')" />';
            $('returnMsgClient').innerHTML = '<s:text name="ERR.INF.104" />';
            $('portFormatForm.type').focus();
            $('portFormatForm.type').select();
            return false;
        }
        if (trim($('portFormatForm.status').value) == ""){
//            $('returnMsgClient').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.INF.105')" />';
            $('returnMsgClient').innerHTML = '<s:text name="ERR.INF.105" />';
            $('portFormatForm.status').focus();
            $('portFormatForm.status').select();
            return false;
        }
        
        if (trim($('portFormatForm.portFormat').value) == ""){
//            $('returnMsgClient').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.INF.106')" />';
            $('returnMsgClient').innerHTML = '<s:text name="ERR.INF.106" />';
            $('portFormatForm.portFormat').focus();
            $('portFormatForm.portFormat').select();
            return false;
        }


        
        return true;
    }

    prepareEditPortFormat = function(id) {
        //        gotoAction('bodyContent','portFormatAction!prepareEditPortFormat.do?eqId=' + eqId + "&portFormat="+portFormat);
        gotoAction('bodyContent','portFormatAction!prepareEditPortFormat.do?' + id);
    }

    deletePortFormat = function(id) {
//        if(confirm(getUnicodeMsg('<s:property escapeJavaScript="true"  value="getError('MSG.INF.00028')" />'))){
        if(confirm(getUnicodeMsg('<s:text name="MSG.INF.00028" />'))){
            //gotoAction('bodyContent','portFormatAction!deletePortFormat.do?eqId=' + eqId + "&portFormat="+portFormat);
            gotoAction('bodyContent','portFormatAction!deletePortFormat.do?' + id + '&' + token.getTokenParamString());
        }
    }

   

</script>

