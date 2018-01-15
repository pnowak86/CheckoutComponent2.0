create table if not exists STOCK_LIST(
stock_id int auto_increment primary key,
item_name char(20) not null,
stock_price int not null,
units_for_discount int not null,
discount_price int not null
);