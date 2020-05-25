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

insert into role_permissions (role_id, permission_id) values (1, 1);
insert into role_permissions (role_id, permission_id) values (2, 2);
insert into role_permissions (role_id, permission_id) values (3, 3);

insert into role_permissions (role_id, permission_id) values (1, 4);
insert into role_permissions (role_id, permission_id) values (2, 5);
insert into role_permissions (role_id, permission_id) values (3, 6);

insert into role_permissions (role_id, permission_id) values (1,7);
insert into role_permissions (role_id, permission_id) values (2,7);
insert into role_permissions (role_id, permission_id) values (3,7);


insert into user (name, surname, email, password, enabled, type, role, company_name, company_number, address, phone_number, published_ads_number, status) values ('admin', 'admin', 'admintim19@gmail.com', '$2a$10$JFGdR3QqXo4X/GjBFZ0TLePLp/W80NOXSW8aMBsnUq8jTlP.Bfm/i', true, 'ADMIN', 'ROLE_ADMIN', null, null, null, null, null, null);
insert into user (name, surname, email, password, enabled, type, role, company_name, company_number, address, phone_number, published_ads_number, status) values ('agent', 'agent', 'agenttim19@gmail.com', '$2a$10$7WJNO0.0yIbZ4Q12wY.iB.NX3.4U3LA.PNiNvMhCE.Y/MpyEPaNk.', true, 'AGENT', 'ROLE_AGENT', 'My company', '123456789', 'Alekse Santica 4', null, null, null);
insert into user (name, surname, email, password, enabled, type, role, company_name, company_number, address, phone_number, published_ads_number, status) values ('client', 'client', 'clienttim19@gmail.com', '$2a$10$/yo0ogfBgQRwyNQOghOcN.TLk02aarxzycJmbeCwQTHkGGUdCj4pi', true, 'CLIENT', 'ROLE_CLIENT', null, null, null, '123456789', 0, 'ACTIVE');

insert into user_roles (user_id, role_id) values (1, 1);
insert into user_roles (user_id, role_id) values (2, 2);
insert into user_roles (user_id, role_id) values (3, 3);