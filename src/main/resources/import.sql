
INSERT INTO kitchen (name) VALUES ('Tailandesa')
INSERT INTO kitchen (name) VALUES ('Indiana')

INSERT INTO Payment_Type (description) VALUES ('Dinheiro')
INSERT INTO Payment_Type (description) VALUES ('Cartão')
INSERT INTO Payment_Type (description) VALUES ('Pix')

INSERT INTO Permission (name, description) VALUES ('Permissão teste', 'Descrição teste')
INSERT INTO Permission (name, description) VALUES ('Permissão teste 2', 'Descrição teste 2')

INSERT INTO State (name) VALUES ('CE')

INSERT INTO City (name, state_id) VALUES ('Crato', 1)
INSERT INTO City (name, state_id) VALUES ('Juazeiro', 1)

INSERT INTO Restaurant (name, delivery_tax, kitchen_id, address_zip_code, address_public_place, address_number, address_complement, address_district, address_city) VALUES ('Thai gourmet', 10, 1, '2033-5', 'Public place 1', '255', 'complemento teste 1', 'bairro teste 1', 1)
INSERT INTO Restaurant (name, delivery_tax, kitchen_id, address_zip_code, address_public_place, address_number, address_complement, address_district, address_city) VALUES ('Portuguesa', 5, 2, '4589-5', 'Public place 2', '100', 'complemento teste 2', 'bairro teste 2', 1)
INSERT INTO Restaurant (name, delivery_tax, kitchen_id, address_zip_code, address_public_place, address_number, address_complement, address_district, address_city) VALUES ('Chinesa', 8, 1, '5689-5', 'Public place 3', '200', 'complemento teste 3', 'bairro teste 3', 2)



INSERT INTO restaurant_payment_type (restaurant_id, payment_type_id) VALUES (1,1), (1,2), (1,3), (2,3), (3,2)