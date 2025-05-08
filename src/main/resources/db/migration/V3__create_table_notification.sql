CREATE TABLE tb_notification(
    id serial PRIMARY KEY,
    to_user varchar(255) NOT NULL,
    subject varchar(255) NOT NULL,
    message TEXT NOT NULL,
    status VARCHAR(50) NOT NULL,
    send_at TIMESTAMP,
    subscription_id INTEGER,
    CONSTRAINT fk_subscription FOREIGN KEY (subscription_id) REFERENCES tb_subscription(id) ON DELETE CASCADE
);
