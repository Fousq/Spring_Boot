# Use this docker file to boot the mysql prod db

FROM mysql:latest

ENV MYSQL_ROOT_PASSWORD=0000
ENV MYSQL_DATABASE=prod

COPY ./sql-scripts/billSchema.sql .
