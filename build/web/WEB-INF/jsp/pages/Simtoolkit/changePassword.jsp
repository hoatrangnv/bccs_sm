<%-- 
    Document   : changePassword
    Created on : Feb 1, 2012, 9:15:54 AM
    Author     : TrongLV
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib prefix="imDef" uri="imDef" %>

<div style="width: 700px; height: 150px;">
<s:form action="simtkManagmentAction" theme="simple" method="post" id="form">
<s:token/>

    <tags:imPanel title="MSG.SIK.250">
        <s:hidden name="form.vfSimtk.accountId" id="form.vfSimtk.accountId"/>
        <div>
            <tags:displayResult id="messageShow" property="messageShow" propertyValue="paramMsg" type="key" />
        </div>
        <table class="inputTbl4Col" style="width:100%;">
            <tr>
                <td class="label"><tags:label key="Input mpin"/></td>
                <td class="text">
                    <s:password name="form.vfSimtk.mpin" id="form.vfSimtk.mpin"
                                 theme="simple" maxlength="10"  cssClass="txtInputFull" readonly = "false" />
                </td>                
            </tr>
             <tr>     
                <td class="label"><tags:label key="Input mpin again"/></td>
                <td class="text">
                    <s:password name="form.vfSimtk.mpin2" id="form.vfSimtk.mpin2"
                                 theme="simple" maxlength="10"  cssClass="txtInputFull" readonly = "false" />
                </td>
            </tr>
        </table>
<br/>
        <div>
            <input type="button" onclick="changePassword();"  value="<s:property escapeJavaScript="true"  value="getText('MSG.SIK.056')"/>" style="width: 120px;"/>
            <input type="button" onclick="window.close();" value="<s:property escapeJavaScript="true"  value="getText('Close')"/>" style="width: 120px;"/>
        </div>
    </tags:imPanel>
</s:form>
</div>

<script type="text/javascript" language="javascript">
        changePassword = function() {
            if (!validate()){
                event.cancel = true;
            }
            var strConfirm = getUnicodeMsg('<s:property escapeJavaScript="true"  value="getText('Are you sure to change mpin?')"/>');
            if (!confirm(strConfirm)){
                event.cancel = true;
            }
            document.getElementById("form").action="simtkManagmentAction!changePassword.do";
            document.getElementById("form").submit();
        }
        validate = function(){
            if (trim($('form.vfSimtk.mpin').value) == ''){
                $('messageShow').innerHTML='<s:text name ="Error. You must input mpin!"/>';
                $('form.vfSimtk.mpin').focus();
                $('form.vfSimtk.mpin').select();
                
                return false;
            }
            if (trim($('form.vfSimtk.mpin2').value) == ''){
                $('messageShow').innerHTML='<s:text name ="Error. You must input mpin again!"/>';
                $('form.vfSimtk.mpin2').focus();
                $('form.vfSimtk.mpin2').select();
                return false;
            }
            if (trim($('form.vfSimtk.mpin').value) != trim($('form.vfSimtk.mpin2').value)){
                $('messageShow').innerHTML='<s:text name ="Error. Mpin must be same!"/>';
                $('form.vfSimtk.mpin').focus();
                $('form.vfSimtk.mpin').select();
                return false;
            }
            return true;
        }
</script>        
