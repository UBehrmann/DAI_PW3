# DAI_PW3

## Authors

- [Rodrigo Lopez Dos Santos](https://github.com/RodrigoLopesDos)
- [Urs Behrmann](https://github.com/UBehrmann) 

### Build and run docker image

First clone this repo on your machine and `cd` in the cloned folder:

```bash
git clone
cd ProjectManager
```

Then, you need to build the docker image:

```bash
docker build -t crud_server .
```

Finally build the docker with the data base

```bash
docker-compose up --build
```

If the build was already done befor, you can only run the docker with

```bash
docker-compose start
```
