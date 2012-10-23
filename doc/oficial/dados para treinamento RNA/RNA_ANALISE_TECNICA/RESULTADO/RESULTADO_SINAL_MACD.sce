clear;
path_resultado = get_absolute_file_path('RESULTADO_SINAL_MACD.sce');

exec( path_resultado+"\_dados_entrada.sce" );
exec( path_resultado+"\..\RNA_ANALISE_TECNICA.sce" );

printf( 'Iniciando calculos dos resultados MACD...' );
codigoAtivo = 'BBAS3';
dados_entrada = getDadosEntrada( codigoAtivo, MAXIMO_LINHA_ARQUIVO );

macd_line = dados_entrada(:,4);
macd_sinal = dados_entrada(:,5);

coeficientes = regressao_linear( [[1:length(macd_line)]' macd_line], 3 );
b_max_abs_macdline = maximo_absoluto( coeficientes(:,1) );
valor_max_abs_macdline = maximo_absoluto( macd_line );

coeficientes = regressao_linear( [[1:length(macd_sinal)]' macd_sinal], 3 );
b_max_abs_macdsinal = maximo_absoluto( coeficientes(:,1) );
valor_max_abs_macdsinal = maximo_absoluto( macd_sinal );

resultado = [];
for i=1:length( macd_line )
  valorMacdLine = normalizar_valor( macd_line(i), valor_max_abs_macdline );
  valorMacdSinal = normalizar_valor( macd_sinal(i), valor_max_abs_macdsinal );
  
  if i <= 2
    
    alphaMacdLine = 0.5;
    alphaMacdSinal = 0.5;
    
  else
    
    cof = regressaoLinear( [[1:3]' [macd_line(i-2);macd_line(i-1);macd_line(i)]] );
    b = cof(1);
    alphaMacdLine = normalizar_valor( b, b_max_abs_macdline );
    
    cof = regressaoLinear( [[1:3]' [macd_sinal(i-2);macd_sinal(i-1);macd_sinal(i)]] );
    b = cof(1);
    alphaMacdSinal = normalizar_valor( b, b_max_abs_macdsinal );
    
  end
  
  saida_rna_macd = rna_MACD( valorMacdLine, alphaMacdLine, valorMacdSinal, alphaMacdSinal );
  resultado = [ resultado ; saida_rna_macd' ];
  
  if pmodulo( i, 10 ) == 0 then
    printf( ' (' );
    printf( string( i ) );
    printf( ') ' );
  end
  
  if pmodulo( i, 100 ) == 0 then
    printf( '\n' );
  end
end
printf( 'Calculos efetuados com sucesso.' );

printf( 'Gravando resultado...' );
gravarDados( path_resultado + '\Saida\RESULTADO_MACD_'+codigoAtivo+'.txt', resultado );
printf( 'Resultado Gravado.' );