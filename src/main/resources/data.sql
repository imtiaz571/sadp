-- =============================================
-- Bangladesh Airline Data - Seed Script
-- Real airlines, airports, routes & fares
-- =============================================

-- -------------------------------------------
-- 1. Admin & Sample Users
-- -------------------------------------------
INSERT IGNORE INTO users (full_name, email, password, phone, role)
VALUES ('Admin', 'admin@skyway.com', 'admin123', '01700000000', 'ADMIN');

INSERT IGNORE INTO users (full_name, email, password, phone, role)
VALUES ('Imtiaz Ahmed', 'imtiaz@gmail.com', 'user123', '01712345678', 'USER');

INSERT IGNORE INTO users (full_name, email, password, phone, role)
VALUES ('Rafiq Islam', 'rafiq@gmail.com', 'user123', '01812345678', 'USER');

INSERT IGNORE INTO users (full_name, email, password, phone, role)
VALUES ('Fatema Begum', 'fatema@gmail.com', 'user123', '01912345678', 'USER');

INSERT IGNORE INTO users (full_name, email, password, phone, role)
VALUES ('Kamal Hossain', 'kamal@gmail.com', 'user123', '01612345678', 'USER');


-- -------------------------------------------
-- 2. Aircraft (Airlines with Flight Classes)
-- -------------------------------------------

-- BUSINESS Class (mapped to AC in DB)
INSERT IGNORE INTO buses (bus_name, bus_number, bus_type, total_seats, amenities)
VALUES ('Biman Bangladesh Airlines', 'BG-101', 'AC', 30, 'Business Class, Lounge, Meals, WiFi, Priority Boarding');

INSERT IGNORE INTO buses (bus_name, bus_number, bus_type, total_seats, amenities)
VALUES ('US-Bangla Airlines', 'BS-201', 'AC', 30, 'Business Class, Lounge, Meals, WiFi, Extra Legroom');

INSERT IGNORE INTO buses (bus_name, bus_number, bus_type, total_seats, amenities)
VALUES ('Novoair', 'VQ-301', 'AC', 24, 'Business Class, Snacks, Priority Boarding, Lounge');

INSERT IGNORE INTO buses (bus_name, bus_number, bus_type, total_seats, amenities)
VALUES ('Emirates', 'EK-501', 'AC', 42, 'Business Class, Flat Bed, Meals, WiFi, Entertainment');

INSERT IGNORE INTO buses (bus_name, bus_number, bus_type, total_seats, amenities)
VALUES ('Singapore Airlines', 'SQ-601', 'AC', 36, 'Business Class, Flat Bed, Champagne, WiFi, Lounge');

INSERT IGNORE INTO buses (bus_name, bus_number, bus_type, total_seats, amenities)
VALUES ('Qatar Airways', 'QR-701', 'AC', 40, 'Business Class, Qsuite, Meals, WiFi, Entertainment');

INSERT IGNORE INTO buses (bus_name, bus_number, bus_type, total_seats, amenities)
VALUES ('Turkish Airlines', 'TK-801', 'AC', 38, 'Business Class, Lounge, Meals, WiFi, Entertainment');

-- FIRST CLASS (mapped to LUXURY in DB)
INSERT IGNORE INTO buses (bus_name, bus_number, bus_type, total_seats, amenities)
VALUES ('Biman Bangladesh Airlines', 'BG-102', 'LUXURY', 12, 'First Class, Private Suite, Fine Dining, Chauffeur, Lounge');

INSERT IGNORE INTO buses (bus_name, bus_number, bus_type, total_seats, amenities)
VALUES ('Emirates', 'EK-502', 'LUXURY', 14, 'First Class, Private Suite, Shower Spa, Fine Dining, Bar');

INSERT IGNORE INTO buses (bus_name, bus_number, bus_type, total_seats, amenities)
VALUES ('Singapore Airlines', 'SQ-602', 'LUXURY', 12, 'First Class, Suite, Book the Cook, WiFi, Champagne');

INSERT IGNORE INTO buses (bus_name, bus_number, bus_type, total_seats, amenities)
VALUES ('Qatar Airways', 'QR-702', 'LUXURY', 8, 'First Class, Qsuite, Caviar, Fine Dining, WiFi');

INSERT IGNORE INTO buses (bus_name, bus_number, bus_type, total_seats, amenities)
VALUES ('Cathay Pacific', 'CX-401', 'LUXURY', 10, 'First Class, Suite, Fine Dining, Champagne, WiFi');

