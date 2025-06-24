INSERT INTO public.users
(id, email, full_name, "password", password_reset_token, created_date, updated_date,
 created_by, updated_by)
VALUES (uuid_generate_v4(), 'nafieak25@gmail.com', 'nafia Ak',
        '$2a$10$5dTfyQAzV9EGBQ/dQHKkwOuUBfWrsAA8d8SJu4JUmjYGLMD.1fPIu', 'd914d862-d201-4aa6-94a1-f042648519dd',
         '2025-01-28 18:19:36.690', '2025-01-28 13:58:30.329', 'nafieak25@gmail.com', 'nafia');


-- Insert token_confirmation
INSERT INTO public.token_confirmation
(id, "token", created_at, expires_at, confirmed_at, user_id)
VALUES (13,
        'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbWluZS5rQHZpcnR1b2NvZGUuY29tIiwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfU1VQRVJfQURNSU4ifV19.fGaF2e-fXwvtecZ5oG_cDsjS3O_T9liI0DkqIMawrBw',
        '2025-01-29 14:10:36.317', '2030-10-23 14:10:36.317', NULL,
        (SELECT id FROM users WHERE email = 'nafieak25@gmail.com'));