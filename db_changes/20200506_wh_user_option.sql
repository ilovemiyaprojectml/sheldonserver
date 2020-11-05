-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.1.35-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win32
-- HeidiSQL Version:             9.5.0.5196
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping structure for table fis_waho_db.wh_user_options
DROP TABLE IF EXISTS `wh_user_options`;
CREATE TABLE IF NOT EXISTS `wh_user_options` (
  `user_opt_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL DEFAULT '0',
  `option_code` varchar(50) DEFAULT NULL,
  `option_value` varchar(50) DEFAULT NULL,
  `date_created` datetime DEFAULT NULL,
  `date_last_updated` datetime DEFAULT NULL,
  `created_by` int(11) NOT NULL DEFAULT '0',
  `last_updated_by` int(11) NOT NULL DEFAULT '0',
  `is_active` char(1) NOT NULL DEFAULT 'Y',
  PRIMARY KEY (`user_opt_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- Dumping data for table fis_waho_db.wh_user_options: ~1 rows (approximately)
/*!40000 ALTER TABLE `wh_user_options` DISABLE KEYS */;
/*!40000 ALTER TABLE `wh_user_options` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;


DELETE FROM wh_sys_codes WHERE sys_code = 'ALERT_SOUND_ENABLE';

INSERT INTO wh_sys_codes (sys_code_category, sys_code_name, sys_code,value1)
VALUES ('USER_OPTIONS', 'Alert Sound Enable', 'ALERT_SOUND_ENABLE','Y');
