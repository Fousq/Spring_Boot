create table if not exists ticket(
    id int primary key auto_increment,
    user_id int,
    event_id int not null,
    booked boolean default false,
    foreign key (user_id) references user_account(id),
    foreign key (event_id) references event(id)
);