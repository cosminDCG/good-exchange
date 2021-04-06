CREATE SCHEMA IF NOT EXISTS dbfortest;
create table if not exists feature (id binary not null, key varchar(255), value varchar(255), product_id binary, primary key (id));
create table if not exists meeting (id binary not null, approved boolean not null, date timestamp, location varchar(255), buyer_id binary, seller_id binary, product_id binary, primary key (id));
create table if not exists product (id binary not null, address varchar(255), available boolean, description varchar(255), name varchar(255), price double, type varchar(255), image varchar, seller_id binary, primary key (id));
create table if not exists user (id binary not null, address varchar(255), email varchar(255), first_name varchar(255), last_name varchar(255), password varchar(255), phone varchar(255), primary key (id));
create table if not exists message (id binary not null, content varchar(255), date timestamp, from_id binary, to_id binary, primary key (id));
alter table feature add constraint FK810l56o2f4ev18qmd4434tqnw foreign key (product_id) references product;
alter table meeting add constraint FK1omnt7is7akynr42i9dnuvpv1 foreign key (buyer_id) references user;
alter table meeting add constraint FK2fa8147yrtk7x8ojcahor45bs foreign key (seller_id) references user;
alter table product add constraint FKmsvavr0t3lra70gf2ymxdi5te foreign key (seller_id) references user;
alter table message add constraint FKkn6rt8591aaepiuacwuggj556 foreign key (from_id) references user;
alter table message add constraint FK7w4n34mf259wjvyqqc0pb534n foreign key (to_id) references user;
alter table meeting add constraint FKp4d1gmyp3kb77135kqwgv2cjs foreign key (product_id) references product;

INSERT INTO user(id, first_name, last_name, address, phone, email, password)
VALUES(random_uuid(), 'Cosmin', 'Dimisca', 'Brasov', '0765999999', 'cosmin.dimisca@gmail.com', '$2a$10$WvCivAYACTit8t.vfeSDf.sUFl7fYu57vAamlA0niOqRHJeJXDFpy');

INSERT INTO user(id, first_name, last_name, address, phone, email, password)
VALUES(random_uuid(), 'Ana', 'Petrescu', 'Bucuresti', '0765999999', 'ana.petrescu@gmail.com', '$2a$10$WvCivAYACTit8t.vfeSDf.sUFl7fYu57vAamlA0niOqRHJeJXDFpy');