insert into user_account(username, password) values ('test_user', 'test');
insert into event(name) values ('test_event');
insert into ticket(event_id) values (1);
insert into ticket(user_id, event_id, booked) values (1, 1, true);