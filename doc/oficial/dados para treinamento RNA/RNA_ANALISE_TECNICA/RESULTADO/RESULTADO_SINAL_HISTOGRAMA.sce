clear;
path_resultado = get_absolute_file_path('RESULTADO_SINAL_HISTOGRAMA.sce');

exec( path_resultado+"\_dados_entrada.sce" );
exec( path_resultado+"\..\RNA_ANALISE_TECNICA.sce" );

printf( 'Iniciando calculos dos resultados...' );
codigoAtivo = 'BBAS3';
dados_entrada = getDadosEntrada( codigoAtivo, MAXIMO_LINHA_ARQUIVO );

resultado = [];
dados = dados_entrada(:,3);

coeficientes = regressao_linear( [[1:length(dados)]' dados], 3 );
coef_angular_max_abs = maximo_absoluto( coeficientes(:,1) );
valor_hist_max_abs = maximo_absoluto( dados );

for i=1:length( dados )
  if i <= 2
    saida_rna = rna_HISTOGRAMA( normalizar_valor( dados(i), valor_hist_max_abs ), 0.5 );
  else
    cof = regressaoLinear( [[1:3]' [dados(i-2);dados(i-1);dados(i)]] );
    b = cof(1);
    alpha = normalizar_valor( b, coef_angular_max_abs );
    
    saida_rna = rna_HISTOGRAMA( normalizar_valor( dados(i), valor_hist_max_abs ), alpha );
  end
  resultado = [ resultado ; saida_rna' ];
  
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
gravarDados( path_resultado + '\Saida\RESULTADO_HISTOGRAMA_'+codigoAtivo+'.txt', resultado );
printf( 'Resultado Gravado.' );