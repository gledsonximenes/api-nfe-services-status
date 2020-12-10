# api-nfe-services-status

## Descrição

Este projeto contém a implementação da API NFE Services Status, que verifica e armazena os status dos serviços de nota fiscal eletrônica por estado.

## Pré-requisitos

Para a execução deste projeto são necessários os seguintes pré-requisitos:

- Git
- Java 14+
- Maven 3.6.1+
- Docker Compose 1.24.x

### Configuração das Conexões

Este projeto depende de conexões com o `PostgreSQL` para executar, por isso, antes de executá-lo, é necessário primeiramente que esses serviços estejam disponíveis. Para o desenvolvimento é utilizado o `docker-compose` para orquestrar um conjunto de containers com os serviços necessários.

Para executar os serviços com o `docker-compose` primeiro é necessário navegar para a pasta onde o arquivo `docker-compose.yml` se encontra (geralmente na raiz do projeto) e executar o comando abaixo.

```bash
docker-compose up -d
```

Após executar o comando acima é possível ver o status dos containers através do seguinte comando.

```bash
docker-compose ps
```

Em caso de sucesso, todos os serviços devem conter o status `UP`.

### Execução do Código

Por padrão a aplicação executa na porta `8088`, mas pode ser configurada através do arquivo `application.properties`.

### Swagger

Segue o link com swagger da aplicação: https://github.com/gledsonximenes/api-nfe-services-status/blob/main/swagger.yaml