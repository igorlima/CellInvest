root_dados_entrada = get_absolute_file_path('_dados_entrada.sce');

function [dados] = getDadosEntrada( codigoAtivo, qteDados )
  str_ifr = "IFR_";
  str_estocastico = "ESTOCASTICO_";
  str_histograma = "HISTOGRAMA_";
  str_macd_line = "MACD_LINE_";
  str_macd_sinal = "MACD_SINAL_";
  str_obv = "OBV_";
  
  dados_ifr = extrairDados( root_dados_entrada+"\DadosEntrada\"+ str_ifr +codigoAtivo+".txt", qteDados );
  dados_estocastico = extrairDados( root_dados_entrada+"\DadosEntrada\"+ str_estocastico +codigoAtivo+".txt", qteDados );
  dados_histograma = extrairDados( root_dados_entrada+"\DadosEntrada\"+ str_histograma +codigoAtivo+".txt", qteDados );
  dados_macd_line = extrairDados( root_dados_entrada+"\DadosEntrada\"+ str_macd_line +codigoAtivo+".txt", qteDados );
  dados_macd_sinal = extrairDados( root_dados_entrada+"\DadosEntrada\"+ str_macd_sinal +codigoAtivo+".txt", qteDados );
  dados_obv = extrairDados( root_dados_entrada+"\DadosEntrada\"+ str_obv +codigoAtivo+".txt", qteDados );
  
  dados = [];
  dados = [ dados_ifr, dados_estocastico, dados_histograma, dados_macd_line, dados_macd_sinal, dados_obv ];
endfunction

function [dados] = getCotacaoEntrada( codigoAtivo, qteDados )
  dados = [];
  str_classificacao = "COTACAO_";
  
  dados_classificacao = extrairDados( root_dados_entrada+"\DadosEntrada\"+ str_classificacao +codigoAtivo+".txt", qteDados );
  dados = [ dados_classificacao ];
endfunction
