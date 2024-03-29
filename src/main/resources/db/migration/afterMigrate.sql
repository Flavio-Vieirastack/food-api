INSERT INTO kitchen (name) VALUES ('Tailandesa');
INSERT INTO kitchen (name) VALUES ('Indiana');

INSERT INTO Payment_Type (description) VALUES ('Dinheiro');
INSERT INTO Payment_Type (description) VALUES ('Cartão');
INSERT INTO Payment_Type (description) VALUES ('Pix');

INSERT INTO Permission (name, description) VALUES ('Permissão teste', 'Descrição teste');
INSERT INTO Permission (name, description) VALUES ('Permissão teste 2', 'Descrição teste 2');
INSERT INTO Permission (name, description) VALUES ('Permissão teste 3', 'Descrição teste 3');

INSERT INTO State (name) VALUES ('CE');

INSERT INTO City (name, state_id) VALUES ('Crato', 1);
INSERT INTO City (name, state_id) VALUES ('Juazeiro', 1);

INSERT INTO Restaurant (name, delivery_tax, kitchen_id, address_zip_code, address_public_place, address_number, address_complement, address_district, address_city, created_at, updated_at, active, is_open) VALUES ('Thai gourmet', 10, 1, '2033-5', 'Public place 1', '255', 'complemento teste 1', 'bairro teste 1', 1, CURDATE(), CURDATE(), true, true);
INSERT INTO Restaurant (name, delivery_tax, kitchen_id, address_zip_code, address_public_place, address_number, address_complement, address_district, address_city, created_at, updated_at, active, is_open) VALUES ('Portuguesa', 5, 2, '4589-5', 'Public place 2', '100', 'complemento teste 2', 'bairro teste 2', 1, CURDATE(), CURDATE(), true, false);
INSERT INTO Restaurant (name, delivery_tax, kitchen_id, address_zip_code, address_public_place, address_number, address_complement, address_district, address_city, created_at, updated_at, active, is_open) VALUES ('Chinesa', 8, 1, '5689-5', 'Public place 3', '200', 'complemento teste 3', 'bairro teste 3', 2, CURDATE(), CURDATE(), true, true);

INSERT INTO Product(name, description, price, active, restaurant_id) VALUES ('produto teste 1', 'descrição teste 1', 100.00, true, 1);
INSERT INTO Product(name, description, price, active, restaurant_id) VALUES ('produto teste 2', 'descrição teste 2', 50.00, true, 2);
INSERT INTO Product(name, description, price, active, restaurant_id) VALUES ('produto teste 3', 'descrição teste 3', 20.00, false, 3);
INSERT INTO Product(name, description, price, active, restaurant_id) VALUES ('produto teste 4', 'descrição teste 4', 10.00, true, 2);

INSERT INTO Group_Permissions (name) VALUES ('Grupo 1');
INSERT INTO Group_Permissions (name) VALUES ('Grupo 2');
INSERT INTO Group_Permissions (name) VALUES ('Grupo 3');

INSERT INTO User_Client (name, email, password, created_at) VALUES ('Nome 1', 'email@email.com', '123', CURDATE());
INSERT INTO User_Client (name, email, password, created_at) VALUES ('Nome 2', 'email@email2.com', '123', CURDATE());
INSERT INTO User_Client (name, email, password, created_at) VALUES ('Nome 3', 'email@email3.com', '123', CURDATE());

INSERT INTO restaurant_payment_type (restaurant_id, payment_type_id) VALUES (1,1), (1,2), (1,3), (2,3), (3,2);
INSERT INTO RESTAURANT_PRODUCTS (products_id, restaurant_id) VALUES (1,1), (2,2), (3,3), (4,3);
INSERT INTO GROUP_PERMISSIONS_TB (GROUP_ID, PERMISSION_ID) VALUES (1,1), (2,2), (3,3), (1,1), (2,2), (3,3);
INSERT INTO USER_GROUP_TB (GROUP_ID, USER_ID) VALUES (1,1), (2,2), (3,3), (1,1), (2,2), (3,3);

INSERT INTO orders (cancel_date, delivery_tax, order_status, sub_total, total_value, address_city, confirmation_date, creation_date, delivery_date, restaurant_id, user_client_id, address_complement, address_district, address_number, address_public_place, address_zip_code)
VALUES
('2024-03-10 12:00:00', 5.50, 2, 50.00, 55.50, 1, '2024-03-08 10:00:00', '2024-03-08 09:30:00', '2024-03-10 12:30:00', 1, 1, 'Bloco A', 'Centro', '123', 'Rua Principal', '12345-678'),
('2024-03-12 11:00:00', 7.00, 1, 30.00, 37.00, 2, '2024-03-10 14:00:00', '2024-03-10 13:30:00', '2024-03-12 11:30:00', 2, 2, 'Apto 101', 'Centro', '456', 'Avenida Central', '54321-987'),
('2024-03-15 14:00:00', 6.50, 3, 40.00, 46.50, 1, '2024-03-13 16:00:00', '2024-03-13 15:30:00', '2024-03-15 14:30:00', 3, 3, 'Casa 2', 'Bairro Norte', '789', 'Rua Secundária', '13579-246');

INSERT INTO order_item (quantity, total_price, unitary_price, orders_id, observation)
VALUES
(2, 25.00, 12.50, 1, 'Sem cebola'),
(3, 30.00, 10.00, 1, 'Com molho extra'),
(1, 15.00, 15.00, 2, 'Pedido especial'),
(4, 40.00, 10.00, 2, 'Entrega rápida');

INSERT INTO order_item_product_list (order_item_id, product_list_id)
VALUES
(1, 1),
(1, 2),
(1, 3),
(2, 2),
(3, 1),
(3, 3);

INSERT INTO orders_order_item (order_item_id, orders_id)
VALUES
(1, 1),
(2, 1),
(3, 2);

INSERT INTO orders_payment_type (orders_id, payment_type_id)
VALUES
(1, 1),
(1, 2),
(2, 1),
(3, 2);

INSERT INTO restaurant_users (restaurant_id, user_id)
VALUES
(1, 1),
(1, 2),
(2, 1),
(3, 2);
