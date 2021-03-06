
create table t_user(
	user_id     bigint(20)  not null auto_increment,
	username    varchar(50) not null,
	password    varchar(200) not null,
	email       varchar(60) not null,
	primary key (user_id)
);

create table t_user_handle(
    handle_id           bigint(20)  not null auto_increment,
    user_id             bigint(20) not null,
    external_id         int,
    user_handle         varchar(50)  not null,
    rating              int,
    max_rating          int,
    user_rank           varchar(30),
    max_user_rank       varchar(30),
    platform            varchar(50)  not null,
    primary key (handle_id),
    foreign key (user_id)   references t_user(user_id)
);

create table t_problem(
	problem_id          bigint(20)  not null auto_increment,
	problem_name        varchar(100)  not null,
	platform            varchar(50),
	problem_url         varchar(100),
	rating              int,
	rate_count          int,
	rate_sum            bigint(20),
	primary key (problem_id)
);

create table t_problem_rating(
	problem_rating_id   bigint(20) not null auto_increment,
	problem_id          bigint(20) not null,
	user_id             bigint(20) not null,
	rating              int not null,
	primary key (problem_rating_id),
	foreign key (problem_id)    references t_problem(problem_id),
	foreign key (user_id)       references t_user(user_id)
);

create table t_submission(
    submission_id       bigint(20)  not null auto_increment,
    remote_id           int,
    problem_id          bigint(20),
    handle_id           bigint(20)  not null,
    verdict             varchar(50),
    submitted           datetime,
    primary key (submission_id),
    foreign key (problem_id)    references t_problem(problem_id),
    foreign key (handle_id)     references t_user_handle(handle_id)
);

create table t_contest(
	contest_id          bigint(20)  not null auto_increment,
	remote_id           int,
	contest_name        varchar(300),
	start_time          datetime,
	duration            int,
	platform            varchar(50),
	primary key (contest_id)
);

create table t_problem_tag(
    problem_tag_id      bigint(20) not null auto_increment,
    problem_id          bigint(20) not null,
    category_tag        varchar(50) not null,
    primary key (problem_tag_id),
    foreign key (problem_id) references t_problem(problem_id)
);
