path_rna_momento = get_absolute_file_path('RNA_MOMENTO.sce');
exec( path_rna_momento+"\IFR\RNA_IFR.sce" );
exec( path_rna_momento+"\ESTOCASTICO\RNA_ESTOCASTICO.sce" );

N_MOMENTO = getN( path_rna_momento + "\N_MOMENTO.txt" );
W_MOMENTO = getW( path_rna_momento, "MOMENTO" );

function saida_da_rna = rna_momento( ifr, estocastico )
  saida_da_rna = ann_FF_run( [ rna_ifr(ifr); rna_estocastico(estocastico) ], N_MOMENTO, W_MOMENTO );
endfunction

