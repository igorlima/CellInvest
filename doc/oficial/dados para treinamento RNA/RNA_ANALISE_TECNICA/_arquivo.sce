function [dados] = dadosTreinamento( path_do_arquivo, nomeDoArquivo )
  qte_dados_entrada = extrairDados( path_do_arquivo + '\' + nomeDoArquivo + '.txt' , MAXIMO_LINHA_ARQUIVO );
  qte_dados_entrada = qte_dados_entrada(1,1);
  dados = [];
  for j=1:qte_dados_entrada
    dados = [ dados, extrairDados( path_do_arquivo+"\"+nomeDoArquivo+"_"+string(j)+".txt", MAXIMO_LINHA_ARQUIVO ) ];
  end
endfunction

function [N] = getN( arquivo )
  N = extrairDados( arquivo, MAXIMO_LINHA_ARQUIVO );
  N = N';
endfunction

function [dados] = extrairDados( arquivo, qteDados )
  dados = [];
  fid = mopen(arquivo, "r");
  if (fid == -1)
    mclose(fid);
    error("Nao foi possivel abrir o arquivo para leitura");
  end
  
  done_yet = 0;
  count = 1;
  while (done_yet == 0)
    [num_read, val(1)] = mfscanf(fid, "%f");
    
    if (num_read <= 0)
      done_yet = 1;
    end
    
    if (num_read ~= 1)
      done_yet = 1;
    end
    
    dados = [ dados; val(1) ];
    
    if( count >= qteDados )
      done_yet = 1;
    end
    count = count + 1;
  end
  
  mclose(fid);
endfunction

function gravarDados( arquivo, dados )
  fid = mopen(arquivo, "w");
  if (fid == -1)
    mclose(fid);
    error("Erro ao abrir arquivo para gravacao");
  end
  
  for i=1:size(dados,1)
    for j=1:size(dados,2)
      mfprintf(fid, "%f ", dados(i,j) );
    end
    mfprintf(fid, "\n" );
  end
  
  mclose(fid);  
endfunction

function gravarWn( dados, path_do_arquivo, nomeDoArquivo, n )
  for j=1:size(dados,2)
    gravarDados( path_do_arquivo+"\W"+string(n)+"_"+nomeDoArquivo+"_"+string(j)+".txt", dados(:,j) );
  end
endfunction

function gravarW( W, path_do_arquivo, nomeDoArquivo )
  gravarDados( path_do_arquivo+"\W_"+nomeDoArquivo+".txt", size( W )' );
  
  sizeW =  size( W );
  for n=1:sizeW(3)
    gravarWn( W(:,:,n), path_do_arquivo, nomeDoArquivo, n );
  end
  
  disp( "Gravacao concluida do W_"+nomeDoArquivo+"!" );
endfunction


function [dados] = getWn( path_do_arquivo, nomeDoArquivo, n )
  size_W = extrairDados( path_do_arquivo+"\W_"+nomeDoArquivo+".txt", MAXIMO_LINHA_ARQUIVO );
  qte_de_coluna = size_W( 2 );
  dados = [];
  for j=1:qte_de_coluna
    dados = [ dados, extrairDados( path_do_arquivo+"\W"+string(n)+"_"+nomeDoArquivo+"_"+string(j)+".txt", MAXIMO_LINHA_ARQUIVO ) ];
  end
endfunction

function [W] = getW( path_do_arquivo, nomeDoArquivo )  
  W = [[][][]];
  
  sizeW =  extrairDados( path_do_arquivo+"\W_"+nomeDoArquivo+".txt", MAXIMO_LINHA_ARQUIVO );
  for n=1:sizeW(3)
    W(:,:,n) = getWn( path_do_arquivo, nomeDoArquivo, n );
  end
  
endfunction
