clear;
path_gerador_de_entrada = get_absolute_file_path('_gerar_entrada_indicador.sce');
exec( path_gerador_de_entrada+"\..\_carregar_rede_de_treinamento.sce" );
exec( path_gerador_de_entrada+"\..\_arquivo.sce" );
exec( path_gerador_de_entrada+"\..\_util.sce" );
exec( path_gerador_de_entrada+"\..\_dados.sce" );

exec( path_gerador_de_entrada+"\Momento\RNA_MOMENTO.sce" );
exec( path_gerador_de_entrada+"\Sinal\RNA_SINAL.sce" );
exec( path_gerador_de_entrada+"\Volume\RNA_VOLUME.sce" );

function gerar_entrada_indicador( nome_do_ativo )
  ativo = getDados( nome_do_ativo, MAXIMO_LINHA_ARQUIVO );
  
  //Armazenar a quantidade de dados de cada analise tecnica do ativo
  qte_dados_analise = length( ativo(:,1) );
  
  //Calcular saida do Momento
  saida_momento = [];
  for momento_i=1:qte_dados_analise
    saida_momento = [ saida_momento, rna_momento( ativo(momento_i,1)/100, ativo(momento_i,2)/100 ) ];
  end
  
  //Calcular saida do Sinal
  histograma = normalizar( ativo(:,3) );
  alpha_histograma = convert_to_alpha( ativo(:,3) );
  macd_line = normalizar( ativo(:,4) );
  alpha_macd_line = convert_to_alpha( ativo(:,4) );
  macd_sinal = normalizar( ativo(:,5) );
  alpha_macd_sinal = convert_to_alpha( ativo(:,5) );
  
  saida_sinal = [];
  for sinal_i=1:qte_dados_analise
    saida_sinal = [ saida_sinal, rna_sinal( histograma(sinal_i), alpha_histograma(sinal_i), macd_line(sinal_i), alpha_macd_line(sinal_i), macd_sinal(sinal_i), alpha_macd_sinal(sinal_i) ) ];
  end

  //Calcular saida do Volume
  alpha_obv = convert_to_alpha( ativo(:,6) );
  
  saida_volume = [];
  for volume_i=1:qte_dados_analise
    saida_volume = [saida_volume, rna_volume( alpha_obv(volume_i) ) ];
  end
  
  gravarDados( path_gerador_de_entrada + '\INDICADOR_1.txt', saida_momento(1,:)' );
  gravarDados( path_gerador_de_entrada + '\INDICADOR_2.txt', saida_momento(2,:)' );
  gravarDados( path_gerador_de_entrada + '\INDICADOR_3.txt', saida_momento(3,:)' );
  gravarDados( path_gerador_de_entrada + '\INDICADOR_4.txt', saida_sinal(1,:)' );
  gravarDados( path_gerador_de_entrada + '\INDICADOR_5.txt', saida_sinal(2,:)' );
  gravarDados( path_gerador_de_entrada + '\INDICADOR_6.txt', saida_sinal(3,:)' );
  gravarDados( path_gerador_de_entrada + '\INDICADOR_7.txt', saida_volume(1,:)' );
  gravarDados( path_gerador_de_entrada + '\INDICADOR_8.txt', saida_volume(2,:)' );
  gravarDados( path_gerador_de_entrada + '\INDICADOR_9.txt', saida_volume(3,:)' );
endfunction