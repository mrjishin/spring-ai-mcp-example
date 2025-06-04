create database mcp_sample character set utf8mb4 collate utf8mb4_general_ci;

CREATE TABLE inquiries (
  id bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  question text COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Question',
  answer text COLLATE utf8mb4_general_ci COMMENT 'Answer',
  mod_date datetime(6) DEFAULT NULL COMMENT 'Modified date',
  reg_date datetime(6) DEFAULT NULL COMMENT 'Created date',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='Inquiry';