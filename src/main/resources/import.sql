INSERT INTO tb_user (id, email, github_url, additional_url, name, password, active, created_at, updated_at)
VALUES
    ('0a10cc2c-d527-44bc-912f-95ea6aa94961', 'breno@gmail.com', 'https://github.com/brenooduarte', 'www.linkedin.com/in/breno-freitas-duarte', 'Breno Duarte', '08/NKMXah89RNC+4zd6YBLZQItLF6Aa85D7gbeBKXOA=', true, '2023-08-11T00:15:49.483342884', '2023-08-11T00:15:49.483342884'),
    ('c114fe61-f54f-4858-9c4d-9f6d9d8207aa', 'harlon@gmail.com', 'https://github.com/HarlonGarcia', 'www.linkedin.com/in/harlon-garcia', 'Harlon Garcia', '08/NKMXah89RNC+4zd6YBLZQItLF6Aa85D7gbeBKXOA=', true, '2023-08-11T00:15:49.483342884', '2023-08-11T00:15:49.483342884'),
    ('3aec17ef-b1ac-45a0-9963-3a68583b41b5', 'pedro@gmail.com', 'https://github.com/Palc3301', 'www.linkedin.com/in/pedro-arruda', 'Pedro Arruda', '08/NKMXah89RNC+4zd6YBLZQItLF6Aa85D7gbeBKXOA=', true, '2023-08-11T00:15:49.483342884', '2023-08-11T00:15:49.483342884'),
    ('3e96371b-2aaa-4a64-9a1d-209f9851c03f', 'pedro.lucas@gmail.com', 'https://github.com/PedroLucasNeto', 'www.linkedin.com/in/pedrolucasneto', 'Pedro Lucas', '08/NKMXah89RNC+4zd6YBLZQItLF6Aa85D7gbeBKXOA=', true, '2023-08-11T00:15:49.483342884', '2023-08-11T00:15:49.483342884');
-- The password that is encrypted in these three users is 12345678

INSERT INTO tb_role (id, name)
VALUES
    ('28439d95-3df9-41ac-9718-dc4a906ef9a9', 'ADMIN'),
    ('fc136e23-4bc9-468c-b4dd-cf99d0f1bd8b', 'USER');

INSERT INTO tb_label (id, title, description)
VALUES
    ('4a4b8953-7f30-481d-9a4d-ec305039237a', 'Challenger', 'Bla bla bla'),
    ('ae51aad2-d6ff-403e-8230-4b71afb6d580', 'Avançado', 'Lorem ipsum');

INSERT INTO tb_category (id, name)
VALUES
    ('436de9e1-5ae5-470f-a014-e2d096376ec3', 'Backend'),
    ('b47afe2a-e870-45f6-a484-f2352ff92468', 'Frontend'),
    ('f35235bc-a07c-4a70-917d-347109bf4260', 'FullStack');

INSERT INTO tb_challenge (id, description, title, author_id, active, status, category_id, created_at)
VALUES
    ('dd57f958-586b-47d4-8868-f6f7e78aa387', 'Api de Rede Social com NodeJS e NoSQL', 'NodeJS + NoSQL', '0a10cc2c-d527-44bc-912f-95ea6aa94961', true, 'TO_BEGIN', '436de9e1-5ae5-470f-a014-e2d096376ec3', '2023-08-11T00:15:49.483342884'),
    ('275a42b8-0d84-47fc-90d5-2b7c2b94c4b8', '# Header da descrição/n/n> pnpm install && pnpm dev', 'Interface Spotify', 'c114fe61-f54f-4858-9c4d-9f6d9d8207aa', true, 'TO_BEGIN', 'b47afe2a-e870-45f6-a484-f2352ff92468', '2023-08-12T00:15:49.483342884');

INSERT INTO tb_technology (id, name, color, documentation_link)
VALUES
    ('8e423476-1326-4291-b106-332eb0198a04', 'SQL', 'AAAaaa', 'documentation_link'),
    ('687cdccc-f13a-4474-a5fb-c28573834624', 'Java', '000111', 'documentation_link'),
    ('b4ba40d6-7bbd-44a5-8bd1-38847d4b663a', 'React', '656065', 'documentation_link');

INSERT INTO tb_solution (id, author_id, challenge_id, deploy_url, repository_url)
VALUES
    ('3460d1b8-f756-4425-b79f-a76b07a006ba', 'c114fe61-f54f-4858-9c4d-9f6d9d8207aa', 'dd57f958-586b-47d4-8868-f6f7e78aa387', 'heroku.com', 'github.com/harlon/banco-tal'),
    ('c3e658fe-a3ff-4715-b3de-642a8d7a85a0', '3aec17ef-b1ac-45a0-9963-3a68583b41b5', 'dd57f958-586b-47d4-8868-f6f7e78aa387', 'netlify.com', 'github.com/pedro/banco-tal');

