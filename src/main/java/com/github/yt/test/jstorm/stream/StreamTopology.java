package com.github.yt.test.jstorm.stream;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author limiao
 */
public class StreamTopology {

    private static Logger log = LoggerFactory.getLogger(StreamTopology.class);

    private static Map<String, Object> conf = new HashMap<>();

    static {
        conf.put(Config.TOPOLOGY_DEBUG, true);
        conf.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 1);
    }

    public static void main(String[] args) {

        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("spout1", new StreamSpouts(), 1);
        builder.setBolt("blots1", new StreamBlots(), 1).shuffleGrouping("spout1","stream1");
        builder.setBolt("blots2", new StreamBlots2(), 1).shuffleGrouping("spout1","stream2");

        //本地模式
        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("hello-topology1", conf, builder.createTopology());
        log.info("本地模式启动... ...");
    }
}