/**
 * @author: Hoang Long
 * @class:  DOM
 */


DOM.prototype.brwInfo;
DOM.prototype.isIE;


function DOM() {
    var brwVer  = navigator.appVersion;
    var brwName = navigator.appName;
    var brwAgent = navigator.userAgent;

    this.brwInfo = [brwAgent, brwVer, brwName];
    this.isIE = ( brwVer.indexOf( 'MSIE' ) != -1 ) ? true : false;
}


// [ Array handlers
DOM.prototype.inArray = function ( val, arr )
{
    if( val == null || arr == null ) return false;
    if( arr.length == 0 ) return false;

    for( var i = 0; i < arr.length; i++ ) {
        if( arr[i] == val ) return true;
    }

    return false;
}

DOM.prototype.indexInArray = function( val, arr )
{
    if( val == null || arr == null ) return -1;
    if( arr.length == 0 ) return -1;

    for( var i = 0; i < arr.length; i++ ) {
        if( arr[i] == val ) return i;
    }

    return -1;
}

DOM.prototype.removeFromArray = function( val, arr )
{
    if( val == null || arr == null ) return null;
    if( arr.length == 0 ) return null;

    return arr.splice( this.indexInArray( val, arr ), 1 );
}

DOM.prototype.removeAllOptions = function( _obj ) {
    var i;
    var ele = this.obj( _obj );

    for( i = 0; i < ele.options.length; i++ ) {
        ele.removeChild( ele.options[i] );
    }
}
// ]


// [ DOM element handlers
DOM.prototype.$ = function( id ) { return document.getElementById( id ); }

DOM.prototype.obj = function( idOrObj )
{
    if( !idOrObj ) return null;

    var type = ( typeof idOrObj ).toLowerCase();
    var elem = null;

    if( type == "string" ) {
        elem = this.$( idOrObj );
    } else {
        elem = idOrObj;
    }

    if( !elem ) return null;
    return elem;
}

DOM.prototype.getElementsByAPartOfId = function()
{
    var rootObj, aPartOfId;

    switch( arguments.length ) {
        case 1:
            rootObj = document;
            aPartOfId = arguments[0];
            break;
        case 2:
            rootObj = arguments[0];
            aPartOfId = arguments[1];
            break;
    }

    var eles = rootObj.getElementsByTagName( "*" );
    var res = [];
    for( var i = 0; i < eles.length; i++ ) {
        var obj = eles[i];
        if( obj.id && obj.id.indexOf( aPartOfId ) != -1 ) {
            res.push( obj );
        }
    }

    return res;
}

DOM.prototype.getElementsByClass = function()
{
    var rootObj, className;

    switch( arguments.length ) {
        case 1:
            rootObj = document;
            className = arguments[0];
            break;
        case 2:
            rootObj = arguments[0];
            className = arguments[1];
            break;
    }

    var eles = rootObj.getElementsByTagName( "*" );
    var res = [];
    for( var i = 0; i < eles.length; i++ ) {
        var obj = eles[i];
        if( obj.className && obj.className == className ) {
            res.push( obj );
        }
    }

    return res;
}

DOM.prototype.style = function()
{
    var obj;
    obj = this.obj( arguments[0] );
    return obj.style;
}

DOM.prototype._value = function()
{
    var obj, val;
    obj = this.obj( arguments[0] );

    switch( arguments.length ) {
        case 1:
            break;
        case 2:
            val = arguments[1];
            obj.value = val;
            break;
    }

    return obj.value;
}

DOM.prototype._html = function()
{
    var obj, val;
    obj = this.obj( arguments[0] );

    switch( arguments.length ) {
        case 1:
            break;
        case 2:
            val = arguments[1];
            obj.innerHTML = val;
            break;
    }

    return obj.innerHTML;
}

DOM.prototype.disableElement = function( id, disabled )
{
    if( this.obj( id ) ) {
        this.obj( id ).disabled = disabled;
    }
}

DOM.prototype.disableElements = function()
{
    for( var i = 0; i < arguments.length; i++ ) {
        this.disableElements( arguments[i][0], arguments[i][1] );
    }
}

DOM.prototype.lockElements = function()
{
    for( var i = 0; i < arguments.length; i++ ) {
        this.disableElement( arguments[i], true );
    }
}

DOM.prototype.unlockElements = function()
{
    for( var i = 0; i < arguments.length; i++ ) {
        this.disableElement( arguments[i], false );
    }
}

