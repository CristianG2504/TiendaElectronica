CREATE DATABASE  IF NOT EXISTS `tienda_electronica` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `tienda_electronica`;
-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: tienda_electronica
-- ------------------------------------------------------
-- Server version	8.4.3

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
-- Table structure for table `categoria`
--

DROP TABLE IF EXISTS `categoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categoria` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categoria`
--

LOCK TABLES `categoria` WRITE;
/*!40000 ALTER TABLE `categoria` DISABLE KEYS */;
INSERT INTO `categoria` VALUES (1,'Laptops','Computadoras portátiles para oficina, estudio y gaming'),(2,'Smartphones','Teléfonos inteligentes de distintas marcas'),(3,'Televisore','TV LED, OLED, QLED y Smart TV'),(4,'Audio','Bocinas, audífonos, parlantes y equipos de sonido'),(5,'Componentes de PC','Tarjetas madre, RAM, procesadores y más'),(6,'Accesorios','Cables, cargadores, soportes, adaptadores y más'),(7,'Electrodomésticos','Artículos electrónicos para el hogar'),(8,'Redes y Comunicación','Routers, repetidores, switches y cables de red'),(9,'Tablets','Tablets Android y iPad'),(10,'Gaming','Consolas, controles y accesorios gamer');
/*!40000 ALTER TABLE `categoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cliente` (
  `cedula` varchar(25) NOT NULL,
  `nombre` varchar(150) NOT NULL,
  `direccion` varchar(255) DEFAULT NULL,
  `telefono` varchar(30) DEFAULT NULL,
  `correo` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`cedula`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES ('123456789','Juan Pérez','Calle 1, San José','88880000','juan.perez@email.com'),('321654987','Luis Fernández','Barrio 5, Puntarenas','84440000','luis.fernandez@email.com'),('456123789','Carlos Sánchez','Boulevard 3, Alajuela','86660000','carlos.sanchez@email.com'),('789456123','Ana Gómez','Ruta 4, Cartago','85550000','ana.gomez@email.com'),('987654321','María López','Avenida 2, Heredia','87770000','maria.lopez@email.com');
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `producto`
--

DROP TABLE IF EXISTS `producto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `producto` (
  `codigo` varchar(50) NOT NULL,
  `nombre` varchar(150) NOT NULL,
  `id_categoria` int DEFAULT NULL,
  `precio` decimal(12,2) NOT NULL,
  `cantidad` int NOT NULL DEFAULT '0',
  `proveedor_id` int DEFAULT NULL,
  `fecha_registro` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`codigo`),
  KEY `id_categoria` (`id_categoria`),
  KEY `proveedor_id` (`proveedor_id`),
  CONSTRAINT `producto_ibfk_1` FOREIGN KEY (`id_categoria`) REFERENCES `categoria` (`id`),
  CONSTRAINT `producto_ibfk_2` FOREIGN KEY (`proveedor_id`) REFERENCES `proveedor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `producto`
--

LOCK TABLES `producto` WRITE;
/*!40000 ALTER TABLE `producto` DISABLE KEYS */;
INSERT INTO `producto` VALUES ('P001','Laptop Lenovo IdeaPad 5',2,350000.00,10,1,'2025-12-10 21:28:53'),('P002','Mouse Logitech M170',1,8500.00,50,2,'2025-12-10 21:28:53'),('P003','Teclado Redragon Kumara K552',1,24000.00,20,3,'2025-12-10 21:28:53'),('P004','Monitor Samsung 24\"',1,95000.00,8,1,'2025-12-10 21:28:53'),('P005','Silla Ejecutiva Ergonómica',2,120000.00,5,2,'2025-12-10 21:28:53'),('P006','Mesa de Oficina Grande',2,180000.00,3,2,'2025-12-10 21:28:53'),('P007','USB 64GB Kingston',1,6500.00,100,1,'2025-12-10 21:28:53'),('P008','Impresora Epson Ecotank L3150',1,155000.00,4,3,'2025-12-10 21:28:53'),('P009','Audífonos Sony WH-CH510',1,32000.00,12,1,'2025-12-10 21:28:53'),('P010','Cámara Web Logitech C270',1,17000.00,15,2,'2025-12-10 21:28:53');
/*!40000 ALTER TABLE `producto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `proveedor`
--

