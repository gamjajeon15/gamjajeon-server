create database if not exists gamjajeon ;

create table users
(
    id         bigint               not null auto_increment,
    created_at datetime(6) comment '생성 일자',
    is_deleted TINYINT(1) default 1 not null comment '삭제 여부',
    updated_at datetime(6) comment '수정 일자',
    ad_status  TINYINT(1) default 1 not null comment '마케팅 정보 수신 동의 여부 (0: 미동의, 1:동의)',
    email      varchar(255)         not null comment '사용자 이메일',
    password   varchar(255)         not null comment '사용자 비밀번호',
    username   varchar(32)          not null comment '로그인할 때 사용하는 ID',
    primary key (id),
    unique (username),
    unique (email)
) engine = InnoDB