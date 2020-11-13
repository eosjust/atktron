#!/bin/bash
mv ~/atktron/dstron/start.sh /etc/rc.d/init.d/startdstron.sh
chmod a+x /etc/rc.d/init.d/startdstron.sh
cd /etc/rc.d/init.d
chkconfig --add startdstron.sh
chkconfig startdstron.sh on