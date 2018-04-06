package com.github.yt.test.jstorm.grouping.fieldGrouping;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;

import java.util.HashMap;
import java.util.Map;

/**
 * @author limiao
 */
public class FieldsGroupingTopology {

    private static Map<String, Object> conf = new HashMap<>();
    static {
        conf.put(Config.TOPOLOGY_DEBUG, true);
        conf.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 1);
    }

    public static void main(String[] args) {

        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("spout1", new FieldsGroupingSpouts(), 1);
        builder.setBolt("blots1", new FieldsGroupingBlots1(), 3).fieldsGrouping("spout1", new Fields("line"));

        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("hello-topology1", conf, builder.createTopology());
        System.out.println("启动成功!");
    }
}