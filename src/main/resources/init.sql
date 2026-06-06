CREATE TABLE IF NOT EXISTS public.users (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    CONSTRAINT email UNIQUE(email)
);

CREATE TABLE IF NOT EXISTS public.stock (
	id SERIAL PRIMARY KEY,
	count INTEGER NOT NULL,
	owner_id INTEGER NOT NULL,
	symbol VARCHAR(15) NOT NULL,
	shortname VARCHAR(63) NOT NULL,
	FOREIGN KEY (owner_id) REFERENCES public.users (id)
);

CREATE INDEX IF NOT EXISTS email_idx ON users (email);
CREATE INDEX IF NOT EXISTS owner_id_idx ON stock (owner_id);