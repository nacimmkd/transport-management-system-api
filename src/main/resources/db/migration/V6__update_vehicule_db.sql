ALTER TABLE vehicles DROP CONSTRAINT IF EXISTS vehicles_plate_number_key;

CREATE UNIQUE INDEX idx_vehicle_plate_active
    ON vehicles (plate_number, company_id)
    WHERE deleted = false;


ALTER TABLE clients DROP CONSTRAINT IF EXISTS clients_email_key;

CREATE UNIQUE INDEX idx_client_email_active
    ON clients (email, company_id)
    WHERE deleted = false;