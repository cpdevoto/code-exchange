#
# MIS Scheduler
#

echo Setting the MIS Scheduler environment..

SCHEDULER_HOME=`dirname "$0"`
export SCHEDULER_HOME=`cd "$SCHEDULER_HOME/.."; pwd`

LOG_DIR=/var/logs/mis-scheduler
if [ -d $LOG_DIR ]; then LOG_BASE=$LOG_DIR
else
   LOG_DIR=$SCHEDULER_HOME/logs
fi 

export SCHEDULER_LOG=$LOG_DIR
export SCHEDULER_CFG=$SCHEDULER_HOME/config
export SCHEDULER_LIB=$SCHEDULER_HOME/lib
export SCHEDULER_CFG_FILE=mis-scheduler.yml

export SCHEDULER_CLASSPATH=$SCHEDULER_LIB/bootstrap-1.0.jar

export SCHEDULER_OPTS="-Dbootstrap.home=$SCHEDULER_HOME -Dbootstrap.class=com.doradosystems.mis.scheduler.MisSchedulerApplication -Duser.timezone=GMT -Dlog.dir=$SCHEDULER_LOG"

export JMX_PORT=50844


echo --------------------------
echo Home Dir:   $SCHEDULER_HOME
echo Config Dir: $SCHEDULER_CFG
echo Lib Dir:    $SCHEDULER_LIB
echo Log Dir:    $SCHEDULER_LOG
echo Classpath:  $SCHEDULER_CLASSPATH
echo Java Opts:  $SCHEDULER_OPTS
echo JMX port:   $JMX_PORT
echo --------------------------