INSERT IGNORE INTO buses (bus_name, bus_number, bus_type, total_seats, amenities)
VALUES ('Malaysia Airlines', 'MH-901', 'LUXURY', 8, 'First Class, Suite, Satay on Demand, WiFi, Lounge');

-- ECONOMY Class (mapped to REGULAR in DB)
INSERT IGNORE INTO buses (bus_name, bus_number, bus_type, total_seats, amenities)
VALUES ('Biman Bangladesh Airlines', 'BG-103', 'REGULAR', 180, 'Economy Class, Meals, Entertainment, USB Charging');

INSERT IGNORE INTO buses (bus_name, bus_number, bus_type, total_seats, amenities)
VALUES ('US-Bangla Airlines', 'BS-202', 'REGULAR', 150, 'Economy Class, Snacks, USB Charging, Water');

INSERT IGNORE INTO buses (bus_name, bus_number, bus_type, total_seats, amenities)
VALUES ('Novoair', 'VQ-302', 'REGULAR', 120, 'Economy Class, Snacks, Water, Entertainment');

INSERT IGNORE INTO buses (bus_name, bus_number, bus_type, total_seats, amenities)
VALUES ('Air Astra', 'A5-401', 'REGULAR', 78, 'Economy Class, Brand New Fleet, Snacks, Water');

INSERT IGNORE INTO buses (bus_name, bus_number, bus_type, total_seats, amenities)
VALUES ('Emirates', 'EK-503', 'REGULAR', 300, 'Economy Class, Meals, ICE Entertainment, WiFi, USB');

INSERT IGNORE INTO buses (bus_name, bus_number, bus_type, total_seats, amenities)
VALUES ('Singapore Airlines', 'SQ-603', 'REGULAR', 250, 'Economy Class, Meals, KrisWorld, USB, WiFi');

INSERT IGNORE INTO buses (bus_name, bus_number, bus_type, total_seats, amenities)
VALUES ('IndiGo', 'IG-111', 'REGULAR', 186, 'Economy Class, Snacks, USB Charging, Water');


-- -------------------------------------------
-- 3. Routes (Domestic & International Flights)
-- -------------------------------------------

-- Domestic Routes
INSERT IGNORE INTO routes (route_name, origin, destination, distance_km)
VALUES ('Dhaka - Chittagong', 'Dhaka', 'Chittagong', 250);

INSERT IGNORE INTO routes (route_name, origin, destination, distance_km)
VALUES ('Dhaka - Sylhet', 'Dhaka', 'Sylhet', 300);

INSERT IGNORE INTO routes (route_name, origin, destination, distance_km)
VALUES ('Dhaka - Cox''s Bazar', 'Dhaka', 'Cox''s Bazar', 400);

INSERT IGNORE INTO routes (route_name, origin, destination, distance_km)
VALUES ('Dhaka - Rajshahi', 'Dhaka', 'Rajshahi', 260);

INSERT IGNORE INTO routes (route_name, origin, destination, distance_km)
VALUES ('Dhaka - Jessore', 'Dhaka', 'Jessore', 300);

INSERT IGNORE INTO routes (route_name, origin, destination, distance_km)
VALUES ('Dhaka - Saidpur', 'Dhaka', 'Saidpur', 420);

INSERT IGNORE INTO routes (route_name, origin, destination, distance_km)
VALUES ('Dhaka - Barisal', 'Dhaka', 'Barisal', 200);

INSERT IGNORE INTO routes (route_name, origin, destination, distance_km)
VALUES ('Chittagong - Cox''s Bazar', 'Chittagong', 'Cox''s Bazar', 152);

-- International Routes
INSERT IGNORE INTO routes (route_name, origin, destination, distance_km)
VALUES ('Dhaka - Dubai', 'Dhaka', 'Dubai', 4500);

INSERT IGNORE INTO routes (route_name, origin, destination, distance_km)
VALUES ('Dhaka - Singapore', 'Dhaka', 'Singapore', 3200);

INSERT IGNORE INTO routes (route_name, origin, destination, distance_km)
VALUES ('Dhaka - Kolkata', 'Dhaka', 'Kolkata', 350);

INSERT IGNORE INTO routes (route_name, origin, destination, distance_km)
VALUES ('Dhaka - Bangkok', 'Dhaka', 'Bangkok', 2400);

