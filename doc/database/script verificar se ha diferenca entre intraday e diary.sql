SELECT id_ativo,
       date( data ) as data,
       abertura,
       maximo,
       minimo,
       fechamento,
       volume
FROM  ( (SELECT id_ativo                               AS id_ativo,
               Date_format(MAX(data_hora), '%Y-%m-%d') AS data,
               MAX(data_hora)                          AS data_hora,
               (SELECT abertura
                 FROM  intraday
                 WHERE i.id_ativo = id_ativo
                  AND Date_format(i.data_hora,'%Y-%m-%d') =
                      Date_format(data_hora,'%Y-%m-%d')
                 ORDER BY data_hora ASC
                 LIMIT 1)
                                                       AS abertura,
               MAX(maximo)                             AS maximo,
               MIN(minimo)                             AS minimo,
               (SELECT fechamento
                 FROM  intraday
                 WHERE i.id_ativo = id_ativo
                  AND Date_format(i.data_hora,'%Y-%m-%d') =
                      Date_format(data_hora,'%Y-%m-%d')
                 ORDER BY data_hora DESC
                 LIMIT 1)
                                                       AS fechamento,
               SUM(volume)                             AS volume,
               DAY(data_hora)                          AS dia,
               MONTH(data_hora)                        AS mes
        FROM   intraday i
        GROUP  BY id_ativo,
                  dia,
                  mes
        ORDER  BY data, id_ativo ) AS tb_diary  )
WHERE  CONCAT(id_ativo,   ',',
              DATE(data), ',',
              abertura,   ',',
              maximo,     ',',
              minimo,     ',',
              fechamento, ',',
              volume) <> ALL (SELECT CONCAT( id_ativo,   ',',
                                             DATE(data), ',',
                                             abertura,   ',',
                                             maximo,     ',',
                                             minimo,     ',',
                                             fechamento, ',',
                                             volume )
                               FROM   diary
                               WHERE  id_ativo = tb_diary.id_ativo
                                      AND Date_format(data_hora,'%Y-%m-%d') =
                                          Date_format(tb_diary.data_hora,'%Y-%m-%d')  );