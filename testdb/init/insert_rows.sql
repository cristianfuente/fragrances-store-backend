-- Datos para clients
INSERT INTO clients (name, api_key, margin, status, code) VALUES
('Cliente 1', 'APIKEY123', 0.15, 'ACTIVE', 'CL001'),
('Cliente 2', 'APIKEY456', 0.10, 'INACTIVE', 'CL002');

-- Datos para payment_methods
INSERT INTO payment_methods (name, url, session_duration, code) VALUES
('PayU', 'https://payu.com', 30, 'PAYU'),
('Stripe', 'https://stripe.com', 45, 'STRIPE');

-- Datos para payment_parameters
INSERT INTO payment_parameters (id_payment_method, key, value, code) VALUES
(1, 'api_key', 'payu_api_key', 'KEY1'),
(1, 'merchant_id', '123456', 'MERCHANT'),
(2, 'api_key', 'stripe_api_key', 'KEY2');

-- Datos para catalogs
INSERT INTO catalogs (name, description) VALUES
('Género', 'Clasificación por género'),
('Edición', 'Clasificación por edición');

-- Datos para catalogs_parameters
INSERT INTO catalogs_parameters (id_catalog, name, description) VALUES
(1, 'Hombre', 'Perfume para hombres'),
(1, 'Mujer', 'Perfume para mujeres'),
(2, 'Especial', 'Edición especial'),
(2, 'Regular', 'Edición estándar');

-- Datos para fragrances
INSERT INTO fragrances (id_fragrance_id_catalog, name, description) VALUES
(1, 'Ocean Breeze', 'Frescura marina'),
(2, 'Vanilla Dream', 'Dulce y suave');

-- Datos para size
INSERT INTO size (size, unit, label) VALUES
(50, 'ml', 'Pequeño'),
(100, 'ml', 'Mediano'),
(200, 'ml', 'Grande');

-- Datos para fragrances_sizes
INSERT INTO fragrances_sizes (id_size, id_fragrance, stock, price, image_id) VALUES
(1, 1, 100, 19.99, 'https://media.falabella.com/falabellaCO/68209055_001/w=1500,h=1500,fit=pad'),
(2, 1, 50, 34.99, 'https://media.falabella.com/falabellaCO/68209055_001/w=1500,h=1500,fit=pad'),
(1, 2, 80, 24.99, 'https://media.falabella.com/falabellaCO/68209055_001/w=1500,h=1500,fit=pad');

-- Datos para catalogs_parameters_fragrances
INSERT INTO catalogs_parameters_fragrances (id_fragrance, id_catalog, id_catalog_parameter) VALUES
(1, 1, 1),  -- Ocean Breeze, Género, Hombre
(1, 2, 4),  -- Ocean Breeze, Edición, Regular
(2, 1, 2),  -- Vanilla Dream, Género, Mujer
(2, 2, 3);  -- Vanilla Dream, Edición, Especial


