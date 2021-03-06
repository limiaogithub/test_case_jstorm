package com.github.yt.test.jstorm.grouping.all;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author limiao
 */
public class AllGroupingTopology {

    private static Map<String, Object> conf = new HashMap<>();

    static {
        conf.put(Config.TOPOLOGY_DEBUG, true);
        conf.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 1);
    }

    public static void main(String[] args) {

        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("spout1", new AllGroupingSpouts(), 1);
        builder.setBolt("blots1", new AllGroupingBlots(), 2).allGrouping("spout1");

        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("topology1", conf, builder.createTopology());
        System.out.println("启动成功!");
    }
}