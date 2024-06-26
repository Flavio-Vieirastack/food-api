create table order_item (
    quantity integer  not null,
    total_price numeric(38,2) not null,
    unitary_price numeric(38,2) not null,
    id bigint generated by default as identity,
    orders_id bigint,
    observation varchar(100),
    primary key (id)
);
create table order_item_product_list (
    order_item_id bigint not null,
    product_list_id bigint not null
);
create table orders (
cancel_date timestamp(6),
    delivery_tax numeric(38,2) not null,
    order_status varchar(20) not null,
    sub_total numeric(38,2) not null,
    total_value numeric(38,2) not null,
    address_city bigint,
    confirmation_date timestamp(6),
    creation_date timestamp(6),
    delivery_date timestamp(6),
    id bigint generated by default as identity,
    restaurant_id bigint,
    user_client_id bigint,
    address_complement varchar(200) not null,
    address_district varchar(200) not null,
    address_number varchar(10) not null,
    address_public_place varchar(200) not null,
    address_zip_code varchar(50) not null,
    primary key (id)
);
create table orders_order_item (
    order_item_id bigint not null,
    orders_id bigint not null
);
create table orders_payment_type (
    orders_id bigint not null,
    payment_type_id bigint not null
);

ALTER TABLE IF EXISTS order_item ADD CONSTRAINT fk_order_item_orders FOREIGN KEY (orders_id) REFERENCES orders;
ALTER TABLE IF EXISTS order_item_product_list ADD CONSTRAINT fk_order_item_product_list_product FOREIGN KEY (product_list_id) REFERENCES product;
ALTER TABLE IF EXISTS order_item_product_list ADD CONSTRAINT fk_order_item_product_list_order_item FOREIGN KEY (order_item_id) REFERENCES order_item;
ALTER TABLE IF EXISTS orders ADD CONSTRAINT fk_orders_address_city FOREIGN KEY (address_city) REFERENCES city;
ALTER TABLE IF EXISTS orders ADD CONSTRAINT fk_orders_restaurant FOREIGN KEY (restaurant_id) REFERENCES restaurant;
ALTER TABLE IF EXISTS orders ADD CONSTRAINT fk_orders_user_client FOREIGN KEY (user_client_id) REFERENCES user_client;
ALTER TABLE IF EXISTS orders_order_item ADD CONSTRAINT fk_orders_order_item_order_item FOREIGN KEY (order_item_id) REFERENCES order_item;
ALTER TABLE IF EXISTS orders_order_item ADD CONSTRAINT fk_orders_order_item_orders FOREIGN KEY (orders_id) REFERENCES orders;
ALTER TABLE IF EXISTS orders_payment_type ADD CONSTRAINT fk_orders_payment_type_payment_type FOREIGN KEY (payment_type_id) REFERENCES payment_type;
ALTER TABLE IF EXISTS orders_payment_type ADD CONSTRAINT fk_orders_payment_type_orders FOREIGN KEY (orders_id) REFERENCES orders;
