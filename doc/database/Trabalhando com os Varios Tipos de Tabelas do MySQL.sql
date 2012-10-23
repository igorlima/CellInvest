/*
http://www.sqlmagazine.com.br/Colunistas/eber/05_TabelasMySQL.asp



Trabalhando com os Vários Tipos de Tabelas do MySQL

O MySQL possui uma característica um pouco diferente dos outros sistemas gerenciadores de banco de dados, uma vez que no MySQL é possível escolher o tipo da tabela no momento da criação da mesma. O formato de armazenamento dos dados, bem como alguns recursos do banco de dados são dependentes do tipo de tabela escolhido. Neste artigo apresento os tipos de tabelas suportados pelo MySQL e as suas características.

A definição do tipo de tabela é feito através do comando CREATE TABLE, como mostrado a seguir:
*/

CREATE TABLE teste (
        id INT NOT NULL,
        texto CHAR(30) NOT NULL,
        PRIMARY KEY (id)
) TYPE=MyISAM;


/*
No comando acima, TYPE=MyISAM, indica que a tabela criada será do tipo MyISAM, que é o valor default caso não seja informado o TYPE. A partir da versão 4.0.18 você pode utilizar ENGINE como sinônimo de TYPE. A seguir estão apresentados os tipos de tabelas existentes no MySQL:

1. MyISAM:
Este é o tipo de tabela padrão do MySQL. Caso não seja informado o tipo de tabela, o MySQL criará a tabela do tipo MyISAM. O tipo de tabela default pode ser alterado incluindo-se no arquivo de configuração, a opção descrita a seguir: default-table-type=tipo.
As tabelas MyISAM são armazenadas em 3 arquivos: .FRM que armazena a definição da tabela, o arquivo .MYD que contém os dados e o .MYI contendo os índices. Estas tabelas são de grande desempenho para leitura, uma vez que os seus índices são armazenados em árvores binárias balanceadas, o que provê um ganho para o acesso às informações. O MyISAM não provê controle de transações (commit ou rollback) e também não possue integridade referencial, isto é, ao incluir uma chave estrangeira com alguns constraints, esta servirá apenas como documentação, mas as restrições não serão respeitadas pelo banco. Como as tabelas MyISAM são representadas por arquivos no modelo uma tabela para três arquivos, existe, em alguns sistemas operacionais, restrições quanto ao tamanho destas tabelas. No Linux, por exemplo existe a restrição de 2G/4GB por arquivo, com isto uma tabela MyISAM poderia ter somente 2G/4GB de dados. Para superar esta limitação foi introduzido na versão 4.1 a opção RAID_TYPE utilizada no CREATE TABLE, que permite ao próprio MySQL dividir o armazenamento da tabela em vários arquivos. Com isto, o tamanho da tabela MyISAM não fica preso ao tamanho máximo de arquivo do sistema operacional. O principal problema encontrado com as tabelas MyISAM é que elas possuem um mecanismo de locks por tabela. Assim, todas as vezes que há uma escrita na tabela, o MySQL precisa travar a tabela como um todo, o que bloqueia por um instante o acesso à esta tabela, mesmo para processos que tentem acessar registros que estão sendo modificados. Assim, é gerada uma fila de espera até que o lock seja liberado e os outros processos possam ser executados. Por outro lado, estas tabelas são extremamente rápidas, devido à sua arquitetura simplificada.

2. HEAP:
Tabelas HEAP são armazenadas em memória e por isto são extramente rápidas, por outro lado o seu conteúdo é volátil, uma vez que não são gravadas em disco. Caso haja uma queda do SGBD os dados destas tabelas serão perdidos. Além disto, é necessário um processo para dar a carga inicial nos dados quando o servidor de banco iniciar e sua execução. A principal aplicação das tabelas HEAP seria para tabelas que são consultadas com muita frequência, mas que não sofrem muitas alterações (lookup tables).

3. MERGE:
As tabelas MERGE são coleções de tabelas MyISAM idênticas. Este recurso permite a divisão de uma tabela grande em várias partes menores, e ainda assim permite acesso ao conteúdo de todas elas como se fossem uma única tabela. O exemplo a seguir ilustra a criação de uma tabela MERGE:
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
Percebam que as tabelas t1, t2 e total são idênticas e as duas primeiras são tabelas MyISAM (foi omitido o TYPE no comando CREATE TABLE). Ao se submeter um SELECT na tabela total, você tem acesso ao conteúdo das tabelas t1 e t2, simultaneamente. A principal vantagem da tabela MERGE é permitir a divisão de tabelas grandes em tabelas pequenas, mais facilmente gerenciadas. Também permite superar as limitações do sistema operacional em relação ao tamanho de arquivos, uma vez que a tabela seria dividida em várias partes, onde cada parte poderia atingir o tamanho máximo de arquivo. A desvantagem é que podemos fazer apenas MERGE de tabelas MyISAM idênticas.

4. BDB:
O tipo de tabela BDB vem de BerkeleyDB, e é desenvolvido pela Sleepycat (http://www.sleepycat.com). O BDB provê ao MySQL um manipulador de tabelas com controle de transação, isto é, você poderá utilizar os comandos COMMIT e ROLLBACK, além de fornecer a recuperação automática de dados em caso de queda do sistema. O BDB apresenta um mecanismo de lock em nível de página, onde apenas os dados de uma mesma página ficarão bloqueados durante um perído de lock. além disto, você poderá ter vários locks ativos numa única tabela BDB, uma vez que a tabela poderá ser constituída de várias páginas.

5. InnoDB:
O InnoDB é um tipo de tabela transacional, desenvolvido pela InnoDBase Oy. A partir da versão 4.0 do MySQL ele passa a ser parte integrada das distribuições do MySQL. O InnoDB apresenta, além da capacidade transacional, outros recursos importantes:
->Integridade referencial, com implementação dos constraints SET NULL, SET DEFAULT, RESTRICT e CASCADE;
->Ferramenta de backup on-line (ferramenta comercial, não GPL);
->Lock de registro, como Oracle, DB2, etc;
->Níveis de isolamento;
->Armazenamentos de dados em tablespace.
Por se tratar de um tipo de tabela com recursos mais avançados, requer mais espaço em memória e disco, além de se apresentar, em determinadas situações, um pouco mais lento que MyISAM. Apesar disto, o InnoDB tem se mostrado extremamente rápido se comparado com outros SGBDs transacionais.

A vantagem da utilização de tipos de tabela é que você poderá optar por utilizar um determinado tipo ou outro dependendo dos requisitos exigidos pela sua aplicação. Por exemplo, se a sua aplicação é apenas de consulta você pode optar pelo tipo MyISAM em vez do InnoDB, com isto você evita o overhead da transação, obtendo um maior desempenho. É importante lembrar que você poderá utilizar vários tipos de tabelas em um mesmo banco de dados. Neste caso, deve-se tomar cuidado com a utilização de determinados recursos, por exemplo, se você tiver uma transação envolvendo tabelas InnoDB e MyISAM e você submeter um comando ROLLBACK, apenas os comandos executados no InnoDB serão desfeitos, enquanto que os comandos executados no MyISAM persistirão. A utilização do tipo de tabela correto é um fator chave para determinar o desempenho da sua aplicação.
*/