INSERT IGNORE INTO routes (route_name, origin, destination, distance_km)
VALUES ('Dhaka - Kuala Lumpur', 'Dhaka', 'Kuala Lumpur', 3800);

INSERT IGNORE INTO routes (route_name, origin, destination, distance_km)
VALUES ('Dhaka - Istanbul', 'Dhaka', 'Istanbul', 6500);

INSERT IGNORE INTO routes (route_name, origin, destination, distance_km)
VALUES ('Dhaka - Doha', 'Dhaka', 'Doha', 4300);

-- Reverse Domestic Routes
INSERT IGNORE INTO routes (route_name, origin, destination, distance_km)
VALUES ('Chittagong - Dhaka', 'Chittagong', 'Dhaka', 250);

INSERT IGNORE INTO routes (route_name, origin, destination, distance_km)
VALUES ('Sylhet - Dhaka', 'Sylhet', 'Dhaka', 300);

INSERT IGNORE INTO routes (route_name, origin, destination, distance_km)
VALUES ('Cox''s Bazar - Dhaka', 'Cox''s Bazar', 'Dhaka', 400);

INSERT IGNORE INTO routes (route_name, origin, destination, distance_km)
VALUES ('Rajshahi - Dhaka', 'Rajshahi', 'Dhaka', 260);

INSERT IGNORE INTO routes (route_name, origin, destination, distance_km)
VALUES ('Jessore - Dhaka', 'Jessore', 'Dhaka', 300);

INSERT IGNORE INTO routes (route_name, origin, destination, distance_km)
VALUES ('Saidpur - Dhaka', 'Saidpur', 'Dhaka', 420);

INSERT IGNORE INTO routes (route_name, origin, destination, distance_km)
VALUES ('Barisal - Dhaka', 'Barisal', 'Dhaka', 200);

INSERT IGNORE INTO routes (route_name, origin, destination, distance_km)
VALUES ('Cox''s Bazar - Chittagong', 'Cox''s Bazar', 'Chittagong', 152);

-- Reverse International Routes
INSERT IGNORE INTO routes (route_name, origin, destination, distance_km)
VALUES ('Dubai - Dhaka', 'Dubai', 'Dhaka', 4500);

INSERT IGNORE INTO routes (route_name, origin, destination, distance_km)
VALUES ('Singapore - Dhaka', 'Singapore', 'Dhaka', 3200);

INSERT IGNORE INTO routes (route_name, origin, destination, distance_km)
VALUES ('Kolkata - Dhaka', 'Kolkata', 'Dhaka', 350);

INSERT IGNORE INTO routes (route_name, origin, destination, distance_km)
VALUES ('Bangkok - Dhaka', 'Bangkok', 'Dhaka', 2400);

INSERT IGNORE INTO routes (route_name, origin, destination, distance_km)
VALUES ('Kuala Lumpur - Dhaka', 'Kuala Lumpur', 'Dhaka', 3800);

INSERT IGNORE INTO routes (route_name, origin, destination, distance_km)
VALUES ('Istanbul - Dhaka', 'Istanbul', 'Dhaka', 6500);

INSERT IGNORE INTO routes (route_name, origin, destination, distance_km)
VALUES ('Doha - Dhaka', 'Doha', 'Dhaka', 4300);


-- -------------------------------------------
-- 4. Stops (Layover Airports)
-- -------------------------------------------

-- Dhaka - Chittagong (direct, no stops)
INSERT IGNORE INTO stops (route_id, stop_name, stop_order)
VALUES ((SELECT id FROM routes WHERE route_name = 'Dhaka - Chittagong'), 'Hazrat Shahjalal Intl (DAC)', 1);

INSERT IGNORE INTO stops (route_id, stop_name, stop_order)
VALUES ((SELECT id FROM routes WHERE route_name = 'Dhaka - Chittagong'), 'Shah Amanat Intl (CGP)', 2);

-- Dhaka - Cox's Bazar (direct)
INSERT IGNORE INTO stops (route_id, stop_name, stop_order)
VALUES ((SELECT id FROM routes WHERE route_name = 'Dhaka - Cox''s Bazar'), 'Hazrat Shahjalal Intl (DAC)', 1);

INSERT IGNORE INTO stops (route_id, stop_name, stop_order)
VALUES ((SELECT id FROM routes WHERE route_name = 'Dhaka - Cox''s Bazar'), 'Cox''s Bazar Airport (CXB)', 2);

