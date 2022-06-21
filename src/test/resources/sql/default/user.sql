-- user
INSERT INTO user (user_type, user_id, created_at, is_deleted, updated_at, mail_address, user_role)
VALUES ('oauth', 1, '2022-04-11 01:14:02', 0, '2022-04-12 01:14:02', 'test@test.com', 'GENERAL'),
       ('oauth', 3, '2022-04-13 20:30:22', 0, '2022-04-14 20:30:22', 'test2@test.com', 'GENERAL'),
       ('oauth', 5, '2022-04-15 20:30:22', 0, '2022-04-16 20:30:22', 'test4@test.com', 'GENERAL'),
       ('oauth', 7, '2022-04-17 20:30:22', 0, '2022-04-17 21:30:22', 'test6@test.com', 'GENERAL'),
       ('oauth', 9, '2022-04-08 20:30:22', 0, '2022-04-12 20:30:22', 'test8@test.com', 'GENERAL'),
       ('password', 2, '2022-03-12 20:30:22', 0, '2022-04-12 20:30:22', 'test1@test.com', 'GENERAL'),
       ('password', 4, '2022-11-12 20:30:22', 0, '2022-04-12 02:10:22', 'test3@test.com', 'GENERAL'),
       ('password', 6, '2021-01-12 20:30:22', 0, '2022-02-11 20:32:22', 'test5@test.com', 'GENERAL'),
       ('password', 8, '2021-02-12 20:30:22', 0, '2022-03-22 20:30:22', 'test7@test.com', 'GENERAL'),
       ('password', 10, '2020-03-12 20:30:22', 0, '2021-04-12 01:00:22', 'test9@test.com', 'GENERAL');


-- password user
INSERT INTO password_user (password, user_id, is_temporary_password)
VALUES ('test-password1', 2, 0),
       ('test-password2', 4, 0),
       ('test-password3', 6, 0),
       ('test-password4', 8, 0),
       ('test-password5', 10, 0);

-- oauth user
INSERT INTO oauth_user (oauth_provider, user_id)
VALUES ('KAKAO', 1),
       ('APPLE', 3),
       ('KAKAO', 5),
       ('APPLE', 7),
       ('KAKAO', 9);