DOM.prototype.displayElement = function( id, display )
{
    if( this.obj( id ) ) {
        this.obj( id ).style.display = ( display ) ? '' : 'none';
    }
}

DOM.prototype.displayElements = function()
{
    for( var i = 0; i < arguments.length; i++ ) {
        this.displayElement( arguments[i][0], arguments[i][1] );
    }
}

DOM.prototype.showElements = function()
{
    for( var i = 0; i < arguments.length; i++ ) {
        this.displayElement( arguments[i], true );
    }
}

DOM.prototype.hideElements = function()
{
    for( var i = 0; i < arguments.length; i++ ) {
        this.displayElement( arguments[i], false );
    }
}

DOM.prototype.isVisible = function( id )
{
    if( !this.obj( id ) ) return false;
    return !( this.obj( id ).style.display == 'none' );
}

DOM.prototype.setOpacity = function( obj, opacityAsInt )
{
    // Source: http://www.akxl.net/
    var elem = this.obj( obj );
    var opacityAsDecimal = opacityAsInt;

    if( !elem ) return false;

    if( opacityAsInt > 100 )
        opacityAsInt = opacityAsDecimal = 100;
    else if( opacityAsInt < 0 )
        opacityAsInt = opacityAsDecimal = 0;

    opacityAsDecimal /= 100;
    if ( opacityAsInt < 1 ) {
        opacityAsInt = 1; // IE7 bug, text smoothing cuts out if 0
    }

    elem.style.opacity = ( opacityAsDecimal );
    elem.style.MozOpacity = ( opacityAsDecimal );
    elem.style.filter  = "alpha(opacity=" + opacityAsInt + ")";

    return true;
}

DOM.prototype.getOpacity = function( obj )
{
    var elem = this.obj( obj );
    if( !elem ) return false;

    if( elem.style.opacity ) {
        return elem.style.opacity * 100;
    }
    else if( elem.style.MozOpacity ) {
        return elem.style.MozOpacity * 100;
    }

    return true;
}
// ]


// [ Form handlers
DOM.prototype.getForm = function(  ) {
    var obj;
    var needToEncode;
    
    obj = arguments[0];
    switch( arguments.length ) {
        case 1:
            needToEncode = true
            break;
        case 2:
            needToEncode = arguments[1];
            break;
    }

    var frm = this.obj( obj );
    var eles = frm.elements;
    var i, item, val;
    var paramArr = [];

    for( i = 0; i < eles.length; i++ ) {
        item = eles[i];
        if( item.nodeName.toString().toLowerCase() == 'input' ) {
            if( this.inArray( item.type.toString().toLowerCase(), ['text', 'hidden'] ) ) {
                val = ( needToEncode ) ? encodeURIComponent( item.value ) : item.value;
                paramArr.push( [item.name, val].join( "=" ) );
            }
            else if( this.inArray( item.type.toString().toLowerCase(), ['radio', 'checkbox'] ) ) {
                if( item.checked == true ) {
                    val = ( needToEncode ) ? encodeURIComponent( item.value ) : item.value;
                    paramArr.push( [item.name, val].join( "=" ) );
                }
            }
        }
        else if( this.inArray( item.nodeName.toString().toLowerCase(), ['select', 'textarea'] ) ) {
            val = ( needToEncode ) ? encodeURIComponent( item.value ) : item.value;
            paramArr.push( [item.name, val].join( "=" ) );
        }
    }

    return paramArr;
}

DOM.prototype.radio = function( obj )
{
    var elem = this.obj( obj );
    var form = null;
    var name = null;
    var radio = null;

    if( !elem || !elem.name ) return null;
    name = elem.name;
    form = elem.form;
    if( !form ) return null;
    eval( "radio = form." + name );

    for( var i = 0; i < radio.length; i++ ) {
        if( radio[i].checked == true ) {
            return radio[i].value;
        }
    }

    return null;
}

DOM.prototype.createHidden = function( formId, hiddenId, hiddenValue ) {
    var frm = this.obj( formId );
    var exist = false;

    for ( var i=0 ; i < frm.elements.length; i++ ) {
        if( frm.elements[i].type == "hidden" &&
            frm.elements[i].id == hiddenId ) {
            frm.elements[i].value = hiddenValue;
            exist = true;
            break;
        }
    }

    if( !exist ) {
        var hidden = document.createElement( 'input' );

        hidden.type = "hidden";
        hidden.name = hiddenId;
        hidden.id = hiddenId;
        hidden.value = hiddenValue;

        frm.appendChild( hidden );
    }
    //alert( 'hidden check = ' + hiddenId + ': ' + exist );
}

