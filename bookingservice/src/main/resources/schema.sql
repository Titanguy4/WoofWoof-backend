CREATE TABLE IF NOT EXISTS booking (
    id SERIAL PRIMARY KEY,
    stay_id BIGINT,
    user_id UUID,
    start_requested_date DATE,
    end_requested_date DATE,
    status VARCHAR(255),
    email VARCHAR(255),
    number VARCHAR(255)
);
