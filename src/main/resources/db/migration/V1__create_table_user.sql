CREATE TABLE tb_user(
    id serial PRIMARY KEY,
    username varchar(100) NOT NULL,
    password varchar(255) NOT NULL,
    email varchar(150) NOT NULL UNIQUE,
    created_at TIMESTAMP
);