path_rna_HISTOGRAMA = get_absolute_file_path('RNA_HISTOGRAMA.sce');

N_HISTOGRAMA = getN( path_rna_HISTOGRAMA + "\N_HISTOGRAMA.txt" );
W_HISTOGRAMA = getW( path_rna_HISTOGRAMA, "HISTOGRAMA" );

function saida_da_rna = rna_HISTOGRAMA( alphaHIST )
  saida_da_rna = ann_FF_run( [alphaHIST], N_HISTOGRAMA, W_HISTOGRAMA );
endfunction

