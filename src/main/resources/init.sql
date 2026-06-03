CREATE TABLE public.users (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    CONSTRAINT email UNIQUE(email)
);

CREATE TABLE public.stock (
	id SERIAL PRIMARY KEY,
	count INTEGER NOT NULL,
	owner_id INTEGER NOT NULL,
	symbol VARCHAR(255) NOT NULL,
	FOREIGN KEY (owner_id) REFERENCES public.users (id)
);