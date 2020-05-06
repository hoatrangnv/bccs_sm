<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : changeDamagedGoods
    Created on : Mar 23, 2009, 5:29:53 PM
--%>

<%--
    Note: doi hang hong
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%
            request.setAttribute("contextPath", request.getContextPath());
            String require = "&nbsp;<font color='red'>(*)</font>";
            request.setAttribute("require", require);
%>
<s:form action="changeDamagedGoodsAction" theme="simple" method="post" id="changeDamagedId">
<s:token/>

    <!-- phan nhap thong tin hang doi -->
    <tags:imPanel title="MSG.import.damage.goods">

        <table class="inputTbl4Col">
            <tr>
                <td class="label" style="width:10%"><tags:label key="MSG.imType" required="true"/></td>
                <td class="text" colspan="7" style="width:90%">
                    <tags:imRadio name="impType"
                                  id="impType"
                                  list="2:MSG.imBySerial,1:MSG.imByFile"
                                  onchange="radioClick(this.value);"/>
                </td>
            </tr>
            <tr>
                <td class="label">
                    <tags:label key="MSG.selectStockType" required="true"/>
                </td>
                <td class="text">

                    <s:select name="stockTypeId"
                              id="stockTypeId" theme="simple"
                              cssClass="txtInput"
                              headerKey="" headerValue="%{getText('MSG.stock.select.goods.type')}"
                              list="%{#request.lstStockTypes != null?#request.lstStockTypes :# {}}"
                              listKey="stockTypeId" listValue="name"
                              onchange="updateCombo('stockTypeId','stockId','%{#request.contextPath}/changeDamagedGoodsAction!getStockModel.do');"/>
                </td>
                <td class="label">
                    <tags:label key="MSG.stock.select.goods" required="true"/>
                </td>
                <td class="text">
                    <s:select name="stockId"
                              id="stockId" theme="simple"
                              cssClass="txtInput"
                              headerKey="" headerValue="%{getText('MSG.stock.select.goods')}"
                              list="%{#request.lstStockModel != null?#request.lstStockModel:# {}}"
                              listKey="stockModelId" listValue="name"
                              />

                </td>
            </tr>
            <s:if test="#request.importsucess!=null"  >
                <tr>
                <td class="label"><tags:label key="MSG.oldSerial" required="true"/></td>
                <td class="text">
                    <s:textfield name="oldSerial" id="oldSerial" maxlength="20" cssClass="txtInput" readonly="true"/>
                </td>
                <td class="label"><tags:label key="MSG.newSerial" required="true"/></td>
                <td class="text">
                    <s:textfield name="newSerial" id="newSerial" maxlength="20" cssClass="txtInput" readonly="true"/>
                </td>
                </tr>
            </s:if>

            <s:else>
                <tr>

                    <td class="label"><tags:label key="MSG.oldSerial" required="true"/></td>
                    <td class="text">
                        <s:textfield name="oldSerial" id="oldSerial" maxlength="20" cssClass="txtInput"/>
                    </td>
                    <td class="label"><tags:label key="MSG.newSerial" required="true"/></td>
                    <td class="text">
                        <s:textfield name="newSerial" id="newSerial" maxlength="20" cssClass="txtInput"/>
                    </td>
                </tr>
            </s:else>


            <s:if test="#request.importsucess!=null"  >
                <tr id="trImpByFile">
                    <td  class="label" style="width:10%"><tags:label key="MSG.generates.file_data" required="true"/></td>
                    <td class="text" colspan="7" style="width:90%">
                        <tags:imFileUpload name="clientFileName" id="clientFileName" serverFileName="serverFileName" extension="xls" cssStyle="width:500px;"/>
                        <a onclick=downloadPatternFile()>${fn:escapeXml(template_download_file)}</a>
                    </td>

                </tr>
            </s:if>
            <s:else>
                <tr id="trImpByFile" style="display:none">
                    <td  class="label" style="width:10%"><tags:label key="MSG.generates.file_data" required="true"/></td>
                    <td class="text" colspan="7" style="width:90%">
                        <tags:imFileUpload name="clientFileName" id="clientFileName" serverFileName="serverFileName" extension="xls" cssStyle="width:500px;"/>
                        <a onclick=downloadPatternFile()>${fn:escapeXml(template_download_file)}</a>
                    </td>

                </tr>
            </s:else>
            <tr>
                <td colspan="4" align="center">
                    <tags:submit  formId="changeDamagedId" showLoadingText="true" confirm="true" confirmText="MSG.confirm.change" value="MSG.changeGoods"
                                  cssStyle="width:80px;"
                                  targets="bodyContent" preAction="changeDamagedGoodsAction!saveChangeDamagedGoods.do" validateFunction="validateForm()" />

                </td>
            </tr>
            <tr>
                <td colspan="4"  align="center">
                    <tags:displayResult id="returnMsgChangeDamagedGoods" property="returnMsg" propertyValue="returnMsgValue"/>
                </td>
            </tr>

            <div align="center">
                <s:if test="#request.requestFilePath != null">
                    <script>
                        window.open('${contextPath}/${fn:escapeXml(requestFilePath)}','','toolbar=yes,scrollbars=yes');
                    </script>
                    <td colspan="4"  align="center">
                        <a href="${contextPath}/${fn:escapeXml(requestFilePath)}">

                            <tags:displayResult id="requestFileMessage" property="requestFileMessage"/>

                        </a>
                    </td>
                </s:if>
            </div>

        </table>
    </tags:imPanel>
    <s:token />
