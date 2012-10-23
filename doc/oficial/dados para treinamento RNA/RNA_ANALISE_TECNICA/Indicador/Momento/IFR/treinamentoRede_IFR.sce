clear;
path_treina_IFR = get_absolute_file_path('treinamentoRede_IFR.sce');
exec( path_treina_IFR+"\..\..\..\_treinamento.sce" );

treinar( path_treina_IFR, "IFR" );