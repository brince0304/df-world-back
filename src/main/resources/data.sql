INSERT INTO SAVE_FILE (FILE_NAME, FILE_PATH, FILE_SIZE, FILE_TYPE, CREATED_AT, CREATED_BY, MODIFIED_AT, MODIFIED_BY)
VALUES ('icon_char_0.png', '/home/ubuntu/df-project/df-toy-project/src/main/resources/static/images/icon_char/icon_char_0.png', '0', '.png', NOW(), 'brinc', NOW(), 'brinc');

INSERT INTO USER_ACCOUNT (USER_ID, CREATED_AT, CREATED_BY, MODIFIED_AT, MODIFIED_BY, EMAIL, NICKNAME, PASSWORD, PROFILE_ICON)
VALUES ('test', NOW(), 'test', NOW(), 'test', 'test@email.com', '안녕내이름은방가', '$2a$10$DAd.J6N1fv8ecD0UsYKOu.yPnrAQe.lw4pJmLaX6d3fhJ5Bzllw5.', 1);
INSERT INTO USER_ACCOUNT_ROLES (USER_ACCOUNT_USER_ID, ROLES) VALUES ('test', 'ROLE_USER');
