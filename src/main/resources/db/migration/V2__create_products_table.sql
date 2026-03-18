create table products(
   id bigserial primary key,
   code varchar(50) unique not null,
   name varchar(255) not null,
   description varchar(500),
   price numeric(10, 2) not null check(price>0),
   stock integer not null check(stock>=0),
   category varchar(100) not null,
   created_at timestamp not null default current_timestamp,
   updated_at timestamp not null default current_timestamp
);