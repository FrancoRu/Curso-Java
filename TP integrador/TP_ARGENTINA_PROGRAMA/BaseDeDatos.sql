CREATE DATABASE MyFirstDataBase;
SHOW DATABASES;
USE MyFirstDataBase;
CREATE TABLE ENUMS(
    id int not null auto_increment,
    enums varchar(10) not null,
    PRIMARY KEY(id)
);
CREATE TABLE FASE(
	id int not null auto_increment,
    nameFase varchar(50) not null,
    PRIMARY KEY(id)
);
CREATE TABLE ROUNDS(
	id int not null auto_increment,
    nameRounds varchar(50) not null,
    idFase int not null,
    PRIMARY KEY(id),
    FOREIGN KEY(idFase) REFERENCES FASE(id)
);
CREATE TABLE TEAM(
    id int not null auto_increment,
    nameTeam varchar(50) not null,
    descriptionTeam varchar(100) not null,
    PRIMARY KEY(id)
);
CREATE TABLE PEOPLE(
    id int not null auto_increment,
    UserDNI varchar(10) not null,
    Username varchar(50) not null,
    PRIMARY KEY(id)
);
CREATE TABLE GAME(
    id int not null,
    idTeam1 int not null,
    idTeam2 int not null,
    scoreGame1 int not null,
    scoreGame2 int not null,
    idRound int not null,
    PRIMARY KEY(id),
    FOREIGN KEY(idTeam1) REFERENCES TEAM(id),
    FOREIGN KEY(idTeam2) REFERENCES TEAM(id),
    FOREIGN KEY(idRound) REFERENCES ROUNDS(id)
);
CREATE TABLE PERSON_BETS_MATCH_TEAM(
    id int not null auto_increment,
    idGame int not null,
    idTeam int not null,
    idEnum int not null,
    idPeople int not null,
    idRound int not null,
    PRIMARY KEY(id),
    FOREIGN KEY(idGame) REFERENCES GAME(id),
    FOREIGN KEY(idTeam) REFERENCES TEAM(id),
    FOREIGN KEY(idEnum) REFERENCES ENUMS(id),
    FOREIGN KEY(idPeople) REFERENCES PEOPLE(id),
    FOREIGN KEY(idRound) REFERENCES ROUNDS(id)
);


-- SHOW TABLES;
-- DESCRIBE Enums;
-- DESCRIBE Forecast;
-- DESCRIBE Team;
-- DESCRIBE Game;
-- DESCRIBE People;
