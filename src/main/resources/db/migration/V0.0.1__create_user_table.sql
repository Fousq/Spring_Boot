create table if not exists user_account(
    id int primary key auto_increment,
    username varchar(100) not null,
    password varchar(100) not null
);