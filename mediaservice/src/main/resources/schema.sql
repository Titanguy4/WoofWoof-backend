CREATE TABLE IF NOT EXISTS media (
    id SERIAL PRIMARY KEY,
    url VARCHAR(255) NOT NULL,
    post_date DATE,
    media_type VARCHAR(50),  -- correspond à l'Enum MediaType stocké en String
    stay_id BIGINT,           -- nullable, utilisé pour WOOFSHARE_PHOTO et STAY_PHOTO
    user_id UUID     -- nullable, utilisé pour PROFILE_PHOTO
);
