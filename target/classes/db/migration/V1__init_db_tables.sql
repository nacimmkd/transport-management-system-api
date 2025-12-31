CREATE TYPE user_role AS ENUM ('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_DRIVER');
CREATE TYPE vehicle_type AS ENUM ('CAR', 'VAN', 'TRUCK', 'HEAVY_TRUCK');
CREATE TYPE vehicle_status AS ENUM ('AVAILABLE', 'IN_USE', 'MAINTENANCE');
CREATE TYPE delivery_status AS ENUM ('PENDING', 'ASSIGNED', 'IN_TRANSIT', 'ARRIVED', 'DELIVERED', 'CANCELLED', 'FAILED');
CREATE TYPE license_type AS ENUM ('B', 'C', 'CE');


CREATE EXTENSION IF NOT EXISTS "pgcrypto";


CREATE TABLE "companies" (
                             "id" UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                             "name" VARCHAR(255) NOT NULL,
                             "created_at" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                             "siren" CHAR(9) NOT NULL UNIQUE,
                             "address" VARCHAR(255),
                             "email" VARCHAR(255) NOT NULL UNIQUE,
                             "phone" VARCHAR(255) NOT NULL,
                             "is_active" BOOLEAN NOT NULL DEFAULT TRUE,
                             "deleted_at" TIMESTAMP
);


CREATE TABLE "users" (
                         "id" UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                         "username" VARCHAR(255) NOT NULL,
                         "email" VARCHAR(255) NOT NULL UNIQUE,
                         "password" VARCHAR(255) NOT NULL,
                         "role" user_role NOT NULL,
                         "company_id" UUID NOT NULL,
                         "created_at" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         "is_active" BOOLEAN NOT NULL DEFAULT TRUE,
                         "phone" VARCHAR(255) NOT NULL,
                         "deleted_at" TIMESTAMP,
                         FOREIGN KEY ("company_id") REFERENCES "companies"("id") ON DELETE NO ACTION ON UPDATE RESTRICT
);


CREATE TABLE "vehicles" (
                            "id" UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                            "model" VARCHAR(255),
                            "plate_number" VARCHAR(255) UNIQUE,
                            "type" vehicle_type NOT NULL,
                            "capacity_kg" DECIMAL(10,2) NOT NULL,
                            "status" vehicle_status NOT NULL,
                            "company_id" UUID NOT NULL,
                            "brand" VARCHAR(255) NOT NULL,
                            "created_at" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            "is_active" BOOLEAN NOT NULL DEFAULT TRUE,
                            "deleted_at" TIMESTAMP,
                            FOREIGN KEY ("company_id") REFERENCES "companies"("id") ON DELETE NO ACTION ON UPDATE RESTRICT
);


CREATE TABLE "clients" (
                           "id" UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                           "name" VARCHAR(255),
                           "phone" VARCHAR(255) NOT NULL,
                           "email" VARCHAR(255) NOT NULL UNIQUE,
                           "address" VARCHAR(255) NOT NULL,
                           "company_id" UUID NOT NULL,
                           "created_at" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           "is_active" BOOLEAN DEFAULT TRUE,
                           "deleted_at" TIMESTAMP,
                           FOREIGN KEY ("company_id") REFERENCES "companies"("id") ON DELETE NO ACTION ON UPDATE RESTRICT
);


CREATE TABLE "drivers" (
                           "id" UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                           "user_id" UUID NOT NULL UNIQUE,
                           "license_number" VARCHAR(255) NOT NULL,
                           "license_category" license_type NOT NULL,
                           "license_expiry_date" TIMESTAMP NOT NULL,
                           FOREIGN KEY ("user_id") REFERENCES "users"("id") ON DELETE NO ACTION ON UPDATE RESTRICT
);



CREATE TABLE "deliveries" (
                              "id" UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                              "pickup_address" VARCHAR(255) NOT NULL,
                              "delivery_address" VARCHAR(255) NOT NULL,
                              "requested_delivery_time" TIMESTAMP NOT NULL,
                              "delivered_at" TIMESTAMP,
                              "status" delivery_status NOT NULL,
                              "client_id" UUID NOT NULL,
                              "vehicle_id" UUID NOT NULL,
                              "company_id" UUID NOT NULL,
                              "driver_id" UUID NOT NULL,
                              "delivery_code" VARCHAR(255) NOT NULL,
                              "planned_start_time" TIMESTAMP NOT NULL,
                              "price" DECIMAL(10,2) NOT NULL DEFAULT 0.00,
                              FOREIGN KEY ("client_id") REFERENCES "clients"("id") ON DELETE NO ACTION ON UPDATE RESTRICT,
                              FOREIGN KEY ("vehicle_id") REFERENCES "vehicles"("id") ON DELETE NO ACTION ON UPDATE RESTRICT,
                              FOREIGN KEY ("company_id") REFERENCES "companies"("id") ON DELETE NO ACTION ON UPDATE RESTRICT,
                              FOREIGN KEY ("driver_id") REFERENCES "drivers"("id") ON DELETE NO ACTION ON UPDATE RESTRICT
);


CREATE TABLE "deliveries_history" (
                                      "id" UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                      "status" delivery_status NOT NULL,
                                      "updated_at" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                      "delivery_id" UUID NOT NULL,
                                      FOREIGN KEY ("delivery_id") REFERENCES "deliveries"("id") ON DELETE CASCADE ON UPDATE RESTRICT
);


