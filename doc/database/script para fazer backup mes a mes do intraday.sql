/*
Script para fazer backup mês a mês da tabela intraday
*/
SELECT * 
FROM   bolsa.intraday
WHERE  Date_format(intraday.data_hora, '%Y-%m-%d') LIKE '2011-01-%' 
ORDER  BY data_hora DESC



INSERT INTO bolsa_backup.intraday_2011jan
            (id_ativo,
             data_hora,
             abertura,
             maximo,
             minimo,
             fechamento,
             volume)
(SELECT id_ativo,
        data_hora,
        abertura,
        maximo,
        minimo,
        fechamento,
        volume
 FROM   bolsa.intraday
 WHERE  Date_format(intraday.data_hora, '%Y-%m-%d') LIKE '2011-01-%' );


/* ******************************************************************** */