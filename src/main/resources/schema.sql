CREATE TABLE IF NOT EXISTS "account" (
  "account_id" uuid PRIMARY KEY NOT NULL DEFAULT (gen_random_uuid()),
  "email" varchar(255) UNIQUE NOT NULL,
  "phone_no" varchar(10) UNIQUE,
  "account_type" varchar(10) NOT NULL,
  "created_at" timestamp NOT NULL,
  "updated_at" timestamp NOT NULL
);

CREATE TABLE IF NOT EXISTS "personal_account" (
  "account_id" uuid PRIMARY KEY NOT NULL,
  "name" varchar(255),
  "age" int,
  "gender" varchar(6),
  "zip_code" varchar(5),
  "available_net_time" int DEFAULT 0,
  "auth_provider" varchar(20) NOT NULL, -- Pending to add curp url dir
  "is_active" bool NOT NULL DEFAULT true
);

CREATE TABLE IF NOT EXISTS "company_account" (
  "account_id" uuid PRIMARY KEY NOT NULL,
  "company_name" varchar(255),
  "sector" varchar(255),
  "tax_certificated_url" varchar(512) UNIQUE,
  "address_proof_url" varchar(512) UNIQUE,
  "address" varchar(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS "device" (
  "device_id" uuid PRIMARY KEY NOT NULL DEFAULT (gen_random_uuid()),
  "account_id" uuid NOT NULL,
  "mac_address" varchar(17) UNIQUE NOT NULL,
  "is_online" bool NOT NULL
);

CREATE TABLE IF NOT EXISTS "session" (
  "session_id" uuid PRIMARY KEY NOT NULL DEFAULT (gen_random_uuid()),
  "device_id" uuid NOT NULL,
  "refresh_token" varchar(512) NOT NULL,
  "created_at" timestamp NOT NULL,
  "expires_at" timestamp NOT NULL
);

CREATE TABLE IF NOT EXISTS "credential" (
  "credential_id" uuid PRIMARY KEY NOT NULL DEFAULT (gen_random_uuid()),
  "account_id" uuid UNIQUE NOT NULL,
  "hashed_pwd" varchar(512) NOT NULL,
  "created_at" timestamp NOT NULL,
  "updated_at" timestamp NOT NULL
);

COMMENT ON COLUMN "account"."account_type" IS 'personal | company';

ALTER TABLE "personal_account" ADD FOREIGN KEY ("account_id") REFERENCES "account" ("account_id");

ALTER TABLE "company_account" ADD FOREIGN KEY ("account_id") REFERENCES "account" ("account_id");

ALTER TABLE "device" ADD FOREIGN KEY ("account_id") REFERENCES "account" ("account_id");

ALTER TABLE "session" ADD FOREIGN KEY ("device_id") REFERENCES "device" ("device_id");

ALTER TABLE "credential" ADD FOREIGN KEY ("account_id") REFERENCES "account" ("account_id");