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
    HEIGHT   NUMBER(5, 2)
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


