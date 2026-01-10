ALTER TABLE companies DROP CONSTRAINT IF EXISTS companies_siren_key;
ALTER TABLE companies DROP CONSTRAINT IF EXISTS companies_email_key;


CREATE UNIQUE INDEX idx_companies_email_active_unique
    ON companies (email)
    WHERE deleted = false;