-- Dhaka - Sylhet (direct)
INSERT IGNORE INTO stops (route_id, stop_name, stop_order)
VALUES ((SELECT id FROM routes WHERE route_name = 'Dhaka - Sylhet'), 'Hazrat Shahjalal Intl (DAC)', 1);

INSERT IGNORE INTO stops (route_id, stop_name, stop_order)
VALUES ((SELECT id FROM routes WHERE route_name = 'Dhaka - Sylhet'), 'Osmani Intl Airport (ZYL)', 2);

-- Dhaka - Rajshahi (direct)
INSERT IGNORE INTO stops (route_id, stop_name, stop_order)
VALUES ((SELECT id FROM routes WHERE route_name = 'Dhaka - Rajshahi'), 'Hazrat Shahjalal Intl (DAC)', 1);

INSERT IGNORE INTO stops (route_id, stop_name, stop_order)
VALUES ((SELECT id FROM routes WHERE route_name = 'Dhaka - Rajshahi'), 'Shah Makhdum Airport (RJH)', 2);

-- Dhaka - Dubai (direct)
INSERT IGNORE INTO stops (route_id, stop_name, stop_order)
VALUES ((SELECT id FROM routes WHERE route_name = 'Dhaka - Dubai'), 'Hazrat Shahjalal Intl (DAC)', 1);

INSERT IGNORE INTO stops (route_id, stop_name, stop_order)
VALUES ((SELECT id FROM routes WHERE route_name = 'Dhaka - Dubai'), 'Dubai Intl Airport (DXB)', 2);

-- Dhaka - Singapore (direct)
INSERT IGNORE INTO stops (route_id, stop_name, stop_order)
VALUES ((SELECT id FROM routes WHERE route_name = 'Dhaka - Singapore'), 'Hazrat Shahjalal Intl (DAC)', 1);

INSERT IGNORE INTO stops (route_id, stop_name, stop_order)
VALUES ((SELECT id FROM routes WHERE route_name = 'Dhaka - Singapore'), 'Changi Airport (SIN)', 2);

-- Dhaka - Istanbul (1 stop via Dubai)
INSERT IGNORE INTO stops (route_id, stop_name, stop_order)
VALUES ((SELECT id FROM routes WHERE route_name = 'Dhaka - Istanbul'), 'Hazrat Shahjalal Intl (DAC)', 1);

INSERT IGNORE INTO stops (route_id, stop_name, stop_order)
VALUES ((SELECT id FROM routes WHERE route_name = 'Dhaka - Istanbul'), 'Dubai Intl (DXB) - Transit', 2);

INSERT IGNORE INTO stops (route_id, stop_name, stop_order)
VALUES ((SELECT id FROM routes WHERE route_name = 'Dhaka - Istanbul'), 'Istanbul Airport (IST)', 3);

-- Dhaka - Doha (direct)
INSERT IGNORE INTO stops (route_id, stop_name, stop_order)
VALUES ((SELECT id FROM routes WHERE route_name = 'Dhaka - Doha'), 'Hazrat Shahjalal Intl (DAC)', 1);

INSERT IGNORE INTO stops (route_id, stop_name, stop_order)
VALUES ((SELECT id FROM routes WHERE route_name = 'Dhaka - Doha'), 'Hamad Intl Airport (DOH)', 2);

-- Dhaka - Kolkata (direct)
INSERT IGNORE INTO stops (route_id, stop_name, stop_order)
VALUES ((SELECT id FROM routes WHERE route_name = 'Dhaka - Kolkata'), 'Hazrat Shahjalal Intl (DAC)', 1);

INSERT IGNORE INTO stops (route_id, stop_name, stop_order)
VALUES ((SELECT id FROM routes WHERE route_name = 'Dhaka - Kolkata'), 'Netaji Subhas Chandra Bose Intl (CCU)', 2);

-- Dhaka - Bangkok (direct)
INSERT IGNORE INTO stops (route_id, stop_name, stop_order)
VALUES ((SELECT id FROM routes WHERE route_name = 'Dhaka - Bangkok'), 'Hazrat Shahjalal Intl (DAC)', 1);

INSERT IGNORE INTO stops (route_id, stop_name, stop_order)
VALUES ((SELECT id FROM routes WHERE route_name = 'Dhaka - Bangkok'), 'Suvarnabhumi Airport (BKK)', 2);

