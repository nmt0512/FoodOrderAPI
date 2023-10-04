USE FoodStore

ALTER TABLE DBUser 
ALTER COLUMN Role int NOT NULL

INSERT INTO Category(Code, Name) VALUES('Thuc-uong', N'Thức uống')

UPDATE Category SET Code = 'thuc-uong' WHERE Id = 2

SELECT * FROM Category

INSERT INTO Product(Code, Name, Quantity, UnitPrice, CategoryId) 
VALUES('mi-tron-indome-1-goi-full-topping', N'Mì trộn indome 1 gói full toping', 100, 20000, 1)

INSERT INTO Product(Code, Name, Quantity, UnitPrice, CategoryId) 
VALUES('capuchino', 'Capuchino', 15, 55000, 2)

SELECT * FROM Product

UPDATE Product SET Quantity = 3000 WHERE Id = 3

SELECT * FROM Image

UPDATE Image SET Link = 'https://anchay.vn/wp-content/uploads/2021/11/3.jpg' WHERE ProductId = 7

INSERT INTO Image(Link, ProductId) VALUES('https://bonjourcoffee.vn/blog/wp-content/uploads/2020/01/capuchino.jpg', 6)

SELECT * FROM BillItem

SELECT * FROM Bill WHERE Time = (SELECT MIN(Time) FROM Bill)

DELETE FROM Bill WHERE Id = 10

DELETE FROM BillItem WHERE Id = 11

UPDATE Bill SET Status = 1

DELETE FROM BillItem WHERE BillId IN (SELECT Id FROM Bill WHERE Status IN (1,4))

DELETE FROM Bill WHERE Status IN (1,4)

SELECT * FROM Promotion

DELETE FROM Promotion

INSERT INTO Promotion(Active, Percentage, Quantity, ApplyingPrice, Image) VALUES(1, 70, 10, 700000, 'https://www.citypng.com/public/uploads/preview/hd-70-discount-off-sale-red-badge-transparent-png-316321015478lxh0vkvec.png')

SELECT * FROM Promotion WHERE ApplyingPrice < 300000

SELECT * FROM DBUser

UPDATE DBUser SET Phone = '0362764805' WHERE Id = 23

INSERT INTO DBUser(Username, Password, Fullname, Role, Email, Gender) VALUES('shipper5', 
'$2a$12$VHBb4mLxAXKlHOkK6TT9guU0MgD47eoWG8JTMMRQm6S1WArh6LHoG', 'Shipper5', 2, 'shipper5@gmail.com', 0)



ALTER TABLE DBUser
ALTER COLUMN Gender bit null

UPDATE Product SET Quantity = 600

SELECT * FROM Product

INSERT INTO Product(Code, Name, Quantity, UnitPrice, CategoryId) 
VALUES('matcha-latte', N'Matcha latte', 800, 50000, 2)

INSERT INTO Image(Link, ProductId) VALUES('https://beptruong.edu.vn/wp-content/uploads/2016/02/sinh-to-xoai-sua-tuoi.jpg', 17)

SELECT YEAR(Time) AS Time, SUM(TotalPrice) AS TotalRevenue FROM Bill WHERE Status = 2 GROUP BY YEAR(Time)

DELETE FROM BillItem
DELETE FROM Bill

SELECT * FROM BillItem

SELECT * FROM Bill

INSERT INTO Bill(Time, TotalPrice, UserId, Status) 
VALUES('2023-06-12 06:46:35.2430000', 210000, 6, 2)

SELECT DISTINCT YEAR(Time) FROM Bill ORDER BY YEAR(Time) ASC

SELECT FORMAT(Time, 'dd/MM/yyyy') AS Time, SUM(TotalPrice) AS Revenue
FROM Bill WHERE Status = 2 AND FORMAT(Time, 'dd/MM/yyyy') = '01/06/2023'
GROUP BY FORMAT(Time, 'dd/MM/yyyy')

INSERT INTO Banner(Link) VALUES('https://res.cloudinary.com/dtcdff7yy/image/upload/v1685939047/food/7_bmqlgi.jpg')

SELECT * FROM Banner

SELECT * FROM DBUser
SELECT * FROM Promotion

INSERT INTO DBUserPromotion VALUES(6,4)

SELECT * FROM DBUserPromotion

SELECT * FROM Bill ORDER BY Time ASC

UPDATE DBUser SET Phone = '0000000000' WHERE Username = 'thieunm'

UPDATE DBUser SET Username = 'thieunm' WHERE Username = 'admin123'

UPDATE Bill SET StaffName = N'Lê Đức Thọ'

ALTER TABLE Bill 
DROP COLUMN StaffName

SELECT * FROM DBUserPromotion

INSERT INTO DBUserPromotion VALUES(15, 3)

UPDATE Bill SET Status = 3 WHERE Id = 195

UPDATE Bill SET StaffId = (SELECT Id FROM DBUser WHERE Bill.StaffName = DBUser.Fullname)

SELECT * FROM StaffTracking

UPDATE StaffTracking SET Revenue = 210000 WHERE StaffId IN (26, 27)

SELECT DISTINCT(FORMAT(LoginTime, 'MM/yyyy')) AS Time 
FROM StaffTracking

DELETE FROM StaffTracking

DROP TABLE StaffTracking

SELECT MONTH(Time) AS Time, SUM(TotalPrice) AS Revenue 
FROM Bill WHERE Status = 2 AND YEAR(Time) = 2023
GROUP BY MONTH(Time)

SELECT st.StaffId AS Id, u.Fullname, SUM(st.Revenue) AS TotalRevenue, SUM(st.WorkingDuration) AS TotalDuration 
FROM DBUser u JOIN StaffTracking st ON u.Id = st.StaffId 
WHERE FORMAT(st.LoginTime, 'MM/yyyy') = '09/2023' 
GROUP BY st.StaffId, u.Fullname

UPDATE Product SET Quantity = 1000 WHERE Id = 2

DELETE FROM StaffTracking

SELECT * FROM PendingPrepaidBill

SELECT * FROM PendingPrepaidBillItem 

DELETE FROM PendingPrepaidBill WHERE Id != 40

DELETE FROM PendingPrepaidBillItem WHERE PendingPrepaidBillId != 40

CREATE TRIGGER Trigger_UpdateOnlyOneTime_Shipper 
ON PendingPrepaidBill 
FOR UPDATE
AS
BEGIN
    IF EXISTS (
        SELECT 1 
		FROM deleted 
		WHERE ShipperId != NULL
    )
    BEGIN
        RAISERROR('Cannot update when "Shipper" is not null', 16, 1);
        ROLLBACK TRANSACTION;
        RETURN;
    END;
END;


SELECT * FROM FirebaseUserDevice

DELETE FROM FirebaseUserDevice WHERE Id = 6

