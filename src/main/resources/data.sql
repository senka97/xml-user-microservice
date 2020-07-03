insert into role (name) values ('ROLE_ADMIN');
insert into role (name) values ('ROLE_AGENT');
insert into role (name) values ('ROLE_CLIENT');

insert into permission (name) values ('helloAdmin');
insert into permission (name) values ('helloAgent');
insert into permission (name) values ('helloClient');

insert into permission (name) values ('postAdmin');
insert into permission (name) values ('postAgent');
insert into permission (name) values ('postClient');

insert into permission (name) values ('currentUser_read');

insert into permission (name) values ('registration_request_read'); --8
insert into permission (name) values ('client_read'); --9
insert into permission (name) values ('allActiveClients'); --10 ova se ne koristi
insert into permission (name) values ('allBlockedClients'); --11 ova se ne koristi
insert into permission (name) values ('client_delete'); --12
insert into permission (name) values ('agent_read'); --13
insert into permission (name) values ('getAgent'); --14 ova se ne koristi
insert into permission (name) values ('client_update'); --15
insert into permission (name) values ('blockClient'); --16 ova se ne koristi
insert into permission (name) values ('car_model_create'); --17
insert into permission (name) values ('car_model_delete'); --18
insert into permission (name) values ('car_brand_create'); --19
insert into permission (name) values ('car_brand_delete'); --20
insert into permission (name) values ('car_class_create'); --21
insert into permission (name) values ('car_class_delete'); --22
insert into permission (name) values ('fuel_type_create'); --23
insert into permission (name) values ('fuel_type_delete'); --24
insert into permission (name) values ('transmission_type_create'); --25
insert into permission (name) values ('transmission_type_delete'); --26

insert into permission (name) values ('cartItem_insert');
insert into permission (name) values ('cartItem_delete');
insert into permission (name) values ('cart_read');

insert into permission (name) values ('comment_read');
insert into permission (name) values ('comment_create');
insert into permission (name) values ('comment_update');

insert into permission (name) values ('ad_read');
insert into permission (name) values ('ad_create');
insert into permission (name) values ('ad_update');

insert into permission (name) values ('request_create');

insert into permission (name) values ('registration_request_a_read'); --37

insert into permission (name) values ('fillClient');
insert into permission (name) values ('request_update');
insert into permission (name) values ('request_read');
insert into permission (name) values ('request_update_cancel');

insert into permission (name) values ('allComments'); --42 ova se ne koristi
insert into permission (name) values ('allReplies'); --43 ova se ne koristi
insert into permission (name) values ('reply_update'); --44 ova se ne koristi
insert into permission (name) values ('request_reject_update'); --45 ova se ne koristi

insert into permission (name) values ('rate_update'); --46
insert into permission (name) values ('rate_create'); --47

insert into permission (name) value ('message_r_c'); -- 48
--49 50 51 52
insert into permission (name) values ('reservation_read');
insert into permission (name) values ('reservation_create');
insert into permission (name) values ('reservation_update');
insert into permission (name) values ('reservation_delete');
--53 54 55 56
insert into permission (name) values ('priceList_create');
insert into permission (name) values ('priceList_read');
insert into permission (name) values ('priceList_update');
insert into permission (name) values ('priceList_delete');

--57
insert into permission (name) values ('registration_request_update');
--58 59
insert into permission (name) values ('report_create');
insert into permission (name) values ('report_read');

--60 61 62
insert into permission (name) values ('car_create');
insert into permission (name) values ('car_read');
insert into permission (name) values ('car_update');

insert into permission (name) values ('password_update');

insert into role_permissions (role_id, permission_id) values (1, 1);
insert into role_permissions (role_id, permission_id) values (2, 2);
insert into role_permissions (role_id, permission_id) values (3, 3);

insert into role_permissions (role_id, permission_id) values (1, 4);
insert into role_permissions (role_id, permission_id) values (2, 5);
insert into role_permissions (role_id, permission_id) values (3, 6);

