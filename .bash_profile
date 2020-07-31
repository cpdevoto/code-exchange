# source the .bashrc file
[[ -s ~/.bashrc ]] && source ~/.bashrc

export APACHE_ROOT=/Library/WebServer/Documents
export GIT_HOME=/usr/local/Cellar/git/2.14.1

# export PATH="/usr/local/opt/openssl/bin:$PATH"
export PATH="/Users/cdevoto/Tools/apache-maven-3.3.1/bin:$PATH"
export PATH="/Users/cdevoto/Tools/hbase-1.0.1/bin/:$PATH"
export PATH="/Applications/Sublime\ Text.app/Contents/SharedSupport/bin:$PATH"
export PATH="$PATH:/Applications/Postgres.app/Contents/Versions/latest/bin"
export PATH="/usr/bin:/usr/local/bin:/usr/local/sbin:$PATH"
export PATH="/Users/cdevoto/.rvm/gems/ruby-2.3.1@cp/bin:/Users/cdevoto/.rvm/gems/ruby-2.3.3/bin:$PATH"
export PATH="$GIT_HOME/bin:$PATH"

# To start RabbitMQ, run 'rabbitmq-server -detached'
# To stop RabbitMQ, run 'suod rabbitmqctl stop'

export GIT_EDITOR=vim
export JAVA_HOME JAVA_HOME=$(/usr/libexec/java_home)
export GRADLE_HOME=/usr/local/Cellar/gradle/2.3
HISTFILESIZE=10000000

# added by Anaconda 2.2.0 installer
# export PATH="//anaconda/bin:$PATH"
alias eclipse="nohup /Applications/eclipse/jee-oxygen/Eclipse.app/Contents/MacOS/eclipse >/dev/null 2>&1 &"

alias gclipse="./gradlew cleanEclipse eclipse --refresh-dependencies"
alias git-conflicts="git diff --name-only --diff-filter=U"

# alias postgres-dev="ssh -Nf -i ~/.ssh/resolute-dev ubuntu@mgmt.dc.res0.net -L 5433:dev-postgres0.czuvmmzjfnto.us-east-1.rds.amazonaws.com:5432 2>&1 &"

alias postgres-dev="ssh -NL 5434:postgres0-db.dc.res0.local:5432 webapp0.dc.res0.local"
alias postgres-dev30="ssh -NL 5424:postgres1-db.dc.res0.local:5432 webapp0.dc.res0.local"

alias postgres-stg="ssh -NL 5435:postgres0-db.sc.res0.local:5432 webapp0.sc.res0.local"
alias postgres-stg30="ssh -NL 5425:postgres1-db.sc.res0.local:5432 webapp0.sc.res0.local"

alias postgres-prod="ssh -NL 5436:postgres0-db.pc.res0.local:5432 webapp0.pc.res0.local"
alias postgres-prod30="ssh -NL 5426:postgres1-db.pc.res0.local:5432 webapp0.pc.res0.local"

alias internal-api-dev="ssh -NL 8181:localhost:8080 internal-api0.dc.res0.local 2>&1 &"

alias opentsdb-dev="ssh webapp0.dc.res0.local -L 4242:tsd.dc.res0.net:80"

alias opentsdb-stg="ssh webapp0.sc.res0.local -L 4242:tsd.sc.res0.net:80"

alias opentsdb-prod="ssh webapp0.pc.res0.local -L 4242:tsd.pc.res0.net:80"

alias tsdbone-dev="ssh -L 0.0.0.0:8088:tsdbone.dc.res0.local:80 bigdata-master0.dc.res0.local"

alias tsdbone-stg="ssh -L 8088:tsdbone.sc.res0.local:80 bigdata-master0.sc.res0.local"

alias tsdbone-prod="ssh -L 8089:tsdbone.pc.res0.local:80 bigdata-master0.pc.res0.local"

alias internal-api-dev="ssh -L 8090:internal-api.dc.res0.net:80 webapp0.dc.res0.local"

alias internal-api-stg="ssh -L 8090:internal-api.sc.res0.net:80 webapp0.sc.res0.local"

alias internal-api-prod="ssh -L 8090:internal-api.prod.res0.net:80 webapp0.prod.res0.local"

alias hero="/Users/cdevoto/Documents/Beta/peacekeepers/HeroDesigner/run.sh"

alias burp="nohup java -jar /Users/cdevoto/Tools/burpsuite_free_v1.6.32.jar >/dev/null 2>&1 &"

alias import-trusted-cert="/Users/cdevoto/shell-scripts/import-trusted-cert.sh"

alias apache-start="sudo apachectl start"

alias apache-stop="sudo apachectl stop"

alias hadoop-dev="ssh -NL 50070:localhost:50070 bigdata-master0.dc.res0.local 2>&1 &"

alias mysql=/usr/local/mysql/bin/mysql
alias mysqladmin=/usr/local/mysql/bin/mysqladmin

alias remove-docker-all='docker rm -f $(docker ps -a -q) && docker rmi -f $(docker images -q)'
alias remove-docker-containers='docker rm -f $(docker ps -a -q)'
alias remove-docker-images='docker rmi -f $(docker images -q)'

alias migrations-up='docker-compose -f docker/docker-compose.yml up'
alias migrations-drop='docker-compose -f docker/docker-compose.yml run migrations-app rake db:drop'
alias migrations-create='docker-compose -f docker/docker-compose.yml run migrations-app rake db:create'
alias migrations-migrate='docker-compose -f docker/docker-compose.yml run migrations-app rake db:migrate'
alias migrations-rollback='docker-compose -f docker/docker-compose.yml run migrations-app rake db:rollback'