-- Dhaka - Kuala Lumpur (direct)
INSERT IGNORE INTO stops (route_id, stop_name, stop_order)
VALUES ((SELECT id FROM routes WHERE route_name = 'Dhaka - Kuala Lumpur'), 'Hazrat Shahjalal Intl (DAC)', 1);

INSERT IGNORE INTO stops (route_id, stop_name, stop_order)
VALUES ((SELECT id FROM routes WHERE route_name = 'Dhaka - Kuala Lumpur'), 'Kuala Lumpur Intl (KUL)', 2);


-- -------------------------------------------
-- 5. Schedules (Forward Flights)
-- -------------------------------------------

-- Domestic: Dhaka - Chittagong
INSERT IGNORE INTO schedules (bus_id, route_id, departure_time, arrival_time, available_seats, fare, status)
VALUES (
  (SELECT id FROM buses WHERE bus_number = 'BG-103'),
  (SELECT id FROM routes WHERE route_name = 'Dhaka - Chittagong'),
  '2026-04-01 07:00:00', '2026-04-01 07:55:00', 180, 3500, 'ACTIVE'
);

INSERT IGNORE INTO schedules (bus_id, route_id, departure_time, arrival_time, available_seats, fare, status)
VALUES (
  (SELECT id FROM buses WHERE bus_number = 'BS-202'),
  (SELECT id FROM routes WHERE route_name = 'Dhaka - Chittagong'),
  '2026-04-01 10:30:00', '2026-04-01 11:25:00', 150, 3200, 'ACTIVE'
);

INSERT IGNORE INTO schedules (bus_id, route_id, departure_time, arrival_time, available_seats, fare, status)
VALUES (
  (SELECT id FROM buses WHERE bus_number = 'BG-101'),
  (SELECT id FROM routes WHERE route_name = 'Dhaka - Chittagong'),
  '2026-04-01 14:00:00', '2026-04-01 14:55:00', 30, 8500, 'ACTIVE'
);

-- Domestic: Dhaka - Sylhet
INSERT IGNORE INTO schedules (bus_id, route_id, departure_time, arrival_time, available_seats, fare, status)
VALUES (
  (SELECT id FROM buses WHERE bus_number = 'BG-103'),
  (SELECT id FROM routes WHERE route_name = 'Dhaka - Sylhet'),
  '2026-04-01 08:00:00', '2026-04-01 09:00:00', 180, 4000, 'ACTIVE'
);

INSERT IGNORE INTO schedules (bus_id, route_id, departure_time, arrival_time, available_seats, fare, status)
VALUES (
  (SELECT id FROM buses WHERE bus_number = 'VQ-302'),
  (SELECT id FROM routes WHERE route_name = 'Dhaka - Sylhet'),
  '2026-04-01 15:00:00', '2026-04-01 16:00:00', 120, 3800, 'ACTIVE'
);

-- Domestic: Dhaka - Cox's Bazar
INSERT IGNORE INTO schedules (bus_id, route_id, departure_time, arrival_time, available_seats, fare, status)
VALUES (
  (SELECT id FROM buses WHERE bus_number = 'BS-202'),
  (SELECT id FROM routes WHERE route_name = 'Dhaka - Cox''s Bazar'),
  '2026-04-01 06:30:00', '2026-04-01 07:35:00', 150, 5500, 'ACTIVE'
);

INSERT IGNORE INTO schedules (bus_id, route_id, departure_time, arrival_time, available_seats, fare, status)
VALUES (
  (SELECT id FROM buses WHERE bus_number = 'A5-401'),
  (SELECT id FROM routes WHERE route_name = 'Dhaka - Cox''s Bazar'),
  '2026-04-01 11:00:00', '2026-04-01 12:05:00', 78, 5200, 'ACTIVE'
);

INSERT IGNORE INTO schedules (bus_id, route_id, departure_time, arrival_time, available_seats, fare, status)
VALUES (
  (SELECT id FROM buses WHERE bus_number = 'BS-201'),
  (SELECT id FROM routes WHERE route_name = 'Dhaka - Cox''s Bazar'),
  '2026-04-01 16:00:00', '2026-04-01 17:05:00', 30, 12000, 'ACTIVE'
);

-- Domestic: Dhaka - Rajshahi
INSERT IGNORE INTO schedules (bus_id, route_id, departure_time, arrival_time, available_seats, fare, status)
VALUES (
  (SELECT id FROM buses WHERE bus_number = 'BG-103'),
  (SELECT id FROM routes WHERE route_name = 'Dhaka - Rajshahi'),
  '2026-04-01 09:00:00', '2026-04-01 09:55:00', 180, 3800, 'ACTIVE'
);

