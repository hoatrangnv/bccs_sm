<%-- 
    Document   : changeISDN
    Created on : Apr 21, 2015, 2:37:31 PM
    Author     : minhtn7
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib prefix="imDef" uri="imDef" %>

<div style="width: 700px;">
    <s:form action="simtkManagmentAction" theme="simple" method="post" id="form">
        <s:token/>

        <tags:imPanel title="MSG.SIK.273">
            <s:hidden name="form.vfSimtk.accountId" id="form.vfSimtk.accountId"/>
            <s:hidden name="form.vfSimtk.idNo" id="form.vfSimtk.idNo"/>
            <s:hidden name="form.vfSimtk.msisdn" id="form.vfSimtk.msisdn"/>
            <div>
                <tags:displayResult id="messageShow" property="messageShow" propertyValue="paramMsg" type="key" />
            </div>
            <table class="inputTbl4Col" style="width:100%;">
                <tr>
                    <td class="label"><tags:label key="New ISDN"/></td>
                    <td class="text">
                        <s:textfield name="form.vfSimtk.newMsisdn" id="form.vfSimtk.newMsisdn"
                                     theme="simple" maxlength="30"  cssClass="txtInputFull" />
                    </td>                
                </tr>
               <tr>     
                    <td class="label">OTP</td>
                    <td class="text">
                        <s:textfield  id="form.vfSimtk.otp" name="form.vfSimtk.otp" theme="simple" maxlength="20" cssClass="txtInputFull" />
                    </td>
                </tr>
            </table>
            <br/>
            <div>
                <input type="button" onclick="getOTP();" value="<s:property escapeJavaScript="false"  value="getText('MSG.SAE.215')"/>" style="width: 120px;"/>
                <input type="button" onclick="changeISDN();"  value="<s:property escapeJavaScript="false"  value="getText('MSG.SIK.273')"/>" style="width: 120px;"/>
                <input type="button" onclick="window.close();" value="<s:property escapeJavaScript="false"  value="getText('Close')"/>" style="width: 120px;"/>
            </div>
            <br/>
            <div>
                <tags:label key="msg.retail.getotp.turial1"/>
                <br/>
                <tags:label key="msg.retail.getotp.turial2"/>
                <br/>
                <tags:label key="msg.retail.getotp.turial3"/>
                <br/>
                <tags:label key="msg.retail.getotp.turial4"/>
            </div>
        </tags:imPanel>
    </s:form>
</div>

<script type="text/javascript" language="javascript">
    changeISDN = function () {
        if (!validate()) {
            event.cancel = true;
        }
        var strConfirm = getUnicodeMsg('<s:property escapeJavaScript="true"  value="getText('Are you sure to change ISDN?')"/>');
        if (!confirm(strConfirm)) {
            event.cancel = true;
        }
        document.getElementById("form").action = "simtkManagmentAction!changeISDN.do";
        document.getElementById("form").submit();
    }
    validate = function () {
        if (trim($('form.vfSimtk.newMsisdn').value) === '') {
            $('messageShow').innerHTML = '<s:text name ="E.200017"/>';
            $('form.vfSimtk.newMsisdn').focus();
            $('form.vfSimtk.newMsisdn').select();
            return false;
        }
        
        if (trim($("form.vfSimtk.otp").value) == ''){
            $('form.vfSimtk.otp').select();
            $('form.vfSimtk.otp').focus();
            $('messageShow').innerHTML='<s:text name ="msg.retail.getotp.require"/>';
            return false;
        }
            
//            if (trim($('form.vfSimtk.newIccid').value) == ''){
//                $('messageShow').innerHTML='<s:text name ="Error. You must input serial!"/>';
//                $('form.vfSimtk.newIccid').focus();
//                $('form.vfSimtk.newIccid').select();
//                return false;
//            }
        return true;
    }
    
    getSerial = function () {

    }
    
    getOTP = function () {
        if (!checkValidateGetOTP()) {
            event.cancel = true;
        }
        var strConfirm = getUnicodeMsg('<s:property escapeJavaScript="true"  value="getText('MSG.SAE.206')"/>');
        if (!confirm(strConfirm)) {
            event.cancel = true;
        }
        document.getElementById("form").action = "simtkManagmentAction!getOTP.do";
        document.getElementById("form").submit();
    }
    
    checkValidateGetOTP = function(){
        if(trim($('form.vfSimtk.newMsisdn').value) == ""){            
            $('messageShow').innerHTML = '<s:text name="saleRetail.warn.custMobile"/>';
            $('form.vfSimtk.newMsisdn').select();
            $('form.vfSimtk.newMsisdn').focus();
            return false;
        }
        $('messageShow').innerHTML = "";
        return true;    
    }
</script>        
