--drop schema emoticons cascade;

--create schema emoticons;

set search_path to emoticons;

--drop table emoticon;

create table emoticon
(
	id serial,
	icon character varying (20),
	description character varying (40),
	primary key (id)
);

create table log
(
	id serial,
	emoticonId serial,
	event character varying (6),
	primary key (id)
);