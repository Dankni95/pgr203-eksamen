CREATE TABLE user_info
(
    id SERIAL PRIMARY KEY,
    first_name      varchar(50) not null,
    last_name       varchar(50) not null,
    email           varchar(50) not null
);

INSERT INTO user_info (first_name, last_name, email)
VALUES ('Annon', 'Annon', 'annon@annon.com');

