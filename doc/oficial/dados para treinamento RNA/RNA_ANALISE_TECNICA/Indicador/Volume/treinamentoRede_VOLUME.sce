clear;
path_treina_VOLUME = get_absolute_file_path('treinamentoRede_VOLUME.sce');
exec( path_treina_VOLUME+"\..\..\_treinamento.sce" );

treinar( path_treina_VOLUME, "VOLUME" );

