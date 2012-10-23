path_rna_MACD = get_absolute_file_path('RNA_MACD.sce');

N_MACD = getN( path_rna_MACD + "\N_MACD.txt" );
W_MACD = getW( path_rna_MACD, "MACD" );

function saida_da_rna = rna_MACD( macdLine, macdSinal, alphaMacdLine, alphaMacdSinal )
  saida_da_rna = ann_FF_run( [macdLine;macdSinal;alphaMacdLine;alphaMacdSinal], N_MACD, W_MACD );
endfunction

