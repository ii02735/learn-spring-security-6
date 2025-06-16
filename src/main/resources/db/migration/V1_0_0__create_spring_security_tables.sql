-- Prélevé depuis le schéma par défaut se trouvant dans org/springframework/security/core/userdetails/jdbc/users.ddl

create table if not exists users
(
    username varchar(50)  not null primary key,
    password varchar(500) not null,
    enabled  boolean      not null
);
create table authorities
(
    username  varchar(50) not null,
    authority varchar(50) not null,
    constraint fk_authorities_users foreign key (username) references users (username)
);

create unique index if not exists ix_auth_username on authorities (username, authority);