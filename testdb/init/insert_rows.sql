-- Datos para clients
INSERT INTO clients (name, api_key, margin, status, code) VALUES
('Cliente 1', 'APIKEY123', 0.15, 'ACTIVE', 'CL001'),
('Cliente 2', 'APIKEY456', 0.10, 'INACTIVE', 'CL002');

-- Datos para payment_methods
INSERT INTO payment_methods (name, url, session_duration, code) VALUES
('PayU', 'https://payu.com', 5, 'PAYU'),
('Nequi', 'https://stripe.com', 5, 'STRIPE');

-- Datos para payment_parameters
INSERT INTO payment_parameters (id_payment_method, key, value, code) VALUES
(1, 'api_key', 'payu_api_key', null),
(1, 'redirect_uri', null, 'REDIRECT_URI'),
(1, 'payment', null, 'PAYMENT'),
(2, 'payment', null, 'PAYMENT'),
(2, 'redirect_uri', null, 'REDIRECT_URI'),
(2, 'api_key', 'stripe_api_key', null);

-- Datos para catalogs
INSERT INTO catalogs (id, name, description) VALUES
(1, 'Marca', 'Marca del perfume'),
(2, 'Género', 'Orientación de la fragancia por género'),
(3, 'Concentración', 'Nivel de concentración del perfume'),
(4, 'Familia Olfativa', 'Tipo de aroma dominante');

-- Datos para catalogs_parameters
INSERT INTO catalogs_parameters (id_catalog, name, description) VALUES
(1, 'Chanel', 'Chanel'),
(1, 'Dior', 'Dior'),
(1, 'Calvin Klein', 'Calvin Klein'),
(1, 'Carolina Herrera', 'Carolina Herrera'),
(1, 'Versace', 'Versace'),
(2, 'Hombre', 'Hombre'),
(2, 'Mujer', 'Mujer'),
(2, 'Unisex', 'Unisex'),
(3, 'Eau de Toilette', 'Eau de Toilette'),
(3, 'Eau de Parfum', 'Eau de Parfum'),
(3, 'Parfum', 'Parfum'),
(4, 'Floral', 'Floral'),
(4, 'Amaderado', 'Amaderado'),
(4, 'Oriental', 'Oriental'),
(4, 'Cítrico', 'Cítrico'),
(4, 'Aromático', 'Aromático');

-- Fragancias
-- ================================
INSERT INTO fragrances (id_fragrance_id_catalog, name, description) VALUES
(1, 'Bleu de Chanel', 'Fragancia ''Bleu de Chanel'' con estilo distintivo y sofisticado.'),
(1, 'Sauvage', 'Fragancia ''Sauvage'' con estilo distintivo y sofisticado.'),
(1, 'CK One', 'Fragancia ''CK One'' con estilo distintivo y sofisticado.'),
(1, 'Good Girl', 'Fragancia ''Good Girl'' con estilo distintivo y sofisticado.'),
(1, 'Versace Eros', 'Fragancia ''Versace Eros'' con estilo distintivo y sofisticado.'),
(1, 'J''adore', 'Fragancia ''J''adore'' con estilo distintivo y sofisticado.'),
(1, '212 VIP Men', 'Fragancia ''212 VIP Men'' con estilo distintivo y sofisticado.'),
(1, 'Bright Crystal', 'Fragancia ''Bright Crystal'' con estilo distintivo y sofisticado.'),
(1, 'Invictus', 'Fragancia ''Invictus'' con estilo distintivo y sofisticado.'),
(1, 'La Vie Est Belle', 'Fragancia ''La Vie Est Belle'' con estilo distintivo y sofisticado.'),
(1, 'Acqua di Gio', 'Fragancia ''Acqua di Gio'' con estilo distintivo y sofisticado.'),
(1, 'Black Opium', 'Fragancia ''Black Opium'' con estilo distintivo y sofisticado.'),
(1, 'L''Homme', 'Fragancia ''L''Homme'' con estilo distintivo y sofisticado.'),
(1, 'Flowerbomb', 'Fragancia ''Flowerbomb'' con estilo distintivo y sofisticado.'),
(1, 'Light Blue', 'Fragancia ''Light Blue'' con estilo distintivo y sofisticado.'),
(1, 'The One', 'Fragancia ''The One'' con estilo distintivo y sofisticado.'),
(1, 'Olympea', 'Fragancia ''Olympea'' con estilo distintivo y sofisticado.'),
(1, 'Boss Bottled', 'Fragancia ''Boss Bottled'' con estilo distintivo y sofisticado.'),
(1, 'Alien', 'Fragancia ''Alien'' con estilo distintivo y sofisticado.'),
(1, 'Euphoria', 'Fragancia ''Euphoria'' con estilo distintivo y sofisticado.');

