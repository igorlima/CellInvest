$(document).ready(function(){
  /* adicionando os popover's */
  $(function () {
    $("a[rel=popover]")
    .popover({
      offset: 10
    })
    .click(function(e) {
      e.preventDefault();
    })
  });
  
  /* adicionando os twipsy's */
  $(function () {
    $("a[rel=tooltip]").tooltip({
      live: true
    })
  })
  
  /* alterando os source das imagens das modelagens */
  $("#imgModelagemFuzzyLogic").attr("src","imagem/modelagem_fuzzy.jpg");
  $("#imgModelagemRNA").attr("src","imagem/modelagem_rna.jpg");
  
  /* adição dos typeahead */
  $('#nomeSistemaFuzzy').typeahead( {"source":[ "Histograma", "Indicador", "Momento", "MACD", "Sinal", "Volume" ], "items":8} );
  $('#nomeUniversoFuzzy').typeahead( {"source":[ "AlphaHistograma", "AlphaMacd", "AlphaOBV", "DiferencaDoAlphaMacd", "DiferencaMacdComSinal", "DistanciaDoHistogramaAoEixoZero", "DistanciaDoMacdAoEixoZero", "Estocastico", "Histograma", "IFR", "Indicador", "MACD", "Momento", "Sinal", "Volume" ], "items":8} );
});

onload=function(){
  if( typeof JSONeditor == 'undefined' ) return;
  
	JSONeditor.start('tree','jform',false,true)
	JSONeditorRNA.start('treejsoneditorrna','jsoneditorrna',false,false)
	Opera=(navigator.userAgent.toLowerCase().indexOf("opera")!=-1)
	Safari=(navigator.userAgent.toLowerCase().indexOf("safari")!=-1)
	Explorer=(document.all && (!(Opera || Safari)))
	Explorer7=(Explorer && (navigator.userAgent.indexOf("MSIE 7.0")>=0))
		
	if(Explorer7 && location.href.indexOf("file:")!=0){prompt("This is just to get input boxes started in IE7 - who deems them unsecure.","I like input boxes...")}
	
	
}

function onclickButtonSalvarFuncao(){
  $('#btSalvarFuncao').button('loading');
  
  var strUrl = URL_SAVE_FUNCAO;
  var data = $('#taSalvarFuncao').val();
  
  $.ajax({
    url:strUrl,
    type:"POST",
    data:data,
    contentType:"application/json; charset=utf-8",
    dataType:"json",
    success: function(data){
        alert(data.message);
      },
    complete: function(){
        $('#btSalvarFuncao').button('reset');
      },
    error: function(error){
    }
  });
  
}

function onclickButtonSalvarConjuntoFuzzy(){
  $('#btSalvarConjuntoFuzzy').button('loading');
  
  var data = $('#taSalvarConjuntoFuzzy').val();
  var strUrl = URL_SAVE_CONJUNTO_FUZZY;
  
  $.ajax({
    url:strUrl,
    type:"POST",
    data:data,
    contentType:"application/json; charset=utf-8",
    dataType:"json",
    success: function(data){
        alert(data.message);
      },
    complete: function(){
        $('#btSalvarConjuntoFuzzy').button('reset');
      },
    error: function(error){
    }
  });
  
}

function onclickButtonSalvarUniversoFuzzy(){
  $('#btSalvarUniversoFuzzy').button('loading');
  
  var data = $('#taSalvarUniversoFuzzy').val();
  var strUrl = URL_SAVE_UNIVERSO_FUZZY;
  
  $.ajax({
    url:strUrl,
    type:"POST",
    data:data,
    contentType:"application/json; charset=utf-8",
    dataType:"json",
    success: function(data){
        alert(data.message);
      },
    complete: function(){
        $('#btSalvarUniversoFuzzy').button('reset');
      },
    error: function(error){
    }
  });
  
}

function onclickButtonSalvarMaquinaInferencia(){
  $('#btSalvarMaquinaInferencia').button('loading');
  
  var data = $('#taSalvarMaquinaInferencia').val();
  var strUrl = URL_SAVE_MAQUINA_INFERENCIA;

  $.ajax({
    url:strUrl,
    type:"POST",
    data:data,
    contentType:"application/json; charset=utf-8",
    dataType:"json",
    success: function(data){
        alert(data.message);
      },
    complete: function(){
        $('#btSalvarMaquinaInferencia').button('reset');
      },
    error: function(error){
    }
  });
  
}

function onclickButtonSalvarSistemaFuzzy(){
  $('#btSalvarSistemaFuzzy').button('loading');
  
  var data = $('#taSalvarSistemaFuzzy').val();
  var strUrl = URL_SAVE_SISTEMA_FUZZY;
  
  $.ajax({
    url:strUrl,
    type:"POST",
    data:data,
    contentType:"application/json; charset=utf-8",
    dataType:"json",
    success: function(data){
        alert(data.message);
      },
    complete: function(){
        $('#btSalvarSistemaFuzzy').button('reset');
      },
    error: function(error){
    }
  });
  
}