-- Domestic: Dhaka - Jessore
INSERT IGNORE INTO schedules (bus_id, route_id, departure_time, arrival_time, available_seats, fare, status)
VALUES (
  (SELECT id FROM buses WHERE bus_number = 'VQ-302'),
  (SELECT id FROM routes WHERE route_name = 'Dhaka - Jessore'),
  '2026-04-01 13:00:00', '2026-04-01 14:00:00', 120, 4200, 'ACTIVE'
);

-- Domestic: Dhaka - Saidpur
INSERT IGNORE INTO schedules (bus_id, route_id, departure_time, arrival_time, available_seats, fare, status)
VALUES (
  (SELECT id FROM buses WHERE bus_number = 'BS-202'),
  (SELECT id FROM routes WHERE route_name = 'Dhaka - Saidpur'),
  '2026-04-01 07:30:00', '2026-04-01 08:30:00', 150, 4500, 'ACTIVE'
);

-- International: Dhaka - Dubai
INSERT IGNORE INTO schedules (bus_id, route_id, departure_time, arrival_time, available_seats, fare, status)
VALUES (
  (SELECT id FROM buses WHERE bus_number = 'EK-503'),
  (SELECT id FROM routes WHERE route_name = 'Dhaka - Dubai'),
  '2026-04-01 02:00:00', '2026-04-01 05:30:00', 300, 35000, 'ACTIVE'
);

INSERT IGNORE INTO schedules (bus_id, route_id, departure_time, arrival_time, available_seats, fare, status)
VALUES (
  (SELECT id FROM buses WHERE bus_number = 'EK-501'),
  (SELECT id FROM routes WHERE route_name = 'Dhaka - Dubai'),
  '2026-04-01 22:00:00', '2026-04-02 01:30:00', 42, 85000, 'ACTIVE'
);

INSERT IGNORE INTO schedules (bus_id, route_id, departure_time, arrival_time, available_seats, fare, status)
VALUES (
  (SELECT id FROM buses WHERE bus_number = 'EK-502'),
  (SELECT id FROM routes WHERE route_name = 'Dhaka - Dubai'),
  '2026-04-01 22:00:00', '2026-04-02 01:30:00', 14, 180000, 'ACTIVE'
);

INSERT IGNORE INTO schedules (bus_id, route_id, departure_time, arrival_time, available_seats, fare, status)
VALUES (
  (SELECT id FROM buses WHERE bus_number = 'BG-103'),
  (SELECT id FROM routes WHERE route_name = 'Dhaka - Dubai'),
  '2026-04-01 10:00:00', '2026-04-01 14:00:00', 180, 32000, 'ACTIVE'
);

-- International: Dhaka - Singapore
INSERT IGNORE INTO schedules (bus_id, route_id, departure_time, arrival_time, available_seats, fare, status)
VALUES (
  (SELECT id FROM buses WHERE bus_number = 'SQ-603'),
  (SELECT id FROM routes WHERE route_name = 'Dhaka - Singapore'),
  '2026-04-01 23:55:00', '2026-04-02 06:00:00', 250, 42000, 'ACTIVE'
);

INSERT IGNORE INTO schedules (bus_id, route_id, departure_time, arrival_time, available_seats, fare, status)
VALUES (
  (SELECT id FROM buses WHERE bus_number = 'SQ-601'),
  (SELECT id FROM routes WHERE route_name = 'Dhaka - Singapore'),
  '2026-04-01 23:55:00', '2026-04-02 06:00:00', 36, 95000, 'ACTIVE'
);

-- International: Dhaka - Kolkata
INSERT IGNORE INTO schedules (bus_id, route_id, departure_time, arrival_time, available_seats, fare, status)
VALUES (
  (SELECT id FROM buses WHERE bus_number = 'BG-103'),
  (SELECT id FROM routes WHERE route_name = 'Dhaka - Kolkata'),
  '2026-04-01 11:00:00', '2026-04-01 11:55:00', 180, 8500, 'ACTIVE'
);

INSERT IGNORE INTO schedules (bus_id, route_id, departure_time, arrival_time, available_seats, fare, status)
VALUES (
  (SELECT id FROM buses WHERE bus_number = 'IG-111'),
  (SELECT id FROM routes WHERE route_name = 'Dhaka - Kolkata'),
  '2026-04-01 16:00:00', '2026-04-01 16:55:00', 186, 7200, 'ACTIVE'
);

