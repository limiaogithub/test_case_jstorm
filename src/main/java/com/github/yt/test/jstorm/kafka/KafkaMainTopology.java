package com.github.yt.test.jstorm.kafka;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import com.alibaba.jstorm.utils.LoadConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;


/**
 * @author limiao
 */
public class KafkaMainTopology {

    private static Logger log = LoggerFactory.getLogger(KafkaMainTopology.class);

    private static Map<String, Object> conf = new HashMap<>();

    static {
        conf.put(Config.TOPOLOGY_DEBUG, true);
        conf.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 1);
    }

    /**
     * @param arg String
     */
    public static void loadConf(String arg) {
        conf.putAll(LoadConf.LoadProperty(arg));
    }

    public static void main(String[] args) {

        loadConf("kafka.properties");

        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("spout1", new KafkaSpouts(), 1);
        builder.setBolt("blots1", new KafkaBlots(), 1).shuffleGrouping("spout1");

        //本地模式
        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("hello-topology1", conf, builder.createTopology());
        log.info("本地模式启动... ...");
    }
}