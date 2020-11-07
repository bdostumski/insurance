set @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
set @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
set @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

drop schema if exists `insurance_info`;
create schema if not exists `insurance_info` default character set latin1;
use `insurance_info`;

-- -----------------------------------------------------
-- Table `insurance_info`.`authorities`
-- -----------------------------------------------------

create table authorities (
    id int auto_increment primary key,
    authority varchar(50) not null,
    constraint uername_authority unique (id, authority)
);

-- -----------------------------------------------------
-- Table `insurance_info`.`base_amount_details`
-- -----------------------------------------------------

create table base_amount_details (
    id int auto_increment primary key,
    cc_min int not null,
    cc_max int not null,
    car_age_min int not null,
    car_age_max int not null,
    base_amount double not null
);

-- -----------------------------------------------------
-- Table `insurance_info`.`car_brand`
-- -----------------------------------------------------

create table car_brand (
    id int auto_increment primary key,
    brand varchar(100) not null,
    constraint car_brand_brand_uindex unique (brand)
);

-- -----------------------------------------------------
-- Table `insurance_info`.`car_model`
-- -----------------------------------------------------

create table car_model (
    id int auto_increment primary key,
    model varchar(100) null,
    brand_id int not null,
    constraint car_model_car_brand_id_fk foreign key (brand_id) references car_brand (id)
);

-- -----------------------------------------------------
-- Table `insurance_info`.`coefficient`
-- -----------------------------------------------------

create table coefficient (
    id int auto_increment primary key,
    name varchar(20) null,
    accident double not null,
    age_limit int not null,
    age_coefficient double null,
    tax_amount double null
);

-- -----------------------------------------------------
-- Table `insurance_info`.`info_dto`
-- -----------------------------------------------------

create table info_dto (
    id int auto_increment primary key,
    car_brand varchar(100) null,
    car_model varchar(100) null,
    car_cubic varchar(100) null,
    registration_date varchar(100) null,
    driver_birthdate varchar(100) null,
    accident varchar(100) null,
    total_price varchar(100) null,
    user_token varchar(200) null
);

-- -----------------------------------------------------
-- Table `insurance_info`.`user_info`
-- -----------------------------------------------------

create table user_info (
    id int auto_increment primary key,
    firstname varchar(100) null,
    lastname varchar(100) null,
    email varchar(100) not null,
    birthdate varchar(100) null,
    prev_accident tinyint(1) default 0 not null,
    soft_delete tinyint(1) default 0 null,
    password varchar(200) not null,
    enabled tinyint(1) default 1 not null,
    role_id int not null,
    phone_number varchar(50) null,
    address varchar(200) null,
    constraint user_info_email_uindex unique (email),
    constraint user_info_authorities_id_fk foreign key (role_id) references authorities (id)
);

-- -----------------------------------------------------
-- Table `insurance_info`.`car_info`
-- -----------------------------------------------------

create table car_info (
    id int auto_increment primary key,
    reg_date varchar(100) null,
    cubic_cap varchar(50) null,
    model_id int null,
    user_id int null,
    constraint car_info_car_model_id_fk foreign key (model_id) references car_model (id),
    constraint car_info_user_info_id_fk foreign key (user_id) references user_info (id)
);

-- -----------------------------------------------------
-- Table `insurance_info`.`email_tokens`
-- -----------------------------------------------------

create table email_tokens (
    id int auto_increment primary key,
    token varchar(100) not null,
    expiry_date datetime null,
    user_id int not null,
    constraint email_tokens_user_info_id_fk foreign key (user_id) references user_info (id)
);

-- -----------------------------------------------------
-- Table `insurance_info`.`policy_info`
-- -----------------------------------------------------

create table policy_info (
    id int auto_increment primary key,
    start_date  varchar(100) null,
    total_price double null,
    is_approval tinyint(1) default 0 not null,
    user_id int null,
    car_id int null,
    start_time varchar(100) null,
    constraint policy_info_car_info_id_fk foreign key (car_id) references car_info (id),
    constraint policy_info_user_info_id_fk foreign key (user_id) references user_info (id)
);

-- -----------------------------------------------------
-- Table `insurance_info`.`image_info`
-- -----------------------------------------------------

create table image_info (
    id int auto_increment primary key,
    name varchar(200) null,
    path varchar(200) null,
    policy_id int null,
    constraint image_info_policy_info_id_fk foreign key (policy_id) references policy_info (id)
);

