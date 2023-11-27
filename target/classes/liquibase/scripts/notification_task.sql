-- liquibase formatted sql

-- changeset vrubanov:1
CREATE TABLE notification_task
(
    id        bigserial primary key,
    chat_id   bigserial,
    notification_message      varchar,
    notification_time timestamp
);