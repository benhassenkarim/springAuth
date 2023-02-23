create table if not exists user1(
      id            identity         not null ,
      first_name    varchar (255)    not null ,
      last_name     varchar (255)    not null ,
      email         varchar (255)    not null ,
      password      varchar (255)    not null
);

alter table if exists user1
add constraint if not exists uq_email unique (email);

create table if not exists token(
      id            identity         not null ,
      refresh_token varchar (255)    not null ,
      expired_at    datetime         not null ,
      issued_at     datetime         not null ,
      user1         bigint           not null ,
      constraint fk_token_user foreign key (user1) references user1(id)

);

create table if not exists password_recovery(
      id            identity         not null ,
      token         varchar (255)    not null ,
      user1         bigint           not null ,
      constraint fk_password_recofery_user foreign key (user1) references user1(id)
);
