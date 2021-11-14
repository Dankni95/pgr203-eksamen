CREATE TABLE question
(

    id             SERIAL PRIMARY KEY,
    question_title varchar(200) not null,
    question_text  varchar(200) not null,
    survey_id      int          not null,
    user_id        int          not null,
    FOREIGN KEY (survey_id) REFERENCES user_survey (id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES user_info (id) ON DELETE CASCADE


);
