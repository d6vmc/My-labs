INSERT INTO territory (name, type, parent_id, parent_type) VALUES
('First_ZONE', 'ZONE', NULL, NULL),
('Second_ZONE', 'ZONE', NULL, NULL);

INSERT INTO territory (name, type, parent_id, parent_type) VALUES
('Earth', 'PLANET', 1, 'ZONE'),
('Mars', 'PLANET', 2, 'ZONE');

INSERT INTO territory (name, type, parent_id, parent_type) VALUES
('Moon', 'MOON', 3, 'PLANET'),
('Phobos', 'MOON', 4, 'PLANET');

INSERT INTO occupation (name) VALUES
('Scientist'),
('Butcher'),
('Pilot');

INSERT INTO occupation_rank (occupation_id, rank_no, title) VALUES
(1, 1, 'Junior'),
(1, 2, 'Senior'),
(2, 1, 'Helper'),
(2, 2, 'Main Butcher'),
(3, 1, 'Cadet'),
(3, 2, 'Commander');

INSERT INTO ruler (pseudonym, type, territory_id) VALUES
('Ilon Mask', 'Planetary Ruler', 3),
('Magister Storm', 'Planetary Ruler', 4);


INSERT INTO magister (pseudonym, birth_date, zone_id) VALUES
('QWEN', DATE '1180-05-14', 1),
('OMEGA', DATE '2090-11-02', 2);

INSERT INTO person (full_name, institution, territory_id, occupation_id, rank_no) VALUES
('Ivan', 'Mars Academy', 4, 1, 1),
('Anna', 'Mars Engineering Hub', 4, 2, 2),
('Maxim', 'Phobos Station', 6, 3, 1),
('Elena', 'Moon Lab', 5, 1, 2),
('Eldar', 'Mars Command', 4, 2, 1),
('Alex', 'Earth Base', 3, 3, 2);
