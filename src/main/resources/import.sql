
INSERT INTO kitchen (name) VALUES ('Tailandesa')
INSERT INTO kitchen (name) VALUES ('Indiana')

INSERT INTO Payment_Type (description) VALUES ('Dinheiro')
INSERT INTO Payment_Type (description) VALUES ('Cartão')
INSERT INTO Payment_Type (description) VALUES ('Pix')

INSERT INTO Permission (name, description) VALUES ('Permissão teste', 'Descrição teste')
INSERT INTO Permission (name, description) VALUES ('Permissão teste 2', 'Descrição teste 2')

INSERT INTO State (name) VALUES ('CE')

INSERT INTO City (name, state_id) VALUES ('Crato', 1)

INSERT INTO Restaurant (name, delivery_tax, kitchen_id) VALUES ('Thai gourmet', 10, 1)
INSERT INTO Restaurant (name, delivery_tax, kitchen_id) VALUES ('Portuguesa', 20, 1)
INSERT INTO Restaurant (name, delivery_tax, kitchen_id) VALUES ('Indiana', 5.5, 2)

INSERT INTO restaurant_payment_type (restaurant_id, payment_type_id) VALUES (1,1), (1,2), (1,3), (2,3), (3,2)