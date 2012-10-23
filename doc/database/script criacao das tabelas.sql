/*
criação de um novo database e criação de usuários
*/
CREATE DATABASE bolsaaux;
CREATE USER bolsaaux IDENTIFIED BY 'bolsaaux';
GRANT ALL ON bolsaaux.* TO bolsaaux;
GRANT SELECT ON bolsa.* TO bolsaaux;


CREATE USER 'igorribeirolima'@'localhost' IDENTIFIED BY 'im123654';
GRANT ALL ON bolsa.* TO 'igorribeirolima'@'localhost';
SET PASSWORD FOR 'bob'@'%.loc.gov' = PASSWORD('newpass');

SELECT host, user FROM mysql.user;

DROP USER user@host;
DROP USER igorribeirolima@localhost;

SHOW GRANTS FOR igorribeirolima;
SHOW GRANTS FOR igorribeirolima@localhost;

CREATE SCHEMA IF NOT EXISTS bolsaaux;
ALTER TABLE minhatabela ENGINE = InnoDB;
ALTER TABLE minhatabela TYPE = InnoDB;
/* ****************************************** */



/*
Criação das tabelas
*/
DROP TABLE IF EXISTS ativo;  
CREATE TABLE ativo
  (
     id             SMALLINT NOT NULL AUTO_INCREMENT,
     codigo         VARCHAR(10) NOT NULL,
     nome_pregao    VARCHAR(64) NOT NULL,
     nome_companhia VARCHAR(45) NOT NULL,
     PRIMARY KEY (id),
     UNIQUE KEY codigo (codigo)
  )
ENGINE=InnoDB
DEFAULT CHARSET=latin1
AUTO_INCREMENT=1;


DROP TABLE IF EXISTS intraday;  
CREATE TABLE intraday
  (
     id_ativo   SMALLINT NOT NULL,
     data_hora  TIMESTAMP NOT NULL,
     abertura   DECIMAL(6, 2) NULL,
     maximo     DECIMAL(6, 2) NULL,
     minimo     DECIMAL(6, 2) NULL,
     fechamento DECIMAL(6, 2) NULL,
     volume     BIGINT NULL,
     PRIMARY KEY (id_ativo, data_hora)
  )
ENGINE = InnoDB 
--PARTITION BY HASH(id_ativo)
--PARTITIONS 1000;
ALTER TABLE intraday ADD CONSTRAINT FOREIGN KEY fk_intraday_id_ativo(id_ativo) REFERENCES ativo(id);


DROP TABLE IF EXISTS diary;  
CREATE TABLE diary
  (
     id_ativo   SMALLINT NOT NULL,
     data       TIMESTAMP NOT NULL,
     abertura   DECIMAL(6, 2) NULL,
     maximo     DECIMAL(6, 2) NULL,
     minimo     DECIMAL(6, 2) NULL,
     fechamento DECIMAL(6, 2) NULL,
     volume     BIGINT NULL,
     PRIMARY KEY (id_ativo, data)
  )
ENGINE = InnoDB;
ALTER TABLE diary ADD CONSTRAINT FOREIGN KEY fk_diary_id_ativo(id_ativo) REFERENCES ativo(id);



DROP TABLE IF EXISTS usuario;
CREATE TABLE usuario
  (
     id         INT NOT NULL AUTO_INCREMENT,
     login      VARCHAR(20) NOT NULL,
     senha      VARCHAR(30) NOT NULL,
     nome       VARCHAR(100) NOT NULL,
     email      VARCHAR(100) NOT NULL,
     PRIMARY KEY (id),
     UNIQUE KEY uk_do_login_do_usuario (login)
  )
ENGINE = InnoDB
AUTO_INCREMENT=1;


DROP TABLE IF EXISTS portifolio;
CREATE TABLE portifolio
  (
     id_do_usuario   INT NOT NULL,
     id_do_ativo     SMALLINT NOT NULL,
     PRIMARY KEY (id_do_usuario, id_do_ativo)
  )
ENGINE = InnoDB;
ALTER TABLE portifolio ADD CONSTRAINT FOREIGN KEY fk_portifolio_id_do_usuario(id_do_usuario) REFERENCES usuario(id);
ALTER TABLE portifolio ADD CONSTRAINT FOREIGN KEY fk_portifolio_id_do_ativo  (id_do_ativo)   REFERENCES ativo(id);



DROP TABLE IF EXISTS noticia;
CREATE TABLE noticia
  (
     id            BIGINT    NOT NULL AUTO_INCREMENT,
     id_do_ativo   SMALLINT  NULL,
     data          TIMESTAMP NOT NULL,
     manchete      VARCHAR(250)  NOT NULL,
     conteudo      VARCHAR(1000) NULL,
     link          VARCHAR(256)  NULL,
     PRIMARY KEY (id),
     UNIQUE KEY noticia_unica (id_do_ativo,data,manchete),
     INDEX (data),
     INDEX (id_do_ativo)
  )
ENGINE = InnoDB
AUTO_INCREMENT=1;
ALTER TABLE noticia ADD CONSTRAINT FOREIGN KEY fk_noticia_id_do_ativo (id_do_ativo)   REFERENCES ativo(id);




/* **************************************************************************************************** */








/*
Select pra transferir elementos de um schema pra outro
*/
INSERT INTO bolsaaux.ativo
            (id,
             codigo,
             nome_pregao,
             nome_companhia)
(SELECT id,
        codigo,
        nome_pregao,
        nome_companhia
 FROM   bolsa.ativo);



INSERT INTO bolsaaux.diary
            (id_ativo,
             data,
             abertura,
             maximo,
             minimo,
             fechamento,
             volume)
(SELECT id_ativo,
        data,
        abertura,
        maximo,
        minimo,
        fechamento,
        volume
 FROM   bolsa.diary);
 
/* ****************************************************** */


/*
Select para verificar eventuais duplicidades nas tabelas
*/
SELECT id_ativo,
       data_hora,
       COUNT(*)
FROM   cotacao_intraday
GROUP  BY id_ativo,
          data_hora
HAVING COUNT(*) > 1;
/* ****************************************************** */