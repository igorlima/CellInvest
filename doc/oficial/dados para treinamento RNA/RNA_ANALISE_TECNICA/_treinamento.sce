path_principal = get_absolute_file_path('_treinamento.sce');

function treinar( path_treinamento, nome_treinamento )

  exec( path_principal+"\_util.sce" );
  exec( path_principal+"\_carregar_rede_de_treinamento.sce" );
  exec( path_principal+"\_arquivo.sce" );
  
  //GERAÇÃO DA SEMENTE DE NUMERO ALEATORIO
  rand( 'seed', 0 );
  disp( 'Lendo parametros de '+nome_treinamento+'...' );
  
  //Para o vetor N:
  //primeira posicao: num de entradas de dados
  //segunda posicao: num de neuronios da 1a camada
  //terceira posicao: num de neuronios na saida
  N = getN( path_treinamento + "\N_"+nome_treinamento+".txt" );
  
  X = dadosTreinamento( path_treinamento, nome_treinamento );
  
  dados_classificacao = extrairDados( path_treinamento+"CLASSE_"+nome_treinamento+"_COMPRA.txt", MAXIMO_LINHA_ARQUIVO );
  dados_classificacao = [dados_classificacao , extrairDados( path_treinamento+"CLASSE_"+nome_treinamento+"_NEUTRA.txt", MAXIMO_LINHA_ARQUIVO ) ];
  dados_classificacao = [dados_classificacao , extrairDados( path_treinamento+"CLASSE_"+nome_treinamento+"_VENDA.txt", MAXIMO_LINHA_ARQUIVO ) ];
  
  Y = [ dados_classificacao ];
  X = X';
  Y = Y';
  
  //Parametros de treinamento
  //Para o vetor Lp
  //primeira posicao: taxa de aprendizado
  //segunda posicao: valor minimo p/ ajuste de peso
  Lp = [TAXA_APRENDIZAGEM, 0, RNA_AJUSTE_DE_PESO, 0 ];
  
  T = NUMERO_DE_EPOCAS; //Numero de epocas
  disp( 'Inicializando a Rede Neural: ' + nome_treinamento );
  
  //matriz de pesos
  W = ann_FF_init( N );
  
  disp( 'Treinando a Rede '+nome_treinamento+'...' );
  erroepoca = [];
  for i=1:T
    W = ann_FF_Mom_online( X, Y, N, W, Lp, 1 );
    Ys = ann_FF_run( X, N, W );
    
    //calculo do erro
    erro = Y - Ys;
    
    //calculo do erro quadratico
    somaErroQuadratico = sum(erro(1,:)^2);
    somaErroQuadratico = [ somaErroQuadratico; sum(erro(2,:)^2) ];
    somaErroQuadratico = [ somaErroQuadratico; sum(erro(3,:)^2) ];
    erroEpocaAtual = somaErroQuadratico;
    
    //armazenamento do erro da epoca atual
    erroepoca = [erroepoca erroEpocaAtual];
    
    //Condicao para acompanhar o erro da epoca
    if pmodulo( i, N_ACOMPANHAMENTO ) == 0 then
      printf( ' (' );
      printf( string( erroEpocaAtual(1) ) );
      printf( ' ' );
      printf( string( erroEpocaAtual(2) ) );
      printf( ' ' );
      printf( string( erroEpocaAtual(3) ) );
      printf( ') ' );
    end
    
    //Condicao para inserir quebra de linha
    if pmodulo( i, N_QUEBRA_DE_LINHA ) == 0 then
      printf( '\n <' + string(i) + '> ' + '\n' );
    end
  
  end
  
  disp( 'Rede '+nome_treinamento+' treinada.' );
  
  Ys = ann_FF_run( X, N, W );
  Ys = round( Ys );
  
  //gravar o W em arquivo
  gravarW(W, path_treinamento, nome_treinamento );
  
endfunction