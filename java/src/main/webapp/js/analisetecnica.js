function getDadosAnaliseTecnica(){
  $('#btGetAnaliseTecnica').button('loading');
  var strAtivo = $('#inputStrAtivo').val();
  var strUrl = setUrlParameter( URL_ANALISETECNICA_DADOS, strAtivo );
  $.get( strUrl, 
  function(data){
    if( data.status = 'SUCCESS' && data.returnObject!=null ){
      drawAllAnaliseTecnica( data.returnObject.indicadoresTecnicos );
      preencher_dados_de_entrada_das_modelagens( data.returnObject.dadosDeEntradaModelagem )
    }else{
      alert(data.message);
    }
  }, "json")
  .complete(function(){
    $('#btGetAnaliseTecnica').button('reset');
  });
}

function preencher_dados_de_entrada_das_modelagens( dados ){
  if( dados == null ) return;
  $('#valoresdeentradamodelagemfuzzy').val( getEntradaFuzzy(dados) );
  $('#valoresentradamodelagemrna').val( getEntradaRNA(dados) );
  
  function getEntradaFuzzy( dados ){
    return   '' + dados.ifr +
           ', ' + dados.estocastico +
           ', ' + dados.distanciaMacdAoEixoZero +
           ', ' + (dados.linhaMacd-dados.linhaSinal) +
           ', ' + dados.anguloMacd +
           ', ' + (dados.anguloMacd-dados.anguloSinal) +
           ', ' + dados.histograma +
           ', ' + dados.anguloHistograma +
           ', ' + dados.anguloObv;
  }
  
  function getEntradaRNA( dados ){
    return    '' + dados.ifrNormalizado +
            ', ' + dados.estocasticoNormalizado +
            ', ' + dados.distanciaMacdAoEixoZero +
            ', ' + dados.distanciaSinalAoEixoZero +
            ', ' + dados.anguloMacd +
            ', ' + dados.anguloSinal +
            ', ' + dados.histograma +
            ', ' + dados.anguloHistograma +
            ', ' + dados.anguloObv;
  }
}

function drawAllAnaliseTecnica( dados_analise_tecnica ){
  if( dados_analise_tecnica == null ) return;
  drawTableAnaliseTecnica( dados_analise_tecnica );
  drawAnaliseTecnica( dados_analise_tecnica, 'R$', 'price', 'analisetecnicapreco' );
  drawAnaliseTecnica( dados_analise_tecnica, 'IFR', 'ifr', 'analisetecnicaifr' );
  drawAnaliseTecnica( dados_analise_tecnica, 'Estocástico', 'estocastico', 'analisetecnicaestocastico' );
  drawMACDAnaliseTecnica( dados_analise_tecnica );
  drawAnaliseTecnica( dados_analise_tecnica, 'Histograma', 'histograma', 'analisetecnicahistograma' );
  drawAnaliseTecnica( dados_analise_tecnica, 'OBV', 'obv', 'analisetecnicaobv' );
}

function drawTableAnaliseTecnica( dados_analise_tecnica ) {
  // Create and populate the data table.
  var data = new google.visualization.DataTable();
  
  data.addColumn('string', 'Data');
  data.addColumn('number', 'R$');
  data.addColumn('number', 'IFR');
  data.addColumn('number', 'Est');
  data.addColumn('number', 'MLine');
  data.addColumn('number', 'MSinal');
  data.addColumn('number', 'Hist');
  data.addColumn('number', 'OBV');
  
  if( dados_analise_tecnica != null && jQuery.isArray(dados_analise_tecnica) ){
    $.each( dados_analise_tecnica, function(key,value){
      data.addRows(1);
      data.setCell(key, 0, value.strDate ); 
      data.setCell(key, 1, value.price ); 
      data.setCell(key, 2, value.ifr ); 
      data.setCell(key, 3, value.estocastico ); 
      data.setCell(key, 4, value.macdline ); 
      data.setCell(key, 5, value.macdsinal ); 
      data.setCell(key, 6, value.histograma ); 
      data.setCell(key, 7, value.obv ); 
    });
  }
  
  // Create and draw the visualization.
  var visualization = new google.visualization.Table(document.getElementById('analisetecnicatable'));
  visualization.draw(data, {width: 700});
  
}

function drawAnaliseTecnica( dados_analise_tecnica, nome_analise_tecnica, atributo_do_objeto, idContainer ) {
  if( nome_analise_tecnica == null ) return;
  var data = new google.visualization.DataTable();
  
  data.addColumn('string', 'Data');
  data.addColumn('number', nome_analise_tecnica);
  
  if( dados_analise_tecnica != null && jQuery.isArray(dados_analise_tecnica) ){
    $.each( dados_analise_tecnica, function(key,value){
      data.addRow( [ value.strDate, value[atributo_do_objeto] ] );
    });
  }
  
  // Create and draw the visualization.
  new google.visualization.LineChart(document.getElementById(idContainer)).
  draw(data, {curveType: "none",
              axisTitlesPosition: 'in',
              legend: 'bottom',
              titlePosition: 'in',
              titleTextStyle: {fontSize: 15},
              width: 550, 
              height: 200
              } 
      );
  
}

function drawMACDAnaliseTecnica( dados_analise_tecnica ) {
  // Create and populate the data table.
  var data = new google.visualization.DataTable();
  
  data.addColumn('string', 'Data');
  data.addColumn('number', 'Linha MACD');
  data.addColumn('number', 'Linha Sinal');
  
  if( dados_analise_tecnica != null && jQuery.isArray(dados_analise_tecnica) ){
    $.each( dados_analise_tecnica, function(key,value){
      data.addRow( [ value.strDate, value.macdline, value.macdsinal ] );
    });
  }
  
  // Create and draw the visualization.
  new google.visualization.LineChart(document.getElementById('analisetecnicamacd')).
  draw(data, {curveType: "none",
              axisTitlesPosition: 'in',
              legend: 'bottom',
              titlePosition: 'in',
              titleTextStyle: {fontSize: 15},
              width: 550, 
              height: 200
              } 
      );
  
}

google.setOnLoadCallback(drawTableAnaliseTecnica);
google.setOnLoadCallback(drawAnaliseTecnica);
google.setOnLoadCallback(drawMACDAnaliseTecnica);