DROP TRIGGER IF EXISTS check_territory_parent_type ON territory;
DROP FUNCTION IF EXISTS check_territory_parent_type();

DROP TABLE IF EXISTS person CASCADE;
DROP TABLE IF EXISTS magister CASCADE;
DROP TABLE IF EXISTS ruler CASCADE;
DROP TABLE IF EXISTS occupation_rank CASCADE;
DROP TABLE IF EXISTS occupation CASCADE;
DROP TABLE IF EXISTS territory CASCADE;


CREATE TABLE territory (
    territory_id SERIAL PRIMARY KEY,
    name TEXT NOT NULL UNIQUE,
    type TEXT NOT NULL CHECK (type IN ('ZONE', 'PLANET', 'MOON')),
    parent_id INT,
    parent_type TEXT CHECK (parent_type IN ('ZONE', 'PLANET')),
    CONSTRAINT territory_parent
        FOREIGN KEY (parent_id) REFERENCES territory(territory_id),
    CONSTRAINT territory_check
        CHECK (
            (type = 'ZONE' AND parent_id IS NULL AND parent_type IS NULL)
            OR
            (type = 'PLANET' AND parent_id IS NOT NULL AND parent_type = 'ZONE')
            OR
            (type = 'MOON' AND parent_id IS NOT NULL AND parent_type = 'PLANET')
        ),
    CONSTRAINT territory_not_self_parent
        CHECK (territory_id <> parent_id)
);


CREATE TABLE occupation (
    occupation_id SERIAL PRIMARY KEY,
    name TEXT NOT NULL UNIQUE
);


CREATE TABLE occupation_rank (
    occupation_id INT NOT NULL,
    rank_no INT NOT NULL,
    title TEXT NOT NULL,
    PRIMARY KEY (occupation_id, rank_no),
    CONSTRAINT occupation_rank_occupation
        FOREIGN KEY (occupation_id) REFERENCES occupation(occupation_id)
);


CREATE TABLE ruler (
    ruler_id SERIAL PRIMARY KEY,
    pseudonym TEXT NOT NULL,
    type TEXT NOT NULL,
    territory_id INT NOT NULL UNIQUE,
    CONSTRAINT ruler_territory
        FOREIGN KEY (territory_id) REFERENCES territory(territory_id)
);


CREATE TABLE magister (
    magister_id SERIAL PRIMARY KEY,
    pseudonym TEXT NOT NULL,
    birth_date DATE NOT NULL,
    zone_id INT NOT NULL UNIQUE,
    CONSTRAINT magister_zone
        FOREIGN KEY (zone_id) REFERENCES territory(territory_id)
);


CREATE TABLE person (
    person_id SERIAL PRIMARY KEY,
    full_name TEXT NOT NULL,
    institution TEXT,
    territory_id INT NOT NULL,
    occupation_id INT NOT NULL,
    rank_no INT NOT NULL,
    CONSTRAINT person_territory
        FOREIGN KEY (territory_id) REFERENCES territory(territory_id),
    CONSTRAINT person_occupation_rank
        FOREIGN KEY (occupation_id, rank_no)
        REFERENCES occupation_rank(occupation_id, rank_no)
);