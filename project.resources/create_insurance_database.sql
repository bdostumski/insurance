create table base_amount_details
(
    cc_min      int    null,
    cc_max      int    null,
    car_age_min int    null,
    car_age_max int    null,
    base_amount double null
);

create table car_brand
(
    id    int auto_increment
        primary key,
    brand varchar(100) not null,
    constraint car_brand_brand_uindex
        unique (brand)
);

create table car_model
(
    id       int auto_increment
        primary key,
    model    varchar(100) null,
    brand_id int          not null,
    constraint car_model_car_brand_id_fk
        foreign key (brand_id) references car_brand (id)
);

create table coefficient
(
    accident   double null,
    age_limit  int    null,
    driver_age int    null,
    tax_amount double null
);

create table user_info
(
    id            int auto_increment
        primary key,
    firstname     varchar(100)         null,
    lastname      varchar(100)         null,
    email         varchar(100)         not null,
    birthdate     datetime             null,
    prev_accident tinyint(1) default 0 not null,
    soft_delete   tinyint(1) default 0 null,
    constraint user_info_email_uindex
        unique (email)
);

create table car_info
(
    id        int auto_increment
        primary key,
    reg_date  datetime null,
    cubic_cap double   null,
    brand_id  int      null,
    user_id   int      null,
    constraint car_info_car_brand_id_fk
        foreign key (brand_id) references car_brand (id),
    constraint car_info_user_info_id_fk
        foreign key (user_id) references user_info (id)
);

create table policy_info
(
    id              int auto_increment
        primary key,
    start_date_time datetime             null,
    total_price     double               null,
    is_approval     tinyint(1) default 0 not null,
    user_id         int                  null,
    car_id          int                  null,
    constraint policy_info_car_info_id_fk
        foreign key (car_id) references car_info (id),
    constraint policy_info_user_info_id_fk
        foreign key (user_id) references user_info (id)
);

create table image_info
(
    id        int auto_increment
        primary key,
    name      varchar(200) null,
    path      varchar(200) null,
    policy_id int          null,
    constraint image_info_policy_info_id_fk
        foreign key (policy_id) references policy_info (id)
);