DOM.prototype.focusFirstElementInForm = function( id )
{
    var frm = this.obj( id );
    var allowedInputElement = [ 'text', 'password', 'radio', 'checkbox' ];
    var allowedElement = [ 'textarea', 'select' ];

    for ( var i = 0 ; i < frm.elements.length; i++ ) {
        if( ( this.inArray( frm.elements[i].type, allowedInputElement ) || this.inArray( frm.elements[i].nodeName.toLowerCase(), allowedElement ) ) &&
            !frm.elements[i].disabled && frm.elements[i].focus ) {
            frm.elements[i].focus();
            break;
        }
    }
}

DOM.prototype.focusLastElementInForm = function( id )
{
    var frm = this.obj( id );
    var allowedInputElement = [ 'text', 'password', 'radio', 'checkbox' ];
    var allowedElement = [ 'textarea', 'select' ];

    for ( var i = frm.elements.length - 1; i >= 0; i-- ) {
        if( ( this.inArray( frm.elements[i].type, allowedInputElement ) || this.inArray( frm.elements[i].nodeName.toLowerCase(), allowedElement ) ) &&
            !frm.elements[i].disabled && frm.elements[i].focus ) {
            frm.elements[i].focus();
            break;
        }
    }
}

DOM.prototype.resetForm = function()
{
    var id, exceptFieldIdArr;
    switch( arguments.length ) {
        case 1:
            id = arguments[0];
            exceptFieldIdArr = null;
            break;
        case 2:
            id = arguments[0];
            exceptFieldIdArr = arguments[1];
            break;
    }

    var frm = this.obj( id );

    if( exceptFieldIdArr == null || exceptFieldIdArr.length < 1 ) {
        for ( var i=0 ; i < frm.elements.length; i++ ) {
            if( frm.elements[i].type ) {
                if( frm.elements[i].type == 'text' || frm.elements[i].type == 'textarea' ) {
                    frm.elements[i].value = "";
                }
                else if( frm.elements[i].type.indexOf( 'select' ) != -1 ) {
                    frm.elements[i].selectedIndex = 0;
                }
                else if( frm.elements[i].type == 'checkbox' || frm.elements[i].type == 'radio' ) {
                    frm.elements[i].checked = false;
                }
            }
        }
    }
    else {
        for ( var i=0 ; i < frm.elements.length; i++ ) {
            if( frm.elements[i].type && !this.inArray( frm.elements[i].id, exceptFieldIdArr ) ) {
                if( frm.elements[i].type == 'text' || frm.elements[i].type == 'textarea' ) {
                    frm.elements[i].value = "";
                }
                else if( frm.elements[i].type.indexOf( 'select' ) != -1 ) {
                    frm.elements[i].selectedIndex = 0;
                }
                else if( frm.elements[i].type == 'checkbox' || frm.elements[i].type == 'radio' ) {
                    frm.elements[i].checked = false;
                }
            }
        }
    }
}

DOM.prototype.resetForms = function()
{
    for( var i = 0; i < arguments.length; i++ ) {
        resetForm( arguments[i] );
    }
}

DOM.prototype.disableForm = function()
{
    var id, disabled, exceptFieldIdArr;

    switch( arguments.length ) {
        case 2:
            id = arguments[0];
            disabled = arguments[1];
            exceptFieldIdArr = null;
            break;
        case 3:
            id = arguments[0];
            disabled = arguments[2];
            exceptFieldIdArr = arguments[1];
            break;
    }

    var frm = this.obj( id );

    if( exceptFieldIdArr == null || exceptFieldIdArr.length < 1 ) {
        for ( var i=0 ; i < frm.elements.length; i++ ) {
            frm.elements[i].disabled = disabled;
        }
    }
    else {
        for ( var i=0 ; i < frm.elements.length; i++ ) {
            if( !this.inArray( frm.elements[i].id, exceptFieldIdArr ) ) {
                frm.elements[i].disabled = disabled;
            }
        }
    }
}

DOM.prototype.disableForms = function()
{
    for( var i = 0; i < arguments.length; i++ ) {
        disableForm( arguments[i] );
    }
}
// ]


