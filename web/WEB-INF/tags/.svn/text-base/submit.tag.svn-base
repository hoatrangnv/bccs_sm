<%-- 
    Document   : submit
    Created on : May 14, 2009, 10:36:28 AM
    Author     : ThanhDat 
--%>

<%@tag display-name="Viettel's submit tag" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<%@tag import="java.util.ResourceBundle"%>
<%@tag import="java.util.Locale"%>

<%@attribute name="id" %>
<%@attribute name="formId" required="true"%>
<%@attribute name="targets" %>
<%@attribute name="value" %>
<%@attribute name="showLoadingText" %>

<%@attribute name="preAction" required="true" %>
<%@attribute name="postAction" %>
<%@attribute name="validateFunction" %>
<%@attribute name="cssStyle" %>
<%@attribute name="cssClass" %>
<%@attribute name="confirm" %>
<%@attribute name="confirmText" %>
<%@attribute name="disabled" %>

<!-- hien thi progressDiv -->
<%@attribute name="showProgressDiv" %>
<%@attribute name="showProgressClass" %>
<%@attribute name="showProgressMethod" %>
<%@attribute name="showProgressIntervalTime" %>
<%@taglib prefix="imDef" uri="imDef" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
            com.viettel.database.DAO.BaseDAOAction baseDAOAction = new com.viettel.database.DAO.BaseDAOAction();
            //request.setAttribute("tagValue", baseDAOAction.getText(value));
            //request.setAttribute("tagConfirmText", baseDAOAction.getText(confirmText));
            request.setAttribute("tagValue", baseDAOAction.getText(value));
            request.setAttribute("tagConfirmText", baseDAOAction.getText(confirmText));
%>


<s:if test="#attr.disabled == 'true'">
    <span style="padding:0px; margin:0px;" id="btnCont">
        <input type="button" style="width:90px;" onclick="" value="${requestScope.tagValue}" disabled/>
    </span>
</s:if>
<s:else>
    <span style="padding:0px; margin:0px;" id="btnCont">
        <sx:submit parseContent="true" executeScripts="true" id="%{#attr.id}" formId="%{#attr.formId}"
                   afterNotifyTopics="%{'after_'+ #attr.preAction}"
                   showLoadingText="false" targets="%{#attr.targets}"                   
                   beforeNotifyTopics="%{'before_'+ #attr.preAction}"
                   errorNotifyTopics="errorAction"
                   value="%{#request.tagValue}"
                   cssClass="%{#attr.cssClass}"
                   cssStyle="%{#attr.cssStyle}">
        </sx:submit>
    </span>
</s:else>

<!-- hien thi progressDiv -->
<s:if test="#attr.showProgressDiv!=null && #attr.showProgressDiv=='true'">
    <tags:imProgress showProgressClass="${pageScope.showProgressClass}"
                     showProgressMethod="${pageScope.showProgressMethod}"
                     showProgressIntervalTime="${pageScope.showProgressIntervalTime}"/>
</s:if>

<script>

    blurSXSubmit = function() {
        var btn = $( 'btnCont' ).getElementsByTagName( 'input' )[0];
        btn.blur();
    }

    setFocus =function (){

        var formId ='<s:property value="%{#attr.formId}"/>';

        var forms =document.getElementById(formId);

        for(var i=0;i< forms.length;i++){
            if(forms.elements[i].type == 'text'){
                forms.elements[i].focus();
                break;
            }
        }

    }


    var notify_before = 'before_${pageScope.preAction}';

    var notify_after = 'after_${pageScope.preAction}';

    dojo.event.topic.subscribe(notify_after, function(event, widget){
    <s:if test="#attr.showLoadingText!=null && #attr.showLoadingText=='true'">
            resetProgress();
    </s:if>

    <s:if test="#attr.showProgressDiv!=null && #attr.showProgressDiv=='true'">
            //tamdt1, stop tien trinh update div
            stopProgressDiv();
    </s:if>

    <s:if test="#attr.postAction!=null">
            widget.href ='${pageScope.postAction}' + "?" + token.getTokenParamString();
    </s:if>
        });

        dojo.event.topic.subscribe(notify_before, function(event, widget){
    
    <s:if test="#attr.preAction!=null">

        <s:if test="#attr.validateFunction!=null">

                if(!eval('${pageScope.validateFunction}')){
                    event.cancel=true;
                    return;
                }
            <s:if test="#attr.confirm!=null && #attr.confirm=='true'">
                    var confirmText ="Bạn có thực sự muốn thực hiện hành động này?";
                <s:if test="#request.tagConfirmText!=null">

                        confirmText = "${fn:escapeXml(tagConfirmText)}";

                    <%--s:if test="#attr.confirmText!=null"--%>
                        
                </s:if>
                        if(!confirm(confirmText)){
                            event.cancel=true;
                            return;
                        }
            </s:if>
       
            <s:if test="#attr.showLoadingText!=null && #attr.showLoadingText=='true'">
                    blurSXSubmit();
                    initProgress();
                    // setFocus();
            </s:if>

            <s:if test="#attr.showProgressDiv!=null && #attr.showProgressDiv=='true'">
                    //tamdt1, hien thi thanh progress len tren
                    startProgressDiv();
                    showProgressDivOnTop();
            </s:if>
                    var vPreAction = '${pageScope.preAction}';
                    if (vPreAction.indexOf('?') > 0)  {
                        widget.href ='${pageScope.preAction}'+"&"+ token.getTokenParamString();
                    }

                    else {
                        widget.href ='${pageScope.preAction}'+"?"+ token.getTokenParamString();
                    }

//                    widget.href ='${pageScope.preAction}' + "?" + token.getTokenParamString();
        </s:if>
        <s:else>

            <s:if test="#attr.confirm!=null && #attr.confirm=='true'">
                    var confirmText ="Bạn có thực sự muốn thực hiện hành động này?";
                <s:if test="#request.tagConfirmText!=null">

                        confirmText = "${fn:escapeXml(tagConfirmText)}";
                        
                </s:if>
                        if(!confirm(confirmText)){
                            event.cancel=true;
                            return;
                        }
            </s:if>
            <s:if test="#attr.showLoadingText!=null && #attr.showLoadingText=='true'">
                    blurSXSubmit();
                    initProgress();
                    // setFocus();
            </s:if>

            <s:if test="#attr.showProgressDiv!=null && #attr.showProgressDiv=='true'">
                    //tamdt1, hien thi thanh progress len tren
                    startProgressDiv();
                    showProgressDivOnTop();
            </s:if>
                    var vPreAction = '${pageScope.preAction}';
                    if (vPreAction.indexOf('?') > 0)  {
                        widget.href ='${pageScope.preAction}'+"&"+ token.getTokenParamString();
                    }

                    else {
                        widget.href ='${pageScope.preAction}'+"?"+ token.getTokenParamString();
                    }
        </s:else>
    </s:if>
        });

</script>