INSERT INTO technology (id, name) VALUES
    (1, 'Kafka'),
    (2, 'Redis'),
    (3,'RabbitMQ'),
    (4, 'TensorFlow'),
    (5, 'GraphQL'),
    (6, 'React'),
    (7, 'Spring Boot'),
    (8, 'Angular'),
    (9, 'Ionic');


INSERT INTO users (id, first_name, last_name, email, password, enabled) VALUES
    (1, 'Eduardo', 'Gordilho', 'edu_gordilho@hotmail.com', '12356', true),
    (2, 'Maria', 'Pereira', 'mariapereira@example.com', '12356', true),
    (3, 'Pedro', 'Santos', 'pedrosantos@example.com', '12356', true),
    (4, 'José', 'Oliveira', 'joseoliveira@example.com', '12356', true),
    (5, 'Ana', 'Souza', 'anasouza@example.com', '12356', true),
    (6, 'Sofia', 'Rodrigues', 'sofiarodrigues@example.com', '12356', true),
    (7, 'Felipe', 'Ferreira', 'felipeferreira@example.com', '12356', true),
    (8, 'Augusto', 'Alves', 'augustoalves@example.com', '12356', true),
    (9, 'Priscila', 'Lima', 'priscilalima@example.com', '12356', true),
    (10, 'Jessica', 'Gomes', 'jessicagomes@example.com', '12356', true);

-- CHECK ProficiencyEnum
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
INSERT INTO privilege (id, name) VALUES
    (1, 'READ'),
    (2, 'WRITE'),
    (3, 'CHANGE_PASSWORD');

INSERT INTO roles_privileges(role_id, privilege_id) VALUES
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


