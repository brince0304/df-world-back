INSERT INTO save_file (file_name, file_path, file_size, file_type, created_at, created_by, modified_at, modified_by)
VALUES ('icon_char_0.png', '/home/ubuntu/df-project/df-toy-project/src/main/resources/static/images/icon_char/icon_char_0.png', '0', '.png', NOW(), 'brinc', NOW(), 'brinc');

INSERT INTO user_account (user_id, created_at, created_by, modified_at, modified_by, email, nickname, password, profile_icon)
VALUES ('test', NOW(), 'test', NOW(), 'test', 'test@email.com', '안녕내이름은방가', '$2a$10$DAd.J6N1fv8ecD0UsYKOu.yPnrAQe.lw4pJmLaX6d3fhJ5Bzllw5.', 1);
INSERT INTO user_account_roles (user_account_user_id, roles) VALUES ('test', 'ROLE_USER');
