CREATE TABLE user_survey
(
    id           SERIAL PRIMARY KEY,
    user_id      INT          NOT NULL,
    survey_title VARCHAR(100) NOT NULL,

    FOREIGN KEY (user_id) REFERENCES user_info (id) ON DELETE CASCADE
);