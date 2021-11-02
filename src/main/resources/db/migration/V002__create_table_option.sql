CREATE TABLE option
(
    id SERIAL PRIMARY KEY,
    question_id       integer REFERENCES question (id),
    option_title varchar(100) not null
);
