ALTER TABLE `wh_user_shift_schedules` ADD COLUMN `remaining_break_time` INT(11) NULL DEFAULT '5400' AFTER `date_created`;
INSERT INTO `fis_waho_db`.`wh_sys_codes` (`sys_code_category`, `sys_code_name`, `sys_code`, `value1`, `value2`, `value3`, `date_created`, `date_last_updated`) VALUES ('BREAK_TIME_ALLOWED', '4-Hours Shift Break Time', '4_HOURS_SHIFT_BREAK_TIME', '0', '4', '15', '2020-10-14 18:25:57', '2020-10-14 18:25:58');
INSERT INTO `fis_waho_db`.`wh_sys_codes` (`sys_code_category`, `sys_code_name`, `sys_code`, `value1`, `value2`, `value3`, `date_created`, `date_last_updated`) VALUES ('BREAK_TIME_ALLOWED', '9-Hours Shift Break Time', '9_HOURS_SHIFT_BREAK_TIME', '4', '9', '90', '2020-10-14 18:25:57', '2020-10-14 18:25:58');
INSERT INTO `fis_waho_db`.`wh_sys_codes` (`sys_code_category`, `sys_code_name`, `sys_code`, `value1`, `value2`, `value3`, `date_created`, `date_last_updated`) VALUES ('BREAK_TIME_ALLOWED', '11-Hours Shift Break Time', '11_HOURS_SHIFT_BREAK_TIME', '9', '11', '105', '2020-10-14 18:25:57', '2020-10-14 18:25:58');
INSERT INTO `fis_waho_db`.`wh_sys_codes` (`sys_code_category`, `sys_code_name`, `sys_code`, `value1`, `value2`, `value3`, `date_created`, `date_last_updated`) VALUES ('BREAK_TIME_ALLOWED', '15-Hours Shift Break Time', '15_HOURS_SHIFT_BREAK_TIME', '11', '15', '120', '2020-10-14 18:25:57', '2020-10-14 18:25:58');
INSERT INTO `fis_waho_db`.`wh_sys_codes` (`sys_code_category`, `sys_code_name`, `sys_code`, `value1`, `value2`, `value3`, `date_created`, `date_last_updated`) VALUES ('BREAK_TIME_ALLOWED', '18-Hours Shift Break Time', '18_HOURS_SHIFT_BREAK_TIME', '15', '18', '195', '2020-10-14 18:25:57', '2020-10-14 18:25:58');
INSERT INTO `fis_waho_db`.`wh_sys_codes` (`sys_code_category`, `sys_code_name`, `sys_code`, `value1`, `value2`, `value3`, `date_created`, `date_last_updated`) VALUES ('BREAK_TIME_ALLOWED', '24-Hours Shift Break Time', '24_HOURS_SHIFT_BREAK_TIME', '18', '24', '210', '2020-10-14 18:25:57', '2020-10-14 18:25:58');
INSERT INTO `fis_waho_db`.`wh_sys_codes` (`sys_code_category`, `sys_code_name`, `sys_code`, `value1`) VALUES ('USER_OPTIONS', 'Break Time Disable', 'BREAK_TIME_DISABLE', 'Y');
commit;