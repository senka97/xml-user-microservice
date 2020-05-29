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

insert into permission (name) values ('allRegistrationRequests');
insert into permission (name) values ('allClients');
insert into permission (name) values ('allActiveClients');
insert into permission (name) values ('allBlockedClients');
insert into permission (name) values ('deleteClient');
insert into permission (name) values ('allAgents');
insert into permission (name) values ('getAgent');
insert into permission (name) values ('activateClient');
insert into permission (name) values ('blockClient');
insert into permission (name) values ('addCarModel');
insert into permission (name) values ('removeCarModel');
insert into permission (name) values ('addCarBrand');
insert into permission (name) values ('removeCarBrand');
insert into permission (name) values ('addCarClass');
insert into permission (name) values ('removeCarClass');
insert into permission (name) values ('addFuelType');
insert into permission (name) values ('removeFuelType');
insert into permission (name) values ('addTransmissionType');
insert into permission (name) values ('removeTransmissionType');

insert into permission (name) values ('cartItem_insert');
insert into permission (name) values ('cartItem_delete');
insert into permission (name) values ('cart_read');

insert into permission (name) values ('comment_read');
insert into permission (name) values ('comment_create');
insert into permission (name) values ('comment_update');

insert into permission (name) values ('ad_read');
insert into permission (name) values ('ad_create');
insert into permission (name) values ('ad_update');

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
insert into role_permissions (role_id, permission_id) values (1, 10);
insert into role_permissions (role_id, permission_id) values (1, 11);
insert into role_permissions (role_id, permission_id) values (1, 12);
insert into role_permissions (role_id, permission_id) values (1, 13);
insert into role_permissions (role_id, permission_id) values (1, 14);
insert into role_permissions (role_id, permission_id) values (1, 15);
insert into role_permissions (role_id, permission_id) values (1, 16);
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

insert into user (name, surname, email, password, enabled, type, role, company_name, company_number, address, phone_number, published_ads_number, status) values ('admin', 'admin', 'admintim19@gmail.com', '$2a$10$JFGdR3QqXo4X/GjBFZ0TLePLp/W80NOXSW8aMBsnUq8jTlP.Bfm/i', true, 'ADMIN', 'ROLE_ADMIN', null, null, null, null, null, null);
insert into user (name, surname, email, password, enabled, type, role, company_name, company_number, address, phone_number, published_ads_number, status) values ('agent', 'agent', 'agenttim19@gmail.com', '$2a$10$7WJNO0.0yIbZ4Q12wY.iB.NX3.4U3LA.PNiNvMhCE.Y/MpyEPaNk.', true, 'AGENT', 'ROLE_AGENT', 'My company', '123456789', 'Alekse Santica 4', null, null, null);
insert into user (name, surname, email, password, enabled, type, role, company_name, company_number, address, phone_number, published_ads_number, status, removed) values ('client', 'client', 'clienttim19@gmail.com', '$2a$10$/yo0ogfBgQRwyNQOghOcN.TLk02aarxzycJmbeCwQTHkGGUdCj4pi', true, 'CLIENT', 'ROLE_CLIENT', null, null, null, '123456789', 0, 'ACTIVE', false);
insert into user (name, surname, email, password, enabled, type, role, company_name, company_number, address, phone_number, published_ads_number, status, removed) values ('client', 'client', 'client2tim19@gmail.com', '$2a$10$/yo0ogfBgQRwyNQOghOcN.TLk02aarxzycJmbeCwQTHkGGUdCj4pi', true, 'CLIENT', 'ROLE_CLIENT', null, null, null, '123456789', 0, 'BLOCKED', false);

insert into user_roles (user_id, role_id) values (1, 1);
insert into user_roles (user_id, role_id) values (2, 2);
insert into user_roles (user_id, role_id) values (3, 3);
insert into user_roles (user_id, role_id) values (4, 3);

insert into registration_request (rreq_name, rreq_surname, rreq_email, rreq_password, rreq_phone_number, rreq_status)
values ("Nikola", "Nikolic", "nikola@mail.com", "$2a$10$DxUgxwukiMSEfd/HB/o.tuNzQuv/c6BMGj1dS0qDw8o0Xvyw4VFCq", "012345678", "PENDING");