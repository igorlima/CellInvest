path_rna_indicador = get_absolute_file_path('RNA_INDICADOR.sce');
exec( path_rna_indicador+"\Momento\RNA_MOMENTO.sce" );
exec( path_rna_indicador+"\Sinal\RNA_SINAL.sce" );
exec( path_rna_indicador+"\Volume\RNA_VOLUME.sce" );

N_INDICADOR = getN( path_rna_indicador + "\N_INDICADOR.txt" );
W_INDICADOR = getW( path_rna_indicador, "INDICADOR" );

function saida_da_rna = rna_indicador( macdLine, macdSinal, alphaMacdLine, alphaMacdSinal, alphaHIST, alphaObv, ifr, estocastico )
  saida_rna_sinal = rna_sinal( macdLine, macdSinal, alphaMacdLine, alphaMacdSinal, alphaHIST );
  saida_rna_volume = rna_volume( alphaObv );
  saida_rna_momento = rna_momento( ifr, estocastico );
  entrada_rna = [ saida_rna_sinal; saida_rna_volume; saida_rna_momento ];
  saida_da_rna = ann_FF_run( [entrada_rna], N_INDICADOR, W_INDICADOR );
endfunction


