insert into tb_user (id, email, github_url, additional_url, name, password, active, created_at, updated_at) values ('0a10cc2c-d527-44bc-912f-95ea6aa94961', 'breno@gmail.com', 'https://github.com/brenooduarte', 'www.linkedin.com/in/breno-freitas-duarte', 'Breno Duarte', 'Wwtf1LEt+oBYjbew/WeFdU+HFW+oMIGDhTy+E6Q0f4Q=', true, '2023-08-11T00:15:49.483342884', '2023-08-11T00:15:49.483342884');
insert into tb_user (id, email, github_url, additional_url, name, password, active, created_at, updated_at) values ('c114fe61-f54f-4858-9c4d-9f6d9d8207aa', 'harlon@gmail.com', 'https://github.com/HarlonGarcia', 'www.linkedin.com/in/harlon-garcia', 'Harlon Garcia', 'Wwtf1LEt+oBYjbew/WeFdU+HFW+oMIGDhTy+E6Q0f4Q=', true, '2023-08-11T00:15:49.483342884', '2023-08-11T00:15:49.483342884');
insert into tb_user (id, email, github_url, additional_url, name, password, active, created_at, updated_at) values ('3aec17ef-b1ac-45a0-9963-3a68583b41b5', 'pedro@gmail.com', 'https://github.com/Palc3301', 'www.linkedin.com/in/pedro-arruda', 'Pedro Arruda', 'Wwtf1LEt+oBYjbew/WeFdU+HFW+oMIGDhTy+E6Q0f4Q=', true, '2023-08-11T00:15:49.483342884', '2023-08-11T00:15:49.483342884');
-- The password that is encrypted in these three users is 12345

insert into tb_role (id, name) values ('28439d95-3df9-41ac-9718-dc4a906ef9a9', 'ADMIN');
insert into tb_role (id, name) values ('fc136e23-4bc9-468c-b4dd-cf99d0f1bd8b', 'USER');

insert into tb_user_role (user_id, role_id) values ('0a10cc2c-d527-44bc-912f-95ea6aa94961', '28439d95-3df9-41ac-9718-dc4a906ef9a9');
insert into tb_user_role (user_id, role_id) values ('0a10cc2c-d527-44bc-912f-95ea6aa94961', 'fc136e23-4bc9-468c-b4dd-cf99d0f1bd8b');
insert into tb_user_role (user_id, role_id) values ('c114fe61-f54f-4858-9c4d-9f6d9d8207aa', '28439d95-3df9-41ac-9718-dc4a906ef9a9');
insert into tb_user_role (user_id, role_id) values ('3aec17ef-b1ac-45a0-9963-3a68583b41b5', 'fc136e23-4bc9-468c-b4dd-cf99d0f1bd8b');

insert into tb_label (id, title, description) values ('4a4b8953-7f30-481d-9a4d-ec305039237a', '', '');
insert into tb_label (id, title, description) values ('ae51aad2-d6ff-403e-8230-4b71afb6d580', '', '');

insert into tb_user_label (user_id, label_id) values ('0a10cc2c-d527-44bc-912f-95ea6aa94961', '4a4b8953-7f30-481d-9a4d-ec305039237a');
insert into tb_user_label (user_id, label_id) values ('0a10cc2c-d527-44bc-912f-95ea6aa94961', 'ae51aad2-d6ff-403e-8230-4b71afb6d580');
insert into tb_user_label (user_id, label_id) values ('c114fe61-f54f-4858-9c4d-9f6d9d8207aa', 'ae51aad2-d6ff-403e-8230-4b71afb6d580');

insert into tb_category (id, name) values ('436de9e1-5ae5-470f-a014-e2d096376ec3', 'Backend');
insert into tb_category (id, name) values ('b47afe2a-e870-45f6-a484-f2352ff92468', 'Frontend');
insert into tb_category (id, name) values ('f35235bc-a07c-4a70-917d-347109bf4260', 'FullStack');

