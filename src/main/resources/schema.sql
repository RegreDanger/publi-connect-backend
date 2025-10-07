CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS individual_user (
	user_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
	name VARCHAR(255) NOT NULL,
	age INT NOT NULL,
	gender VARCHAR(50) NOT NULL,
	email VARCHAR(255) UNIQUE NOT NULL,
	phone_no VARCHAR(20) NOT NULL,
	zip_code VARCHAR(20) NOT NULL,
	auth_provider VARCHAR(50) NOT NULL,
	is_active BOOLEAN DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS device (
	device_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
	user_id UUID NOT NULL,
	mac_address VARCHAR(255) UNIQUE NOT NULL,
	valid_until TIMESTAMP NOT NULL,
	is_logged BOOLEAN DEFAULT TRUE,
	CONSTRAINT fk_device_user FOREIGN KEY (user_id) REFERENCES individual_user (user_id)
);

CREATE TABLE IF NOT EXISTS session (
	session_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
	device_id UUID UNIQUE NOT NULL,
	refresh_token VARCHAR(512) NOT NULL,
	created_at TIMESTAMP NOT NULL,
	expires_at TIMESTAMP NOT NULL,
	
	CONSTRAINT fk_device_session FOREIGN KEY (device_id) REFERENCES device (device_id)
);

CREATE TABLE IF NOT EXISTS credential (
	credential_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
	user_id UUID UNIQUE NOT NULL,
	hashed_pwd VARCHAR(512) NOT NULL,
	created_at TIMESTAMP NOT NULL,
	updated_at TIMESTAMP NOT NULL,

	CONSTRAINT fk_credential_user FOREIGN KEY (user_id) REFERENCES individual_user(user_id)

);
