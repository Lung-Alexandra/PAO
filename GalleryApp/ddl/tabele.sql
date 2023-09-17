drop table if exists fotografie;
drop table if exists imagine;
drop table if exists videoclip;
drop table if exists element_eticheta;
drop table if exists album_element;
drop table if exists element;
drop table if exists album;
drop table if exists eticheta;


-- Tabela Element
CREATE TABLE Element
(
    id           INT AUTO_INCREMENT PRIMARY KEY,
    name         VARCHAR(255) NOT NULL,
    description  VARCHAR(255),
    size         INT          NOT NULL,
    creationDate DATE         NOT NULL
);

-- Tabela Imagine
CREATE TABLE Imagine
(
    id             INT AUTO_INCREMENT PRIMARY KEY,
    element_id     INT,
    resolution     VARCHAR(255),
    location       VARCHAR(255),
    FOREIGN KEY (element_id) REFERENCES Element (id)
);

-- Tabela Fotografie
CREATE TABLE Fotografie
(
    id             INT AUTO_INCREMENT PRIMARY KEY,
    imagine_id     INT,
    cameraType     VARCHAR(255),
    cameraSettings VARCHAR(255),
    FOREIGN KEY (imagine_id) REFERENCES Imagine (id)
);

-- Tabela Videoclip
CREATE TABLE Videoclip
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    element_id INT,
    duration   INT,
    FOREIGN KEY (element_id) REFERENCES Element (id)
);

-- Tabela Album
CREATE TABLE Album
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Tabela pentru asocierea elementelor cu albumele
CREATE TABLE Album_Element
(
    album_id   INT,
    element_id INT,
    FOREIGN KEY (album_id) REFERENCES Album (id),
    FOREIGN KEY (element_id) REFERENCES Element (id)
);

-- Tabela Eticheta
CREATE TABLE Eticheta
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Tabela pentru asocierea elementelor cu etichetele
CREATE TABLE Element_Eticheta
(
    element_id  INT,
    eticheta_id INT,
    FOREIGN KEY (element_id) REFERENCES Element (id),
    FOREIGN KEY (eticheta_id) REFERENCES Eticheta (id)
);
