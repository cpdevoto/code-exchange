#!/bin/bash

#
# Stops the Recurrent Test Scheduler Engine
#

SCHEDULER_HOME=`dirname "$0"`
export SCHEDULER_HOME=`cd "$SCHEDULER_HOME/.."; pwd`
. $SCHEDULER_HOME/bin/setenv.sh

EXTRA_OPTS="$MEM_OPTS -Dlog.sfx=_termination_alert_$$"

java $SCHEDULER_OPTS $EXTRA_OPTS $SCHEDULER_PROPS -classpath $SCHEDULER_CLASSPATH org.devoware.bootstrap.Bootstrap stop
