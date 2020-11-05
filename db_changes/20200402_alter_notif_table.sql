ALTER TABLE `wh_notification_queue` ADD COLUMN `date_created` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP AFTER `notif_sched`;
ALTER TABLE `wh_notification_queue` ADD COLUMN `date_last_updated` TIMESTAMP NULL DEFAULT NULL AFTER `date_created`;
ALTER TABLE `wh_notification_queue` ADD COLUMN `created_by` INT(11) NOT NULL DEFAULT '0' AFTER `date_last_updated`;
ALTER TABLE `wh_notification_queue` ADD COLUMN `last_updated_by` INT(11) NOT NULL DEFAULT '0' AFTER `created_by`;
ALTER TABLE `wh_notification_queue` ADD COLUMN `is_active` CHAR(1) NOT NULL DEFAULT 'Y' AFTER `last_updated_by`;
commit;