</s:form>
<script type="text/javascript">
    validateForm= function(){
        var clientFileName = document.getElementById("clientFileName");
        //$('requestFileMessage').innerHTML='';
        if($('stockTypeId').value==''){
            $('returnMsgChangeDamagedGoods').innerHTML= '<s:property escapeJavaScript="true"  value="getText('ERR.SAE.031')"/>';
            //$('returnMsgChangeDamagedGoods').innerHTML="Bạn chưa chọn loại hàng hoá";
            $('stockTypeId').focus();
            return false;
        }
        if($('stockId').value==''){
            $('returnMsgChangeDamagedGoods').innerHTML= '<s:property escapeJavaScript="true"  value="getText('ERR.SAE.032')"/>';
            //$('returnMsgChangeDamagedGoods').innerHTML="Bạn chưa chọn hàng hoá";
            $('stockId').focus();
            return false;
        }
        if($('oldSerial').value=='' && $('impType2').checked == true ){
            $('returnMsgChangeDamagedGoods').innerHTML= '<s:property escapeJavaScript="true"  value="getText('ERR.SAE.033')"/>';
            //$('returnMsgChangeDamagedGoods').innerHTML="Bạn chưa nhập serial hỏng";
            $('oldSerial').focus();
            return false;
        }
        if((!isNum($('oldSerial').value) || $('oldSerial').value*1<=0) && $('impType2').checked == true){
            $('returnMsgChangeDamagedGoods').innerHTML= '<s:property escapeJavaScript="true"  value="getText('ERR.SAE.034')"/>';
            //$('returnMsgChangeDamagedGoods').innerHTML="Trường serial hỏng phải là số nguyên dương";
            $('oldSerial').focus();
            return false;
        }
        if($('newSerial').value=='' && $('impType2').checked == true){
            $('returnMsgChangeDamagedGoods').innerHTML= '<s:property escapeJavaScript="true"  value="getText('ERR.SAE.035')"/>';
            //$('returnMsgChangeDamagedGoods').innerHTML="Bạn chưa nhập serial đổi lại";
            $('newSerial').focus();
            return false;
        }
        if((!isNum($('newSerial').value) || $('newSerial').value*1<=0) && $('impType2').checked == true){
            $('returnMsgChangeDamagedGoods').innerHTML= '<s:property escapeJavaScript="true"  value="getText('ERR.SAE.036')"/>';
            //$('returnMsgChangeDamagedGoods').innerHTML="Trường serial đổi lại phải là số";
            $('newSerial').focus();
            return false;
        }

        if( ($('newSerial').value == $('oldSerial').value) && $('impType2').checked == true){
            $('returnMsgChangeDamagedGoods').innerHTML= '<s:property escapeJavaScript="true"  value="getText('ERR.SAE.037')"/>';
            //$('returnMsgChangeDamagedGoods').innerHTML="Trường serial đổi lại phải khác serial hỏng";
            $('newSerial').focus();
            return false;
        }

        if (trim(clientFileName.value).length ==0 &&  $('impType1').checked == true){
            $('returnMsgChangeDamagedGoods').innerHTML='<s:property escapeJavaScript="true"  value="getText('MSG.SMS.007')"/>';
            clientFileName.focus();
            return false;
        }

        return true;
    }

    radioClick = function(value){
        if(value == 1) {
//            document.getElementById('oldSerial').readOnly="true";
//            document.getElementById('newSerial').readOnly="true";
            document.getElementById('oldSerial').disabled=true;
            document.getElementById('newSerial').disabled=true;
            $('trImpByFile').style.display = "";
            // document.getElementById('trImpByFile').readOnly="";

        }
        else if (value == 2)  {
            //$('trImpByFile').style.display = "none";
            //document.getElementById('trImpByFile').readOnly="true";
            $('trImpByFile').style.display="none";
            document.getElementById('oldSerial').readOnly="";
            document.getElementById('newSerial').readOnly="";
            document.getElementById('oldSerial').disabled=false;
            document.getElementById('newSerial').disabled=false;
        }
    }

    downloadPatternFile = function() {
        window.open('${contextPath}/share/pattern/tempate_import_change_goods.xls','','toolbar=yes,scrollbars=yes');
    }

</script>

