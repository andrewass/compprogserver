
create table t_handle(
    handle_id        bigint(20)  not null auto_increment,
    username         varchar(50) not null,
    first_name       varchar(50),
    last_name        varchar(50),
    rating           int(11),
    date_created     datetime,
    date_updated     datetime,
    residing_country varchar(50),
    email_address    varchar(50),
    platform         varchar(20)
    primary key (handle_id)
);
