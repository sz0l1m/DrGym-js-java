SELECT u.username, u.email, w.start_datetime, w.end_datetime
FROM users u
         JOIN WORKOUTS w ON u.username = w.username
WHERE u.verified = 1
  AND w.start_datetime >= TO_DATE('2024-08-01', 'YYYY-MM-DD')
ORDER BY w.start_datetime;

SELECT u.username, COUNT(a.activity_id) AS activities_count
FROM users u
         JOIN workouts w ON u.username = w.username
         JOIN activities a ON w.workout_id = a.workout_id
GROUP BY u.username
HAVING COUNT(w.workout_id) >= 7
ORDER BY activities_count DESC;

SELECT u.username, u.email, w.start_datetime, w.end_datetime
FROM users u
         JOIN WORKOUTS w ON u.username = w.username
WHERE u.verified = 1
  AND w.start_datetime BETWEEN TO_DATE('2024-08-01', 'YYYY-MM-DD') AND TO_DATE('2024-12-31', 'YYYY-MM-DD')
ORDER BY w.start_datetime;

SELECT u.username, COUNT(w.workout_id) AS workouts_count
FROM users u
         JOIN workouts w ON u.username = w.username
GROUP BY u.username
ORDER BY workouts_count DESC;

SELECT u.username, u.email, w.start_datetime, w.end_datetime, w.description
FROM users u
         JOIN WORKOUTS w ON u.username = w.username
WHERE u.verified = 1
  AND w.description LIKE '%better%'
ORDER BY w.start_datetime;

-- funkcje
DECLARE
exercise_count CLOB;
BEGIN
    exercise_count := GET_USER_DAILY_EXERCISE_COUNT('skuter', '2024-01-01', '2025-01-01');
    DBMS_OUTPUT.PUT_LINE('Daily exercise count: ' || exercise_count);
END;
/

DECLARE
exercises CLOB;
BEGIN
    exercises := GET_USERS_EXERCISES_IN_PERIOD('skuter', '2024-01-01', '2025-01-01');
    DBMS_OUTPUT.PUT_LINE('Exercises in period: ' || exercises);
END;
/

DECLARE
is_friend NUMBER;
BEGIN
    is_friend := ARE_FRIENDS('skuter', 'milosz');
    DBMS_OUTPUT.PUT_LINE('Are friends: ' || is_friend);
END;
/

DECLARE
    result_clob CLOB;
BEGIN
    result_clob := GET_EXERCISE_RANKING('skuter', 1);
    DBMS_OUTPUT.PUT_LINE(result_clob);
END;
/

-- procedury
DECLARE
friends SYS_REFCURSOR;
    friend_username USERS.USERNAME%TYPE;
    avatar USERS.AVATAR%TYPE;
BEGIN
    GET_USER_FRIENDS_WITH_AVATAR('skuter', friends);
    LOOP
FETCH friends INTO friend_username, avatar;
        EXIT WHEN friends%NOTFOUND;
        DBMS_OUTPUT.PUT_LINE('Friend: ' || friend_username || ', Avatar: ' || avatar);
END LOOP;
CLOSE friends;
END;
/

DECLARE
requests SYS_REFCURSOR;
    request_id NUMBER;
    who_send_username USERS.USERNAME%TYPE;
    who_receive_username USERS.USERNAME%TYPE;
    avatar USERS.AVATAR%TYPE;
BEGIN
    GET_USER_FRIEND_REQUESTS_WITH_AVATAR('skuter', requests);
    LOOP
FETCH requests INTO request_id, who_send_username, who_receive_username, avatar;
        EXIT WHEN requests%NOTFOUND;
        DBMS_OUTPUT.PUT_LINE('Request ID: ' || request_id || ', Sender: ' || who_send_username || ', Receiver: ' || who_receive_username || ', Avatar: ' || avatar);
END LOOP;
CLOSE requests;
END;
/

-- wyzwalacze
DELETE FROM friendships
WHERE (friend1_username = 'mandrysz' AND friend2_username = 'pedziwiatr')
   OR (friend1_username = 'pedziwiatr' AND friend2_username = 'mandrysz');
commit;

INSERT INTO friendship_invitations (who_send_username, who_receive_username)
VALUES ('mandrysz', 'pedziwiatr');
commit;

INSERT INTO friendships (friend1_username, friend2_username)
VALUES ('mandrysz', 'pedziwiatr');
commit;

SELECT * FROM friendship_invitations
WHERE who_send_username = 'mandrysz' AND who_receive_username = 'pedziwiatr';



INSERT INTO workouts (username, start_datetime, end_datetime, description)
VALUES ('skuter', TO_TIMESTAMP('2025-01-20 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-01-20 11:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Test workout');
commit;

INSERT INTO posts (author_username, title, workout_id, content)
VALUES ('skuter', 'Test Post', (SELECT workout_id FROM workouts WHERE username = 'skuter' AND description = 'Test workout'), 'Test content');
commit;

SELECT is_posted FROM workouts
WHERE username = 'skuter' AND description = 'Test workout';

DELETE FROM posts
WHERE author_username = 'skuter' AND title = 'Test Post' AND content = 'Test content';
commit;

DELETE FROM workouts
WHERE username = 'skuter' AND description = 'Test workout';
commit;



DELETE FROM friendship_invitations WHERE who_send_username IN ('skuter', 'pedziwiatr') OR who_receive_username IN ('skuter', 'pedziwiatr');
commit;

BEGIN
    INSERT INTO friendship_invitations (who_send_username, who_receive_username)
    VALUES ('skuter', 'pedziwiatr');
    INSERT INTO friendship_invitations (who_send_username, who_receive_username)
    VALUES ('pedziwiatr', 'skuter');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('TG_BLOCK_DUPLICATE_INVITATIONS: ' || SQLERRM);
END;
/

DELETE FROM friendship_invitations WHERE who_send_username IN ('skuter', 'pedziwiatr') OR who_receive_username IN ('skuter', 'pedziwiatr');
commit;



DELETE FROM users WHERE username = 'user1';
commit;

INSERT INTO users (username, name, surname, email, password, weight, height, verified)
VALUES ('user1', 'Test', 'User', 'testuser@example.com', 'password', 70, 175, 0);
commit;

INSERT INTO token (email, verification_token)
VALUES ('testuser@example.com', 'generated_verification_token');
commit;

UPDATE users SET verified = 1 WHERE username = 'user1';
SELECT * FROM token WHERE email = (SELECT email FROM users WHERE username = 'user1');

DELETE FROM users WHERE username = 'user1';
commit;