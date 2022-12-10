# Quarkus-projeto-teste
projeto teste para aprender as funcionalidades da Stack Quarkus


## Tecnologias utilizadas

- Java 11
- Quarkus
- Docker
- Kubernetes
- AWS Secret Manager
- Spock Framework para testes unitários
- Banco de dados MySql

## Iniciar banco de dados Mysql

para iniciar o banco de dados Mysql local, já possuimos um arquivo Docker-compose.yaml com
toda configuração necessária, basta apenas executar o comando na pasta 
"Quarkus-projeto-teste/mysql docker/":

```
docker-compose up
```

Executando esse comando, será criado o banco de dados, o usuario para conexão,
e a tabela utilizada pelo projeto.


## Criação da imagem Docker

para realizar a criação da imagem docker do projeto, primeiro vc deve executar o
comando abaixo na raiz do projeto:


```
docker build -t localhost:32000/quarkus-crud:local -f src/main/docker/Dockerfile.jvm . 
```

Com isso podemos executar local com o comando abaixo:

```
docker run --name quarkus--crud \
-p 5000:5000 \
--network=quarkus-projeto-teste_default \
-e DATABASE_HOST="jdbc:mysql://quarkus-projeto-teste_db_1:3306/db_quarkus" \
-e DATABASE_USER_NAME=wb-quarkus-usuarios \
-e DATABASE_USER_PASSWORD=quarkusdb \
-e QUARKUS_PROFILE=docker \
projeto--teste-quarkus;
```
