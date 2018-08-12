#!/usr/bin/env sh

cd /opt/dropwizard

java $JAVA_OPTS -jar -Done-jar.silent=true rule-generator-all.jar server service-config.yml
