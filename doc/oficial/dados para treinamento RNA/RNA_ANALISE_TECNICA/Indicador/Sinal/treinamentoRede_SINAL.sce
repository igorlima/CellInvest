clear;
path_treina_SINAL = get_absolute_file_path('treinamentoRede_SINAL.sce');
exec( path_treina_SINAL+"\..\..\_treinamento.sce" );

treinar( path_treina_SINAL, "SINAL" );

