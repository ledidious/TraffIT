CREATE TABLE STARTING_GRID
(
  sg_id   INTEGER(10)  Auto_Increment,    
  nr      INTEGER(10),
  name    VARCHAR(255),

  PRIMARY KEY (sg_id)
);

CREATE TABLE STREET
(
  s_id          INTEGER(10)  Auto_Increment,
  nr            INTEGER(10),
  s_length      INTEGER(10),
  numberLanes   INTEGER(10),

  PRIMARY KEY (s_id),
  FOREIGN KEY (s_id) REFERENCES STARTING_GRID (sg_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);


CREATE TABLE LANE
(
  l_id   INTEGER(10) Auto_Increment,
  nr     INTEGER(10),

  PRIMARY KEY (l_id),
  FOREIGN KEY (l_id) REFERENCES STREET (s_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);


CREATE TABLE CONSRTUCTIONSITE
(
  cs_id       INTEGER(10) Auto_Increment,
  nr          INTEGER(10),
  cs_length   INTEGER(10),

  PRIMARY KEY (cs_id),
  FOREIGN KEY (cs_id) REFERENCES LANE (l_id)
    ON UPDATE CASCADE
    ON DELETE CASCADE

);


CREATE TABLE CELL
(
  c_id                      INTEGER(10)    Auto_Increment,
  nr                        INTEGER(10),
  c_index                   INTEGER(10),

  PRIMARY KEY (c_id),
  FOREIGN KEY (c_id) REFERENCES LANE (l_id)
  ON DELETE CASCADE
  ON UPDATE CASCADE

);


CREATE TABLE STREET_SIGN (

  ss_id        INTEGER(10) Auto_Increment,
  nr           INTEGER(10),
  speedLimit   INTEGER(10),

  PRIMARY KEY (ss_id),
  FOREIGN KEY (ss_id) REFERENCES CELL (c_id)
  ON DELETE CASCADE
  ON UPDATE CASCADE
  );


CREATE TABLE VEHICLE
(
  v_id            INTEGER(10) Auto_Increment,
  nr              INTEGER(10),
  currentspeed    INTEGER(10),
  maxspeed        INTEGER(10),
  speedLimit      INTEGER(10),

PRIMARY KEY (v_id),
FOREIGN KEY (v_id) REFERENCES STARTING_GRID (sg_id)

);
