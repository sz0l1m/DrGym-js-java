create sequence TOKEN_SEQUENCE
    /

create table USERS
(
    USERNAME VARCHAR2(50)  not null
        primary key,
    NAME     VARCHAR2(50)  not null,
    SURNAME  VARCHAR2(50)  not null,
    EMAIL    VARCHAR2(100) not null
        unique,
    PASSWORD VARCHAR2(100) not null,
    WEIGHT   NUMBER(5, 2),
    HEIGHT   NUMBER(5, 2),
    VERIFIED NUMBER(1) default 0
)
    /

create table MUSCLES
(
    MUSCLE_ID   NUMBER(4)    not null
        primary key,
    MUSCLE_NAME VARCHAR2(40) not null
)
    /

create table EXERCISES
(
    EXERCISE_ID NUMBER(4)    not null
        primary key,
    TYPE        CHAR      default 'S',
    KCAL_BURNED NUMBER(4) default 0,
    NAME        VARCHAR2(40) not null
)
    /

create table ACTIVITIES
(
    ACTIVITY_ID NUMBER(4) not null
        primary key,
    EXERCISE_ID NUMBER(4) not null
        constraint FK_ACTIVITES_EXERCISE_ID
            references EXERCISES,
    REPS        NUMBER(4) default 0,
    WEIGHT      NUMBER(4) default 0,
    DURATION    DATE      default TO_DATE('00:00:00', 'HH24:MI:SS')
)
    /

create table EXERCISES_MUSCLES
(
    EXERCISE_ID NUMBER(4) not null
        references EXERCISES,
    MUSCLE_ID   NUMBER(4) not null
        references MUSCLES,
    primary key (EXERCISE_ID, MUSCLE_ID)
)
    /

create table WORKOUTS
(
    WORKOUT_ID       NUMBER       not null
        primary key,
    START_DATETIME   DATE         not null,
    USERNAME         VARCHAR2(50) not null
        references USERS
            on delete cascade,
    END_DATETIME     DATE,
    DESCRIPTION      VARCHAR2(50 char),
    CREATED_DATETIME TIMESTAMP(6) default CURRENT_TIMESTAMP
)
    /

create table WORKOUT_ACTIVITIES
(
    WORKOUT_ID          NUMBER not null
        references WORKOUTS
            on delete cascade,
    ACTIVITY_ID         NUMBER not null
        references ACTIVITIES
            on delete cascade,
    WORKOUT_ACTIVITY_ID NUMBER not null
        constraint WORKOUT_ACTIVITIES_PK
            primary key
)
    /

create table TOKEN
(
    EMAIL              VARCHAR2(255) not null
        primary key,
    VERIFICATION_TOKEN VARCHAR2(255),
    RESET_TOKEN        VARCHAR2(255),
    RESET_EXPIRY       TIMESTAMP(6)
)
    /

create table FRIENDSHIPS
(
    ID               NUMBER(8)    not null
        primary key,
    FRIEND1_USERNAME VARCHAR2(50) not null
        constraint FRIEND1_FK
            references USERS,
    FRIEND2_USERNAME VARCHAR2(50) not null
        constraint FRIEND2_FK
            references USERS,
    CREATED_AT       TIMESTAMP(6) default CURRENT_TIMESTAMP,
    constraint FRIENDSHIP_UNIQUE_PAIR1
        unique (FRIEND1_USERNAME, FRIEND2_USERNAME)
)
    /

alter table FRIENDSHIPS
    add constraint FRIENDSHIP_UNIQUE_PAIR2
        unique (FRIEND1_USERNAME, FRIEND2_USERNAME)
    /

create trigger TG_DIFFERENT_USERS_CHECK
    before insert
    on FRIENDSHIPS
    for each row
BEGIN
    IF :NEW.FRIEND1_USERNAME = :NEW.FRIEND2_USERNAME THEN
        RAISE_APPLICATION_ERROR(-20001, 'Friendship cannot be created with only 1 user!');
END IF;
END;
/

create trigger TG_DELETE_FRIENDSHIP_INVITATION
    after insert
    on FRIENDSHIPS
    for each row
BEGIN
    DELETE FROM FRIENDSHIP_INVITATIONS
    WHERE WHO_SEND_USERNAME = :NEW.FRIEND1_USERNAME
      AND WHO_RECEIVE_USERNAME = :NEW.FRIEND2_USERNAME;
    DELETE FROM FRIENDSHIP_INVITATIONS
    WHERE WHO_SEND_USERNAME = :NEW.FRIEND2_USERNAME
      AND WHO_RECEIVE_USERNAME = :NEW.FRIEND1_USERNAME;
END;
/

create table FRIENDSHIP_INVITATIONS
(
    FRIENDSHIP_INVITATION_ID NUMBER(8)    not null
        primary key,
    WHO_SEND_USERNAME        VARCHAR2(50) not null
        constraint WHO_SEND_FK
            references USERS,
    WHO_RECEIVE_USERNAME     VARCHAR2(50) not null
        constraint WHO_RECEIVE_FK
            references USERS,
    SEND_TIME                TIMESTAMP(6) default CURRENT_TIMESTAMP
)
    /

create table POSTS
(
    POST_ID         NUMBER(8)     not null
        primary key,
    AUTHOR_USERNAME VARCHAR2(50)  not null
        constraint AUTHOR_FK
            references USERS,
    POST_DATE       TIMESTAMP(6) default CURRENT_TIMESTAMP,
    TITLE           VARCHAR2(100) not null,
    WORKOUT_ID      NUMBER(8)
        constraint WORKOUT_FK
            references WORKOUTS,
    CONTENT         VARCHAR2(200)
)
    /

create trigger TG_DELETE_REACTIONS
    after delete
    on POSTS
    for each row
BEGIN
    DELETE FROM POST_REACTIONS
    WHERE POST_ID = :OLD.POST_ID;
END;
/

create trigger TG_DELETE_COMMENTS
    after delete
    on POSTS
    for each row
BEGIN
    DELETE FROM POST_COMMENTS
    WHERE POST_ID = :OLD.POST_ID;
END;
/

create table POST_COMMENTS
(
    POST_COMMENT_ID   NUMBER(8)    not null
        primary key,
    POST_ID           NUMBER(8)    not null
        references POSTS,Ftg
    AUTHOR_USERNAME   VARCHAR2(50) not null
        references USERS,
    CONTENT           CLOB,
    POST_COMMENT_DATE TIMESTAMP(6) default CURRENT_TIMESTAMP
)
    /

create table POST_REACTIONS
(
    POST_REACTION_ID NUMBER(8)    not null
        primary key,
    POST_ID          NUMBER(8)    not null
        references POSTS,
    AUTHOR_USERNAME  VARCHAR2(40) not null
        references USERS
)
    /

create FUNCTION ARE_FRIENDS (
    user1 IN VARCHAR2,
    user2 IN VARCHAR2
) RETURN NUMBER IS
         has_friends NUMBER;
BEGIN
SELECT COUNT(*)
INTO has_friends
FROM FRIENDSHIPS
WHERE (FRIEND1_USERNAME = user1 AND FRIEND2_USERNAME = user2)
   OR (FRIEND1_USERNAME = user2 AND FRIEND2_USERNAME = user1);

RETURN has_friends;
END;
/

create FUNCTION GET_FRIENDS_COUNT (
    username IN VARCHAR2
) RETURN NUMBER IS
         friend_count NUMBER;
BEGIN
SELECT COUNT(*)
INTO friend_count
FROM FRIENDSHIPS
WHERE FRIEND1_USERNAME = username
   OR FRIEND2_USERNAME = username;

RETURN friend_count;
END;
/

