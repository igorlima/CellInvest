clear;
path_rna = get_absolute_file_path('rna.sce');

exec( path_rna+"\_input_data_file.sce" );
exec( path_rna+"\..\RNA_ANALISE_TECNICA.sce" );

printf( 'Iniciando rna...\n' );
input_data = getInputDataFile();
output_data = rna_indicador(input_data(1),input_data(2),input_data(3),input_data(4),input_data(5),input_data(6),input_data(7),input_data(8),input_data(9));
gravarDados( path_rna + '\output_data.txt', output_data );
printf( 'Rna executada com sucesso.' );
exit;
