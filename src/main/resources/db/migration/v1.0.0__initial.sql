-- Tables

CREATE TABLE STREET
(
  s_id            INTEGER(10) AUTO_INCREMENT,
  nr              INTEGER(10),
  s_length        INTEGER(10),
  startinggrid_id INTEGER(10),

  PRIMARY KEY (s_id)
);


CREATE TABLE STARTING_GRID
(
  sg_id INTEGER(10) AUTO_INCREMENT,
  nr    INTEGER(10),
  name  VARCHAR(255),

  PRIMARY KEY (sg_id)
);


CREATE TABLE LANE
(
  l_id      INTEGER(10) AUTO_INCREMENT,
  nr        INTEGER(10),
  street_id INTEGER(10),
  l_index   INTEGER(10),

  PRIMARY KEY (l_id)
);

CREATE TABLE CONSTRUCTION_SITE
(
  cs_id       INTEGER(10) AUTO_INCREMENT,
  nr          INTEGER(10),
  length   INTEGER(10),
  tailCell_id INTEGER(10),

  PRIMARY KEY (cs_id)
);

CREATE TABLE CELL
(
  c_id    INTEGER(10) AUTO_INCREMENT,
  nr      INTEGER(10),
  c_index INTEGER(10),
  lane_id INTEGER(10),

  -- Vehicle and constructionSite referenced from other side
  PRIMARY KEY (c_id)
);

CREATE TABLE STREET_SIGN (

  ss_id       INTEGER(10) AUTO_INCREMENT,
  nr          INTEGER(10),
  speedLimit  INTEGER(10),
  tailCell_id INTEGER(10),
  length      INTEGER(10),

  PRIMARY KEY (ss_id)
);

CREATE TABLE VEHICLE
(
  v_id            INTEGER(10) AUTO_INCREMENT,
  type            VARCHAR(255),
  nr              INTEGER(10),
  tailCell_id     INTEGER(10),
  currentspeed    INTEGER(10),
  maxspeed        INTEGER(10),
  startinggrid_id INTEGER(10),

  PRIMARY KEY (v_id)
);

-- Foreign keys

ALTER TABLE STREET
  ADD FOREIGN KEY (startinggrid_id) REFERENCES STARTING_GRID (sg_id)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

ALTER TABLE LANE
  ADD FOREIGN KEY (street_id) REFERENCES STREET (s_id)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

ALTER TABLE CONSTRUCTION_SITE
  ADD FOREIGN KEY (tailCell_id) REFERENCES CELL (c_id)
  ON UPDATE CASCADE
  ON DELETE CASCADE;

ALTER TABLE CELL
  ADD FOREIGN KEY (lane_id) REFERENCES LANE (l_id)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

ALTER TABLE STREET_SIGN
  ADD FOREIGN KEY (tailCell_id) REFERENCES CELL (c_id)
  ON DELETE CASCADE
  ON UPDATE CASCADE; -- Delete when cell is deleted

ALTER TABLE VEHICLE
  ADD FOREIGN KEY (startinggrid_id) references STARTING_GRID (sg_id)
  ON DELETE CASCADE
  ON UPDATE CASCADE,
  ADD FOREIGN KEY (tailCell_id) references CELL (c_id)
  ON UPDATE CASCADE
  ON DELETE CASCADE;
