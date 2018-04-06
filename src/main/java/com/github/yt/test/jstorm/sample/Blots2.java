package com.github.yt.test.jstorm.sample;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Tuple;

import java.util.HashMap;
import java.util.Map;

/**
 * @author limiao
 */
public class Blots2 implements IRichBolt {

    private OutputCollector collector;

    private Map<String, Integer> counters = new HashMap<>();

    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.collector = outputCollector;
    }

    @Override
    public void execute(Tuple tuple) {
        String str = tuple.getString(0);
        if (!counters.containsKey(str)) {
            counters.put(str, 1);
        } else {
            Integer c = counters.get(str) + 1;
            counters.put(str, c);
        }
        collector.ack(tuple);
    }

    @Override
    public void cleanup() {
        for (String key : counters.keySet()) {
            System.out.println("key:" + key + "value:" + counters.get(key));
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        System.out.println("Blots2 declareOutputFields");
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        System.out.println("Blots2 getComponentConfiguration");
        return null;
    }
}
