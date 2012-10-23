ALTER TABLE minhatabela ENGINE = InnoDB;

CREATE TABLE security.users
  (
    USER_NAME VARCHAR(15) NOT NULL,
    USER_PASS VARCHAR(15) NOT NULL,
    PRIMARY KEY (USER_NAME)
  )
ENGINE=InnoDB
DEFAULT CHARSET=latin1;
  
CREATE TABLE security.user_roles
  (
    USER_NAME     VARCHAR(15) NOT NULL,
    ROLE_NAME     VARCHAR(200) NOT NULL,
    APPLICATION   VARCHAR(200),
    PRIMARY KEY (USER_NAME, ROLE_NAME, APPLICATION)
  )
ENGINE=InnoDB
DEFAULT CHARSET=latin1;



---------------------------------------------------------
--  Security JETTY
---------------------------------------------------------

CREATE TABLE security.jetty_users
(
    id integer primary key,
    user_name varchar(100) not null unique key,
    user_pass varchar(20) not null
)
ENGINE=InnoDB;
 
CREATE TABLE security.jetty_roles
(
    id integer primary key,
    role varchar(100) not null unique key
)
ENGINE=InnoDB
DEFAULT CHARSET=latin1;

CREATE TABLE security.jetty_user_roles
(
    user_id integer not null,
    role_id integer not null,
    index(user_id),
    PRIMARY KEY (user_id, role_id)
)
ENGINE=InnoDB
DEFAULT CHARSET=latin1;
ALTER TABLE security.jetty_user_roles ADD CONSTRAINT FOREIGN KEY fk_user_id(user_id) REFERENCES security.jetty_users(id);
ALTER TABLE security.jetty_user_roles ADD CONSTRAINT FOREIGN KEY fk_role_id(role_id) REFERENCES security.jetty_roles(id);
