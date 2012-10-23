var dadosGrafico = null;
var nomeUniversoFuzzy = '';

function desenharGrafico(){
	nomeUniversoFuzzy = prompt( 'Insira o nome do Universo Fuzzy', "" );

	if( nomeUniversoFuzzy != null && nomeUniversoFuzzy != "" ){
		dadosGrafico = null;
		getDadosGrafico(nomeUniversoFuzzy);
	}
	
}


function getDadosGrafico(nomeUniversoFuzzy){
    var strUrl = "/FuzzyLogic//universoFuzzy/{nome}/dadosgrafico.json";
    strUrl = setUrlParameter( strUrl, nomeUniversoFuzzy );
    
    $.get( strUrl, 
    function(data){
      if( data.status == 'SUCCESS' ){
        
        dadosGrafico = data.returnObject;
        if( dadosGrafico ){ drawVisualization(); }
        
      }else{
        alert(data.message);
      }
    }, "json");
    
}

function createTable( dadosGrafico ){
    
    
    var table = new google.visualization.DataTable();
    addColumns( dadosGrafico );
    addRows( dadosGrafico );
    return table;
    
    function addColumns( dadosGrafico ){
        table.addColumn('string', 'Universo de Discurso');
        
        var firstConjuntoFuzzy = dadosGrafico.conjuntosFuzzy[0];
        
        $.each( firstConjuntoFuzzy, function(nmConjFuzzy,value){
            table.addColumn('number', nmConjFuzzy );
        });
        
    }
    
    function addRows( dadosGrafico ){
        //data.addRow(["03-jul", 204, 204]);
        
      $.each( dadosGrafico.conjuntosFuzzy, function(index,conjuntoFuzzySaida){
            var strRow = "[\""+dadosGrafico.eixox[index]+"\",";
            
            $.each( conjuntoFuzzySaida, function(nmConjFuzzy,value){
                strRow += " "+value+",";
            });
            strRow = strRow.substr( strRow, strRow.length-1 );
            strRow += "]";
            table.addRow( eval(strRow) );
        });
    }
}


function drawVisualization() {
    if( dadosGrafico == null ) return;
    
    // Create and populate the data table.
    var table = createTable( dadosGrafico );
    
    // Create and draw the visualization.
    new google.visualization.LineChart(document.getElementById('visualization')).
        draw(table, {curveType: "none",
                    axisTitlesPosition: 'in',
                    legend: {position:'bottom', textStyle: {fontSize: 10}},
                    title: nomeUniversoFuzzy,
                    titlePosition: 'in',
                    titleTextStyle: {fontSize: 15},
                    width: 700, height: 400,
                    hAxis: {title: 'x', textStyle: {fontSize:10} },
                    vAxis: {title: 'f(x)', maxValue: 1, baseline: 0}} 
            );
    
}

google.setOnLoadCallback(drawVisualization);