-- International: Dhaka - Bangkok
INSERT IGNORE INTO schedules (bus_id, route_id, departure_time, arrival_time, available_seats, fare, status)
VALUES (
  (SELECT id FROM buses WHERE bus_number = 'BG-103'),
  (SELECT id FROM routes WHERE route_name = 'Dhaka - Bangkok'),
  '2026-04-01 09:00:00', '2026-04-01 13:00:00', 180, 28000, 'ACTIVE'
);

-- International: Dhaka - Kuala Lumpur
INSERT IGNORE INTO schedules (bus_id, route_id, departure_time, arrival_time, available_seats, fare, status)
VALUES (
  (SELECT id FROM buses WHERE bus_number = 'MH-901'),
  (SELECT id FROM routes WHERE route_name = 'Dhaka - Kuala Lumpur'),
  '2026-04-01 01:00:00', '2026-04-01 07:30:00', 8, 150000, 'ACTIVE'
);

-- International: Dhaka - Istanbul
INSERT IGNORE INTO schedules (bus_id, route_id, departure_time, arrival_time, available_seats, fare, status)
VALUES (
  (SELECT id FROM buses WHERE bus_number = 'TK-801'),
  (SELECT id FROM routes WHERE route_name = 'Dhaka - Istanbul'),
  '2026-04-01 03:00:00', '2026-04-01 10:30:00', 38, 55000, 'ACTIVE'
);

-- International: Dhaka - Doha
INSERT IGNORE INTO schedules (bus_id, route_id, departure_time, arrival_time, available_seats, fare, status)
VALUES (
  (SELECT id FROM buses WHERE bus_number = 'QR-701'),
  (SELECT id FROM routes WHERE route_name = 'Dhaka - Doha'),
  '2026-04-01 04:00:00', '2026-04-01 07:00:00', 40, 48000, 'ACTIVE'
);

INSERT IGNORE INTO schedules (bus_id, route_id, departure_time, arrival_time, available_seats, fare, status)
VALUES (
  (SELECT id FROM buses WHERE bus_number = 'QR-702'),
  (SELECT id FROM routes WHERE route_name = 'Dhaka - Doha'),
  '2026-04-01 04:00:00', '2026-04-01 07:00:00', 8, 200000, 'ACTIVE'
);


-- -------------------------------------------
-- 6. Schedules (Return Flights)
-- -------------------------------------------

-- Return: Chittagong - Dhaka
INSERT IGNORE INTO schedules (bus_id, route_id, departure_time, arrival_time, available_seats, fare, status)
VALUES (
  (SELECT id FROM buses WHERE bus_number = 'BG-103'),
  (SELECT id FROM routes WHERE route_name = 'Chittagong - Dhaka'),
  '2026-04-01 17:00:00', '2026-04-01 17:55:00', 180, 3500, 'ACTIVE'
);

INSERT IGNORE INTO schedules (bus_id, route_id, departure_time, arrival_time, available_seats, fare, status)
VALUES (
  (SELECT id FROM buses WHERE bus_number = 'BS-201'),
  (SELECT id FROM routes WHERE route_name = 'Chittagong - Dhaka'),
  '2026-04-01 20:00:00', '2026-04-01 20:55:00', 30, 8500, 'ACTIVE'
);

-- Return: Sylhet - Dhaka
INSERT IGNORE INTO schedules (bus_id, route_id, departure_time, arrival_time, available_seats, fare, status)
VALUES (
  (SELECT id FROM buses WHERE bus_number = 'VQ-302'),
  (SELECT id FROM routes WHERE route_name = 'Sylhet - Dhaka'),
  '2026-04-01 18:00:00', '2026-04-01 19:00:00', 120, 4000, 'ACTIVE'
);

-- Return: Cox's Bazar - Dhaka
INSERT IGNORE INTO schedules (bus_id, route_id, departure_time, arrival_time, available_seats, fare, status)
VALUES (
  (SELECT id FROM buses WHERE bus_number = 'A5-401'),
  (SELECT id FROM routes WHERE route_name = 'Cox''s Bazar - Dhaka'),
  '2026-04-01 14:00:00', '2026-04-01 15:05:00', 78, 5200, 'ACTIVE'
);

