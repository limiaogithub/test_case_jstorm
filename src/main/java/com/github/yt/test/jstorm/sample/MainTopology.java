package com.github.yt.test.jstorm.sample;

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
public class MainTopology {

    private static Logger log = LoggerFactory.getLogger(MainTopology.class);

    public static void main(String[] args) {

        Map<String, Object> conf = new HashMap<String, Object>();
        conf.put(Config.TOPOLOGY_DEBUG, true);
        conf.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 1);

        TopologyBuilder builder = new TopologyBuilder();

        builder.setSpout("spout1", new Spouts1(), 1);
        builder.setBolt("blots1", new Blots1(), 1).shuffleGrouping("spout1");

        //远程模式
//        String topologyName = "limiao_test";
//        ConfigExtension.setUserDefinedLogbackConf(conf, "logback.xml");
//        try {
//            StormSubmitter.submitTopology(topologyName, conf, builder.createTopology());
//        } catch (AlreadyAliveException e) {
//            e.printStackTrace();
//        } catch (InvalidTopologyException e) {
//            e.printStackTrace();
//        }
//        log.info("远程模式启动... ...");

        //本地模式
        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("hello-topology1", conf, builder.createTopology());
        log.info("本地模式启动... ...");
    }
}