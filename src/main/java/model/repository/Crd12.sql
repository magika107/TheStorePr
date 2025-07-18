BEGIN
    BEGIN EXECUTE IMMEDIATE 'DROP TABLE payments CASCADE CONSTRAINTS';     EXCEPTION WHEN OTHERS THEN NULL; END;
    BEGIN EXECUTE IMMEDIATE 'DROP TABLE order_items CASCADE CONSTRAINTS';  EXCEPTION WHEN OTHERS THEN NULL; END;
    BEGIN EXECUTE IMMEDIATE 'DROP TABLE orders CASCADE CONSTRAINTS';       EXCEPTION WHEN OTHERS THEN NULL; END;
    BEGIN EXECUTE IMMEDIATE 'DROP TABLE products CASCADE CONSTRAINTS';     EXCEPTION WHEN OTHERS THEN NULL; END;
    BEGIN EXECUTE IMMEDIATE 'DROP TABLE customers CASCADE CONSTRAINTS';    EXCEPTION WHEN OTHERS THEN NULL; END;
    BEGIN EXECUTE IMMEDIATE 'DROP TABLE users CASCADE CONSTRAINTS';        EXCEPTION WHEN OTHERS THEN NULL; END;
END;
/

BEGIN
    BEGIN EXECUTE IMMEDIATE 'DROP SEQUENCE payment_seq';   EXCEPTION WHEN OTHERS THEN NULL; END;
    BEGIN EXECUTE IMMEDIATE 'DROP SEQUENCE order_seq';     EXCEPTION WHEN OTHERS THEN NULL; END;
    BEGIN EXECUTE IMMEDIATE 'DROP SEQUENCE product_seq';   EXCEPTION WHEN OTHERS THEN NULL; END;
    BEGIN EXECUTE IMMEDIATE 'DROP SEQUENCE customer_seq';  EXCEPTION WHEN OTHERS THEN NULL; END;
    BEGIN EXECUTE IMMEDIATE 'DROP SEQUENCE user_seq';      EXCEPTION WHEN OTHERS THEN NULL; END;
END;
/





CREATE TABLE users (
                       id           NUMBER PRIMARY KEY,
                       gender       NVARCHAR2(20),
                       name         NVARCHAR2(20) NOT NULL,
                       family       NVARCHAR2(20) NOT NULL,
                       birth_date   DATE,
                       phone_number NVARCHAR2(20) UNIQUE,
                       username     NVARCHAR2(20) UNIQUE NOT NULL,
                       password     NVARCHAR2(20) NOT NULL
);
CREATE SEQUENCE user_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE customers (
                           id           NUMBER PRIMARY KEY,
                           gender       NVARCHAR2(20),
                           name         NVARCHAR2(20) NOT NULL,
                           family       NVARCHAR2(20) NOT NULL,
                           birth_date   DATE,
                           phone_number NVARCHAR2(20) UNIQUE
);
CREATE SEQUENCE customer_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE products (
                          id           NUMBER PRIMARY KEY,
                          title        NVARCHAR2(20) NOT NULL,
                          brand        NVARCHAR2(20),
                          model        NVARCHAR2(20),
                          serialNumber NVARCHAR2(20) UNIQUE,
                          buy_price    NUMBER CHECK (buy_price >= 0)
);
CREATE SEQUENCE product_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE orders (
                        id           NUMBER PRIMARY KEY,
                        order_serial NVARCHAR2(20) UNIQUE NOT NULL,
                        customer_id  NUMBER REFERENCES customers(id),
                        user_id      NUMBER REFERENCES users(id),
                        order_type   NVARCHAR2(20) CHECK (order_type IN ('buy', 'sell')),
                        discount     NUMBER DEFAULT 0 CHECK (discount >= 0),
                        pure_amount  NUMBER DEFAULT 0 CHECK (pure_amount >= 0),
                        order_time   TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE SEQUENCE order_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE order_items (
                             id         NUMBER PRIMARY KEY,
                             product_id NUMBER REFERENCES products(id),
                             quantity   NUMBER NOT NULL CHECK (quantity > 0),
                             price      NUMBER NOT NULL CHECK (price >= 0),
                             order_id   NUMBER REFERENCES orders(id)
);

CREATE TABLE payments (
                          id           NUMBER PRIMARY KEY,
                          payment_type NVARCHAR2(20) CHECK (payment_type IN ('cash', 'card', 'bank')),
                          order_id     NUMBER REFERENCES orders(id),
                          amount       NUMBER NOT NULL CHECK (amount >= 0),
                          customer_id  NUMBER REFERENCES customers(id),
                          user_id      NUMBER REFERENCES users(id),
                          payment_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE SEQUENCE payment_seq START WITH 1 INCREMENT BY 1;

-- ساخت ویو برای گزارش سفارش‌ها
CREATE OR REPLACE VIEW ORDER_REPORT AS
SELECT
    o.id                   AS order_id,
    o.order_serial         AS order_serial,
    o.order_time           AS order_time,
    o.order_type           AS order_type,
    o.discount             AS discount,
    o.pure_amount          AS pure_amount,

    c.id                   AS customer_id,
    c.name                 AS customer_name,
    c.family               AS customer_family,
    c.phone_number         AS customer_phone,

    u.id                   AS user_id,
    u.name                 AS user_name,
    u.family               AS user_family,
    u.username             AS user_username,

    NVL((SELECT SUM(oi.quantity) FROM order_items oi WHERE oi.order_id = o.id), 0) AS total_quantity,
    NVL((SELECT SUM(oi.quantity * oi.price) FROM order_items oi WHERE oi.order_id = o.id), 0) AS total_price

FROM orders o
         LEFT JOIN customers c ON o.customer_id = c.id
         LEFT JOIN users u     ON o.user_id = u.id;
