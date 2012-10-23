clear;
path_resultado = get_absolute_file_path('RESULTADO_SINAL.sce');

exec( path_resultado+"\_dados_entrada.sce" );
exec( path_resultado+"\..\RNA_ANALISE_TECNICA.sce" );

printf( 'Iniciando calculos dos resultados Sinal...\n' );
codigoAtivo = 'BBAS3';
dados_entrada = getDadosEntrada( codigoAtivo, MAXIMO_LINHA_ARQUIVO );

histograma = dados_entrada(:,3);
macd_line = dados_entrada(:,4);
macd_sinal = dados_entrada(:,5);

coeficientes = regressao_linear( [[1:length(histograma)]' histograma], 3 );
b_max_abs_histograma = maximo_absoluto( coeficientes(:,1) );
valor_max_abs_hist = maximo_absoluto( histograma );

coeficientes = regressao_linear( [[1:length(macd_line)]' macd_line], 3 );
b_max_abs_macdline = maximo_absoluto( coeficientes(:,1) );
valor_max_abs_macdline = maximo_absoluto( macd_line );

coeficientes = regressao_linear( [[1:length(macd_sinal)]' macd_sinal], 3 );
b_max_abs_macdsinal = maximo_absoluto( coeficientes(:,1) );
valor_max_abs_macdsinal = maximo_absoluto( macd_sinal );

resultado = [];
for i=1:length( histograma )
  valorHistograma = normalizar_valor( histograma(i), valor_max_abs_hist );
  valorMacdLine = normalizar_valor( macd_line(i), valor_max_abs_macdline );
  valorMacdSinal = normalizar_valor( macd_sinal(i), valor_max_abs_macdsinal );
  
  if i <= 2
    
    alphaHistograma = 0.5;
    alphaMacdLine   = 0.5;
    alphaMacdSinal  = 0.5;
    
  else
    
    cof = regressaoLinear( [[1:3]' [histograma(i-2);histograma(i-1);histograma(i)]] );
    b = cof(1);
    alphaHistograma = normalizar_valor( b, b_max_abs_histograma );
    
    cof = regressaoLinear( [[1:3]' [macd_line(i-2);macd_line(i-1);macd_line(i)]] );
    b = cof(1);
    alphaMacdLine = normalizar_valor( b, b_max_abs_macdline );
    
    cof = regressaoLinear( [[1:3]' [macd_sinal(i-2);macd_sinal(i-1);macd_sinal(i)]] );
    b = cof(1);
    alphaMacdSinal = normalizar_valor( b, b_max_abs_macdsinal );
    
  end
  
  saida_rna_sinal = rna_sinal( valorHistograma, alphaHistograma, valorMacdLine, alphaMacdLine, valorMacdSinal, alphaMacdSinal );
  resultado = [ resultado ; saida_rna_sinal' ];
  
  if pmodulo( i, 10 ) == 0 then
    printf( ' (' );
    printf( string( i ) );
    printf( ') ' );
  end
  
  if pmodulo( i, 100 ) == 0 then
    printf( '\n' );
  end
end
printf( '\nCalculos efetuados com sucesso.' );

printf( '\nGravando resultado...' );
gravarDados( path_resultado + '\Saida\RESULTADO_SINAL_'+codigoAtivo+'.txt', resultado );
printf( '\nResultado Gravado.' );