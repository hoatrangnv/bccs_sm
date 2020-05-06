<%-- 
    Document   : brasIpPool
    Created on : May 19, 2010, 3:26:35 PM
    Author     : tronglv
--%> 

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<%@taglib uri="VTdisplaytaglib" prefix="display"%>

<%
//            if (request.getAttribute("lstBrasIppoolStatus") == null) {
//                request.setAttribute("lstBrasIppoolStatus", request.getSession().getAttribute("lstBrasIppoolStatus"));
//            }
            
            request.setAttribute("contextPath", request.getContextPath());
%>

<s:form action="brasIppoolAction" theme="simple" enctype="multipart/form-data"  method="post" id="brasIppoolForm">
<s:token/>

    <tags:imPanel title="MSG.bars.ippool.info">

    <!-- hien thi message -->
    <div>
        <tags:displayResult id="returnMsgClient" property="returnMsg" propertyValue="returnMsgValue" type="key"/>
    </div>

    <!-- thong tin bras -->
    
        <s:hidden name="brasIppoolForm.poolId" id="brasIppoolForm.poolId"/>

        <table class="inputTbl4Col" style="width:100%" >
            <tr>
                <td class="label"><tags:label key="MSG.ip" required="true"/> </td>
                <td class="text">
                    <s:textfield name="brasIppoolForm.ipPool" id="brasIppoolForm.ipPool" maxlength="20" cssClass="txtInputFull" theme="simple"/>
                </td>
                <td class="label"><tags:label key="MSG.pool.name" required="true"/> </td>
                <td class="text">
                    <s:textfield name="brasIppoolForm.poolname" id="brasIppoolForm.poolname" maxlength="20" cssClass="txtInputFull" theme="simple"/>
                </td>
            </tr>
            <tr>
                <td class="label"><tags:label key="MSG.domain" required="true"/> </td>
                <td class="text">
                    <tags:imSearch codeVariable="brasIppoolForm.domain" nameVariable="brasIppoolForm.domainDescription"
                                       codeLabel="MSG.domain" nameLabel="MSG.discription"
                                       searchClassName="com.viettel.im.database.DAO.BrasIppoolDAO"
                                       searchMethodName="getListDomain"
                                       getNameMethod="getListDomainName"
                                       roleList=""/>
                </td>
                <td class="label"><tags:label key="MSG.AP.Bras_ip" required="true"/> </td>
                    <td class="text">
                        <tags:imSearch codeVariable="brasIppoolForm.brasIp" nameVariable="brasIppoolForm.brasAaaIp"
                                       codeLabel="MSG.ip" nameLabel="MSG.AAA_IP"
                                       searchClassName="com.viettel.im.database.DAO.BrasIppoolDAO"
                                       searchMethodName="getListBrasIp"
                                       getNameMethod="getListBrasIpName"
                                       roleList=""/>
                    </td>
            </tr>            
            <tr>
                <td class="label"><tags:label key="MSG.generates.status" required="true"/> </td>
                <td class="text">
                    <tags:imSelect name="brasIppoolForm.status" id="brasIppoolForm.status"
                              cssClass="txtInputFull" disabled="false"
                              list="lstBrasIppoolStatus"
                              headerKey="" headerValue="MSG.FAC.AssignPrice.ChooseStatus"
                              listKey="code" listValue="name"/>
                </td>
                <td class="label"><tags:label key="MSG.mask.ip" /></td>
                <td class="text">
                    <s:textfield name="brasIppoolForm.ipMask" id="brasIppoolForm.ipMask" maxlength="20" cssClass="txtInputFull" theme="simple"/>
                </td>
            <tr>
                <td class="label"><tags:label key="MSG.pooluniq" /></td>
                <td class="text">
                    <s:textfield name="brasIppoolForm.pooluniq" id="brasIppoolForm.pooluniq" maxlength="20" cssClass="txtInputFull" theme="simple"/>
                </td>
                <td class="label"><tags:label key="MSG.update.date" required="true"/> </td>
                <td class="text">
                    <tags:dateChooser property="brasIppoolForm.dateUpdate" styleClass="txtInputFull" id="brasIppoolForm.dateUpdate" readOnly="false"/>
                </td>
            </tr>
        </table>

                    <s:if test=" brasIppoolForm.poolId != null && (brasIppoolForm.poolId * 1) > 0 ">
                        <div align="center" style="vertical-align:top; padding-top:10px;">
                            <tags:submit targets="bodyContent" formId="brasIppoolForm"
                                         confirm="true" confirmText="MSG.INF.Confirm36"
                                         showLoadingText="true" cssStyle="width:85px;"
                                         value="MSG.generates.edit" preAction="brasIppoolAction!editBrasIppool.do"
                                         validateFunction="validateBrasIppool()"
                                         />
                            <tags:submit targets="bodyContent" formId="brasIppoolForm"
                                         confirm="false"
                                         showLoadingText="true" cssStyle="width:85px;"
                                         value="MSG.INF.047" preAction="brasIppoolAction!cancelEditBrasIppool.do"
                                         />
                        </div>
                    </s:if>
                    <s:else>
                        <div align="center" style="vertical-align:top; padding-top:10px;">
                            <tags:submit targets="bodyContent" formId="brasIppoolForm"
                                         confirm="true" confirmText="MSG.INF.Confirm35"
                                         showLoadingText="true" cssStyle="width:85px;"
                                         value="MSG.generates.addNew" preAction="brasIppoolAction!addBrasIppool.do"
                                         validateFunction="validateBrasIppool()"
                                         />
                            <tags:submit targets="bodyContent" formId="brasIppoolForm"
                                         confirm="false"
                                         showLoadingText="true" cssStyle="width:85px;"
                                         value="MSG.find" preAction="brasIppoolAction!searchBrasIppool.do"
                                         />

                        </div>


                    </s:else>
    
