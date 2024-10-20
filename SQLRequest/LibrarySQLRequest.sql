drop database libraryservice;
create database libraryservice;
use libraryservice;
create table book (
	id int primary key,
    name varchar(25) not null,
    genre varchar(20) not null,
    description varchar(100) not null,
    author varchar(40) not null,
    isbn varchar(13) not null unique
);
create table user (
	id int primary key,
    username varchar(25) not null unique,
    password varchar(20) not null,
    role varchar(100) not null
);