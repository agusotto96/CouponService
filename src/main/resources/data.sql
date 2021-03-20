DROP TABLE IF EXISTS items;

CREATE TABLE items (
  id VARCHAR(250) PRIMARY KEY,
  price DECIMAL(10,2) NOT NULL
);

INSERT INTO items (id, price) VALUES
  ('A', 10),
  ('B', 20),
  ('C', 30);