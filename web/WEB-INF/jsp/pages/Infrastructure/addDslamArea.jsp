<%-- 
    Document   : addDslamArea
    Created on : May 15, 2010, 4:58:27 PM
    Author     : tronglv
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>


<%
        request.setAttribute("contextPath", request.getContextPath());
%>

<s:form action="assignDslamAreaAction" theme="simple" enctype="multipart/form-data"  method="post" id="form">
<s:token/>


    <s:if test="#request.recoverInvoice == true">
        <script type="text/javascript" language="javascript">
            window.close();
        </script>
    </s:if>

    <div style="width:100%">
        <tags:imPanel title="MSG.INF.038">
            <table class="inputTbl2Col">
                <tr>
                    <td>
                        
                    </td>
                </tr>

            </table>
        </tags:imPanel>
    </div>
</s:form>    
