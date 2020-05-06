<%--Chi tra tien dat coc cho dai ly--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>


<s:form action="createAgentDepositRecieptAction" id="DepositCreateReceiptFormId" method="post" theme="simple" >
<s:token/>

    <fieldset style="width:100%;">
        <%--<legend class="transparent">Thông tin đại lý</legend>--%>
        <tags:imPanel  title="mesages.info.agent">
            <table class="inputTbl6Col">
                <tr>
                    <td class="label"> <tags:label key="MSG.branch"/></td>
                    <td class="text">
                        <s:textfield name="branch" id="branch" readonly="true" size="20" cssClass="txtInput"/>
                    </td>
                    <td class="label"><tags:label key="MSG.shopId" required="true"/></td>
                    <td class="text">
                        <s:url id="optionsUrlAgent"  action="viewAutoCompleterForAgent" method="getAutoCompleterAgentAction" />
                        <sx:autocompleter
                            name="agentCode" valueNotifyTopics="/valueAgent"
                            id="autoAgentId" href="%{#optionsUrlAgent}"
                            loadOnTextChange="true" loadMinimumCount="0"
                            cssClass="txtInput" size="20"/>
                    </td>
                    <td class="label">
                        <tags:label key="MSG.agent.name"/>
                    </td>
                    <td colspan="2" class="text">
                        <s:textfield name="codeAgentName" id="codeAgentNameId" readonly="true" cssClass="txtInput"/>
                    </td>
                    <td>
                        <%--<sx:submit parseContent="true" executeScripts="true"
                                   cssStyle="width:100px;"
                                   formId="DepositCreateReceiptFormId" loadingText="Ðang thực hiện..."
                                   showLoadingText="true" targets="bodyContent"
                                   value="Tìm kiếm"  beforeNotifyTopics="DepositCreateReceiptFormId/searchDepositExport"/>--%>
                        <tags:submit targets="bodyContent" formId="DepositCreateReceiptFormId"
                                     cssStyle="width:100px;" value="MSG.find"
                                     preAction="searchInformationDepositDetailAction!searchDepositExport.do"
                                     />
                    </td>
                </tr>
                <tr>
                    <td colspan="7">
                        <tags:displayResult id="displayResultMsgClient1" property="returnMsg1" propertyValue="returnMsgValue" type="key" />
                    </td>
                </tr>
            </table>
        </tags:imPanel>
    </fieldset>
</s:form>

<table class="inputTbl2Col">
    <tr>
        <td style="width:60%; vertical-align:top;">
            <%--<fieldset style="width:100%">--%>
            <%--<legend class="transparent">Thông tin đặt cọc của đại lý</legend>--%>
            <tags:imPanel title="MSG.info.deposit.agent">
                <br />
                <jsp:include page="agentDepositInfo.jsp"/>
                <sx:div id="informationDepositDetailId">
                    <jsp:include page="informationDepositDetail.jsp"  />
                </sx:div>
            </tags:imPanel>
            <%--</fieldset>--%>
        </td>
        <td style="vertical-align:top;">
            <%--<fieldset style="width:100%">--%>
            <jsp:include page="agentPayDepositInfo.jsp"/>
            <%--</fieldset>--%>
        </td>
    </tr>
</table>

<script language="javascript">
    var htmlTag = '<[^>]*>';
    var _myWidget1 = dojo.widget.byId("autoAgentId");
    _myWidget1.textInputNode.focus();


    dojo.event.topic.subscribe("/valueAgent", function(value, key, text, widget){
        document.getElementById("codeAgentNameId").value = key;

        //document.getElementById("companyNameId").value = key;
        //document.getElementById("tempCompanyNameId").value = key;
        //document.getElementById("companyId").value = value;
    });

    dojo.event.topic.subscribe("DepositCreateReceiptFormId/searchDepositExport", function(event, widget){
        if (!validateSearch()){
            event.cancel = true;
        }
        else
            widget.href = "searchInformationDepositDetailAction!searchDepositExport.do" + "?" + token.getTokenParamString();
    });

    validateSearch = function(){
        var _myWidget = dojo.widget.byId("autoAgentId");        
        if (_myWidget.textInputNode.value.trim().length==0)
        {   $('displayResultMsgClient1').innerHTML= '<s:text name="ERR.DET.039"/>';
            //$( 'displayResultMsgClient1' ).innerHTML = "Mã đại lý không được trống !";
            _myWidget.textInputNode.focus();
            return false;
        }
        
        if (_myWidget.textInputNode.value.trim().match(htmlTag) != null)
        {   $('displayResultMsgClient1').innerHTML= '<s:text name="ERR.DET.040"/>';
            //$( 'displayResultMsgClient1' ).innerHTML = "Không nên nhập thẻ html ở mã đại lý !";
            _myWidget.textInputNode.focus();
            _myWidget.textInputNode.select();
            return false;
        }
        
        return true;
    }   


</script>
