$(document).ready(function(){
  createComboBox_ExampleComponents();
  createComboBox_TipoDeDados();
});

Type = {
    FUNCAO : '1',
    CONJ_FUZZY : '2',
    UNIV_FUZZY : '3',
    MAQUINA_INFERENCIA : '4',
    SISTEMA_FUZZY : '5'
}

function createComboBox_TipoDeDados(){
	var urlExampleComponent = '/FuzzyLogic/example/type/';
	var urlAllExamplesComponents = '/FuzzyLogic/example/all.json';
	
	var tagComboBox = jQuery('<select/>', {
    id: 'tipoDeDado',
    style: 'margin-top: 10px;'
  });
	
	tagComboBox.append( getOptionComboBox( Type.FUNCAO, 'Função' ) );
	tagComboBox.append( getOptionComboBox( Type.CONJ_FUZZY, 'Conjunto Fuzzy' ) );
	tagComboBox.append( getOptionComboBox( Type.UNIV_FUZZY, 'Universo Fuzzy' ) );
	tagComboBox.append( getOptionComboBox( Type.MAQUINA_INFERENCIA, 'Maquina de Inferência' ) );
	tagComboBox.append( getOptionComboBox( Type.SISTEMA_FUZZY, 'Sistema Fuzzy' ) );
	
	$('#containerComboBoxTipoDeDados').html( 'Tipo de Dado:  ' );
	$('#containerComboBoxTipoDeDados').append( tagComboBox );
	$('#containerComboBoxTipoDeDados').append( createButtonFormula() );
	
	return;
	
	function getOptionComboBox( value, text ){
		if( typeof text != "string" ) return;
		
		var tagOption = jQuery('<option/>', {
	    value: value
	  });
		
		tagOption.html( text );
		return tagOption;
	}
	
	function createButtonFormula(){
    var bt = jQuery('<input/>', {
      type: 'button',
      value: 'F(x)',
      style: 'font-size: 11px; font-family: Verdana, Arial, Helvetica, sans-serif; margin-left: 10px;'
    });
    
    bt.attr( 'class', 'btn' );
    bt.click( function(){
      alert( 'Equação reta: f(x)=y0+(x-x0)/(m)\n' );
    } );
		
		return bt;
	}
}

function createComboBox_ExampleComponents(){
	var idButtonGetExampleComponent = 'urlAvancar';
	var urlExampleComponent = '/FuzzyLogic/example/type/';
	var urlAllExamplesComponents = '/FuzzyLogic/example/all.json';
	
	var tagComboBox = jQuery('<select/>', {});
	tagComboBox.change( function(){
	  onchangeComboBoxOnSelectComponent();
	});
	
	dataDispatcherGetAllExampleComponents();
	$('#containerComboBoxCarregarExemplo').html( 'Carregar Exemplo:  ' );
	$('#containerComboBoxCarregarExemplo').append( tagComboBox );
	
	return;

	function onchangeComboBoxOnSelectComponent(){
		var selectedIndexComboBoxForms = tagComboBox[0].selectedIndex;
		if( selectedIndexComboBoxForms > 0 ){
			var selectedItem = tagComboBox[0].options[ selectedIndexComboBoxForms ];
			var strUrl = urlExampleComponent + selectedItem.value + '/name/' + selectedItem.text + '.json';
			getJsonByUrl( strUrl, true );
			tagComboBox[0].selectedIndex = 0;
		}else{
			alert('Selecione um exemplo de component!');
		}
	}

	function dataDispatcherGetAllExampleComponents(){
	  $.get( urlAllExamplesComponents, 
    function(data){
      if( data.status = 'SUCCESS' ){
        responseAnalyserGetAllExampleComponents( data );
      }else{
        alert(data.message);
      }
    }, "json");
	}
	  
	function responseAnalyserGetAllExampleComponents( responseJSON ){
	    var optionsComboBox = responseJSON.returnObject;
	    tagComboBox.append( getOptionComboBoxDefault() );
	    for( var i=0; i<optionsComboBox.length; i++ ){
	        var item = optionsComboBox[i];
	        tagComboBox.append( getOptionComboBox( item['type'], item['name'] ) );
	    }
	}

	function getOptionComboBoxDefault(){
	  
	  var tagOption = jQuery('<option/>', {
      value: 'undefined',
      selected: 'selected'
    });
	  
		tagOption.html( 'Selecione uma opção...' );
		return tagOption;
	}

	function getOptionComboBox( value, text ){
		if( typeof text != "string" ) return;
		
		var tagOption = jQuery('<option/>', {
      value: value
    });
		
		tagOption.html( text );
		return tagOption;
	}
}
