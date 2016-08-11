#!/bin/bash

#
# Start the Recurrent Test Scheduler Engine
#

SCHEDULER_HOME=`dirname "$0"`
export SCHEDULER_HOME=`cd "$SCHEDULER_HOME/.."; pwd`
. $SCHEDULER_HOME/bin/setenv.sh

MEM_OPTS="-Xms2048m -Xmx2048m -XX:NewSize=1024m -XX:MaxNewSize=1024m -XX:PermSize=1024m -XX:MaxPermSize=1024m -XX:+DisableExplicitGC"

EXTRA_OPTS="$MEM_OPTS -Dlog.sfx=_alert_$$ -Dcom.sun.management.jmxremote.port=$JMX_PORT -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false"

java $SCHEDULER_OPTS $EXTRA_OPTS $SCHEDULER_PROPS -classpath $SCHEDULER_CLASSPATH org.devoware.bootstrap.Bootstrap start $SCHEDULER_CFG_FILE
