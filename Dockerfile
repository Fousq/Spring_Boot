# docker file to run protheus
FROM prom/prometheus:latest

WORKDIR .

COPY ./prometheus/prometheus.yml /etc/prometheus/prometheus.yml

EXPOSE 9090