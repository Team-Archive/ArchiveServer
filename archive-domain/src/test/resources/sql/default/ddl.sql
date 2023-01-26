-- Test table ddl
create table if not exists archive
(
    archive_id       bigint auto_increment primary key,
    created_at       timestamp   default CURRENT_TIMESTAMP null,
    is_deleted       tinyint(1)  default 0                 null,
    updated_at       timestamp   default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    companions       varchar(255)                          null,
    emotion          varchar(255)                          null,
    main_image       varchar(255)                          null,
    name             varchar(100)                          not null,
    watched_on       timestamp                             null,
    author_id        bigint                                not null,
    is_public        tinyint(1)  default 0                 null,
    cover_image_type varchar(20) default 'EMOTION_COVER'   not null
);

create table if not exists archive_image
(
    archive_image_id bigint auto_increment primary key,
    created_at       timestamp  default CURRENT_TIMESTAMP null,
    is_deleted       tinyint(1) default 0                 null,
    updated_at       timestamp  default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    image            varchar(255)                         null,
    review           varchar(255)                         null,
    archive_id       bigint                               null,
    background_color varchar(45)                          null
);

create table if not exists banner
(
    banner_id     bigint auto_increment primary key,
    summary_image varchar(255)                         null,
    main_content  varchar(500)                         null,
    is_deleted    tinyint(1) default 0                 null,
    created_at    timestamp  default CURRENT_TIMESTAMP null,
    updated_at    timestamp  default CURRENT_TIMESTAMP null,
    type          varchar(10)                          null
);

create table if not exists user
(
    user_type     varchar(31)  not null,
    user_id       bigint auto_increment primary key,
    created_at    timestamp    null,
    is_deleted    tinyint(1)   null,
    updated_at    timestamp    null,
    mail_address  varchar(255) null,
    user_role     varchar(255) null,
    profile_image varchar(255) null,
    nickname      varchar(255) null
);

create table if not exists archive_like
(
    archive_like_id bigint auto_increment primary key,
    created_at      timestamp  default CURRENT_TIMESTAMP null,
    is_deleted      tinyint(1) default 0                 null,
    updated_at      timestamp  default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    user_id         bigint                               not null,
    archive_id      bigint                               not null
);

create table if not exists archive_report
(
    archive_report_id bigint auto_increment comment 'report_id' primary key,
    user_id           bigint                               null,
    archive_id        bigint                               null,
    reason            varchar(100)                         null,
    created_at        timestamp  default CURRENT_TIMESTAMP null,
    updated_at        timestamp  default CURRENT_TIMESTAMP null,
    is_deleted        tinyint(1) default 0                 null
);

create table if not exists oauth_user
(
    oauth_provider varchar(255) null,
    user_id        bigint       not null primary key
);

create table if not exists password_user
(
    password              varchar(255)         null,
    user_id               bigint               not null primary key,
    is_temporary_password tinyint(1) default 0 null
);

create table if not exists user_deleted
(
    user_type     varchar(31)  not null,
    user_id       bigint auto_increment primary key,
    created_at    timestamp    null,
    is_deleted    tinyint(1)   null,
    updated_at    timestamp    null,
    mail_address  varchar(255) null,
    user_role     varchar(255) null,
    profile_image varchar(255) null,
    nickname      varchar(255) null
);