insert into role_permissions (role_id, permission_id) values (1,7);
insert into role_permissions (role_id, permission_id) values (2,7);
insert into role_permissions (role_id, permission_id) values (3,7);

insert into role_permissions (role_id, permission_id) values (1, 8);
insert into role_permissions (role_id, permission_id) values (1, 9);
insert into role_permissions (role_id, permission_id) values (1, 10); --ne koristi se
insert into role_permissions (role_id, permission_id) values (1, 11); --ne koristi se
insert into role_permissions (role_id, permission_id) values (1, 12);
insert into role_permissions (role_id, permission_id) values (1, 13);
insert into role_permissions (role_id, permission_id) values (1, 14); --ne koristi se
insert into role_permissions (role_id, permission_id) values (1, 15);
insert into role_permissions (role_id, permission_id) values (1, 16); --ne koristi se
insert into role_permissions (role_id, permission_id) values (1, 17);
insert into role_permissions (role_id, permission_id) values (1, 18);
insert into role_permissions (role_id, permission_id) values (1, 19);
insert into role_permissions (role_id, permission_id) values (1, 20);
insert into role_permissions (role_id, permission_id) values (1, 21);
insert into role_permissions (role_id, permission_id) values (1, 22);
insert into role_permissions (role_id, permission_id) values (1, 23);
insert into role_permissions (role_id, permission_id) values (1, 24);
insert into role_permissions (role_id, permission_id) values (1, 25);
insert into role_permissions (role_id, permission_id) values (1, 26);

insert into role_permissions (role_id, permission_id) values (3, 27);
insert into role_permissions (role_id, permission_id) values (3, 28);
insert into role_permissions (role_id, permission_id) values (3, 29);
--30 31 32 comment
insert into role_permissions (role_id, permission_id) values (1, 30);
insert into role_permissions (role_id, permission_id) values (1, 32); -- admin r and u
insert into role_permissions (role_id, permission_id) values (2, 30);
insert into role_permissions (role_id, permission_id) values (2, 32); -- agent r and u
insert into role_permissions (role_id, permission_id) values (3, 30);
insert into role_permissions (role_id, permission_id) values (3, 31);
insert into role_permissions (role_id, permission_id) values (3, 32); -- client r, c and u
--33 34 35 ad
insert into role_permissions (role_id, permission_id) values (1, 33); -- admin r
insert into role_permissions (role_id, permission_id) values (2, 33);
insert into role_permissions (role_id, permission_id) values (2, 34);
insert into role_permissions (role_id, permission_id) values (2, 35); -- agent r, c and u
insert into role_permissions (role_id, permission_id) values (3, 33);
insert into role_permissions (role_id, permission_id) values (3, 34);
insert into role_permissions (role_id, permission_id) values (3, 35);-- client r, c and u

insert into role_permissions (role_id, permission_id) values (3, 36);

insert into role_permissions (role_id, permission_id) values (1, 37);

insert into role_permissions (role_id, permission_id) values (2, 38);
insert into role_permissions (role_id, permission_id) values (3, 38);
insert into role_permissions (role_id, permission_id) values (2, 39);
insert into role_permissions (role_id, permission_id) values (3, 39);
insert into role_permissions (role_id, permission_id) values (2, 40);
insert into role_permissions (role_id, permission_id) values (3, 40);
insert into role_permissions (role_id, permission_id) values (3, 41);

insert into role_permissions (role_id, permission_id) values (1, 42); --ne koristi se
insert into role_permissions (role_id, permission_id) values (1, 43); --ne koristi se
insert into role_permissions (role_id, permission_id) values (1, 44); --ne koristi se
insert into role_permissions (role_id, permission_id) values (1, 45); --ne koristi se
insert into role_permissions (role_id, permission_id) values (1, 39);

insert into role_permissions (role_id, permission_id) values (3, 46);
insert into role_permissions (role_id, permission_id) values (3, 47);

