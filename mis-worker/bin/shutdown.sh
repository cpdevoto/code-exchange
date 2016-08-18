#!/bin/bash

#
# Stops the MIS Worker Application
#

WORKER_HOME=`dirname "$0"`
export WORKER_HOME=`cd "$WORKER_HOME/.."; pwd`
. $WORKER_HOME/bin/setenv.sh

EXTRA_OPTS="-Dlog.extra.appender=NOP $MEM_OPTS"

java $WORKER_OPTS $EXTRA_OPTS $WORKER_PROPS -classpath $WORKER_CLASSPATH org.devoware.bootstrap.Bootstrap stop