function calcularSistemaFuzzy(){
  $('#btCalcularSistemaFuzzy').button('loading');
	var strUrl = setUrlParameter( URL_FUZZY_LOGIC_CALCULAR, $('#nomeSistemaFuzzy').val() );
	var data = {};
	data.input = $('#taEntradaSistemaFuzzy').val();
	
	$.post( strUrl, 
    data,
    function(data){
  	  
  	  if( data.status == "SUCCESS" ){
  	    $('#taSaidaSistemaFuzzy').val( $.toJSON(data.returnObject.conjuntosFuzzySaida) );
  	    $('#taDefuzzyficacao').val( $.toJSON(data.returnObject.defuzzyficacao) );
  	  }
  	  
      if( data.message != null ){ alert(data.message); }
      
    }, "json")
    .complete(function(){
      $('#btCalcularSistemaFuzzy').button('reset');
    });
	
}

function calcularUniversoFuzzy(){
  $('#btCalcularUniversoFuzzy').button('loading');
	var strUrl = setUrlParameter( URL_UNIV_FUZZY_LOGIC_CALCULAR, $('#nomeUniversoFuzzy').val() );
	var data = {};
	data.input = $('#taEntradaUniversoFuzzy').val(); 
  
  $.post( strUrl, 
    data,
    function(data){
      
      if( data.status == "SUCCESS" ){
        $('#taSaidaUniversoFuzzy').val( $.toJSON(data.returnObject) );
      }
      
      if( data.message != null ){ alert(data.message); }
      
    }, "json")
    .complete(function(){
      $('#btCalcularUniversoFuzzy').button('reset');
    });
  
}

function visualizar_arquivo_rna(){
  $('#btVisualizarArquivoRna').button('loading');
  var data = {};
  data.path = $('#path_arquivo_rna').val();
  
  $.post( URL_RNA_VISUALIZACAO, 
    data,
    function(data){
      if( data.status = 'SUCCESS' )
        $('#taConteudoArquivoRNA').val(data.returnObject);
	  
      if( data.message != null )
        $('#taSaidaDoArquivoExecutado').val(data.message);
      
    }, "json")
    .complete(function(){
      $('#btVisualizarArquivoRna').button('reset');
    });
  
}

function execucao_arquivo_rna(){
  $('#btExecutarArquivoRna').button('loading');
  var data = {};
  data.pathFile = $('#path_arquivo_rna').val();
  data.strCommandScilab = $('#taConteudoArquivoRNA').val();
  
  $.post( URL_RNA_EXECUCAO, 
    data,
    function(data){
      if( data.message != null ){
        $('#taSaidaDoArquivoExecutado').val(data.message);
      }
    }, "json")
    .complete(function(){
      $('#btExecutarArquivoRna').button('reset');
    });
}

function edicao_arquivo_rna(){
  if( !confirm( "Tem certeza que deseja editar esse arquivo?" ) )
	  return;
  
  $('#btEditarArquivoRna').button('loading');
  var data = {};
  data.path = $('#path_arquivo_rna').val();
  data.content = $('#taConteudoArquivoRNA').val();
  
  $.post( URL_RNA_EDICAO, 
    data,
    function(data){
      if( data.message != null ){
        $('#taSaidaDoArquivoExecutado').val(data.message);
      }
    }, "json")
    .complete(function(){
      $('#btEditarArquivoRna').button('reset');
    });
}

function listar_estrutura_de_diretorios_rna(){
  $.get( URL_RNA_LISTA_DIRETORIOS, 
    function(data){
      if( data.status = 'SUCCESS' ){
        var lista_diretorios = format_lista_diretorios( data.returnObject );
        $('#idEditorJsonRNA').val( $.toJSON( lista_diretorios ) );
      }else{
        alert(data.message);
      }
    }, "json");
}

function format_lista_diretorios( data ){
  var newdata = {};
  if( data.children == null ){
    return ''+data.path;
  }else{
    $.each( data.children, function(key,value){
      newdata[''+value.name] = format_lista_diretorios( value );
    });
    return newdata;
  }
}

function alterar_entrada_de_dados_da_modelagem( idinputentrada, idvalorminimo, idvalormaximo ){
  var minimo = parseInt( $('#'+idvalorminimo).html() ) -1;				
  var maximo = parseInt( $('#'+idvalormaximo).html() ) -1;
  
  var newinputentrada = '';
  var array = $('#valoresdeentradamodelagemfuzzy').val().split(',');
  $.each(array, function(index, value) { 
     newinputentrada += index>=minimo && index<=maximo ? ''+value+' ' : ''; 
  });
  
  $('#'+idinputentrada).val( newinputentrada );
}
