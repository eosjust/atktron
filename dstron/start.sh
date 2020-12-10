#!/bin/bash
#chkconfig:2345 80 90
#decription:autostart
export JAVA_HOME=/opt/java/jdk1.8.0_271
export PATH=$JAVA_HOME/bin:$PATH
export NODE_HOME=/usr/local/node-v14.6.0
export PATH=$PATH:$NODE_HOME/bin

nohup node ~/nodetron/bin/www >> ~/nodetron.log 2>&1 &
nohup java -jar ~/atktron/dstron/target/dstron.jar >> ~/dstron.log 2>&1 &