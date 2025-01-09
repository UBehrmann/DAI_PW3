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

