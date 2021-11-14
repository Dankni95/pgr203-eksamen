CREATE TABLE user_survey_answer
(
    id             SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    question_id    INT NOT NULL,
    option_id      INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user_info (id) ON DELETE CASCADE,
    FOREIGN KEY (option_id) REFERENCES option (id) ON DELETE CASCADE,
    FOREIGN KEY (question_id) REFERENCES question (id) ON DELETE CASCADE
);