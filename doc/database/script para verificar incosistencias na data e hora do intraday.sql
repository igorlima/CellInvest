SELECT * 
FROM   intraday, 
       ativo 
WHERE  intraday.id_ativo = ativo.id 
       AND ativo.codigo IS NOT NULL 
       AND Date_format(intraday.data_hora, '%s') <> '00' 
       AND Date_format(intraday.data_hora, '%Y-%m-%d') LIKE '%' 
ORDER  BY data_hora DESC; 



DELETE i1 FROM intraday as i1,
(SELECT * 
FROM   intraday, 
       ativo 
WHERE  intraday.id_ativo = ativo.id 
       AND ativo.codigo IS NOT NULL 
       AND Date_format(intraday.data_hora, '%s') <> '00' 
       AND Date_format(intraday.data_hora, '%Y-%m-%d') LIKE '%' 
) as i2
WHERE i1.id_ativo = i2.id_ativo and i1.data_hora = i2.data_hora;


