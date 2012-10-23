clear;
path_classific_indicador = get_absolute_file_path('_gerar_classificacao_indicador.sce');
exec( path_classific_indicador+"\..\_carregar_rede_de_treinamento.sce" );
exec( path_classific_indicador+"\..\_arquivo.sce" );
exec( path_classific_indicador+"\..\_util.sce" );
exec( path_classific_indicador+"\..\_dados.sce" );

function gerar_classif_indicador( nome_do_ativo )
  y=0.035;
  x = getCotacao( nome_do_ativo, MAXIMO_LINHA_ARQUIVO );
  diferenca = ([x(2:length(x)) ; 0] - x);
  
  variacao_percentual = [];
  for i=1:length( x )
    variacao_percentual = [ variacao_percentual; diferenca(i)/x(i)];
  end
  
  //variacao_percentual = [0; variacao_percentual]
  //variacao_percentual = variacao_percentual(1:length(x))
  
  classificacoes = [];
  for i=1:length(variacao_percentual)
    classificacao = [];
    if variacao_percentual(i) > y
      classificacao = [ 1.00 0.10 0.01 ];
    elseif variacao_percentual(i) > y-0.005
      classificacao = [ 0.90 0.20 0.05 ];
    elseif variacao_percentual(i) > y-0.010
      classificacao = [ 0.80 0.30 0.10 ];
    elseif variacao_percentual(i) > y-0.015
      classificacao = [ 0.70 0.35 0.15 ];
    elseif variacao_percentual(i) > y-0.020
      classificacao = [ 0.65 0.40 0.20 ];
    elseif variacao_percentual(i) > y-0.025
      classificacao = [ 0.60 0.45 0.25 ];
    elseif variacao_percentual(i) < -y
      classificacao = [ 0.01 0.10 1.00 ];
    elseif variacao_percentual(i) < -y+0.005
      classificacao = [ 0.05 0.20 0.90 ];
    elseif variacao_percentual(i) < -y+0.010
      classificacao = [ 0.10 0.30 0.80 ];
    elseif variacao_percentual(i) < -y+0.015
      classificacao = [ 0.15 0.35 0.70 ];
    elseif variacao_percentual(i) < -y+0.020
      classificacao = [ 0.20 0.40 0.65 ];
    elseif variacao_percentual(i) < -y+0.025
      classificacao = [ 0.25 0.45 0.60 ];
    else
      classificacao = [ 0.50 0.50 0.50 ];
    end
    classificacoes = [ classificacoes ; classificacao ];
  end
  
  gravarDados( path_classific_indicador + '\CLASSE_INDICADOR_COMPRA.txt', classificacoes(:,1) );
  gravarDados( path_classific_indicador + '\CLASSE_INDICADOR_NEUTRA.txt', classificacoes(:,2) );
  gravarDados( path_classific_indicador + '\CLASSE_INDICADOR_VENDA.txt', classificacoes(:,3) );
endfunction