ALTER TABLE `wh_shift_sched_times` ADD COLUMN `is_acknowledged` CHAR(1) NULL DEFAULT NULL AFTER `is_active`;
ALTER TABLE `wh_shift_sched_times` ADD COLUMN `temp` VARCHAR(4000) NULL DEFAULT NULL AFTER `is_acknowledged`;
commit;