// [ Position / Coordinates / Shape handlers
DOM.prototype.getLeft = function()
{
    var oNode = this.obj( arguments[0] );
    var iLeft = 0;

    if( oNode == null ) return;

    while( oNode.tagName != "BODY" && oNode.tagName != "HTML" ) {
        iLeft += oNode.offsetLeft;
        oNode = oNode.offsetParent;
        if( oNode == null ) break;
    }

    return iLeft;
}

DOM.prototype.getTop = function()
{

    var oNode = this.obj( arguments[0] );
    var iTop = 0;

    if( oNode == null ) return;

    while(oNode.tagName != "BODY" && oNode.tagName != "HTML") {
        iTop += oNode.offsetTop;
        oNode = oNode.offsetParent;
        if( oNode == null ) break;
    }

    return iTop;
}

DOM.prototype.getMaxZIndex = function() {
	var allElems = document.getElementsByTagName ? document.getElementsByTagName("*") : document.all;
	var maxZIndex = 0;

	for(var i=0;i<allElems.length;i++) {
		var elem = allElems[i];
		var cStyle = null;

		if (elem.currentStyle) {
			cStyle = elem.currentStyle;
		}
		else if (document.defaultView && document.defaultView.getComputedStyle)
		{
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

DOM.prototype.gotoXY = function( _oNode, x, y ) {
    var oNode = this.obj( _oNode );
    if( arguments.length == 4 ) {
        oNode.style.zIndex = arguments[3];
    } 

	oNode.style.position = 'absolute';
	oNode.style.left = x + 'px';
	oNode.style.top = y + 'px';
}

DOM.prototype.getScrollXY = function() {
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
    //alert( scrOfX + ',' + scrOfY );
    return [ scrOfX, scrOfY ];
}

DOM.prototype.getPageWH = function() {
    var pageW = ( window.innerWidth ) ? window.innerWidth : ( document.documentElement.offsetWidth );
    var pageH = ( window.innerHeight ) ? window.innerHeight : ( document.documentElement.offsetHeight );
    var body = document.getElementsByTagName( 'body' )[0];

    var actualW;
    var actualH;

    if( body.scrollHeight > pageH ) {
        actualH = body.scrollHeight;
        actualW = body.scrollWidth;
    } else {
        actualH = pageH;
        actualW = pageW;

        // [ Stupid IE
        if( document.all ) {
            actualW = ( body.scrollWidth > pageW ) ? pageW : body.scrollWidth;
        }
        // ] Stupid IE
    }
    //alert( body.scrollWidth );
    return [ pageW, pageH, actualW, actualH ];
}
// ]


// [ Window / Dialog handlers
DOM.prototype.openDialog = function( url, _w, _h, modal )
{
    var brwVer  = navigator.appVersion;
    var brwName = navigator.appName;
    var brwAgent = navigator.userAgent;

    var w = _w;
    var h = _h;

    var _top;
    var _left;

    switch( arguments.length ) {
        case 5:
            _top = arguments[4];
            break;
        case 6:
            _top = arguments[4];
            _left = arguments[5];
            break;
        default:
            _top = 10;
            _left = 10;
            break;
    }

    var dialog = null;
    var sep = ( brwVer.indexOf( 'MSIE' ) == -1 ) ? ";" : ",";

    if( modal ) {
        if( brwVer.indexOf( 'MSIE' ) == -1 && brwAgent.indexOf( 'Firefox/3' ) == -1 ) {
            dialog = window.open(    url, '_blank',
                            "modal=yes" + sep + "location=no" + sep + "status=no" + sep + "scrollbars=yes" + sep + "width=" + w + "" + sep + "height=" + h + sep + "top=" + _top + sep + "left=" + _left );
        } else {
            dialog = window.showModalDialog( url, "",
                                    "dialogwidth:" + w + "px" + sep + "dialogheight:" + h + "px" + sep + "center:yes" + sep + "status:no" + sep + "resizable:no" + sep + "location:no" + sep + "toolbar:no" + sep + "menubar:no" + sep + "dialogTop:" + _top + "px" + sep + "dialogLeft:" + _left + "px" );
        }
    }
    else {
        sep = ",";
        dialog = window.open(    url, '_blank',
                        "modal=no" + sep + "location=no" + sep + "status=no" + sep + "scrollbars=yes" + sep + "width=" + w + "" + sep + "height=" + h + sep + "top=" + _top + sep + "left=" + _left );
    }

    return dialog;
}
// ]


