CREATE TABLE IF NOT EXISTS Invoice (
        order_id BIGINT PRIMARY KEY,
        id_MarketplaceService BIGINT NOT NULL,
        total_amount DECIMAL NOT NULL,
        status_invoice VARCHAR(20) NOT NULL,
        email_owner VARCHAR(40) NOT NULL,
        create_date DATE NOT NULL
);


CREATE SEQUENCE invoice_main_sequence
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807;

ALTER TABLE Invoice ALTER COLUMN order_id SET DEFAULT invoice_main_sequence.nextval;