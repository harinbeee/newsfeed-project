CREATE TABLE users
(
    `user_id`         BIGINT       NOT NULL AUTO_INCREMENT,
    `email`           VARCHAR(255) NOT NULL,
    `password`        VARCHAR(255) NOT NULL,
    `username`        VARCHAR(255) NOT NULL,
    `nickname`        VARCHAR(255) NOT NULL,
    `created_at`      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `phone`           VARCHAR(255) NULL,
    `profile_picture` VARCHAR(255) NULL,
    `description`     VARCHAR(255) NULL,
    `is_deleted`      BOOL         NULL,
    PRIMARY KEY (user_id)
);

CREATE TABLE boards
(
    `board_id`   BIGINT       NOT NULL AUTO_INCREMENT,
    `user_id`    BIGINT       NOT NULL,
    `title`      VARCHAR(255) NOT NULL,
    `content`    TEXT         NOT NULL,
    `created_at` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (board_Id),
    FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE friends
(
    `friends_id`   BIGINT NOT NULL AUTO_INCREMENT,
    `to_user_id`   BIGINT NOT NULL,
    `from_user_id` BIGINT NOT NULL,
    PRIMARY KEY (friends_id),
    FOREIGN KEY (to_user_id) REFERENCES users (user_id),
    FOREIGN KEY (from_user_id) REFERENCES users (user_id)
);

CREATE TABLE comments
(
    comment_id BIGINT    NOT NULL AUTO_INCREMENT,
    user_id    BIGINT    NOT NULL,
    board_id   BIGINT    NOT NULL,
    content    TEXT      NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (comment_id),
    FOREIGN KEY (user_id) REFERENCES users (user_id),
    FOREIGN KEY (board_id) REFERENCES boards (board_id)
);

ALTER TABLE boards
    ADD COLUMN board_image VARCHAR(512);

create table likes
(
    board_id   bigint not null,
    comment_id bigint,
    id         bigint not null auto_increment,
    user_id    bigint not null,
    primary key (id)
);

CREATE TABLE friend_requests
(
    id           BIGINT NOT NULL AUTO_INCREMENT,
    to_user_id   BIGINT,
    from_user_id BIGINT,
    FOREIGN KEY (to_user_id) REFERENCES users (user_id),
    FOREIGN KEY (from_user_id) REFERENCES users (user_id)
);