insert into  role_permissions (role_id, permission_id) value (2, 48); -- message read and create
insert into  role_permissions (role_id, permission_id) value (3, 48);

-- 49 50 reservations
insert into  role_permissions (role_id, permission_id) value (2, 49);
insert into  role_permissions (role_id, permission_id) value (2, 50); -- agent r and c
insert into  role_permissions (role_id, permission_id) value (3, 49);
insert into  role_permissions (role_id, permission_id) value (3, 50); -- client r and c

-- 53 - 56 price list crud
insert into  role_permissions (role_id, permission_id) value (2, 53);
insert into  role_permissions (role_id, permission_id) value (2, 54);
insert into  role_permissions (role_id, permission_id) value (2, 56); --agent c r d
insert into  role_permissions (role_id, permission_id) value (3, 53);
insert into  role_permissions (role_id, permission_id) value (3, 54);
insert into  role_permissions (role_id, permission_id) value (3, 56); --client c r d

insert into  role_permissions (role_id, permission_id) value (1, 57); --admin registration request update
-- 58 59 report cr
insert into  role_permissions (role_id, permission_id) value (2, 58);
insert into  role_permissions (role_id, permission_id) value (2, 59); --agent c r
insert into  role_permissions (role_id, permission_id) value (3, 58);
insert into  role_permissions (role_id, permission_id) value (3, 59); --client c r

-- 60 61 62 car cru
insert into  role_permissions (role_id, permission_id) value (2, 60);
insert into  role_permissions (role_id, permission_id) value (2, 61);
insert into  role_permissions (role_id, permission_id) value (2, 62); --agent c r u
insert into  role_permissions (role_id, permission_id) value (3, 60);
insert into  role_permissions (role_id, permission_id) value (3, 61);
insert into  role_permissions (role_id, permission_id) value (3, 62); --client c r u

insert into role_permissions (role_id, permission_id) values (1, 63); --admin password update
insert into role_permissions (role_id, permission_id) values (2, 63); --agent password update
insert into role_permissions (role_id, permission_id) values (3, 63); --client password update

insert into user (name, surname, email, password, enabled, type, role, company_name, company_number, address, phone_number, published_ads_number, status) values ('Admin', 'Admin', 'admin@gmail.com', '$2a$10$aMBAEXjwqcavdNT373EM7.hlk2MWp1SNYCZ5NWk/Dytyf/xPsI02a', true, 'ADMIN', 'ROLE_ADMIN', null, null, null, null, null, null);
insert into user (name, surname, email, password, enabled, type, role, company_name, company_number, address, phone_number, published_ads_number, status) values ('Agent1', 'Agent', 'agent@gmail.com', '$2a$10$7YXN6idLLmf5Gy2K5guobOghxYVQBcc9a04fFmRd82ns12klLCmr.', true, 'AGENT', 'ROLE_AGENT', 'My company', '123456789', 'Alekse Santica 4', null, null, null);
insert into user (name, surname, email, password, enabled, type, role, company_name, company_number, address, phone_number, published_ads_number, status, removed, can_comment, canceled_request_number, can_add_to_cart) values ('Client1', 'Client ', 'client@gmail.com', '$2a$10$noSRMxRJjDHt2Ky.E1QHYupSHa8tTZ0fyVXvWE5o73PKdlrLgSyMG', true, 'CLIENT', 'ROLE_CLIENT', null, null, null, '123456789', 0, 'ACTIVE', false, true, 0, true);
insert into user (name, surname, email, password, enabled, type, role, company_name, company_number, address, phone_number, published_ads_number, status, removed, can_comment, canceled_request_number, can_add_to_cart) values ('Client2 ', 'Client', 'client2@gmail.com', '$2a$10$noSRMxRJjDHt2Ky.E1QHYupSHa8tTZ0fyVXvWE5o73PKdlrLgSyMG', false, 'CLIENT', 'ROLE_CLIENT', null, null, null, '123456789', 0, 'BLOCKED', false, true, 0, true);
insert into user (name, surname, email, password, enabled, type, role, company_name, company_number, address, phone_number, published_ads_number, status, removed, can_comment, canceled_request_number, can_add_to_cart) values ('Client3', 'Client', 'client3@gmail.com', '$2a$10$noSRMxRJjDHt2Ky.E1QHYupSHa8tTZ0fyVXvWE5o73PKdlrLgSyMG', true, 'CLIENT', 'ROLE_CLIENT', null, null, null, '123456789', 0, 'ACTIVE', false, true, 0, true);
insert into user (name, surname, email, password, enabled, type, role, company_name, company_number, address, phone_number, published_ads_number, status) values ('Agent2', 'Agent', 'agent2@gmail.com', '$2a$10$7YXN6idLLmf5Gy2K5guobOghxYVQBcc9a04fFmRd82ns12klLCmr.', true, 'AGENT', 'ROLE_AGENT', 'My company', '123456789', 'Alekse Santica 4', null, null, null);

