INSERT INTO save_file (file_name, file_path, file_size, file_type, created_at, created_by, modified_at, modified_by)
VALUES ('icon_char_0.png', '/home/ubuntu/app/icon_char/icon_char_0.png', '0', '.png', NOW(), 'brinc', NOW(), 'brinc');
INSERT INTO save_file (file_name, file_path, file_size, file_type, created_at, created_by, modified_at, modified_by)
VALUES ('icon_char_1.png', '/home/ubuntu/app/icon_char/icon_char_1.png', '0', '.png', NOW(), 'brinc', NOW(), 'brinc');
INSERT INTO save_file (file_name, file_path, file_size, file_type, created_at, created_by, modified_at, modified_by)
VALUES ('icon_char_2.png', '/home/ubuntu/app/icon_char/icon_char_2.png', '0', '.png', NOW(), 'brinc', NOW(), 'brinc');
INSERT INTO save_file (file_name, file_path, file_size, file_type, created_at, created_by, modified_at, modified_by)
VALUES ('icon_char_3.png', '/home/ubuntu/app/icon_char/icon_char_3.png', '0', '.png', NOW(), 'brinc', NOW(), 'brinc');
INSERT INTO save_file (file_name, file_path, file_size, file_type, created_at, created_by, modified_at, modified_by)
VALUES ('icon_char_4.png', '/home/ubuntu/app/icon_char/icon_char_4.png', '0', '.png', NOW(), 'brinc', NOW(), 'brinc');
INSERT INTO save_file (file_name, file_path, file_size, file_type, created_at, created_by, modified_at, modified_by)
VALUES ('icon_char_5.png', '/home/ubuntu/app/icon_char/icon_char_5.png', '0', '.png', NOW(), 'brinc', NOW(), 'brinc');
INSERT INTO save_file (file_name, file_path, file_size, file_type, created_at, created_by, modified_at, modified_by)
VALUES ('icon_char_6.png', '/home/ubuntu/app/icon_char/icon_char_6.png', '0', '.png', NOW(), 'brinc', NOW(), 'brinc');
INSERT INTO save_file (file_name, file_path, file_size, file_type, created_at, created_by, modified_at, modified_by)
VALUES ('icon_char_7.png', '/home/ubuntu/app/icon_char/icon_char_7.png', '0', '.png', NOW(), 'brinc', NOW(), 'brinc');
INSERT INTO save_file (file_name, file_path, file_size, file_type, created_at, created_by, modified_at, modified_by)
VALUES ('icon_char_8.png', '/home/ubuntu/app/icon_char/icon_char_8.png', '0', '.png', NOW(), 'brinc', NOW(), 'brinc');
INSERT INTO save_file (file_name, file_path, file_size, file_type, created_at, created_by, modified_at, modified_by)
VALUES ('icon_char_9.png', '/home/ubuntu/app/icon_char/icon_char_9.png', '0', '.png', NOW(), 'brinc', NOW(), 'brinc');
INSERT INTO save_file (file_name, file_path, file_size, file_type, created_at, created_by, modified_at, modified_by)
VALUES ('icon_char_10.png', '/home/ubuntu/app/icon_char/icon_char_10.png', '0', '.png', NOW(), 'brinc', NOW(), 'brinc');
INSERT INTO save_file (file_name, file_path, file_size, file_type, created_at, created_by, modified_at, modified_by)
VALUES ('icon_char_11.png', '/home/ubuntu/app/icon_char/icon_char_11.png', '0', '.png', NOW(), 'brinc', NOW(), 'brinc');
INSERT INTO save_file (file_name, file_path, file_size, file_type, created_at, created_by, modified_at, modified_by)
VALUES ('icon_char_12.png', '/home/ubuntu/app/icon_char/icon_char_12.png', '0', '.png', NOW(), 'brinc', NOW(), 'brinc');
INSERT INTO save_file (file_name, file_path, file_size, file_type, created_at, created_by, modified_at, modified_by)
VALUES ('icon_char_13.png', '/home/ubuntu/app/icon_char/icon_char_13.png', '0', '.png', NOW(), 'brinc', NOW(), 'brinc');
INSERT INTO save_file (file_name, file_path, file_size, file_type, created_at, created_by, modified_at, modified_by)
VALUES ('icon_char_14.png', '/home/ubuntu/app/icon_char/icon_char_14.png', '0', '.png', NOW(), 'brinc', NOW(), 'brinc');
INSERT INTO save_file (file_name, file_path, file_size, file_type, created_at, created_by, modified_at, modified_by)
VALUES ('icon_char_15.png', '/home/ubuntu/app/icon_char/icon_char_15.png', '0', '.png', NOW(), 'brinc', NOW(), 'brinc');




