clear;
path_resultado = get_absolute_file_path('RESULTADO_INDICADOR.sce');

exec( path_resultado+"\_dados_entrada.sce" );
exec( path_resultado+"\..\RNA_ANALISE_TECNICA.sce" );

printf( 'Iniciando calculos dos resultados finais...\n' );
codigoAtivo = 'BBAS3';
dados_entrada = getDadosEntrada( codigoAtivo, MAXIMO_LINHA_ARQUIVO );

ifr = dados_entrada(:,1);
estocastico = dados_entrada(:,2);
histograma = dados_entrada(:,3);
macd_line = dados_entrada(:,4);
macd_sinal = dados_entrada(:,5);
obv = dados_entrada(:,6);

coeficientes = regressao_linear( [[1:length(histograma)]' histograma], 3 );
b_max_abs_histograma = maximo_absoluto( coeficientes(:,1) );
valor_max_abs_hist = maximo_absoluto( histograma );

coeficientes = regressao_linear( [[1:length(macd_line)]' macd_line], 3 );
b_max_abs_macdline = maximo_absoluto( coeficientes(:,1) );
valor_max_abs_macdline = maximo_absoluto( macd_line );

coeficientes = regressao_linear( [[1:length(macd_sinal)]' macd_sinal], 3 );
b_max_abs_macdsinal = maximo_absoluto( coeficientes(:,1) );
valor_max_abs_macdsinal = maximo_absoluto( macd_sinal );

coeficientes = regressao_linear( [[1:length(obv)]' obv], 3 );
b_max_abs_obv = maximo_absoluto( coeficientes(:,1) );

result_ifr = [];
result_est = [];
result_momento = [];
result_hist_valor = [];
result_hist_alpha = [];
result_histograma = [];
result_macdline_valor = [];
result_macdline_alpha = [];
result_macdline = [];
result_macdsinal_valor = [];
result_macdsinal_alpha = [];
result_macdsinal = [];
result_macd = [];
result_sinal = [];
result_obv_alpha = [];
result_volume = [];
result_indicador = [];
for i=1:length( ifr )
  
  valorIfr = ifr(i)/100;
  valorEstocastico = estocastico(i)/100;
  valorHistograma = normalizar_valor( histograma(i), valor_max_abs_hist );
  valorMacdLine = normalizar_valor( macd_line(i), valor_max_abs_macdline );
  valorMacdSinal = normalizar_valor( macd_sinal(i), valor_max_abs_macdsinal );
  
  if i <= 2
    
    alphaHistograma = 0.5;
    alphaMacdLine   = 0.5;
    alphaMacdSinal  = 0.5;
    alphaObv        = 0.5;
    
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
    
    cof = regressaoLinear( [[1:3]' [obv(i-2);obv(i-1);obv(i)]] );
    b = cof(1);
    alphaObv = normalizar_valor( b, b_max_abs_obv );
    
  end
  
  saida_ifr = rna_ifr( valorIfr );
  saida_est = rna_estocastico( valorEstocastico );
  saida_momento = rna_momento( valorIfr, valorEstocastico );
  saida_hist_valor = rna_valorHIST( valorHistograma );
  saida_hist_alpha = rna_alphaHIST( alphaHistograma );
  saida_histograma = rna_HISTOGRAMA( valorHistograma, alphaHistograma );
  saida_macdline_valor = rna_valorMACDLINE( valorMacdLine );
  saida_macdline_alpha = rna_alphaMACDLINE( alphaMacdLine );
  saida_macdline = rna_MACDLINE( valorMacdLine, alphaMacdLine );
  saida_macdsinal_valor = rna_valorMACDSINAL( valorMacdSinal );
  saida_macdsinal_alpha = rna_alphaMACDSINAL( alphaMacdSinal );
  saida_macdsinal = rna_MACDSINAL( valorMacdSinal, alphaMacdSinal );
  saida_macd = rna_MACD( valorMacdLine, alphaMacdLine, valorMacdSinal, alphaMacdSinal );
  saida_sinal = rna_sinal( valorHistograma, alphaHistograma, valorMacdLine, alphaMacdLine, valorMacdSinal, alphaMacdSinal );
  saida_obv_alpha = rna_obv( alphaObv );
  saida_volume = rna_volume( alphaObv );
  saida_indicador = rna_indicador( valorIfr,valorEstocastico,valorHistograma,alphaHistograma,valorMacdLine,alphaMacdLine,valorMacdSinal,alphaMacdSinal,alphaObv );
  
  result_ifr = [ result_ifr ; saida_ifr' ];
  result_est = [ result_est ; saida_est' ];
  result_momento = [ result_momento ; saida_momento' ];
  result_hist_valor = [ result_hist_valor ; saida_hist_valor' ];
  result_hist_alpha = [ result_hist_alpha ; saida_hist_alpha' ];
  result_histograma = [ result_histograma ; saida_histograma' ];
  result_macdline_valor = [ result_macdline_valor ; saida_macdline_valor' ];
  result_macdline_alpha = [ result_macdline_alpha ; saida_macdline_alpha' ];
  result_macdline = [ result_macdline ; saida_macdline' ];
  result_macdsinal_valor = [ result_macdsinal_valor ; saida_macdsinal_valor' ];
  result_macdsinal_alpha = [ result_macdsinal_alpha ; saida_macdsinal_alpha' ];
  result_macdsinal = [ result_macdsinal ; saida_macdsinal' ];
  result_macd = [ result_macd; saida_macd' ];
  result_sinal = [ result_sinal ; saida_sinal' ];
  result_obv_alpha = [ result_obv_alpha ; saida_obv_alpha' ];
  result_volume = [ result_volume ; saida_volume' ];
  result_indicador = [ result_indicador ; saida_indicador' ];
  
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
gravarDados( path_resultado + '\Saida\RESULTADO_IFR_'+codigoAtivo+'.txt', result_ifr );
gravarDados( path_resultado + '\Saida\RESULTADO_ESTOCASTICO_'+codigoAtivo+'.txt', result_est );
gravarDados( path_resultado + '\Saida\RESULTADO_MOMENTO_'+codigoAtivo+'.txt', result_momento );
gravarDados( path_resultado + '\Saida\RESULTADO_HIST_VALOR_'+codigoAtivo+'.txt', result_hist_valor );
gravarDados( path_resultado + '\Saida\RESULTADO_HIST_ALPHA_'+codigoAtivo+'.txt', result_hist_alpha );
gravarDados( path_resultado + '\Saida\RESULTADO_HISTOGRAMA_'+codigoAtivo+'.txt', result_histograma );
gravarDados( path_resultado + '\Saida\RESULTADO_MACDLINE_VALOR_'+codigoAtivo+'.txt', result_macdline_valor );
gravarDados( path_resultado + '\Saida\RESULTADO_MACDLINE_ALPHA_'+codigoAtivo+'.txt', result_macdline_alpha );
gravarDados( path_resultado + '\Saida\RESULTADO_MACDLINE_'+codigoAtivo+'.txt', result_macdline );
gravarDados( path_resultado + '\Saida\RESULTADO_MACDSINAL_VALOR_'+codigoAtivo+'.txt', result_macdsinal_valor );
gravarDados( path_resultado + '\Saida\RESULTADO_MACDSINAL_ALPHA_'+codigoAtivo+'.txt', result_macdsinal_alpha );
gravarDados( path_resultado + '\Saida\RESULTADO_MACDSINAL_'+codigoAtivo+'.txt', result_macdsinal );
gravarDados( path_resultado + '\Saida\RESULTADO_MACD_'+codigoAtivo+'.txt', result_macd );
gravarDados( path_resultado + '\Saida\RESULTADO_SINAL_'+codigoAtivo+'.txt', result_sinal );
gravarDados( path_resultado + '\Saida\RESULTADO_OBV_'+codigoAtivo+'.txt', result_obv_alpha );
gravarDados( path_resultado + '\Saida\RESULTADO_VOLUME_'+codigoAtivo+'.txt', result_volume );
gravarDados( path_resultado + '\Saida\RESULTADO_INDICADOR_'+codigoAtivo+'.txt', result_indicador );
printf( '\nResultado Gravado.' );