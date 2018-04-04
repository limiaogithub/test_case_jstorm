package com.github.yt.test.jstorm.sample.topology;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import com.github.yt.test.jstorm.sample.bolts.Blots1;
import com.github.yt.test.jstorm.sample.bolts.Blots2;
import com.github.yt.test.jstorm.sample.spouts.Spouts1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class Main {

    private static Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        Map<String, Object> conf = new HashMap<String, Object>();
        conf.put(Config.TOPOLOGY_DEBUG, true);
        conf.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 1);

        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("spout-spouts1", new Spouts1(), 1);
        builder.setBolt("blots1", new Blots1(), 1).shuffleGrouping("spout-spouts1");
        builder.setBolt("blots2", new Blots2(), 1).shuffleGrouping("blots1");

        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("hello-topology1", conf, builder.createTopology());
        log.info("本地模式启动... ...");
    }
}