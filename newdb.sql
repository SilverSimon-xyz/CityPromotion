SHOW DATABASES;
USE newdb;
SHOW TABLES;

#DROP DATABASE newdb;
#CREATE DATABASE newdb;

#-Table for Users
CREATE TABLE users (
  user_id INTEGER NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY(user_id),
  UNIQUE KEY(name)
);

#-Table for Roles
CREATE TABLE roles (
    role_id INTEGER AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description VARCHAR(255) NOT NULL UNIQUE
);

#Table for Users-Roles
CREATE TABLE users_roles (
    user_id INTEGER NOT NULL,
	role_id INTEGER NOT NULL,
    email VARCHAR(255) NOT NULL,
    role_name VARCHAR(255) NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(role_id) ON DELETE CASCADE
);

#-Table for Privileges
CREATE TABLE privileges (
    privilege_id INTEGER AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

#-Table for Roles-Privileges
CREATE TABLE role_privileges (
    role_id INTEGER NOT NULL,
    role_name VARCHAR(255) NOT NULL,
    privilege_id INTEGER NOT NULL,
    privilege_name VARCHAR(255) NOT NULL,
    PRIMARY KEY (role_id, privilege_id),
    FOREIGN KEY (role_id) REFERENCES roles(role_id) ON DELETE CASCADE,
    FOREIGN KEY (privilege_id) REFERENCES privileges(privilege_id) ON DELETE CASCADE
);

#-Table for POI
CREATE TABLE pois (
    poi_id INTEGER NOT NULL AUTO_INCREMENT,
	name VARCHAR(255) NOT NULL,
	description TEXT NOT NULL,
    author VARCHAR(255) NOT NULL,
	lat DOUBLE NOT NULL,
    lon DOUBLE NOT NULL,
    type ENUM('TOURISM', 'ACCOMMODATION', 'SERVICE', 'NATURE', 'OTHER') NOT NULL,
	open_time TIME,
    close_time TIME,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (poi_id),
    FOREIGN KEY (author) REFERENCES users(name) 
);

#-Table for Contest
CREATE TABLE contest (
  contest_id INTEGER NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  description TEXT NOT NULL,
  author VARCHAR(255) NOT NULL,
  rules VARCHAR(255) NOT NULL,
  goal VARCHAR(255) NOT NULL,
  prize VARCHAR(255) NOT NULL,
  active BOOLEAN NOT NULL,
  deadline DATE NOT NULL,
  number_participants INTEGER NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY(contest_id),
  FOREIGN KEY (author) REFERENCES users(name) 
);

#-Table for MultimediaContent
CREATE TABLE multimediacontent (
  mc_id INTEGER NOT NULL AUTO_INCREMENT,
  title VARCHAR(255) NOT NULL,
  type VARCHAR(255) NOT NULL,
  description TEXT NOT NULL,
  author VARCHAR(255) NOT NULL,
  data LONGBLOB NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  status ENUM('PENDING', 'APPROVED', 'REJECTED') NOT NULL,
  PRIMARY KEY(mc_id),
  FOREIGN KEY (author) REFERENCES users(name) 
);

#-Table for Contest-Participation
CREATE TABLE contest_participation (
  id INTEGER NOT NULL AUTO_INCREMENT,
  contest_id INTEGER NOT NULL,
  participant VARCHAR(255) NOT NULL,
  mc_id INTEGER NOT NULL,
  vote INTEGER NOT NULL,
  description TEXT NOT NULL,
  is_quote BOOLEAN NOT NULL,
  PRIMARY KEY(id),
  FOREIGN KEY(contest_id) REFERENCES contest(contest_id),
  FOREIGN KEY(mc_id) REFERENCES multimediacontent(mc_id),
  FOREIGN KEY (participant) REFERENCES users(name) 
);



#User for testing DB
INSERT INTO users (name, email, password) VALUES ('Simone Stacchiotti', 'silver.simon@gmail.com', '$2a$10$HY1BGND6c2pAptwNYZAV5uFZIYHOhT5bqixrOXHubpjuSLxtHzaG2');
INSERT INTO users (name, email, password) VALUES ('Davide Novelli', 'dado92@gmail.com', '$2a$10$piQNs34MtJm0IkYbihG1F.vi8/V5iaxv.wXLh5SYArq1l5fi/E99.');
INSERT INTO users (name, email, password) VALUES ('Ambra', 'anna.franca@gmail.com', '$2a$10$aqwUyrD6qW11HggAYFbs3e2ac8e3peKiifHWeTDiHq4vQKBXQYRdW');
INSERT INTO users (name, email, password) VALUES ('Lorenzo Muti', 'mratlantis@gmail.com', '$2a$10$hZKmLrCN8jOx2AT7MgGkPe3w2HdzIw0zF3FBKa6hr4HEsB4oNeZyS');
INSERT INTO users (name, email, password) VALUES ('Giacomo Freddi', 'giacomo.freddi@gmail.com', '$2a$10$6EwlKtv0zc8e1zBvdyCJHePUXg7FLKvHxIHYsY3EbHxpEDjZYKhIS');
INSERT INTO users (name, email, password) VALUES ('Federico Badiali', 'radius@gmail.com', '$2a$10$dWTI8ezvRUp1R7ziyuO4TOCyIaBoHAHIOVEBOrpfLR8CriSI9mrmO');

#POI for testing DB
INSERT INTO pois (name, description, author, lat, lon, type, open_time, close_time) 
VALUES ('nome di prova', 'descrizione di prova','Simone Stacchiotti', 1.0, 1.0, 'TOURISM', '09:00:00', '18:00:00');