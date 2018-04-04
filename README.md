# test_case_jstorm
程序例子+如何在centos7上搭建jstrom环境+部署jstormUI监控

<h1>1.准备</h1>
1.下载zookeeper,我下载的3.4.11</br>
https://mirrors.tuna.tsinghua.edu.cn/apache/zookeeper/</br>
</br>
2.下载jstorm jar包 jstorm-2.2.1.zip</br>
http://www.jstorm.io/Downloads.html</br>
</br>
3.准备好一个tomcat</br>
4.准备好java环境,我装的jdk8</br>
</br>
<h1>2.开始</h1>
zookeeper</br>
tar -zxvf zookeeper-3.4.6.tar.gz  </br>
设置环境变量 </br>
export ZOOKEEPER_HOME=/opt/app/zookeeper-3.4.11   </br>
export PATH=$ZOOKEEPER_HOME:$PATH </br>
source /etc/profile</br>
到目录下 mv zoo_sample.cfg ./zoo.cfg </br>
sh zkServer.sh start</br>
启动成功</br>
</br>
</br>
jstorm</br>
unzip jstorm-2.2.1.zip</br>
export JSTORM_HOME=/opt/app/jstorm-2.2.1</br>
export PATH=$JSTORM_HOME/bin:$PATH</br>
source /etc/profile</br>
修改配置文件 /opt/app/jstorm-2.2.1/conf/storm.yaml,在首行加入</br>
<pre>
nimbus.childopts: "-Xms512m -Xmx512m -Xmn768m -XX:SurvivorRatio=4 -XX:MaxTenuringThreshold=10 -XX:+UseConcMarkSweepGC  -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70 -XX:+HeapDumpOnOutOfMemoryError -XX:CMSMaxAbortablePrecleanTime=5000"
</pre>
#mkdir ~/.jstorm  </br>
#cp -f $JSTORM_HOME/conf/storm.yaml ~/.jstorm</br>
nohup jstorm nimbus &  </br>
nohup jstorm supervisor & </br>
/opt/app/jstorm-2.2.1/logs看有没有日志</br>
启动成功</br>
</br>
</br>
发布</br>
把git工程打成war包</br>
jstorm jar jstorm-core-2.2.1.jar com.github.yt.test.jstorm.sample.topology.Main</br>
