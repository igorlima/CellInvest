clear;
path_treina_MOMENTO = get_absolute_file_path('treinamentoRede_MOMENTO.sce');
exec( path_treina_MOMENTO+"\..\..\_treinamento.sce" );

treinar( path_treina_MOMENTO, "MOMENTO" );

