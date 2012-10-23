root_dados_entrada = get_absolute_file_path('_input_data_file.sce');

function [dados] = getInputDataFile()
  input_data = extrairDados( root_dados_entrada+"\input_data.txt", 9 );
  dados = [ input_data ];
endfunction
