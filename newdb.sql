SHOW DATABASES;
USE newdb;
SHOW TABLES;

#-Table for Users
CREATE TABLE users (
  user_id Integer NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(45) NOT NULL,
  email VARCHAR(45) NOT NULL UNIQUE KEY,
  password VARCHAR(64) NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP,
  UNIQUE KEY user_id_name (id, nome)
);

ALTER TABLE users 
ADD CONSTRAINT user_id_name UNIQUE (user_id, name);

ALTER TABLE pois DROP column longitude; 

#-Table for Roles
CREATE TABLE roles (
    role_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(100) NOT NULL UNIQUE
);

#Table for Users-Roles
CREATE TABLE users_roles (
    user_id INTEGER,
	role_id INTEGER,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id)
);

#-Table for Privileges
CREATE TABLE privileges (
    privilege_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

#-Table for Roles-Privileges
CREATE TABLE role_privileges (
    role_id INTEGER,
    privilege_id INTEGER,
    PRIMARY KEY (role_id, privilege_id),
    FOREIGN KEY (role_id) REFERENCES roles(id),
    FOREIGN KEY (privilege_id) REFERENCES privileges(id)
);

#-Table for POI
CREATE TABLE pois (
    poi_id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(45) NOT NULL,
	description VARCHAR(100) NOT NULL,
    author VARCHAR(100) NOT NULL,
	lat DOUBLE NOT NULL,
    lon DOUBLE NOT NULL,
    type VARCHAR(40) NOT NULL,
	open_time TIME,
    close_time TIME,
    status VARCHAR(40) NOT NULL
);

#-Table for POI-Users
CREATE TABLE pois_users (
    poi_id INTEGER NOT NULL,
    user_idfk INTEGER NOT NULL,
    user_name VARCHAR(45) NOT NULL,
    PRIMARY KEY (poi_id),
    FOREIGN KEY (poi_id) REFERENCES pois(poi_id),
    FOREIGN KEY (user_idfk, user_name) REFERENCES users(user_id, name)
);


#POI for testing DB
INSERT INTO pois (name, description, author, lat, lon, type, open_time, close_time, status) 
VALUES ('nome di prova', 'descrizione di prova', 'Simone Stacchiotti', 1.0, 1.0, 'Turismo', '09:00:00', '18:00:00', 'APPROVED');