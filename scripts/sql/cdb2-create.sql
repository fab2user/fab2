CREATE SCHEMA `cdb2-dev` DEFAULT CHARACTER SET utf8mb4;
ALTER SCHEMA `cdb2-dev`  DEFAULT COLLATE utf8mb4_unicode_ci ;

CREATE TABLE admin_area_subdivision_major (id BIGINT AUTO_INCREMENT NOT NULL, code VARCHAR(255) NOT NULL, created_by VARCHAR(255), created_on DATETIME, deleted TINYINT(1) default 0, modified_by VARCHAR(255), modified_on DATETIME, name VARCHAR(255) NOT NULL, PRIMARY KEY (id));
CREATE TABLE admin_area_subdivision_middle (id BIGINT AUTO_INCREMENT NOT NULL, code VARCHAR(255) NOT NULL, created_by VARCHAR(255), created_on DATETIME, deleted TINYINT(1) default 0, modified_by VARCHAR(255), modified_on DATETIME, name VARCHAR(255) NOT NULL, admin_area_subdivision_major BIGINT, PRIMARY KEY (id));
CREATE TABLE admin_area_subdivision_minor (id BIGINT AUTO_INCREMENT NOT NULL, code VARCHAR(255) NOT NULL, created_by VARCHAR(255), created_on DATETIME, deleted TINYINT(1) default 0, modified_by VARCHAR(255), modified_on DATETIME, name VARCHAR(255) NOT NULL, admin_area_subdivision_middle BIGINT, PRIMARY KEY (id));
CREATE TABLE address (id BIGINT AUTO_INCREMENT NOT NULL, created_by VARCHAR(255), created_on DATETIME, deleted TINYINT(1) default 0, address VARCHAR(255), modified_by VARCHAR(255), modified_on DATETIME, municipality BIGINT, phone VARCHAR(25), email VARCHAR(50), PRIMARY KEY (id));
CREATE TABLE bailiff (id BIGINT AUTO_INCREMENT NOT NULL, created_by VARCHAR(255), created_on DATETIME, deleted TINYINT(1) default 0, name VARCHAR(255) NOT NULL, modified_by VARCHAR(255), modified_on DATETIME, address BIGINT, PRIMARY KEY (id));
CREATE TABLE competence (id bigint not null auto_increment, created_by VARCHAR(255), created_on datetime, deleted bit, modified_by VARCHAR(255), modified_on datetime, code varchar(255) not null, description varchar(255) not null, instrument bigint, primary key (id));
CREATE TABLE instrument (id bigint not null auto_increment, created_by VARCHAR(255), created_on datetime, deleted bit, modified_by VARCHAR(255), modified_on datetime, code varchar(255) not null, description varchar(255) not null, primary key (id));
CREATE TABLE language (id bigint not null auto_increment, created_by VARCHAR(255), created_on datetime, deleted bit, modified_by VARCHAR(255), modified_on datetime, code varchar(255) not null, language varchar(255) not null, primary key (id));
CREATE TABLE municipality (id bigint not null auto_increment, created_by VARCHAR(255), created_on datetime, deleted bit, modified_by VARCHAR(255), modified_on datetime, latitude varchar(255), longitude varchar(255), name varchar(255) not null, postal_code varchar(255) not null, admin_area_subdivision_major bigint, admin_area_subdivision_middle bigint, admin_area_subdivision_minor bigint, geo_area bigint, primary key (id));
CREATE TABLE rel_bailiff_lang (bailiff_id bigint not null, lang_id bigint not null);
CREATE TABLE geo_area (id bigint not null auto_increment, created_by bigint, created_on datetime, deleted bit, modified_by bigint, modified_on datetime, name varchar(255) not null, primary key (id));

ALTER TABLE admin_area_subdivision_middle ADD CONSTRAINT dminareasubdivisionmiddleadminareasubdivisionmajor FOREIGN KEY (admin_area_subdivision_major) REFERENCES admin_area_subdivision_major (id);
ALTER TABLE admin_area_subdivision_minor ADD CONSTRAINT dminareasubdivisionminoradminareasubdivisionmiddle FOREIGN KEY (admin_area_subdivision_middle) REFERENCES admin_area_subdivision_middle (id);
ALTER TABLE municipality ADD CONSTRAINT FK_municipality_admin_area_subdivision_middle FOREIGN KEY (admin_area_subdivision_middle) REFERENCES admin_area_subdivision_middle (id);
ALTER TABLE municipality ADD CONSTRAINT FK_municipality_admin_area_subdivision_major FOREIGN KEY (admin_area_subdivision_major) REFERENCES admin_area_subdivision_major (id);
ALTER TABLE municipality ADD CONSTRAINT FK_municipality_admin_area_subdivision_minor FOREIGN KEY (admin_area_subdivision_minor) REFERENCES admin_area_subdivision_minor (id);
ALTER TABLE municipality ADD CONSTRAINT FK_municipality_geo_area foreign key (geo_area) references geo_area (id);
ALTER TABLE address ADD CONSTRAINT FK_address_municipality FOREIGN KEY (municipality) REFERENCES municipality (id);
ALTER TABLE bailiff ADD CONSTRAINT FK_bailiff_address FOREIGN KEY (address) REFERENCES address (id);
ALTER TABLE competence add constraint FK_competence_instrument foreign key (instrument) references instrument (id);
ALTER TABLE rel_bailiff_lang add constraint FK_bailiff_lang foreign key (lang_id) references language (id);
ALTER TABLE rel_bailiff_lang add constraint FK_lang_bailiff foreign key (bailiff_id) references bailiff (id);

ALTER TABLE admin_area_subdivision_major ADD INDEX `idx_code_major` (`code` ASC);
ALTER TABLE admin_area_subdivision_middle ADD INDEX `idx_code_middle` (`code` ASC);
ALTER TABLE admin_area_subdivision_minor ADD INDEX `idx_code_minor` (`code` ASC);
ALTER TABLE municipality ADD INDEX `idx_code_municipality` (`postal_code` ASC);