-- Datos para size
INSERT INTO size (size, unit, label) VALUES
(50, 'ml', 'Pequeño'),
(100, 'ml', 'Mediano'),
(200, 'ml', 'Grande');

-- Datos para fragrances_sizes
-- ================================
INSERT INTO fragrances_sizes (id_size, id_fragrance, stock, price, image_id) VALUES
(1, 1, 70, 200000, 'https://media.falabella.com/falabellaCO/68209055_001/w=1500,h=1500,fit=pad'),
(2, 1, 119, 300000, 'https://media.falabella.com/falabellaCO/68209055_001/w=1500,h=1500,fit=pad'),
(1, 2, 54, 200000, 'https://media.falabella.com/falabellaCO/68209055_001/w=1500,h=1500,fit=pad'),
(2, 2, 70, 300000, 'https://media.falabella.com/falabellaCO/68209055_001/w=1500,h=1500,fit=pad'),
(1, 3, 115, 200000, 'https://media.falabella.com/falabellaCO/68209055_001/w=1500,h=1500,fit=pad'),
(2, 3, 88, 300000, 'https://media.falabella.com/falabellaCO/68209055_001/w=1500,h=1500,fit=pad'),
(1, 4, 91, 200000, 'https://media.falabella.com/falabellaCO/68209055_001/w=1500,h=1500,fit=pad'),
(2, 4, 108, 300000, 'https://media.falabella.com/falabellaCO/68209055_001/w=1500,h=1500,fit=pad'),
(1, 5, 107, 200000, 'https://media.falabella.com/falabellaCO/68209055_001/w=1500,h=1500,fit=pad'),
(2, 5, 95, 300000, 'https://media.falabella.com/falabellaCO/68209055_001/w=1500,h=1500,fit=pad'),
(1, 6, 99, 200000, 'https://media.falabella.com/falabellaCO/68209055_001/w=1500,h=1500,fit=pad'),
(2, 6, 80, 300000, 'https://media.falabella.com/falabellaCO/68209055_001/w=1500,h=1500,fit=pad'),
(1, 7, 89, 200000, 'https://media.falabella.com/falabellaCO/68209055_001/w=1500,h=1500,fit=pad'),
(2, 7, 70, 300000, 'https://media.falabella.com/falabellaCO/68209055_001/w=1500,h=1500,fit=pad'),
(1, 8, 65, 200000, 'https://media.falabella.com/falabellaCO/68209055_001/w=1500,h=1500,fit=pad'),
(2, 8, 102, 300000, 'https://media.falabella.com/falabellaCO/68209055_001/w=1500,h=1500,fit=pad'),
(1, 9, 72, 200000, 'https://media.falabella.com/falabellaCO/68209055_001/w=1500,h=1500,fit=pad'),
(2, 9, 66, 300000, 'https://media.falabella.com/falabellaCO/68209055_001/w=1500,h=1500,fit=pad'),
(1, 10, 82, 200000, 'https://media.falabella.com/falabellaCO/68209055_001/w=1500,h=1500,fit=pad'),
(2, 10, 61, 300000, 'https://media.falabella.com/falabellaCO/68209055_001/w=1500,h=1500,fit=pad'),
(1, 11, 86, 200000, 'https://media.falabella.com/falabellaCO/68209055_001/w=1500,h=1500,fit=pad'),
(2, 11, 108, 300000, 'https://media.falabella.com/falabellaCO/68209055_001/w=1500,h=1500,fit=pad'),
(1, 12, 104, 200000, 'https://media.falabella.com/falabellaCO/68209055_001/w=1500,h=1500,fit=pad'),
(2, 12, 50, 300000, 'https://media.falabella.com/falabellaCO/68209055_001/w=1500,h=1500,fit=pad'),
(1, 13, 54, 200000, 'https://media.falabella.com/falabellaCO/68209055_001/w=1500,h=1500,fit=pad'),
(2, 13, 60, 300000, 'https://media.falabella.com/falabellaCO/68209055_001/w=1500,h=1500,fit=pad'),
(1, 14, 78, 200000, 'https://media.falabella.com/falabellaCO/68209055_001/w=1500,h=1500,fit=pad'),
(2, 14, 107, 300000, 'https://media.falabella.com/falabellaCO/68209055_001/w=1500,h=1500,fit=pad'),
(1, 15, 56, 200000, 'https://media.falabella.com/falabellaCO/68209055_001/w=1500,h=1500,fit=pad'),
(2, 15, 73, 300000, 'https://media.falabella.com/falabellaCO/68209055_001/w=1500,h=1500,fit=pad'),
(1, 16, 104, 200000, 'https://media.falabella.com/falabellaCO/68209055_001/w=1500,h=1500,fit=pad'),
(2, 16, 50, 300000, 'https://media.falabella.com/falabellaCO/68209055_001/w=1500,h=1500,fit=pad'),
(1, 17, 50, 200000, 'https://media.falabella.com/falabellaCO/68209055_001/w=1500,h=1500,fit=pad'),
(2, 17, 94, 300000, 'https://media.falabella.com/falabellaCO/68209055_001/w=1500,h=1500,fit=pad'),
(1, 18, 70, 200000, 'https://media.falabella.com/falabellaCO/68209055_001/w=1500,h=1500,fit=pad'),
(2, 18, 58, 300000, 'https://media.falabella.com/falabellaCO/68209055_001/w=1500,h=1500,fit=pad'),
(1, 19, 69, 200000, 'https://media.falabella.com/falabellaCO/68209055_001/w=1500,h=1500,fit=pad'),
(2, 19, 61, 300000, 'https://media.falabella.com/falabellaCO/68209055_001/w=1500,h=1500,fit=pad'),
(1, 20, 62, 200000, 'https://media.falabella.com/falabellaCO/68209055_001/w=1500,h=1500,fit=pad'),
(2, 20, 60, 300000, 'https://media.falabella.com/falabellaCO/68209055_001/w=1500,h=1500,fit=pad');

