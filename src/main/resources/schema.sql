CREATE TABLE IF NOT EXISTS "accounts" (
  "account_id" uuid PRIMARY KEY NOT NULL DEFAULT (gen_random_uuid()),
  "email" varchar(255) UNIQUE NOT NULL,
  "phone_no" varchar(10) UNIQUE,
  "account_type" varchar(10) NOT NULL,
  "created_at" timestamp NOT NULL,
  "updated_at" timestamp NOT NULL
);

CREATE TABLE IF NOT EXISTS "users" (
  "account_id" uuid PRIMARY KEY NOT NULL,
  "name" varchar(255),
  "age" int,
  "gender" varchar(6),
  "zip_code" varchar(5),
  "available_net_time" int DEFAULT 0,
  "auth_provider" varchar(20) NOT NULL, -- Pending to add curp url dir
  "is_active" bool NOT NULL DEFAULT true
);

CREATE TABLE IF NOT EXISTS "enterprises" (
  "account_id" uuid PRIMARY KEY NOT NULL,
  "company_name" varchar(255),
  "sector" varchar(255),
  "tax_certificated_url" varchar(512) UNIQUE,
  "address_proof_url" varchar(512) UNIQUE,
  "address" varchar(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS "devices" (
  "device_id" uuid PRIMARY KEY NOT NULL DEFAULT (gen_random_uuid()),
  "account_id" uuid NOT NULL,
  "mac_address" varchar(17) UNIQUE NOT NULL,
  "is_online" bool NOT NULL
);

CREATE TABLE IF NOT EXISTS "credentials" (
  "credential_id" uuid PRIMARY KEY NOT NULL DEFAULT (gen_random_uuid()),
  "account_id" uuid UNIQUE NOT NULL,
  "hashed_pwd" varchar(512) NOT NULL,
  "created_at" timestamp NOT NULL,
  "updated_at" timestamp NOT NULL
);

COMMENT ON COLUMN "accounts"."account_type" IS 'user | enterprise';

ALTER TABLE "users" ADD FOREIGN KEY ("account_id") REFERENCES "accounts" ("account_id");

ALTER TABLE "enterprises" ADD FOREIGN KEY ("account_id") REFERENCES "accounts" ("account_id");

ALTER TABLE "devices" ADD FOREIGN KEY ("account_id") REFERENCES "accounts" ("account_id");

ALTER TABLE "credentials" ADD FOREIGN KEY ("account_id") REFERENCES "accounts" ("account_id");