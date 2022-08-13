-- auto-generated definition
create table if not exists RATING_MPA
(
    ID_RATING      INTEGER auto_increment
        primary key,
    NAME_OF_RATING CHARACTER VARYING(50)
);
create table if not exists FILMS
(
    FILM_ID          INTEGER auto_increment
        primary key,
    FILM_NAME        CHARACTER VARYING(100),
    FILM_DESCRIPTION CHARACTER VARYING(100),
    FILM_RELEASE     DATE,
    FILM_DURATION    INTEGER,
    RATE             INTEGER,
    ID_MPA           INTEGER,
    constraint FK_MPA
        foreign key (ID_MPA) references RATING_MPA
);



create table if not exists USERS
(
    USER_ID    INTEGER auto_increment
        primary key,
    USER_EMAIL CHARACTER VARYING(200)
        constraint UQ_EMAIL
            unique,
    USER_LOGIN CHARACTER VARYING(100)
        constraint UQ_LOGIN
            unique,
    USER_NAME  CHARACTER VARYING(100),
    BIRTHDAY   DATE
);

create table if not exists FRIENDS
(
    USER_ID   INTEGER not null,
    FRIEND_ID INTEGER not null,
    CONFIRM   BOOLEAN,
    constraint FRIEND_PK
        primary key (USER_ID, FRIEND_ID),
    constraint FROM_USER_ID_FK
        foreign key (USER_ID) references USERS
            on delete cascade,
    constraint TO_USER_ID_FK
        foreign key (FRIEND_ID) references USERS
            on delete cascade
);





create table if not exists GENRE
(
    ID_GENRE      INTEGER auto_increment,
    NAME_OF_GENRE CHARACTER VARYING(50),
    constraint PK
        primary key (ID_GENRE)
);


create table if not exists LIKES
(
    FILM_ID INTEGER not null,
    USER_ID INTEGER not null,
    constraint LIKES_PK
        primary key (FILM_ID, USER_ID),
    constraint LIKES_FILM_ID_FK
        foreign key (FILM_ID) references FILMS
            on delete cascade,
    constraint LIKES_USER_ID_FK
        foreign key (USER_ID) references USERS
            on delete cascade
);


-- auto-generated definition
create table IF NOT EXISTS FILM_GENRE
(
    ID_FILM  INTEGER not null,
    ID_GENRE INTEGER not null,
    constraint FILM_GENRE_PK
        primary key (ID_FILM,ID_GENRE),
    constraint FK_FILM_GENRE_FILM_ID
        foreign key (ID_FILM) references FILMS,
    constraint FK_FILM_GENRE_GENRE_ID
        foreign key (ID_GENRE) references GENRE
);




-- auto-generated definition


