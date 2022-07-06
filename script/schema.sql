CREATE USER 'wb-quarkus-usuarios'@'%' IDENTIFIED BY 'quarkusdb';
GRANT ALL PRIVILEGES ON * . * TO 'wb-quarkus-usuarios'@'%';
CREATE DATABASE db_quarkus;
GRANT ALL PRIVILEGES ON db_quarkus.* TO 'wb-quarkus-usuarios'@'%';
FLUSH PRIVILEGES;