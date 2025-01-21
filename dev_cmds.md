<div align="justify" style="margin-right:25px;margin-left:25px">

# How to deploy, run and access the web applications with Docker Compose

Here are some commands to upload the docker images to the GitHub container registry and to update the dockers on Azure.

## Upload crud_server docker image to github container registry <!-- omit in toc -->

To upload the docker image to the GitHub container registry, you can use the following commands:

```bash

docker build . -t crud_server
docker tag crud_server ghcr.io/ubehrmann/crud_server:latest
docker push ghcr.io/ubehrmann/crud_server:latest

```

## Upload db_crud_server docker image to github container registry <!-- omit in toc -->

To upload the docker image to the GitHub container registry, you can use the following commands:

```bash

docker build . -t db_crud_server 
docker tag db_crud_server ghcr.io/ubehrmann/db_crud_server:latest
docker push ghcr.io/ubehrmann/db_crud_server:latest

```

### Update dockers sur Azure <!-- omit in toc -->

After connection to the Azure virtual machine, you can update the dockers with the following commands:

```bash

docker-compose down
docker-compose pull
docker-compose up --build

```

### MAJ docker-compose.yml sur Azure <!-- omit in toc -->

To update the docker-compose.yml file on the Azure virtual machine, you can use the following command:

```bash

scp -i ~/.ssh/azure ~/DAI/DAI_PW3/docker-compose.yml ubuntu@172.201.218.98:~

```

</div>