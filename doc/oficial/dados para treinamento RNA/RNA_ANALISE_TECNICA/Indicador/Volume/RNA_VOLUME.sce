path_rna_volume = get_absolute_file_path('RNA_VOLUME.sce');
exec( path_rna_volume+"\OBV\RNA_OBV.sce" );

N_VOLUME = getN( path_rna_volume + "\N_VOLUME.txt" );
W_VOLUME = getW( path_rna_volume, "VOLUME" );

function saida_da_rna = rna_volume( alpha_obv )
  saida_da_rna = ann_FF_run( [ rna_obv(alpha_obv) ], N_VOLUME, W_VOLUME );
endfunction

