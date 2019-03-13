CREATE TABLE IF NOT EXISTS user
(
  id               bigint auto_increment PRIMARY KEY,
  b2c_account_id   bigint NOT NULL UNIQUE,
  b2b_account_id   bigint NOT NULL
);