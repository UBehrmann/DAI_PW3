# DAI_PW3

## Authors

- [Rodrigo Lopez Dos Santos](https://github.com/RodrigoLopesDos)
- [Urs Behrmann](https://github.com/UBehrmann) 

## How to run the service

There are 3 ways to run this service:
- Build from source with your IDE of choice
- Clone the repo and run the docker file
- Use the docker image from github packages

___

### 1 Build from source

First clone this repo on your machine and `cd` in the cloned folder:

```bash
git clone git@github.com:UBehrmann/DAI_PW3.git
cd crud_server
```

####  Prerequisites

Make sure you have the following installed on your system:

- [Maven](https://maven.apache.org/)
- [SdkMan](https://sdkman.io/)

#### Steps to Build and Run

1. Download the project dependencies:
```bash
./mvnw dependency:go-offline
```

2. Build the project:

```bash
./mvnw package
```

3. Run the service:

```
java -jar target/java-tcp-programming-1.0-SNAPSHOT.jar
```

___

### 2 Build and run docker image

First, clone this repository on your machine and navigate into the cloned folder:

```bash
git clone git@github.com:UBehrmann/DAI_PW3.git
cd crud_server
```

**Build the Docker Image**

Run the following command to build the Docker image:

```bash
docker build -t crud_server .
```

**Run the Service with Docker Compose**

To build and run the service along with the database, use:

```bash
docker-compose up --build
```

If the build has already been done, you can simply start the containers without rebuilding:

```bash
docker-compose start
```

___

### 3 Use the docker image from github packages

1. Pull the Docker image from GitHub Packages:

```bash
docker pull ghcr.io/ubehrmann/dai_pw3:latest
```

2. Run the Docker container:

```bash
docker run -p 7000:7000 ghcr.io/ubehrmann/dai_pw3:latest
```

## How to connect to the db with docker

```bash
docker exec -it postgres-db psql -U user -d mydb
```

## How to connect to the azure vm

```bash
ssh ubuntu@172.201.218.98 -i ~/.ssh/azure
```

In .ssh/azure is the private key for the azure vm.

### Upload crud_server docker image to github container registry

docker build . -t crud_server
docker tag crud_server ghcr.io/ubehrmann/crud_server:latest
docker push ghcr.io/ubehrmann/crud_server:latest

### Upload db_crud_server docker image to github container registry

docker build . -t db_crud_server 
docker tag db_crud_server ghcr.io/ubehrmann/db_crud_server:latest
docker push ghcr.io/ubehrmann/db_crud_server:latest

### Update dockers sur Azure

docker-compose down
docker-compose up -d

### MAJ docker-compose.yml sur Azure

scp -i ~/.ssh/azure /wsl.localhost/Ubuntu/home/ub/DAI/DAI_PW3/docker-compose.yml ubuntu@172.201.218.98:~
