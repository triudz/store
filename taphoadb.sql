-- MySQL dump 10.13  Distrib 8.0.44, for Win64 (x86_64)
--
-- Host: localhost    Database: taphoadb
-- ------------------------------------------------------
-- Server version	8.0.44

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `description` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,'Đồ uống','Các loại nước ngọt, bia, trà'),(2,'Bánh kẹo','Bánh quy, kẹo mút, socola'),(3,'Gia vị','Mắm, muối, mì chính, dầu ăn'),(4,'Đồ ăn vặt','Bim bim, hạt dưa, khô gà'),(5,'Thực phẩm khô','Mì tôm, phở gói, bún khô'),(6,'Sữa','Sữa tươi, sữa chua, sữa đặc'),(7,'Đồ hộp','Cá hộp, thịt hộp, pate'),(8,'Hóa mỹ phẩm','Dầu gội, sữa tắm, xà phòng'),(9,'Đồ gia dụng','Chổi, túi rác, giấy vệ sinh'),(10,'Rau củ quả','Rau xanh, trái cây tươi');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orderdetails`
--

DROP TABLE IF EXISTS `orderdetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orderdetails` (
  `order_id` int NOT NULL,
  `product_id` int NOT NULL,
  `quantity` int NOT NULL,
  `unit_price` decimal(10,2) NOT NULL,
  PRIMARY KEY (`order_id`,`product_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `orderdetails_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  CONSTRAINT `orderdetails_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orderdetails`
--

LOCK TABLES `orderdetails` WRITE;
/*!40000 ALTER TABLE `orderdetails` DISABLE KEYS */;
INSERT INTO `orderdetails` VALUES (1,1,10,10000.00),(2,2,1,45000.00),(3,8,2,60000.00),(4,5,5,4000.00),(5,10,1,80000.00),(6,3,1,35000.00),(7,9,5,30000.00),(8,6,1,8000.00),(9,8,2,60000.00),(9,10,1,80000.00),(10,8,1,60000.00);
/*!40000 ALTER TABLE `orderdetails` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `voucher_id` int DEFAULT NULL,
  `order_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `total_amount` decimal(10,2) NOT NULL,
  `status` varchar(50) DEFAULT 'PENDING',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `voucher_id` (`voucher_id`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`voucher_id`) REFERENCES `vouchers` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,3,1,'2026-03-20 08:30:00',90000.00,'COMPLETED'),(2,4,NULL,'2026-03-21 09:15:00',45000.00,'COMPLETED'),(3,5,2,'2026-03-21 14:20:00',100000.00,'SHIPPING'),(4,6,NULL,'2026-03-22 10:00:00',20000.00,'PENDING'),(5,7,4,'2026-03-22 16:45:00',65000.00,'COMPLETED'),(6,8,NULL,'2026-03-23 11:10:00',35000.00,'SHIPPING'),(7,9,5,'2026-03-23 18:00:00',120000.00,'PENDING'),(8,10,NULL,'2026-03-24 07:20:00',8000.00,'PENDING'),(9,3,3,'2026-03-24 08:00:00',150000.00,'COMPLETED'),(10,4,NULL,'2026-03-24 08:15:00',60000.00,'PENDING');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `id` int NOT NULL AUTO_INCREMENT,
  `category_id` int DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `stock_quantity` int NOT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `description` text,
  PRIMARY KEY (`id`),
  KEY `category_id` (`category_id`),
  CONSTRAINT `products_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,1,'Coca Cola 320ml',10000.00,100,'coca.jpg','Nước ngọt có gas giải khát'),(2,2,'Bánh quy Cosy',45000.00,50,'cosy.jpg','Bánh quy bơ lạt giòn rụm'),(3,3,'Nước mắm Nam Ngư',35000.00,80,'namngu.jpg','Nước mắm cá cơm đậm đà'),(4,4,'Bim bim Oishi',5000.00,200,'oishi.jpg','Bim bim vị tôm cay'),(5,5,'Mì Hảo Hảo chua cay',4000.00,500,'haohao.jpg','Mì gói quốc dân'),(6,6,'Sữa tươi Vinamilk 180ml',8000.00,150,'vinamilk.jpg','Sữa tươi 100% có đường'),(7,7,'Cá nục xốt cà chua',20000.00,60,'canuc.jpg','Cá hộp 3 Cô Gái'),(8,8,'Dầu gội Clear 170g',60000.00,40,'clear.jpg','Dầu gội sạch gàu mát lạnh'),(9,9,'Giấy vệ sinh Hà Nội',30000.00,100,'giayvs.jpg','Bịch 10 cuộn giấy lụa mềm'),(10,10,'Táo nhập khẩu',80000.00,30,'tao.jpg','Táo Gala ngọt giòn (1kg)');
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `full_name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phone` varchar(15) DEFAULT NULL,
  `address` text,
  `role` varchar(20) DEFAULT 'CUSTOMER',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Quản Trị Viên 1','admin1@gmail.com','123456','0901234567','Hà Nội','ADMIN'),(2,'Quản Trị Viên 2','admin2@gmail.com','123456','0912345678','Hồ Chí Minh','ADMIN'),(3,'Khách Hàng A','khacha@gmail.com','123456','0923456789','Hà Nội','CUSTOMER'),(4,'Khách Hàng B','khachb@gmail.com','123456','0934567890','Đà Nẵng','CUSTOMER'),(5,'Khách Hàng C','khachc@gmail.com','123456','0945678901','Hải Phòng','CUSTOMER'),(6,'Khách Hàng D','khachd@gmail.com','123456','0956789012','Cần Thơ','CUSTOMER'),(7,'Khách Hàng E','khache@gmail.com','123456','0967890123','Nha Trang','CUSTOMER'),(8,'Khách Hàng F','khachf@gmail.com','123456','0978901234','Huế','CUSTOMER'),(9,'Khách Hàng G','khachg@gmail.com','123456','0989012345','Bình Dương','CUSTOMER'),(10,'Khách Hàng H','khachh@gmail.com','123456','0990123456','Đồng Nai','CUSTOMER');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vouchers`
--

DROP TABLE IF EXISTS `vouchers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vouchers` (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(50) NOT NULL,
  `discount_amount` decimal(10,2) NOT NULL,
  `usage_limit` int NOT NULL,
  `expiry_date` date NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vouchers`
--

LOCK TABLES `vouchers` WRITE;
/*!40000 ALTER TABLE `vouchers` DISABLE KEYS */;
INSERT INTO `vouchers` VALUES (1,'GIAM10K',10000.00,100,'2026-12-31'),(2,'GIAM20K',20000.00,50,'2026-12-31'),(3,'GIAM50K',50000.00,20,'2026-12-31'),(4,'FREESHIP',15000.00,200,'2026-10-31'),(5,'HESOI',30000.00,30,'2026-08-15'),(6,'SIEUSALE',100000.00,5,'2026-11-11'),(7,'CUOITUAN',25000.00,40,'2026-06-30'),(8,'KHACHMOI',50000.00,100,'2026-12-31'),(9,'NAMMOI2027',40000.00,50,'2027-01-31'),(10,'TRIAN',15000.00,100,'2026-09-02');
/*!40000 ALTER TABLE `vouchers` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-03-24 20:58:57
