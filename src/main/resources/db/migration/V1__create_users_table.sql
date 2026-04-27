CREATE TABLE admin(
    id char(36) PRIMARY KEY,
    login varchar(100) NOT NULL unique,
    hash_password varchar(100) NOT NULL,
    created_at timestamp not null default current_timestamp
);