create database BekeryMngement;
use BekeryMngement;
create table user_info
(
id int primary key ,
uname varchar(20) not null,
pass varchar(20) not null,
roleIs varchar(20) not null
);
insert into user_info values(1,'sid',123,'admin');
insert into user_info value(2,'user',123,'cus');

ALTER TABLE `bekerymngement`.`user_info` 
DROP PRIMARY KEY,
ADD PRIMARY KEY (`uname`, `pass`);
;

CREATE TABLE `bekerymngement`.`order_info` (
  `oId` INT NOT NULL,
  `costTotal` DOUBLE NULL,
  `user_id` VARCHAR(45) NULL,
  PRIMARY KEY (`oId`),
  INDEX `fk_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `bekerymngement`.`user_info` (`uname`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);


create table product(
pId int primary key auto_increment,
pName varchar(30),
pPrice double ,
aValQty int 
);

CREATE TABLE `bekerymngement`.`orders_info` (
  `Oid` INT NULL,
  `pId` INT NULL,
  `pQty` INT NOT NULL,
  `cost` DOUBLE NOT NULL,
  INDEX `fk_idx` (`pId` ASC) VISIBLE,
  INDEX `k_idx` (`Oid` ASC) VISIBLE,
  CONSTRAINT `k`
    FOREIGN KEY (`Oid`)
    REFERENCES `bekerymngement`.`order_info` (`Oid`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fko`
    FOREIGN KEY (`pId`)
    REFERENCES `bekerymngement`.`product` (`pId`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);
    
    -- Insert data into the product table
INSERT INTO product (pId, pName, pPrice, aValQty) VALUES (1, 'Product1', 10.0, 100);
INSERT INTO product (pId, pName, pPrice, aValQty) VALUES (3, 'Product3', 123.0, 80);

-- Insert data into the order_info table
INSERT INTO order_info (Oid, costTotal, user_id) VALUES (1, 0.0, 'user');
INSERT INTO order_info (Oid,  user_id) VALUES (3, 'user');

-- Insert data into the orders_info table
INSERT INTO orders_info (Oid, pId, pQty, cost) VALUES (1, 1, 2, 20.0);
INSERT INTO orders_info (Oid, pId, pQty, cost) VALUES (3, 1, 3, 36.0);
SELECT * FROM bekerymngement.order_info;
SELECT * FROM bekerymngement.orders_info;
select * from orders_info os inner join order_info o on os.Oid = o.oId where o.user_id='user' LIMIT 0, 50000;
SELECT * FROM bekerymngement.product;
select pQty from orders_info where Oid=(select oId from order_info where user_id='user' and oId=2) and pId=(select pId from product where pName='Product2');
select aValQty from product where pId=(select pId from product where pName='Product2');
select pId from product where pName='Product2';
 DELETE FROM orders_info WHERE Oid = 15 AND pId = 2 limit 1;
 
 ALTER TABLE orders_info
ADD COLUMN timestamp_column TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;


DELIMITER //

CREATE PROCEDURE cancelOrder(IN orderId INT, IN productName VARCHAR(20), IN userRole VARCHAR(20))
BEGIN
    DECLARE totalCost DOUBLE;
    DECLARE qty2Add INT;
    DECLARE oid4Qty INT;
    DECLARE upPid INT;
	
    -- Start a transaction
    START TRANSACTION;

    -- Get the corresponding oId for the user and orderId
    SELECT oId INTO oid4Qty FROM order_info WHERE user_id = userRole AND oId = orderId LIMIT 1;

    -- Get the quantity to add back to the product's available quantity
    SELECT pQty INTO qty2Add FROM orders_info WHERE Oid = orderId AND pId = (SELECT pId FROM product WHERE pName = productName) LIMIT 1;

    -- Get the productId for the product name
    SELECT pId INTO upPid FROM product WHERE pName = productName LIMIT 1;

    -- Update the available quantity in the product table
    UPDATE product SET aValQty = aValQty + qty2Add WHERE pId = upPid;

    -- Delete the corresponding row from orders_info based on the timestamp_column (oldest first)
    DELETE FROM orders_info WHERE Oid = oid4Qty AND pId = upPid ORDER BY timestamp_column LIMIT 1;

    -- Recalculate the total cost for the order
    SELECT COALESCE(SUM(cost), 0) INTO totalCost FROM orders_info WHERE Oid = orderId;

    -- Update the total cost in the order_info table
    UPDATE order_info SET costTotal = totalCost WHERE oId = orderId;

	-- if no order in the orders_info table then delete the oid as well
    delete from order_info where costTotal=0 and oId=orderId;
    
    -- Commit the transaction
    COMMIT;
END //

DELIMITER ;

select  o.oId ,o.user_id,
ors.Oid,ors.pId,ors.pQty,ors.cost,
p.pName from order_info o inner join orders_info ors on o.oId=ors.Oid inner join product p on ors.pId=p.pId where o.user_id='user' order by ors.Oid;
