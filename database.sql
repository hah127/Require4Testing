CREATE DATABASE IF NOT EXISTS require4testing
  CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE require4testing;


CREATE TABLE requirement (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(250) NOT NULL,
    description VARCHAR(1000)
);

CREATE TABLE testrun (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(250) NOT NULL,
    tester VARCHAR(250) NOT NULL
);

CREATE TABLE testcase (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(250) NOT NULL,
    description VARCHAR(1000),
    requirement_id BIGINT NOT NULL,
    FOREIGN KEY (requirement_id) REFERENCES requirement(id)
);

CREATE TABLE testexecution (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    result VARCHAR(20) NOT NULL DEFAULT 'Not Executed',
    testrun_id BIGINT NOT NULL,
    testcase_id BIGINT NOT NULL,
    FOREIGN KEY (testrun_id) REFERENCES testrun(id),
    FOREIGN KEY (testcase_id) REFERENCES testcase(id),
    UNIQUE (testrun_id, testcase_id)
);


INSERT INTO requirement (title, description) VALUES
    ('Login-Funktion', 'Benutzer kann sich mit Benutzername und Passwort anmelden'),
    ('Profil bearbeiten', 'Benutzer kann sein Profil ändern und speichern');

INSERT INTO testcase (title, description, requirement_id) VALUES
    ('Login mit gültigen Daten', 'Richtige Daten eingeben, erwartet: Erfolg', 1),
    ('Login mit falschem Passwort', 'Erwartet: Fehlermeldung', 1),
    ('Profil speichern', 'Änderung speichern, erwartet: gespeichert', 2);

INSERT INTO testrun (name, tester) VALUES
    ('Sprint 1', 'Alice');

INSERT INTO testexecution (result, testrun_id, testcase_id) VALUES
    ('Not Executed', 1, 1),
    ('Not Executed', 1, 2);
