CREATE TABLE Rarities
( id_rarity INT PRIMARY KEY,
  name VARCHAR(5) NOT NULL
);

CREATE TABLE Items
( id_item INT PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  type VARCHAR(50),
  price INT NOT NULL,
  rarity INT NOT NULL,
  FOREIGN KEY (rarity) REFERENCES Rarities(id_rarity),
  weight INT,
  image VARCHAR(50)
)

CREATE TABLE Hands
( 
  id_item INT PRIMARY KEY,
  type VARCHAR(10) NOT NULL,
  FOREIGN KEY (id_item) REFERENCES Items( id_item )
)


CREATE TABLE Monsters
( id_monster INT PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  hp INT NOT NULL,
  experience INT,
  gold_max INT,
  image VARCHAR(50)
)

CREATE TABLE Rarity_chances
( 
  id_monster INT NOT NULL,
  id_rarity INT NOT NULL,
  value INT,
  CONSTRAINT pk_rarity_chances PRIMARY KEY (id_monster,id_rarity),
  FOREIGN KEY (id_monster) REFERENCES Monsters(id_monster),
  FOREIGN KEY (id_rarity) REFERENCES Rarities(id_rarity)
)

CREATE TABLE Stats
( id_stat INT PRIMARY KEY,
  name VARCHAR(50) NOT NULL
)

CREATE TABLE Item_stat
( 
  id_item INT NOT NULL,
  id_stat INT NOT NULL,
  value INT,
  CONSTRAINT pk_item_stat PRIMARY KEY (id_item,id_stat),
  FOREIGN KEY (id_item) REFERENCES Items(id_item),
  FOREIGN KEY (id_stat) REFERENCES Stats(id_stat)
)

CREATE TABLE Monster_immunities
( 
  id_monster INT NOT NULL,
  id_stat INT NOT NULL,
  bonus INT,
  CONSTRAINT pk_monster_immunities PRIMARY KEY (id_monster,id_stat),
  FOREIGN KEY (id_monster) REFERENCES Monsters(id_monster),
  FOREIGN KEY (id_stat) REFERENCES Stats(id_stat)
)

CREATE TABLE Monster_sensivities
( 
  id_monster INT NOT NULL,
  id_stat INT NOT NULL,
  multiplier INT,
  CONSTRAINT pk_monster_sensivities PRIMARY KEY (id_monster,id_stat),
  FOREIGN KEY (id_monster) REFERENCES Monsters(id_monster),
  FOREIGN KEY (id_stat) REFERENCES Stats(id_stat)
)

CREATE TABLE Item_immunities
( 
  id_item INT NOT NULL,
  id_stat INT NOT NULL,
  bonus INT,
  CONSTRAINT pk_item_immunities PRIMARY KEY (id_item,id_stat),
  FOREIGN KEY (id_item) REFERENCES Items(id_item),
  FOREIGN KEY (id_stat) REFERENCES Stats(id_stat)
)

CREATE TABLE Loot
( 
  id_monster INT NOT NULL,
  id_item INT NOT NULL,
  quantity INT,
  CONSTRAINT pk_item_stat PRIMARY KEY (id_monster,id_item),
  FOREIGN KEY (id_monster) REFERENCES Monsters(id_monster),
  FOREIGN KEY (id_item) REFERENCES Items(id_item)
)

CREATE TABLE Subtypes
( 
  id_item INT NOT NULL,
  name VARCHAR(20) NOT NULL,
  CONSTRAINT pk_subtypes PRIMARY KEY (id_item,name),
  FOREIGN KEY (id_item) REFERENCES Items(id_item)
)

CREATE TABLE Players
( 
  id_player INT PRIMARY KEY,
  name VARCHAR(20) NOT NULL,
  hp INT NOT NULL,
  mp INT NOT NULL,
  level INT NOT NULL,
  magic_level INT NOT NULL,
  experience INT NOT NULL,
  gold INT NOT NULL,
  capacity INT NOT NULL,
  acc_number INT NOT NULL,
  password VARCHAR(20) NOT NULL,
  attack INT NOT NULL,
  defence INT NOT NULL,
  critical INT NOT NULL,
  critical_chance INT NOT NULL,
  eq_helmet INT,
  eq_armor INT,
  eq_legs INT,
  eq_boots INT,
  eq_weapon INT,
  eq_shield INT,
  eq_ring_left INT,
  eq_ring_right INT,
  eq_amulet INT,
  skill_points INT,
  used_hp_sp INT,
  used_mp_sp INT,
  used_attack_sp INT,
  used_defence_sp INT,
  used_critical_sp INT,
  used_critical_chance_sp INT,
  health_potions INT,
  mana_potions INT,
  magic_experience INT,
  FOREIGN KEY (eq_helmet) REFERENCES Items(id_item),
  FOREIGN KEY (eq_armor) REFERENCES Items(id_item),
  FOREIGN KEY (eq_legs) REFERENCES Items(id_item),
  FOREIGN KEY (eq_boots) REFERENCES Items(id_item),
  FOREIGN KEY (eq_weapon) REFERENCES Items(id_item),
  FOREIGN KEY (eq_shield) REFERENCES Items(id_item),
  FOREIGN KEY (eq_ring_left) REFERENCES Items(id_item),
  FOREIGN KEY (eq_ring_right) REFERENCES Items(id_item),
  FOREIGN KEY (eq_amulet) REFERENCES Items(id_item)
)

CREATE TABLE Depot
( 
  id_player INT NOT NULL,
  id_item INT NOT NULL,
  quantity INT,
  CONSTRAINT pk_depot PRIMARY KEY (id_player,id_item),
  FOREIGN KEY (id_player) REFERENCES Players(id_player),
  FOREIGN KEY (id_item) REFERENCES Items(id_item)
)

CREATE TABLE Attributes
( 
  id_item INT NOT NULL,
  id_stat INT NOT NULL,
  value INT,
  CONSTRAINT pk_attributes PRIMARY KEY (id_item,id_stat),
  FOREIGN KEY (id_item) REFERENCES Items(id_item),
  FOREIGN KEY (id_stat) REFERENCES Stats(id_stat)
)


