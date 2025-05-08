CREATE TABLE tb_subscription(
    id serial PRIMARY KEY,
    service_name VARCHAR(150) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    due_date DATE NOT NULL,
    frequency VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL,
    user_id INTEGER,
    CONSTRAINT fk_subscription_user FOREIGN KEY (user_id) REFERENCES tb_user(id) ON DELETE CASCADE
);