-- Test-Daten für Hangar Management System

-- Aircraft Owners (Benutzer Typ mit IDs 1-3)
INSERT INTO benutzer (id, name, email, password, contact, role) VALUES 
(1, 'Max Mustermann', 'max@example.com', 'pass123', '0176-1234567', 'AIRCRAFT_OWNER'),
(2, 'Anna Schmidt', 'anna@example.com', 'pass456', '0177-9876543', 'AIRCRAFT_OWNER'),
(3, 'Hans Meyer', 'hans@example.com', 'pass789', '0178-5555555', 'AIRCRAFT_OWNER');

INSERT INTO aircraft_owner (id) VALUES (1), (2), (3);

-- Hangar Providers (Benutzer Typ mit IDs 4-5)
INSERT INTO benutzer (id, name, email, password, contact, role) VALUES
(4, 'Sky Hangar GmbH', 'sky@hangar.de', 'hp123', '0800-111111', 'HANGAR_PROVIDER'),
(5, 'AeroStorage AG', 'aero@storage.de', 'hp456', '0800-222222', 'HANGAR_PROVIDER');

INSERT INTO hangar_provider (id, service_hours) VALUES
(4, 'Mo-Fr 8:00-18:00'),
(5, 'Mo-So 6:00-22:00');

INSERT INTO hangar_provider_storage_conditions (hangar_provider_id, storage_conditions) VALUES
(4, 'Climate controlled'),
(4, '24/7 Security'),
(5, 'Basic storage'),
(5, 'Fire protection');

-- Parts Providers (Benutzer Typ mit IDs 6-8)
INSERT INTO benutzer (id, name, email, password, contact, role) VALUES
(6, 'Aviation Parts Inc', 'parts@aviation.com', 'ps123', '0800-333333', 'PARTS_PROVIDER'),
(7, 'Aircraft Components Ltd', 'components@aircraft.com', 'ps456', '0800-444444', 'PARTS_PROVIDER'),
(8, 'AeroSupply GmbH', 'supply@aero.de', 'ps789', '0800-555555', 'PARTS_PROVIDER');

INSERT INTO parts_provider (id) VALUES (6), (7), (8);

-- Spare Parts
INSERT INTO spare_part (name, description, price, quantity, availability, is_reserved, parts_provider_id) VALUES
('Propeller Blade', 'High-quality aluminum propeller blade', 1500, 10, true, false, 6),
('Landing Gear', 'Heavy-duty landing gear system', 5000, 5, true, false, 6),
('Engine Filter', 'Oil filter for aircraft engines', 150, 50, true, false, 7),
('Navigation Light', 'LED navigation light', 200, 25, true, false, 7),
('Fuel Pump', 'Electric fuel pump', 800, 15, true, false, 8),
('Brake Pad Set', 'Carbon brake pads', 600, 30, true, false, 8);

-- Aircrafts
INSERT INTO aircraft (registration_mark, dimensions, size, maintenance_status, flight_readiness, aircraft_owner_id) VALUES
('D-ABCD', '10m x 15m', 5, 'Good', 'Ready', 1),
('D-EFGH', '12m x 18m', 8, 'Maintenance Required', 'Not Ready', 2),
('D-IJKL', '11m x 16m', 6, 'Excellent', 'Ready', 3);

-- Services (Hizmetler aircraft owner'lara atanmış)
INSERT INTO service (name, description, price, hangar_provider_id, aircraft_owner_id) VALUES
('Basic Maintenance', 'Standard maintenance check', 500, 4, 1),
('Full Inspection', 'Complete aircraft inspection', 2000, 4, 2),
('Cleaning Service', 'Interior and exterior cleaning', 300, 5, 1),
('Engine Overhaul', 'Complete engine overhaul', 15000, 5, 3);

-- Parking Spots
INSERT INTO parking (number, status, site_status, hangar_provider_id) VALUES
(1, 'AVAILABLE', 'Indoor', 4),
(2, 'AVAILABLE', 'Indoor', 4),
(3, 'OCCUPIED', 'Outdoor', 5),
(4, 'AVAILABLE', 'Outdoor', 5),
(5, 'RESERVED', 'Indoor', 4);
