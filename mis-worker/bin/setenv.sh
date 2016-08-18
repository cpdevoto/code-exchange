#
# MIS Worker
#

echo Setting the MIS Worker environment..

WORKER_HOME=`dirname "$0"`
export WORKER_HOME=`cd "$WORKER_HOME/.."; pwd`

LOG_DIR=/var/logs/mis-worker
if [ -d $LOG_DIR ]; then LOG_BASE=$LOG_DIR
else
   LOG_DIR=$WORKER_HOME/logs
fi 

export WORKER_LOG=$LOG_DIR
export WORKER_CFG=$WORKER_HOME/config
export WORKER_LIB=$WORKER_HOME/lib
export WORKER_CFG_FILE=mis-worker.yml

export WORKER_CLASSPATH=$WORKER_LIB/bootstrap-1.0.jar

export WORKER_OPTS="-Dbootstrap.home=$WORKER_HOME -Dbootstrap.class=com.doradosystems.mis.worker.MisWorkerApplication -Duser.timezone=GMT -Dlog.dir=$WORKER_LOG"

export JMX_PORT=50844


echo --------------------------
echo Home Dir:   $WORKER_HOME
echo Config Dir: $WORKER_CFG
echo Lib Dir:    $WORKER_LIB
echo Log Dir:    $WORKER_LOG
echo Classpath:  $WORKER_CLASSPATH
echo Java Opts:  $WORKER_OPTS
echo JMX port:   $JMX_PORT
echo --------------------------