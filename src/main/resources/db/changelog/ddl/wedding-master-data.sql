USE emoldino;

DROP TABLE IF EXISTS MMS_QUEUE;

CREATE TABLE WEDDING_POST(
   ID BIGINT NOT NULL AUTO_INCREMENT,   
   NAME VARCHAR (50) NOT NULL,
   PASSWORD VARCHAR (50) NOT NULL,							   
   CONTENT  LONGTEXT NOT NULL,    
   CREATED_AT DATETIME NULL DEFAULT NULL,
   UPDATED_AT DATETIME NULL DEFAULT NULL,   
   PRIMARY KEY (ID) USING BTREE
)CHARSET=UTF8MB4;
