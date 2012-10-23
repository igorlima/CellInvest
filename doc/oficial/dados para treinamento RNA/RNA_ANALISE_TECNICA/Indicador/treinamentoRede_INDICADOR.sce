clear;
path_treina_INDICADOR = get_absolute_file_path('treinamentoRede_INDICADOR.sce');
exec( path_treina_INDICADOR+"\..\_treinamento.sce" );

treinar( path_treina_INDICADOR, "INDICADOR" );

