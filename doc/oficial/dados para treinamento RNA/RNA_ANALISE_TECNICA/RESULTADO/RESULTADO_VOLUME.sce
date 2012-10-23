clear;
path_resultado = get_absolute_file_path('RESULTADO_VOLUME.sce');

exec( path_resultado+"\_dados_entrada.sce" );
exec( path_resultado+"\..\RNA_ANALISE_TECNICA.sce" );

printf( 'Iniciando calculos dos resultados Volume...\n' );
codigoAtivo = 'BBAS3';
dados_entrada = getDadosEntrada( codigoAtivo, MAXIMO_LINHA_ARQUIVO );

volume = dados_entrada(:,6);

coeficientes = regressao_linear( [[1:length(volume)]' volume], 3 );
b_max_abs_volume = maximo_absoluto( coeficientes(:,1) );

resultado = [];
for i=1:length( volume )
  
  if i <= 2
    
    alphaVolume = 0.5;
    
  else
    
    cof = regressaoLinear( [[1:3]' [volume(i-2);volume(i-1);volume(i)]] );
    b = cof(1);
    alphaVolume = normalizar_valor( b, b_max_abs_volume );
    
  end
  
  saida_rna_volume = rna_volume( alphaVolume );
  resultado = [ resultado ; saida_rna_volume' ];
  
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
gravarDados( path_resultado + '\Saida\RESULTADO_VOLUME_'+codigoAtivo+'.txt', resultado );
printf( '\nResultado Gravado.' );