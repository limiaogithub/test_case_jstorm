package com.github.yt.test.jstorm.stream;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;

import java.util.Map;

/**
 * @author limiao
 */
public class StreamBlots implements IRichBolt {

    //private OutputCollector collector;

    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        //this.collector = outputCollector;
    }

    @Override
    public void execute(Tuple tuple) {
        String sentence = tuple.getString(0);
        System.out.println("-------------------- StreamBlots1:[" + Thread.currentThread() + "],str:[" + sentence + "]");
    }

    @Override
    public void cleanup() {

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("word"));
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}
