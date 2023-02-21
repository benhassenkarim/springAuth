create table if not exists user1(
      id            identity         not null ,
      first_name    varchar (255)    not null ,
      last_name     varchar (255)    not null ,
      email         varchar (255)    not null ,
      password      varchar (255)    not null
);