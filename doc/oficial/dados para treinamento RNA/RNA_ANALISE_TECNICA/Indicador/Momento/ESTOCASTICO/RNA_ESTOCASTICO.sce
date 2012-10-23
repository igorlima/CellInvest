path_rna_estocastico = get_absolute_file_path('RNA_ESTOCASTICO.sce');

N_ESTOCASTICO = getN( path_rna_estocastico + "\N_ESTOCASTICO.txt" );
W_ESTOCASTICO = getW( path_rna_estocastico, "ESTOCASTICO" );

function saida_da_rna = rna_estocastico( estocastico )
  saida_da_rna = ann_FF_run( [estocastico], N_ESTOCASTICO, W_ESTOCASTICO );
endfunction
