
create table t_user_handle(
    handle_id           bigint(20)  not null auto_increment,
    username            varchar(50) not null,
    first_name          varchar(50),
    last_name           varchar(50),
    rating              int(11),
    max_rating          int(11),
    user_rank           varchar(30),
    max_user_rank       varchar(30),
    date_created        datetime,
    date_updated        datetime,
    residing_country    varchar(50),
    email_address       varchar(50),
    platform            varchar(50),
    primary key (handle_id)
);

create table t_problem(
	problem_id          bigint(20)  not null auto_increment,
	problem_name        varchar(100),
	platform            varchar(50),
	problem_tag         varchar(50),
	primary key (problem_id)
);

create table t_submission(
    submission_id       bigint(20)  not null auto_increment,
    remote_id           int(11),
    contest_id          int(11),
    problem_id          bigint(20)  not null,
    handle_id           bigint(20)  not null,
    verdict             varchar(50),
    submitted           datetime,
    primary key (submission_id),
    foreign key (problem_id)    references t_problem(problem_id),
    foreign key (handle_id)     references t_user_handle(handle_id)
);