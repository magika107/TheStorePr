BEGIN
    FOR t IN (SELECT table_name FROM user_tables)
        LOOP
            EXECUTE IMMEDIATE 'DROP TABLE "' || t.table_name || '" CASCADE CONSTRAINTS';
        END LOOP;
END;
/

BEGIN
    FOR s IN (SELECT sequence_name FROM user_sequences)
        LOOP
            EXECUTE IMMEDIATE 'DROP SEQUENCE "' || s.sequence_name || '"';
        END LOOP;
END;
/



create table users
(
    id           number primary key,
    gender       nvarchar2(20),
    name         nvarchar2(20),
    family       nvarchar2(20),
    birth_date   date,
    phone_number nvarchar2(20),
    username     nvarchar2(20),
    password     nvarchar2(20)
);

create sequence user_seq start with 1 increment by 1;

--

create table customers
(
    id           number primary key,
    gender       nvarchar2(20),
    name         nvarchar2(20),
    family       nvarchar2(20),
    birth_date   date,
    phone_number nvarchar2(20)
);

create sequence customer_seq start with 1 increment by 1;

--

create table products
(
    id           number primary key,
    title        nvarchar2(20),
    brand        nvarchar2(20),
    model        nvarchar2(20),
    serialNumber nvarchar2(20),
    buy_price    number
);

create sequence product_seq start with 1 increment by 1;

--

create table orders
(
    id           number primary key,
    order_serial nvarchar2(20),
    customer_id references customers,
    user_id references users,
    order_type   nvarchar2(20),
    discount     number,
    pure_amount  number,
    order_time   timestamp
);
create sequence order_seq start with 1 increment by 1;

--
create table order_items
(
    id       number primary key,
    product_id references products,
    quantity number,
    price    number,
    order_id references orders
);

--

create table payments
(
    id           number primary key,
    payment_type nvarchar2(20),
    order_id references orders,
    amount       number,
    customer_id references customers,
    user_id references users,
    payment_time timestamp
);
create sequence payment_seq start with 1 increment by 1;
