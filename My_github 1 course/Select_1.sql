SELECT table_name
FROM information_schema.tables
WHERE table_schema = 's505343'
ORDER BY table_name;

-- SELECT * FROM territory ORDER BY territory_id;
-- SELECT * FROM occupation ORDER BY occupation_id;
-- SELECT * FROM occupation_rank ORDER BY occupation_id, rank_no;
-- SELECT * FROM ruler ORDER BY ruler_id;
-- SELECT * FROM magister ORDER BY magister_id;
-- SELECT * FROM person ORDER BY person_id;

-- Находим всю информацию о территории по ее типу
SELECT * FROM territory
WHERE type = 'MOON';

-- Находим весь персонал какого-либо объекта по его названию
SELECT * FROM person
WHERE institution = 'Moon Lab';

--Всего территорий в таблице
SELECT COUNT(*) FROM territory;

-- Вывести людей с первым рангом в профессии
SELECT * FROM person
WHERE rank_no = 1;

-- Посчитать количество людей
SELECT COUNT(*) FROM person;

-- Сгруппировать по типу
SELECT type, COUNT(*)
FROM territory
GROUP BY type;