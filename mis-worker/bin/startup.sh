#!/bin/bash

#
# Start the MIS Worker Application
#

WORKER_HOME=`dirname "$0"`
export WORKER_HOME=`cd "$WORKER_HOME/.."; pwd`
. $WORKER_HOME/bin/setenv.sh

MEM_OPTS="-Xms2048m -Xmx2048m -XX:NewSize=1024m -XX:MaxNewSize=1024m -XX:PermSize=1024m -XX:MaxPermSize=1024m -XX:+DisableExplicitGC"

EXTRA_OPTS="-Dlog.extra.appender=FILE $MEM_OPTS -Dcom.sun.management.jmxremote.port=$JMX_PORT -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false"

java $WORKER_OPTS $EXTRA_OPTS $WORKER_PROPS -classpath $WORKER_CLASSPATH org.devoware.bootstrap.Bootstrap start $WORKER_CFG_FILE
