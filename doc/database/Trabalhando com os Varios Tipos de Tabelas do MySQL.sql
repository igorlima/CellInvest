/*
http://www.sqlmagazine.com.br/Colunistas/eber/05_TabelasMySQL.asp



Trabalhando com os V�rios Tipos de Tabelas do MySQL

O MySQL possui uma caracter�stica um pouco diferente dos outros sistemas gerenciadores de banco de dados, uma vez que no MySQL � poss�vel escolher o tipo da tabela no momento da cria��o da mesma. O formato de armazenamento dos dados, bem como alguns recursos do banco de dados s�o dependentes do tipo de tabela escolhido. Neste artigo apresento os tipos de tabelas suportados pelo MySQL e as suas caracter�sticas.

A defini��o do tipo de tabela � feito atrav�s do comando CREATE TABLE, como mostrado a seguir:
*/

CREATE TABLE teste (
        id INT NOT NULL,
        texto CHAR(30) NOT NULL,
        PRIMARY KEY (id)
) TYPE=MyISAM;


/*
No comando acima, TYPE=MyISAM, indica que a tabela criada ser� do tipo MyISAM, que � o valor default caso n�o seja informado o TYPE. A partir da vers�o 4.0.18 voc� pode utilizar ENGINE como sin�nimo de TYPE. A seguir est�o apresentados os tipos de tabelas existentes no MySQL:

1. MyISAM:
Este � o tipo de tabela padr�o do MySQL. Caso n�o seja informado o tipo de tabela, o MySQL criar� a tabela do tipo MyISAM. O tipo de tabela default pode ser alterado incluindo-se no arquivo de configura��o, a op��o descrita a seguir: default-table-type=tipo.
As tabelas MyISAM s�o armazenadas em 3 arquivos: .FRM que armazena a defini��o da tabela, o arquivo .MYD que cont�m os dados e o .MYI contendo os �ndices. Estas tabelas s�o de grande desempenho para leitura, uma vez que os seus �ndices s�o armazenados em �rvores bin�rias balanceadas, o que prov� um ganho para o acesso �s informa��es. O MyISAM n�o prov� controle de transa��es (commit ou rollback) e tamb�m n�o possue integridade referencial, isto �, ao incluir uma chave estrangeira com alguns constraints, esta servir� apenas como documenta��o, mas as restri��es n�o ser�o respeitadas pelo banco. Como as tabelas MyISAM s�o representadas por arquivos no modelo uma tabela para tr�s arquivos, existe, em alguns sistemas operacionais, restri��es quanto ao tamanho destas tabelas. No Linux, por exemplo existe a restri��o de 2G/4GB por arquivo, com isto uma tabela MyISAM poderia ter somente 2G/4GB de dados. Para superar esta limita��o foi introduzido na vers�o 4.1 a op��o RAID_TYPE utilizada no CREATE TABLE, que permite ao pr�prio MySQL dividir o armazenamento da tabela em v�rios arquivos. Com isto, o tamanho da tabela MyISAM n�o fica preso ao tamanho m�ximo de arquivo do sistema operacional. O principal problema encontrado com as tabelas MyISAM � que elas possuem um mecanismo de locks por tabela. Assim, todas as vezes que h� uma escrita na tabela, o MySQL precisa travar a tabela como um todo, o que bloqueia por um instante o acesso � esta tabela, mesmo para processos que tentem acessar registros que est�o sendo modificados. Assim, � gerada uma fila de espera at� que o lock seja liberado e os outros processos possam ser executados. Por outro lado, estas tabelas s�o extremamente r�pidas, devido � sua arquitetura simplificada.

2. HEAP:
Tabelas HEAP s�o armazenadas em mem�ria e por isto s�o extramente r�pidas, por outro lado o seu conte�do � vol�til, uma vez que n�o s�o gravadas em disco. Caso haja uma queda do SGBD os dados destas tabelas ser�o perdidos. Al�m disto, � necess�rio um processo para dar a carga inicial nos dados quando o servidor de banco iniciar e sua execu��o. A principal aplica��o das tabelas HEAP seria para tabelas que s�o consultadas com muita frequ�ncia, mas que n�o sofrem muitas altera��es (lookup tables).

3. MERGE:
As tabelas MERGE s�o cole��es de tabelas MyISAM id�nticas. Este recurso permite a divis�o de uma tabela grande em v�rias partes menores, e ainda assim permite acesso ao conte�do de todas elas como se fossem uma �nica tabela. O exemplo a seguir ilustra a cria��o de uma tabela MERGE:
*/




