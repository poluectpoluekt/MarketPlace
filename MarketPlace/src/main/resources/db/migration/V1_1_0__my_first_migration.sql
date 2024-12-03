CREATE SEQUENCE customer_main_sequence
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807;

CREATE SEQUENCE role_main_sequence
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807;

CREATE SEQUENCE item_main_sequence
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807;

CREATE SEQUENCE invoice_main_sequence
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807;

CREATE TABLE IF NOT EXISTS Customer
(
    customer_id                BIGINT PRIMARY KEY,
    email                      VARCHAR(50) NOT NULL,
    password                   VARCHAR(60) NOT NULL,
    first_name                 VARCHAR(50),
    second_name                VARCHAR(50),
    create_date                DATE        NOT NULL,
    orders_list                BIGINT,
    basket_added_items         BIGINT,
    is_account_non_expired     boolean     NOT NULL,
    is_account_non_locked      boolean     NOT NULL,
    is_credentials_non_expired boolean     NOT NULL,
    is_enabled                 boolean     NOT NULL
);

CREATE TABLE IF NOT EXISTS Invoice
(
    order_id     BIGINT PRIMARY KEY,
    owner_id     BIGINT      NOT NULL,
    total_amount DECIMAL     NOT NULL,
    create_date  DATE        NOT NULL,
    order_status VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS Item
(
    item_id             BIGINT PRIMARY KEY,
    title               VARCHAR(50) NOT NULL,
    price               DECIMAL     NOT NULL,
    amount_on_warehouse int         NOT NULL
);

CREATE TABLE IF NOT EXISTS Role
(
    role_id BIGINT PRIMARY KEY,
    name    VARCHAR(50) NOT NULL
);

ALTER TABLE Customer
    ADD FOREIGN KEY (orders_list) REFERENCES Invoice (order_id);
ALTER TABLE Customer
    ADD FOREIGN KEY (basket_added_items) REFERENCES Item (item_id);

ALTER TABLE Invoice
    ADD FOREIGN KEY (owner_id) REFERENCES Customer (customer_id);

ALTER TABLE Customer
    ALTER COLUMN customer_id SET DEFAULT customer_main_sequence.nextval;
ALTER TABLE Role
    ALTER COLUMN role_id SET DEFAULT role_main_sequence.nextval;
ALTER TABLE Item
    ALTER COLUMN item_id SET DEFAULT Item_main_sequence.nextval;
ALTER TABLE Invoice
    ALTER COLUMN order_id SET DEFAULT invoice_main_sequence.nextval;