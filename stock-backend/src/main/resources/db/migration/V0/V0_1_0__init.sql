/*
* Engine: PostgresSQL
* Version: 1.0.0
* Description: ...
*/

-- Drop the existing uuid_generate_v4() function if it exists
DROP FUNCTION IF EXISTS uuid_generate_v4();

-- Define the uuid_generate_v4() function with the desired return type
CREATE OR REPLACE FUNCTION uuid_generate_v4()
RETURNS VARCHAR(36) AS $$
BEGIN
RETURN md5(random()::text || clock_timestamp()::text)::uuid::varchar;
END;
$$ LANGUAGE plpgsql;

-- Category table
CREATE TABLE IF NOT EXISTS category (
    id VARCHAR(36) PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(36) DEFAULT 'admin',
    updated_by VARCHAR(36) DEFAULT 'admin',
    CONSTRAINT unique_category_name UNIQUE (name)
);

-- Product table
CREATE TABLE IF NOT EXISTS product (
    id VARCHAR(36) PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    price DECIMAL(10, 2) NOT NULL,
    quantity INTEGER NOT NULL,
    category_id VARCHAR(36) NOT NULL,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(36) DEFAULT 'admin',
    updated_by VARCHAR(36) DEFAULT 'admin',
    CONSTRAINT positive_price CHECK (price > 0),
    CONSTRAINT positive_quantity CHECK (quantity >= 0),
    FOREIGN KEY (category_id) REFERENCES category (id)
);

-- User table
CREATE TABLE IF NOT EXISTS users (
    id VARCHAR(36) PRIMARY KEY DEFAULT uuid_generate_v4(),
    email VARCHAR(50)  NOT NULL,
    full_name VARCHAR(50)  NOT NULL,
    password VARCHAR(120) NOT NULL,
    password_reset_token VARCHAR(255),
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(36) DEFAULT 'admin',
    updated_by VARCHAR(36) DEFAULT 'admin'
);

CREATE TABLE IF NOT EXISTS token_confirmation
(
    id           SERIAL PRIMARY KEY,
    token        VARCHAR(255) NOT NULL,
    created_at   TIMESTAMP    NOT NULL,
    expires_at   TIMESTAMP    NOT NULL,
    confirmed_at TIMESTAMP,
    user_id      VARCHAR(36) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);