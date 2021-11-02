CREATE TABLE question
(

    id              SERIAL PRIMARY KEY,
    questions_title VARCHAR(100) NOT NULL,
    question_text   varchar(200) not null,
    option_id       integer REFERENCES options (id)

);
