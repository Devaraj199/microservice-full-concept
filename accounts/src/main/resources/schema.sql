CREATE TABLE IF NOT EXISTS `customer`(
 `customer_id` int AUTO_INCREMENT PRIMARY KEY,
 `name` varchar(200) not null,
 `email` varchar(200) not null,
 `mobile_number` varchar(20) not null,
 `created_at` Date not null,
 `created_by` varchar(200) not null,
 `updated_at` Date not null,
 `updated_by` varchar(200) not null
);

CREATE TABLE IF NOT EXISTS `accounts` (
  `customer_id` int NOT NULL,
   `account_number` int AUTO_INCREMENT  PRIMARY KEY,
  `account_type` varchar(100) NOT NULL,
  `branch_address` varchar(200) NOT NULL,
  `created_at` date NOT NULL,
   `created_by` varchar(20) NOT NULL,
   `updated_at` date DEFAULT NULL,
    `updated_by` varchar(20) DEFAULT NULL
);