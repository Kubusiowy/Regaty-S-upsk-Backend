create table refreshToken(
    id char(36) PRIMARY KEY,
    user_id char(36) not null,
    token_hash VARCHAR(255) not null,
    expires_at TIMESTAMP not null,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    revoked BOOLEAN DEFAULT FALSE,

    FOREIGN KEY (user_id)
        REFERENCES admin(id) ON DELETE CASCADE

)