<%-- 
    Document   : GoodsRecover
    Created on : Sep 25, 2009, 8:50:26 AM
    Author     : Vunt
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ page import="com.guhesan.querycrypt.QueryCrypt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<s:form action="CommonStockAction"  theme="simple" enctype="multipart/form-data"  method="POST" id="goodsForm">
<s:token/>

    <fieldset style="width:100%">        
        <table style="width:100%" cellspacing="4" cellpadding="0">
            <tr>
                <td style="width:50%; vertical-align:top">
                    <div id="stockGoods">
                        <jsp:include page="StockGoodsRecover.jsp"/>
                    </div>
                </td>
                <td  style="width:50%; vertical-align:top">
                    <sx:div id="listGoods" >
                        <jsp:include page="ListGoodsRecover.jsp"/>

                    </sx:div>

                </td>
            </tr>
        </table>
    </fieldset>
</s:form>
