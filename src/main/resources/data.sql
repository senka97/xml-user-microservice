insert into role (id, name) values (1, 'ROLE_ADMIN');
insert into role (id, name) values (2, 'ROLE_AGENT');
insert into role (id, name) values (3, 'ROLE_CLIENT');

insert into permission (id, name) values (1, 'helloAdmin');
insert into permission (id, name) values (2, 'helloAgent');
insert into permission (id, name) values (3, 'helloClient');

insert into permission (id, name) values (4, 'postAdmin');
insert into permission (id, name) values (5, 'postAgent');
insert into permission (id, name) values (6, 'postClient');

insert into role_permissions (role_id, permission_id) values (1, 1);
insert into role_permissions (role_id, permission_id) values (2, 2);
insert into role_permissions (role_id, permission_id) values (3, 3);

insert into role_permissions (role_id, permission_id) values (1, 4);
insert into role_permissions (role_id, permission_id) values (2, 5);
insert into role_permissions (role_id, permission_id) values (3, 6);

insert into user (id, name, surname, email, password, enabled, type, role, company_name, company_number, address, phone_number, published_ads_number, status) values (1, 'admin', 'admin', 'admin@gmail.com', '$2a$10$H5LUyyiwlrFQK/92xcPj6u463Yzl5DT12SONvO/SsTi6Sqhx9vTve', true, 'ADMIN', 'ROLE_ADMIN', null, null, null, null, null, null);
insert into user (id, name, surname, email, password, enabled, type, role, company_name, company_number, address, phone_number, published_ads_number, status) values (2, 'agent', 'agent', 'agent@gmail.com', '$2a$10$z8pgIaOYaOE7MgeY7g/ceuqt377Zp8U/ZWQK2N5i1/8Sn4PYM1IIS', true, 'AGENT', 'ROLE_AGENT', 'My company', '123456789', 'Alekse Santica 4', null, null, null);
insert into user (id, name, surname, email, password, enabled, type, role, company_name, company_number, address, phone_number, published_ads_number, status) values (3, 'client', 'client', 'client@gmail.com', '$2a$10$DxUgxwukiMSEfd/HB/o.tuNzQuv/c6BMGj1dS0qDw8o0Xvyw4VFCq', true, 'CLIENT', 'ROLE_CLIENT', null, null, null, '123456789', 0, 'ACTIVE');

insert into user_roles (user_id, role_id) values (1, 1);
insert into user_roles (user_id, role_id) values (2, 2);
insert into user_roles (user_id, role_id) values (3, 3);