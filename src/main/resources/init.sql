CREATE TABLE public.users (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    CONSTRAINT email UNIQUE(email)
);

CREATE TABLE public.stock (
	id SERIAL PRIMARY_KEY,
	count INTEGER NOT NULL,
	owner_id INTEGER NULL,
	symbol varchar(255) NOT NULL,
	CONSTRAINT stock_pkey PRIMARY KEY (id),
	FOREIGN KEY (owner_id) REFERENCES public.users (id)
);