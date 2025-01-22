# Dokumentacja Bazy Danych

---

## Opis rozwiązania
Baza danych przechowuje informacje potrzebne do działania aplikacji DrGym.
Zawiera informacje o użytkownikach, znajomościach między nimi, bazowych ćwiczeniach z połączonymi mięśniami, aktywnościach i treningach użytkowników, ich postach oraz reakcjach na posty.
Każdy użytkownik ma możliwość dodawania znajomych, tworzenia treningów dodając do nich własne aktywności, publikowania postów z utworzonymi treningami oraz reagowania na posty innych użytkowników.
Posty użytkowników są dostępne tylko dla ich znajomych.
Użytkownik, zakładając konto, otrzymuje token weryfikacyjny na podany adres e-mail, który musi potwierdzić, aby móc korzystać z aplikacji.

---

## Struktura bazy danych

### Tabele

#### **Users**
Przechowuje podstawowe informacje o użytkownikach.

Kolumny:
- `username`: unikalna nazwa
- `name`: imię 
- `surname`: nazwisko
- `email`: adres e-mail
- `password`: zaszyfrowane hasło
- `weight`: waga
- `height`: wzrost
- `verified`: czy konto zostało zweryfikowane przez e-mail
- `favorite_exercise`: ulubione ćwiczenie
- `avatar`: kolor tła awataru

Wyzwalacze:
- `update_token_on_verified`: ustawia wartość `verified` na `true` po weryfikacji konta

#### **Token**
Przechowuje informacje o tokenach potrzebnych do weryfikacji oraz resetowania hasła.

Kolumny:
- `email`: adres e-mail
- `verification_token`: token weryfikacyjny e-mail
- `reset_token`: token resetowania hasła
- `reset_expiry`: data ważności tokenu resetowania hasła

#### **Friendship_invitations**
Przechowuje informacje o zaproszeniach do znajomych.

Kolumny:
- `id`: unikalny identyfikator
- `who_send_username`: nazwa użytkownika, który wysłał zaproszenie
- `who_receive_username`: nazwa użytkownika, do którego wysłano zaproszenie
- `send_time`: data wysłania zaproszenia

Relacje:
- Klucz obcy `fk_who_receive` odnosi się do kolumny `username` w tabeli `users`.
- Klucz obcy `fk_who_send` odnosi się do kolumny `username` w tabeli `users`.

Wyzwalacze:
- `tg_block_duplicate_invitations`: blokuje duplikaty zaproszeń

#### **Friendships**
Przechowuje informacje o znajomościach między użytkownikami.

Kolumny:
- `id`: unikalny identyfikator
- `friend1_username`: nazwa pierwszego użytkownika
- `friend2_username`: nazwa drugiego użytkownika
- `created_at`: data utworzenia znajomości

Relacje:
- Klucz obcy `fk_user1` odnosi się do kolumny `username` w tabeli `users`.
- Klucz obcy `fk_user2` odnosi się do kolumny `username` w tabeli `users`.

Wyzwalacze:
- `delete_invitation_after_friendship`: usuwa zaproszenie po zaakceptowaniu znajomości

#### **Exercises**
Przechowuje informacje o bazowych ćwiczeniach.

Kolumny:
- `exercise_id`: unikalny identyfikator
- `type`: typ ćwiczenia (C - cardio, S - strength, F - crossfit)
- `kcal_burned`: ilość spalonych kalorii na godzinę
- `name`: nazwa ćwiczenia
- `video_id`: identyfikator wideo z YouTube z instruktażem

#### **Muscles**
Przechowuje informacje o mięśniach.

Kolumny:
- `muscle_id`: unikalny identyfikator
- `muscle_name`: nazwa mięśnia

#### **Exercises_Muscles**
Przechowuje informacje o relacji między ćwiczeniami a mięśniami.

Kolumny:
- `id`: unikalny identyfikator
- `exercise_id`: identyfikator ćwiczenia
- `muscle_id`: identyfikator mięśnia