insert into tb_challenge (id, description, title, author_id, active, status, category_id, created_at) values ('dd57f958-586b-47d4-8868-f6f7e78aa387', 'Api de Rede Social com NodeJS e NoSQL', 'NodeJS + NoSQL', '0a10cc2c-d527-44bc-912f-95ea6aa94961', true, 'TO_BEGIN', '436de9e1-5ae5-470f-a014-e2d096376ec3', '2023-08-11T00:15:49.483342884');
insert into tb_challenge (id, description, title, author_id, active, status, category_id, created_at) values ('275a42b8-0d84-47fc-90d5-2b7c2b94c4b8', 'Fazer o front end do banco tal', 'Front end do banco tal', 'c114fe61-f54f-4858-9c4d-9f6d9d8207aa', true, 'TO_BEGIN', 'b47afe2a-e870-45f6-a484-f2352ff92468', '2023-08-12T00:15:49.483342884');

insert into tb_technology (id, name, color, documentation_link) values ('8e423476-1326-4291-b106-332eb0198a04', 'SQL', '#aaa', 'documentation_link');
insert into tb_technology (id, name, color, documentation_link) values ('687cdccc-f13a-4474-a5fb-c28573834624', 'Java', '#111', 'documentation_link');
insert into tb_technology (id, name, color, documentation_link) values ('b4ba40d6-7bbd-44a5-8bd1-38847d4b663a', 'React', '#656065', 'documentation_link');

insert into tb_challenge_technology (id, challenge_id, technology_id) values ('9f893e6d-e1d8-4928-910e-3b9969b9c796', 'dd57f958-586b-47d4-8868-f6f7e78aa387', '8e423476-1326-4291-b106-332eb0198a04');
insert into tb_challenge_technology (id, challenge_id, technology_id) values ('5398e2d5-d1b4-4e18-90eb-526b0ac9af00', 'dd57f958-586b-47d4-8868-f6f7e78aa387', '687cdccc-f13a-4474-a5fb-c28573834624');
insert into tb_challenge_technology (id, challenge_id, technology_id) values ('c8e25869-95d1-45d1-b638-21d7fed8e134', '275a42b8-0d84-47fc-90d5-2b7c2b94c4b8', 'b4ba40d6-7bbd-44a5-8bd1-38847d4b663a');

insert into tb_solution (id, author_id, challenge_id, deploy_url, repository_url) values ('3460d1b8-f756-4425-b79f-a76b07a006ba', 'c114fe61-f54f-4858-9c4d-9f6d9d8207aa', 'dd57f958-586b-47d4-8868-f6f7e78aa387', 'heroku.com', 'github.com/harlon/banco-tal');
insert into tb_solution (id, author_id, challenge_id, deploy_url, repository_url) values ('c3e658fe-a3ff-4715-b3de-642a8d7a85a0', '3aec17ef-b1ac-45a0-9963-3a68583b41b5', 'dd57f958-586b-47d4-8868-f6f7e78aa387', 'netlify.com', 'github.com/pedro/banco-tal');

insert into tb_like (participant_id, solution_id) values ('0a10cc2c-d527-44bc-912f-95ea6aa94961', '3460d1b8-f756-4425-b79f-a76b07a006ba');
insert into tb_like (participant_id, solution_id) values ('0a10cc2c-d527-44bc-912f-95ea6aa94961', 'c3e658fe-a3ff-4715-b3de-642a8d7a85a0');
insert into tb_like (participant_id, solution_id) values ('c114fe61-f54f-4858-9c4d-9f6d9d8207aa', 'c3e658fe-a3ff-4715-b3de-642a8d7a85a0');
insert into tb_like (participant_id, solution_id) values ('3aec17ef-b1ac-45a0-9963-3a68583b41b5', 'c3e658fe-a3ff-4715-b3de-642a8d7a85a0');