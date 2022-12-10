CREATE USER 'wb-usuarios'@'%' IDENTIFIED BY 'quarkusdb';
GRANT ALL PRIVILEGES ON * . * TO 'wb-usuarios'@'%';
CREATE DATABASE db_quarkus;
GRANT ALL PRIVILEGES ON db_quarkus.* TO 'wb-usuarios'@'%';
FLUSH PRIVILEGES;

-- para listar todos os usuarios do banco de dados: "select host, user, password from mysql.user;"