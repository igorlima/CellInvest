clear;
path_treina_HISTOGRAMA = get_absolute_file_path('treinamentoRede_HISTOGRAMA.sce');
exec( path_treina_HISTOGRAMA+"\..\..\..\_treinamento.sce" );

treinar( path_treina_HISTOGRAMA, "HISTOGRAMA" );