DROP TABLE IF EXISTS `proveedor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `proveedor` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150) NOT NULL,
  `contacto` varchar(100) DEFAULT NULL,
  `direccion` varchar(255) DEFAULT NULL,
  `telefono` varchar(30) DEFAULT NULL,
  `correo` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `proveedor`
--

LOCK TABLES `proveedor` WRITE;
/*!40000 ALTER TABLE `proveedor` DISABLE KEYS */;
INSERT INTO `proveedor` VALUES (1,'ElectroMundo S.A.','Carlos Rodríguez','San José, Avenida Central','2222-3344','contacto@electromundo.cr'),(2,'TecnoProveedores','María Sánchez','Heredia, Centro','2266-7788','info@tecnoproveedores.com'),(3,'Distribuidora Digital','Jorge Pineda','Cartago, Paraíso','2555-1122','ventas@ddigital.cr'),(4,'Electro Solutions','Fernanda Vega','Alajuela, El Coyol','2444-5566','soluciones@electrosolutions.com'),(5,'Tecnología Global','Luis Martínez','Puntarenas, Barranca','2666-9900','contacto@tecnologiaglobal.com'),(6,'Proveeduría Tech','Andrea López','Guanacaste, Liberia','2680-3344','ventas@proveeduriatech.cr'),(7,'Mundo Electrónico','Ricardo Araya','San José, Escazú','2288-4477','gerencia@mundo-electronico.cr'),(8,'Componentes del Norte','Sara Vargas','Guanacaste, Nicoya','2685-2211','contacto@cdnorte.cr'),(9,'Tecnologicentro','David Jiménez','Limón, Centro','2790-1133','servicio@tecnologicentro.cr'),(10,'Suministros Eléctricos CR','Paola Mora','San Carlos, Quesada','2460-7788','ventas@suministroscr.com');
/*!40000 ALTER TABLE `proveedor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password_hash` varchar(255) NOT NULL,
  `nombre` varchar(150) DEFAULT NULL,
  `rol` enum('ADMIN','VENDEDOR') NOT NULL,
  `activo` tinyint(1) DEFAULT '1',
  `fecha_creacion` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (2,'cami','3a48d3ed7d6c02fb57e559562865cc40e58aeaa1920bbf6017cda684703ba73f',NULL,'ADMIN',1,'2025-12-11 03:42:20');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `venta`
--

DROP TABLE IF EXISTS `venta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `venta` (
  `id` int NOT NULL AUTO_INCREMENT,
  `fecha` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `cedula_cliente` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `cedula_cliente` (`cedula_cliente`),
  CONSTRAINT `venta_ibfk_1` FOREIGN KEY (`cedula_cliente`) REFERENCES `cliente` (`cedula`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `venta`
--

LOCK TABLES `venta` WRITE;
/*!40000 ALTER TABLE `venta` DISABLE KEYS */;
INSERT INTO `venta` VALUES (1,'2025-12-11 04:59:01','456123789');
/*!40000 ALTER TABLE `venta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `venta_detalle`
--

DROP TABLE IF EXISTS `venta_detalle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `venta_detalle` (
  `id` int NOT NULL AUTO_INCREMENT,
  `venta_id` int NOT NULL,
  `producto_codigo` varchar(50) NOT NULL,
  `cantidad` int NOT NULL,
  `precio_unitario` decimal(12,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `venta_id` (`venta_id`),
  KEY `producto_codigo` (`producto_codigo`),
  CONSTRAINT `venta_detalle_ibfk_1` FOREIGN KEY (`venta_id`) REFERENCES `venta` (`id`) ON DELETE CASCADE,
  CONSTRAINT `venta_detalle_ibfk_2` FOREIGN KEY (`producto_codigo`) REFERENCES `producto` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `venta_detalle`
--

LOCK TABLES `venta_detalle` WRITE;
/*!40000 ALTER TABLE `venta_detalle` DISABLE KEYS */;
INSERT INTO `venta_detalle` VALUES (1,1,'P003',2,24000.00);
/*!40000 ALTER TABLE `venta_detalle` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'tienda_electronica'
--

--
-- Dumping routines for database 'tienda_electronica'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-12-11  0:00:57
