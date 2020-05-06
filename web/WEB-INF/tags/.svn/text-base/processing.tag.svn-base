<%--
    Document   : loadingSearchAjax
    Created on : Dec 1, 2008, 5:38:05 PM
    Author     : Vu Viet Dinh
--%>

<%@tag description="put the tag description here" pageEncoding="UTF-8"%>
<%request.setAttribute("contextPath", request.getContextPath());%>
<script type="text/javascript" language="javascript">
    /*
     * USER DEFINED FUNCTIONS
     */


    function showMaskLayer(){
        var pageW = ( window.innerWidth ) ? window.innerWidth : ( document.documentElement.offsetWidth );
        var pageH = ( window.innerHeight ) ? window.innerHeight : ( document.documentElement.offsetHeight );
        var body = document.getElementsByTagName( 'body' )[0];

        var w = ( body.scrollWidth > pageW ) ? body.scrollWidth : pageW;
        var h = ( body.scrollHeight > pageH ) ? body.scrollHeight : pageH;

        $("maskLayer").style.width =  w + "px";
        $("maskLayer").style.height = h  + "px";
        $("maskLayer").style.display  = "block";
    }
    function hideMaskLayer(){
        $("maskLayer").style.display  = "none";
    }

    function initProgress() {       
        Element.show('start');
       // $('maskLayer').focus();
        showMaskLayer();

        var scrOfX = 0, scrOfY = 0;
        if( typeof( window.pageYOffset ) == 'number' ) {
            //Netscape compliant
            scrOfY = window.pageYOffset;
            scrOfX = window.pageXOffset;
        } else if( document.body && ( document.body.scrollLeft || document.body.scrollTop ) ) {
            //DOM compliant
            scrOfY = document.body.scrollTop;
            scrOfX = document.body.scrollLeft;
        } else if( document.documentElement && ( document.documentElement.scrollLeft || document.documentElement.scrollTop ) ) {
            //IE6 standards compliant mode
            scrOfY = document.documentElement.scrollTop;
            scrOfX = document.documentElement.scrollLeft;
        }

        var pageW = ( window.innerWidth ) ? window.innerWidth : ( document.documentElement.offsetWidth - 5 );
        var pageH = ( window.innerHeight ) ? window.innerHeight : ( document.documentElement.offsetHeight - 5 );

        var left = parseInt( scrOfX + ( pageW - $( 'start' ).offsetWidth ) / 2 );
        var top = parseInt( scrOfY + ( pageH - $( 'start' ).offsetHeight ) / 2 );

        $( 'start' ).style.zIndex = getHighestZIndex() + 1;
        $( 'start' ).style.left = left + 'px';
        $( 'start' ).style.top = top + 'px';


        //setTimeout("Effect.Shrink('start');", 2500);
    }

    function getHighestZIndex() {
        var allElems = document.getElementsByTagName ? document.getElementsByTagName("*") : document.all;
        var maxZIndex = 0;

        for(var i=0;i<allElems.length;i++) {
            var elem = allElems[i];
            var cStyle = null;

            if (elem.currentStyle) {
                cStyle = elem.currentStyle;
            }
            else if (document.defaultView && document.defaultView.getComputedStyle) {
                cStyle = document.defaultView.getComputedStyle(elem,"");
            }

            var sNum;
            if (cStyle) {
                sNum = Number(cStyle.zIndex);
            } else {
                sNum = Number(elem.style.zIndex);
            }
            if (!isNaN(sNum)) {
                maxZIndex = Math.max(maxZIndex,sNum);
            }
        }

        return maxZIndex;
    }

    function resetProgress() {
        hideMaskLayer();
        Element.hide('start');
        Element.show('successMsg');
        //setTimeout("Effect.DropOut('successMsg');", 25000);
        Element.hide('successMsg');

    }

    function reportError() {
        if ($F('mph') == "") {
            $('errorMsg').innerHTML = "You must enter a value";
            new Effect.Highlight('mph', {duration:5});
        } else {
            $('errorMsg').innerHTML = "Calculator busted!";
        }
        Element.show('errorMsg');
        setTimeout("Effect.DropOut('errorMsg')", 2500);
    }
</script>
<table style="width:100%;" >
    <tr>
        <td align="center">

        </td>
        <td>
            <div id="start" class="sb-preloader-table" style="display:none;position:absolute;left:0px;top:0px;">
                <table border="0" cellspacing="3" cellpadding="3" width="100%" height="100%">
                    <tr>
                        <td align="right" valign="middle" width="40%">
                            <img id="ImagePreloader" src="${contextPath}/share/img/loading.gif" style="border-width:0px;" />
                        </td>
                        <td align="left" valign="middle">
                            <span id="LabelPreloader" class="sb-preloader-text">                              
                                Đang xử lý ....                                
                            </span>
                        </td>
                    </tr>
                </table>
            </div>
        <td>
            <%--<div align="center" id="start" style="position:absolute;visibility:visible;display:none;border:1px solid #0e0;background-color:#efe;padding:2px;margin-top:8px;width:300px;font:normal 12px Arial;color:#090;height:20px"><img src="${contextPath}/share/img/indicator.gif"/> Processing....</div>--%>
            <div id="successMsg" style="display:none;border:1px solid #0e0;background-color:#efe;padding:2px;margin-top:8px;width:300px;font:normal 12px Arial;color:#090">Complete</div>
            <div id="errorMsg" style="display:none;border:1px solid #e00;background-color:#fee;padding:2px;margin-top:8px;width:300px;font:normal 12px Arial;color:#900"></div>
        </td>
        <td>

        </td>
    </tr>
</table>