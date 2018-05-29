CREATE DATABASE baza;

USE baza;

CREATE TABLE pomiary (
id INT AUTO_INCREMENT,
data DATE,
temperatura FLOAT,
cisnienie1 INT(3),
cisnienie2 INT(3),
tetno INT(3)
PRIMARY KEY (id)); //to chyba nie jest potrzebne 

