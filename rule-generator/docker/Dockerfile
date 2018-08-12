FROM openjdk:8u121-jdk-alpine

# This is 
# This docker file installs JRE8 on Alpine, then Resolute's "Laser" Ninja Data Service
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

COPY service-config.yml docker/entrypoint.sh build/libs/rule-generator-all.jar /opt/dropwizard/

EXPOSE 8080

WORKDIR /opt/dropwizard
CMD ["./entrypoint.sh"]