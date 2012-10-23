clear;
path_treina_OBV = get_absolute_file_path('treinamentoRede_OBV.sce');
exec( path_treina_OBV+"\..\..\..\_treinamento.sce" );

treinar( path_treina_OBV, "OBV" );

