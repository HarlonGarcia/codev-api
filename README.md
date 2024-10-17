# Codev

Codev is a platform for developers to participate in challenges and create a network.
We want to help developers to improve their skills and learn new technologies by solving challenges.

## Instalation

Run the following command to clone the repository and enter the project folder:

```bash
$ git clone https://github.com/HarlonGarcia/codev-api.git && cd codev-api
```

## Requirements, dependencies and execution

You will need to have installed on your machine to run the project:

- [Java](https://www.oracle.com/br/java/technologies/downloads/): 17.0.12^
- [Docker](https://docs.docker.com/get-docker/)

Run the following command to run the Docker image

```bash
$ docker compose up -d
```

To run in the development environment, you may need to install maven CLI and run the following command:

```bash
$ ./mvnw compile quarkus:dev
```

In dev environment, the application port is set to [8000].
Also, there is a file `import.sql` in the resources folder that you can run to mock data;

## Contributors ✨

New contributors are welcome! Feel free to open a pull request or submit an issue.

- [Breno Duarte](https://github.com/brenooduarte)
- [Harlon García](https://github.com/HarlonGarcia)

See related repositories:

- [Codev Frontend](https://github.com/HarlonGarcia/codev-front)
