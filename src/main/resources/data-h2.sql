INSERT INTO technology (id, name) VALUES
    (1, 'Kafka'),
    (2, 'Redis'),
    (3, 'RabbitMQ'),
    (4, 'TensorFlow'),
    (5, 'GraphQL'),
    (6, 'React'),
    (7, 'Spring Boot'),
    (8, 'Angular'),
    (9, 'Ionic'),
    (10, 'ReactJS'),
    (11, 'React Native'),
    (12, 'Vue'),
    (13, 'Kubernetes'),
    (14, 'Graphana'),
    (15, 'Kibana'),
    (16, 'Prometheus'),
    (17, 'Redux'),
    (18, 'PyTorch'),
    (19, 'Keras');


INSERT INTO users (id, uuid, first_name, last_name, email, password, enabled) VALUES
    (1, 'fccb401a-ecbf-4214-8aae-b198150229c2', 'Eduardo', 'Gordilho', 'edu_gordilho@hotmail.com', '12356', true),
    (2, 'e225d8bb-91dd-42bb-b84b-a0cfd975a2e2', 'Maria', 'Pereira', 'mariapereira@example.com', '12356', true),
    (3, 'c6a3567f-5ef0-4d03-83cb-95bc1818b9c9', 'Pedro', 'Santos', 'pedrosantos@example.com', '12356', true),
    (4, 'f3812516-8a0a-41b1-a3d8-7009df5a129b', 'José', 'Oliveira', 'joseoliveira@example.com', '12356', true),
    (5, '255f20e6-2180-4ac6-a256-3a779b4f14af', 'Ana', 'Souza', 'anasouza@example.com', '12356', true),
    (6, 'ed2ca334-eef9-4c15-85f3-fff9a5b9bc4b', 'Sofia', 'Rodrigues', 'sofiarodrigues@example.com', '12356', true),
    (7, '8e1814fd-ff69-4f83-b0b7-e3f238e5480a', 'Felipe', 'Ferreira', 'felipeferreira@example.com', '12356', true),
    (8, '7e713758-43f2-4b3d-9a8b-3d980ee4068f', 'Augusto', 'Alves', 'augustoalves@example.com', '12356', true),
    (9, 'b666308c-60ec-4648-a2dd-cc3081e21171', 'Priscila', 'Lima', 'priscilalima@example.com', '12356', true),
    (10, '7e37278a-d6f2-4539-b412-082bde74d612', 'Jessica', 'Gomes', 'jessicagomes@example.com', '12356', true);

-- Check ProficiencyEnum
INSERT INTO user_technology (user_id, technology_id, proficiency_enum) VALUES
    (1, 7, 'BEGGINING'),
    (1, 2, 'INTERMEDIATE'),

    (2, 1, 'ADVANCED'),

    (3, 3, 'ADVANCED'),
    (3, 5, 'BEGGINING'),
    (3, 8, 'INTERMEDIATE'),

    (4, 2, 'ADVANCED'),
    (4, 4, 'BEGGINING'),
    (4, 6, 'ADVANCED'),
    (4, 5, 'INTERMEDIATE'),

    (5, 1, 'INTERMEDIATE'),
    (5, 3, 'ADVANCED'),
    (5, 2, 'ADVANCED'),

    (6, 1, 'BEGGINING'),
    (6, 5, 'BEGGINING'),

    (7, 7, 'BEGGINING'),
    (7, 1, 'ADVANCED'),
    (7, 3, 'INTERMEDIATE'),
    (7, 4, 'ADVANCED'),

    (8, 7, 'BEGGINING'),

    (9, 1, 'ADVANCED'),
    (9, 2, 'INTERMEDIATE'),

    (10, 1, 'INTERMEDIATE'),
    (10, 3, 'ADVANCED'),
    (10, 6, 'BEGGINING');

-- Check RoleEnum
INSERT INTO role (id, role) VALUES
    (1, 'USER'),
    (2, 'STAFF'),
    (3, 'ADMIN');

-- Check PrivilegeEnum
INSERT INTO permission (id, permission) VALUES
    (1, 'READ'),
    (2, 'WRITE'),
    (3, 'CHANGE_PASSWORD');

INSERT INTO roles_permissions(role_id, permission_id) VALUES
    (1, 1),
    (2, 1),
    (2, 2),
    (3, 1),
    (3, 2),
    (3, 3);

INSERT INTO user_role (user_id, role_id) VALUES
    (1, 3),
    (2, 1),
    (3, 1),
    (4, 1),
    (5, 1),
    (6, 1),
    (7, 1),
    (8, 1),
    (9, 1),
    (10, 1);


