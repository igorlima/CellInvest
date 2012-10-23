path_rna_sinal = get_absolute_file_path('RNA_SINAL.sce');
exec( path_rna_sinal+"\HISTOGRAMA\RNA_HISTOGRAMA.sce" );
exec( path_rna_sinal+"\MACD\RNA_MACD.sce" );

N_SINAL = getN( path_rna_sinal + "\N_SINAL.txt" );
W_SINAL = getW( path_rna_sinal, "SINAL" );

function saida_da_rna = rna_sinal( macdLine, macdSinal, alphaMacdLine, alphaMacdSinal, alphaHIST )
  valorMACD = [rna_MACD( macdLine, macdSinal, alphaMacdLine, alphaMacdSinal )];
  valorHISTOGRAMA = [rna_HISTOGRAMA( alphaHIST )];
  valorSINAL = [valorMACD;valorHISTOGRAMA];
  saida_da_rna = ann_FF_run( [valorSINAL], N_SINAL, W_SINAL );
endfunction


