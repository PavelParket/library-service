drop database libraryservice;
create database libraryservice;
use libraryservice;
create table book (
	id bigint auto_increment primary key,
    name varchar(255) not null,
    genre varchar(255) not null,
    description varchar(255) not null,
    author varchar(255) not null,
    isbn varchar(255) not null unique
);
create table user (
	id bigint auto_increment primary key,
    username varchar(255) not null unique,
    password varchar(255) not null,
    role varchar(255) not null
);
create table loan (
	id bigint auto_increment primary key,
    book_id bigint not null,
    pickup_time date,
    return_time date,
    available boolean default true,
    foreign key (book_id) references book(id)
);