CREATE TABLE IF NOT EXISTS orders (
    id BIGSERIAL PRIMARY KEY,
    uuid VARCHAR(36) NOT NULL UNIQUE,
    client_document VARCHAR(20) NOT NULL,
    product_code VARCHAR(50) NOT NULL,
    quantity INTEGER NOT NULL CHECK (quantity >= 1 AND quantity < 100),
    additional_info VARCHAR(511),
    unit_price_without_tax NUMERIC(10,2) NOT NULL,
    subtotal_without_tax NUMERIC(10,2) NOT NULL,
    tax_amount NUMERIC(10,2) NOT NULL,
    total_with_tax NUMERIC(10,2) NOT NULL,
    delivered BOOLEAN NOT NULL DEFAULT FALSE,
    order_date TIMESTAMP NOT NULL,
    delivered_date TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_order_client FOREIGN KEY (client_document)
        REFERENCES clients(document) ON DELETE CASCADE,

    CONSTRAINT fk_order_product FOREIGN KEY (product_code)
        REFERENCES products(code) ON DELETE CASCADE
);