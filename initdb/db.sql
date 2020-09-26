CREATE TABLE users (
                       id serial NOT NULL,
                       name character varying(45),
                       password character varying(100),
                       role character varying(15),
                       email character varying(45),
                       PRIMARY KEY (id)
);

CREATE TABLE orders (
                        id serial NOT NULL,
                        status character varying(15),
                        submitted_by integer,
                        submitted_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
                        updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
                        PRIMARY KEY (id),
                        FOREIGN KEY (submitted_by) REFERENCES users(id)
                            ON DELETE SET NULL
                            ON UPDATE CASCADE
);

CREATE TABLE suppliers (
                           id serial NOT NULL,
                           user_id integer,
                           name character varying(45),
                           address character varying(100),
                           payer_number character varying(15),
                           registration_certificate_number character varying(20),
                           registration_date timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
                           phone_number character varying,
                           PRIMARY KEY (id),
                           FOREIGN KEY (user_id) REFERENCES users(id)
                               ON DELETE SET NULL
                               ON UPDATE CASCADE
);

CREATE TABLE products (
                          id serial NOT NULL,
                          supplier_id integer,
                          name character varying(45),
                          PRIMARY KEY (id),
                          FOREIGN KEY (supplier_id) REFERENCES suppliers (id)
                              ON DELETE SET NULL
                              ON UPDATE CASCADE
);

CREATE TABLE lineitems (
                           id serial NOT NULL,
                           order_id integer,
                           product_id integer,
                           quantity integer NOT NULL,
                           Primary key(id),
                           FOREIGN KEY (order_id) REFERENCES orders (id)
                               ON DELETE SET NULL
                               ON UPDATE CASCADE,
                           FOREIGN KEY (product_id) REFERENCES products (id)
                               ON DELETE SET NULL
                               ON UPDATE CASCADE);

CREATE TABLE stocks (
                        id serial NOT NULL,
                        quantity integer,
                        PRIMARY KEY (id)
);



-- use username:A password:A
insert into users(name, password, role, email)
values ('A',
        '$2a$10$ynJ0WFAkRIdjv5uMJ8MPOODzONsCpzAnBkxXunT6919fnEPmHquzO',
        'admin',
        'a@rkansas');

--postman collection with cases: https://www.getpostman.com/collections/e821884e9f7580e21292 (import in Postman)

