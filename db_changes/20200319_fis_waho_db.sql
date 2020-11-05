-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.4.11-MariaDB-log - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             10.3.0.5771
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping structure for table fis_waho_db.wh_idp_users
DROP TABLE IF EXISTS `wh_idp_users`;
CREATE TABLE IF NOT EXISTS `wh_idp_users` (
  `temp_acc_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT 0,
  `user_eid` varchar(50) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `resetpassword` varchar(255) DEFAULT NULL,
  `otp_code` varchar(255) DEFAULT NULL,
  `date_created` datetime DEFAULT NULL,
  `date_last_updated` datetime DEFAULT NULL,
  `created_by` int(11) DEFAULT 0,
  `last_updated_by` int(11) DEFAULT 0,
  `is_active` char(1) DEFAULT 'Y',
  PRIMARY KEY (`temp_acc_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

-- Dumping data for table fis_waho_db.wh_idp_users: ~7 rows (approximately)
/*!40000 ALTER TABLE `wh_idp_users` DISABLE KEYS */;
INSERT INTO `wh_idp_users` (`temp_acc_id`, `user_id`, `user_eid`, `password`, `resetpassword`, `otp_code`, `date_created`, `date_last_updated`, `created_by`, `last_updated_by`, `is_active`) VALUES
	(1, 1, 'e5555622', 'art123', NULL, '1234', '2018-10-23 19:02:42', '2018-10-23 19:02:43', 1, 1, 'Y'),
	(2, 4, 'e5555995', 'hi123', NULL, '1234', '2018-10-23 19:04:50', '2018-10-23 19:04:56', 1, 1, 'Y'),
	(3, 5, 'e5555994', 'hi123', NULL, '1234', '2018-10-23 19:05:16', '2018-10-23 19:05:17', 1, 1, 'Y'),
	(4, 6, 'e1234567', 'hi123', NULL, '1234', '2018-10-23 19:08:55', '2018-10-23 19:08:55', 1, 1, 'Y'),
	(5, 10, 'e1111222', 'hi123', NULL, '1234', '2018-12-20 14:44:24', '2018-12-20 14:44:24', 1, 1, 'Y'),
	(6, 36, 'e1110000', 'hi123', NULL, '1234', '2018-12-20 15:18:40', '2018-12-20 15:18:40', 1, 1, 'Y'),
	(7, 8, 'e5555626', 'hi123', NULL, '1234', '2018-12-20 15:21:23', '2018-12-20 15:21:23', 1, 1, 'Y');
/*!40000 ALTER TABLE `wh_idp_users` ENABLE KEYS */;

-- Dumping structure for table fis_waho_db.wh_notification_queue
DROP TABLE IF EXISTS `wh_notification_queue`;
CREATE TABLE IF NOT EXISTS `wh_notification_queue` (
  `notif_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `notif_sched` time NOT NULL,
  PRIMARY KEY (`notif_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19911 DEFAULT CHARSET=latin1;

-- Dumping data for table fis_waho_db.wh_notification_queue: ~9 rows (approximately)
/*!40000 ALTER TABLE `wh_notification_queue` DISABLE KEYS */;
INSERT INTO `wh_notification_queue` (`notif_id`, `user_id`, `notif_sched`) VALUES
	(19845, 1, '14:52:00'),
	(19846, 1, '14:53:00'),
	(19847, 1, '14:56:00'),
	(19853, 1, '15:26:00'),
	(19873, 1, '16:21:00'),
	(19875, 1, '16:23:00'),
	(19876, 1, '09:08:00'),
	(19878, 1, '09:07:00'),
	(19909, 1, '10:04:00');
/*!40000 ALTER TABLE `wh_notification_queue` ENABLE KEYS */;

-- Dumping structure for table fis_waho_db.wh_shift_sched_times
DROP TABLE IF EXISTS `wh_shift_sched_times`;
CREATE TABLE IF NOT EXISTS `wh_shift_sched_times` (
  `sched_time_id` int(11) NOT NULL AUTO_INCREMENT,
  `shift_sched_id` int(11) NOT NULL DEFAULT 0,
  `sched_time` datetime DEFAULT NULL,
  `sched_time_status` varchar(50) DEFAULT 'PENDING',
  `remarks` varchar(4000) DEFAULT NULL,
  `date_created` datetime DEFAULT NULL,
  `date_last_updated` datetime DEFAULT NULL,
  `created_by` int(11) DEFAULT 0,
  `last_updated_by` int(11) DEFAULT 0,
  `is_active` char(1) DEFAULT 'Y',
  PRIMARY KEY (`sched_time_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1549 DEFAULT CHARSET=latin1;

-- Dumping data for table fis_waho_db.wh_shift_sched_times: ~73 rows (approximately)
/*!40000 ALTER TABLE `wh_shift_sched_times` DISABLE KEYS */;
INSERT INTO `wh_shift_sched_times` (`sched_time_id`, `shift_sched_id`, `sched_time`, `sched_time_status`, `remarks`, `date_created`, `date_last_updated`, `created_by`, `last_updated_by`, `is_active`) VALUES
	(1476, 144, '2020-03-05 13:45:01', 'STARTED', NULL, '2020-03-05 13:45:01', '2020-03-05 13:45:01', 1, 1, 'Y'),
	(1477, 144, '2020-03-05 14:12:00', 'MISSED', '1', '2020-03-05 14:12:00', '2020-03-05 14:26:38', 1, 1, 'Y'),
	(1478, 144, '2020-03-05 14:13:00', 'MISSED', '2', '2020-03-05 14:13:00', '2020-03-05 14:26:39', 1, 1, 'Y'),
	(1479, 144, '2020-03-05 14:22:00', 'MISSED', '3', '2020-03-05 14:22:00', '2020-03-05 14:26:40', 1, 1, 'Y'),
	(1480, 144, '2020-03-05 14:28:00', 'MISSED', '233', '2020-03-05 14:28:00', '2020-03-05 14:31:41', 1, 1, 'Y'),
	(1481, 144, '2020-03-05 14:32:00', 'MISSED', 'werwer', '2020-03-05 14:32:00', '2020-03-05 14:38:55', 1, 1, 'Y'),
	(1482, 144, '2020-03-05 14:35:00', 'MISSED', 'fghfghfgh', '2020-03-05 14:35:00', '2020-03-05 14:38:56', 1, 1, 'Y'),
	(1483, 144, '2020-03-05 14:37:00', 'CONFIRMED', NULL, '2020-03-05 14:37:00', '2020-03-05 14:38:48', 1, 1, 'Y'),
	(1484, 144, '2020-03-05 14:38:00', 'CONFIRMED', NULL, '2020-03-05 14:38:00', '2020-03-05 14:38:49', 1, 1, 'Y'),
	(1485, 144, '2020-03-05 14:40:00', 'CONFIRMED', NULL, '2020-03-05 14:40:00', '2020-03-05 14:41:24', 1, 1, 'Y'),
	(1486, 144, '2020-03-05 14:41:54', 'ENDED', NULL, '2020-03-05 14:41:54', '2020-03-05 14:41:54', 1, 1, 'Y'),
	(1487, 145, '2020-03-05 14:42:42', 'STARTED', NULL, '2020-03-05 14:42:42', '2020-03-05 14:42:42', 1, 1, 'Y'),
	(1488, 145, '2020-03-05 14:46:00', 'CONFIRMED', NULL, '2020-03-05 14:46:00', '2020-03-05 14:47:48', 1, 1, 'Y'),
	(1489, 145, '2020-03-05 14:49:00', 'CONFIRMED', NULL, '2020-03-05 14:49:00', '2020-03-05 14:50:54', 1, 1, 'Y'),
	(1490, 145, '2020-03-05 14:50:00', 'CONFIRMED', NULL, '2020-03-05 14:50:00', '2020-03-05 14:50:57', 1, 1, 'Y'),
	(1491, 145, '2020-03-10 15:13:00', 'MISSED', NULL, '2020-03-10 15:13:00', '2020-03-10 15:16:00', 1, 1, 'Y'),
	(1492, 145, '2020-03-10 15:17:00', 'CONFIRMED', NULL, '2020-03-10 15:17:00', '2020-03-10 15:17:05', 1, 1, 'Y'),
	(1493, 145, '2020-03-10 15:21:00', 'CONFIRMED', NULL, '2020-03-10 15:21:00', '2020-03-10 15:21:44', 1, 1, 'Y'),
	(1494, 145, '2020-03-10 15:22:00', 'CONFIRMED', NULL, '2020-03-10 15:22:00', '2020-03-10 15:44:54', 1, 1, 'Y'),
	(1495, 145, '2020-03-10 15:23:00', 'CONFIRMED', NULL, '2020-03-10 15:23:00', '2020-03-10 15:44:56', 1, 1, 'Y'),
	(1496, 145, '2020-03-10 15:47:00', 'MISSED', NULL, '2020-03-10 15:47:00', '2020-03-10 15:50:00', 1, 1, 'Y'),
	(1497, 145, '2020-03-10 15:49:00', 'MISSED', NULL, '2020-03-10 15:49:00', '2020-03-10 15:52:00', 1, 1, 'Y'),
	(1498, 145, '2020-03-10 15:49:00', 'MISSED', NULL, '2020-03-10 15:49:00', '2020-03-10 15:52:00', 1, 1, 'Y'),
	(1499, 145, '2020-03-10 15:52:00', 'MISSED', NULL, '2020-03-10 15:52:00', '2020-03-10 15:55:00', 1, 1, 'Y'),
	(1500, 145, '2020-03-10 15:53:00', 'CONFIRMED', NULL, '2020-03-10 15:53:00', '2020-03-10 15:55:11', 1, 1, 'Y'),
	(1501, 145, '2020-03-10 15:53:00', 'CONFIRMED', NULL, '2020-03-10 15:53:00', '2020-03-10 15:55:12', 1, 1, 'Y'),
	(1502, 145, '2020-03-10 15:56:00', 'CONFIRMED', NULL, '2020-03-10 15:56:00', '2020-03-10 15:56:07', 1, 1, 'Y'),
	(1503, 145, '2020-03-10 16:00:00', 'CONFIRMED', NULL, '2020-03-10 16:00:00', '2020-03-10 16:00:53', 1, 1, 'Y'),
	(1504, 145, '2020-03-10 16:01:00', 'MISSED', NULL, '2020-03-10 16:01:00', '2020-03-10 16:04:00', 1, 1, 'Y'),
	(1505, 145, '2020-03-10 16:02:00', 'MISSED', NULL, '2020-03-10 16:02:00', '2020-03-10 16:05:00', 1, 1, 'Y'),
	(1506, 145, '2020-03-10 16:04:00', 'MISSED', NULL, '2020-03-10 16:04:00', '2020-03-10 16:07:00', 1, 1, 'Y'),
	(1507, 145, '2020-03-10 16:05:00', 'MISSED', NULL, '2020-03-10 16:05:00', '2020-03-10 16:08:00', 1, 1, 'Y'),
	(1508, 145, '2020-03-10 16:06:00', 'MISSED', NULL, '2020-03-10 16:06:00', '2020-03-10 16:09:00', 1, 1, 'Y'),
	(1509, 145, '2020-03-10 16:08:00', 'MISSED', NULL, '2020-03-10 16:08:00', '2020-03-10 16:11:00', 1, 1, 'Y'),
	(1510, 145, '2020-03-10 16:09:00', 'MISSED', NULL, '2020-03-10 16:09:00', '2020-03-10 16:12:00', 1, 1, 'Y'),
	(1511, 145, '2020-03-10 16:11:00', 'MISSED', NULL, '2020-03-10 16:11:00', '2020-03-10 16:14:00', 1, 1, 'Y'),
	(1512, 145, '2020-03-10 16:16:00', 'MISSED', NULL, '2020-03-10 16:16:00', '2020-03-10 16:19:00', 1, 1, 'Y'),
	(1513, 145, '2020-03-10 16:16:00', 'MISSED', NULL, '2020-03-10 16:16:00', '2020-03-10 16:19:00', 1, 1, 'Y'),
	(1514, 145, '2020-03-10 16:19:00', 'CONFIRMED', NULL, '2020-03-10 16:19:00', '2020-03-10 16:20:28', 1, 1, 'Y'),
	(1515, 145, '2020-03-10 16:20:00', 'CONFIRMED', NULL, '2020-03-10 16:20:00', '2020-03-10 16:20:29', 1, 1, 'Y'),
	(1516, 145, '2020-03-11 09:10:00', 'MISSED', NULL, '2020-03-11 09:10:00', '2020-03-11 09:13:00', 1, 1, 'Y'),
	(1517, 145, '2020-03-11 09:10:00', 'MISSED', NULL, '2020-03-11 09:10:00', '2020-03-11 09:13:00', 1, 1, 'Y'),
	(1518, 145, '2020-03-11 09:11:00', 'CONFIRMED', NULL, '2020-03-11 09:11:00', '2020-03-11 09:13:40', 1, 1, 'Y'),
	(1519, 145, '2020-03-11 09:12:00', 'CONFIRMED', NULL, '2020-03-11 09:12:00', '2020-03-11 09:13:41', 1, 1, 'Y'),
	(1520, 145, '2020-03-11 09:13:00', 'CONFIRMED', NULL, '2020-03-11 09:13:00', '2020-03-11 09:13:43', 1, 1, 'Y'),
	(1521, 145, '2020-03-11 09:19:00', 'MISSED', NULL, '2020-03-11 09:19:00', '2020-03-11 09:22:00', 1, 1, 'Y'),
	(1522, 145, '2020-03-11 09:19:00', 'MISSED', NULL, '2020-03-11 09:19:00', '2020-03-11 09:22:00', 1, 1, 'Y'),
	(1523, 145, '2020-03-11 09:20:00', 'MISSED', NULL, '2020-03-11 09:20:00', '2020-03-11 09:23:00', 1, 1, 'Y'),
	(1524, 145, '2020-03-11 09:22:00', 'MISSED', NULL, '2020-03-11 09:22:00', '2020-03-11 09:25:00', 1, 1, 'Y'),
	(1525, 145, '2020-03-11 09:23:00', 'MISSED', NULL, '2020-03-11 09:23:00', '2020-03-11 09:26:00', 1, 1, 'Y'),
	(1526, 145, '2020-03-11 09:26:00', 'MISSED', NULL, '2020-03-11 09:26:00', '2020-03-11 09:29:00', 1, 1, 'Y'),
	(1527, 145, '2020-03-11 09:26:00', 'MISSED', NULL, '2020-03-11 09:26:00', '2020-03-11 09:29:00', 1, 1, 'Y'),
	(1528, 145, '2020-03-11 09:27:00', 'MISSED', NULL, '2020-03-11 09:27:00', '2020-03-11 09:30:00', 1, 1, 'Y'),
	(1529, 145, '2020-03-11 09:28:00', 'MISSED', NULL, '2020-03-11 09:28:00', '2020-03-11 09:31:00', 1, 1, 'Y'),
	(1530, 145, '2020-03-11 09:29:00', 'MISSED', NULL, '2020-03-11 09:29:00', '2020-03-11 09:32:00', 1, 1, 'Y'),
	(1531, 145, '2020-03-11 09:31:00', 'MISSED', NULL, '2020-03-11 09:31:00', '2020-03-11 09:34:00', 1, 1, 'Y'),
	(1532, 145, '2020-03-11 09:34:00', 'MISSED', NULL, '2020-03-11 09:34:00', '2020-03-11 09:37:00', 1, 1, 'Y'),
	(1533, 145, '2020-03-11 09:34:00', 'MISSED', NULL, '2020-03-11 09:34:00', '2020-03-11 09:37:00', 1, 1, 'Y'),
	(1534, 145, '2020-03-11 09:35:00', 'MISSED', NULL, '2020-03-11 09:35:00', '2020-03-11 09:38:00', 1, 1, 'Y'),
	(1535, 145, '2020-03-11 09:37:00', 'MISSED', NULL, '2020-03-11 09:37:00', '2020-03-11 09:40:00', 1, 1, 'Y'),
	(1536, 145, '2020-03-11 09:41:00', 'MISSED', NULL, '2020-03-11 09:41:00', '2020-03-11 09:44:00', 1, 1, 'Y'),
	(1537, 145, '2020-03-11 09:42:00', 'MISSED', NULL, '2020-03-11 09:42:00', '2020-03-11 09:45:00', 1, 1, 'Y'),
	(1538, 145, '2020-03-11 09:43:00', 'MISSED', NULL, '2020-03-11 09:43:00', '2020-03-11 09:46:00', 1, 1, 'Y'),
	(1539, 145, '2020-03-11 09:49:00', 'MISSED', NULL, '2020-03-11 09:49:00', '2020-03-11 09:52:00', 1, 1, 'Y'),
	(1540, 145, '2020-03-11 09:50:00', 'MISSED', NULL, '2020-03-11 09:50:00', '2020-03-11 09:53:00', 1, 1, 'Y'),
	(1541, 145, '2020-03-11 09:51:00', 'MISSED', NULL, '2020-03-11 09:51:00', '2020-03-11 09:54:00', 1, 1, 'Y'),
	(1542, 145, '2020-03-11 09:54:00', 'MISSED', NULL, '2020-03-11 09:54:00', '2020-03-11 09:57:00', 1, 1, 'Y'),
	(1543, 145, '2020-03-11 09:55:00', 'MISSED', NULL, '2020-03-11 09:55:00', '2020-03-11 09:58:00', 1, 1, 'Y'),
	(1544, 145, '2020-03-11 09:58:00', 'MISSED', NULL, '2020-03-11 09:58:00', '2020-03-11 10:01:00', 1, 1, 'Y'),
	(1545, 145, '2020-03-11 09:58:00', 'MISSED', NULL, '2020-03-11 09:58:00', '2020-03-11 10:01:00', 1, 1, 'Y'),
	(1546, 145, '2020-03-11 10:01:00', 'CONFIRMED', NULL, '2020-03-11 10:01:00', '2020-03-11 10:03:00', 1, 1, 'Y'),
	(1547, 145, '2020-03-11 10:01:00', 'CONFIRMED', NULL, '2020-03-11 10:01:00', '2020-03-11 10:03:02', 1, 1, 'Y'),
	(1548, 145, '2020-03-11 10:02:55', 'ENDED', NULL, '2020-03-11 10:02:55', '2020-03-11 10:02:55', 1, 1, 'Y');
/*!40000 ALTER TABLE `wh_shift_sched_times` ENABLE KEYS */;

-- Dumping structure for table fis_waho_db.wh_sys_codes
DROP TABLE IF EXISTS `wh_sys_codes`;
CREATE TABLE IF NOT EXISTS `wh_sys_codes` (
  `sys_code_id` int(11) NOT NULL AUTO_INCREMENT,
  `sys_code_category` varchar(255) DEFAULT NULL,
  `sys_code_name` varchar(255) DEFAULT NULL,
  `sys_code` varchar(50) DEFAULT NULL,
  `value1` varchar(255) DEFAULT NULL,
  `value2` varchar(255) DEFAULT NULL,
  `value3` varchar(255) DEFAULT NULL,
  `value4` varchar(4000) DEFAULT NULL,
  `date_created` datetime DEFAULT NULL,
  `date_last_updated` datetime DEFAULT NULL,
  `created_by` int(11) DEFAULT 0,
  `last_updated_by` int(11) DEFAULT 0,
  `is_active` char(1) DEFAULT 'Y',
  PRIMARY KEY (`sys_code_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;

-- Dumping data for table fis_waho_db.wh_sys_codes: ~17 rows (approximately)
/*!40000 ALTER TABLE `wh_sys_codes` DISABLE KEYS */;
INSERT INTO `wh_sys_codes` (`sys_code_id`, `sys_code_category`, `sys_code_name`, `sys_code`, `value1`, `value2`, `value3`, `value4`, `date_created`, `date_last_updated`, `created_by`, `last_updated_by`, `is_active`) VALUES
	(1, 'ALERT_CONFIG', 'Max imum Alerts in an Hour', 'MAX_ALERTS_IN_HOUR', '3', NULL, NULL, NULL, '2018-10-03 14:26:56', '2018-10-03 14:26:58', 0, 0, 'Y'),
	(2, 'ALERT_CONFIG', 'Alert Grace Period', 'ALERT_GRACE_PERIOD', '16', 'Minutes', NULL, NULL, '2018-10-03 14:27:40', '2018-10-03 14:27:42', 0, 0, 'Y'),
	(3, 'ALERT_CONFIG', 'Session Timeout Interval', 'SESSION_TIMEOUT_INTERVAL', '60', 'Minutes', NULL, NULL, '2018-10-03 14:28:49', '2018-10-03 14:28:50', 0, 0, 'Y'),
	(4, 'ALERT_CONFIG', 'Range for random added minutes', 'RANDOM_RANGE_FOR_ADDED_MINUTES', '60', 'Minutes', NULL, NULL, '2018-10-03 14:29:33', '2018-10-03 14:29:35', 0, 0, 'Y'),
	(5, 'ALERT_CONFIG', 'Purge Period of Alert Logged', 'PURGE_PERIOD', '30', 'Days', NULL, NULL, '2018-10-03 14:30:31', '2018-10-03 14:30:32', 0, 0, 'Y'),
	(6, 'ALERT_CONFIG', 'Show End Shift Interval', 'SHOW_END_SHIFT_INTERVAL', '60', 'Minutes', NULL, NULL, '2018-10-03 14:31:12', '2018-10-03 14:31:13', 0, 0, 'Y'),
	(7, ' USER_ROLES', 'Administrator', 'ADMIN', '1', NULL, NULL, NULL, '2018-10-03 14:31:41', '2018-10-03 14:31:42', 0, 0, 'Y'),
	(8, ' USER_ROLES', 'Manager', 'MANAGER', '2', NULL, NULL, NULL, '2018-10-03 14:32:01', '2018-10-03 14:32:02', 0, 0, 'Y'),
	(9, ' USER_ROLES', 'Employee', 'EMPLOYEE', '3', NULL, NULL, NULL, '2018-10-03 14:32:16', '2018-10-03 14:32:17', 0, 0, 'Y'),
	(10, 'ALERT_STATUS', 'Pending', 'PENDING', '1', NULL, NULL, NULL, '2018-10-03 14:32:54', '2018-10-03 14:32:54', 0, 0, 'Y'),
	(11, 'ALERT_STATUS', 'Missed', 'MISSED', '2', NULL, NULL, NULL, '2018-10-03 14:33:07', '2018-10-03 14:33:08', 0, 0, 'Y'),
	(12, 'ALERT_STATUS', 'Confirmed', 'CONFIRMED', '3', NULL, NULL, NULL, '2018-10-03 14:33:21', '2018-10-03 14:33:21', 0, 0, 'Y'),
	(13, 'DOWNLOAD_DAYS_FILTER', 'Last 3 Days', '3_DAYS', '3', 'Days', NULL, NULL, '2018-10-03 14:35:16', '2018-10-03 14:35:16', 0, 0, 'Y'),
	(14, 'DOWNLOAD_DAYS_FILTER', 'Last 7 Days', '7_DAYS', '7', 'Days', NULL, NULL, '2018-10-03 14:35:40', '2018-10-03 14:35:40', 0, 0, 'Y'),
	(15, 'DOWNLOAD_DAYS_FILTER', 'Last 14 Days', '14_DAYS', '14', 'Days', NULL, NULL, '2018-10-03 14:36:19', '2018-10-03 14:36:19', 0, 0, 'Y'),
	(16, 'DOWNLOAD_DAYS_FILTER', 'Last 30 Days', '30_DAYS', '30', 'Days', NULL, NULL, '2018-10-03 14:36:43', '2018-10-03 14:36:44', 0, 0, 'Y'),
	(17, 'DOWNLOAD_DAYS_FILTER', 'All', 'ALL', '0', NULL, NULL, NULL, '2018-10-03 14:37:08', '2018-10-03 14:37:08', 0, 0, 'Y');
/*!40000 ALTER TABLE `wh_sys_codes` ENABLE KEYS */;

-- Dumping structure for table fis_waho_db.wh_users
DROP TABLE IF EXISTS `wh_users`;
CREATE TABLE IF NOT EXISTS `wh_users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_eid` varchar(50) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `user_parent_id` int(11) DEFAULT 0,
  `effective_start_date` datetime DEFAULT NULL,
  `effective_end_date` datetime DEFAULT NULL,
  `date_created` datetime DEFAULT NULL,
  `date_last_updated` datetime DEFAULT NULL,
  `created_by` int(11) DEFAULT 0,
  `last_updated_by` int(11) DEFAULT 0,
  `is_active` char(1) DEFAULT 'Y',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=latin1;

-- Dumping data for table fis_waho_db.wh_users: ~10 rows (approximately)
/*!40000 ALTER TABLE `wh_users` DISABLE KEYS */;
INSERT INTO `wh_users` (`user_id`, `user_eid`, `first_name`, `last_name`, `email`, `user_parent_id`, `effective_start_date`, `effective_end_date`, `date_created`, `date_last_updated`, `created_by`, `last_updated_by`, `is_active`) VALUES
	(1, 'e5555622', 'Arthur', 'Olano', NULL, 10, '2018-10-03 00:01:15', '2028-10-03 00:01:15', '2018-10-03 00:01:15', '2018-10-03 00:01:15', 1, 1, 'Y'),
	(4, 'e5555995', 'Emman', 'Corpuz', NULL, 10, '2018-10-23 19:02:53', '2028-10-23 19:02:56', '2018-10-23 19:03:05', '2018-10-23 19:03:13', 1, 1, 'Y'),
	(5, 'e5555994', 'Eric', 'Dela Cruz', NULL, 10, '2018-10-23 19:03:44', '2028-10-23 19:03:48', '2018-10-23 19:03:54', '2018-10-23 19:03:54', 1, 1, 'Y'),
	(6, 'e1234567', 'Waho', 'Admin', NULL, 0, '2018-10-23 19:08:11', '2028-10-23 19:08:18', '2018-10-23 19:08:23', '2018-10-23 19:08:23', 1, 1, 'Y'),
	(8, 'e5555626', 'RenzCy', 'Vergara', NULL, 10, '2020-01-10 18:14:34', '2025-01-10 18:14:35', '2020-01-10 18:14:43', '2020-01-10 18:14:45', 0, 0, 'Y'),
	(9, 'e102022', 'Amelia', 'Grafalda', NULL, 36, '2018-10-26 16:08:26', '2018-10-26 16:08:26', '2018-10-26 16:08:26', '2018-10-26 16:08:26', 0, 0, 'Y'),
	(10, 'e1111222', 'John Michael', 'Dy', NULL, 0, '2018-10-24 00:00:00', '2018-10-29 00:00:00', '2018-10-26 18:02:32', '2018-10-26 18:02:32', 0, 0, 'Y'),
	(11, 'e4444123', 'Ruberson', 'Tria', NULL, 36, '2015-04-23 00:00:00', '2022-10-27 00:00:00', '2018-10-26 18:03:06', '2018-10-26 18:03:06', 0, 0, 'Y'),
	(12, 'e5555234', 'Test5', 'Test51', NULL, 3, '2018-10-22 00:00:00', '2018-10-30 00:00:00', '2018-10-26 18:30:15', '2018-10-26 18:30:15', 0, 0, 'Y'),
	(36, 'e1110000', 'Frances', 'Sarmiento', NULL, 0, NULL, NULL, '2018-11-28 15:06:51', '2018-11-28 15:06:51', 0, 0, 'Y');
/*!40000 ALTER TABLE `wh_users` ENABLE KEYS */;

-- Dumping structure for table fis_waho_db.wh_user_activities
DROP TABLE IF EXISTS `wh_user_activities`;
CREATE TABLE IF NOT EXISTS `wh_user_activities` (
  `user_activity_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL DEFAULT 0,
  `activity_code` varchar(50) DEFAULT NULL,
  `date_activity` datetime DEFAULT NULL,
  `remarks` varchar(4000) DEFAULT NULL,
  `date_created` datetime DEFAULT NULL,
  `date_last_updated` datetime DEFAULT NULL,
  `created_by` int(11) DEFAULT 0,
  `last_updated_by` int(11) DEFAULT 0,
  `is_active` char(1) DEFAULT 'Y',
  PRIMARY KEY (`user_activity_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table fis_waho_db.wh_user_activities: ~0 rows (approximately)
/*!40000 ALTER TABLE `wh_user_activities` DISABLE KEYS */;
/*!40000 ALTER TABLE `wh_user_activities` ENABLE KEYS */;

-- Dumping structure for table fis_waho_db.wh_user_alert_logins
DROP TABLE IF EXISTS `wh_user_alert_logins`;
CREATE TABLE IF NOT EXISTS `wh_user_alert_logins` (
  `alert_login_id` int(11) NOT NULL AUTO_INCREMENT,
  `shift_sched_id` int(11) NOT NULL DEFAULT 0,
  `date_logged_in` datetime DEFAULT NULL,
  `date_created` datetime DEFAULT NULL,
  `date_last_updated` datetime DEFAULT NULL,
  `created_by` int(11) DEFAULT 0,
  `last_updated_by7` int(11) DEFAULT 0,
  `is_active` char(1) DEFAULT 'Y',
  PRIMARY KEY (`alert_login_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table fis_waho_db.wh_user_alert_logins: ~0 rows (approximately)
/*!40000 ALTER TABLE `wh_user_alert_logins` DISABLE KEYS */;
/*!40000 ALTER TABLE `wh_user_alert_logins` ENABLE KEYS */;

-- Dumping structure for table fis_waho_db.wh_user_roles
DROP TABLE IF EXISTS `wh_user_roles`;
CREATE TABLE IF NOT EXISTS `wh_user_roles` (
  `user_role_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT 0,
  `role_cd` varchar(50) DEFAULT NULL,
  `date_created` datetime DEFAULT NULL,
  `date_last_updated` datetime DEFAULT NULL,
  `created_by` int(11) DEFAULT 0,
  `last_updated_by` int(11) DEFAULT 0,
  `is_active` char(1) DEFAULT 'Y',
  PRIMARY KEY (`user_role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;

-- Dumping data for table fis_waho_db.wh_user_roles: ~13 rows (approximately)
/*!40000 ALTER TABLE `wh_user_roles` DISABLE KEYS */;
INSERT INTO `wh_user_roles` (`user_role_id`, `user_id`, `role_cd`, `date_created`, `date_last_updated`, `created_by`, `last_updated_by`, `is_active`) VALUES
	(2, 4, 'EMPLOYEE', '2018-10-23 19:06:57', '2018-10-23 19:07:01', 1, 1, 'Y'),
	(3, 5, 'EMPLOYEE', '2018-10-23 19:07:17', '2018-10-23 19:07:17', 1, 1, 'Y'),
	(4, 6, 'ADMIN', '2018-10-23 19:09:12', '2018-10-23 19:09:12', 1, 1, 'Y'),
	(5, 1, 'EMPLOYEE', '2018-10-23 19:09:45', '2018-10-23 19:09:45', 1, 1, 'Y'),
	(11, 10, 'MANAGER', '2018-11-13 16:14:57', '2018-11-13 16:14:57', 0, 0, 'Y'),
	(12, 36, 'MANAGER', '2018-11-13 16:20:50', '2018-11-13 16:20:50', 0, 0, 'Y'),
	(14, NULL, 'EMPLOYEE', '2018-11-13 16:24:31', '2018-11-13 16:24:31', 0, 0, 'Y'),
	(15, NULL, 'EMPLOYEE', '2018-11-13 16:26:41', '2018-11-13 16:26:41', 0, 0, 'Y'),
	(16, NULL, 'EMPLOYEE', '2018-11-13 16:33:35', '2018-11-13 16:33:35', 0, 0, 'Y'),
	(17, 0, 'EMPLOYEE', '2018-11-13 16:52:38', '2018-11-13 16:52:38', 0, 0, 'Y'),
	(18, 0, 'EMPLOYEE', '2018-11-13 16:54:43', '2018-11-13 16:54:43', 0, 0, 'Y'),
	(19, 0, 'EMPLOYEE', '2018-11-28 15:06:51', '2018-11-28 15:06:51', 0, 0, 'Y'),
	(20, 8, 'EMPLOYEE', '2020-01-10 18:15:17', '2020-01-10 18:15:18', 0, 0, 'Y');
/*!40000 ALTER TABLE `wh_user_roles` ENABLE KEYS */;

-- Dumping structure for table fis_waho_db.wh_user_sessions
DROP TABLE IF EXISTS `wh_user_sessions`;
CREATE TABLE IF NOT EXISTS `wh_user_sessions` (
  `session_id` int(11) NOT NULL AUTO_INCREMENT,
  `session_uuid` varchar(25) DEFAULT NULL,
  `user_id` int(11) DEFAULT 0,
  `date_logged_in` datetime DEFAULT NULL,
  `date_logged_out` datetime DEFAULT NULL,
  `date_timeout` datetime DEFAULT NULL,
  `date_created` datetime DEFAULT NULL,
  `date_last_updated` datetime DEFAULT NULL,
  `created_by` int(11) DEFAULT 0,
  `last_updated_by` int(11) DEFAULT 0,
  `is_active` char(1) DEFAULT 'Y',
  PRIMARY KEY (`session_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table fis_waho_db.wh_user_sessions: ~0 rows (approximately)
/*!40000 ALTER TABLE `wh_user_sessions` DISABLE KEYS */;
/*!40000 ALTER TABLE `wh_user_sessions` ENABLE KEYS */;

-- Dumping structure for table fis_waho_db.wh_user_shift_schedules
DROP TABLE IF EXISTS `wh_user_shift_schedules`;
CREATE TABLE IF NOT EXISTS `wh_user_shift_schedules` (
  `shft_sched_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT 0,
  `sched_start_date` datetime DEFAULT NULL,
  `sched_end_date` datetime DEFAULT NULL,
  `actual_end_date` datetime DEFAULT NULL,
  `date_created` datetime DEFAULT NULL,
  `date_last_updated` datetime DEFAULT NULL,
  `created_by` int(11) DEFAULT 0,
  `last_updated_by` int(11) DEFAULT 0,
  `is_active` char(1) DEFAULT 'Y',
  PRIMARY KEY (`shft_sched_id`),
  KEY `FK_wh_user_shift_schedules_wh_users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=146 DEFAULT CHARSET=latin1;

-- Dumping data for table fis_waho_db.wh_user_shift_schedules: ~2 rows (approximately)
/*!40000 ALTER TABLE `wh_user_shift_schedules` DISABLE KEYS */;
INSERT INTO `wh_user_shift_schedules` (`shft_sched_id`, `user_id`, `sched_start_date`, `sched_end_date`, `actual_end_date`, `date_created`, `date_last_updated`, `created_by`, `last_updated_by`, `is_active`) VALUES
	(144, 1, '2020-03-05 13:44:00', '2020-03-05 22:44:00', '2020-03-05 14:41:54', '2020-03-05 13:45:01', '2020-03-05 13:45:01', 1, 1, 'Y'),
	(145, 1, '2020-03-05 14:42:00', '2020-03-05 23:42:00', '2020-03-11 10:02:55', '2020-03-05 14:42:42', '2020-03-05 14:42:42', 1, 1, 'Y');
/*!40000 ALTER TABLE `wh_user_shift_schedules` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
