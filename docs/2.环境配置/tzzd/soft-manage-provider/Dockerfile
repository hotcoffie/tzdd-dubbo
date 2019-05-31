FROM openjdk:8

MAINTAINER labwinner.com<hotcoffie@163.com>

VOLUME /tmp

ADD app.jar app.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
