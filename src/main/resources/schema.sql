drop table if exists tech_stack;
drop table if exists profile;

create table profile (
  id bigint auto_increment primary key,
  name varchar(50) not null,
  email varchar(100) not null unique,
  bio varchar(500),
  position varchar(20) not null,
  career_years int not null default 0,
  github_url varchar(200),
  blog_url varchar(200),
  created_at timestamp default current_timestamp,
  updated_at timestamp default current_timestamp on update current_timestamp
);

create table tech_stack (
  id bigint auto_increment primary key,
  profile_id bigint not null,
  name varchar(50) not null,
  category varchar(20) not null,
  proficiency varchar(20) not null,
  years_of_exp int not null default 0,
  created_at timestamp default current_timestamp,
  updated_at timestamp default current_timestamp on update current_timestamp,
  foreign key (profile_id) references profile(id) on delete cascade
);

create index idx_profile_position on profile(position);
create index idx_tech_stack_profile_id on tech_stack(profile_id);
create index idx_tech_stack_category on tech_stack(category);