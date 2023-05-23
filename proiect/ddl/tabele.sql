drop table  if exists  element;
drop table  if exists  album;
drop table  if exists  elementalbum;
drop table  if exists  elementeticheta;
drop table  if exists  eticheta;
drop table  if exists  imagine;
drop table  if exists  fotografie;
drop table  if exists  videoclip;

-- Tabela de bază 'Element'
CREATE TABLE Element
(
    id           INT PRIMARY KEY AUTO_INCREMENT,
    name         VARCHAR(255) NOT NULL,
    description  VARCHAR(255),
    size         INT,
    creationDate DATE
);

-- Tabela 'Imagine' care moștenește 'Element'
CREATE TABLE Imagine
(
    id         INT PRIMARY KEY,
    resolution VARCHAR(50),
    location   VARCHAR(100),
    FOREIGN KEY (id) REFERENCES Element (id)
);

-- Tabela 'Fotografie' care moștenește 'Imagine'
CREATE TABLE Fotografie
(
    id             INT PRIMARY KEY,
    cameraType     VARCHAR(50),
    cameraSettings VARCHAR(100),
    FOREIGN KEY (id) REFERENCES Imagine (id)
);

-- Tabela 'Videoclip' care moștenește 'Element'
CREATE TABLE Videoclip
(
    id       INT PRIMARY KEY,
    duration INT,
    FOREIGN KEY (id) REFERENCES Element (id)
);

-- Tabela 'Album'
CREATE TABLE Album
(
    id   INT PRIMARY KEY AUTO_INCREMENT,
    nume VARCHAR(255) NOT NULL
);

-- Tabela 'Eticheta'
CREATE TABLE Eticheta
(
    id   INT PRIMARY KEY AUTO_INCREMENT,
    nume VARCHAR(255) NOT NULL
);

-- Tabela de legătură între 'Element' și 'Eticheta' (Many-to-Many relationship)
CREATE TABLE ElementEticheta
(
    elementId  INT,
    etichetaId INT,
    FOREIGN KEY (elementId) REFERENCES Element (id),
    FOREIGN KEY (etichetaId) REFERENCES Eticheta (id)
);

-- Tabela de legătură între 'Element' și 'Album' (Many-to-Many relationship)
CREATE TABLE ElementAlbum
(
    elementId INT,
    albumId   INT,
    FOREIGN KEY (elementId) REFERENCES Element (id),
    FOREIGN KEY (albumId) REFERENCES Album (id)
);

select * from Element;