Relacje:
- Klucz obcy `exercise_fk` odnosi się do kolumny `exercise_id` w tabeli `exercises`.
- Klucz obcy `muscle_fk` odnosi się do kolumny `muscle_id` w tabeli `muscles`.

#### **Activities**
Przechowuje informacje o aktywnościach użytkowników na podstawie bazowych ćwiczeń.

Kolumny:
- `activity_id`: unikalny identyfikator
- `exercise_id`: identyfikator ćwiczenia
- `reps`: liczba powtórzeń
- `weight`: ciężar
- `duration`: czas trwania
- `workout_id`: identyfikator treningu

Relacje:
- Klucz obcy `fk_exercise` odnosi się do kolumny `exercise_id` w tabeli `exercises`.
- Klucz obcy `fk_workout` odnosi się do kolumny `workout_id` w tabeli `workouts`.

#### **Workouts**
Przechowuje informacje o treningach użytkowników.

Kolumny:
- `workout_id`: unikalny identyfikator
- `start_datetime`: data i godzina rozpoczęcia
- `end_datetime`: data i godzina zakończenia
- `username`: nazwa użytkownika
- `description`: opis treningu
- `created_datetime`: data utworzenia
- `is_posted`: czy trening został opublikowany
- `scheduled`: co ile dni trening ma się powtarzać

Relacje:
- Klucz obcy `fk_workout_username` odnosi się do kolumny `username` w tabeli `users`.

#### **Posts**
Przechowuje informacje o postach utworzonych przez użytkowników.

Kolumny:
- `post_id`: unikalny identyfikator
- `author_username`: nazwa autora
- `post_date`: data utworzenia
- `title`: tytuł
- `workout_id`: identyfikator treningu zawartego w poście
- `content`: treść posta

Relacje:
- Klucz obcy `fk_username` odnosi się do kolumny `username` w tabeli `users`.
- Klucz obcy `fk_workout_id` odnosi się do kolumny `workout_id` w tabeli `workouts`.

Wyzwalacze:
- `update_is_posted`: ustawia wartość `is_posted` w konkretnym treningu na `true` po dodaniu posta

#### **Post_reactions**
Przechowuje informacje o reakcjach użytkowników na posty.

Kolumny:
- `post_reaction_id`: unikalny identyfikator
- `post_id`: identyfikator posta
- `author_username`: nazwa autora reakcji

Relacje:
- Klucz obcy `fk_reaction_post_id` odnosi się do kolumny `post_id` w tabeli `posts`.
- Klucz obcy `fk_reaction_username` odnosi się do kolumny `username` w tabeli `users`.

#### **Post_comments**
Przechowuje informacje o komentarzach użytkowników pod postami.

Kolumny:
- `post_comment_id`: unikalny identyfikator
- `post_id`: identyfikator posta
- `author_username`: nazwa autora komentarza
- `content`: treść komentarza
- `post_comment_date`: data utworzenia

Relacje:
- Klucz obcy `fk_author_username` odnosi się do kolumny `username` w tabeli `users`.
- Klucz obcy `fk_post_id` odnosi się do kolumny `post_id` w tabeli `posts`.

### Funkcje

#### **Are_friends**
Sprawdza, czy dwaj użytkownicy są znajomymi.

#### **Get_friends_count**
Zwraca liczbę znajomych danego użytkownika.

#### **Get_user_daily_exercise_count**
Zwraca liczbę aktywności danego użytkownika w ciągu jednego dnia dla konkretnego okresu.

#### **Get_user_exercises_in_period**
Zwraca liczbę aktywności i przećwiczonych mięśni w konkretnym okresie.

#### **Get_exercise_ranking**
Zwraca ranking ciężarów wybranego ćwiczenia dla konkretnego użytkownika i jego znajomych.

### Procedury

#### **Get_user_friend_requests_with_avatar**
Zwraca listę zaproszeń do znajomych z awatarem (kolorem) każdego użytkownika.

#### **Get_user_friends_with_avatar**
Zwraca listę znajomych z awatarem (kolorem) każdego użytkownika.