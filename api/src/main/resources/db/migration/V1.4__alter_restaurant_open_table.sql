alter table restaurant add is_open boolean not null;
update restaurant set is_open=true;