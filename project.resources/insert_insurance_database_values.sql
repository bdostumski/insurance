#
# Dumping data for table `authorities`
#

insert into `authorities` (id, authority) values (1, 'ROLE_ADMIN');
insert into `authorities` (id, authority) values (2, 'ROLE_AGENT');
insert into `authorities` (id, authority) values (3, 'ROLE_USER');


#
# Dumping data for table `base_amount_details`
#

insert into `base_amount_details` (cc_min, cc_max, car_age_min, car_age_max, base_amount) values (0, 1047, 0 , 19, 403.25);
insert into `base_amount_details` (cc_min, cc_max, car_age_min, car_age_max, base_amount) values (0, 1047, 20 , 999, 413.25);
insert into `base_amount_details` (cc_min, cc_max, car_age_min, car_age_max, base_amount) values (1048, 1309, 0 , 19, 529.63);
insert into `base_amount_details` (cc_min, cc_max, car_age_min, car_age_max, base_amount) values (1048, 1309, 20 , 999, 539.63);
insert into `base_amount_details` (cc_min, cc_max, car_age_min, car_age_max, base_amount) values (1310, 2356, 0 , 19, 690.96);
insert into `base_amount_details` (cc_min, cc_max, car_age_min, car_age_max, base_amount) values (1310, 2356, 20 , 999, 700.96);
insert into `base_amount_details` (cc_min, cc_max, car_age_min, car_age_max, base_amount) values (2357, 2880, 0 , 19, 862.86);
insert into `base_amount_details` (cc_min, cc_max, car_age_min, car_age_max, base_amount) values (2357, 2880, 20 , 999, 892.86);
insert into `base_amount_details` (cc_min, cc_max, car_age_min, car_age_max, base_amount) values (2881, 4188, 0 , 19, 957.89);
insert into `base_amount_details` (cc_min, cc_max, car_age_min, car_age_max, base_amount) values (2881, 4188, 20 , 999, 987.89);
insert into `base_amount_details` (cc_min, cc_max, car_age_min, car_age_max, base_amount) values (4189, 5497, 0 , 19, 1076.62);
insert into `base_amount_details` (cc_min, cc_max, car_age_min, car_age_max, base_amount) values (4189, 5497, 20 , 999, 1106.62);
insert into `base_amount_details` (cc_min, cc_max, car_age_min, car_age_max, base_amount) values (5498, 999999, 0 , 19, 1193.25);
insert into `base_amount_details` (cc_min, cc_max, car_age_min, car_age_max, base_amount) values (5498, 999999, 20 , 999, 1263.25);

#
# Dumping data for table `coefficient`
#

insert into `coefficient` (id, name, accident, age_limit, age_coefficient, tax_amount) values (1, 'BG', 0.2, 25, 0.05, 0.1);

#
# Dumping data for table `car_brand`
#

insert into `car_brand` (brand) values ('Audi');
insert into `car_brand` (brand) values ('BMW');
insert into `car_brand` (brand) values ('Mercedes');
insert into `car_brand` (brand) values ('Volvo');
insert into `car_brand` (brand) values ('VW');


#
# Dumping data for table `car_model`
#

insert into `car_model` (model, brand_id) values ('XC90', 4);
insert into `car_model` (model, brand_id) values ('XC60', 4);
insert into `car_model` (model, brand_id) values ('GLS', 3);
insert into `car_model` (model, brand_id) values ('GLE', 3);
insert into `car_model` (model, brand_id) values ('S-class', 3);
insert into `car_model` (model, brand_id) values ('Q8', 1);
insert into `car_model` (model, brand_id) values ('A8', 1);
insert into `car_model` (model, brand_id) values ('A6', 1);
insert into `car_model` (model, brand_id) values ('Touareg', 5);
insert into `car_model` (model, brand_id) values ('Tiguan', 5);
insert into `car_model` (model, brand_id) values ('X5', 2);
insert into `car_model` (model, brand_id) values ('X6', 2);
insert into `car_model` (model, brand_id) values ('7-series', 2);


#
# Dumping data for table `user_info`
# password: admin
#
insert into `user_info` (firstname, lastname, email, birthdate, password, role_id, phone_number, address)
    values ('Borislav', 'Dostumski', 'admin@mail.com', '1988-12-14', '$2a$10$ueb2tKbQEF/stQAOhGDB4uB.qKMMTDj3X3ws8Q1H0n.eSRwQJdLw6', 1, '0889815914', 'Blagoevgrad');