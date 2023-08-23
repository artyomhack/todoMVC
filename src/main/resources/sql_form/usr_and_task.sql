CREATE TABLE task (
    id SERIAL NOT NULL PRIMARY KEY,
    title VARCHAR(255),
    description TEXT,
    date DATE
);

CREATE TABLE usr (
    id SERIAL NOT NULL PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    email TEXT,
    password_hash TEXT
);

CREATE TABLE usr_task (
    usr_id BIGINT REFERENCES usr(id) ON UPDATE CASCADE ON DELETE CASCADE,
    task_id BIGINT REFERENCES task(id) ON UPDATE CASCADE,
    CONSTRAINT usr_task_fk PRIMARY KEY (usr_id, task_id)
);