INSERT INTO user_account (user_id, created_at, created_by, modified_at, modified_by, email, nickname, password, profile_icon)
VALUES ('test', NOW(), 'test', NOW(), 'test', 'test@email.com', '안녕내이름은방가', '$2a$10$DAd.J6N1fv8ecD0UsYKOu.yPnrAQe.lw4pJmLaX6d3fhJ5Bzllw5.', 1);
INSERT INTO user_account_roles (user_account_user_id, roles) VALUES ('test', 'ROLE_USER');

insert into board (id, board_type, board_title, board_content, board_like_count, board_view_count, user_id, created_at, modified_at, created_by, modified_by, deleted) values (1, 'NOTICE', 'Toolbox Murders', 'Cookley', 1, 1, 'test', '2022-03-08 04:56:38', '2022-10-22 08:21:10', 'test', 'test', false);
insert into board (id, board_type, board_title, board_content, board_like_count, board_view_count, user_id, created_at, modified_at, created_by, modified_by, deleted) values (2, 'MARKET', 'Legend of the Black Scorpion (a.k.a. The Banquet) (Ye yan)', 'Hatity', 2, 2, 'test', '2022-10-08 10:20:10', '2022-11-14 21:16:26', 'test', 'test', true);
insert into board (id, board_type, board_title, board_content, board_like_count, board_view_count, user_id, created_at, modified_at, created_by, modified_by, deleted) values (3, 'FREE', 'Keep the River on Your Right: A Modern Cannibal Tale', 'Konklux', 3, 3, 'test', '2022-08-03 11:54:26', '2022-10-06 12:36:39', 'test', 'test', false);
insert into board (id, board_type, board_title, board_content, board_like_count, board_view_count, user_id, created_at, modified_at, created_by, modified_by, deleted) values (4, 'RECRUITMENT', 'Unknown Soldier, The (Unbekannte Soldat, Der)', 'Mat Lam Tam', 4, 4, 'test', '2022-06-02 19:52:29', '2022-07-16 04:06:56', 'test', 'test', false);
insert into board (id, board_type, board_title, board_content, board_like_count, board_view_count, user_id, created_at, modified_at, created_by, modified_by, deleted) values (5, 'RECRUITMENT', 'Norma Rae', 'Voyatouch', 5, 5, 'test', '2022-02-03 19:08:17', '2022-09-16 15:18:10', 'test', 'test', false);
insert into board (id, board_type, board_title, board_content, board_like_count, board_view_count, user_id, created_at, modified_at, created_by, modified_by, deleted) values (6, 'FREE', 'Place at the Table, A', 'Bamity', 6, 6, 'test', '2022-03-16 01:16:13', '2022-09-24 10:24:02', 'test', 'test', false);
insert into board (id, board_type, board_title, board_content, board_like_count, board_view_count, user_id, created_at, modified_at, created_by, modified_by, deleted) values (7, 'NOTICE', 'Ritz, The', 'Y-Solowarm', 7, 7, 'test', '2022-01-27 11:32:38', '2022-10-19 08:41:56', 'test', 'test', false);
insert into board (id, board_type, board_title, board_content, board_like_count, board_view_count, user_id, created_at, modified_at, created_by, modified_by, deleted) values (8, 'RECRUITMENT', 'From Hell', 'Daltfresh', 8, 8, 'test', '2022-06-04 03:13:49', '2022-03-20 22:48:52', 'test', 'test', false);
insert into board (id, board_type, board_title, board_content, board_like_count, board_view_count, user_id, created_at, modified_at, created_by, modified_by, deleted) values (9, 'FREE', 'Prêt à tout', 'Zaam-Dox', 9, 9, 'test', '2022-05-08 15:44:30', '2022-02-28 04:03:33', 'test', 'test', false);
insert into board (id, board_type, board_title, board_content, board_like_count, board_view_count, user_id, created_at, modified_at, created_by, modified_by, deleted) values (10, 'FREE', 'The Gilded Cage', 'Andalax', 10, 10, 'test', '2022-07-19 11:09:11', '2022-09-20 19:19:40', 'test', 'test', false);
insert into board (id, board_type, board_title, board_content, board_like_count, board_view_count, user_id, created_at, modified_at, created_by, modified_by, deleted) values (11, 'FREE', 'Showgirls', 'Redhold', 11, 11, 'test', '2022-06-27 02:10:16', '2022-05-27 05:25:27', 'test', 'test', false);
insert into board (id, board_type, board_title, board_content, board_like_count, board_view_count, user_id, created_at, modified_at, created_by, modified_by, deleted) values (12, 'MARKET', 'Private Resort', 'Pannier', 12, 12, 'test', '2022-04-26 12:33:44', '2022-07-08 18:00:32', 'test', 'test',true);
insert into board (id, board_type, board_title, board_content, board_like_count, board_view_count, user_id, created_at, modified_at, created_by, modified_by, deleted) values (13, 'FREE', 'Regret to Inform', 'Vagram', 1300, 13, 'test', now(), now(), 'test', 'test', false);
insert into BOARD_COMMENT (ID,created_at, created_by, modified_at, modified_by, comment_content, comment_like_count, deleted, is_parent, board_id, parent_id, user_account_user_id) VALUES (1, now(), 'test', now(), 'test', 'test', 135, false, true, 1, null, 'test' );
insert into BOARD_COMMENT (id,created_at, created_by, modified_at, modified_by, comment_content, comment_like_count, deleted, is_parent, board_id, parent_id, user_account_user_id) VALUES (2, now(), 'test', now(), 'test', 'test', 148, true, false, 2, 1, 'test' );
insert into BOARD_COMMENT (id,created_at, created_by, modified_at, modified_by, comment_content, comment_like_count, deleted, is_parent, board_id, parent_id, user_account_user_id) VALUES (3, now(), 'test', now(), 'test', 'test', 157, false, false, 3, 1, 'test' );
insert into BOARD_COMMENT (id,created_at, created_by, modified_at, modified_by, comment_content, comment_like_count, deleted, is_parent, board_id, parent_id, user_account_user_id) VALUES (4, now(), 'test', now(), 'test', 'test', 16, false, false, 4, null, 'test' );
insert into BOARD_COMMENT (id,created_at, created_by, modified_at, modified_by, comment_content, comment_like_count, deleted, is_parent, board_id, parent_id, user_account_user_id) VALUES (5, now(), 'test', now(), 'test', 'test', 17, false, true, 5, null, 'test' );
insert into BOARD_COMMENT (id,created_at, created_by, modified_at, modified_by, comment_content, comment_like_count, deleted, is_parent, board_id, parent_id, user_account_user_id) VALUES (6, now(), 'test', now(), 'test', 'test', 600, false, true, 5, null, 'test' );

insert into BOARD_COMMENT (id,created_at, created_by, modified_at, modified_by, comment_content, comment_like_count, deleted, is_parent, board_id, parent_id, user_account_user_id) VALUES (7, now(), 'test', now(), 'test', 'test', 677, false, true, 5, null, 'test' );
insert into BOARD_COMMENT (id,created_at, created_by, modified_at, modified_by, comment_content, comment_like_count, deleted, is_parent, board_id, parent_id, user_account_user_id) VALUES (8, now(), 'test', now(), 'test', 'test', 500, false, true, 5, null, 'test' );