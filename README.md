# Quarkus-projeto-teste
projeto teste para aprender as funcionalidades da Stack Quarkus, onde será desenvolvido
um projeto CRUD para validar o uso utilizando ferramentas de mercado (AWS Secret Manager, AWS EKS), criptografia (Bcrypt)
e avaliar vantagens de utiliza-lá com o GraalVM(ainda não implementado no projeto).



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
--network=mysqldocker_default \
-e DATABASE_HOST="jdbc:mysql://mysqldocker_db_1:3306/db_quarkus" \
-e DATABASE_USER_NAME=wb-usuarios \
-e DATABASE_USER_PASSWORD=quarkusdb \
-e QUARKUS_PROFILE=docker \
projeto--teste-quarkus;
```

## Execução em um Cluster Kubernetes local

### Enviar imagem para o Cluster Kubernetes
Para executar em um Cluster Kubernetes local, devemos primeiro enviar a imagem para o 
registry do Cluster.

Primeiramente, devemos pegar o IMAGE ID da image do projeto Quarkus executando o seguinte comando:

```
 docker images
```

Esse comando irá listar todas as imagens no seu computador, copie o IMAGE ID
da imagem com o REPOSITORY "localhost:32000/quarkus-crud:local" e execute o comando abaixo:

```
docker tag IMAGE_ID localhost:32000/quarkus-crud:local
```

Com a imagem tageada corretamente, devemos envia-la para ser utilizada no cluster com o comando abaixo:

```
 docker push localhost:32000/quarkus-crud:local
```

### Cria banco MySql para ser utilizado pelo serviço no cluster

Agora, com a imagem criada, vamos preparar o cluster para receber a nossa aplicação.
Como é uma aplicação simples, possui apenas uma dependencia de um banco MySql, que podemos criar dentro
do proprio cluster Kubernetes para teste local.

**Caso opte por utilizar um banco de dados externo, esse passo não é necessário!!!**

Para criar o banco de dados dentro do cluster, primeiro devemos ir para a pasta
"Quarkus-projeto-teste/mysql docker/kubernetes/" e executar o comando abaixo:

```
kubectl apply -f .
```

Com isso será criado um pod com o banco de dados MySql, agora devemos criar o usuario
par o projeto e a tabela de usuario. Para isso, iremos acessar o terminal do pod e executar os comandos
manualmente.

Execute o seguinte comando para pegarmos o nome do pod que será acessado:

```
kubectl get pod
```

o pod que procuramos tem o prefixo "mysql", segue um exemplo abaixo do nome do pod:

```
mysql-6f9bcdb469-dfm26
```

Para acessar o pod, execute o comando abaixo:

```
kubectl exec --stdin --tty NOME_DO_POD -- /bin/bash
```

Já dentro do pod executamos o comando abaixo para acessar o banco de dados MySql
como usuario root:

```
mysql -p
```

Ao executar esse comando, será solicitado um password do usuario root, que está dentro do arquivo 
"Quarkus-projeto-teste/mysql docker/kubernetes/mysql-secrets.yaml"

Digitando a senha e acessando o terminal do MySql, devemos copiar e colar os comando do arquivo
"Quarkus-projeto-teste/mysql docker/script/schema.sql" e pressionar o enter
com isso o usuario e a tabela são criadas para serem utilizadas pela aplicação


### Deploy da aplicação no cluster Kubernetes

Para realizar o deploy da aplicação no cluster, devemos ir para a pasta
"Quarkus-projeto-teste/quarkus--projeto-teste/kubernetes/local/" e executar o comando abaixo:

```
kubectl apply -k .
```
isso irá aplicar todos os artefatos Kubernetes da aplicação, será criado os pods com o namespace "quarkus--crud" 
e o ingress para acessar a aplicação no "localhost".

## Referencias

[Quarkus - Supersonic Subatomic Java ](https://quarkus.io/)

[Kubernetes](https://kubernetes.io/pt-br/)

[Docker](https://www.docker.com/)

[Kustomize](https://kustomize.io/)

[MySql](https://www.mysql.com/)




