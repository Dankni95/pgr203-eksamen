CREATE TABLE response
(
    id SERIAL PRIMARY KEY,
    user_id       integer REFERENCES user_info (id),
    answer        integer references option(id)
);
