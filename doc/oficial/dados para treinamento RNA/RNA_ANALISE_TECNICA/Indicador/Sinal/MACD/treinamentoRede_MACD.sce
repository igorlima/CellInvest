clear;
path_treina_MACD = get_absolute_file_path('treinamentoRede_MACD.sce');
exec( path_treina_MACD+"\..\..\..\_treinamento.sce" );

treinar( path_treina_MACD, "MACD" );

