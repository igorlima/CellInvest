var MAP_URL_GET_BY_NAME = {};
MAP_URL_GET_BY_NAME[Type.CONJ_FUZZY] = "/FuzzyLogic/conjuntoFuzzy/nome/{nome}.json";
MAP_URL_GET_BY_NAME[Type.UNIV_FUZZY] = "/FuzzyLogic/universoFuzzy/nome/{nome}.json";
MAP_URL_GET_BY_NAME[Type.MAQUINA_INFERENCIA] = "/FuzzyLogic/maquinaInferencia/nome/{nome}.json";
MAP_URL_GET_BY_NAME[Type.SISTEMA_FUZZY] = "/FuzzyLogic/sistemaFuzzy/nome/{nome}.json";

var MAP_URL_GET_BY_ID = {};
MAP_URL_GET_BY_ID[Type.FUNCAO] = "/FuzzyLogic/funcao/{id}.json";
MAP_URL_GET_BY_ID[Type.CONJ_FUZZY] = "/FuzzyLogic/conjuntoFuzzy/{id}.json";
MAP_URL_GET_BY_ID[Type.UNIV_FUZZY] = "/FuzzyLogic/universoFuzzy/{id}.json";
MAP_URL_GET_BY_ID[Type.MAQUINA_INFERENCIA] = "/FuzzyLogic/maquinaInferencia/{id}.json";
MAP_URL_GET_BY_ID[Type.SISTEMA_FUZZY] = "/FuzzyLogic/sistemaFuzzy/{id}.json";

var MAP_URL_GET_ALL = {};
MAP_URL_GET_ALL[Type.FUNCAO] = "/FuzzyLogic/funcao/all.json";
MAP_URL_GET_ALL[Type.CONJ_FUZZY] = "/FuzzyLogic/conjuntoFuzzy/all.json";
MAP_URL_GET_ALL[Type.UNIV_FUZZY] = "/FuzzyLogic/universoFuzzy/all.json";
MAP_URL_GET_ALL[Type.MAQUINA_INFERENCIA] = "/FuzzyLogic/maquinaInferencia/all.json";
MAP_URL_GET_ALL[Type.SISTEMA_FUZZY] = "/FuzzyLogic/sistemaFuzzy/all.json";

function getAll(){
  var strUrl = MAP_URL_GET_ALL[ $('#tipoDeDado').val() ];
  getDataForJSONeditor(strUrl);	
}

function getById(){
  var strUrl = MAP_URL_GET_BY_ID[ $('#tipoDeDado').val() ];
  strUrl = setUrlParameter( strUrl, $('#idDataDataBase').val() );
  getDataForJSONeditor(strUrl);
}

function getByName(){
  var strUrl = MAP_URL_GET_BY_NAME[ $('#tipoDeDado').val() ];
  strUrl = setUrlParameter( strUrl, $('#idDataDataBase').val() );
  getDataForJSONeditor(strUrl);
}

function getDataForJSONeditor( strUrl ){
  $.get( strUrl, 
    function(data){
      if( data.status = 'SUCCESS' ){
        $('#idEditorJson').val( $.toJSON(data.returnObject) );
      }else{
        alert(data.message);
      }
    }, "json");
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

function getJsonByUrl( strUrl ){
  
  $.get( strUrl, 
    function(data){
      if( data.status = 'SUCCESS' ){
        $('#idEditorJson').val( $.toJSON(data.returnObject) );
      }else{
        alert(data.message);
      }
    }, "json");
  
}
