<%--
    Created on : Feb 18, 2009, 11:43:14 AM
    Author     : tutm1
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>

<%
            request.setAttribute("contextPath", request.getContextPath());

%>
<s:form action="unLockUserAction" theme="simple" method="POST" id="reqCardNotSaleFrom">
    <tags:imPanel title="UNLOCK.USER">
        <div>
            <tags:displayResult id="message" property="message" propertyValue="messageParam" type="key"  />
        </div>
        <table class="inputTbl4Col">
             <tr>
                    <s:hidden name="reqCardNotSaleFrom.reqId" id="reqCardNotSaleFrom.reqId"/>
                    <td class="label"><tags:label key="MSG.GOD.240" required="true" /><%--Loại Ly Do--%></td>
                    <td colspan="2">
                        <div class="text">
                            <tags:imSelect name="reqCardNotSaleFrom.reasonId" id="reqCardNotSaleFrom.reasonId" headerKey="" 
                                           headerValue="label.option" 
                                           cssClass="txtInputFull"
                                           list="listReasonUnLockUser"
                                           listKey="reasonId" 
                                           listValue="reasonName"
                                           />
                        </div>
                    </td>
              </tr>
              <tr>
                    <td class="label"><tags:label key="lbl.file_cong_van_dinh_kem" required="true" /><font color="red">(select file .pdf)</td>
                    <td class="text">
                        <tags:imFileUpload name="reqCardNotSaleFrom.clientFileName" id="reqCardNotSaleFrom.clientFileName" serverFileName="reqCardNotSaleFrom.serverFileName" extension="pdf" />
                    </td>
              </tr>
        </table>
    </tags:imPanel>
</s:form>
<div align="center" class="button">
    <input type="button" value="<s:property value="getText('MSG.SIK.059')"/>" style="width:80px;" onclick="unLockUser()">
</div>

<script type="text/javascript" language="javascript">

    function unLockUser() {
       if (checkValidFields()) {
          setAction("reqCardNotSaleFrom", "unLockUserAction!unLockUser.do?" + token.getTokenParamString()); 
       }
    }
    
     checkValidFields = function() {
           
            var reasonId = document.getElementById("reqCardNotSaleFrom.reasonId");
            if (reasonId.value == ''){
                <%--"Bạn chưa nhap ly do"--%>
                $('message').innerHTML = '<s:property value="getText('err.input.reason')"/>';
                reasonId.focus();
                return false;
            }
            
            //ten file ko duoc de trong
            var clientFileName = document.getElementById("reqCardNotSaleFrom.clientFileName");
            if (trim(clientFileName.value).length ==0){
                <%--$('resultViewChangeStaffInShopClient').innerHTML="Bạn chưa chọn file cần tạo"--%>
                $('message').innerHTML = '<s:property value="getText('ERR.GOD.067')"/>';
                //$('message').innerHTML = "!!!Lỗi. Bạn chưa chọn file dữ liệu";
                clientFileName.focus();
                return false;
            }
            
            return true;
      }
</script>
