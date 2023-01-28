create database student-info;
use student-info;

create table students(
    id int auto_increment primary key,
    FirstName varchar(20) not null,
    LastName varchar(20) not null,
    COURSE varchar(20) not null
);
insert into students(FirstName, LastName, COURSE) values("Dara","Keo", "JAVA")