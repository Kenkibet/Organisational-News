SET MODE PostgreSQL;

CREATE TABLE IF NOT EXISTS users (
 id int PRIMARY KEY auto_increment,
 name VARCHAR,
 role VARCHAR,
 dept_id INTEGER,
);

CREATE TABLE IF NOT EXISTS articles (
 id int PRIMARY KEY auto_increment,
 title VARCHAR,
 content VARCHAR,
 isScoped int
);


CREATE TABLE IF NOT EXISTS departments (
 id int PRIMARY KEY auto_increment,
 name VARCHAR,
 description VARCHAR,
 no_of_employees INTEGER
);

CREATE TABLE IF NOT EXISTS departments_articles (
 id int PRIMARY KEY auto_increment,
 department_id INTEGER,
 article_id INTEGER
);


