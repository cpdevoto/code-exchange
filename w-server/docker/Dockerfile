FROM openjdk:8u121-jdk-alpine

# This is 
# This docker file installs JRE8 on Alpine, then the Wantify Server
#

RUN set -x \
    && apk --update add \
       rsyslog \
       bash \
       curl \
       wget \
       gettext \
       jq \
    && rm -rf /var/cache/apk/*

COPY target/wantify-server-1.0.0.jar application.conf logback.xml docker/entrypoint.sh /opt/wantify/

EXPOSE 8080

WORKDIR /opt/wantify
CMD ["./entrypoint.sh"]