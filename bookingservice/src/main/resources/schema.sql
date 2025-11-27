CREATE TABLE IF NOT EXISTS booking (
    id SERIAL PRIMARY KEY,
    stay_id BIGINT,
    user_id BIGINT,
    start_requested_date DATE,
    end_requested_date DATE,
    status VARCHAR(255),
    email VARCHAR(255),
    phone_number VARCHAR(255)
);
