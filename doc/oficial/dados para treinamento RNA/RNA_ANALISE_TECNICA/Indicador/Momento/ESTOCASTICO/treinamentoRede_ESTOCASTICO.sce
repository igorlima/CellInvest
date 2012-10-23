clear;
path_treina_ESTOCASTICO = get_absolute_file_path('treinamentoRede_ESTOCASTICO.sce');
exec( path_treina_ESTOCASTICO+"\..\..\..\_treinamento.sce" );

treinar( path_treina_ESTOCASTICO, "ESTOCASTICO" );
