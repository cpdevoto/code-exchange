#!/usr/bin/env sh

cd /opt/wantify

java $JAVA_OPTS -Dninja.external.configuration=application.conf -Dlogback.configurationFile=logback.xml -jar wantify-server-$WANTIFY_VERSION.jar
