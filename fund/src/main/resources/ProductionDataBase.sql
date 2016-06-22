--
-- Current User: prod.
--

DROP USER 'prod'@'localhost';
CREATE USER 'prod'@'localhost' IDENTIFIED BY 'prod123';

--
-- Current Database: `FUND`
--

DROP DATABASE /*!32312 IF EXISTS*/ `FUND`;
CREATE DATABASE /*!32312 IF NOT EXISTS*/ `FUND` /*!40100 DEFAULT CHARACTER SET latin1 */;

--
-- Assign all privileges on FUND database to prod user.
--

GRANT ALL PRIVILEGES ON FUND.* TO 'prod'@'localhost';
FLUSH PRIVILEGES;
COMMIT;

USE `FUND`;

--
-- Table structure for table `FundType`
--

DROP TABLE IF EXISTS `FundType`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `FundType` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `FundType`
--

LOCK TABLES `FundType` WRITE;
/*!40000 ALTER TABLE `FundType` DISABLE KEYS */;
INSERT INTO `FundType` VALUES (1,'Deuda < 90 días'),(2,'Deuda < 365 días'),(3,'Deuda > 365 días'),(4,'Accionario'),(5,'Accionario'),(6,'Balanceado'),(7,'Estructurados'),(8,'Inversionistas Calificados');
/*!40000 ALTER TABLE `FundType` ENABLE KEYS */;
UNLOCK TABLES;
