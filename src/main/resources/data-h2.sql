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


INSERT INTO users (id, created_at, uuid, first_name, last_name, username, password, enabled) VALUES
    (1, '2022-05-28 00:00:00', 'fccb401a-ecbf-4214-8aae-b198150229c2', 'Eduardo', 'Gordilho', 'eduardogordilho@example.com', 'e28c2024b1178702415b9cb27f8778329e74c85b766a7d1c4c8d8cd42d4989dec6dd0cddad899f32a26cb044e19c9142f46d8b85075a26aa6a686327576a5bad42167017b4bb750b', true),
    (2, '2022-05-28 00:00:00', 'e225d8bb-91dd-42bb-b84b-a0cfd975a2e2', 'Maria', 'Pereira', 'mariapereira@example.com', 'e28c2024b1178702415b9cb27f8778329e74c85b766a7d1c4c8d8cd42d4989dec6dd0cddad899f32a26cb044e19c9142f46d8b85075a26aa6a686327576a5bad42167017b4bb750b', true),
    (3, '2022-05-28 00:00:00', 'c6a3567f-5ef0-4d03-83cb-95bc1818b9c9', 'Pedro', 'Santos', 'pedrosantos@example.com', 'e28c2024b1178702415b9cb27f8778329e74c85b766a7d1c4c8d8cd42d4989dec6dd0cddad899f32a26cb044e19c9142f46d8b85075a26aa6a686327576a5bad42167017b4bb750b', true),
    (4, '2022-05-28 00:00:00', 'f3812516-8a0a-41b1-a3d8-7009df5a129b', 'José', 'Oliveira', 'joseoliveira@example.com', 'e28c2024b1178702415b9cb27f8778329e74c85b766a7d1c4c8d8cd42d4989dec6dd0cddad899f32a26cb044e19c9142f46d8b85075a26aa6a686327576a5bad42167017b4bb750b', true),
    (5, '2022-05-28 00:00:00', '255f20e6-2180-4ac6-a256-3a779b4f14af', 'Ana', 'Souza', 'anasouza@example.com', 'e28c2024b1178702415b9cb27f8778329e74c85b766a7d1c4c8d8cd42d4989dec6dd0cddad899f32a26cb044e19c9142f46d8b85075a26aa6a686327576a5bad42167017b4bb750b', true),
    (6, '2022-05-28 00:00:00', 'ed2ca334-eef9-4c15-85f3-fff9a5b9bc4b', 'Sofia', 'Rodrigues', 'sofiarodrigues@example.com', 'e28c2024b1178702415b9cb27f8778329e74c85b766a7d1c4c8d8cd42d4989dec6dd0cddad899f32a26cb044e19c9142f46d8b85075a26aa6a686327576a5bad42167017b4bb750b', true),
    (7, '2022-05-28 00:00:00', '8e1814fd-ff69-4f83-b0b7-e3f238e5480a', 'Felipe', 'Ferreira', 'felipeferreira@example.com', 'e28c2024b1178702415b9cb27f8778329e74c85b766a7d1c4c8d8cd42d4989dec6dd0cddad899f32a26cb044e19c9142f46d8b85075a26aa6a686327576a5bad42167017b4bb750b', true),
    (8, '2022-05-28 00:00:00', '7e713758-43f2-4b3d-9a8b-3d980ee4068f', 'Augusto', 'Alves', 'augustoalves@example.com', 'e28c2024b1178702415b9cb27f8778329e74c85b766a7d1c4c8d8cd42d4989dec6dd0cddad899f32a26cb044e19c9142f46d8b85075a26aa6a686327576a5bad42167017b4bb750b', true),
    (9, '2022-05-28 00:00:00', 'b666308c-60ec-4648-a2dd-cc3081e21171', 'Priscila', 'Lima', 'priscilalima@example.com', 'e28c2024b1178702415b9cb27f8778329e74c85b766a7d1c4c8d8cd42d4989dec6dd0cddad899f32a26cb044e19c9142f46d8b85075a26aa6a686327576a5bad42167017b4bb750b', true),
    (10, '2022-05-28 00:00:00', '7e37278a-d6f2-4539-b412-082bde74d612', 'Jessica', 'Gomes', 'jessicagomes@example.com', 'e28c2024b1178702415b9cb27f8778329e74c85b766a7d1c4c8d8cd42d4989dec6dd0cddad899f32a26cb044e19c9142f46d8b85075a26aa6a686327576a5bad42167017b4bb750b', true);

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
    (1, 'READ_USER'),
    (2, 'READ_PERMISSION'),
    (3, 'READ_TECHNOLOGY'),
    (4, 'READ_USER_TECHNOLOGY'),
    (5, 'WRITE_USER'),
    (6, 'WRITE_ROLE'),
    (7, 'WRITE_PERMISSION'),
    (8, 'WRITE_TECHNOLOGY'),
    (9, 'WRITE_USER_TECHNOLOGY');


INSERT INTO role_permission(role_id, permission_id) VALUES
    (1, 1),
    (1, 5),
    (1, 3),
    (1, 8),
    (1, 9),
    (2, 1),
    (2, 2),
    (2, 5),
    (2, 8),
    (2, 9),
    (3, 1),
    (3, 2),
    (3, 3),
    (3, 4),
    (3, 5),
    (3, 6),
    (3, 7),
    (3, 8),
    (3, 9);

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


