<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<html> 
    <head>
        <title><%--<tiles:getAsString name="title"/>--%></title>


        <script>




            var title='<tiles:getAsString name="title" ignore="true"/>';
            if(title!=null && title!=''){

            <s:set name="key">
                <tiles:getAsString name='title' ignore="true"/>
            </s:set>

                    //document.title=getUnicodeMsg('<s_:property value="getText(#attr.key)"/>');
                    document.title=getUnicodeMsg('<s:text name="%{#attr.key}"/>');
                    //document.getElementById("myHeader").innerHTML=getUnicodeMsg('<s_:property value="getText(#attr.key)"/>');
                    document.getElementById("myHeader").innerHTML=getUnicodeMsg('<s:text name="%{#attr.key}"/>');
            
                    //document.title=title;
                    //document.getElementById("myHeader").innerHTML=title;
                }
        </script>
        <%--<tiles:insertTemplate template="/WEB-INF/jsp/pages/layout/external_CSS_JS.jsp" />--%>
    </head>

    <sx:head parseContent="true" cache="true"/>

    <body id="minwidth" topmargin="0" >

        <script language="javascript">
            //disableStart();
        </script>

        <tags:displayResult id="msgResultJavaScript" propertyValue="msgResultJavaScriptParam" property="msgResultJavaScript" type="key"/>
        <tiles:insertAttribute name="body"/>

    </body>
</html>