CREATE TABLE STARTING_GRID
(
  SG_ID INTEGER(10),
  NAME  VARCHAR(255),

  PRIMARY KEY (SG_ID)
);

CREATE TABLE STREET
(
  S_ID         INTEGER(10),
  LENGTH       INTEGER(10),
  STARTINGGRID INTEGER(10),

  PRIMARY KEY (S_ID),
  FOREIGN KEY (STARTINGGRID) REFERENCES STARTING_GRID (SG_ID)
);


CREATE TABLE LANE
(
  L_ID   INTEGER(10),
  STREET INTEGER(10),

  PRIMARY KEY (L_ID),
  FOREIGN KEY (STREET) REFERENCES STREET (S_ID)
);


CREATE TABLE CONSRTUCTIONSITE
(
  CS_ID                 INTEGER(10),
  LANE                  INTEGER(10),
  CONTRUCTIONSITE_START INTEGER(10),
  CONSTRUCTIONSITE_END  INTEGER(10),

  PRIMARY KEY (CS_ID),
  FOREIGN KEY (LANE) REFERENCES LANE (L_ID)

);

CREATE TABLE STREET_SIGN (

  SS_ID INTEGER(10),

  PRIMARY KEY (SS_ID)
);


CREATE TABLE CELL
(
  CELL_ID               INTEGER(10),
  STREET_SIGN           INTEGER(10),
  LANE                  INTEGER(10),
  CONTRUCTIONSITE_START INTEGER(10),
  CONTRUCTIONSITE_END   INTEGER(10),

  PRIMARY KEY (CELL_ID),
  FOREIGN KEY (STREET_SIGN) REFERENCES STREET_SIGN (SS_ID),
  FOREIGN KEY (LANE) REFERENCES LANE (L_ID),
  FOREIGN KEY (CONTRUCTIONSITE_START) REFERENCES CONSRTUCTIONSITE (CS_ID),
  FOREIGN KEY (CONTRUCTIONSITE_END) REFERENCES CONSRTUCTIONSITE (CS_ID)
);


CREATE TABLE VEHICLE
(
  V_ID          INTEGER(10),
  STARTING_GRID INTEGER(10),
  LENGTH        INTEGER(10),
  SPEED         INTEGER(10),
  MAXSPEED      INTEGER(10),
  MINSPEED      INTEGER(10),
  MODELL        VARCHAR(255),

  PRIMARY KEY (V_ID),
  FOREIGN KEY (STARTING_GRID) REFERENCES STARTING_GRID (SG_ID)

);

