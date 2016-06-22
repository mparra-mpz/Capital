
--
-- Current User: dev.
--

DROP USER 'dev'@'localhost';
CREATE USER 'dev'@'localhost' IDENTIFIED BY 'dev123';

--
-- Current Database: `FUND_DEV`
--

DROP DATABASE /*!32312 IF EXISTS*/ `FUND_DEV`;
CREATE DATABASE /*!32312 IF NOT EXISTS*/ `FUND_DEV` /*!40100 DEFAULT CHARACTER SET latin1 */;

--
-- Assign all privileges on FUND_DEV database to dev user.
--

GRANT ALL PRIVILEGES ON FUND_DEV.* TO 'dev'@'localhost';
FLUSH PRIVILEGES;
COMMIT;

USE `FUND_DEV`;

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
