CREATE TABLE IF NOT EXISTS `department`
(

    `id`   int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` varchar(20)

);

ALTER TABLE `employees`
    ADD `dept_id` int AFTER `email`;