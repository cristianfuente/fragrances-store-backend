FROM amazoncorretto:21

RUN yum -y update && \


    yum install -y openssl

ENV LANG=C.UTF-8 \
    LC_ALL=C.UTF-8

VOLUME /tmp

COPY target/quarkus-app/lib /lib/
COPY target/quarkus-app/quarkus-run.jar /app.jar
COPY target/quarkus-app/app /app/
COPY target/quarkus-app/quarkus /quarkus/

ENTRYPOINT ["bash", "-c", "exec java -Djava.security.egd=file:/dev/./urandom -jar /app.jar"]