</tags:imPanel>

<br />
<div id="brasIpPoolList">
    <tags:imPanel title="MSG.bars.ippool.list">
        <jsp:include page="brasIpPoolList.jsp"/>
    </tags:imPanel>
</div>

</s:form>

<script type="text/javascript" language="javascript">

    validateBrasIppool = function(){        
        if (trim($('brasIppoolForm.ipPool').value) == ""){
            $('returnMsgClient').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.INF.045')" />';
            $('brasIppoolForm.ipPool').focus();
            $('brasIppoolForm.ipPool').select();
            return false;
        }
        if (trim($('brasIppoolForm.poolname').value) == ""){
            $('returnMsgClient').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.INF.046')" />';
            $('brasIppoolForm.poolname').focus();
            $('brasIppoolForm.poolname').select();
            return false;
        }
        if (trim($('brasIppoolForm.domain').value) == ""){
            $('returnMsgClient').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.INF.047')" />';
            $('brasIppoolForm.domain').focus();
            $('brasIppoolForm.domain').select();
            return false;
        }
        if (trim($('brasIppoolForm.brasIp').value) == ""){
            $('returnMsgClient').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.INF.048')" />';
            $('brasIppoolForm.brasIp').focus();
            $('brasIppoolForm.brasIp').select();
            return false;
        }
        if (trim($('brasIppoolForm.status').value) == ""){
            $('returnMsgClient').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.INF.049')" />';
            $('brasIppoolForm.status').focus();
            $('brasIppoolForm.status').select();
            return false;
        }

        var dateUpdate= dojo.widget.byId("brasIppoolForm.dateUpdate");
        if(dateUpdate.domNode.childNodes[1].value.trim() == ""){
            $( 'returnMsgClient' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.INF.050')" />';
            dateUpdate.domNode.childNodes[1].focus();
            return false;
        }

        if(dateUpdate.domNode.childNodes[1].value.trim() != "" &&
            !isDateFormat(dateUpdate.domNode.childNodes[1].value)){
            $( 'returnMsgClient' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.INF.051')" />';
            dateUpdate.domNode.childNodes[1].focus();
            return false;
        }

        return true;
    }



    prepareCopyBras = function(poolId) {
        gotoAction('bodyContent','brasIppoolAction!prepareCopyBras.do?' + poolId);
    }

    prepareEditBras = function(poolId) {
        gotoAction('bodyContent','brasIppoolAction!prepareEditBras.do?' + poolId);
    }
    
    deleteBras = function(poolId) {
        if(confirm(getUnicodeMsg('<s:property escapeJavaScript="true"  value="getError('MSG.INF.046')" />'))){
            gotoAction('bodyContent','brasIppoolAction!deleteBras.do?' + poolId + '&' + token.getTokenParamString());
        }
    }

</script>

