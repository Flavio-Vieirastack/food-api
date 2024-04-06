CREATE TABLE if not exists product_photo (
  product_id bigint not null,
  file_name varchar(150) not null,
  description varchar(150),
  content_type varchar(80),
  file_size int not null,

  primary key (product_id),
  constraint fk_product_photo foreign key (product_id) references product(id)
);
