CREATE TABLE user_survey_answer
(
    id             SERIAL PRIMARY KEY,
    user_survey_id INT NOT NULL,
    question_id    INT NOT NULL,
    option_id      INT NOT NULL,
    FOREIGN KEY (user_survey_id) REFERENCES user_survey (id) ON DELETE CASCADE,
    FOREIGN KEY (option_id) REFERENCES option (id) ON DELETE CASCADE,
    FOREIGN KEY (question_id) REFERENCES question (id) ON DELETE CASCADE
);