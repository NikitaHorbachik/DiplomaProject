CREATE SCHEMA IF NOT EXISTS diploma;

CREATE TABLE IF NOT EXISTS diploma.u_roles
(
    id   bigserial PRIMARY KEY,
    name text NOT NULL
);

CREATE TABLE IF NOT EXISTS diploma.users
(
    id       bigserial PRIMARY KEY,
    username text NOT NULL UNIQUE,
    email    text NOT NULL UNIQUE,
    name     text NOT NULL,
    surname  text,
    role     bigint references diploma.u_roles (id) DEFAULT 1,
    created_at timestamp NOT NULL,
    updated_at timestamp
);
