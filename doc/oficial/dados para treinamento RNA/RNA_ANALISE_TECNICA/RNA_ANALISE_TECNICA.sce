path_rna_analise_tec = get_absolute_file_path('RNA_ANALISE_TECNICA.sce');

exec( path_rna_analise_tec+"\_util.sce" );
exec( path_rna_analise_tec+"\_carregar_rede_de_treinamento.sce" );
exec( path_rna_analise_tec+"\_arquivo.sce" );
exec( path_rna_analise_tec+"\_dados.sce" );
  
exec( path_rna_analise_tec+"\Indicador\RNA_INDICADOR.sce" );

//rna_analise_tecnica( 'BBAS3', 52.11600, 0.00000, -138.22420, 239.700000, 377.924200, 15375314249 )
function saida_da_rna = rna_analise_tecnica( nome_do_ativo, ifr, estocastico, hist, macdLine, macdSinal, obv )
  ativo = getDados( nome_do_ativo, MAXIMO_LINHA_ARQUIVO );
  
  ifr = ifr/100;
  estocastico = estocastico/100;
  
  hist = normalizar( [ativo(:,3); hist ] );
  hist = hist( length(hist) );
  alphaHist = convert_to_alpha( [ativo(:,3); hist] );
  alphaHist = alphaHist( length(alphaHist) );
  
  macdLine = normalizar( [ativo(:,4); macdLine] );
  macdLine = macdLine( length(macdLine) );
  alphaMacdLine = convert_to_alpha( [ativo(:,4); macdLine] );
  alphaMacdLine = alphaMacdLine( length(alphaMacdLine) );
  macdSinal = normalizar( [ativo(:,5); macdSinal] );
  macdSinal = macdSinal( length(macdSinal) );
  alphaMacdSinal = convert_to_alpha( [ativo(:,5); macdSinal] );
  alphaMacdSinal = alphaMacdSinal( length(alphaMacdSinal) );
  
  alphaObv = convert_to_alpha( [ativo(:,6); obv] );
  alphaObv = alphaObv( length(alphaObv) );
  
  saida_da_rna = rna_indicador( ifr, estocastico, hist, alphaHist, macdLine, alphaMacdLine, macdSinal, alphaMacdSinal, alphaObv )
endfunction


