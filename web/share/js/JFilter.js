JFilter.prototype.dialogId = null;

JFilter.prototype.waitTime = null;
JFilter.prototype.filterName = null;

JFilter.prototype.rowEventDblClickCallback = null;
JFilter.prototype.rowEventDblClickCallbackParam = null;

JFilter.prototype.frm;
JFilter.prototype.bindingStore = [];

JFilter.prototype.tbl;
JFilter.prototype.tbody;
JFilter.prototype.rowStore = [];


function JFilter() {
    switch( arguments.length ) {
        case 1:
            this.tbl = this.$( arguments[0] );
            this.tbody = this.tbl.getElementsByTagName( 'tbody' )[0];
            this.feedStore();
            break;
        case 2:
            this.tbl = this.$( arguments[0] );
            this.frm = this.$( arguments[1] );
           
            this.tbody = this.tbl.getElementsByTagName( 'tbody' )[0];
            this.feedStore();
            break;
    }

    this.waitTime = 1;
}


JFilter.prototype.filter = function( filterName ) {
    this.filterName = filterName;
}


JFilter.prototype.init = function() {
    var tblId, frmId;
    switch( arguments.length ) {
        case 1:
            tblId = arguments[0];
            break;
        case 2:
            frmId = arguments[1];
            this.frm = this.$( frmId );
            break;
    }

    this.tbl = this.$( tblId );
    this.tbody = this.tbl.getElementsByTagName( 'tbody' )[0];
    this.feedStore();
}


JFilter.prototype.setDialogId = function( id ) {
    this.dialogId = id;
}


JFilter.prototype.setWaitTime = function( _waitTime ) {
    this.waitTime = _waitTime;
}


JFilter.prototype.setDblClickCallback = function() {
    this.rowEventDblClickCallback = arguments[0];
    switch( arguments.length ) {
        case 1:
            this.rowEventDblClickCallbackParam = null;
            break;
        case 2:
            this.rowEventDblClickCallbackParam = arguments[1];
            break;
    }
}


JFilter.prototype.search = function() {
    var cmdArr = [];
    var cmd = "";
    var i, item, dataType, pos, obj;

    this.restore();

    // [ Spec for Dat09 ngu
    for( i = 0; i < this.bindingStore.length; i++ ) {
        item = this.bindingStore[i];
        obj = this.$( item[0] );
        dataType = item[1];
        pos = item[2];
        if( dataType != null && pos != null ) {
            //item.value = item.value.replace( /^\s+|\s+$/g, "" );
            
            if( obj.disabled != true && obj.value.length > 0 ) {
                cmd = " ( ";
                switch( dataType.toString().toLowerCase() ) {
                    case 's':
                        cmd += " this.rowStore[i].tds[" + pos + "].toString().toLowerCase().indexOf( this.$( '" + obj.id + "' ).value.toString().toLowerCase() ) != -1 ";
                        break;
                    case 'n':
                        cmd += " this.rowStore[i].tds[" + pos + "] == this.$( '" + obj.id + "' ).value ";
                        break;
                }
                cmd += " ) ";
                cmdArr.push( cmd );
            }
        }
    }
    // ] Spec for Dat09 ngu

    /*
    for( i = 0; i < this.frm.elements.length; i++ ) {
        item = this.frm.elements[i];
        obj = this.getBindInfo( item.id );
        dataType = obj.dataType;
        pos = obj.pos;
        if( dataType != null && pos != null ) {
            //item.value = item.value.replace( /^\s+|\s+$/g, "" );
            
            if( item.value.length > 0 ) {
                cmd = " ( ";
                switch( dataType.toString().toLowerCase() ) {
                    case 's':
                        cmd += " this.rowStore[i].tds[" + pos + "].toString().toLowerCase().indexOf( this.$( '" + item.id + "' ).value.toString().toLowerCase() ) != -1 ";
                        break;
                    case 'n':
                        cmd += " this.rowStore[i].tds[" + pos + "] == this.$( '" + item.id + "' ).value ";
                        break;
                }
                cmd += " ) ";
                cmdArr.push( cmd );
            }
        }
    }
    */
    var checkVar;
    if( cmdArr.length > 0 ) {
        for( i = 0; i < this.rowStore.length; i++ ) {
            eval( "checkVar = ( " + cmdArr.join( "&&" ) + " );" );
            if( !checkVar ) {
                this.tbody.removeChild( this.rowStore[i].tr );
            }
        }
    }
}


JFilter.prototype.bindForm = function() {
    var _self = this;
    var i;
    this.bindingStore = [];
    for( i = 0; i < arguments.length; i++ ) {
        // arguments[i] = [ formItemId, formItemDataType, formItemPositionInTable ]
        this.bindingStore.push( arguments[i] );
        if( this.waitTime != null ) {
            bindEvent( arguments[i][0] );
        }
    }

    function bindEvent( id ) {
        var obj = _self.$( id );
        obj.onkeyup = function( e ) {
            var evt = ( window.event ) ? window.event : e;
            setTimeout( _self.filterName + "();", _self.waitTime * 1000 );
        }
    }
}


JFilter.prototype.getBindInfo = function() {
    var id, pos;

    id = arguments[0];
    switch( arguments.length ) {
        case 1:
            pos = 0;
            break;
        case 2:
            pos = arguments[1];
            break;
    }

    var i;
    var resObj = { id:null, dataType:null, pos:null };
    for( i = 0; i < this.bindingStore.length; i++ ) {
        if( id == this.bindingStore[i][pos] ) {
            resObj.id = this.bindingStore[i][0];
            resObj.dataType = this.bindingStore[i][1];
            resObj.pos = this.bindingStore[i][2];
            break;
        }
    }
    return resObj;
}


JFilter.prototype.feedStore = function() {
    var _self = this;
    var trs = this.tbody.getElementsByTagName( 'tr' );
    var i, j;

    this.rowStore = [];

    for( i = 0; i < trs.length; i++ ) {
        var tds = trs[i].getElementsByTagName( 'td' );
        var row = {};
        row.tr = trs[i];
        row.tds = [];
        for( j = 0; j < tds.length; j++ ) {
            row.tds[j] = tds[j].innerHTML;
        }
        bindEvent( row.tr );
        this.rowStore.push( row );
    }

    function bindEvent( obj ) {
        obj.onclick = function(){
            var i, tds, bindObj;
            tds = obj.getElementsByTagName( 'td' );
            for( i = 0; i < tds.length; i++ ) {
                bindObj = _self.getBindInfo( i, 2 );
                _self.$( bindObj.id ).value = tds[i].innerHTML;
            }
        }
        
        if( _self.rowEventDblClickCallback ) {
            obj.ondblclick = function() {
                if( _self.dialogId != null ) {
                    dialog.close( _self.dialogId );
                }
                _self.rowEventDblClickCallback( obj, _self.rowEventDblClickCallbackParam );
            }
        }
    }
}


JFilter.prototype.restore = function() {
    var i;
    for( i = 0; i < this.rowStore.length; i++ ) {
        this.tbody.appendChild( this.rowStore[i].tr );
    }
}


JFilter.prototype.truncate = function() {
    var i;
    for( i = 0; i < this.rowStore.length; i++ ) {
        try {
            this.tbody.removeChild( this.rowStore[i].tr );
        } catch ( e ) {

        }
    }
}


JFilter.prototype.reset = function() {
    this.rowStore = [];
    this.bindingStore = [];
}


JFilter.prototype.$ = function( id ) {
    return document.getElementById( id );
}
