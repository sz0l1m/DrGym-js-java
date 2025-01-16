create sequence TOKEN_SEQUENCE
    /

create table USERS
(
    USERNAME          VARCHAR2(50)  not null
        primary key,
    NAME              VARCHAR2(50)  not null,
    SURNAME           VARCHAR2(50)  not null,
    EMAIL             VARCHAR2(100) not null
        unique,
    PASSWORD          VARCHAR2(100) not null,
    WEIGHT            NUMBER(5, 2),
    HEIGHT            NUMBER(5, 2),
    VERIFIED          NUMBER(1) default 0,
    FAVORITE_EXERCISE NUMBER
)
    /

create trigger UPDATE_TOKEN_ON_VERIFIED
    after update of VERIFIED
    on USERS
    for each row
    when (NEW.verified = 1)
BEGIN
    UPDATE TOKEN
    SET VERIFICATION_TOKEN = NULL
    WHERE EMAIL = :NEW.EMAIL;
END;
/

create table MUSCLES
(
    MUSCLE_ID   NUMBER(4)    not null
        primary key,
    MUSCLE_NAME VARCHAR2(40) not null
)
    /

create table TOKEN
(
    EMAIL              VARCHAR2(255) not null
        primary key
        constraint EMAIL_FK
            references USERS (EMAIL)
                on delete cascade,
    VERIFICATION_TOKEN VARCHAR2(255),
    RESET_TOKEN        VARCHAR2(255),
    RESET_EXPIRY       TIMESTAMP(6)
)
    /

create table WORKOUTS
(
    WORKOUT_ID       NUMBER default "Z13"."ISEQ$$_643017".nextval generated as identity
        primary key,
    START_DATETIME   TIMESTAMP(6),
    USERNAME         VARCHAR2(255)
        constraint FK_WORKOUTS_USERNAME
            references USERS
                on delete cascade,
    END_DATETIME     TIMESTAMP(6),
    DESCRIPTION      VARCHAR2(255),
    CREATED_DATETIME TIMESTAMP(6),
    IS_POSTED        NUMBER default 0
)
    /

create index IDX_WORKOUT_START_DATE_ORDER
    on WORKOUTS (START_DATETIME)
    /

create table EXERCISES
(
    EXERCISE_ID NUMBER generated as identity
        primary key,
    TYPE        CHAR,
    KCAL_BURNED NUMBER,
    NAME        VARCHAR2(255)
)
    /

create table ACTIVITIES
(
    ACTIVITY_ID NUMBER generated as identity
        primary key,
    EXERCISE_ID NUMBER not null
        constraint FK_EXERCISE
            references EXERCISES
            on delete cascade,
    REPS        NUMBER,
    WEIGHT      NUMBER,
    DURATION    TIMESTAMP(6),
    WORKOUT_ID  NUMBER
        constraint FK_WORKOUT
            references WORKOUTS
            on delete cascade
)
    /

create table EXERCISES_MUSCLES
(
    ID          NUMBER generated as identity
        primary key,
    EXERCISE_ID NUMBER
        constraint EXERCISE_FK
            references EXERCISES
            on delete cascade,
    MUSCLE_ID   NUMBER
        constraint MUSCLE_FK
            references MUSCLES
)
    /

create table FRIENDSHIPS
(
    ID               NUMBER generated as identity
        primary key,
    FRIEND1_USERNAME VARCHAR2(255)
        constraint FK_USER1
            references USERS
                on delete cascade,
    FRIEND2_USERNAME VARCHAR2(255)
        constraint FK_USER2
            references USERS
                on delete cascade,
    CREATED_AT       TIMESTAMP(6)
)
    /

create trigger DELETE_INVITATION_AFTER_FRIENDSHIP
    after insert
    on FRIENDSHIPS
    for each row
BEGIN
    DELETE FROM friendship_invitations
    WHERE (who_send_username = :NEW.friend1_username AND who_receive_username = :NEW.friend2_username)
       OR (who_send_username = :NEW.friend2_username AND who_receive_username = :NEW.friend1_username);
END;
/

create table FRIENDSHIP_INVITATIONS
(
    ID                   NUMBER generated as identity
        primary key,
    WHO_SEND_USERNAME    VARCHAR2(255)
        constraint FK_WHO_SEND
            references USERS
                on delete cascade,
    WHO_RECEIVE_USERNAME VARCHAR2(255)
        constraint FK_WHO_RECEIVE
            references USERS
                on delete cascade,
    SEND_TIME            TIMESTAMP(6),
    constraint UNIQUE_FRIENDSHIP_INVITATION
        unique (WHO_SEND_USERNAME, WHO_RECEIVE_USERNAME)
)
    /

create trigger TG_BLOCK_DUPLICATE_INVITATIONS
    before insert
    on FRIENDSHIP_INVITATIONS
    for each row
DECLARE
invitation_count NUMBER;
BEGIN
SELECT COUNT(*)
INTO invitation_count
FROM FRIENDSHIP_INVITATIONS
WHERE WHO_SEND_USERNAME = :NEW.WHO_RECEIVE_USERNAME
  AND WHO_RECEIVE_USERNAME = :NEW.WHO_SEND_USERNAME;

