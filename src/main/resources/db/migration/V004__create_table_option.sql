CREATE TABLE option
(
    id           SERIAL PRIMARY KEY,
    option_title varchar(100) not null,
    question_id    INT          NOT NULL,
    FOREIGN KEY (question_id) REFERENCES question (id) ON DELETE CASCADE


);
