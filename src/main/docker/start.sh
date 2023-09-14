#!/bin/bash

# Запуск Docker Compose файлов
#docker-compose -f jhipster-control-center.yml up -d
#docker-compose -f monitoring.yml up -d
docker-compose -f postgresql.yml up -d
docker-compose -f redis.yml up -d
#docker-compose -f sonar.yml up -d
#docker-compose -f zipkin.yml up -d


echo "All Docker Compose files have been started."