IF invitation_count > 0 THEN
        RAISE_APPLICATION_ERROR(-20001, 'The invitation receiver has already sent the sender an invitation.');
END IF;
END;
/

create table POSTS
(
    POST_ID         NUMBER       default "Z13"."ISEQ$$_643189".nextval generated as identity
        primary key,
    AUTHOR_USERNAME VARCHAR2(50)  not null
        constraint FK_USERNAME
            references USERS
                on delete cascade,
    POST_DATE       TIMESTAMP(6) default CURRENT_TIMESTAMP,
    TITLE           VARCHAR2(100) not null,
    WORKOUT_ID      NUMBER(8)
        constraint FK_WORKOUT_ID
            references WORKOUTS
                on delete cascade,
    CONTENT         VARCHAR2(200)
)
    /

create index IDX_POST_DATE_ORDER
    on POSTS (POST_DATE)
    /

create trigger UPDATE_IS_POSTED
    after insert
    on POSTS
    for each row
BEGIN
    UPDATE workouts
    SET is_posted = 1
    WHERE workout_id = :NEW.workout_id;
END;
/

create table POST_COMMENTS
(
    POST_COMMENT_ID   NUMBER(8)    default "Z13"."ISEQ$$_643195".nextval generated as identity
		primary key,
    POST_ID           NUMBER(8)    not null
        constraint FK_POST_ID
            references POSTS,
    AUTHOR_USERNAME   VARCHAR2(50) not null
        constraint FK_AUTHOR_USERNAME
            references USERS,
    CONTENT           CLOB,
    POST_COMMENT_DATE TIMESTAMP(6) default CURRENT_TIMESTAMP
)
    /

create table POST_REACTIONS
(
    POST_REACTION_ID NUMBER(8) generated as identity
        primary key,
    POST_ID          NUMBER(8)    not null
        constraint FK_REACTION_POST_ID
            references POSTS
                on delete cascade,
    AUTHOR_USERNAME  VARCHAR2(40) not null
        constraint FK_REACTOR_USERNAME
            references USERS
                on delete cascade,
    constraint UNIQUE_REACTION_AUTHOR
        unique (POST_ID, AUTHOR_USERNAME)
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

create FUNCTION GET_USERS_EXERCISES_IN_PERIOD(
    p_username IN VARCHAR2,
    p_start_date IN VARCHAR2,
    p_end_date IN VARCHAR2
) RETURN CLOB IS
         data_json CLOB;
BEGIN
SELECT
    JSON_ARRAYAGG(
            JSON_OBJECT(
                    'name' VALUE exercises.NAME,
                    'muscles' VALUE (
                            SELECT JSON_ARRAYAGG(muscles.MUSCLE_NAME)
                            FROM EXERCISES_MUSCLES exercises_muscles
                                     JOIN MUSCLES muscles ON exercises_muscles.MUSCLE_ID = muscles.MUSCLE_ID
                            WHERE exercises_muscles.EXERCISE_ID = exercises.EXERCISE_ID
                        )
            )
    )
INTO data_json
FROM EXERCISES exercises
         JOIN ACTIVITIES activities ON activities.EXERCISE_ID = exercises.EXERCISE_ID
         JOIN WORKOUTS workouts ON workouts.WORKOUT_ID = activities.WORKOUT_ID
WHERE workouts.USERNAME = p_username
  AND workouts.START_DATETIME BETWEEN TO_DATE(p_start_date, 'YYYY-MM-DD') AND TO_DATE(p_end_date, 'YYYY-MM-DD');

RETURN data_json;
END;
/

create FUNCTION GET_USER_DAILY_EXERCISE_COUNT(
    p_username IN VARCHAR2,
    p_start_date IN VARCHAR2,
    p_end_date IN VARCHAR2
) RETURN CLOB IS
         data_json CLOB;
BEGIN
SELECT JSON_ARRAYAGG(
               JSON_OBJECT(
                       'date' VALUE TO_CHAR(workouts.END_DATETIME, 'YYYY-MM-DD'),
                       'count' VALUE COUNT(*),
                       'level' VALUE CASE
                                             WHEN COUNT(*) <= 0 THEN 0
                                             WHEN COUNT(*) <= 6 THEN 1
                                             WHEN COUNT(*) <= 12 THEN 2
                                             WHEN COUNT(*) <= 18 THEN 2
                                             ELSE 3
                               END
               )
       )
INTO data_json
FROM WORKOUTS workouts
         JOIN ACTIVITIES activities ON activities.WORKOUT_ID = workouts.WORKOUT_ID
WHERE workouts.USERNAME = p_username
  AND workouts.END_DATETIME BETWEEN TO_DATE(p_start_date, 'YYYY-MM-DD') AND TO_DATE(p_end_date, 'YYYY-MM-DD')
GROUP BY TO_CHAR(workouts.END_DATETIME, 'YYYY-MM-DD')
HAVING COUNT(*) > 0;

RETURN data_json;
END;
/

