create table if not exists user1(
      id            identity         not null ,
      first_name    varchar (255)    not null ,
      last_name     varchar (255)    not null ,
      email         varchar (255)    not null ,
      password      varchar (255)    not null
);
alter table if exists user1
add constraint if not exists uq_email unique (email);