-- Return: Rajshahi - Dhaka
INSERT IGNORE INTO schedules (bus_id, route_id, departure_time, arrival_time, available_seats, fare, status)
VALUES (
  (SELECT id FROM buses WHERE bus_number = 'BG-103'),
  (SELECT id FROM routes WHERE route_name = 'Rajshahi - Dhaka'),
  '2026-04-01 16:00:00', '2026-04-01 16:55:00', 180, 3800, 'ACTIVE'
);

-- Return: Dubai - Dhaka
INSERT IGNORE INTO schedules (bus_id, route_id, departure_time, arrival_time, available_seats, fare, status)
VALUES (
  (SELECT id FROM buses WHERE bus_number = 'EK-503'),
  (SELECT id FROM routes WHERE route_name = 'Dubai - Dhaka'),
  '2026-04-01 09:00:00', '2026-04-01 16:30:00', 300, 35000, 'ACTIVE'
);

INSERT IGNORE INTO schedules (bus_id, route_id, departure_time, arrival_time, available_seats, fare, status)
VALUES (
  (SELECT id FROM buses WHERE bus_number = 'EK-501'),
  (SELECT id FROM routes WHERE route_name = 'Dubai - Dhaka'),
  '2026-04-01 14:00:00', '2026-04-01 21:30:00', 42, 85000, 'ACTIVE'
);

INSERT IGNORE INTO schedules (bus_id, route_id, departure_time, arrival_time, available_seats, fare, status)
VALUES (
  (SELECT id FROM buses WHERE bus_number = 'BG-103'),
  (SELECT id FROM routes WHERE route_name = 'Dubai - Dhaka'),
  '2026-04-01 20:00:00', '2026-04-02 03:00:00', 180, 32000, 'ACTIVE'
);

-- Return: Singapore - Dhaka
INSERT IGNORE INTO schedules (bus_id, route_id, departure_time, arrival_time, available_seats, fare, status)
VALUES (
  (SELECT id FROM buses WHERE bus_number = 'SQ-603'),
  (SELECT id FROM routes WHERE route_name = 'Singapore - Dhaka'),
  '2026-04-01 08:00:00', '2026-04-01 11:00:00', 250, 42000, 'ACTIVE'
);

-- Return: Kolkata - Dhaka
INSERT IGNORE INTO schedules (bus_id, route_id, departure_time, arrival_time, available_seats, fare, status)
VALUES (
  (SELECT id FROM buses WHERE bus_number = 'BG-103'),
  (SELECT id FROM routes WHERE route_name = 'Kolkata - Dhaka'),
  '2026-04-01 14:00:00', '2026-04-01 14:55:00', 180, 8500, 'ACTIVE'
);

-- Return: Bangkok - Dhaka
INSERT IGNORE INTO schedules (bus_id, route_id, departure_time, arrival_time, available_seats, fare, status)
VALUES (
  (SELECT id FROM buses WHERE bus_number = 'BG-103'),
  (SELECT id FROM routes WHERE route_name = 'Bangkok - Dhaka'),
  '2026-04-01 15:00:00', '2026-04-01 17:30:00', 180, 28000, 'ACTIVE'
);

-- Return: Istanbul - Dhaka
INSERT IGNORE INTO schedules (bus_id, route_id, departure_time, arrival_time, available_seats, fare, status)
VALUES (
  (SELECT id FROM buses WHERE bus_number = 'TK-801'),
  (SELECT id FROM routes WHERE route_name = 'Istanbul - Dhaka'),
  '2026-04-01 18:00:00', '2026-04-02 05:30:00', 38, 55000, 'ACTIVE'
);

-- Return: Doha - Dhaka
INSERT IGNORE INTO schedules (bus_id, route_id, departure_time, arrival_time, available_seats, fare, status)
VALUES (
  (SELECT id FROM buses WHERE bus_number = 'QR-701'),
  (SELECT id FROM routes WHERE route_name = 'Doha - Dhaka'),
  '2026-04-01 20:00:00', '2026-04-02 04:00:00', 40, 48000, 'ACTIVE'
);

-- Return: Kuala Lumpur - Dhaka
INSERT IGNORE INTO schedules (bus_id, route_id, departure_time, arrival_time, available_seats, fare, status)
VALUES (
  (SELECT id FROM buses WHERE bus_number = 'MH-901'),
  (SELECT id FROM routes WHERE route_name = 'Kuala Lumpur - Dhaka'),
  '2026-04-01 10:00:00', '2026-04-01 13:00:00', 8, 150000, 'ACTIVE'
);