alias migrations-latest='docker pull maddogtechnology-docker-develop.jfrog.io/migrations-app'

alias migrations-drop30-dblocal='docker run -it --rm --env-file migration.env --net host maddogtechnology-docker-develop.jfrog.io/migrations-app rake db:drop'
alias migrations-create30-dblocal='docker run -it --rm --env-file migration.env --net host maddogtechnology-docker-develop.jfrog.io/migrations-app rake db:create'
alias migrations-migrate30-dblocal='docker run -it --rm --env-file migration.env --net host maddogtechnology-docker-develop.jfrog.io/migrations-app rake db:migrate'
alias migrations-rollback30-dblocal='docker run -it --rm --env-file migration.env --net host maddogtechnology-docker-develop.jfrog.io/migrations-app rake db:rollback'

alias migrations-drop20-dblocal='docker run -it --rm --env-file migration2.env --volume ~/GitHub/captain-planet/db/migrate:/var/www/migrations-app/db/migrate --net host maddogtechnology-docker-develop.jfrog.io/migrations-app rake db:drop'
alias migrations-create20-dblocal='docker run -it --rm --env-file migration2.env --volume ~/GitHub/captain-planet/db/migrate:/var/www/migrations-app/db/migrate --net host maddogtechnology-docker-develop.jfrog.io/migrations-app rake db:create'
alias migrations-migrate20-dblocal='docker run -it --rm --env-file migration2.env --volume ~/GitHub/captain-planet/db/migrate:/var/www/migrations-app/db/migrate --net host maddogtechnology-docker-develop.jfrog.io/migrations-app rake db:migrate'
alias migrations-rollback20-dblocal='docker run -it --rm --env-file migration2.env --volume ~/GitHub/captain-planet/db/migrate:/var/www/migrations-app/db/migrate --net host maddogtechnology-docker-develop.jfrog.io/migrations-app rake db:rollback'

alias k8-dev='ssh -L 9443:localhost:443 api.internal.beta1.devk8s.res0.local'
alias k8-stg='ssh -L 8443:localhost:443 api.internal.beta1.stgk8s.res0.local'
alias k8-prod='ssh -L 7443:localhost:443 api.internal.beta1.prodk8s.res0.local'

alias postgres_stop='brew services stop postgres'
alias postgres_start='brew services start postgres'

alias dev_pg='ssh webapp0.dc.res0.local -L 5403:postgres0-db.dc.res0.local:5432'
alias dev_tsdb='ssh -L 4242:tsdbone.dc.res0.local:80 bigdata-master2.dc.res0.local'
alias tests='rake RAILS_ENV=test db:test:prepare && rake RAILS_ENV=test spec'
alias dev_rails='rails s -p 5003 --binding=0.0.0.0' # start captain-planet
alias netstatx='sudo lsof -iTCP -sTCP:LISTEN -n -P'
alias use_cp='rvm gemset use cp'
alias openfire='docker run --name openfire -d --restart=always   --publish 9090:9090 --publish 5222:5222 --publish 7777:7777   --volume /Users/cdevoto/Tools/openfire:/var/lib/openfire sameersbn/openfire:3.10.3-19'

alias wantify-postgres-latest='docker pull shrinedevelopment-docker-develop.jfrog.io/postgres-schema'
alias wantify-postgres='docker run -it --rm -p 5432:5432 -h localhost --name wantify-db shrinedevelopment-docker-develop.jfrog.io/postgres-schema'
alias wantify-server-latest='docker pull shrinedevelopment-docker-develop.jfrog.io/wantify-server'
alias wantify-server='docker run -it --rm -p 8080:8080 -h localhost --env-file ~/GitHub/wantify/wantify-server/docker/local.env --name wantify-server shrinedevelopment-docker-develop.jfrog.io/wantify-server:latest'

alias ssh-cpdevoto-jenkins='ssh -i ~/.ssh/cpdevoto-aws.pem ubuntu@ec2-34-227-110-151.compute-1.amazonaws.com'
alias ssh-shrinedev-jenkins='ssh -i ~/.ssh/shrinedev-aws.pem ubuntu@ec2-18-188-176-192.us-east-2.compute.amazonaws.com'

migrations-new_migration20-dblocal () {
	docker run -it --rm --env-file migration2.env --volume ~/GitHub/captain-planet/db/migrate:/var/www/migrations-app/db/migrate --net host maddogtechnology-docker-develop.jfrog.io/migrations-app rake db:new_migration name=$1
}

migrations-new_migration () {
	docker-compose -f docker/docker-compose.yml run migrations-app rake db:new_migration name=$1
}


function cdl() { cd "$1"; ls -lf; }

function merge-ours () {
	git merge -s ours --no-ff $1
}
function cherry-pick-ours () {
    git cherry-pick --strategy=recursive -X ours $1
}
#THIS MUST BE AT THE END OF THE FILE FOR SDKMAN TO WORK!!!
export SDKMAN_DIR="/Users/cdevoto/.sdkman"
[[ -s "/Users/cdevoto/.sdkman/bin/sdkman-init.sh" ]] && source "/Users/cdevoto/.sdkman/bin/sdkman-init.sh"

[[ -s "$HOME/.rvm/scripts/rvm" ]] && source "$HOME/.rvm/scripts/rvm" # Load RVM into a shell session *as a function*
