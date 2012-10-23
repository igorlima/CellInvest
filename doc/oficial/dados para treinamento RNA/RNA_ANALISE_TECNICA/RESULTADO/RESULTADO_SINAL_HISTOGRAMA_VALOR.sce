clear;
path_resultado = get_absolute_file_path('RESULTADO_SINAL_HISTOGRAMA_VALOR.sce');

exec( path_resultado+"\_dados_entrada.sce" );
exec( path_resultado+"\..\RNA_ANALISE_TECNICA.sce" );

printf( 'Iniciando calculos dos resultados...' );
codigoAtivo = 'BBAS3';
dados_entrada = getDadosEntrada( codigoAtivo, MAXIMO_LINHA_ARQUIVO );

resultado = [];
dados = dados_entrada(:,3);
for i=1:length( dados )
  valor_normalizado = normalizar( [dados; dados(i)] );
  valor_normalizado = valor_normalizado( length(valor_normalizado) );
  saida_rna = rna_valorHIST( valor_normalizado );
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
gravarDados( path_resultado + '\Saida\RESULTADO_HISTOGRAMA_VALOR_'+codigoAtivo+'.txt', resultado );
printf( 'Resultado Gravado.' );