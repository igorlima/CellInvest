clear;
path_resultado = get_absolute_file_path('RESULTADO.sce');

exec( path_resultado+"\_dados_entrada.sce" );
exec( path_resultado+"\..\RNA_ANALISE_TECNICA.sce" );

printf( 'Iniciando calculos dos resultados...' );
codigoAtivo = 'BBAS3';
dados_entrada = getDadosEntrada( codigoAtivo, MAXIMO_LINHA_ARQUIVO );

resultado = [];
for i=1:length( dados_entrada(:,1) )
  disp( rna_ifr( dados_entrada(i,1)/100 ) );
  //saida_rna = rna_analise_tecnica( codigoAtivo, dados_entrada(i,1), dados_entrada(i,2), dados_entrada(i,3), dados_entrada(i,4), dados_entrada(i,5), dados_entrada(i,6) );
  //resultado = [ resultado ; saida_rna' ];
  
//  if pmodulo( i, 10 ) == 0 then
//    printf( ' (' );
//    printf( string( i ) );
//    printf( ') ' );
//  end
  
//  if pmodulo( i, 100 ) == 0 then
//    printf( '\n' );
//  end
end
printf( 'Calculos efetuados com sucesso.' );

//printf( 'Gravando resultado...' );
//gravarDados( path_resultado + '\Saida\RESULTADO_'+codigoAtivo+'.txt', resultado );
//printf( 'Resultado Gravado.' );