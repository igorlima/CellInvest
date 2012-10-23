path_rna_ifr = get_absolute_file_path('RNA_IFR.sce');

N_IFR = getN( path_rna_ifr + "\N_IFR.txt" );
W_IFR = getW( path_rna_ifr, "IFR" );

function saida_da_rna = rna_ifr( ifr )
  saida_da_rna = ann_FF_run( [ifr], N_IFR, W_IFR );
endfunction
