CREATE TABLE IF NOT EXISTS  complaints (
                            id BIGSERIAL PRIMARY KEY,
                            product_id BIGINT NOT NULL,
                            user_id BIGINT NOT NULL,
                            description TEXT NOT NULL,
                            counter BIGINT NOT NULL DEFAULT 1,
                            country_code VARCHAR(10) NOT NULL,
                            created_date TIMESTAMP NOT NULL DEFAULT NOW(),
                            updated_date TIMESTAMP NOT NULL DEFAULT NOW()
);
