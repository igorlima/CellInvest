-- COMMAND MYSQL --
-- create database [databasename];
-- show databases;
-- use [db name];
-- show tables;
-- describe [table name];
-- drop database [database name];
-- drop table [table name];
-- show columns from [table name];
-- grant all privileges on databasename.* to username@localhost;

-- HOW TO IMPORT SQL DUMP FILE to mySQL database --
-- select host, user from mysql.user;
-- create user 'monty'@'localhost' IDENTIFIED BY 'some_pass';
-- mysql -u username -p password database_name < filename.sql 
-- mysql -u baseu01 -p h4z56s3 database01 < export.sql
-- mysql.exe -u root -p bolsa < c:\Users\igorlima\Downloads\2011mar.sql

--
-- Database: fuzzylogic
--

DROP TABLE IF EXISTS Alias;
CREATE TABLE IF NOT EXISTS Alias 
	(
	  idAlias BIGINT(20) NOT NULL AUTO_INCREMENT,
	  txAlias VARCHAR(255) NOT NULL,
	  txValor VARCHAR(255) NOT NULL,
	  PRIMARY KEY  (idAlias)
	) 
ENGINE=InnoDB  
DEFAULT 
CHARSET=latin1 
AUTO_INCREMENT=1;



DROP TABLE IF EXISTS GrupoAlias;
CREATE TABLE IF NOT EXISTS GrupoAlias 
	(
	  idGrupoAlias BIGINT(20) NOT NULL AUTO_INCREMENT,
	  nmGrupoAlias VARCHAR(255) NOT NULL,
	  PRIMARY KEY  (idGrupoAlias),
	  UNIQUE KEY nmGrupoAlias (nmGrupoAlias)
	) 
ENGINE=InnoDB  
DEFAULT CHARSET=latin1 
AUTO_INCREMENT=1;



DROP TABLE IF EXISTS Alias_GrupoAlias;
CREATE TABLE IF NOT EXISTS Alias_GrupoAlias 
	(
	  idAlias BIGINT(20) NOT NULL,
	  idGrupoAlias BIGINT(20) NOT NULL,
	  PRIMARY KEY  (idAlias,idGrupoAlias)
	) 
ENGINE=InnoDB 
DEFAULT CHARSET=latin1
AUTO_INCREMENT=1;



DROP TABLE IF EXISTS BaseDeRegra;
CREATE TABLE IF NOT EXISTS BaseDeRegra 
	(
	  idBaseDeRegra BIGINT(20) NOT NULL AUTO_INCREMENT,
	  nmBaseDeRegra VARCHAR(255) NOT NULL,
	  PRIMARY KEY  (idBaseDeRegra),
	  UNIQUE KEY mBaseDeRegra (nmBaseDeRegra)
	) 
ENGINE=InnoDB  
DEFAULT CHARSET=latin1 
AUTO_INCREMENT=1;


DROP TABLE IF EXISTS ConjFuzzy;
CREATE TABLE IF NOT EXISTS ConjFuzzy 
	(
	  idConjFuzzy BIGINT(20) NOT NULL AUTO_INCREMENT,
	  nome VARCHAR(255) default NULL,
	  PRIMARY KEY  (idConjFuzzy)
	) 
ENGINE=InnoDB  
DEFAULT CHARSET=latin1 
AUTO_INCREMENT=1;




DROP TABLE IF EXISTS ConjFuzzy_Funcao;
CREATE TABLE IF NOT EXISTS ConjFuzzy_Funcao 
	(
	  idConjFuzzy BIGINT(20) NOT NULL,
	  idFuncao BIGINT(20) NOT NULL,
	  PRIMARY KEY  (idConjFuzzy,idFuncao)
	) 
ENGINE=InnoDB 
DEFAULT CHARSET=latin1
AUTO_INCREMENT=1;




DROP TABLE IF EXISTS Dominio;
CREATE TABLE IF NOT EXISTS Dominio 
	(
	  idDominio BIGINT(20) NOT NULL AUTO_INCREMENT,
	  incluiLimiteInferior bit(1) NOT NULL,
	  incluiLimiteSuperior bit(1) NOT NULL,
	  limiteInferior DOUBLE NOT NULL,
	  limiteSuperior DOUBLE NOT NULL,
	  PRIMARY KEY  (idDominio)
	) 
ENGINE=InnoDB  
DEFAULT CHARSET=latin1 
AUTO_INCREMENT=1;




DROP TABLE IF EXISTS Funcao;
CREATE TABLE IF NOT EXISTS Funcao (
  idFuncao BIGINT(20) NOT NULL AUTO_INCREMENT,
  txFuncao VARCHAR(255) default NULL,
  idDominio BIGINT(20) default NULL,
  PRIMARY KEY  (idFuncao)
) 
ENGINE=InnoDB  
DEFAULT CHARSET=latin1 
AUTO_INCREMENT=1;









DROP TABLE IF EXISTS MaquinaInferencia;
CREATE TABLE IF NOT EXISTS MaquinaInferencia 
	(
	  idMaquinaInferencia BIGINT(20) NOT NULL AUTO_INCREMENT,
	  nmMaquinaInferencia VARCHAR(255) NOT NULL,
	  idBaseDeRegra BIGINT(20) NOT NULL,
	  idGrupoAlias BIGINT(20) NOT NULL,
	  PRIMARY KEY  (idMaquinaInferencia),
	  UNIQUE KEY nmMaquinaInferencia (nmMaquinaInferencia)
	) 
ENGINE=InnoDB  
DEFAULT CHARSET=latin1 
AUTO_INCREMENT=1;




