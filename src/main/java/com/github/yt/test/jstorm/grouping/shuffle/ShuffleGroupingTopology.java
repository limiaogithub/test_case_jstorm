package com.github.yt.test.jstorm.grouping.shuffle;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author limiao
 */
public class ShuffleGroupingTopology {

    private static Map<String, Object> conf = new HashMap<>();

    static {
        conf.put(Config.TOPOLOGY_DEBUG, true);
        conf.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 1);
    }

    public static void main(String[] args) {

        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("spout1", new ShuffleGroupingSpouts(), 1);
        builder.setBolt("blots1", new ShuffleGroupingBlots(), 3).shuffleGrouping("spout1");

        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("topology1", conf, builder.createTopology());
        System.out.println("启动成功!");
    }

}