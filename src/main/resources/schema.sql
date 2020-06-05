CREATE TABLE IF NOT EXISTS users (
id     bigint  UNSIGNED NOT NULL AUTO_INCREMENT,
name   varchar(20)  NOT NULL,
gender varchar(6),
email  varchar(128) NOT NULL UNIQUE,
mobile varchar(16),
PRIMARY KEY (id)
);