DROP TABLE IF EXISTS Regra_BaseDeRegra;
CREATE TABLE IF NOT EXISTS Regra_BaseDeRegra 
	(
	  idRegra BIGINT(20) NOT NULL,
	  idBaseDeRegra BIGINT(20) NOT NULL,
	  PRIMARY KEY  (idRegra,idBaseDeRegra)
	) 
ENGINE=InnoDB 
DEFAULT CHARSET=latin1
AUTO_INCREMENT=1;



DROP TABLE IF EXISTS Regra;
CREATE TABLE IF NOT EXISTS Regra 
	(
	  idRegra BIGINT(20) NOT NULL AUTO_INCREMENT,
	  txRegra VARCHAR(255) NOT NULL,
	  PRIMARY KEY  (idRegra)
	) 
ENGINE=InnoDB  
DEFAULT CHARSET=latin1 
AUTO_INCREMENT=1;



DROP TABLE IF EXISTS RelAlias_GrupoAlias;
CREATE TABLE IF NOT EXISTS RelAlias_GrupoAlias 
	(
	  idGrupoAlias BIGINT(20) NOT NULL,
	  idAlias BIGINT(20) NOT NULL,
	  PRIMARY KEY  (idGrupoAlias,idAlias)
	) 
ENGINE=InnoDB 
DEFAULT CHARSET=latin1
AUTO_INCREMENT=1;




DROP TABLE IF EXISTS RelConjFuzzyUniversoFuzzy;
CREATE TABLE IF NOT EXISTS RelConjFuzzyUniversoFuzzy (
  idConjFuzzy BIGINT(20) NOT NULL,
  idUniversoDeDiscursoFuzzy BIGINT(20) NOT NULL,
  PRIMARY KEY  (idConjFuzzy,idUniversoDeDiscursoFuzzy)
) 
ENGINE=InnoDB 
DEFAULT CHARSET=latin1
AUTO_INCREMENT=1;




DROP TABLE IF EXISTS RelFuncaoConjFuzzy;
CREATE TABLE IF NOT EXISTS RelFuncaoConjFuzzy 
	(
	  idFuncao BIGINT(20) NOT NULL,
	  idConjFuzzy BIGINT(20) NOT NULL,
	  PRIMARY KEY  (idFuncao,idConjFuzzy)
	) 
ENGINE=InnoDB 
DEFAULT CHARSET=latin1
AUTO_INCREMENT=1;





DROP TABLE IF EXISTS RelRegra_BaseDeRegra;
CREATE TABLE IF NOT EXISTS RelRegra_BaseDeRegra 
	(
	  idBaseDeRegra BIGINT(20) NOT NULL,
	  idRegra BIGINT(20) NOT NULL,
	  PRIMARY KEY  (idBaseDeRegra,idRegra)
	) 
ENGINE=InnoDB 
DEFAULT CHARSET=latin1
AUTO_INCREMENT=1;





DROP TABLE IF EXISTS RelSistemaFuzzyMaquinaInferencia;
CREATE TABLE IF NOT EXISTS RelSistemaFuzzyMaquinaInferencia 
	(
	  idMaquinaInferencia BIGINT(20) NOT NULL,
	  idSistemaFuzzy BIGINT(20) NOT NULL,
	  prioridade BIGINT(20) default NULL,
	  PRIMARY KEY  (idMaquinaInferencia,idSistemaFuzzy)
	) 
ENGINE=InnoDB 
DEFAULT CHARSET=latin1
AUTO_INCREMENT=1;




DROP TABLE IF EXISTS RelSistemaFuzzyUniversoFuzzy;
CREATE TABLE IF NOT EXISTS RelSistemaFuzzyUniversoFuzzy 
	(
	  idSistemaFuzzy BIGINT(20) NOT NULL,
	  idUniversoDeDiscursoFuzzy BIGINT(20) NOT NULL,
	  prioridade BIGINT(20) default NULL,
	  PRIMARY KEY  (idSistemaFuzzy,idUniversoDeDiscursoFuzzy)
	) 
ENGINE=InnoDB 
DEFAULT CHARSET=latin1
AUTO_INCREMENT=1;




DROP TABLE IF EXISTS SistemaFuzzy;
CREATE TABLE IF NOT EXISTS SistemaFuzzy 
	(
	  idSistemaFuzzy BIGINT(20) NOT NULL AUTO_INCREMENT,
	  nmSistemaFuzzy VARCHAR(255) NOT NULL,
	  PRIMARY KEY  (idSistemaFuzzy),
	  UNIQUE KEY nmSistemaFuzzy (nmSistemaFuzzy)
	) 
ENGINE=InnoDB  
DEFAULT CHARSET=latin1 
AUTO_INCREMENT=1;





DROP TABLE IF EXISTS UniversoDeDiscursoFuzzy;
CREATE TABLE IF NOT EXISTS UniversoDeDiscursoFuzzy 
	(
	  idUniversoDeDiscursoFuzzy BIGINT(20) NOT NULL AUTO_INCREMENT,
	  nome VARCHAR(255) default NULL,
	  idDominio BIGINT(20) default NULL,
	  PRIMARY KEY  (idUniversoDeDiscursoFuzzy),
	  UNIQUE KEY nome (nome)
	) 
ENGINE=InnoDB  
DEFAULT CHARSET=latin1 
AUTO_INCREMENT=1;





DROP TABLE IF EXISTS UniversoDeDiscursoFuzzy_ConjFuzzy;
CREATE TABLE IF NOT EXISTS UniversoDeDiscursoFuzzy_ConjFuzzy 
	(
	  idUniversoDeDiscursoFuzzy BIGINT(20) NOT NULL,
	  idConjFuzzy BIGINT(20) NOT NULL,
	  PRIMARY KEY  (idUniversoDeDiscursoFuzzy,idConjFuzzy)
	) 
ENGINE=InnoDB 
DEFAULT CHARSET=latin1
AUTO_INCREMENT=1;