insert into user (name, surname, email, password, enabled, type, role, company_name, company_number, address, phone_number, published_ads_number, status, removed, can_comment, canceled_request_number, can_add_to_cart) values ('Pera', 'Peric', 'pera@gmail.com', '$2a$10$noSRMxRJjDHt2Ky.E1QHYupSHa8tTZ0fyVXvWE5o73PKdlrLgSyMG', true, 'CLIENT', 'ROLE_CLIENT', null, null, null, '123456789', 0, 'ACTIVE', false, true, 0, true);
insert into user (name, surname, email, password, enabled, type, role, company_name, company_number, address, phone_number, published_ads_number, status, removed, can_comment, canceled_request_number, can_add_to_cart) values ('Djura', 'Djuric', 'djura@gmail.com', '$2a$10$noSRMxRJjDHt2Ky.E1QHYupSHa8tTZ0fyVXvWE5o73PKdlrLgSyMG', true, 'CLIENT', 'ROLE_CLIENT', null, null, null, '123456789', 0, 'ACTIVE', false, true, 0, true);

--insert into user (name, surname, email, password, enabled, type, role, company_name, company_number, address, phone_number, published_ads_number, status, removed) values ('test', 'test', 'test@gmail.com', '$2a$10$r.O2iTjbT7mzzKii9pdgYeo0LrqZTwTGqcWqZ5fRgytBxmm0SHWJK', true, 'CLIENT', 'ROLE_CLIENT', null, null, null, '123456789', 0, 'ACTIVE', false);

insert into user_roles (user_id, role_id) values (1, 1);
insert into user_roles (user_id, role_id) values (2, 2);
insert into user_roles (user_id, role_id) values (3, 3);
insert into user_roles (user_id, role_id) values (4, 3);
insert into user_roles (user_id, role_id) values (5, 3);
insert into user_roles (user_id, role_id) values (6, 2);
insert into user_roles (user_id, role_id) values (7, 3);
insert into user_roles (user_id, role_id) values (8, 3);

--insert into user_roles (user_id, role_id) values (9, 3);

insert into registration_request (rreq_name, rreq_surname, rreq_email, rreq_password, rreq_phone_number, rreq_status)
values ("Nikola", "Nikolic", "nikola@mail.com", "$2a$10$DxUgxwukiMSEfd/HB/o.tuNzQuv/c6BMGj1dS0qDw8o0Xvyw4VFCq", "012345678", "PENDING");

insert into registration_request_agent (rreq_name_a, rreq_surname_a, rreq_email_a, rreq_password_a, rreq_company_name_a, rreq_company_number_a, rreq_address_a, rreq_status_a)
values ("Jovan", "Jovanovic", "jovan@gmail.com", "$2a$10$7YXN6idLLmf5Gy2K5guobOghxYVQBcc9a04fFmRd82ns12klLCmr.", "Jovanovic-Company", "012345678", "Skerliceva 5","PENDING");