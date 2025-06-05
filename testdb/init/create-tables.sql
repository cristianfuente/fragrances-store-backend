CREATE TYPE state_types as ENUM (
    'WAITING_PAYMENTS',
    'PAYMENT_IN_PROCESS',
    'EXPIRED',
    'PAID',
    'ERROR',
    'SENT',
    'COMPLETE'
);

-- Tabla: clients
CREATE TABLE clients (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    api_key VARCHAR(255),
    margin NUMERIC,
    url TEXT,
    status VARCHAR(50),
    code VARCHAR(50),
    created_at TIMESTAMP DEFAULT NOW()
);

-- Tabla: payment_methods
CREATE TABLE payment_methods (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    url TEXT,
    session_duration INTEGER,
    code VARCHAR(50)
);

-- Tabla: payment_parameters
CREATE TABLE payment_parameters (
    id SERIAL PRIMARY KEY,
    id_payment_method INTEGER REFERENCES payment_methods(id),
    key VARCHAR(255),
    value VARCHAR(255),
    code VARCHAR(50)
);

-- Tabla: catalogs
CREATE TABLE catalogs (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    description TEXT
);

-- Tabla: catalogs_parameters
CREATE TABLE catalogs_parameters (
    id SERIAL PRIMARY KEY,
    id_catalog INTEGER REFERENCES catalogs(id),
    name VARCHAR(255),
    description TEXT
);

-- Tabla: fragrances
CREATE TABLE fragrances (
    id SERIAL PRIMARY KEY,
    id_fragrance_id_catalog INTEGER, -- campo compuesto como FK más adelante
    name VARCHAR(255),
    description TEXT
);

-- Tabla: size
CREATE TABLE size (
    id SERIAL PRIMARY KEY,
    size NUMERIC,
    unit VARCHAR(50),
    label VARCHAR(100)
);

-- Tabla: fragrances_sizes
CREATE TABLE fragrances_sizes (
    id_size INTEGER,
    id_fragrance INTEGER,
    stock INTEGER,
    price NUMERIC,
    image_id VARCHAR(255),
    PRIMARY KEY (id_size, id_fragrance),
    FOREIGN KEY (id_size) REFERENCES size(id),
    FOREIGN KEY (id_fragrance) REFERENCES fragrances(id)
);

-- Tabla: catalogs_parameters_fragrances
CREATE TABLE catalogs_parameters_fragrances (
    id_fragrance INTEGER,
    id_catalog INTEGER,
    id_catalog_parameter INTEGER,
    PRIMARY KEY (id_fragrance, id_catalog, id_catalog_parameter),
    FOREIGN KEY (id_fragrance) REFERENCES fragrances(id),
    FOREIGN KEY (id_catalog) REFERENCES catalogs(id),
    FOREIGN KEY (id_catalog_parameter) REFERENCES catalogs_parameters(id)
);

-- Tabla: transactions
CREATE TABLE transactions (
    id SERIAL PRIMARY KEY,
    id_client INTEGER REFERENCES clients(id),
    id_payment_method INTEGER REFERENCES payment_methods(id),
    state state_types NOT NULL,
    total_payment NUMERIC,
    create_at TIMESTAMP DEFAULT NOW(),
    email VARCHAR(255),
    address TEXT,
    code VARCHAR(50),
    firstName VARCHAR(50),
    lastName VARCHAR(50),
    documentNumber TEXT,
    phone TEXT,
    additionalAddressInfo TEXT,
    country VARCHAR(50),
    department TEXT,
    postalCode VARCHAR(50)
);

-- Tabla: transactions_fragrances
CREATE TABLE transactions_fragrances (
    id_transaction INTEGER,
    id_size INTEGER,
    id_fragrance INTEGER,
    quantity INTEGER,
    PRIMARY KEY (id_transaction, id_size, id_fragrance),
    FOREIGN KEY (id_transaction) REFERENCES transactions(id),
    FOREIGN KEY (id_size, id_fragrance) REFERENCES fragrances_sizes(id_size, id_fragrance)
);

-- Tabla: transactions_history
CREATE TABLE transactions_history (
    id INTEGER,
    id_client INTEGER,
    id_payment_method INTEGER,
    state VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ip_address VARCHAR(100),
    margin_at_purchase NUMERIC,
    redirect_uri_payment TEXT,
    total_payment NUMERIC,
    email VARCHAR(255),
    address TEXT,
    status_client VARCHAR(50),
    additional TEXT
);

-- Tabla: transactions_fragrances_history
CREATE TABLE transactions_fragrances_history (
    id_size INTEGER,
    id_transaction INTEGER,
    id_fragrance INTEGER,
    fragance_name VARCHAR(255),
    size_unit VARCHAR(50),
    size NUMERIC,
    quantity INTEGER,
    original_price_at_purchase NUMERIC,
    payment_price_at_purchase NUMERIC,
    stock_before INTEGER,
    stock_after INTEGER,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    additional TEXT
);

-- Tabla: otp
CREATE TABLE otp (
    id SERIAL PRIMARY KEY,
    otp_token VARCHAR(64) NOT NULL UNIQUE,
    id_transaction BIGINT NOT NULL UNIQUE,
    issued_at TIMESTAMPTZ NOT NULL,
    expires_at TIMESTAMPTZ NOT NULL,
    FOREIGN KEY (id_transaction) REFERENCES transactions(id)
);