CREATE TABLE t1 (
        a INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
        message CHAR(20)
);

CREATE TABLE t2 (
        a INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
        message CHAR(20)
);

INSERT INTO t1 (message) VALUES ('Testing'),('table'),('t1');
INSERT INTO t2 (message) VALUES ('Testing'),('table'),('t2');

CREATE TABLE total (
        a INT NOT NULL AUTO_INCREMENT,
        message CHAR(20), INDEX(a)
)TYPE=MERGE UNION=(t1,t2);

SELECT * FROM total;


/*
Percebam que as tabelas t1, t2 e total s�o id�nticas e as duas primeiras s�o tabelas MyISAM (foi omitido o TYPE no comando CREATE TABLE). Ao se submeter um SELECT na tabela total, voc� tem acesso ao conte�do das tabelas t1 e t2, simultaneamente. A principal vantagem da tabela MERGE � permitir a divis�o de tabelas grandes em tabelas pequenas, mais facilmente gerenciadas. Tamb�m permite superar as limita��es do sistema operacional em rela��o ao tamanho de arquivos, uma vez que a tabela seria dividida em v�rias partes, onde cada parte poderia atingir o tamanho m�ximo de arquivo. A desvantagem � que podemos fazer apenas MERGE de tabelas MyISAM id�nticas.

4. BDB:
O tipo de tabela BDB vem de BerkeleyDB, e � desenvolvido pela Sleepycat (http://www.sleepycat.com). O BDB prov� ao MySQL um manipulador de tabelas com controle de transa��o, isto �, voc� poder� utilizar os comandos COMMIT e ROLLBACK, al�m de fornecer a recupera��o autom�tica de dados em caso de queda do sistema. O BDB apresenta um mecanismo de lock em n�vel de p�gina, onde apenas os dados de uma mesma p�gina ficar�o bloqueados durante um per�do de lock. al�m disto, voc� poder� ter v�rios locks ativos numa �nica tabela BDB, uma vez que a tabela poder� ser constitu�da de v�rias p�ginas.

5. InnoDB:
O InnoDB � um tipo de tabela transacional, desenvolvido pela InnoDBase Oy. A partir da vers�o 4.0 do MySQL ele passa a ser parte integrada das distribui��es do MySQL. O InnoDB apresenta, al�m da capacidade transacional, outros recursos importantes:
->Integridade referencial, com implementa��o dos constraints SET NULL, SET DEFAULT, RESTRICT e CASCADE;
->Ferramenta de backup on-line (ferramenta comercial, n�o GPL);
->Lock de registro, como Oracle, DB2, etc;
->N�veis de isolamento;
->Armazenamentos de dados em tablespace.
Por se tratar de um tipo de tabela com recursos mais avan�ados, requer mais espa�o em mem�ria e disco, al�m de se apresentar, em determinadas situa��es, um pouco mais lento que MyISAM. Apesar disto, o InnoDB tem se mostrado extremamente r�pido se comparado com outros SGBDs transacionais.

A vantagem da utiliza��o de tipos de tabela � que voc� poder� optar por utilizar um determinado tipo ou outro dependendo dos requisitos exigidos pela sua aplica��o. Por exemplo, se a sua aplica��o � apenas de consulta voc� pode optar pelo tipo MyISAM em vez do InnoDB, com isto voc� evita o overhead da transa��o, obtendo um maior desempenho. � importante lembrar que voc� poder� utilizar v�rios tipos de tabelas em um mesmo banco de dados. Neste caso, deve-se tomar cuidado com a utiliza��o de determinados recursos, por exemplo, se voc� tiver uma transa��o envolvendo tabelas InnoDB e MyISAM e voc� submeter um comando ROLLBACK, apenas os comandos executados no InnoDB ser�o desfeitos, enquanto que os comandos executados no MyISAM persistir�o. A utiliza��o do tipo de tabela correto � um fator chave para determinar o desempenho da sua aplica��o.
*/

