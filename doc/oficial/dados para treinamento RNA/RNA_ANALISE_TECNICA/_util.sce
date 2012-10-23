N_ACOMPANHAMENTO = 10; //Condicao para acompanhar o erro da epoca
N_QUEBRA_DE_LINHA = 50; //Condicao para inserir quebra de linha
TAXA_APRENDIZAGEM = 0.2;
RNA_AJUSTE_DE_PESO = 0.1;
NUMERO_DE_EPOCAS = 600;
MAXIMO_LINHA_ARQUIVO = 99999;

// Método que retorna os coeficientes da regressao linear
//
// As coordenadas de entrada deve seguir o seguinte padrão:
// coordenadas =[ [1, 2] ; [3,4 ] ; [5,6 ] ; [7,8] ]
//
// ---------
// | 1 | 2 |
// | 3 | 4 |
// | 5 | 6 |
// | 7 | 8 |
// ---------
// -----------
// | x1 | y1 |
// | x2 | y2 |
// | x3 | y3 |
// | x4 | y4 |
// -----------
//
// Retorna dois coeficientes: 'a' e 'b'
//    onde:
//     'a' é o coeficiente linear
//     'b' é o coeficinete angular
//
//    y = a + b*x
// f(x) = a + b*x
// [ b, a ]
function [coeficientes_angulares] = regressaoLinear( coordenadas )
  b=0;
  a=0;
  
  //Deve ter duas coordenadas: 'x' e 'y'
  if( length( coordenadas(1,:) ) <> 2 )
    error("Quantidade de coordenadas invalidas.");
  end
  
  //Deve ter pelo menos 2 valores para calcular a regressao linear
  qte_coordenadas = length( coordenadas(:,1) );
  if( qte_coordenadas < 2 )
    error("Eh necessario pelo menos " + code2str(2) + " coordenadas.");
  end
  
  x_media = mean( coordenadas(:,1) );
  y_media = mean( coordenadas(:,2) );
  
  //Somatorio da seguinte operacao: (xi-x_media)*(yi-y_media) 
  somatorio1 = 0;
  
  //Somatorio da seguinte operacao: (xi-x_media)^2
  somatorio2 = 0;
  
  for i_coord=1:qte_coordenadas
    somatorio1 = somatorio1 + ( (coordenadas(i_coord, 1)-x_media)*(coordenadas(i_coord, 2)-y_media) );
    somatorio2 = somatorio2 + ( coordenadas(i_coord,1)-x_media )^2;
  end
  
  
  b = somatorio1/somatorio2;
  a = y_media - b*x_media;
  coeficientes_angulares = [ b, a ];
endfunction

//Método que retorna a regressao linear, usando n par ordenado
function [coeficientes_angulares] = regressao_linear( coordenadas, n )
  coeficientes_angulares = [];
  
  //Deve ter duas coordenadas: 'x' e 'y'
  if( length( coordenadas(1,:) ) <> 2 )
    error("Quantidade de coordenadas invalidas.");
  end
  
  //Deve ter pelo menos 'n' valores para calcular a regressao linear
  qte_coordenadas = length( coordenadas(:,1) );
  if( qte_coordenadas < n )
    error("Eh necessario pelo menos " + code2str(n) + " coordenadas.");
  end
  
  //As (n-1) coeficientes são zeros pq não tem valores suficientes
  for i_coord=1:(n-1)
    coeficientes_angulares = [ coeficientes_angulares; 0, 0 ];
  end
  
  //A partir da (n-1) par ordenado, será calculada os coeficientes angulares
  for i_coord=1:(qte_coordenadas-(n-1))
    coordenadas_aux = [];
    coordenadas_aux = [ coordenadas_aux; coordenadas(i_coord,:) ];
    for j=1:(n-1)
       coordenadas_aux = [ coordenadas_aux; coordenadas(i_coord+j,:) ];
    end
    
    coeficientes_angulares = [ coeficientes_angulares; regressaoLinear( coordenadas_aux ) ];
  end
  
endfunction


//Metodo para normalizar os dados entre 0 e 1
function [dados_normalizados] = normalizar( dados )
  //Deve ter apenas uma dimensao
  if( length( dados(1,:) ) <> 1 )
    error("Valores invalido. Os dados deve ter apenas uma dimensao.");
  end
   
  valor_maximo_absoluto = maximo_absoluto( dados );
  dados_normalizados = normalizar_valor( dados, valor_maximo_absoluto );
endfunction

function [valor_maximo_absoluto] = maximo_absoluto( dados )
  //Deve ter apenas uma dimensao
  if( length( dados(1,:) ) <> 1 )
    error("Valores invalido. Os dados deve ter apenas uma dimensao.");
  end
  
  valor_maximo_absoluto = max( abs(dados) );
  valor_maximo_absoluto = valor_maximo_absoluto + 0.1*valor_maximo_absoluto;
endfunction

//Metodo para normalizar um valor entre 0 e 1, usando o valor maximo absoluto
function [valor_normalizado] = normalizar_valor( valor, valor_maximo_absoluto )
  //Deve ter apenas uma dimensao
  if( length( valor(1,:) ) <> 1 )
    error("Valores invalido. Os dados deve ter apenas uma dimensao.");
  end
  
  //normalizando...
  valor_normalizado = valor/valor_maximo_absoluto;
  valor_normalizado = valor_normalizado+1;
  valor_normalizado = valor_normalizado/2;
  
endfunction

function [valor_alpha] = convert_to_alpha( valores )
  valor_normalizado = normalizar( valores );
  valor_x = 1:length(valor_normalizado);
  valor_x = valor_x';
  reg_linear = regressao_linear( [valor_x, valor_normalizado], 3 );
  valor_alpha = reg_linear(:,1);
  valor_alpha = normalizar( valor_alpha );
endfunction