INSERT INTO tb_user_role (user_id, role_id)
VALUES
    ('0a10cc2c-d527-44bc-912f-95ea6aa94961', '28439d95-3df9-41ac-9718-dc4a906ef9a9'),
    ('0a10cc2c-d527-44bc-912f-95ea6aa94961', 'fc136e23-4bc9-468c-b4dd-cf99d0f1bd8b'),
    ('c114fe61-f54f-4858-9c4d-9f6d9d8207aa', '28439d95-3df9-41ac-9718-dc4a906ef9a9'),
    ('3aec17ef-b1ac-45a0-9963-3a68583b41b5', 'fc136e23-4bc9-468c-b4dd-cf99d0f1bd8b');

INSERT INTO tb_user_label (user_id, label_id)
VALUES
    ('0a10cc2c-d527-44bc-912f-95ea6aa94961', '4a4b8953-7f30-481d-9a4d-ec305039237a'),
    ('0a10cc2c-d527-44bc-912f-95ea6aa94961', 'ae51aad2-d6ff-403e-8230-4b71afb6d580'),
    ('c114fe61-f54f-4858-9c4d-9f6d9d8207aa', 'ae51aad2-d6ff-403e-8230-4b71afb6d580');

INSERT INTO tb_challenge_technology (id, challenge_id, technology_id)
VALUES
    ('29e148ef-6f85-4379-b612-0e08f84928f9', 'dd57f958-586b-47d4-8868-f6f7e78aa387', '8e423476-1326-4291-b106-332eb0198a04'),
    ('1cfb6b37-8da2-4420-8e09-632ed0a77504', 'dd57f958-586b-47d4-8868-f6f7e78aa387', '687cdccc-f13a-4474-a5fb-c28573834624'),
    ('c8e25869-95d1-45d1-b638-21d7fed8e134', '275a42b8-0d84-47fc-90d5-2b7c2b94c4b8', 'b4ba40d6-7bbd-44a5-8bd1-38847d4b663a');

INSERT INTO tb_like (participant_id, solution_id)
VALUES
	('0a10cc2c-d527-44bc-912f-95ea6aa94961', '3460d1b8-f756-4425-b79f-a76b07a006ba'),
	('0a10cc2c-d527-44bc-912f-95ea6aa94961', 'c3e658fe-a3ff-4715-b3de-642a8d7a85a0'),
	('c114fe61-f54f-4858-9c4d-9f6d9d8207aa', 'c3e658fe-a3ff-4715-b3de-642a8d7a85a0'),
	('3aec17ef-b1ac-45a0-9963-3a68583b41b5', 'c3e658fe-a3ff-4715-b3de-642a8d7a85a0');

INSERT INTO tb_participant (challenge_id, participant_id)
VALUES
('dd57f958-586b-47d4-8868-f6f7e78aa387', '0a10cc2c-d527-44bc-912f-95ea6aa94961'), -- Breno participa do desafio NodeJS + NoSQL
('275a42b8-0d84-47fc-90d5-2b7c2b94c4b8', 'c114fe61-f54f-4858-9c4d-9f6d9d8207aa'), -- Harlon participa do desafio Frontend do banco tal
('dd57f958-586b-47d4-8868-f6f7e78aa387', '3aec17ef-b1ac-45a0-9963-3a68583b41b5'), -- Pedro participa do desafio NodeJS + NoSQL
('275a42b8-0d84-47fc-90d5-2b7c2b94c4b8', '3e96371b-2aaa-4a64-9a1d-209f9851c03f'), -- Pedro Lucas participa do desafio Frontend do banco tal

-- Inserindo Harlon em todos os desafios
('dd57f958-586b-47d4-8868-f6f7e78aa387', 'c114fe61-f54f-4858-9c4d-9f6d9d8207aa'), -- Harlon participa do desafio NodeJS + NoSQL

-- Inserindo todos os participantes no desafio NodeJS + NoSQL
('dd57f958-586b-47d4-8868-f6f7e78aa387', '0a10cc2c-d527-44bc-912f-95ea6aa94961'), -- Breno
('dd57f958-586b-47d4-8868-f6f7e78aa387', 'c114fe61-f54f-4858-9c4d-9f6d9d8207aa'), -- Harlon
('dd57f958-586b-47d4-8868-f6f7e78aa387', '3aec17ef-b1ac-45a0-9963-3a68583b41b5'), -- Pedro Arruda
('dd57f958-586b-47d4-8868-f6f7e78aa387', '3e96371b-2aaa-4a64-9a1d-209f9851c03f');  -- Pedro Lucas

