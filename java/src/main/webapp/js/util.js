function removeChildrenFromNode(node){
   if(node == undefined && node == null){
      return;
   }

   var len = node.childNodes.length;
   while( node.hasChildNodes() ){
	  node.removeChild(node.firstChild);
   }
}

function getParams() {
	var mapOfParams = new Array();
	
	var loc = location.search.substring(1, location.search.length);
	var param_value = false;
	var params = loc.split("&");
	for (var i=0; i<params.length;i++) {
		var param_name = params[i].substring(0,params[i].indexOf('='));
		var param_value = params[i].substring(params[i].indexOf('=')+1);
		mapOfParams[ param_name ] = param_value; 
	}
	
	return mapOfParams;
}

function setUrlParameter(){
	var strUrl = "";
	var args = Array.prototype.slice.call(arguments);
	
	strUrl = args[0];
	var strUrlSplited = strUrl.split( /{[a-zA-Z0-9]*}/ );
	
	strUrl = strUrlSplited[0];
	for( var i=1; i<strUrlSplited.length; i++ ){
		strUrl += (i<args.length?args[i]:'') + strUrlSplited[i];
	}
	
	return strUrl;
}

function parseToBoolean( obj ){
	if( $type( obj ) == 'boolean' ){
		return obj;
	}else if( $type( obj ) == 'string' ){
		if( obj == 'true' )
			return true;
		else
			return false;
	}else{
		return false;
	}
}

function addClass( tag, newClass ){
	tag.setAttribute( 'class', tag.getAttribute( 'class' ) + ' ' + newClass );
}
