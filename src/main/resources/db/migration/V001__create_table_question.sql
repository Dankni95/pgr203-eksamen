CREATE TABLE question
(

    id              SERIAL PRIMARY KEY,
    question_title VARCHAR(100) NOT NULL,
    question_text   varchar(200) not null
);
