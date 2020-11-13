#!/bin/bash
#chkconfig:2345 80 90
#decription:autostart
sleep 30s
nohup java -jar ~/atktron/dstron/target/dstron.jar >> ~/dstron.log 2>&1 &