-- Datos para catalogs_parameters_fragrances
-- ================================
INSERT INTO catalogs_parameters_fragrances (id_fragrance, id_catalog, id_catalog_parameter) VALUES
(1, 1, (SELECT id FROM catalogs_parameters WHERE name = 'Chanel' AND id_catalog = 1)),
(1, 2, (SELECT id FROM catalogs_parameters WHERE name = 'Hombre' AND id_catalog = 2)),
(1, 3, (SELECT id FROM catalogs_parameters WHERE name = 'Parfum' AND id_catalog = 3)),
(1, 4, (SELECT id FROM catalogs_parameters WHERE name = 'Amaderado' AND id_catalog = 4)),
(2, 1, (SELECT id FROM catalogs_parameters WHERE name = 'Dior' AND id_catalog = 1)),
(2, 2, (SELECT id FROM catalogs_parameters WHERE name = 'Hombre' AND id_catalog = 2)),
(2, 3, (SELECT id FROM catalogs_parameters WHERE name = 'Eau de Parfum' AND id_catalog = 3)),
(2, 4, (SELECT id FROM catalogs_parameters WHERE name = 'Aromático' AND id_catalog = 4)),
(3, 1, (SELECT id FROM catalogs_parameters WHERE name = 'Calvin Klein' AND id_catalog = 1)),
(3, 2, (SELECT id FROM catalogs_parameters WHERE name = 'Unisex' AND id_catalog = 2)),
(3, 3, (SELECT id FROM catalogs_parameters WHERE name = 'Eau de Toilette' AND id_catalog = 3)),
(3, 4, (SELECT id FROM catalogs_parameters WHERE name = 'Cítrico' AND id_catalog = 4)),
(4, 1, (SELECT id FROM catalogs_parameters WHERE name = 'Carolina Herrera' AND id_catalog = 1)),
(4, 2, (SELECT id FROM catalogs_parameters WHERE name = 'Mujer' AND id_catalog = 2)),
(4, 3, (SELECT id FROM catalogs_parameters WHERE name = 'Eau de Parfum' AND id_catalog = 3)),
(4, 4, (SELECT id FROM catalogs_parameters WHERE name = 'Oriental' AND id_catalog = 4)),
(5, 1, (SELECT id FROM catalogs_parameters WHERE name = 'Versace' AND id_catalog = 1)),
(5, 2, (SELECT id FROM catalogs_parameters WHERE name = 'Hombre' AND id_catalog = 2)),
(5, 3, (SELECT id FROM catalogs_parameters WHERE name = 'Eau de Parfum' AND id_catalog = 3)),
(5, 4, (SELECT id FROM catalogs_parameters WHERE name = 'Aromático' AND id_catalog = 4)),
(6, 1, (SELECT id FROM catalogs_parameters WHERE name = 'Dior' AND id_catalog = 1)),
(6, 2, (SELECT id FROM catalogs_parameters WHERE name = 'Mujer' AND id_catalog = 2)),
(6, 3, (SELECT id FROM catalogs_parameters WHERE name = 'Eau de Parfum' AND id_catalog = 3)),
(6, 4, (SELECT id FROM catalogs_parameters WHERE name = 'Floral' AND id_catalog = 4)),
(7, 1, (SELECT id FROM catalogs_parameters WHERE name = 'Carolina Herrera' AND id_catalog = 1)),
(7, 2, (SELECT id FROM catalogs_parameters WHERE name = 'Hombre' AND id_catalog = 2)),
(7, 3, (SELECT id FROM catalogs_parameters WHERE name = 'Eau de Toilette' AND id_catalog = 3)),
(7, 4, (SELECT id FROM catalogs_parameters WHERE name = 'Oriental' AND id_catalog = 4)),
(8, 1, (SELECT id FROM catalogs_parameters WHERE name = 'Versace' AND id_catalog = 1)),
(8, 2, (SELECT id FROM catalogs_parameters WHERE name = 'Mujer' AND id_catalog = 2)),
(8, 3, (SELECT id FROM catalogs_parameters WHERE name = 'Eau de Toilette' AND id_catalog = 3)),
(8, 4, (SELECT id FROM catalogs_parameters WHERE name = 'Floral' AND id_catalog = 4)),
(9, 1, (SELECT id FROM catalogs_parameters WHERE name = 'Calvin Klein' AND id_catalog = 1)),
(9, 2, (SELECT id FROM catalogs_parameters WHERE name = 'Hombre' AND id_catalog = 2)),
(9, 3, (SELECT id FROM catalogs_parameters WHERE name = 'Eau de Toilette' AND id_catalog = 3)),
(9, 4, (SELECT id FROM catalogs_parameters WHERE name = 'Aromático' AND id_catalog = 4)),
(10, 1, (SELECT id FROM catalogs_parameters WHERE name = 'Dior' AND id_catalog = 1)),
(10, 2, (SELECT id FROM catalogs_parameters WHERE name = 'Mujer' AND id_catalog = 2)),
(10, 3, (SELECT id FROM catalogs_parameters WHERE name = 'Eau de Parfum' AND id_catalog = 3)),
(10, 4, (SELECT id FROM catalogs_parameters WHERE name = 'Oriental' AND id_catalog = 4)),
(11, 1, (SELECT id FROM catalogs_parameters WHERE name = 'Calvin Klein' AND id_catalog = 1)),
(11, 2, (SELECT id FROM catalogs_parameters WHERE name = 'Hombre' AND id_catalog = 2)),
(11, 3, (SELECT id FROM catalogs_parameters WHERE name = 'Eau de Toilette' AND id_catalog = 3)),
(11, 4, (SELECT id FROM catalogs_parameters WHERE name = 'Cítrico' AND id_catalog = 4)),
(12, 1, (SELECT id FROM catalogs_parameters WHERE name = 'Chanel' AND id_catalog = 1)),
(12, 2, (SELECT id FROM catalogs_parameters WHERE name = 'Mujer' AND id_catalog = 2)),
(12, 3, (SELECT id FROM catalogs_parameters WHERE name = 'Parfum' AND id_catalog = 3)),
(12, 4, (SELECT id FROM catalogs_parameters WHERE name = 'Oriental' AND id_catalog = 4)),
(13, 1, (SELECT id FROM catalogs_parameters WHERE name = 'Carolina Herrera' AND id_catalog = 1)),
(13, 2, (SELECT id FROM catalogs_parameters WHERE name = 'Hombre' AND id_catalog = 2)),
(13, 3, (SELECT id FROM catalogs_parameters WHERE name = 'Eau de Toilette' AND id_catalog = 3)),
(13, 4, (SELECT id FROM catalogs_parameters WHERE name = 'Amaderado' AND id_catalog = 4)),
(14, 1, (SELECT id FROM catalogs_parameters WHERE name = 'Dior' AND id_catalog = 1)),
(14, 2, (SELECT id FROM catalogs_parameters WHERE name = 'Mujer' AND id_catalog = 2)),
(14, 3, (SELECT id FROM catalogs_parameters WHERE name = 'Eau de Parfum' AND id_catalog = 3)),
(14, 4, (SELECT id FROM catalogs_parameters WHERE name = 'Floral' AND id_catalog = 4)),
(15, 1, (SELECT id FROM catalogs_parameters WHERE name = 'Versace' AND id_catalog = 1)),
(15, 2, (SELECT id FROM catalogs_parameters WHERE name = 'Mujer' AND id_catalog = 2)),
(15, 3, (SELECT id FROM catalogs_parameters WHERE name = 'Eau de Toilette' AND id_catalog = 3)),
(15, 4, (SELECT id FROM catalogs_parameters WHERE name = 'Cítrico' AND id_catalog = 4)),
(16, 1, (SELECT id FROM catalogs_parameters WHERE name = 'Calvin Klein' AND id_catalog = 1)),
(16, 2, (SELECT id FROM catalogs_parameters WHERE name = 'Mujer' AND id_catalog = 2)),
(16, 3, (SELECT id FROM catalogs_parameters WHERE name = 'Parfum' AND id_catalog = 3)),
(16, 4, (SELECT id FROM catalogs_parameters WHERE name = 'Oriental' AND id_catalog = 4)),
(17, 1, (SELECT id FROM catalogs_parameters WHERE name = 'Chanel' AND id_catalog = 1)),
(17, 2, (SELECT id FROM catalogs_parameters WHERE name = 'Mujer' AND id_catalog = 2)),
(17, 3, (SELECT id FROM catalogs_parameters WHERE name = 'Eau de Parfum' AND id_catalog = 3)),
(17, 4, (SELECT id FROM catalogs_parameters WHERE name = 'Oriental' AND id_catalog = 4)),
(18, 1, (SELECT id FROM catalogs_parameters WHERE name = 'Versace' AND id_catalog = 1)),
(18, 2, (SELECT id FROM catalogs_parameters WHERE name = 'Hombre' AND id_catalog = 2)),
(18, 3, (SELECT id FROM catalogs_parameters WHERE name = 'Eau de Toilette' AND id_catalog = 3)),
(18, 4, (SELECT id FROM catalogs_parameters WHERE name = 'Amaderado' AND id_catalog = 4)),
(19, 1, (SELECT id FROM catalogs_parameters WHERE name = 'Dior' AND id_catalog = 1)),
(19, 2, (SELECT id FROM catalogs_parameters WHERE name = 'Mujer' AND id_catalog = 2)),
(19, 3, (SELECT id FROM catalogs_parameters WHERE name = 'Parfum' AND id_catalog = 3)),
(19, 4, (SELECT id FROM catalogs_parameters WHERE name = 'Oriental' AND id_catalog = 4)),
(20, 1, (SELECT id FROM catalogs_parameters WHERE name = 'Calvin Klein' AND id_catalog = 1)),
(20, 2, (SELECT id FROM catalogs_parameters WHERE name = 'Unisex' AND id_catalog = 2)),
(20, 3, (SELECT id FROM catalogs_parameters WHERE name = 'Eau de Parfum' AND id_catalog = 3)),
(20, 4, (SELECT id FROM catalogs_parameters WHERE name = 'Floral' AND id_catalog = 4));


