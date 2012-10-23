path_rna_obv = get_absolute_file_path('RNA_OBV.sce');

N_OBV = getN( path_rna_obv + "\N_OBV.txt" );
W_OBV = getW( path_rna_obv, "OBV" );

function saida_da_rna = rna_obv( alpha_obv )
  saida_da_rna = ann_FF_run( [alpha_obv], N_OBV, W_OBV );
endfunction

