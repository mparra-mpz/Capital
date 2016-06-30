
--
-- Current User: dev.
--

GRANT USAGE ON *.* TO 'dev'@'localhost';
DROP USER 'dev'@'localhost';
CREATE USER 'dev'@'localhost' IDENTIFIED BY 'dev123';

--
-- Current Database: `fund_dev`
--

DROP DATABASE /*!32312 IF EXISTS*/ `fund_dev`;
CREATE DATABASE /*!32312 IF NOT EXISTS*/ `fund_dev` /*!40100 DEFAULT CHARACTER SET latin1 */;

--
-- Assign all privileges on fund_dev database to dev user.
--

GRANT ALL PRIVILEGES ON fund_dev.* TO 'dev'@'localhost';
FLUSH PRIVILEGES;
COMMIT;

USE `fund_dev`;

--
-- Table structure for table `fund_type`
--

DROP TABLE IF EXISTS `fund_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fund_type` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fund_type`
--

LOCK TABLES `fund_type` WRITE;
/*!40000 ALTER TABLE `fund_type` DISABLE KEYS */;
INSERT INTO `fund_type` VALUES (1,'Deuda < 90 días'),(2,'Deuda < 365 días'),(3,'Deuda > 365 días'),(4,'Accionario'),(5,'Accionario'),(6,'Balanceado'),(7,'Estructurados'),(8,'Inversionistas Calificados');
/*!40000 ALTER TABLE `fund_type` ENABLE KEYS */;
UNLOCK TABLES;
